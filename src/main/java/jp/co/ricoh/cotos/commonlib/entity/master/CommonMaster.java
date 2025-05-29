package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
	@ApiModelProperty(value = "汎用マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * マスタ名称
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "マスタ名称", required = true, position = 2, allowableValues = "range[0,255]")
	private String articleName;

	/**
	 * カラム名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "カラム名", required = true, position = 3, allowableValues = "range[0,255]")
	private String columnName;

	/**
	 * サービスカテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービスカテゴリ", required = true, allowableValues = "共通(\"0\"), 見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 4)
	private ServiceCategory serviceCategory;

	/**
	 * マスタ説明
	 */
	@ApiModelProperty(value = "マスタ説明", required = false, position = 5, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 汎用マスタ明細リスト
	 */
	@OneToMany(mappedBy = "commonMaster")
	@ApiModelProperty(value = "汎用マスタ明細", required = false, position = 6)
	private List<CommonMasterDetail> commonMasterDetailList;

	/**
	 * 削除フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "削除フラグ", required = true, position = 7, allowableValues = "range[0,1]")
	private String deleteFlg;
}
