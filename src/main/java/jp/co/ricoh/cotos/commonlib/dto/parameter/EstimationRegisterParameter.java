package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class EstimationRegisterParameter {

	/**
	 * 見積ID
	 */
	private long estimationId;

	/**
	 * 商品グループマスタID
	 */
	private long productGroupMasterId;

	/**
	 * 拡張項目
	 */
	@Lob
	private String extendsParameter;

	/**
	 * 登録者
	 */
	private String createdUser;

	/**
	 * 見積明細
	 */
	private List<EstimationDetailRegisterParameter> estimationDetailRegisterParameterList;

}
