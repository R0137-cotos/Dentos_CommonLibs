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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "estimation_detail")
public class EstimationDetail extends EntityBase {
	public enum RunningSummaryDiv {

		年額, 月額;// TODO ERD、汎用コード値資料に記載がないため正しいか確認

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<RunningSummaryDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

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
	 * 数量
	 */
	@ApiModelProperty(value = "数量", required = true, position = 3, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * イニシャル見積金額
	 */
	@ApiModelProperty(value = "イニシャル見積金額", required = false, position = 4, allowableValues = "range[0.00,9999999999999999999.99]")
	@Pattern(regexp = "9999999999999999999.99")
	private BigDecimal initialAmountSummary;

	/**
	 * イニシャル粗利率
	 */
	@ApiModelProperty(value = "イニシャル粗利率", required = false, position = 5, allowableValues = "range[0.00,99999.99]")
	@Pattern(regexp = "99999.99")
	private BigDecimal initialGrossProfitRate;

	/**
	 * ランニングサマリ対象区分
	 */
	@ApiModelProperty(value = "ランニングサマリ対象区分", required = false, position = 6)
	private RunningSummaryDiv runningSummaryDiv;

	/**
	 * ランニングサマリ見積金額
	 */
	@ApiModelProperty(value = "ランニングサマリ見積金額", required = false, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	@Pattern(regexp = "9999999999999999999.99")
	private BigDecimal runningAmountSummary;

	/**
	 * ランニング粗利率
	 */
	@ApiModelProperty(value = "ランニング粗利率", required = false, position = 8, allowableValues = "range[0.00,99999.99]")
	@Pattern(regexp = "99999.99")
	private BigDecimal runningGrossProfitRate;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要", required = false, position = 9, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 10)
	private String extendsParameter;

	@OneToOne(optional = false)
	@ApiModelProperty(value = "品種(見積用)", required = true, position = 11)
	private ItemEst itemEst;

}
