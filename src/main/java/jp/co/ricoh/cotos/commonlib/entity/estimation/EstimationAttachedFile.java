package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 見積情報の添付ファイルを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EstimationAttachedFileListener.class)
@ToString
@Table(name = "estimation_attached_file")
public class EstimationAttachedFile extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_attached_file_seq")
	@SequenceGenerator(name = "estimation_attached_file_seq", sequenceName = "estimation_attached_file_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積添付ファイルID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * ファイル名
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル名", required = true, position = 3, allowableValues = "range[0,255]")
	private String fileName;

	/**
	 * ファイル種類
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル種類", required = false, position = 4, allowableValues = "range[0,255]")
	private String fileKind;

	/**
	 * 添付ファイル
	 */
	@OneToOne(optional = false)
	@Valid
	@JoinColumn(name = "attached_file_id", referencedColumnName = "id")
	@ApiModelProperty(value = "添付ファイル", required = true, position = 5)
	private AttachedFile attachedFile;

	/**
	 * コメント
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "コメント", required = false, position = 6, allowableValues = "range[0,1000]")
	private String attachedComment;

	/**
	 * 添付者MoM社員ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "添付者MoM社員ID(作成時不要)<br/>※POST時「RJ社員情報マスタ」存在チェック実施", required = true, position = 7, allowableValues = "range[0,255]", readOnly = true)
	private String attachedEmpId;

	/**
	 * 添付者氏名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "添付者氏名(作成時不要)", required = true, position = 8, allowableValues = "range[0,255]", readOnly = true)
	private String attachedEmpName;

	/**
	 * 添付者組織名
	 */
	@ApiModelProperty(value = "添付者組織名(作成時不要)", required = false, position = 9, allowableValues = "range[0,255]", readOnly = true)
	private String attachedOrgName;

	/**
	 * 添付日時
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "添付日時(作成時不要)", required = true, position = 10, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date attachedAt;

	/**
	 * ファイル情報
	 */
	@Transient
	@ApiModelProperty(hidden = true)
	private MultipartFile multipartFile;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.attachedAt = super.getCreatedAt();
	}
}
