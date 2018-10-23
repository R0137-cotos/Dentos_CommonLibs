package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class DealerEstimationListener {

	private static VKjbMasterRepository vKjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(VKjbMasterRepository kjbMasterRepository) {
		DealerEstimationListener.vKjbMasterRepository = kjbMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 企事部マスタ情報を販売店（見積用）トランザクションに紐づけます。
	 *
	 * @param dealerEstimation
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationDealerFields(DealerEstimation dealerEstimation) {
		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(dealerEstimation.getVKjbMaster().getMclMomRelId());

		if (vKjbMaster == null) {
			String[] regexList = { "販売店（見積用）" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

		// 結合して表示するものを設定
		dealerEstimation.setDealerName(this.convertJoinedDealerName(vKjbMaster));
		dealerEstimation.setAddress(this.convertJoinedAddress(vKjbMaster));

		dealerEstimation.setVKjbMaster(vKjbMaster);
		dealerEstimation.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		dealerEstimation.setOrgPhoneNumber(vKjbMaster.getKgyKgyTelNum());
	}

	private String convertJoinedDealerName(VKjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		return sb.toString();
	}

	private String convertJoinedAddress(VKjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsAzatusyoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsBantiNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsGoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsFlorNm(), StringUtils.EMPTY));

		return sb.toString();
	}

}
