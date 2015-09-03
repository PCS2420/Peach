<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="include_header.jsp" />  
		<title>Avaliando Descricao</title>
	</head>
	<body>
		<div class="page-header">
			<h1>Avaliando descricao</h1>
		</div>
		<div id="container">
			<div id="imageBox">
				<div id="imageBoxInside" style="position: relative; left: 0px; top: 0px;">
					<img style="width:50%;height:auto;display: block;margin-left: auto;margin-right: auto;" id="img_livro${im.id}" src="${im.path_imagem}"/>
				</div>
				<div style="text-align: center">
					<form id="formularioRevisao" action="EnviarRevisao" method="GET">
						Descricao:<br>
						<input type="hidden" id="id_descricao" name="id_descricao" value="${id_descricao}" />
						<input type="hidden" id="id_imagem" name="id_imagem" value="${im.id}" />
						<input type="hidden" id="status" name="status" value="" />
						<textarea id="descricao" name="descricao" rows="10" cols="80">${texto}</textarea>
						<br>
						<button type="button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#modalConfirmApprove">Aprovar</button>
						<button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#modalConfirmDisapprove">Reprovar</button>
						<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalConfirmCancel">Cancelar</button>
					</form>
				</div>
			
			</div>
		</div>
		<script>
		$(document).ready(function() {
			$("#confirm_approval").click(function() {
				$("#status").val("aprovar");
				document.getElementById("formularioRevisao").submit();
			});
			$("#confirm_disapproval").click(function() {
				$("#status").val("desaprovar");
				document.getElementById("formularioRevisao").submit();
			});
			$("#confirm_delete").click(function() {
				$("#status").val("liberar");
				document.getElementById("formularioRevisao").submit();
			});
		});
		</script>
		<div id="modalConfirmApprove" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce realmente deseja aprovar a revisão do descritor?</h4>
					</div>
					<div class="modal-body">
						<p>Verifique se não existe alguma alteração necessária.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" id="confirm_approval">Aprovar</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>
		<div id="modalConfirmDisapprove" class="modal fade" role="dialog">
			<div class="modal-dialog">
			    <div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce deseja realmente desaprovar essa descricao?</h4>
					</div>
					<div class="modal-body">
						<p>É possível editá-la para adequar aos padrões de descrição.</p>
					</div>
			    	<div class="modal-footer">
						<button type="button" class="btn btn-warning" id="confirm_disapproval">Sim</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
		    	</div>
			</div>
		</div>
		<div id="modalConfirmCancel" class="modal fade" role="dialog">
			<div class="modal-dialog">
			    <div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce deseja realmente parar de descrever?</h4>
					</div>
					<div class="modal-body">
						<p>Todas suas alterações serão perdidas.</p>
					</div>
			    	<div class="modal-footer">
						<button type="button" class="btn btn-warning" id="confirm_delete">Sair</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
		    	</div>
			</div>
		</div>
	</body>
</html>