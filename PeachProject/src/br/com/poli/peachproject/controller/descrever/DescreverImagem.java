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
 * Servlet implementation class DescreverImagem
 */
@WebServlet("/DescreverImagem")
public class DescreverImagem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DescreverImagem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_imagem = Integer.parseInt(request.getParameter("id_imagem"));
		
		ImagemDAO iDAO = new ImagemDAO();
 		Imagem im = iDAO.retrieveImagemById(id_imagem);
 		im.setEstado(Imagem.ESTADO1_OCUPADA);				
 		iDAO.update(im);
		request.setAttribute("im", im);
		
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		DescricaoDAO dDAO = new DescricaoDAO();
		int id_descricao = -1; // variavel intermediaria inutil
		Descricao inacabada = dDAO.retrieveByDescritorAndState(u.getId(), Descricao.ESTADO0_NAOACABADA);
		if (inacabada != null) { // nao precisa testar se o usuario existe pois a request seria trapada no Filter
			if (inacabada.getId_imagem() == id_imagem) {
				if (inacabada.getTexto_backup() == null || inacabada.getUltima_mod_texto().after(inacabada.getUltima_mod_backup())) {
					request.setAttribute("texto", inacabada.getTexto());	
				} else {
					request.setAttribute("backup", inacabada.getUltima_mod_backup());
					request.setAttribute("texto", inacabada.getTexto_backup());
				}
				request.setAttribute("retomando", true);
				
				request.setAttribute("id_descricao", inacabada.getId());
				request.setAttribute("id_imagem", id_imagem);
				request.getRequestDispatcher("/descrever_imagem.jsp").forward(request, response);	
			} else {
				response.sendRedirect("Index");
			}
		} else {
			id_descricao = dDAO.create(new Descricao(id_imagem, u.getId(), Descricao.ESTADO0_NAOACABADA, null, null, Descricao.getCurrentTimeStamp(), Descricao.getCurrentTimeStamp()));
			request.setAttribute("id_descricao", id_descricao);
			request.setAttribute("id_imagem", id_imagem);
			
			System.out.println("Criada Descricao para " + u.getLogin() + "@DescreverImagem" );
			request.getRequestDispatcher("/descrever_imagem.jsp").forward(request, response);			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
