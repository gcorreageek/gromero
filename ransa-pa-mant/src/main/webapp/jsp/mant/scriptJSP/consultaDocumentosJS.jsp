<%-- <%@page import="pe.com.ransa.portal.primax.vdocs.util.Util"%> --%>
<%-- <%@page import="pe.com.ransa.portal.primax.vdocs.comun.ConstantesComunes"%> --%>
<%-- <%@page import="pe.com.ransa.portal.logic.vdocs.util.Constantes"%> --%>
<%-- <%@page import="pe.com.ransa.portal.logic.vdocs.dominio.TipoComportamientoAtributo"%> --%>

<portlet:resourceURL var="obtenerListaAreas" id="obtenerListaAreas" />
<portlet:resourceURL var="obtenerListaTD" id="obtenerListaTD" />
<portlet:resourceURL var="obtenerFiltros" id="obtenerFiltros" />
<portlet:resourceURL var="obtenerListaCuentas" id="obtenerListaCuentas" />
<portlet:resourceURL var="buscarDocumentosHeader" id="buscarDocumentosHeader" />
<portlet:resourceURL var="buscarDocumentos" id="buscarDocumentos" />
<portlet:resourceURL var="abrirVisorPDF" id="abrirVisorPDF" />
<portlet:resourceURL var="descargarPDFUnificado" id="descargarPDFUnificado" />
<portlet:resourceURL var="completarDescarga" id="completarDescarga" />
<portlet:resourceURL var="limpiarDescarga" id="limpiarDescarga" />
<portlet:resourceURL var="validaSesionActiva" id="validaSesionActiva" />


<script type="text/javascript">
//Variables Globales
var arrDpickers = [];
var indx = 0;
var cmbCuentaId = "";
var step = 25;
var scrolling = false;
var initTime;
$( document ).ready(function() {
	validaSesionActivaAjax();
	initTime = new Date();
	$("#idEmpresaCbo" ).val("");
	// Wire up events for the 'scrollLeft' link:
	$("#scrollLeft").bind("click", function(event) {
	    event.preventDefault();
	    // Animates the scrollTop property by the specified
	    // step.
	    $("#divFiltros").animate({
	        scrollLeft: "-=" + step + "px"
	    });
	}).bind("mouseover", function(event) {
	    scrolling = true;
	    scrollContent("left");
	}).bind("mouseout", function(event) {
	    scrolling = false;
	});


	$("#scrollRigth").bind("click", function(event) {
	    event.preventDefault();
	    $("#divFiltros").animate({
	        scrollLeft: "+=" + step + "px"
	    });
	}).bind("mouseover", function(event) {
	    scrolling = true;
	    scrollContent("rigth");
	}).bind("mouseout", function(event) {
	    scrolling = false;
	});
});

function scrollContent(direction) {
    var amount = (direction === "left" ? "-=2px" : "+=2px");
    $("#divFiltros").animate({
        scrollLeft: amount
    }, 1, function() {
        if (scrolling) {
            scrollContent(direction);
        }
    });
}

$( document ).click(function(){
	var newTime = new Date();
	if((newTime - initTime) >= 1200000){
		initTime = new Date();
		alert("La Sesión expiró.");
		document.getElementById('lnkCloseSession').click();
	}else {
		if((newTime - initTime) >= 300000){
			initTime = new Date();
			validaSesionActivaAjax();
		}
	} 
});

//Funciones ajax
function validaSesionActivaAjax()
{
	$.ajax({
	    url:  '${validaSesionActiva}',
	    type: 'get',
	    datatype:'json',
	    data: {},
	    success: function(){
			
	    },
		error: function(e)
		{
			if(e.status == '500'){
				alert("La Sesión expiró.");
				document.getElementById('lnkCloseSession').click();
			}
			
		}
	});
}

