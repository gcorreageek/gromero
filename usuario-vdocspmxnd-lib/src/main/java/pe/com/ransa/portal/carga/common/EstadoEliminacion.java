package pe.com.ransa.portal.carga.common;

public enum EstadoEliminacion {
	ESTADO_A("A"),ESTADO_I("I") ; 
	public final String estado;
	private EstadoEliminacion(String estado) { this.estado = estado; }
}