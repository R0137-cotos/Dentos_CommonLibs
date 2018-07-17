package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 汎用マスタ明細
 */
@Entity
@Data
@Table(name = "common_master_detail")
public class CommonMasterDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_master_detail_seq")
	@SequenceGenerator(name = "common_master_detail_seq", sequenceName = "common_master_detail_seq", allocationSize = 1)
	private long id;

	/**
	 * 汎用マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "common_master_id")
	@JsonIgnore
	private CommonMaster commonMaster;

	/**
	 * コード値
	 */
	@Column(nullable = false)
	private String code;

	/**
	 * コード内容値
	 */
	private String name;

	/**
	 * 設定値1
	 */
	private String column1;

	/**
	 * 設定値2
	 */
	private String column2;

	/**
	 * 設定値3
	 */
	private String column3;

	/**
	 * 設定値4
	 */
	private String column4;

	/**
	 * 設定値5
	 */
	private String column5;

	/**
	 * 有効期間From
	 */
	private Date availablePeriodFrom;

	/**
	 * 有効期間To
	 */
	private Date availablePeriodTo;

	/**
	 * 表示順
	 */
	private Integer dispOrder;

	/**
	 * 削除フラグ
	 */
	@Column(length = 1)
	private boolean deleteFlg;
}
