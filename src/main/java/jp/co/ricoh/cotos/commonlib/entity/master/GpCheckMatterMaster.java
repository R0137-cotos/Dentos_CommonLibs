package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 汎用チェック事項マスタを表すEntity
 */

@Entity
@Data
@Table(name = "gp_check_matter_master")
public class GpCheckMatterMaster {

	@Id
	@ApiModelProperty(value = "汎用チェック事項マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * チェック事項コード
	 */
	@ApiModelProperty(value = "チェック事項コード", required = true, position = 2, allowableValues = "range[0,255]")
	private String checkMatterCode;

	/**
	 * チェック事項文面
	 */
	@ApiModelProperty(value = "チェック事項文面", required = true, position = 3, allowableValues = "range[0,255]")
	private String checkMatterText;

	/**
	 * 手配チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "arrangeChecklistCompMaster")
	@ApiModelProperty(value = "手配チェックリスト構成マスタ", required = false, position = 4)
	private List<ArrangeChecklistCompMaster> arrangeWorkCompMasterList;

	/**
	 * チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "checklistCompMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 5)
	private List<ChecklistCompMaster> checklistCompMasterList;

}
