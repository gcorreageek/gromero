<%@include file="../common/resources.jsp"%>

<portlet:defineObjects />
<%@include file="../common/imports.jsp"%>
<portlet:actionURL var="pruebaActionURl"/>
<portlet:actionURL var="pruebaActionURl2"/>
<portlet:actionURL var="pruebaActionURl3"/>
<fmt:setBundle basename="bundle.mantPrimaxBundle" var="bundleMessages"/>
<fmt:message var="tituloPagina" key='mantprimax.accesosusuario.titulopagina' bundle="${bundleMessages}"/>

<fmt:message var="lblIdFiltro" key='mantprimax.accesosusuario.filtro.label.id' bundle="${bundleMessages}"/>
<fmt:message var="lblTipoUsuarioFiltro" key='mantprimax.accesosusuario.filtro.label.tipousuario' bundle="${bundleMessages}"/> 
<fmt:message var="lblEstadoFiltro" key='mantprimax.accesosusuario.filtro.label.estado' bundle="${bundleMessages}"/> 

<fmt:message var="lblFiltrar" key='mantprimax.accesosusuario.botones.filtrar' bundle="${bundleMessages}"/>

<portlet:resourceURL var="obtenerListaUsuarios" id="obtenerListaUsuarios" />
<portlet:resourceURL var="guardarUsuario" id="guardarUsuario" />

<portlet:resourceURL var="obtenerListaAccesoDocumental" id="obtenerListaAccesoDocumental" />
<portlet:resourceURL var="guardarAccesoDocumental" id="guardarAccesoDocumental" />

<portlet:resourceURL var="obtenerListaAccesoCuentas" id="obtenerListaAccesoCuentas" />
<portlet:resourceURL var="guardarAccesoCuenta" id="guardarAccesoCuenta" />

<portlet:resourceURL var="obtenerListaAccesoDocumentalActivos" id="obtenerListaAccesoDocumentalActivos" />
<portlet:resourceURL var="guardarAccesoUsuarioDocumental" id="guardarAccesoUsuarioDocumental" />
<portlet:resourceURL var="eliminarAccesoUsuarioDocumental" id="eliminarAccesoUsuarioDocumental" />

<portlet:resourceURL var="obtenerListaClienteCuentas" id="obtenerListaClienteCuentas" />
<portlet:resourceURL var="guardarClienteCuentasUsuario" id="guardarClienteCuentasUsuario" />
<portlet:resourceURL var="eliminarAccesoUsuarioCuenta" id="eliminarAccesoUsuarioCuenta" />

