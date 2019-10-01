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
public class ContractInstallationLocationListener {

	private static VKjbMasterRepository vKjbMasterRepository;
	private static CheckUtil checkUtil;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		ContractInstallationLocationListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractInstallationLocationListener.checkUtil = checkUtil;
	}

	/**
	 * 顧客マスタ情報を設置先(契約用)トランザクションに紐づけます。
	 *
	 * @param contractInstallationLocation
	 */
	@PrePersist
	@Transactional
	public void appendsCustomerEstimationFields(ContractInstallationLocation contractInstallationLocation) {

		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(contractInstallationLocation.getMomKjbSystemId());
		if (vKjbMaster == null) {
			String[] regexList = { "設置先(契約用)" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

		// 結合して表示するものを設定
		// 値が設定されていない場合のみ補完する
		if (StringUtils.isBlank(contractInstallationLocation.getCompanyName()))
			contractInstallationLocation.setCompanyName(vKjbMaster.getKgyKgyNmKnji()); // 画面からは法人格付きの会社名が送られてくる

		if (StringUtils.isBlank(contractInstallationLocation.getCustomerName()))
			contractInstallationLocation.setCustomerName(this.convertJoinedCustomerName(vKjbMaster, contractInstallationLocation));
		if (StringUtils.isBlank(contractInstallationLocation.getAddress()))
			contractInstallationLocation.setAddress(vKjbMaster.getKgyCuicClnMaeAds());

		// 企事部設定区分により設定値を振り分け
		if (DepartmentDiv.企事部.equals(vKjbMaster.getPrflKjbSetKbn())) {
			if (StringUtils.isBlank(contractInstallationLocation.getPhoneNumber()))
				contractInstallationLocation.setPhoneNumber(vKjbMaster.getBmnBmnTelNum());
			if (StringUtils.isBlank(contractInstallationLocation.getFaxNumber()))
				contractInstallationLocation.setFaxNumber(vKjbMaster.getBmnBmnFaxNum());
		} else {
			if (StringUtils.isBlank(contractInstallationLocation.getPhoneNumber()))
				contractInstallationLocation.setPhoneNumber(vKjbMaster.getJgsJgsTelNum());
			if (StringUtils.isBlank(contractInstallationLocation.getFaxNumber()))
				contractInstallationLocation.setFaxNumber(vKjbMaster.getJgsJgsFaxNum());
		}

		contractInstallationLocation.setDepartmentDiv(vKjbMaster.getPrflKjbSetKbn());
		contractInstallationLocation.setCompanyId(vKjbMaster.getPrflMomKgyId());
		contractInstallationLocation.setOfficeId(vKjbMaster.getPrflMomJgsId());
		contractInstallationLocation.setOfficeName(vKjbMaster.getJgsJgsNmKnji());
		contractInstallationLocation.setMomCustId(vKjbMaster.getMclMomKjbId());
		contractInstallationLocation.setPostNumber(vKjbMaster.getJgsJgsPostNum());
		contractInstallationLocation.setDepartmentName(vKjbMaster.getBmnBmnNmKnji());
	}

	private String convertJoinedCustomerName(VKjbMaster kjbMaster, ContractInstallationLocation contractInstallationLocation) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(contractInstallationLocation.getCompanyName(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(kjbMaster.getJgsJgsNmKnji(), StringUtils.EMPTY));

		if (DepartmentDiv.企事部.equals(kjbMaster.getPrflKjbSetKbn())) {
			sb.append(StringUtils.defaultIfEmpty(kjbMaster.getBmnBmnNmKnji(), StringUtils.EMPTY));
		}

		return sb.toString();
	}

}
