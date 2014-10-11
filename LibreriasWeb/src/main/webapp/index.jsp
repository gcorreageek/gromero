<html>
<head>
<link rel="stylesheet" type="text/css" href="/wps/PA_ConsultadeIngresos/css/style.css">
<script type="text/javascript" src="/wps/PA_ConsultadeIngresos/js/util.js"></script>
<script type="text/javascript" src="/wps/PA_ConsultadeIngresos/displaytag/js/script.js"></script>
<link rel="stylesheet" type="text/css" href="/wps/PA_ConsultadeIngresos/displaytag/css/style.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<!-- <script src="/resources/demos/external/jquery.mousewheel.js"></script> -->
<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="/resources/demos/style.css">  -->
<script src="jquery.ui.datepicker-es.js"></script>
<script type="text/javascript">
$(function() { 
	var spinner = $( "#spinner" ).spinner();
// 	spinner.spinner( "disable" );
	spinner.spinner( "value", 20 );
// 	$.datepicker.setDefaults($.datepicker.regional["es"]);
	$.datepicker.setDefaults( $.datepicker.regional[ "fr" ] );
$( "#fechaInicioConsultaIngresos1" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar.gif",
	buttonImageOnly: true
}); 
$( "#fechaInicioConsultaIngresos2" ).datepicker();


$("#inputtt").keyup(function (e) {
    if (e.keyCode == 13) {
    	f_here();
    }
});
var cont =0;
$("#presssiona").click(function ( ) { 
// 	$(this).css("display:none"); 
	console.log('PINTO!');
// 	setTimeout (function(){
// 		$("#imagenCarga").css("display:block");
// 	}, 8000); 
	
// 	cont++;
//    $(this).attr("href", "#"); 
//    console.log('mira:'+cont);
//    $(this).attr("href", "##"); 
//    return false;
});  
$("#presssiona").dblclick(function ( ) {   
   return false;
});

});
function ns_7_HOSVSKV0ACUIB0ASCJKD3G2020_consultar(){ 
	var form = document.getElementById("form_DisplayTag");	
	form.action='/wps/myportal/intranet/!ut/p/c5/04_SB8K8xLLM9MSSzPy8xBz9CP0os3gP_-CwYO8wA0fnUE8nA8dgZy9vF2N3IwMDI6B8JB55A5J0B_v6Oxp4Wvo6-YUauxsbeBLS7eeRn5uqX5AbGlHuqKgIAEeqp_E!/dl3/d3/L0lDU0lKSmdrS0NsRUpJQSEvb01vZ0FFSVFoakVLSVFBQkdjWndqSlFRQVFnIS80QzFiOVdfTnIwZ0RFU1pJSlJDSWtmZyEvN19IT1NWU0tWMEFDVUlCMEFTQ0pLRDNHMjAyMC9fWnB5MDQxMTAwMTMvaWJtLmludi8yNjcyMDM4NjU4Mjk!/';
	form.target='_self';
	form.accion.value = "consultarIngresosAction";
	form.submit();
} 
function f_vacio(){ 
}
function presiona(str){
	window.location.href = str;
}
// $("#idExportarExcel").click(function(){
// 	window.location.href = '<portlet:resourceURL id="exportarExcel"></portlet:resourceURL>';
// });
</script>

<!-- <script type="text/javascript"> -->
  
<!-- function  consultar(){  -->
<!-- // 	alert('Here you go!'); -->
<!-- 	var fecha1 = document.getElementById("fechaInicioConsultaIngresos1").value; -->
<!-- 	var fecha2 = document.getElementById("fechaInicioConsultaIngresos2").value; -->
	
<!-- 	var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g; -->
<!-- 	var vFecha1 = fecha1.match(reg); -->
	
	
<!-- 	if(vFecha1){ -->
<!-- 		var vFecha2 = fecha2.match(reg); -->
<!-- 		if(vFecha2){ -->
<!-- // 			alert('fechas iguales:'+fecha1+'|'+fecha2); -->
<!-- 			var aFecha1 = fecha1.split('/'); -->
<!-- 			var dia1 = aFecha1[0]; -->
<!-- 			var mes1 = aFecha1[1]; -->
<!-- 			var anio1 = aFecha1[2]; -->
			
<!-- 			var aFecha2 = fecha2.split('/'); -->
<!-- 			var dia2 = aFecha2[0]; -->
<!-- 			var mes2 = aFecha2[1]; -->
<!-- 			var anio2 = aFecha2[2]; -->
<!-- // 			alert('=>'+dia1+'/'+mes1+'/'+anio1); -->
<!-- // 			(anio1,mes1,dia1); theDate = new Date(myDateArray[0],myDateArray[1]-1,myDateArray[2]);  -->
<!-- 			var f1 =new Date(aFecha1[2],aFecha1[1]-1,aFecha1[0]);  -->
<!-- 			var f2 =new Date(aFecha2[2],aFecha2[1]-1,aFecha2[0]);  -->
<!-- 			if(f1=f2){ -->
<!-- 				alert('fechas iguales:'+f1+'|'+f2); -->
<!-- 			}else{ -->
<!-- 				if(f1<f2){ -->
<!-- 					alert('f2 es mayor a f1'); -->
<!-- 				}else{ -->
<!-- 					alert('f1 es menor a f2'); -->
<!-- 				}	 -->
<!-- 			} -->
			
			
			 
<!-- 		}else{ -->
<!-- 			alert('Fecha de Fin, invalida'); -->
<!-- 		} -->
<!-- 	}else{ -->
<!-- 		alert('Fecha de Inicio, invalida'); -->
<!-- 	} -->
	
	
<!-- }  -->

