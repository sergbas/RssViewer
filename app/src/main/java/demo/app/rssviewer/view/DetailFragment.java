package demo.app.rssviewer.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.octo.android.robospice.SpiceManager;
import com.squareup.picasso.Picasso;

import demo.app.rssviewer.R;
import demo.app.rssviewer.common.BaseFragment;
import demo.app.rssviewer.di.components.IMainActivityComponent;
import demo.app.rssviewer.network.RssViewerService;
import demo.app.rssviewer.presenter.DetailFragmentPresenterImpl;

import javax.inject.Inject;

public class DetailFragment extends BaseFragment implements IDetailFragmentView {

    @Inject
    DetailFragmentPresenterImpl presenter;

    protected SpiceManager spiceManager = new SpiceManager(RssViewerService.class);

    public static final String BUNDLE_ID = "bundleID";
    public static final String BUNDLE_DESC = "bundleDESC";
    public static final String BUNDLE_URL = "bundleURL";
    public static final String BUNDLE_TITLE = "bundleTITLE";

    private Activity activity;
    private int id;
    private String url;
    private String title;
    private String desc;

    private ImageView imageView;
    private TextView nameTextView;
    private TextView descTextView;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(int id, String url, String title, String desc) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_ID, id);
        bundle.putString(BUNDLE_URL, url);
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putString(BUNDLE_DESC, desc);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if (getArguments() != null && getArguments().containsKey(BUNDLE_ID)) {
             this.id = getArguments().getInt(BUNDLE_ID);
             this.url = getArguments().getString(BUNDLE_URL);
             this.title = getArguments().getString(BUNDLE_TITLE);
             this.desc = getArguments().getString(BUNDLE_DESC);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(int id)");
        }
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
        presenter.onResume(id, url, title, desc);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        imageView = (ImageView)view.findViewById(R.id.detailImageView);
        nameTextView = (TextView)view.findViewById(R.id.detailNameTextView);
        descTextView = (TextView)view.findViewById(R.id.detailDescTextView);
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void updateViews(String url, String title, String desc) {
        Picasso.with(activity)
                .load(url)
                .placeholder(R.drawable.picasso_loading_animation)
                .into(imageView);
        nameTextView.setText(title);
        descTextView.setText(desc);
    }

    @Override
    public void hideProgressDialog() {
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