function cargaComboAreasAjax()
{
	var idEmpresa = document.getElementById("idEmpresaCbo").value;
	if (!isBlank(idEmpresa))
	{

	$.ajax({
	    url:  '${obtenerListaAreas}',
	    type: 'get',
	    datatype:'json',
	    data: 'idEmpresa='+idEmpresa,
	    success: function(response){
	    	var areas = $.parseJSON(response);
	   		var oElemento;
	   		var cmbArea = cargaOpcionDefault("idAreaCbo", "${lblComboArea}");						
			for (var i = 1; i <= areas.length; i++) {
				oElemento=areas[i-1];
				cmbArea.options[i] = new Option();
				cmbArea.options[i].value = oElemento.id;
				cmbArea.options[i].text = oElemento.nombre;
			}	   	 	 		    		
			limpiarFiltros();
			clearGrid();
			ocultar("btnFiltrar,tblOpciones,divFlechas");
	    },
		error: function(e)
		{
			cargaOpcionDefault("idAreaCbo", "${lblComboArea}");
			cargaOpcionDefault("idTipoDocumentalCbo", "${lblComboTDocumental}");
			limpiarFiltros();
			clearGrid();
			ocultar("btnFiltrar,tblOpciones,divFlechas");
			Alert.show("Mensaje del Sistema", "Ocurrio un error interno con el servidor.");
		}
	});
	}else{
		cargaOpcionDefault("idAreaCbo", "${lblComboArea}");
		cargaOpcionDefault("idTipoDocumentalCbo", "${lblComboTDocumental}");
		limpiarFiltros();
		clearGrid();
		ocultar("btnFiltrar,tblOpciones,divFlechas");
	}
}

function cargaComboTDAjax()
{
	var parametros = new Object();
	parametros.idArea = document.getElementById("idAreaCbo").value;
	parametros.idEmpresa = document.getElementById("idEmpresaCbo").value;
	if (!isBlank(parametros.idArea) && !isBlank(parametros.idEmpresa))
	{
		
	$.ajax({
	    url:  '${obtenerListaTD}',
	    type: 'get',
	    datatype:'json',
	    data: parametros,
	    success: function(response){
	    	var tiposDocumentales = $.parseJSON(response);
	   		var oElemento;
	   		var cmbTD = cargaOpcionDefault("idTipoDocumentalCbo", "${lblComboTDocumental}");						
			for (var i = 1; i <= tiposDocumentales.length; i++) {
				oElemento=tiposDocumentales[i-1];
				cmbTD.options[i] = new Option();
				cmbTD.options[i].value = oElemento.id;
				cmbTD.options[i].text = oElemento.nombre;
			}
			limpiarFiltros();
			clearGrid();
			ocultar("btnFiltrar,tblOpciones,divFlechas");
	
	    },
		error: function(e)
		{
			cargaOpcionDefault("idTipoDocumentalCbo", "${lblComboTDocumental}");
			limpiarFiltros();
			clearGrid();
			ocultar("btnFiltrar,tblOpciones,divFlechas");
			Alert.show("Mensaje del Sistema", "Ocurrio un error interno con el servidor.");
		}
	});
	}else{
		cargaOpcionDefault("idTipoDocumentalCbo", "${lblComboTDocumental}");
		limpiarFiltros();
		clearGrid();
		ocultar("btnFiltrar,tblOpciones,divFlechas");
	}
}

function cargarFiltrosAjax()
{
	var parametros = new Object();
	parametros.idTipoDocumental = document.getElementById("idTipoDocumentalCbo").value;
	parametros.idArea = document.getElementById("idAreaCbo").value;
	parametros.idEmpresa = document.getElementById("idEmpresaCbo").value;
	if (!isBlank(parametros.idTipoDocumental) && !isBlank(parametros.idArea) && !isBlank(parametros.idEmpresa))
	{
		$.ajax({
		    url:  '${obtenerFiltros}',
		    type: 'get',
		    datatype:'json',
		    data: parametros,
		    success: function(response){
		    	var filtros = $.parseJSON(response);
		    	limpiarFiltros();
		    	clearGrid();
		    	if(filtros.length > 0){
		    		var table = document.getElementById("tblFiltros");
			    	var rowHeader = table.insertRow(0);
			    	var rowBody = table.insertRow(1);
			    	rowBody.className="td-grilla-blanco";
			    	var cellHeader;
			    	var cellBody;
			    	for(var i=0;i<filtros.length;i++){
			    		cellHeader = rowHeader.insertCell(i);
			    		cellHeader.className="cab-grilla";
			    		cellHeader.innerHTML=filtros[i].nombre;
			    		cellBody = rowBody.insertCell(i);
			    		cellBody.align="center";
			    		cellBody.appendChild(obtenerObjetHTML(filtros[i]));
			    	}
			    	setDatePicker();
			    	document.getElementById("divFiltros").className="divScroll";
			    	mostrar("btnFiltrar,divFlechas");
		    	}else{
		    		ocultar("btnFiltrar,tblOpciones,divFlechas");
		    		Alert.show("Mensaje del Sistema", "No existen filtros de Consulta configurados");
		    	}
		    	
		    },
			error: function(e)
			{
				ocultar("btnFiltrar,tblOpciones,divFlechas");
				clearGrid();
				Alert.show("Mensaje del Sistema", "Ocurrio un error interno con el servidor.");
			}
		});
	}
	else
	{
		limpiarFiltros();
		ocultar("btnFiltrar,tblOpciones,divFlechas");
	}
}