<script type="text/javascript">
$( document ).ready(function() { 
	$( "#tabs" ).tabs();
	$( "#tabs" ).tabs( "disable", 1 ); 
	$( "#tabs" ).tabs( "disable", 2 );
	$("#tabUsuarios").click(function(){ 
		$('#txtIdFiltro').attr('readonly', false);
		$('#txtTipoUsuarioFiltro').attr('readonly', false);   
		$('#txtEstadoFiltro').removeAttr('disabled');
	});
	$("#tabAccesoDocumental").click(function(){
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]);  
			 
// 			txtIdFiltro txtTipoUsuarioFiltro txtEstadoFiltro
			$('#txtIdFiltro').attr('readonly', true);
			$('#txtTipoUsuarioFiltro').attr('readonly', true);  
			$('#txtEstadoFiltro').attr('disabled', 'disabled');
			
			$("#txtIdFiltro").val(ret.idUsuario); 
			$("#txtTipoUsuarioFiltro").val(ret.idTipoUsuario);   
			$("#txtEstadoFiltro").val(ret.stsUsuario);  
			
			var urlParametros1= '${obtenerListaAccesoDocumental}'+'?txtIdUsuario='+$("#txtIdFiltro").val();
			jQuery("#list2").jqGrid('setGridParam',{url:urlParametros1,page:1}).trigger("reloadGrid");
			
// 			$("#divMantAreas").css({display:'block'}); 
// 			$("#divMantEmpresa").css({display:'none'}); 
		}else{
			alert('Seleccione una opcion');
		}  
	}); //tabAccesoCuenta
	$("#tabAccesoCuenta").click(function(){
		var selecccionadoGrilla = jQuery("#list1").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var ret = jQuery("#list1").jqGrid('getRowData',selecccionadoGrilla[0]);  
			 
			$('#txtIdFiltro').attr('readonly', true);
			$('#txtTipoUsuarioFiltro').attr('readonly', true);  
			$('#txtEstadoFiltro').attr('disabled', 'disabled');
			
			$("#txtIdFiltro").val(ret.idUsuario); 
			$("#txtTipoUsuarioFiltro").val(ret.idTipoUsuario);   
			$("#txtEstadoFiltro").val(ret.stsUsuario);  
			
			var urlParametros2= '${obtenerListaAccesoCuentas}'+'?txtIdUsuario='+$("#txtIdFiltro").val()+'&estado=';
			jQuery("#list3").jqGrid('setGridParam',{url:urlParametros2,page:1}).trigger("reloadGrid");

		}else{
			alert('Seleccione una opcion');
		}  
	});
	jQuery("#list1").jqGrid({
		url : '${obtenerListaUsuarios}'+'?txtIdUsuario=&txtTipoUsuario=&txtEstado=', 
		datatype : "json", 
		colNames : [ 'IDUSUARIO','TIPO USUARIO','ESTADO' ],//idEmpresa nombre 
		colModel : [ { name : 'idUsuario', index : 'idUsuario', width : 450 },  
		             { name : 'idTipoUsuario', index : 'idTipoUsuario', width : 250 },   
		             { name : 'stsUsuario', index : 'stsUsuario', width : 130 }
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
			if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ $( "#tabs" ).tabs( "enable" ,1);$( "#tabs" ).tabs( "enable" ,2);   
			}else{ $( "#tabs" ).tabs( "disable" ,1);$( "#tabs" ).tabs( "disable" ,2);   }
		},
		caption : "Usuarios"
	});
	jQuery("#list2").jqGrid({
		url : '${obtenerListaAccesoDocumental}'+'?txtIdUsuario=', 
		datatype : "json", 
		colNames : [ 'idEmpresa','EMPRESA','idArea','AREA','idTipoDocumental','TIPO DOCUMENTAL','ESTADO' ],//
		colModel : [ { name : 'idEmpresa', index : 'idEmpresa', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'area.empresa.nombre', index : 'area.empresa.nombre', width : 250 },   
		             { name : 'idArea', index : 'idArea', width : 1,hidden:true,editrules:{edithidden:true} },
		             { name : 'area.nombre', index : 'area.nombre', width : 250 },
		             { name : 'idTipoDocumental', index : 'idTipoDocumental', width : 1,hidden:true,editrules:{edithidden:true} },
		             { name : 'nombre', index : 'nombre', width : 250 },
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
		},
		caption : "Acceso Documental"
	});
	jQuery("#list3").jqGrid({
		url : '${obtenerListaAccesoCuentas}'+'?txtIdUsuario=&estado=', 
		datatype : "json", 
		colNames : [ 'idCliente','RUC','RAZON SOCIAL','idCuenta','NRO CUENTA','ESTADO' ],//idEmpresa nombre 
		colModel : [ { name : 'idCliente', index : 'idCliente', width : 10 },  
		             { name : 'cliente.RUC', index : 'cliente.RUC', width : 250 },   
		             { name : 'cliente.razonSocial', index : 'cliente.razonSocial', width : 330 },
		             { name : 'id', index : 'id', width : 80 },
		             { name : 'numeroCuenta', index : 'numeroCuenta', width : 250 },
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
		},
		caption : "Acceso Cuenta"
	});
	jQuery("#list4").jqGrid({//ruc=&nroCuenta=&razonSocial=&estado=|txtRucMantCuentasFiltro txtNroCuentaMantCuentasFiltro txtRazonSocialMantCuentasFiltro
		url : '${obtenerListaClienteCuentas}'+'?txtRucMantCuentasFiltro=&txtNroCuentaMantCuentasFiltro=&txtRazonSocialMantCuentasFiltro=&txtIdUsuario=', 
		datatype : "json", 
		colNames : [ 'idCliente','RUC','RAZON SOCIAL','idCuenta','NRO.CUENTA' ],//idEmpresa nombre 
		colModel : [ { name : 'idCliente', index : 'idCliente', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'cliente.RUC', index : 'cliente.RUC', width : 120 },   
		             { name : 'cliente.razonSocial', index : 'cliente.razonSocial', width : 300 },
		             { name : 'idCuenta', index : 'idCuenta', width : 80,hidden:true,editrules:{edithidden:true} },
		             { name : 'numeroCuenta', index : 'numeroCuenta', width : 220 }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager4',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		onSelectRow: function(id){ 
		},
		caption : "Clientes y Cuentas Activas"
	});  
	jQuery("#list5").jqGrid({
		url : '${obtenerListaAccesoDocumentalActivos}'+'?txtIdUsuario=&txtEmpresaFiltro=&txtAreaFiltro=&txtTdFiltro=', 
		datatype : "json", 
		colNames : [ 'idEmpresa','EMPRESA','idArea','AREA','idTipoDocumental','TIPO DOCUMENTAL','ESTADO' ],//
		colModel : [ { name : 'idEmpresa', index : 'idEmpresa', width : 1,hidden:true,editrules:{edithidden:true} },  
		             { name : 'area.empresa.nombre', index : 'area.empresa.nombre', width : 250 },   
		             { name : 'idArea', index : 'idArea', width : 1,hidden:true,editrules:{edithidden:true} },
		             { name : 'area.nombre', index : 'area.nombre', width : 250 },
		             { name : 'idTipoDocumental', index : 'idTipoDocumental', width : 1,hidden:true,editrules:{edithidden:true} },
		             { name : 'nombre', index : 'nombre', width : 250 },
		             { name : 'estado', index : 'estado', width : 80,hidden:true,editrules:{edithidden:true} }
		             ],
		rowNum : 10,
		rowList : [ 10, 20, 30 ],
		pager : '#pager5',
		sortname : '1',
		viewrecords : true,
		sortorder : "1",
		multiselect: true,
		onSelectRow: function(id){ 
		},
		caption : "Empresa/Area/Tipo Documentales Activos"
	});
	$("#btnFiltrarUsuario").click(function(){ 
		var urlParametros= '${obtenerListaUsuarios}'+'?'+$('#form_Consulta :input').serialize();
		jQuery("#list1").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
	});
	$("#btnAgregarAccesoDocumental").click(function(){
		$("#txtEmpresaFiltro").val("");
		$("#txtAreaFiltro").val("");
		$("#txtTdFiltro").val("");
		$( "#dialogMantTipoDocumentalActivado" ).dialog({ width:850,height:490,modal:true,title: "Agregar Empresa/Area/Tipo Doc."});
		var urlParametros1= '${obtenerListaAccesoDocumentalActivos}'+'?txtIdUsuario='+$("#txtIdFiltro").val()+"&txtEmpresaFiltro=&txtAreaFiltro=&txtTdFiltro=";
		jQuery("#list5").jqGrid('setGridParam',{url:urlParametros1,page:1}).trigger("reloadGrid");
	});
	$("#btnGrabarTipoDocumental").click(function(){
		var selecccionadoGrilla = jQuery("#list5").jqGrid('getGridParam','selarrrow'); 
		if(selecccionadoGrilla.length >= 1){
			var urlGrabar = '';
			var ids='';
			for (var i = 0; i < selecccionadoGrilla.length; i++) {
				var ret = jQuery("#list5").jqGrid('getRowData',selecccionadoGrilla[i]);  
				var idEmpresa=ret.idEmpresa;
				var idArea=ret.idArea;
				var idTipoDocumental=ret.idTipoDocumental;
				ids = ids+'&idEmpresaidAreaidTipoDocumental='+idEmpresa+'-'+idArea+'-'+idTipoDocumental;
			}
			urlGrabar ='?txtIdUsuario='+$("#txtIdFiltro").val()+ids;
			$.get( "${guardarAccesoUsuarioDocumental}"+urlGrabar ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list2").trigger("reloadGrid");
						$("#dialogMantTipoDocumentalActivado").dialog( "close" );  
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;
					        case 'CODERROR_ACCESO_TD_NOSEINGRESARONVARIOS':
					        	mensajeError = "Error, No se ingresaron "+obj.mensajeError+"; para mas detalle revise el log de auditoria";
					            break;
					    }
						$("#errorServerMantTipoDocumentalActivado").html(mensajeError);  
					}
			});
		}else{
			alert('Seleccione una opcion');
		}
	});
	$("#btnCancelarTipoDocumental").click(function(){
		$("#dialogMantTipoDocumentalActivado").dialog( "close" );  
	});
	$("#btnFiltrarTipoDocumental").click(function(){
		var urlParametros1= '${obtenerListaAccesoDocumentalActivos}'+'?txtIdUsuario='+$("#txtIdFiltro").val()+'&'+$('#form_Consulta3 :input').serialize();
		jQuery("#list5").jqGrid('setGridParam',{url:urlParametros1,page:1}).trigger("reloadGrid");
	});
	$("#btnEliminarAccesoDocumental").click(function(){
		var selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			$("#errorServerMantTipoDocumentalEliminar").html("");  
			
	 		var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]); 
	 		if(ret.estado=='A'){
				$("#cboEstadoTipoDocumentalUsuario").val('I');
			}else{
				$("#cboEstadoTipoDocumentalUsuario").val('A');
			}
	 		$("#dialogMantTipoDocumentalEliminar").dialog({width:270,height:130,modal:true,title: "Elimina"});
	 	}else{
	 		alert('Seleccione una opcion');
	 	}  
	});
	$("#btnGrabarTipoDocumentalEliminar").click(function(){
		var selecccionadoGrilla = jQuery("#list2").jqGrid('getGridParam','selarrrow'); 
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var urlEliminar = '';
			var ret = jQuery("#list2").jqGrid('getRowData',selecccionadoGrilla[0]);  
			var idEmpresa=ret.idEmpresa;
			var idArea=ret.idArea;
			var idTipoDocumental=ret.idTipoDocumental;
			var ids='&idEmpresaidAreaidTipoDocumental='+idEmpresa+'-'+idArea+'-'+idTipoDocumental;;
			urlEliminar ='?txtIdUsuario='+$("#txtIdFiltro").val()+'&cboEstadoTipoDocumentalUsuario='+$("#cboEstadoTipoDocumentalUsuario").val()+ids;
			$.get( "${eliminarAccesoUsuarioDocumental}"+urlEliminar ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list2").trigger("reloadGrid");
						$("#dialogMantTipoDocumentalEliminar").dialog( "close" );  
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break; 
					    }
						$("#errorServerMantTipoDocumentalEliminar").html(mensajeError);  
					}
			});
		}else{
			alert('Seleccione una opcion');
		}
	});
	$("#btnCancelarTipoDocumentalEliminar").click(function(){
		$("#dialogMantTipoDocumentalEliminar").dialog( "close" ); 
	}); 

	$("#btnAgregarAccesoCuenta").click(function(){ 
		$("#txtRucMantCuentasFiltro").val('');
		$("#txtRazonSocialMantCuentasFiltro").val('');
		$("#txtNroCuentaMantCuentasFiltro").val('');
		$("#divMantCuentas").css({display:'block'}); 
		$( "#dialog1" ).dialog({ width:780,height:430,modal:true,title: "Listar Cliente Cuentas Activas"}); 
		var urlParametros= '${obtenerListaClienteCuentas}'+'?'+$('#form_Consulta2 :input').serialize()+"&txtIdUsuario="+$("#txtIdFiltro").val();
		jQuery("#list4").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
	});
	$("#btnEliminarAccesoCuenta").click(function(){
		var selecccionadoGrilla = jQuery("#list3").jqGrid('getGridParam','selarrrow');  
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			$("#errorServerMantCuentaEliminar").html("");  
			
	 		var ret = jQuery("#list3").jqGrid('getRowData',selecccionadoGrilla[0]); 
	 		if(ret.estado=='A'){
				$("#cboEstadoCuentaUsuario").val('I');
			}else{
				$("#cboEstadoCuentaUsuario").val('A');
			}
	 		$("#dialogMantCuentaEliminar").dialog({width:270,height:130,modal:true,title: "Elimina"});
	 	}else{
	 		alert('Seleccione una opcion');
	 	}
	}); 
	$("#btnGrabarCuentaEliminar").click(function(){
		var selecccionadoGrilla = jQuery("#list3").jqGrid('getGridParam','selarrrow'); 
		if(selecccionadoGrilla.length<2 && selecccionadoGrilla.length>0){ 
			var urlEliminar = '';
			var ret = jQuery("#list3").jqGrid('getRowData',selecccionadoGrilla[0]);  
			var idCliente=ret.idCliente;
			var idCuenta=ret.id; 
			var ids='&idClienteidCuenta='+idCliente+'-'+idCuenta;
			urlEliminar ='?txtIdUsuario='+$("#txtIdFiltro").val()+'&cboEstadoCuentaUsuario='+$("#cboEstadoCuentaUsuario").val()+ids;
			$.get( "${eliminarAccesoUsuarioCuenta}"+urlEliminar ,function(data){
					var obj = JSON.parse(data);
					if(obj.seGuardo){
						jQuery("#list3").trigger("reloadGrid");
						$("#dialogMantCuentaEliminar").dialog( "close" );  
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break; 
					    }
						$("#errorServerMantCuentaEliminar").html(mensajeError);  
					}
			});
		}else{
			alert('Seleccione una opcion');
		}
	});
	$("#btnCancelarCuentaEliminar").click(function(){
		$("#dialogMantCuentaEliminar").dialog( "close" ); 
	}); 
	$("#btnFiltrarMantCuentasFiltro").click(function(){  
		var urlParametros= '${obtenerListaClienteCuentas}'+'?'+$('#form_Consulta2 :input').serialize()+"&txtIdUsuario="+$("#txtIdFiltro").val();
		jQuery("#list4").jqGrid('setGridParam',{url:urlParametros,page:1}).trigger("reloadGrid");
	});
 	$("#btnGrabarCuenta").click(function(){ 
		var  selecccionadoGrilla = jQuery("#list4").jqGrid('getGridParam','selarrrow'); 
		if(selecccionadoGrilla.length >= 1){
			var parametros= '';
	 		var ids='';
	 		var idUsuario ="txtIdUsuario="+$("#txtIdFiltro").val();
			for (var i = 0; i < selecccionadoGrilla.length; i++) {
				var ret= jQuery("#list4").jqGrid('getRowData',selecccionadoGrilla[i]);
				ids = ids + '&idClienteidCuenta='+ret.idCliente+'-'+ret.idCuenta;
			}
			parametros = idUsuario+ids;
			$.get( "${guardarClienteCuentasUsuario}"+"?"+parametros ,function(data){
					var obj = JSON.parse(data);
					console.log(obj);
					if(obj.seGuardo){
						jQuery("#list3").trigger("reloadGrid");
						$("#dialog1").dialog( "close" );  
					}else{ 
						 var mensajeError = '';
						 switch (obj.codigoError) {
					        case 'CODERROR_INESPERADO':
					        	mensajeError = "Error, porfavor vuelva intentarlo luego";
					            break;
					        case 'CODERROR_ACCESO_CUENTA_NOSEINGRESARONVARIOS':
					        	mensajeError = "Error, No se ingresaron "+obj.mensajeError+", para mas detalle revise el log de auditoria";
					            break;
					    }
						$("#errorServerMantCuentas").html(mensajeError);  
					}
			});
		}else{
			alert('Seleccione una opcion');
		}
 	});
 	$("#btnCancelarCuenta").click(function(){ 
 		$( "#dialog1" ).dialog( "close" );
 	});
	
});
</script>


