package com.alexzuzow.capturetheflagapp.Tools;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;

import com.alexzuzow.capturetheflagapp.Sprites.Flag;
import com.alexzuzow.capturetheflagapp.Sprites.InteractiveTileObject;
import com.alexzuzow.capturetheflagapp.Sprites.Character;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        if(FixA==null|| FixB==null){
            System.out.println("======================================================");
            System.out.println("ONE FIXTURE IS NULL");
            System.out.println("======================================================");
            return;
        }


        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        if(FixA.getFilterData().categoryBits==4096 | FixB.getFilterData().categoryBits==4096){

        }
        switch (cDef) {
            //Red Player picks up Blue Flag
            case (CaptureTheFlagApp.RED_PLAYER_BIT | CaptureTheFlagApp.BLUE_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {
                    ((Character) FixA.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixB.getUserData().toString()));
                } else {
                    ((Character) FixB.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Blue Player picks up Red Flag
            case (CaptureTheFlagApp.BLUE_PLAYER_BIT | CaptureTheFlagApp.RED_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {
                    ((Character) FixA.getUserData()).onFlagPickup();
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixB.getUserData().toString()));
                } else {
                    ((Character) FixB.getUserData()).onFlagPickup();
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
                    ((Character) FixA.getUserData()).onFlagCapture();
                    ((Flag)FixB.getUserData()).flagCaptured(FixB.getUserData().toString());
                } else {
                    ((Character) FixB.getUserData()).onFlagCapture();
                    ((Flag)FixA.getUserData()).flagCaptured(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BLUE_FLAG_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {
                    ((Character) FixA.getUserData()).onFlagCapture();
                    ((Flag)FixB.getUserData()).flagCaptured(FixB.getUserData().toString());
                } else {
                    ((Character) FixB.getUserData()).onFlagCapture();
                    ((Flag)FixA.getUserData()).flagCaptured(FixB.getUserData().toString());

                }
                return;
                // check to see if player is inside a spawn area
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixA.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixA.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixA.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onContact(FixA.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onContact(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BLUE_FLAG_CARRIER):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((Character)FixB.getUserData()).onPlayerContact();
                } else {
                    ((Character)FixA.getUserData()).onPlayerContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.RED_FLAG_CARRIER):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((Character)FixB.getUserData()).onPlayerContact();
                } else {
                    ((Character)FixA.getUserData()).onPlayerContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((Character)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Character)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPIKE_BIT):

                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((Character)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Character)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((Character)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Character)FixB.getUserData()).onSpikeContact();

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPIKE_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((Character)FixA.getUserData()).onSpikeContact();
                } else {
                    ((Character)FixB.getUserData()).onSpikeContact();

                }
                return;
            //Blue Player hits bomb
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BOMB_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                } else {
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
            //Red Player hits bomb
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BOMB_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                } else {
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                }
                return;
                //Blue player hits red trap
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.RED_TRAP_BIT):
                System.out.println("BLUE PLAYER RED TRAP");
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {

                    ((Character)FixA.getUserData()).onLavaContact();
                } else {
                    ((Character)FixB.getUserData()).onLavaContact();

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BLUE_TRAP_BIT):
                System.out.println("RED PLAYER BLUE TRAP");
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {

                    ((Character)FixA.getUserData()).onLavaContact();
                } else {
                    ((Character)FixB.getUserData()).onLavaContact();

                }
                return;
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.RED_TRAP_BIT):
                System.out.println("BLUE PLAYER RED TRAP");
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {

                    ((Character)FixA.getUserData()).onLavaContact();
                } else {
                    ((Character)FixB.getUserData()).onLavaContact();

                }
                return;
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BLUE_TRAP_BIT):
                System.out.println("RED PLAYER BLUE TRAP");
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {

                    ((Character)FixA.getUserData()).onLavaContact();
                } else {
                    ((Character)FixB.getUserData()).onLavaContact();

                }
                return;
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BUTTON_BIT) {
                    //sending button object info of player in contact
                    ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData()));

                } else {
                    ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData()));

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

                }return;
            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):
            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):

                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BUTTON_BIT) {
                    //sending button object info of player in contact
                    ((InteractiveTileObject) FixA.getUserData()).onExit(FixB.getUserData().toString());

                } else {
                    ((InteractiveTileObject) FixB.getUserData()).onExit((FixA.getUserData().toString()));

                }return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
