package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.DummyCodeValue;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class CustomerEstimationListener {

	private static VKjbMasterRepository vKjbMasterRepository;
	private static CheckUtil checkUtil;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		CustomerEstimationListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		CustomerEstimationListener.checkUtil = checkUtil;
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

		// 結合して表示するものを設定
		customerEstimation.setCustomerName(this.convertJoinedCustomerName(vKjbMaster));
		customerEstimation.setAddress(this.convertJoinedAddress(vKjbMaster));

		// 企事部設定区分により設定値を振り分け
		if (DepartmentDiv.企事部.equals(vKjbMaster.getPrflKjbSetKbn())) {
			customerEstimation.setPhoneNumber(vKjbMaster.getBmnBmnTelNum());
			customerEstimation.setFaxNumber(vKjbMaster.getBmnBmnFaxNum());
		} else {
			customerEstimation.setPhoneNumber(vKjbMaster.getJgsJgsTelNum());
			customerEstimation.setFaxNumber(vKjbMaster.getJgsJgsFaxNum());
		}

		customerEstimation.setDepartmentDiv(vKjbMaster.getPrflKjbSetKbn());
		customerEstimation.setCompanyId(vKjbMaster.getPrflMomKgyId());
		customerEstimation.setOfficeId(vKjbMaster.getPrflMomJgsId());
		customerEstimation.setOfficeName(vKjbMaster.getJgsJgsNmKnji());
		customerEstimation.setMomCustId(vKjbMaster.getMclMomKjbId());
		customerEstimation.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		customerEstimation.setCompanyName(vKjbMaster.getKgyKgyNmKnji());
		customerEstimation.setDepartmentName(vKjbMaster.getBmnBmnNmKnji());
	}

	private String convertJoinedCustomerName(VKjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		if (DepartmentDiv.企事部.equals(kjbMaster.getPrflKjbSetKbn())) {
			sb.append(StringUtils.defaultIfEmpty(kjbMaster.getBmnBmnNmKnji(), StringUtils.EMPTY));
		}

		return sb.toString();
	}

	private String convertJoinedAddress(VKjbMaster vKjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(vKjbMaster.getJgsJgsAdsAzatusyoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(vKjbMaster.getJgsJgsAdsBantiNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(vKjbMaster.getJgsJgsAdsGoNm(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(vKjbMaster.getJgsJgsAdsFlorNm(), StringUtils.EMPTY));

		return sb.toString();
	}

}
