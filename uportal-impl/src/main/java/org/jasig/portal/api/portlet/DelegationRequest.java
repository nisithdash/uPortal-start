/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */

package org.jasig.portal.api.portlet;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Used to set the state of the delegate and for the basis of URLs generated by the delegate.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class DelegationRequest {
    private DelegateState delegateState;
    private WindowState parentWindowState;
    private PortletMode parentPortletMode;
    private Map<String, List<String>> parentParameters;
    
    
    public DelegateState getDelegateState() {
        return this.delegateState;
    }
    public void setDelegateState(DelegateState delegateState) {
        this.delegateState = delegateState;
    }
    public WindowState getParentWindowState() {
        return this.parentWindowState;
    }
    public void setParentWindowState(WindowState parentWindowState) {
        this.parentWindowState = parentWindowState;
    }
    public PortletMode getParentPortletMode() {
        return this.parentPortletMode;
    }
    public void setParentPortletMode(PortletMode parentPortletMode) {
        this.parentPortletMode = parentPortletMode;
    }
    public Map<String, List<String>> getParentParameters() {
        return this.parentParameters;
    }
    public void setParentParameters(Map<String, List<String>> parentParameters) {
        this.parentParameters = parentParameters;
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DelegationRequest)) {
            return false;
        }
        DelegationRequest rhs = (DelegationRequest) object;
        return new EqualsBuilder()
            .append(this.delegateState, rhs.delegateState)
            .append(this.parentWindowState, rhs.parentWindowState)
            .append(this.parentPortletMode, rhs.parentPortletMode)
            .append(this.parentParameters, rhs.parentParameters)
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(1445247369, -1009176817)
            .append(this.delegateState)
            .append(this.parentWindowState)
            .append(this.parentPortletMode)
            .append(this.parentParameters)
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("delegateState", this.delegateState)
            .append("parentWindowState", this.parentWindowState)
            .append("parentPortletMode", this.parentPortletMode)
            .append("parentParameters", this.parentParameters)
            .toString();
    }
}