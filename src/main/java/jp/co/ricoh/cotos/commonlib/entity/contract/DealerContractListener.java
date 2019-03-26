package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

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
	private static CheckUtil checkUtil;

	@Autowired
	public void setVkjbMasterRepository(VKjbMasterRepository vKjbMasterRepository) {
		DealerContractListener.vKjbMasterRepository = vKjbMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		DealerContractListener.checkUtil = checkUtil;
	}

	@PrePersist
	@Transactional
	public void appendsDealerContractFields(DealerContract dealerContract) {

		VKjbMaster vKjbMaster = vKjbMasterRepository.findByMclMomRelId(dealerContract.getMomKjbSystemId());
		if (vKjbMaster == null) {
			String[] regexList = { "販売店（契約用）" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistKjbMaster", regexList));
		}

	}
}