<div id="cuerpo">
  <div id="divMantUsuario"  >
  <form:form id="form_Consulta" cssClass="formulario" modelAttribute="mantPrimaxAccesoUsuarioCommand" method="post" action="${pruebaActionURl}" acceptCharset="ISO-8859-1" style="margin-left:0;margin-top:-25px" >
  		<div><span class="titulopag">${tituloPagina}</span></div>
        <div id="box01">
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   >  
	              		<td class="cab-grilla" width="200" >${lblIdFiltro}</td>
	              		<td class="cab-grilla" width="480" >${lblTipoUsuarioFiltro}</td> 
	              		<td class="cab-grilla" width="100" >${lblEstadoFiltro}</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">  
	              		<td align="center" ><input name="txtIdUsuario" id="txtIdFiltro"   style="width:80%"  class="mayusculas" /></td>
	              		<td align="center" ><input name="txtTipoUsuario" id="txtTipoUsuarioFiltro"  style="width:80%"   /></td>
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
              <td width="100" align="right"><input type="button" id="btnFiltrarUsuario" value="${lblFiltrar}" class="skip"    ></td>
            </tr> 
          </table>
    	</div>
    	</form:form>
  </div>
    
    <div id="tabs" style="padding-top:13px" >
      <ul>
	      <li><a id="tabUsuarios" href="#tab1" name="#tab1" class="tituloTab" >Usuarios</a></li>
	      <li><a id="tabAccesoDocumental" href="#tab2" name="#tab2" class="tituloTab" >Acceso Documental</a></li>    
	      <li><a id="tabAccesoCuenta" href="#tab3" name="#tab3" class="tituloTab" >Acceso Cuenta</a></li>    
	  </ul> 
	  <div id="content">
	      <div   id="tab1"   > 
		      <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
		      	</div>
			    <table id="list1"></table> <div id="pager1"></div>
			  </div>
	      </div>
	      <div  id="tab2">
	       	  <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			        <input type="button" id="btnAgregarAccesoDocumental" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarAccesoDocumental" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list2"></table> <div id="pager2"></div>
			  </div>
	      </div> 
	      <div  id="tab3">
	       	  <div style="margin-left:0px">
		      	<div style="margin-top:0px;margin-bottom:10px;text-align:right;padding-right:0px;">
			        <input type="button" id="btnAgregarAccesoCuenta" value="Agregar" class="skip"   onclick="javascript:void(0);">
			      	<input type="button" id="btnEliminarAccesoCuenta" value="Eliminar" class="skip"   onclick="javascript:void(0);">
		      	</div>
			    <table id="list3"></table> <div id="pager3"></div>
			  </div>
	      </div> 
	  </div>
    </div> 
    
