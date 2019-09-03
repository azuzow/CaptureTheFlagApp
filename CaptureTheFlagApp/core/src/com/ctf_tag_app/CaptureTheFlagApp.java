package com.ctf_tag_app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ctf_tag_app.Screens.GameScreen;
import com.ctf_tag_app.Screens.MainMenuScreen;

public class CaptureTheFlagApp extends Game {
    public SpriteBatch batch;
    public static final int vWidth = 400;
    public static final int vHeight = 208;
    public static final float PPM = 100;

    public static final short WALL_BIT = 1;
    public static final short BLUE_PLAYER_BIT = 2;
    public static final short RED_FLAG_BIT = 4;
    public static final short BLUE_FLAG_BIT = 8;
    public static final short BOMB_BIT = 16;
    public static final short SPIKE_BIT = 32;
    public static final short BUTTON_BIT = 64;
    public static final short USED_BIT = 128;
    public static final short RED_PLAYER_BIT = 256;
    public static final short RED_FLAG_CARRIER = 512;
    public static final short BLUE_FLAG_CARRIER = 1024;
    public static final short SPAWN_BIT = 2048;


    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));

    }

    @Override
    public void render() {
        super.render();


    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
