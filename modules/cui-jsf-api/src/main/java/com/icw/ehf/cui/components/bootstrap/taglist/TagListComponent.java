package com.icw.ehf.cui.components.bootstrap.taglist;

import java.util.Collection;

import javax.faces.component.FacesComponent;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.tag.support.TagHelper;
import com.icw.ehf.cui.core.api.components.base.BaseCuiNamingContainer;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.ContentProvider;
import com.icw.ehf.cui.core.api.components.partial.ContextSizeProvider;
import com.icw.ehf.cui.core.api.components.partial.ContextStateProvider;
import com.icw.ehf.cui.core.api.components.partial.StyleAttributeProvider;

import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Tag similar to a JIRA Tag. The tag is rendered within a span with
 * the according classes. The content and title are resolved using the cui
 * standard label-resolving mechanism.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>value: the value of the component. It is supposed to be either a single
 * {@link ConceptKeyType}, {@link String} or a {@link Collection} of {@link ConceptKeyType} or
 * {@link String}</li>
 * <li>contentEscape: indicating whether the content of the tags need to be
 * escaped. defaults to <code>true</code></li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:tagList value="#{bean.tags}" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.TAG_LIST_COMPONENT)
public class TagListComponent extends BaseCuiNamingContainer {

    /** Default exception message for an invalid value. */
    public static final String INVALID_VALUE_EXCEPTION =
        "Neither java.util.Collection, ConceptKeyType, nor String found for the value-attribute: %s";

    private static final String TAG_LIST_KEY = "value";

    /** Partial elements. */
    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    /** Used for accessing the contentEscape attribute. */
    private final ContentProvider contentProvider;

    /**
     *
     */
    public TagListComponent() {
        super();
        super.setRendererType(BootstrapFamily.TAG_LIST_COMPONENT_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        contentProvider = new ContentProvider(this);
    }

    /**
     * @return the value of the component. It is supposed to be either a single
     *         {@link ConceptKeyType} or a {@link Collection} of {@link ConceptKeyType}
     */
    public Object getValue() {
        return getStateHelper().eval(TAG_LIST_KEY);
    }

    /**
     * @param tagList
     *            the value of the component. It is supposed to be either a
     *            single {@link ConceptKeyType} or a {@link Collection} of
     *            {@link ConceptKeyType}
     */
    public void setValue(final Object tagList) {
        getStateHelper().put(TAG_LIST_KEY, tagList);
    }

    /**
     * @return boolean indicating whether the content of the tags need to be
     *         escaped. defaults to true
     */
    public boolean getContentEscape() {
        return contentProvider.getContentEscape();
    }

    /**
     * @param contentEscape
     *            to set.
     */
    public void setContentEscape(final boolean contentEscape) {
        contentProvider.setContentEscape(contentEscape);
    }

    @Override
    public boolean isRendered() {
        return super.isRendered() && !TagHelper.getValueAsSet(getValue()).isEmpty();
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
