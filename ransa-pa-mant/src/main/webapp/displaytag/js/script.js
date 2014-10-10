setTimeout("cargarDisplayTag()",1000);

function validatePage(event) {
	var idForm = "form_DisplayTag";
	var form = document.getElementById(idForm);
	
	var idChar = event.keyCode;
	// Verificando si se presiono ENTER				
	if (idChar == 13) { 
		var paginacionEtiqueta = form.paginacionEtiqueta.value;
		form.tipoPaginacion.value = paginacionEtiqueta; 
		form.accion.value = "irPagina"; 
		form.submit();	
	}
	// Validando caracteres
	if(idChar != 8 && idChar != 37 && idChar != 39 && idChar != 46 && idChar != 13 &&
			(idChar < 96 || idChar > 105) && (idChar < 48 || idChar > 57)){
		Alert.show("Mensaje de Sistema","Ingresar caracteres numericos");
	}
	return false;
}

function validateNumberPage(txtPage, total) {
	var page = txtPage.value;
	if(page != "" && page < 1 && page > total){
		Alert.show("Mensaje de Sistema","Ingresar numero dentro del rango 1 - "+total);
	}
	return false;
}

function cargarDisplayTag(){
	
	var idForm = "form_DisplayTag";
	var form = document.getElementById(idForm);
	if(form != null && form.page != null){
		var valorTemp = form.page.value;
		valorTemp = valorTemp.replace(".", "").replace(",", "");
		form.page.value = valorTemp;
	}
	
}