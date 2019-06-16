package com.journal.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JournalFilter implements Filter{

	private List<String> allowed = new ArrayList<String>();
	private List<String> allowedWithoutLogin = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		allowedWithoutLogin.add("/journals");
		allowedWithoutLogin.add("/login");
		allowedWithoutLogin.add("/logout");
		allowedWithoutLogin.add("/addMenuScript");
		allowedWithoutLogin.add("/getArticles");
		allowedWithoutLogin.add("/getJournalById");
		allowedWithoutLogin.add("/getArticlesByJournalId");
		allowedWithoutLogin.add("/getEditorsByJournalId");
		allowedWithoutLogin.add("/accept");
		allowedWithoutLogin.add("/reject");
		allowedWithoutLogin.add("/editors");

		
		allowed.add("/login");
		allowed.add("/logout");
		
		//Journals
		allowed.add("/journals");
		allowed.add("/addJournal");
		allowed.add("/updateJournal");
		allowed.add("/deleteJournal");
		
		//Editors
		allowed.add("/editors");
		allowed.add("/addEditor");
		allowed.add("/updateEditor");
		allowed.add("/deleteEditor");
		
		//Reviewers
		allowed.add("/reviewers");
		allowed.add("/addReviewer");
		allowed.add("/updateReviewer");
		allowed.add("/deleteReviewer");
		
		//Reviewers
		allowed.add("/getArticles");
		allowed.add("/addArticle");
		allowed.add("/updateArticle");
		allowed.add("/deleteArticle");
		allowed.add("/getArticlesByState");

		//Reviewers
		allowed.add("/menuScriptList");
		allowed.add("/addMenuScript");
		allowed.add("/updateMenuScript");
		allowed.add("/deleteMenuScript");
		
		allowed.add("/getReviewersByJournalId");
		allowed.add("/approveMenuScript");
		allowed.add("/rejectMenuScript");
		allowed.add("/reviewerDecline");
		allowed.add("/reviewerAccept");
		
		allowed.add("/downloadArticle");
		allowed.add("/updateArticleState");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String pathInfo = request.getPathInfo();
		
		System.out.println("PathInfo :" + pathInfo);
		System.out.println("Request URI :" + request.getRequestURI());
		System.out.println("Request url: " + request.getRequestURL());

		
		HttpSession session = request.getSession(false);
		
		int index = pathInfo.indexOf("/", 2);
		
		if (index > 2) {
			pathInfo = pathInfo.substring(0, index);
		}
		
		if (!allowedWithoutLogin.contains(pathInfo)) {
			
			if (session == null || session.getAttribute("user") == null) {
				System.out.println("Not allowed and Returned For no Session or User:" + pathInfo);
				response.sendRedirect("/login");
				return;
			} else {
				System.out.println("User :" + session.getAttribute("user"));
			}
			System.out.println("Cutdowned path: " + pathInfo);
			
			if (!allowed.contains(pathInfo)) {
				System.out.println("Not allowed and Returned not present in allowed list:"+pathInfo);
				response.sendRedirect("/login");
				return;
			}
		}
		System.out.println("Allowed into system:" + pathInfo);
		
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
