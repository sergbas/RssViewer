package demo.app.rssviewer.presenter;

import com.octo.android.robospice.SpiceManager;

import demo.app.rssviewer.common.BaseFragmentPresenter;
import demo.app.rssviewer.model.ItemRss;
import demo.app.rssviewer.view.IListFragmentView;

import java.util.List;

public interface IListFragmentPresenter extends BaseFragmentPresenter<IListFragmentView> {
    void onResume(SpiceManager spiceManager);
    void onPause();
    void onLoadMore();
    void onItemClick(ItemRss itemRss);
    void addListToAdapter(List<ItemRss> itemRssList);
}
