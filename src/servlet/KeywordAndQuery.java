package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/KeywordAndQuery")
public class KeywordAndQuery extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String keyword = request.getParameter("keyword");
		String[] query = request.getParameterValues("query");
		String reply = request.getParameter("reply");
		// String dbName = request.getParameter("dbName"); // for query
		HttpSession session = request.getSession(false);
		String dbName = (String) session.getAttribute("dbName");

		if (keyword != null && query != null && reply != null && dbName != null) {
			boolean flag = true;
			for (String s : query) {
				if (!s.contains(keyword)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				try {
					Connection con = new DBConnection().checkConnection(dbName, "root");
					// creating reply table
					pw.println("db name is " + dbName + " ok0");
					String tableSql = "CREATE TABLE IF NOT EXISTS replies( replyId int AUTO_INCREMENT PRIMARY KEY,reply varchar(100) not null)";
					PreparedStatement pst = con.prepareStatement(tableSql);
					int status = pst.executeUpdate();
					// if(status > 0){
					pw.println("ok1");
					// inserting into reply table
					String sql = "INSERT INTO replies(reply) values(?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, reply);
					int row = pst.executeUpdate();

					if (row > 0) {
						pw.println("ok2");
						// selecting latest answer from reply table
						String replySql = "SELECT MAX(replyId) as replyId FROM replies";
						pst = con.prepareStatement(replySql);
						ResultSet rst = pst.executeQuery();
						if (rst.next()) {
							int replyId = rst.getInt("replyId");
							pw.println("ok3");
							// creating keyword table
							String keywordTableSql = "CREATE TABLE IF NOT EXISTS " + keyword
									+ "( id int AUTO_INCREMENT PRIMARY KEY,query varchar(100) not null,replyId int not null,FOREIGN KEY (replyId) REFERENCES replies(replyId))";
							pst = con.prepareStatement(keywordTableSql);
							int status2 = pst.executeUpdate();
							// if(status2 > 0){
							pw.println("ok4");
							StringBuilder sb = new StringBuilder("INSERT INTO " + keyword + "(query,replyId) VALUES");
							for (int i = 0; i < query.length; i++) {
								sb.append("('" + query[i] + "'," + replyId + ")");
								if (i != (query.length - 1)) {
									sb.append(",");
								}
							}
							pst = con.prepareStatement(sb.toString());
							int finalStatus = pst.executeUpdate();
							if (finalStatus > 0) {
								RequestDispatcher rd = request.getRequestDispatcher("adminHome.jsp");
								rd.forward(request, response);
								// pw.println("Bot Has Been Trained
								// Successfully." + "<a
								// href='adminHome.jsp'>GoTo Home</a>");
							}
							// }
						}
					}
					// }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				pw.write("i th no query doesnt contain keyword");
			}
		}
	}

}
