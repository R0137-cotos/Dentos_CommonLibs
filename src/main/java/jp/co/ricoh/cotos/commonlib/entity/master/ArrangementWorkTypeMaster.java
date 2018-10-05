package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手配業務タイプマスタを表すEntity
 */
@Entity
@Data
@Table(name = "arrangement_work_type_master")
public class ArrangementWorkTypeMaster {

	@Id
	@ApiModelProperty(value = "手配業務タイプマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務タイプ名
	 */
	@ApiModelProperty(value = "手配業務タイプ名", required = true, position = 2, allowableValues = "range[0,255]")
	private String name;;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 3, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 手配承認ルートグループ
	 */
	@ManyToOne
	@JoinColumn(name = "arrange_approval_route_grp_id", referencedColumnName = "id")
	@ApiModelProperty(value = "品種マスタ", required = false, position = 4)
	private ApprovalRouteGrpMaster approvalRouteGrpMaster;

	/**
	 * 手配業務構成マスタ
	 */
	@OneToMany(mappedBy = "arrangementWorkTypeMaster")
	@ApiModelProperty(value = "手配業務タイプマスタ", required = false, position = 5)
	private List<ArrangementWorkCompMaster> arrangementWorkCompMasterList;

	/**
	 * 手配チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "arrangementWorkTypeMaster")
	@ApiModelProperty(value = "手配チェックリスト構成マスタ", required = false, position = 6)
	private List<ArrangementChecklistCompMaster> arrangementChecklistCompMasterList;

}
