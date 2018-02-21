package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/MyBotHandler")
public class MyBotHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter pw = response.getWriter();
		String query = request.getParameter("query");
		HttpSession session = request.getSession(false);
		String dbName = (String) session.getAttribute("dbName");
		Connection con = new DBConnection().checkConnection(dbName, "root");

		if (con != null) {
			// getting all keywords
			String sql = "SELECT table_name from information_schema.tables WHERE table_schema='" + dbName + "'";
			try {
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rst = pst.executeQuery();
				// checking how many keywords the query contains
				List<String> keywords = new ArrayList<>();
				while (rst.next()) {
					String keyword = rst.getString("table_name");
					if (query.contains(keyword)) {
						keywords.add(keyword);
					}
				}

				// logic for single keyword query
				int length = keywords.size();
				if (length == 1) {
					sql = "SELECT * FROM " + keywords.get(0);
					pst = con.prepareStatement(sql);
					rst = pst.executeQuery();
					while (rst.next()) {
						String preGeneratedQuery = rst.getString("query");
						if (query.equals(preGeneratedQuery)) {
							int replyId = rst.getInt("replyId");
							pst = con.prepareStatement("SELECT * FROM replies WHERE replyId = ?");
							pst.setInt(1, replyId);
							rst = pst.executeQuery();
							if (rst.next()) {
								pw.write(rst.getString("reply"));
							}
						}
					}
					// if no query matched
					pw.write("Single Keyword Matched");
				}
				// logic for multiple keywords query
				else if (length > 1) {
					for (String keyword : keywords) {
						sql = "SELECT * FROM " + keyword;
						pst = con.prepareStatement(sql);
						rst = pst.executeQuery();
						while (rst.next()) {
							String preGeneratedQuery = rst.getString("query");
							if (query.equals(preGeneratedQuery)) {
								int replyId = rst.getInt("replyId");
								pst = con.prepareStatement("SELECT * FROM replies WHERE replyId = ?");
								pst.setInt(1, replyId);
								rst = pst.executeQuery();
								if (rst.next()) {
									pw.write(rst.getString("reply"));
									break;
								}
							}
						}
					}
					pw.write("Multiple Keywords Matched");
				} else {
					pw.write("Sorry I couldn't get you :( ");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
