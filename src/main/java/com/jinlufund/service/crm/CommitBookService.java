package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.CommitBook;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.CommitBookRespository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 15/11/28.
 */
@Service
public class CommitBookService {
    @Autowired
    CommitBookRespository commitBookRespository;
    public CommitBook findById(Long id) {
        // TODO: 15/11/28
        CommitBook commitBook = assertCommitBookExist(id);
        return commitBook;
    }
    public CommitBook assertCommitBookExist(Long id){
        CommitBook commitBook = commitBookRespository.findOne(id);
        if (commitBook==null||commitBook.isDeleteOrNot()){
            JinluException exception = new JinluException();
            exception.setErrorCode("commitBookService.commitBook.NotExist");
            exception.addParameter("commitBook",commitBook);
        }
        return commitBook;
    }

    public CommitBook updateOrSave(Long id, CommitBook input) {
        CommitBook commitBook = assertCommitBookExist(id);
        if (commitBook!=null){
            return update(id,input);
        }else {
            return save(input);
        }
    }
    public List<CommitBook> findByCustomerId(Long customerId){
        Map<String,Object> searchParams = new HashMap<String,Object>();
        searchParams.put("customerId_EQ_INTEGER",customerId);
        Specification<CommitBook> specification = JpaSpecificationUtils.createSpecification(searchParams,CommitBook.class);
        List<CommitBook> all = commitBookRespository.findAll(specification);
        List<CommitBook> validCommitBook = new ArrayList<CommitBook>();
        for (CommitBook c :
                all) {
            if (!c.isDeleteOrNot()){
                validCommitBook.add(c);
            }
        }
        return validCommitBook;
    }
    public CommitBook save(CommitBook commitBook){
        commitBook.setDeleteOrNot(false);
        return commitBookRespository.save(commitBook);
    }
    public CommitBook update(Long id,CommitBook commit){
        CommitBook commitBook = assertCommitBookExist(id);
        commitBook.setAddress(commit.getAddress());
        commitBook.setCertificateNumber(commit.getCertificateNumber());
        commitBook.setCertificateType(commit.getCertificateType());
        commitBook.setContactPhone(commit.getContactPhone());
        return commitBookRespository.save(commitBook);
    }
    public void deleteByCustomerId(Long customerId){
        List<CommitBook> commitBooks = findByCustomerId(customerId);
        for (CommitBook c :
                commitBooks) {
            c.setDeleteOrNot(true);
            update(c.getId(),c);
        }
    }
    public void delteById(Long id){
        CommitBook commitBook = assertCommitBookExist(id);
        commitBook.setDeleteOrNot(true);
        update(id,commitBook);
    }
}
