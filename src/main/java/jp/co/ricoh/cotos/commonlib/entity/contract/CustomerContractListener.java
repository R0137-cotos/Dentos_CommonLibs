package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;

@Component
public class CustomerContractListener {

	private static VKjbMasterRepository vKjbMasterRepository;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		CustomerContractListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 顧客マスタ情報を顧客(契約用)トランザクションに紐づけます。
	 * 
	 * @param customerContract
	 */
	@PrePersist
	@Transactional
	public void appendsCustomerContractFields(CustomerContract customerContract) {

		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(customerContract.getMomKjbSystemId());
		if (vKjbMaster == null) {
			String[] regexList = { "顧客（契約用）" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

		// 結合して表示するものを設定
		customerContract.setCustomerName(this.convertJoinedCustomerName(vKjbMaster));
		customerContract.setAddress(this.convertJoinedAddress(vKjbMaster));

		// 企事部設定区分により設定値を振り分け
		if (DepartmentDiv.企事部.equals(vKjbMaster.getPrflKjbSetKbn())) {
			customerContract.setPhoneNumber(vKjbMaster.getBmnBmnTelNum());
			customerContract.setFaxNumber(vKjbMaster.getBmnBmnFaxNum());
		} else {
			customerContract.setPhoneNumber(vKjbMaster.getJgsJgsTelNum());
			customerContract.setFaxNumber(vKjbMaster.getJgsJgsFaxNum());
		}

		customerContract.setDepartmentDiv(vKjbMaster.getPrflKjbSetKbn());
		customerContract.setCompanyId(vKjbMaster.getPrflMomKgyId());
		customerContract.setOfficeId(vKjbMaster.getPrflMomJgsId());
		customerContract.setOfficeName(vKjbMaster.getJgsJgsNmKnji());
		customerContract.setMomCustId(vKjbMaster.getMclMomKjbId());
		customerContract.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		customerContract.setCompanyName(vKjbMaster.getKgyKgyNmKnji());
		customerContract.setDepartmentName(vKjbMaster.getBmnBmnNmKnji());
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
