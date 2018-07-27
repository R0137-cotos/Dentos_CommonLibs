package jp.co.ricoh.cotos.commonlib.check;

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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementDetailMakeInfo;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.FunctionCheckArrangement;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFunctionCheckArrangement {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	FunctionCheckArrangement functionCheckArrangement;
	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

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
	public void 手配情報作成チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		契約データ作成();

		// 契約IDがNull
		try {
			functionCheckArrangement.checkArrangementCreateFirst(null, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RCO00001", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの契約IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 契約IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCreateFirst(999L, "00623070");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RCO00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない契約IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementCreateFirst(1L, null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCreateFirst(1L, "000");
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務単位にサマリーした手配マスタがTBLに存在しない
		try {
			List<ArrangementMaster> summaryArrangementMasterList = new ArrayList<>();
			List<ArrangementDetailMakeInfo> arrangementDetailMakeInfoList = new ArrayList<>();
			arrangementDetailMakeInfoList.add(new ArrangementDetailMakeInfo());
			functionCheckArrangement.checkArrangementCreateSecond(summaryArrangementMasterList, arrangementDetailMakeInfoList);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00008", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務に紐づく手配マスタが存在しません。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務に紐づく手配明細がTBLに存在しない
		try {
			List<ArrangementMaster> summaryArrangementMasterList = new ArrayList<>();
			summaryArrangementMasterList.add(new ArrangementMaster());
			List<ArrangementDetailMakeInfo> arrangementDetailMakeInfoList = new ArrayList<>();
			functionCheckArrangement.checkArrangementCreateSecond(summaryArrangementMasterList, arrangementDetailMakeInfoList);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00009", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務に紐づく手配明細が存在しません。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員ID、手配業務単位にサマリーした手配マスタ、手配業務に紐づく手配明細がTBLに存在する
		try {
			List<ArrangementMaster> summaryArrangementMasterList = new ArrayList<>();
			summaryArrangementMasterList.add(new ArrangementMaster());
			List<ArrangementDetailMakeInfo> arrangementDetailMakeInfoList = new ArrayList<>();
			arrangementDetailMakeInfoList.add(new ArrangementDetailMakeInfo());
			functionCheckArrangement.checkArrangementCreateSecond(summaryArrangementMasterList, arrangementDetailMakeInfoList);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配情報取得チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		// 手配IDがNull
		try {
			functionCheckArrangement.checkArrangementFind(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00001", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementFind(999L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配IDがTBLに存在する
		try {
			functionCheckArrangement.checkArrangementFind(1L);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配業務情報取得チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		// 手配業務IDがNull
		try {
			functionCheckArrangement.checkArrangementWorkFind(null);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00002", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務IDが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementWorkFind(999L);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在する
		try {
			functionCheckArrangement.checkArrangementWorkFind(1L);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配業務受付チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");

		// 手配業務IDリストがNull
		try {
			functionCheckArrangement.checkArrangementAcceptWork(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00003", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDリストにNull行がある
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
			arrangementWorkList.add(null);
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00010", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストに手配業務情報が未設定の行があります。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			ArrangementWork arrangementWork = new ArrangementWork();
			arrangementWork.setId(999L);
			arrangementWorkList.add(arrangementWork);
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		List<ArrangementWork> arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(2L));
		// 手配業務ステータスが不正
		try {
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務ステータスに受付済みまたは対応済みが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員IDがTBLに存在し、手配業務ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");
			functionCheckArrangement.checkArrangementAcceptWork(arrangementWorkList, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配担当者設定チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");

		// 手配業務IDリストがNull
		try {
			functionCheckArrangement.checkArrangementAssignWorker(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00003", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDリストにNull行がある
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
			arrangementWorkList.add(null);
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00010", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストに手配業務情報が未設定の行があります。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			ArrangementWork arrangementWork = new ArrangementWork();
			arrangementWork.setId(999L);
			arrangementWorkList.add(arrangementWork);
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		List<ArrangementWork> arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータのMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータに存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員IDがTBLに存在する
		try {
			bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");
			functionCheckArrangement.checkArrangementAssignWorker(arrangementWorkList, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配不受理チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");

		// 手配業務IDリストがNull
		try {
			functionCheckArrangement.checkArrangementRefuseWork(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00003", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストが未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDリストにNull行がある
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
			arrangementWorkList.add(null);
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00010", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務リストに手配業務情報が未設定の行があります。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			List<ArrangementWork> arrangementWorkList = new ArrayList<>();
			ArrangementWork arrangementWork = new ArrangementWork();
			arrangementWork.setId(999L);
			arrangementWorkList.add(arrangementWork);
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		List<ArrangementWork> arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(1L));
		// 手配業務ステータスが不正
		try {
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00006", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務ステータスに受付済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWorkList = new ArrayList<>();
		arrangementWorkList.add(arrangementWorkRepository.findOne(2L));
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員IDがTBLに存在し、手配業務ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(new ArrangementWork(), "arrangementWork");
			functionCheckArrangement.checkArrangementRefuseWork(arrangementWorkList, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配完了取消チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		ArrangementWork arrangementWork = new ArrangementWork();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(arrangementWork, "arrangementWork");

		// 手配業務IDリストがNull
		try {
			functionCheckArrangement.checkArrangementCancelCompleteWork(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00011", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCancelCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementCancelCompleteWork(arrangementWork, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCancelCompleteWork(arrangementWork, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(2L);
		// 手配業務ステータスが不正
		try {
			functionCheckArrangement.checkArrangementCancelCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00006", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務ステータスに対応済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(3L);
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員IDがTBLに存在し、手配業務ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(arrangementWork, "arrangementWork");
			functionCheckArrangement.checkArrangementCancelCompleteWork(arrangementWork, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	@Test
	@Transactional
	public void 手配完了チェック確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		手配データ作成();

		ArrangementWork arrangementWork = new ArrangementWork();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(arrangementWork, "arrangementWork");

		// 手配業務IDリストがNull
		try {
			functionCheckArrangement.checkArrangementCompleteWork(null, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00011", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータの手配業務情報が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00005", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "存在しない手配業務IDが設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(1L);
		// MoM社員IDがNull
		try {
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, null, bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00004", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者のMoM社員が未設定です。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// MoM社員IDがTBLに存在しない
		try {
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "000", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00007", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "操作者に存在しないMoM社員が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(3L);
		// 手配業務ステータスが不正
		try {
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "RAR00006", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "手配業務ステータスに受付済み以外が設定されています。", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		arrangementWork = arrangementWorkRepository.findOne(2L);
		// Entityチェックでエラー
		try {
			ObjectError error = new ObjectError("test", "テストID:テストメッセージ");
			bindingResult.addError(error);
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "00623070", bindingResult);
			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException ece) {
			Assert.assertEquals("エラーIDが正しく設定されること", "テストID", ece.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "テストメッセージ", ece.getErrorInfoList().get(0).getErrorMessage());
		}
		// 手配業務ID、MoM社員IDがTBLに存在し、手配業務ステータスが正常
		try {
			bindingResult = new BeanPropertyBindingResult(arrangementWork, "arrangementWork");
			functionCheckArrangement.checkArrangementCompleteWork(arrangementWork, "00623070", bindingResult);
		} catch (ErrorCheckException ece) {
			Assert.fail("異常終了してしまった");
		}
	}

	private void 契約データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractInsert.sql", new HashMap<>());
	}

	private void 手配データ作成() {
		dbUtil.execute("sql/check/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testEmployeeMasterInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testContractInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testArrangementInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testArrangementMasterInsert.sql", new HashMap<>());
		// dbUtil.execute("sql/check/testArrangementDetailInsert.sql", new HashMap<>());
		dbUtil.execute("sql/check/testArrangementWorkInsert.sql", new HashMap<>());
	}
}
