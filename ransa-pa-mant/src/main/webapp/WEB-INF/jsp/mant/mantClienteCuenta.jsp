<%@include file="../common/resources.jsp"%>

<portlet:defineObjects />
<%@include file="../common/imports.jsp"%>
<portlet:actionURL var="pruebaActionURl"/>
 
<fmt:setBundle basename="bundle.mantPrimaxBundle" var="bundleMessages"/>
<fmt:message var="tituloPagina" key='mantprimax.carteraclientes.titulopagina' bundle="${bundleMessages}"/>

<fmt:message var="lblRuc" key='mantprimax.carteraclientes.filtro.label.ruc' bundle="${bundleMessages}"/>
<fmt:message var="lblRazonSocial" key='mantprimax.carteraclientes.filtro.label.razonSocial' bundle="${bundleMessages}"/>
<fmt:message var="lblCodAuxiliar" key='mantprimax.carteraclientes.filtro.label.codAuxiliar' bundle="${bundleMessages}"/>
<fmt:message var="lblEstado" key='mantprimax.carteraclientes.filtro.label.estado' bundle="${bundleMessages}"/> 
<fmt:message var="lblFiltrar" key='mantprimax.carteraclientes.botones.filtrar' bundle="${bundleMessages}"/>

<%-- <%@include file="scriptJSP/consultaDocumentosJS.jsp"%> --%>
<portlet:resourceURL var="obtenerListaClientes" id="obtenerListaClientes" />
<portlet:resourceURL var="guardarCliente" id="guardarCliente" />

<portlet:resourceURL var="obtenerListaCuentas" id="obtenerListaCuentas" />
<portlet:resourceURL var="guardarCuenta" id="guardarCuenta" />