</div> 
 
 <style>
 input.mayusculas{text-transform:uppercase;} 
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

<div id="dialogMantTipoDocumentalActivado"    style="display:none" > 
	<div id="divMantTipoDocumentalActivado"  >
	<table  style="padding:10px;" > 
	<tr><td colspan="2"><span id="errorServerMantTipoDocumentalActivado" class="errorValidarServer" ></span></td></tr>
	</table>
	<form:form id="form_Consulta3" cssClass="formulario" modelAttribute="mantPrimaxAccesoUsuarioCommand" method="post" action="${pruebaActionURl3}" acceptCharset="ISO-8859-1" style="margin-left:0;margin-top:-25px" >
  		<div><span class="titulopag">Buscar Empresa/Area/Tipo Documental Activados</span></div>
        <div id="box01">
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   >  
	              		<td class="cab-grilla" width="250" >Empresa</td>
	              		<td class="cab-grilla" width="250" >Area</td> 
	              		<td class="cab-grilla" width="250" >Tipo Documental</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">  
	              		<td align="center" ><input name="txtEmpresaFiltro" id="txtEmpresaFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtAreaFiltro" id="txtAreaFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtTdFiltro" id="txtTdFiltro"  style="width:80%"  /></td>
	              	</tr>
	             </table>
              </td>
              <td>&nbsp;&nbsp;&nbsp;</td>
              <td width="100" align="right"><input type="button" id="btnFiltrarTipoDocumental" value="Filtrar" class="skip"    ></td>
            </tr> 
          </table>
    	</div>
    	</form:form>
 	 </div>
 	 <div id="divGrillaTipoDocumentalActivado"  style="margin:10px;"  >
 	 <table id="list5"></table> <div id="pager5"></div>
 	 </div>
 	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarTipoDocumental" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarTipoDocumental" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div> 

