package com.alexzuzow.capturetheflagapp.Scenes;


import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Tools.Joystick;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Hud implements Disposable {
    public Stage stage;


    public Viewport viewport;
    private Integer timer;
    private float gameTimer;
    private Integer worldTimer;
    private static Integer redScore;
    private static Integer blueScore;

    Label countDownLabel;
    static Label redScoreLabel;
    static Label blueScoreLabel;
    Label timeLabel;
    static Joystick joystick;

    public Hud(SpriteBatch sb) {
        worldTimer = 300;
        gameTimer = 0;
        redScore = 0;
        blueScore = 0;
        viewport = new FitViewport(CaptureTheFlagApp.vWidth, CaptureTheFlagApp.vHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        redScoreLabel = new Label(String.format("%01d", redScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        blueScoreLabel = new Label(String.format("%01d", blueScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(blueScoreLabel).expandX().padTop(10);
        table.add(countDownLabel).expandX().padTop(10);
        table.add(redScoreLabel).expandX().padTop(10);
        Table table2 = new Table();
//        table2.bottom();
//        table2.setBounds(300,100,5,5);
        joystick=new Joystick(table2);
        stage.addActor(table);
        stage.addActor(table2);
        Gdx.input.setInputProcessor(stage);




    }

    public void update(float dt) {
        gameTimer += dt;
        if (gameTimer >= 1) {
            worldTimer--;
            countDownLabel.setText(String.format("%03d", worldTimer));
            gameTimer = 0;
        }

    }

    public static void addBlueScore() {
        blueScore += 1;
        blueScoreLabel.setText(String.format("%01d", blueScore));

    }

    public static void addRedScore() {
        redScore += 1;
        redScoreLabel.setText(String.format("%01d", redScore));
    }
    public Stage getStage(){
        return stage;
    }
    public Touchpad getTouchPad(){
        return joystick.getTouchpad();
    }

    @Override
    public void dispose() {
        joystick.Dispose();
        stage.dispose();

    }
}
