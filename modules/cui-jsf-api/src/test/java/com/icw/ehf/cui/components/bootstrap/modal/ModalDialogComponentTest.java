package com.icw.ehf.cui.components.bootstrap.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "closable", "footerKey", "footerValue", "headerKey", "headerValue", "size", "state",
    "style", "animation" })
class ModalDialogComponentTest extends AbstractComponentTest<ModalDialogComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldHandleStyleClass() {
        var component = anyComponent();
        assertEquals("modal modal-default", component.getStyleClass());
        component.setSize("fluid");
        assertEquals("modal modal-default", component.getStyleClass());
        component.setStyleClass("someStyleClass");
        assertEquals("modal modal-default someStyleClass", component.getStyleClass());
        component.setAnimation("fade");
        assertEquals("modal modal-default fade someStyleClass", component.getStyleClass());
    }

    @Test
    void shouldFailOnUnsetId() {
        assertThrows(IllegalArgumentException.class, () -> {
            anyComponent().resolveDialogId();
        });
    }

    @Test
    void shouldResolveToId() {
        var component = anyComponent();
        component.setId("dialogid");
        assertEquals("dialogid", component.resolveDialogId());
    }
}
