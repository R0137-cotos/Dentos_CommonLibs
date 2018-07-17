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

import lombok.Data;

/**
 * 汎用マスタ
 */
@Entity
@Data
@Table(name = "common_master")
public class CommonMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_master_seq")
	@SequenceGenerator(name = "common_master_seq", sequenceName = "common_master_seq", allocationSize = 1)
	private long id;

	/**
	 * マスタID
	 */
	@Column(nullable = false)
	private String itemId;

	/**
	 * マスタ名称
	 */
	private String itemName;

	/**
	 * マスタ説明
	 */
	private String description;

	/**
	 * 汎用マスタ明細リスト
	 */
	@OneToMany(mappedBy = "commonMaster")
	private List<CommonMasterDetail> commonMasterDetailList;
}
