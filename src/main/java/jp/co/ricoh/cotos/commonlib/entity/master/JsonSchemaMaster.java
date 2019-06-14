package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	@OneToMany(mappedBy = "jsonSchemaMaster")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	@JsonIgnore
	private List<ProductMaster> productMasterList;

	/**
	 * JSONスキーマ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "JSONスキーマ", required = true, position = 4)
	@Lob
	private String jsonSchema;
}
