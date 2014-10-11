<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<tr>
<td>d1</td>
<td>d2</td>
<td>d3</td>
<td>d4</td>
<td>d5</td>
</tr>

<c:forEach   var="x" items="${LmmTempo}" >
<tr>
<td>${x.imp112}</td>
<td>${x.imp113}</td>
<td>${x.imp114}</td>
<td>${x.tdirci}</td>
<td>${x.tdirem}</td>
</tr>
</c:forEach>
</table>


</body>
</html>