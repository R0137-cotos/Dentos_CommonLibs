package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務タイプマスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "arrangement_work_type_master")
public class ArrangementWorkTypeMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_type_master_seq")
	@SequenceGenerator(name = "arrangement_work_type_master_seq", sequenceName = "arrangement_work_type_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務タイプマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務タイプ名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "手配業務タイプ名", required = true, position = 2, allowableValues = "range[0,255]")
	private String arrangementWorkName;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 3, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 承認ルートグループマスタ
	 */
	@ManyToOne
	@JoinColumn(name = "arrangement_approval_route_grp_id", referencedColumnName = "id")
	@ApiModelProperty(value = "承認ルートグループマスタ", required = false, position = 4)
	private ApprovalRouteGrpMaster approvalRouteGrpMaster;

	/**
	 * 手配業務構成マスタ
	 */
	@OneToMany(mappedBy = "arrangementWorkTypeMaster")
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 5)
	private List<ArrangementWorkCompMaster> arrangementWorkCompMasterList;

	/**
	 * 手配チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "arrangementWorkTypeMaster")
	@ApiModelProperty(value = "手配チェックリスト構成マスタ", required = true, position = 6)
	private List<ArrangementChecklistCompMaster> arrangementChecklistCompMasterList;

}