<div id="dialogMantTipoDocumentalEliminar"    style="display:none" > 
	<div id="divMantTipoDocumentalEliminar"  >
	<table  style="padding:4px;" > 
	<tr><td colspan="2"><span id="errorServerMantTipoDocumentalEliminar" class="errorValidarServer" ></span></td></tr>
	</table>
	<table   class="idTableCss"     >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoTipoDocumentalUsuario"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table> 
 	 </div> 
 	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarTipoDocumentalEliminar" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarTipoDocumentalEliminar" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>


<div id="dialog1"    style="display:none" > 
<div id="divMantCuentas" > 
<form:form id="form_Consulta2" cssClass="formulario" modelAttribute="mantPrimaxAccesoUsuarioCommand" method="post" action="${pruebaActionURl2}"  acceptCharset="ISO-8859-1" style="margin-left:0;margin-top:-25px"  >
  		<div><span class="titulopag">Busqueda Cliente/Nro.Cuenta</span></div>
        <div id="box01">
        <table  style="padding:10px;" > 
		<tr><td colspan="2"><span id="errorServerMantCuentas" class="errorValidarServer" ></span></td></tr>
		</table>
          <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              	<table id="tblFiltros" width="100%" border="0" cellpadding="0" cellspacing="0" class="grilla" style="margin-top:12px;"  >
	              	<tr   >  
	              		<td class="cab-grilla" width="200" >RUC</td>
	              		<td class="cab-grilla" width="480" >Razon Social</td> 
	              		<td class="cab-grilla" width="200" >Nro.Cuenta</td>
	              	</tr>
	              	<tr class="td-grilla-blanco">  
	              		<td align="center" ><input name="txtRucMantCuentasFiltro" id="txtRucMantCuentasFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtRazonSocialMantCuentasFiltro" id="txtRazonSocialMantCuentasFiltro"  style="width:80%"   /></td>
	              		<td align="center" ><input name="txtNroCuentaMantCuentasFiltro" id="txtNroCuentaMantCuentasFiltro"  style="width:80%"   /></td>
	              	</tr>
	             </table>
              </td>
              <td>&nbsp;&nbsp;&nbsp;</td>
              <td width="100" align="right"><input type="button" id="btnFiltrarMantCuentasFiltro" value="${lblFiltrar}" class="skip"    ></td>
            </tr> 
          </table>
    	</div>
