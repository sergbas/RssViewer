package demo.app.rssviewer.presenter;

import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import demo.app.rssviewer.model.ItemRss;
import demo.app.rssviewer.network.HandleXML;
import demo.app.rssviewer.view.IListFragmentView;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ListFragmentPresenterImpl implements IListFragmentPresenter {

    private static final String TAG = "ListFragmentPr...";
    private HandleXML obj;

    private static final String URL_LIST_RSS_API = "https://lenta.ru/rss";

    private IListFragmentView view;
    private SpiceManager spiceManager;

    @Inject
    public ListFragmentPresenterImpl() {
    }

    @Override
    public void init(IListFragmentView view) {
        this.view=view;
    }

    @Override
    public void onResume(SpiceManager spiceManager) {
        Log.i(TAG, "onResume");
        view.startService();
        this.spiceManager = spiceManager;
        String url = URL_LIST_RSS_API;
        sendRequest(url, spiceManager, true);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        view.stopService();
    }

    @Override
    public void onLoadMore() {
        Log.i(TAG, "onLoadMore");
    }

    @Override
    public void onItemClick(ItemRss itemRss) {
        int id = itemRss.getId();
        String url = itemRss.getUrl();
        String title = itemRss.getName();
        String desc = itemRss.getDescription();
        view.replaceToDetailFragment(id, url, title, desc);
    }

    @Override
    public void addListToAdapter(List<ItemRss> itemRssList) {
        view.addListToAdapter(itemRssList);
    }

    private void sendRequest(String url, SpiceManager spiceManager, boolean isLoadMore) {
        SimpleTextRequest request = new SimpleTextRequest(url);

        if (!isLoadMore){
            view.showProgressDialog();
        }
        spiceManager.execute(request, "Rss", DurationInMillis.ONE_HOUR, new RssRequestListener());
    }

    private final class RssRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            view.hideProgressDialog();
        }

        @Override
        public void onRequestSuccess(String rssResult) {
            //Log.i(TAG, "onRequestSuccess:" + rssResult);
            view.hideProgressDialog();
            view.setRssListAdapter(getListFromRss(rssResult));
        }

        private String getCharacterDataFromElement(Element e) {
            NodeList list = e.getChildNodes();
            String data;

            for(int index = 0; index < list.getLength(); index++){
                if(list.item(index) instanceof CharacterData){
                    CharacterData child = (CharacterData) list.item(index);
                    data = child.getData();

                    if(data != null && data.trim().length() > 0)
                        return child.getData();
                }
            }
            return "";
        }

        private List<ItemRss> getListFromRss(String rssString) {

            List<ItemRss> itemRssList = new ArrayList<>();

            try {
                Log.i(TAG, "getListFromRss:" + rssString);

                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(rssString));

                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("item");

                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);

                        NodeList description = element.getElementsByTagName("description");
                        Element e = (Element) description.item(0);
                        String desc = getCharacterDataFromElement(e);

                        NodeList title = element.getElementsByTagName("title");
                        e = (Element) title.item(0);
                        String stitle = getCharacterDataFromElement(e);

                        String image = "";
                        NodeList enclosure = element.getElementsByTagName("enclosure");
                        e = (Element) enclosure.item(0);
                        if(e != null && e.hasAttribute("url"))
                            image = e.getAttribute("url");

                        ItemRss itemRss = new ItemRss();
                        itemRss.setId(i);
                        itemRss.setName(stitle);
                        itemRss.setDescription(desc);
                        itemRss.setPublished("--:--");
                        itemRss.setUrl(image);
                        itemRssList.add(itemRss);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemRssList;
        }
    }
}
