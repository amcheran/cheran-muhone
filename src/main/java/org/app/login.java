package org.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(
        name = "login",
        urlPatterns = {"/login"},
        description = "A simple servlet that prints 'yees' on the browser"
)
public class login extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(servelets.class.getName());



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        // Get the form data

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/cleanIt";
            String dbUsername = "root";
            String dbPassword = "123456";
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepare SQL statement to select username and password from login table
            String sql = "SELECT * FROM login WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if the username and password match
            if (rs.next()) {
                // Username and password match
                // Redirect to welcome page

                out.println("<h1>Authentication suces. Please tryin.</h1>");
                response.sendRedirect("index.html");
            } else {
                // Username and password do not match
                out.println("<h1>Authentication failed. Please tryin.</h1>");
                out.println("<a href=\"login.jsp\">Back to Login</a>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while processing the request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // Close the database resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while closing the database resources", e);
            }
            // Close the PrintWriter
            out.close();
        }
    }


}