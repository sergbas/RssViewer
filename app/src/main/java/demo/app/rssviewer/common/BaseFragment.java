package demo.app.rssviewer.common;

import android.app.Fragment;

import demo.app.rssviewer.di.IHasComponent;

public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }
}