function cargarCuentasCliente(objCmb)
{
	var parametros = new Object();
	parametros.rucCliente = objCmb.value;
	if (!isBlank(parametros.rucCliente))
	{
		
	$.ajax({
	    url:  '${obtenerListaCuentas}',
	    type: 'get',
	    datatype:'json',
	    data: parametros,
	    success: function(response){
	    	var cuentas = $.parseJSON(response);
	   		var oElemento;
	   		var obCuenta = document.getElementById("cmb_"+cuentas.id);
	   		if(obCuenta != null){
	   			var cmbCuent = cargaOpcionDefault(obCuenta.id, "${lblComboGeneral}");
	   			cmbCuentaId = obCuenta.id;
				for (var i = 1; i <= cuentas.listaValores.itemsListaAtributo.length; i++) {
					oElemento=cuentas.listaValores.itemsListaAtributo[i-1];
					cmbCuent.options[i] = new Option();
					cmbCuent.options[i].value = oElemento.codigo;
					cmbCuent.options[i].text = oElemento.nombreCorto;
				}  			
	   		}
	
	    },
		error: function(e)
		{
			Alert.show("Mensaje del Sistema", "Ocurrio un error interno con el servidor.");
		}
	});
	}else{
		cargaOpcionDefault(cmbCuentaId, "${lblComboGeneral}");
		cmbCuentaId = "";
	}
}

function cargarListaDocumentos()
{
	var parametros = new Object();
	parametros.strFiltros = obtenerFiltrosBusqueda();
	parametros.idArea = document.getElementById("idAreaCbo").value;
	parametros.idEmpresa = document.getElementById("idEmpresaCbo").value;
	if (!isBlank(parametros.strFiltros) && !isBlank(parametros.idArea) && !isBlank(parametros.idEmpresa))
	{
		//createGrid(parametros);
	$.ajax({
	    url:  '${buscarDocumentosHeader}',
	    type: 'get',
	    datatype:'json',
	    data: {},
	    success: function(response){
	    	var objResponse = $.parseJSON(response);
	    	clearGrid();
	    	if(objResponse.success == "OK"){
	    		createGrid(parametros, objResponse.rowsHead, objResponse.rowsM);
	    		mostrar("tblOpciones");
	    	}else{
	    		Alert.show("Mensaje del Sistema","No se encontro data por los filtros seleccionados.");
	    		ocultar("tblOpciones");
	    	}
	    },
		error: function(e)
		{
			Alert.show("Mensaje del Sistema", "Ocurrio un error interno con el servidor.");
			ocultar("tblOpciones");
		}
	});
	}else{
		Alert.show("Mensaje del Sistema", "No existen filtros de Consulta configurados");
		ocultar("tblOpciones");
	}
}
function clearGrid() {
	jQuery('#listaDocumentos').jqGrid('GridUnload');
}
function createGrid(parametros, headerH, modelM) {
    jQuery("#listaDocumentos").jqGrid({

        //datatype: 'jsonstring',
        //datastr: response,
        datatype: "json",
        mtype: 'get',
		url:  '${buscarDocumentos}',
		postData: {
			strFiltros: parametros.strFiltros,
			idArea: parametros.idArea,
			idEmpresa: parametros.idEmpresa
		},
        jsonReader: { 
        	repeatitems: true, 
        	root: "rows",
        	page: "page", 
            total: "total", 
            records: "records",
        	cell: "columnasValue",
        	id: "id",
        	userdata: "userdata"
        },
        loadComplete: loadCompleteFunction,
        emptyDataText: 'No existen registros por los filtros ingresados.',
        colNames: headerH,
        colModel: modelM,
        loadonce: false,
        pager: jQuery('#plistaDocumentos'),
        multiselect: true,
        cmTemplate: { sortable: false },
        rowNum: 200,
        rowList: [20, 50, 100, 200, 500],
        viewrecords: true,
        caption : "Lista de Documentos",
        onPaging: function(pgButton) {
        	var newpage, last;
        	if ("user" == pgButton) {
        		last = parseInt(jnc(this).getGridParam("lastpage"));
        		
        		if(isNaN($(this.p.pager).find('input:text').val())){
        			//$(this.p.pager).find('input:text').val(last);
        			return 'stop';
        		}
        		newpage = parseInt($(this.p.pager).find('input:text').val());
        		
        		if (newpage > last) {
        			//$(this.p.pager).find('input:text').val(last);
        			return 'stop';
        		}
        	}
        }
    });
    //jQuery("#listaDocumentos").jqGrid('navGrid', '#plistaDocumentos', { edit: false, add: false, del: false }, null, null, true, { multipleSearch: true });
    //var height = $(window).height();
    jQuery("#listaDocumentos").jqGrid('setGridWidth', parseInt($(window).width()) - 300);
    jQuery("#listaDocumentos").jqGrid('setGridHeight', parseInt($(window).height()) - 100);
}

