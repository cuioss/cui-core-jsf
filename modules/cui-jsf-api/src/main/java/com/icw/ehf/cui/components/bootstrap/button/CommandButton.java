package com.icw.ehf.cui.components.bootstrap.button;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PreRenderComponentEvent;

import org.omnifaces.util.State;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.button.support.ButtonSize;
import com.icw.ehf.cui.components.bootstrap.button.support.ButtonState;
import com.icw.ehf.cui.core.api.components.base.BaseCuiCommandButton;
import com.icw.ehf.cui.core.api.components.css.AlignHolder;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProviderImpl;
import com.icw.ehf.cui.core.api.components.partial.ContextSizeProvider;
import com.icw.ehf.cui.core.api.components.partial.ContextStateProvider;
import com.icw.ehf.cui.core.api.components.partial.IconAlignProvider;
import com.icw.ehf.cui.core.api.components.partial.IconProvider;
import com.icw.ehf.cui.core.api.components.partial.LabelProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;

import lombok.experimental.Delegate;

/**
 * <p>
 * An extension to h:commandButton that conforms to Bootstrap styling and incorporates the display
 * of icons. Caution: do not use the value attribute but the corresponding labelKey / labelValue.
 * The same goes for the title element: use titleKey or titleValue.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link IconProvider}</li>
 * <li>{@link IconAlignProvider}</li>
 * <li>{@link LabelProvider}</li>
 * <li>All attributes from {@link HtmlCommandButton}</li>
 * <li>keyBinding: The key-binding for this button, aka keyboard shortcut. The key will be bound as
 * onClickHandler. Caution: The implementor must ensure that there is only one button for the same
 * type existent per page, otherwise the behavior is non-deterministic.</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.COMMAND_BUTTON_COMPONENT)
@ListenerFor(systemEventClass = PreRenderComponentEvent.class)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class CommandButton extends BaseCuiCommandButton {

    private static final String KEY_BINDING_KEY = "keyBinding";

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final IconProvider iconProvider;

    @Delegate
    private final IconAlignProvider iconAlignProvider;

    @Delegate
    private final LabelProvider labelProvider;

    private final ComponentStyleClassProvider styleClassProvider;

    private final State state;

    /**
     * Constructor.
     */
    public CommandButton() {
        super();
        super.setRendererType(BootstrapFamily.COMMAND_BUTTON_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        iconProvider = new IconProvider(this);
        labelProvider = new LabelProvider(this);
        styleClassProvider = new ComponentStyleClassProviderImpl(this);
        iconAlignProvider = new IconAlignProvider(this);
        state = new State(getStateHelper());
    }

    @Override
    public String getStyleClass() {
        return CssBootstrap.BUTTON.getStyleClassBuilder()
                .append(ButtonState.getForContextState(contextStateProvider.getState()))
                .append(ButtonSize.getForContextSize(contextSizeProvider.resolveContextSize()))
                .append(styleClassProvider)
                .getStyleClass();
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PreRenderComponentEvent && !isEmpty(getKeyBinding())) {
            getPassThroughAttributes().put(AttributeValue.CUI_CLICK_BINDING.getContent(), getKeyBinding());
        }
        super.processEvent(event);
    }

    @Override
    public void setStyleClass(final String styleClass) {
        styleClassProvider.setStyleClass(styleClass);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public void setValue(final Object value) {
        throw new IllegalArgumentException("element 'value' not permitted, use labelKey or labelValue instead");
    }

    @Override
    public Object getValue() {
        return labelProvider.resolveLabel();
    }

    /**
     * @return boolean indicating whether to display an icon on the right side of the button text
     */
    public boolean isDisplayIconRight() {
        return iconProvider.isIconSet() && AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @return boolean indicating whether to display an icon on the left side of the button text
     */
    public boolean isDisplayIconLeft() {
        return iconProvider.isIconSet() && !AlignHolder.RIGHT.equals(iconAlignProvider.resolveIconAlign());
    }

    /**
     * @param keyBinding
     */
    public void setKeyBinding(final String keyBinding) {
        state.put(KEY_BINDING_KEY, keyBinding);
    }

    /**
     * @return the keyBinding
     */
    public String getKeyBinding() {
        return state.get(KEY_BINDING_KEY);
    }

    /**
     * Factory method to instantiate a concrete instance of
     * {@link CommandButton}, usually used if you programmatically add it
     * as a child.
     *
     * @param facesContext
     *            must not be null
     * @return concrete instance of {@link CommandButton}
     */
    public static final CommandButton create(final FacesContext facesContext) {
        return (CommandButton) facesContext.getApplication()
                .createComponent(BootstrapFamily.COMMAND_BUTTON_COMPONENT);
    }
}
