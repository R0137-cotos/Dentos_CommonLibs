package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "estimation_detail")
public class EstimationDetail extends EntityBase {

	@Id
	@ApiModelProperty(value = "見積明細ID", required = true, position = 1)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	private Estimation estimation;

	/**
	 * 状態
	 */
	@ApiModelProperty(value = "状態", required = true, position = 3)
	private DetailStatus status;

	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積金額
	 */
	@ApiModelProperty(value = "見積金額", required = false, position = 5, allowableValues = "range[0.00,9999999999999999999.99]")
	@Pattern(regexp = "9999999999999999999.99")
	private BigDecimal amountSummary;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要", required = false, position = 6, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	private String extendsParameter;

}
