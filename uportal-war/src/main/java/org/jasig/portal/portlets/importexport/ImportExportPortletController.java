package org.jasig.portal.portlets.importexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.EntityIdentifier;
import org.jasig.portal.io.xml.IDataImportExportService;
import org.jasig.portal.io.xml.IPortalDataType;
import org.jasig.portal.security.IAuthorizationPrincipal;
import org.jasig.portal.security.IPerson;
import org.jasig.portal.security.IPersonManager;
import org.jasig.portal.services.AuthorizationService;
import org.jasig.portal.url.IPortalRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

/**
 * ImportExportPortletController controls the display of the import/export
 * portlet interface views.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 * @version $Revision$
 */
@Controller
@RequestMapping("VIEW")
public class ImportExportPortletController {
	
	private static final String OWNER = "UP_SYSTEM";
	private static final String EXPORT_PERMISSION = "EXPORT_ENTITY";
	private static final String DELETE_PERMISSION = "DELETE_ENTITY";

    protected final Log log = LogFactory.getLog(getClass());

    private List<String> importExportTypes;
    
    @Required
    @Resource(name="importExportTypes")
    public void setImportExportTypes(List<String> importExportTypes) {
    	this.importExportTypes = importExportTypes;
    }
    
    private IPersonManager personManager;
    
    @Autowired(required = true)
    public void setPersonManager(IPersonManager personManager) {
    	this.personManager = personManager;
    }

    private IPortalRequestUtils portalRequestUtils;
    
    @Autowired(required = true)
    public void setPortalRequestUtils(IPortalRequestUtils portalRequestUtils) {
        Validate.notNull(portalRequestUtils);
        this.portalRequestUtils = portalRequestUtils;
    }

    private IDataImportExportService importExportService;
    @Autowired
    public void setImportExportService(IDataImportExportService importExportService) {
		this.importExportService = importExportService;
	}

	/**
     * Display the entity import form view.
     * 
     * @param request
     * @return
     */
    @RequestMapping
    public String getImportView(PortletRequest request) {
    	return "/jsp/ImportExportPortlet/import";
    }
    
    /**
     * Display the entity export form view.
     * 
     * @param request
     * @return
     */
    @RequestMapping(params="action=export")
    public ModelAndView getExportView(PortletRequest request) {
    	Map<String,Object> model = new HashMap<String,Object>();
    
    	// add a list of all permitted export types
    	final List<IPortalDataType> types = getAllowedTypes(request, EXPORT_PERMISSION);
    	model.put("supportedTypes", types);
    	
        return new ModelAndView("/jsp/ImportExportPortlet/export", model);
    }

    /**
     * Display the entity deletion form view.
     * 
     * @param request
     * @return
     */
    @RequestMapping(params="action=delete")
    public ModelAndView getDeleteView(PortletRequest request) {
    	Map<String,Object> model = new HashMap<String,Object>();
    	
    	// add a list of all permitted deletion types
    	final List<IPortalDataType> types = getAllowedTypes(request, DELETE_PERMISSION);
    	model.put("supportedTypes", types);
    	
        return new ModelAndView("/jsp/ImportExportPortlet/delete", model);
    }
    
    /**
     * Return a list of all permitted import/export types for the given permission
     * and the current user.
     * 
     * @param request
     * @param activityName
     * @return
     */
    protected List<IPortalDataType> getAllowedTypes(PortletRequest request, String activityName) {

    	// get the authorization principal representing the current user
        final HttpServletRequest httpServletRequest = this.portalRequestUtils.getPortletHttpRequest(request);
		final IPerson person = personManager.getPerson(httpServletRequest);
		final EntityIdentifier ei = person.getEntityIdentifier();
	    final IAuthorizationPrincipal ap = AuthorizationService.instance().newPrincipal(ei.getKey(), ei.getType());

	    Set<IPortalDataType> dataTypes = this.importExportService.getPortalDataTypes();
	    
	    // filter the list of configured import/export types by user permission
    	final List<IPortalDataType> results = new ArrayList<IPortalDataType>();
    	for (IPortalDataType type : dataTypes) {
    		final String typeId = type.getTypeId();
    	    if (ap.hasPermission(OWNER, activityName, typeId)) {
    	    	results.add(type);
    	    }    		
    	}
    	
    	for(String legacyType: importExportTypes) {
    		if (ap.hasPermission(OWNER, activityName, legacyType)) {
    			results.add(new CernnunosOnlyPortalDataType(legacyType));
    		}
    	}
    	return results;
    }

}
