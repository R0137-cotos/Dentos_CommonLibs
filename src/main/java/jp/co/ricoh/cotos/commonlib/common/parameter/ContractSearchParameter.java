package jp.co.ricoh.cotos.commonlib.common.parameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 契約を検索するためのキー項目クラスを表します。
 */
@Data
public class ContractSearchParameter {

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
	 * 見積書番号
	 */
	@ApiParam(value = "見積書番号", required = false)
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiParam(value = "見積件名:部分一致", required = false)
	private String likeSearchEstimationTitle;

	/**
	 * 案件番号
	 */
	@ApiParam(value = "案件番号", required = false)
	private String caseNumber;

	/**
	 * 案件名
	 */
	@ApiParam(value = "案件名:部分一致", required = false)
	private String likeSearchCaseTitle;

	/**
	 * お客様企業ID
	 */
	@ApiParam(value = "お客様企業ID", required = false)
	private String customerId;

	/**
	 * 担当支社
	 */
	@ApiParam(value = "担当支社：会社IDを指定", required = false)
	private String picAffiliateId;

	/**
	 * 担当部門
	 */
	@ApiParam(value = "担当部門：会社IDを指定", required = false)
	private String picDepartmentId;

	/**
	 * 担当課所
	 */
	@ApiParam(value = "担当課所：会社IDを指定", required = false)
	private String picDivisionId;

	/**
	 * 担当営業
	 */
	@ApiParam(value = "担当営業：MoM社員IDを指定", required = false)
	private String picEmptxId;

	/**
	 * 審査／承認者
	 */
	@ApiParam(value = "審査／承認者：MoM社員IDを指定", required = false)
	private String approvalEmptxId;

	/**
	 * 協力者
	 */
	@ApiParam(value = "協力者：MoM社員IDを指定", required = false)
	private String collaborationEmptxId;

	/**
	 * お客様企業名
	 */
	@ApiParam(value = "お客様企業名:部分一致", required = false)
	private String likeSearchCustomerName;

	/**
	 * 事業所名
	 */
	@ApiParam(value = "お客様事業所名:部分一致", required = false)
	private String likeSearchOfficeName;

	/**
	 * 部門名
	 */
	@ApiParam(value = "お客様部門名:部分一致", required = false)
	private String likeSearchDepartmentName;

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
	 * 契約ステータス
	 */
	@ApiParam(value = "契約ステータス", required = false)
	private String contractStatus;

	/**
	 * 契約種別
	 */
	@ApiParam(value = "契約種別", required = false)
	private String contractType;

	/**
	 * 商品名称
	 */
	@ApiParam(value = "商品名称", required = false)
	private String productName;

	/**
	 * 得意先コード
	 */
	@ApiParam(value = "得意先コード", required = false)
	private String billingCustomerSpCode;

	/**
	 * 請求開始月
	 */
	@ApiParam(value = "請求開始月", required = false)
	private String billingMonth;

	/**
	 * ソート項目
	 */
	@NotEmpty(message = "{SortColumn}{NotEmptyError}:{SortColumn}{NotEmptyErrorMsg}")
	@ApiParam(value = "ソート項目", required = false)
	private int sortColumn;

	/**
	 * ソート順
	 */
	@ApiParam(value = "ソート順", required = true, allowableValues = "昇順, 降順")
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
