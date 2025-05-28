package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文セットアップ先情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_setup_info")
public class OrderSetupInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_setup_info_seq")
	@SequenceGenerator(name = "order_setup_info_seq", sequenceName = "order_setup_info_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * 注文基本情報ID
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "order_basic_info_id", referencedColumnName = "id")
	@JsonIgnore
	private OrderBasicInfo orderBasicInfo;

	/**
	 * セットアップ先情報　会社名
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　会社名", required = false, position = 2, allowableValues = "range[0,]")
	private String setupCompanyName;

	/**
	 * セットアップ先情報　漢字（姓＋名）
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　漢字（姓＋名）", required = false, position = 3, allowableValues = "range[0,]")
	private String setupNameKanji;

	/**
	 * セットアップ先情報　カナ（姓＋名）
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　カナ（姓＋名）", required = false, position = 4, allowableValues = "range[0,]")
	private String setupNameKana;

	/**
	 * セットアップ先情報　メールアドレス
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　メールアドレス", required = false, position = 5, allowableValues = "range[0,]")
	private String setupMailAddress;

	/**
	 * セットアップ先情報　郵便番号
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　郵便番号", required = false, position = 6, allowableValues = "range[0,]")
	private String setupPostNumber;

	/**
	 * セットアップ先情報　事業所名
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　事業所名", required = false, position = 7, allowableValues = "range[0,]")
	private String setupOfficeName;

	/**
	 * セットアップ先情報　住所
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　住所", required = false, position = 8, allowableValues = "range[0,]")
	private String setupAddress;

	/**
	 * セットアップ先情報　電話番号
	 */
	@Column
	@ApiModelProperty(value = "セットアップ先情報　電話番号", required = false, position = 9, allowableValues = "range[0,]")
	private String setupPhoneNumber;

}
