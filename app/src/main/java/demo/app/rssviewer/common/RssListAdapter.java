package demo.app.rssviewer.common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import demo.app.rssviewer.R;
import demo.app.rssviewer.model.ItemRss;

import java.util.List;

public class RssListAdapter extends BaseAdapter {

    private static final String TAG = "RssListAdapt";
    private List<ItemRss> itemRssList;
    private LayoutInflater layoutInflater;
    private Activity activity;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;


    public RssListAdapter (Activity activity, List<ItemRss> itemRssList) {
        this.itemRssList = itemRssList;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if(itemRssList == null) return 0;
        return itemRssList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= itemRssList.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public ItemRss getItem(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? itemRssList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position : -1;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            return getFooterView(position, convertView, parent);
        }
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_rss_list, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.nameText = (TextView)view.findViewById(R.id.nameTextView);
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder)view.getTag();
        ItemRss itemRss = getItem(position);
        holder.nameText.setText(itemRss.getName());
        //holder.descText.setText(itemRss.getDescription());

        String url = itemRss.getUrl();
        Picasso.with(activity)
                .load(url)
                .placeholder(R.drawable.picasso_loading_animation)
                .into(holder.imageView);

        return view;
    }

    public void add(List<ItemRss> itemRsss) {
        if(itemRssList != null) {
            itemRssList.clear();
            itemRssList.addAll(itemRsss);
            notifyDataSetChanged();
        }
    }

    public View getFooterView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = layoutInflater.inflate(R.layout.list_footer, parent, false);
        }
        return row;
    }

    static class ViewHolder {
        TextView nameText;
        ImageView imageView;
    }
}
