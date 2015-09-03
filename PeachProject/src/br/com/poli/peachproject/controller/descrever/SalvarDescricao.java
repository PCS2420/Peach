package br.com.poli.peachproject.controller.descrever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.poli.peachproject.infrastructure.DescricaoDAO;
import br.com.poli.peachproject.model.description.Descricao;

/**
 * Servlet implementation class SelecionarImagem
 */
@WebServlet("/SalvarDescricao")
public class SalvarDescricao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalvarDescricao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_descricao = Integer.parseInt(request.getParameter("id_descricao"));
		String descricao = request.getParameter("descricao");
		boolean isBackup = request.getParameter("backup") != null;
		
		DescricaoDAO dDAO = new DescricaoDAO();
		// Tres parametros -> um pouco redundante, mas espero que faca o trabalho
		Descricao d = dDAO.retrieveById(id_descricao);
		if (isBackup) {
			d.setTexto_backup(descricao); //TODO errei! ops, tem que enviar excecao um layer para cima? como eu sei que deu errrado?
			d.setUltima_mod_backup(Descricao.getCurrentTimeStamp());
		} else {
			d.setTexto(descricao); //TODO errei! ops, tem que enviar excecao um layer para cima? como eu sei que deu errrado?
			d.setUltima_mod_texto(Descricao.getCurrentTimeStamp());
		}
		dDAO.updateWithoutRevisor(d);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson("success"));
	}
}
