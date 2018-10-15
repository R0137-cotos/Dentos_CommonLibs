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
 * トークン発行権限マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "jwt_sys_auth_master")
public class JwtSysAuthMaster extends EntityBaseMaster {

	@Id
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jwt_sys_auth_master_seq")
 	@SequenceGenerator(name = "jwt_sys_auth_master_seq", sequenceName = "jwt_sys_auth_master_seq", allocationSize = 1)
	@Column(nullable = false)
	@ApiModelProperty(value = "システムID", required = true, position = 1)
	private String systemId;

	@Column(nullable = false)
	@ApiModelProperty(value = "パスワード", required = true, position = 2)
	private String password;

	@Column(nullable = false)
	@ApiModelProperty(value = "オリジン", required = true, position = 3)
	private String origin;
}
