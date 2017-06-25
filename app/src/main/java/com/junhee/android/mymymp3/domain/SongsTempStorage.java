package com.junhee.android.mymymp3.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JunHee on 2017. 6. 22..
 */

public class SongsTempStorage {

    private static SongsTempStorage instance = null;
    private List<Music.Item> items;




    public static SongsTempStorage getInstance() {
        if (instance == null)
            instance = new SongsTempStorage();

        return instance;
    }

    private SongsTempStorage() {
        items = new ArrayList<>();
    }

    public void setItems(List<Music.Item> items) {
        this.items = items;
    }

    public void removeAllSongs() {

        Iterator<Music.Item> iterator = items.iterator();

        while (iterator.hasNext()) {
            Music.Item tempItem = iterator.next();
            iterator.remove();
        }
    }


//            for (Music.Item temp : items) {
//                items.remove(temp);
//            }


    public void additem(Music.Item item) {
        items.add(item);
    }

    public void init() {
        removeAllSongs();
    }

    public List<Music.Item> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

}

