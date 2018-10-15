package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetContractType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 計上分解構成マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "record_decompose_comp_master")
public class RecordDecomposeCompMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "計上分解構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "品種マスタ", required = true, position = 2)
	private ItemMaster itemMaster;

	/**
	 * 対象契約種別
	 */
	@ApiModelProperty(value = "対象契約種別", required = true, position = 3)
	private TargetContractType targetContractType;

	/**
	 * 解約フラグ
	 */
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
	@ApiModelProperty(value = "明細番号", required = true, position = 6, allowableValues = "range[0,999]")
	private int seqNumber;

}
