<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc"/>
<c:set var="currentPage" value="user"/>
<script type="text/javascript">
var functionIds = '${functionIds}';
$('#editModal_saveButton').on('click', function () {
	$.ajax({
		type: 'POST',
		url: '/${currentModule}/${currentPage}/saveFunction',
		timeout : 60000,
		data : {
			id : '${item.id}',
			functionIds : functionIds
		},
		dataType : 'json',
		beforeSend : function() {
			$('#editModal_saveButton').button('loading');
		},
		complete : function() {
			$('#editModal_saveButton').button('reset');
			$('#editModal').on('hidden.bs.modal', function() {
				document.location.reload();
			})
		},
		success : function(data, textStatus) {
			if (data.status == 0) {
				$('#editModal_successText').show();
				$('#editModal_failText').hide();
			} else {
				$('#editModal_errorMessage').html(data.message);
				$('#editModal_failText').show();
				$('#editModal_successText').hide();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			$('#editModal_errorMessage').html(textStatus);
			$('#editModal_failText').show();
			$('#editModal_successText').hide();
		}
	});
});

function zTreeOnCheck(event, treeId, treeNode) {
	var zTreeObj = $.fn.zTree.getZTreeObj("functionTree");
	var nodes = zTreeObj.getCheckedNodes();
	functionIds = '';
	for (var i = 0, l = nodes.length; i < l; i++) {
		var id = nodes[i].id;
		if (id != undefined) {
			functionIds += id + ',';
		}
		console.log('functionIds:' + functionIds);
	}
	if (functionIds.length > 0 ) {
		functionIds = functionIds.substring(0, functionIds.length-1);
	}
};

var zTreeObj;
var setting = {
	check: {
		enable: true,
		chkboxType: { "Y" : "p", "N" : "s" }
	},
	callback : {
		onCheck : zTreeOnCheck
	}
};
var zNodes = ${functionJson};
$(document).ready(function() {
	zTreeObj = $.fn.zTree.init($('#functionTree'), setting, zNodes);
	console.log(functionIds);
	var functionIdArray = functionIds.split(',');
	for (var i = 0, l = functionIdArray.length; i < l; i++) {
		var functionId = functionIdArray[i];
		if (functionId != '') {
			var node = zTreeObj.getNodeByParam("id", functionId);
			if (node != null) {
				zTreeObj.checkNode(node, true, true);
			}
		}
	}
});
</script>

<div id="editModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">编辑用户功能权限</h4>
			</div>
			<div class="modal-body">
				<div id="editModal_successText" class="alert alert-success" style="display: none">
					<strong>保存成功</strong>
				</div>
				<div id="editModal_failText" class="alert alert-danger" style="display: none">
					<strong id="editModal_errorMessage"></strong>
				</div>
				<div id="editModal_form">
					<div class="form-group">
						<ul id="functionTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="editModal_saveButton" type="button" class="btn btn-primary" data-loading-text="正在保存">保存</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
