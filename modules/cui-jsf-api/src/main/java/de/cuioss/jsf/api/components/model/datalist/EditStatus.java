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
package de.cuioss.jsf.api.components.model.datalist;

/**
 * Defines the current status of editing for that precise {@link Object}. Is is
 * always used in conjunction with {@link ItemWrapper}
 *
 * @author Oliver Wolff
 */
public enum EditStatus {

    /** The initially loaded, unmodified item */
    INITIAL,

    /** The item is currently being edited. */
    EDIT,

    /**
     * The item was added by the EditableDataListComponent by calling
     * {@link EditableDataListModel#createEmptyItem()}
     */
    ADDED,

    /**
     * Defines an item that was loaded from the BE-system but modified in some way.
     */
    MODIFIED,

    /** The item is marked to be deleted. */
    DELETED
}
