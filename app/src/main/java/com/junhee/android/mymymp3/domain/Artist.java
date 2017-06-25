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
 * Created by JunHee on 2017. 6. 22..
 */

public class Artist {

    private static Artist instance = null;
    // 중복을 방지하기 위해 데이터 저장소의 형태를 Set 으로 설정
    private Set<Item> items = null;

    private Artist() {
        items = new HashSet<>();
    }

    public static Artist getInstance(){
        if(instance == null)
            instance = new Artist();

        return instance;
    }

    public List<Item> getItems(){
        return new ArrayList(items);
    } // 123 번지
    // 700

    // 음악 데이터를 폰에서 꺼낸다음 List 저장소에 담아둔다.
    public void loader(Context context) {
        // items.clear(); Set을 사용함으로 중복을 방지할 수 있다
        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블 명 정의 ?
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        // 2. 가져올 컬럼명 정의
        String proj[] = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
        };

        // 3. 쿼링~
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Item item = new Item();
                item.id = getValue(cursor, proj[0]);
                item.artistName = getValue(cursor, proj[1]);
                item.numberOfAlbums = getValue(cursor, proj[2]);
                item.numberOfSongs = getValue(cursor, proj[3]);


                item.musicUri = makeMusicUri(item.id);
                // 저장소에 담는다...
                items.add(item);
            }
        }
        // 커서 꼭 닫을것...
        cursor.close();
    }

    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    // Set 이 중복값을 허용하지 않도록 equals 와 hashCode를 활용한다
    public class Item {
        public String id;
        public String artist;
        public String title;
        public String artistName;
        public String numberOfAlbums;
        public String numberOfSongs;

        public Uri musicUri;
        public Uri albumArtUri;

        public boolean itemClicked = false;

        @Override
        public boolean equals(Object item) {
            // null 체크
            if(item == null) return false;
            // 객체 타입 체크
            if (!(item instanceof Item)) return false;
            // 키값의 hashcode 비교
            return id.hashCode() == item.hashCode();
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }


    private Uri makeMusicUri(String musicId){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, musicId);
    }

    private Uri makeAlbumUri(String albumId){
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri + albumId);
    }
}
