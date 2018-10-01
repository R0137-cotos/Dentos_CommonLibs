package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 手配を検索するためのキー項目クラスを表します。
 */
@Data
public class ArrangementSearchParameter {

	public enum SortOrder {

		昇順, 降順;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<SortOrder> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	/**
	 * 契約番号
	 */
	@ApiParam(value = "契約番号", required = false)
	private String contractNumber;

	/**
	 * サービス識別番号
	 */
	@ApiParam(value = "サービス識別番号", required = false)
	private String serviceIdentificationNumber;

	/**
	 * 契約種別
	 */
	@ApiParam(value = "契約種別", required = false)
	private String contractType;

	/**
	 * サービス開始日(前)
	 */
	@ApiParam(value = "サービス開始日(前)", required = false)
	private String serviceTermStartFrom;

	/**
	 * サービス開始日(後)
	 */
	@ApiParam(value = "サービス開始日(後)", required = false)
	private String serviceTermStartTo;

	/**
	 * サービス終了日(前)
	 */
	@ApiParam(value = "サービス開始日(前)", required = false)
	private String serviceTermEndFrom;

	/**
	 * サービス終了日(後)
	 */
	@ApiParam(value = "サービス開始日(後)", required = false)
	private String serviceTermEndTo;

	/**
	 * 商品ID
	 */
	@ApiParam(value = "商品ID", required = false)
	private String productId;

	/**
	 * 得意先コード
	 */
	@ApiParam(value = "得意先コード", required = false)
	private String billingCustomerSpCode;

	/**
	 * 見積書番号
	 */
	@ApiParam(value = "見積書番号", required = false)
	private String estimateNumber;

	/**
	 * 担当支社
	 */
	@ApiParam(value = "担当支社：会社IDを指定", required = false)
	private String picAffiliateId;

	/**
	 * 担当営業
	 */
	@ApiParam(value = "担当営業：MoM社員IDを指定", required = false)
	private String picEmptxId;

	/**
	 * 協力者
	 */
	@ApiParam(value = "協力者：MoM社員IDを指定", required = false)
	private String collaborationEmptxId;

	/**
	 * お客様企業名
	 */
	@ApiParam(value = "お客様企業名： 部分一致", required = false)
	private String likeSearchCustomerName;

	/**
	 * 事業所名
	 */
	@ApiParam(value = "お客様事業所名： 部分一致", required = false)
	private String likeSearchOfficeName;

	/**
	 * 手配マスタID
	 */
	@ApiParam(value = "手配マスタID", required = false)
	private String arrangementMasterId;

	/**
	 * 手配業務担当者
	 */
	@ApiParam(value = "手配業務担当者：MoM社員IDを指定", required = false)
	private String workUserEmptxId;

	/**
	 * 手配ステータス
	 */
	@ApiParam(value = "手配ステータス", required = false)
	private String arrangementStatus;

	/**
	 * 手配業務ステータス
	 */
	@ApiParam(value = "手配業務ステータス", required = false)
	private String arrangementWorkStatus;

	/**
	 * ソート項目
	 */
	@NotNull(message = "{SortColumn}{NotEmptyError}:{SortColumn}{NotEmptyErrorMsg}")
	@ApiParam(value = "ソート項目", required = true, allowableValues = "range[4, 18]")
	private int sortColumn;

	/**
	 * ソート順
	 */
	@ApiParam(value = "ソート順", required = true)
	private SortOrder sortOrder;

	/**
	 * 降順
	 */
	@ApiParam(value = "降順", required = false, hidden = true)
	private boolean sortDesc;

	/**
	 * パラメータをMapにする
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
