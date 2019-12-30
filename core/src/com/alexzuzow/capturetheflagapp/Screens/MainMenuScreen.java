package com.alexzuzow.capturetheflagapp.Screens;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Scenes.Hud;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private Texture playButton;
    private Texture exitButton;
    private CaptureTheFlagApp game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private int vWidth= 300;
    private int vHeight= 150;
    public MainMenuScreen(CaptureTheFlagApp game){
        this.game =game;
        playButton= new Texture("PlayButton.png");
        exitButton=new Texture("ExitButton.png");
        gameCam = new OrthographicCamera();
        gameCam.zoom+= 1;
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        gamePort.apply();
        gameCam.setToOrtho(false, gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameCam.update();
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        float x=(Gdx.graphics.getWidth()-vWidth)/2;
        float y =(Gdx.graphics.getHeight())/2;
        game.batch.draw(playButton,x,y,vWidth,vHeight);
        game.batch.draw(exitButton,x,y-200,vWidth,vHeight);
//        System.out.println("PICTURE Y"+ (y-vHeight));
//        System.out.println("GET Y "+Gdx.input.getY());
        if((Gdx.input.getX()<x+vWidth && Gdx.input.getX()>x)&& (Gdx.input.getY()>y-vHeight&&Gdx.input.getY()<y)){
            if(Gdx.input.isTouched()){
                game.setScreen(new GameScreen(game));

            }
        }

//        game.batch.draw(playButton, 0, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//        game.batch.draw(exitButton, 0, 0, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

        System.out.println("WIDTH " + width+ " Height "+ height);
        gamePort.update(width, height);
        gameCam.position.x=Gdx.graphics.getWidth()/2;
        gameCam.position.y=Gdx.graphics.getHeight()/2;
//        game.batch.draw(playButton,(width-vWidth)/2,height/2,vWidth,vHeight);
//        game.batch.draw(exitButton,(width-vWidth)/2,height/2-200,vWidth,vHeight);
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

    @Override
    public void dispose() {

    }
}
