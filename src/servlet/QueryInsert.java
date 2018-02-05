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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String tableName = request.getParameter("paramT");
		String dbName = request.getParameter("dbName");
		String[] query = request.getParameterValues("query");
		
		// entity query for corresponding bot
		Connection con = new DBConnection().checkConnection(dbName,"root");
		if(con != null){
			StringBuilder sql = new StringBuilder("INSERT INTO "+tableName+"(query) VALUES(");
			for(int i = 0; i<query.length; i++){
				sql.append("'"+query[i]+"'"); 
				if(i != (query.length - 1) ){
					sql.append(",");
				}
			}
			sql.append(")");
			pw.println(sql);
			try {
				PreparedStatement pst = con.prepareStatement(sql.toString());
				int status = pst.executeUpdate();
				if(status > 0){
					String sql2 = "SELECT * FROM "+tableName;
					pst = con.prepareStatement(sql2);
					ResultSet rst = pst.executeQuery();
					List<String> list = new ArrayList<>();
					while(rst.next()){
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