function loadCompleteFunction()
{
    if (jnc('#listaDocumentos').getGridParam('records') == 0){ // are there any records?
        displayEmptyText(true);
        ocultar("tblOpciones");
    }
    else{
        displayEmptyText(false);
        mostrar("tblOpciones");
    }
}

function displayEmptyText( display)
{
    var grid = jnc('#listaDocumentos');
    var emptyText = grid.getGridParam('emptyDataText'); // get the empty text
    var container = grid.parents('.ui-jqgrid-view'); // find the grid's container
    if (display) {
        container.find('.ui-jqgrid-hdiv, .ui-jqgrid-bdiv').hide(); // hide the column headers and the cells below
        jnc('#plistaDocumentos').hide();
        container.find('.ui-jqgrid-titlebar').after('' + emptyText + ''); // insert the empty data text
    }
    else {
        container.find('.ui-jqgrid-hdiv, .ui-jqgrid-bdiv').show(); // show the column headers
        jnc('#plistaDocumentos').show();
        container.find('#EmptyData' + emptyText).remove(); // remove the empty data text
    }
}
function verArchivo(idDoc)
{
	var parametros = new Object();
	parametros.idDocumento = idDoc;
	parametros.idTipoDocumental = document.getElementById("idTipoDocumentalCbo").value;
	parametros.idArea = document.getElementById("idAreaCbo").value;
	if (!isBlank(parametros.idDocumento))
	{
		abrirVentana(parametros);
		
	}
}

function verArchivos()
{
	var idsDocumento = jQuery("#listaDocumentos").jqGrid('getGridParam','selarrrow');
	if (!isEmpty(idsDocumento))
	{
		var parametros = new Object();
		parametros.idTipoDocumental = document.getElementById("idTipoDocumentalCbo").value;
		parametros.idArea = document.getElementById("idAreaCbo").value;
		
		for(var i=0;i<idsDocumento.length;i++){
			parametros.idDocumento = idsDocumento[i];
			abrirVentana(parametros);
		}
	}else
		{
		Alert.show("Mensaje del Sistema", "Seleccione uno o más Documentos.");
		}
}

