package jp.co.ricoh.cotos.commonlib.common.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 処理中タスクを示すDto
 */
@Data
public class WorkInProgresTaskDto {

	/**
	 * 対象文書キー
	 */
	@ApiModelProperty(value = "対象文書キー", required = false, position = 1, allowableValues = "range[0,255]")
	private String targetDocKey;

	/**
	 * 対象文書番号
	 */
	@ApiModelProperty(value = "対象文書番号", required = false, position = 2, allowableValues = "range[0,255]")
	private String targetDocNumber;

	/**
	 * 対象文書画面URL
	 */
	@ApiModelProperty(value = "対象文書画面URL", required = false, position = 3, allowableValues = "range[0,255]")
	private String targetDocUrl;

	/**
	 * 案件名
	 */
	@ApiModelProperty(value = "案件名", required = false, position = 4, allowableValues = "range[0,255]")
	private String taskTitle;

	/**
	 * 作成日
	 */
	@ApiModelProperty(value = "作成日", required = false, position = 5)
	private Date taskCreatedAt;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 6, allowableValues = "range[0,255]")
	private String taskCompanyName;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 7, allowableValues = "range[0,255]")
	private String taskOfficeName;

	/**
	 * 販売店名
	 */
	@ApiModelProperty(value = "販売店名", required = false, position = 8, allowableValues = "range[0,255]")
	private String taskSalesName;

	/**
	 * 担当SA
	 */
	@ApiModelProperty(value = "担当SA", required = false, position = 9, allowableValues = "range[0,255]")
	private String employeeName;
}
