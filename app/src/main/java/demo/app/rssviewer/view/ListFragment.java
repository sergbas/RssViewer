package demo.app.rssviewer.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.octo.android.robospice.SpiceManager;

import demo.app.rssviewer.R;
import demo.app.rssviewer.common.BaseFragment;
import demo.app.rssviewer.common.RssListAdapter;
import demo.app.rssviewer.di.components.IMainActivityComponent;
import demo.app.rssviewer.model.ItemRss;
import demo.app.rssviewer.network.RssViewerService;
import demo.app.rssviewer.presenter.ListFragmentPresenterImpl;

import java.util.List;

import javax.inject.Inject;

public class ListFragment extends BaseFragment implements IListFragmentView {

    @Inject
    ListFragmentPresenterImpl presenter;

    protected SpiceManager spiceManager = new SpiceManager(RssViewerService.class);

    private Activity activity;
    private ListView listView;
    private RssListAdapter RssListAdapter;
    private View rootView;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        presenter.onResume(spiceManager);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false);
            listView = (ListView) rootView.findViewById(R.id.rssListView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onItemClick((ItemRss) listView.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void setRssListAdapter(List<ItemRss> itemRssList) {
        if (RssListAdapter == null) {
            RssListAdapter = new RssListAdapter(activity, itemRssList);
            listView.setAdapter(RssListAdapter);
        } else {
            presenter.addListToAdapter(itemRssList);
        }
    }

    @Override
    public void addListToAdapter(List<ItemRss> itemRssList) {
        RssListAdapter.add(itemRssList);
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void hideProgressDialog() {
    }

    @Override
    public void replaceToDetailFragment(int id, String url, String title, String desc) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        DetailFragment detailFragment = DetailFragment.newInstance(id, url, title, desc);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startService() {
        if (!spiceManager.isStarted()){
            spiceManager.start(activity);
        }
    }

    @Override
    public void stopService() {
        if (spiceManager.isStarted()){
            spiceManager.shouldStop();
        }
    }
}