<script type="text/javascript">
$( document ).ready(function() { 
	$("#btnCancelarCliente").click(function(){
		$( "#dialog1" ).dialog( "close" );
	});
	$("#btnGrabarCliente").click(function(){ 
		$("#errorRucCliente").html("");
		$("#errorRazonSocialCliente").html(""); 
		$("#errorCodAuxiliarCliente").html(""); 
		$("#errorServerCliente").html("");
		 
		var esValidoRucCliente = false;
		var esValidoRazonSocialCliente = false;
		var esValidoCodAuxiliarCliente = false; 
		
		var txtIdCliente = $("#txtIdCliente").val(); 
		var txtRucCliente = $("#txtRucCliente").val();
		var txtRazonSocialCliente = $("#txtRazonSocialCliente").val();
		var txtCodAuxiliarCliente = $("#txtCodAuxiliarCliente").val();
		var cboEstadoCliente = $("#cboEstadoCliente").val(); 
		var tipoCRUDCliente = $("#tipoCRUDCliente").val();  
		
		if(txtRucCliente==''){  
			$("#errorRucCliente").html("Ingrese Ruc"); 
		}else{   
			if(!/^\d\d\d\d\d\d\d\d\d\d\d$/.test(txtRucCliente)){
				$("#errorRucCliente").html("Ingrese Ruc valido"); 
			}else{
				esValidoRucCliente = true;
			} 
		}
		if(txtRazonSocialCliente=='' || txtRazonSocialCliente.trim()==''){  
			$("#errorRazonSocialCliente").html("Ingrese Razon Social"); 
		}else{//valida el numero de caracteres
			if(!(txtRazonSocialCliente.length<100)){
				$("#errorRazonSocialCliente").html("Ingrese Razon Social valida, numero de caracteres excede a 99"); 
			}else{
				var esValidoCaracteres = true; 
				for (var i = 0; i < txtRazonSocialCliente.length; i++) { 
			 	    if ("#$%^&*()+=-[]\\'/{}|\"<>".indexOf(txtRazonSocialCliente.charAt(i)) != -1) {  
			 	    	$("#errorRazonSocialCliente").html("Ingrese Razon Social valida, caracteres no validos");   
			 	    	esValidoCaracteres = false;
			 	    } 
				} 
				if(esValidoCaracteres){
					esValidoRazonSocialCliente = true;
				}
			}  
		}
		if(txtCodAuxiliarCliente==''){  
			$("#errorCodAuxiliarCliente").html("Ingrese Codigo Auxiliar"); 
		}else{ 
			if(!/^\d+$/.test(txtCodAuxiliarCliente)){//valida digitos
				$("#errorCodAuxiliarCliente").html("Ingrese Codigo Auxiliar valido, numeros enteros positivos"); 
			}else{
				if(!(txtCodAuxiliarCliente<=2147483647 && txtCodAuxiliarCliente>=-2147483648 )){//rango de un Integer
					$("#errorCodAuxiliarCliente").html("Ingrese Codigo Auxiliar valido, entre el rango de numeros validos");
				}else{
					esValidoCodAuxiliarCliente = true;
				} 
			}   
		} 
		var seGuarda = false;
		if(tipoCRUDCliente=='ELIMINA'){
			seGuarda = true;
		}else{
			if(esValidoRucCliente && esValidoRazonSocialCliente && esValidoCodAuxiliarCliente){
				seGuarda = true;
			}
		}
		
		 
		if(seGuarda){
			$.get("${guardarCliente}", { 'txtIdCliente': txtIdCliente,'txtRucCliente':txtRucCliente,'txtRazonSocialCliente':txtRazonSocialCliente,
				'txtCodAuxiliarCliente':txtCodAuxiliarCliente,'cboEstadoCliente':cboEstadoCliente,'tipoCRUDCliente':tipoCRUDCliente } ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list1").trigger("reloadGrid");
						$("#dialog1").dialog( "close" );
						$( "#tabs" ).tabs( "disable" ,1);
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;
					        case 'CODERROR_CLIENTE_RUC':
					        	mensajeError = "Error, ruc invalido";
					            break; 
					        case 'CODERROR_CLIENTE_RAZONSOCIAL':
					        	mensajeError = "Error, razon social invalida";
					            break; 
					        case 'CODERROR_CLIENTE_CODAUXILIAR':
					        	mensajeError = "Error, codigo auxiliar invalido";
					            break; 
					        case 'CODERROR_CLIENTE_ESTADO':
					        	mensajeError = "Error, estado invalido";
					            break; 
					        case 'CODERROR_CLIENTE_RUC_REPETIDO':
					        	mensajeError = "Error, ruc repetido";
					            break; 
					        case 'CODERROR_CLIENTE_NOENCONTRADO':
					        	mensajeError = "Error, Cliente no encontrado";
					            break; 
					        case 'CODERROR_CLIENTE_TIENECUENTASAACTIVAS':
					        	mensajeError = "Error, Cliente tienes cuentas Activas; desactivelas para poder eliminarlas";
					            break; 
					        case 'CODERROR_CLIENTE_TIENECUENTASUSUARIOAACTIVAS':
					        	mensajeError = "Error, Cliente esta asociado a un Usuario; desactivelo para poder eliminarlas";
					            break; 
					    }
						$("#errorServerCliente").html(mensajeError);  
					} 
			});
		}
		
	});
	$("#btnAgregarCliente").click(function(){ 
		$("#errorRucCliente").html("");
		$("#errorRazonSocialCliente").html(""); 
		$("#errorCodAuxiliarCliente").html(""); 
		$("#errorServerCliente").html("");
		
		$("#txtIdCliente").val(''); 
		$("#txtRucCliente").val('');
		$("#txtRazonSocialCliente").val('');
		$("#txtCodAuxiliarCliente").val(''); 
		$("#cboEstadoCliente").val('');
		$("#tipoCRUDCliente").val('AGREGA'); 
		
		$("#idTableCliente").css({display:'block'});
		$("#idTableClienteElimina").css({display:'none'}); 
		$( "#dialog1" ).dialog({ width:550,height:250,modal:true,title: "Registro Cartera Cliente"}); 
	});
	jQuery("#btnModificarCliente").click( function() { 
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]);  
			$("#errorRucCliente").html("");
			$("#errorRazonSocialCliente").html(""); 
			$("#errorCodAuxiliarCliente").html("");
			$("#errorServerCliente").html("");
			
			$("#txtIdCliente").val(ret.id); 
			$("#txtRucCliente").val(ret.RUC);
			$("#txtRazonSocialCliente").val(ret.razonSocial);
			$("#txtCodAuxiliarCliente").val(ret.codigo);
			$("#cboEstadoCliente").val(ret.estado);
			$("#tipoCRUDCliente").val('ACTUALIZA'); 
			 
			$("#idTableCliente").css({display:'block'});
			$("#idTableClienteElimina").css({display:'none'});  
			$( "#dialog1" ).dialog({ width:550,height:200,modal:true,title: "Modificar Cartera Cliente"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	jQuery("#btnEliminarCliente").click( function() { 
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorRucCliente").html("");
			$("#errorRazonSocialCliente").html(""); 
			$("#errorCodAuxiliarCliente").html(""); 
			$("#errorServerCliente").html("");
			
			$("#txtIdCliente").val(ret.id); 
			$("#txtRucCliente").val(ret.RUC);
			$("#txtRazonSocialCliente").val(ret.razonSocial);
			$("#txtCodAuxiliarCliente").val(ret.codigo);
			if(ret.estado=='A'){
				$("#cboEstadoCliente").val('I');
			}else{
				$("#cboEstadoCliente").val('A');
			}
			
			$("#tipoCRUDCliente").val('ELIMINA'); 
			
			$("#idTableCliente").css({display:'none'});
			$("#idTableClienteElimina").css({display:'block'});   
			$( "#dialog1" ).dialog({ width:270,height:130,modal:true,title: "Elimina Cliente"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	
	
	
	$("#btnGrabarCuenta").click(function(){ 
		$("#errorNumeroCuenta").html("");
		$("#errorNombreUsuario").html(""); 
		$("#errorPlacaVehicular").html(""); 
		$("#errorServerCuenta").html("");
		 
		var esValidoNumeroCuenta = false;
		var esValidoNombreUsuario = false;
		var esValidoPlacaVehicular = false;  
		
		var txtIdCliente = $("#txtIdClienteFiltro").val();
		var txtIdCuenta = $("#txtIdCuenta").val();   
		var txtNumeroCuenta = $("#txtNumeroCuenta").val();
		var txtNombreUsuario = $("#txtNombreUsuario").val();
		var txtPlacaVehicular = $("#txtPlacaVehicular").val();
		var txtCodEs = $("#txtCodEs").val();
		var cboEstadoCuenta =  $("#cboEstadoCuenta").val();
		var tipoCRUDCuenta = $("#tipoCRUDCuenta").val();  
		
		if(txtNumeroCuenta=='' || txtNumeroCuenta.trim()==''){  
			$("#errorNumeroCuenta").html("Ingrese Numero Cuenta"); 
		}else{//valida el numero de caracteres
			if(!(txtNumeroCuenta.length<21)){
				$("#errorNumeroCuenta").html("Ingrese Numero Cuenta valida, numero de caracteres mayor a 20"); 
			}else{
				esValidoNumeroCuenta = true;
			}  
		}
		if(txtPlacaVehicular=='' || txtPlacaVehicular.trim()==''){  
			$("#errorPlacaVehicular").html("Ingrese Placa Vehicular"); 
		}else{//valida el numero de caracteres
			if(!(txtPlacaVehicular.length<11)){
				$("#errorPlacaVehicular").html("Ingrese Placa Vehicular valida, numero de caracteres mayor a 10"); 
			}else{
				esValidoPlacaVehicular = true;
			}  
		}
		if(txtNombreUsuario=='' || txtNombreUsuario.trim()==''){  
			$("#errorNombreUsuario").html("Ingrese Nombre Usuario"); 
		}else{//valida el numero de caracteres
			if(!(txtNombreUsuario.length<61)){
				$("#errorNombreUsuario").html("Ingrese Nombre Usuario valida, numero de caracteres mayor a 60"); 
			}else{
				esValidoNombreUsuario = true;
			}  
		} 
		 
		var seGuarda = false;
		if(tipoCRUDCuenta=='ELIMINA'){
			seGuarda = true;
		}else{
			if(esValidoNumeroCuenta && esValidoNombreUsuario && esValidoPlacaVehicular){
				seGuarda = true;
			} 
		}
		
		if(seGuarda){
			$.get( "${guardarCuenta}", { 'txtIdCliente': txtIdCliente,'txtIdCuenta':txtIdCuenta,'txtNumeroCuenta':txtNumeroCuenta,'txtNombreUsuario':txtNombreUsuario,
				'txtPlacaVehicular':txtPlacaVehicular,'cboEstadoCuenta':cboEstadoCuenta,'tipoCRUDCuenta':tipoCRUDCuenta,'txtCodEs':txtCodEs } ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list2").trigger("reloadGrid");
						$("#dialog2").dialog( "close" );  
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;
					        case 'CODERROR_CUENTA_NROCUENTA':
					        	mensajeError = "Error, numero cuenta invalido";
					            break; 
					        case 'CODERROR_CUENTA_NOMBREUSUARIO':
					        	mensajeError = "Error, nombre de usuario invalido";
					            break; 
					        case 'CODERROR_CUENTA_PLACAVEHICULAR':
					        	mensajeError = "Error, placa vehicular invalido";
					            break;  
					        case 'CODERROR_CUENTA_TIENECUENTASUSUARIOAACTIVAS':
					        	mensajeError = "Error, Cuenta esta asociado a un Usuario; desactivela para poder eliminarla";
					            break;  
					    }
						$("#errorServerCuenta").html(mensajeError);  
					}
					 
			});
		}
		
		
	});
	$("#btnAgregarCuenta").click(function(){  
		$("#errorNumeroCuenta").html("");
		$("#errorNombreUsuario").html(""); 
		$("#errorPlacaVehicular").html(""); 
		$("#errorServerCuenta").html("");
		
		$("#txtIdCuenta").val(''); 
		$("#txtRucCuenta").val($("#txtRucFiltro").val());
		$("#txtNumeroCuenta").val('');
		$("#txtNombreUsuario").val('');
		$("#txtPlacaVehicular").val('');  
		$("#txtCodEs").val(''); 
		$("#cboEstadoCuenta").val('');
		$("#tipoCRUDCuenta").val('AGREGA');
		
		$("#idTableCuenta").css({display:'block'});
		$("#idTableCuentaElimina").css({display:'none'}); 
		$( "#dialog2" ).dialog({ width:550,height:220,modal:true,title: "Registro Nro.Cuenta"}); 
	});  
	
	jQuery("#btnEliminarCuenta").click( function() { 
		var selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorNumeroCuenta").html("");
			$("#errorNombreUsuario").html(""); 
			$("#errorPlacaVehicular").html(""); 
			$("#errorServerCuenta").html("");
			
			$("#txtIdCuenta").val(''); 
			$("#txtRucCuenta").val('');
			$("#txtNumeroCuenta").val('');
			$("#txtNombreUsuario").val('');
			$("#txtPlacaVehicular").val('');   
			$("#txtCodEs").val('');
			$("#tipoCRUDCuenta").val('ELIMINA'); 
			$("#cboEstadoCuenta").val('');
			  
			$("#txtIdCuenta").val(ret.idCuenta); 
			$("#txtRucCuenta").val($("#txtRucFiltro").val());
			$("#txtNumeroCuenta").val(ret.numeroCuenta);
			$("#txtNombreUsuario").val(ret.nombreUsuario);
			$("#txtPlacaVehicular").val(ret.placaVehicular); 
			$("#txtCodEs").val(ret.codigoEs);
			$("#tipoCRUDCuenta").val('ELIMINA'); 
			
			if(ret.estado=='A'){
				$("#cboEstadoCuenta").val('I');
			}else{
				$("#cboEstadoCuenta").val('A');
			}
			$("#idTableCuenta").css({display:'none'});
			$("#idTableCuentaElimina").css({display:'block'});  
			$( "#dialog2" ).dialog({width:270,height:130,modal:true,title: "Elimina Nro.Cuenta"});
		}else{
			alert('Seleccione una opcion');
		} 
	});
	$("#btnCancelarCuenta").click(function(){
		$( "#dialog2" ).dialog( "close" );
	});		
	jQuery("#btnModificarCuenta").click( function() { 
		var selecccionadoGrilla; 
		selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		 
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]); 
			$("#errorNumeroCuenta").html("");
			$("#errorNombreUsuario").html(""); 
			$("#errorPlacaVehicular").html(""); 
			$("#errorServerCuenta").html("");
			
			$("#txtIdCuenta").val(''); 
			$("#txtRucCuenta").val('');
			$("#txtNumeroCuenta").val('');
			$("#txtNombreUsuario").val('');
			$("#txtPlacaVehicular").val('');  
			$("#txtCodEs").val('');
			
			$("#tipoCRUDCuenta").val('ACTUALIZA'); 
			$("#cboEstadoCuenta").val('');
			 
			$("#txtIdCuenta").val(ret.idCuenta); 
			$("#txtRucCuenta").val($("#txtRucFiltro").val());
			$("#txtNumeroCuenta").val(ret.numeroCuenta);
			$("#txtNombreUsuario").val(ret.nombreUsuario);
			$("#txtPlacaVehicular").val(ret.placaVehicular);  
			$("#txtCodEs").val(ret.codigoEs);
			
			$("#tipoCRUDCuenta").val('ACTUALIZA'); 
			$("#cboEstadoCuenta").val(ret.estado); 
		 
			$("#idTableCuenta").css({display:'block'});
			$("#idTableCuentaElimina").css({display:'none'}); 
			$( "#dialog2" ).dialog({ width:550,height:200,modal:true,title: "Modificar Nro.Cuenta"});
		}else{
			alert('Seleccione una opcion');
		} 
	}); 
	
	
	
	
	//txtRuc txtRazonSocial txtCodAuxiliar txtEstadoFiltro
	jQuery("#list1").jqGrid({
		url : '${obtenerListaClientes}'+'?txtRuc=&txtRazonSocial=&txtCodAuxiliar=&txtEstadoFiltro=', 
		datatype : "json", 
		colNames : [ 'Id','RUC', 'Razon Social', 'Cod.Auxiliar', 'Estado' ],
		colModel : [ { name : 'id', index : 'id', width : 10,hidden:true,editrules:{edithidden:true}  },  
		             { name : 'RUC', index : 'RUC', width : 160 }, 
		             { name : 'razonSocial', index : 'razonSocial', width : 630 }, 
		             { name : 'codigo', index : 'codigo', width : 130, align : "right" },
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
			}else{ $( "#tabs" ).tabs( "disable" ,1); }
		},
		onPaging: function(pgButton) {
			$( "#tabs" ).tabs( "disable" ,1);
	 	},
		caption : "Clientes"
	});
	jQuery("#list2").jqGrid({  
		url : '${obtenerListaCuentas}'+'?id=', 
		datatype : "json", 
		colNames : [ 'IdCuenta','idCliente','codigoEs','Nro.Cuenta', 'Nombre Usuario', 'Placa Vehic.', 'Estado' ],
		colModel : [ { name : 'idCuenta', index : 'idCuenta', width : 10,hidden:true,editrules:{edithidden:true}  },
					 { name : 'idCliente', index : 'idCliente', width : 10,hidden:true,editrules:{edithidden:true}  },  
					 { name : 'codigoEs', index : 'codigoEs', width : 10,hidden:true,editrules:{edithidden:true}  },  
		             { name : 'numeroCuenta', index : 'numeroCuenta', width : 150 }, 
		             { name : 'nombreUsuario', index : 'nombreUsuario', width : 620 }, 
		             { name : 'placaVehicular', index : 'placaVehicular', width : 150, align : "right" },
		             { name : 'estado', index : 'estado', width : 80 }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager2',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		caption : "Cuentas"
	});
	$( "#tabs" ).tabs();
	$( "#tabs" ).tabs( "disable", 1 ); 
	$("#btnFiltrarCliente").click(function(){  
		var  urlParametros= '${obtenerListaClientes}'+'?'+$('#form_Consulta :input').serialize();
		jQuery("#list1").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
		$( "#tabs" ).tabs( "disable" ,1);
	});
	$("#tabCarteraClientes").click(function(){
		$("#txtIdClienteFiltro").val(''); 
		$("#txtRucFiltro").val('');
		$("#txtRazonSocialFiltro").val('');
		$("#txtCodAuxiliarFiltro").val('');
		$("#txtEstadoFiltro").val(''); 
	});
	$("#tabNroCuentas").click(function(){
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]);  
			$("#txtIdClienteFiltro").val(ret.id); 
			$("#txtRucFiltro").val(ret.RUC);
			$("#txtRazonSocialFiltro").val(ret.razonSocial);
			$("#txtCodAuxiliarFiltro").val(ret.codigo);
			$("#txtEstadoFiltro").val(ret.estado); 
			 
			var urlParametros= '${obtenerListaCuentas}'+'?id='+$("#txtIdClienteFiltro").val();
			jQuery("#list2").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
		}else{
			alert('Seleccione una opcion');
		}  
	});
});
</script>
<div id="cuerpo">
<form:form id="form_Consulta" cssClass="formulario" modelAttribute="mantPrimaxClienteCuentaCommand" method="post" action="${pruebaActionURl}" acceptCharset="ISO-8859-1" style="margin-left:0;margin-top:-25px" onKeyPress="return disableEnterKey(event);">
  <div ><span class="titulopag">${tituloPagina}</span></div>
        <div id="box01">
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   > 
	              		<td class="cab-grilla" width="200" >${lblRuc}</td>
	              		<td class="cab-grilla" width="480" >${lblRazonSocial}</td>
	              		<td class="cab-grilla" width="150" >${lblCodAuxiliar}</td>
	              		<td class="cab-grilla" width="100" >${lblEstado}</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">
	              		<td align="center" ><input name="txtRuc" id="txtRucFiltro" style="width:80%"   /></td>
	              		<td align="center" ><input name="txtRazonSocial" id="txtRazonSocialFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtCodAuxiliar" id="txtCodAuxiliarFiltro"  style="width:80%"   /></td>
	              		<td align="center" >
	              		<select id="txtEstadoFiltro" name="txtEstadoFiltro" style="width:80%"  > 
	              			<option value="" ></option>
	              			<option value="A" >A</option>
	              			<option value="I" >I</option>
	              		</select>  
	              		</td>
	              	</tr>
	              </table>
              </td>
              <td>&nbsp;&nbsp;&nbsp;</td>
              <td width="100" align="right"><input type="button" id="btnFiltrarCliente" value="${lblFiltrar}" class="skip"    ></td>
            </tr> 
          </table>
    </div>
