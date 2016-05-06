package demo.app.rssviewer.di.modules;

import android.app.Application;

import demo.app.rssviewer.app.RssViewerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RssViewerAppModule {

    private final RssViewerApp app;

    public RssViewerAppModule(RssViewerApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
