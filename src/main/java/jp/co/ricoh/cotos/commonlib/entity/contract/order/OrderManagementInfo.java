package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文管理情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_management_info")
public class OrderManagementInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_management_info_seq")
	@SequenceGenerator(name = "order_management_info_seq", sequenceName = "order_management_info_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * 注文基本情報ID
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "order_basic_info_id", referencedColumnName = "id")
	@JsonIgnore
	private OrderBasicInfo orderBasicInfo;

	/**
	 * 契約取込状況
	 */
	@Column
	@ApiModelProperty(value = "契約取込状況", required = true, position = 2, allowableValues = "range[0,]")
	private String contractCaptureStatus;

	/**
	 * 契約取込日時
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "契約取込日時", required = false, position = 3)
	private Date contractCaptureAt;

	/**
	 * 処理不可事由
	 */
	@Column
	@ApiModelProperty(value = "処理不可事由", required = false, position = 4, allowableValues = "range[0,]")
	private String unprocessedReason;

	/**
	 * RJ管理番号
	 */
	@Column
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 5, allowableValues = "range[0,]")
	private String rjManageNumber;

	/**
	 * 契約ID
	 */
	@Column
	@ApiModelProperty(value = "契約ID", required = false, position = 6)
	private long contractId;

}
