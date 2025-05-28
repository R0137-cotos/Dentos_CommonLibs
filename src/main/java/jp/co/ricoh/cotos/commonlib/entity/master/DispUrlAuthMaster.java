package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 画面URL権限マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "disp_url_auth_master")
public class DispUrlAuthMaster extends EntityBaseMaster {

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * システムドメイン
		 */
		@Column(nullable = false)
		@ApiModelProperty(value = "システムドメイン", required = true, position = 1)
		private String systemDomain;

		/**
		 * URLパターン
		 */
		@Column(nullable = false)
		@ApiModelProperty(value = "URLパターン", required = true, position = 2)
		private String urlPattern;

		/**
		 * アクションID
		 */
		@Column(nullable = false)
		@ApiModelProperty(value = "アクションID", required = true, position = 3)
		private String actionId;
	}

	@EmbeddedId
	private Id id;

	/**
	 * アクション名
	 */
	@ApiModelProperty(value = "アクション名", required = false, position = 4)
	private String actionName;

	/**
	 * 権限パターンマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "auth_pattern_id", referencedColumnName = "authPatternId")
	@ApiModelProperty(value = "権限パターンマスタ", required = true, position = 5)
	private AuthPatternMaster authPatternMaster;
}
