package com.jinlufund.repository.crm;

import com.jinlufund.entity.crm.ContactPerson;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zz on 15/11/27.
 */
public interface ContactPersonRepository  extends PagingAndSortingRepository<ContactPerson, Long>, JpaSpecificationExecutor<ContactPerson> {
}
