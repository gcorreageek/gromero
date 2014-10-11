<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
<!-- 	<link rel="stylesheet" href="../../themes/base/jquery.ui.all.css"> -->
<!-- 	<script src="../../jquery-1.10.2.js"></script> -->
<!-- 	<script src="../../ui/jquery.ui.core.js"></script> -->
<!-- 	<script src="../../ui/jquery.ui.widget.js"></script> -->
<!-- 	<script src="../../ui/jquery.ui.datepicker.js"></script> -->
<!-- 	<link rel="stylesheet" href="../demos.css"> -->
	
<link rel="stylesheet" href="datepicker/jquery.ui.all.css">
<script src="datepicker/jquery-1.10.2.js"></script>
<script src="datepicker/jquery.ui.core.js"></script>
<script src="datepicker/jquery.ui.widget.js"></script>
<script src="datepicker/jquery.ui.datepicker.js"></script>
<script src="datepicker/jquery.ui.datepicker-es.js"></script>
<link rel="stylesheet" href="datepicker/demo.css">

<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- <script src="http://code.jquery.com/jquery-1.10.2.js"></script> -->
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      
<script>
$(function() {
	$( "#fechaInicioConsultaIngresos1" ).datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat:"dd/mm/yy",
		changeMonth: true,
		changeYear: true
	});
	$( "#fechaInicioConsultaIngresos2" ).datepicker({
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		dateFormat:"dd/mm/yy",
		changeMonth: true,
		changeYear: true
	}); 
	var spinner = $( "#spinner" ).spinner();
// 	spinner.spinner( "destroy" );
// 	spinner.spinner( "enable" );
// 	spinner.spinner( "disable" );
});
</script>
<body>
<h2>Hello World!</h2>
<a href="templates_b/clearzone/indexMio3.html">Template-ClearZone</a>
<form action="#"   >
<table> 
  <tr>
    <td>Nombress:</td>
    <td><input  name="fecha1" type="text" value="01/01/2007"   id="fechaInicioConsultaIngresos1" />
    <input  name="fecha2" type="text"  id="fechaInicioConsultaIngresos2" /></td>
  </tr>
  <tr>
    <td> </td>
    <td><input  id="spinner" name="spinner" value="20" style="width: 40px;" readonly  /></td>
<!--     <td><input  name="Saludar" type="submit" /></td> -->
  </tr>
  <a href="#"  onclick="javascript:consultar();" >Pruebame!</a>
<!--   <input  type="button"  onclick="javascript:consultar();"    value="Consultarress"  name="jj"  id="ss"  />   -->
<input type="submit"  value="Consultar"  style="color: #003366;font-weight: bold;background: white;border:2;  "    />
</table> 
</form>

</body>
</html>
