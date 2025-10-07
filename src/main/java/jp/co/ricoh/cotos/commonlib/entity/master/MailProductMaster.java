package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知メール対象商材マスタ
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "mail_product_master")
public class MailProductMaster extends EntityBaseMaster {

	/**
	 * 通知メール対象商材マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_product_master_seq")
	@SequenceGenerator(name = "mail_product_master_seq", sequenceName = "mail_product_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "通知メール対象商材マスタID", required = true, position = 1)
	private long id;

	/**
	 * 通知メール制御マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mail_control_master_id", referencedColumnName = "id")
	@JsonIgnore
	private MailControlMaster mailControlMaster;
	
	/**
	 * 対象マスタ区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象マスタ区分", required = true, position = 1, allowableValues = "range[0,]")
	@Enumerated(EnumType.STRING)
	private TargetMasterType targetMasterType;

	/**
	 * 対象マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象マスタID", required = true, position = 2)
	private long targetMasterId;
	
	/**
	 * 対象マスタ区分
	 * @author z00se03039
	 *
	 */
	public enum TargetMasterType {
		GROUP, PRODUCT, ITEM
	}

}
