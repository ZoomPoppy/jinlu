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
		<div class="row">
			<div class="col-md-2">
				<%@ include file="/WEB-INF/views/common/common_sidebar.jsp"%>
			</div>
			<div class="col-md-10">
				<img src="/static/image/home.jpg" style="height:800px">
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/common_footer.jsp"%>
</body>
</html>
