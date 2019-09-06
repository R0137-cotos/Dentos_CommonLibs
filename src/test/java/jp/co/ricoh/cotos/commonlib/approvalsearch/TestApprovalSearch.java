package jp.co.ricoh.cotos.commonlib.approvalsearch;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.dto.result.ApprovalRouteMasterResult;
import jp.co.ricoh.cotos.commonlib.dto.result.RouteFormulaResult.RouteFormulaStatus;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.logic.approvalsearch.ApprovalSearch;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestApprovalSearch {

	@Autowired
	ApprovalSearch approvalSearch;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	@Transactional
	public void 承認ルート取得確認_正常() {
		テストデータ作成();

		try {
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(1L, テストエンティティ作成(false), "test");
			Assert.assertEquals("ステータスが正常であること", RouteFormulaStatus.正常, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートマスタが正しく取得されること", 1L, result.getApprovalRouteMaster().getId());
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 承認ルート取得確認_異常() {
		テストデータ作成();

		try {
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(2L, テストエンティティ作成(false), "test");
			Assert.assertEquals("ステータスが異常であること", RouteFormulaStatus.異常, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートが特定できないこと", false, result.getRouteFormulaResult().isApplyRoute());
			Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 承認ルート取得確認_警告() {
		テストデータ作成();

		try {
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(3L, テストエンティティ作成(true), "test");
			Assert.assertEquals("ステータスが警告であること", RouteFormulaStatus.警告, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートが特定できないこと", true, result.getRouteFormulaResult().isApplyRoute());
			Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 複数承認ルート取得確認_正常() {
		テストデータ作成();

		try {
			List<ApprovalRouteMasterResult> resultList = approvalSearch.findApprovalRouteMasterCandidate(1L, テストエンティティ作成(false), "test");
			Assert.assertEquals("承認ルートが複数取れること", 2, resultList.size());
			resultList.stream().forEach(result -> {
				Assert.assertEquals("ステータスが正常であること", RouteFormulaStatus.正常, result.getRouteFormulaResult().getStatus());
				Assert.assertEquals("承認ルートマスタが正しく取得されること", 1L, result.getApprovalRouteMaster().getApprovalRouteGrpMaster().getId());
			});
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 複数承認ルート取得確認_正常異常混在() {
		テストデータ作成();

		try {
			List<ApprovalRouteMasterResult> resultList = approvalSearch.findApprovalRouteMasterCandidate(2L, テストエンティティ作成(false), "test");
			resultList.stream().filter(result -> RouteFormulaStatus.異常 == result.getRouteFormulaResult().getStatus()).forEach(result -> {
				Assert.assertEquals("承認ルートが特定できないこと", false, result.getRouteFormulaResult().isApplyRoute());
				Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
			});
			resultList.stream().filter(result -> RouteFormulaStatus.正常 == result.getRouteFormulaResult().getStatus()).forEach(result -> {
				Assert.assertEquals("承認ルートマスタが正しく取得されること", 5L, result.getApprovalRouteMaster().getId());
			});
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 複数承認ルート取得確認_正常警告混在() {
		テストデータ作成();

		try {
			List<ApprovalRouteMasterResult> resultList = approvalSearch.findApprovalRouteMasterCandidate(3L, テストエンティティ作成(true), "test");
			resultList.stream().filter(result -> RouteFormulaStatus.警告 == result.getRouteFormulaResult().getStatus()).forEach(result -> {
				Assert.assertEquals("承認ルートが特定できないこと", true, result.getRouteFormulaResult().isApplyRoute());
				Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
			});
			resultList.stream().filter(result -> RouteFormulaStatus.正常 == result.getRouteFormulaResult().getStatus()).forEach(result -> {
				Assert.assertEquals("承認ルートマスタが正しく取得されること", 6L, result.getApprovalRouteMaster().getId());
			});
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void 承認ルート特定_承認ルートマスタIDによる絞り込み() {

		テストデータ作成();

		Estimation estimation = new Estimation();
		EstimationApprovalRoute estimationApprovalRoute = new EstimationApprovalRoute();
		estimationApprovalRoute.setApprovalRouteMasterId(8L);
		estimation.setEstimationApprovalRoute(estimationApprovalRoute);

		Contract contract = new Contract();
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRouteMasterId(9L);
		contract.setContractApprovalRouteList(Arrays.asList(contractApprovalRoute));

		ArrangementWork arrangementWork = new ArrangementWork();
		ArrangementWorkApprovalRoute arrangementWorkApprovalRoute = new ArrangementWorkApprovalRoute();
		arrangementWorkApprovalRoute.setApprovalRouteMasterId(12L);
		arrangementWork.setArrangementWorkApprovalRoute(arrangementWorkApprovalRoute);

		ApprovalRouteMasterResult result1 = approvalSearch.findApprovalRouteMaster(4L, estimation, "estimation");
		Assert.assertNotNull("承認ルートが正しく取得されること", result1);
		Assert.assertEquals("見積承認ルートの承認ルートマスタIDと一致する、承認ルートが取れること", result1.getApprovalRouteMaster().getId(), 8L);

		ApprovalRouteMasterResult result2 = approvalSearch.findApprovalRouteMaster(5L, contract, "contrct");
		Assert.assertNotNull("承認ルートが正しく取得されること", result2);
		Assert.assertEquals("契約承認ルートの承認ルートマスタIDと一致する、承認ルートが取れること", result2.getApprovalRouteMaster().getId(), 9L);

		ApprovalRouteMasterResult result3 = approvalSearch.findApprovalRouteMaster(6L, arrangementWork, "arrangementWork");
		Assert.assertNotNull("承認ルートが正しく取得されること", result3);
		Assert.assertEquals("手配業務承認ルートの承認ルートマスタIDと一致する、承認ルートが取れること", result3.getApprovalRouteMaster().getId(), 12L);

		estimationApprovalRoute.setApprovalRouteMasterId(7L);
		estimation.setEstimationApprovalRoute(estimationApprovalRoute);
		try {
			approvalSearch.findApprovalRouteMaster(1L, estimation, "estimation");
			Assert.fail("例外が発生しない");
		} catch (NoSuchElementException ignore) {
		} catch (Exception e) {
			Assert.fail("想定外の例外が発生した");
		}
	}

	private void テストデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/approval/testProductGrpMasterInsert.sql");
		context.getBean(DBConfig.class).initTargetTestData("sql/approval/testApprovalRouteGrpMasetrInsert.sql");
		context.getBean(DBConfig.class).initTargetTestClobData("sql/approval/testApprovalRouteMasterInsert.sql");
	}

	private TestApprovalData テストエンティティ作成(boolean isWarning) {
		TestApprovalData testApprovalData = new TestApprovalData();
		testApprovalData.setId(1L);
		testApprovalData.setName(!isWarning ? "承認ルート取得テスト" : null);
		return testApprovalData;
	}
}
