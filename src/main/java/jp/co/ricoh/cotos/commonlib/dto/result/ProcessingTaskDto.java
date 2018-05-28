package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TOP画面の処理中タスク一覧に表示される、画面の操作者がボールを持つタスクを保持するDto
 */
@Data
public class ProcessingTaskDto {

	/** 作成中見積一覧 */
	@ApiModelProperty(value = "作成中見積一覧", required = false, position = 1)
	private List<WorkInProgresTaskDto> estimationWIPList;

	/** 作成中契約一覧 */
	@ApiModelProperty(value = "作成中契約一覧", required = false, position = 2)
	private List<WorkInProgresTaskDto> contractWIPList;

	/** 手配処理中一覧 */
	@ApiModelProperty(value = "手配処理中一覧", required = false, position = 3)
	private List<WorkInProgresTaskDto> arrangeWIPList;

	/**
	 * コンストラクタ
	 */
	public ProcessingTaskDto() {
		estimationWIPList = new ArrayList<>();
		contractWIPList = new ArrayList<>();
		arrangeWIPList = new ArrayList<>();
	}
}
