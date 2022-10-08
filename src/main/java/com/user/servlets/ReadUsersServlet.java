package com.user.servlets;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/readServlet")
public class ReadUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public void init() { // Called only once

		try {
			System.out.println("init");
			Class.forName("com.mysql.cj.jdbc.Driver"); // Required for tomact
			connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "Venky1234@");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) // doGet is ued to get info
			throws ServletException, IOException {
		System.out.println("doGet");

		try {
			Statement statement = connection.createStatement(); // Creation of statement
			ResultSet resultSet = statement.executeQuery("select * from user");
			PrintWriter out = response.getWriter();

			out.print("<table>");
			out.print("<tr>");
			out.print("<th>");
			out.print("First Name");
			out.print("</th>");
			out.print("<th>");
			out.print("Last Name");
			out.print("</th>");
			out.print("<th>");
			out.print("Email");
			out.print("</th>");
			out.print("</tr>");
			while (resultSet.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.print(resultSet.getString(1));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(2));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(3));
				out.println("</td>");
				out.println("</tr>");
			}
			out.print("</table>");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() { // Called only once

		try {
			System.out.println("destroy");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
