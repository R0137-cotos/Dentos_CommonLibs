package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 直送販売店情報マスタ
 */
@Entity
@Data
@Table(name = "v_direct_delivery_dealer_info")
public class VDirectDeliveryDealerInfoMaster {

	@Embeddable
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 販売店識別子
		 */
		@ApiModelProperty(value = "販売店識別子", required = true, position = 1)
		private String dealerDiscrimCd;

		/**
		 * URLパターン
		 */
		@ApiModelProperty(value = "デポコード", required = true, position = 2)
		private String dpCd;

	}

	@EmbeddedId
	private Id id;

	@Column
	@ApiModelProperty(value = "販売店名(漢字)", required = false, position = 3)
	private String dlPrCommDistDealerName;

	@Column
	@ApiModelProperty(value = "販売店名(カナ)", required = false, position = 4)
	private String dlPrCommDistDealerNameKana;

	@Column
	@ApiModelProperty(value = "デポ名(漢字)", required = false, position = 5)
	private String dlDpName;

	@Column
	@ApiModelProperty(value = "デポ名(カナ)", required = false, position = 6)
	private String dlDpNameKana;

	@Column
	@ApiModelProperty(value = "売上情報送信年月日", required = false, position = 7)
	private String adSalesSendDate; //yyyyMMddのフォーマットの文字列で設定されている

}
