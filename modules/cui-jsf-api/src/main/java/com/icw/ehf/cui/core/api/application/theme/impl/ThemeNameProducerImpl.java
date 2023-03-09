package com.icw.ehf.cui.core.api.application.theme.impl;

import java.io.Serializable;

import com.icw.ehf.cui.core.api.application.theme.ThemeConfiguration;
import com.icw.ehf.cui.core.api.application.theme.ThemeNameProducer;
import com.icw.ehf.cui.core.api.application.theme.accessor.ThemeConfigurationAccessor;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Simple implementation of {@link ThemeNameProducer} that will always return the default theme
 * derived from {@link ThemeConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeNameProducerImpl implements ThemeNameProducer, Serializable {

    private static final long serialVersionUID = -8491235324464722495L;

    private final ThemeConfigurationAccessor themeConfiguration = new ThemeConfigurationAccessor();

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "themeNameProducer";

    @Override
    public String getTheme() {
        return themeConfiguration.getValue().getDefaultTheme();
    }

}
