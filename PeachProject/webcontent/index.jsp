<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
		<title>Index Test</title>
		<jsp:include page="include_header.jsp" /> 
	</head>
	<body>
		<header class="main-header">
			<h1><a href="Index" id="peach_icon" data-icon="&#9819;" class="button pink serif round glass"><b>Peach</b></a></h1>
			<!-- EXPECTING WHOIS -->
			<div style="float: right;">
				<c:if test="${tipo_usuario == 'descritor_pontuavel' || tipo_usuario == 'revisor_pontuavel'}">
					<a href="Perfil"><img src="${path_personagem}" style="width: 15%;position: relative;left: 45px;" /></a>
					<span style="position: relative;left: 25.7%;top: -9px;">Oi, ${login}</span>
					<a href="Perfil" style="text-decoration: none;margin-right: 5px;position: relative;top: 11px;"><span>Pontuacao/Ver Perfil</span></a>
					<button style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg" onclick="location.href='Sobre?logoff=1'">Logout</button>
				</c:if>
				<c:if test="${tipo_usuario == 'revisor'}">
					<span style="position: relative;left: 25.7%;top: -9px;">Oi, ${login}</span>
					<a href="Perfil" style="text-decoration: none;margin-right: 5px;position: relative;top: 11px;"><span>Ver Perfil</span></a>
					<button style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg" onclick="location.href='Sobre?logoff=1'">Logout</button>
				</c:if>
			</div>
		</header>
		<br><br><br><br>
		<div class="alertas">
			<c:if test="${redirected || signupped}">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Ola!</strong> Bem vindo(a) ao sistema Peach!
				</div>
			</c:if>
			<c:if test="${finished_description}">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Sucesso</strong> Obrigado por realizar uma descricao no sistema Peach!
				</div>
			</c:if>
			<c:if test="${deleted}">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Sucesso</strong> Sua descricao foi deletada com sucesso!
				</div>
			</c:if>
			<c:if test="${approved_revision}">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Sucesso</strong> A descricao foi aprovada com sucesso!
				</div>
			</c:if>
			<c:if test="${disapproved_revision}">
				<div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Sucesso</strong> A descricao foi recusada com sucesso!
				</div>
			</c:if>
		</div>
		<div style="margin-left:2cm;" >
			<font size="4">Sugest&atildeo de livros</font> 
			<div style="float:right;width: 281px;" >
				<form action="Busca" method="GET">
					<input type="text" class="form-control" placeholder="Buscar livros no sistema..." name="search" style="float: left;width: 192px;" />
					<button type="submit" class="btn btn-default">Procurar</button>
				</form>
			</div>
			<br>
			<input type="radio" name="opcao" value="descrever" checked/>Descrever<br>
			<input type="radio" name="opcao" value="revisar"/>Revisar<br>
		</div>
		
		<br><br>
		<!-- START OF CONTENT -->
	   	<div id="descreverContent">
			<c:forEach var="livro" items="${livros}" varStatus="id">
				<div class="slide img${id.count}">
					<a href="SelecionarLivro?id_livro=${livro.id}">
						<img src="books/${livro.autor}/${livro.titulo}/capa.jpg" />
						<span class="text-content"><h1>Descrever</h1></span>
					</a>
				</div>
			</c:forEach>
		</div>
		<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel'}">
		   	<div id="revisarContent">
				<c:if test="${empty livrosRevisor}">
					<h3 style="text-align: center;">Nao existem livros do seu curso para revisar! Você pode procurar por livros de outros cursos na busca</h3>
				</c:if>
				<c:if test="${not empty livrosRevisor}">
					<c:forEach var="livro" items="${livrosRevisor}" varStatus="id">
						<div class="slide img${id.count}">
							<a href="SelecionarLivroRevisar?id_livro=${livro.id}">
								<img src="books/${livro.autor}/${livro.titulo}/capa.jpg" />
								<span class="text-content"><h1>Revisar</h1></span>
							</a>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</c:if>
	</body>
	<script>
	$(document).ready(function() {
		$("input[name='opcao']").change(function() {
			if ($("input[name='opcao']:checked").val() == "descrever") {
				fadeDescritor();
			} else {
				<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel'}">
				fadeRevisor();
				</c:if>
				<c:if test="${tipo_usuario != 'revisor' && tipo_usuario != 'revisor_pontuavel'}">
				$("#modalNoPermission").modal();
				</c:if>
			}
		});
		
		$('#descreverContent').bxSlider({
			slideWidth: 300,
			minSlides: 2,
			maxSlides: 3,
			moveSlides: 1,
			slideMargin: 10
		});
		
		inicializarRevisor();
	});
	
	function fadeDescritor() {
		$("#revisarContent").fadeOut(function(){
			$($(".bx-wrapper")[0]).fadeIn();
		});
	}
	
	function fadeRevisor() {
		$($(".bx-wrapper")[0]).fadeOut(function(){
			$("#revisarContent").fadeIn();
		});
	}
	function inicializarRevisor() {
		$("#revisarContent").hide();
	}
	</script>
	<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel' && not empty livrosRevisor}">
		<script>
		function fadeDescritor() {
			$($(".bx-wrapper")[1]).fadeOut(function(){
				$($(".bx-wrapper")[0]).fadeIn();
			});
		}
		
		function fadeRevisor() {
			$($(".bx-wrapper")[0]).fadeOut(function(){
				$($(".bx-wrapper")[1]).fadeIn();
			});
		}
		
		function inicializarRevisor() {
			$('#revisarContent').bxSlider({
				slideWidth: 300,
				minSlides: 2,
				maxSlides: 3,
				moveSlides: 1,
				slideMargin: 10
			});
			
			$($(".bx-wrapper")[1]).hide();
		}
		</script>
	</c:if>
	
	<!-- ---- CASO O USUARIO TENHA APENAS PERMISSAO PARA DESCREVER --- -->
	<c:if test="${tipo_usuario != 'revisor' && tipo_usuario != 'revisor_pontuavel'}">
		<div class="modal fade in" id="modalNoPermission" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Sem permissão!</h4>
					</div>
					<div class="modal-body">
						<p>Apenas Peaches podem revisar! Se esforce para conseguir pontos e virar um revisor!</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-warning" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<!-- ------------------ PERMISSAO DESCREVER ---------------------- -->
	
	<!-- ---- CASO O USUARIO TENHA SIDO PROMOVIDO PARA REVISOR PONTUAVEL ---- -->
	<c:if test="${user_upgraded}">
		<div class="modal fade in" id="modalPromoted" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce foi promovido!!</h4>
					</div>
					<div class="modal-body">
						<p>Parabens! Sua ultima descricao foi aceita e com isso voce foi promovido!</p>
						<p>Agora voce tem acesso a aba de revisao, boa sorte!</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>
		<script>
		$(document).ready(function() {
			$("#modalPromoted").modal();
		});
		</script>
	</c:if>
	<!-- ------------------------ PROMOVIDO REVISOR ---------------------------- -->
	
	<!-- ---- CASO O USUARIO TENHA SIDO REBAIXADO PARA DESCRITOR PONTUAVEL ---- -->
	<c:if test="${user_downgraded}">
		<div class="modal fade in" id="modalDemoted" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Voce foi rebaixado</h4>
					</div>
					<div class="modal-body">
						<p>Ops, sua ultima descricao foi reprovada e com isso voce foi rebaixado para Descritor...</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-warning" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>
		<script>
		$(document).ready(function() {
			$("#modalDemoted").modal();
		});
		</script>
	</c:if>
	<!-- ------------------------ REBAIXADO REVISOR -------------------------- -->
	
	<!-- ---- CASO O USUARIO NAO TENHA FINALIZADO UMA DESCRICAO ---- -->
	<c:if test="${unfinished_description}">
		<div class="modal fade in" id="modalUnfinished" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Descricao Inacabada</h4>
					</div>
					<form method="GET" action="DescreverImagem">
						<input type="hidden" name="id_imagem" value="${id_imagem}"/>
						<div class="modal-body">
							<p>Voce possui uma descricao inacabada, voce pode Continua-la ou Deleta-la</p>
						</div>
						<div class="modal-footer">
							<button id="continue_description" type="submit" class="btn btn-success">Continuar</button>
							<button id="delete_description" type="button" class="btn btn-danger">Deletar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<script>
		$(document).ready(function() {
			$("#modalUnfinished").modal("show");

			$("#delete_description").click(function() {
				$.ajax({
					url: "ApagarDescricao",
					method: "POST",
					data: {
						id_imagem: $("input[name='id_imagem']").val()
					}, 
					success: function(data) {
						if (data == "success") {
							$(".alertas").append('<div class="alert alert-success" role="alert">A descricao foi deletada com sucesso!</div>');
							$("#modalUnfinished").modal("hide");
						} else {
							$(".alertas").append('<div class="alert alert-danger" role="alert">A descricao nao foi deletada com sucesso. Favor contatar administrador!</div>');
						}
					},
				});
				$("#modalUnfinished").modal("hide");
			});
		});
		</script>
	</c:if>
	<!-- --------------------- DESCRICAO INACABADA ----------------------- -->
	
	<!-- ---- CASO O USUARIO NAO TENHA FINALIZADO UMA REVISAO ---- -->
	<c:if test="${unfinished_revision}">
		<div class="modal fade in" id="modalUnfinishedRevision" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Revisão Inacabada</h4>
					</div>
					<form id="formularioRevisao" method="GET" action="EnviarRevisao">
						<input type="hidden" name="id_imagem" value="${id_imagem}"/>
						<input type="hidden" name="id_descricao" value="${id_descricao}"/>
						<input type="hidden" name="status" value="liberar"/>
						<div class="modal-body">
							<p>Voce possui uma revisao inacabada, voce pode Continua-la ou Deleta-la</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success" id="continue_revision">Continuar</button>
							<button type="button" class="btn btn-danger" id="delete_revision">Deletar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<script>
			$(document).ready(function() {
				$("#modalUnfinishedRevision").modal("show");
			
				$("#continue_revision").click(function() {
					$("#formularioRevisao").attr("action", "AvaliarDescricao");
					document.getElementById("formularioRevisao").submit();
				});
				$("#delete_revision").click(function() {
					$("#formularioRevisao").attr("action", "EnviarRevisao");
					document.getElementById("formularioRevisao").submit();
				});
			});
		</script>
	</c:if>
	<!-- --------------------- REVISAO INACABADA ----------------------- -->
	
</html>