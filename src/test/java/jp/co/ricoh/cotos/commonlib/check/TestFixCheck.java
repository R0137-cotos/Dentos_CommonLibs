package jp.co.ricoh.cotos.commonlib.check;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;

/**
 * フォーマット変換確認メソッドのテストクラス
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFixCheck {

	@Autowired
	CheckUtil checkUtil;
	
	@Autowired
	ObjectMapper mapper;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.stop();
		}
	}

	@Test
	public void エラーが発生しないこと() {
		Contract contract = new Contract();

		contract.setContractType(ContractType.新規);
		contract.setLifecycleStatus(LifecycleStatus.作成完了);
		contract.setWorkflowStatus(WorkflowStatus.承認済);

		checkUtil.fixCheck(contract);
	}

	@Test
	public void エラーが発生すること_契約種別() throws Exception {
		Contract contract = new Contract();

		try {
			checkUtil.fixCheck(contract);
		} catch (ErrorCheckException e) {
			List<ErrorInfo> messageInfo = e.getErrorInfoList();
			Assert.assertEquals(1, messageInfo.size());
			Assert.assertEquals(messageInfo.get(0).getErrorId(), "RCO00003");
			Assert.assertEquals(messageInfo.get(0).getErrorMessage(), "契約種別が「新規」「契約変更」以外の契約で契約情報確定は行えません。");
		}
	}

	@Test
	public void エラーが発生すること_ライフサイクル() {
		Contract contract = new Contract();

		contract.setContractType(ContractType.新規);

		try {
			checkUtil.fixCheck(contract);
		} catch (ErrorCheckException e) {
			List<ErrorInfo> messageInfo = e.getErrorInfoList();
			Assert.assertEquals(1, messageInfo.size());
			Assert.assertEquals(messageInfo.get(0).getErrorId(), "RCO00003");
			Assert.assertEquals(messageInfo.get(0).getErrorMessage(), "ライフサイクル状態が「作成完了」以外の契約で契約情報確定は行えません。");
		}
	}

	@Test
	public void エラーが発生すること_ワークフロー() {
		Contract contract = new Contract();

		contract.setContractType(ContractType.新規);
		contract.setLifecycleStatus(LifecycleStatus.作成完了);

		try {
			checkUtil.fixCheck(contract);
		} catch (ErrorCheckException e) {
			List<ErrorInfo> messageInfo = e.getErrorInfoList();
			Assert.assertEquals(1, messageInfo.size());
			Assert.assertEquals(messageInfo.get(0).getErrorId(), "RCO00003");
			Assert.assertEquals(messageInfo.get(0).getErrorMessage(), "ワークフロー状態が「承認済」以外の契約で契約情報確定は行えません。");
		}
	}
}
