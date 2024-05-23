package com.vsboilerplatev2.modules.splashscreen;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactApplicationContext;

public class SplashScreenModule extends ReactContextBaseJavaModule {
  public SplashScreenModule(ReactApplicationContext reactContext) {
      super(reactContext);
  }

  @Override
  public String getName() {
    return "SplashScreen";
  }

  @ReactMethod()
  public void show() {
    SplashScreenImpl.show(getReactApplicationContext());
  }

 @ReactMethod()
  public void hide() {
    SplashScreenImpl.hide(getReactApplicationContext());
  }
}
