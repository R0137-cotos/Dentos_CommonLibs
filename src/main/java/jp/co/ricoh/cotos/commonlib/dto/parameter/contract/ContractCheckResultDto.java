package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractCheckResultDto extends DtoBase {

	/**
	 * 対象ライフサイクル状態
	 */
	@NotNull
	@ApiModelProperty(value = "対象ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), キャンセル手続き中(\"3\"), 破棄(\"4\"), 予定日待ち(\"5\"), 締結中(\"6\"), 解約手続き中(\"7\"), 解約予定日待ち(\"8\"), 解約(\"9\"), 旧契約(\"10\")", example = "1", position = 3)
	private LifecycleStatus targetLifecycleStatus;

	/**
	 * チェック事項コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 4, allowableValues = "range[0,255]")
	private String checkMatterCode;

	/**
	 * チェック事項文面
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 5, allowableValues = "range[0,255]")
	private String checkMatterText;

	/**
	 * 表示順
	 */
	@Min(0)
	@Max(999)
	@ApiModelProperty(value = "表示順", required = true, position = 6, allowableValues = "range[0,999]")
	private int displayOrder;

	/**
	 * チェック実施者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "チェック実施者MoM社員ID", required = false, position = 7, allowableValues = "range[0,255]")
	private String checkedUserId;

	/**
	 * チェック実施者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "チェック実施者氏名", required = false, position = 8, allowableValues = "range[0,255]")
	private String checkedUserName;

	/**
	 * チェック実施者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "チェック実施者組織名", required = false, position = 9, allowableValues = "range[0,255]")
	private String checkedOrgName;

	/**
	 * チェック実施日時
	 */
	@ApiModelProperty(value = "チェック実施日時", required = false, position = 10)
	private Date checkedAt;
}
