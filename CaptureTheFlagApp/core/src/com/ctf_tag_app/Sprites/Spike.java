package com.ctf_tag_app.Sprites;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class Spike extends InteractiveTileObject {
    public Spike(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.SPIKE_BIT);
        //create bomb object

    }

    @Override
    public void onContact(String player) {

    }

    @Override
    public void onExit(String player) {

    }
}
