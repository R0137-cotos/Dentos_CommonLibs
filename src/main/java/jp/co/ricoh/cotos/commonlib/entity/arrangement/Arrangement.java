package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報と紐づく手配情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement")
public class Arrangement extends EntityBase {
	public enum ArrangementStatus {

		手配待ち, 手配中, 手配完了;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ArrangementStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_seq")
	@SequenceGenerator(name = "arrangement_seq", sequenceName = "arrangement_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配ID", required = true, position = 1)
	private long id;

	/**
	 * 手配ステータス
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "手配ステータス", required = false, position = 2)
	private ArrangementStatus arrangementStatus;

	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = false, position = 3)
	private long contractId;

	/**
	 * 手配業務
	 */
	@OneToMany(mappedBy = "arrangement")
	@ApiModelProperty(value = "手配業務", required = false, position = 4)
	private List<ArrangementWork> arrangementWorkList;

	/**
	 * 手配明細
	 */
	@OneToMany(mappedBy = "arrangement")
	@ApiModelProperty(value = "手配明細", required = false, position = 5)
	private List<ArrangementDetail> arrangementDetailList;
}