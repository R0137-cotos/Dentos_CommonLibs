package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ProductEstimationListener.class)
@Data
@Table(name = "product_estimation")
public class ProductEstimation extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_estimation_seq")
	@SequenceGenerator(name = "product_estimation_seq", sequenceName = "product_estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 商品マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 商品名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = true, position = 3, allowableValues = "range[0,255]")
	private String productEstimationName;

	/**
	 * 代表品種マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long repItemMasterId;

	/**
	 * サービス識別番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String serviceIdentNumber;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 6)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	@Lob
	private String extendsParameter;

	@PrePersist
	public void prePersist() {
		super.setCreatedAt(new Date());
	}
}
