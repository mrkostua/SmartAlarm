package com.mrkostua.mathalarm.extentions;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowMediaPlayer;
import org.robolectric.shadows.util.DataSource;

import java.io.IOException;

import static org.robolectric.shadows.ShadowMediaPlayer.State.INITIALIZED;

/**
 * @author Kostiantyn Prysiazhnyi on 5/8/2018.
 */
@Implements(MediaPlayer.class)
public class MyShadowMediaPlayer extends ShadowMediaPlayer {
    @Implementation
    public void setDataSource(Context context, Uri uri) throws IOException {
        System.out.println("mySetDataSource()");
        create(context,uri);

    }

    @Implementation
    @Override
    public void prepareAsync() {
        System.out.println("myPrepareAsync()");
        super.prepareAsync();
    }

    public static MediaPlayer create(Context context, Uri uri) {
        System.out.println("myCreate()");
        DataSource ds = (DataSource.toDataSource(context, uri));
        addMediaInfo(ds, new ShadowMediaPlayer.MediaInfo());

        MediaPlayer mp = new MediaPlayer();
        ShadowMediaPlayer shadow = Shadow.extract(mp);
        try {
            shadow.setDataSource(ds);
            shadow.setState(INITIALIZED);
            mp.prepare();
        } catch (Exception e) {
            return null;
        }

        return mp;
    }

    @Implementation
    @Override
    public void start() {
        System.out.println("myStart()");

    }

}
