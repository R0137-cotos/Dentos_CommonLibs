package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import jakarta.persistence.Lob;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class EstimationRegisterParameter {

	/**
	 * 見積ID
	 */
	@Min(0)
	@ApiParam(value = "見積ID", allowableValues = "range[0,9223372036854775807]", required = true)
	private long estimationId;

	/**
	 * 商品グループマスタID
	 */
	@Min(0)
	@ApiParam(value = "商品グループマスタID", allowableValues = "range[0,9223372036854775807]", required = true)
	private long productGroupMasterId;

	/**
	 * 拡張項目
	 */
	@NotNull
	@ApiParam(value = "拡張項目", required = true)
	@Lob
	private String extendsParameter;

	/**
	 * 登録者
	 */
	@NotNull
	@Size(max = 255)
	@ApiParam(value = "登録者", allowableValues = "range[0,255]", required = true)
	private String createdUser;

	/**
	 * 見積明細
	 */
	@NotNull
	@Valid
	@ApiParam(value = "見積明細", required = true)
	private List<EstimationDetailRegisterParameter> estimationDetailRegisterParameterList;

}
