package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 担当支社を保持するエンティティ
 */
@Entity
@Data
@Table(name = "v_pic_affiliate_master")
public class PicAffiliateMaster {

	@Id
	@Column(length = 21)
	private String orgId;

	@Column(length = 3000)
	private String orgName;
	
	private Integer orgHierarchyLevel;
	
	@Column(length = 21)
	private String highOrgId;
	
	@Column(length = 3000)
	private String highOrgName;
}