package com.gromero.portal.visor.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.ConsultaDAO;
import com.gromero.portal.visor.domain.MmTempo;

@WebService 
@SOAPBinding(style = SOAPBinding.Style.RPC)
//@Service
public class ConsultaServiceImpl implements ConsultaService {

	@Autowired
	ConsultaDAO consultaDAO;
	
	@WebMethod(operationName = "listaMmTempo")
	@Override
	public MmTempo[] listaMmTempo() {
//		MmTempo mm = new MmTempo();
//		mm.setImp112(22.2);
//		mm.setImp113(2221.2);
//		MmTempo mm1 = new MmTempo();
//		mm1.setImp112(21.2);
//		mm1.setImp113(4421.2);
//		
//		MmTempo[] mmm = new MmTempo[]{mm1,mm};
		return consultaDAO.listaMmTempo().toArray(new MmTempo[]{ new MmTempo()});
//		return mmm;
	}

	
 

public static void main(String[] args) {
System.out.println("Here we go!");
Endpoint.publish("http://localhost:8080/proy", new ConsultaServiceImpl());
System.out.println("Yeahh!");
}


}
