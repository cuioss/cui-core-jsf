package com.icw.ehf.cui.components.bootstrap.tag;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.el.MethodExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssCuiBootstrap;
import com.icw.ehf.cui.components.bootstrap.button.CloseCommandButton;
import com.icw.ehf.cui.core.api.components.JsfHtmlComponent;
import com.icw.ehf.cui.core.api.components.base.BaseCuiInputComponent;
import com.icw.ehf.cui.core.api.components.events.ModelPayloadEvent;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;
import com.icw.ehf.cui.core.api.components.partial.ContentProvider;
import com.icw.ehf.cui.core.api.components.partial.ContextSizeProvider;
import com.icw.ehf.cui.core.api.components.partial.ContextStateProvider;
import com.icw.ehf.cui.core.api.components.partial.ModelProvider;
import com.icw.ehf.cui.core.api.components.partial.StyleAttributeProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProviderImpl;
import com.icw.ehf.cui.core.api.components.util.CuiState;

import de.cuioss.tools.logging.CuiLogger;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Tag similar to a JIRA Tag. The tag is rendered within a span with
 * the according classes. The content and title are resolved using the cui
 * standard label-resolving mechanism.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>{@link ContentProvider}</li>
 * <li>{@link ModelProvider}</li>
 * <li>disposable: Indicates whether the tag can be disposed.
 * <li>disposeListener: Method expression to listen to dispose events. The
 * listener must be in the form of
 * {@code void methodName(com.icw.ehf.cui.core.api.components.events.ModelPayloadEvent)}. In case
 * you set it to <code>true</code> you must provide the according model. If not it falls back to
 * #contentValue</li></li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:tag contentValue="Some Value" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.TAG_COMPONENT)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "javascript.enabler", name = "enabler.tagdispose.js",
        target = "head")
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class TagComponent extends BaseCuiInputComponent
        implements TitleProvider, ClientBehaviorHolder, ValueChangeListener {

    private static final CuiLogger log = new CuiLogger(TagComponent.class);

    /**
     * Suffix for clientId
     */
    static final String DISPOSE_INFO_SUFFIX = "disposedInfo";

    /**
     * ClientBehaviorHolder.
     */
    private static final String CLICK = "click";

    private static final List<String> EVENT_NAMES = immutableList(CLICK);

    /**
     * Component specific Keys.
     */
    private static final String DISPOSABLE_KEY = "disposable";

    /**
     * Partial elements.
     */
    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContentProvider contentProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    @Delegate
    private final ModelProvider modelProvider;

    private final CuiState state;

    /**
     * TagComponent constructor
     */
    public TagComponent() {
        super();
        super.setRendererType(BootstrapFamily.TAG_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contentProvider = new ContentProvider(this);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        modelProvider = new ModelProvider(this);
        state = new CuiState(getStateHelper());
    }

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PostAddToViewEvent) {
            accessCloseButton();
            accessDisposeValueHolder();
        }
        super.processEvent(event);
    }

    private Optional<UIInput> accessDisposeValueHolder() {

        if (!isDisposable()) {
            return Optional.empty();
        }

        final var hiddenInput = getChildren().stream()
                .filter(component -> component instanceof UIInput)
                .findFirst();

        if (hiddenInput.isPresent()) {
            return Optional.of((UIInput) hiddenInput.get());
        }

        final UIInput newCreatedHiddenInput = JsfHtmlComponent.HIDDEN.component(getFacesContext());

        newCreatedHiddenInput.setId(DISPOSE_INFO_SUFFIX);
        newCreatedHiddenInput.setValue("false");
        newCreatedHiddenInput.addValueChangeListener(this);
        getChildren().add(newCreatedHiddenInput);

        return Optional.of(newCreatedHiddenInput);
    }

    private Optional<CloseCommandButton> accessCloseButton() {

        if (!isDisposable()) {
            return Optional.empty();
        }

        final var found = getChildren().stream()
                .filter(component -> component instanceof CloseCommandButton)
                .findFirst();

        if (found.isPresent()) {
            return Optional.of((CloseCommandButton) found.get());
        }
        final var button = CloseCommandButton.createComponent(getFacesContext());
        button.addClientBehavior(CLICK, new DisposeBehavior());
        button.setId("closeButton");
        button.setStyleClass(CssCuiBootstrap.TAG_DISPOSE_BUTTON.getStyleClass());
        getChildren().add(button);

        return Optional.of(button);
    }

    @Override
    public void addClientBehavior(final String eventName, final ClientBehavior behavior) {
        final var button = accessCloseButton();
        log.debug("Adding client Behavior name='{}', type = behavior='{}'", eventName, behavior);
        if (button.isPresent()) {
            button.get().addClientBehavior(eventName, behavior);
        } else {
            log.warn(
                    "Invalid configuration: In order to use a client-behavior you need to set disposable=true, clientid='{}'",
                    getClientId());
        }
    }

    /**
     * @return the disposable
     */
    public boolean isDisposable() {
        return state.getBoolean(DISPOSABLE_KEY, false);
    }

    /**
     * @param disposable the disposable to set
     */
    public void setDisposable(final boolean disposable) {
        state.put(DISPOSABLE_KEY, disposable);
    }

    /**
     * @return boolean indicating whether the component has been disposed.
     *         Defaults to <code>false</code>
     */
    private boolean isDisposed() {
        final var inputOption = accessDisposeValueHolder();
        return inputOption.map(uiInput -> Boolean.valueOf((String) uiInput.getValue())).orElse(false);
    }

    /**
     * In addition to {@link UIComponent#isRendered()} it checks whether the
     * component was disposed: Disposed tags should not be rendered.
     */
    @Override
    public boolean isRendered() {
        return super.isRendered() && !isDisposed();
    }

    /**
     * ATTENTION: Evaluation the MethodExpression may already trigger executing the method!
     *
     * @return the disposeListener
     */
    public MethodExpression getDisposeListener() {
        return (MethodExpression) getStateHelper().eval(TagHandler.DISPOSE_LISTENER_NAME);
    }

    /**
     * @param disposeListener the disposeListener to set
     */
    public void setDisposeListener(final MethodExpression disposeListener) {
        getStateHelper().put(TagHandler.DISPOSE_LISTENER_NAME, disposeListener);
    }

    /**
     * @return the eventNames
     */
    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return CLICK;
    }

    @Override
    public void broadcast(final FacesEvent event) {
        super.broadcast(event);
        final var disposeMethod = getDisposeListener();
        if (event instanceof ModelPayloadEvent && disposeMethod != null) {
            disposeMethod.invoke(getFacesContext().getELContext(), new Object[] { event });
        }
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }

    @Override
    public void processValueChange(final ValueChangeEvent event) throws AbortProcessingException {
        var payload = getModel();
        if (null == payload) {
            // Fallback to resolved content
            payload = resolveContent();
        }
        queueEvent(new ModelPayloadEvent(this, payload));
    }
}
