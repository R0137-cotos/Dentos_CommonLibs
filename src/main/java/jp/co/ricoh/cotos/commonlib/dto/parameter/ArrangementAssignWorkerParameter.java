package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import lombok.Data;

/**
 * 手配担当作業者を設定するためのキー項目クラスを表します。
 */

@Data
public class ArrangementAssignWorkerParameter {

	/**
	 * 手配業務リスト
	 */
	@ApiModelProperty(value = "手配業務リスト", required = true, position = 1)
	private List<ArrangementWork> arrangementWorkList;

	/**
	 * MoM社員ID（手配担当作業者）
	 */
	@ApiModelProperty(value = "MoM社員ID", required = true, position = 2, allowableValues = "range[0,24]")
	private String employeeId;
}
