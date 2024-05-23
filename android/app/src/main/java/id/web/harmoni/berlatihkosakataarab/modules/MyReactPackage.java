package id.web.harmoni.berlatihkosakataarab.modules;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import id.web.harmoni.berlatihkosakataarab.modules.constanthelper.ConstantHelperModule;
import id.web.harmoni.berlatihkosakataarab.modules.splashscreen.SplashScreenModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyReactPackage implements ReactPackage {

    // Deprecated RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new SplashScreenModule(reactContext));
        modules.add(new ConstantHelperModule(reactContext));
        return modules;
    }
}
