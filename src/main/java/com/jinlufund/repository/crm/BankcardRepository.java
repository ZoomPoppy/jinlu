package com.jinlufund.repository.crm;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jinlufund.entity.crm.Bankcard;
import com.jinlufund.entity.crm.Customer;

public interface BankcardRepository extends PagingAndSortingRepository<Bankcard, String>, JpaSpecificationExecutor<Bankcard>  {
	ArrayList<Bankcard> findByCustomerId(Long id);

}
