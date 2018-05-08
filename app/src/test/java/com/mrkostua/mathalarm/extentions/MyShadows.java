package com.mrkostua.mathalarm.extentions;

import android.media.MediaPlayer;

import org.robolectric.Shadows;
import org.robolectric.shadow.api.Shadow;

/**
 * @author Kostiantyn Prysiazhnyi on 5/8/2018.
 */

public class MyShadows extends Shadows {
    public static MyShadowMediaPlayer myShadowOf(MediaPlayer mediaPlayer) {
        return (MyShadowMediaPlayer) Shadow.extract(mediaPlayer);
    }
}
