package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import javax.persistence.Lob;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class EstimationRegisterParameter {

	/**
	 * 見積ID
	 */
	@ApiParam(value = "見積ID", allowableValues = "range[0,9999999999999999999]", required = true)
	private long estimationId;

	/**
	 * 商品グループマスタID
	 */
	@ApiParam(value = "商品グループマスタID", allowableValues = "range[0,9999999999999999999]", required = true)
	private long productGroupMasterId;

	/**
	 * 拡張項目
	 */
	@ApiParam(value = "拡張項目", required = true)
	@Lob
	private String extendsParameter;

	/**
	 * 登録者
	 */
	@ApiParam(value = "登録者", allowableValues = "range[0,255]", required = true)
	private String createdUser;

	/**
	 * 見積明細
	 */
	@ApiParam(value = "見積明細", required = true)
	private List<EstimationDetailRegisterParameter> estimationDetailRegisterParameterList;

}
