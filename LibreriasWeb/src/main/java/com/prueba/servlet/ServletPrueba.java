package com.prueba.servlet;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.ransa.portal.intranet.dto.ReporteIngresoBodega;
import pe.com.ransa.portal.intranet.util.UtilExcel;


/**
 * Servlet implementation class ServletPrueba
 */
public class ServletPrueba extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletPrueba() {
        // TODO Auto-generated constructor stub
    }
    private static final int BYTES_DOWNLOAD = 1024;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		System.out.println("llama al get!");
		List<ReporteIngresoBodega> lReporte = new ArrayList<ReporteIngresoBodega>();
		for (int i = 0; i < 2200; i++) {
			ReporteIngresoBodega e2 = new ReporteIngresoBodega();
			e2.setNroDocumentoIngreso("NRO2232211"+i);
			e2.setNombreConsignatario("Gusta 3"+i);
			lReporte.add(e2);
		}
		
		String nombreArchivoTemporal = request.getSession().getId();
		StringBuilder rutaSalida = new StringBuilder();
		rutaSalida.append("D:\\").append(nombreArchivoTemporal).append(".xls"); 
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=export.xls");
		
		 
			try {
				UtilExcel.procesoExcel(lReporte, response.getOutputStream(), "D://test.xls", rutaSalida.toString());
			} catch (SocketException e) {
				System.out.println("Se perdio la conexion con el cliente!");
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("entra al post");
		System.out.println("entra al post:"+request.getParameter("nombre"));
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/resultado.jsp");
		rd.forward(request, response);
	}
	
	

}
