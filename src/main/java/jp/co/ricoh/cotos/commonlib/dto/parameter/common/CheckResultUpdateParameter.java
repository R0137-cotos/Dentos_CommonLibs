package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import jakarta.validation.constraints.Min;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 汎用チェック結果更新時のパラメーターを表します。
 */
@Data
public class CheckResultUpdateParameter {

	/**
	 * チェック結果ID
	 */
	@Min(0)
	@ApiParam(value = "チェック結果ID", allowableValues = "range[0,9223372036854775807]", required = true)
	private long checkResultId;

	/**
	 * 更新ステータス
	 */
	@ApiParam(value = "更新ステータス", required = true)
	private boolean updateStatus;

}
