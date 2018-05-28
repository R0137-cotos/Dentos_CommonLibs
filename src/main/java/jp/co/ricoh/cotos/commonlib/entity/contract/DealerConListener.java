package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.KjbMasterRepository;

@Component
public class DealerConListener {

	private static KjbMasterRepository kjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(KjbMasterRepository kjbMasterRepository) {
		DealerConListener.kjbMasterRepository = kjbMasterRepository;
	}

	/**
	 * 企事部マスタ情報を販売店トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsContractDealerFields(DealerCon dealerCon) {
		KjbMaster kjbMaster = kjbMasterRepository.findByMclMomRelId(dealerCon.getKjbMaster().getMclMomRelId());

		if (kjbMaster == null) {
			throw new IllegalArgumentException("Target Not Found By MclMomRelId : KjbMaster " + "MclMomRelId:" + dealerCon.getKjbMaster().getMclMomRelId());
		}

		// 結合して表示するものを設定
		dealerCon.setDealerName(this.convertJoinedDealerName(kjbMaster));
		dealerCon.setAddress(this.convertJoinedAddress(kjbMaster));

		dealerCon.setKjbMaster(kjbMaster);
		dealerCon.setPostNumber(kjbMaster.getJgsJgsPostNum());
		dealerCon.setOrgPhoneNumber(kjbMaster.getKgyKgyTelNum());
	}

	private String convertJoinedDealerName(KjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		return sb.toString();
	}

	private String convertJoinedAddress(KjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsAzatusyoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsBantiNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsGoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsFlorNm(), StringUtils.EMPTY));

		return sb.toString();
	}
}
