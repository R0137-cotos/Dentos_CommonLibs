package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品拡張項目マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_extends_parameter_master")
public class ProductExtendsParameterMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_extends_parameter_master_seq")
	@SequenceGenerator(name = "product_extends_parameter_master_seq", sequenceName = "product_extends_parameter_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品拡張項目マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * JSONスキーママスタ
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "json_schema_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "JSONスキーママスタ", required = true, position = 3)
	private JsonSchemaMaster jsonSchemaMaster;

	/**
	 * 登録区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "登録区分", required = true, position = 4, allowableValues = "range[0,255]")
	private String registryType;

}