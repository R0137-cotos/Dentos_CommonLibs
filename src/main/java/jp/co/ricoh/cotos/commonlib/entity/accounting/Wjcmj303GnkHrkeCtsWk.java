package jp.co.ricoh.cotos.commonlib.entity.accounting;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "wjcmj303_gnk_hrke_cts_wk")
public class Wjcmj303GnkHrkeCtsWk {

	/** 連携元管理明細ID **/
	@Id
	private String rnkiMtKnrMisiId;

	/** 原価振替情報ID **/
	private Long gnkHrkeZyuhuId;

	/** 振替元CUBIC部門コード **/
	private String hrkemtCubicBmnCd;

	/** 振替元従業員番号 **/
	private String hrkemtZyugyuinBngu;

	/** 振替先CUBIC部門コード **/
	private String hrkeskCubicBmnCd;

	/** 振替先従業員番号 **/
	private String hrkeskZyugyuinBngu;

	/** 契約No **/
	private String kiykNo;

	/** 振替金額総額 **/
	private BigDecimal hrkeKngkSugk;

	/** 経過済振替金額総額 **/
	private BigDecimal kikzmHrkeKngkSugk;

	/** 登録者ID **/
	private String turksyId;

	/** 登録者名 **/
	private String turksyMi;

	/** 登録履歴日時 **/
	private String turkRrkNtj;

	/** 更新者ID **/
	private String kusnsyId;

	/** 更新者名 **/
	private String kusnsyMi;

	/** 更新履歴日時ID **/
	private String kusnRrkNtj;

}
