package demo.app.rssviewer.presenter;

import com.octo.android.robospice.SpiceManager;

import demo.app.rssviewer.common.BaseFragmentPresenter;
import demo.app.rssviewer.view.IDetailFragmentView;

public interface IDetailFragmentPresenter extends BaseFragmentPresenter<IDetailFragmentView> {
    void onResume(int id, String url, String title, String desc);
    void onPause();
}
