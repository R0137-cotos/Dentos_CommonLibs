package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import lombok.Data;

/**
 * 手配明細作成用の情報を表すEntity
 */
@Entity
@Data
public class ArrangementDetailMakeInfo {

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 契約明細ID
		 */
		private long contractDetailId;

		/**
		 * 品種ID（契約用）
		 */
		private long itemConId;

		/**
		 * 手配マスタID
		 */
		private long arrangementMasterId;
	}

	@EmbeddedId
	private Id id;

	/**
	 * 品種名
	 */
	private String itemName;

	/**
	 * リコー品種コード
	 */
	private String ricohItemCode;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}