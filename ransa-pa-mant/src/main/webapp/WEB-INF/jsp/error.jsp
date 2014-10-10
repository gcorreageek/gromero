<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

<link rel="stylesheet" type="text/css" href='<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/css/style.css")%>' />

<div class="contenedorPanelFormBox">
	<div>
		<form class="form" method="post" action="">
    		<table class="panelFormBox sinPadding" align="center" width="100%">
				<tbody>
        			<tr>
          				<td>
            				<span class="outputTextBold" style="color: #E00;">
            					<span>${mensajeError}</span>
								<br/>
								<a href="${url}"><span>${regresar}</span></a>
							</span>
          				</td>
          			</tr>          			
          		</tbody>
          	</table>
        </form>
	</div>	
</div>