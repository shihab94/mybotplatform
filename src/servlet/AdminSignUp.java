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


@WebServlet("/AdminSignUp")
public class AdminSignUp extends HttpServlet {
	
    public AdminSignUp() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String userName = request.getParameter("userName");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if( (userName != "" && userName != null) && (password != "" && password != null) ){
			Connection con = new DBConnection().checkConnection("myBot","root");
			if( con != null ){
				String sql = "INSERT INTO admininfo(fullName,userName,password,email) values(?,?,?,?)";
				try {
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, name);
					pst.setString(2, userName);
					pst.setString(3, password);
					pst.setString(4, email);
					int status = pst.executeUpdate();
					if(status > 0){
						pw.println("Your Account Created Successfully. Now Login To Use MyBot");
						RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
						rd.include(request, response);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				pw.println("Connection Error!");
			}
		}else{
			pw.println("user name and password can't be empty");
		}
	}

}
