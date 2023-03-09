package com.icw.ehf.cui.components.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;

import de.cuioss.test.jsf.component.AbstractUiComponentTest;

class InputGroupComponentTest extends AbstractUiComponentTest<InputGroupComponent> {

    @Test
    void shouldProvideCorrectStyleClass() {
        final var component = anyComponent();
        final var expected = CssBootstrap.INPUT_GROUP.getStyleClass();
        assertEquals(expected, component.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LAYOUT_RENDERER, anyComponent().getRendererType());
    }
}
