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
 * 注文者情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "orderer_Info")
public class OrdererInfo extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderer_Info_seq")
	@SequenceGenerator(name = "orderer_Info_seq", sequenceName = "orderer_Info_seq", allocationSize = 1)
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
	 * 注文者情報　会社名
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　会社名", required = false, position = 2, allowableValues = "range[0,]")
	private String ordererCompanyName;

	/**
	 * 注文者情報　漢字（姓＋名）
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　漢字（姓＋名）", required = false, position = 3, allowableValues = "range[0,]")
	private String ordererNameKanji;

	/**
	 * 注文者情報　カナ（姓＋名）
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　カナ（姓＋名）", required = false, position = 4, allowableValues = "range[0,]")
	private String ordererNameKana;

	/**
	 * 注文者情報　メールアドレス
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　メールアドレス", required = false, position = 5, allowableValues = "range[0,]")
	private String ordererMailAddress;

	/**
	 * 注文者情報　郵便番号
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　郵便番号", required = false, position = 6, allowableValues = "range[0,]")
	private String ordererPostNumber;

	/**
	 * 注文者情報　事業所名
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　事業所名", required = false, position = 7, allowableValues = "range[0,]")
	private String ordererOfficeName;

	/**
	 * 注文者情報　住所
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　住所", required = false, position = 8, allowableValues = "range[0,]")
	private String ordererAddress;

	/**
	 * 注文者情報　電話番号
	 */
	@Column
	@ApiModelProperty(value = "注文者情報　電話番号", required = false, position = 9, allowableValues = "range[0,]")
	private String ordererPhoneNumber;

}
