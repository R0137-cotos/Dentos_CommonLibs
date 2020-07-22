package jp.co.ricoh.cotos.commonlib.excel.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputEntity {

	/**
	 * 申込書（単数）
	 */
	private Application app;

	/**
	 * 申込書（複数）
	 */
	private List<Application> appList;

	/**
	 * 申込書（複数）タイトルリスト
	 */
	private List<String> appTitleList;

}