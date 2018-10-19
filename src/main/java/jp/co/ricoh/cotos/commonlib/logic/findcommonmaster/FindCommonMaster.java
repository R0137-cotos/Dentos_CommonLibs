package jp.co.ricoh.cotos.commonlib.logic.findcommonmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.CommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.MomCommonMasterSearchParameter;
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
	 * <pre>
	 * 【処理内容】
	 *　・引数の汎用マスタIDリストを元に汎用マスタTBL(COMMON_MASTER)、汎用マスタ明細TBL(COMMON_MASTER_DETAIL)から汎用マスタ情報取得
	 *　・以下の条件に一致する汎用マスタ情報を取得
	 *　　条件：
	 *　　　汎用マスタTBL.サービスカテゴリー(COMMON_MASTER.SERVICE_CATEGORY)=引数のサービスカテゴリー または 汎用マスタTBL.サービスカテゴリー(COMMON_MASTER.SERVICE_CATEGORY)=0(共通)
	 *　　　汎用マスタTBL.削除フラグ(COMMON_MASTER.DELETE_FLG)=0
	 *　・検索条件に一致する汎用マスタ情報に紐づく汎用マスタ明細情報を取得　詳細は以下を参照
	 *　　条件：
	 *　　　汎用マスタID(COMMON_MASTER.ID)=汎用マスタID(COMMON_MASTER_DETAIL.COMMON_MASTER_ID)
	 *　　　有効期間From(COMMON_MASTER_DETAIL.AVAILABLE_PERIOD_FROM)≦システム日付≦有効期間To(COMMON_MASTER_DETAIL.AVAILABLE_PERIOD_TO)
	 *　　　削除フラグ(COMMON_MASTER_DETAIL.DELETE_FLG)=0
	 *　　取得項目：
	 *　　　コード値(CODE_VALUE)、表示値(DISPLAY_VALUE)、データエリア1(DATA_AREA_1)～データエリア5(DATA_AREA_5)、表示順(DISPLAY_ORDER)
	 *　　ソート順：
	 *　　　表示順(DISPLAY_ORDER)の昇順
	 *　・引数の空行追加フラグを「true」に指定すると、各汎用マスタ明細の先頭行に空行を追加した状態で取得可能
	 * </pre>
	 * 
	 * @param parameter
	 *            汎用マスタ取得パラメータ
	 * @return 汎用マスタリスト
	 */
	public List<CommonMasterResult> findCommonMaster(CommonMasterSearchParameter parameter) {
		List<CommonMasterResult> list = new ArrayList<>();

		if (null != parameter && null != parameter.getServiceCategory()) {
			List<CommonMaster> commonMasterList = commonMasterRepository.findByServiceCategory(parameter.getServiceCategory().toString());
			commonMasterList.stream().forEach(commonMaster -> {
				CommonMasterResult result = new CommonMasterResult();
				result.setColumnName(commonMaster.getColumnName());
				result.setArticleName(commonMaster.getArticleName());
				List<CommonMasterDetailResult> detailResultList = createCommonMasterDetailResult(commonMasterDetailRepository.findByCommonMasterId(commonMaster.getId()));
				if (parameter.isAddBlankRowFlg() && !detailResultList.isEmpty()) {
					detailResultList.add(0, addBlankRow());
				}
				result.setCommonMasterDetailResultList(detailResultList);
				list.add(result);
			});
		}

		return list;
	}

	/**
	 * MoM汎用マスタ取得
	 * 
	 * <pre>
	 * 【処理内容】
	 *　・引数の汎用マスタIDリストを元にMoM汎用マスタTBL(MV_TJMMB010_UTL_ITEM)、MoM汎用マスタ明細TBL(MV_TJMMB020_UTL_CD)からMoM汎用マスタ情報取得
	 *　・一度に複数のMoM汎用マスタ情報が取得可能で、以下の条件に一致するMoM汎用マスタ情報を取得
	 *　　条件：
	 *　　　MoM汎用マスタTBL.汎用マスタコード(MV_TJMMB010_UTL_ITEM.ITEM_ID)=引数の汎用マスタIDリスト
	 *　　　MoM汎用マスタTBL.削除フラグ(MV_TJMMB010_UTL_ITEM.DEL_FLG)=0
	 *　・引数の汎用マスタIDリストに一致するMoM汎用マスタ情報に紐づくMoM汎用マスタ明細情報を取得　詳細は以下を参照
	 *　　条件：
	 *　　　MoM汎用マスタID(MV_TJMMB010_UTL_ITEM.ITEM_ID)=MoM汎用マスタID(MV_TJMMB020_UTL_CD.ITEM_ID)
	 *　　　削除フラグ(MV_TJMMB020_UTL_CD.DEL_FLG)=0
	 *　　取得項目：
	 *　　　コード値(CD_VAL)、表示値(DECD_VAL)、データエリア1(DATA_AREA1)～データエリア5(DATA_AREA5)、表示順(SORT_NUMBER)
	 *　　ソート順：
	 *　　　表示順(SORT_NUMBER)の昇順
	 *　・引数の空行追加フラグを「true」に指定すると、各MoM汎用マスタ明細の先頭行に空行を追加した状態で取得可能
	 * </pre>
	 * 
	 * @param parameter
	 *            汎用マスタ取得パラメータ
	 * @return MoM汎用マスタリスト
	 */
	public List<CommonMasterResult> findMomCommonMaster(MomCommonMasterSearchParameter parameter) {
		List<CommonMasterResult> list = new ArrayList<>();

		if (null != parameter && null != parameter.getCommonArticleCdList()) {
			parameter.getCommonArticleCdList().stream().forEach(articleCd -> {
				MvTjmmb010UtlItem mvTjmmb010UtlItem = mvTjmmb010UtlItemRepository.findByItemId(articleCd);
				if (null != mvTjmmb010UtlItem) {
					CommonMasterResult result = new CommonMasterResult();
					result.setColumnName(mvTjmmb010UtlItem.getItemId());
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
			detailResult.setCodeValue(detail.getCodeValue());
			detailResult.setDisplayValue(detail.getDisplayValue());
			detailResult.setDataArea1(detail.getDataArea1());
			detailResult.setDataArea2(detail.getDataArea2());
			detailResult.setDataArea3(detail.getDataArea3());
			detailResult.setDataArea4(detail.getDataArea4());
			detailResult.setDataArea5(detail.getDataArea5());
			detailResult.setDisplayOrder(detail.getDisplayOrder());
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
			detailResult.setCodeValue(detail.getId().getCdVal());
			detailResult.setDisplayValue(detail.getDecdVal());
			detailResult.setDataArea1(detail.getDataArea1());
			detailResult.setDataArea2(detail.getDataArea2());
			detailResult.setDataArea3(detail.getDataArea3());
			detailResult.setDataArea4(detail.getDataArea4());
			detailResult.setDataArea5(detail.getDataArea5());
			detailResult.setDisplayOrder(detail.getSortNumber());
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
		detailResult.setCodeValue(null);
		detailResult.setDisplayValue(null);
		detailResult.setDisplayOrder(-1);
		return detailResult;
	}
}
