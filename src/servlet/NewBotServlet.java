package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/NewBotServlet")
public class NewBotServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String botName = request.getParameter("botName");
		HttpSession session = request.getSession(false);
		int adminId = (int) session.getAttribute("adminId");

		if (botName != null && botName != "") {
			Connection con = new DBConnection().checkConnection("myBot", "root");
			if (con != null) {
				String sql = "INSERT INTO bots(name,adminId) values(?,?)";
				try {
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, botName);
					pst.setInt(2, adminId);
					int status = pst.executeUpdate();
					if (status > 0) {
						// creating new database for new bot
						con.close();
						con = new DBConnection().checkConnection("", "root");
						String sql2 = "CREATE DATABASE " + botName;
						pst = con.prepareStatement(sql2);
						int status2 = pst.executeUpdate();
						if (status2 > 0) {
							session.setAttribute("dbName", botName);
							RequestDispatcher rd = request.getRequestDispatcher("/keywordAndQuery.jsp");
							rd.forward(request, response);
						} else {
							// delete the new row of 'bots' table if the create
							// database query failed
						}
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
	}

}
