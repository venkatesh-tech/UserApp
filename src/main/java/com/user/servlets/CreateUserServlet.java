package com.user.servlets;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/addServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public void init(ServletConfig config) { // Called only once

		try {
			ServletContext context = config.getServletContext();
			System.out.println("init");

			Enumeration<String> parameterNames = context.getInitParameterNames();
			while (parameterNames.hasMoreElements()) {
				String eachName = parameterNames.nextElement();
				System.out.println("context param name: " + eachName);
				System.out.println("context param value: " + context.getInitParameter(eachName));

			}

			Class.forName("com.mysql.cj.jdbc.Driver"); // Required for tomact
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			Statement statement = connection.createStatement(); // Creation of statement
			int result1 = statement.executeUpdate("insert into user values('" + firstName + "','" + lastName + "','"
					+ email + "','" + password + "')");

			PrintWriter out = response.getWriter();
			if (result1 > 0) { // result1 gives integer values
				out.print("<h1>User Created</h1>");
			} else {
				out.print("<h1>Error creating the user</h1>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

//		doGet(request, response);
	}

	public void destroy() { // Called only once

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
