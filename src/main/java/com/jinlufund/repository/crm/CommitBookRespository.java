package com.jinlufund.repository.crm;

import com.jinlufund.entity.crm.AdditionalInfo;
import com.jinlufund.entity.crm.CommitBook;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zz on 15/11/28.
 */
public interface CommitBookRespository extends PagingAndSortingRepository<CommitBook, Long>, JpaSpecificationExecutor<CommitBook> {

}
