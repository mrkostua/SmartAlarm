package com.mrkostua.mathalarm.extentions;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowMediaPlayer;

import java.io.IOException;

import static org.robolectric.shadows.util.DataSource.toDataSource;

/**
 * @author Kostiantyn Prysiazhnyi on 5/8/2018.
 */
@Implements(MediaPlayer.class)
public class MyShadowMediaPlayer extends ShadowMediaPlayer {
    @Implementation
    public void setDataSource(Context context, Uri uri) throws IOException {
        System.out.println("mySetDataSource()");
        setDataSource(toDataSource(context, uri));
    }

    @Implementation
    @Override
    public void prepareAsync() {
        System.out.println("myPrepareAsync()");
        super.prepareAsync();
    }

    @Implementation
    @Override
    public void start() {
        System.out.println("myStart()");

    }
}
