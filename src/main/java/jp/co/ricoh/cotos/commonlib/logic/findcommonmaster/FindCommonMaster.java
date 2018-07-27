package jp.co.ricoh.cotos.commonlib.logic.findcommonmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.CommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.dto.result.CommonMasterDetailResult;
import jp.co.ricoh.cotos.commonlib.dto.result.CommonMasterResult;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MomCommonMasterDetail;
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
	public List<CommonMasterResult> findCommonMaster(CommonMasterSearchParameter parameter) {
		List<CommonMasterResult> list = new ArrayList<>();

		if (null != parameter && null != parameter.getCommonItemIdList()) {
			parameter.getCommonItemIdList().stream().forEach(articleCd -> {
				CommonMaster commonMaster = commonMasterRepository.findByArticleCd(articleCd);
				if (null != commonMaster) {
					CommonMasterResult result = createCommonMasterResult(commonMaster);
					List<CommonMasterDetailResult> detailResultList = createCommonMasterDetailResult(commonMasterDetailRepository.findByCommonMasterId(commonMaster.getId()));
					if (parameter.isAddBlankRowFlg() && !detailResultList.isEmpty()) {
						detailResultList.add(0, addBlankRow());
					}
					result.setCommonMasterDetailResultList(detailResultList);
					list.add(result);
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
	public List<CommonMasterResult> findMomCommonMaster(CommonMasterSearchParameter parameter) {
		List<CommonMasterResult> list = new ArrayList<>();

		if (null != parameter && null != parameter.getCommonItemIdList()) {
			parameter.getCommonItemIdList().stream().forEach(articleCd -> {
				MomCommonMaster momCommonMaster = momCommonMasterRepository.findByItemId(articleCd);
				if (null != momCommonMaster) {
					CommonMasterResult result = createCommonMasterResultMom(momCommonMaster);
					List<CommonMasterDetailResult> detailResultList = createCommonMasterDetailResultMom(momCommonMasterDetailRepository.findByItemId(articleCd));
					if (parameter.isAddBlankRowFlg() && !detailResultList.isEmpty()) {
						detailResultList.add(0, addBlankRow());
					}
					result.setCommonMasterDetailResultList(detailResultList);
					list.add(result);
				}
			});
		}

		return list;
	}

	/**
	 * 汎用マスタ結果生成
	 * 
	 * @param master
	 *            COTOS汎用マスタ
	 * @return 汎用マスタ結果
	 */
	private CommonMasterResult createCommonMasterResult(CommonMaster master) {
		CommonMasterResult result = new CommonMasterResult();
		result.setArticleCd(master.getArticleCd());
		result.setArticleName(master.getArticleName());
		return result;
	}

	/**
	 * 汎用マスタ結果生成
	 * 
	 * @param master
	 *            MoM汎用マスタ
	 * @return 汎用マスタ結果
	 */
	private CommonMasterResult createCommonMasterResultMom(MomCommonMaster master) {
		CommonMasterResult result = new CommonMasterResult();
		result.setArticleCd(master.getItemId());
		result.setArticleName(master.getItemName());
		return result;
	}

	/**
	 * 汎用マスタ明細結果リスト生成
	 * 
	 * @param detailList
	 *            COTOS汎用マスタ明細リスト
	 * @return 汎用マスタ明細結果リスト
	 */
	private List<CommonMasterDetailResult> createCommonMasterDetailResult(List<CommonMasterDetail> detailList) {
		List<CommonMasterDetailResult> list = new ArrayList<>();
		detailList.stream().forEach(detail -> {
			CommonMasterDetailResult detailResult = new CommonMasterDetailResult();
			detailResult.setCdVal(detail.getCdVal());
			detailResult.setDisplayVal(detail.getDisplayVal());
			detailResult.setDataArea1(detail.getDataArea1());
			detailResult.setDataArea2(detail.getDataArea2());
			detailResult.setDataArea3(detail.getDataArea3());
			detailResult.setDataArea4(detail.getDataArea4());
			detailResult.setDataArea5(detail.getDataArea5());
			detailResult.setSortNumber(detail.getSortNumber());
			list.add(detailResult);
		});
		return list;
	}

	/**
	 * 汎用マスタ明細結果リスト生成
	 * 
	 * @param detailList
	 *            COTOS汎用マスタ明細リスト
	 * @return 汎用マスタ明細結果リスト
	 */
	private List<CommonMasterDetailResult> createCommonMasterDetailResultMom(List<MomCommonMasterDetail> detailList) {
		List<CommonMasterDetailResult> list = new ArrayList<>();
		detailList.stream().forEach(detail -> {
			CommonMasterDetailResult detailResult = new CommonMasterDetailResult();
			detailResult.setCdVal(detail.getId().getCdVal());
			detailResult.setDisplayVal(detail.getDecdVal());
			detailResult.setDataArea1(detail.getDataArea1());
			detailResult.setDataArea2(detail.getDataArea2());
			detailResult.setDataArea3(detail.getDataArea3());
			detailResult.setDataArea4(detail.getDataArea4());
			detailResult.setDataArea5(detail.getDataArea5());
			detailResult.setSortNumber(detail.getSortNumber());
			list.add(detailResult);
		});
		return list;
	}

	/**
	 * 汎用マスタ明細結果空行作成
	 * 
	 * @return 汎用マスタ明細結果
	 */
	private CommonMasterDetailResult addBlankRow() {
		CommonMasterDetailResult detailResult = new CommonMasterDetailResult();
		detailResult.setCdVal(null);
		detailResult.setDisplayVal(null);
		detailResult.setSortNumber(-1);
		return detailResult;
	}
}
