package pe.com.ransa.portal.carga.common;

public class ConstantesLib {

	public enum Estado {
		ESTADO_A("A"),ESTADO_I("I") ; 
		public final String estado;
		private Estado(String estado) { this.estado = estado; }
	}
	
	private ConstantesLib() { }
	public static final String MODE_VIEW = "VIEW";
	public static final String _RUTA_ARCHIVOS= "/";
	public static final String HOST_FTP = "10.72.16.18";
    public static final String USER_FTP = "was_admin";
    public static final String PASS_FTP = "admin_was";
	
	public static final String PA_CONSULTADESPRENDIMIENTOS_PREF_REGPAGINA = "registrosPagina";//ya estaba
	
	public static final String PA_CONSULTADESPRENDIMIENTOS_PREF_PAGINACION_NUMERO_FILAS = "paginacionNumeroFilas";
	public static final String CARGAUSUARIOS_COMMAND_CLASS = "cargaUsuariosCommand";
//	public static final String VIEW_CONSULTADESPRENDIMIENTOS = "consultas/desprendimientos/consultaDesprendimientos";//consultaDesprendimientos.jsp
	public static final String VIEW_CARGAUSUARIOS = "carga/usuarios/cargaUsuarios";//consultaDesprendimientos.jsp
	
	public static final String MAPPING_ACTION = "action";
	public static final String MAPPING_ACCION = "accion";    
    public static final String IGUAL ="=";
    public static final String IR_PAGINA="irPagina"; 
//    getCboConsignatarioAction 
    
    
    public static final String CONSULTAR_CONSULTADESPRENDIMIENTOS_ACTION="consultarDesprendimientoAction";
    public static final String CONSULTAR_CONSULTADESPRENDIMIENTOS_RENDER="consultarDesprendimientoRender";
    public static final String GET_CBOCONSIGNATARIO_ACTION="getCboConsignatarioAction";
    public static final String GET_CBOCONSIGNATARIO_RENDER="getCboConsignatarioRender";
//  eliminarCboAction  
    public static final String ELIMINARCBO_ACTION="eliminarCboAction";
    public static final String ELIMINARCBO_RENDER="eliminarCboRender";

    
	public static final String NOMBRE_GRILLA_RESULTADO_BUSQUEDA = "tblResultadoConsulta";
	public static final String CONSULTAR_CONSULTADESPRENDIMIENTOS_PAGINADO_RENDER = "consultarDesprendimientoPaginadoRender";
	public static final String VALIDACION_ENTRADA = "isValidateAction";
    public static final String CODIGO_ENTRADA = "1";
    
    public static final String RUTA_EXCEL_ENTRADA="/Apps/PortalApplications/ransa-config-pa-consultadesprendimientos/DESPRENDIMIENTOS_YYYYMMDD_hhmmss.xls"; 
	public static final String RUTA_CONF="/Apps/PortalApplications/ransa-config-pa-consultadesprendimientos/";

	public static final String SUBIR_ARCHIVO_ACTION = "fileUploadAction";
	public static final String SUBIR_ARCHIVO_RENDER = "fileUploadRender";
	
	
	
	
//	public static final String ID_APPLICATION = "187";
	public static final String PATH_CONFIG = "/Apps/PortalApplications/ransa-config-pa-cargausuarios/configuracion.properties";
	
	
	
	public static final String ACTIVO="A";
	public static final String INACTIVO="I";
	
	
	
	
	public static final String CODERROR_INESPERADO = "CODERROR_INESPERADO";
	//
	public static final String CODERROR_CLIENTE_RUC ="CODERROR_CLIENTE_RUC";
	public static final String CODERROR_CLIENTE_RAZONSOCIAL ="CODERROR_CLIENTE_RAZONSOCIAL";
	public static final String CODERROR_CLIENTE_CODAUXILIAR ="CODERROR_CLIENTE_CODAUXILIAR";
	public static final String CODERROR_CLIENTE_ESTADO ="CODERROR_CLIENTE_ESTADO";
	public static final String CODERROR_CLIENTE_RUC_REPETIDO ="CODERROR_CLIENTE_RUC_REPETIDO"; 
	
