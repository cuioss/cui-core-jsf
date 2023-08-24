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
package de.cuioss.jsf.api.components.decorator;

import de.cuioss.jsf.api.components.util.ComponentModifier;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class ParentDecoratorDummy extends AbstractParentDecorator {

    @Getter
    @Setter
    private boolean decorateCalled = false;

    @Override
    public void decorate(final ComponentModifier parent) {
        decorateCalled = true;

    }

    @Override
    public String getFamily() {
        return "de.cuioss.jsf.api.components.decorator.ParentDecoratorDummy";
    }

}
