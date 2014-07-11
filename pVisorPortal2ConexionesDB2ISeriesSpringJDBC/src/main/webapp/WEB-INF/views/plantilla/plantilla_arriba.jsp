<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	<link href='http://fonts.googleapis.com/css?family=Raleway:100' rel='stylesheet' type='text/css'>
  <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700' rel='stylesheet' type='text/css'>
  

    <!-- Bootstrap core CSS --> 
    <link href="${session_ruta}js/bootstrap/dist/css/bootstrap.css" rel="stylesheet" />
	<link rel="stylesheet" href="${session_ruta}fonts/font-awesome-4/css/font-awesome.min.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <![endif]-->
  <link rel="stylesheet" type="text/css" href="${session_ruta}js/jquery.gritter/css/jquery.gritter.css" />

<link rel="stylesheet" type="text/css" href="${session_ruta}js/jquery.nanoscroller/nanoscroller.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/jquery.easypiechart/jquery.easy-pie-chart.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/bootstrap.switch/bootstrap-switch.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/jquery.select2/select2.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/bootstrap.slider/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${session_ruta}js/intro.js/introjs.css" />
<!-- Custom styles for this template -->
<link href="${session_ruta}css/style.css" rel="stylesheet" />

</head>
<body>

  <!-- Fixed navbar -->
  <div id="head-nav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="fa fa-gear"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/inicio.html"><span>Clean Zone</span></a>
      </div>
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li class="active"><a href="${pageContext.request.contextPath}/inicio.html">Inicio</a></li>
<!--           <li><a href="#about">About</a></li> -->
<!--           <li class="dropdown"> -->
<!--             <a href="#" class="dropdown-toggle" data-toggle="dropdown">Contact <b class="caret"></b></a> -->
<!--             <ul class="dropdown-menu"> -->
<!--               <li><a href="#">Action</a></li> -->
<!--               <li><a href="#">Another action</a></li> -->
<!--               <li><a href="#">Something else here</a></li> -->
<!-- 		      <li class="dropdown-submenu"><a href="#">Sub menu</a> -->
<!-- 		        <ul class="dropdown-menu"> -->
<!-- 		          <li><a href="#">Action</a></li> -->
<!-- 		          <li><a href="#">Another action</a></li> -->
<!-- 		          <li><a href="#">Something else here</a></li> -->
<!-- 		          </ul> -->
<!-- 		      </li>               -->
<!-- 		    </ul> -->
<!--           </li> -->
<!--           <li class="dropdown"> -->
<!--               <a href="#" class="dropdown-toggle" data-toggle="dropdown">Large menu <b class="caret"></b></a> -->
<!-- 		      <ul class="dropdown-menu col-menu-2"> -->
<!-- 		        <li class="col-sm-6 no-padding"> -->
<!-- 		          <ul> -->
<!-- 		          <li class="dropdown-header"><i class="fa fa-group"></i>Users</li> -->
<!-- 		          <li><a href="#">Action</a></li> -->
<!-- 		          <li><a href="#">Another action</a></li> -->
<!-- 		          <li><a href="#">Something else here</a></li> -->
<!-- 		          <li class="dropdown-header"><i class="fa fa-gear"></i>Config</li> -->
<!-- 		          <li><a href="#">Action</a></li> -->
<!-- 		          <li><a href="#">Another action</a></li> -->
<!-- 		          <li><a href="#">Something else here</a></li>  -->
<!-- 		          </ul> -->
<!-- 		        </li> -->
<!-- 		        <li  class="col-sm-6 no-padding"> -->
<!-- 		          <ul> -->
<!-- 		          <li class="dropdown-header"><i class="fa fa-legal"></i>Sales</li> -->
<!-- 		          <li><a href="#">New sale</a></li> -->
<!-- 		          <li><a href="#">Register a product</a></li> -->
<!-- 		          <li><a href="#">Register a client</a></li>  -->
<!-- 		          <li><a href="#">Month sales</a></li> -->
<!-- 		          <li><a href="#">Delivered orders</a></li> -->
<!-- 		          </ul> -->
<!-- 		        </li> -->
<!-- 		      </ul> -->
<!--           </li> -->
        </ul>
    <ul class="nav navbar-nav navbar-right user-nav">
      <li class="dropdown profile_menu">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img alt="Avatar" src="${session_ruta}images/avatar2.jpg" /><span>${session_usuario.nombres}</span> <b class="caret"></b></a>
        <ul class="dropdown-menu">
<!--           <li><a href="#">My Account</a></li> -->
<!--           <li><a href="#">Profile</a></li> -->
<!--           <li><a href="#">Messages</a></li> -->
<!--           <li class="divider"></li> -->
          <li><a href="${pageContext.request.contextPath}/des_logueo.html">Sign Out</a></li>
        </ul>
      </li>
    </ul>			
    <ul class="nav navbar-nav navbar-right not-nav" >
      <li class="button dropdown">
        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class=" fa fa-comments"></i></a>