	public static final String CODERROR_CLIENTE_NOENCONTRADO ="CODERROR_CLIENTE_NOENCONTRADO"; 
	public static final String CODERROR_CLIENTE_TIENECUENTASAACTIVAS ="CODERROR_CLIENTE_TIENECUENTASAACTIVAS";
	public static final String CODERROR_CLIENTE_TIENECUENTASUSUARIOAACTIVAS ="CODERROR_CLIENTE_TIENECUENTASUSUARIOAACTIVAS";
	
	
	public static final String CODERROR_CUENTA_NROCUENTA ="CODERROR_CUENTA_NROCUENTA";
	public static final String CODERROR_CUENTA_NOMBREUSUARIO ="CODERROR_CUENTA_NOMBREUSUARIO"; 
	public static final String CODERROR_CUENTA_PLACAVEHICULAR ="CODERROR_CUENTA_PLACAVEHICULAR"; 
	public static final String CODERROR_CUENTA_TIENECUENTASUSUARIOAACTIVAS ="CODERROR_CUENTA_TIENECUENTASUSUARIOAACTIVAS"; 
	
	
	public static final String CODERROR_EMPRESA_IDEMPRESA ="CODERROR_EMPRESA_IDEMPRESA";
	public static final String CODERROR_EMPRESA_NOMBRE ="CODERROR_EMPRESA_NOMBRE";
	public static final String CODERROR_EMPRESA_DESCRIPCION ="CODERROR_EMPRESA_DESCRIPCION";
	public static final String CODERROR_EMPRESA_CODIGO ="CODERROR_EMPRESA_CODIGO";
	public static final String CODERROR_EMPRESA_IDEMPRESA_REPETIDO ="CODERROR_EMPRESA_IDEMPRESA_REPETIDO"; 
	public static final String CODERROR_EMPRESA_CODIGO_REPETIDO ="CODERROR_EMPRESA_CODIGO_REPETIDO"; 
	
	
	public static final String CODERROR_EMPRESA_TIENEAREASACTIVAS ="CODERROR_EMPRESA_TIENEAREASACTIVAS"; 
	public static final String CODERROR_EMPRESA_TIENEUSUARIOASINGNADOSACTIVAS ="CODERROR_EMPRESA_TIENEUSUARIOASINGNADOSACTIVAS"; 
	
	
	
	
	public static final String CODERROR_AREA_NOMBRE ="CODERROR_AREA_NOMBRE"; 
	public static final String CODERROR_AREA_CODIGO ="CODERROR_AREA_CODIGO";
	 
	public static final String CODERROR_AREA_CODIGO_REPETIDO ="CODERROR_AREA_CODIGO_REPETIDO"; 
	
	public static final String CODERROR_AREA_TIENETIPODOCACTIVO ="CODERROR_AREA_TIENETIPODOCACTIVO";
	public static final String CODERROR_AREA_TIENEUSUARIOSACTIVO = "CODERROR_AREA_TIENEUSUARIOSACTIVO";
	
	
	
	
	
	public static final String CODERROR_TD_NOMBRE ="CODERROR_TD_NOMBRE"; 
	public static final String CODERROR_TD_CODIGO ="CODERROR_TD_CODIGO";
	public static final String CODERROR_TD_NOMBRETABLA ="CODERROR_TD_NOMBRETABLA";
	  
	public static final String CODERROR_TD_CODIGO_REPETIDO ="CODERROR_TD_CODIGO_REPETIDO"; 
	public static final String CODERROR_TD_NOMBRETABLA_REPETIDO ="CODERROR_TD_NOMBRETABLA_REPETIDO"; 
	
	public static final String CODERROR_TD_TIENEUSUARIOACTIVO ="CODERROR_TD_TIENEUSUARIOACTIVO";
	public static final String CODERROR_TD_TIENEDOCUMENTOACTIVO ="CODERROR_TD_TIENEDOCUMENTOACTIVO";
	public static final String CODERROR_TD_TIENEATRIBUTOTIPODOCACTIVO ="CODERROR_TD_TIENEATRIBUTOTIPODOCACTIVO";
	
	
	
	
	
	public static final String CODERROR_ACCESO_TD_NOSEINGRESARONVARIOS ="CODERROR_ACCESO_TD_NOSEINGRESARONVARIOS";
	public static final String CODERROR_ACCESO_CUENTA_NOSEINGRESARONVARIOS ="CODERROR_ACCESO_CUENTA_NOSEINGRESARONVARIOS";
	
	
	
	
	
	
	
}
