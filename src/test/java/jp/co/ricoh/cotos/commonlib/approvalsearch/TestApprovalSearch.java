package jp.co.ricoh.cotos.commonlib.approvalsearch;

import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.dto.result.ApprovalRouteMasterResult;
import jp.co.ricoh.cotos.commonlib.dto.result.RouteFormulaResult.RouteFormulaStatus;
import jp.co.ricoh.cotos.commonlib.logic.approvalsearch.ApprovalSearch;

@RunWith(SpringRunner.class)
@SpringBootTest
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
