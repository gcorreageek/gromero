<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>La Ruta Lib Web es:${session_ruta}</h2>
<br>
<form action="ingresar_ruta_lib.html" method="post"   >
	<input type="text" name="ruta" />
	<input type="submit" value="Cambiar Ruta" >
</form>

</body>
</html>