package com.gromero.portal.visor.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gromero.portal.visor.constantes.ConstantesBD;
import com.gromero.portal.visor.constantes.ConstantesComunes;
import com.gromero.portal.visor.dao.mapper.UsuarioMapper;
import com.gromero.portal.visor.domain.DetalleOperacionTodo;
import com.gromero.portal.visor.domain.RSap21;
import com.gromero.portal.visor.domain.TBN016;

@Repository
public class ProcesoDAOImpl implements ProcesoDAO {
//	public final static String INSERT_RSAP21 = "INSERT INTO RSAP21 VALUES (@P1,@P2,@P3,@P4,@P5,@P6,@P7,@P8,@P9,@P10,@P11,@P12,@P13,@P14,@P15,@P16,@P17,@P18,@P19,@P20,@P21,@P22,@P23,@P24,@P25,@P26,@P27,@P28,@P29,@P30,@P31,@P32)";
	private final static String COTIZADOS = new StringBuilder()
   .append(" SELECT A.NUMOPE,B.NROOI, B.SIGLA || B.SERIE AS CONTENEDOR,J.CODSRVCO,I.NRODOCID,A.FECOPE,K.CODMON,   ")
   .append(" F.IMPSCPR AS IMP_SILE,J.CDRFCODMAT,J.NCCSAP,A.FECOPE,C.NROPEDI,C.FLGPEDI,C.IMPPEDI AS IMP_PEDIDO,    ")
   .append(" CASE WHEN D.IDCSTCNTCL IS NOT NULL THEN 'COTIZADO' END AS TIPO, J.DESSRVCO, I.RZINLO,C.IDOPEPED,     ")
   .append(" B.SIGLA, B.SERIE     ")
   .append(" FROM SICEX.TBN011 A     ")
   .append(" INNER JOIN SICEX.TBN014 B ON A.IDOPE=B.IDOPE AND B.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN016 C ON C.IDOPETCOI=B.IDOPETCOI  AND C.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN013 D ON D.IDOPECST=C.IDOPECST AND D.SACRG=1     ")
   .append( " INNER JOIN SICEX.TBN006 E ON E.IDCSTCNTCL=D.IDCSTCNTCL AND E.SACRG=1    " )
   .append(" INNER JOIN SICEX.TBN002 F ON F.IDCONDET=E.IDCONDET AND F.SACRG=1     ")
   .append( " INNER JOIN SICEX.TBN001 G ON G.IDCONPRV=F.IDCONPRV AND G.SACRG=1     ")
   .append(" INNER JOIN SICEX.TAM031 H ON H.IDINLOUNRL=G.IDINLOUNRL AND H.SACRG=1    ")
   .append(" INNER JOIN SICEX.TAM018 I ON I.IDINLOCTR=H.IDINLOCTR AND I.SACRG=1     ")
   .append(" INNER JOIN SICEX.TAM020 J ON J.IDSRVCO=F.IDSRVCO AND J.SACRG=1     ")
   .append(" INNER JOIN SICEX.TAM013 K ON K.IDMON=F.IDMON AND K.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN004 M ON M.IDCNTCLCAB=A.IDCNTCLCAB AND M.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN005 N ON N.IDCNTCLCAB=M.IDCNTCLCAB AND N.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN012 O ON O.IDOPEVTA=D.IDOPEVTA  AND O.IDOPE=A.IDOPE AND O.IDCNTCLDET=N.IDCNTCLDET AND O.SACRG=1     ")
   .append(" INNER JOIN SICEX.TBN015 P ON P.IDOPETCOI=B.IDOPETCOI AND P.IDOPEVTA=O.IDOPEVTA AND P.SACRG=1     ")
   .append(" WHERE A.SACRG=1 AND D.ESTOPECST='A' AND C.NROPEDI=0 AND     ")
   .append(" I.NRODOCID NOT IN (SELECT RUCPRO FROM SICEX.TAM052 WHERE SACRG=1)     ")
   .append(" AND O.ESTOPEVTA IN ('P','F') ORDER BY A.NUMOPE,B.NROOI,I.NRODOCID,K.CODMON  ").toString();
	
	private static final String INSERT_RSAP21 = "INSERT INTO RSAP21 VALUES (?,?,"+"?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?)";
	private static final String TAM044_SELECT_LAST_ULTNUM ="SELECT UTLNUM FROM TAM044 WHERE CLAVE = ? ";
	private static final String TAM044_UPDATE_ULTNUM_X_CLAVE ="UPDATE TAM044 SET UTLNUM = ? WHERE CLAVE = ? ";
	private static final String RSAP21_SELECT_NRFSAP_X_NUMOPG =" SELECT NRFSAP FROM DC@RNSLIB.RSAP21 WHERE NUMOPG = ? ";
	private static final String TBN016_UPDATE_CON_NROPEDI_IDOPEPED =" UPDATE TBN016 SET NROPEDI = ?  WHERE IDOPEPED = ? "; 
	
//	SELECT NRFSAP FROM DC@RNSLIB.RSAP21 WHERE NUMOPG =
			
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	

