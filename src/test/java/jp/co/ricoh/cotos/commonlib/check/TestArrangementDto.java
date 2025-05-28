package jp.co.ricoh.cotos.commonlib.check;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.TestTools.ParameterErrorIds;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementPicWorkerEmpDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementWorkApprovalRouteDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementWorkApprovalRouteNodeDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementWorkAttachedFileDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementWorkCheckResultDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement.ArrangementWorkDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AttachedFileDto;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement.WorkflowStatus;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementPicWorkerEmp;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkCheckResult;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementPicWorkerEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * TestArrangementDtoのテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "test.context.id = TestArrangementDto")
public class TestArrangementDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final String STR_4001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final int INT_MINUS_1 = -1;
	private static final int INT_10 = 10;
	private static final int INT_1000 = 1000;

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	ArrangementPicWorkerEmpRepository arrangementPicWorkerEmpRepository;

	@Autowired
	ArrangementRepository arrangementRepository;

	@Autowired
	ArrangementWorkApprovalResultRepository arrangementWorkApprovalResultRepository;

	@Autowired
	ArrangementWorkApprovalRouteNodeRepository arrangementWorkApprovalRouteNodeRepository;

	@Autowired
	ArrangementWorkApprovalRouteRepository arrangementWorkApprovalRouteRepository;

	@Autowired
	ArrangementWorkAttachedFileRepository arrangementWorkAttachedFileRepository;

	@Autowired
	ArrangementWorkCheckResultRepository arrangementWorkCheckResultRepository;

	@Autowired
	ArrangementWorkOperationLogRepository arrangementWorkOperationLogRepository;

	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

	@Autowired
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/arrangement.sql");
	}

	@Autowired
	TestSecurityController testSecurityController;

	@LocalServerPort
	private int localServerPort;

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void ArrangementWorkDtoのテスト() throws Exception {
		ArrangementWork entity = arrangementWorkRepository.findById(401L).get();
		ArrangementWorkDto dto = new ArrangementWorkDto();
		ArrangementWorkDto testTarget = new ArrangementWorkDto();

		BeanUtils.copyProperties(entity, dto);

		// 担当作業者社員
		ArrangementPicWorkerEmpDto worker = new ArrangementPicWorkerEmpDto();
		BeanUtils.copyProperties(entity.getArrangementPicWorkerEmp(), worker);
		dto.setArrangementPicWorkerEmp(worker);

		// 手配業務添付ファイル
		ArrangementWorkAttachedFileDto esAttached = new ArrangementWorkAttachedFileDto();
		BeanUtils.copyProperties(entity.getWorkAttachedFileList().get(0), esAttached);
		AttachedFileDto attached = new AttachedFileDto();
		BeanUtils.copyProperties(entity.getWorkAttachedFileList().get(0).getAttachedFile(), attached);
		esAttached.setAttachedFile(attached);
		dto.setWorkAttachedFileList(Arrays.asList(esAttached));

		// 手配
		ArrangementDto arrangement = new ArrangementDto();
		BeanUtils.copyProperties(entity.getArrangement(), arrangement);
		dto.setArrangement(arrangement);

		// 手配業務承認ルート
		ArrangementWorkApprovalRouteDto route = new ArrangementWorkApprovalRouteDto();
		BeanUtils.copyProperties(entity.getArrangementWorkApprovalRoute(), route);
		ArrangementWorkApprovalRouteNodeDto node = new ArrangementWorkApprovalRouteNodeDto();
		BeanUtils.copyProperties(entity.getArrangementWorkApprovalRoute().getArrangementWorkApprovalRouteNodeList().get(0), node);
		route.setArrangementWorkApprovalRouteNodeList(Arrays.asList(node));
		dto.setArrangementWorkApprovalRoute(route);

		// 手配業務チェック結果
		ArrangementWorkCheckResultDto check = new ArrangementWorkCheckResultDto();
		BeanUtils.copyProperties(entity.getArrangementWorkCheckResultList().get(0), check);
		dto.setArrangementWorkCheckResultList(Arrays.asList(check));

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setArrangementWorkApprovalRoute(null);
		testTarget.setArrangementWorkCheckResultList(null);
		testTarget.setArrangementPicWorkerEmp(null);
		testTarget.setWorkAttachedFileList(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setArrangement(null);
		testTarget.setWorkflowStatus(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "手配が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setMemo(STR_4001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "メモは最大文字数（4000）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setHoldingFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "保留フラグは最大値（9）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setHoldingFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "保留フラグは最小値（0）を下回っています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getArrangement().setWorkflowStatus(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ワークフロー状態が設定されていません。"));

		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getArrangement().setWorkflowStatus(WorkflowStatus.手配中);
		testTarget.getArrangementWorkApprovalRoute().setApprovalRequesterEmpId(STR_256);
		testTarget.getArrangementPicWorkerEmp().setMomOrgId(STR_256);
		testTarget.getWorkAttachedFileList().get(0).setFileName(STR_256);
		testTarget.getArrangementWorkCheckResultList().get(0).setCheckMatterCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認依頼者MoM社員IDは最大文字数（255）を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementWork.class, ArrangementWorkDto.class);
	}

	@Test
	public void ArrangementDtoのテスト() throws Exception {
		Arrangement entity = arrangementRepository.findById(4L).get();
		ArrangementDto testTarget = new ArrangementDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setWorkflowStatus(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ワークフロー状態が設定されていません。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisengagementFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "解約フラグは最大値（9）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisengagementFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "解約フラグは最小値（0）を下回っています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(Arrangement.class, ArrangementDto.class);
	}

	@Test
	public void ArrangementWorkApprovalRouteDtoのテスト() throws Exception {
		ArrangementWorkApprovalRoute entity = arrangementWorkApprovalRouteRepository.findById(401L).get();
		ArrangementWorkApprovalRouteDto dto = new ArrangementWorkApprovalRouteDto();
		ArrangementWorkApprovalRouteDto testTarget = new ArrangementWorkApprovalRouteDto();

		BeanUtils.copyProperties(entity, dto);
		dto.setArrangementWorkApprovalRouteNodeList(new ArrayList<ArrangementWorkApprovalRouteNodeDto>());
		entity.getArrangementWorkApprovalRouteNodeList().forEach(s -> {
			ArrangementWorkApprovalRouteNodeDto node = new ArrangementWorkApprovalRouteNodeDto();
			BeanUtils.copyProperties(s, node);
			dto.getArrangementWorkApprovalRouteNodeList().add(node);
		});

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setArrangementWorkApprovalRouteNodeList(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "手配業務承認ルートノードが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setApprovalRequesterEmpId(STR_256);
		testTarget.setApprovalRequesterName(STR_256);
		testTarget.setApprovalRequesterOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認依頼者組織名は最大文字数（255）を超えています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getArrangementWorkApprovalRouteNodeList().get(0).setApproverName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者氏名は最大文字数（255）を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementWorkApprovalRoute.class, ArrangementWorkApprovalRouteDto.class);
	}

	@Test
	public void ArrangementWorkApprovalRouteNodeDtoのテスト() throws Exception {
		ArrangementWorkApprovalRouteNode entity = arrangementWorkApprovalRouteNodeRepository.findById(401L).get();
		ArrangementWorkApprovalRouteNodeDto testTarget = new ArrangementWorkApprovalRouteNodeDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApproverEmpId(null);
		testTarget.setApproverName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者氏名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApproverEmpId(STR_256);
		testTarget.setApproverName(STR_256);
		testTarget.setApproverOrgName(STR_256);
		testTarget.setSubApproverEmpId(STR_256);
		testTarget.setSubApproverName(STR_256);
		testTarget.setSubApproverOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "代理承認者組織名は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApprovalOrder(INT_1000);
		testTarget.setApproverOrgLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApprovalOrder(INT_MINUS_1);
		testTarget.setApproverOrgLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者組織階層レベルは最小値（0）を下回っています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementWorkApprovalRouteNode.class, ArrangementWorkApprovalRouteNodeDto.class);
	}

	@Test
	public void ArrangementWorkAttachedFileDtoのテスト() throws Exception {

		ArrangementWorkAttachedFile entity = arrangementWorkAttachedFileRepository.findById(401L).get();
		ArrangementWorkAttachedFileDto dto = new ArrangementWorkAttachedFileDto();
		ArrangementWorkAttachedFileDto testTarget = new ArrangementWorkAttachedFileDto();

		BeanUtils.copyProperties(entity, dto);
		AttachedFileDto attachedFile = new AttachedFileDto();
		BeanUtils.copyProperties(entity.getAttachedFile(), attachedFile);
		dto.setAttachedFile(attachedFile);

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setFileName(null);
		testTarget.setAttachedFile(null);
		testTarget.setAttachedEmpId(null);
		testTarget.setAttachedEmpName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "添付者氏名が設定されていません。"));

		// 異常系（@Size(max)
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setFileName(STR_256);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedComment(STR_1001);
		testTarget.setAttachedEmpId(STR_256);
		testTarget.setAttachedEmpName(STR_256);
		testTarget.setAttachedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "添付者MoM社員IDは最大文字数（255）を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementWorkAttachedFile.class, ArrangementWorkAttachedFileDto.class);
	}

	@Test
	public void ArrangementWorkCheckResultDtoのテスト() throws Exception {
		ArrangementWorkCheckResult entity = arrangementWorkCheckResultRepository.findById(401L).get();
		ArrangementWorkCheckResultDto testTarget = new ArrangementWorkCheckResultDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setCheckMatterCode(null);
		testTarget.setCheckMatterText(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "チェック事項文面が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setCheckMatterCode(STR_256);
		testTarget.setCheckMatterText(STR_256);
		testTarget.setCheckedUserId(STR_256);
		testTarget.setCheckedUserName(STR_256);
		testTarget.setCheckedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "チェック事項文面は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisplayOrder(INT_1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisplayOrder(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最小値（0）を下回っています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementWorkCheckResult.class, ArrangementWorkCheckResultDto.class);
	}

	@Test
	public void ArrangementPicWorkerEmpDtoのテスト() throws Exception {
		ArrangementPicWorkerEmp entity = arrangementPicWorkerEmpRepository.findById(401L).get();
		ArrangementPicWorkerEmpDto testTarget = new ArrangementPicWorkerEmpDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setMomEmployeeId(null);
		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setMomEmployeeId(STR_256);
		testTarget.setMomOrgId(STR_256);
		testTarget.setOrgName(STR_256);
		testTarget.setSalesCompanyName(STR_256);
		testTarget.setOrgPhoneNumber(STR_256);
		testTarget.setEmployeeName(STR_256);
		testTarget.setSalesDepartmentName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setPhoneNumber(STR_256);
		testTarget.setFaxNumber(STR_256);
		testTarget.setMailAddress(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 12);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDは最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最小値（0）を下回っています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最大値（9）を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(ArrangementPicWorkerEmp.class, ArrangementPicWorkerEmpDto.class);
	}

}
