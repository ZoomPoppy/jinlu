<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc" />
<c:set var="currentPage" value="rank" />
<script type="text/javascript">
	$('#editModal_saveButton').on('click', function() {
		var name = $('#editModal input[name=name]').val();
		var description = $('#editModal input[name=description]').val();

		$.ajax({
			type : 'POST',
			url : '/${currentModule}/${currentPage}/update',
			timeout : 60000,
			data : {
				id : '${item.id}',
				name : name,
				description : description
			},
			dataType : 'json',
			beforeSend : function() {
				$('#editModal_saveButton').button('loading');
			},
			complete : function() {
				$('#editModal_saveButton').button('reset');
				$('#editModal').on('hidden.bs.modal', function() {
					$(window.location).attr('href', '/${currentModule}/${currentPage}/tree?currentRankId=${item.id}');
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
</script>

<div id="editModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">编辑职级信息</h4>
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
						<label for="name">功能名称</label>
						<input id="name" name="name" value="${item.name}" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label for="description">功能描述</label>
						<input id="description" name="description" value="${item.description}" type="text" class="form-control">
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
