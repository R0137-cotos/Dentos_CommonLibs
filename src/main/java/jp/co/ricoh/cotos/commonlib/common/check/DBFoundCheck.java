package jp.co.ricoh.cotos.commonlib.common.check;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.common.entity.Arrangement;
import jp.co.ricoh.cotos.commonlib.common.entity.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.common.entity.BillingCustomerInfo;
import jp.co.ricoh.cotos.commonlib.common.entity.Contract;
import jp.co.ricoh.cotos.commonlib.common.entity.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.common.entity.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;
import jp.co.ricoh.cotos.commonlib.common.entity.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.common.entity.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.common.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.common.master.MvTJmci101Master;
import jp.co.ricoh.cotos.commonlib.common.master.MvTJmci105Master;
import jp.co.ricoh.cotos.commonlib.common.repository.ArrangementRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.ArrangementWorkRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.ContractApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.ContractApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.ContractRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.EmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.EstimationApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.EstimationRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.KjbMasterRepository;
import jp.co.ricoh.cotos.commonlib.common.repository.MvTJmci101Repository;
import jp.co.ricoh.cotos.commonlib.common.repository.MvTJmci105Repository;

/**
 * DB存在チェック管理クラス
 */
@Component
public class DBFoundCheck {

	@Autowired
	EstimationRepository estimationRepository;
	@Autowired
	EstimationApprovalRouteRepository estimationApprovalRouteRepository;
	@Autowired
	EstimationApprovalRouteNodeRepository estimationApprovalRouteNodeRepository;
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	ContractApprovalRouteRepository contractApprovalRouteRepository;
	@Autowired
	ContractApprovalRouteNodeRepository contractApprovalRouteNodeRepository;
	@Autowired
	ArrangementRepository arrangementRepository;
	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

	@Autowired
	EmployeeMasterRepository employeeMasterRepository;
	@Autowired
	KjbMasterRepository kjbMasterRepository;
	@Autowired
	MvTJmci101Repository billingSiteMasterRepository;
	@Autowired
	MvTJmci105Repository billingCompanyMasterRepository;

	/**
	 * 見積情報存在チェック
	 * 
	 * @param estimationId
	 *            見積ID
	 * @return 見積情報
	 */
	public Estimation existsFoundEstimation(Long estimationId) {
		if (null != estimationId) {
			Estimation estimation = estimationRepository.findOne(estimationId);
			if (null != estimation) {
				return estimation;
			}
		}
		return null;
	}

	/**
	 * 見積承認ルート情報存在チェック
	 * 
	 * @param estimationApprovalRouteId
	 *            見積承認ルートID
	 * @return 見積承認ルート情報
	 */
	public EstimationApprovalRoute existsFoundEstimationApprovalRoute(Long estimationApprovalRouteId) {
		if (null != estimationApprovalRouteId) {
			EstimationApprovalRoute estimationApprovalRoute = estimationApprovalRouteRepository.findOne(estimationApprovalRouteId);
			if (null != estimationApprovalRoute) {
				return estimationApprovalRoute;
			}
		}
		return null;
	}

	/**
	 * 見積承認ルートノード情報存在チェック
	 * 
	 * @param estimationApprovalRouteNodeId
	 *            見積承認ルートノードID
	 * @return 見積承認ルートノード情報
	 */
	public EstimationApprovalRouteNode existsFoundEstimationApprovalRouteNode(Long estimationApprovalRouteNodeId) {
		if (null != estimationApprovalRouteNodeId) {
			EstimationApprovalRouteNode estimationApprovalRouteNode = estimationApprovalRouteNodeRepository.findOne(estimationApprovalRouteNodeId);
			if (null != estimationApprovalRouteNode) {
				return estimationApprovalRouteNode;
			}
		}
		return null;
	}

	/**
	 * 契約情報存在チェック
	 * 
	 * @param contractId
	 *            契約ID
	 * @return 契約情報
	 */
	public Contract existsFoundContract(Long contractId) {
		if (null != contractId) {
			Contract contract = contractRepository.findOne(contractId);
			if (null != contract) {
				return contract;
			}
		}
		return null;
	}

