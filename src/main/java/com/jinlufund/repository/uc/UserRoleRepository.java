package com.jinlufund.repository.uc;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jinlufund.entity.uc.UserRole;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {

	List<UserRole> findByUserId(Long userId);
	
}
