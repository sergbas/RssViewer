package demo.app.rssviewer.app;

import android.app.Application;
import android.content.Context;

import demo.app.rssviewer.di.components.DaggerIRssViewerAppComponent;
import demo.app.rssviewer.di.components.IRssViewerAppComponent;
import demo.app.rssviewer.di.modules.RssViewerAppModule;

public class RssViewerApp extends Application {

    private IRssViewerAppComponent appComponent;

    public static RssViewerApp get(Context context) {
        return (RssViewerApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraphAndInject();
    }

    public IRssViewerAppComponent getAppComponent() {
        return appComponent;
    }

    public void buildGraphAndInject() {
        appComponent = DaggerIRssViewerAppComponent.builder()
                .rssViewerAppModule(new RssViewerAppModule(this))
                .build();
        appComponent.inject(this);
    }
}
