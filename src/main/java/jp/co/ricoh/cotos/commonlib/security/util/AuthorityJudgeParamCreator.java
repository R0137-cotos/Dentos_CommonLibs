package jp.co.ricoh.cotos.commonlib.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class AuthorityJudgeParamCreator {

	/** ロガー */
	private static final Log log = LogFactory.getLog(AuthorityJudgeParamCreator.class);

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	VKjbMasterRepository vKjbMasterRepository;

	public AuthorityJudgeParameter createFromEstimation(Estimation estimation, MvEmployeeMaster actor, AccessType accessType) {

		// 権限判定用パラメーター
		log.info(messageUtil.createMessageInfo("AuthorizeCreateParamToJudgeInfo", Arrays.asList(accessType.name()).toArray(new String[0])).getMsg());
		AuthorityJudgeParameter authJudgeParam = new AuthorityJudgeParameter();

		// アクター
		log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("アクター", "MoM社員ID", actor.getMomEmployeeId()).toArray(new String[0])).getMsg());
		authJudgeParam.setActorMvEmployeeMaster(actor);

		// 参照種別に応じて、権限判定パラメーターを変更
		if (AccessType.承認.equals(accessType)) {
			// 承認依頼者情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認依頼者", "MoM社員ID", estimation.getEstimationApprovalRoute().getApprovalRequesterEmpId()).toArray(new String[0])).getMsg());
			authJudgeParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findByMomEmployeeId(estimation.getEstimationApprovalRoute().getApprovalRequesterEmpId()));
		} else {
			// 社員情報
			List<MvEmployeeMaster> employeeMasterList = new ArrayList<>();

			// 担当SA
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("担当SA", "MoM社員ID", estimation.getEstimationPicSaEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
			employeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(estimation.getEstimationPicSaEmp().getMomEmployeeId()));

			// 追加編集者
			if (estimation.getEstimationAddedEditorEmpList() != null) {
				employeeMasterList.addAll(estimation.getEstimationAddedEditorEmpList().stream().map(employee -> {
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("追加編集者", "MoM社員ID", employee.getMomEmployeeId()).toArray(new String[0])).getMsg());
					return mvEmployeeMasterRepository.findByMomEmployeeId(employee.getMomEmployeeId());
				}).collect(Collectors.toList()));
			}
			authJudgeParam.setMvEmployeeMasterList(employeeMasterList);

			// 顧客情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("顧客情報", "MoM企事部システム連携ID", estimation.getCustomerEstimation().getMomKjbSystemId()).toArray(new String[0])).getMsg());
			authJudgeParam.setVKjbMaster(vKjbMasterRepository.findByMclMomRelId(estimation.getCustomerEstimation().getMomKjbSystemId()));
		}

		// 承認ルートが存在する場合
		if (estimation.getEstimationApprovalRoute() != null) {

			List<EstimationApprovalRouteNode> nodeList = estimation.getEstimationApprovalRoute().getEstimationApprovalRouteNodeList();

			// 承認者情報
			List<MvEmployeeMaster> approverList = nodeList.stream().map(node -> {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者（代理編集者でない）", "MoM社員ID", node.getApproverEmpId()).toArray(new String[0])).getMsg());
				return mvEmployeeMasterRepository.findByMomEmployeeId(node.getApproverEmpId());
			}).collect(Collectors.toList());
			authJudgeParam.setApproverMvEmployeeMasterList(approverList);

			// 次回承認者情報（代理編集者でない）
			EstimationApprovalRouteNode nextApproverNode = this.specifyEstimationApprovalRouteNode(nodeList, estimation.getEstimationApprovalRoute().getEstimationApprovalResultList());
			if (nextApproverNode != null) {

				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("次回承認者（代理編集者でない）", "MoM社員ID", nextApproverNode.getApproverEmpId()).toArray(new String[0])).getMsg());
				MvEmployeeMaster nextApprover = mvEmployeeMasterRepository.findByMomEmployeeId(nextApproverNode.getApproverEmpId());
				authJudgeParam.setNextApproverMvEmployeeMaster(nextApprover);

				// 承認者直接指定フラグ
				boolean isManualApprover = false;

				// ログインユーザーが直接指定された承認者の場合
				if (actor.getMomEmployeeId().equals(nextApproverNode.getApproverEmpId()) && ApproverDeriveMethodDiv.ユーザー直接指定.equals(nextApproverNode.getApproverDeriveMethodDiv())) {
					isManualApprover = true;
				}
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者直接指定フラグ", "フラグ", Boolean.toString(isManualApprover)).toArray(new String[0])).getMsg());
				authJudgeParam.setManualApprover(isManualApprover);
			}
		}

		return authJudgeParam;
	}

	public AuthorityJudgeParameter createFromContract(Contract contract, MvEmployeeMaster actor, AccessType accessType) {

		// 権限判定用パラメーター
		log.info(messageUtil.createMessageInfo("AuthorizeCreateParamToJudgeInfo", Arrays.asList(accessType.name()).toArray(new String[0])).getMsg());
		AuthorityJudgeParameter authJudgeParam = new AuthorityJudgeParameter();

		// アクター
		log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("アクター", "MoM社員ID", actor.getMomEmployeeId()).toArray(new String[0])).getMsg());
		authJudgeParam.setActorMvEmployeeMaster(actor);

		if (AccessType.承認.equals(accessType)) {

			// 対象の契約承認ルートを特定
			ContractApprovalRoute targetContractApprovalRoute = contract.getContractApprovalRouteList().stream().filter(contractApprovalRoute -> contract.getLifecycleStatus().equals(contractApprovalRoute.getTargetLifecycleStatus())).findFirst().get();

			// 承認依頼者情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認依頼者", "MoM社員ID", targetContractApprovalRoute.getApprovalRequesterEmpId()).toArray(new String[0])).getMsg());
			authJudgeParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findByMomEmployeeId(targetContractApprovalRoute.getApprovalRequesterEmpId()));
		} else {
			// 社員情報設定
			List<MvEmployeeMaster> mvEmployeeMasterList = new ArrayList<>();

			// 担当SA
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("担当SA", "MoM社員ID", contract.getContractPicSaEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
			mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicSaEmp().getMomEmployeeId()));

			// 受付担当CE
			if (contract.getContractPicAccCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("受付担当CE", "MoM社員ID", contract.getContractPicAccCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicAccCeEmp().getMomEmployeeId()));
			}

			// 導入担当CE
			if (contract.getContractPicIntCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("導入担当CE", "MoM社員ID", contract.getContractPicIntCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicIntCeEmp().getMomEmployeeId()));
			}

			// 保守担当CE
			if (contract.getContractPicMntCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("保守担当CE", "MoM社員ID", contract.getContractPicMntCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicMntCeEmp().getMomEmployeeId()));
			}

			// 追加編集者
			if (contract.getContractAddedEditorEmpList() != null) {

				mvEmployeeMasterList.addAll(contract.getContractAddedEditorEmpList().stream().map(employee -> {
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("追加編集者", "MoM社員ID", employee.getMomEmployeeId()).toArray(new String[0])).getMsg());
					return mvEmployeeMasterRepository.findByMomEmployeeId(employee.getMomEmployeeId());
				}).collect(Collectors.toList()));
			}
			authJudgeParam.setMvEmployeeMasterList(mvEmployeeMasterList);

			// 顧客情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("顧客情報", "MoM企事部システム連携ID", contract.getCustomerContract().getMomKjbSystemId()).toArray(new String[0])).getMsg());
			authJudgeParam.setVKjbMaster(vKjbMasterRepository.findByMclMomRelId(contract.getCustomerContract().getMomKjbSystemId()));
		}

		// 承認ルートが存在する場合
		if (!CollectionUtils.isEmpty(contract.getContractApprovalRouteList())) {

			// 対象の契約承認ルートを特定
			Optional<ContractApprovalRoute> targetContractApprovalRoute = contract.getContractApprovalRouteList().stream().filter(contractApprovalRoute -> contract.getLifecycleStatus().equals(contractApprovalRoute.getTargetLifecycleStatus())).findFirst();

			if (targetContractApprovalRoute.isPresent()) {

				List<ContractApprovalRouteNode> nodeList = targetContractApprovalRoute.get().getContractApprovalRouteNodeList();

				// 承認者情報
				List<MvEmployeeMaster> approverList = nodeList.stream().map(node -> {
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者", "MoM社員ID", node.getApproverEmpId()).toArray(new String[0])).getMsg());
					return mvEmployeeMasterRepository.findByMomEmployeeId(node.getApproverEmpId());
				}).collect(Collectors.toList());
				authJudgeParam.setApproverMvEmployeeMasterList(approverList);

				// 次回承認者情報
				ContractApprovalRouteNode nextApproverNode = this.specifyContractApprovalRouteNode(nodeList, targetContractApprovalRoute.get().getContractApprovalResultList());

				if (nextApproverNode != null) {
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("次回承認者", "MoM社員ID", nextApproverNode.getApproverEmpId()).toArray(new String[0])).getMsg());
					MvEmployeeMaster nextApprover = mvEmployeeMasterRepository.findByMomEmployeeId(nextApproverNode.getApproverEmpId());
					authJudgeParam.setNextApproverMvEmployeeMaster(nextApprover);

					// 承認者直接指定フラグ
					boolean isManualApprover = false;

					// ログインユーザーが直接指定された承認者の場合
					if (actor.getMomEmployeeId().equals(nextApproverNode.getApproverEmpId()) && ApproverDeriveMethodDiv.ユーザー直接指定.equals(nextApproverNode.getApproverDeriveMethodDiv())) {
						isManualApprover = true;
					}
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者直接指定フラグ", "フラグ", Boolean.toString(isManualApprover)).toArray(new String[0])).getMsg());
					authJudgeParam.setManualApprover(isManualApprover);
				}
			}
		}

		return authJudgeParam;
	}

	public AuthorityJudgeParameter createFromArrangementWork(ArrangementWork arrangementWork, Contract contract, MvEmployeeMaster actor, AccessType accessType) {

		// 権限判定用パラメーター
		log.info(messageUtil.createMessageInfo("AuthorizeCreateParamToJudgeInfo", Arrays.asList(accessType.name()).toArray(new String[0])).getMsg());
		AuthorityJudgeParameter authJudgeParam = new AuthorityJudgeParameter();

		// アクター
		log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("アクター", "MoM社員ID", actor.getMomEmployeeId()).toArray(new String[0])).getMsg());
		authJudgeParam.setActorMvEmployeeMaster(actor);

		if (AccessType.承認.equals(accessType)) {
			// 承認依頼者情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認依頼者", "MoM社員ID", arrangementWork.getArrangementWorkApprovalRoute().getApprovalRequesterEmpId()).toArray(new String[0])).getMsg());
			authJudgeParam.setRequesterMvEmployeeMaster(mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWork.getArrangementWorkApprovalRoute().getApprovalRequesterEmpId()));
		} else {

			// 社員情報設定
			List<MvEmployeeMaster> mvEmployeeMasterList = new ArrayList<>();

			// 担当SA
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("担当SA", "MoM社員ID", contract.getContractPicSaEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
			mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicSaEmp().getMomEmployeeId()));

			// 手配担当者
			if (arrangementWork != null && arrangementWork.getArrangementPicWorkerEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("担当作業者", "MoM社員ID", arrangementWork.getArrangementPicWorkerEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWork.getArrangementPicWorkerEmp().getMomEmployeeId()));
			}

			// 受付担当CE
			if (contract.getContractPicAccCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("受付担当CE", "MoM社員ID", contract.getContractPicAccCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicAccCeEmp().getMomEmployeeId()));
			}

			// 導入担当CE
			if (contract.getContractPicIntCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("導入担当CE", "MoM社員ID", contract.getContractPicIntCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicIntCeEmp().getMomEmployeeId()));
			}

			// 保守担当CE
			if (contract.getContractPicMntCeEmp() != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("保守担当CE", "MoM社員ID", contract.getContractPicMntCeEmp().getMomEmployeeId()).toArray(new String[0])).getMsg());
				mvEmployeeMasterList.add(mvEmployeeMasterRepository.findByMomEmployeeId(contract.getContractPicMntCeEmp().getMomEmployeeId()));
			}

			// 追加編集者
			if (contract.getContractAddedEditorEmpList() != null) {
				mvEmployeeMasterList.addAll(contract.getContractAddedEditorEmpList().stream().map(contractAddedEditorEmp -> {
					log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("追加編集者", "MoM社員ID", contractAddedEditorEmp.getMomEmployeeId()).toArray(new String[0])).getMsg());
					return mvEmployeeMasterRepository.findByMomEmployeeId(contractAddedEditorEmp.getMomEmployeeId());
				}).collect(Collectors.toList()));
			}
			authJudgeParam.setMvEmployeeMasterList(mvEmployeeMasterList);

			// 顧客情報
			log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("顧客情報", "MoM企事部システム連携ID", contract.getCustomerContract().getMomKjbSystemId()).toArray(new String[0])).getMsg());
			authJudgeParam.setVKjbMaster(vKjbMasterRepository.findByMclMomRelId(contract.getCustomerContract().getMomKjbSystemId()));
		}

		// 承認ルートが存在する場合
		if (arrangementWork != null && arrangementWork.getArrangementWorkApprovalRoute() != null) {

			// 承認者情報
			List<MvEmployeeMaster> approverList = arrangementWork.getArrangementWorkApprovalRoute().getArrangementWorkApprovalRouteNodeList().stream().map(arrangementWorkApprovalRouteNode -> {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者", "MoM社員ID", arrangementWorkApprovalRouteNode.getApproverEmpId()).toArray(new String[0])).getMsg());
				return mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWorkApprovalRouteNode.getApproverEmpId());
			}).collect(Collectors.toList());
			authJudgeParam.setApproverMvEmployeeMasterList(approverList);

			// 次回承認者情報（代理編集者でない）
			ArrangementWorkApprovalRouteNode nextApproverNode = this.specifyArrangementWorkApprovalRouteNode(arrangementWork.getArrangementWorkApprovalRoute().getArrangementWorkApprovalRouteNodeList(), arrangementWork.getArrangementWorkApprovalRoute().getArrangementWorkApprovalResultList());
			if (nextApproverNode != null) {
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("次回承認者（代理編集者でない）", "MoM社員ID", nextApproverNode.getApproverEmpId()).toArray(new String[0])).getMsg());
				MvEmployeeMaster nextApprover = mvEmployeeMasterRepository.findByMomEmployeeId(nextApproverNode.getApproverEmpId());
				authJudgeParam.setNextApproverMvEmployeeMaster(nextApprover);

				// 承認者直接指定フラグ
				boolean isManualApprover = false;

				// ログインユーザーが直接指定された承認者の場合
				if (actor.getMomEmployeeId().equals(nextApproverNode.getApproverEmpId()) && ApproverDeriveMethodDiv.ユーザー直接指定.equals(nextApproverNode.getApproverDeriveMethodDiv())) {
					isManualApprover = true;
				}
				log.info(messageUtil.createMessageInfo("AuthorizeSetJudgeParamInfo", Arrays.asList("承認者直接指定フラグ", "フラグ", Boolean.toString(isManualApprover)).toArray(new String[0])).getMsg());
				authJudgeParam.setManualApprover(isManualApprover);
			}
		}

		return authJudgeParam;
	}

	/**
	 * 承認実績を元に承認ルートノード特定 最新の承認依頼以降の実績を取得し、実績のリストにまだ存在しないルートノードを返します。
	 * 
	 * @param estimationApprovalRouteNodeList
	 *            承認ルートノードリスト
	 * @param estimationApprovalResultList
	 *            承認実績リスト
	 * @param empId
	 *            MoM社員ID
	 * @param approvalProcessCategory
	 *            承認処理カテゴリ
	 * @return 承認ルートノード
	 */
	public EstimationApprovalRouteNode specifyEstimationApprovalRouteNode(List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList, List<EstimationApprovalResult> estimationApprovalResultList) {

		// 最新の承認依頼以降の実績を取得
		List<EstimationApprovalResult> latestApprovalResults = new ArrayList<>();

		// 実績が存在する場合
		if (!CollectionUtils.isEmpty(estimationApprovalResultList)) {

			for (int i = 0; i < estimationApprovalResultList.size(); i++) {

				// 承認実績の後方から取得
				EstimationApprovalResult estimationApprovalResult = estimationApprovalResultList.get(estimationApprovalResultList.size() - i - 1);

				// リストの先頭に追記していく（処理日の降順とするため）
				latestApprovalResults.add(0, estimationApprovalResult);

				// 最新の承認依頼までのデータを取得
				if (ApprovalProcessCategory.承認依頼.equals(estimationApprovalResult.getApprovalProcessCategory())) {
					break;
				}
			}
		}

		for (EstimationApprovalRouteNode node : estimationApprovalRouteNodeList) {
			// 対象のルートノードIDに紐づく実績は除外する。
			List<EstimationApprovalResult> filterdApprovalResults = latestApprovalResults.stream().filter(result -> null != result.getEstimationApprovalRouteNodeId() && Long.compare(node.getId(), result.getEstimationApprovalRouteNodeId()) == 0).collect(Collectors.toList());

			// 実績が存在しない場合、承認未実施であると判断し対象のルートノードIDを返す。
			if (filterdApprovalResults.isEmpty()) {
				return node;
			}
		}

		return null;
	}

	/**
	 * 承認実績を元に承認ルートノード特定 最新の承認依頼以降の実績を取得し、実績のリストにまだ存在しないルートノードを返します。
	 *
	 * @param contractApprovalRouteNodeList
	 *            承認ルートノードリスト
	 * @param contractApprovalResultList
	 *            承認実績リスト
	 * @param empId
	 *            MoM社員ID
	 * @return 承認ルートノードID
	 */
	public ContractApprovalRouteNode specifyContractApprovalRouteNode(List<ContractApprovalRouteNode> contractApprovalRouteNodeList, List<ContractApprovalResult> contractApprovalResultList) {

		// 最新の承認依頼以降の実績を取得
		List<ContractApprovalResult> latestApprovalResults = new ArrayList<>();

		// 実績が存在する場合
		if (!CollectionUtils.isEmpty(contractApprovalResultList)) {

			for (int i = 0; i < contractApprovalResultList.size(); i++) {

				// 承認実績の後方から取得
				ContractApprovalResult contractApprovalResult = contractApprovalResultList.get(contractApprovalResultList.size() - i - 1);

				// リストの先頭に追記していく（処理日の昇順とするため）
				latestApprovalResults.add(0, contractApprovalResult);

				// 最新の承認依頼までのデータを取得
				if (ApprovalProcessCategory.承認依頼.equals(contractApprovalResult.getApprovalProcessCategory())) {
					break;
				}
			}
		}

		for (ContractApprovalRouteNode node : contractApprovalRouteNodeList) {
			// 対象のルートノードIDに紐づく実績は除外する。（承認と差戻しの実績以外はルートノードIDが紐づかずnullが設定されているため、比較処理で落ちないようnullチェックを先に行っています）
			List<ContractApprovalResult> filterdApprovalResults = latestApprovalResults.stream().filter(result -> null != result.getContractApprovalRouteNodeId() && Long.compare(node.getId(), result.getContractApprovalRouteNodeId()) == 0).collect(Collectors.toList());
			// 実績が存在しない場合、承認未実施であると判断し対象のルートノードIDを返す。
			if (filterdApprovalResults.isEmpty()) {
				return node;
			}
		}

		return null;
	}

	/**
	 * 承認実績を元に承認ルートノード特定 最新の承認依頼以降の実績を取得し、実績のリストにまだ存在しないルートノードIDを返します。
	 *
	 * @param arrangementWorkApprovalRouteNodeList
	 *            承認ルートノードリスト
	 * @param arrangementWorkApprovalResultList
	 *            承認実績リスト
	 * @param empId
	 *            MoM社員ID
	 * @param approvalProcessCategory
	 *            承認処理カテゴリ
	 * @return 承認ルートノードID
	 */
	public ArrangementWorkApprovalRouteNode specifyArrangementWorkApprovalRouteNode(List<ArrangementWorkApprovalRouteNode> arrangementWorkApprovalRouteNodeList, List<ArrangementWorkApprovalResult> arrangementWorkApprovalResultList) {

		// 最新の承認依頼以降の実績を取得
		List<ArrangementWorkApprovalResult> latestApprovalResults = new ArrayList<>();

		// 実績が存在しない場合は空の結果を返却
		if (!CollectionUtils.isEmpty(arrangementWorkApprovalResultList)) {

			for (int i = 0; i < arrangementWorkApprovalResultList.size(); i++) {

				// 承認実績の後方から取得
				ArrangementWorkApprovalResult arrangementWorkApprovalResult = arrangementWorkApprovalResultList.get(arrangementWorkApprovalResultList.size() - i - 1);

				// リストの先頭に追記していく（処理日の昇順とするため）
				latestApprovalResults.add(0, arrangementWorkApprovalResult);

				// 最新の承認依頼までのデータを取得
				if (ApprovalProcessCategory.承認依頼.equals(arrangementWorkApprovalResult.getApprovalProcessCategory())) {
					break;
				}
			}
		}

		for (ArrangementWorkApprovalRouteNode node : arrangementWorkApprovalRouteNodeList) {
			// 対象のルートノードIDに紐づく実績は除外する。（承認と差戻しの実績以外はルートノードIDが紐づかずnullが設定されているため、比較処理で落ちないようnullチェックを先に行っています）
			List<ArrangementWorkApprovalResult> filterdApprovalResults = latestApprovalResults.stream().filter(result -> null != result.getArrangementWorkApprovalRouteNodeId() && Long.compare(node.getId(), result.getArrangementWorkApprovalRouteNodeId()) == 0).collect(Collectors.toList());
			// 実績が存在しない場合、承認未実施であると判断し対象のルートノードIDを返す。
			if (filterdApprovalResults.isEmpty()) {
				return node;
			}
		}

		return null;
	}
}