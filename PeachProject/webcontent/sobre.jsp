<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Sobre Peach</title>
		<link rel="stylesheet" type="text/css" href="css/header.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<style>
		
		</style>
	</head>
	<header class="main-header">
		<h1><a href="Index" id="peach_icon" data-icon="&#9819;" class="button pink serif round glass"><b>Peach</b></a></h1>
			<nav>
			<ul>
				<li><button style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalCadastro">Cadastro</button></li>
				<li><button style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalLogin">Login</button></li>
			</ul>
		</nav>
	</header>
	<br><br><br><br>
	<c:if test="${message == 'wrong_pw'}">
		<div class="alert alert-danger alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <strong>Erro!</strong> O login e a senha nao conferem no sistema.
		</div>	</c:if>
	<c:if test="${message == 'no_auth'}">
		<div class="alert alert-warning alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <strong>Aviso!</strong> Voce nao tem permissao para acessar essa pagina, por favor realize login.
		</div>
	</c:if>
	<body>
	<div>
		<img width= 20% align="left" src="img/peach.jpg"> <br><br>
		<div align="center">
			O Sistema Peach &eacute um sistema de crowdsourcing para ajudar a criar livros para pessoas com defici&ecircncia visual.<br>
			Inscreva-se agora! 
		</div>
	</div>
	
	<!-- CADASTRO MODAL -->
	
	<div class="modal fade" id="modalCadastro" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Cadastre-se!</h4>
				</div>
				<form id="cadastrar_usuario" action="Cadastrar" method="GET">
					<div class="modal-body" style="text-align: center;">
							<div class="row">
								<div class="col-lg-12">
									<input type="text" class="form-control" placeholder="Nome Completo" name="nome">
								</div>
							</div>
							<div class="row">
								<div class="col-lg-6">
								  	<input type="text" class="form-control" placeholder="NUSP" name="login">
							  	</div><!-- /.col-lg-6 -->
								<div class="col-lg-6">
							    	<select class="form-control" name="tipo_usuario">
								        <option value="descritor" checked>Descritor</option>
								        <option value="revisor">Revisor</option>
							      	</select>
							  	</div><!-- /.col-lg-6 -->
							</div><!-- /.row -->
							<div class="row">
								<div class="col-lg-12">
						    		<select class="form-control" name="cursos">			
						    			<c:forEach var="curso" items="${cursos}">
						    		 		<option value="${curso.id}">${curso.nome}</option>	
										</c:forEach>
							      	</select>
							  	</div><!-- /.col-lg-6 -->
							</div><!-- /.row -->
							<div class="row">
								<div class="col-lg-6">
							  		<input type="password" class="form-control" placeholder="Senha" name="senha">
								</div><!-- /.col-lg-6 -->
								<div class="col-lg-6">
									<input type="password" class="form-control" placeholder="Confirmacao" name="confirmacao">
								</div><!-- /.col-lg-6 -->
							</div><!-- /.row -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" id="botao_cadastrar">Cadastrar</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$("#botao_cadastrar").click(function() {
			$.ajax({
				url: "Cadastrar",
				method: "POST",
				data: {
					nome: $("input[name='nome']").val(),
					login: $("input[name='login']").val(),
					tipo_usuario: $("select[name='tipo_usuario']").val(),
					cursos: $("select[name='cursos']").val(),
					senha: $("input[name='senha']").val(),
					confirmacao: $("input[name='confirmacao']").val()
				}, 
				success: function(data) {
					if (data == "campo_vazio") {
						alert("Voce deixou de preencher algum campo");
					} else if (data == "login_invalido") {
						alert("Nusp invalido");
					} else if (data == "ok") {
						document.getElementById("cadastrar_usuario").submit();
					} 
				}
			});			
			console.log("salvei texto normal");
		});
	
	});
	</script>
	<!-- LOGIN MODAL -->
	
	<div class="modal fade" id="modalLogin" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Login</h4>
				</div>
				<form id="login_form" method="POST" action="Login">
					<div class="modal-body">
						<p>Entre no sistema Peach!</p>
						<label>Login</label><input name="login" type="text" id="login"/>
						<label>Senha</label><input name="senha" type="password" id="senha"/>
					</div>
					<div class="modal-footer">
						<button id="login_button" type="submit" class="btn btn-success">Login!</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>