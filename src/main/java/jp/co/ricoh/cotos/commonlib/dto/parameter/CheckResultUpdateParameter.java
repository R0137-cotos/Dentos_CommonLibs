package jp.co.ricoh.cotos.commonlib.dto.parameter;

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
	@ApiParam(value = "チェック結果ID", allowableValues = "range[0,9999999999999999999]", required = true)
	private long checkResultId;

	/**
	 * 更新ステータス
	 */
	@ApiParam(value = "更新ステータス", required = true)
	private boolean updateStatus;

}
