
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Loading...</title>
    </head>
    <body>
        <%
		List<String> l = new LinkedList<String>();
		for (int x=0;x<2;x++) {
			for (int i=0;i<1000000;i++) {
				l.add("Ryan"+i+"F"+Math.random());
			}
			Thread.sleep(1000);
			l.clear();
			Thread.sleep(2000);
		}
		%>
    </body>
</html>
