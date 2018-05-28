package jp.co.ricoh.cotos.commonlib.check;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.logic.check.FunctionCheckCommunication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFunctionCheckCommunication {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	CheckUtil checkUtil;
	@Autowired
	FunctionCheckCommunication functionCheckCommunication;

	@Test
	@Transactional
	public void コミュニケーション情報作成チェック確認() {
		Communication communication = new Communication();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(communication, "communication");

		// コミュニケーション情報がNull
		try {
			functionCheckCommunication.checkCommunicationCreate(null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Communication", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのコミュニケーション情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckCommunication.checkCommunicationCreate(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// コミュニケーション情報がNotNull、Entityチェックが正常
		try {
			bindingResult = new BeanPropertyBindingResult(communication, "communication");
			functionCheckCommunication.checkCommunicationCreate(communication, bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void コミュニケーション情報更新チェック確認() {
		Communication communication = new Communication();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(communication, "communication");

		// コミュニケーション情報がNull
		try {
			functionCheckCommunication.checkCommunicationUpdate(null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Communication", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのコミュニケーション情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckCommunication.checkCommunicationUpdate(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// コミュニケーション情報がNotNull、Entityチェックが正常
		try {
			bindingResult = new BeanPropertyBindingResult(communication, "communication");
			functionCheckCommunication.checkCommunicationUpdate(communication, bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void TOP画面処理中タスク一覧取得チェック確認() {
		社員マスタデータ作成();

		// MoM社員IDがNull
		try {
			functionCheckCommunication.checkCommunicationFindProcessingTaskList(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckCommunication.checkCommunicationFindProcessingTaskList("000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在する
		try {
			functionCheckCommunication.checkCommunicationFindProcessingTaskList("00623070");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void コミュニケーション情報更新メール送信チェック確認() {
		社員マスタデータ作成();

		Communication communication = new Communication();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(communication, "communication");

		// コミュニケーション情報がNull
		try {
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error Communication", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのコミュニケーション情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		bindingResult = new BeanPropertyBindingResult(communication, "communication");
		// 伝達者MoM社員IDがNull
		try {
			communication.setRequestFrom(null);
			communication.setRequestTo("00623070");
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 伝達者MoM社員IDがTBLに存在しない
		try {
			communication.setRequestFrom("000");
			communication.setRequestTo("00623070");
			functionCheckCommunication.checkCommunicationFindProcessingTaskList("000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 被伝達者MoM社員IDがNull
		try {
			communication.setRequestFrom("00623070");
			communication.setRequestTo(null);
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error MomEmployeeId", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 被伝達者MoM社員IDがTBLに存在しない
		try {
			communication.setRequestFrom("00623070");
			communication.setRequestTo("000");
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(communication, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Master Does Not Exist EmployeeMaster", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// コミュニケーション情報がNotNull、Entityチェックが正常
		try {
			communication.setRequestFrom("00623070");
			communication.setRequestTo("01196146");
			functionCheckCommunication.checkCommunicationUpdateAndSendMail(communication, bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 再承認依頼時コミュニケーション情報ステータス更新チェック確認() {

		// 対象文書キーがNull
		try {
			functionCheckCommunication.checkCommunicationUpdateStatusReApprovalRequest(null, "a");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error TargetDocKey", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの対象文書キーが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// コミュニケーションカテゴリーがNull
		try {
			functionCheckCommunication.checkCommunicationUpdateStatusReApprovalRequest("a", null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Argument Null Error CommunicationCategory", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのコミュニケーションカテゴリーが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// コミュニケーションカテゴリーが存在しない
		try {
			functionCheckCommunication.checkCommunicationUpdateStatusReApprovalRequest("a", "b");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "Does Not Exist CommunicationCategory", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しないコミュニケーションカテゴリーが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 対象文書キーが設定されていて、コミュニケーションカテゴリーが存在する
		try {
			functionCheckCommunication.checkCommunicationUpdateStatusReApprovalRequest("a", "見積");
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	private void 社員マスタデータ作成() {
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
	}
}
