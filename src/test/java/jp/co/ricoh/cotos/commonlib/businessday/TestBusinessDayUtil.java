package jp.co.ricoh.cotos.commonlib.businessday;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.logic.businessday.BusinessDayUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestBusinessDayUtil {

	@Autowired
	BusinessDayUtil businessDayUtil;

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
	public void 営業日判定() throws Exception {
		テストデータ作成();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse("2019/01/01");
		Assert.assertEquals("非営業日であること", false, businessDayUtil.isBusinessDay(date));
		date = sdf.parse("2019/01/02");
		Assert.assertEquals("営業日であること", true, businessDayUtil.isBusinessDay(date));
	}

	@Test
	@Transactional
	public void 最短営業日取得() throws Exception {
		テストデータ作成();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse("2019/06/27");
		Assert.assertEquals("最短営業日_加算が正しいこと", sdf.parse("2019/07/04"), businessDayUtil.findShortestBusinessDay(date, 5, false));
		Assert.assertEquals("最短営業日_減算が正しいこと", sdf.parse("2019/06/20"), businessDayUtil.findShortestBusinessDay(date, 5, true));
	}

	@Test
	@Transactional
	public void 最短営業日取得_時間計算() throws Exception {
		テストデータ作成();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse("2019/06/27");
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date baseDateTime = sdfTime.parse("2019/06/27 11:59:59");
		Assert.assertEquals("最短営業日(午前)が正しいこと", sdf.parse("2019/07/04"), businessDayUtil.findShortestBusinessDayTimeCalc(date, 5, baseDateTime));
		baseDateTime = sdfTime.parse("2019/06/27 12:00:00");
		Assert.assertEquals("最短営業日(午後)が正しいこと", sdf.parse("2019/07/05"), businessDayUtil.findShortestBusinessDayTimeCalc(date, 5, baseDateTime));
		date = sdf.parse("2019/07/05");
		baseDateTime = sdfTime.parse("2019/07/05 12:00:00");
		Assert.assertEquals("最短営業日_加算時営業日考慮(午後)が正しいこと", sdf.parse("2019/07/16"), businessDayUtil.findShortestBusinessDayTimeCalc(date, 5, baseDateTime));
	}

	@Test
	public void 月末最終営業日取得() {
		業務カレンダーマスタ関連テストデータ作成();
		// 引数
		List<String> argList = Arrays.asList("2019/06", "2019/07", "2019/08", "2019/09", "2019/10", "2019/11", "2019/12", "2020/01", "2020/02", "2020/03");
		// 期待値
		List<String> expectList = Arrays.asList("2019/06/28", "2019/07/31", "2019/08/30", "2019/09/30", "2019/10/31", "2019/11/29", "2019/12/27", "2020/01/31", "2020/02/28", "2020/03/31");
		for (int index = 0; index < argList.size(); index++) {
			Date result = businessDayUtil.getLastBusinessDayOfTheMonth(argList.get(index));
			Assert.assertEquals(argList.get(index) + "の想定通りの月末最終営業日が取得できること。", 日付想定値取得(expectList.get(index)), result);
		}

		Assert.assertEquals("年月がyyyyMM形式でも月末最終営業日が取得できること", 日付想定値取得("2019/06/28"), businessDayUtil.getLastBusinessDayOfTheMonth("201906"));
		Assert.assertEquals("年月がyyyy-MM形式でも月末最終営業日が取得できること", 日付想定値取得("2019/06/28"), businessDayUtil.getLastBusinessDayOfTheMonth("2019-06"));
		Assert.assertNull("業務カレンダーマスタに登録されていない年月の場合はnullが返ること", businessDayUtil.getLastBusinessDayOfTheMonth("2919/06"));
	}

	private Date 日付想定値取得(String expectrd) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(StringUtils.substring(expectrd, 0, 4)), Integer.parseInt(StringUtils.substring(expectrd, 5, 7)) - 1, Integer.parseInt(StringUtils.substring(expectrd, 8, 10)), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private void テストデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/businessday/testNonBusinessDayCalendarMasterInsert.sql");
	}

	private void 業務カレンダーマスタ関連テストデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/businessday/testBusinessCalenderMasterInsert.sql");
	}
}
