package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;

@WebServlet("/QueryInsert")
public class QueryInsert extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String tableName = request.getParameter("paramT");
		String dbName = request.getParameter("dbName");
		String[] query = request.getParameterValues("query");
		String answer = request.getParameter("answer");

		// entity query for corresponding bot
		Connection con = new DBConnection().checkConnection(dbName, "root");
		if (con != null) {
			int replyId = 0;
			PreparedStatement pst;
			if (answer != null && answer != "") {
				String sql = "INSERT INTO replies(reply) values(?)";
				try {
					pst = con.prepareStatement(sql);
					pst.setString(1, answer);
					int row = pst.executeUpdate();
					if (row > 0) {
						// pw.println("ok2");
						// selecting latest answer from reply table
						String replySql = "SELECT MAX(replyId) as replyId FROM replies";
						pst = con.prepareStatement(replySql);
						ResultSet rst = pst.executeQuery();
						if (rst.next()) {
							replyId = rst.getInt("replyId");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				String replySql = "SELECT MAX(replyId) as replyId FROM replies";
				try {
					pst = con.prepareStatement(replySql);
					ResultSet rst = pst.executeQuery();
					if (rst.next()) {
						replyId = rst.getInt("replyId");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(query,replyId) VALUES");
			for (int i = 0; i < query.length; i++) {
				sql.append("('" + query[i] + "'," + replyId + ")");
				if (i != (query.length - 1)) {
					sql.append(",");
				}
			}
			// sql.append(")");
			pw.println(sql);
			try {
				pst = con.prepareStatement(sql.toString());
				int status = pst.executeUpdate();
				if (status > 0) {
					String sql2 = "SELECT * FROM " + tableName;
					pst = con.prepareStatement(sql2);
					ResultSet rst = pst.executeQuery();
					List<String> list = new ArrayList<>();
					while (rst.next()) {
						list.add(rst.getString("query"));
					}
					con.close();
					request.setAttribute("queries", list);
					RequestDispatcher rd = request.getRequestDispatcher("query.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
