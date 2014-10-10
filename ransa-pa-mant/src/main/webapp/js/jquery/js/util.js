var ACCION_ACEPTAR = 'A';
var ACCION_CANCELAR = 'C';
var ID_ERROR_CAMPOS_OBLIGATORIO ="Los campos con (*) son obligatorios.";
var SALTO_LINEA = "<br>";
	
//begin desabilitar teclas
document.onkeydown = function()
{ 
	//116-> f5
	//122-> f11
	if (window.event && (window.event.keyCode == 122 || window.event.keyCode == 116))
	{
		window.event.keyCode = 505; 
	}
	
	if (window.event.keyCode == 505)
	{ 
		return false; 
	} 
	
	if (window.event && (window.event.keyCode == 8))
	{
		valor = document.activeElement.value;
		if (valor==undefined) 
			{ return false; } //Evita Back en página.
		else
		{
			if (document.activeElement.getAttribute('type')=='select-one')
			    { return false; } //Evita Back en select.
			if (document.activeElement.getAttribute('type')=='button')
			    { return false; } //Evita Back en button.
			if (document.activeElement.getAttribute('type')=='radio')
			    { return false; } //Evita Back en radio.
			if (document.activeElement.getAttribute('type')=='checkbox')
			    { return false; } //Evita Back en checkbox.
			if (document.activeElement.getAttribute('type')=='file')
			    { return false; } //Evita Back en file.
			if (document.activeElement.getAttribute('type')=='reset')
			    { return false; } //Evita Back en reset.
			if (document.activeElement.getAttribute('type')=='submit')
			    { return false; } //Evita Back en submit.
			else //Text, textarea o password
			{
			    if (document.activeElement.value.length==0)
			        { return false; } //No realiza el backspace(largo igual a 0).
			    else
			    { document.activeElement.value.keyCode = 8; } //Realiza el backspace.
			}
		}
	}
}
//end desabilitar teclas 

function getParametro(nombreParametro){
  	nombreParametro = nombreParametro.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  	var regexS = "[\\?&]" + nombreParametro + "=([^&#]*)";
  	var regex = new RegExp(regexS);
  	var resultados = regex.exec(window.location.href);
  	if(resultados == null)
    	return "";
  	else
    	return resultados[1];
}

var Alert = {
		show: function(titulo,value) {
			//window.scrollTo(0,0);
			new Dialog({title: titulo,
						content: value});
		}
	};

var Confirmar = {
	show: function(titulo, contenido, funcion){
		var dialog = new Dialog({
			title: titulo,
			content: contenido,
			buttons: {
				'Si': function() {
					eval(funcion);
					this.close();
				},
				'No': Dialog.prototype.close
				}
			}
		);
	}	
};

function trimSpace(str){
	return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}
function obtenerValor(id){
	var elemento = document.getElementById(id);
	if(elemento.type == "hidden" || elemento.type == "text"){
		return elemento.value;
	}else{
		return elemento.options[elemento.selectedIndex].value;
	}	
}

/*
Blog Entry:
Ask Ben: Javascript String Replace Method

Author:
Ben Nadel / Kinky Solutions

Link:
http://www.bennadel.com/index.cfm?event=blog.view&id=142

Date Posted:
Jul 17, 2006 at 7:36 AM
*/
String.prototype.replaceAll = function( 
	strTarget, // The substring you want to replace
	strSubString // The string you want to replace in.
	){
	var strText = this;
	var intIndexOfMatch = strText.indexOf( strTarget );
	
	// Keep looping while an instance of the target string
	// still exists in the string.
	while (intIndexOfMatch != -1){
		// Relace out the current instance.
		strText = strText.replace( strTarget, strSubString );
		 
		// Get the index of any next matching substring.
		intIndexOfMatch = strText.indexOf( strTarget );
	}
		 
	// Return the updated string with ALL the target strings
	// replaced out with the new substring.
	return( strText );
}

function isBlank(mensaje){
	return mensaje == null || !(trim(mensaje).length > 0);
}

function isNotBlank(mensaje){
	return mensaje!= null && trim(mensaje).length > 0;
}

function ltrim(cadena) {
	if(cadena != null && cadena.length > 0){
		return cadena.replace(/^\s+/, "");
	}
	return cadena;
}

function rtrim(cadena) {
	if(cadena != null && cadena.length > 0){
		return cadena.replace(/\s+$/, "");
	}
	return cadena;
}

function trim(cadena) {
	if(cadena != null && cadena.length > 0){
		return rtrim(ltrim(cadena));
	}
	return cadena;
}

var EXPRESION_LETTER_NUMBER = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
var EXPRESION_NUMBER_ONLY = "0123456789";
var EXPRESION_NUMBER_PUNTO= "0123456789.";
var EXPRESION_NUMBER_TWO_POINTS = "0123456789:";
var EXPRESION_NUMBER_SLACH = "0123456789/";

function numberOnly(e)
{
	return validaExpresion(e, EXPRESION_NUMBER_ONLY);
}

function numberPuntoOnly(e)
{
	return validaExpresion(e, EXPRESION_NUMBER_PUNTO);
}

function letternumber(e)
{
	return validaExpresion(e, EXPRESION_LETTER_NUMBER);
}
function letternumberSinEnter(e)
{
	return validaExpresionSinEnter(e, EXPRESION_LETTER_NUMBER);
}

