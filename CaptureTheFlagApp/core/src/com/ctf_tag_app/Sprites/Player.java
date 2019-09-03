package com.ctf_tag_app.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class Player extends Sprite {
    public World world;
    public Body b2Body;
    float playerXCoord;
    float playerYCoord;
    private TextureRegion playerBody;
    private Fixture fixture;

    private boolean hasFlag;

    public Player(GameScreen screen, float playerXCoord, float playerYCoord) {
        super(screen.getAtlas().findRegion("RedPlayerSprite"));
        hasFlag = false;
        playerBody = new TextureRegion(getTexture(), 0, 115, 40, 39);
        setBounds(0, 0, 40f / CaptureTheFlagApp.PPM, 40f / CaptureTheFlagApp.PPM);
        setRegion(playerBody);
        this.world = screen.getWorld();
        this.playerXCoord = playerXCoord;
        this.playerYCoord = playerYCoord;
        definePlayer();

    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void onFlagPickup() {
        hasFlag = true;
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.RED_FLAG_CARRIER;
        fixture.setFilterData(filter);

    }

    public void onFlagCapture() {
        hasFlag = false;
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.RED_PLAYER_BIT;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(playerXCoord / CaptureTheFlagApp.PPM, playerYCoord / CaptureTheFlagApp.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);
        b2Body.setLinearDamping(0.5f);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(17 / CaptureTheFlagApp.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = CaptureTheFlagApp.RED_PLAYER_BIT;
        fdef.filter.maskBits = CaptureTheFlagApp.WALL_BIT |
                CaptureTheFlagApp.BLUE_FLAG_BIT |
                CaptureTheFlagApp.BOMB_BIT | CaptureTheFlagApp.SPIKE_BIT | CaptureTheFlagApp.BUTTON_BIT
                | CaptureTheFlagApp.BLUE_PLAYER_BIT | CaptureTheFlagApp.RED_PLAYER_BIT |
                CaptureTheFlagApp.RED_FLAG_BIT|CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BLUE_FLAG_CARRIER;
        fixture = b2Body.createFixture(fdef);
        fixture.setUserData(this);

    }

    public float getFlagSpriteX() {
        return b2Body.getPosition().x - getWidth() / 2;
    }

    public float getFlagSpriteY() {
        return b2Body.getPosition().y - getHeight() / 2;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

}
