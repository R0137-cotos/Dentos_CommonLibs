package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
	 * 商品拡張項目マスタ
	 */
	@OneToMany(mappedBy = "jsonSchemaMaster")
	@JsonIgnore
	@ApiModelProperty(value = "商品拡張項目マスタ", required = false, position = 2)
	private List<ProductExtendsParameterMaster> productExtendsParameterMasterList;

	/**
	 * JSONスキーマ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "JSONスキーマ", required = true, position = 3)
	@Lob
	private String jsonSchema;

	/**
	 * JSONスキーマ初期値
	 */
	@Column(nullable = true)
	@ApiModelProperty(value = "JSONスキーマ初期値", required = true, position = 4)
	@Lob
	private String jsonSchemaInitial;

}
