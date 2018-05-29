package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_detail")
public class ContractDetail extends EntityBase {
	public enum RunningSummaryDiv {

		年額, 月額;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_detail_seq")
	@SequenceGenerator(name = "contract_detail_seq", sequenceName = "contract_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約明細ID", required = true, position = 1)
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Contract contract;

	@OneToOne(optional = false)
	@JoinColumn(name = "itemConId")
	@ApiModelProperty(value = "品種", required = false, position = 2)
	private ItemCon itemCon;

	/**
	 * イニシャル見積金額
	 */
	@ApiModelProperty(value = "イニシャル見積金額", required = false, position = 3)
	private Long initialAmountSummary;

	/**
	 * イニシャル粗利率
	 */
	@ApiModelProperty(value = "イニシャル粗利率", required = false, position = 4)
	private Long initialGrossProfitRate;

	/**
	 * ランニングサマリ対象区分
	 */
	@ApiModelProperty(value = "ランニングサマリ対象区分", required = false, position = 5)
	@Enumerated(EnumType.STRING)
	private RunningSummaryDiv runningSummaryDiv;

	/**
	 * ランニングサマリ見積金額
	 */
	@ApiModelProperty(value = "ランニングサマリ見積金額", required = false, position = 6)
	private Long runningAmountSummary;

	/**
	 * ランニング粗利率
	 */
	@ApiModelProperty(value = "ランニング粗利率", required = false, position = 7)
	private Long runningGrossProfitRate;

	/**
	 * 摘要 abstractはjavaの抽象クラスと被るためdetailAbstractとした
	 */
	@ApiModelProperty(value = "摘要", required = false, position = 8, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * jsonを入れてください。
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@ApiModelProperty(value = "拡張項目", required = false, position = 9, allowableValues = "range[0,255]")
	private String extendsParameter;
}