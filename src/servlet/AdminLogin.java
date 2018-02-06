package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet(description = "This page hadles the login operation and also redirect to signup page for non registered", urlPatterns = {
		"/AdminLogin" })
public class AdminLogin extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String action = request.getParameter("action");

		// checking login or signup
		if (action.equals("Login")) {
			// form validation
			if ((userName != "" && userName != null) && (password != "" && password != null)) {
				Connection con = new DBConnection().checkConnection("myBot", "root");
				if (con != null) {
					pw.println("login");
					String sql = "SELECT * FROM admininfo WHERE userName = ? AND password = ?";
					try {
						PreparedStatement pst = con.prepareStatement(sql);
						pst.setString(1, userName);
						pst.setString(2, password);
						ResultSet rst = pst.executeQuery();

						if (rst.next()) {
							// adding a session
							HttpSession session = request.getSession();
							session.setAttribute("user", userName);
							// Bots table query for a particular admin all bots
							int adminId = rst.getInt("adminId");
							session.setAttribute("adminId", adminId);
							String sql2 = "SELECT * FROM bots WHERE adminId = ?";
							pst = con.prepareStatement(sql2);
							pst.setInt(1, adminId);
							ResultSet rst2 = pst.executeQuery();
							ArrayList<String> botNames = new ArrayList<>();
							while (rst2.next()) {
								botNames.add(rst2.getString("name"));
							}
							session.setAttribute("bots", botNames);
							RequestDispatcher rd = request.getRequestDispatcher("adminHome.jsp");
							rd.forward(request, response);
						} else {
							pw.println("Please SignUp First");
							RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
							rd.include(request, response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					pw.println("Connection Error!");
				}
			} else {
				pw.println("user name and password can't be empty");
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/adminSignUp.jsp");
			rd.forward(request, response);
		}
	}

}
