package demo.app.rssviewer.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import demo.app.rssviewer.R;
import demo.app.rssviewer.di.IHasComponent;
import demo.app.rssviewer.di.components.DaggerIMainActivityComponent;
import demo.app.rssviewer.di.components.IMainActivityComponent;
import demo.app.rssviewer.di.components.IRssViewerAppComponent;
import demo.app.rssviewer.common.BaseActivity;
import demo.app.rssviewer.di.modules.MainActivityModule;
import demo.app.rssviewer.presenter.MainActivityPresenterImpl;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject
    MainActivityPresenterImpl presenter;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();
        ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag("ListFragment");
        if (listFragment == null){
            listFragment = new ListFragment();
        }
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            presenter.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupComponent(IRssViewerAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iRssViewerAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mainActivityComponent.inject(this);
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }

    @Override
    public void popFragmentFromStack() {
        fragmentManager.popBackStack();
    }
}
