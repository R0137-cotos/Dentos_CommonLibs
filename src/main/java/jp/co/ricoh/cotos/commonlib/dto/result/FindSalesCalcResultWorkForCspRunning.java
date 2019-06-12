package jp.co.ricoh.cotos.commonlib.dto.result;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FindSalesCalcResultWorkForCspRunning {
	
	/**
	 * 連番
	 */
	@Id
	private Integer rownum;

	/**
	 * RJ管理番号
	 */
	private String rjManageNumber;
	
	/**
	 * 契約ID
	 */
	private Long contractId;
	
	/**
	 * 契約明細ID
	 */
	private Long contractDetailId;
	
	/**
	 * 商流区分
	 */
	private String commercialFlowDiv;
	
	/**
	 * 費用種別
	 */
	private String costType;
	
	/**
	 * 品種区分
	 */
	private String itemType;
	
	/**
	 * 振替先箇所コード
	 */
	private String ffmDataPtn;
	
	/**
	 * リコー品種コード
	 */
	private String ricohItemCode;
	
	/**
	 * 品種名
	 */
	private String itemContractName;
	
	/**
	 * 得意先コード
	 */
	private String billingCustomerSpCode;
	
	/**
	 * 数量
	 */
	private Integer quantity;
	
	/**
	 * 金額
	 */
	private BigDecimal amountSummary;
	
	/**
	 * RJ仕切価格
	 */
	private BigDecimal rjDividingPrice;
	
	/**
	 * RJ仕入価格
	 */
	private BigDecimal rjPurchasePrice;
	
	/**
	 * 母店売価(接点店仕切)
	 */
	private BigDecimal motherStorePrice;
	
	/**
	 * R原価
	 */
	private BigDecimal rCost;
	
	/**
	 * サービス開始日
	 */
	private Date serviceTermStart;
	
	/**
	 * サービス終了日
	 */
	private Date serviceTermEnd;

	/**
	 * 振替先課所コード
	 */
	private String transToServiceOrgName;
}
