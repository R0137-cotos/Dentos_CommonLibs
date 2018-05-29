package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci101Master;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.EmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.KjbMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci101Repository;

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

	/**
	 * 見積情報存在チェック
	 * 
	 * @param estimationId
	 *            見積ID
	 * @return チェック結果
	 */
	public boolean existsEstimation(Long estimationId) {
		if (null != estimationId) {
			Estimation estimation = estimationRepository.findOne(estimationId);
			if (null != estimation) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 見積承認ルート情報存在チェック
	 * 
	 * @param estimationApprovalRouteId
	 *            見積承認ルートID
	 * @return チェック結果
	 */
	public boolean existsEstimationApprovalRoute(Long estimationApprovalRouteId) {
		if (null != estimationApprovalRouteId) {
			EstimationApprovalRoute estimationApprovalRoute = estimationApprovalRouteRepository.findOne(estimationApprovalRouteId);
			if (null != estimationApprovalRoute) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 見積承認ルートノード情報存在チェック
	 * 
	 * @param estimationApprovalRouteNodeId
	 *            見積承認ルートノードID
	 * @return チェック結果
	 */
	public boolean existsEstimationApprovalRouteNode(Long estimationApprovalRouteNodeId) {
		if (null != estimationApprovalRouteNodeId) {
			EstimationApprovalRouteNode estimationApprovalRouteNode = estimationApprovalRouteNodeRepository.findOne(estimationApprovalRouteNodeId);
			if (null != estimationApprovalRouteNode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 契約情報存在チェック
	 * 
	 * @param contractId
	 *            契約ID
	 * @return 契約情報
	 */
	public boolean existsContract(Long contractId) {
		if (null != contractId) {
			Contract contract = contractRepository.findOne(contractId);
			if (null != contract) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 契約承認ルート情報存在チェック
	 * 
	 * @param contractApprovalRouteId
	 *            契約承認ルートID
	 * @return チェック結果
	 */
	public boolean existsContractApprovalRoute(Long contractApprovalRouteId) {
		if (null != contractApprovalRouteId) {
			ContractApprovalRoute contractApprovalRoute = contractApprovalRouteRepository.findOne(contractApprovalRouteId);
			if (null != contractApprovalRoute) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 契約承認ルートノード情報存在チェック
	 * 
	 * @param contractApprovalRouteNodeId
	 *            契約承認ルートノードID
	 * @return チェック結果
	 */
	public boolean existsContractApprovalRouteNode(Long contractApprovalRouteNodeId) {
		if (null != contractApprovalRouteNodeId) {
			ContractApprovalRouteNode contractApprovalRouteNode = contractApprovalRouteNodeRepository.findOne(contractApprovalRouteNodeId);
			if (null != contractApprovalRouteNode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 手配情報存在チェック
	 * 
	 * @param arrangementId
	 *            手配ID
	 * @return チェック結果
	 */
	public boolean existsArrangement(Long arrangementId) {
		if (null != arrangementId) {
			Arrangement arrangement = arrangementRepository.findOne(arrangementId);
			if (null != arrangement) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 手配業務情報存在チェック
	 * 
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @return チェック結果
	 */
	public boolean existsArrangementWork(Long arrangementWorkId) {
		if (null != arrangementWorkId) {
			ArrangementWork arrangementWork = arrangementWorkRepository.findOne(arrangementWorkId);
			if (null != arrangementWork) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 社員マスタ存在チェック
	 * 
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @return チェック結果
	 */
	public boolean existsEmployeeMaster(String momEmployeeId) {
		if (null != momEmployeeId) {
			EmployeeMaster employeeMaster = employeeMasterRepository.findByMomEmployeeId(momEmployeeId);
			if (null != employeeMaster) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 企事部マスタ存在チェック
	 * 
	 * @param mclMomKjbId
	 *            企事部ID
	 * @return チェック結果
	 */
	public boolean existsKjbMaster(String mclMomKjbId) {
		if (null != mclMomKjbId) {
			List<KjbMaster> masterList = kjbMasterRepository.findByMclMomKjbIdOrderByMclMomRelId(mclMomKjbId);
			if (0 != masterList.size()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得意先マスタ存在チェック
	 * 
	 * @param originalSystemCode
	 *            得意先コード
	 * @return チェック結果
	 */
	public boolean existsBillingCustomerMaster(String originalSystemCode) {
		if (null != originalSystemCode) {
			MvTJmci101Master siteMaster = billingSiteMasterRepository.findByOriginalSystemCode(originalSystemCode);
			if (null != siteMaster) {
				return true;
			}
		}
		return false;
	}
}
