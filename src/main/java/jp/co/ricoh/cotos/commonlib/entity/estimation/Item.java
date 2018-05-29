package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ItemListener.class)
@Data
public class Item extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
	@SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
	@ApiModelProperty(value = "品種ID", required = true, position = 1)
	private long id;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = false, position = 2, allowableValues = "range[0,255]")
	private String name;

	/**
	 * 品種コード
	 */
	@ApiModelProperty(value = "品種コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String itemCode;

	@ManyToOne
	@ApiModelProperty(value = "品種マスタ", required = false, position = 4)
	private ItemMaster itemMaster;
}
