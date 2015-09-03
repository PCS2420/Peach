<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="include_header.jsp" />  
		<title>Descrevendo Imagem</title>
	</head>
	<body>
		<div class="page-header">
			<c:if test="${retomando == null}">
  				<h1>Descrevendo imagem</h1>
			</c:if>
			<c:if test="${retomando}">
  				<h1>Descrevendo imagem<small>Retomando da ultima modificacao feita </small> 
  					<c:if test="${backup != null}">
  						<small>as <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss" value="${backup}" /></small>
  					</c:if>
  				</h1>
			</c:if>
		</div>
		<div id="container">
			<div id="imageBox">
				<div id="imageBoxInside" style="position: relative; left: 0px; top: 0px;">
					<img style="width:50%;height:auto;display: block;margin-left: auto;margin-right: auto;" id="img_livro${im.id}" src="${im.path_imagem}"/>
				</div>
				<div style="text-align: center">
					<form id="formularioDescricao" action="EnviarDescricao" method="GET">
						<input type="hidden" id="id_descricao" name="id_descricao" value="${id_descricao}" />
						<textarea id="descricao" name="descricao" rows="10" cols="80"><c:if test="${retomando}">${texto}</c:if></textarea>
						<br>
						<button type="button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#modalConfirm">Enviar</button>
						<button id="guardar" style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg">Guardar</button>
						<button type="button" class="btn btn-danger btn-lg" id="cancelar" data-toggle="modal" data-target="#modalConfirmDelete">Cancelar</button>
					</form>
				</div>
			</div>
		</div>
	</body>
	<script>
	var mod = 0;
	$(document).ready(function() {
		$("#guardar").click(function() {
			$.ajax({
				url: "SalvarDescricao",
				method: "POST",
				data: {
					id_descricao: $("#id_descricao").val(),
					descricao: $("#descricao").val()
				}, 
				success: function(data) {
					if (data == "success") {
						$("#modalSuccess .modal-dialog .modal-content .modal-header h4").html("Obrigado!");
						$("#modalSuccess .modal-dialog .modal-content .modal-body p").html
						("Sua descricao foi salva com sucesso.<br>Voce pode voltar a descrever ou sair dessa pagina, mas nesse caso voce tera uma descricao incompleta");
						$("#modalSuccess").modal('show');
					} else {
						$("#modalSuccess .modal-dialog .modal-content .modal-header h4").html("Oops");
						$("#modalSuccess .modal-dialog .modal-content .modal-body p").html("Sua descricao nao foi salva com sucesso. Algo aconteceu de errado...");
						$("#modalSuccess").modal('show');
					}
				},
			});			
			console.log("salvei texto normal");
		});
		
		$("textarea").keypress(function() {
			mod++;
			if (mod > 5) {
				$.ajax({
					url: "SalvarDescricao",
					method: "POST",
					data: {
						id_descricao: $("#id_descricao").val(),
						descricao: $("#descricao").val(),
						backup: true
					}, 
					success: function(data) {
						if (data == "success") {
							console.log("backup feito com sucesso");
						} else {
							console.log("ocorreu algum erro");
						}
					},
				});
				mod = 0;
			}
		});
		
		$("#confirm_yes").click(function() {
			document.getElementById("formularioDescricao").submit();
		});
		
	});
	</script>
	<div class="modal fade" id="modalSuccess" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Obrigado</h4>
				</div>
				<div class="modal-body">
					<p>Sua descricao foi salva com sucesso!!</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					<button id="imma_outta_here" type="button" class="btn btn-warning" onclick="location.href='Index';">Sair</button>
				</div>
			</div>
		</div>
	</div>
	<div id="modalConfirm" class="modal fade" role="dialog">
		<div class="modal-dialog">
		    <div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Voce deseja realmente enviar sua descricao?</h4>
				</div>
				<div class="modal-body">
					<p>Nesse caso ela será enviada para aprovação. Verifique se não existe nenhum erro antes!</p>
				</div>
		    	<div class="modal-footer">
					<button id="confirm_yes" type="button" class="btn btn-success">Sim</button>
					<button type="button" class="btn btn-warning" data-dismiss="modal">Nao</button>
				</div>
	    	</div>
		</div>
	</div>
	<div id="modalConfirmDelete" class="modal fade" role="dialog">
		<div class="modal-dialog">
		    <div class="modal-content">
		    	<form action="ApagarDescricao" method="GET">
		    		<input type="hidden" name="id_imagem" value="${id_imagem}"/>
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce deseja realmente deletar sua descricao?</h4>
					</div>
				<div class="modal-body">
					<p>Todos os dados escritos serão perdidos, e ela será desvinculada de você.</p>
				</div>
			    	<div class="modal-footer">
						<button type="submit" class="btn btn-success">Sim</button>
						<button type="button" class="btn btn-warning" data-dismiss="modal">Nao</button>
					</div>
				</form>
	    	</div>
		</div>
	</div>
</html>