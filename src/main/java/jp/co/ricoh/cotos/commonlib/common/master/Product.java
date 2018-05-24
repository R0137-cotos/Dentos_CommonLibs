package jp.co.ricoh.cotos.commonlib.common.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商材を表すMaster
 */

@Entity
@Data
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	@SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品ID", required = true, position = 1)
	private long id;

	/**
	 * 見積承認ルートグループID
	 */
	@ApiModelProperty(value = "見積承認ルートグループID", required = true, position = 2)
	private long estimationApprovalRouteGrpId;
	
	/**
	 * 契約承認ルートグループID
	 */
	@ApiModelProperty(value = "契約承認ルートグループID", required = true, position = 3)
	private long contractApprovalRouteGrpId;
	
	/**
	 * 商品名
	 */
	@Column(length = 255, nullable = false)
	@ApiModelProperty(value = "商品名", required = true, position = 4, allowableValues = "range[0,255]")
	private String name;

	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 5)
	private long repItemId;

	/**
	 * 品種マスタ
	 */
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<ItemMaster> itemMasterList;

}
