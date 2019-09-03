package com.ctf_tag_app.Screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Scenes.Hud;
import com.ctf_tag_app.Sprites.BlueTeam;
import com.ctf_tag_app.Sprites.Bomb;
import com.ctf_tag_app.Sprites.Player;
import com.ctf_tag_app.Sprites.SpawnPoint;
import com.ctf_tag_app.Tools.B2WorldCreator;
import com.ctf_tag_app.Tools.WorldContactListener;


public class GameScreen extends ApplicationAdapter implements Screen {

    private CaptureTheFlagApp game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    //    private Player player;
    private TextureAtlas atlas;
    private B2WorldCreator creator;
    private Sprite redFlagSprite;
    private Texture redFlagSpriteTexture;
    private Sprite blueFlagSprite;
    private Texture blueFlagSpriteTexture;
    private BlueTeam temp;

    public GameScreen(CaptureTheFlagApp game) {
        atlas = new TextureAtlas("spritesheet.txt");

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

//        player = new Player(this, 630f, 800f);
        creator = new B2WorldCreator(this);
        temp = new BlueTeam(this, 630f, 800f, CaptureTheFlagApp.RED_PLAYER_BIT, CaptureTheFlagApp.RED_FLAG_CARRIER, "RedPlayerSprite.png");
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
        for (BlueTeam bluePlayer : creator.getBlueTeam()) {
            if (bluePlayer.isAlive()) {
                bluePlayer.draw(game.batch);
                if (bluePlayer.hasFlag()) {
                    redFlagSprite.draw(game.batch);
                }
            }

        }
        if (temp.hasFlag()) {
            blueFlagSprite.draw(game.batch);
        }
        temp.draw(game.batch);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void handleInput(float dt) {
        Vector2 velocity = temp.b2Body.getLinearVelocity();
        float x = velocity.x;
        float y = velocity.y;

//        System.out.println("X speed: "+velocity.x);
//        System.out.println("Y speed: "+velocity.y);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            temp.b2Body.applyForce(new Vector2(0, 45 * dt), temp.b2Body.getWorldCenter(), true);
//            player.b2Body.applyLinearImpulse(new Vector2(0,4f),player.b2Body.getWorldCenter(),true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            temp.b2Body.applyForce(new Vector2(-45f * dt, 0), temp.b2Body.getWorldCenter(), true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            temp.b2Body.applyForce(new Vector2(0, 45f * dt), temp.b2Body.getWorldCenter(), true);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            temp.b2Body.applyForce(new Vector2(0, -45f * dt), temp.b2Body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            temp.b2Body.applyForce(new Vector2(45f * dt, 0), temp.b2Body.getWorldCenter(), true);
        }


    }

    public void update(float dt) {

        handleInput(dt);
        temp.update(0, 0);
        for (BlueTeam bluePlayer : creator.getBlueTeam()) {
            if (bluePlayer.isAlive()) {
                bluePlayer.update(0, 0);
                if (bluePlayer.hasFlag()) {
                    redFlagSprite.setPosition(bluePlayer.getFlagSpriteX() + 0.3f, bluePlayer.getFlagSpriteY() + 0.3f);
                }
            }
            else {
                if (bluePlayer.hasFlag()) {
                    creator.getRedFlag().resetFlag();
                    bluePlayer.resetHasFlag();
                }
                for (SpawnPoint point : creator.getBluePlayerSpawns()) {
                    if (point.isSafeSpawnPoint()) {
                        System.out.println("POINT IS SAFE");
                        bluePlayer.update(point.getX(), point.getY());
                    }
                }


            }

        }
        for (Bomb bomb : creator.getBombs()) {
            bomb.update(dt);
        }
        if (temp.hasFlag()) {
            blueFlagSprite.setPosition(temp.getFlagSpriteX() + 0.3f, temp.getFlagSpriteY() + 0.3f);
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

        world.step(1 / 60f, 6, 2);
        hud.update(dt);
        gameCam.position.x = temp.b2Body.getPosition().x;
        gameCam.position.y = temp.b2Body.getPosition().y;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        System.out.println("resize X: " + width);
        System.out.println("resize Y: " + height);

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


}
