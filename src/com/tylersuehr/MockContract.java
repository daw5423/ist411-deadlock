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

package com.tylersuehr;

import com.tylersuehr.framework.ui_layer.IPresenter;
import com.tylersuehr.framework.ui_layer.IView;

/**
 * Demonstration of mock contract for MVP architecture.
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public interface MockContract {
    interface IMockPresenter extends IPresenter<IMockView> {
        void loadMockUser(String userId);
    }
    
    interface IMockView extends IView {
        void onMockUserReady(MockUser user);
    }
}