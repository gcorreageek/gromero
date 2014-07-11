<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<jsp:include page="../../plantilla/plantilla_arriba.jsp"></jsp:include>
<div class="container-fluid" id="pcont">
	<div class="cl-mcont">
		<div class="row dash-cols">
			<div class="col-sm-12 col-md-12">
				<div class="block-flat">
		          <div class="header">							
		            <h3>Extranet Visor</h3>
		          </div>
		          <div class="content">
		             <form:form class="form-horizontal group-border-dashed" action="${pageContext.request.contextPath}/" style="border-radius: 0px;">
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Empresa</label>
		                <div class="col-sm-6"> 
<!-- 				                  <select class="form-control"> -->
<!-- 				                    <option value="0">Seleccionar</option>  -->
				                    <form:select path="" id="idEmpresa" cssClass="form-control" >
					                    <form:option value="0" label="Seleccionar" />
						   				<form:options items="${countryList}" />
				                    </form:select>
				                    
<!-- 				                  </select>		  -->
<!-- 		                  <input type="text" class="form-control"  > -->
		                </div>
		              </div>
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Area/Seccion|<%=request.getContextPath()%>|</label>
		                <div class="col-sm-6"> 
				                  <select class="form-control">
				                    <option>1</option>
				                    <option>2</option>
				                    <option>3</option>
				                    <option>4</option>
				                    <option>5</option>
				                  </select>	 
<!-- 		                  <input type="password" class="form-control"> -->
		                </div>
		              </div>
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Tipo Documental</label>
		                <div class="col-sm-6">
<!-- 		                  <input type="text" class="form-control" placeholder="Placeholder text"> -->
		                </div>
		              </div>
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Disabled Input</label>
		                <div class="col-sm-6">
		                  <input type="text" disabled="disabled" class="form-control" placeholder="Disabled input text">
		                </div>
		              </div>
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Readonly Input</label>
		                <div class="col-sm-6">
		                  <input type="text" readonly="readonly" class="form-control" value="Readonly input text">
		                </div>
		              </div>
		              <div class="form-group">
		                <label class="col-sm-3 control-label">Textarea</label>
		                <div class="col-sm-6">
		                  <textarea class="form-control"></textarea>
		                </div>
		              </div>
		              <div class="form-group" style="text-align: right;">
			              <button class="btn btn-primary" type="submit">Submit</button>
	              		  <button class="btn btn-default">Cancel</button>
	              	  </div>
		            </form:form>
		          </div>
		        </div>
			
			</div>
		</div>
	</div>
</div>
<jsp:include page="../../plantilla/plantilla_abajo.jsp"></jsp:include>




