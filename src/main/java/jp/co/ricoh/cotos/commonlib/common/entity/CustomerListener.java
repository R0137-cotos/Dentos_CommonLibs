package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster.KjbSetDiv;
import jp.co.ricoh.cotos.commonlib.common.repository.KjbMasterRepository;

@Component
public class CustomerListener {

	private static KjbMasterRepository kjbMasterRepository;

	@Autowired
	public void setKjbMasterRepository(KjbMasterRepository kjbMasterRepository) {
		CustomerListener.kjbMasterRepository = kjbMasterRepository;
	}

	/**
	 * 顧客マスタ情報を顧客トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationCustomerFields(Customer customer) {
		KjbMaster kjbMaster = kjbMasterRepository.findByMclMomRelId(customer.getKjbMaster().getMclMomRelId());

		if (kjbMaster == null) {
			throw new IllegalArgumentException("Target Not Found By MclMomKjbId : KjbMaster " + "MclMomKjbId:" + customer.getKjbMaster().getMclMomRelId());
		}

		// 結合して表示するものを設定
		customer.setCustomerName(this.convertJoinedCustomerName(kjbMaster));
		customer.setAddress(this.convertJoinedAddress(kjbMaster));

		// 企事部設定区分により設定値を振り分け
		if (KjbSetDiv.企事部.equals(kjbMaster.getPrflKjbSetKbn())) {
			customer.setPhoneNumber(kjbMaster.getBmnBmnTelNum());
		} else {
			customer.setPhoneNumber(kjbMaster.getJgsJgsTelNum());
		}

		customer.setKjbMaster(kjbMaster);
		customer.setDepartmentDiv(kjbMaster.getPrflKjbSetKbn());
		customer.setCompanyId(kjbMaster.getPrflMomKgyId());
		customer.setOfficeId(kjbMaster.getPrflMomJgsId());
		customer.setOfficeName(kjbMaster.getJgsJgsNmKnji());
		customer.setMomCustId(kjbMaster.getMclMomKjbId());
		customer.setPostNumber(kjbMaster.getJgsJgsPostNum());
		customer.setCompanyName(kjbMaster.getKgyKgyNmKnji());
		customer.setDepartmentName(kjbMaster.getBmnBmnNmKnji());
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
