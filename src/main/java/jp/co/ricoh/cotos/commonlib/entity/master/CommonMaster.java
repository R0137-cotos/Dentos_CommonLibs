package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
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
	@ApiModelProperty(value = "汎用マスタID", required = true, position = 1)
	private long id;

	/**
	 * マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "マスタID", required = true, position = 2, allowableValues = "range[0,255]")
	private String articleCd;

	/**
	 * マスタ名称
	 */
	@ApiModelProperty(value = "マスタ名称", required = false, position = 3, allowableValues = "range[0,255]")
	private String articleName;

	/**
	 * マスタ説明
	 */
	@ApiModelProperty(value = "マスタ説明", required = false, position = 4, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 汎用マスタ明細リスト
	 */
	@OneToMany(mappedBy = "commonMaster")
	@ApiModelProperty(value = "汎用マスタ明細", required = false, position = 5)
	private List<CommonMasterDetail> commonMasterDetailList;
}
