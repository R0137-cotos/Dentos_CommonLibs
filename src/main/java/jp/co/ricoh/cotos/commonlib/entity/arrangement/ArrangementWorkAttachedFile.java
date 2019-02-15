package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務に紐づく添付ファイル情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ArrangementWorkAttachedFileListener.class)
@Data
@Table(name = "arrangement_work_attached_file")
public class ArrangementWorkAttachedFile extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_attached_file_seq")
	@SequenceGenerator(name = "arrangement_work_attached_file_seq", sequenceName = "arrangement_work_attached_file_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務添付ファイルID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配業務
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	private ArrangementWork arrangementWork;

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
	@NotNull
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
	@ApiModelProperty(value = "添付者MoM社員ID(作成時不要)", required = true, position = 7, allowableValues = "range[0,255]", readOnly = true)
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
