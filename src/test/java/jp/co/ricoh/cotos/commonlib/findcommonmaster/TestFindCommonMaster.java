package jp.co.ricoh.cotos.commonlib.findcommonmaster;

import java.util.Arrays;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.CommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.MomCommonMasterSearchParameter;
import jp.co.ricoh.cotos.commonlib.dto.result.CommonMasterResult;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.logic.findcommonmaster.FindCommonMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFindCommonMaster {

	@Autowired
	FindCommonMaster findCommonMaster;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	@Transactional
	public void 汎用マスタ取得空行なし() {
		汎用マスタデータ作成();

		CommonMasterSearchParameter parameter = 汎用マスタ検索パラメータ作成(ServiceCategory.見積, false);
		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(parameter);
		汎用マスタ結果確認(commonList, false);
	}

	@Test
	@Transactional
	public void 汎用マスタ取得空行あり() {
		汎用マスタデータ作成();

		CommonMasterSearchParameter parameter = 汎用マスタ検索パラメータ作成(ServiceCategory.見積, true);
		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(parameter);
		汎用マスタ結果確認(commonList, true);
	}

	@Test
	@Transactional
	public void 汎用マスタ取得一致データなし() {
		汎用マスタデータ作成共通なし();

		CommonMasterSearchParameter parameter = 汎用マスタ検索パラメータ作成(ServiceCategory.契約, false);
		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(parameter);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void 汎用マスタ取得一致データなし削除データ指定() {
		汎用マスタデータ作成共通なし();

		CommonMasterSearchParameter parameter = 汎用マスタ検索パラメータ作成(ServiceCategory.手配, false);
		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(parameter);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void 汎用マスタ取得検索条件サービスカテゴリーNull() {
		汎用マスタデータ作成();

		CommonMasterSearchParameter parameter = 汎用マスタ検索パラメータ作成(null, false);
		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(parameter);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void 汎用マスタ取得パラメータNull() {
		汎用マスタデータ作成();

		List<CommonMasterResult> commonList = findCommonMaster.findCommonMaster(null);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得単一空行なし() {
		List<String> commonArticleCdList = Arrays.asList("JF-CONSUMPTION_TAX_CTGR");
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(commonArticleCdList, false);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		MoM汎用マスタ結果確認(commonList, false, false);
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得単一空行あり() {
		List<String> commonArticleCdList = Arrays.asList("JF-CONSUMPTION_TAX_CTGR");
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(commonArticleCdList, true);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		MoM汎用マスタ結果確認(commonList, true, false);
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得複数() {
		List<String> commonArticleCdList = Arrays.asList("JF-CONSUMPTION_TAX_CTGR", "JF-DELIVERY_PATTERN_CTGR");
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(commonArticleCdList, false);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		MoM汎用マスタ結果確認(commonList, false, true);
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得一致データなし削除データ指定() {
		List<String> commonArticleCdList = Arrays.asList("JFC-PARALLEL_OPERATION_INV_GRP_CD");
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(commonArticleCdList, false);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		Assert.assertEquals("MoM汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得一致データなし() {
		List<String> commonArticleCdList = Arrays.asList("COTOS_TEST");
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(commonArticleCdList, false);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		Assert.assertEquals("MoM汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得検索条件カラム名リストNull() {
		MomCommonMasterSearchParameter parameter = MoM汎用マスタ検索パラメータ作成(null, false);
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(parameter);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得パラメータNull() {
		List<CommonMasterResult> commonList = findCommonMaster.findMomCommonMaster(null);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	private void 汎用マスタデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/findcommonmaster/testCommonMasterInsert.sql");
		context.getBean(DBConfig.class).initTargetTestData("sql/findcommonmaster/testCommonMasterDetailInsert.sql");
	}

	private void 汎用マスタデータ作成共通なし() {
		context.getBean(DBConfig.class).initTargetTestData("sql/findcommonmaster/testCommonMasterInsertNotFoundComonn.sql");
		context.getBean(DBConfig.class).initTargetTestData("sql/findcommonmaster/testCommonMasterDetailInsertNotFoundComonn.sql");
	}

	private CommonMasterSearchParameter 汎用マスタ検索パラメータ作成(ServiceCategory serviceCategory, boolean addBlankRowFlg) {
		CommonMasterSearchParameter parameter = new CommonMasterSearchParameter();
		parameter.setServiceCategory(serviceCategory);
		parameter.setAddBlankRowFlg(addBlankRowFlg);
		return parameter;
	}

	private MomCommonMasterSearchParameter MoM汎用マスタ検索パラメータ作成(List<String> commonArticleCdList, boolean addBlankRowFlg) {
		MomCommonMasterSearchParameter parameter = new MomCommonMasterSearchParameter();
		parameter.setCommonArticleCdList(commonArticleCdList);
		parameter.setAddBlankRowFlg(addBlankRowFlg);
		return parameter;
	}

	private void 汎用マスタ結果確認(List<CommonMasterResult> commonList, boolean isAddBlankRow) {
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 2, commonList.size());
		Assert.assertEquals("カラム名が正しく設定されること", "test_column1", commonList.get(0).getColumnName());
		Assert.assertEquals("汎用マスタ名称が正しく設定されること", "テストマスタ1", commonList.get(0).getArticleName());
		Assert.assertEquals("カラム名が正しく設定されること", "test_column3", commonList.get(1).getColumnName());
		Assert.assertEquals("汎用マスタ名称が正しく設定されること", "テストマスタ3", commonList.get(1).getArticleName());

		int index1 = 0;
		int index2 = 1;
		if (isAddBlankRow) {
			index1++;
			index2++;
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 3, commonList.get(0).getCommonMasterDetailResultList().size());
			Assert.assertEquals("汎用マスタ明細値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailResultList().get(0).getCodeValue());
			Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailResultList().get(0).getDisplayValue());
			Assert.assertEquals("汎用マスタ明細ソート順が正しく設定されること", -1, commonList.get(0).getCommonMasterDetailResultList().get(0).getDisplayOrder().intValue());
		} else {
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 2, commonList.get(0).getCommonMasterDetailResultList().size());
		}

		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "1", commonList.get(0).getCommonMasterDetailResultList().get(index1).getCodeValue());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "項目1", commonList.get(0).getCommonMasterDetailResultList().get(index1).getDisplayValue());
		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "5", commonList.get(0).getCommonMasterDetailResultList().get(index2).getCodeValue());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "項目5", commonList.get(0).getCommonMasterDetailResultList().get(index2).getDisplayValue());
	}

	private void MoM汎用マスタ結果確認(List<CommonMasterResult> commonList, boolean isAddBlankRow, boolean isPlural) {
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", !isPlural ? 1 : 2, commonList.size());
		Assert.assertEquals("汎用マスタIDが正しく設定されること", "JF-CONSUMPTION_TAX_CTGR", commonList.get(0).getColumnName());
		Assert.assertEquals("汎用マスタ名称が正しく設定されること", "税区分", commonList.get(0).getArticleName());

		int index1 = 0;
		int index2 = 1;
		if (isAddBlankRow) {
			index1++;
			index2++;
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 6, commonList.get(0).getCommonMasterDetailResultList().size());
			Assert.assertEquals("汎用マスタ明細値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailResultList().get(0).getCodeValue());
			Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailResultList().get(0).getDisplayValue());
			Assert.assertEquals("汎用マスタ明細ソート順が正しく設定されること", -1, commonList.get(0).getCommonMasterDetailResultList().get(0).getDisplayOrder().intValue());
		} else {
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 5, commonList.get(0).getCommonMasterDetailResultList().size());
		}

		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "0", commonList.get(0).getCommonMasterDetailResultList().get(index1).getCodeValue());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "対象外", commonList.get(0).getCommonMasterDetailResultList().get(index1).getDisplayValue());
		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "1", commonList.get(0).getCommonMasterDetailResultList().get(index2).getCodeValue());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "外税", commonList.get(0).getCommonMasterDetailResultList().get(index2).getDisplayValue());
	}
}
