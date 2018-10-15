package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * スーパーユーザーを管理するマスター
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "super_user_master")
public class SuperUserMaster extends EntityBaseMaster {

	@Id
	@Column(nullable = false)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "super_user_master_seq")
 	@SequenceGenerator(name = "super_user_master_seq", sequenceName = "super_user_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "スーパーユーザーマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * MoM社員ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "MoM社員ID", required = true, position = 2 )
	private String userId;
}
