package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster.KjbSetDiv;
import jp.co.ricoh.cotos.commonlib.repository.master.KjbMasterRepository;

@Component
public class CustomerConListener {

	private static KjbMasterRepository kjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(KjbMasterRepository kjbMasterRepository) {
		CustomerConListener.kjbMasterRepository = kjbMasterRepository;
	}

	/**
	 * 企事部マスタ情報を顧客トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsContractCustomerFields(CustomerCon customerCon) {
		KjbMaster kjbMaster = kjbMasterRepository.findByMclMomRelId(customerCon.getKjbMaster().getMclMomRelId());

		if (kjbMaster == null) {
			throw new IllegalArgumentException("Target Not Found By MclMomKjbId : KjbMaster " + "MclMomKjbId:" + customerCon.getKjbMaster().getMclMomRelId());
		}

		// 結合して表示するものを設定
		customerCon.setCustomerName(this.convertJoinedCustomerName(kjbMaster));
		customerCon.setAddress(this.convertJoinedAddress(kjbMaster));
		customerCon.setPhoneNumber(kjbMaster.getKgyKgyTelNum());
		customerCon.setFaxNumber(kjbMaster.getKgyKgyFaxNum());
		customerCon.setCompanyRepresentativeName(kjbMaster.getKgyKgyDhyshNmKnji());
		customerCon.setKjbMaster(kjbMaster);
		customerCon.setDepartmentDiv(kjbMaster.getPrflKjbSetKbn());
		customerCon.setCompanyId(kjbMaster.getPrflMomKgyId());
		customerCon.setOfficeId(kjbMaster.getPrflMomJgsId());
		customerCon.setOfficeName(kjbMaster.getJgsJgsNmKnji());
		customerCon.setMomCustId(kjbMaster.getMclMomKjbId());
		customerCon.setPostNumber(kjbMaster.getJgsJgsPostNum());
		customerCon.setCompanyName(kjbMaster.getKgyKgyNmKnji());
		customerCon.setCompanyNameKana(kjbMaster.getKgyKgyNmKana());
		customerCon.setDepartmentName(kjbMaster.getBmnBmnNmKnji());
	}

	private String convertJoinedCustomerName(KjbMaster kjbMaster) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getKgyKgyNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		if (KjbSetDiv.企事部.equals(kjbMaster.getPrflKjbSetKbn())) {
			sb.append(StringUtils.defaultIfEmpty(kjbMaster.getBmnBmnNmKnji(), StringUtils.EMPTY));
		}

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
