package com.alexzuzow.capturetheflagapp.Screens;


import com.alexzuzow.capturetheflagapp.Buffers.BufferAddExistingUsers;
import com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerUpdate;
import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Scenes.Hud;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.alexzuzow.capturetheflagapp.Sprites.Bomb;
import com.alexzuzow.capturetheflagapp.Sprites.SpawnPoint;
import com.alexzuzow.capturetheflagapp.Tools.B2WorldCreator;
import com.alexzuzow.capturetheflagapp.Tools.WorldContactListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.HashMap;


public class GameScreen extends ApplicationAdapter implements Screen{

    private CaptureTheFlagApp game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Sprite redFlagSprite;
    private Texture redFlagSpriteTexture;
    private Sprite blueFlagSprite;
    private Texture blueFlagSpriteTexture;

    private int redTeamSize;
    private int blueTeamSize;

    public GameScreen(CaptureTheFlagApp game) {

        redTeamSize = 0;
        blueTeamSize = 0;
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(CaptureTheFlagApp.vWidth / CaptureTheFlagApp.PPM, CaptureTheFlagApp.vHeight / CaptureTheFlagApp.PPM);
        gamePort.apply();
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("firstMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / CaptureTheFlagApp.PPM);
        gameCam.zoom += 4;
        gameCam.setToOrtho(false, gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener());
        blueFlagSpriteTexture = new Texture(Gdx.files.internal("BlueFlagSprite.png"));
        blueFlagSprite = new Sprite(blueFlagSpriteTexture, 0, 0, 40, 40);
        blueFlagSprite.setBounds(0, 0, 40f / CaptureTheFlagApp.PPM, 40f / CaptureTheFlagApp.PPM);
        redFlagSpriteTexture = new Texture(Gdx.files.internal("RedFlagSprite.png"));
        redFlagSprite = new Sprite(redFlagSpriteTexture, 0, 0, 40, 40);
        redFlagSprite.setBounds(0, 0, 40f / CaptureTheFlagApp.PPM, 40f / CaptureTheFlagApp.PPM);
    }


    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        update(delta);
        //render game map
        renderer.render();
        //render box2d debug lines
        b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        for (HashMap.Entry<Integer, User> entry : CaptureTheFlagApp.playerList.entrySet()) {
            if (entry.getValue().isAlive()) {
                entry.getValue().draw(game.batch);

                if (entry.getValue().hasFlag() && entry.getValue().getPlayerBit() == 2) {
                    redFlagSprite.draw(game.batch);

                } else if (entry.getValue().hasFlag() && entry.getValue().getPlayerBit() == 256) {
                    blueFlagSprite.draw(game.batch);
                }
            }

        }


        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }
//on new player create json data object then send all the user info
    // this way whenever a new player joins every player who is already connected
    // will send all their data so they can create all the previous players


    public void handleInput(float dt) {
        if(CaptureTheFlagApp.playerList.containsKey(CaptureTheFlagApp.clientID)) {
            if (!CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).isAlive()) {
                CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).b2Body.setLinearVelocity(0, 0);
            } else {

                float x =(hud  .getTouchPad().getKnobPercentX()*45);
                float y = (hud  .getTouchPad().getKnobPercentY()*45);
                CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).b2Body.applyForce(new Vector2(x * dt, y*dt), CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).b2Body.getWorldCenter(), true);
