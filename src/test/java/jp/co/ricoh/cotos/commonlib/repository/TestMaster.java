package jp.co.ricoh.cotos.commonlib.repository;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.entity.master.AppMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkTypeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.BusinessCalendar;
import jp.co.ricoh.cotos.commonlib.entity.master.CeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.ContractChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.DispUrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.DummyUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.EquipmentCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.EstimationChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ExtendsParameterCorrelationCheckMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.GpCheckMatterMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.IfsCsvMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemTransCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.JsonSchemaMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailControlMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailConvertValueMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ModelAbbreviationMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci101Master;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci105Master;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb010UtlItem;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb020UtlCd;
import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc020OrgAllInfoCom;
import jp.co.ricoh.cotos.commonlib.entity.master.NonBusinessDayCalendarMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.RecordDecomposeCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.RecordDecomposeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportPageMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.SuperUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.SystemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Domain;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VPicAffiliateMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VPicAffiliateMasterFull;
import jp.co.ricoh.cotos.commonlib.repository.master.AppMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteGrpMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteNodeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementWorkCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementWorkTypeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.AuthPatternMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.BusinessCalendarRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ContractChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.DispUrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.DummyUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.EquipmentCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.EstimationChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ExtendsParameterCorrelationCheckMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.GpCheckMatterMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.IfsCsvMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemTransCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.JsonSchemaMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MailControlMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MailConvertValueMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MailTemplateMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ModelAbbreviationMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci101MasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci105Repository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb010UtlItemRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb020UtlCdRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvWjmoc020OrgAllInfoComRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.NonBusinessDayCalendarMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductGrpMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.RecordDecomposeCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.RecordDecomposeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ReportPageMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ReportTemplateMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.SystemMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.UrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.VPicAffiliateMasterFullRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.VPicAffiliateMasterRepository;

