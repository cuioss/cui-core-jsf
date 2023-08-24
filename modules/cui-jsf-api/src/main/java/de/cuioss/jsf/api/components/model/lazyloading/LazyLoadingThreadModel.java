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
package de.cuioss.jsf.api.components.model.lazyloading;

import de.cuioss.jsf.api.components.model.resultContent.ResultErrorHandler;
import de.cuioss.uimodel.result.ResultObject;

/**
 * Enrich a {@link LazyLoadingModel} with a type and a request id to be handled
 * in a asynchronous thread.
 */
public interface LazyLoadingThreadModel<T> extends LazyLoadingModel {

    /**
     * @return a unique request id.
     */
    long getRequestId();

    void resetNotificationBox();

    void handleRequestResult(ResultObject<T> result, ResultErrorHandler errorHandler);
}
