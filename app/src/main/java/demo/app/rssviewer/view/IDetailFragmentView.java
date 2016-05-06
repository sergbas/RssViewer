package demo.app.rssviewer.view;

public interface IDetailFragmentView {
    void updateViews(String url, String title, String desc);
    void hideProgressDialog();
    void startService();
    void stopService();
}
