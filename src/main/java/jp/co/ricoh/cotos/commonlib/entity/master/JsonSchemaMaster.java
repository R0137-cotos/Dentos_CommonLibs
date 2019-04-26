package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * JSONスキーママスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "json_schema_master")
public class JsonSchemaMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "json_schema_master_seq")
	@SequenceGenerator(name = "json_schema_master_seq", sequenceName = "json_schema_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "JSONスキーママスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
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
	 * アプリケーションID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 3, allowableValues = "range[0,255]")
	private String appId;

	/**
	 * JSONスキーマ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "JSONスキーマ", required = true, position = 4)
	@Lob
	private String jsonSchema;
}
