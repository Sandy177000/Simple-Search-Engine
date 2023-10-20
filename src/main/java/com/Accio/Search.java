package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet  {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        try {
            Connection connection = DatabaseConnection.getConnection();
            // store the query of the user as history
            PreparedStatement preparedStatement = connection.prepareStatement("insert into history values(?,?);");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            ResultSet resultSet = connection.createStatement().executeQuery("select distinct pagetitle,pagelink,(length(lower(pagetext)) - length(replace(lower(pagetext),'"+keyword.toLowerCase()+"','')))/length('"+keyword.toLowerCase()+"') as countoccurence from pages order by countoccurence desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()) {

                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pagetitle"));
                searchResult.setLink(resultSet.getString("pagelink"));
                results.add(searchResult);
            }

            for (SearchResult sr : results) {
                System.out.println(sr.getTitle() + " " + sr.getLink());
            }

            request.setAttribute("results", results);
            request.getRequestDispatcher("search.jsp").forward(request, response); //send data to display

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        } catch (SQLException | ServletException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
