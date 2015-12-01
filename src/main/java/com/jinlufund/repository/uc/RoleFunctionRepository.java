package com.jinlufund.repository.uc;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jinlufund.entity.uc.RoleFunction;

public interface RoleFunctionRepository extends PagingAndSortingRepository<RoleFunction, Long> {

	List<RoleFunction> findByRoleId(Long roleId);

}
