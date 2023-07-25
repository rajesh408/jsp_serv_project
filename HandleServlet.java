package com.rajesh.lms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import com.rajesh.lms.dao.RoleDao;
import com.rajesh.lms.dao.UserDAO;
import com.rajesh.lms.service.RoleDaoImpl;
import com.rajesh.lms.service.UserDaoImpl;
import com.rajesh.lms.model.Role;
import com.rajesh.lms.model.User;

/**
 * Servlet implementation class HandleServlet
 */
@WebServlet("/Handle")
public class HandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDAO userDaoImpl = new UserDaoImpl();
	RoleDao roleDaoImpl = new RoleDaoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String useremail = request.getParameter("email");
		String pass = request.getParameter("password");
		System.out.println("Action : " + request.getParameter("action"));
		String action = request.getParameter("action");
		
		
		switch (action) {
		case "login":
			Integer id = userDaoImpl.getUserIdByEmailandPass(useremail, pass);

			if (id != null) {
				System.out.println("here....1"+id);
				User user = userDaoImpl.getUserById(id);
				Role role = roleDaoImpl.getRoleById(user.getRole().getId());
				user.setRole(role);
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				response.sendRedirect("dashboard.jsp");
			}else {
				response.getWriter().print("Email & Password incorrect");
			}
			break;

		case "logout":
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("login.jsp");
			break;
			
		case "update":
			session = request.getSession();
			User u = (User)session.getAttribute("user");
			
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			
			
			u.setName(firstName + " "+ lastName);
			u.setEmail(useremail);
			u.setPassword(pass);
			u.setUpdatedBy(u.getRole());
			
			Integer result = userDaoImpl.updateUser(u);
			
			if (result > 0) {
				response.sendRedirect("my-profile.jsp");
			}
			
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
