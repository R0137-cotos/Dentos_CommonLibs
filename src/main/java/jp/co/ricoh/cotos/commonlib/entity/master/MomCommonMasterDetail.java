package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "mv_tjmmb020_utl_cd")
public class MomCommonMasterDetail {

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
		private String itemId;

		/**
		 * コード値
		 */
		@Column(length = 100, nullable = false)
		private String cdVal;

	}

	@EmbeddedId
	private Id id;

	/**
	 * コード内容
	 */
	@Column(length = 256)
	private String decdVal;

	/**
	 * コード説明
	 */
	@Column(length = 256)
	private String cdNote;

	/**
	 * データエリア1
	 */
	@Column(length = 256)
	private String dataArea1;

	/**
	 * データエリア2
	 */
	@Column(length = 256)
	private String dataArea2;

	/**
	 * データエリア3
	 */
	@Column(length = 256)
	private String dataArea3;

	/**
	 * データエリア4
	 */
	@Column(length = 256)
	private String dataArea4;

	/**
	 * データエリア5
	 */
	@Column(length = 256)
	private String dataArea5;

	/**
	 * ソート順
	 */
	private Integer sortNumber;

	/**
	 * 削除フラグ
	 */
	@Column(length = 1)
	private String delFlg;

	/**
	 * 更新MOM会社ID
	 */
	@Column(length = 6)
	private String updateCorpId;

	/**
	 * 登録者ID
	 */
	@Column(length = 8)
	private String registerUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 100)
	private String registerUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 8)
	private String updatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 100)
	private String updatingUserName;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatingDate;

	/**
	 * 登録プログラムID
	 */
	@Column(length = 100)
	private String registerProgramId;

	/**
	 * 更新プログラムID
	 */
	@Column(length = 100)
	private String updatingProgramId;
}
