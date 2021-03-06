/*
 * Copyright (C) 2016 Nemi
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * overning permissions and limitations under License.
 */
package com.github.shareme.gwssampleapps.mymateriallibrary.viewdecorator;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Method;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class ViewFactoryV11 implements ViewFactory {
    private Method method;
    private LayoutInflater layoutInflater;

    public ViewFactoryV11(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.method = getOnCreateViewMethod();
    }

    @Override
    public View createView(View parent, String name, AttributeSet attrs) {
        if(method != null) {
            return onCreateView(parent, name, attrs);
        }

        return null;
    }

    private View onCreateView(View parent, String name, AttributeSet attrs){
        try {
            return (View) method.invoke(layoutInflater, parent, name, attrs);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method getOnCreateViewMethod() {
        try {
            Method method = LayoutInflater.class.getDeclaredMethod("onCreateView",
                    View.class, String.class, AttributeSet.class);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
