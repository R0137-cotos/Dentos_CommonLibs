package jp.co.ricoh.cotos.commonlib.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;
import lombok.Data;

/**
 * COTOSマスタエンティティー共通項目
 * COTOSのマスタエンティティー（COTOSでデータを管理するテーブルを持つエンティティー）はこのクラスのサブクラスとしてください。
 */
@MappedSuperclass
@Data
public class EntityBaseMaster {
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "作成日時", required = true, position = 101)
	private Date createdAt;

	@ApiModelProperty(value = "作成者MoM社員ID", required = true, position = 102, allowableValues = "range[0,255]")
	private String createdUser;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "更新日時", required = true, position = 103)
	private Date updatedAt;

	@ApiModelProperty(value = "更新者MoM社員ID", required = true, position = 104, allowableValues = "range[0,255]")
	private String updatedUser;

	@Column(length = 1)
	@ApiModelProperty(value = "削除フラグ", required = false, position = 105)
	private boolean deleteFlg = false;

	@Version
	@ApiModelProperty(value = "バージョン", required = true, position = 106)
	private long version;

	@PrePersist
	public void prePersist() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.createdUser = userInfo.getMomEmployeeId();
		this.createdAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.updatedUser = userInfo.getMomEmployeeId();
		this.updatedAt = new Date();
	}
}