/**
 * Repository（マスタドメイン）のテストクラス
 *
 * @author hideto.yamanaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMaster {

	static ConfigurableApplicationContext context;

	@Autowired
	private ApprovalRouteGrpMasterRepository approvalRouteGrpMasterRepository;
	@Autowired
	private ApprovalRouteMasterRepository approvalRouteMasterRepository;
	@Autowired
	private ApprovalRouteNodeMasterRepository approvalRouteNodeMasterRepository;
	@Autowired
	private ArrangementChecklistCompMasterRepository arrangementChecklistCompMasterRepository;
	@Autowired
	private ArrangementWorkCompMasterRepository arrangementWorkCompMasterRepository;
	@Autowired
	private ArrangementWorkTypeMasterRepository arrangementWorkTypeMasterRepository;
	@Autowired
	private AuthPatternMasterRepository authPatternMasterRepository;
	@Autowired
	private BusinessCalendarRepository businessCalendarRepository;
	@Autowired
	private CommonMasterDetailRepository commonMasterDetailRepository;
	@Autowired
	private CommonMasterRepository commonMasterRepository;
	@Autowired
	private ContractChecklistCompMasterRepository contractChecklistCompMasterRepository;
	@Autowired
	private DispUrlAuthMasterRepository dispUrlAuthMasterRepository;
	@Autowired
	private EstimationChecklistCompMasterRepository estimationChecklistCompMasterRepository;
	@Autowired
	private GpCheckMatterMasterRepository gpCheckMatterMasterRepository;
	@Autowired
	private ItemMasterRepository itemMasterRepository;
	@Autowired
	private SystemMasterRepository systemMasterRepository;
	@Autowired
	private AppMasterRepository appMasterRepository;
	@Autowired
	private MailTemplateMasterRepository mailTemplateMasterRepository;
	@Autowired
	private MvEmployeeMasterRepository mvEmployeeMasterRepository;
	@Autowired
	private MvTJmci101MasterRepository mvTJmci101MasterRepository;
	@Autowired
	private MvTJmci105Repository mvTJmci105Repository;
	@Autowired
	private MvTjmmb010UtlItemRepository mvTjmmb010UtlItemRepository;
	@Autowired
	private MvTjmmb020UtlCdRepository mvTjmmb020UtlCdRepository;
	@Autowired
	private ProductCompMasterRepository productCompMasterRepository;
	@Autowired
	private ProductGrpMasterRepository productGrpMasterRepository;
	@Autowired
	private ProductMasterRepository productMasterRepository;
	@Autowired
	private RecordDecomposeCompMasterRepository recordDecomposeCompMasterRepository;
	@Autowired
	private RecordDecomposeMasterRepository recordDecomposeMasterRepository;
	@Autowired
	private SuperUserMasterRepository superUserMasterRepository;
	@Autowired
	private UrlAuthMasterRepository urlAuthMasterRepository;
	@Autowired
	private VKjbMasterRepository vKjbMasterRepository;
	@Autowired
	private VPicAffiliateMasterRepository vPicAffiliateMasterRepository;
	@Autowired
	private VPicAffiliateMasterFullRepository vPicAffiliateMasterFullRepository;
	@Autowired
	private MailControlMasterRepository mailControlMasterRepository;
	@Autowired
	private MailConvertValueMasterRepository mailConvertValueMasterRepository;
	@Autowired
	private DummyUserMasterRepository dummyUserMasterRepository;
	@Autowired
	private JsonSchemaMasterRepository jsonSchemaMasterRepository;
	@Autowired
	private ExtendsParameterCorrelationCheckMasterRepository extendsParameterCorrelationCheckMasterRepository;
	@Autowired
	private CeMasterRepository ceMasterRepository;
	@Autowired
	private ModelAbbreviationMasterRepository modelAbbreviationMasterRepository;
	@Autowired
	private EquipmentCompMasterRepository equipmentCompMasterRepository;
	@Autowired
	private ItemTransCompMasterRepository itemTransCompMasterRepository;
	@Autowired
	private ReportTemplateMasterRepository reportTemplateMasterRepository;
	@Autowired
	private ReportPageMasterRepository reportPageMasterRepository;
	@Autowired
	private MvWjmoc020OrgAllInfoComRepository mvWjmoc020OrgAllInfoComRepository;
	@Autowired
	private IfsCsvMasterRepository ifsCsvMasterRepository;
	@Autowired
	private NonBusinessDayCalendarMasterRepository nonBusinessDayCalendarMasterRepository;

	@Autowired
	TestTools testTool = null;

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
	public void CommonMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMasterDetail.sql");

		Long id = 1L;
		CommonMaster found = commonMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getCommonMasterDetailList() == null || found.getCommonMasterDetailList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void SystemMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/systemMaster.sql");

		String id = "cotos";
		SystemMaster found = systemMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void AppMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/systemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/appMaster.sql");

		String id = "cotos_test";
		AppMaster found = appMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void CommonMasterDetailRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMasterDetail.sql");

		Long id = 1L;
		CommonMasterDetail found = commonMasterDetailRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目が値が null ではないことを確認
		if (found.getCommonMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void CommonMasterDetailRepository_CommonMasterDetailRepositoryのテスト() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMasterDetail.sql");
		CommonMasterDetail found = commonMasterDetailRepository.findByCommonMasterColumnNameAndAvailablePeriodBetween("sales_tax_rate", "20190101");
		Assert.assertEquals("取得データの税率が8(%)であること", found.getCodeValue(), "8");
	}

	@Test
	public void MailTemplateMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailTemplateMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailControlMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailConvertValueMaster.sql");

		Long id = 1L;
		MailTemplateMaster found = mailTemplateMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void BusinessCalendarRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/businessCalendar.sql");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date id = sdf.parse("2018-09-20");

		BusinessCalendar found = businessCalendarRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		Assert.assertTrue(found.getBusinessDay() != null && found.getCalendarDate() != null);

	}

	@Test
	public void SuperUserMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/superUserMaster.sql");

		// エンティティの取得
		Long id = 1L;
		SuperUserMaster found = superUserMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// エンティティ取得
		found = superUserMasterRepository.findByUserId("MOM_EMPLOYEE_ID");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void UrlAuthMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/urlAuthMaster.sql");

		// エンティティの取得
		UrlAuthMaster.Id id = new UrlAuthMaster.Id();
		id.setUrlPattern("/api/estimation$");
		id.setMethod(HttpMethod.GET);
		id.setDomain(Domain.estimation);
		UrlAuthMaster found = urlAuthMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void AuthPatternMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/authPatternMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/dispUrlAuthMaster.sql");

		// エンティティの取得
		Long id = 1L;
		AuthPatternMaster found = authPatternMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getAuthPatternMasterList() == null || found.getAuthPatternMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void DispUrlAuthMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/authPatternMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/dispUrlAuthMaster.sql");

		// エンティティの取得
		DispUrlAuthMaster.Id id = new DispUrlAuthMaster.Id();
		id.setSystemDomain("cotos.ricoh.co.jp");
		id.setUrlPattern("api/estimation/[0-9]+");
		id.setActionId("test_action_001");
		DispUrlAuthMaster found = dispUrlAuthMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getAuthPatternMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ArrangementChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ArrangementChecklistCompMaster found = arrangementChecklistCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getArrangementWorkTypeMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void GpCheckMatterMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/estimationChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/contractChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		GpCheckMatterMaster found = gpCheckMatterMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getArrangementChecklistCompMasterList() == null || found.getArrangementChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getEstimationChecklistCompMasterList() == null || found.getEstimationChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getContractChecklistCompMasterList() == null || found.getContractChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void ArrangementWorkTypeMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ArrangementWorkTypeMaster found = arrangementWorkTypeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getApprovalRouteGrpMaster() == null)
			Assert.assertTrue(false);
		if (found.getArrangementWorkCompMasterList() == null || found.getArrangementWorkCompMasterList() == null)
			Assert.assertTrue(false);
		if (found.getArrangementChecklistCompMasterList() == null || found.getArrangementChecklistCompMasterList() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void EstimationChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/estimationChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");

		// エンティティの取得
		Long id = 1L;
		EstimationChecklistCompMaster found = estimationChecklistCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getProductMaster() == null)
			Assert.assertTrue(false);
		if (found.getGpCheckMatterMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ContractChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/contractChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ContractChecklistCompMaster found = contractChecklistCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getProductMaster() == null)
			Assert.assertTrue(false);
		if (found.getGpCheckMatterMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ArrangementWorkCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ArrangementWorkCompMaster found = arrangementWorkCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getItemMaster() == null)
			Assert.assertTrue(false);
		if (found.getArrangementWorkTypeMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ApprovalRouteNodeMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteNodeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ApprovalRouteNodeMaster found = approvalRouteNodeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getApprovalRouteMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ApprovalRouteMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteNodeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ApprovalRouteMaster found = approvalRouteMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getApprovalRouteGrpMaster() == null)
			Assert.assertTrue(false);
		if (found.getApprovalRouteNodeMasterList() == null || found.getApprovalRouteNodeMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void ApprovalRouteGrpMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteNodeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ApprovalRouteGrpMaster found = approvalRouteGrpMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getApprovalRouteMasterList() == null || found.getApprovalRouteMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getArrangementWorkTypeMasterList() == null || found.getArrangementWorkTypeMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void ProductCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ProductCompMaster found = productCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getProductGrpMaster() == null)
			Assert.assertTrue(false);
		if (found.getProductMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ProductGrpMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteNodeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ProductGrpMaster found = productGrpMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getContractApprovalRouteGrpMaster() == null)
			Assert.assertTrue(false);
		if (found.getEstimationApprovalRouteGrpMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void ProductMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/contractChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/estimationChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/jsonMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/extendsParameterCorrelationCheckMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/ifsCsvMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ProductMaster found = productMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getContractChecklistCompMasterList() == null || found.getContractChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getEstimationChecklistCompMasterList() == null || found.getEstimationChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getItemMasterList() == null || found.getItemMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getJsonSchemaMasterList() == null || found.getJsonSchemaMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getExtendsParameterCorrelationCheckMasterList() == null || found.getExtendsParameterCorrelationCheckMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void ItemMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ItemMaster found = itemMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getArrangementWorkCompMasterList() == null || found.getArrangementWorkCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getProductMaster() == null)
			Assert.assertTrue(false);
		if (found.getRecordDecomposeCompMasterList() == null || found.getRecordDecomposeCompMasterList().size() == 0)
			Assert.assertTrue(false);
		
		String itemCd = "CP6572";
		ItemMaster found2 = itemMasterRepository.findByProductMasterIdAndRicohItemCode(id, itemCd);
		// Entity が null ではないことを確認
		Assert.assertNotNull(found2);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found2);
	}

	@Test
	public void RecordDecomposeCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeMaster.sql");

		// エンティティの取得
		Long id = 1L;
		RecordDecomposeCompMaster found = recordDecomposeCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getItemMaster() == null)
			Assert.assertTrue(false);
		if (found.getRecordDecomposeMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	public void RecordDecomposeMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeMaster.sql");

		// エンティティの取得
		Long id = 1L;
		RecordDecomposeMaster found = recordDecomposeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getRecordDecomposeCompMasterList() == null || found.getRecordDecomposeCompMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	public void MvTJmci105Masterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "4930594";
		MvTJmci105Master found = mvTJmci105Repository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void MvTJmci101Masterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "1";
		MvTJmci101Master found = mvTJmci101MasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void MvTjmmb010UtlItemのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "JMM_PH23_RELEASE";
		MvTjmmb010UtlItem found = mvTjmmb010UtlItemRepository.findByItemId(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void MvTjmmb020UtlItemのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		MvTjmmb020UtlCd.Id id = new MvTjmmb020UtlCd.Id();
		id.setItemId("JFCDM660");
		id.setCdVal("subject");
		MvTjmmb020UtlCd found = mvTjmmb020UtlCdRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test

	public void MvEmployeeMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "00150194";
		MvEmployeeMaster found = mvEmployeeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void VPicAffiliateMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "0263975";
		VPicAffiliateMaster found = vPicAffiliateMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void VPicAffiliateMasterFullのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "0263975";
		VPicAffiliateMasterFull found = vPicAffiliateMasterFullRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void VKjbMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "000000003985825";
		VKjbMaster found = vKjbMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void MailControlMasterのテスト() throws Exception {

		// テストデータ登録

		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailTemplateMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailControlMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailConvertValueMaster.sql");
		// エンティティの取得
		Long id = 1L;
		MailControlMaster found = mailControlMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void MailConvertValueMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailTemplateMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailControlMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailConvertValueMaster.sql");

		// エンティティの取得
		Long id = 1L;
		MailConvertValueMaster found = mailConvertValueMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getMailControlMaster() == null)
			Assert.assertTrue(false);

		// メール制御マスタからデータ取得
		MailControlMaster found2 = mailControlMasterRepository.findOne(id);
		List<MailConvertValueMaster> result = mailConvertValueMasterRepository.findByMailControlMaster(found2);
		// Entity が null ではないことを確認
		Assert.assertNotNull(result);
	}

	@Test
	public void ProductMasterRepositoryの条件テスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		List<String> appId = Arrays.asList("electric");
		List<ProductMaster> list = productMasterRepository.findByAppIdNotInOrderByIdAsc(appId);
		Assert.assertNotEquals(0, list.size());
		appId = Arrays.asList("cotos_dev");
		list = productMasterRepository.findByAppIdInOrderByIdAsc(appId);
		Assert.assertNotEquals(0, list.size());
	}

	@Test
	public void ArrangementWorkTypeMasterRepositoryの条件テスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		List<String> appId = Arrays.asList("electric");
		List<ArrangementWorkTypeMaster> list = arrangementWorkTypeMasterRepository.findByAppIdNotInOrderByIdAsc(appId);
		Assert.assertNotEquals(0, list.size());
		appId = Arrays.asList("cotos_dev");
		list = arrangementWorkTypeMasterRepository.findByAppIdInOrderByIdAsc(appId);
		Assert.assertNotEquals(0, list.size());
	}

	@Test
	public void DummyUserMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/dummyUserMaster.sql");

		// エンティティの取得
		Long id = 1L;
		DummyUserMaster found = dummyUserMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// エンティティ取得
		found = dummyUserMasterRepository.findByUserId("COTOS_BATCH_USER");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void JsonSchemaMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/jsonMaster.sql");

		// エンティティの取得
		Long id = 1L;
		JsonSchemaMaster found = jsonSchemaMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void ExtendsParameterCorrelationCheckMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/extendsParameterCorrelationCheckMaster.sql");

		// エンティティの取得
		ExtendsParameterCorrelationCheckMaster found = extendsParameterCorrelationCheckMasterRepository.findByProductMasterIdAndDomain(1L, "1");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void CeMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/ceMaster.sql");

		// エンティティの取得
		Long id = 1L;
		CeMaster found = ceMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void ModelAbbreviationMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/modelAbbreviationMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ModelAbbreviationMaster found = modelAbbreviationMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void EquipmentCompMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/equipmentCompMaster.sql");

		// エンティティの取得
		Long id = 1L;
		EquipmentCompMaster found = equipmentCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void ItemTransCompMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemTransCompMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ItemTransCompMaster found = itemTransCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void ReportTemplateMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/reportTemplateMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ReportTemplateMaster found = reportTemplateMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void ReportPageMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/reportTemplateMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/reportPageMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ReportPageMaster found = reportPageMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void MvWjmoc020OrgAllInfoComのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date formatDate = sdf.parse("2019/01/01");
		MvWjmoc020OrgAllInfoCom found = mvWjmoc020OrgAllInfoComRepository.findByOrgId("0913849", formatDate);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void CommonMaster_findByColumnNameAndServiceCategoryAndDetailCodeValuesのテスト() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/commonMasterDetail.sql");

		List<CommonMaster> foundList = commonMasterRepository.findByColumnNameAndServiceCategoryAndDetailCodeValues("issue_format", "1", Arrays.asList("1"));
		// データが取得できていることを確認
		Assert.assertTrue(foundList.size() > 0);

		// Entity の各項目の値が null ではないことを確認
		try {
			testTool.assertColumnsNotNull(foundList.get(0));
		} catch (Exception e) {
			Assert.fail("throw Exception :" + e.getMessage());
		}
	}

	@Test
	public void ReportTemplateMasterRepository_findByReportListParameterのテスト() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/reportTemplateMaster.sql");
		List<ReportTemplateMaster> foundList = reportTemplateMasterRepository.findByReportListParameter("1", "1", "1", "1", 1L, "1", "2");
		// データが取得できていることを確認
		Assert.assertTrue(foundList.size() > 0);

		// Entity の各項目の値が null ではないことを確認
		try {
			testTool.assertColumnsNotNull(foundList.get(0));
		} catch (Exception e) {
			Assert.fail("throw Exception :" + e.getMessage());
		}
	}

	@Test
	public void ProductGrpMasterRepository_findByProductGrpCodeのテスト() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		List<String> foundTestString = Arrays.asList("1234");
		List<ProductGrpMaster> foundList = productGrpMasterRepository.findByProductGrpCodeIn(foundTestString);
		// データが取得できていることを確認
		Assert.assertTrue(foundList.size() > 0);

		// Entity の各項目の値が null ではないことを確認
		try {
			testTool.assertColumnsNotNull(foundList.get(0));
		} catch (Exception e) {
			Assert.fail("throw Exception :" + e.getMessage());
		}

	}

	@Test
	public void IfsCsvMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/ifsCsvMaster.sql");

		// エンティティの取得
		Long id = 1L;
		IfsCsvMaster found = ifsCsvMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void NonBusinessDayCalendarMasterのテスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/nonBusinessDayCalendarMaster.sql");

		// エンティティの取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf.parse("2019/01/01");
		NonBusinessDayCalendarMaster found = nonBusinessDayCalendarMasterRepository.findOne(date);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}
}