//


            }
        }
    }

    public void update(float dt) {
        //add players that already exist and new players that connect

        for (User disconnectedPlayer: CaptureTheFlagApp.disconectedPlayers){

            if(disconnectedPlayer.isDestroyed()){
                CaptureTheFlagApp.playerList.remove(disconnectedPlayer.getClinetID());
                disconnectedPlayer.dispose();
                CaptureTheFlagApp.disconectedPlayers.removeValue(disconnectedPlayer,true);

            }
        }
        for (BufferAddNewUser player : CaptureTheFlagApp.newPlayers) {

            if (!CaptureTheFlagApp.playerList.containsKey(player.clientID)) {

                Texture playerSprite;
                if (player.playerCategoryBit == 2) {
                    playerSprite = new Texture(Gdx.files.internal("BluePlayerSprite.png"));
                } else {
                    playerSprite = new Texture(Gdx.files.internal("RedPlayerSprite.png"));
                }

                User newPlayer = new User(this, player.xCoord, player.yCoord, player.playerCategoryBit, player.carrierCategoryBit, playerSprite);
                newPlayer.setClinetID(player.clientID);
                CaptureTheFlagApp.playerList.put(player.clientID, newPlayer);

            }
        }
        //possible problem maybe players connect during this process and then it removes them so it never reognizes them
        //possible fix make another vector of players added and then remove them from new players list using their id maybe make map for players
        CaptureTheFlagApp.newPlayers.clear();

        handleInput(dt);
        //if character has flag and is alive
        for (HashMap.Entry<Integer, User> player : CaptureTheFlagApp.playerList.entrySet()) {
//            System.out.println("(X,Y): "+player.getValue().getxCoord()+" "+player.getValue().getyCoord());

            if (player.getValue().getxCoord() < 100 && player.getValue().getClinetID() != CaptureTheFlagApp.clientID) {
                player.getValue().setBodyPosition(player.getValue().getxCoord(), player.getValue().getyCoord());
            }
            if (player.getValue().hasMoved()) {
                BufferPlayerUpdate updatePlayer = new BufferPlayerUpdate();
                updatePlayer.clientID = player.getKey();
                updatePlayer.xCoord = (player.getValue().getxCoord());
                updatePlayer.yCoord = player.getValue().getyCoord();
                updatePlayer.playerCategoryBit = player.getValue().getPlayerCategoryBit();
                updatePlayer.carrierCategoryBit = player.getValue().getCarrierCategoryBit();
                updatePlayer.hasFlag = player.getValue().getHasFlag();
                updatePlayer.alive = player.getValue().isAlive();
                updatePlayer.respawnTimer = player.getValue().getRespawnTimer();
                updatePlayer.previousLocation = player.getValue().getPreviousLocation();
                updatePlayer.setToDestroy = player.getValue().isSetToDestroy();
                updatePlayer.destroyed = player.getValue().isDestroyed();
                updatePlayer.linearVelocity = player.getValue().b2Body.getLinearVelocity();
                CaptureTheFlagApp.client.sendTCP(updatePlayer);
            }
            if (player.getValue().isAlive()) {

                player.getValue().update(0, 0);
                if (player.getValue().hasFlag()) {
                    if (player.getValue().getPlayerBit() == 2) {

                        redFlagSprite.setPosition(player.getValue().getFlagSpriteX() + 0.3f, player.getValue().getFlagSpriteY() + 0.3f);
                    } else {
                        blueFlagSprite.setPosition(player.getValue().getFlagSpriteX() + 0.3f, player.getValue().getFlagSpriteY() + 0.3f);
                    }
                }
            }//if character is dead and has flag reset it
            else {
                if (player.getValue().hasFlag()) {
                    if (player.getValue().getPlayerBit() == 2) {
                        creator.getRedFlag().resetFlag();
                        player.getValue().resetFlag();
                    } else {
                        creator.getBlueFlag().resetFlag();
                        player.getValue().resetFlag();
                    }

                }
                if (player.getValue().getPlayerBit() == 2) {
                    for (SpawnPoint point : creator.getBluePlayerSpawns()) {
                        if (point.isSafeSpawnPoint()) {

                            player.getValue().update(point.getX(), point.getY());
                            break;
                        }
                    }
                } else {
                    for (SpawnPoint point : creator.getRedPlayerSpawns()) {
                        if (point.isSafeSpawnPoint()) {

                            player.getValue().update(point.getX(), point.getY());
                            break;
                        }
                    }
                }


            }


            for (Bomb bomb : creator.getBombs()) {
                bomb.update(dt);
            }
            if (creator.getBlueFlag().wasFlagCaptured()) {
                //update red score +1
                creator.getBlueFlag().resetFlag();
                creator.getRedFlag().resetFlag();
                hud.addBlueScore();
            } else if (creator.getRedFlag().wasFlagCaptured()) {
                //update red score +1
                hud.addRedScore();
                creator.getRedFlag().resetFlag();
                creator.getBlueFlag().resetFlag();

            }
            int counter = 0;


            world.step(1 / 60f, 6, 2);

            hud.update(dt);
        }

        createPlayer();
        gameCam.position.x = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).b2Body.getPosition().x;
        gameCam.position.y = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).b2Body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        redFlagSpriteTexture.dispose();
        blueFlagSpriteTexture.dispose();

        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        for (HashMap.Entry<Integer, User> player : CaptureTheFlagApp.playerList.entrySet()) {
            player.getValue().dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hud.getStage().getViewport().update(width,height);
//        System.out.println("resize X: " + width);
//        System.out.println("resize Y: " + height);

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
    public void show() {

    }


    public void createPlayer() {

        if (!CaptureTheFlagApp.playerList.containsKey(CaptureTheFlagApp.clientID)) {
            for (SpawnPoint point : creator.getRedPlayerSpawns()) {

                    System.out.println(point.isSafeSpawnPoint());

            }
            if (CaptureTheFlagApp.redTeamSize <= CaptureTheFlagApp.blueTeamSize) {
                //add player to red team and send info about player to rest of players
                for (SpawnPoint redSpawn : creator.getRedPlayerSpawns()) {
                    if (redSpawn.isSafeSpawnPoint()) {

                        Texture playerSprite = new Texture(Gdx.files.internal("RedPlayerSprite.png"));

                        CaptureTheFlagApp.playerList.put(CaptureTheFlagApp.clientID, new User(this, redSpawn.getX() * CaptureTheFlagApp.PPM, redSpawn.getY() * CaptureTheFlagApp.PPM,
                                CaptureTheFlagApp.RED_PLAYER_BIT, CaptureTheFlagApp.RED_FLAG_CARRIER, playerSprite));
                        CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).setClinetID(CaptureTheFlagApp.clientID);
//                        CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).setxCoord(redSpawn.getX()/CaptureTheFlagApp.PPM);
//                        CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).setyCoord(redSpawn.getY()/CaptureTheFlagApp.PPM);
                        redTeamSize++;
                        break;
                    }
                }
            } else {
                //add player to blue team and send info about player to rest of players
                for (SpawnPoint blueSpawn : creator.getBluePlayerSpawns())

                    if (blueSpawn.isSafeSpawnPoint()) {

                        Texture playerSprite = new Texture(Gdx.files.internal("BluePlayerSprite.png"));
                        CaptureTheFlagApp.playerList.put(CaptureTheFlagApp.clientID, new User(this, blueSpawn.getX() * CaptureTheFlagApp.PPM, blueSpawn.getY() * CaptureTheFlagApp.PPM,
                                CaptureTheFlagApp.BLUE_PLAYER_BIT, CaptureTheFlagApp.BLUE_FLAG_CARRIER, playerSprite));
                        CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).setClinetID(CaptureTheFlagApp.clientID);

                        blueTeamSize++;
                        break;
                    }
            }
            BufferAddNewUser newPlayerServer = new BufferAddNewUser();
            newPlayerServer.clientID = CaptureTheFlagApp.clientID;
            newPlayerServer.xCoord = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getxCoord();
            newPlayerServer.yCoord = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getyCoord();
            newPlayerServer.playerCategoryBit = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getPlayerCategoryBit();
            newPlayerServer.carrierCategoryBit = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getCarrierCategoryBit();
            newPlayerServer.hasFlag = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getHasFlag();
            newPlayerServer.alive = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).isAlive();
            newPlayerServer.respawnTimer = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getRespawnTimer();
            newPlayerServer.previousLocation = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).getPreviousLocation();
            newPlayerServer.setToDestroy = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).isSetToDestroy();
            newPlayerServer.destroyed = CaptureTheFlagApp.playerList.get(CaptureTheFlagApp.clientID).isDestroyed();
            CaptureTheFlagApp.client.sendTCP(newPlayerServer);

        }

    }
}
