package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/display")
public class DisplayConnection extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL Database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb",
                    "root",
                    "pavan@1234"
            );

            // Create Statement
            Statement st = con.createStatement();

            // Execute SQL Query
            ResultSet rs = st.executeQuery("SELECT * FROM college");

            // Display HTML
            out.println("<html>");
            out.println("<head>");
            out.println("<title>College Student Records</title>");
            out.println("</head>");

            out.println("<body>");

            out.println("<h1>College Student Records</h1>");

            out.println("<table border='1'>");

            out.println("<tr>");
            out.println("<th>Roll No</th>");
            out.println("<th>Name</th>");
            out.println("<th>Year</th>");
            out.println("<th>Course</th>");
            out.println("<th>Fee</th>");
            out.println("</tr>");

            // Display records
            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>" + rs.getInt("rollno") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("year") + "</td>");
                out.println("<td>" + rs.getString("course") + "</td>");
                out.println("<td>" + rs.getDouble("fee") + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</body>");
            out.println("</html>");

            // Close database resources
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Database Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}