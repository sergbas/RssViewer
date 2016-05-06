package demo.app.rssviewer.presenter;

import android.util.Log;

import demo.app.rssviewer.view.IDetailFragmentView;

import javax.inject.Inject;

public class DetailFragmentPresenterImpl implements IDetailFragmentPresenter {

    private static final String TAG = "DetailFragm...";
    private IDetailFragmentView view;

    @Inject
    public DetailFragmentPresenterImpl() {
    }

    @Override
    public void init(IDetailFragmentView view) {
        this.view = view;
    }

    @Override
    public void onResume(int id, String url, String title, String desc) {
        view.startService();
        sendRequest(id, url, title, desc);
    }

    @Override
    public void onPause() {
        view.stopService();
    }

    private void sendRequest(int id, String url, String title, String desc) {
        Log.i(TAG, id + ":" + desc);
        view.hideProgressDialog();
        view.updateViews(url, title, desc);
    }


}
