package jp.co.ricoh.cotos.commonlib.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * アプリケーションマスタ
 */
@Entity
@Data
@ToString(exclude = { "systemMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "app_master")
public class AppMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "アプリケーションID", required = true, position = 1)
	private String appId;

	@Column(nullable = false)
	@ApiModelProperty(value = "パスワード", required = true, position = 2)
	private String password;

	@Column(nullable = false)
	@ApiModelProperty(value = "オリジン", required = true, position = 3)
	private String origin;

	@ManyToOne(optional = false)
	@JoinColumn(name = "system_id", referencedColumnName = "systemId")
	@ApiModelProperty(value = "システムマスタ", required = true, position = 2)
	@JsonIgnore
	private SystemMaster systemMaster;
}
