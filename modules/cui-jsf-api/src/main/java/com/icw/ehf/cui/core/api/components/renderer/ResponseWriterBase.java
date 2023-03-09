package com.icw.ehf.cui.core.api.components.renderer;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.tools.string.MoreStrings;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Base class for providing convenience methods on {@link ResponseWriter} without
 * {@link UIComponent} context, compared to {@link DecoratingResponseWriter}. It defines a fluent
 * api.
 */
@RequiredArgsConstructor
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class ResponseWriterBase extends ResponseWriterWrapper {

    @Getter
    @NonNull
    private final ResponseWriter wrapped;

    /**
     * @param node
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withStartElement(final Node node) throws IOException {
        startElement(node.getContent(), null);
        return this;
    }

    /**
     * @param node
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withEndElement(final Node node) throws IOException {
        endElement(node.getContent());
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withStyleClass(final String styleClass) throws IOException {
        if (styleClass != null && !styleClass.isEmpty()) {
            writeAttribute(AttributeName.CLASS.getContent(), styleClass,
                    AttributeName.CLASS.getContent());
        }
        return this;
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withStyleClass(final StyleClassProvider styleClass)
        throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds the "class" attribute to the current dom-element.
     *
     * @param styleClass
     *            to be set
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withStyleClass(final StyleClassBuilder styleClass)
        throws IOException {
        return withStyleClass(styleClass.getStyleClass());
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName
     *            must not be null
     * @param attributeValue
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName,
            final String attributeValue)
        throws IOException {
        writeAttribute(attributeName.getContent(), attributeValue, attributeName.getContent());
        return this;
    }

    /**
     * Adds an attribute to the current dom-element.
     *
     * @param attributeName
     *            must not be null
     * @param attributeValue
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withAttribute(final AttributeName attributeName,
            final AttributeValue attributeValue)
        throws IOException {
        return withAttribute(attributeName, attributeValue.getContent());
    }

    /**
     * Adds the title attribute to the current dom-element.
     *
     * @param title
     *            if it is null or empty, not title will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withAttributeTitle(final String title) throws IOException {
        if (!MoreStrings.isEmpty(title)) {
            withAttribute(AttributeName.TITLE, title);
        }
        return this;
    }

    /**
     * Adds the style attribute to the current dom-element.
     *
     * @param style
     *            if it is null or empty, not style-attribute will be written
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withAttributeStyle(final String style) throws IOException {
        if (!MoreStrings.isEmpty(style)) {
            withAttribute(AttributeName.STYLE, style);
        }
        return this;
    }

    /**
     * Writes the given pass-through-attributes. In case there are no
     * pass-through-attributes nothing will happen.
     *
     * @param facesContext
     *
     * @param passThroughAttributes the attributes to render
     * @return the {@link DecoratingResponseWriter}
     * @throws IOException
     */
    public ResponseWriterBase withPassThroughAttributes(final FacesContext facesContext,
            final Map<String, Object> passThroughAttributes)
        throws IOException {
        if (null != passThroughAttributes && !passThroughAttributes.isEmpty()) {
            for (final Entry<String, Object> entry : passThroughAttributes.entrySet()) {
                getWrapped().writeAttribute(entry.getKey(), resolveValue(facesContext, entry.getValue()), null);
            }
        }
        return this;
    }

    private static Object resolveValue(final FacesContext facesContext, final Object value) {
        if (value instanceof ValueExpression) {
            return ((ValueExpression) value).getValue(facesContext.getELContext());
        }
        return value;
    }

}
