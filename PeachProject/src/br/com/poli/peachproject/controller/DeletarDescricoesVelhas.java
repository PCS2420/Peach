package br.com.poli.peachproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.poli.peachproject.infrastructure.DescricaoDAO;
import br.com.poli.peachproject.infrastructure.ImagemDAO;
import br.com.poli.peachproject.infrastructure.MYSQLConnector;
import br.com.poli.peachproject.model.description.Descricao;
import br.com.poli.peachproject.model.description.Imagem;

/**
 * Application Lifecycle Listener implementation class DeletarDescricoesVelhas
 *
 */
@WebListener
public class DeletarDescricoesVelhas implements ServletContextListener {
	private ScheduledExecutorService scheduler;
	
    /**
     * Default constructor. 
     */
    public DeletarDescricoesVelhas() {
        System.out.println("Inicializando sistema de exclusao de descricoes inativas inacabadas.");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	scheduler.shutdownNow();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new ProcuraEDeletaDescricoesVelhas(), 1, 1, TimeUnit.MINUTES);
    }
	
    public class ProcuraEDeletaDescricoesVelhas extends TimerTask {

		@Override
		public void run() {
			try {
				Connection conn = MYSQLConnector.openConnection();
				/*String query = "SELECT * FROM descricao WHERE estado = ? AND DATEDIFF(NOW(), ultima_mod_texto) >= ? OR " 
				 		+ "DATEDIFF(NOW(), ultima_mod_backup) >= ?";*/
				String query = "SELECT * FROM descricao WHERE estado = ? AND TIMESTAMPDIFF(MINUTE,ultima_mod_texto,NOW()) > ? OR "
						+ "TIMESTAMPDIFF(MINUTE,ultima_mod_backup,NOW()) > ?";
			    PreparedStatement pst = conn.prepareStatement(query);
			    pst.setInt(1, Descricao.ESTADO0_NAOACABADA); // imagem ocupada
			    //pst.setInt(2, 3); // 3 DIAS
			    //pst.setInt(3, 3); // 3 DIAS
			    pst.setInt(2, 1); // 1 MINUTO
			    pst.setInt(3, 1); // 1 MINUTO
			    ResultSet r = pst.executeQuery();
			    
			    ArrayList<Descricao> descricoes = new ArrayList<Descricao>();
			    while(r.next()) {
			    	descricoes.add(new Descricao(r.getInt("id_descricao"), r.getInt("id_imagem"), r.getInt("id_descritor"),
			    			r.getInt("id_revisor"), r.getInt("estado"), r.getString("texto"), r.getString("texto_backup"),
			    			r.getTimestamp("ultima_mod_texto"), r.getTimestamp("ultima_mod_backup")));
			    }
			    if(!descricoes.isEmpty()) { // se existem descricoes para deletar
			    	DescricaoDAO dDAO = new DescricaoDAO();
			    	ImagemDAO iDAO = new ImagemDAO();
			    	for (Descricao d : descricoes) {
			    		System.out.println("Deletando descricao " + d + "por inatividade e liberando imagem");
			    		Imagem i = iDAO.retrieveImagemById(d.getId_imagem());
			    		i.setEstado(Imagem.ESTADO0_NAODESCRITA);
			    		iDAO.update(i); // libera imagem
			    		dDAO.delete(d); // deleta todas descricoes inativas e libera as imagens			    		
			    	}
			    }
		      	MYSQLConnector.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
    	
    }
}
