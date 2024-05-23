package id.web.harmoni.berlatihkosakataarab.modules.splashscreen;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.window.SplashScreen;
import android.window.SplashScreenView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import id.web.harmoni.berlatihkosakataarab.R;

public class SplashScreenImpl {

    private enum Status {
        HIDDEN,
        HIDING,
        INITIALIZING,
        VISIBLE,
    }

    @NonNull
    private static Status mStatus = Status.HIDDEN;

    @StyleRes
    private static int mThemeResId = R.style.Theme_BootSplash_DayNight;

    @Nullable
    private static SplashScreenDialog mInitialDialog = null;

    public static void init(
            @Nullable final Activity activity
    ) {
        // Keep the splash screen on-screen until Dialog is shown
        final View contentView = activity.findViewById(android.R.id.content);
        mStatus = Status.INITIALIZING;

        contentView
                .getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (mStatus == Status.INITIALIZING) {
                            return false;
                        }

                        contentView
                                .getViewTreeObserver()
                                .removeOnPreDrawListener(this);

                        return true;
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // This is not called on Android 12 when activity is started using intent
            // (Android studio / CLI / notification / widget…)
            SplashScreen.OnExitAnimationListener listener = new SplashScreen.OnExitAnimationListener() {
                @Override
                public void onSplashScreenExit(@NonNull SplashScreenView view) {
                    view.remove(); // Remove it immediately, without animation

                    activity
                            .getSplashScreen()
                            .clearOnExitAnimationListener();
                }
            };

            activity
                    .getSplashScreen()
                    .setOnExitAnimationListener(listener);
        }

        mInitialDialog = new SplashScreenDialog(activity,  mThemeResId, true);

        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInitialDialog.show(new Runnable() {
                    @Override
                    public void run() {
                        mStatus = Status.VISIBLE;
                    }
                });
            }
        });
    }

    // From https://stackoverflow.com/a/61062773
    public static boolean isSamsungOneUI4() {
        String name = "SEM_PLATFORM_INT";

        try {
            Field field = Build.VERSION.class.getDeclaredField(name);
            int version = (field.getInt(null) - 90000) / 10000;
            return version == 4;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static void show(final ReactApplicationContext reactContext) {
        mStatus = Status.INITIALIZING;
        final Activity activity = reactContext.getCurrentActivity();
        mInitialDialog = new SplashScreenDialog(activity, mThemeResId, true);
        mInitialDialog.show(new Runnable() {
            @Override
            public void run() {
                mStatus = Status.VISIBLE;
            }
        });
    }

    public static void hide(
            final ReactApplicationContext reactContext
    ) {
        final Activity activity = reactContext.getCurrentActivity();
        if (mStatus == Status.INITIALIZING
                || activity == null
                || activity.isFinishing()
                || activity.isDestroyed()
        ) {
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer.cancel();
                    hide(reactContext);
                }
            }, 100);

            return;
        }


        if (mInitialDialog == null || mStatus == Status.HIDDEN) {
            return; // both initial and fade out dialog are hidden
        }

        mStatus = Status.HIDDEN;
        mInitialDialog.dismiss(new Runnable() {
            @Override
            public void run() {
                mStatus = Status.HIDDEN;
                mInitialDialog = null;
            }
        });
    }
}