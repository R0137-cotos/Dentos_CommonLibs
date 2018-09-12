package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 企事部マスタ
 */
@Entity
@Data
@Table(name = "v_kjb_master")
public class KjbMaster {

	public enum KjbSetDiv {
		企事, 企事部;
	}

	/**
	 * MoM顧客システム連携ID
	 */
	@Id
	@ApiModelProperty(value = "MoM顧客システム連携ID", required = true, position = 1, allowableValues = "range[0,15]")
	private String mclMomRelId;

	/**
	 * MoM顧客企事部ID
	 */
	@ApiModelProperty(value = "MoM顧客企事部ID", required = false, position = 2, allowableValues = "range[0,15]")
	private String mclMomKjbId;

}
