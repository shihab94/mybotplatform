package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/UseBot")
public class UseBot extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String dbName = request.getParameter("botName");
		Connection con = new DBConnection().checkConnection(dbName, "root");
		if (con != null) {
			HttpSession session = request.getSession();
			session.setAttribute("dbName", dbName);
			response.sendRedirect("myBot.jsp");
			// RequestDispatcher rd = request.getRequestDispatcher("myBot.jsp");
			// rd.forward(request, response);
		} else {
			pw.write("Please Enter Correct Bot Name. There is no bot named like " + dbName);
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
	}

}
