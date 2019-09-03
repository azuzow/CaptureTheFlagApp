package com.ctf_tag_app.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Scenes.Hud;

public class MainMenuScreen implements Screen {
    private CaptureTheFlagApp game;
    private Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    public MainMenuScreen(CaptureTheFlagApp game) {
        this.game = game;
//        texture = new Texture("badlogic.jpg");
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(game.vWidth,game.vHeight,gameCam);
        hud= new Hud(game.batch);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
//        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.draw(texture,0,0);
//        game.batch.end();
    }
    @Override
    public void dispose() {

    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void show() {

    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
