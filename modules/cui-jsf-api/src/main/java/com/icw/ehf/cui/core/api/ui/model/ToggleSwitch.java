package com.icw.ehf.cui.core.api.ui.model;

import java.io.Serializable;

import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ToggleSwitch as holder of toggled state
 *
 * @author i000576 (Eugen Fischer)
 */
@ToString
@EqualsAndHashCode
public class ToggleSwitch implements Serializable {

    private static final long serialVersionUID = -2220680284073352864L;

    @Setter
    @Getter
    private boolean toggled;

    /**
     * Constructor. Sets {@link #isToggled()} to false.
     */
    public ToggleSwitch() {
        toggled = false;
    }

    /**
     * Constructor.
     *
     * @param toggled
     */
    public ToggleSwitch(final Boolean toggled) {
        if (null == toggled) {
            this.toggled = false;
        } else {
            this.toggled = toggled;
        }
    }

    /**
     * Toggles, whether the state
     *
     * @param actionEvent ActionEvent
     */
    public void toggle(final ActionEvent actionEvent) {
        toggle();
    }

    /**
     * toggle the state
     */
    public void toggle() {
        toggled = !toggled;
    }

    /**
     * Listener for resetting the toggle
     *
     * @param event
     */
    @SuppressWarnings("squid:S1172") // defined by API (Unused parameter)
    public void resetToggle(final ComponentSystemEvent event) {
        toggled = false;
    }
}
