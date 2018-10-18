package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 権限パターンマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "auth_pattern_master")
public class AuthPatternMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "権限パターンID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long authPatternId;

	/**
	 * アクション区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "アクション区分", required = true, position = 2)
	private ActionDiv actionDiv;

	/**
	 * 権限区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "権限区分", required = true, position = 3)
	private AuthDiv authDiv;

	/**
	 * 参照種別
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "参照種別", required = true, position = 4)
	private AccessType accessType;

	/**
	 * 画面URL権限マスタ
	 */
	@OneToMany(mappedBy = "authPatternMaster")
	@ApiModelProperty(value = "画面URL権限マスタ", required = false, position = 5)
	private List<DispUrlAuthMaster> authPatternMasterList;
}
