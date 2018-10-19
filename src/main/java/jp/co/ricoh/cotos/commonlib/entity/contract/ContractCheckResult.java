package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約チェック結果を表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "contract_check_result")
public class ContractCheckResult extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_check_result_seq")
	@SequenceGenerator(name = "contract_check_result_seq", sequenceName = "contract_check_result_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約チェック結果ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象ライフサイクル状態", required = true, position = 3)
	private LifecycleStatus targetLifecycleStatus;

	/**
	 * チェック事項コード
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 4, allowableValues = "range[0,255]")
	private String checkMatterCode;

	/**
	 * チェック事項文面
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 5, allowableValues = "range[0,255]")
	private String checkMatterText;

	/**
	 * 表示順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "表示順", required = true, position = 6, allowableValues = "range[0,999]")
	private int displayOrder;

	/**
	 * チェック実施者MoM社員ID
	 */
	@ApiModelProperty(value = "チェック実施者MoM社員ID", required = false, position = 7)
	private String checkedUserId;

	/**
	 * チェック実施者氏名
	 */
	@ApiModelProperty(value = "チェック実施者氏名", required = false, position = 8, allowableValues = "range[0,255]")
	private String checkedUserName;

	/**
	 * チェック実施者組織名
	 */
	@ApiModelProperty(value = "チェック実施者組織名", required = false, position = 9, allowableValues = "range[0,255]")
	private String checkedOrgName;

	/**
	 * チェック実施者日時
	 */
	@ApiModelProperty(value = "チェック実施者日時", required = false, position = 10, readOnly = true)
	private Date checkedAt;
}
