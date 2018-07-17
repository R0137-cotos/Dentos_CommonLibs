package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name = "mv_tjmmb010_utl_item")
public class MomCommonMaster {

	/**
	 * マスタID
	 */
	@Id
	@Column(length = 40)
	private String itemId;

	/**
	 * マスタ名称
	 */
	@Column(length = 256)
	private String itemName;

	/**
	 * マスタ説明
	 */
	@Column(length = 256)
	private String itemNote;

	/**
	 * マスタ主管区システムID
	 */
	@Column(length = 4)
	private String controlSystemId;

	/**
	 * コード値最大桁数
	 */
	private Integer cdCalMaxlength;

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

	/**
	 * MoM汎用マスタ明細
	 */
	@Transient
	private List<MomCommonMasterDetail> momCommonDetailMasterList;
}
