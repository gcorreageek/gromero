<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="${session_ruta}images/favicon.png">

	<title>Clean Zone</title>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>

	<!-- Bootstrap core CSS -->
	<link href="${session_ruta}js/bootstrap/dist/css/bootstrap.css" rel="stylesheet">

	<link rel="stylesheet" href="${session_ruta}fonts/font-awesome-4/css/font-awesome.min.css">

	<!-- Custom styles for this template -->
	<link href="${session_ruta}css/style.css" rel="stylesheet" />	

</head> 
<body class="texture">
<div id="cl-wrapper" class="login-container">

	<div class="middle-login">
		<div class="block-flat">
			<div class="header">							
				<h3 class="text-center"><img class="logo-img" src="${session_ruta}images/logo.png" alt="logo"/>Clean Zone</h3>
			</div>
			<div>
				<form:form style="margin-bottom: 0px !important;" class="form-horizontal" action="${pageContext.request.contextPath}/login.html" commandName="usu" >
					<div class="content">
						<h4 class="title">Login Access</h4>
							<div class="form-group">
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-user"></i></span> 
										<form:input type="text" placeholder="Username" id="username" class="form-control" path="userName"   />
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-lock"></i></span>
										<form:password   placeholder="Password" id="password" class="form-control" path="userPass" />
										<br>
										<span style="color: red;" >${mensaje_usuario}</span>
									</div>
								</div>
							</div>
							
					</div>
					<div class="foot">
						<button class="btn btn-default" data-dismiss="modal" type="button">Register</button>
						<button class="btn btn-primary" data-dismiss="modal" type="submit">Log me in</button>
					</div>
				</form:form>
			</div>
		</div>
		<div class="text-center out-links"><a href="#">&copy; 2013 Your Company</a></div>
	</div> 
	
</div>

<script src="${session_ruta}js/jquery.js"></script>
	<script type="text/javascript" src="${session_ruta}js/behaviour/general.js"></script>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
  <script src="${session_ruta}js/behaviour/voice-commands.js"></script>
  <script src="${session_ruta}js/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.labels.js"></script>
</body>
</html>
