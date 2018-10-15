package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 汎用マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "common_master")
public class CommonMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_master_seq")
	@SequenceGenerator(name = "common_master_seq", sequenceName = "common_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "汎用マスタID", required = true, position = 1,  allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * マスタ名称
	 */
	@ApiModelProperty(value = "マスタ名称", required = true, position = 2, allowableValues = "range[0,255]")
	private String articleName;

	/**
	 * カラム名
	 */
	@ApiModelProperty(value = "カラム名", required = false, position = 3, allowableValues = "range[0,255]")
	private String columnName;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 5)
	private ServiceCategory serviceCategory;

	/**
	 * マスタ説明
	 */
	@ApiModelProperty(value = "マスタ説明", required = false, position = 6, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 汎用マスタ明細リスト
	 */
	@OneToMany(mappedBy = "commonMaster")
	@ApiModelProperty(value = "汎用マスタ明細", required = true, position = 7)
	private List<CommonMasterDetail> commonMasterDetailList;
}
