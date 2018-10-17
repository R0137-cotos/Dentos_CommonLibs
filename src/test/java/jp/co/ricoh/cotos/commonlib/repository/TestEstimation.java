package jp.co.ricoh.cotos.commonlib.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.estimation.OperationLog;
import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster;
import jp.co.ricoh.cotos.commonlib.repository.estimation.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.OperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.AuthPatternMasterRepository;

/**
 * Repository（見積ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestEstimation {

	@Autowired
	DBUtil dbUtil;

	@Autowired
	EntityManager entityManager;

	static ConfigurableApplicationContext context;

	/*
	 * @Autowired private ApprovalRouteGrpMasterRepository
	 * approvalRouteGrpMasterRepository;
	 * 
	 * @Autowired private ApprovalRouteMasterRepository
	 * approvalRouteMasterRepository;
	 * 
	 * @Autowired private ApprovalRouteNodeMasterRepository
	 * approvalRouteNodeMasterRepository;
	 * 
	 * @Autowired private ArrangementChecklistCompMasterRepository
	 * arrangementChecklistCompMasterRepository;
	 * 
	 * @Autowired private ArrangementWorkCompMasterRepository
	 * arrangementWorkCompMasterRepository;
	 * 
	 * @Autowired private ArrangementWorkTypeMasterRepository
	 * arrangementWorkTypeMasterRepository;
	 * 
	 * @Autowired private AuthPatternMasterRepository authPatternMasterRepository;
	 * 
	 * @Autowired private BusinessCalendarRepository businessCalendarRepository;
	 * 
	 * @Autowired private CommonMasterDetailRepository commonMasterDetailRepository;
	 * 
	 * @Autowired private CommonMasterRepository commonMasterRepository;
	 * 
	 * @Autowired private ContractChecklistCompMasterRepository
	 * contractChecklistCompMasterRepository;
	 * 
	 * @Autowired private DispUrlAuthMasterRepository dispUrlAuthMasterRepository;
	 * 
	 * @Autowired private EstimationChecklistCompMasterRepository
	 * estimationChecklistCompMasterRepository;
	 * 
	 * @Autowired private GpCheckMatterMasterRepository
	 * gpCheckMatterMasterRepository;
	 * 
	 * @Autowired private ItemMasterRepository itemMasterRepository;
	 * 
	 * @Autowired private JwtSysAuthMasterRepository jwtSysAuthMasterRepository;
	 * 
	 * @Autowired private MailTemplateMasterRepository mailTemplateMasterRepository;
	 * 
	 * @Autowired private MvEmployeeMasterRepository mvEmployeeMasterRepository;
	 * 
	 * @Autowired private MvTJmci101MasterRepository mvTJmci101MasterRepository;
	 * 
	 * @Autowired private MvTJmci105Repository mvTJmci105Repository;
	 * 
	 * @Autowired private MvTjmmb010UtlItemRepository mvTjmmb010UtlItemRepository;
	 * 
	 * @Autowired private MvTjmmb020UtlCdRepository mvTjmmb020UtlCdRepository;
	 * 
	 * @Autowired private ProductCompMasterRepository productCompMasterRepository;
	 * 
	 * @Autowired private ProductGrpMasterRepository productGrpMasterRepository;
	 * 
	 * @Autowired private ProductMasterRepository productMasterRepository;
	 * 
	 * @Autowired private RecordDecomposeCompMasterRepository
	 * recordDecomposeCompMasterRepository;
	 * 
	 * @Autowired private RecordDecomposeMasterRepository
	 * recordDecomposeMasterRepository;
	 * 
	 * @Autowired private SuperUserMasterRepository superUserMasterRepository;
	 * 
	 * @Autowired private UrlAuthMasterRepository urlAuthMasterRepository;
	 * 
	 * @Autowired private VKbMasterRepository vKbMasterRepository;
	 * 
	 * @Autowired private VPicAffiliateMasterRepository
	 * vPicAffiliateMasterRepository;
	 */
	
	//@Autowired private AttachedFileRepository attachedFileRepository;

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
	public void AttachedFileRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationAttachedFile.sql");
		
		// リポジトリ作成
		AttachedFileRepository repository = context.getBean(AttachedFileRepository.class);
		EstimationAttachedFile found = repository.findOne(401L);
		Assert.assertNotNull(found);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void OperationLogRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/OperationLog.sql");
		
		// リポジトリ作成
		OperationLogRepository repository = context.getBean(OperationLogRepository.class);
		OperationLog found = repository.findOne(401L);
		Assert.assertNotNull(found);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	/*
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void CommonMasterRepositoryのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/commonMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/commonMasterDetail.sql");
	 * 
	 * Long id = 1L; CommonMaster found = commonMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getCommonMasterDetailList() == null ||
	 * found.getCommonMasterDetailList().size() == 0) Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void JwtSysAuthMasterRepositoryのテスト() throws Exception
	 * {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/jwtSysAuthMaster.sql");
	 * 
	 * String id = "cotos_test"; JwtSysAuthMaster found =
	 * jwtSysAuthMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void CommonMasterDetailRepositoryのテスト() throws
	 * Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/commonMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/commonMasterDetail.sql");
	 * 
	 * Long id = 1L; CommonMasterDetail found =
	 * commonMasterDetailRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目が値が null ではないことを確認 if (found.getCommonMaster() ==
	 * null) Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void MailTemplateMasterRepositoryのテスト() throws
	 * Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/mailTemplateMaster.sql");
	 * 
	 * Long id = 1L; MailTemplateMaster found =
	 * mailTemplateMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void BusinessCalendarRepositoryのテスト() throws Exception
	 * {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/businessCalendar.sql");
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date id =
	 * sdf.parse("2018-09-20");
	 * 
	 * BusinessCalendar found = businessCalendarRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 Assert.assertTrue(found.getBusinessDay() !=
	 * null && found.getBusinessHoliday() != null && found.getCalendarDate() !=
	 * null);
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void SuperUserMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/superUserMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; SuperUserMaster found =
	 * superUserMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void UrlAuthMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/urlAuthMaster.sql");
	 * 
	 * // エンティティの取得 UrlAuthMaster.Id id = new UrlAuthMaster.Id();
	 * id.setUrlPattern("/api/estimation$"); id.setMethod(HttpMethod.GET);
	 * id.setDomain(Domain.estimation); UrlAuthMaster found =
	 * urlAuthMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void AuthPatternMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/authPatternMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/dispUrlAuthMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; AuthPatternMaster found =
	 * authPatternMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getAuthPatternMasterList() == null ||
	 * found.getAuthPatternMasterList().size() == 0) Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void DispUrlAuthMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/authPatternMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/dispUrlAuthMaster.sql");
	 * 
	 * // エンティティの取得 DispUrlAuthMaster.Id id = new DispUrlAuthMaster.Id();
	 * id.setSystemDomain("cotos.ricoh.co.jp");
	 * id.setUrlPattern("api/estimation/[0-9]+"); id.setActionId("test_action_001");
	 * DispUrlAuthMaster found = dispUrlAuthMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getAuthPatternMaster() == null) Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void ArrangementChecklistCompMasterのテスト() throws
	 * Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementChecklistCompMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementWorkTypeMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/gpCheckMatterMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; ArrangementChecklistCompMaster found =
	 * arrangementChecklistCompMasterRepository.findOne(id); found =
	 * arrangementChecklistCompMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getArrangementWorkTypeMaster() == null) Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void GpCheckMatterMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/gpCheckMatterMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementChecklistCompMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/estimationChecklistCompMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/contractChecklistCompMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; GpCheckMatterMaster found =
	 * gpCheckMatterMasterRepository.findOne(id); found =
	 * gpCheckMatterMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getArrangementChecklistCompMasterList() == null ||
	 * found.getArrangementChecklistCompMasterList().size() == 0)
	 * Assert.assertTrue(false); if (found.getEstimationChecklistCompMasterList() ==
	 * null || found.getEstimationChecklistCompMasterList().size() == 0)
	 * Assert.assertTrue(false); if (found.getContractChecklistCompMasterList() ==
	 * null || found.getContractChecklistCompMasterList().size() == 0)
	 * Assert.assertTrue(false); }
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void ArrangementWorkTypeMasterのテスト() throws Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementWorkTypeMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/approvalRouteGrpMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementWorkCompMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/arrangementChecklistCompMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; ArrangementWorkTypeMaster found =
	 * arrangementWorkTypeMasterRepository.findOne(id); found =
	 * arrangementWorkTypeMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if
	 * (found.getApprovalRouteGrpMaster() == null) Assert.assertTrue(false); if
	 * (found.getArrangementWorkCompMasterList() == null ||
	 * found.getArrangementWorkCompMasterList() == null) Assert.assertTrue(false);
	 * if (found.getArrangementChecklistCompMasterList() == null ||
	 * found.getArrangementChecklistCompMasterList() == null)
	 * Assert.assertTrue(false); }
	 * 
	 * 
	 * @Test
	 * 
	 * @WithMockCustomUser
	 * 
	 * @Transactional public void EstimationChecklistCompMasterのテスト() throws
	 * Exception {
	 * 
	 * // テストデータ登録 context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/estimationChecklistCompMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/productMaster.sql");
	 * context.getBean(DBConfig.class).initTargetTestData(
	 * "repository/master/gpCheckMatterMaster.sql");
	 * 
	 * // エンティティの取得 Long id = 1L; EstimationChecklistCompMaster found =
	 * estimationChecklistCompMasterRepository.findOne(id);
	 * 
	 * // Entity が null ではないことを確認 Assert.assertNotNull(found);
	 * 
	 * // Entity の各項目の値が null ではないことを確認 testTool.assertColumnsNotNull(found);
	 * 
	 * // Entity の リストとエンティティクラスの項目の値が null ではないことを確認 if (found.getProductMaster()
	 * == null) Assert.assertTrue(false); if (found.getGpCheckMatterMaster() ==
	 * null) Assert.assertTrue(false);
	 * 
	 * }
	 */

	/*
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
	*/

}
