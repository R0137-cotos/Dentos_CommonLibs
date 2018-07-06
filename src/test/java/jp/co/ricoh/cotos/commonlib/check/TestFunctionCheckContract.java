package jp.co.ricoh.cotos.commonlib.check;

import java.util.ArrayList;
import java.util.Calendar;
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

import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.FunctionCheckContract;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFunctionCheckContract {

	private static String momServiceUrl = "http://165.96.254.183:10110/jmo/services/KengenService";
	private static String relatedId = "JMR";
	private static String dbUrl = "jdbc:oracle:thin:@dev-db.cotos.ricoh.co.jp:1521/pdb1";
	private static String dbUser = "cotos_dev";
	private static String dbPassword = "cotos_dev";

	@Autowired
	DBUtil dbUtil;
	@Autowired
	FunctionCheckContract functionCheckContract;
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	ContractApprovalRouteNodeRepository contractApprovalRouteNodeRepository;

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
	public void 契約情報作成チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		見積データ作成();

		// 見積IDがNull
		try {
			functionCheckContract.checkContractCreate(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error EstimationId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの見積IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積IDがTBLに存在しない
		try {
			functionCheckContract.checkContractCreate(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない見積IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractCreate(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractCreate(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ステータスが不正
		try {
			functionCheckContract.checkContractCreate(1L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error EstimationStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "見積ステータスに受注以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 見積ID、MoM社員IDがTBLに存在し、見積ステータスが正常
		try {
			functionCheckContract.checkContractCreate(2L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報取得チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckContract.checkContractFind(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractFind(999L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在する
		try {
			functionCheckContract.checkContractFind(1L);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報更新チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		Calendar calDate = Calendar.getInstance();
		Contract contract = new Contract();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(contract, "contract");

		// 契約情報がNull
		try {
			functionCheckContract.checkContractUpdate(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractUpdate(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractUpdate(contract, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractUpdate(contract, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 解約予定日NotNullチェック
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			calDate.set(2019, 3, 20);
			contractTemp.setCancelScheduledDate(new java.sql.Date(calDate.getTimeInMillis()));
			functionCheckContract.checkContractUpdate(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Empty Error CancelScheduledDate", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "解約予定日が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckContract.checkContractUpdate(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員ID共にTBLに存在する
		try {
			bindingResult = new BeanPropertyBindingResult(contract, "contract");
			functionCheckContract.checkContractUpdate(contract, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報承認ルート取得チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckContract.checkContractFindApprover(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractFindApprover(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractFindApprover(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractFindApprover(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータに存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員ID共にTBLに存在する
		try {
			functionCheckContract.checkContractFindApprover(1L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報代理承認者設定チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		// 契約承認ルートノードがNull
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractApprovalRouteNode", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約承認ルートノード情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約承認ルートノードがTBLに存在しない
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(contractApprovalRouteNode, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist ContractApprovalRouteNode", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約承認ルートノードIDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contractApprovalRouteNode = contractApprovalRouteNodeRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(contractApprovalRouteNode, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(contractApprovalRouteNode, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 承認者と代理承認者が重複
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(contractApprovalRouteNode, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Duplication Error Contract SubApproverEmployee", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "承認者と代理承認者に同様のユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contractApprovalRouteNode = contractApprovalRouteNodeRepository.findOne(2L);
		// 契約承認ルートノード、MoM社員ID共にTBLに存在し、承認者と代理承認者が重複していない
		try {
			functionCheckContract.checkContractRegisterSubApproverEmployee(contractApprovalRouteNode, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("正常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報承認依頼チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		Contract contract = new Contract();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(contract, "contract");

		// 契約情報がNull
		try {
			functionCheckContract.checkContractApprovalRequestFirst(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApprovalRequestFirst(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractApprovalRequestFirst(contract, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApprovalRequestFirst(contract, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			contractTemp.setContractStatus(ContractStatus.承認依頼中);
			functionCheckContract.checkContractApprovalRequestFirst(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに作成中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約情報の承認ルート情報が未設定
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			contractTemp.setContractApprovalRoute(null);
			functionCheckContract.checkContractApprovalRequestFirst(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist ContractApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckContract.checkContractApprovalRequestFirst(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}

		bindingResult = new BeanPropertyBindingResult(contract, "contract");
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		List<ContractApprovalRouteNode> approvalRouteNodeMasterList = new ArrayList<>();
		String approvalRequesterMomEmployeeId = "00483179";
		// 承認者と代理承認者が重複
		try {
			Contract contractTemp = contractRepository.findOne(6L);
			contractApprovalRouteNodeList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			functionCheckContract.checkContractApprovalRequestSecond(contractApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Duplication Error Contract SubApproverEmployee", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "承認者と代理承認者に同様のユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 契約承認ルートと承認ルートマスタが不一致
		try {
			Contract contractTemp = contractRepository.findOne(7L);
			contractApprovalRouteNodeList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			contractTemp = contractRepository.findOne(8L);
			approvalRouteNodeMasterList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			functionCheckContract.checkContractApprovalRequestSecond(contractApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Not Equal Error ContractApprovalRouteNodeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約承認ルートに承認ルートマスタと異なるルート情報が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 代理承認者の組織階層が承認者より上位
		try {
			Contract contractTemp = contractRepository.findOne(9L);
			contractApprovalRouteNodeList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			approvalRouteNodeMasterList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			functionCheckContract.checkContractApprovalRequestSecond(contractApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Level Superior Error Contract SubApprover OrgHierarchy", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "代理承認者に承認者より下位階層に位置するユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 代理承認者に承認権限がない
		try {
			Contract contractTemp = contractRepository.findOne(10L);
			contractApprovalRouteNodeList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			approvalRouteNodeMasterList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			approvalRequesterMomEmployeeId = "00548632";
			functionCheckContract.checkContractApprovalRequestSecond(contractApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Approval Authority Error ContractSubApprover", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "代理承認者に承認権限がないユーザーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		} catch (Exception e) {
			Assert.fail("異常終了してしまった");
		}
		// 上記チェックが全て正常
		try {
			Contract contractTemp = contractRepository.findOne(11L);
			contractApprovalRouteNodeList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			approvalRouteNodeMasterList = contractTemp.getContractApprovalRoute().getContractApprovalRouteNodeList();
			approvalRequesterMomEmployeeId = "00548632";
			functionCheckContract.checkContractApprovalRequestSecond(contractApprovalRouteNodeList, approvalRouteNodeMasterList, approvalRequesterMomEmployeeId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);
		} catch (Exception ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報承認依頼差戻チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckContract.checkContractApproval(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractApproval(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			functionCheckContract.checkContractApproval(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに承認依頼中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約承認ルートがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(4L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist ContractApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員ID、契約承認ルートがTBLに存在し、契約ステータスが正常
		try {
			functionCheckContract.checkContractApproval(5L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報承認チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckContract.checkContractApproval(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractApproval(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			functionCheckContract.checkContractApproval(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに承認依頼中以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約承認ルートがTBLに存在しない
		try {
			functionCheckContract.checkContractApproval(4L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist ContractApprovalRoute", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約情報の承認ルート情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員ID、契約承認ルートがTBLに存在し、契約ステータスが正常
		try {
			functionCheckContract.checkContractApproval(5L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	// TODO: リポジトリからfindのみしかしていないが、EntityBaseのpreupdateが実行される。認証情報が必要なため、一時的に下記アノテーションを使用。
	@WithMockCustomUser
	public void 契約情報解約手続きチェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		Calendar calDate = Calendar.getInstance();
		Contract contract = new Contract();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(contract, "contract");

		// 契約情報がNull
		try {
			functionCheckContract.checkContractCancellation(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractCancellation(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractCancellation(contract, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractCancellation(contract, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			functionCheckContract.checkContractCancellation(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに承認済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(2L);
		// 契約日Nullチェック
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			contractTemp.setContractDate(null);
			functionCheckContract.checkContractCancellation(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Not Empty Error ContractDate", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約日が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 解約予定日Nullチェック
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			contractTemp.setCancelScheduledDate(null);
			functionCheckContract.checkContractCancellation(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Not Empty Error CancelScheduledDate", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "解約予定日が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約日解約予定逆転チェック
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			calDate.set(2018, 3, 21);
			contractTemp.setContractDate(new java.sql.Date(calDate.getTimeInMillis()));
			calDate.set(2018, 3, 20);
			contractTemp.setCancelScheduledDate(new java.sql.Date(calDate.getTimeInMillis()));
			functionCheckContract.checkContractCancellation(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Date Reverse Error", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "解約予定日以降の日付が契約日に設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckContract.checkContractApprovalRequestFirst(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員IDがTBLに存在し、契約ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(contract, "contract");
			calDate.set(2019, 3, 20);
			contract.setCancelScheduledDate(new java.sql.Date(calDate.getTimeInMillis()));
			functionCheckContract.checkContractCancellation(contract, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報情報変更チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		Calendar calDate = Calendar.getInstance();
		Contract contract = new Contract();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(contract, "contract");

		// 契約情報がNull
		try {
			functionCheckContract.checkContractUpdateAfter(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractUpdateAfter(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractUpdateAfter(contract, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractUpdateAfter(contract, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			functionCheckContract.checkContractUpdateAfter(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに承認済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		contract = contractRepository.findOne(2L);
		// 解約予定日NotNullチェック
		try {
			Contract contractTemp = new Contract();
			BeanUtils.copyProperties(contract, contractTemp);
			calDate.set(2019, 3, 20);
			contractTemp.setCancelScheduledDate(new java.sql.Date(calDate.getTimeInMillis()));
			functionCheckContract.checkContractUpdateAfter(contractTemp, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Empty Error CancelScheduledDate", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "解約予定日が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckContract.checkContractUpdateAfter(contract, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員IDがTBLに存在し、契約ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(contract, "contract");
			functionCheckContract.checkContractUpdateAfter(contract, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 契約情報プラン変更チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckContract.checkContractPlanChange(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error ContractId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckContract.checkContractPlanChange(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Entity Does Not Exist Contract", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckContract.checkContractPlanChange(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckContract.checkContractPlanChange(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ステータスが不正
		try {
			functionCheckContract.checkContractPlanChange(1L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Wrong Not Error ContractStatus", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "契約ステータスに承認済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// プラン変更実施済の契約情報
		try {
			functionCheckContract.checkContractPlanChange(2L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Condition Match Error Estimation", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "既にプラン変更実施済の契約情報です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約ID、MoM社員IDがTBLに存在し、契約ステータスが正常
		try {
			functionCheckContract.checkContractPlanChange(3L, "00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 得意先情報取得チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		dbUtil.execute("sql/check/testBillingCustomerInfoMasterInsert.sql", new HashMap<>());

		// 得意先コードがNull
		try {
			functionCheckContract.checkContractFindBillingCustomerInfo(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error OriginalSystemCode", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの得意先コードが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 得意先コードがTBLに存在しない
		try {
			functionCheckContract.checkContractFindBillingCustomerInfo("000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist BillingCustomerMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない得意先コードが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 得意先コードがTBLに存在する
		try {
			functionCheckContract.checkContractFindBillingCustomerInfo("60247295433");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	private void 見積データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationInsert.sql", new HashMap<>());
	}

	private void 契約データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeConInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEstimationInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractApprovalRouteInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractApprovalRouteNodeInseet.sql", new HashMap<>());
	}
}
