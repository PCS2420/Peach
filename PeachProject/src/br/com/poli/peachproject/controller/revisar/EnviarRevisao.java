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
import br.com.poli.peachproject.infrastructure.PersonagemDAO;
import br.com.poli.peachproject.infrastructure.UsuarioDAO;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Imagem;
import br.com.poli.peachproject.model.users.DescritorPontuavel;
import br.com.poli.peachproject.model.users.Personagem;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet implementation class EnviarRevisao
 */
@WebServlet("/EnviarRevisao")
public class EnviarRevisao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarRevisao() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_descricao = Integer.parseInt(request.getParameter("id_descricao"));
		int id_imagem = Integer.parseInt(request.getParameter("id_imagem"));
		String descricao = request.getParameter("descricao");
		String status = request.getParameter("status");
		
		// Busca Usuario Revisor
		HttpSession session = request.getSession();
		Usuario u = (Usuario) session.getAttribute("user");
		
		// Busca Descricao
		DescricaoDAO dDAO = new DescricaoDAO();
		Descricao d = dDAO.retrieveById(id_descricao);
		d.setId_revisor(u.getId());
		
		// Busca Imagem
		ImagemDAO iDAO = new ImagemDAO();
		Imagem i = iDAO.retrieveImagemById(id_imagem);
		
		// Busca Usuario Descritor
		UsuarioDAO uDAO = new UsuarioDAO();
		Usuario descritor = uDAO.retrieveById(d.getId_descritor());
		
		// Busca Personagem -- caso seja atualizado
		PersonagemDAO pDAO = new PersonagemDAO();
		Personagem p = null;
		
		switch(status) {
		case "aprovar":
			if (descritor instanceof DescritorPontuavel || descritor instanceof RevisorPontuavel) {
				// Atualiza pontuacao do descritor
				if (d.getTexto().equals(descricao)) {
					descritor.setPontos(descritor.getPontos() + 3);
				} else {
					descritor.setPontos(descritor.getPontos() + 1);
				}
				
				// Atualiza personagem do descritor
				p = pDAO.retrieveByPoints(descritor.getPontos());
				if (p != null)
					descritor.setId_personagem(p.getId());
				
				if (descritor.getPontos() > 50) { // promovido
					if (descritor instanceof DescritorPontuavel) {
						descritor = ((DescritorPontuavel) descritor).promoverParaRevisorPontuavel();
						descritor.setPromovido(true);
						System.out.println("Usuario " + u.getLogin() + " promovido@EnviarRevisao" );

					}
				}
				uDAO.update(descritor);
			} // caso seja apenas um revisor, nao precisa tratar de pontos e personagen
			// Atualiza imagem com novo Status
			i.setEstado(Imagem.ESTADO3_REVISADA);
			iDAO.update(i);
			
			//Atualiza Descricao com informacoes atuais
			d.setTexto(descricao);
			d.setUltima_mod_texto(Descricao.getCurrentTimeStamp());
			d.setEstado(Descricao.ESTADO3_APROVADA);
			dDAO.update(d);
			
			session.setAttribute("approved_revision", true);
			System.out.println("Aprovada Revisao para " + u.getLogin() + "@EnviarRevisao" );
			break;
		case "desaprovar":
			if (descritor instanceof DescritorPontuavel || descritor instanceof RevisorPontuavel) {
				// Atualiza pontuacao do descritor
				descritor.setPontos(descritor.getPontos() - 3);
				
				// Atualiza personagem do descritor
				p = pDAO.retrieveByPoints(descritor.getPontos());
				if (p != null)
					descritor.setId_personagem(p.getId());
				
				if (u.getPontos() < 45 && u.getPontos() >= -15) { // rebaixado
					descritor = ((RevisorPontuavel) descritor).rebaixarParaDescritor();
					descritor.setPromovido(true);
					System.out.println("Usuario " + u.getLogin() + " rebaixado@EnviarRevisao" );
				} else if (u.getPontos() < -15) {  // bloqueado
					//TODO sistema nao bloqueia ainda
				}
				
				uDAO.update(descritor);
			} // caso seja apenas um revisor, nao precisa tratar de pontos e personagen
			// Atualiza imagem com novo Status
			i.setEstado(Imagem.ESTADO0_NAODESCRITA); // descricao "deletada"
			iDAO.update(i);
			
			// Atualiza Descricao com informacoes atuais
			d.setTexto(descricao);
			d.setUltima_mod_texto(Descricao.getCurrentTimeStamp());
			d.setEstado(Descricao.ESTADO4_DESAPROVADA);			
			dDAO.update(d);
			
			// Deleta descricao pois nao serve
			//dDAO.delete(d);
			
			session.setAttribute("disapproved_revision", true);
			System.out.println("Desaprovada Revisao para " + u.getLogin() + "@EnviarRevisao" );
			break;
		case "liberar":
			// Atualiza imagem com novo Status
			i.setEstado(Imagem.ESTADO2_DESCRITA); // tecnicamente nao existe a necessidade de mudar isso
			iDAO.update(i);
			
			// Atualiza Descricao com informacoes atuais
			d.setEstado(Descricao.ESTADO1_ACABADA);
			dDAO.update(d);
			
			System.out.println("Liberada Revisao para " + u.getLogin() + "@EnviarRevisao" );
			break;
		}
		
		response.sendRedirect("Index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
