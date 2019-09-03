package com.ctf_tag_app.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class Button extends InteractiveTileObject {
    public Button(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.BUTTON_BIT);

    }

    @Override
    public void onContact(String player) {
        Gdx.app.log("Button Pressed", player);
    }

    @Override
    public void onExit(String player) {

    }

}


