package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.common.repository.KjbMasterRepository;

@Component
public class DealerListener {

	private static KjbMasterRepository kjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(KjbMasterRepository kjbMasterRepository) {
		DealerListener.kjbMasterRepository = kjbMasterRepository;
	}

	/**
	 * 企事部マスタ情報を販売店トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationDealerFields(Dealer dealer) {
		KjbMaster kjbMaster = kjbMasterRepository.findByMclMomRelId(dealer.getKjbMaster().getMclMomRelId());

		if (kjbMaster == null) {
			throw new IllegalArgumentException("Target Not Found By MclMomRelId : KjbMaster " + "MclMomRelId:" + dealer.getKjbMaster().getMclMomRelId());
		}

		// 結合して表示するものを設定
		dealer.setDealerName(this.convertJoinedDealerName(kjbMaster));
		dealer.setAddress(this.convertJoinedAddress(kjbMaster));

		dealer.setKjbMaster(kjbMaster);
		dealer.setPostNumber(kjbMaster.getJgsJgsPostNum());
		dealer.setOrgPhoneNumber(kjbMaster.getKgyKgyTelNum());
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