<!--         <ul class="dropdown-menu messages"> -->
<!--           <li> -->
<!--             <div class="nano nscroller"> -->
<!--               <div class="content"> -->
<!--                 <ul> -->
<!--                   <li> -->
<!--                     <a href="#"> -->
<!--                       <img src="images/avatar2.jpg" alt="avatar" /><span class="date pull-right">13 Sept.</span> <span class="name">Daniel</span> I'm following you, and I want your money!  -->
<!--                     </a> -->
<!--                   </li> -->
<!--                   <li> -->
<!--                     <a href="#"> -->
<!--                       <img src="images/avatar_50.jpg" alt="avatar" /><span class="date pull-right">20 Oct.</span><span class="name">Adam</span> is now following you  -->
<!--                     </a> -->
<!--                   </li> -->
<!--                   <li> -->
<!--                     <a href="#"> -->
<!--                       <img src="images/avatar4_50.jpg" alt="avatar" /><span class="date pull-right">2 Nov.</span><span class="name">Michael</span> is now following you  -->
<!--                     </a> -->
<!--                   </li> -->
<!--                   <li> -->
<!--                     <a href="#"> -->
<!--                       <img src="images/avatar3_50.jpg" alt="avatar" /><span class="date pull-right">2 Nov.</span><span class="name">Lucy</span> is now following you  -->
<!--                     </a> -->
<!--                   </li> -->
<!--                 </ul> -->
<!--               </div> -->
<!--             </div> -->
<!--             <ul class="foot"><li><a href="#">View all messages </a></li></ul>            -->
<!--           </li> -->
<!--         </ul> -->
      </li>
      <li class="button dropdown">
      <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-globe"></i></a>
<!--         <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-globe"></i><span class="bubble">2</span></a> -->
<!--         <ul class="dropdown-menu"> -->
<!--           <li> -->
<!--             <div class="nano nscroller"> -->
<!--               <div class="content"> -->
<!--                 <ul> -->
<!--                   <li><a href="#"><i class="fa fa-cloud-upload info"></i><b>Daniel</b> is now following you <span class="date">2 minutes ago.</span></a></li> -->
<!--                   <li><a href="#"><i class="fa fa-male success"></i> <b>Michael</b> is now following you <span class="date">15 minutes ago.</span></a></li> -->
<!--                   <li><a href="#"><i class="fa fa-bug warning"></i> <b>Mia</b> commented on post <span class="date">30 minutes ago.</span></a></li> -->
<!--                   <li><a href="#"><i class="fa fa-credit-card danger"></i> <b>Andrew</b> killed someone <span class="date">1 hour ago.</span></a></li> -->
<!--                 </ul> -->
<!--               </div> -->
<!--             </div> -->
<!--             <ul class="foot"><li><a href="#">View all activity </a></li></ul>            -->
<!--           </li> -->
<!--         </ul> -->
      </li>
      <li class="button"><a href="javascript:;" class="speech-button"><i class="fa fa-microphone"></i></a></li>				
    </ul>

      </div><!--/.nav-collapse animate-collapse -->
    </div>
  </div>

	<div id="cl-wrapper" class="fixed-menu">
		<div class="cl-sidebar" data-position="right" data-step="1" data-intro="<strong>Fixed Sidebar</strong> <br/> It adjust to your needs." >
			<div class="cl-toggle"><i class="fa fa-bars"></i></div>
			<div class="cl-navblock">
        <div class="menu-space">
          <div class="content">
            <div class="side-user">
              <div class="avatar"><img src="${session_ruta}images/avatar1_50.jpg" alt="Avatar" /></div>
              <div class="info">
                <a href="#">${session_usuario.nombres}</a>
                <img src="${session_ruta}images/state_online.png" alt="Status" /> <span>Online</span>
              </div>
            </div>
            <ul class="cl-vnavigation">
	              <li><a href="#"><i class="fa fa-home"></i><span>Intranet</span></a>
	                <ul class="sub-menu">
	                  <li class="active"><a href="${pageContext.request.contextPath}/i/visor/inicio.html">Visor</a></li> 
	                </ul>
	              </li>
	              <li><a href="#"><i class="fa fa-home"></i><span>Extranet</span></a>
	                <ul class="sub-menu">
	                  <li class="active"><a href="${pageContext.request.contextPath}/e/visor/inicio.html">Visor</a></li> 
	                </ul>
	              </li>
