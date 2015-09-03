<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
		<title>Perfil</title>
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
					<a href="Perfil" style="text-decoration: none;margin-right: 5px;position: relative;top: 11px;"><span>Pontua&ccedil;&atilde;o/Ver Perfil</span></a>
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

		<c:if test="${message == 'wrong_pw'}">
		<div class="alert alert-danger alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <strong>Erro!</strong> A senha atual n&atilde;o confere no sistema.
		</div>	
		</c:if>
		<c:if test="${message == 'right_pw'}">
			<div class="alert alert-success alert-dismissible" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <strong>Sucesso!</strong> A senha foi alterada com sucesso.
			</div>
		</c:if>
		<c:if test="${message == 'wrong_conf'}">
			<div class="alert alert-warning alert-dismissible" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <strong>Erro!</strong> A nova senha n&atilde;o confere com a digitada na confirma&ccedil;&atilde;o.
			</div>
		</c:if>

		<div style="margin-left:2cm;">
		<font size="6"> Perfil</font> 
		</div>
		<div style="margin-left:2cm;">
			Nome: ${nome} <br>
			NUSP: ${login} <br>
			Pontua&ccedil;&atilde;o: ${pontos} <br><br><br>
			<c:if test="${tipo_usuario == 'descritor_pontuavel' || tipo_usuario == 'revisor_pontuavel'}">
				<img src="${path_hd}" style="width: 15%;position: relative;left: 45px;" />
				<div style="width: 15%;position: relative;left: 45px;"><font size="4"><b>Rank ${rank} - ${personagem} <br></b></font></div>
			</c:if>
		</div>
		<nav>
		  <ul class="pager">
		    <li>
				<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#modalSenha">Alterar Senha</button>
			</li>
			<li>
				<button style="border-color:#FF79A5;background:#FF79A5" type="button" class="btn btn-primary btn-lg" onclick="location.href='Historico';">Hist&oacute;rico de Pontos</button>
			</li>
		    <li>
				<button type="button" class="btn btn-danger btn-lg" onclick="location.href='Index';">Sair</button>
			</li>
		 </ul>
		</nav>
		
		
		
		<!-- ALTERAR SENHA MODAL -->
		
		<div class="modal fade" id="modalSenha" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Alterar Senha</h4>
					</div>
					<form id="login_form" method="POST" action="AlterarSenha">
						<div class="modal-body">
							<p>Digite sua senha atual e sua nova senha</p>
							<label>Senha Atual </label><input name="senhaAtual" type="password" id="atual"/>
							<label>Nova Senha </label><input name="senhaNova" type="password" id="nova"/>
							<label>Confirmacao da Nova Senha </label><input name="senhaConfirmacao" type="password" id="confirmacao"/>
						</div>
						<div class="modal-footer">
							<button id="senha_button" type="submit" class="btn btn-success">Enviar</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>