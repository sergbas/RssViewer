package demo.app.rssviewer.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.app.rssviewer.di.components.IRssViewerAppComponent;
import demo.app.rssviewer.app.RssViewerApp;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(RssViewerApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(IRssViewerAppComponent appComponent);

}
