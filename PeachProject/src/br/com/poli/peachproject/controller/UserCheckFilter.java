package br.com.poli.peachproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.poli.peachproject.model.users.Revisor;
import br.com.poli.peachproject.model.users.RevisorPontuavel;
import br.com.poli.peachproject.model.users.Usuario;

/**
 * Servlet Filter implementation class UserCheckFilter
 */
@WebFilter(filterName="UserCheckFilter", urlPatterns="/*")
public class UserCheckFilter implements Filter {
	private ArrayList<String> urlList;

    /**
     * Default constructor. 
     */
    public UserCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		boolean allowedRequest = false;
		
		for (String inner : urlList) {
			if (url.contains(inner)) {
				allowedRequest = true;				
			}
		}
		
		if (!allowedRequest) {
			// place your code here
	        HttpSession session = request.getSession();
	        Usuario user = (Usuario) session.getAttribute("user");
	        
	        if (user == null){
	        	System.out.println("Usuario nao reconhecido na sessao para request > " + url);
	        	response.sendRedirect("Sobre");
	            return;
	        } else if ((url.contains("AvaliarDescricao") || url.contains("EnviarRevisao") || url.contains("SelecionarLivroRevisar")) && 
	        			!(user instanceof RevisorPontuavel || user instanceof Revisor)) {
	        	// Precisa de autenticao para revisor mas nao e revisor
	        	System.out.println("Usuario nao revisor na sessao para request > " + url);
	        	response.sendRedirect("Sobre");
	            return;
	        }
		}
		chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String urls = "/sobre.jsp,/include_header.jsp,/Login,/Cadastrar,/Sobre,"
				+ "/js/javascript.js,/css/header.css,/css/index.css,/img/peach.jpg";
		StringTokenizer token = new StringTokenizer(urls, ",");
		
		this.urlList = new ArrayList<String>();
		
		while(token.hasMoreTokens()) {
			String currentToken = token.nextToken();
			System.out.println("Allowed request: [" + currentToken + "]");
			this.urlList.add(currentToken);
		}

	}
}
