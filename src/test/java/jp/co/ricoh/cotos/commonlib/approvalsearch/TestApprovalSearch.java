package jp.co.ricoh.cotos.commonlib.approvalsearch;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.result.ApprovalRouteMasterResult;
import jp.co.ricoh.cotos.commonlib.dto.result.RouteFormulaResult.RouteFormulaStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.logic.approvalsearch.ApprovalSearch;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApprovalSearch {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	ApprovalSearch approvalSearch;
	@Autowired
	EstimationRepository estimationRepository;
	@Autowired
	ContractRepository contractRepository;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}

	@Test
	@Transactional
	public void 見積承認ルート取得確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		見積データ作成();

		// 正常
		try {
			Estimation estimation = estimationRepository.findOne(13L);
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(estimation, null);
			Assert.assertEquals("ステータスが正常であること", RouteFormulaStatus.正常, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートマスタが正しく取得されること", 1L, result.getApprovalRouteMaster().getId());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}

		// 警告
		try {
			Estimation estimation = estimationRepository.findOne(14L);
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(estimation, null);
			Assert.assertEquals("ステータスが警告であること", RouteFormulaStatus.警告, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートが特定できないこと", false, result.getRouteFormulaResult().isApplyRoute());
			Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約承認ルート取得確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 正常
		try {
			Contract contract = contractRepository.findOne(12L);
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(null, contract);
			Assert.assertEquals("ステータスが正常であること", RouteFormulaStatus.正常, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートマスタが正しく取得されること", 2L, result.getApprovalRouteMaster().getId());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}

		// 警告
		try {
			Contract contract = contractRepository.findOne(13L);
			ApprovalRouteMasterResult result = approvalSearch.findApprovalRouteMaster(null, contract);
			Assert.assertEquals("ステータスが警告であること", RouteFormulaStatus.警告, result.getRouteFormulaResult().getStatus());
			Assert.assertEquals("承認ルートが特定できないこと", false, result.getRouteFormulaResult().isApplyRoute());
			Assert.assertEquals("エラー項目数", 1, result.getRouteFormulaResult().getErrorPropertyList().size());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
	}

	private void 見積データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testApprovalRouteGrpMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testApprovalRouteMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationInsert.sql", new HashMap<>());
	}

	private void 契約データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testApprovalRouteGrpMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testApprovalRouteMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractInsert.sql", new HashMap<>());
	}
}
