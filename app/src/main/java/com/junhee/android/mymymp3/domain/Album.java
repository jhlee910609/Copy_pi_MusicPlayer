package com.junhee.android.mymymp3.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JunHee on 2017. 6. 21..
 */

public class Album {

    private static Album instance = null;
    private Set<Item> items = null;

    private Album() {
        items = new HashSet<>();
    }

    public static Album getInstance() {
        if (instance == null)
            instance = new Album();

        return instance;
    }

    public List<Item> getItems() {
        return new ArrayList(items);
    }

    public void loader(Context context) {
        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블 명 정의 ?
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        // 2. 가져올 컬럼명 정의
        String proj[] = {
                // ====== TODO 데이터 받아오는 종류 다르게 설정 ========
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS
        };
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.albumId = getValue(cursor, proj[0]);
                item.albumName = getValue(cursor, proj[1]);
                item.artist = getValue(cursor, proj[2]);
                item.numberOfSongs = getValue(cursor, proj[3]);

                item.musicUri = makeMusicUri(item.albumId);
                item.albumArtUri = makeAlbumUri(item.albumId);
                items.add(item);
            }
        }
        cursor.close();
    }

    private String getValue(Cursor cursor, String name) {
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    public class Item {

        public String albumId;
        public String artist;
        public String albumName;
        public String numberOfSongs;

        public Uri musicUri;
        public Uri albumArtUri;

        public boolean itemClicked = false;

        @Override
        public boolean equals(Object item) {
            // null 체크
            if (item == null) return false;
            // 객체 타입 체크
            if (!(item instanceof Item)) return false;
            // 키값의 hashcode 비교
            return albumId.hashCode() == item.hashCode();
        }

        @Override
        public int hashCode() {
            return albumId.hashCode();
        }
    }


    private Uri makeMusicUri(String musicId) {
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, musicId);
    }

    private Uri makeAlbumUri(String albumId) {
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri + albumId);
    }
}
