package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 見積チェック結果を表すEntity
 */
@Entity
@Data
@Table(name = "estimation_check_result")
public class EstimationCheckResult {

	@Id
	@ApiModelProperty(value = "見積チェック結果ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	private Estimation estimation;

	/**
	 * チェック事項コード
	 */
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 3)
	private String checkmatterCode;

	/**
	 * チェック事項文面
	 */
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 4)
	private String checkmatterText;

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
	 * チェック実施者日時
	 */
	@ApiModelProperty(value = "チェック実施者日時", required = false, position = 9, readOnly = true)
	private Date checkedAt;

}
