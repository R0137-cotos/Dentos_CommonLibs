package jp.co.ricoh.cotos.commonlib.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerContract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.security.util.AuthorityJudgeParamCreator;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestAuthorityJudgeParamCreator {

	@Autowired
	AuthorityJudgeParamCreator authorityJudgeParamCreator;

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.stop();
		}
	}

	@Test
	public void 正常_権限判定用パラメーター取得_見積_参照() throws ParseException {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 見積
		Estimation estimation = new Estimation();

		// 承認ルート
		EstimationApprovalRoute estimationApprovalRoute = new EstimationApprovalRoute();
		estimationApprovalRoute.setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList = new ArrayList<>();
		List<EstimationApprovalResult> estimationApprovalResultList = new ArrayList<>();
		EstimationApprovalRouteNode estimationApprovalRouteNode1 = new EstimationApprovalRouteNode();
		estimationApprovalRouteNode1.setId(1L);
		estimationApprovalRouteNode1.setApproverEmpId("00229692");
		estimationApprovalRouteNode1.setSubApproverEmpId("01901115");
		estimationApprovalRouteNode1.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		estimationApprovalRouteNodeList.add(estimationApprovalRouteNode1);
		EstimationApprovalRouteNode estimationApprovalRouteNode2 = new EstimationApprovalRouteNode();
		estimationApprovalRouteNode2.setId(2L);
		estimationApprovalRouteNode2.setApproverEmpId("01901306");
		estimationApprovalRouteNode2.setSubApproverEmpId("01901177");
		estimationApprovalRouteNode2.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		estimationApprovalRouteNodeList.add(estimationApprovalRouteNode2);
		EstimationApprovalResult estimationApprovalResult1 = new EstimationApprovalResult();
		estimationApprovalResult1.setApprovalProcessCategory(ApprovalProcessCategory.承認依頼);
		estimationApprovalResult1.setActualEmpId("00500784");
		estimationApprovalResult1.setProcessedAt(new SimpleDateFormat("yyyy/MM/dd").parse("2020/05/25"));
		estimationApprovalResultList.add(estimationApprovalResult1);
		EstimationApprovalResult estimationApprovalResult2 = new EstimationApprovalResult();
		estimationApprovalResult2.setApprovalProcessCategory(ApprovalProcessCategory.承認);
		estimationApprovalResult2.setActualEmpId("00229692");
		estimationApprovalResult2.setProcessedAt(new SimpleDateFormat("yyyy/MM/dd").parse("2020/05/26"));
		estimationApprovalResult2.setEstimationApprovalRouteNodeId(estimationApprovalRouteNode1.getId());
		estimationApprovalResultList.add(estimationApprovalResult2);

		estimationApprovalRoute.setEstimationApprovalRouteNodeList(estimationApprovalRouteNodeList);
		estimationApprovalRoute.setEstimationApprovalResultList(estimationApprovalResultList);
		estimation.setEstimationApprovalRoute(estimationApprovalRoute);

		// 担当SA
		estimation.setEstimationPicSaEmp(new EstimationPicSaEmp());
		estimation.getEstimationPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		estimation.setEstimationAddedEditorEmpList(new ArrayList<EstimationAddedEditorEmp>());
		estimation.getEstimationAddedEditorEmpList().add(new EstimationAddedEditorEmp());
		estimation.getEstimationAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		estimation.setCustomerEstimation(new CustomerEstimation());
		estimation.getCustomerEstimation().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromEstimation(estimation, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に次回代理承認者の社員情報が作成されていること", authParam.getNextSubApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に前回承認者の社員情報が作成されていること", authParam.getPrevApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に前回代理承認者の社員情報が作成されていること", authParam.getPrevSubApproverMvEmployeeMaster());
		Assert.assertNull("承認依頼者の社員情報が作成されていないこと", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_見積_承認() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 見積
		Estimation estimation = new Estimation();

		// 承認ルート
		estimation.setEstimationApprovalRoute(new EstimationApprovalRoute());
		estimation.getEstimationApprovalRoute().setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList = new ArrayList<>();
		EstimationApprovalRouteNode estimationApprovalRouteNode = new EstimationApprovalRouteNode();
		estimationApprovalRouteNode.setApproverEmpId("00229692");
		estimationApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		estimationApprovalRouteNodeList.add(estimationApprovalRouteNode);
		estimation.getEstimationApprovalRoute().setEstimationApprovalRouteNodeList(estimationApprovalRouteNodeList);

		// 担当SA
		estimation.setEstimationPicSaEmp(new EstimationPicSaEmp());
		estimation.getEstimationPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		estimation.setEstimationAddedEditorEmpList(new ArrayList<EstimationAddedEditorEmp>());
		estimation.getEstimationAddedEditorEmpList().add(new EstimationAddedEditorEmp());
		estimation.getEstimationAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		estimation.setCustomerEstimation(new CustomerEstimation());
		estimation.getCustomerEstimation().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromEstimation(estimation, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_見積_承認_代理承認者あり() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 見積
		Estimation estimation = new Estimation();

		// 承認ルート
		estimation.setEstimationApprovalRoute(new EstimationApprovalRoute());
		estimation.getEstimationApprovalRoute().setApprovalRequesterEmpId("00229692");

		// 承認ルートノード
		List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList = new ArrayList<>();
		EstimationApprovalRouteNode estimationApprovalRouteNode = new EstimationApprovalRouteNode();
		estimationApprovalRouteNode.setApproverEmpId("00229692");
		estimationApprovalRouteNode.setSubApproverEmpId("00500784");
		estimationApprovalRouteNodeList.add(estimationApprovalRouteNode);
		estimation.getEstimationApprovalRoute().setEstimationApprovalRouteNodeList(estimationApprovalRouteNodeList);

		// 担当SA
		estimation.setEstimationPicSaEmp(new EstimationPicSaEmp());
		estimation.getEstimationPicSaEmp().setMomEmployeeId("00500784");

		// 顧客
		estimation.setCustomerEstimation(new CustomerEstimation());
		estimation.getCustomerEstimation().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromEstimation(estimation, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に次回代理承認者の社員情報が作成されていること", authParam.getNextSubApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_見積_承認_ユーザー直接指定() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 見積
		Estimation estimation = new Estimation();

		// 承認ルート
		estimation.setEstimationApprovalRoute(new EstimationApprovalRoute());
		estimation.getEstimationApprovalRoute().setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList = new ArrayList<>();
		EstimationApprovalRouteNode estimationApprovalRouteNode = new EstimationApprovalRouteNode();
		estimationApprovalRouteNode.setApproverEmpId("00500784");
		estimationApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.ユーザー直接指定);
		estimationApprovalRouteNodeList.add(estimationApprovalRouteNode);
		estimation.getEstimationApprovalRoute().setEstimationApprovalRouteNodeList(estimationApprovalRouteNodeList);

		// 担当SA
		estimation.setEstimationPicSaEmp(new EstimationPicSaEmp());
		estimation.getEstimationPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		estimation.setEstimationAddedEditorEmpList(new ArrayList<EstimationAddedEditorEmp>());
		estimation.getEstimationAddedEditorEmpList().add(new EstimationAddedEditorEmp());
		estimation.getEstimationAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		estimation.setCustomerEstimation(new CustomerEstimation());
		estimation.getCustomerEstimation().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromEstimation(estimation, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に顧客情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertNotNull("正常に社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertTrue("ユーザー直接指定であること", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_参照() throws ParseException {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		List<ContractApprovalResult> contractApprovalResultList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode1 = new ContractApprovalRouteNode();
		contractApprovalRouteNode1.setId(1L);
		contractApprovalRouteNode1.setApproverEmpId("00229692");
		contractApprovalRouteNode1.setSubApproverEmpId("01901115");
		contractApprovalRouteNode1.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode1);
		ContractApprovalRouteNode contractApprovalRouteNode2 = new ContractApprovalRouteNode();
		contractApprovalRouteNode2.setId(2L);
		contractApprovalRouteNode2.setApproverEmpId("01901306");
		contractApprovalRouteNode2.setSubApproverEmpId("01901177");
		contractApprovalRouteNode2.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode2);
		ContractApprovalResult contractApprovalResult1 = new ContractApprovalResult();
		contractApprovalResult1.setApprovalProcessCategory(ApprovalProcessCategory.承認依頼);
		contractApprovalResult1.setActualEmpId("00500784");
		contractApprovalResult1.setProcessedAt(new SimpleDateFormat("yyyy/MM/dd").parse("2020/05/25"));
		contractApprovalResultList.add(contractApprovalResult1);
		ContractApprovalResult contractApprovalResult2 = new ContractApprovalResult();
		contractApprovalResult2.setApprovalProcessCategory(ApprovalProcessCategory.承認);
		contractApprovalResult2.setActualEmpId("00229692");
		contractApprovalResult2.setProcessedAt(new SimpleDateFormat("yyyy/MM/dd").parse("2020/05/26"));
		contractApprovalResult2.setContractApprovalRouteNodeId(contractApprovalRouteNode1.getId());
		contractApprovalResultList.add(contractApprovalResult2);

		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contractApprovalRoute.setContractApprovalResultList(contractApprovalResultList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に次回代理承認者の社員情報が作成されていること", authParam.getNextSubApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に前回承認者の社員情報が作成されていること", authParam.getPrevApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に前回代理承認者の社員情報が作成されていること", authParam.getPrevSubApproverMvEmployeeMaster());
		Assert.assertNull("承認依頼者の社員情報が作成されていないこと", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_参照_承認ルート無し() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成完了);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		contractApprovalRouteNode.setApproverEmpId("00229692");
		contractApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode);
		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNull("正常に承認者の社員情報が作成されていないこと", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNull("正常に次回承認者の社員情報が作成されていないこと", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNull("承認依頼者の社員情報が作成されていないこと", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_承認() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		contractApprovalRouteNode.setApproverEmpId("00229692");
		contractApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode);
		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_承認_代理承認者あり() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00229692");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		contractApprovalRouteNode.setApproverEmpId("00229692");
		contractApprovalRouteNode.setSubApproverEmpId("00500784");
		contractApprovalRouteNodeList.add(contractApprovalRouteNode);
		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_承認_ユーザー直接指定() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		contractApprovalRouteNode.setApproverEmpId("00500784");
		contractApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.ユーザー直接指定);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode);
		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に顧客情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertNotNull("正常に社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertTrue("ユーザー直接指定であること", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_契約_承認_自己承認() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルート
		contract.setContractApprovalRouteList(new ArrayList<>());
		ContractApprovalRoute contractApprovalRoute = new ContractApprovalRoute();
		contractApprovalRoute.setApprovalRequesterEmpId("00500784");
		contractApprovalRoute.setTargetLifecycleStatus(LifecycleStatus.作成中);

		// 承認ルートノード
		List<ContractApprovalRouteNode> contractApprovalRouteNodeList = new ArrayList<>();
		ContractApprovalRouteNode contractApprovalRouteNode = new ContractApprovalRouteNode();
		contractApprovalRouteNode.setApproverEmpId("00500784");
		contractApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.自己承認);
		contractApprovalRouteNodeList.add(contractApprovalRouteNode);
		contractApprovalRoute.setContractApprovalRouteNodeList(contractApprovalRouteNodeList);
		contract.getContractApprovalRouteList().add(contractApprovalRoute);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に顧客情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertNotNull("正常に社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定であること", authParam.isManualApprover());
		Assert.assertTrue("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_手配_参照() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 手配業務
		ArrangementWork arrangementWork = new ArrangementWork();

		// 承認ルート
		arrangementWork.setArrangementWorkApprovalRoute(new ArrangementWorkApprovalRoute());
		arrangementWork.getArrangementWorkApprovalRoute().setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<ArrangementWorkApprovalRouteNode> arrangementWorkApprovalRouteNodeList = new ArrayList<>();
		ArrangementWorkApprovalRouteNode arrangementWorkApprovalRouteNode = new ArrangementWorkApprovalRouteNode();
		arrangementWorkApprovalRouteNode.setApproverEmpId("00229692");
		arrangementWorkApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		arrangementWorkApprovalRouteNodeList.add(arrangementWorkApprovalRouteNode);
		arrangementWork.getArrangementWorkApprovalRoute().setArrangementWorkApprovalRouteNodeList(arrangementWorkApprovalRouteNodeList);

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromArrangementWork(arrangementWork, contract, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNull("承認依頼者の社員情報が作成されていないこと", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_手配_承認() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 手配業務
		ArrangementWork arrangementWork = new ArrangementWork();

		// 承認ルート
		arrangementWork.setArrangementWorkApprovalRoute(new ArrangementWorkApprovalRoute());
		arrangementWork.getArrangementWorkApprovalRoute().setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<ArrangementWorkApprovalRouteNode> arrangementWorkApprovalRouteNodeList = new ArrayList<>();
		ArrangementWorkApprovalRouteNode arrangementWorkApprovalRouteNode = new ArrangementWorkApprovalRouteNode();
		arrangementWorkApprovalRouteNode.setApproverEmpId("00229692");
		arrangementWorkApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.直属上司指定);
		arrangementWorkApprovalRouteNodeList.add(arrangementWorkApprovalRouteNode);
		arrangementWork.getArrangementWorkApprovalRoute().setArrangementWorkApprovalRouteNodeList(arrangementWorkApprovalRouteNodeList);

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromArrangementWork(arrangementWork, contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_手配_直接_ユーザー直接指定() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 手配業務
		ArrangementWork arrangementWork = new ArrangementWork();

		// 承認ルート
		arrangementWork.setArrangementWorkApprovalRoute(new ArrangementWorkApprovalRoute());
		arrangementWork.getArrangementWorkApprovalRoute().setApprovalRequesterEmpId("00500784");

		// 承認ルートノード
		List<ArrangementWorkApprovalRouteNode> arrangementWorkApprovalRouteNodeList = new ArrayList<>();
		ArrangementWorkApprovalRouteNode arrangementWorkApprovalRouteNode = new ArrangementWorkApprovalRouteNode();
		arrangementWorkApprovalRouteNode.setApproverEmpId("00500784");
		arrangementWorkApprovalRouteNode.setApproverDeriveMethodDiv(ApproverDeriveMethodDiv.ユーザー直接指定);
		arrangementWorkApprovalRouteNodeList.add(arrangementWorkApprovalRouteNode);
		arrangementWork.getArrangementWorkApprovalRoute().setArrangementWorkApprovalRouteNodeList(arrangementWorkApprovalRouteNodeList);

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromArrangementWork(arrangementWork, contract, actor, AccessType.承認);

		Assert.assertNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNull("正常に顧客情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNotNull("正常に承認依頼者の社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertNotNull("正常に社員情報が作成されていること", authParam.getRequesterMvEmployeeMaster());
		Assert.assertTrue("ユーザー直接指定であること", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}

	@Test
	public void 正常_権限判定用パラメーター取得_手配_手配無し() {

		// ログインユーザー
		MvEmployeeMaster actor = mvEmployeeMasterRepository.findByMomEmployeeId("00500784");

		// 契約
		Contract contract = new Contract();
		contract.setLifecycleStatus(LifecycleStatus.作成中);

		// 担当SA
		contract.setContractPicSaEmp(new ContractPicSaEmp());
		contract.getContractPicSaEmp().setMomEmployeeId("00500784");

		// 追加編集者
		contract.setContractAddedEditorEmpList(new ArrayList<>());
		contract.getContractAddedEditorEmpList().add(new ContractAddedEditorEmp());
		contract.getContractAddedEditorEmpList().get(0).setMomEmployeeId("00500784");

		// 顧客
		contract.setCustomerContract(new CustomerContract());
		contract.getCustomerContract().setMomKjbSystemId("000000003985825");

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromArrangementWork(null, contract, actor, AccessType.参照);

		Assert.assertNotNull("正常に社員情報が作成されていること", authParam.getMvEmployeeMasterList());
		Assert.assertNotNull("正常に顧客情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNull("正常に承認者の社員情報が作成されていないこと", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNull("正常に次回承認者の社員情報が作成されていないこと", authParam.getNextApproverMvEmployeeMaster());
		Assert.assertNull("正常に承認依頼者の社員情報が作成されていないこと", authParam.getRequesterMvEmployeeMaster());
		Assert.assertFalse("ユーザー直接指定でないこと", authParam.isManualApprover());
		Assert.assertFalse("自己承認でないこと", authParam.isSelfApprover());
	}
}
