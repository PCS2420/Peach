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
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class EnviarDescricao
 */
@WebServlet("/EnviarDescricao")
public class EnviarDescricao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarDescricao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_descricao = Integer.parseInt(request.getParameter("id_descricao"));
		String descricao = request.getParameter("descricao");
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		DescricaoDAO dDAO = new DescricaoDAO();
		// Tres parametros -> um pouco redundante, mas espero que faca o trabalho
		Descricao d = dDAO.retrieveById(id_descricao);
		d.setTexto(descricao);
		d.setUltima_mod_texto(Descricao.getCurrentTimeStamp());
		d.setEstado(Descricao.ESTADO1_ACABADA);
		dDAO.updateWithoutRevisor(d);	
		
		ImagemDAO iDAO = new ImagemDAO();
		Imagem im = iDAO.retrieveImagemById(d.getId_imagem());
		im.setEstado(Imagem.ESTADO2_DESCRITA);
		iDAO.update(im);
		
		session.setAttribute("finished_description", true);
		System.out.println("Finalizada Descricao para " + u.getLogin() + "@EnviarDescricao" );
		response.sendRedirect("Index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
}
