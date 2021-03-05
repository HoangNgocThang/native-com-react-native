package com.demonative;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class CustomViewManager extends SimpleViewManager<CustomView> {

    public static final String REACT_CLASS = "MyCustomReactViewManager";
    ReactApplicationContext context;

    public CustomViewManager(ReactApplicationContext mContent) {
        context= mContent;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public CustomView createViewInstance(ThemedReactContext context) {
        return new CustomView(context);
    }
}