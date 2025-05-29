package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetContractType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 手配業務構成マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = { "itemMaster" })
@Table(name = "arrangement_work_comp_master")
public class ArrangementWorkCompMaster extends EntityBaseMaster {

	/**
	 * 手配業務構成マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_comp_master_seq")
	@SequenceGenerator(name = "arrangement_work_comp_master_seq", sequenceName = "arrangement_work_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "品種マスタ", required = true, position = 2)
	private ItemMaster itemMaster;

	/**
	 * 対象契約種別
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象契約種別", required = true, allowableValues = "共通(\"1\"), 新規(\"2\"), 契約変更(\"3\"), 情報変更(\"4\")", example = "1", position = 3)
	private TargetContractType targetContractType;

	/**
	 * 解約フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "解約フラグ", required = true, position = 4, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * 手配業務タイプマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_type_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 5)
	private ArrangementWorkTypeMaster arrangementWorkTypeMaster;

	/**
	 * 明細番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "明細番号", required = true, position = 6, allowableValues = "range[0,999]")
	private int seqNumber;

	/**
	 * 明細状態
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "明細状態", required = false, position = 7, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "1")
	private DetailStatus detailStatus;

}
