package jp.co.ricoh.cotos.commonlib.security;

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
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerContract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
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
	public void 正常_権限判定用パラメーター取得_見積_参照() {

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

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromEstimation(estimation, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
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
	public void 正常_権限判定用パラメーター取得_契約_参照() {

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

		AuthorityJudgeParameter authParam = authorityJudgeParamCreator.createFromContract(contract, actor, AccessType.参照);

		Assert.assertEquals("正常に社員情報が作成されていること", 2, authParam.getMvEmployeeMasterList().size());
		Assert.assertNotNull("正常に会社情報が作成されていること", authParam.getVKjbMaster());
		Assert.assertNotNull("正常にログインユーザー情報が作成されていること", authParam.getActorMvEmployeeMaster());
		Assert.assertNotNull("正常に承認者の社員情報が作成されていること", authParam.getApproverMvEmployeeMasterList());
		Assert.assertNotNull("正常に次回承認者の社員情報が作成されていること", authParam.getNextApproverMvEmployeeMaster());
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