	@Override
	public Object procesarTarea(Object o) {
		return null;
	}


	@Override
	public RSap21 insertSap21(RSap21 sap21) {
		jdbcTemplate.update(INSERT_RSAP21,
		sap21.getP1(),sap21.getP2(),sap21.getP3(),sap21.getP4(),sap21.getP5(),sap21.getP6(),sap21.getP7(),sap21.getP8(),sap21.getP9(),sap21.getP10(),
		sap21.getP11(),sap21.getP12(),sap21.getP13(),sap21.getP14(),sap21.getP15(),sap21.getP16(),sap21.getP17(),sap21.getP18(),sap21.getP19(),sap21.getP20(),
		sap21.getP21(),sap21.getP22(),sap21.getP23(),sap21.getP24(),sap21.getP25(),sap21.getP26(),sap21.getP27(),sap21.getP28(),sap21.getP29(),sap21.getP30(),
		sap21.getP31(),sap21.getP32());
		return sap21;
	}


	@Override
	public void ejecutaProcedimientoSILEX21C(final Object... o) {
		this.jdbcTemplate.execute("CALL SILEX21C(?,?,?,?,?,?,?,?) ", new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
						ps.setString(1, o[0].toString());
						ps.setString(2, o[1].toString());
						ps.setString(3, o[2].toString());
						ps.setString(4, o[3].toString());
						ps.setString(5, o[4].toString());
						ps.setString(6, o[5].toString());
						ps.setString(7, o[6].toString());
						ps.setString(8, o[7].toString());
				return ps.execute();
			}
		}); 
	}


	@Override
	public List<DetalleOperacionTodo> consultaRSAP04() { 
		List<DetalleOperacionTodo> detalleOperacionTodo = 
		this.jdbcTemplate.query(COTIZADOS, 
				new RowMapper<DetalleOperacionTodo>(){
								@Override
								public DetalleOperacionTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
									DetalleOperacionTodo detalle = new DetalleOperacionTodo(); 
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
									detalle.setIDOPEPED(rs.getString("IDOPEPED"));
									detalle.setSIGLA(rs.getString("SIGLA"));
									detalle.setSERIE(rs.getString("SERIE"));
									return detalle;
								} 
							}); 
		return detalleOperacionTodo;
	}  
	@Override
	public String getNRFSAPPorNROOPG(Double nroFact) { 
		List<String> NRFSAP =  this.jdbcTemplate.queryForList(RSAP21_SELECT_NRFSAP_X_NUMOPG,new Object[]{nroFact},  String.class);
		if(!NRFSAP.isEmpty()){
			return NRFSAP.get(0).toString();
		} 
		return null;
	} 
	@Override
	public TBN016 actualizarTBN016Operaciones(TBN016 tbn016) { 
		this.jdbcTemplate.update(TBN016_UPDATE_CON_NROPEDI_IDOPEPED,new Object[]{tbn016.getNROPEDI(),tbn016.getIDOPEPED()});
		return tbn016;
	}
	@Override
	public Double actualizarObtenerNumerador(String codigo) {
		double resp =  0; 
		List<String> NRFSAP =  this.jdbcTemplate.queryForList(TAM044_SELECT_LAST_ULTNUM,new Object[]{codigo},  String.class);
		if(!NRFSAP.isEmpty()){
			resp =Double.parseDouble( NRFSAP.get(0).toString());
		}
		resp = resp +1;
		this.jdbcTemplate.update(TAM044_UPDATE_ULTNUM_X_CLAVE, resp, codigo);
		return resp;
	}
	@Override
	public List<DetalleOperacionTodo> consultaRSAP04Adicionales() {
		return null;
	}


	@Override
	public List<DetalleOperacionTodo> consulta1RSAP04() {
		List<DetalleOperacionTodo> detalleOperacionTodo = 
		this.jdbcTemplate.query(ConstantesBD.COTIZACION1,new RowMapper<DetalleOperacionTodo>(){ 
								@Override
								public DetalleOperacionTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
									DetalleOperacionTodo detalle = new DetalleOperacionTodo(); 
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
									detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
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
											detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
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
											detalle.setIDOPEPED(rs.getString("IDOPEPED")); 
											return detalle;
										} 
									}); 
				return detalleOperacionTodo;
	}

 
	 
}
