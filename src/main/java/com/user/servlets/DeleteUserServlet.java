package com.user.servlets;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public void init() { // Called only once

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Required for tomact
			connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "Venky1234@");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		try {
			Statement statement = connection.createStatement(); // Creation of statement
			int result1 = statement.executeUpdate("delete from user where email='" + email + "'");

			PrintWriter out = response.getWriter();
			if (result1 > 0) { // result1 gives integer values
				out.print("<h1>User Deleted</h1>");
			} else {
				out.print("<h1>Error deleting the user</h1>");
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
