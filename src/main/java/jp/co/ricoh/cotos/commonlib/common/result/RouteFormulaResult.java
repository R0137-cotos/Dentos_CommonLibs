package jp.co.ricoh.cotos.commonlib.common.result;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ルート条件式実行結果用パラメーター
 *
 */
@Data
public class RouteFormulaResult {

	public enum RouteFormulaStatus {
		正常, 異常, 警告;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<RouteFormulaStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	/**
	 * 条件式実行結果
	 */
	@ApiModelProperty(value = "条件式判定結果", required = true, position = 1)
	private boolean applyRoute;

	/**
	 * 条件式実行結果ステータス
	 */
	@ApiModelProperty(value = "条件式実行結果ステータス", required = true, position = 2)
	private RouteFormulaStatus status;

	/**
	 * エラー発生項目
	 */
	@ApiModelProperty(value = "エラー発生項目", required = true, position = 3)
	private List<String> errorPropertyList;
}
