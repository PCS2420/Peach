<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Busca</title>
		<jsp:include page="include_header.jsp" /> 
		<style>
		.paralelo {
			float: left;
		}
		.slide {
			width: 100%;
		}
		.slide a img {
			float: left;
			width: 284px;
			height: 400px;
			margin: 20px;
		}
		.detalhes {
			font-size: 22px;
		}
		.text-content {
		    position: absolute;
		    top: 0;
			margin: 20px;
			height: 91%;
			width: 22.5%;
		    text-align: center;
		    opacity: 0;
		    background-color: #333;
		    transition: all 0.5s ease;
		   	display:table-cell;
		}
 		.img:hover .text-content { 
 			opacity: 0.8; 
 		} 
		</style>
	</head>
	<body>
		<header class="main-header" style="z-index: +1;">
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
		<div style="z-index: -1;">
			<br><br><br>
			<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel'}">
				<div>
					<input type="radio" name="opcao" value="descrever" checked/>Descrever
					<input type="radio" name="opcao" value="revisar"/>Revisar<br>
				</div>
			<br>
			</c:if>
			<h1>Resultados para a busca:</h1>
			<div class="descricoes">
				<c:if test="${not empty livros}">
					<c:forEach var="livro" items="${livros}" varStatus="id">
						<div class="slide img${id.count}">
							<a href="SelecionarLivro?id_livro=${livro.id}">
								<img style="" src="books/${livro.autor}/${livro.titulo}/capa.jpg" />
								<span class="text-content"><h1>Descrever</h1></span>
							</a>
							<div class="paralelo" style="position: relative;top: 162px;">
								<ul>
									<li>
										<span class="detalhes">Título: ${livro.titulo}</span>
									</li>
									<li>
										<span class="detalhes">Autor: ${livro.autor}</span>
									</li>
									<li>
										<span class="detalhes">Curso: ${cursos[id.index]}</span>
									</li>
								</ul>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty livros}">
					<h3>Não foram encontrados resultados</h3>
				</c:if>
			</div>
			<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel'}">
				<div class="revisoes" style="display: none;">
				<c:if test="${not empty livrosRevisar}">
					<c:forEach var="livro" items="${livrosRevisar}" varStatus="id">
						<div class="slide img${id.count}">
							<a href="SelecionarLivro?id_livro=${livro.id}">
								<img style="" src="books/${livro.autor}/${livro.titulo}/capa.jpg" />
								<span class="text-content"><h1>Descrever</h1></span>
							</a>
							<div class="paralelo" style="position: relative;top: 162px;">
								<ul>
									<li>
										<span class="detalhes">Título: ${livro.titulo}</span>
									</li>
									<li>
										<span class="detalhes">Autor: ${livro.autor}</span>
									</li>
									<li>
										<span class="detalhes">Curso: ${cursosRevisar[id.index]}</span>
									</li>
								</ul>
							</div>
						</div>
					</c:forEach>
				</div>
				</c:if>
				<c:if test="${empty livrosRevisar}">
					<h3>Não foram encontrados resultados</h3>
				</c:if>
			</c:if>
		</div>
		<c:if test="${tipo_usuario == 'revisor' || tipo_usuario == 'revisor_pontuavel'}">
			<script>
			$("input[name='opcao']").change(function() {
				if ($("input[name='opcao']:checked").val() == "descrever") {
					$(".revisoes").fadeOut(function(){
						$(".descricoes").fadeIn();
					});
				} else {
					$(".descricoes").fadeOut(function(){
						$(".revisoes").fadeIn();
					});
				}
			});
			</script>
		</c:if>
	</body>
</html>