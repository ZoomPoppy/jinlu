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
					$('#theCard').on('hidden.bs.modal', function() {
						document.location.reload();
					})
				},
				success : function(data, textStatus) {
					if (data.status == 0) {
						$('#theCard_successText').show();
						$('#theCard_failText').hide();
					} else {
						$('#theCard_errorMessage').html(data.message);
						$('#theCard_failText').show();
						$('#theCard_successText').hide();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(errorThrown);
					$('#theCard_errorMessage').html(textStatus);
					$('#theCard_failText').show();
					$('#theCard_successText').hide();
				}
			});
		});
		//如果是 编辑自动选中性别
		setSex("${itme.sex}");
		//根据身份证号自动出现日期和性别
		$("#certificateNo input[name=cardCertificateNo]").change(function(){
			var number=$(this).val();
			if(number.length==15){
				var birthday=number.substring(6,12);
				var sex=parseInt(number.substring(14,15))%2==0;
				saveSex(sex);
				$("#birthday input[name=cardBirthday]").val("19"+birthday.substring(0,3)+"-"+birthday.substring(3,5)+"-"+birthday.substring(5,7));
			}else if(number.length==18){
				var birthday=number.substring(6,14);
				var sex=parseInt(number.substring(17,18))%2==0;
				saveSex(sex);
				$("#birthday input[name=cardBirthday]").val(birthday.substring(0,5)+"-"+birthday.substring(5,7)+"-"+birthday.substring(7,9));
			}
		});
		
		function saveSex(sex){
			if(sex){
				setSex("女");
			}else{
				setSex("男");
			}
		}
		//radio男女选中事件
		function setSex(str){
			var list=document.getElementsByName("cardSex");
			for(var i=0;i<list.length;i++){
				if(str==list[i].value){
					list[i].checked;
				}
			}
		}
	});
</script>
<div id="theCard">
	<input type="hidden" id="certificateId" name="certificateId" value="${item.id}"/>
	<span id="theCard_errorMessage" style="display:none;"></span>
	<span id="theCard_failText" style="display:none;"></span>
	<span id="theCard_successText" style="display:none;"></span>
	<label>客户姓名</label><input id="name" name="cardName" value="${item.name}"/>
	<label>证件类型</label><input id="certificateType" name="cardCertificateType" value="${item.certificateType}"/>
	<label>证件号码</label><input id="certificateNo" name="cardCertificateNo" value="${item.certificateNo}"/>
	<label>性别</label><input id="sex" type="radio" name="cardSex" value="男" /><input id="sex" type="radio" name="cardSex" value="女" />
	<label>出生日期</label><input id="birthday" name="cardBirthday" value="${item.birthday}"/>
	<label>证件地址</label><input id="address" name="cardAddress" value="${item.address}"/>
	<label>有效期截止日</label><input id="validityPeriod" name="cardValidityPeriod" value="${item.validityPeriod}"/>
	<button id="theCardSave">保存</button><button id="theCardCancel">取消</button>
</div>