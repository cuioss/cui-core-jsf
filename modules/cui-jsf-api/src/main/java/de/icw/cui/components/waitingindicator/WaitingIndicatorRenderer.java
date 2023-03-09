package de.icw.cui.components.waitingindicator;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * Renderer for {@linkplain WaitingIndicatorComponent}.
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.WAITING_INDICATOR_RENDERER)
public class WaitingIndicatorRenderer extends BaseDecoratorRenderer<WaitingIndicatorComponent> {

    /**
     *
     */
    public WaitingIndicatorRenderer() {
        super(true);
    }

    @Override
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<WaitingIndicatorComponent> writer,
            WaitingIndicatorComponent component)
        throws IOException {

        // Write element
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.getStyleClass());
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        writer.withStartElement(Node.DIV);
        writer.withStyleClass("waiting-indicator waiting-indicator-size-" + component.getSize());

        for (var i = 1; i <= 5; i++) {
            writer.withStartElement(Node.DIV);
            writer.withStyleClass("item-" + i + " item-size-" + component.getSize());
            writer.withEndElement(Node.DIV);
            writer.write(" ");
        }

        writer.withEndElement(Node.DIV);

        writer.withEndElement(Node.DIV);
    }

}
