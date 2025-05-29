package jp.co.ricoh.cotos.commonlib.dto.result;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;

@Data
@Entity
public class SalesCalcResultWorkForCspRunning {

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
	 * 契約No
	 */
	private String contractNumber;

	/**
	 * お問い合わせ番号
	 */
	private String contactNo;

	/**
	 * 注文番号
	 */
	private String orderNo;

	/**
	 * 商流区分
	 */
	private String commercialFlowDiv;

	/**
	 * 費用種別
	 */
	private CostType costType;

	/**
	 * 品種区分
	 */
	private ItemType itemType;

	/**
	 * 作成データパターン
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
	 * 原価
	 */
	private BigDecimal cost;

	/**
	 * 単価
	 */
	private BigDecimal unitPrice;

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
	private String transToServiceOrgCode;

	/**
	 * 恒久契約識別番号
	 */
	private String immutableContIdentNumber;

	/**
	 * 企業名（カナ）
	 */
	private String companyNameKana;
}
