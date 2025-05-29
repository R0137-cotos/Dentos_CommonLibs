package jp.co.ricoh.cotos.commonlib.entity.master;

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
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * IFSその他機器情報管理マスタを表すEntity
 */
@Entity
@Data
@ToString(exclude = { "productMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "ifs_csv_master")
public class IfsCsvMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ifs_csv_master_seq")
	@SequenceGenerator(name = "ifs_csv_master_seq", sequenceName = "ifs_csv_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "IFSその他機器情報管理マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * 契約Noヘッダ
	 */
	@ApiModelProperty(value = "契約Noヘッダ", required = false, position = 3, allowableValues = "range[0,255]")
	private String contractNoHeader;

	/**
	 * 売上先コード
	 */
	@ApiModelProperty(value = "売上先コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String nBillAcountCode;

	/**
	 * DM送付不可区分
	 */
	@ApiModelProperty(value = "DM送付不可区分", required = false, position = 5, allowableValues = "range[0,255]")
	private String nNotTransDmFlg;

	/**
	 * 移行用キーワード１
	 */
	@ApiModelProperty(value = "移行用キーワード１", required = false, position = 6, allowableValues = "range[0,255]")
	private String nMigarateKeyword1;

	/**
	 * サービス購入フラグ
	 */
	@ApiModelProperty(value = "サービス購入フラグ", required = false, position = 7, allowableValues = "range[0,255]")
	private String nServicePurchFlg;

	/**
	 * オンサイト保守フラグ
	 */
	@ApiModelProperty(value = "オンサイト保守フラグ", required = false, position = 8, allowableValues = "range[0,255]")
	private String nOnSiteMainteFlg;

	/**
	 * インシデント管理フラグ
	 */
	@ApiModelProperty(value = "インシデント管理フラグ", required = false, position = 9, allowableValues = "range[0,255]")
	private String nIncidentManageFlg;

	/**
	 * CE手配完了報告
	 */
	@ApiModelProperty(value = "CE手配完了報告", required = false, position = 10, allowableValues = "range[0,255]")
	private String nCeOrderdFlg;

	/**
	 * CE確定報告
	 */
	@ApiModelProperty(value = "CE確定報告", required = false, position = 11, allowableValues = "range[0,255]")
	private String nCeFixedFlg;

	/**
	 * CE到着予定報告
	 */
	@ApiModelProperty(value = "CE到着予定報告", required = false, position = 12, allowableValues = "range[0,255]")
	private String nCePlannedArriveFlg;

	/**
	 * CE到着報告
	 */
	@ApiModelProperty(value = "CE到着報告", required = false, position = 13, allowableValues = "range[0,255]")
	private String nCeArrivedFlg;

	/**
	 * 機器前到着及び作業開始報告
	 */
	@ApiModelProperty(value = "機器前到着及び作業開始報告", required = false, position = 14, allowableValues = "range[0,255]")
	private String nWorkStartFlg;

	/**
	 * チェックポイント１
	 */
	@Column(name ="n_check_point1_flg")
	@ApiModelProperty(value = "チェックポイント１", required = false, position = 15, allowableValues = "range[0,255]")
	private String nCheckPoint1Flg;

	/**
	 * チェックポイント２
	 */
	@Column(name ="n_check_point2_flg")
	@ApiModelProperty(value = "チェックポイント２", required = false, position = 16, allowableValues = "range[0,255]")
	private String nCheckPoint2Flg;

	/**
	 * チェックポイント３
	 */
	@Column(name ="n_check_point3_flg")
	@ApiModelProperty(value = "チェックポイント３", required = false, position = 17, allowableValues = "range[0,255]")
	private String nCheckPoint3Flg;

	/**
	 * 作業完了報告
	 */
	@ApiModelProperty(value = "作業完了報告", required = false, position = 18, allowableValues = "range[0,255]")
	private String nWorkEndFlg;

	/**
	 * 退出報告
	 */
	@ApiModelProperty(value = "退出報告", required = false, position = 19, allowableValues = "range[0,255]")
	private String nCeCheckOutFlg;

	/**
	 * 部材到着予定報告
	 */
	@ApiModelProperty(value = "部材到着予定報告", required = false, position = 20, allowableValues = "range[0,255]")
	private String nPartsToArriveFlg;

	/**
	 * 部材到着報告
	 */
	@ApiModelProperty(value = "部材到着報告", required = false, position = 21, allowableValues = "range[0,255]")
	private String nPartsArrivedFlg;

	/**
	 * 成果物回収報告
	 */
	@ApiModelProperty(value = "成果物回収報告", required = false, position = 22, allowableValues = "range[0,255]")
	private String nOutComeFlg;

	/**
	 * リモート可不可フラグ
	 */
	@ApiModelProperty(value = "リモート可不可フラグ", required = false, position = 23, allowableValues = "range[0,255]")
	private String nRemoteAllowedFlg;

	/**
	 * 月報作成要否
	 */
	@ApiModelProperty(value = "月報作成要否", required = false, position = 24, allowableValues = "range[0,255]")
	private String nMonthryReportFlg;
}
