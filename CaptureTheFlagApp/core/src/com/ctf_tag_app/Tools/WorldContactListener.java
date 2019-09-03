package com.ctf_tag_app.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Sprites.Flag;
import com.ctf_tag_app.Sprites.InteractiveTileObject;
import com.ctf_tag_app.Sprites.Player;
import com.ctf_tag_app.Sprites.Player1;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        if(FixA==null|| FixB==null){
            System.out.println("ONE FIXTURE IS NULL");
            return;
        }

        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        switch (cDef) {
            //Red Player picks up Blue Flag
            case (CaptureTheFlagApp.RED_PLAYER_BIT | CaptureTheFlagApp.BLUE_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {
                    ((Player1) FixA.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixB.getUserData().toString()));
                } else {
                    ((Player1) FixB.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Blue Player picks up Red Flag
            case (CaptureTheFlagApp.BLUE_PLAYER_BIT | CaptureTheFlagApp.RED_FLAG_BIT):
                System.out.println(FixA.getUserData());
                System.out.println(FixA.getFilterData().categoryBits);
                System.out.println(FixB.getUserData());
                System.out.println(FixB.getFilterData().categoryBits);
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {
                    ((Player1) FixA.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixB.getUserData().toString()));
                } else {
                    ((Player1) FixB.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Blue Player hits bomb
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.BOMB_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                } else {
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Red Player hits bomb
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BOMB_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                } else {
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Red Flag Carrier Captures the flag
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.RED_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {
                    ((Player1) FixA.getUserData()).onFlagCapture();
                    ((Flag)FixB.getUserData()).flagCaptured(FixB.getUserData().toString());
                } else {
                    ((Player1) FixB.getUserData()).onFlagCapture();
                    ((Flag)FixA.getUserData()).flagCaptured(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BLUE_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {
                    ((Player1) FixA.getUserData()).onFlagCapture();
                    ((Flag)FixB.getUserData()).flagCaptured(FixB.getUserData().toString());
                } else {
                    ((Player1) FixB.getUserData()).onFlagCapture();
                    ((Flag)FixA.getUserData()).flagCaptured(FixB.getUserData().toString());

                }
                return;
                // check to see if player is inside a spawn area
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BLUE_FLAG_CARRIER):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((Player1)FixB.getUserData()).onPlayerContact();
                } else {
                    ((Player1)FixA.getUserData()).onPlayerContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.RED_FLAG_CARRIER):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((Player1)FixB.getUserData()).onPlayerContact();
                } else {
                    ((Player1)FixA.getUserData()).onPlayerContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((Player1)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Player1)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((Player1)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Player1)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((Player1)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Player1)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((Player1)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Player1)FixB.getUserData()).onSpikeContact();

                }
                return;



        }

    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("end contact: ");
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        if(FixA==null|| FixB==null){
            System.out.println("ONE FIXTURE IS NULL");
            return;
        }

        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        switch (cDef) {
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());

                }
                return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
