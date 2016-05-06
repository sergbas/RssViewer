package demo.app.rssviewer.di.components;

import demo.app.rssviewer.app.RssViewerApp;
import demo.app.rssviewer.di.modules.RssViewerAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                RssViewerAppModule.class
        }
)
public interface IRssViewerAppComponent {
    void inject(RssViewerApp app);
}