<!-- </script> -->
<style type="text/css">
.btnGenerales{
  height: 23px; background-color: #f5f5f0; border-bottom: 1px solid #555555;
  border-right:1px solid #555555; border-top:0px; border-left:0px; font-size: 9px;
  color:black; background-image: url(./img_comuns/btns.png);
  padding-left: 12px; background-repeat: no-repeat; cursor:hand; cursor:pointer;
  margin-left:5px; margin-right:5px; outline-width:0px;
}
.btnGenerales:hover{
  height: 23px; background-color: #f5f5f0; border-bottom: 1px solid #AAAAAA;
  border-right:1px solid #AAAAAA; border-top:0px; border-left:0px;
  font-size: 9px; color:black;padding-left: 12px; background-repeat: no-repeat;
  cursor:hand; cursor:pointer; margin-left:5px; margin-right:5px;outline-width:0px;
  background-image: url(./img_comuns/btns_act.png);
} 
.btnGeneralesDelete{ background-position: 0px -46px; }
.btnGeneralesCancel{ background-position: 0px -23px; }
a#eeexel{
	text-decoration: none;
	color:green;
	font-weight: bold; 
}
</style>
</head>
<body>
<h2>Hello World!</h2>

<!-- <form id="TheForm" method="post" action="prueba" target="TheWindow"> -->
<!-- 	<input type="hidden" name="nombre" value="something" />  -->
<!-- </form> -->
<!-- <script type="text/javascript"> -->
<!-- window.open('', 'TheWindow'); -->
<!-- document.getElementById('TheForm').submit(); -->
<!-- </script> -->

<br>
<script>
function onccclick(){
	 var mapForm = document.createElement("form");
	    mapForm.target = "Map";
	    mapForm.method = "POST"; // or "post" if appropriate
	    mapForm.action = "http://localhost:8080/LibreriasWeb/prueba";

	    var mapInput = document.createElement("input");
	    mapInput.type = "hidden";
	    mapInput.name = "nombre";
	    mapInput.value = 'Gustavo';
	    mapForm.appendChild(mapInput); 
	    document.body.appendChild(mapForm); 
	    map = window.open("", "Map", "status=0,title=0,height=600,width=800,scrollbars=1");

	if (map) {
	    mapForm.submit();
	} else {
	    alert('You must allow popups for this map to work.');
	}
} 
</script>



<a href="#"  onclick="onccclick();" >Template-ClearZone</a>
 <br><br>
 <button  onclick="javascript:window.close();" >l[enter]l</button>
 <br>
 
 
 
 <a href="javascript:close()">Cerrar ventana</a>
Con el Internet Explorer funciona perfectamente, pero cuando lo pruebo en Firefox no funciona.
¿alguno de ustedes podría decirme si existe algún código para cerrar ventana que funcione en cualquier navegador?
Gracias.

Con respecto a esto el enlace para que firefox cierre la ventana debería ser el siguiente:
<a href="javascript:window.close()">cerrar</a> 

<br>

<button type="submit" name="btnExit" id="btnExit" onClick="return exit();">Salir</button>

<br>
 <a href="javascript:window.open('','_self').close();">close</a>
 <br>
 <br>
<table> 
<tr>
<td>Here:</td>
<td><input  id="inputtt"   type="text"    /></td>
</tr>
  <tr>
    <td>Nombress:</td>
    <td><input  name="nom" type="text"   id="fechaInicioConsultaIngresos1" /><input  name="nom" type="text"  id="fechaInicioConsultaIngresos2" /></td>
  </tr>
  <tr>
    <td> </td>
    <td><input  id="spinner" path="spinnerDias"  style="width: 40px;" readonly  /></td>
  </tr>
  <a ondblclick="f_vacio()" >Feliponncio</a>
  <input  type="button"  onclick="javascript:consultar();"  class="btn btncancel"     name="jj"  id="ss"  /> 
  <div  id="divddd" >
  <a  href="index2.jsp"  id="presssiona" >CLickkking</a>
  <img   id="imagenCarga" src="loading_throbber.png" style="display:none"   />
  </div> 
  <a href="#"  id="eeexel"   onclick="presiona('http://localhost:8080/LibreriasWeb/img_comuns/btns.png')" >
		<img src="/wps/PA_CeDesprendimientos/imagenes/desprendimiento/excel.png"   border="0"> 
		Exportar Excel
	</a> 
</table> 

<style type="text/css" >
table#ggus thead tr {
background:red;
}
table#ggus thead tr td br{
background:yellow;
}
table#ggus tbody tr {
background:green;
}

</style>
<table  id="ggus">
<thead>
<tr>
<td><br>Codigo</td>
<td>INPUT<br>Nombre</td>
<td><br>Correo</td>
</tr>
</thead>
<tbody>
<tr>
<td>1221</td>
<td>Gustavo An.Correa C.</td>
<td>gcorrea@ff.com</td> 
</tr>
<tr>
<td>1222</td>
<td>Maria An.Tele C.</td>
<td>ddddd@ddee.com</td> 
</tr>
</tbody>
</table>

</body>
</html>
