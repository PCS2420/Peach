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


		<div style="margin-left:2cm;">
		<font size="6">Hist&oacute;rico</font> 
		</div>
		<div style="margin-left:2cm;">
			Descri&ccedil;&otilde;es n&atilde;o avaliadas: ${descPendente} <br>
			Descri&ccedil;&otilde;es aprovadas com ou sem edi&ccedil;&atilde;o: ${descAprovada} <br>
			Descri&ccedil;&otilde;es rejeitadas: ${descRejeitada} <br>
			<br><br><br>
			<c:if test="${tipo_usuario == 'descritor_pontuavel' || tipo_usuario == 'revisor_pontuavel'}">
				<img src="${path_hd}" style="width: 15%;position: relative;left: 45px;" />
				<div style="width: 15%;position: relative;left: 45px;"><font size="4"><b>Rank ${rank} - ${personagem} <br></b></font></div>
			</c:if>
		</div>
		
		<nav>
		  <ul class="pager">
		    <li>
				<button type="button" class="btn btn-danger btn-lg" onclick="location.href='Perfil';">Sair</button>
			</li>
		 </ul>
		</nav>
		
	</body>
</html>