function descargarUnificado()
{
	var arrDocumento = jQuery("#listaDocumentos").jqGrid('getGridParam','selarrrow');
	
	if(!isEmpty(arrDocumento))
		{
		var parametros = new Object();
		parametros.idsDocumento = arrDocumento.toString();
		parametros.idTipoDocumental = document.getElementById("idTipoDocumentalCbo").value;
		parametros.idArea = document.getElementById("idAreaCbo").value;
		parametros.idEmpresa = document.getElementById("idEmpresaCbo").value;
		var $preparingFileModal = $("#preparing-file-modal");
	    $preparingFileModal.dialog({ modal: true });
	    
		jnc.fileDownload('${descargarPDFUnificado}', {
			httpMethod: "post",
		    data: parametros,
			successCallback: function (url) {
				$preparingFileModal.dialog('close');
		    },
		    failCallback: function (responseHtml, url) {
		    	$preparingFileModal.dialog('close');
                $("#error-modal").dialog({ modal: true });
		    }
		});
		completarDescarga();
		}else
			{
			Alert.show("Mensaje del Sistema", "Seleccione uno o más Documentos.");
			}
	
}
function completarDescarga(){
	var $preparingFileModal = $("#preparing-file-modal");
	if ($preparingFileModal != null)
	{
		
	$.ajax({
	    url:  '${completarDescarga}',
	    type: 'get',
	    datatype:'json',
	    success: function(response){
	    	var objResponse = $.parseJSON(response);
	    	if(objResponse == "OK"){
	    		$preparingFileModal.dialog('close');
	    		limpiarDescarga();
	    	}else{
	    		setTimeout("completarDescarga()", 500);
	    	}
	    },
		error: function(e)
		{
			
		}
	});
	}
}
function limpiarDescarga(){
		
	$.ajax({
	    url:  '${limpiarDescarga}',
	    type: 'get',
	    datatype:'json',
	    success: function(){
	    	
	    },
		error: function(e)
		{
			
		}
	});

}
//Funciones genericas
function abrirVentana(parametros)
{
	var idForm = "frmVisor_"+parametros.idDocumento;
	var idMap = "MapVisor_"+parametros.idDocumento;
	var mapForm = document.getElementById(idForm);
	if(mapForm){
		$("#"+idForm).empty();
		document.body.removeChild(mapForm);
	}
		
	
    mapForm = document.createElement("form");
    mapForm.id = idForm;
    mapForm.target = idMap;
    mapForm.method = "POST";
    mapForm.action = "${abrirVisorPDF}";
    
    var keys = new Array("idDocumento", "idTipoDocumental", "idArea");
    var values = new Array(parametros.idDocumento, parametros.idTipoDocumental, parametros.idArea);
    var mapInput;
    for(var i=0;i<keys.length;i++){
      mapInput = document.createElement("input");
      mapInput.type = "hidden";
      mapInput.name = keys[i];
      mapInput.value = values[i];
      mapForm.appendChild(mapInput);
    }
    document.body.appendChild(mapForm);
    var map = window.open("", idMap, "toolbar=0,scrollbars=0,location=0,statusbar=1,menubar=0,resizable=1,width=1200,height=650,left = 50,top = 50");
    
    if (map) {
      mapForm.submit();
    } else {
      Alert.show("Mensaje del Sistema", "Popup's deshabilitados por el navegador.");
    }
  }

function obtenerObjetHTML(fila)
{
	var obj;
	if(fila.tipoComportamiento == '<%=TipoComportamientoAtributo.LISTA%>'){
		obj = document.createElement("select");
		obj.id = "cmb_"+fila.id;
		obj.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
		obj.className = "selectEstilo";
		var option = document.createElement("option");
		option.value = "";
	    option.text = "${lblComboGeneral}";
	    obj.appendChild(option);
		for(var i=0;i<fila.listaValores.itemsListaAtributo.length;i++){
			option = document.createElement("option");
			option.value = fila.listaValores.itemsListaAtributo[i].codigo;
		    option.text = fila.listaValores.itemsListaAtributo[i].nombreCorto;
		    obj.appendChild(option);
		}
		if(fila.nombreColumna == '<%=ConstantesComunes.FILTRO_CLIENTE%>'){
			obj.onchange = function(){
				cargarCuentasCliente(this);
			};
		}
	}else{
		if(fila.tipoDato == '<%=Constantes.TIPO_DATO_STRING%>'){
			obj = document.createElement("input");
			obj.type = "text";
			obj.id = "txt_"+fila.id;
			obj.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
			obj.className = "txt-box-campos";
			obj.style.width = "190px";
		}else if(fila.tipoDato == '<%=Constantes.TIPO_DATO_DOUBLE%>'){
			obj = document.createElement("input");
			obj.type = "text";
			obj.id = "txt_"+fila.id;
			obj.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
			obj.className = "txt-box-campos";
			obj.style.width = "150px";
			obj.onkeydown = function(e){
				return validarNumeroDecimal(e,this);
			};
			obj.onblur = function(){
				validarCerosDecimal(this);
			};
		}else if(fila.tipoDato == '<%=Constantes.TIPO_DATO_INTEGER%>'){
			obj = document.createElement("input");
			obj.type = "text";
			obj.id = "txt_"+fila.id;
			obj.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
			obj.className = "txt-box-campos";
			obj.style.width = "150px";
			obj.onkeydown = function(e){
				return validarNumeroEntero(e,this);
			};
			obj.onblur = function(){
				validarCerosEntero(this);
			};
		}else if(fila.tipoDato == '<%=Constantes.TIPO_DATO_DATETIME%>'){
			obj = document.createElement("div");
			obj.style.width = "260px";

			var objDel = new Image(); 
			objDel.src = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/images/borrador.gif")%>';
			objDel.onclick = function(){
				var f1 = document.getElementById("dt_"+fila.id+"_"+"<%=ConstantesComunes.FILTRO_FECHA_INICIO%>");
				var f2 = document.getElementById("dt_"+fila.id+"_"+"<%=ConstantesComunes.FILTRO_FECHA_FIN%>");
				if(f1 != null)
					f1.value = "";
				if(f2 != null)
					f2.value = "";
			}
			var objF1 = document.createElement("input");
			objF1.type = "text";
			objF1.id =  "dt_"+fila.id+"_"+"<%=ConstantesComunes.FILTRO_FECHA_INICIO%>";
			objF1.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
<%-- 			objF1.value = '<%=Util.getFechaActual(Constantes.FORMAT_DATE)%>' --%>
			objF1.readOnly = "readOnly";
			objF1.style.width = "95px";
			objF1.ondblclick = function(){
				objF1.value = "";
			};
			arrDpickers[indx] = objF1.id;
			indx++;
			var objF2 = document.createElement("input");
			objF2.type = "text";
			objF2.id = "dt_"+fila.id+"_"+"<%=ConstantesComunes.FILTRO_FECHA_FIN%>";
			objF2.name = "<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>";
<%-- 			objF2.value = '<%=Util.getFechaActual(Constantes.FORMAT_DATE)%>' --%>
			objF2.readOnly = "readOnly";
			objF2.style.width = "95px";
			arrDpickers[indx] = objF2.id;
			indx++;
			obj.appendChild(objDel);
			obj.appendChild(objF1);
			obj.appendChild(objF2);
		}

	}
	return obj;
}