</form:form>
</div>
<div style="margin:10px;" >
<table id="list4"></table> <div id="pager4"></div>
</div>
<div>
	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarCuenta" type="button" value="Agregar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarCuenta" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
</div> 

<!--  	 cboEstadoCuentaUsuario dialogMantCuentaEliminar btnGrabarCuentaEliminar btnCancelarCuentaEliminar eliminarAccesoUsuarioCuenta -->
<div id="dialogMantCuentaEliminar"    style="display:none" > 
	<div id="divMantCuentaEliminar"  >
	<table  style="padding:4px;" > 
	<tr><td colspan="2"><span id="errorServerMantCuentaEliminar" class="errorValidarServer" ></span></td></tr>
	</table>
	<table   class="idTableCss"     >
	<tr><th class="from"></th><th class="subject"></th></tr>
	<tr>
		<td>Estado</td>
		<td> 
			<select id="cboEstadoCuentaUsuario"  style="width:80%"  disabled="disabled" > 
          			<option value="A" >A</option>
          			<option value="I" >I</option>
	        </select> 
		</td>
	</tr>
	</table> 
 	 </div> 
 	<table  class="idTableCss" >
	<tr align="center"  >
		<td  colspan="2" ><input id="btnGrabarCuentaEliminar" type="button" value="Grabar" class="skip"  />&nbsp;&nbsp;&nbsp;<input id="btnCancelarCuentaEliminar" type="button"  value="Cancelar" class="skip" /></td>
	</tr>
	</table>
</div>
 
 
