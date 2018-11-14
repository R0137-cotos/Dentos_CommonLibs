package jp.co.ricoh.cotos.commonlib.entity.master;

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
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetContractType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 計上分解構成マスタを表すEntity
 */
@Entity
@Data
@ToString(exclude = { "itemMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "record_decompose_comp_master")
public class RecordDecomposeCompMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_decompose_comp_master_seq")
	@SequenceGenerator(name = "record_decompose_comp_master_seq", sequenceName = "record_decompose_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "計上分解構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
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
	@ApiModelProperty(value = "対象契約種別", required = true, allowableValues = "共通(\"1\"), 新規(\"2\"), プラン変更(\"3\"), 情報変更(\"4\")", example = "1", position = 3)
	private TargetContractType targetContractType;

	/**
	 * 解約フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "解約フラグ", required = true, position = 4)
	private int disengagementFlg;

	/**
	 * 計上分解マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "record_decompose_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "計上分解マスタ", required = true, position = 5)
	private RecordDecomposeMaster recordDecomposeMaster;

	/**
	 * 明細番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "明細番号", required = true, position = 6, allowableValues = "range[0,999]")
	private int seqNumber;

}
