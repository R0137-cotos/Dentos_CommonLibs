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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配チェックリスト構成マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "arrangement_checklist_comp_master")
public class ArrangementChecklistCompMaster extends EntityBaseMaster {

	@Id
	@Column(nullable = false)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_checklist_comp_master_seq")
 	@SequenceGenerator(name = "arrangement_checklist_comp_master_seq", sequenceName = "arrangement_checklist_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務タイプマスタ
	 */
	@Column(nullable = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_type_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 2)
	private ArrangementWorkTypeMaster arrangementWorkTypeMaster;

	/**
	 * 汎用チェック事項マスタ
	 */
	@Column(nullable = false)
	@ManyToOne(optional = false)
	@JoinColumn(name = "gp_check_matter_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "汎用チェック事項マスタ", required = true, position = 3)
	private GpCheckMatterMaster gpCheckMatterMaster;

	/**
	 * 表示順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "表示順", required = true, position = 4, allowableValues = "range[0,999]")
	private int displayOrder;

}
