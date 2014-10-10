package com.ransa.portal.portlet;

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.ransa.portal.util.JSFUtil;

/**
 * Provides a common base class for all generated code behind files.
 */
@SuppressWarnings("unchecked")
public abstract class PageCodeBase {

	protected static final String ERROR_RESOURCE_BUNDLE = "com.ransa.portal.util.MensajeErrorUsuarioResource";
	protected String PATH_RESOURCE_BUNDLE;
	
	public PageCodeBase() {
	}

	/**
	 * <p>
	 * Return the {@link UIComponent} (if any) with the specified
	 * <code>id</code>, searching recursively starting at the specified
	 * <code>base</code>, and examining the base component itself, followed by
	 * examining all the base component's facets and children. Unlike
	 * findComponent method of {@link UIComponentBase}, which skips recursive
	 * scan each time it finds a {@link NamingContainer}, this method examines
	 * all components, regardless of their namespace (assuming IDs are unique).
	 * 
	 * @param base
	 *            Base {@link UIComponent} from which to search
	 * @param id
	 *            Component identifier to be matched
	 */
	public static UIComponent findComponent(UIComponent base, String id) {

		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			return base;
		}

		// Search through our facets and children
		UIComponent kid = null;
		UIComponent result = null;
		Iterator kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	public static UIComponent findComponentInRoot(String id) {
		UIComponent ret = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = findComponent(root, id);
		}

		return ret;
	}
	
	protected void gotoPage(String pageName) {
		if (pageName != null) {
			FacesContext context = JSFUtil.getFacesContext();
			UIViewRoot newView = context.getApplication().getViewHandler().createView(context, pageName);
			context.setViewRoot(newView);
			context.renderResponse();
		}
	}

	/**
	 * Place an Object on the tree's attribute map
	 * 
	 * @param key
	 * @param value
	 */
	protected void putTreeAttribute(String key, Object value) {
		JSFUtil.getFacesContext().getViewRoot().getAttributes().put(key, value);
	}

	/**
	 * Retrieve an Object from the tree's attribute map
	 * 
	 * @param key
	 * @return
	 */
	protected Object getTreeAttribute(String key) {
		return JSFUtil.getFacesContext().getViewRoot().getAttributes().get(key);
	}

	protected Map getApplicationScope() {
		return JSFUtil.getExternalContext().getApplicationMap();
	}
	
	protected Map getRequestParam() {
		return JSFUtil.getExternalContext().getRequestParameterMap();
	}

	protected Map getRequestScope() {
		return JSFUtil.getExternalContext().getRequestMap();
	}

	protected Map getSessionScope() {
		return JSFUtil.getExternalContext().getSessionMap();
	}

	protected Object getRequest() {
		return JSFUtil.getExternalContext().getRequest();
	}

	protected Object getResponse() {
		return JSFUtil.getExternalContext().getResponse();
	}

	protected String getMensaje(String keyMensaje) {
		return JSFUtil.getMensaje(keyMensaje, ERROR_RESOURCE_BUNDLE);
	}
	
	protected String getMensaje(String keyMensaje, String[] parametros) {
		return JSFUtil.getMensaje(keyMensaje, parametros, ERROR_RESOURCE_BUNDLE);
	}

	protected void addError(String message) {
		addError(null, message);
	}

	protected void addError(String id, String message) {
		FacesMessage facesMessage = null;
		if (id == null) {
			facesMessage = new FacesMessage(message);
		} else {
			facesMessage = new FacesMessage(null, message);
		}
		JSFUtil.getFacesContext().addMessage(id, facesMessage);
	}

	protected void setAttribute(String namParam, Object obj) {
		this.getServletRequest().setAttribute(namParam, obj);
	}

	protected HttpServletRequest getServletRequest() {
		return (HttpServletRequest) JSFUtil.getExternalContext().getRequest();
	}

}