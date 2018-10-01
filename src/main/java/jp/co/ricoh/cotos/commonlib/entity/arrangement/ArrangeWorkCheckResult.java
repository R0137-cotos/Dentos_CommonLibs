package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務Entity作成時のチェック結果を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrange_work_check_result")
public class ArrangeWorkCheckResult extends EntityBase {

	@Id
	@ApiModelProperty(value = "手配業務チェック結果ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 手配業務
	 */
	@ManyToOne
	@JoinColumn(name = "arrangement_work_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	private ArrangementWork arrangementWork;

	/**
	 * チェック事項コード
	 */
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 3, allowableValues = "range[0,255]")
	private String checkMatterCode;

	/**
	 * チェック事項文面
	 */
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 4, allowableValues = "range[0,255]")
	private String checkMatterText;

	/**
	 * 表示順
	 */
	@ApiModelProperty(value = "表示順", required = true, position = 5, allowableValues = "range[0,999]")
	private int displayOrder;

	/**
	 * チェック実施者
	 */
	@ApiModelProperty(value = "チェック実施者", required = false, position = 6, allowableValues = "range[0,255]")
	private String checkedUser;

	/**
	 * チェック実施者氏名
	 */
	@ApiModelProperty(value = "チェック実施者氏名", required = false, position = 7, allowableValues = "range[0,255]")
	private String checkedUserName;

	/**
	 * チェック実施者組織名
	 */
	@ApiModelProperty(value = "チェック実施者組織名", required = false, position = 8, allowableValues = "range[0,255]")
	private String checkedOrgName;

	/**
	 * チェック実施日時
	 */
	@ApiModelProperty(value = "チェック実施日時", required = false, position = 9, readOnly = true)
	private Date checkedAt;

}
