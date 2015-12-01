<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="panel panel-default" style="margin-top: 40px">
	<div class="panel-heading">用户管理模块</div>
	<div class="list-group">
		<a href="/uc/user" class="list-group-item ${currentModule == 'uc' && currentPage == 'user' ? 'active' : ''}">用户查询</a>
	</div>
</div>

<div class="panel panel-default" style="margin-top: 40px">
	<div class="panel-heading">职级部门模块</div>
	<div class="list-group">
		<a href="/uc/departmentCategory" class="list-group-item ${currentModule == 'uc' && currentPage == 'departmentCategory' ? 'active' : ''}">部门类别管理</a>
		<a href="/uc/department" class="list-group-item ${currentModule == 'uc' && currentPage == 'department' ? 'active' : ''}">部门管理</a>
		<a href="/uc/rank" class="list-group-item ${currentModule == 'uc' && currentPage == 'rank' ? 'active' : ''}">职级管理</a>
	</div>
</div>

<div class="panel panel-default" style="margin-top: 40px">
	<div class="panel-heading">功能角色模块</div>
	<div class="list-group">
		<a href="/uc/function" class="list-group-item ${currentModule == 'uc' && currentPage == 'function' ? 'active' : ''}">功能管理</a>
		<a href="/uc/role" class="list-group-item ${currentModule == 'uc' && currentPage == 'role' ? 'active' : ''}">角色管理</a>
	</div>
</div>
