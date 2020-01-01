package com.alexzuzow.capturetheflagapp.Tools;


import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.alexzuzow.capturetheflagapp.Sprites.Corner;
import com.alexzuzow.capturetheflagapp.Sprites.Lava;
import com.alexzuzow.capturetheflagapp.Sprites.Portal;
import com.alexzuzow.capturetheflagapp.Sprites.PowerUp;
import com.alexzuzow.capturetheflagapp.Sprites.SpeedPad;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.alexzuzow.capturetheflagapp.Sprites.Bomb;
import com.alexzuzow.capturetheflagapp.Sprites.Button;
import com.alexzuzow.capturetheflagapp.Sprites.Flag;
import com.alexzuzow.capturetheflagapp.Sprites.SpawnPoint;
import com.alexzuzow.capturetheflagapp.Sprites.Spike;
import com.alexzuzow.capturetheflagapp.Sprites.Wall;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class B2WorldCreator {
    private Array<User> blueTeam;
    private Flag redFlag;
    private Flag blueFlag;
    private Array<Bomb> bombs;
    private Array<Spike> spikes;
    private Array<SpawnPoint> bluePlayerSpawns;
    private Array<SpawnPoint> redPlayerSpawns;
    private Array<Button> buttons;
    private Array<Lava> lavas;
    private Array<PowerUp> powerUps;
    private Array<Portal> portals;
    private Array<SpeedPad> speedPads;
    //maybe i can fix the spawns by sending the spawn object
    public B2WorldCreator(GameScreen screen) {

        TiledMap map = screen.getMap();
//        create walls object
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Wall(screen, rect, null);
        }
        //create corner triangle pieces
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            new Corner(screen, null, null, poly);
        }

        //create red Flag otherPlayerObjects
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            redFlag = new Flag(screen, null, ellipse, CaptureTheFlagApp.INTERACTIVE_ITEM_BIT, false, true);
        }
        //create blue Flag object
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            blueFlag = new Flag(screen, null, ellipse, CaptureTheFlagApp.INTERACTIVE_ITEM_BIT, true, false);
        }
        //create bomb object
        bombs = new Array<Bomb>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            bombs.add(new Bomb(screen, null, ellipse));
        }
        powerUps = new Array<PowerUp>();
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            powerUps.add(new PowerUp(screen, null, ellipse));
        }
        portals=new Array<Portal>();
        Array<Portal> temp = new Array<>();
        int portalPairSize= ((MapGroupLayer)map.getLayers().get("Portals")).getLayers().getCount();
        for (int i = 0; i <portalPairSize ; i++) {
            for (MapObject object : ((MapGroupLayer) map.getLayers().get("Portals")).getLayers().get(i).getObjects().getByType(EllipseMapObject.class)) {

                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                temp.add(new Portal(screen, null, ellipse));
                if (temp.size ==2) {
                    temp.get(0).setPair(temp.get(1));
                    temp.get(1).setPair(temp.get(0));
                    for (Portal portal : temp) {

                        portals.add(portal);
                    }
                    temp.clear();
                }
            }
        }
        //Neutral SpeedPad
//        System.out.println(((MapGroupLayer)map.getLayers().get("SpeedPad")).getLayers().get(0).getObjects().getCount());
        speedPads=new Array<SpeedPad>();
        for (MapObject object : ((MapGroupLayer) map.getLayers().get("SpeedPad")).getLayers().get("SpeedPadNeutral").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

            speedPads.add(new SpeedPad(screen, null, ellipse,"speedpad.png",false,false));
        }
        //Red SpeedPad
        for (MapObject object : ((MapGroupLayer) map.getLayers().get("SpeedPad")).getLayers().get("SpeedPadRed").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

            speedPads.add(new SpeedPad(screen, null, ellipse,"speedpadred.png",false,true));
        }
        //Blue SpeedPad
        for (MapObject object : ((MapGroupLayer) map.getLayers().get("SpeedPad")).getLayers().get("SpeedPadBlue").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

            speedPads.add(new SpeedPad(screen, null, ellipse,"speedpadblue.png",true,false));
        }
        //create button object
        buttons = new Array<Button>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            buttons.add(new Button(screen, null, ellipse));

        }
        lavas = new Array<Lava>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            lavas.add(new Lava(screen, rectangle, null, CaptureTheFlagApp.USED_BIT, 0));

        }
        for (int i = 0; i < buttons.size; i++) {
            for (int j = 0; j < lavas.size; j++) {
                buttons.get(i).addLava(lavas.get(j));

            }
        }

//        create newPlayers on User
//        blueTeam = new Array<User>();
        bluePlayerSpawns = new Array<SpawnPoint>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bluePlayerSpawns.add(new SpawnPoint(screen, rect, null));
        }
        redPlayerSpawns = new Array<SpawnPoint>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            redPlayerSpawns.add(new SpawnPoint(screen, rect, null));
        }
        spikes = new Array<Spike>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            spikes.add(new Spike(screen, null, ellipse));
        }
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

    public Array<PowerUp> getPowerUps() {
        return powerUps;
    }
    public Array<Portal> getPortals() {
        return portals;
    }
    public Array<SpeedPad>getSpeedPads(){
        return speedPads;
    }

}
