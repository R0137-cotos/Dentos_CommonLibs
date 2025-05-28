package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 固定帳票情報マスタ
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "ledger_master")
public class LedgerMaster extends EntityBaseMaster {

	/**
	 * 固定帳票情報マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ledger_master_seq")
	@SequenceGenerator(name = "ledger_master_seq", sequenceName = "ledger_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "固定帳票情報マスタID", required = true, position = 1)
	private long id;

	/**
	 * 商品マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 3)
	private long productMasterId;

	/**
	 * ファイル名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "ファイル名", required = true, position = 4, allowableValues = "range[0,]")
	private String fileName;

	/**
	 * ファイルパス
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "ファイルパス", required = true, position = 5, allowableValues = "range[0,]")
	private String filePath;

}
