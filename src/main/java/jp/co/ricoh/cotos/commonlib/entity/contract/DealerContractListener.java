package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.common.MomCommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.dto.result.CommonMasterResult;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.logic.findcommonmaster.FindCommonMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class DealerContractListener {

	private static String HJN_KAKU_ITEM_CD = "JMC-HJN_KAKU_CD";

	private static VKjbMasterRepository vKjbMasterRepository;
	private static CheckUtil checkUtil;
	private static FindCommonMaster findCommonMaster;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		DealerContractListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		DealerContractListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setFindCommonMaster(FindCommonMaster findCommonMaster) {
		DealerContractListener.findCommonMaster = findCommonMaster;
	}

	@PrePersist
	@Transactional
	public void appendsDealerContractFields(DealerContract dealerContract) {

		if (null != dealerContract.getMomKjbSystemId()) {
			VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(dealerContract.getMomKjbSystemId());
			if (vKjbMaster == null) {
				String[] regexList = { "販売店（契約用）" };
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
			}

			// 結合して表示するものを設定
			// 値が設定されていない場合のみ補完する
			if (StringUtils.isBlank(dealerContract.getDealerName()))
				dealerContract.setDealerName(this.convertJoinedDealerName(vKjbMaster));
			if (StringUtils.isBlank(dealerContract.getAddress()))
				dealerContract.setAddress(this.convertJoinedAddress(vKjbMaster));

			if (StringUtils.isBlank(dealerContract.getPostNumber()))
				dealerContract.setPostNumber(vKjbMaster.getJgsJgsPostNum());
			if (StringUtils.isBlank(dealerContract.getOrgPhoneNumber()))
				dealerContract.setOrgPhoneNumber(vKjbMaster.getKgyKgyTelNum());
		}

	}

	private String convertJoinedDealerName(VKjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		MomCommonMasterSearchParameter parameter = new MomCommonMasterSearchParameter();
		parameter.setCommonArticleCdList(Arrays.asList(HJN_KAKU_ITEM_CD));
		List<CommonMasterResult> commonMasterList = findCommonMaster.findMomCommonMaster(parameter);
		commonMasterList.stream().forEach(commonMasterResult -> {
			commonMasterResult.getCommonMasterDetailResultList().stream().forEach(commonMasterDetailResult -> {
				if (commonMasterDetailResult.getCodeValue().equals(kjbMaster.getKgyHjnKakuCd())) {
					if (kjbMaster.getKgyHjnKakuZengoCd().equals("1")) {
						sb.append(StringUtils.defaultIfEmpty(commonMasterDetailResult.getDataArea1(), StringUtils.EMPTY));
						sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
					} else if (kjbMaster.getKgyHjnKakuZengoCd().equals("2")) {
						sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
						sb.append(StringUtils.defaultIfEmpty(commonMasterDetailResult.getDataArea1(), StringUtils.EMPTY));
					}
				}
			});
		});

		return sb.toString();
	}

	private String convertJoinedAddress(VKjbMaster kjbMaster) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsKtdhknNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsKskugnchosnKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsKowaTusyoKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsJkowChomeKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsAzatusyoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsAdsBantiNm(), StringUtils.EMPTY));
		if (!StringUtils.isEmpty(kjbMaster.getJgsJgsAdsGoNm())) {
			sb.append("－" + kjbMaster.getJgsJgsAdsGoNm());
		}
		if (!StringUtils.isEmpty(kjbMaster.getJgsJgsAdsBldgNm())) {
			sb.append("　" + kjbMaster.getJgsJgsAdsBldgNm());
		}
		if (!StringUtils.isEmpty(kjbMaster.getJgsJgsAdsFlorNm())) {
			sb.append("　" + kjbMaster.getJgsJgsAdsFlorNm());
		}

		return sb.toString();
	}
}
