package jp.co.ricoh.cotos.commonlib.entity.contract;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品（契約用）を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ProductContractListener.class)
@Data
@Table(name = "product_contract")
public class ProductContract extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_contract_seq")
	@SequenceGenerator(name = "product_contract_seq", sequenceName = "product_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 商品マスタID
	 */
	@Min(0)
	@Column(nullable = false)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 商品名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品名(作成時不要)", required = true, position = 3, allowableValues = "range[0,255]", readOnly = true)
	private String productContractName;

	/**
	 * 代表品種マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "代表品種マスタID", required = false, position = 4, allowableValues = "range[0,9223372036854775807]")
	private Long repItemMasterId;

	/**
	 * サービス識別番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービス識別番号(作成時不要)", required = true, position = 5, allowableValues = "range[0,255]", readOnly = true)
	private String serviceIdentNumber;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 6)
	private Contract contract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	@Lob
	private String extendsParameter;
	
	/**
	 * 拡張項目繰返
	 */
	@ApiModelProperty(value = "拡張項目繰返", required = false, position = 8)
	@Lob
	private String extendsParameterIterance;

}
