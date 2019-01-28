package jp.co.ricoh.cotos.commonlib.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorCheckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private List<ErrorInfo> errorInfoList = new ArrayList<>();

	public ErrorCheckException(List<ErrorInfo> errorInfoList) {
		super();
		this.errorInfoList = errorInfoList;
	}
	
	public List<ErrorInfo> getErrorInfoList() {
		return errorInfoList;
	}
}
