package demo.app.rssviewer.network;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

public class RssViewerService extends SpiceService {
    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        InFileStringObjectPersister rssPersister = new InFileStringObjectPersister(application);
        cacheManager.addPersister(rssPersister);
        return cacheManager;
    }

    @Override
    public int getThreadCount() {
        return 3;
    }
}
