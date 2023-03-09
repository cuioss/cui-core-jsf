package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'form-group'.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <ul>
 * <li>Form-Groups are similar to rows but within a form-context</li>
 * <li>Use rows to create horizontal groups of columns.</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "http://ehf-ui-trunk.ci.dev.icw.int:8080/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 *  {@code <cui:formGroup />}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'form-group'</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.LAYOUT_FORMGROUP_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class FormGroupComponent extends AbstractLayoutComponent {

    /**
     *
     */
    public FormGroupComponent() {
        super();
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(super.getStyleClass());
    }
}
