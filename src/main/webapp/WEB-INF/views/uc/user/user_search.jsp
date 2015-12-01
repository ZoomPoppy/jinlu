<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc"/>
<c:set var="currentPage" value="user"/>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/common_import.jsp"%>
<%@ include file="/WEB-INF/views/common/common_import_ztree.jsp"%>
<title></title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/common_header.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2">
				<%@ include file="/WEB-INF/views/common/common_sidebar.jsp"%>
			</div>
			<div class="col-md-10">
				<h1 class="page-header">用户管理</h1>
				<div style="height: 50px">
					<form id="searchForm" method="get" action="/uc/user/search" class="form-inline">
						<label for="searchForm_name">用户姓名：</label>
						<input id="searchForm_name" name="search_name_LIKE_STRING" value="${searchParams.name_LIKE_STRING}" type="text" class="form-control">
						<label for="searchForm_username">用户登录名：</label>
						<input id="searchForm_username" name="search_username_LIKE_STRING" value="${searchParams.username_LIKE_STRING}" type="text" class="form-control">
						<input type="hidden" name="sort" value="lastUpdate,desc"/>
						<button type="submit" class="form-control btn btn-primary">查询</button>
					</form>
				</div>
				<div style="margin-bottom:10px">
					<button type="button" class="btn btn-primary" onclick="remoteModal('/${currentModule}/${currentPage}/edit')">创建用户</button>
				</div>
				<div>
					<c:choose>
						<c:when test="${page.totalElements == 0}">
							<div class="alert alert-danger">
								<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
								<span>没有找到满足条件的记录</span>
							</div>
						</c:when>
						<c:otherwise>
							<table class="table table-bordered">
								<tr>
									<td>序号</td>
									<td>姓名</td>
									<td>邮箱</td>
									<td>登录名</td>
									<td>上次登录时间</td>
									<td>上次修改时间</td>
									<td>状态</td>
									<td>操作</td>
								</tr>
								<c:forEach items="${page.content}" var="item" varStatus="itemStatus">
									<tr>
										<td>${itemStatus.count}</td>
										<td>${item.name}</td>
										<td>${item.email}</td>
										<td>${item.username}</td>
										<td>${item.lastLogin}</td>
										<td>${item.lastUpdate}</td>
										<td>${item.status.description}</td>
										<td>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/edit?id=${item.id}')">编辑</button>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/editPassword?id=${item.id}')">修改密码</button>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/resetPasswordConfirm?id=${item.id}')">重置密码</button>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/editRole?id=${item.id}')">编辑角色</button>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/editFunction?id=${item.id}')">编辑权限</button>
											<button type="button" class="btn btn-primary btn-sm" onclick="remoteModal('/${currentModule}/${currentPage}/deleteConfirm?id=${item.id}')">删除</button>
										</td>
									</tr>
								</c:forEach>
							</table>
							<nav>
								<tags:pagination page="${page}" showPageCount="20" />
							</nav>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/common_footer.jsp"%>
</body>
</html>
