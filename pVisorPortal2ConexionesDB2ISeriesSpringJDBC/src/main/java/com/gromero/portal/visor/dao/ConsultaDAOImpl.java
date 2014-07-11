package com.gromero.portal.visor.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gromero.portal.visor.constantes.ConstantesBD;
import com.gromero.portal.visor.domain.DetalleOperacionTodo;

@Repository
public class ConsultaDAOImpl implements ConsultaDAO {
 
			
	
	private final Log log = LogFactory.getLog(getClass());
	
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate1(DataSource jdbcTemplate2) {
		this.jdbcTemplate = new JdbcTemplate(jdbcTemplate2);
	}

	@Override
	public List<DetalleOperacionTodo> consulta1RSAP04() {
		List<DetalleOperacionTodo> detalleOperacionTodo = 
		this.jdbcTemplate.query(ConstantesBD.COTIZACION1,new RowMapper<DetalleOperacionTodo>(){ 
								@Override
								public DetalleOperacionTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
									DetalleOperacionTodo detalle = new DetalleOperacionTodo(); 
									detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
									detalle.setNUMOPE(rs.getString("NUMOPE"));
									detalle.setNROOI(rs.getString("NROOI"));
									detalle.setCONTENEDOR(rs.getString("CONTENEDOR"));
									detalle.setCODSRVCO(rs.getString("CODSRVCO"));
									detalle.setNRODOCID(rs.getString("NRODOCID"));
									detalle.setFECOPE(rs.getString("FECOPE"));
									detalle.setCODMON(rs.getString("CODMON"));
									detalle.setIMPSCPR(rs.getString("IMP_SILE"));
									detalle.setCDRFCODMAT(rs.getString("CDRFCODMAT"));
									detalle.setNCCSAP(rs.getString("NCCSAP"));
									detalle.setFECOPE(rs.getString("FECOPE"));
									detalle.setNROPEDI(rs.getString("NROPEDI"));
									detalle.setFLGPEDI(rs.getString("FLGPEDI"));
									detalle.setIMP_PEDIDO(rs.getString("IMP_PEDIDO"));
									detalle.setTIPO(rs.getString("TIPO"));
									detalle.setDESSRVCO(rs.getString("DESSRVCO"));
									detalle.setRZINLO(rs.getString("RZINLO"));
									detalle.setSIGLA(rs.getString("SIGLA"));
									detalle.setSERIE(rs.getString("SERIE"));
									return detalle;
								} 
							}); 
		return detalleOperacionTodo;
	}


	@Override
	public List<DetalleOperacionTodo> consulta2RSAP04() {
				List<DetalleOperacionTodo> detalleOperacionTodo = 
				this.jdbcTemplate.query(ConstantesBD.COTIZACION2,new RowMapper<DetalleOperacionTodo>(){  
										@Override
										public DetalleOperacionTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
											DetalleOperacionTodo detalle = new DetalleOperacionTodo(); 
											detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
											detalle.setNUMOPE(rs.getString("NUMOPE"));
											detalle.setNROOI(rs.getString("NROOI"));
											detalle.setCONTENEDOR(rs.getString("CONTENEDOR"));
											detalle.setCODSRVCO(rs.getString("CODSRVCO"));
											detalle.setNRODOCID(rs.getString("NRODOCID"));
											detalle.setFECOPE(rs.getString("FECOPE"));
											detalle.setCODMON(rs.getString("CODMON"));
											detalle.setIMPSCPR(rs.getString("IMP_SILE"));
											detalle.setCDRFCODMAT(rs.getString("CDRFCODMAT"));
											detalle.setNCCSAP(rs.getString("NCCSAP"));
											detalle.setFECOPE(rs.getString("FECOPE"));
											detalle.setNROPEDI(rs.getString("NROPEDI"));
											detalle.setFLGPEDI(rs.getString("FLGPEDI"));
											detalle.setIMP_PEDIDO(rs.getString("IMP_PEDIDO"));
											detalle.setTIPO(rs.getString("TIPO"));
											detalle.setDESSRVCO(rs.getString("DESSRVCO"));
											detalle.setRZINLO(rs.getString("RZINLO"));
											detalle.setSIGLA(rs.getString("SIGLA"));
											detalle.setSERIE(rs.getString("SERIE"));
											return detalle;
										} 
									}); 
				return detalleOperacionTodo;
	}


	@Override
	public List<DetalleOperacionTodo> consulta3RSAP04() { 
			List<DetalleOperacionTodo> detalleOperacionTodo = 
				this.jdbcTemplate.query(ConstantesBD.COTIZACION3,new RowMapper<DetalleOperacionTodo>(){  
										@Override
										public DetalleOperacionTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
											DetalleOperacionTodo detalle = new DetalleOperacionTodo(); 
											detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
											detalle.setNUMOPE(rs.getString("NUMOPE"));
											detalle.setNROOI(rs.getString("NROOI"));
											detalle.setCONTENEDOR(rs.getString("CONTENEDOR"));
											detalle.setCODSRVCO(rs.getString("CODSRVCO"));
											detalle.setNRODOCID(rs.getString("NRODOCID"));
											detalle.setFECOPE(rs.getString("FECOPE"));
											detalle.setCODMON(rs.getString("CODMON"));
											detalle.setIMPSCPR(rs.getString("IMP_SILE"));
											detalle.setCDRFCODMAT(rs.getString("CDRFCODMAT"));
											detalle.setNCCSAP(rs.getString("NCCSAP"));
											detalle.setFECOPE(rs.getString("FECOPE"));
											detalle.setNROPEDI(rs.getString("NROPEDI"));
											detalle.setFLGPEDI(rs.getString("FLGPEDI"));
											detalle.setIMP_PEDIDO(rs.getString("IMP_PEDIDO"));
											detalle.setTIPO(rs.getString("TIPO"));
											detalle.setDESSRVCO(rs.getString("DESSRVCO"));
											detalle.setRZINLO(rs.getString("RZINLO"));
											detalle.setSIGLA(rs.getString("SIGLA"));
											detalle.setSERIE(rs.getString("SERIE"));
											return detalle;
										} 
									}); 
				return detalleOperacionTodo;
	}





 
	 
}
