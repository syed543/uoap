package com.journal.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.journal.model.User;

public class JournalFilter implements Filter{

	private List<String> allowed = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		allowed.add("/login");
		allowed.add("/logout");
		allowed.add("/journals");
		allowed.add("/addJournal");
		allowed.add("/updateJournal");
		allowed.add("/deleteJournal");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String pathInfo = request.getPathInfo();
		
		HttpSession session = request.getSession(false);
		
		System.out.println("Going through filter +" + new Date());
		System.out.println("Session : " + session);
		System.out.println("Path Info: " + pathInfo);
		System.out.println("############################");
		
		
		if (session == null || session.getAttribute("user") == null) {
			
			if (allowed.contains(pathInfo)) {
				
				System.out.println("Allowing to into system from allowed condition");
				chain.doFilter(servletRequest, servletResponse);
				return;
			} else {
				
				System.out.println("Not allowed and Returned");
				response.sendRedirect("/login");
				return;
			}
		}
		
		User user = (User) session.getAttribute("user");
		
		System.out.println("Allowed into system");
		
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
