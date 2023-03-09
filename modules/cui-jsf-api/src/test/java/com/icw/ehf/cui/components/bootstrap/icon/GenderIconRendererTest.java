package com.icw.ehf.cui.components.bootstrap.icon;

import static com.icw.ehf.cui.components.bootstrap.icon.GenderIconComponentTest.GENDER_MALE_TITLE;
import static com.icw.ehf.cui.components.bootstrap.icon.GenderIconComponentTest.GENDER_UNKNOWN_TITLE;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.HtmlTreeBuilder;
import com.icw.ehf.cui.core.api.components.html.Node;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.model.Gender;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class GenderIconRendererTest extends AbstractComponentRendererTest<IconRenderer> {

    private static final String ICON_PREFIX = "cui-icon ";

    private static final String GENDER_UNKNOWN_CSS = ICON_PREFIX + Gender.UNKNOWN.getCssClass();

    private static final String GENDER_MALE_CSS = ICON_PREFIX + Gender.MALE.getCssClass();

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(GENDER_UNKNOWN_CSS)
                .withAttribute(AttributeName.TITLE, GENDER_UNKNOWN_TITLE);
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderGenderIcon() {
        var component = new GenderIconComponent();
        component.setGender(Gender.MALE);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(GENDER_MALE_CSS)
                .withAttribute(AttributeName.TITLE, GENDER_MALE_TITLE);
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new GenderIconComponent();
    }
}
