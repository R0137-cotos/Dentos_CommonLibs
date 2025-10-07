package jp.co.ricoh.cotos.commonlib.logic.approvalsearch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import jp.co.ricoh.cotos.commonlib.dto.result.ApprovalRouteMasterResult;
import jp.co.ricoh.cotos.commonlib.dto.result.RouteFormulaResult;
import jp.co.ricoh.cotos.commonlib.dto.result.RouteFormulaResult.RouteFormulaStatus;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;
import jp.co.ricoh.cotos.commonlib.exception.RouteFormulaScriptException;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteGrpMasterRepository;

@Component
public class ApprovalSearch {

	@Autowired
	ApprovalRouteGrpMasterRepository approvalRouteGrpMasterRepository;

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 承認ルート特定
	 *
	 * <pre>
	 * 【処理内容】
	 * ・引数の承認ルートグループIDを元に承認ルートグループマスタTBL(APPROVAL_ROUTE_GRP_MASTER)から承認ルートグループマスタ情報取得
	 * ・引数のエンティティを元にJavaScriptエンジン「Nashorn」を使用し、承認ルートマスタTBL(APPROVAL_ROUTE_MASTER)からルート条件式に一致にする承認ルートマスタ情報取得
	 *  ※上記処理で失敗した場合は、処理結果ステータスに「警告」または「異常」を設定し戻り値返却
	 * ・承認ルートマスタ情報に紐づく承認ルードノード情報は各ドメインの共通処理で取得
	 * </pre>
	 *
	 * @param approvalRouteGrpId
	 *            承認ルートグループID
	 * @param entity
	 *            エンティティ
	 * @param domain
	 *            ドメイン(estimation・contract・arrangementのいずれかを設定)
	 * @return 承認ルート
	 */
	@SuppressWarnings("hiding")
	public <T> ApprovalRouteMasterResult findApprovalRouteMaster(long approvalRouteGrpId, T entity, String domain) {

		ApprovalRouteMasterResult reslut = new ApprovalRouteMasterResult();
		ApprovalRouteGrpMaster approvalRouteGrpMaster = approvalRouteGrpMasterRepository.findById(approvalRouteGrpId).orElse(null);

		// ルート特定、または条件式実行結果ステータスの異常・警告が発生するまでループ
		ApprovalRouteMaster applyApprovalRouteMaster = approvalRouteGrpMaster.getApprovalRouteMasterList().stream().filter(approvalRouteMaster -> {
			// 条件式実行
			RouteFormulaResult formulaResult = this.execRouteFormula(entity, domain, approvalRouteMaster);
			return (RouteFormulaStatus.正常.equals(formulaResult.getStatus()) && formulaResult.isApplyRoute()) || RouteFormulaStatus.異常.equals(formulaResult.getStatus()) || RouteFormulaStatus.警告.equals(formulaResult.getStatus());
		}).findFirst().get();

		// 特定した承認ルートマスタの条件式を実行
		RouteFormulaResult formulaResult = this.execRouteFormula(entity, domain, applyApprovalRouteMaster);

		reslut.setRouteFormulaResult(formulaResult);
		reslut.setApprovalRouteMaster(applyApprovalRouteMaster);

		return reslut;
	}

	/**
	 * 複数承認ルート特定
	 *
	 * <pre>
	 * 【処理内容】
	 * ・引数の承認ルートグループIDを元に承認ルートグループマスタTBL(APPROVAL_ROUTE_GRP_MASTER)から承認ルートグループマスタ情報取得
	 * ・引数のエンティティを元にJavaScriptエンジン「Nashorn」を使用し、承認ルートマスタTBL(APPROVAL_ROUTE_MASTER)からルート条件式に一致にする承認ルートマスタ情報取得
	 *  ※上記処理で失敗した場合は、処理結果ステータスに「警告」または「異常」を設定し戻り値返却
	 * ・承認ルートマスタ情報に紐づく承認ルードノード情報は各ドメインの共通処理で取得
	 * </pre>
	 *
	 * @param approvalRouteGrpId
	 *            承認ルートグループID
	 * @param entity
	 *            エンティティ
	 * @param domain
	 *            ドメイン(estimation・contract・arrangementのいずれかを設定)
	 * @return 承認ルート
	 */
	@SuppressWarnings("hiding")
	public <T> List<ApprovalRouteMasterResult> findApprovalRouteMasterCandidate(long approvalRouteGrpId, T entity, String domain) {

		List<ApprovalRouteMasterResult> resultList = new ArrayList<>();
		ApprovalRouteGrpMaster approvalRouteGrpMaster = approvalRouteGrpMasterRepository.findById(approvalRouteGrpId).orElse(null);

		// ルート特定、または条件式実行結果ステータスの異常・警告が発生するまでループ
		approvalRouteGrpMaster.getApprovalRouteMasterList().stream().filter(approvalRouteMaster -> {
			// 条件式実行
			RouteFormulaResult formulaResult = this.execRouteFormula(entity, domain, approvalRouteMaster);
			return (RouteFormulaStatus.正常.equals(formulaResult.getStatus()) && formulaResult.isApplyRoute()) || RouteFormulaStatus.異常.equals(formulaResult.getStatus()) || RouteFormulaStatus.警告.equals(formulaResult.getStatus());
		}).forEach(approvalRouteMaster -> {
			ApprovalRouteMasterResult approvalRouteMasterResult = new ApprovalRouteMasterResult();
			approvalRouteMasterResult.setApprovalRouteMaster(approvalRouteMaster);
			approvalRouteMasterResult.setRouteFormulaResult(this.execRouteFormula(entity, domain, approvalRouteMaster));
			resultList.add(approvalRouteMasterResult);
		});

		return resultList;
	}

	/**
	 * ルート条件式を実行
	 *
	 * @param entity
	 *            エンティティ
	 * @param domain
	 *            ドメイン
	 * @param approvalRouteMaster
	 *            条件式
	 * @return 実施結果
	 * @throws ScriptException
	 */
	@SuppressWarnings("hiding")
	private <T> RouteFormulaResult execRouteFormula(T entityClass, String domain, ApprovalRouteMaster approvalRouteMaster) {

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");

		try {
			engine.put(domain, entityClass);
			engine.eval(loadScriptFromClasspath("js/routeFormulaTemplate.js", approvalRouteMaster.getRouteConditionFormula()));
			return (RouteFormulaResult) engine.eval("result");
		} catch (ScriptException e) {
			throw new RouteFormulaScriptException(e);
		}
	}

	/**
	 * JavaScriptのテンプレートファイルを読み込み、置換文字列を置換
	 *
	 * @param jsFilePathOnClasspath
	 *            テンプレートファイルパス
	 * @param formula
	 *            置換対象条件式
	 * @return 置換後のJavaScript
	 */
	private String loadScriptFromClasspath(String jsFilePathOnClasspath, String formula) {
		try {
			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(jsFilePathOnClasspath)), jsFilePathOnClasspath);
			StringWriter out = new StringWriter();

			Map<String, Object> parameter = new HashMap<>();
			parameter.put("ROUTE_FORMULA", formula);

			mustache.execute(out, parameter).flush();
			return out.toString();
		} catch (IOException neverOccure) {
			throw new RuntimeException(neverOccure);
		}
	}
}