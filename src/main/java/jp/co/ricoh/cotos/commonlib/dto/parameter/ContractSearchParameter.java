package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;
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
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約状態<br />" //
			+ "状態遷移上のワークフローステータスを表す。", //
			required = false) //
	private WorkflowStatus contractCondition;

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
	 * お客様企業ID
	 */
	@ApiParam(value = "お客様企業ID", required = false)
	@ApiModelProperty(value = "お客様企業ID", required = false, allowableValues = "range[0,255]")
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
	@Pattern(regexp = "yyyy/MM")
	private String billingMonth;

	/**
	 * 契約ステータス
	 */
	@ApiParam(value = "契約ステータス", required = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約ステータス<br />" //
			+ "状態遷移上のライフサイクル状態を表す。", //
			required = false) //
	private LifecycleStatus contractStatus;

	/**
	 * 契約種別
	 */
	@ApiParam(value = "契約種別", required = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約種別<br />" //
			+ "新規, プラン変更, 解約などの契約種別を表す。", //
			required = false)
	private ContractType contractType;

	/**
	 * 商品名称
	 */
	@ApiParam(value = "商品名称", required = false)
	@ApiModelProperty(value = "商品名称", required = false, allowableValues = "range[0,255]")
	private String productName;

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
			+ "10:事業所／部門<br />" //
			+ "11:サービス開始日<br />" //
			+ "12:サービス終了日<br />" //
			+ "13:商品名称<br />" //
			+ "14:担当営業<br />" //
			+ "15:担当営業所属", //
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