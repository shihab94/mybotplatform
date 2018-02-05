package servlet;

import java.util.List;
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

import util.DBConnection;


@WebServlet("/SingleBotHandler")
public class SingleBotHandler extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String dbName =  request.getParameter("dbName");
		pw.println(dbName);
		
		// entity query for corresponding bot
		Connection con = new DBConnection().checkConnection(dbName,"root");
		if(con != null){
			try {
				String tableNameSql = "SELECT table_name FROM information_schema.tables where table_schema='"+dbName+"'";
				PreparedStatement pst = con.prepareStatement(tableNameSql);
				ResultSet rst = pst.executeQuery();
				List<String> list = new ArrayList<>();
				while(rst.next()){
					String table = rst.getString("table_name");
					if(!table.equals("replies")){
						pw.println(table);
						list.add(table);
					}
				}
				request.setAttribute("entities", list);
				RequestDispatcher rd = request.getRequestDispatcher("singleBot.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
