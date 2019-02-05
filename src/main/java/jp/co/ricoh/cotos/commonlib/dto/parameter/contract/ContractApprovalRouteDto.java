package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractApprovalRouteDto extends DtoBase {

	/**
	 * 承認依頼者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String approvalRequesterEmpId;

	/**
	 * 承認依頼者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者氏名", required = true, position = 4, allowableValues = "range[0,255]")
	private String approvalRequesterName;

	/**
	 * 承認依頼者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String approvalRequesterOrgName;

	/**
	 * 特価承認対象フラグ
	 */
	@Max(9)
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 6, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * 契約承認ルートノード
	 */
	@OneToMany(mappedBy = "contractApprovalRoute")
	@ApiModelProperty(value = "契約承認ルートノード", required = true, position = 7)
	@OrderBy("approvalOrder ASC")
	private List<ContractApprovalRouteNodeDto> contractApprovalRouteNodeList;
}
