<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc"/>
<c:set var="currentPage" value="user"/>
<script type="text/javascript">
$('#editModal_saveButton').on('click', function () {
	var name = $('#editModal input[name=name]').val();
	var email = $('#editModal input[name=email]').val();
	var username = $('#editModal input[name=username]').val();
	var rankId = $('#editModal input[name=rankId]').val();
	var departmentId = $('#editModal input[name=departmentId]').val();
	var status = $('#editModal select[name=status]').val();
	
	$.ajax({
		type: 'POST',
		url: '/${currentModule}/${currentPage}/save',
		timeout : 60000,
		data : {
			id : '${item.id}',
			name : name,
			email : email,
			username : username,
			rankId : rankId,
			departmentId : departmentId,
			status : status
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

var rankSetting = {
	callback: {
		onClick: rankTreeOnClick
	}
};
var departmentSetting = {
	callback: {
		onClick: departmentTreeOnClick
	}
};
var rankNodes = ${rankJson};
var departmentNodes = ${departmentJson};

function rankTreeOnClick(event, treeId, treeNode) {
	var id = treeNode.id;
	var name = treeNode.name;
	$('#editModal input[name=rankId]').attr("value", id);
	$('#editModal input[name=rankName]').attr("value", name);
}

function departmentTreeOnClick(event, treeId, treeNode) {
	var id = treeNode.id;
	var name = treeNode.name;
	$('#editModal input[name=departmentId]').attr("value", id);
	$('#editModal input[name=departmentName]').attr("value", name);
}

function showRankMenu() {
	var rankObj = $("#rankName");
	var rankOffset = rankObj.offset();
	$("#rankMenuContent").css({left:rankOffset.left + "px", top:rankOffset.top + rankObj.outerHeight() + "px", "z-index":9999, "background-color":"#fff", width:rankObj.width() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}

function showDepartmentMenu() {
	var departmentObj = $("#departmentName");
	var departmentOffset = departmentObj.offset();
	$("#departmentMenuContent").css({left:departmentOffset.left + "px", top:departmentOffset.top + departmentObj.outerHeight() + "px", "z-index":9999, "background-color":"#fff", width:departmentObj.width() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	$("#rankMenuContent").fadeOut("fast");
	$("#departmentMenuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	var isRank = event.target.id == "rankMenuBtn" || event.target.id == "rankMenuContent" || $(event.target).parents("#rankMenuContent").length > 0;
	var isDepartment = event.target.id == "departmentMenuBtn" || event.target.id == "departmentMenuContent" || $(event.target).parents("#departmentMenuContent").length > 0;
	if (!(isRank || isDepartment)) {
		hideMenu();
	}
}

$(document).ready(function(){
	$.fn.zTree.init($("#rankTree"), rankSetting, rankNodes);
	$.fn.zTree.init($("#departmentTree"), departmentSetting, departmentNodes);
});
</script>

<div id="editModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">编辑用户信息</h4>
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
						<label for="name">姓名</label>
						<input id="name" name="name" value="${item.name}" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="email">邮箱</label>
						<input id="email" name="email" value="${item.email}" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="username">登录名</label>
						<input id="username" name="username" value="${item.username}" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="rankId">职级</label>
						<input id="rankId" name="rankId" value="${item.rankId}" type="hidden" class="form-control">
						<input id="rankName" name="rankName" value="${item.rank.name}" type="text" class="form-control" readonly/>
						<a id="rankMenuBtn" href="#" onclick="showRankMenu(); return false;">选择</a>
					</div>
					<div class="form-group">
						<label for="departmentId">部门</label>
						<input id="departmentId" name="departmentId" value="${item.departmentId}" type="hidden" class="form-control">
						<input id="departmentName" name="departmentName" value="${item.department.name}" type="text" class="form-control" readonly/>
						<a id="departmentMenuBtn" href="#" onclick="showDepartmentMenu(); return false;">选择</a>
					</div>
					<div class="form-group">
						<label for="status">状态</label>
						<select id="status" name="status" class="form-control">
							<c:forEach items="${statusTypes}" var="statusType">
								<option value="${statusType.name()}" ${item.status == statusType.name() ? "selected" : ""}>${statusType.description}</option>
							</c:forEach>
						</select>
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

<div id="rankMenuContent" class="rankMenuContent" style="display:none; position: absolute; border: solid 1px #ccc">
	<ul id="rankTree" class="ztree" style="margin-top:0"></ul>
</div>

<div id="departmentMenuContent" class="departmentMenuContent" style="display:none; position: absolute; border: solid 1px #ccc">
	<ul id="departmentTree" class="ztree" style="margin-top:0"></ul>
</div>
