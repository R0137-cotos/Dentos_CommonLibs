package jp.co.ricoh.cotos.commonlib.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.common.VMailAddressList;

@Repository
public interface VMailAddressListRepository extends CrudRepository<VMailAddressList, Long> {

	@Query(value = "SELECT MAIL_ADDRESS FROM V_MAIL_ADDRESS_LIST WHERE DOMAIN_TYPE = :DOMAINTYPE AND TABLE_TYPE = :TABLETYPE AND TRANSACTION_ID = :TRANSACTIONID ", nativeQuery = true)
	public List<String> findByDomainAndTableAndTranId(@Param("DOMAINTYPE") String domainType, @Param("TABLETYPE") String tableType, @Param("TRANSACTIONID") long tranId);
	
	//public List<> findByDomainAndTabAndTranId();

}
