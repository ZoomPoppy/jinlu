<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="currentModule" value="crm"/>
<c:set var="currentPage" value="cunstomer"/>
<script type="text/javascript">
	$(document).ready(function(){
		//保存证件信息
		$("#theCardSave").click(function(){
			var name=$("#name input[name=cardName]").val();
			var certificateType=$("#certificateType input[name=cardCertificateType]").val();
			var certificateNo=$("#certificateNo input[name=cardCertificateNo]").val();
			var sex=$("#sex input[name=cardSex]").val();
			var birthday=$("#birthday input[name=cardBirthday]").val();
			var address=$("#address input[name=cardAddress]").val();
			var validityPeriod=$("#validityPeriod input[name=cardValidityPeriod]").val();
			$.ajax({
				type: 'POST',
				url: '/${currentModule}/${currentPage}/saveCard',
				timeout : 60000,
				data : {
					id : '${item.id}',
					name : name,
					certificateType : certificateType,
					certificateNo : certificateNo,
					sex : sex,
					birthday : birthday,
					address : address,
					validityPeriod : validityPeriod
				},
				dataType : 'json',
				beforeSend : function() {
					$('#theCardSave').button('loading');
				},
				complete : function() {
					$('#theCardSave').button('reset');
					$('#thePassport').on('hidden.bs.modal', function() {
						document.location.reload();
					})
				},
				success : function(data, textStatus) {
					if (data.status == 0) {
						$('#thePassport_successText').show();
						$('#thePassport_failText').hide();
					} else {
						$('#theCard_errorMessage').html(data.message);
						$('#thePassport_failText').show();
						$('#thePassport_successText').hide();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(errorThrown);
					$('#thePassport_errorMessage').html(textStatus);
					$('#thePassport_failText').show();
					$('#thePassport_successText').hide();
				}
			});
		});
	});
</script>
<div id="thePassport">
	<input type="hidden" id="certificateId" name="certificateId" value="${item.id}"/>
	<span id="thePassport_errorMessage" style="display:none;"></span>
	<span id="thePassport_failText" style="display:none;"></span>
	<span id="thePassport_successText" style="display:none;"></span>
	<label>类型</label><input id="name" name="cardName" value="${item.name}"/>
	<label>国家码</label><input id="certificateType" name="cardCertificateType" value="${item.certificateType}"/>
	<label>护照号</label><input id="certificateNo" name="cardCertificateNo" value="${item.certificateNo}"/>
	<label>姓</label><input id="sex" type="radio" name="cardSex" value="男" /><input id="sex" type="radio" name="cardSex" value="女" />
	<label>名</label><input id="birthday" name="cardBirthday" value="${item.birthday}"/>
	<label>性别</label><input id="address" name="cardAddress" value="${item.address}"/>
	<label>出生日期</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<label>出生地</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<label>签发日期</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<label>有效期</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<label>签发机关</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<button id="thePassportSave">保存</button><button id="thePassportCancel">取消</button>
</div>