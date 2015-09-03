package br.com.poli.peachproject.controller.descrever;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.infrastructure.DescricaoDAO;
import br.com.poli.peachproject.infrastructure.ImagemDAO;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Imagem;

import com.google.gson.Gson;

/**
 * Servlet implementation class ApagarDescricao
 */
@WebServlet("/ApagarDescricao")
public class ApagarDescricao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApagarDescricao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_imagem = Integer.parseInt(request.getParameter("id_imagem"));
		
		DescricaoDAO dDAO = new DescricaoDAO();
		// Tres parametros -> um pouco redundante, mas espero que faca o trabalho
		Descricao d = dDAO.retrieveByIdImagem(id_imagem);
		dDAO.delete(d);
		
		ImagemDAO iDAO = new ImagemDAO();
 		Imagem im = iDAO.retrieveImagemById(id_imagem);
 		im.setEstado(Imagem.ESTADO0_NAODESCRITA);				
 		iDAO.update(im);
 		
		HttpSession session = request.getSession();
		session.setAttribute("deleted", true);
		response.sendRedirect("Index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_imagem = Integer.parseInt(request.getParameter("id_imagem"));
	
		DescricaoDAO dDAO = new DescricaoDAO();
		// Tres parametros -> um pouco redundante, mas espero que faca o trabalho
		Descricao d = dDAO.retrieveByIdImagem(id_imagem);
		dDAO.delete(d);
		
		ImagemDAO iDAO = new ImagemDAO();
 		Imagem im = iDAO.retrieveImagemById(id_imagem);
 		im.setEstado(Imagem.ESTADO0_NAODESCRITA);				
 		iDAO.update(im);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson("success"));
	}

}
