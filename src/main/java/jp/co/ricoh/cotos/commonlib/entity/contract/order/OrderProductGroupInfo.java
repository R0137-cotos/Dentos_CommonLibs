package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品グループ情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_product_group_info")
public class OrderProductGroupInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_group_info_seq")
	@SequenceGenerator(name = "order_product_group_info_seq", sequenceName = "order_product_group_info_seq", allocationSize = 1)
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
	 * 商品グループコード
	 */
	@Column
	@ApiModelProperty(value = "商品グループコード", required = false, position = 2, allowableValues = "range[0,]")
	private String productGroupCd;

	/**
	 * 商品グループ名
	 */
	@Column
	@ApiModelProperty(value = "商品グループ名", required = false, position = 3, allowableValues = "range[0,]")
	private String productGroupName;

}
