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

@WebServlet("/QueryHandler")
public class QueryHandler extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String dbName =  request.getParameter("dbName");
		String tableName =  request.getParameter("paramT");
		pw.println(dbName);
		
		// entity query for corresponding bot
		Connection con = new DBConnection().checkConnection(dbName,"root");
		if(con != null){
			try {
				String queries = "SELECT * FROM "+tableName;
				PreparedStatement pst = con.prepareStatement(queries);
				ResultSet rst = pst.executeQuery();
				List<String> list = new ArrayList<>();
				while(rst.next()){
					list.add(rst.getString("query"));
				}
				con.close();
				request.setAttribute("queries", list);
				RequestDispatcher rd = request.getRequestDispatcher("query.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
