package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class AttachedFile extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attached_file_seq")
	@SequenceGenerator(name = "attached_file_seq", sequenceName = "attached_file_seq", allocationSize = 1)
	private long id;

	@JsonIgnore
	private String savedPath;

	private String fileName;

	private String filePhysicsName;

	private String userComment;

	private long fileSize;

	private String contentType;

	@Transient
	private MultipartFile multipartFile;
}
