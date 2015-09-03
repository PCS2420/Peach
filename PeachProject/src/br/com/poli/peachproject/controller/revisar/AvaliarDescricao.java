package br.com.poli.peachproject.controller.revisar;

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
 * Servlet implementation class AvaliarDescricao
 */
@WebServlet("/AvaliarDescricao")
public class AvaliarDescricao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AvaliarDescricao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_imagem = Integer.parseInt(request.getParameter("id_imagem"));
		int id_descricao = Integer.parseInt(request.getParameter("id_descricao"));
		
		ImagemDAO iDAO = new ImagemDAO();
 		Imagem im = iDAO.retrieveImagemById(id_imagem);
		request.setAttribute("im", im);
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		DescricaoDAO dDAO = new DescricaoDAO();
		Descricao d = dDAO.retrieveById(id_descricao);
		d.setEstado(Descricao.ESTADO2_OCUPADA_POR_REVISOR); // ocupa a descricao 
		d.setId_revisor(u.getId());
		dDAO.update(d);
		
		request.setAttribute("id_descricao", id_descricao);
		request.setAttribute("texto", d.getTexto());
		request.getRequestDispatcher("/avaliar_descricao.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
