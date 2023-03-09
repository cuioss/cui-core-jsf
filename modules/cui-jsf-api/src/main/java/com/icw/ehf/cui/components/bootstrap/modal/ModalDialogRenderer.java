package com.icw.ehf.cui.components.bootstrap.modal;

import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.MODAL_DIALOG_BODY;
import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.MODAL_DIALOG_FOOTER;
import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.MODAL_DIALOG_FOOTER_TEXT;
import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.MODAL_DIALOG_HEADER;
import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.MODAL_DIALOG_TITLE;
import static com.icw.ehf.cui.core.api.components.html.AttributeName.DATA_BACKDROP;
import static com.icw.ehf.cui.core.api.components.html.AttributeName.DATA_MODAL_ID;
import static com.icw.ehf.cui.core.api.components.html.AttributeName.ROLE;
import static com.icw.ehf.cui.core.api.components.html.AttributeName.TABINDEX;
import static com.icw.ehf.cui.core.api.components.html.AttributeValue.ROLE_DIALOG;
import static com.icw.ehf.cui.core.api.components.html.Node.DIV;
import static com.icw.ehf.cui.core.api.components.html.Node.H4;
import static com.icw.ehf.cui.core.api.components.html.Node.SPAN;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.common.HtmlSnippetRenderer;
import com.icw.ehf.cui.components.bootstrap.modal.support.ModalDialogSize;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * Renders a bootstrap-conform modal dialog.
 * <h2>Opening and Closing</h2>
 * <p>
 * The Dialog renders the attribute {@link AttributeName#DATA_MODAL_ID} with the dialog-id
 * </p>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class ModalDialogRenderer extends BaseDecoratorRenderer<ModalDialogComponent> {

    @SuppressWarnings("javadoc")
    public ModalDialogRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer,
            final ModalDialogComponent component)
        throws IOException {
        // wrapper element
        writer.withStartElement(DIV).withClientIdIfNecessary().withStyleClass(component.getStyleClass())
                .withAttributeStyle(component.getStyle()).withPassThroughAttributes()
                .withAttribute(DATA_MODAL_ID, component.resolveDialogId())
                .withAttribute(TABINDEX, "-1")
                .withAttribute(ROLE, ROLE_DIALOG);

        // if dialog should not be closable data-backdrop="static" must be available
        if (!component.isClosable()) {
            writer.withAttribute(DATA_BACKDROP, "static");
        }
        // Inner Wrapper
        writer.withStartElement(DIV)
                .withStyleClass(CssBootstrap.MODAL_DIALOG.getStyleClassBuilder()
                        .append(ModalDialogSize.getFromString(component.getSize())))
                .withAttribute(ROLE, "document");
        // Modal Content
        writer.withStartElement(DIV).withStyleClass(CssBootstrap.MODAL_CONTENT);

        writeHeader(context, writer, component);

        // Start Body
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_BODY);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<ModalDialogComponent> writer,
            final ModalDialogComponent component)
        throws IOException {
        // Body End
        writer.withEndElement(DIV);
        writeFooter(context, writer, component);
        // End Modal Content
        writer.withEndElement(DIV);
        // End Inner wrapper
        writer.withEndElement(DIV);
        // End wrapper
        writer.withEndElement(DIV);
    }

    private static void writeHeader(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer,
            final ModalDialogComponent component)
        throws IOException {
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_HEADER);
        if (component.isClosable()) {
            HtmlSnippetRenderer.renderCloseButton(writer, "modal");
        }

        if (component.shouldRenderHeader()) {
            if (component.shouldRenderHeaderFacet()) {
                component.getHeaderFacet().encodeAll(context);
            } else {
                writer.withStartElement(H4).withStyleClass(MODAL_DIALOG_TITLE)
                        .withTextContent(component.resolveHeader(), component.isHeaderEscape()).withEndElement(H4);
            }
        }
        writer.withEndElement(DIV);
    }

    private static void writeFooter(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer,
            final ModalDialogComponent component)
        throws IOException {
        if (!component.shouldRenderFooter()) {
            return;
        }
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_FOOTER);
        if (component.shouldRenderFooterFacet()) {
            component.getFooterFacet().encodeAll(context);
        } else {
            writer.withStartElement(SPAN).withStyleClass(MODAL_DIALOG_FOOTER_TEXT)
                    .withTextContent(component.resolveFooter(), component.isFooterEscape()).withEndElement(SPAN);
        }
        writer.withEndElement(DIV);
    }
}
