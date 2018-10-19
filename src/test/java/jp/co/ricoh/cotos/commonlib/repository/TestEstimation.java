package jp.co.ricoh.cotos.commonlib.repository;

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
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ItemEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.OperationLog;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ProductEstimation;
import jp.co.ricoh.cotos.commonlib.repository.estimation.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.CustomerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.DealerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationPicSaEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.ItemEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.OperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.ProductEstimationRepository;

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

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

		// REVIEW:下記の必要性
		// Entity の リストとエンティティクラスの項目が値が null ではないことを確認
		// testTool.assertColumnsNotNull(found.getEstimation());
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void OperationLogRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/operationLog.sql");

		// リポジトリ作成
		OperationLogRepository repository = context.getBean(OperationLogRepository.class);
		OperationLog found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationAddedEditorEmpRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationAddedEditorEmp.sql");

		// リポジトリ作成
		EstimationAddedEditorEmpRepository repository = context.getBean(EstimationAddedEditorEmpRepository.class);
		EstimationAddedEditorEmp found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void DealerEstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/dealerEstimation.sql");

		// リポジトリ作成
		DealerEstimationRepository repository = context.getBean(DealerEstimationRepository.class);
		DealerEstimation found = repository.findOne(402L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationCheckResultRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationCheckResult.sql");

		// リポジトリ作成
		EstimationCheckResultRepository repository = context.getBean(EstimationCheckResultRepository.class);
		EstimationCheckResult found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	// TODO: エラーになる
	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationDetailRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/itemEstimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationDetail.sql");

		// リポジトリ作成
		EstimationDetailRepository repository = context.getBean(EstimationDetailRepository.class);
		EstimationDetail found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void ProductEstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/productEstimation.sql");

		// リポジトリ作成
		ProductEstimationRepository repository = context.getBean(ProductEstimationRepository.class);
		ProductEstimation found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	// TODO: エラーになる
	@Test
	@Transactional
	public void EstimationApprovalResultRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRoute.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalResult.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRouteNode.sql");

		// リポジトリ作成
		EstimationApprovalResultRepository repository = context.getBean(EstimationApprovalResultRepository.class);
		EstimationApprovalResult found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	// TODO: エラーになる
	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationApprovalRouteNodeRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRoute.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRouteNode.sql");

		// リポジトリ作成
		EstimationApprovalRouteNodeRepository repository = context.getBean(EstimationApprovalRouteNodeRepository.class);
		EstimationApprovalRouteNode found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void CustomerEstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/customerEstimation.sql");

		// リポジトリ作成
		CustomerEstimationRepository repository = context.getBean(CustomerEstimationRepository.class);
		CustomerEstimation found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationPicSaEmpRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationPicSaEmp.sql");

		// リポジトリ作成
		EstimationPicSaEmpRepository repository = context.getBean(EstimationPicSaEmpRepository.class);
		EstimationPicSaEmp found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	// TODO: エラーになる
	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationApprovalRouteRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRouteNode.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalResult.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRoute.sql");

		// リポジトリ作成
		EstimationApprovalRouteRepository repository = context.getBean(EstimationApprovalRouteRepository.class);
		EstimationApprovalRoute found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	// TODO: エラーになる
	@Test
	@WithMockCustomUser
	@Transactional
	public void ItemEstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationDetail.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/itemEstimation.sql");

		// リポジトリ作成
		ItemEstimationRepository repository = context.getBean(ItemEstimationRepository.class);
		ItemEstimation found = repository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void EstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationApprovalRoute.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimationPicSaEmp.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/customerEstimation.sql");

		// リポジトリ作成
		EstimationRepository repository = context.getBean(EstimationRepository.class);
		Estimation found = repository.findOne(4L);

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
	 * public static void main(String[] args) throws Exception { TestTools tool =
	 * new TestTools(); ProductCompMaster entity = new ProductCompMaster();
	 * entity.setCreatedAt(Calendar.getInstance().getTime());
	 * entity.setCreatedUserId(""); entity.setId(1L); entity.setProductGrpMaster(new
	 * ProductGrpMaster()); entity.setUpdatedAt(Calendar.getInstance().getTime());
	 * entity.setUpdatedUserId(""); entity.setVersion(1L);
	 * System.out.println(tool.findNullProperties(entity)); }
	 */

}
