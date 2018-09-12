package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手配チェックリスト構成マスタを表すEntity
 */
@Entity
@Data
@Table(name = "arrange_checklist_comp_master")
public class ArrangeChecklistCompMaster {

	@Id
	@ApiModelProperty(value = "手配チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務タイプマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrange_work_type_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 2)
	private ArrangeWorkTypeMaster arrangeWorkTypeMaster;

	/**
	 * 汎用チェック事項マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "gp_check_matter_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "汎用チェック事項マスタ", required = true, position = 3)
	private GpCheckMatterMaster gpCheckMatterMaster;

	/**
	 * 表示順
	 */
	@ApiModelProperty(value = "表示順", required = true, position = 4, allowableValues = "range[0,999]")
	private int displayOrder;

}