function numberTwoPointsOnly(e)
{
	return validaExpresion(e, EXPRESION_NUMBER_TWO_POINTS);
}

function numberSlachOnly(e)
{
	return validaExpresion(e, EXPRESION_NUMBER_SLACH);
}

function validaExpresion(e, cadena){	
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();
	
	// control keys
	//Sin pegar CTRL V
	if(("Vv").indexOf(keychar) > -1 && e.ctrlKey)
	   return false;
	
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) )
	   return true;

	// just numbers
	else if (((cadena).indexOf(keychar) > -1))
	   return true;
	else
	   return false;
}

function validaExpresionSinEnter(e, cadena){	
	var key;
	var keychar;

	if (window.event)
	   key = window.event.keyCode;
	else if (e)
	   key = e.which;
	else
	   return true;
	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();
	
	// control keys
	//Sin pegar CTRL V
	if(("Vv").indexOf(keychar) > -1 && e.ctrlKey)
	   return false;
	
	if(key==13)
		return false;
	
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==27) )
	   return true;

	// just numbers
	else if (((cadena).indexOf(keychar) > -1))
	   return true;
	else
	   return false;
}

function validarNumeros(cadena) {
	cadena = trim(cadena);
	return /^([0-9])*$/.test(cadena);
}

function validarTexto(cadena) {
	cadena = trim(cadena);
	var resultado = /^([a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s])*$/.test(cadena)
	if(!resultado){
		return false;
	}
	resultado = /^\s+/.test(cadena);
	if (resultado){
       return false;
    }
	resultado = /\s+$/.test(cadena);
	if (resultado){
       return false;
    }
	return true;
}

var DATE_PATTERN=/^\d{2}\/\d{2}\/\d{4}$/;
var TIME_PATTERN=/^([01][0-9]|2[0-3]):[0-5][0-9]$/;
//var TIME_PATTERN=/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/;

function validarFormatofecha(fecha, mensaje, nombreCampo){
	var continuar = true;
	var mensajeTemporal="";
    if (fecha != undefined && fecha != "" ){
        if (!DATE_PATTERN.test(fecha)){
        	mensajeTemporal = "Formato de "+ nombreCampo+" no v&aacute;lido. Formato de fecha v&aacute;lido: (dd/mm/aaaa)";
        	continuar = false;
        }
        if(continuar){
	        var dia  =  parseInt(fecha.substring(0,2),10);
	        var mes  =  parseInt(fecha.substring(3,5),10);
	        var anio =  parseInt(fecha.substring(6),10);
	 
			    switch(mes){
			        case 1:
			        case 3:
			        case 5:
			        case 7:
			        case 8:
			        case 10:
			        case 12:
			            numDias=31;
			            break;
			        case 4: case 6: case 9: case 11:
			            numDias=30;
			            break;
			        case 2:
			            if (comprobarSiBisisesto(anio)){ numDias=29 }else{ numDias=28};
			            break;
			        default:
			        	mensajeTemporal = nombreCampo+" introducida err&oacute;nea. Formato de fecha v&aacute;lido: (dd/mm/aaaa)";
			            continuar = false;
			            break;
		    	}
		 		if(continuar){			 			
			        if (dia>numDias || dia==0){
			        	mensajeTemporal = nombreCampo+" introducida err&oacute;nea. Formato de fecha v&aacute;lido: (dd/mm/aaaa)";
			            continuar = false;
			        }
		 		}

        }
    }else{
    	mensajeTemporal = "El campo "+ nombreCampo+" no debe ser vacia.";
    	continuar = false;
    }
    
    if(!continuar){
		if(isBlank(mensaje)){
			mensaje += mensajeTemporal;
		}else{
			mensaje += SALTO_LINEA+mensajeTemporal;
		}
    }
    return mensaje;
}
 
function comprobarSiBisisesto(anio){
	if ( ( anio % 100 != 0) && ((anio % 4 == 0) || (anio % 400 == 0))) {
	    return true;
	}
	else {
	    return false;
	}
}	

function validarFormatoHora(hora, mensaje, nombreCampo){
	var continuar = true;
	var mensajeTemporal = "";
    if (hora != undefined && hora.value != "" ){
        if (!TIME_PATTERN.test(hora.value)){
        	mensajeTemporal = nombreCampo +" inv&aacute;lido. Ingrese una hora v&aacute;lida en el formato (hh:mm).";
            continuar = false;
        }
	}else{
		mensajeTemporal = "El campo "+ nombreCampo+" no debe ser vacia.";
		var continuar = false;
	}
    
    if(!continuar){
		if(isBlank(mensaje)){
			mensaje += mensajeTemporal;
		}else{
			mensaje += SALTO_LINEA+mensajeTemporal;
		}
    }
    return mensaje;
}

function validarDiferenciaFechasHoras(fecha1, fecha2, hora1, hora2){
	var resultado = "";
	var fechaHora1 = fecha1.replaceAll("/","")+hora1.value.replaceAll(":","");
	var fechaHora2 = fecha2.replaceAll("/","")+hora2.value.replaceAll(":","");
	var diferencia = fechaHora2 - fechaHora1;
	if (diferencia<=0)
		resultado = "Las Fechas de Trabajo no son correctas.";
		
	return resultado;
}

function mostrarMensajeError(mensaje) {
	document.getElementById('mensajeError').innerHTML = mensaje;
	document.getElementById('error').style.display = "inline";
}
