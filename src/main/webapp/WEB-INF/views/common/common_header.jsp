<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="navbar navbar-static-top navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">金鹿大运营平台</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="/home">首页</a></li>
			<li class=""><a href="#">UC</a></li>
			<li class=""><a href="#">CRM</a></li>
			<li class=""><a href="#">Fortune</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:if test="${sessionScope.user != null}">
				<li><a href="#">当前用户：${sessionScope.user.username}</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						操作
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">编辑个人信息</a></li>
						<li><a href="#">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="/logout">退出</a></li>
					</ul></li>
			</c:if>
		</ul>
	</div>
</header>
