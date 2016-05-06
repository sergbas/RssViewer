package demo.app.rssviewer.di.modules;

import demo.app.rssviewer.presenter.MainActivityPresenterImpl;
import demo.app.rssviewer.presenter.DetailFragmentPresenterImpl;
import demo.app.rssviewer.presenter.ListFragmentPresenterImpl;
import demo.app.rssviewer.presenter.ShowFragmentPresenterImpl;
import demo.app.rssviewer.view.IMainActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public MainActivityPresenterImpl provideMainActivityPresenterImpl (IMainActivityView view){
        return  new MainActivityPresenterImpl(view);
    }

    @Provides
    public ListFragmentPresenterImpl provideListFragmentPresenterImpl() {
        return new ListFragmentPresenterImpl();
    }

    @Provides
    public DetailFragmentPresenterImpl provideDetailFragmentPresenterImpl() {
        return new DetailFragmentPresenterImpl();
    }

    @Provides
    public ShowFragmentPresenterImpl provideShowFragmentPresenterImpl(){
        return new ShowFragmentPresenterImpl();
    }
}
