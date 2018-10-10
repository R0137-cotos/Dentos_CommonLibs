package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetContractType;
import lombok.Data;

/**
 * 手配業務構成マスタを表すEntity
 */
@Entity
@Data
@Table(name = "arrangement_work_comp_master")
public class ArrangementWorkCompMaster {

	@Id
	@ApiModelProperty(value = "手配業務マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
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
	@ApiModelProperty(value = "解約フラグ", required = true, position = 4, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * 手配業務タイプマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrange_work_type_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 5)
	private ArrangementWorkTypeMaster arrangementWorkTypeMaster;

	/**
	 * 明細番号
	 */
	@ApiModelProperty(value = "明細番号", required = true, position = 6, allowableValues = "range[0,999]")
	private int seqNumber;

}
