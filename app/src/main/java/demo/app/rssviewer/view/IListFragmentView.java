package demo.app.rssviewer.view;

import demo.app.rssviewer.model.ItemRss;

import java.util.List;

public interface IListFragmentView {
    void setRssListAdapter(List<ItemRss> itemRssList);
    void addListToAdapter(List<ItemRss> itemRssList);
    void showProgressDialog();
    void hideProgressDialog();
    void replaceToDetailFragment(int id, String url, String title, String desc);
    void startService();
    void stopService();
}
