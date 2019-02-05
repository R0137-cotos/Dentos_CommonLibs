package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.LifecycleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積チェック結果を表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "estimation_check_result")
public class EstimationCheckResult extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_check_result_seq")
	@SequenceGenerator(name = "estimation_check_result_seq", sequenceName = "estimation_check_result_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積チェック結果ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "対象ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), 受注(\"3\"), 失注(\"4\"), 破棄(\"5\")", example = "1", position = 3)
	private LifecycleStatus targetLifecycleStatus;

	/**
	 * チェック事項コード
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 4, allowableValues = "range[0,255]")
	private String checkMatterCode;

	/**
	 * チェック事項文面
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 5, allowableValues = "range[0,255]")
	private String checkMatterText;

	/**
	 * 表示順
	 */
	@Max(999)
	@OrderBy("desc")
	@Column(nullable = false)
	@ApiModelProperty(value = "表示順", required = true, position = 6, allowableValues = "range[0,999]")
	private int displayOrder;

	/**
	 * チェック実施者
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "チェック実施者", required = false, position = 7, allowableValues = "range[0,255]")
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedAt;
}
