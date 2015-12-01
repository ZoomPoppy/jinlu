<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc" />
<c:set var="currentPage" value="rank" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/common_import.jsp"%>
<%@ include file="/WEB-INF/views/common/common_import_ztree.jsp"%>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode) {
		var id = treeNode.id;
		$.ajax({
			type : 'GET',
			url : '/${currentModule}/${currentPage}/' + id,
			dataType : 'json',
			success : function(data, textStatus) {
				if (data.status == 0) {
					var id = data.data.id;
					$('#rankDetails input[name=name]').val(data.data.name);
					$('#rankDetails input[name=description]').val(data.data.description);
					$('#rankDetails #addChildButton').off('click');
					$('#rankDetails #addChildButton').on('click', function() {
						remoteModal('/${currentModule}/${currentPage}/addChildEdit?parentId=' + id);
					});
					$('#rankDetails #editButton').off('click');
					$('#rankDetails #editButton').on('click', function() {
						remoteModal('/${currentModule}/${currentPage}/updateEdit?id=' + id);
					});
					$('#rankDetails #deleteButton').off('click');
					$('#rankDetails #deleteButton').on('click', function() {
						remoteModal('/${currentModule}/${currentPage}/deleteConfirm?id=' + id);
					});
				}
			}
		});
	};

	var zTreeObj;
	var setting = {
		callback : {
			onClick : zTreeOnClick
		}
	};
	var zNodes = ${jsonData};
	$(document).ready(function() {
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var currentRankId = '${currentRankId}';
		if (currentRankId != '') {
			var currentSelectNode = zTreeObj.getNodeByParam("id", currentRankId);
			zTreeObj.selectNode(currentSelectNode);
			zTreeOnClick(null, null, currentSelectNode);
		}
	});
</script>
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
				<h1 class="page-header">职级管理</h1>
				<div>
					<div class="row">
						<div class="col-md-4">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						<div class="col-md-6">
							<div id="rankDetails">
								<div class="form-group">
									<label for="name">职级名称</label>
									<input id="name" name="name" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<label for="description">职级描述</label>
									<input id="description" name="description" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<button id="addChildButton" type="button" class="btn btn-primary btn-sm">添加子职级</button>
									<button id="editButton" type="button" class="btn btn-primary btn-sm">编辑</button>
									<button id="deleteButton" type="button" class="btn btn-primary btn-sm">删除</button>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/common/common_footer.jsp"%>
</body>
</html>
