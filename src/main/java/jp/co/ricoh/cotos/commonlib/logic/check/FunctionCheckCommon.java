package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.util.SearchProperties;

@Component
public class FunctionCheckCommon {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	CheckUtil checkUtil;
	@Autowired
	SearchProperties searchProperties;

	/*
	 * 検索上限チェック
	 */
	@Transactional
	public void checkLimitSize(Map<String, Object> queryParams, String path) {

		if (dbUtil.loadCountFromSQLFile(path, queryParams) > searchProperties.getLimitSize()) {

			// Message.propertiesにlimitSizeを格納
			List<ErrorInfo> errorInfoList = checkUtil.addErrorInfo(new ArrayList<>(), "LimitOverSearchResult", new String[] { String.valueOf(searchProperties.getLimitSize()) });
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "LimitOverSearchResult"));
		}
	}
}
