package com.ctf_tag_app.Tools;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;
import com.ctf_tag_app.Sprites.BlueTeam;
import com.ctf_tag_app.Sprites.Bomb;
import com.ctf_tag_app.Sprites.Button;
import com.ctf_tag_app.Sprites.Flag;
import com.ctf_tag_app.Sprites.SpawnPoint;
import com.ctf_tag_app.Sprites.Spike;
import com.ctf_tag_app.Sprites.Wall;


public class B2WorldCreator {
    private Array<BlueTeam> blueTeam;
    private Flag redFlag;
    private Flag blueFlag;
    private Array<Bomb> bombs;
    private Array<Spike> spikes;
    private Array<SpawnPoint> bluePlayerSpawns;
    private Array<SpawnPoint> redPlayerSpawns;

    public B2WorldCreator(GameScreen screen) {
        TiledMap map = screen.getMap();
        //create walls object
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Wall(screen, rect, null);
        }
        //create red Flag objects
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            redFlag = new Flag(screen, null, ellipse, CaptureTheFlagApp.RED_FLAG_BIT);
        }
        //create blue Flag object
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            blueFlag = new Flag(screen, null, ellipse, CaptureTheFlagApp.BLUE_FLAG_BIT);
        }
        //create bomb object
        bombs = new Array<Bomb>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            bombs.add(new Bomb(screen, null, ellipse));
        }
        //create button object

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            new Button(screen, null, ellipse);
        }
//        create players on BlueTeam
        blueTeam = new Array<BlueTeam>();
        bluePlayerSpawns = new Array<SpawnPoint>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            String playerSprite = "BluePlayerSprite.png";
            blueTeam.add(new BlueTeam(screen, rect.getX() + 20, rect.getY() + 20,
                    CaptureTheFlagApp.BLUE_PLAYER_BIT, CaptureTheFlagApp.BLUE_FLAG_CARRIER, playerSprite));
            bluePlayerSpawns.add(new SpawnPoint(screen, rect, null));
        }
        spikes = new Array<Spike>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            spikes.add(new Spike(screen, null, ellipse));
        }
    }

    public Array<BlueTeam> getBlueTeam() {
        return blueTeam;
    }

    public Flag getRedFlag() {
        return redFlag;
    }

    public Flag getBlueFlag() {
        return blueFlag;
    }

    public Array<Bomb> getBombs() {
        return bombs;
    }

    public Array<SpawnPoint> getBluePlayerSpawns() {
        return bluePlayerSpawns;
    }

    public Array<SpawnPoint> getRedPlayerSpawns() {
        return redPlayerSpawns;
    }

    public Array<Spike> getSpikes() {
        return spikes;
    }
}
