package jp.co.ricoh.cotos.commonlib.dto.parameter;

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
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationType;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.WorkflowStatus;
import lombok.Data;

/**
 * 見積を検索するためのキー項目クラスを表します。
 */

@Data
public class EstimationSearchParameter {

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
	 * 見積書番号
	 */
	@ApiParam(value = "見積書番号", required = false)
	@ApiModelProperty(value = "見積書番号", required = false, allowableValues = "range[0,15]")
	private String estimateNumber;

	/**
	 * 見積書番号枝番
	 */
	@ApiParam(value = "見積書番号枝番", required = false)
	@ApiModelProperty(value = "見積書番号枝番", required = false, allowableValues = "range[0,2]")
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
	 * 変更元契約番号
	 */
	@ApiParam(value = "変更元契約番号", required = false)
	@ApiModelProperty(value = "変更元契約番号", required = false, allowableValues = "range[0,15]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@ApiParam(value = "変更元契約番号枝番", required = false)
	@ApiModelProperty(value = "変更元契約番号枝番", required = false, allowableValues = "range[0,2]")
	private String originContractBranchNumber;

	/**
	 * 変更元契約件名
	 */
	@ApiParam(value = "変更元契約件名", required = false)
	@ApiModelProperty(value = "変更元契約件名", required = false, allowableValues = "range[0,255]")
	private String originContractTitle;

	/**
	 * サービス識別番号
	 */
	@ApiParam(value = "サービス識別番号", required = false)
	@ApiModelProperty(value = "サービス識別番号", required = false, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

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
	@ApiParam(value = "担当営業：MoM社員IDを指定", required = false)
	@ApiModelProperty(value = "担当営業<br />" //
			+ "担当営業にはMoM社員IDを指定する。", //
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
	 * 企業ID
	 */
	@ApiParam(value = "企事部ID", required = false)
	@ApiModelProperty(value = "企事部ID<br />" //
			+ "企事部IDにはMoM企事部IDを指定する。", //
			required = false, allowableValues = "range[0,255]") //
	private String customerId;

	/**
	 * 企業名
	 */
	@ApiParam(value = "企業名:部分一致", required = false)
	@ApiModelProperty(value = "企業名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchCustomerName;

	/**
	 * 事業所名
	 */
	@ApiParam(value = "事業所名:部分一致", required = false)
	@ApiModelProperty(value = "事業所名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchOfficeName;

	/**
	 * 部門名
	 */
	@ApiParam(value = "部門名:部分一致", required = false)
	@ApiModelProperty(value = "部門名:部分一致<br />" //
			+ "条件入力時、最低2文字以上の入力とする。", //
			required = false, allowableValues = "range[2,255]") //
	private String likeSearchDepartmentName;

	/**
	 * 見積更新日(前)
	 */
	@ApiParam(value = "見積更新日(前)", required = false)
	@ApiModelProperty(value = "見積更新日(前)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date estimationUpdatedFrom;

	/**
	 * 見積更新日(後)
	 */
	@ApiParam(value = "見積更新日(後)", required = false)
	@ApiModelProperty(value = "見積更新日(後)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date estimationUpdatedTo;

	/**
	 * 掲示日(前)
	 */
	@ApiParam(value = "掲示日(前)", required = false)
	@ApiModelProperty(value = "掲示日(前)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date presentationDateFrom;

	/**
	 * 掲示日(後)
	 */
	@ApiParam(value = "掲示日(後)", required = false)
	@ApiModelProperty(value = "掲示日(後)<br />" //
			+ "日付フォーマット:yyyy/MM/dd", //
			required = false) //
	private Date presentationDateTo;

	/**
	 * 見積ステータス
	 */
	@Enumerated(EnumType.STRING)
	@ApiParam(value = "見積ステータス", required = false)
	@ApiModelProperty(value = "見積ステータス<br />" //
			+ "状態遷移上のワークフロー状態を表す。", //
			required = false) //
	private WorkflowStatus status;

	/**
	 * 見積種別
	 */
	@Enumerated(EnumType.STRING)
	@ApiParam(value = "見積種別", required = false)
	@ApiModelProperty(value = "契約種別<br />" //
			+ "新規, 変更などの見積種別を表す。", //
			required = false) //
	private EstimationType estimationType;

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
			+ "0:見積番号<br />" //
			+ "1:見積種別<br />" //
			+ "2:見積件名<br />" //
			+ "3:見積ステータス<br />" //
			+ "4:案件番号<br />" //
			+ "5:事業所名<br />" //
			+ "6:企業名<br />" //
			+ "7:部門名<br />" //
			+ "8:担当営業<br />" //
			+ "9:商品名称<br />" //
			+ "10:提示日<br />" //
			+ "11:担当支社", //
			required = true, allowableValues = "range[0,11]")
	private int sortColumn;

	/**
	 * ソート順
	 */
	@Enumerated(EnumType.STRING)
	@ApiParam(value = "ソート順", required = true)
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