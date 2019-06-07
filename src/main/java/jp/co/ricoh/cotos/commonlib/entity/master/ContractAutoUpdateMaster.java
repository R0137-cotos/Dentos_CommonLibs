package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約自動更新マスタ
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_auto_update_master")
public class ContractAutoUpdateMaster extends EntityBaseMaster {

	/**
	 * 契約自動更新マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_auto_update_master_seq")
	@SequenceGenerator(name = "contract_auto_update_master_seq", sequenceName = "contract_auto_update_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約自動更新マスタID", required = true, position = 1)
	private long id;

	/**
	 * 品種マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 2)
	private long itemMasterId;

	/**
	 * 契約更新方式区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "契約更新方式区分", required = true, position = 3, allowableValues = "range[0,]")
	private String contractUpdateType;

	/**
	 * 手配情報作成区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "手配情報作成区分", required = true, position = 4, allowableValues = "range[0,]")
	private String arrangementCreateType;

	/**
	 * サービス開始日更新区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービス開始日更新区分", required = true, position = 5, allowableValues = "range[0,]")
	private String serviceTermStartType;

	/**
	 * サービス終了日更新区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービス終了日更新区分", required = true, position = 6, allowableValues = "range[0,]")
	private String serviceTermEndType;

}
