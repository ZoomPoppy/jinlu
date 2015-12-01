package com.jinlufund.repository.uc;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jinlufund.entity.uc.UserFunction;

public interface UserFunctionRepository extends PagingAndSortingRepository<UserFunction, Long> {

	List<UserFunction> findByUserId(Long userId);

}
