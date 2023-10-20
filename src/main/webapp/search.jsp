<%@page import = "java.util.ArrayList"%>
<%@page import = "com.Accio.SearchResult"%>

<html>

<body>

<link rel = "stylesheet" type = "text/css" href = "styles.css">
<head>

 <h1> Simple Search Engine </h1>
    <div class = "wrapper1">
            <form action = "Search">
               <input type = "text" name="keyword" placeholder="Search..."></input>
               <button type = "submit" > Search </button>
            </form>
    </div>
    <table border = 2>
        <tr>
            <th>Title</th>
            <th>Link</th>
        </tr>
        <%
            ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
            for(SearchResult result: results ){
        %>
        <tr>
            <td><%out.println(result.getTitle());%></td>
            <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
        </tr>
        <%
            }
        %>

    </table>
    </head>
</body>
</html>