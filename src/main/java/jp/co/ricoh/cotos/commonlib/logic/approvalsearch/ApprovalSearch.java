package jp.co.ricoh.cotos.commonlib.logic.approvalsearch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
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
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteGrpMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;
import jp.co.ricoh.cotos.commonlib.exception.RouteFormulaScriptException;
import jp.co.ricoh.cotos.commonlib.repository.master.ApprovalRouteGrpMasterRepository;

@Component
public class ApprovalSearch {

	@Autowired
	ApprovalRouteGrpMasterRepository approvalRouteGrpMasterRepository;

	/**
	 * 承認ルート特定 ※見積承認ルート取得の場合:(estimation, null) 契約承認ルート取得の場合:(null, contract)
	 *
	 * @param estimation
	 *            見積情報
	 * @param contract
	 *            契約情報
	 * @return 承認ルート
	 */
	public ApprovalRouteMasterResult findApprovalRouteMaster(Estimation estimation, Contract contract) {

		ApprovalRouteMasterResult reslut = new ApprovalRouteMasterResult();

		// 承認ルートグループIDから承認ルートグループを特定
		long approvalRouteGrpId = 0L;
		if (null != estimation) {
			//TODO:エンティティ作成に伴いエラーになった
			//approvalRouteGrpId = estimation.getProductGrpMaster().getEstimationApprovalRouteGrpMaster().getId();
		} else {
			//TODO:エンティティ作成に伴いエラーになった
			//approvalRouteGrpId = contract.getProductGrpMaster().getContractApprovalRouteGrpMaster().getId();
		}
		ApprovalRouteGrpMaster approvalRouteGrpMaster = approvalRouteGrpMasterRepository.findOne(approvalRouteGrpId);

		// ルート特定、または条件式実行結果ステータスの異常・警告が発生するまでループ
		ApprovalRouteMaster applyApprovalRouteMaster = approvalRouteGrpMaster.getApprovalRouteMasterList().stream()
				.filter(approvalRouteMaster -> {
					// 条件式実行
					RouteFormulaResult formulaResult = this.execRouteFormula(estimation, contract, approvalRouteMaster);
					return (RouteFormulaStatus.正常.equals(formulaResult.getStatus()) && formulaResult.isApplyRoute())
							|| RouteFormulaStatus.異常.equals(formulaResult.getStatus())
							|| RouteFormulaStatus.警告.equals(formulaResult.getStatus());
				}).findFirst().get();

		// 特定した承認ルートマスタの条件式を実行
		RouteFormulaResult formulaResult = this.execRouteFormula(estimation, contract, applyApprovalRouteMaster);

		reslut.setRouteFormulaResult(formulaResult);
		reslut.setApprovalRouteMaster(applyApprovalRouteMaster);

		return reslut;
	}

	/**
	 * ルート条件式を実行
	 *
	 * @param estimation
	 *            条件式の引数
	 * @param contract
	 *            条件式の引数
	 * @param approvalRouteMaster
	 *            条件式
	 * @return 実施結果
	 * @throws ScriptException
	 */
	private RouteFormulaResult execRouteFormula(Estimation estimation, Contract contract,
			ApprovalRouteMaster approvalRouteMaster) {

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");

		try {
			if (null != estimation) {
				engine.put("estimation", estimation);
			} else {
				engine.put("contract", contract);
			}
			engine.eval(loadScriptFromClasspath("js/routeFormulaTemplate.js",
					approvalRouteMaster.getRouteConditionFormula()));
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
			Mustache mustache = mf.compile(
					new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(jsFilePathOnClasspath)),
					jsFilePathOnClasspath);
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
