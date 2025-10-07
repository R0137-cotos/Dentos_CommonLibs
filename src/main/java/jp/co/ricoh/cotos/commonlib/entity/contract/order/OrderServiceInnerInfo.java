package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文サービス固有情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_service_inner_info")
public class OrderServiceInnerInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_service_inner_info_seq")
	@SequenceGenerator(name = "order_service_inner_info_seq", sequenceName = "order_service_inner_info_seq", allocationSize = 1)
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
	 * 固有項目１
	 */
	@Column
	@ApiModelProperty(value = "固有項目１", required = false, position = 2, allowableValues = "range[0,]")
	private String item1;

	/**
	 * 固有項目２
	 */
	@Column
	@ApiModelProperty(value = "固有項目２", required = false, position = 3, allowableValues = "range[0,]")
	private String item2;

	/**
	 * 固有項目３
	 */
	@Column
	@ApiModelProperty(value = "固有項目３", required = false, position = 4, allowableValues = "range[0,]")
	private String item3;

	/**
	 * 固有項目４
	 */
	@Column
	@ApiModelProperty(value = "固有項目４", required = false, position = 5, allowableValues = "range[0,]")
	private String item4;

	/**
	 * 固有項目５
	 */
	@Column
	@ApiModelProperty(value = "固有項目５", required = false, position = 6, allowableValues = "range[0,]")
	private String item5;

	/**
	 * 固有項目６
	 */
	@Column
	@ApiModelProperty(value = "固有項目６", required = false, position = 7, allowableValues = "range[0,]")
	private String item6;

	/**
	 * 固有項目７
	 */
	@Column
	@ApiModelProperty(value = "固有項目７", required = false, position = 8, allowableValues = "range[0,]")
	private String item7;

	/**
	 * 固有項目８
	 */
	@Column
	@ApiModelProperty(value = "固有項目８", required = false, position = 9, allowableValues = "range[0,]")
	private String item8;

	/**
	 * 固有項目９
	 */
	@Column
	@ApiModelProperty(value = "固有項目９", required = false, position = 10, allowableValues = "range[0,]")
	private String item9;

	/**
	 * 固有項目１０
	 */
	@Column
	@ApiModelProperty(value = "固有項目１０", required = false, position = 11, allowableValues = "range[0,]")
	private String item10;

	/**
	 * 固有項目１１
	 */
	@Column
	@ApiModelProperty(value = "固有項目１１", required = false, position = 12, allowableValues = "range[0,]")
	private String item11;

	/**
	 * 固有項目１２
	 */
	@Column
	@ApiModelProperty(value = "固有項目１２", required = false, position = 13, allowableValues = "range[0,]")
	private String item12;

	/**
	 * 固有項目１３
	 */
	@Column
	@ApiModelProperty(value = "固有項目１３", required = false, position = 14, allowableValues = "range[0,]")
	private String item13;

	/**
	 * 固有項目１４
	 */
	@Column
	@ApiModelProperty(value = "固有項目１４", required = false, position = 15, allowableValues = "range[0,]")
	private String item14;

	/**
	 * 固有項目１５
	 */
	@Column
	@ApiModelProperty(value = "固有項目１５", required = false, position = 16, allowableValues = "range[0,]")
	private String item15;

	/**
	 * 固有項目１６
	 */
	@Column
	@ApiModelProperty(value = "固有項目１６", required = false, position = 17, allowableValues = "range[0,]")
	private String item16;

	/**
	 * 固有項目１７
	 */
	@Column
	@ApiModelProperty(value = "固有項目１７", required = false, position = 18, allowableValues = "range[0,]")
	private String item17;

	/**
	 * 固有項目１８
	 */
	@Column
	@ApiModelProperty(value = "固有項目１８", required = false, position = 19, allowableValues = "range[0,]")
	private String item18;

	/**
	 * 固有項目１９
	 */
	@Column
	@ApiModelProperty(value = "固有項目１９", required = false, position = 20, allowableValues = "range[0,]")
	private String item19;

	/**
	 * 固有項目２０
	 */
	@Column
	@ApiModelProperty(value = "固有項目２０", required = false, position = 21, allowableValues = "range[0,]")
	private String item20;

	/**
	 * 固有項目２１
	 */
	@Column
	@ApiModelProperty(value = "固有項目２１", required = false, position = 22, allowableValues = "range[0,]")
	private String item21;

	/**
	 * 固有項目２２
	 */
	@Column
	@ApiModelProperty(value = "固有項目２２", required = false, position = 23, allowableValues = "range[0,]")
	private String item22;

	/**
	 * 固有項目２３
	 */
	@Column
	@ApiModelProperty(value = "固有項目２３", required = false, position = 24, allowableValues = "range[0,]")
	private String item23;

	/**
	 * 固有項目２４
	 */
	@Column
	@ApiModelProperty(value = "固有項目２４", required = false, position = 25, allowableValues = "range[0,]")
	private String item24;

	/**
	 * 固有項目２５
	 */
	@Column
	@ApiModelProperty(value = "固有項目２５", required = false, position = 26, allowableValues = "range[0,]")
	private String item25;

	/**
	 * 固有項目２６
	 */
	@Column
	@ApiModelProperty(value = "固有項目２６", required = false, position = 27, allowableValues = "range[0,]")
	private String item26;

	/**
	 * 固有項目２７
	 */
	@Column
	@ApiModelProperty(value = "固有項目２７", required = false, position = 28, allowableValues = "range[0,]")
	private String item27;

	/**
	 * 固有項目２８
	 */
	@Column
	@ApiModelProperty(value = "固有項目２８", required = false, position = 29, allowableValues = "range[0,]")
	private String item28;

	/**
	 * 固有項目２９
	 */
	@Column
	@ApiModelProperty(value = "固有項目２９", required = false, position = 30, allowableValues = "range[0,]")
	private String item29;

	/**
	 * 固有項目３０
	 */
	@Column
	@ApiModelProperty(value = "固有項目３０", required = false, position = 31, allowableValues = "range[0,]")
	private String item30;

}
