package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;


public class Spike extends InteractiveTileObject {
    public Spike(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds,null);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.SPIKE_BIT);
        //create bomb object

    }

    @Override
    public void onContact(String player) {

    }

    @Override
    public void onContact(Object player) {

    }

    @Override
    public void onExit(String player) {

    }
}