function cargaOpcionDefault(idCmb, defaultText)
{
	var cmb = document.getElementById(idCmb);
	cmb.options.length = 0;
	cmb.options[0] = new Option();
	cmb.options[0].value = "";
	cmb.options[0].text = defaultText;
	return cmb;
}
var jnc = jQuery.noConflict();
function setDatePicker()
{
	for(var i=0;i<arrDpickers.length;i++){
		$("#"+arrDpickers[i]).datepicker(
				{dateFormat: 'dd/mm/yy',
				changeMonth: true,
				changeYear: true,
				showButtonPanel: true,
				//minDate: 0,		
				//maxDate: "+60D",
				showOn: 'button', 
				buttonImage: '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/images/icon-datapicker.png")%>', 
				buttonImageOnly: true,
				onSelect: function(selected) {
		 			if($(this).attr('id').endsWith("<%=ConstantesComunes.FILTRO_FECHA_INICIO%>")){
		 				var min = $(this).datepicker('getDate') || new Date();
		 				var pickerFin = $(this).attr('id').replace("<%=ConstantesComunes.FILTRO_FECHA_INICIO%>", "<%=ConstantesComunes.FILTRO_FECHA_FIN%>");
		 				
		 				$("#"+pickerFin).datepicker("option",{minDate: min});
		 			}
					
		 		}
				},
				$.datepicker.regional['es']
			);
	}
	arrDpickers = [];
	indx = 0;
}

function obtenerFiltrosBusqueda()
{
	var filtros = document.getElementsByName("<%=ConstantesComunes.FILTROS_DINAMICOS_NAME%>");
	var cadenaFiltros = "";
	if(filtros != null){
		var oElement;
		cadenaFiltros = "{"
			for(var i=0;i<filtros.length;i++){
				oElement = filtros[i];
				cadenaFiltros += "\"" + oElement.id + "\": ";
				cadenaFiltros += "\"" + oElement.value + "\"";
				if(i< filtros.length -1){
					cadenaFiltros += ", ";	
				}
			}
		cadenaFiltros += "}";
	}
	if(cadenaFiltros == "{}"){
		cadenaFiltros = "";
	}
	return cadenaFiltros;
}

function limpiarFiltros()
{
	var table = document.getElementById("tblFiltros");
    while(table.rows.length > 0){
    	table.deleteRow(-1);
    }
    document.getElementById("divFiltros").className="";
}

function ocultar(objIds)
{
	var arrObj = objIds.split(",");
	for(var i=0;i<arrObj.length;i++ ){
		var obj = document.getElementById(arrObj[i]);
		if(obj != null)
			obj.style.visibility="hidden";	
	}
	
}

function mostrar(objIds)
{
	var arrObj = objIds.split(",");
	for(var i=0;i<arrObj.length;i++ ){
		var obj = document.getElementById(arrObj[i]);
		if(obj != null)
			obj.style.visibility="visible";	
	}
}
</script>