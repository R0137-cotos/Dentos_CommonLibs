package jp.co.ricoh.cotos.commonlib.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.Status;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.FunctionCheckEstimation;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFunctionCheckEstimation {

	private static String momServiceUrl = "http://165.96.254.183:10110/jmo/services/KengenService";
	private static String relatedId = "JMR";
	private static String dbUrl = "jdbc:oracle:thin:@dev-db.cotos.ricoh.co.jp:1521/pdb1";
	private static String dbUser = "cotos_dev";
	private static String dbPassword = "cotos_dev";

	@Autowired
	DBUtil dbUtil;
	@Autowired
	FunctionCheckEstimation functionCheckEstimation;
	@Autowired
	EstimationRepository estimationRepository;
	@Autowired
	EstimationApprovalRouteNodeRepository estimationApprovalRouteNodeRepository;
	
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
	public void 見積情報取得チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationFind(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationFind(999L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在する
		try {
			functionCheckEstimation.checkEstimationFind(1L);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報更新チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		Estimation estimation = new Estimation();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(estimation, "estimation");

		// 見積情報がNull
		try {
			functionCheckEstimation.checkEstimationUpdate(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		estimation = estimationRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationUpdate(estimation, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationUpdate(estimation, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckEstimation.checkEstimationUpdate(estimation, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		bindingResult = new BeanPropertyBindingResult(estimation, "estimation");
		// 見積コピー時に見積ステータスが不正
		try {
			Estimation estimationTemp = estimationRepository.findOne(2L);
			functionCheckEstimation.checkEstimationUpdate(estimationTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに作成中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID共にTBLに存在する
		try {
			bindingResult = new BeanPropertyBindingResult(estimation, "estimation");
			functionCheckEstimation.checkEstimationUpdate(estimation, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報帳票出力チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationOutPutReport(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationOutPutReport(999L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在する
		try {
			functionCheckEstimation.checkEstimationOutPutReport(1L);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報承認ルート取得チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationFindApprover(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationFindApprover(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationFindApprover(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationFindApprover(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータに存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID共にTBLに存在する
		try {
			functionCheckEstimation.checkEstimationFindApprover(1L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報代理承認者設定チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		EstimationApprovalRouteNode estimationApprovalRouteNode = new EstimationApprovalRouteNode();
		// 見積承認ルートノードがNull
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationApprovalRouteNode", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積承認ルートノード情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積承認ルートノードがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(estimationApprovalRouteNode, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist EstimationApprovalRouteNode", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積承認ルートノードIDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		estimationApprovalRouteNode = estimationApprovalRouteNodeRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(estimationApprovalRouteNode, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(estimationApprovalRouteNode, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 承認者と代理承認者が重複
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(estimationApprovalRouteNode, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Duplication Error Estimation SubApproverEmployee", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "承認者と代理承認者に同様のユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		estimationApprovalRouteNode = estimationApprovalRouteNodeRepository.findOne(2L);
		// 見積承認ルートノード、MoM社員ID共にTBLに存在し、承認者と代理承認者が重複していない
		try {
			functionCheckEstimation.checkEstimationRegisterSubApproverEmployee(estimationApprovalRouteNode, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("正常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報承認依頼チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		Estimation estimation = new Estimation();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(estimation, "estimation");

		// 見積情報がNull
		try {
			functionCheckEstimation.checkEstimationApprovalRequestFirst(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimation, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		estimation = estimationRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimation, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimation, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			Estimation estimationTemp = new Estimation();
			BeanUtils.copyProperties(estimation, estimationTemp);
			estimationTemp.setStatus(Status.承認依頼中);
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimationTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに作成中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積情報の承認ルート情報が未設定
		try {
			Estimation estimationTemp = new Estimation();
			BeanUtils.copyProperties(estimation, estimationTemp);
			estimationTemp.setEstimationApprovalRoute(null);
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimationTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist EstimationApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckEstimation.checkEstimationApprovalRequestFirst(estimation, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}

		bindingResult = new BeanPropertyBindingResult(estimation, "estimation");
		List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList = new ArrayList<>();
		List<EstimationApprovalRouteNode> approvalRouteNodeMasterList = new ArrayList<>();
		String approvalRequesterMomEmployeeId = "00483179";
		// 承認者と代理承認者が重複
		try {
			Estimation estimationTemp = estimationRepository.findOne(7L);
			estimationApprovalRouteNodeList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			functionCheckEstimation.checkEstimationApprovalRequestSecond(estimationApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Duplication Error Estimation SubApproverEmployee", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "承認者と代理承認者に同様のユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 見積承認ルートと承認ルートマスタが不一致
		try {
			Estimation estimationTemp = estimationRepository.findOne(8L);
			estimationApprovalRouteNodeList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			estimationTemp = estimationRepository.findOne(9L);
			approvalRouteNodeMasterList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			functionCheckEstimation.checkEstimationApprovalRequestSecond(estimationApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Not Equal Error EstimationApprovalRouteNodeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積承認ルートに承認ルートマスタと異なるルート情報が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 代理承認者の組織階層が承認者より上位
		try {
			Estimation estimationTemp = estimationRepository.findOne(10L);
			estimationApprovalRouteNodeList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			approvalRouteNodeMasterList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			functionCheckEstimation.checkEstimationApprovalRequestSecond(estimationApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Level Superior Error Estimation SubApprover OrgHierarchy", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "代理承認者に承認者より下位階層に位置するユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 代理承認者に承認権限がない
		try {
			Estimation estimationTemp = estimationRepository.findOne(11L);
			estimationApprovalRouteNodeList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			approvalRouteNodeMasterList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			approvalRequesterMomEmployeeId = "00548632";
			functionCheckEstimation.checkEstimationApprovalRequestSecond(estimationApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Approval Authority Error EstimationSubApprover", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "代理承認者に承認権限がないユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 上記チェックが全て正常
		try {
			Estimation estimationTemp = estimationRepository.findOne(12L);
			estimationApprovalRouteNodeList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			approvalRouteNodeMasterList = estimationTemp.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();
			approvalRequesterMomEmployeeId = "00548632";
			functionCheckEstimation.checkEstimationApprovalRequestSecond(estimationApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
		} catch (Exception ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報承認依頼差戻チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationApproval(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationApproval(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckEstimation.checkEstimationApproval(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに承認依頼中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積承認ルートがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(4L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist EstimationApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID、見積承認ルートがTBLに存在し、見積ステータスが正常
		try {
			functionCheckEstimation.checkEstimationApproval(5L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報承認チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationApproval(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationApproval(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckEstimation.checkEstimationApproval(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに承認依頼中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積承認ルートがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationApproval(4L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist EstimationApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID、見積承認ルートがTBLに存在し、見積ステータスが正常
		try {
			functionCheckEstimation.checkEstimationApproval(5L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報最終承認チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationLastApproval(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationLastApproval(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationLastApproval(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationLastApproval(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckEstimation.checkEstimationLastApproval(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに承認依頼中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID共にTBLに存在し、見積ステータスが正常
		try {
			functionCheckEstimation.checkEstimationLastApproval(4L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報受注チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationAccept(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationAccept(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationAccept(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationAccept(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckEstimation.checkEstimationAccept(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに承認済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID共にTBLに存在する
		try {
			functionCheckEstimation.checkEstimationAccept(3L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 見積情報失注チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckEstimation.checkEstimationCancel(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationCancel(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckEstimation.checkEstimationCancel(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationCancel(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckEstimation.checkEstimationCancel(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに受注が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員ID共にTBLに存在する
		try {
			functionCheckEstimation.checkEstimationCancel(1L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 企事部情報取得チェック確認() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		dbUtil.execute("sql/check/testKjbMasterInsert.sql", new HashMap<>());

		// 企事部IDがNull
		try {
			functionCheckEstimation.checkEstimationFindKjbInfo(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MclMomKjbId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの企事部IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 企事部IDがTBLに存在しない
		try {
			functionCheckEstimation.checkEstimationFindKjbInfo("000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist KjbMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない企事部IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 企事部IDがTBLに存在する
		try {
			functionCheckEstimation.checkEstimationFindKjbInfo("000000000447380");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	private void 見積データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationApprovalRouteInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationApprovalRouteNodeInseet.sql", new HashMap<>());
	}
}
