package jp.co.ricoh.cotos.commonlib.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * COTOSエンティティー共通項目
 * COTOSのエンティティー（COTOSでデータを管理するテーブルを持つエンティティー）はこのクラスのサブクラスとしてください。
 * 
 * @author tito
 *
 */
@MappedSuperclass
@Data
public class EntityBase {
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

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "削除日時", required = true, position = 105)
	private Date deletedAt;

	@ApiModelProperty(value = "削除者MoM社員ID", required = true, position = 106, allowableValues = "range[0,255]")
	private String deletedUser;

	@Version
	@ApiModelProperty(value = "バージョン", required = true, position = 107)
	private long version;

	@PrePersist
	public void prePersist() {
		//TODO:各ドメインでSecurity設定が実装完了後ユーザー情報から取得したMoM社員IDをcreatedUserに設定
		this.createdAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		//TODO:各ドメインでSecurity設定が実装完了後ユーザー情報から取得したMoM社員IDをupdatedUserに設定
		this.updatedAt = new Date();
	}
}
