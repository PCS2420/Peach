package br.com.poli.peachproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.users.Administrador;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class AlterarSenha
 */
@WebServlet("/AlterarSenha")
public class AlterarSenha extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarSenha() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String senhaAtual = request.getParameter("senhaAtual");
		String senhaNova = request.getParameter("senhaNova");
		String confirmacao = request.getParameter("senhaConfirmacao");
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8"); 
	
		// Gera atributos para cada tipo de usuario no sistema - Para a pagina de perfil
		if (u instanceof DescritorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");
			request.setAttribute("tipo_usuario", "descritor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("path_hd", p.getImagemHD());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
			request.setAttribute("rank", u.getId_personagem() - 1);
			request.setAttribute("personagem", p.getNome());
		} else if (u instanceof RevisorPontuavel) {
			Personagem p = (Personagem) session.getAttribute("personagem");	
			request.setAttribute("tipo_usuario", "revisor_pontuavel");
			request.setAttribute("path_personagem", p.getImagem());
			request.setAttribute("path_hd", p.getImagemHD());
			request.setAttribute("pontos", u.getPontos());
			request.setAttribute("login", u.getLogin());
			request.setAttribute("rank", u.getId_personagem() - 1);
			request.setAttribute("personagem", p.getNome());
		} else if (u instanceof Revisor) {
			request.setAttribute("tipo_usuario", "revisor");
			request.setAttribute("login", u.getLogin());
		} else if (u instanceof Administrador) {
			// back end
			request.setAttribute("tipo_usuario", "administrador");
		}
		request.setAttribute("nome", u.getNome());

		
		if (u.getSenha().equals(senhaAtual) && senhaNova.equals(confirmacao)) {
			UsuarioDAO uDAO = new UsuarioDAO();
			u.setSenha(senhaNova);
			uDAO.update(u);
			System.out.println("Senha do usuario '" + u.getLogin() + "' alterada com sucesso.");
			request.setAttribute("message", "right_pw");
			request.getRequestDispatcher("/perfil.jsp").forward(request, response);
		}
		else if (u.getSenha().equals(senhaAtual)){
			System.out.println("Senhas nao sao iguais! " + senhaNova + " e " + confirmacao);
			request.setAttribute("message", "wrong_conf");
			request.getRequestDispatcher("/perfil.jsp").forward(request, response);
		}	
		else {
			System.out.println("Senha do usuario '" + u.getLogin() + "' incorreta. Senha fornecida: " + senhaAtual);
			request.setAttribute("message", "wrong_pw");
			request.getRequestDispatcher("/perfil.jsp").forward(request, response);
		}	
	}
}

