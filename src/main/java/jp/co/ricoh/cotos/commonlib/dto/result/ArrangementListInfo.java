package jp.co.ricoh.cotos.commonlib.dto.result;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手配をリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class ArrangementListInfo {
	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 契約ID
		 */
		@ApiModelProperty(value = "契約ID", required = true, position = 1)
		private long contractId;

		/**
		 * 手配ID
		 */
		@ApiModelProperty(value = "手配ID", required = true, position = 2)
		private long arrangementId;

		/**
		 * 手配業務ID
		 */
		@ApiModelProperty(value = "手配業務ID", required = true, position = 3)
		private long arrangementWorkId;
	}

	@EmbeddedId
	private Id id;

	/**
	 * 契約番号
	 */
	@ApiModelProperty(value = "契約番号", required = false, position = 4, allowableValues = "range[0,255]")
	private String contractNumber;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String serviceIdentificationNumber;

	/**
	 * 契約状態 TODO： 仕様が決まり次第実装すること
	 */

	/**
	 * 契約種別
	 */
	@ApiModelProperty(value = "契約種別", required = false, position = 6)
	private String contractType;

	/**
	 * 見積書番号
	 */
	@ApiModelProperty(value = "見積書番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String estimateNumber;

	/**
	 * 担当営業氏名
	 */
	@ApiModelProperty(value = "担当営業氏名", required = false, position = 8, allowableValues = "range[0,255]")
	private String picEmptxName;

	/**
	 * 担当支社
	 */
	@ApiModelProperty(value = "担当支社", required = false, position = 9, allowableValues = "range[0,255]")
	private String picAffiliateName;

	/**
	 * サービス開始日
	 */
	@ApiModelProperty(value = "サービス開始日", required = false, position = 10)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@ApiModelProperty(value = "サービス終了日", required = false, position = 11)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceTermEnd;

	/**
	 * お客様顧客名
	 */
	@ApiModelProperty(value = "お客様顧客名", required = false, position = 12, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 商品名(商品メニュー)
	 */
	@ApiModelProperty(value = "商品名(商品メニュー)", required = false, position = 13, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 手配作成日時
	 */
	@ApiModelProperty(value = "手配作成日時", required = false, position = 14)
	private Date arrangementCreateDate;

	/**
	 * 手配ステータス
	 */
	// TODO Arrangementクラスで定義しているWorkflowStatusを参照する
	// @Enumerated(EnumType.STRING)
	// @ApiModelProperty(value = "手配ステータス", required = false, position = 15)
	// private ArrangementStatus arrangementStatus;

	/**
	 * 手配業務
	 */
	@ApiModelProperty(value = "手配業務", required = false, position = 16, allowableValues = "range[0,255]")
	private String arrangementName;

	/**
	 * 手配業務担当者
	 */
	@ApiModelProperty(value = "手配業務担当者", required = false, position = 17, allowableValues = "range[0,255]")
	private String workUserName;

	/**
	 * 手配業務ステータス
	 */
	// TODO ArrangementWorkクラスで定義しているWorkflowStatusを参照する
	// @Enumerated(EnumType.STRING)
	// @ApiModelProperty(value = "手配業務ステータス", required = false, position = 18)
	// private ArrangementWorkStatus arrangementWorkStatus;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}
