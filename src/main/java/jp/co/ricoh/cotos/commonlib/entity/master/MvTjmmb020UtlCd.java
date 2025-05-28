package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * oM汎用マスタ明細
 */
@Entity
@Data
@Table(name = "mv_tjmmb020_utl_cd")
public class MvTjmmb020UtlCd {

	@Embeddable
	@Data
	public static class Id implements Serializable {
		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * マスタID
		 */
		@Column(length = 40, nullable = false)
		@ApiModelProperty(value = "マスタID", required = true, position = 1, allowableValues = "range[0,40]")
		private String itemId;

		/**
		 * コード値
		 */
		@Column(length = 100, nullable = false)
		@ApiModelProperty(value = "コード値", required = true, position = 2, allowableValues = "range[0,100]")
		private String cdVal;

	}

	@EmbeddedId
	private Id id;

	/**
	 * コード内容
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "コード内容", required = false, position = 3, allowableValues = "range[0,256]")
	private String decdVal;

	/**
	 * コード説明
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "コード説明", required = false, position = 4, allowableValues = "range[0,256]")
	private String cdNote;

	/**
	 * データエリア1
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "データエリア1", required = false, position = 5, allowableValues = "range[0,256]")
	private String dataArea1;

	/**
	 * データエリア2
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "データエリア2", required = false, position = 6, allowableValues = "range[0,256]")
	private String dataArea2;

	/**
	 * データエリア3
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "データエリア3", required = false, position = 7, allowableValues = "range[0,256]")
	private String dataArea3;

	/**
	 * データエリア4
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "データエリア4", required = false, position = 8, allowableValues = "range[0,256]")
	private String dataArea4;

	/**
	 * データエリア5
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "データエリア5", required = false, position = 9, allowableValues = "range[0,256]")
	private String dataArea5;

	/**
	 * ソート順
	 */
	@ApiModelProperty(value = "ソート順", required = false, position = 10)
	private Integer sortNumber;

	/**
	 * 削除フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "削除フラグ", required = false, position = 11, allowableValues = "range[0,1]")
	private String delFlg;

	/**
	 * 更新MOM会社ID
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "更新MOM会社ID", required = false, position = 12, allowableValues = "range[0,6]")
	private String updateCorpId;

	/**
	 * 登録者ID
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "更新MOM会社ID", required = false, position = 13, allowableValues = "range[0,8]")
	private String registerUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "登録者名", required = false, position = 14, allowableValues = "range[0,100]")
	private String registerUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "更新者ID", required = false, position = 15, allowableValues = "range[0,8]")
	private String updatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "更新者名", required = false, position = 16, allowableValues = "range[0,100]")
	private String updatingUserName;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "登録日時", required = false, position = 17)
	private Date registerDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "更新日時", required = false, position = 18)
	private Date updatingDate;

	/**
	 * 登録プログラムID
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "登録プログラムID", required = false, position = 19, allowableValues = "range[0,30]")
	private String registerProgramId;

	/**
	 * 更新プログラムID
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "更新プログラムID", required = false, position = 20, allowableValues = "range[0,30]")
	private String updatingProgramId;
}
