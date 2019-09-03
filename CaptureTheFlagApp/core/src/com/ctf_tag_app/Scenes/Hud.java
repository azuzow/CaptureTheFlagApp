package com.ctf_tag_app.Scenes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ctf_tag_app.CaptureTheFlagApp;





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

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        gameTimer=0;
        redScore=0;
        blueScore=0;
        viewport=new FitViewport(CaptureTheFlagApp.vWidth,CaptureTheFlagApp.vHeight, new OrthographicCamera());
        stage = new Stage(viewport,sb);
        Table table= new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        redScoreLabel = new Label(String.format("%01d",redScore),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        blueScoreLabel = new Label(String.format("%01d",blueScore),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        table.add(blueScoreLabel).expandX().padTop(10);
        table.add(countDownLabel).expandX().padTop(10);
        table.add(redScoreLabel).expandX().padTop(10);
        stage.addActor(table);
    }
    public void update(float dt){
        gameTimer += dt;
        if(gameTimer>=1){
            worldTimer--;
            countDownLabel.setText(String.format("%03d",worldTimer));
            gameTimer=0;
        }

    }
    public static void addBlueScore(){
        blueScore+=1;
        blueScoreLabel.setText(String.format("%01d",blueScore));

    }
    public static void addRedScore(){
        redScore+=1;
        redScoreLabel.setText(String.format("%01d",redScore));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
