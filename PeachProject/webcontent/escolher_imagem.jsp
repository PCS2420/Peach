<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="include_header.jsp" />  
		<title>Escolha uma imagem</title>
		<style>
			#container a img {
				outline: 2px solid rgb(202, 155, 221);
				width: 30%;
				height: auto;
			}
		</style>
	</head>
	<body>
		<div class="page-header">
  			<h1>Escolhendo imagem <small>escolha uma imagem para descrever</small></h1>
		</div>
		<div id="container" style="text-align:center;">
			<a href=DescreverImagem?id_imagem=${im1.id}>
				<img id="img_livro${im1.id}" src="${im1.path_imagem}"/>
			</a>
			<a href=DescreverImagem?id_imagem=${im2.id}>
				<img id="img_livro${im2.id}" src="${im2.path_imagem}"/>
			</a>
		</div>
		<nav>
		  <ul class="pager">
		    <li>
				<button type="button" class="btn btn-info" onclick="location.href='SelecionarLivro?id_livro=${id_livro}';">Pular</button>
			</li>
		    <li>
				<button type="button" class="btn btn-danger" onclick="location.href='Index';">Cancelar</button>
			</li>
		  </ul>
		</nav>
	</body>
</html>