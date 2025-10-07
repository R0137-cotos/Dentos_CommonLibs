package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MoM汎用マスタ
 */
@Entity
@Data
@Table(name = "mv_tjmmb010_utl_item")
public class MvTjmmb010UtlItem {

	/**
	 * マスタID
	 */
	@Id
	@Column(length = 40)
	@ApiModelProperty(value = "マスタID", required = true, position = 1, allowableValues = "range[0,40]")
	private String itemId;

	/**
	 * マスタ名称
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "マスタ名称", required = false, position = 2, allowableValues = "range[0,256]")
	private String itemName;

	/**
	 * マスタ説明
	 */
	@Column(length = 256)
	@ApiModelProperty(value = "マスタ説明", required = false, position = 3, allowableValues = "range[0,256]")
	private String itemNote;

	/**
	 * マスタ主管区システムID
	 */
	@Column(length = 4)
	@ApiModelProperty(value = "マスタ主管区システムID", required = false, position = 4, allowableValues = "range[0,4]")
	private String controlSystemId;

	/**
	 * コード値最大桁数
	 */
	@ApiModelProperty(value = "コード値最大桁数", required = false, position = 5)
	private Integer cdValMaxlength;

	/**
	 * 削除フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "削除フラグ", required = false, position = 6, allowableValues = "range[0,1]")
	private String delFlg;

	/**
	 * 更新MOM会社ID
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "更新MOM会社ID", required = false, position = 7, allowableValues = "range[0,6]")
	private String updateCorpId;

	/**
	 * 登録者ID
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "更新MOM会社ID", required = false, position = 8, allowableValues = "range[0,8]")
	private String registerUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "登録者名", required = false, position = 9, allowableValues = "range[0,100]")
	private String registerUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "更新者ID", required = false, position = 10, allowableValues = "range[0,8]")
	private String updatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "更新者名", required = false, position = 11, allowableValues = "range[0,100]")
	private String updatingUserName;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "登録日時", required = false, position = 12)
	private Date registerDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "更新日時", required = false, position = 13)
	private Date updatingDate;

	/**
	 * 登録プログラムID
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "登録プログラムID", required = false, position = 14, allowableValues = "range[0,30]")
	private String registerProgramId;

	/**
	 * 更新プログラムID
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "更新プログラムID", required = false, position = 15, allowableValues = "range[0,30]")
	private String updatingProgramId;

	/**
	 * MoM汎用マスタ明細
	 */
	@Transient
	@ApiModelProperty(hidden = true)
	private List<MvTjmmb020UtlCd> momCommonDetailMasterList;
}