</form:form>
<input type="hidden" id="txtIdClienteFiltro"  />
    
    <div id="tabs" style="padding-top:13px" >
      <ul>
	      <li><a id="tabCarteraClientes" href="#tab1" name="#tab1" class="tituloTab" >Cartera Clientes</a></li>
	      <li><a id="tabNroCuentas" href="#tab2" name="#tab2" class="tituloTab" >Nro.Cuentas</a></li>    
	  </ul> 
	  <div id="content">
	      <div   id="tab1"   > 
		      <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			      	<input type="button" id="btnAgregarCliente" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnModificarCliente" value="Modificar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarCliente" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list1"></table> <div id="pager1"></div>
			  </div>
	      </div>
	      <div  id="tab2">
	       	  <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			        <input type="button" id="btnAgregarCuenta" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnModificarCuenta" value="Modificar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarCuenta" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list2"></table> <div id="pager2"></div>
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
	<tr><td colspan="2"><span id="errorServerCliente" class="errorValidarServer" ></span></td></tr>
	</table>
	<table  id="idTableCliente"  class="idTableCss">
	<tr><th class="from"></th><th class="subject"></th></tr>
	<input type="hidden"  id="txtIdCliente" name="txtId" value=""  >
	<tr> 
		<td>RUC</td>
		<td><input type="text" id="txtRucCliente" name="txtRuc" style="width:40%" />(*)<span id="errorRucCliente" class="errorValidar"></span></td>
	</tr>
	<tr>
		<td>Razon Social</td>
		<td><input type="text" id="txtRazonSocialCliente" name="txtRazonSocial"  style="width:90%"   />(*)<span id="errorRazonSocialCliente" class="errorValidar"></span></td>
	</tr> 
	<tr>
		<td>Cod.Auxiliar</td>
		<td><input type="text" id="txtCodAuxiliarCliente" name="txtCodAuxiliar" style="width:40%"  />(*)<span id="errorCodAuxiliarCliente" class="errorValidar"></span></td>
	</tr> 
	</table>
	<table id="idTableClienteElimina" class="idTableCss" style="display:none"   >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoCliente"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table>
	<input type="hidden"  id="tipoCRUDCliente" name="tipoCRUD" value=""  >
	<table  class="idTableCss" >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarCliente" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarCliente" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
 
 <div id="dialog2"    style="display:none" > 
 	<table> 
	<tr><td colspan="2"><span id="errorServerCuenta" class="errorValidarServer" ></span></td></tr>
	</table>
	<table  id="idTableCuenta"  class="idTableCss">
	<tr> <th class="from"> </th> <th class="subject"> </th> </tr>
	<input type="hidden"  id="txtIdCuenta" name="txtIdCuenta" value=""  >
	<input type="hidden"  id="txtCodEs" name="txtCodEs" value=""  > 
	<tr>  
		<td>RUC</td>
		<td><input type="text" id="txtRucCuenta" name="txtRucCuenta" style="width:40%" disabled="disabled" /></td>
	</tr>
	<tr>
		<td>Nro.Cuenta</td>
		<td><input type="text" id="txtNumeroCuenta" name="txtNumeroCuenta"  style="width:40%"   />(*)<span id="errorNumeroCuenta" class="errorValidar"></span></td>
	</tr> 
	<tr>
		<td>Nombre Usuario</td>
		<td><input type="text" id="txtNombreUsuario" name="txtNombreUsuario" style="width:90%"  />(*)<span id="errorNombreUsuario" class="errorValidar"></span></td>
	</tr>
	<tr>
		<td>Placa Vehicular</td>
		<td><input type="text" id="txtPlacaVehicular" name="txtPlacaVehicular" style="width:90%"  />(*)<span id="errorPlacaVehicular" class="errorValidar"></span></td>
	</tr>
	</table>
	<table id="idTableCuentaElimina" class="idTableCss" style="display:none"   >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
						<select id="cboEstadoCuenta"  style="width:80%" disabled="disabled" > 
	              			<option value="A" >A</option>
	              			<option value="I" >I</option>
	              		</select> 
		</td>
	</tr>
	</table>
	<input type="hidden"  id="tipoCRUDCuenta" name="tipoCRUDCuenta" value=""  > 
	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarCuenta" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarCuenta" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
