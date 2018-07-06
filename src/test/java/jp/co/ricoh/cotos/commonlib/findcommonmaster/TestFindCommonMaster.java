package jp.co.ricoh.cotos.commonlib.findcommonmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.logic.findcommonmaster.FindCommonMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFindCommonMaster {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	FindCommonMaster findCommonMaster;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}

	@Test
	@Transactional
	public void 汎用マスタ取得単一空行なし() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		汎用マスタデータ作成();

		List<Long> commonMasterIdList = new ArrayList<>();
		commonMasterIdList.add(1L);
		List<CommonMaster> commonList = findCommonMaster.findCommonMaster(commonMasterIdList, false);
		汎用マスタ結果確認(commonList, false, false);
	}

	@Test
	@Transactional
	public void 汎用マスタ取得単一空行あり() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		汎用マスタデータ作成();

		List<Long> commonMasterIdList = new ArrayList<>();
		commonMasterIdList.add(1L);
		List<CommonMaster> commonList = findCommonMaster.findCommonMaster(commonMasterIdList, true);
		汎用マスタ結果確認(commonList, true, false);
	}

	@Test
	@Transactional
	public void 汎用マスタ取得複数() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		汎用マスタデータ作成();

		List<Long> commonMasterIdList = new ArrayList<>();
		commonMasterIdList.add(1L);
		commonMasterIdList.add(2L);
		List<CommonMaster> commonList = findCommonMaster.findCommonMaster(commonMasterIdList, false);
		汎用マスタ結果確認(commonList, false, true);
	}

	@Test
	@Transactional
	public void 汎用マスタ取得一致データなし() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		汎用マスタデータ作成();

		List<Long> commonMasterIdList = new ArrayList<>();
		commonMasterIdList.add(4L);
		List<CommonMaster> commonList = findCommonMaster.findCommonMaster(commonMasterIdList, false);
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", 0, commonList.size());
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得単一空行なし() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		MoM汎用マスタデータ作成();
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得単一空行あり() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		MoM汎用マスタデータ作成();
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得複数() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		MoM汎用マスタデータ作成();
	}

	@Test
	@Transactional
	public void MoM汎用マスタ取得一致データなし() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		MoM汎用マスタデータ作成();
	}

	private void 汎用マスタデータ作成() {
		dbUtil.execute("sql/findcommonmaster/testCommonMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/findcommonmaster/testCommonMasterDetailInsert.sql", new HashMap<>());
	}

	private void MoM汎用マスタデータ作成() {

	}

	private void 汎用マスタ結果確認(List<CommonMaster> commonList, boolean isAddBlankRow, boolean isPlural) {
		Assert.assertEquals("汎用マスタ取得件数が正しいこと", !isPlural ? 1 : 2, commonList.size());
		Assert.assertEquals("汎用マスタIDが正しく設定されること", 1L, commonList.get(0).getId());
		Assert.assertEquals("汎用マスタ名称が正しく設定されること", "テストマスタ1", commonList.get(0).getName());
		Assert.assertEquals("汎用マスタ説明が正しく設定されること", "テスト1", commonList.get(0).getDescription());

		int index1 = 0;
		int index2 = 1;
		if (isAddBlankRow) {
			index1++;
			index2++;
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 3, commonList.get(0).getCommonMasterDetailList().size());
			Assert.assertEquals("汎用マスタ明細IDが正しく設定されること", 0L, commonList.get(0).getCommonMasterDetailList().get(0).getId());
			Assert.assertEquals("汎用マスタ明細値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailList().get(0).getCode());
			Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", null, commonList.get(0).getCommonMasterDetailList().get(0).getColumn1());
		} else {
			Assert.assertEquals("汎用マスタ明細取得件数が正しいこと", 2, commonList.get(0).getCommonMasterDetailList().size());
		}

		Assert.assertEquals("汎用マスタ明細IDが正しく設定されること", 5L, commonList.get(0).getCommonMasterDetailList().get(index1).getId());
		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "1", commonList.get(0).getCommonMasterDetailList().get(index1).getCode());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "項目1", commonList.get(0).getCommonMasterDetailList().get(index1).getColumn1());
		Assert.assertEquals("汎用マスタ明細IDが正しく設定されること", 1L, commonList.get(0).getCommonMasterDetailList().get(index2).getId());
		Assert.assertEquals("汎用マスタ明細値が正しく設定されること", "5", commonList.get(0).getCommonMasterDetailList().get(index2).getCode());
		Assert.assertEquals("汎用マスタ明細表示値が正しく設定されること", "項目5", commonList.get(0).getCommonMasterDetailList().get(index2).getColumn1());
	}
}
