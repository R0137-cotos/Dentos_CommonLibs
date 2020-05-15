package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DummyCodeValue;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.logic.findcommonmaster.FindCommonMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class CustomerEstimationListener {

	private static String HJN_KAKU_ITEM_CD = "JMC-HJN_KAKU_CD";

	private static VKjbMasterRepository vKjbMasterRepository;
	private static CheckUtil checkUtil;
	private static FindCommonMaster findCommonMaster;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		CustomerEstimationListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		CustomerEstimationListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setFindCommonMaster(FindCommonMaster findCommonMaster) {
		CustomerEstimationListener.findCommonMaster = findCommonMaster;
	}

	/**
	 * 顧客マスタ情報を顧客(見積用)トランザクションに紐づけます。
	 *
	 * @param customerEstimation
	 */
	@PrePersist
	@Transactional
	public void appendsCustomerEstimationFields(CustomerEstimation customerEstimation) {

		if (DummyCodeValue.Dummy_Mcl_MoM_Rel_Id.toString().equals(customerEstimation.getMomKjbSystemId())) {
			return;
		}

		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(customerEstimation.getMomKjbSystemId());
		if (vKjbMaster == null) {
			String[] regexList = { "顧客（見積用）" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

		customerEstimation.setCompanyName(this.convertJoinedCompanyName(vKjbMaster));
		customerEstimation.setCustomerName(this.convertJoinedCustomerName(vKjbMaster, customerEstimation));
		customerEstimation.setAddress(this.convertJoinedAddress(vKjbMaster));
		customerEstimation.setPhoneNumber(vKjbMaster.getJgsJgsTelNum());
		customerEstimation.setFaxNumber(vKjbMaster.getJgsJgsFaxNum());
		customerEstimation.setDepartmentDiv(vKjbMaster.getPrflKjbSetKbn());
		customerEstimation.setCompanyId(vKjbMaster.getPrflMomKgyId());
		customerEstimation.setOfficeId(vKjbMaster.getPrflMomJgsId());
		customerEstimation.setOfficeName(vKjbMaster.getJgsJgsNmKnji());
		customerEstimation.setMomCustId(vKjbMaster.getMclMomKjbId());
		customerEstimation.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		customerEstimation.setCompanyNameKana(vKjbMaster.getKgyKgyNmKana());
		customerEstimation.setDepartmentName(vKjbMaster.getBmnBmnNmKnji());
	}

	private String convertJoinedCompanyName(VKjbMaster kjbMaster) {
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

	private String convertJoinedCustomerName(VKjbMaster kjbMaster, CustomerEstimation customerEstimation) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(customerEstimation.getCompanyName(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		if (DepartmentDiv.企事部.equals(kjbMaster.getPrflKjbSetKbn())) {
			sb.append(StringUtils.defaultIfEmpty(kjbMaster.getBmnBmnNmKnji(), StringUtils.EMPTY));
		}

		return sb.toString();
	}

	private String convertJoinedAddress(VKjbMaster kjbMaster) {
		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsJtdhknNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsJskugnchosnKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getAdsJowaTusyoKnji(), StringUtils.EMPTY));
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
