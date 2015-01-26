package com.avene.avene.livina;

import android.app.Application;

import org.fourthline.cling.android.FixedAndroidLogHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by yamai on 1/26/2015.
 */
public class LivinaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Fix the logging integration between java.util.logging and Android internal logging
        org.seamless.util.logging.LoggingUtil.resetRootHandler(new FixedAndroidLogHandler());
        // Now you can enable logging as needed for various categories of Cling:
        Logger.getLogger("org.fourthline.cling").setLevel(Level.INFO);

    }
}
