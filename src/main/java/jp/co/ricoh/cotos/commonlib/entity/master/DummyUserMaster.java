package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * ダミーユーザーを管理するマスタ
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "dummy_user_master")
public class DummyUserMaster extends EntityBaseMaster{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dummy_user_master_seq")
	@SequenceGenerator(name = "dummy_user_master_seq", sequenceName = "dummy_user_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "ダミーユーザーマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * ダミーMoM社員ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "ダミーMoM社員ID", required = true, position = 2)
	private String userId;

	@Size(max=255)
	@ApiModelProperty(value = "ダミー社員名", required = false, position = 3, allowableValues = "range[0,255]")
	private String empName;

	@Size(max=255)
	@ApiModelProperty(value = "ダミー組織名", required = false, position = 4, allowableValues = "range[0,255]")
	private String orgName;
}
