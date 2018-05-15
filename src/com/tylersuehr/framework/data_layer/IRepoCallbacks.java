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

package com.tylersuehr.framework.data_layer;

import java.util.List;

/**
 * Defines callbacks for repository chain of command design pattern.
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public interface IRepoCallbacks {
    interface IError {
        void onNotAvailable(Exception ex);
    }
    
    interface ISingle<T> extends IError {
        void onAvailable(T value);
    }
    
    interface IList<T> extends IError {
        void onAvailable(List<T> values);
    }
}