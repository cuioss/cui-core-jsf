package de.cuioss.jsf.api.components.base;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import lombok.experimental.Delegate;

/**
 * Minimal super-set for cui-based components that are at least {@link HtmlSelectBooleanCheckbox}.
 * Therefore it provides the handling of the styleClass and style attribute and
 * the implicit attributes provided by {@link HtmlSelectBooleanCheckbox}. In addition it acts as a
 * {@link ComponentBridge}
 *
 * @author Sven Haag
 */
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class BaseCuiHtmlSelectBooleanCheckboxComponent extends HtmlSelectBooleanCheckbox
        implements ComponentBridge, ComponentStyleClassProvider, StyleAttributeProvider {

    @Delegate
    protected final ComponentStyleClassProvider styleClassProvider;

    @Delegate
    protected final StyleAttributeProvider styleAttributeProvider;

    /***/
    public BaseCuiHtmlSelectBooleanCheckboxComponent() {
        super();
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        styleAttributeProvider = new StyleAttributeProviderImpl(this);
    }

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }

}
