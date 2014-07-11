<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		
	</div>
<!-- ${session_ruta}/indexMio3.html -->
  <script type="text/javascript" src="${session_ruta}js/jquery.js"></script>
  <script type="text/javascript" src="${session_ruta}js/jquery.gritter/js/jquery.gritter.js"></script>

  <script type="text/javascript" src="${session_ruta}js/jquery.nanoscroller/jquery.nanoscroller.js"></script>
	<script type="text/javascript" src="<c:url value="${session_ruta}js/behaviour/general.js" />"></script>
  <script src="${session_ruta}js/jquery.ui/jquery-ui.js" type="text/javascript"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.sparkline/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.easypiechart/jquery.easy-pie-chart.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.nestable/jquery.nestable.js"></script>
	<script type="text/javascript" src="${session_ruta}js/bootstrap.switch/bootstrap-switch.min.js"></script>
	<script type="text/javascript" src="${session_ruta}js/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
  <script src="${session_ruta}js/jquery.select2/select2.min.js" type="text/javascript"></script>
  <script src="${session_ruta}js/skycons/skycons.js" type="text/javascript"></script>
  <script src="${session_ruta}js/bootstrap.slider/js/bootstrap-slider.js" type="text/javascript"></script>
  <script src="${session_ruta}js/intro.js/intro.js" type="text/javascript"></script>
  <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false"></script>


  <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript">
      $(document).ready(function(){
        //initialize the javascript
        App.init();
        App.dashBoard();   
//           introJs().setOption('showBullets', false).start();
      });
    </script>
    <script src="${session_ruta}js/behaviour/voice-commands.js"></script>
  <script src="${session_ruta}js/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.pie.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.resize.js"></script>
	<script type="text/javascript" src="${session_ruta}js/jquery.flot/jquery.flot.labels.js"></script>
  </body>

</html>
