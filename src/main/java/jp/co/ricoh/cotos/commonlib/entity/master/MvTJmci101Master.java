package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MoM請求売上先サイト情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci101")
public class MvTJmci101Master {

	/**
	 * サイト番号
	 */
	@Id
	@ApiModelProperty(value = "サイト番号", required = true, position = 1, allowableValues = "range[0,255]")
	private String customerSiteNumber;

}