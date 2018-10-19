package jp.co.ricoh.cotos.commonlib.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

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
import org.springframework.transaction.annotation.Transactional;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkTypeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.BusinessCalendar;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.CommonMasterDetail;
import jp.co.ricoh.cotos.commonlib.entity.master.ContractChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.DispUrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.EstimationChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.GpCheckMatterMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.JwtSysAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci101Master;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci105Master;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb010UtlItem;
import jp.co.ricoh.cotos.commonlib.entity.master.MvTjmmb020UtlCd;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.RecordDecomposeCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.RecordDecomposeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.SuperUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Domain;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VPicAffiliateMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteGrpMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteNodeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementWorkCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ArrangementWorkTypeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.AuthPatternMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.BusinessCalendarRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.CommonMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ContractChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.DispUrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.EstimationChecklistCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.GpCheckMatterMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.JwtSysAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MailTemplateMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci101MasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci105Repository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb010UtlItemRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTjmmb020UtlCdRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductGrpMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.RecordDecomposeCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.RecordDecomposeMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.UrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.VKjbMasterRepository;
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

	@Autowired
	DBUtil dbUtil;

	@Autowired
	EntityManager entityManager;

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
	private JwtSysAuthMasterRepository jwtSysAuthMasterRepository;
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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void JwtSysAuthMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/jwtSysAuthMaster.sql");

		String id = "cotos_test";
		JwtSysAuthMaster found = jwtSysAuthMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void MailTemplateMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailTemplateMaster.sql");

		Long id = 1L;
		MailTemplateMaster found = mailTemplateMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void BusinessCalendarRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/businessCalendar.sql");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date id = sdf.parse("2018-09-20");

		BusinessCalendar found = businessCalendarRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		Assert.assertTrue(found.getBusinessDay() != null && found.getBusinessHoliday() != null
				&& found.getCalendarDate() != null);

	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void ArrangementChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ArrangementChecklistCompMaster found = arrangementChecklistCompMasterRepository.findOne(id);
		found = arrangementChecklistCompMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getArrangementWorkTypeMaster() == null)
			Assert.assertTrue(false);
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
		found = gpCheckMatterMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getArrangementChecklistCompMasterList() == null
				|| found.getArrangementChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getEstimationChecklistCompMasterList() == null
				|| found.getEstimationChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getContractChecklistCompMasterList() == null
				|| found.getContractChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void ArrangementWorkTypeMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ArrangementWorkTypeMaster found = arrangementWorkTypeMasterRepository.findOne(id);
		found = arrangementWorkTypeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getApprovalRouteGrpMaster() == null)
			Assert.assertTrue(false);
		if (found.getArrangementWorkCompMasterList() == null || found.getArrangementWorkCompMasterList() == null)
			Assert.assertTrue(false);
		if (found.getArrangementChecklistCompMasterList() == null
				|| found.getArrangementChecklistCompMasterList() == null)
			Assert.assertTrue(false);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/estimationChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");

		// エンティティの取得
		Long id = 1L;
		EstimationChecklistCompMaster found = estimationChecklistCompMasterRepository.findOne(id);
		found = estimationChecklistCompMasterRepository.findOne(id);

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
	@WithMockCustomUser
	@Transactional
	public void ContractChecklistCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/contractChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ContractChecklistCompMaster found = contractChecklistCompMasterRepository.findOne(id);
		found = contractChecklistCompMasterRepository.findOne(id);

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
	@WithMockCustomUser
	@Transactional
	public void ArrangementWorkCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/recordDecomposeCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/ApprovalRouteGrpMaster.sql");

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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
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
		//		if (found.getEstimationProductGrpMasterList() == null || found.getEstimationProductGrpMasterList().size() == 0)
		//			Assert.assertTrue(false);
		//		if (found.getContractProductGrpMasterList() == null || found.getContractProductGrpMasterList().size() == 0)
		//			Assert.assertTrue(false);
		if (found.getArrangementWorkTypeMasterList() == null || found.getArrangementWorkTypeMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void ProductCompMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");

		//		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteMaster.sql");
		//		context.getBean(DBConfig.class).initTargetTestData("repository/master/approvalRouteNodeMaster.sql");
		//		context.getBean(DBConfig.class).initTargetTestData("repository/master/arrangementWorkTypeMaster.sql");

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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void ProductMasterのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/contractChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/estimationChecklistCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/itemMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productCompMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/gpCheckMatterMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");

		// エンティティの取得
		Long id = 1L;
		ProductMaster found = productMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// Entity の リストとエンティティクラスの項目の値が null ではないことを確認
		if (found.getContractChecklistCompMasterList() == null
				|| found.getContractChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getEstimationChecklistCompMasterList() == null
				|| found.getEstimationChecklistCompMasterList().size() == 0)
			Assert.assertTrue(false);
		if (found.getItemMasterList() == null || found.getItemMasterList().size() == 0)
			Assert.assertTrue(false);
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void MvTJmci105Masterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "4930594";
		MvTJmci105Master found = mvTJmci105Repository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void MvTJmci101Masterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "1";
		MvTJmci101Master found = mvTJmci101MasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void MvTjmmb010UtlItemのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "JMM_PH23_RELEASE";
		MvTjmmb010UtlItem found = mvTjmmb010UtlItemRepository.findByItemId(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
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
	@WithMockCustomUser
	@Transactional
	public void MvEmployeeMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "00150194";
		MvEmployeeMaster found = mvEmployeeMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void VPicAffiliateMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "0263975";
		VPicAffiliateMaster found = vPicAffiliateMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void VKjbMasterのテスト() throws Exception {

		// テストデータはなし

		// エンティティの取得
		String id = "000000003985825";
		VKjbMaster found = vKjbMasterRepository.findOne(id);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	public static void main(String[] args) throws Exception {
		TestTools tool = new TestTools();
		ProductCompMaster entity = new ProductCompMaster();
		entity.setCreatedAt(Calendar.getInstance().getTime());
		entity.setCreatedUserId("");
		entity.setId(1L);
		entity.setProductGrpMaster(new ProductGrpMaster());
		entity.setUpdatedAt(Calendar.getInstance().getTime());
		entity.setUpdatedUserId("");
		entity.setVersion(1L);
		System.out.println(tool.findNullProperties(entity));
	}

}
