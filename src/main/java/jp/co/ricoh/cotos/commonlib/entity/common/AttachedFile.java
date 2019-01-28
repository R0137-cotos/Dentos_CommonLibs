package jp.co.ricoh.cotos.commonlib.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 添付ファイル情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "attached_file")
public class AttachedFile extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attached_file_seq")
	@SequenceGenerator(name = "attached_file_seq", sequenceName = "attached_file_seq", allocationSize = 1)
	@ApiModelProperty(value = "添付ファイルID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 物理ファイル名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "物理ファイル名", required = true, position = 2, allowableValues = "range[0,255]")
	private String filePhysicsName;

	/**
	 * ファイルサイズ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "ファイルサイズ", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long fileSize;

	/**
	 * コンテンツタイプ
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "コンテンツタイプ", required = true, position = 4, allowableValues = "range[0,255]")
	private String contentType;

	/**
	 * サーバーパス
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 1000)
	@ApiModelProperty(value = "サーバーパス", required = true, position = 5, allowableValues = "range[0,1000]")
	private String savedPath;
}
