package com.ransa.portal.util;

import java.util.Enumeration;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.ibm.toad.utils.Log;
import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.exception.EjecucionException;

public class ConexionLDAPTDS {
	
	private static Logger logger = Logger.getLogger(ConexionLDAPTDS.class);
	private static final int ID_HOST_LDAP_TDS = 2;
	private static final String LDAP_BASE = "dc=Usuarios,dc=Extranet,dc=Portal,DC=GRUPORANSA,DC=GROMERO,DC=NET";
	
	
	private String DN;
	private String hostLDAP;
	
//	public ConexionLDAPTDS(String idUsuario) {
//		logger.debug("constructor");
//		DN = idUsuario;
//		logger.debug("DN=" + DN);
//		hostLDAP = DaoFactory.getInstance().getUtilDao().getParametroConfiguracion(ID_HOST_LDAP_TDS);
//		//hostLDAP = "ldap://RANCALJ2EE.ransa.net:389";
//		logger.debug("hostLDAP=" + hostLDAP);
//	}
		
	public String getDN() {
		return DN;
	}
	
	public String getHostLDAP() {
		return hostLDAP;
	}
	
	private DirContext getDirContext(String contraseniaUsuario) {
		logger.debug("getDirContext");		
		Properties props = new Properties(); 
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); 
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.SECURITY_CREDENTIALS, contraseniaUsuario); 
		props.put(Context.SECURITY_PRINCIPAL, DN); 
		props.put(Context.PROVIDER_URL, hostLDAP); 
		logger.debug("contraseniaUsuario=" + contraseniaUsuario);
		try {
			logger.debug("verificando si id y contrasenia de usuario"); 
			DirContext ctx = new InitialDirContext(props);
			logger.debug("autenticacion correcta de usuario con DN: " + DN);
			return ctx;
		} catch (Exception e) {
			logger.error("Error: ",e);
			logger.debug("autenticacion incorrecta de usuario con DN: " + DN);
			String mensajeErrorException = Util.getStackTrace(e);
			if (mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_TDS_PASSWORD_EXPIRADO) != -1) {
				logger.debug("Contrasenia de usuario expirado");
				throw new EjecucionException(CodigoError.CODIGO_ERROR_TDS_PASSWORD_EXPIRADO, e);
			} else {
				throw new EjecucionException("Error al autenticar usuario contra ldap", e);
			}
		}		
	}
	
	public boolean autenticacionUsuarioLDAP(String contraseniaUsuario) {
		logger.debug("autenticacionUsuarioLDAP");
		try {
			getDirContext(contraseniaUsuario);
			return true;
		} catch (EjecucionException e) {
			return false;
		}		
	}
	
	public void cambiarContrasenia(String contraseniaUsuario, String nuevaContraseniaUsuario) {
		logger.debug("cambiarContrasenia");
		DirContext ctx = getDirContext(contraseniaUsuario);
		try {
			ModificationItem[] mods = new ModificationItem[1];			
		    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
		    		new BasicAttribute("userPassword", nuevaContraseniaUsuario));
		    ctx.modifyAttributes(DN, mods);	
		    
		} catch (Exception e) {				
			throw new EjecucionException("No se pudo cambiar contrasenia", e);
		}
	}
	
	/**
	 * Metodo que sirvwe para obtener la contrasenia del usuario
	 * en base al uid
	 * @param contraseniaUsuario
	 * @param uidBuscar
	 * @return
	 */
	public String obtenerContraseniaUsuarioPorUid(String contraseniaUsuario, String uidBuscar) {
		logger.debug("obtenerCamposUsuarioEnBaseUID");
		DirContext ctx = getDirContext(contraseniaUsuario);
		try {
			String base = LDAP_BASE;
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "UID="+uidBuscar;
            Log.debug("filter: "+filter);
            NamingEnumeration<?> results = ctx.search(base, filter, constraints);
            logger.debug("results: "+results);
            
            Enumeration<?> enumVals = null;
            StringBuilder sb = new StringBuilder();

            int i=0;
            while (results != null && results.hasMore())
            {

                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();          
                for (NamingEnumeration<?> ne = attrs.getAll(); ne.hasMoreElements();)
                {
                    Attribute attr = (Attribute) ne.next();
                    if (attr.getID().equalsIgnoreCase("userPassword"))
                    {
                        enumVals = attr.getAll();
                        if (enumVals != null)
                        {
                            while (enumVals.hasMoreElements())
                            {
                                sb.append(new String((byte[])enumVals.nextElement()));
                                break;
                            }
                        }
                        break;
                    }
                }                
                i++;
            }
            return sb.toString();
            
		} catch (Exception e) {				
			throw new EjecucionException("No se pudo obtener la contrasenia", e);
		}
	}
	
}