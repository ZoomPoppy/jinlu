<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/common_import.jsp"%>
<title></title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/common_header.jsp"%>
	<div class="container-fluid">
		<div style="border:1px solid #ccc;padding:20px;margin-top:100px" class="col-md-2 col-md-offset-5">
			<form action="/login" method="post">
			<c:if test="${errorMessage != null}">
				<div class="alert alert-danger">
					<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					<span>${errorMessage}</span>
				</div>
			</c:if>
			<div class="form-group">
				<label for="username">用户名</label>
				<input id="username" name="username" type="text" class="form-control">
			</div>
			<div class="form-group">
				<label for="password">密码</label>
				<input id="password" name="password" type="password" class="form-control">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary">登录</button>
			</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/common_footer.jsp"%>
</body>
</html>
