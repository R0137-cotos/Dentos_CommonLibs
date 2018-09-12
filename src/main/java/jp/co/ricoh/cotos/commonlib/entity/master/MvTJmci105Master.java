package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ICI顧客情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci105")
public class MvTJmci105Master {

	/**
	 * 顧客番号
	 */
	@Id
	@ApiModelProperty(value = "顧客番号", required = true, position = 1, allowableValues = "range[0,255]")
	private String customerNumber;

}