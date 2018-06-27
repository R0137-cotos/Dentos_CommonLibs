package jp.co.ricoh.cotos.commonlib.logic.check;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.ApproverCheckParameter;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork.ArrangementWorkStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.EmployeeCon;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Employee;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.Status;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.EmployeeMasterRepository;
import jp.co.ricoh.jmo.cache.AuthoritySearch;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoActionDto;
import jp.co.ricoh.jmo.dto.cache.AuthorityInfoLevelDto;
import jp.co.ricoh.jmo.dto.service.EmployeeAuthInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeInfoDto;
import jp.co.ricoh.jmo.dto.service.EmployeeOrgInfoDto;
import jp.co.ricoh.jmo.service.KengenServiceServiceLocator;

/**
 * 仕様チェック管理クラス
 */
@Component
public class BusinessCheck {

	@Autowired
	EstimationRepository estimationRepository;
	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;
	@Autowired
	EmployeeMasterRepository employeeMasterRepository;

	@Autowired
	DBUtil dbUtil;

	/**
	 * 見積ステータス一致チェック
	 * 
	 * @param status
	 *            見積情報見積ステータス
	 * @param conpereStatus
	 *            比較見積ステータス
	 * @return チェック結果
	 */
	public boolean existsEstimationStatusMatch(Status status, Status conpereStatus) {
		return conpereStatus.equals(status);
	}

	/**
	 * 見積承認ルートノード承認者代理承認者不一致チェック
	 * 
	 * @param estimationApprovalRouteNode
	 *            見積商品ルートノード情報
	 * @return チェック結果
	 */
	public boolean existsEstimationApprovalRouteApproverDuplication(EstimationApprovalRouteNode estimationApprovalRouteNode) {
		if (null != estimationApprovalRouteNode.getSubApproverEmployee() && estimationApprovalRouteNode.getApproverEmployee().getEmployeeMaster().getMomEmployeeId().equals(estimationApprovalRouteNode.getSubApproverEmployee().getEmployeeMaster().getMomEmployeeId())) {
			return false;
		}
		return true;
	}

