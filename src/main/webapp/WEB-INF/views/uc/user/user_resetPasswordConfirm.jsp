<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="uc"/>
<c:set var="currentPage" value="user"/>
<script type="text/javascript">
$('#resetPasswordConfirmModal_resetButton').on('click', function () {
	$.ajax({
		type: 'POST',
		url: '/${currentModule}/${currentPage}/resetPassword',
		timeout: 60000,
		data : {
			id : '${item.id}'
		},
		dataType: 'json',
		beforeSend: function() {
			$('#resetPasswordConfirmModal_resetButton').button('loading');
		},
		complete: function() {
			$('#resetPasswordConfirmModal_resetButton').button('reset');
			$('#resetPasswordConfirmModal').on('hidden.bs.modal', function () {
				document.location.reload();
			})
		},
		success: function(data, textStatus) {
			if (data.status == 0) {
				$('#resetPasswordConfirmModal_questionText').hide();
				$('#resetPasswordConfirmModal_successText').show();
				$('#resetPasswordConfirmModal_resetButton').hide();
			} else {
				$('#resetPasswordConfirmModal_errorMessage').html(data.message);
				$('#resetPasswordConfirmModal_questionText').hide();
				$('#resetPasswordConfirmModal_failText').show();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			$('#resetPasswordConfirmModal_errorMessage').html(textStatus);
			$('#resetPasswordConfirmModal_questionText').hide();
			$('#resetPasswordConfirmModal_failText').show();
		}
	});
});
</script>

<div id="resetPasswordConfirmModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">重置用户密码</h4>
			</div>
			<div class="modal-body">
				<div id="resetPasswordConfirmModal_questionText">
					<h2 style="text-align: center">
						<span class="glyphicon glyphicon-question-sign" style="color: #31b0d5"></span>
						<span>&nbsp;真的要重置 “${item.username}” 的密码吗？</span>
					</h2>
				</div>
				<div id="resetPasswordConfirmModal_successText" style="display: none">
					<h2 style="text-align: center">
						<span class="glyphicon glyphicon-ok-sign" style="color: #449d44"></span>
						<span>&nbsp;重置成功</span>
					</h2>
				</div>
				<div id="resetPasswordConfirmModal_failText" style="display: none">
					<h2 style="text-align: center">
						<span class="glyphicon glyphicon-remove-sign" style="color: #c9302c"></span>
						<span>&nbsp;重置失败</span>
					</h2>
					<div id="resetPasswordConfirmModal_errorMessage" class="alert alert-danger"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id="resetPasswordConfirmModal_resetButton" type="button" class="btn btn-primary" data-loading-text="正在重置">重置</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
