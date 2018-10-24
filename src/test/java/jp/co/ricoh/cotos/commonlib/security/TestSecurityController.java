package jp.co.ricoh.cotos.commonlib.security;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementPicWorkerEmp;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkOperationLog;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
import jp.co.ricoh.cotos.commonlib.entity.communication.CommunicationHistory;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
import jp.co.ricoh.cotos.commonlib.entity.communication.ContactTo;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractOperationLog;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.DealerContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ItemContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ProductContract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ItemEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.OperationLog;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ProductEstimation;
import lombok.Data;

/**
 *
 * テスト実施用コントローラクラス
 *
 */
@Data
@RestController
@RequestMapping("/test/api")
public class TestSecurityController {

	private String swaggerBody = "swagger";

	@RequestMapping(method = RequestMethod.GET, path = "/test/{id}")
	@Transactional
	public String get() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + ","
				+ userInfo.getJwt();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/test")
	@Transactional
	public String post() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + ","
				+ userInfo.getJwt();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/test")
	@Transactional
	public String put() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return userInfo.getSingleUserId() + "," + userInfo.getMomEmployeeId() + "," + userInfo.getOrigin() + ","
				+ userInfo.getJwt();
	}

	@GetMapping(path = "/swagger-ui.html")
	public String swagger() {
		return swaggerBody;
	}

	/**
	 * 指定した Entity クラスに相応しい Validation 実行メソッドを呼び出す
	 * @param entity エンティティ
	 * @param result テスト実行結果
	 * @return BindingResult テスト実行結果
	 */
	public BindingResult callEntityValidation(Object entity, BindingResult result) {
		// 以下、arrangement パッケージの callEntityValidation
		if (entity instanceof Arrangement) {
			callEntityValidation((Arrangement) entity, result);
		} else if (entity instanceof ArrangementPicWorkerEmp) {
			callEntityValidation((ArrangementPicWorkerEmp) entity, result);
		} else if (entity instanceof ArrangementWork) {
			callEntityValidation((ArrangementWork) entity, result);
		} else if (entity instanceof ArrangementWorkApprovalResult) {
			callEntityValidation((ArrangementWorkApprovalResult) entity, result);
		} else if (entity instanceof ArrangementWorkApprovalRoute) {
			callEntityValidation((ArrangementWorkApprovalRoute) entity, result);
		} else if (entity instanceof ArrangementWorkApprovalRouteNode) {
			callEntityValidation((ArrangementWorkApprovalRouteNode) entity, result);
		} else if (entity instanceof ArrangementWorkAttachedFile) {
			callEntityValidation((ArrangementWorkAttachedFile) entity, result);
		} else if (entity instanceof ArrangementWorkCheckResult) {
			callEntityValidation((ArrangementWorkCheckResult) entity, result);
		} else if (entity instanceof ArrangementWorkOperationLog) {
			callEntityValidation((ArrangementWorkOperationLog) entity, result);
		}
		// 以下、communication パッケージの callEntityValidation
		else if (entity instanceof Communication) {
			callEntityValidation((Communication) entity, result);
		} else if (entity instanceof CommunicationHistory) {
			callEntityValidation((CommunicationHistory) entity, result);
		} else if (entity instanceof Contact) {
			callEntityValidation((Contact) entity, result);
		} else if (entity instanceof ContactTo) {
			callEntityValidation((ContactTo) entity, result);
		}
		// 以下、contact パッケージの callEntityValidation
		else if (entity instanceof Contract) {
			callEntityValidation((Contract) entity, result);
		} else if (entity instanceof ContractAddedEditorEmp) {
			callEntityValidation((ContractAddedEditorEmp) entity, result);
		} else if (entity instanceof ContractApprovalResult) {
			callEntityValidation((ContractApprovalResult) entity, result);
		} else if (entity instanceof ContractApprovalRoute) {
			callEntityValidation((ContractApprovalRoute) entity, result);
		} else if (entity instanceof ContractApprovalRouteNode) {
			callEntityValidation((ContractApprovalRouteNode) entity, result);
		} else if (entity instanceof ContractAttachedFile) {
			callEntityValidation((ContractAttachedFile) entity, result);
		} else if (entity instanceof ContractCheckResult) {
			callEntityValidation((ContractCheckResult) entity, result);
		} else if (entity instanceof ContractDetail) {
			callEntityValidation((ContractDetail) entity, result);
		} else if (entity instanceof ContractOperationLog) {
			callEntityValidation((ContractOperationLog) entity, result);
		} else if (entity instanceof ContractPicSaEmp) {
			callEntityValidation((ContractPicSaEmp) entity, result);
		} else if (entity instanceof CustomerContract) {
			callEntityValidation((CustomerContract) entity, result);
		} else if (entity instanceof DealerContract) {
			callEntityValidation((DealerContract) entity, result);
		} else if (entity instanceof ItemContract) {
			callEntityValidation((ItemContract) entity, result);
		} else if (entity instanceof ProductContract) {
			callEntityValidation((ProductContract) entity, result);
		}
		// 以下、estimation パッケージの callEntityValidation
		else if (entity instanceof CustomerEstimation) {
			callEntityValidation((CustomerEstimation) entity, result);
		} else if (entity instanceof DealerEstimation) {
			callEntityValidation((DealerEstimation) entity, result);
		} else if (entity instanceof Estimation) {
			callEntityValidation((Estimation) entity, result);
		} else if (entity instanceof EstimationAddedEditorEmp) {
			callEntityValidation((EstimationAddedEditorEmp) entity, result);
		} else if (entity instanceof EstimationApprovalResult) {
			callEntityValidation((EstimationApprovalResult) entity, result);
		} else if (entity instanceof EstimationApprovalRoute) {
			callEntityValidation((EstimationApprovalRoute) entity, result);
		} else if (entity instanceof EstimationApprovalRouteNode) {
			callEntityValidation((EstimationApprovalRouteNode) entity, result);
		} else if (entity instanceof EstimationAttachedFile) {
			callEntityValidation((EstimationAttachedFile) entity, result);
		} else if (entity instanceof EstimationCheckResult) {
			callEntityValidation((EstimationCheckResult) entity, result);
		} else if (entity instanceof EstimationDetail) {
			callEntityValidation((EstimationDetail) entity, result);
		} else if (entity instanceof EstimationPicSaEmp) {
			callEntityValidation((EstimationPicSaEmp) entity, result);
		} else if (entity instanceof ItemEstimation) {
			callEntityValidation((ItemEstimation) entity, result);
		} else if (entity instanceof OperationLog) {
			callEntityValidation((OperationLog) entity, result);
		} else if (entity instanceof ProductEstimation) {
			callEntityValidation((ProductEstimation) entity, result);
		}
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated Arrangement entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementPicWorkerEmp entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWork entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkApprovalResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkApprovalRoute entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkApprovalRouteNode entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkAttachedFile entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkCheckResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ArrangementWorkOperationLog entity,
			BindingResult result) {
		return result;
	}

	// 以下、communication パッケージの callEntityValidation
	public BindingResult callEntityValidation(@RequestBody @Validated Communication entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated CommunicationHistory entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated Contact entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContactTo entity, BindingResult result) {
		return result;
	}

	// 以下、contact パッケージの callEntityValidation
	public BindingResult callEntityValidation(@RequestBody @Validated Contract entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractAddedEditorEmp entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractApprovalResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractApprovalRoute entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractApprovalRouteNode entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractAttachedFile entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractCheckResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractDetail entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractOperationLog entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ContractPicSaEmp entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated CustomerContract entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated DealerContract entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ItemContract entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ProductContract entity, BindingResult result) {
		return result;
	}

	// 以下、estimation パッケージの callEntityValidation
	public BindingResult callEntityValidation(@RequestBody @Validated CustomerEstimation entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated DealerEstimation entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated Estimation entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationAddedEditorEmp entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationApprovalResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationApprovalRoute entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationApprovalRouteNode entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationAttachedFile entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationCheckResult entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationDetail entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated EstimationPicSaEmp entity,
			BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ItemEstimation entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated OperationLog entity, BindingResult result) {
		return result;
	}

	public BindingResult callEntityValidation(@RequestBody @Validated ProductEstimation entity,
			BindingResult result) {
		return result;
	}
}
