package jp.co.ricoh.cotos.commonlib.logic.findcommonmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterRepository;

/**
 * 汎用マスタ取得共通クラス
 */
@Component
public class FindCommonMaster {

	@Autowired
	CommonMasterRepository commonMasterRepository;
	@Autowired
	CommonMasterDetailRepository commonMasterDetailRepository;

	/**
	 * 汎用マスタ取得
	 * 
	 * @param commonMasterIdList
	 *            汎用マスタIDリスト
	 * @param isAddBlankRow
	 *            空行追加するか
	 * @return 汎用マスタリスト
	 */
	public List<CommonMaster> findCommonMaster(List<Long> commonMasterIdList, boolean isAddBlankRow) {
		List<CommonMaster> list = new ArrayList<>();

		commonMasterIdList.stream().forEach(id -> {
			CommonMaster commonMaster = commonMasterRepository.findOne(id);
			if (null != commonMaster) {
				commonMaster.setCommonMasterDetailList(commonMasterDetailRepository.findByCommonMasterId(commonMaster.getId()));
				if (isAddBlankRow && !commonMaster.getCommonMasterDetailList().isEmpty()) {
					List<CommonMasterDetail> detailList = commonMaster.getCommonMasterDetailList();
					CommonMasterDetail commonMasterDetail = new CommonMasterDetail();
					commonMasterDetail.setId(0);
					detailList.add(0, commonMasterDetail);
				}
				list.add(commonMaster);
			}
		});

		return list;
	}
}
