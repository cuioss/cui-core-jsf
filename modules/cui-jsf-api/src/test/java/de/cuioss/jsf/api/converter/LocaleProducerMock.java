/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.converter;

import java.io.Serializable;
import java.util.Locale;

import de.cuioss.jsf.api.application.locale.LocaleProducer;
import de.cuioss.jsf.api.application.locale.LocaleProducerImpl;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Mock implementation of {@link LocaleProducer} It can be easily configured as
 * bean by using {@link JsfTestConfiguration}
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode
@ToString
public class LocaleProducerMock implements LocaleProducer, BeanConfigurator, Serializable {

    private static final long serialVersionUID = 2728554180387920399L;

    @Setter
    private Locale locale = Locale.ENGLISH;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, LocaleProducerImpl.BEAN_NAME);

    }
}
