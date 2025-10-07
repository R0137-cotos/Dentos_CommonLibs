package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RJ人事組織マスタ(全階層)
 */
@Entity
@Data
@Table(name = "v_pic_affiliate_master_full")
public class VPicAffiliateMasterFull {

	/**
	 * MoM組織ID
	 */
	@Id
	@ApiModelProperty(value = "MoM組織ID", required = true, position = 1, allowableValues = "range[0,7]")
	private String orgId;

	/**
	 * MoM組織名称
	 */
	@ApiModelProperty(value = "MoM組織名称", required = false, position = 2, allowableValues = "range[0,1000]")
	private String orgName;

	/**
	 * 組織階層レベル
	 */
	@ApiModelProperty(value = "組織階層レベル", required = false, position = 3, allowableValues = "range[0,9999999999]")
	private Integer orgHierarchyLevel;

	/**
	 * 上位MoM組織ID
	 */
	@ApiModelProperty(value = "上位MoM組織ID", required = false, position = 4, allowableValues = "range[0,7]")
	private String highOrgId;

	/**
	 * 上位MoM組織名称
	 */
	@ApiModelProperty(value = "上位MoM組織名称", required = false, position = 5, allowableValues = "range[0,1000]")
	private String highOrgName;
}