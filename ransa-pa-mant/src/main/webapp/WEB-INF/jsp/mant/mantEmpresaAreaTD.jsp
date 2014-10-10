<%@include file="../common/resources.jsp"%>

<portlet:defineObjects />
<%@include file="../common/imports.jsp"%>
<portlet:actionURL var="pruebaActionURl"/>

<fmt:setBundle basename="bundle.mantPrimaxBundle" var="bundleMessages"/>
<fmt:message var="tituloPagina" key='mantprimax.empresaareatd.titulopagina' bundle="${bundleMessages}"/>

<fmt:message var="lblIdFiltro" key='mantprimax.empresaareatd.filtro.label.id' bundle="${bundleMessages}"/>
<fmt:message var="lblEmpresaFiltro" key='mantprimax.empresaareatd.filtro.label.empresa' bundle="${bundleMessages}"/> 
<fmt:message var="lblEstadoFiltro" key='mantprimax.empresaareatd.filtro.label.estado' bundle="${bundleMessages}"/> 
<fmt:message var="lblFiltrar" key='mantprimax.empresaareatd.botones.filtrar' bundle="${bundleMessages}"/>

<portlet:resourceURL var="obtenerListaEmpresas" id="obtenerListaEmpresas" />
<portlet:resourceURL var="guardarEmpresa" id="guardarEmpresa" />
<portlet:resourceURL var="obtenerListaAreas" id="obtenerListaAreas" />
<portlet:resourceURL var="guardarArea" id="guardarArea" />
<portlet:resourceURL var="obtenerListaTD" id="obtenerListaTD" />
<portlet:resourceURL var="guardarTD" id="guardarTD" />

