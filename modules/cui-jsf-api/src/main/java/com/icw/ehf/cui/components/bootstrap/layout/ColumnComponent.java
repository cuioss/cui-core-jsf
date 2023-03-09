package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.common.partial.ColumnProvider;
import com.icw.ehf.cui.core.api.components.base.BaseCuiPanel;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.util.ComponentUtility;

import lombok.experimental.Delegate;

/**
 * <p>
 * Renders a bootstrap conform div with the styleClass 'col-sm-' size, resulting
 * in a column.
 * </p>
 * <p>
 * The layout relies completely on the grid-system of twitter-bootstrap, see
 * <a href="http://getbootstrap.com/css/#grid">Bootstrap Documentation</a>
 * </p>
 * <ul>
 * <li>Columns must always reside within a row as a direct child.</li>
 * <li>The size and offset of a column is always defined in 1/12 steps. A row is
 * limited to 12. If the columnSize (and offsets) are more than 12 the surplus
 * columns will be rendered in the next line.</li>
 * <li>Offsets are a convenient way to define a gap between two columns</li>
 * <li>The size definitions of the components are always related to md. In case
 * you want to change the behavior you need to add additional styleClasses, see
 * example.</li>
 * </ul>
 * <p>
 * More information and examples can be found in the <a href=
 * "http://ehf-ui-trunk.ci.dev.icw.int:8080/cui-reference-documentation/faces/pages/documentation/cui_components/demo/layout.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * {@code
<cui:row>
        <cui:column size="4">
            <span>Size="4"</span>
        </cui:column>
        <cui:column size="2" >
            <span>Size="2"</span>
        </cui:column>
        <cui:column size="4" offsetSize="2">
            <span>Size="3", Offset="4"</span>
        </cui:column>
</cui:row>
}
 * </pre>
 *
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is 'col-md-' + size attribute</li>
 * <li>The offset css class is 'col-md-offset-' + offsetSize attribute</li>
 * </ul>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link BaseCuiPanel}</li>
 * <li>{@link ColumnProvider}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.LAYOUT_COLUMN_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class ColumnComponent extends AbstractLayoutComponent {

    @Delegate
    private final ColumnProvider columnProvider;

    /**
     *
     */
    public ColumnComponent() {
        super();
        columnProvider = new ColumnProvider(this);
        super.setRendererType(BootstrapFamily.LAYOUT_RENDERER);
    }

    @Override
    public StyleClassBuilder resolveStyleClass() {
        return columnProvider.resolveColumnCss().append(getStyleClass());
    }

    /**
     * Shorthand for creating a new instance of {@link ColumnComponent}
     *
     * @param facesContext must not be null
     * @return a new instance of {@link ColumnComponent}
     */
    public static ColumnComponent createComponent(final FacesContext facesContext) {
        return ComponentUtility.createComponent(facesContext, BootstrapFamily.LAYOUT_COLUMN_COMPONENT);
    }
}