	/**
	 * 契約承認ルート情報存在チェック
	 * 
	 * @param contractApprovalRouteId
	 *            契約承認ルートID
	 * @return 契約承認ルート情報
	 */
	public ContractApprovalRoute existsFoundContractApprovalRoute(Long contractApprovalRouteId) {
		if (null != contractApprovalRouteId) {
			ContractApprovalRoute contractApprovalRoute = contractApprovalRouteRepository.findOne(contractApprovalRouteId);
			if (null != contractApprovalRoute) {
				return contractApprovalRoute;
			}
		}
		return null;
	}

	/**
	 * 契約承認ルートノード情報存在チェック
	 * 
	 * @param contractApprovalRouteNodeId
	 *            契約承認ルートノードID
	 * @return 契約承認ルートノード情報
	 */
	public ContractApprovalRouteNode existsFoundContractApprovalRouteNode(Long contractApprovalRouteNodeId) {
		if (null != contractApprovalRouteNodeId) {
			ContractApprovalRouteNode contractApprovalRouteNode = contractApprovalRouteNodeRepository.findOne(contractApprovalRouteNodeId);
			if (null != contractApprovalRouteNode) {
				return contractApprovalRouteNode;
			}
		}
		return null;
	}

	/**
	 * 手配情報存在チェック
	 * 
	 * @param arrangementId
	 *            手配ID
	 * @return 手配情報
	 */
	public Arrangement existsFoundArrangement(Long arrangementId) {
		if (null != arrangementId) {
			Arrangement arrangement = arrangementRepository.findOne(arrangementId);
			if (null != arrangement) {
				return arrangement;
			}
		}
		return null;
	}

	/**
	 * 手配業務情報存在チェック
	 * 
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @return 手配業務情報
	 */
	public ArrangementWork existsFoundArrangementWork(Long arrangementWorkId) {
		if (null != arrangementWorkId) {
			ArrangementWork arrangementWork = arrangementWorkRepository.findOne(arrangementWorkId);
			if (null != arrangementWork) {
				return arrangementWork;
			}
		}
		return null;
	}

	/**
	 * 社員マスタ存在チェック
	 * 
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @return 社員マスタ情報
	 */
	public EmployeeMaster existsFoundEmployeeMaster(String momEmployeeId) {
		if (null != momEmployeeId) {
			EmployeeMaster employeeMaster = employeeMasterRepository.findByMomEmployeeId(momEmployeeId);
			if (null != employeeMaster) {
				return employeeMaster;
			}
		}
		return null;
	}

	/**
	 * 企事部マスタ存在チェック
	 * 
	 * @param mclMomKjbId
	 *            企事部ID
	 * @return 企事部マスタ情報
	 */
	public KjbMaster existsFoundKjbMaster(String mclMomKjbId) {
		if (null != mclMomKjbId) {
			List<KjbMaster> masterList = kjbMasterRepository.findByMclMomKjbIdOrderByMclMomRelId(mclMomKjbId);
			if (0 != masterList.size()) {
				return masterList.get(0);
			}
		}
		return null;
	}

	/**
	 * 得意先マスタ存在チェック
	 * 
	 * @param originalSystemCode
	 *            得意先コード
	 * @return 得意先マスタ情報
	 */
	public BillingCustomerInfo existsFoundBillingCustomerMaster(String originalSystemCode) {
		BillingCustomerInfo customerInfo = new BillingCustomerInfo();
		MvTJmci105Master billingCompanyMaster = new MvTJmci105Master();
		MvTJmci101Master siteMaster = billingSiteMasterRepository.findByOriginalSystemCode(originalSystemCode);
		if (siteMaster == null) {
			return null;
		} else if ("1".equals(siteMaster.getBillingCustomerSpecialFlg())) {
			customerInfo.setCustomerName(siteMaster.getEbsBusinessPlaceName());
		} else {
			billingCompanyMaster = billingCompanyMasterRepository.findOne(siteMaster.getCustomerNumber());
			customerInfo.setCustomerName(billingCompanyMaster.getEnterpriseName() + " " + siteMaster.getEbsBusinessPlaceName());
		}
		customerInfo.setOriginalSystemCode(siteMaster.getOriginalSystemCode());

		return customerInfo;
	}
}
