package com.jinlufund.repository.crm;

import com.jinlufund.entity.crm.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer>  {
	/**
	 * 通过用户的证件号查找用户
	 * @param certificateNo  
	 * @return 如果存在 返回 Customer  不存在 返回 NULL
	 */
	Customer findByCertificateNo(String certificateNo);

	
}
