package demo.app.rssviewer.di.components;

import demo.app.rssviewer.di.ActivityScope;
import demo.app.rssviewer.di.modules.MainActivityModule;
import demo.app.rssviewer.view.DetailFragment;
import demo.app.rssviewer.view.ListFragment;
import demo.app.rssviewer.view.MainActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = IRssViewerAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(ListFragment rssListFragment);
    void inject(DetailFragment rssDetailFragment);
}
