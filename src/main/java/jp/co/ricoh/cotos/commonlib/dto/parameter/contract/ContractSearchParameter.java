package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "契約番号", required = false, allowableValues = "range[0,15]")
	private String contractNumber;

	/**
	 * 契約番号枝番
	 */
	@ApiParam(value = "契約番号枝番", required = false)
	@ApiModelProperty(value = "契約番号枝番", required = false, allowableValues = "range[0,2]")
	private String contractBranchNumber;

	/**
	 * サービス識別番号
	 */
	@ApiParam(value = "サービス識別番号", required = false)
	@ApiModelProperty(value = "サービス識別番号", required = false, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

	/**
	 * RJ管理番号
	 */
	@ApiParam(value = "サービス識別番号", required = false)
	@ApiModelProperty(value = "サービス識別番号", required = false, allowableValues = "range[0,20]")
	private String rjManageNumber;

	/**
	 * 見積書番号
	 */
	@ApiParam(value = "見積番号", required = false)
	@ApiModelProperty(value = "見積番号", required = false, allowableValues = "range[0,15]")
	private String estimateNumber;

	/**
	 * 見積書番号
	 */
	@ApiParam(value = "見積番号枝番", required = false)
	@ApiModelProperty(value = "見積番号枝番", required = false, allowableValues = "range[0,2]")
	private String estimateBranchNumber;

	/**
	 * 見積件名
	 */
	@ApiParam(value = "見積件名:部分一致", required = false)
	@ApiModelProperty(value = "見積件名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchEstimationTitle;

	/**
	 * 案件番号
	 */
	@ApiParam(value = "案件番号", required = false)
	@ApiModelProperty(value = "案件番号", required = false, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件名
	 */
	@ApiParam(value = "案件名:部分一致", required = false)
	@ApiModelProperty(value = "案件名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchCaseTitle;

	/**
	 * 契約状態
	 */
	@ApiParam(value = "契約状態", required = false)
	@ApiModelProperty(value = "契約状態<br />" //
			+ "状態遷移上のライフサイクル状態を表す。", //
			required = false) //
	private String lifecycleStatus;

	/**
	 * サービス開始日(前)
	 */
	@ApiParam(value = "サービス開始日(前)", required = false)
	@ApiModelProperty(value = "サービス開始日(前)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date serviceTermStartFrom;

	/**
	 * サービス開始日(後)
	 */
	@ApiParam(value = "サービス開始日(後)", required = false)
	@ApiModelProperty(value = "サービス開始日(後)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date serviceTermStartTo;

	/**
	 * サービス終了日(前)
	 */
	@ApiParam(value = "サービス終了日(前)", required = false)
	@ApiModelProperty(value = "サービス終了日(前)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date serviceTermEndFrom;

	/**
	 * サービス終了日(後)
	 */
	@ApiParam(value = "サービス終了日(後)", required = false)
	@ApiModelProperty(value = "サービス終了日(後)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date serviceTermEndTo;

	/**
	 * お客様企事部ID
	 */
	@ApiParam(value = "お客様企事部ID", required = false)
	@ApiModelProperty(value = "お客様企事部ID", required = false, allowableValues = "range[0,255]")
	private String customerId;

	/**
	 * お客様企業名
	 */
	@ApiParam(value = "お客様企業名:部分一致", required = false)
	@ApiModelProperty(value = "お客様企業名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchCustomerName;

	/**
	 * 事業所名
	 */
	@ApiParam(value = "お客様事業所名:部分一致", required = false)
	@ApiModelProperty(value = "お客様事業所名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchOfficeName;

	/**
	 * 部門名
	 */
	@ApiParam(value = "お客様部門名:部分一致", required = false)
	@ApiModelProperty(value = "お客様部門名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchDepartmentName;

	/**
	 * 希望納期(前)
	 */
	@ApiParam(value = "希望納期(前)", required = false)
	@ApiModelProperty(value = "希望納期(前)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date desiredDeliveryDateFrom;

	/**
	 * 希望納期(後)
	 */
	@ApiParam(value = "希望納期(後)", required = false)
	@ApiModelProperty(value = "希望納期(後)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date desiredDeliveryDateTo;

	/**
	 * 得意先コード
	 */
	@ApiParam(value = "得意先コード", required = false)
	@ApiModelProperty(value = "得意先コード", required = false, allowableValues = "range[0,255]")
	private String billingCustomerSpCode;

	/**
	 * 担当支社
	 */
	@ApiParam(value = "第1階層", required = false)
	@ApiModelProperty(value = "第1階層<br />" //
			+ "設定値はMoM組織ID。", //
			required = false, allowableValues = "range[0,255]") //
	private String picAffiliateId;

	/**
	 * 担当部門
	 */
	@ApiParam(value = "第2階層", required = false)
	@ApiModelProperty(value = "第2階層<br />" //
			+ "設定値はMoM組織ID。", //
			required = false, allowableValues = "range[0,255]") //
	private String picDepartmentId;

	/**
	 * 担当課所
	 */
	@ApiParam(value = "第3階層", required = false)
	@ApiModelProperty(value = "第3階層<br />" //
			+ "設定値はMoM組織ID。", //
			required = false, allowableValues = "range[0,255]") //
	private String picDivisionId;

	/**
	 * 担当者
	 */
	@ApiParam(value = "担当者：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "担当者<br />" //
			+ "担当者にはMoM社員IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String picEmptxId;

	/**
	 * 担当SS
	 */
	@ApiParam(value = "担当SS：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "担当SS<br />" //
			+ "担当SSにはMoM社員IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String picSsId;

	/**
	 * 担当CE
	 */
	@ApiParam(value = "担当CE：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "担当CE<br />" //
			+ "担当CEにはMoM社員IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String picCeId;

	/**
	 * 審査／承認者
	 */
	@ApiParam(value = "審査／承認者：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "審査／承認者<br />" //
			+ "審査／承認者にはMoM社員IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String approvalEmptxId;

	/**
	 * 協力者
	 */
	@ApiParam(value = "協力者：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "協力者<br />" //
			+ "協力者にはMoM社員IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String collaborationEmptxId;

	/**
	 * 請求開始月
	 */
	@ApiParam(value = "請求開始月", required = false)
	@ApiModelProperty(value = "請求開始月<br />" //
			+ "日付フォーマット：yyyy/MM", //
			required = false) //
	private String billingMonth;

	/**
	 * 契約ステータス
	 */
	@ApiParam(value = "契約ステータス", required = false)
	@ApiModelProperty(value = "契約ステータス<br />" //
			+ "状態遷移上のワークフローステータスを表す。", //
			required = false) //
	private String workflowStatus;

	/**
	 * 契約種別
	 */
	@ApiParam(value = "契約種別", required = false)
	@ApiModelProperty(value = "契約種別<br />" //
			+ "新規, プラン変更, 解約などの契約種別を表す。", //
			required = false)
	private String contractType;

	/**
	 * 商品マスタID
	 */
	@ApiParam(value = "商品マスタID", required = false)
	@ApiModelProperty(value = "商品マスタID", required = false)
	private Long productId;

	/**
	 * ソート項目
	 */
	@NotNull(message = "{SortColumn}{NotEmptyError}:{SortColumn}{NotEmptyErrorMsg}")
	@ApiParam(value = "ソート項目", required = true)
	@ApiModelProperty(value = "ソート項目<br />" //
			+ "ソート項目のint値は以下の通り各項目とマッピングされる。<br />" //
			+ "0:契約番号<br />" //
			+ "1:サービス識別番号<br />" //
			+ "2:契約種別<br />" //
			+ "3:契約ステータス<br />" //
			+ "4:契約状態<br />" //
			+ "5:見積番号<br />" //
			+ "6:見積件名<br />" //
			+ "7:案件番号<br />" //
			+ "8:請求開始月<br />" //
			+ "9:お客様企業名<br />" //
			+ "10:事業所<br />"//
			+ "11:部門<br />" //
			+ "12:サービス開始日<br />" //
			+ "13:サービス終了日<br />" //
			+ "14:商品名称<br />" //
			+ "15:担当営業<br />" //
			+ "16:担当営業所属<br />" //
			+ "17:担当SS氏名<br />" //
			+ "18:担当CE氏名", //
			required = true, allowableValues = "range[0,15]") //
	private int sortColumn;

	/**
	 * ソート順
	 */
	@ApiParam(value = "ソート順", required = true)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "ソート順", required = true)
	private SortOrder sortOrder;

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