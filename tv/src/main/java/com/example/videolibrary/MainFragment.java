package com.example.videolibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuwi on 03/07/2017.
 */

public class MainFragment extends BrowseFragment {
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readData();
        startUserInterface();
        loadLists();
    }

    private void readData() {
        MovieList.list = new ArrayList<Movie>();
        String json = Utils.loadJSONFromResource(getActivity(), R.raw.movies);
        Gson gson = new Gson();
        Type collection = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        MovieList.list = gson.fromJson(json, collection);
    }

    private void startUserInterface() {
        setTitle("Videoteca");
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources()
                .getColor(R.color.fastlane_background));
    }

    private void loadLists() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        List<String> categories = getCategories();
        if (categories == null || categories.isEmpty()) return;
        for (String category : categories) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (Movie movie : MovieList.list) {
                if (category.equalsIgnoreCase(movie.getCategory())) listRowAdapter.add(movie);
            }
            if (listRowAdapter.size() > 0) {
                HeaderItem header = new HeaderItem(rowsAdapter.size() - 1, category);
                rowsAdapter.add(new ListRow(header, listRowAdapter));
            }
        }
        preferenceList(rowsAdapter);
        setAdapter(rowsAdapter);
    }

    private List<String> getCategories() {
        if (MovieList.list == null)
            return null;
        List<String> categories = new ArrayList<String>();
        for (Movie movie : MovieList.list) {
            if (!categories.contains(movie.getCategory())) {
                categories.add(movie.getCategory());
            }
        }
        return categories;
    }

    private void preferenceList(ArrayObjectAdapter adapter) {
        HeaderItem gridHeader = new HeaderItem(adapter.size() - 1, "PREFERENCIAS");
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("Vistas");
        gridRowAdapter.add("Errores");
        gridRowAdapter.add("Preferencias");
        adapter.add(new ListRow(gridHeader, gridRowAdapter));


    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }


}