<!--               <li><a href="#"><i class="fa fa-home"></i><span>Dashboard</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li class="active"><a href="index-2.html">Version 1</a></li> -->
<!--                   <li><a href="dashboard2.html"><span class="label label-primary pull-right">New</span> Version 2</a></li> -->
<!--                 </ul> -->
<!--               </li> -->
<!--               <li><a href="#"><i class="fa fa-smile-o"></i><span>UI Elements</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="ui-elements.html">General</a></li> -->
<!--                   <li><a href="ui-buttons.html">Buttons</a></li> -->
<!--                   <li><a href="ui-modals.html"><span class="label label-primary pull-right">New</span> Modals</a></li> -->
<!--                   <li><a href="ui-notifications.html"><span class="label label-primary pull-right">New</span> Notifications</a></li> -->
<!--                   <li><a href="ui-icons.html">Icons</a></li> -->
<!--                   <li><a href="ui-grid.html">Grid</a></li> -->
<!--                   <li><a href="ui-tabs-accordions.html">Tabs & Acordions</a></li> -->
<!--                   <li><a href="ui-nestable-lists.html">Nestable Lists</a></li> -->
<!--                   <li><a href="ui-treeview.html">Tree View</a></li> -->
<!--                 </ul> -->
<!--               </li> -->
<!--               <li><a href="#"><i class="fa fa-list-alt"></i><span>Forms</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="form-elements.html">Components</a></li> -->
<!--                   <li><a href="form-validation.html">Validation</a></li> -->
<!--                   <li><a href="form-wizard.html">Wizard</a></li> -->
<!--                   <li><a href="form-masks.html">Input Masks</a></li> -->
<!--                   <li><a href="form-multiselect.html"><span class="label label-primary pull-right">New</span>Multi Select</a></li> -->
<!--                   <li><a href="form-wysiwyg.html"><span class="label label-primary pull-right">New</span>WYSIWYG Editor</a></li> -->
<!--                   <li><a href="form-upload.html"><span class="label label-primary pull-right">New</span>Multi Upload</a></li> -->
<!--                 </ul> -->
<!--               </li> -->
<!--               <li><a href="#"><i class="fa fa-table"></i><span>Tables</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="tables-general.html">General</a></li> -->
<!--                   <li><a href="tables-datatables.html"><span class="label label-primary pull-right">New</span>Data Tables</a></li> -->
<!--                 </ul> -->
<!--               </li>               -->
<!--               <li><a href="#"><i class="fa fa-map-marker nav-icon"></i><span>Maps</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="maps.html">Google Maps</a></li> -->
<!--                   <li><a href="vector-maps.html"><span class="label label-primary pull-right">New</span>Vector Maps</a></li> -->
<!--                 </ul> -->
<!--               </li>              -->
<!--               <li><a href="#"><i class="fa fa-envelope nav-icon"></i><span>Email</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="email-inbox.html">Inbox</a></li> -->
<!--                   <li><a href="email-read.html">Email Detail</a></li> -->
<!--                   <li><a href="email-compose.html"><span class="label label-primary pull-right">New</span>Email Compose</a></li> -->
<!--                 </ul> -->
<!--               </li> -->
<!--               <li><a href="typography.html"><i class="fa fa-text-height"></i><span>Typography</span></a></li> -->
<!--               <li><a href="charts.html"><i class="fa fa-bar-chart-o"></i><span>Charts</span></a></li> -->
<!--               <li><a href="#"><i class="fa fa-file"></i><span>Pages</span></a> -->
<!--                 <ul class="sub-menu"> -->
<!--                   <li><a href="pages-blank.html">Blank Page</a></li> -->
<!--                   <li><a href="pages-blank-header.html">Blank Page Header</a></li> -->
<!--                   <li><a href="pages-blank-aside.html">Blank Page Aside</a></li> -->
<!--                   <li><a href="pages-login.html">Login</a></li> -->
<!--                   <li><a href="pages-404.html">404 Page</a></li> -->
<!--                   <li><a href="pages-500.html">500 Page</a></li> -->
<!--                   <li><a href="pages-sign-up.html"><span class="label label-primary pull-right">New</span>Sign Up</a></li> -->
<!--                   <li><a href="pages-forgot-password.html"><span class="label label-primary pull-right">New</span>Forgot Password</a></li> -->
<!--                   <li><a href="pages-profile.html"><span class="label label-primary pull-right">New</span>Profile</a></li> -->
<!--                   <li><a href="pages-search.html"><span class="label label-primary pull-right">New</span>Search</a></li> -->
<!--                   <li><a href="pages-calendar.html"><span class="label label-primary pull-right">New</span>Calendar</a></li> -->
<!--                   <li><a href="pages-code-editor.html"><span class="label label-primary pull-right">New</span>Code Editor</a></li> -->
<!--                   <li><a href="pages-gallery.html">Gallery</a></li> -->
<!--                   <li><a href="pages-timeline.html">Timeline</a></li> -->
<!--                 </ul> -->
<!--               </li> -->
            </ul>
          </div>
        </div>
        <div class="text-right collapse-button" style="padding:7px 9px;">
          <input type="text" class="form-control search" placeholder="Search..." />
          <button id="sidebar-collapse" class="btn btn-default" style=""><i style="color:#fff;" class="fa fa-angle-left"></i></button>
        </div>
			</div>
		</div>
	  