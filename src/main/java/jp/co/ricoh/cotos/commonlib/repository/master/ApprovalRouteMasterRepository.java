package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;

public interface ApprovalRouteMasterRepository extends CrudRepository<ApprovalRouteMaster, Long> {
	public List<ApprovalRouteMaster> findByIdIn(List<Long> id);
}
