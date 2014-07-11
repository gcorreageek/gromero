<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<h2>Visor Extranet</h2>
<head></head>
<body>
<form:form method="post"  action="insertar_usuario.html" commandName="usuario" >
	<form:label path="nombres">Nombres:</form:label>
	<form:input path="nombres"/>
	<br>
	<form:label path="userName">Usuario:</form:label>
	<form:input path="userName"/>
	<br>
	<form:label path="userPass">Contraseña:</form:label>
	<form:input path="userPass"/>
	<br>
	<form:label path="rolD">Rol:</form:label>
	<form:input path="rolD"/>
	<br>
	<input type="submit"  value="Ingresar"  />

</form:form> 
</body>













