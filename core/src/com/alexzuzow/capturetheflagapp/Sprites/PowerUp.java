package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

//randomly pick numbers 1-4 and spawn powerup based on that
public class PowerUp extends InteractiveTileObject {
    Random random = new Random();
    int randomNumber;
    private boolean hasBeenUsed;
    private long respawnTimer;

    public PowerUp(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds, null);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
        randomNumber = random.nextInt(3) + 1;
        hasBeenUsed = true;
        respawnTimer = 0;

    }

    @Override
    public void onContact(String player) {

    }

    @Override
    public void onContact(Object player) {
        User character = (User) player;
        if(!character.hasUsedPowerUp()) {
            hasBeenUsed = true;
            respawnTimer = System.currentTimeMillis();

            System.out.println("Button pressed " + character.getClinetID());
            character.setPowerUp(randomNumber);

            getCell().setTile(map.getTileSets().getTile(141));
        }
    }

    @Override
    public void onExit(String player) {

    }

    @Override
    public boolean isBlueTeam() {
        return false;
    }

    @Override
    public boolean isRedTeam() {
        return false;
    }


    public void update(float dt) {
        if (hasBeenUsed) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - respawnTimer) / 1000 >= 60) {
                setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
                randomNumber = random.nextInt(3) + 1;
                if (randomNumber == 1) {
                    getCell().setTile(map.getTileSets().getTile(77));
                } else if (randomNumber == 2) {

                    getCell().setTile(map.getTileSets().getTile(93));
                } else if (randomNumber == 3){
                    getCell().setTile(map.getTileSets().getTile(109));
                }

                hasBeenUsed = false;
            }
        }
    }
}
