<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ventana</title>
<style type="text/css">
#divPDF{
	background: yellow;
	border: 2px solid red;
}
#hrArriba{ 
	background-color: white;
	height: 40px;
	width: 503px;
	position: fixed;
 	top: -10px; 
	left: 7px;
	border: none;
}
#hrCostado{ 
	background-color: white;
	height: 590px;
	width: 20px;
	position: fixed;
 	top: 30px; 
	left: 490px;
	border: none;
}
</style>
</head>
<body> 
<c:url value="/resources/template1/manual1.pdf" />
<c:url value="/resources/manual1.pdf" />
<c:url value="/resources/manual1.pdf" />
<!-- <hr id="hrArriba" > -->
<embed src="<c:url value="/resources/manual.pdf" />" width="500" height="620">
<!-- <hr id="hrCostado" > -->

<!-- <iframe src="http://docs.google.com/gview?url=http://victorpimentel.com/stuff/rubik.pdf&embedded=true" style="width:500px; height:375px;" frameborder="0"></iframe> -->

</body>
</html>