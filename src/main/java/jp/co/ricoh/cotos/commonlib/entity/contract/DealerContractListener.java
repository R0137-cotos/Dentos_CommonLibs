package jp.co.ricoh.cotos.commonlib.entity.contract;

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
public class DealerContractListener {

	private static VKjbMasterRepository vKjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(VKjbMasterRepository kjbMasterRepository) {
		DealerContractListener.vKjbMasterRepository = kjbMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 企事部マスタ情報を販売店（契約用）トランザクションに紐づけます。
	 *
	 * @param dealerContract
	 */
	@PrePersist
	@Transactional
	public void appendsContractDealerFields(DealerContract dealerContract) {
		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(dealerContract.getVKjbMaster().getMclMomRelId());

		if (vKjbMaster == null) {
			String[] regexList = { "販売店（契約用）" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

		// 結合して表示するものを設定
		dealerContract.setDealerName(this.convertJoinedDealerName(vKjbMaster));
		dealerContract.setAddress(this.convertJoinedAddress(vKjbMaster));

		dealerContract.setVKjbMaster(vKjbMaster);
		dealerContract.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		dealerContract.setOrgPhoneNumber(vKjbMaster.getKgyKgyTelNum());
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
