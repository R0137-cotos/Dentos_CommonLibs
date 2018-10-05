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
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb010UtlItem;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb020UtlCd;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb020UtlCdRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb010UtlItemRepository;

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
	MvTjmmb010UtlItemRepository mvTjmmb010UtlItemRepository;
	@Autowired
	MvTjmmb020UtlCdRepository mvTjmmb020UtlCdRepository;

	/**
	 * 汎用マスタ取得
	 * 
	 * @param parameter
	 *            汎用マスタ取得パラメータ
	 * @return 汎用マスタリスト
	 */
	public List<CommonMasterResult> findCommonMaster(CommonMasterSearchParameter parameter) {
		List<CommonMasterResult> list = new ArrayList<>();

		if (null != parameter && null != parameter.getCommonArticleCdList()) {
			parameter.getCommonArticleCdList().stream().forEach(articleCd -> {
				CommonMaster commonMaster = commonMasterRepository.findByArticleCd(articleCd);
				if (null != commonMaster) {
					CommonMasterResult result = new CommonMasterResult();
					result.setArticleCd(commonMaster.getArticleCd());
					result.setArticleName(commonMaster.getArticleName());
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

		if (null != parameter && null != parameter.getCommonArticleCdList()) {
			parameter.getCommonArticleCdList().stream().forEach(articleCd -> {
				MvTjmmb010UtlItem mvTjmmb010UtlItem = mvTjmmb010UtlItemRepository.findByItemId(articleCd);
				if (null != mvTjmmb010UtlItem) {
					CommonMasterResult result = new CommonMasterResult();
					result.setArticleCd(mvTjmmb010UtlItem.getItemId());
					result.setArticleName(mvTjmmb010UtlItem.getItemName());
					List<CommonMasterDetailResult> detailResultList = createCommonMasterDetailResultMom(mvTjmmb020UtlCdRepository.findByItemId(articleCd));
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
	private List<CommonMasterDetailResult> createCommonMasterDetailResultMom(List<MvTjmmb020UtlCd> detailList) {
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