<script type="text/javascript">
$( document ).ready(function() { 
	$( "#tabs" ).tabs();
	$( "#tabs" ).tabs( "disable", 1 ); 
	$( "#tabs" ).tabs( "disable", 2 );
	$("#tabAreas").click(function(){
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]);  
			$("#txtIdEmpresaFiltro").val(ret.idEmpresa); 
			$("#txtNomEmpresaFiltro").val(ret.nombre);    
			
			$("#txtNombreEmpresaArea").val($("#txtNomEmpresaFiltro").val());
			
			var urlParametros= '${obtenerListaAreas}'+'?txtId='+$("#txtIdEmpresaFiltro").val();
			jQuery("#list2").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
			$( "#tabs" ).tabs( "disable", 2 );
			
			$("#divMantAreas").css({display:'block'}); 
			$("#divMantEmpresa").css({display:'none'}); 
		}else{
			alert('Seleccione una opcion');
		}  
	});
	$("#tabTiposDocumentales").click(function(){
		var selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]);  
			$("#txtIdAreaFiltro").val(ret.idArea); 
			$("#txtNomAreaFiltro").val(ret.nombre);   

			$("#txtIdEmpresaTD").val($("#txtIdEmpresaFiltro").val()); 
			$("#txtNombreEmpresaTD").val($("#txtNomEmpresaFiltro").val()); 
			
			$("#txtIdAreaTD").val(ret.idArea); 
			$("#txtNombreAreaTD").val(ret.nombre);   
			var urlParametros= '${obtenerListaTD}'+'?txtIdEmpresa='+$("#txtIdEmpresaFiltro").val()+'&txtIdArea='+$("#txtIdAreaFiltro").val();
			jQuery("#list3").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
			
		}else{
			alert('Seleccione una opcion');
		}  
	});
	$("#tabEmpresas").click(function(){
		$("#divMantAreas").css({display:'none'}); 
		$("#divMantEmpresa").css({display:'block'}); 
		$( "#tabs" ).tabs( "disable" ,1);
		$( "#tabs" ).tabs( "disable" ,2);
		var urlParametros= '${obtenerListaEmpresas}'+'?'+$('#form_Consulta :input').serialize();
		jQuery("#list1").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
	});
	$("#btnFiltrarEmpresa").click(function(){  
		var urlParametros= '${obtenerListaEmpresas}'+'?'+$('#form_Consulta :input').serialize();
		jQuery("#list1").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
		$( "#tabs" ).tabs( "disable" ,1);
		$( "#tabs" ).tabs( "disable", 2 );
	});
	
	jQuery("#list1").jqGrid({
		url : '${obtenerListaEmpresas}'+'?txtId=&txtEmpresa=&txtEstado=', 
		datatype : "json", 
		colNames : [ 'ID','EMPRESA','DESCRIPCION','CODIGO', 'ESTADO' ],//idEmpresa nombre 
		colModel : [ { name : 'idEmpresa', index : 'idEmpresa', width : 180 },  
		             { name : 'nombre', index : 'nombre', width : 600 },  
		             { name : 'descripcion', index : 'descripcion', width : 10,hidden:true,editrules:{edithidden:true} },  
		             { name : 'codigo', index : 'codigo', width : 10,hidden:true,editrules:{edithidden:true}  }, 
		             { name : 'estado', index : 'estado', width : 80 }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager1',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		onSelectRow: function(id){ 
			var  selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
			if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ $( "#tabs" ).tabs( "enable" ,1);  
			}else{ $( "#tabs" ).tabs( "disable" ,1);   }
		},
		onPaging: function(pgButton) {
			$( "#tabs" ).tabs( "disable" ,1);
	 	},
		caption : "Empresas"
	});
	jQuery("#list2").jqGrid({
		url : '${obtenerListaAreas}'+'?txtId=', 
		datatype : "json", 
		colNames : ['IDEMPRESA','EMPRESA','IDAREA','NOMBRE','DESCRIPCION','CODIGO','ESTADO'], 
		colModel : [ { name : 'empresa.idEmpresa', index : 'empresa.idEmpresa', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'empresa.nombre',  index : 'empresa.nombre', width : 300 },  
		             { name : 'idArea', index : 'idArea', width : 1 ,hidden:true,editrules:{edithidden:true}},  
		             { name : 'nombre', index : 'nombre', width : 300 }, 
		             { name : 'descripcion', index : 'descripcion', width : 10,hidden:true,editrules:{edithidden:true}  }, 
		             { name : 'codigo', index : 'codigo', width : 10 ,hidden:true,editrules:{edithidden:true} }, 
		             { name : 'estado', index : 'estado', width : 80 }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager2',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		onSelectRow: function(id){ 
			var  selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
			if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ $( "#tabs" ).tabs( "enable" ,2);  
			}else{ $( "#tabs" ).tabs( "disable" ,2);   }
		},
		onPaging: function(pgButton) {
			$( "#tabs" ).tabs( "disable" ,2);
	 	},
		caption : "Areas"
	});
	jQuery("#list3").jqGrid({
		url : '${obtenerListaTD}'+'?txtIdEmpresa=&txtIdArea=', 
		datatype : "json",  
		colNames : ['IDEMPRESA','EMPRESA','IDAREA','AREA','IDTD','TIPO DOCUMENTAL','DESCRIPCION','CODIGO','NOMBRE TABLA','ESTADO'], 
		colModel : [ { name : 'area.empresa.idEmpresa', index : 'area.empresa.idEmpresa', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'area.empresa.nombre',  index : 'area.empresa.nombre', width : 300 },  
		             { name : 'area.idArea', index : 'area.idArea', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'area.nombre', index : 'area.nombre', width : 300 }, 
		             { name : 'idTipoDocumental', index : 'idTipoDocumental', width : 1,hidden:true,editrules:{edithidden:true} }, 
		             { name : 'nombre', index : 'nombre', width : 300 }, 
		             { name : 'descripcion', index : 'descripcion', width : 1,hidden:true,editrules:{edithidden:true} }, 
		             { name : 'codigo', index : 'codigo', width : 1,hidden:true,editrules:{edithidden:true}  }, 
		             { name : 'nombreTablaTipoDocEmpresaArea', index : 'nombreTablaTipoDocEmpresaArea', width : 1,hidden:true,editrules:{edithidden:true}  }, 
		             { name : 'estado', index : 'estado', width : 80 }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager3',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		onSelectRow: function(id){ 
			var  selecccionadoGrilla = jQuery("#list3").jqGrid('getGridParam','selarrrow');  
			if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){//Selecciona un solo check
				var ret = jQuery("#list3").jqGrid('getRowData',selecccionadoGrilla[0]); 
				$("#txtIdTipoDocumentalFiltro").val(ret.idTipoDocumental);
				$("#txtNomTipoDocFiltro").val(ret.nombre);
				$("#cboNomEstadoFiltro").val(ret.estado);
			}else{
				$("#txtIdTipoDocumentalFiltro").val('');
				$("#txtNomTipoDocFiltro").val('');
				$("#cboNomEstadoFiltro").val('');
			}
		},
		caption : "Tipos Documentales"
	}); 
	$("#btnAgregarEmpresas").click(function(){ 
		$("#errorIdEmpresa").html("");
		$("#errorNombreEmpresa").html(""); 
		$("#errorDescripcionEmpresa").html(""); 
		$("#errorCodigoEmpresa").html("");  
		$("#errorServerEmpresa").html("");
		
		$("#txtIdEmpresa").val(''); 
		$("#txtNombreEmpresa").val('');
		$("#txtDescripcionEmpresa").val('');
		$("#txtCodigoEmpresa").val('');
		
		$("#cboEstadoEmpresa").val('');
		$("#tipoCRUDEmpresa").val('AGREGA'); 
		
		$("#idTableEmpresaRegistra").css({display:'block'});
		$("#idTableEmpresaModifica").css({display:'block'});
		$("#idTableEmpresaElimina").css({display:'none'}); 
		$( "#dialog1" ).dialog({ width:550,height:200,modal:true,title: "Registro Empresa"}); 
	});
	jQuery("#btnModificarEmpresas").click( function() { 
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorIdEmpresa").html("");
			$("#errorNombreEmpresa").html(""); 
			$("#errorDescripcionEmpresa").html(""); 
			$("#errorCodigoEmpresa").html("");  
			$("#errorServerEmpresa").html("");
			
			$("#txtIdEmpresa").val(''); 
			$("#txtNombreEmpresa").val('');
			$("#txtDescripcionEmpresa").val('');
			$("#txtCodigoEmpresa").val('');
			$("#tipoCRUDEmpresa").val('ACTUALIZA'); 
			$("#cboEstadoEmpresa").val('');
			 
			$("#txtIdEmpresa").val(ret.idEmpresa); 
			$("#txtNombreEmpresa").val(ret.nombre);
			$("#txtDescripcionEmpresa").val(ret.descripcion);
			$("#txtCodigoEmpresa").val(ret.codigo);
			$("#tipoCRUDEmpresa").val('ACTUALIZA'); 
			
			 
			
			$("#idTableEmpresaRegistra").css({display:'none'});
			$("#idTableEmpresaModifica").css({display:'block'});
			$("#idTableEmpresaElimina").css({display:'none'}); 
			$( "#dialog1" ).dialog({ width:550,height:200,modal:true,title: "Modifica Empresa"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	jQuery("#btnEliminarEmpresas").click( function() { 
		var  selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorIdEmpresa").html("");
			$("#errorNombreEmpresa").html(""); 
			$("#errorDescripcionEmpresa").html(""); 
			$("#errorCodigoEmpresa").html("");  
			$("#errorServerEmpresa").html("");
			
			$("#txtIdEmpresa").val(ret.idEmpresa); 
			$("#txtNombreEmpresa").val(ret.nombre);
			$("#txtDescripcionEmpresa").val(ret.descripcion);
			$("#txtCodigoEmpresa").val(ret.codigo);
			$("#tipoCRUDEmpresa").val('ELIMINA'); 
			 
			if(ret.estado=='A'){
				$("#cboEstadoEmpresa").val('I');
			}else{
				$("#cboEstadoEmpresa").val('A');
			}
			
			$("#idTableEmpresaRegistra").css({display:'none'});
			$("#idTableEmpresaModifica").css({display:'none'});
			$("#idTableEmpresaElimina").css({display:'block'}); 
			$("#dialog1").dialog({width:270,height:130,modal:true,title: "Elimina Empresa"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	$("#btnGrabarEmpresa").click(function(){
		$("#errorIdEmpresa").html("");
		$("#errorNombreEmpresa").html(""); 
		$("#errorDescripcionEmpresa").html(""); 
		$("#errorCodigoEmpresa").html("");  
		$("#errorServerEmpresa").html("");
		 
		var esValidoIdEmpresa = false;
		var esValidoNombreEmpresa = false;
		var esValidoDescripcionEmpresa = false; 
		var esValidoCodigoEmpresa = false;  
		
		var txtIdEmpresa = $("#txtIdEmpresa").val(); 
		var txtNombreEmpresa = $("#txtNombreEmpresa").val();
		var txtDescripcionEmpresa = $("#txtDescripcionEmpresa").val();
		var txtCodigoEmpresa = $("#txtCodigoEmpresa").val();
		var cboEstadoEmpresa = $("#cboEstadoEmpresa").val();
		
		var tipoCRUDEmpresa = $("#tipoCRUDEmpresa").val(); 
 
		if(txtIdEmpresa=='' || txtIdEmpresa.trim()==''){  
			$("#errorIdEmpresa").html("Ingrese ID"); 
		}else{   
			if(!/^\d+$/.test(txtIdEmpresa)){
				$("#errorIdEmpresa").html("Ingrese ID valido"); 
			}else{
				esValidoIdEmpresa = true;
			} 
		}
		if(txtNombreEmpresa=='' || txtNombreEmpresa.trim()==''){  
			$("#errorNombreEmpresa").html("Ingrese Nombre Empresa"); 
		}else{//valida el numero de caracteres
			if(!(txtNombreEmpresa.length<100)){
				$("#errorNombreEmpresa").html("Ingrese Nombre Empresa valida, numero de caracteres excede a 99"); 
			}else{
				var esValidoCaracteres = true; 
				for (var i = 0; i < txtNombreEmpresa.length; i++) { 
			 	    if ("#$%^&*()+=-[]\\'/{}|\"<>".indexOf(txtNombreEmpresa.charAt(i)) != -1) {  
			 	    	$("#errorNombreEmpresa").html("Ingrese Nombre Empresa valida, caracteres no validos");   
			 	    	esValidoCaracteres = false;
			 	    } 
				} 
				if(esValidoCaracteres){
					esValidoNombreEmpresa = true;
				}
			}  
		}
		if(txtDescripcionEmpresa=='' || txtDescripcionEmpresa.trim()==''){  
			$("#errorDescripcionEmpresa").html("Ingrese Descripcion Empresa"); 
		}else{//valida el numero de caracteres
			if(!(txtDescripcionEmpresa.length<200)){
				$("#errorDescripcionEmpresa").html("Ingrese Descripcion Empresa valida, numero de caracteres excede a 199"); 
			}else{
				var esValidoCaracteres = true; 
				for (var i = 0; i < txtDescripcionEmpresa.length; i++) { 
			 	    if ("#$%^&*()+=-[]\\'/{}|\"<>".indexOf(txtDescripcionEmpresa.charAt(i)) != -1) {  
			 	    	$("#errorDescripcionEmpresa").html("Ingrese Descripcion Empresa valida, caracteres no validos");   
			 	    	esValidoCaracteres = false;
			 	    } 
				} 
				if(esValidoCaracteres){
					esValidoDescripcionEmpresa = true;
				}
			}  
		}
		if(txtCodigoEmpresa=='' || txtCodigoEmpresa.trim()==''){  
			$("#errorCodigoEmpresa").html("Ingrese Codigo Empresa"); 
		}else{//valida el numero de caracteres
			if(!(txtCodigoEmpresa.length<20)){
				$("#errorCodigoEmpresa").html("Ingrese Codigo Empresa valida, numero de caracteres excede a 19"); 
			}else{
				var esValidoCaracteres = true; 
				for (var i = 0; i < txtCodigoEmpresa.length; i++) { 
			 	    if ("#$%^&*()+=-[]\\'/{}|\"<>".indexOf(txtCodigoEmpresa.charAt(i)) != -1) {  
			 	    	$("#errorNombreEmpresa").html("Ingrese Codigo Empresa valida, caracteres no validos");   
			 	    	esValidoCaracteres = false;
			 	    } 
				} 
				if(esValidoCaracteres){
					esValidoCodigoEmpresa = true;
				}
			}  
		}
// 		var seGuarda = true;
		var seGuarda = false;
		if(tipoCRUDEmpresa=='ELIMINA'){
			seGuarda = true;
		}else{  
			if(esValidoIdEmpresa && esValidoNombreEmpresa && esValidoDescripcionEmpresa && esValidoCodigoEmpresa){
				seGuarda = true;
			}
		}
		
		if(seGuarda){  
			$.get( "${guardarEmpresa}", { 'txtIdEmpresa': txtIdEmpresa,'txtNombreEmpresa':txtNombreEmpresa,'txtDescripcionEmpresa':txtDescripcionEmpresa,
				'txtCodigoEmpresa':txtCodigoEmpresa,'cboEstadoEmpresa':cboEstadoEmpresa,'tipoCRUDEmpresa':tipoCRUDEmpresa } ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list1").trigger("reloadGrid");
						$( "#dialog1" ).dialog( "close" ); 
						$( "#tabs" ).tabs( "disable" ,1);
						$( "#tabs" ).tabs( "disable", 2 );
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;
					        case 'CODERROR_EMPRESA_IDEMPRESA':
					        	mensajeError = "Error, ID Empresa invalido";
					            break;
					        case 'CODERROR_EMPRESA_NOMBRE':
					        	mensajeError = "Error, Nombre Empresa invalido";
					            break; 
					        case 'CODERROR_EMPRESA_DESCRIPCION':
					        	mensajeError = "Error, Descripcion Empresa invalida";
					            break; 
					        case 'CODERROR_EMPRESA_CODIGO':
					        	mensajeError = "Error, Codigo Empresa invalido";
					            break;  
					        case 'CODERROR_EMPRESA_IDEMPRESA_REPETIDO':
					        	mensajeError = "Error, Id Empresa repetido";
					            break;  
					        case 'CODERROR_EMPRESA_CODIGO_REPETIDO':
					        	mensajeError = "Error, Codigo Empresa repetido";
					            break;  
					        case 'CODERROR_EMPRESA_TIENEAREASACTIVAS':
					        	mensajeError = "Error, Empresa tiene areas Activas; desactivela para poder eliminarla";
					            break;  
					        case 'CODERROR_EMPRESA_TIENEUSUARIOASINGNADOSACTIVAS':
					        	mensajeError = "Error, Empresa esta asociado a un Usuario; desactivela para poder eliminarla";
					            break;   
					    }
						$("#errorServerEmpresa").html(mensajeError);  
					} 
			});	
		}
		 
		
	});
	$("#btnCancelarEmpresa").click(function(){
		$( "#dialog1" ).dialog( "close" );
	});	
	
	
	$("#btnAgregarArea").click(function(){ 
		$("#errorNombreArea").html("");
		$("#errorCodigoArea").html("");  
		$("#errorServerArea").html("");
		
		$("#txtIdArea").val(''); 
		$("#txtNombreArea").val(''); 
		$("#txtDescripcionArea").val('');
		$("#txtCodigoArea").val('');
		
		$("#cboEstadoArea").val('');
		$("#tipoCRUDArea").val('AGREGA'); 

		$("#idTableAreaRegistra").css({display:'block'});
		$("#idTableAreaElimina").css({display:'none'});
		$( "#dialog2" ).dialog({ width:550,height:200,modal:true,title: "Registro Area"}); 
	});
	jQuery("#btnModificarArea").click( function() { 
		var selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorNombreArea").html("");
			$("#errorCodigoArea").html("");  
			$("#errorServerArea").html("");
			
			$("#txtIdArea").val(ret.idArea); 
			$("#txtNombreArea").val(ret.nombre); 
			$("#txtDescripcionArea").val(ret.descripcion); 
			$("#txtCodigoArea").val(ret.codigo);
			$("#tipoCRUDArea").val('ACTUALIZA'); 
			$("#cboEstadoArea").val('');
			 
			$("#idTableAreaRegistra").css({display:'block'});
			$("#idTableAreaElimina").css({display:'none'});
			$( "#dialog2" ).dialog({ width:550,height:200,modal:true,title: "Modifica Area"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	jQuery("#btnEliminarArea").click( function() { 
		var  selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorNombreArea").html("");
			$("#errorCodigoArea").html("");  
			$("#errorServerArea").html("");
			
			$("#txtIdArea").val(ret.idArea); 
			$("#txtNombreArea").val(ret.nombre); 
			$("#txtDescripcionArea").val(ret.descripcion); 
			$("#txtCodigoArea").val(ret.codigo); 
			$("#tipoCRUDArea").val('ELIMINA'); 
			if(ret.estado=='A'){
				$("#cboEstadoArea").val('I');
			}else{
				$("#cboEstadoArea").val('A');
			}
			$("#idTableAreaRegistra").css({display:'none'});
			$("#idTableAreaElimina").css({display:'block'}); 
			$("#dialog2").dialog({ width:270,height:130,modal:true,title: "Elimina Empresa"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	$("#btnGrabarArea").click(function(){
		$("#errorNombreArea").html("");
		$("#errorCodigoArea").html("");  
		$("#errorServerArea").html("");
		 
		var esValidoNombreArea = false;
		var esValidoCodigoArea = false;  
		
		var txtIdArea = $("#txtIdArea").val(); 
		var txtNombreArea = $("#txtNombreArea").val(); 
		var txtDescripcionArea = $("#txtDescripcionArea").val(); 
		var txtCodigoArea = $("#txtCodigoArea").val();  
		var cboEstadoArea = $("#cboEstadoArea").val(); 
		 
		var tipoCRUDArea = $("#tipoCRUDArea").val();  
		
		if(txtNombreArea=='' || txtNombreArea.trim()==''){  
			$("#errorNombreArea").html("Ingrese Nombre Area"); 
		}else{
			if(!(txtNombreArea.length<100)){
				$("#errorNombreArea").html("Ingrese Nombre Area valida, numero de caracteres mayor a 99"); 
			}else{
				esValidoNombreArea = true;
			}  
		}
		if(txtCodigoArea=='' || txtCodigoArea.trim()==''){  
			$("#errorCodigoArea").html("Ingrese Codigo Area"); 
		}else{
			if(!(txtCodigoArea.length<21)){
				$("#errorCodigoArea").html("Ingrese Codigo Area valida, numero de caracteres mayor a 20"); 
			}else{
				esValidoCodigoArea = true;
			}  
		}
		
		var seGuarda = false;
		if(tipoCRUDArea=='ELIMINA'){
			seGuarda = true;
		}else{
			if(esValidoNombreArea && esValidoCodigoArea){
				seGuarda = true;
			} 
		}
		
		if(seGuarda){
			$.get( "${guardarArea}", { 'txtIdArea': txtIdArea,'txtNombreArea':txtNombreArea,'txtDescripcionArea':txtDescripcionArea,
				'txtCodigoArea':txtCodigoArea,'cboEstadoArea':cboEstadoArea,
				'tipoCRUDArea':tipoCRUDArea,'txtIdEmpresaArea': $("#txtIdEmpresaFiltro").val() } ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list2").trigger("reloadGrid");
						$("#dialog2").dialog( "close" );
						$("#tabs").tabs( "disable", 2 );
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;  
					        case 'CODERROR_AREA_NOMBRE':
					        	mensajeError = "Error, Nombre invalido";
					            break;  
					        case 'CODERROR_AREA_CODIGO':
					        	mensajeError = "Error, Codigo invalido";
					            break;  
					        case 'CODERROR_AREA_CODIGO_REPETIDO':
					        	mensajeError = "Error, codigo repetido";
					            break;  
					        case 'CODERROR_AREA_TIENETIPODOCACTIVO':
					        	mensajeError = "Error, Area tiene Tipo Documentales Activos; desactivelo para poder eliminar";
					            break;  
					        case 'CODERROR_AREA_TIENEUSUARIOSACTIVO':
					        	mensajeError = "Error, Area tiene Usuarios Activos; desactivelo para poder eliminar";
					            break;  
					    }
						$("#errorServerArea").html(mensajeError);  
					}    
			});
		}
	});
	$("#btnCancelarArea").click(function(){
		$( "#dialog2" ).dialog( "close" );
	});
	$("#btnAgregarTipoDocumental").click(function(){   
		$("#txtIdTD").val(''); 
		$("#txtNombreTD").val(''); 
		$("#txtCodigoTD").val('');
		$("#txtNombreTablaTD").val('');
		$("#txtDescripcionTD").val('');
		$("#cboEstadoTD").val('');
		
		$("#tipoCRUDTD").val('AGREGA');
		$("#idTableTDRegistra").css({display:'block'});
		$("#idTableTDElimina").css({display:'none'});
		$( "#dialog3" ).dialog({ width:550,height:200,modal:true,title: "Registro Tipo Documental"}); 
	});
	jQuery("#btnModificarTipoDocumental").click( function() { 
		var selecccionadoGrilla = jQuery("#list3").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list3").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#txtIdTD").val(ret.idTipoDocumental); 
			$("#txtNombreTD").val(ret.nombre); 
			$("#txtCodigoTD").val(ret.codigo);
			$("#txtNombreTablaTD").val(ret.nombreTablaTipoDocEmpresaArea); 
			$("#txtDescripcionTD").val(ret.descripcion); 
			$("#cboEstadoTD").val(ret.estado);
			$("#tipoCRUDTD").val('ACTUALIZA'); 
			
			$("#idTableTDRegistra").css({display:'block'});
			$("#idTableTDElimina").css({display:'none'});
			$( "#dialog3" ).dialog({ width:550,height:200,modal:true,title: "Modifica Tipo Documental"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	jQuery("#btnEliminarTipoDocumental").click( function() { 
		var  selecccionadoGrilla = jQuery("#list3").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list3").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#txtIdTD").val(ret.idTipoDocumental); 
			$("#txtNombreTD").val(ret.nombre); 
			$("#txtCodigoTD").val(ret.codigo);
			$("#txtNombreTablaTD").val(ret.nombreTablaTipoDocEmpresaArea); 
			$("#txtDescripcionTD").val(ret.descripcion); 
			if(ret.estado=='A'){
				$("#cboEstadoTD").val('I');
			}else{
				$("#cboEstadoTD").val('A');
			}
			$("#tipoCRUDTD").val('ELIMINA'); 
			
			$("#idTableTDRegistra").css({display:'none'});
			$("#idTableTDElimina").css({display:'block'}); 
			$("#dialog3").dialog({ width:270,height:130,modal:true,title: "Elimina Tipo Documental"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	$("#btnGrabarTD").click(function(){ 
		$("#errorNombreTD").html("");
		$("#errorCodigoTD").html("");  
		$("#errorNombreTablaTD").html("");  
		$("#errorServerTD").html("");
		 
		var esValidoNombreTD = false;
		var esValidoCodigoTD = false;  
		var esValidoNombreTablaTD = false;  
		
		var txtIdTD = $("#txtIdTD").val(); 
		var txtNombreTD = $("#txtNombreTD").val(); 
		var txtCodigoTD = $("#txtCodigoTD").val(); 
		var txtNombreTablaTD = $("#txtNombreTablaTD").val(); 
		var txtDescripcionTD = $("#txtDescripcionTD").val(); 
		var cboEstadoTD = $("#cboEstadoTD").val(); 

		var txtIdEmpresaTD = $("#txtIdEmpresaFiltro").val(); 
		var txtIdAreaTD = $("#txtIdAreaFiltro").val(); 
		var tipoCRUDTD = $("#tipoCRUDTD").val();   
		if(txtNombreTD=='' || txtNombreTD.trim()==''){  
			$("#errorNombreTD").html("Ingrese Nombre Tipo Documental"); 
		}else{
			if(!(txtNombreTD.length<101)){
				$("#errorNombreTD").html("Ingrese Nombre Tipo Documental valida, numero de caracteres mayor a 100"); 
			}else{
				esValidoNombreTD = true;
			}  
		}
		if(txtCodigoTD=='' || txtCodigoTD.trim()==''){  
			$("#errorCodigoTD").html("Ingrese Codigo Tipo Documental"); 
		}else{
			if(!(txtCodigoTD.length<21)){
				$("#errorCodigoTD").html("Ingrese Codigo Tipo Documental valida, numero de caracteres mayor a 20"); 
			}else{
				esValidoCodigoTD = true;
			}  
		}
		if(txtNombreTablaTD=='' || txtNombreTablaTD.trim()==''){  
			$("#errorNombreTablaTD").html("Ingrese Nombre Tabla Tipo Documental"); 
		}else{
			if(!(txtNombreTablaTD.length<21)){
				$("#errorNombreTablaTD").html("Ingrese Nombre Tabla Tipo Documental valida, numero de caracteres mayor a 20"); 
			}else{
				esValidoNombreTablaTD = true;
			}  
		}  
		var seGuarda = false;
		if(tipoCRUDTD=='ELIMINA'){
			seGuarda = true;
		}else{ 
			if(esValidoNombreTD && esValidoCodigoTD && esValidoNombreTablaTD){
				seGuarda = true;
			} 
		} 
		if(seGuarda){ 
			$.get( "${guardarTD}", { 'txtIdTD': txtIdTD,'txtNombreTD':txtNombreTD,'txtCodigoTD':txtCodigoTD,'txtNombreTablaTD':txtNombreTablaTD,'txtDescripcionTD':txtDescripcionTD,
				'cboEstadoTD':cboEstadoTD,
				'tipoCRUDTD':tipoCRUDTD,'txtIdEmpresaTD': txtIdEmpresaTD,'txtIdAreaTD': txtIdAreaTD  } ,function(data){
					var obj = JSON.parse(data); 
					if(obj.seGuardo){ 
						jQuery("#list3").trigger("reloadGrid");
						$( "#dialog3" ).dialog( "close" );   
					}else{  
						 var mensajeError = '';
						 switch (obj.codigoError) { 
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;     
					        case 'CODERROR_TD_NOMBRE':
					        	mensajeError = "Error, Nombre invalido";
					            break;     
					        case 'CODERROR_TD_CODIGO':
					        	mensajeError = "Error, Codigo invalido";
					            break;     
					        case 'CODERROR_TD_NOMBRETABLA':
					        	mensajeError = "Error, Nombre Tabla invalida";
					            break;     
					        case 'CODERROR_TD_CODIGO_REPETIDO':
					        	mensajeError = "Error, Codigo repetido; ingrese otro codigo";
					            break;     
					        case 'CODERROR_TD_NOMBRETABLA_REPETIDO':
					        	mensajeError = "Error, Nombre tabla repetida, ingrese otro Nombre Tabla";
					            break;     
					        case 'CODERROR_TD_TIENEUSUARIOACTIVO':
					        	mensajeError = "Error, Tipo Documental tiene Usuarios Activos; desactivelo para poder eliminar";
					            break;     
					        case 'CODERROR_TD_TIENEDOCUMENTOACTIVO':
					        	mensajeError = "Error, Tipo Documental tiene Documentos Activos; desactivelo para poder eliminar";
					            break;     
					        case 'CODERROR_TD_TIENEATRIBUTOTIPODOCACTIVO':
					        	mensajeError = "Error, Tipo Documental tiene Atributo Tipo Documental Activos; desactivelo para poder eliminar";
					            break;     
					    }
						$("#errorServerTD").html(mensajeError);  
					}
			});
		} 
	});
	$("#btnCancelarTD").click(function(){
		$( "#dialog3" ).dialog( "close" );
	});	
	
});
</script>


<div id="cuerpo">
<form:form id="form_Consulta" cssClass="formulario" modelAttribute="mantPrimaxEmpresaAreaTDCommand" method="post" action="${pruebaActionURl}" acceptCharset="ISO-8859-1" style="margin-left:0;margin-top:-25px" onKeyPress="return disableEnterKey(event);">
  <div id="divMantEmpresa"  >
  		<div><span class="titulopag">${tituloPagina}</span></div>
        <div id="box01">
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   > 
	              		<td class="cab-grilla" width="200" >${lblIdFiltro}</td>
	              		<td class="cab-grilla" width="480" >${lblEmpresaFiltro}</td> 
	              		<td class="cab-grilla" width="100" >${lblEstadoFiltro}</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">  
	              		<td align="center" ><input name="txtId" id="txtIdFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtEmpresa" id="txtEmpresaFiltro"  style="width:80%"   /></td>
	              		<td align="center" > 
	              		<select id="txtEstadoFiltro" name="txtEstado" style="width:80%"  > 
	              			<option value="" ></option>
	              			<option value="A" >A</option>
	              			<option value="I" >I</option>
	              		</select> 
	              		</td>
	              	</tr>
	              </table>
              </td>
              <td>&nbsp;&nbsp;&nbsp;</td>
              <td width="100" align="right"><input type="button" id="btnFiltrarEmpresa" value="${lblFiltrar}" class="skip"    ></td>
            </tr> 
          </table>
    	</div>
  </div>
</form:form>
  <div id="divMantAreas" style="display:none"  >
  		<div><span class="titulopag">Empresa Seleccionada</span></div>
        <div id="box01">
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   > 
	              		<td class="cab-grilla" width="200" >Empresa</td>
	              		<td class="cab-grilla" width="200" >Area</td> 
	              		<td class="cab-grilla" width="200" >Tipo Documental</td>
	              		<td class="cab-grilla" width="200" >Estado</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">  
	              		<input type="hidden" id="txtIdEmpresaFiltro" name="txtIdEmpresaFiltro" />
	              		<input type="hidden" id="txtIdAreaFiltro" name="txtIdAreaFiltro" />
	              		<input type="hidden" id="txtIdTipoDocumentalFiltro" name="txtIdTipoDocumentalFiltro" />
	              		<td align="center" ><input name="txtNomEmpresaFiltro" id="txtNomEmpresaFiltro"  style="width:80%"  readonly="readonly"  /></td>
	              		<td align="center" ><input name="txtNomAreaFiltro" id="txtNomAreaFiltro"  style="width:80%" readonly="readonly"  /></td>
	              		<td align="center" ><input name="txtNomTipoDocFiltro" id="txtNomTipoDocFiltro"  style="width:80%" readonly="readonly" /></td>
	              		<td align="center" >
	              		<select id="cboNomEstadoFiltro" name="cboNomEstadoFiltro" style="width:80%" disabled="disabled"  > 
	              			<option value="" ></option>
	              			<option value="A" >A</option>
	              			<option value="I" >I</option>
	              		</select> 
	              		</td>
	              	</tr>
	              </table>
              </td> 
            </tr> 
          </table>
    	</div>
  </div>
    
    <div id="tabs" style="padding-top:13px" >
      <ul>
	      <li><a id="tabEmpresas" href="#tab1" name="#tab1" class="tituloTab" >Empresas</a></li>
	      <li><a id="tabAreas" href="#tab2" name="#tab2" class="tituloTab" >Areas</a></li>    
	      <li><a id="tabTiposDocumentales" href="#tab3" name="#tab3" class="tituloTab" >Tipos Documentales</a></li>    
	  </ul> 
	  <div id="content">
	      <div   id="tab1"   > 
		      <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			      	<input type="button" id="btnAgregarEmpresas" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnModificarEmpresas" value="Modificar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarEmpresas" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list1"></table> <div id="pager1"></div>
			  </div>
	      </div>
	      <div  id="tab2">
	       	  <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			        <input type="button" id="btnAgregarArea" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnModificarArea" value="Modificar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarArea" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list2"></table> <div id="pager2"></div>
			  </div>
	      </div> 
	      <div  id="tab3">
	       	  <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			        <input type="button" id="btnAgregarTipoDocumental" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnModificarTipoDocumental" value="Modificar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarTipoDocumental" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list3"></table> <div id="pager3"></div>
			  </div>
	      </div> 
	  </div>
    </div> 
    
</div> 
 <style>
 table.idTableCss{
 width:100%;
 }
th.from  {
  width: 20%
}
th.subject {
  width: 80%;  
}
.errorValidar{
color:red;
font-weight: bold;
}
.errorValidarServer{
color:#A23137;
font-weight: bold;
}
 </style>

<div id="dialog1"    style="display:none" > 
	<table> 
	<tr><td colspan="2"><span id="errorServerEmpresa" class="errorValidarServer" ></span></td></tr>
	</table>
	<table  id="idTableEmpresaRegistra" class="idTableCss" style="display: none" >
	<tr> <th class="from"> </th> <th class="subject"> </th> </tr>
	<tr> 
		<td>ID Empresa</td>
		<td><input type="text" id="txtIdEmpresa" name="txtIdEmpresa" style="width:20%" />(*)<span id="errorIdEmpresa" class="errorValidar"></span></td>
	</tr>
	</table>
	<table  id="idTableEmpresaModifica" class="idTableCss"  style="display: none" >
	<tr> <th class="from"> </th> <th class="subject"> </th> </tr>
	<tr>
		<td>Nombre</td>
		<td><input type="text" id="txtNombreEmpresa" name="txtNombreEmpresa"  style="width:90%"   />(*)<span id="errorNombreEmpresa" class="errorValidar"></span></td>
	</tr> 
	<tr>
		<td>Descripcion</td>
		<td><input type="text" id="txtDescripcionEmpresa" name="txtDescripcionEmpresa" style="width:90%"  />(*)<span id="errorDescripcionEmpresa" class="errorValidar"></span></td>
	</tr>
	<tr>
		<td>Codigo</td>
		<td><input type="text" id="txtCodigoEmpresa" name="txtCodigoEmpresa"  style="width:60%"   />(*)<span id="errorCodigoEmpresa" class="errorValidar"></span></td>
	</tr> 
	</table>
	<table id="idTableEmpresaElimina" class="idTableCss" style="display:none"   >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoEmpresa"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table> 
	<input type="hidden"  id="tipoCRUDEmpresa" name="tipoCRUDEmpresa" value=""  >   
	
	<table  class="idTableCss" >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarEmpresa" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarEmpresa" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
<div id="dialog2"    style="display:none" > 
	<table> 
	<tr><td colspan="2"><span id="errorServerArea" class="errorValidarServer" ></span></td></tr>
	</table>
	<input type="hidden"  name="txtIdArea" id="txtIdArea" value="" >
	<table  id="idTableAreaRegistra"  class="idTableCss" style="display: none" >
	<tr> <th class="from"> </th> <th class="subject"> </th> </tr>
	<tr> 
		<td>Empresa</td>
		<td><input type="text" id="txtNombreEmpresaArea" name="txtNombreEmpresaArea" readonly="readonly"  style="width:90%" /></td>
	</tr>
	<tr>
		<td>Area</td>
		<td><input type="text" id="txtNombreArea" name="txtNombreArea"  style="width:60%"   />(*)<span id="errorNombreArea" class="errorValidar"></span></td>
	</tr>    
	<tr>
		<td>Codigo</td>
		<td><input type="text" id="txtCodigoArea" name="txtCodigoArea"  style="width:60%"   />(*)<span id="errorCodigoArea" class="errorValidar"></span></td>
	</tr>  
	</table>
	<input type="hidden"  id="txtDescripcionArea" name="txtDescripcionArea" value=""  />
	<input type="hidden"  id="tipoCRUDArea" name="tipoCRUDArea" value=""  > 
	
	<table id="idTableAreaElimina" class="idTableCss" style="display:none"   >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoArea"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table> 
	
	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarArea" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarArea" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
<div id="dialog3"    style="display:none" > 
	<table> 
	<tr><td colspan="2"><span id="errorServerTD" class="errorValidarServer" ></span></td></tr>
	</table>
	<input type="hidden" id="txtIdTD" name="txtIdTD"  style="width:20%"   />
	<table  id="idTableTDRegistra"  class="idTableCss" style="display: none" >
	<tr> <th class="from"> </th> <th class="subject"> </th> </tr>
	<tr> 
		<td>Empresa</td>
		<td><input type="text" id="txtNombreEmpresaTD" name="txtNombreEmpresaTD" readonly="readonly"  style="width:90%" /></td>
	</tr>
	<tr> 
		<td>Area</td>
		<td><input type="text" id="txtNombreAreaTD" name="txtNombreAreaTD" readonly="readonly"  style="width:90%" /></td>
	</tr>
	<tr>
		<td>Tipo Documental</td>
		<td>
		<input type="text" id="txtNombreTD" name="txtNombreTD"  style="width:90%"   />(*)<span id="errorNombreTD" class="errorValidar"></span></td>  
	</tr>    
	<tr>
		<td>Codigo</td>
		<td><input type="text" id="txtCodigoTD" name="txtCodigoTD"  style="width:60%"   />(*)<span id="errorCodigoTD" class="errorValidar"></span></td>
	</tr> 
	<tr>
		<td>Nombre de Tabla</td>
		<td><input type="text" id="txtNombreTablaTD" name="txtNombreTablaTD"  style="width:60%"   />(*)<span id="errorNombreTablaTD" class="errorValidar"></span></td>
	</tr>
	</table>
	<input type="hidden"  id="txtDescripcionTD" name="txtDescripcionTD" value=""  />
	<input type="hidden"  id="tipoCRUDTD" name="tipoCRUDTD" value=""  > 
	
	<table id="idTableTDElimina" class="idTableCss" style="display:none"   >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoTD"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table>
	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarTD" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarTD" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>


 


 
 
 
