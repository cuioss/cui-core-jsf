package de.icw.cui.core.api.components.model.lazyloading;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.icw.ehf.cui.core.api.components.css.ContextState;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.icw.cui.components.lazyloading.LazyLoadingComponent;

/**
 * A model for the {@link LazyLoadingComponent}.
 */
public interface LazyLoadingModel extends ActionListener, Serializable {

    /**
     * @return a {@link IDisplayNameProvider} if a notification box should be rendered, otherwise
     *         null.
     */
    IDisplayNameProvider<?> getNotificationBoxValue();

    /**
     * @return the state of the notification box, see also {@link #getNotificationBoxValue()}
     */
    ContextState getNotificationBoxState();

    /**
     * @return true, if the content (the children) should be rendered, otherwise false.
     */
    boolean isRenderContent();

    /**
     * @return true, if the content to be rendered is already retrieved, otherwise false.
     *         False will trigger a lazy loading round trip which will call
     *         {@link #processAction(ActionEvent)} when finished.
     */
    boolean isInitialized();
}
