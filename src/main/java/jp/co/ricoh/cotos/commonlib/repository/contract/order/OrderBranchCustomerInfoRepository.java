package jp.co.ricoh.cotos.commonlib.repository.contract.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBranchCustomerInfo;

@Repository
public interface OrderBranchCustomerInfoRepository extends JpaRepository<OrderBranchCustomerInfo, Long> {

}
