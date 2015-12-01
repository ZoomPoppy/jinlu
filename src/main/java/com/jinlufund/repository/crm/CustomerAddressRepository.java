package com.jinlufund.repository.crm;

import com.jinlufund.entity.crm.Address;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zz on 15/12/01.
 */
public interface CustomerAddressRepository extends JpaSpecificationExecutor<Address>,PagingAndSortingRepository<Address,Long> {
}
