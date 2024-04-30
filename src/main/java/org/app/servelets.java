package org.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;




@WebServlet(
        name = "servelets",
        urlPatterns = {"/servetelt"},
        description = "A simple servlet that prints 'yees' on the browser"
)
public class servelets extends  HttpServlet{


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ;

        System.out.println("good");
    }

    private static final Logger LOGGER = Logger.getLogger(servelets.class.getName());
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ;
        System.out.println("hope its working");
        PrintWriter out = resp.getWriter();


        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/cleanIt";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);

            // Create a SQL statement
            // Prepare the SQL statement
            String sql = "INSERT INTO login (name, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);

            // Get the form data
            String name = req.getParameter("name");
            String pass = req.getParameter("password");

            // Set the parameters
            stmt.setString(1, name);
            stmt.setString(2, pass);

            // Execute the SQL statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                resp.sendRedirect("login.jsp.html");
            } else {
                out.println("<h1>Failed to insert data</h1>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while processing the request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // Close the database resources
            try {
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

