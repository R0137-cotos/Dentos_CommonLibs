package jp.co.ricoh.cotos.commonlib.dto.parameter.communication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 契約詳細を検索するためのキー項目クラスを表します。
 */
@Data
public class ContractSearchParameterForDetail {

	/**
	 * 商品種別
	 */
	@ApiParam(value = "商品種別", required = false)
	@ApiModelProperty(value = "商品種別", required = false, allowableValues = "range[0,15]")
	private String productType;

	/**
	 * COTOS契約ライフサイクル状態
	 */
	@ApiParam(value = "COTOS契約ライフサイクル状態", required = true)
	@ApiModelProperty(value = "COTOS契約ライフサイクル状態", required = true)
	private List<String> status;

	/**
	 * 契約情報更新日From
	 */
	@ApiParam(value = "契約情報更新日From", required = false)
	@ApiModelProperty(value = "契約情報更新日From", required = false, allowableValues = "range[0,15]")
	private String updateFrom;

	/**
	 * 契約情報更新日To
	 */
	@ApiParam(value = "契約情報更新日To", required = false)
	@ApiModelProperty(value = "契約情報更新日To", required = false, allowableValues = "range[0,15]")
	private String updateTo;

	/**
	 * RJ管理番号
	 */
	@ApiParam(value = "RJ管理番号", required = false)
	@ApiModelProperty(value = "RJ管理番号", required = false, allowableValues = "range[0,15]")
	private String rjManageNumber;

	/**
	 * アプリケーションIDリスト
	 */
	@ApiParam(value = "アプリケーションIDリスト", required = true)
	@ApiModelProperty(value = "アプリケーションIDリスト", required = true)
	private List<String> appId;

	/**
	 * 他システムデータ排他フラグ
	 */
	@ApiParam(value = "他システムデータ排他フラグ", required = true)
	@ApiModelProperty(value = "他システムデータ排他フラグ", required = true)
	private int otherSysDataExcludeFlg;

	/**
	 * パラメータをMapにする。
	 */
	public Map<String, Object> createParamaterMap() {
		Map<String, Object> retMap = new HashMap<>();

		FieldUtils.getAllFieldsList(this.getClass()).stream().filter(putField -> !putField.getName().startsWith("$")).forEach(field -> {
			try {
				retMap.put(field.getName(), field.get(this));
			} catch (IllegalAccessException e) {
				retMap.put(field.getName(), null);
			}
		});

		return retMap;
	}
}
