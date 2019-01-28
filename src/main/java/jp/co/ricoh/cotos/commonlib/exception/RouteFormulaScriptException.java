package jp.co.ricoh.cotos.commonlib.exception;

/**
 * 承認者特定用条件式実行時、スクリプトエラー発生用例外
 */
public class RouteFormulaScriptException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RouteFormulaScriptException(Exception e) {
		super(e);
	}
}
