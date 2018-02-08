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

@WebServlet("/NewBotServlet")
public class NewBotServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String botName = request.getParameter("botName");
		HttpSession session = request.getSession(false);
		String tableName = (String) session.getAttribute("tableName");

		if (botName != null && botName != "") {
			Connection con = new DBConnection().checkConnection("myBot", "root");
			if (con != null) {
				String sql = "INSERT INTO " + tableName + "(botName) values(?)";
				try {
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, botName);
					int status = pst.executeUpdate();
					if (status > 0) {
						// creating new database for new bot
						// con.close();
						// con = new DBConnection().checkConnection("", "root");
						sql = "CREATE DATABASE " + botName;
						pst = con.prepareStatement(sql);
						status = pst.executeUpdate();
						if (status > 0) {
							sql = "SELECT * FROM " + tableName;
							pst = con.prepareStatement(sql);
							ResultSet rst = pst.executeQuery();
							ArrayList<String> botNames = new ArrayList<>();
							while (rst.next()) {
								botNames.add(rst.getString("botName"));
							}
							session.setAttribute("bots", botNames);
							session.setAttribute("dbName", botName);
							RequestDispatcher rd = request.getRequestDispatcher("/adminHome.jsp");
							rd.include(request, response);
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
