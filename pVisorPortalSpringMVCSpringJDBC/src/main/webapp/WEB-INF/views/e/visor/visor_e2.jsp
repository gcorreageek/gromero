<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<h2>Visor Extranet</h2>

<form>
<label>Empresa:</label>
<select>
	<option value="0" >Seleccionar</option>
	<option value="1" >Primax</option>
	<option value="2" >Coesti</option>
	<option value="3" >Nexo</option>
</select>
<br>
<label>Area/Seccion:</label>
<select>
	<option value="0" >Seleccionar</option>
	<option value="1" >Administrador</option> 
	<option value="2" >Gerencia General</option> 
</select>
<br>
<label>Tipo Documental:</label>
<select>
	<option value="0" >Seleccionar</option>
	<option value="1" >Notas de Debito</option>
	<option value="2" >Facturas</option>
	<option value="3" >Boletas</option>
</select>
<br>
<table> 
  <tr>
    <td>EESS</td>
    <td>Tipo Informe</td>
    <td>Fecha</td>
    <td>Opcion</td>
  </tr>
  <tr>
    <td><input type="text" /></td>
    <td><input type="text" /></td>
    <td><input type="text" /></td>
    <td><a href="#" >[Buscar]</a></td>
  </tr> 
</table>
<h2>Resultado</h2>
<table>
 <tr>
 	<td>EESS</td>
 	<td>Tipo Informe</td>
 	<td>Fecha</td>
 	<td>Descargar</td>
 </tr>
 <tr>
 	<td>JJJJJJJJJ</td>
 	<td>Factura</td>
 	<td>02/02/2041</td>
 	<td><a href="javascript:window.open('http://10.11.5.36:8080/pVisorPortal/e/visor/ver_pdf.html','','width=600,height=700,left=50,top=50,toolbar=yes');void 0">[PDF]</a></td>
 </tr>
</table>


</form>









