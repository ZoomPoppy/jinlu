<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc" />
<c:set var="currentPage" value="function" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/common/common_import.jsp"%>
<%@ include file="/WEB-INF/views/common/common_import_ztree.jsp"%>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode) {
		var id = treeNode.id;
		var system = treeNode.system;
		if (id == undefined && system != undefined) {
			$('#systemDetails input[name=system]').val(treeNode.name);
			$('#systemDetails #addModuleButton').off('click');
			$('#systemDetails #addModuleButton').on('click', function() {
				remoteModal('/${currentModule}/${currentPage}/addModuleEdit?system=' + system);
			});
			$('#systemDetails').show();
			$('#functionDetails').hide();
			$('#selectWizard').hide();
		} else {
			$.ajax({
				type : 'GET',
				url : '/${currentModule}/${currentPage}/' + treeNode.id,
				dataType : 'json',
				success : function(data, textStatus) {
					if (data.status == 0) {
						var id = data.data.id;
						$('#functionDetails input[name=name]').val(data.data.name);
						$('#functionDetails input[name=description]').val(data.data.description);
						$('#functionDetails input[name=type]').val(data.data.type);
						$('#functionDetails input[name=orderNo]').val(data.data.orderNo);
						$('#functionDetails #addChildButton').off('click');
						$('#functionDetails #addChildButton').on('click', function() {
							remoteModal('/${currentModule}/${currentPage}/addChildEdit?parentId=' + id);
						});
						if (data.data.type == 'BUTTON') {
							$('#functionDetails #addChildButton').hide();
						} else {
							$('#functionDetails #addChildButton').show();
						}
						$('#functionDetails #editButton').off('click');
						$('#functionDetails #editButton').on('click', function() {
							remoteModal('/${currentModule}/${currentPage}/updateEdit?id=' + id);
						});
						$('#functionDetails #deleteButton').off('click');
						$('#functionDetails #deleteButton').on('click', function() {
							remoteModal('/${currentModule}/${currentPage}/deleteConfirm?id=' + id);
						});
						$('#systemDetails').hide();
						$('#functionDetails').show();
						$('#selectWizard').hide();
					}
				}
			});
		}
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
		var currentSystem = '${currentSystem}';
		var currentFunctionId = '${currentFunctionId}';
		if (currentSystem != '') {
			var currentSelectNode = zTreeObj.getNodeByParam("system", currentSystem);
			zTreeObj.selectNode(currentSelectNode);
// 			zTreeObj.expandNode(currentSelectNode);
			zTreeOnClick(null, null, currentSelectNode);
		} else if (currentFunctionId != '') {
			var currentSelectNode = zTreeObj.getNodeByParam("id", currentFunctionId);
			zTreeObj.selectNode(currentSelectNode);
// 			zTreeObj.expandNode(currentSelectNode);
			zTreeOnClick(null, null, currentSelectNode);
		} else {
			$('#selectWizard').show();
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
				<h1 class="page-header">功能管理</h1>
				<div>
					<div class="row">
						<div class="col-md-4">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						<div class="col-md-6">
							<div id="selectWizard" class="alert alert-danger" style="display:none">
								<span>请在左侧选择一个功能进行操作</span>
							</div>
							<div id="systemDetails" style="display:none">
								<div class="form-group">
									<label for="system">系统名称</label>
									<input id="system" name="system" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<button id="addModuleButton" type="button" class="btn btn-primary btn-sm">添加子模块</button>
								</div>
							</div>
							<div id="functionDetails" style="display:none">
								<div class="form-group">
									<label for="name">功能名称</label>
									<input id="name" name="name" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<label for="description">功能描述</label>
									<input id="description" name="description" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<label for="type">功能类型</label>
									<input id="type" name="type" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<label for="orderNo">排序编号</label>
									<input id="orderNo" name="orderNo" value="" type="text" class="form-control" disabled/>
								</div>
								<div class="form-group">
									<button id="addChildButton" type="button" class="btn btn-primary btn-sm">添加子功能</button>
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
