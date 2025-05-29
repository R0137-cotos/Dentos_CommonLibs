package jp.co.ricoh.cotos.commonlib.dto.result;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

/**
 * ストアドにて、承認者を取得するためのDtoです。<br/>
 * 承認者を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class ApproverInfo {

	/**
	 * MOM社員ID
	 */
	@Id
	@Column(length = 24)
	private String empId;

	/**
	 * 業務用氏名（姓）
	 */
	@Column(length = 300)
	private String jobname1;

	/**
	 * 業務用氏名（名）
	 */
	@Column(length = 300)
	private String jobname2;

	/**
	 * 業務用氏名（姓カナ）
	 */
	@Column(length = 300)
	private String kanaJobname1;

	/**
	 * 業務用氏名（名カナ）
	 */
	@Column(length = 300)
	private String kanaJobname2;

	/**
	 * MOM組織ID
	 */
	@Column(length = 21)
	private String orgId;

	/**
	 * MOM会社ID
	 */
	@Column(length = 18)
	private String corpId;

	/**
	 * 組織正式名称
	 */
	@Column(length = 3000)
	private String orgName;

	/**
	 * 正式名称1
	 */
	@Column(length = 600)
	private String orgName1;

	/**
	 * 正式名称2
	 */
	@Column(length = 600)
	private String orgName2;

	/**
	 * 正式名称3
	 */
	@Column(length = 600)
	private String orgName3;

	/**
	 * 正式名称4
	 */
	@Column(length = 600)
	private String orgName4;

	/**
	 * 正式名称5
	 */
	@Column(length = 600)
	private String orgName5;

	/**
	 * 組織階層レベル
	 */
	private Integer orgHierarchyLevel;
}
