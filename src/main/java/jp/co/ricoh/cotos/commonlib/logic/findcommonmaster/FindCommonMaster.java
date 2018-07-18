package jp.co.ricoh.cotos.commonlib.logic.findcommonmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.CommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMasterDetail.Id;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MomCommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MomCommonMasterRepository;

/**
 * 汎用マスタ取得共通クラス
 */
@Component
public class FindCommonMaster {

	@Autowired
	CommonMasterRepository commonMasterRepository;
	@Autowired
	CommonMasterDetailRepository commonMasterDetailRepository;
	@Autowired
	MomCommonMasterRepository momCommonMasterRepository;
	@Autowired
	MomCommonMasterDetailRepository momCommonMasterDetailRepository;

	/**
	 * 汎用マスタ取得
	 * 
	 * @param parameter
	 *            汎用マスタ取得パラメータ
	 * @return 汎用マスタリスト
	 */
	public List<CommonMaster> findCommonMaster(CommonMasterSearchParameter parameter) {
		List<CommonMaster> list = new ArrayList<>();

		if (null != parameter.getCommonItemIdList()) {
			parameter.getCommonItemIdList().stream().forEach(itemId -> {
				CommonMaster commonMaster = commonMasterRepository.findByItemId(itemId);
				if (null != commonMaster) {
					commonMaster.setCommonMasterDetailList(commonMasterDetailRepository.findByCommonMasterId(commonMaster.getId()));
					if (parameter.isAddBlankRowFlg() && !commonMaster.getCommonMasterDetailList().isEmpty()) {
						List<CommonMasterDetail> detailList = commonMaster.getCommonMasterDetailList();
						CommonMasterDetail commonMasterDetail = new CommonMasterDetail();
						commonMasterDetail.setId(0);
						detailList.add(0, commonMasterDetail);
					}
					list.add(commonMaster);
				}
			});
		}

		return list;
	}

	/**
	 * MoM汎用マスタ取得
	 * 
	 * @param parameter
	 *            汎用マスタ取得パラメータ
	 * @return MoM汎用マスタリスト
	 */
	public List<MomCommonMaster> findMomCommonMaster(CommonMasterSearchParameter parameter) {
		List<MomCommonMaster> list = new ArrayList<>();

		if (null != parameter.getCommonItemIdList()) {
			parameter.getCommonItemIdList().stream().forEach(itemId -> {
				MomCommonMaster momCommonMaster = momCommonMasterRepository.findByItemId(itemId);
				if (null != momCommonMaster) {
					momCommonMaster.setMomCommonDetailMasterList(momCommonMasterDetailRepository.findByItemId(itemId));
					if (parameter.isAddBlankRowFlg() && !momCommonMaster.getMomCommonDetailMasterList().isEmpty()) {
						List<MomCommonMasterDetail> detailList = momCommonMaster.getMomCommonDetailMasterList();
						MomCommonMasterDetail momCommonMasterDetail = new MomCommonMasterDetail();
						Id id = new Id();
						id.setItemId(itemId);
						id.setCdVal("-1");
						momCommonMasterDetail.setId(id);
						momCommonMasterDetail.setSortNumber(-1);
						momCommonMasterDetail.setDelFlg("0");
						detailList.add(0, momCommonMasterDetail);
					}
					list.add(momCommonMaster);
				}
			});
		}

		return list;
	}
}
