package jp.co.ricoh.cotos.commonlib.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;
import lombok.Data;

/**
 * COTOSエンティティー共通項目
 * COTOSのエンティティー（COTOSでデータを管理するテーブルを持つエンティティー）はこのクラスのサブクラスとしてください。
 */
@MappedSuperclass
@Data
public class EntityBase {
	@ApiModelProperty(value = "登録日時(作成時不要)", required = true, position = 101, readOnly = true)
	private Date createdAt;

	@ApiModelProperty(value = "登録者(作成時不要)", required = true, position = 102, allowableValues = "range[0,255]", readOnly = true)
	private String createdUserId;

	@ApiModelProperty(value = "更新日時(作成時不要)", required = true, position = 103, readOnly = true)
	private Date updatedAt;

	@ApiModelProperty(value = "更新者(作成時不要)", required = true, position = 104, allowableValues = "range[0,255]", readOnly = true)
	private String updatedUserId;

	@Version
	@ApiModelProperty(value = "version(作成時不要)", required = true, position = 105, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long version;

	@PrePersist
	public void prePersist() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.createdUserId = userInfo.getMomEmployeeId();
		this.createdAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.updatedUserId = userInfo.getMomEmployeeId();
		this.updatedAt = new Date();
	}
}