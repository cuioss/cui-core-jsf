package com.icw.ehf.cui.core.api.application.message;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.common.accessor.ConverterAccessor;
import com.icw.ehf.cui.core.api.components.support.DummyComponent;

import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Decorator for {@link MessageProducer} to log and display
 * {@link ResultObject#getResultDetail()}.
 *
 * @author Matthias Walliczek
 */
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "messageProducer" })
@ToString(exclude = { "messageProducer" })
public class DisplayNameProviderMessageProducer implements Serializable {

    private static final CuiLogger log = new CuiLogger(DisplayNameProviderMessageProducer.class);

    private static final String SILENT_ERROR_LOG = "Error occured but was handeled silent.";

    private static final UIComponent DUMMY = new DummyComponent();

    private static final long serialVersionUID = 3367432860164772689L;

    private static final String NO_FITTING_CONVERTER_REGISTERED =
        "No fitting converter for [%s] class registered.";

    @NonNull
    private final MessageProducer messageProducer;

    /**
     * Create and add a global faces message, use resolved text from detail
     *
     * @param requestResultObject {@linkplain ResultObject } must not be {@code null}
     */
    @SuppressWarnings("squid:S3655")
    public void showAsGlobalMessageAndLog(final ResultObject<?> requestResultObject) {
        FacesMessage.Severity severity;

        switch (requestResultObject.getState()) {
            case ERROR:
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    log.error(SILENT_ERROR_LOG,
                            requestResultObject.getResultDetail().get().getCause().get());
                }
                severity = FacesMessage.SEVERITY_ERROR;
                break;
            case WARNING:
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    log.warn(SILENT_ERROR_LOG,
                            requestResultObject.getResultDetail().get().getCause().get());
                }
                severity = FacesMessage.SEVERITY_WARN;
                break;
            case INFO:
                if (requestResultObject.getResultDetail().get().getCause().isPresent()) {
                    log.info(SILENT_ERROR_LOG,
                            requestResultObject.getResultDetail().get().getCause().get());
                }
                severity = FacesMessage.SEVERITY_INFO;
                break;
            case VALID:
                severity = FacesMessage.SEVERITY_INFO;
                break;
            default:
                throw new UnsupportedOperationException(
                        "No resolving defined for -> " + requestResultObject.getState(),
                        requestResultObject.getResultDetail().get().getCause().orElse(null));
        }

        if (requestResultObject.getResultDetail().isPresent()) {
            showAsGlobalMessage(requestResultObject.getResultDetail().get().getDetail(), severity);
        }
    }

    /**
     * Create and add a global faces message, use resolved text from content
     *
     * @param content {@linkplain IDisplayNameProvider} must not be {@code null}
     * @param severity {@linkplain FacesMessage#getSeverity()} must not be {@code null}
     */
    public void showAsGlobalMessage(final IDisplayNameProvider<?> content,
            final FacesMessage.Severity severity) {

        final var message = resolve(requireNonNull(content, "content"));
        messageProducer.addGlobalMessage(message, severity);
    }

    /**
     * Resolve a {@linkplain IDisplayNameProvider} via matching converter.
     *
     * @param content
     * @return the message string
     */
    public static String resolve(final IDisplayNameProvider<?> content) {
        final Class<?> targetType = content.getClass();
        final var converterAccessor = new ConverterAccessor();
        converterAccessor.setTargetClass(targetType);
        final var converter = converterAccessor.getValue();
        if (null == converter) {
            throw new IllegalStateException(
                    String.format(NO_FITTING_CONVERTER_REGISTERED, targetType.getName()));
        }
        return converter.getAsString(FacesContext.getCurrentInstance(), DUMMY, content);
    }
}
