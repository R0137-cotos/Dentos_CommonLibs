package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商材を表すMaster
 */
@Entity
@Data
@Table(name = "product_master")
public class ProductMaster {

	@Id
	@ApiModelProperty(value = "商品マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積承認ルートグループID
	 */
	@ApiModelProperty(value = "見積承認ルートグループID", required = false, position = 2, allowableValues = "range[0,9999999999999999999]")
	private Long estimationApprovalRouteGrpId;

	/**
	 * 契約承認ルートグループID
	 */
	@ApiModelProperty(value = "契約承認ルートグループID", required = false, position = 3, allowableValues = "range[0,9999999999999999999]")
	private Long contractApprovalRouteGrpId;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名", required = true, position = 4, allowableValues = "range[0,255]")
	private String name;

	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 5, allowableValues = "range[0,9999999999999999999]")
	private long repItemMasterId;

	/**
	 * 積上げ可能期間（開始日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 6, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveFrom;

	/**
	 * 積上げ可能期間（終了日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 7, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveTo;

	/**
	 * 品種マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "品種マスタ", required = true, position = 8)
	private List<ItemMaster> itemMasterList;

	/**
	 * 見積チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 9)
	private List<EstimationChecklistCompMaster> estimationChecklistCompMasterList;
	
	/**
	 * 契約チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 9)
	private List<ContractChecklistCompMaster> contractChecklistCompMasterList;

}
