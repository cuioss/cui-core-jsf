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
package de.cuioss.jsf.api.components.partial;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.CuiMockSearchExpressionHandler;

@VerifyComponentProperties(of = { "process", "update" })
class AjaxProviderTest extends AbstractPartialComponentTest {

    private static final String DEFAULT_UPDATE_KEY = AjaxProvider.DATA_CUI_AJAX + AjaxProvider.UPDATE_KEY;
    private static final String DEFAULT_PROCESS_KEY = AjaxProvider.DATA_CUI_AJAX + AjaxProvider.PROCESS_KEY;

    @SuppressWarnings("unused")
    @Test
    void shouldFailWithNullConstructor() {
        assertThrows(NullPointerException.class, () -> new AjaxProvider(null));
    }

    @Test
    void shouldProvideDefaults() {
        var underTest = anyComponent();
        assertNull(underTest.getUpdate());
        assertNull(underTest.getProcess());

        final var form = "@form";
        assertEquals(form, anyComponent().ajaxDefaultProcess(form).getProcess());
        assertEquals(form, anyComponent().ajaxDefaultUpdate(form).getUpdate());

    }

    @Test
    void shoulHandleEmptyUpdateAndProcess() {
        var any = anyComponent();
        assertTrue(any.resolveAjaxAttributesAsMap(any).isEmpty());
    }

    @Test
    void shoulHandleUpdateAndProcess() {
        var any = anyComponent();
        final var someId = "someId";
        any.setUpdate(someId);
        any.setProcess(someId);

        CuiMockSearchExpressionHandler.retrieve(getFacesContext()).setResolvedClientIds(mutableList(someId));

        var ajaxAttributes = any.resolveAjaxAttributesAsMap(any);
        assertEquals(2, ajaxAttributes.size());

        assertTrue(ajaxAttributes.containsKey(DEFAULT_UPDATE_KEY));
        assertEquals(someId, ajaxAttributes.get(DEFAULT_UPDATE_KEY));

        assertTrue(ajaxAttributes.containsKey(DEFAULT_PROCESS_KEY));
        assertEquals(someId, ajaxAttributes.get(DEFAULT_PROCESS_KEY));
    }

    @Test
    void shoulIgnoreEmptyUpdateAndProcess() {
        var any = anyComponent();
        final var someId = "someId";
        any.setUpdate("");
        any.setProcess(someId);

        CuiMockSearchExpressionHandler.retrieve(getFacesContext()).setResolvedClientIds(Collections.emptyList());

        var ajaxAttributes = any.resolveAjaxAttributesAsMap(any);
        assertTrue(ajaxAttributes.isEmpty());
    }

    @Test
    void shouldHandleCustomDataPrefix() {
        var any = anyComponent();
        var prefix = "huch-";
        any.ajaxDataPrefix(prefix);
        final var someId = "someId";
        any.setUpdate(someId);
        any.setProcess(someId);

        CuiMockSearchExpressionHandler.retrieve(getFacesContext()).setResolvedClientIds(mutableList(someId));

        var ajaxAttributes = any.resolveAjaxAttributesAsMap(any);
        assertEquals(2, ajaxAttributes.size());

        var updateKey = prefix + AjaxProvider.UPDATE_KEY;
        assertTrue(ajaxAttributes.containsKey(updateKey));
        assertEquals(someId, ajaxAttributes.get(updateKey));

        var processKey = prefix + AjaxProvider.PROCESS_KEY;
        assertTrue(ajaxAttributes.containsKey(processKey));
        assertEquals(someId, ajaxAttributes.get(processKey));
    }
}