	/**
	 * 見積承認ルートノード承認者代理承認者不一致チェック
	 * 
	 * @param estimationApprovalRouteNodeList
	 *            見積承認ルートノードリスト
	 * @return チェック結果
	 */
	public boolean existsEstimationApprovalRouteApproverDuplication(List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList) {
		for (int i = 0; i < estimationApprovalRouteNodeList.size(); i++) {
			if (!existsEstimationApprovalRouteApproverDuplication(estimationApprovalRouteNodeList.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 見積承認ルートが承認ルートマスタと一致しているか確認
	 * 
	 * @param estimationApprovalRouteNodeList
	 *            見積承認ルートノード
	 * @param approvalRouteNodeMasterList
	 *            マスタ承認ルートノード
	 * @return チェック結果
	 */
	public boolean existsEstimationApprovalRouteNodeMasterEqual(List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList, List<EstimationApprovalRouteNode> approvalRouteNodeMasterList) {

		if (approvalRouteNodeMasterList.size() != estimationApprovalRouteNodeList.size()) {
			return false;
		}

		// 承認ルートマスタノードを承認順の昇順でソートする
		approvalRouteNodeMasterList.sort((routeNode1, routeNode2) -> (int) (routeNode1.getApprovalOrder() - routeNode2.getApprovalOrder()));

		// 承認ルートマスタノードの社員情報をマスタから取得する
		approvalRouteNodeMasterList.stream().forEach(routeNode -> {
			routeNode.getApproverEmployee().getEmployeeMaster().setMomEmployeeId(routeNode.getApproverEmployee().getEmployeeMaster().getMomEmployeeId());
			setEmployeeInfoEstimation(routeNode.getApproverEmployee());
		});

		for (int i = 0; i < approvalRouteNodeMasterList.size(); i++) {
			if (approvalRouteNodeMasterList.get(i).getApprovalOrder() == estimationApprovalRouteNodeList.get(i).getApprovalOrder() && !approvalRouteNodeMasterList.get(i).getApproverEmployee().getEmployeeMaster().getMomEmployeeId().equals(estimationApprovalRouteNodeList.get(i).getApproverEmployee().getEmployeeMaster().getMomEmployeeId())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 見積承認ルートノード承認者代理承認者階層チェック
	 * 
	 * @param estimationApprovalRouteNodeList
	 *            見積承認ルートノードリスト
	 * @return チェック結果
	 */
	public boolean existsSubApproverOrgHierarchyLevelSuperiorEstimation(List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList) {
		for (int i = 0; i < estimationApprovalRouteNodeList.size(); i++) {
			if (estimationApprovalRouteNodeList.get(i).getSubApproverEmployee() != null && estimationApprovalRouteNodeList.get(i).getApproverEmployee().getEmployeeMaster().getOrgHierarchyLevel() > estimationApprovalRouteNodeList.get(i).getSubApproverEmployee().getEmployeeMaster().getOrgHierarchyLevel()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 見積_代理承認者に承認権限があるか確認
	 * 
	 * @param approvalRequesterMomEmployeeId
	 *            承認依頼者MoM社員ID
	 * @param estimationApprovalRouteNodeList
	 *            見積承認ルートノード
	 * @param momServiceUrl
	 *            MoMサービスURL
	 * @param relatedId
	 *            接続ID
	 * @param dbUrl
	 *            DB_URL
	 * @param dbUser
	 *            DB_USER
	 * @param dbPassword
	 *            DB_PASSWORD
	 * @return チェック結果
	 * @throws Exception
	 */
	public boolean existsSubApproverEmployeeAuthorityEstimation(String approvalRequesterMomEmployeeId, List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		EmployeeMaster approvalRequesterMaster = employeeMasterRepository.findByMomEmployeeId(approvalRequesterMomEmployeeId);
		String approvalRequesterSingleUserId = approvalRequesterMaster.getSingleUserId();

		for (int i = 0; i < estimationApprovalRouteNodeList.size(); i++) {
			if (null != estimationApprovalRouteNodeList.get(i).getSubApproverEmployee()) {
				EmployeeMaster subApproverMaster = employeeMasterRepository.findByMomEmployeeId(estimationApprovalRouteNodeList.get(i).getSubApproverEmployee().getEmployeeMaster().getMomEmployeeId());
				String subApproverSingleUserId = subApproverMaster.getSingleUserId();
				// 承認権限を持っているかどうかを判定する
				if (null != subApproverSingleUserId && !existsAcceptAuthority(approvalRequesterSingleUserId, subApproverSingleUserId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 契約ステータス一致チェック
	 * 
	 * @param status
	 *            契約情報見積ステータス
	 * @param conpereStatus
	 *            比較見積ステータス
	 * @return チェック結果
	 */
	public boolean existsContractStatusMatch(ContractStatus status, ContractStatus conpereStatus) {
		return conpereStatus.equals(status);
	}

	/**
	 * 契約承認ルートノード承認者代理承認者不一致チェック
	 * 
	 * @param contractApprovalRouteNode
	 *            契約商品ルートノード情報
	 * @return チェック結果
	 */
	public boolean existsContractApprovalRouteApproverDuplication(ContractApprovalRouteNode contractApprovalRouteNode) {
		if (null != contractApprovalRouteNode.getSubApproverEmployee() && contractApprovalRouteNode.getApproverEmployee().getEmployeeMaster().getMomEmployeeId().equals(contractApprovalRouteNode.getSubApproverEmployee().getEmployeeMaster().getMomEmployeeId())) {
			return false;
		}
		return true;
	}

	/**
	 * 契約承認ルートノード承認者代理承認者不一致チェック
	 * 
	 * @param contractApprovalRouteNodeList
	 *            契約承認ルートノードリスト
	 * @return チェック結果
	 */
	public boolean existsContractApprovalRouteApproverDuplication(List<ContractApprovalRouteNode> contractApprovalRouteNodeList) {
		for (int i = 0; i < contractApprovalRouteNodeList.size(); i++) {
			if (!existsContractApprovalRouteApproverDuplication(contractApprovalRouteNodeList.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 契約承認ルートノード承認者代理承認者階層チェック
	 * 
	 * @param contractApprovalRouteNodeList
	 *            契約承認ルートノードリスト
	 * @return チェック結果
	 */
	public boolean existsSubApproverOrgHierarchyLevelSuperiorContract(List<ContractApprovalRouteNode> contractApprovalRouteNodeList) {
		for (int i = 0; i < contractApprovalRouteNodeList.size(); i++) {
			if (contractApprovalRouteNodeList.get(i).getSubApproverEmployee() != null && contractApprovalRouteNodeList.get(i).getApproverEmployee().getEmployeeMaster().getOrgHierarchyLevel() > contractApprovalRouteNodeList.get(i).getSubApproverEmployee().getEmployeeMaster().getOrgHierarchyLevel()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 契約承認ルートが承認ルートマスタと一致しているか確認
	 * 
	 * @param contractApprovalRouteNodeList
	 *            契約承認ルートノード
	 * @param approvalRouteNodeMasterList
	 *            マスタ承認ルートノード
	 * @return チェック結果
	 */
	public boolean existsContractApprovalRouteNodeMasterEqual(List<ContractApprovalRouteNode> contractApprovalRouteNodeList, List<ContractApprovalRouteNode> approvalRouteNodeMasterList) {

		if (approvalRouteNodeMasterList.size() != contractApprovalRouteNodeList.size()) {
			return false;
		}

		// 承認ルートマスタノードを承認順の昇順でソートする
		approvalRouteNodeMasterList.sort((routeNode1, routeNode2) -> (int) (routeNode1.getApprovalOrder() - routeNode2.getApprovalOrder()));

		// 承認ルートマスタノードの社員情報をマスタから取得する
		approvalRouteNodeMasterList.stream().forEach(routeNode -> {
			routeNode.getApproverEmployee().getEmployeeMaster().setMomEmployeeId(routeNode.getApproverEmployee().getEmployeeMaster().getMomEmployeeId());
			setEmployeeInfoContract(routeNode.getApproverEmployee());
		});

		for (int i = 0; i < approvalRouteNodeMasterList.size(); i++) {
			if (approvalRouteNodeMasterList.get(i).getApprovalOrder() == contractApprovalRouteNodeList.get(i).getApprovalOrder() && !approvalRouteNodeMasterList.get(i).getApproverEmployee().getEmployeeMaster().getMomEmployeeId().equals(contractApprovalRouteNodeList.get(i).getApproverEmployee().getEmployeeMaster().getMomEmployeeId())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 契約_代理承認者に承認権限があるか確認
	 * 
	 * @param approvalRequesterMomEmployeeId
	 *            承認依頼者MoM社員ID
	 * @param contractApprovalRouteNodeList
	 *            契約承認ルートノード
	 * @param momServiceUrl
	 *            MoMサービスURL
	 * @param relatedId
	 *            接続ID
	 * @param dbUrl
	 *            DB_URL
	 * @param dbUser
	 *            DB_USER
	 * @param dbPassword
	 *            DB_PASSWORD
	 * @return チェック結果
	 * @throws Exception
	 */
	public boolean existsSubApproverEmployeeAuthorityContract(String approvalRequesterMomEmployeeId, List<ContractApprovalRouteNode> contractApprovalRouteNodeList, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		EmployeeMaster approvalRequesterMaster = employeeMasterRepository.findByMomEmployeeId(approvalRequesterMomEmployeeId);
		String approvalRequesterSingleUserId = approvalRequesterMaster.getSingleUserId();

		for (int i = 0; i < contractApprovalRouteNodeList.size(); i++) {
			if (null != contractApprovalRouteNodeList.get(i).getSubApproverEmployee()) {
				EmployeeMaster subApproverMaster = employeeMasterRepository.findByMomEmployeeId(contractApprovalRouteNodeList.get(i).getSubApproverEmployee().getEmployeeMaster().getMomEmployeeId());
				String subApproverSingleUserId = subApproverMaster.getSingleUserId();
				// 承認権限を持っているかどうかを判定する
				if (null != subApproverSingleUserId && !existsAcceptAuthority(approvalRequesterSingleUserId, subApproverSingleUserId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * プラン変更実施済の契約情報かどうか確認
	 * 
	 * @param originContractNumber
	 *            変更元契約番号
	 * @return チェック結果
	 */
	public boolean existsConditionMatchEstimation(String originContractNumber) {
		List<Estimation> list = estimationRepository.findByOriginContractNumberInOrderByEstimateNumber(originContractNumber);
		return list.isEmpty();
	}

	/**
	 * 手配業務ステータス一致チェック
	 * 
	 * @param status
	 *            手配業務情報手配業務ステータス
	 * @param conpereStatus
	 *            比較手配業務ステータス
	 * @return チェック結果
	 */
	public boolean existsArrangementWorkStatusMatch(ArrangementWorkStatus status, ArrangementWorkStatus conpereStatus) {
		return conpereStatus.equals(status);
	}

	/**
	 * 手配業務存在チェック
	 * 
	 * @param arrangementWorkIdList
	 *            手配業務IDリスト
	 * @return チェック結果
	 */
	public boolean existsArrangementWork(List<Long> arrangementWorkIdList) {
		List<Long> noTargetIdList = arrangementWorkIdList.stream().filter(arrangementWorkId -> arrangementWorkRepository.findOne(arrangementWorkId) == null).collect(Collectors.toList());
		return CollectionUtils.isEmpty(noTargetIdList);
	}

	/**
	 * 見積_社員TXに社員マスタ情報を設定する
	 * 
	 * @param employee
	 *            社員TX
	 */
	private void setEmployeeInfoEstimation(Employee employee) {
		EmployeeMaster employeeMaster = employeeMasterRepository.findByMomEmployeeId(employee.getEmployeeMaster().getMomEmployeeId());
		if (null != employeeMaster) {
			employee.setEmployeeMaster(employeeMaster);
		}
	}

	/**
	 * 契約_社員TXに社員マスタ情報を設定する
	 * 
	 * @param employee
	 *            社員TX
	 */
	private void setEmployeeInfoContract(EmployeeCon employee) {
		EmployeeMaster employeeMaster = employeeMasterRepository.findByMomEmployeeId(employee.getEmployeeMaster().getMomEmployeeId());
		if (null != employeeMaster) {
			employee.setEmployeeMaster(employeeMaster);
		}
	}

	/**
	 * 権限区分を元に承認権限があるか確認
	 * 
	 * @param approvalRequesterSingleUserId
	 *            承認依頼者SUID
	 * @param subApproverSingleUserId
	 *            代理承認者SUID
	 * @param momServiceUrl
	 *            MoMサービスURL
	 * @param relatedId
	 *            接続ID
	 * @param dbUrl
	 *            DB_URL
	 * @param dbUser
	 *            DB_USER
	 * @param dbPassword
	 *            DB_PASSWORD
	 * @return チェック結果
	 * @throws Exception
	 */
	private boolean existsAcceptAuthority(String approvalRequesterSingleUserId, String subApproverSingleUserId, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		String authority = searchMomAuthority(subApproverSingleUserId, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword);

		if ("10".equals(authority)) {
			return false;
		}
		if ("90".equals(authority)) {
			return true;
		}
		if (!"30".equals(authority) && !"50".equals(authority) && !"70".equals(authority)) {
			return false;
		}

		ApproverCheckParameter parameter = new ApproverCheckParameter();
		parameter.setSingleUserId(approvalRequesterSingleUserId);

		Map<String, String> requestParams = BeanUtils.describe(parameter);
		requestParams.remove("class");
		Map<String, Object> queryParams = requestParams.keySet().stream().filter(key -> null != requestParams.get(key)).collect(Collectors.toMap(key -> key, key -> requestParams.get(key)));
		long result = dbUtil.loadCountFromSQLFile("sql/approverAcceptAuthority/approverAcceptAuthority_authority" + authority + ".sql", queryParams);

		return 0 != result;
	}

	/**
	 * MOM(組社権)の権限区分を取得する
	 * 
	 * @param singleUserId
	 *            代理承認者SUID
	 * @param momServiceUrl
	 *            MoMサービスURL
	 * @param relatedId
	 *            接続ID
	 * @param dbUrl
	 *            DB_URL
	 * @param dbUser
	 *            DB_USER
	 * @param dbPassword
	 *            DB_PASSWORD
	 * @return 権限区分
	 * @throws Exception
	 */
	private String searchMomAuthority(String singleUserId, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

		KengenServiceServiceLocator kengenServiceLocator = new KengenServiceServiceLocator();
		kengenServiceLocator.setKengenServiceEndpointAddress(momServiceUrl);

		// MoM提供モジュールから社員情報の取得
		EmployeeInfoDto[] employeeInfoDtos = kengenServiceLocator.getKengenService().getContactEmpFromSUID(singleUserId, Calendar.getInstance(), false, relatedId);
		if (employeeInfoDtos.length != 1) {
			return null;
		}

		ArrayList<EmployeeAuthInfoDto> list = new ArrayList<>();
		List<EmployeeOrgInfoDto> employeeOrgInfoDtoList = Arrays.asList(employeeInfoDtos[0].getOrgList());
		employeeOrgInfoDtoList.stream().forEach(employeeOrgInfoDto -> {
			List<EmployeeAuthInfoDto> employeeAuthInfoDtoList = Arrays.asList(employeeOrgInfoDto.getClassifyList());
			employeeAuthInfoDtoList.stream().forEach(employeeAuthInfoDto -> list.add(employeeAuthInfoDto));
		});
		ArrayList<String> classify = new ArrayList<>();
		list.stream().forEach(authTarg -> classify.add(authTarg.getClassifyId()));

		// MoM提供モジュールから権限情報の取得
		AuthoritySearch authoritySearch = new AuthoritySearch();
		AuthorityInfoActionDto[] authorityInfoActionDtos = authoritySearch.getAuthSumFromEmpId((String[]) classify.toArray(new String[0]), relatedId, connection);
		if (authorityInfoActionDtos == null || authorityInfoActionDtos.length == 0) {
			return null;
		}

		// 権限情報から発注承認(0390)の更新(03)の権限区分を取得する
		List<AuthorityInfoActionDto> authorityInfoActionDtoList = Arrays.asList(authorityInfoActionDtos);
		List<AuthorityInfoLevelDto> authorityInfoLevelDtoList = Arrays.asList(authorityInfoActionDtoList.stream().filter(authorityInfoActionDto -> "03".equals(authorityInfoActionDto.getActionId())).findFirst().get().getLevelList());
		return authorityInfoLevelDtoList.stream().filter(authorityInfoLevelDto -> "0390".equals(authorityInfoLevelDto.getInfoId())).findFirst().get().getLevelId();
	}
}
