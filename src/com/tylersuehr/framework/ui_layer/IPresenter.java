/*
 * Copyright 2018 Group 5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tylersuehr.framework.ui_layer;

/**
 * Defines the presenter of the MVP (Model-View-Presenter) architecture.
 * Similar to a controller in MVC, but much better:
 * (1) Complete separation of business and application logic.
 * (2) Two-way dependencies on view and presenter
 *      - the view knows of the presenter 
 *      - the presenter knows of the view
 * (3) Easily prevent memory leakage in contextual-based platforms by
 *     attaching/detaching the view to the presenter in appropriate life
 *     cycle events (i.e. like Android Activity and Fragment life cycles).
 * 
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public interface IPresenter<T extends IView> {
    /**
     * Attaches a view to this presenter.
     * @param view View
     */
    void attach(T view);
    
    /**
     * Detaches the view of this presenter.
     */
    void detach();
    
    /**
     * Gets the view of this presenter.
     * @return View
     */
    T getView();
}