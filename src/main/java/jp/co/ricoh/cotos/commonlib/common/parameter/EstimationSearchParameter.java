package jp.co.ricoh.cotos.commonlib.common.parameter;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiParam;
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
	 * 企業ID
	 */
	@ApiParam(value = "企業ID", required = false)
	private String customerId;

	/**
	 * 変更元契約番号
	 */
	@ApiParam(value = "変更元契約番号", required = false)
	private String originContractNumber;

	/**
	 * サービス識別番号
	 */
	@ApiParam(value = "サービス識別番号", required = false)
	private String serviceIdentificationNumber;

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
	 * 企業名
	 */
	@ApiParam(value = "企業名:部分一致", required = false)
	private String likeSearchCustomerName;

	/**
	 * 事業所名
	 */
	@ApiParam(value = "事業所名:部分一致", required = false)
	private String likeSearchOfficeName;

	/**
	 * 部門名
	 */
	@ApiParam(value = "部門名:部分一致", required = false)
	private String likeSearchDepartmentName;

	/**
	 * 見積更新日(前)
	 */
	@ApiParam(value = "見積更新日(前)", required = false)
	private String estimationUpdatedFrom;

	/**
	 * 見積更新日(後)
	 */
	@ApiParam(value = "見積更新日(後)", required = false)
	private String estimationUpdatedTo;

	/**
	 * 掲示日(前)
	 */
	@ApiParam(value = "掲示日(前)", required = false)
	private String presentationDateFrom;

	/**
	 * 掲示日(後)
	 */
	@ApiParam(value = "掲示日(後)", required = false)
	private String presentationDateTo;

	/**
	 * 見積ステータス
	 */
	@ApiParam(value = "見積ステータス", required = false)
	private String status;

	/**
	 * 見積種別
	 */
	@ApiParam(value = "見積種別", required = false)
	private String estimationDiv;

	/**
	 * 商品名称
	 */
	@ApiParam(value = "商品名称", required = false)
	private String productName;

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
