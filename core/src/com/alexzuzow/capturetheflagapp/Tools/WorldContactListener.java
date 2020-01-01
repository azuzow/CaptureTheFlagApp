package com.alexzuzow.capturetheflagapp.Tools;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;

import com.alexzuzow.capturetheflagapp.Sprites.Bomb;
import com.alexzuzow.capturetheflagapp.Sprites.Button;
import com.alexzuzow.capturetheflagapp.Sprites.Flag;
import com.alexzuzow.capturetheflagapp.Sprites.InteractiveTileObject;
import com.alexzuzow.capturetheflagapp.Sprites.Character;
import com.alexzuzow.capturetheflagapp.Sprites.Lava;
import com.alexzuzow.capturetheflagapp.Sprites.Portal;
import com.alexzuzow.capturetheflagapp.Sprites.PowerUp;
import com.alexzuzow.capturetheflagapp.Sprites.SpawnPoint;
import com.alexzuzow.capturetheflagapp.Sprites.SpeedPad;
import com.alexzuzow.capturetheflagapp.Sprites.Spike;
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
        if (FixA == null || FixB == null) {
            System.out.println("======================================================");
            System.out.println("ONE FIXTURE IS NULL");
            System.out.println("======================================================");
            return;
        }


        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        switch (cDef) {
            //player on player contact
            case (CaptureTheFlagApp.PLAYER_BIT | CaptureTheFlagApp.PLAYER_BIT):
                //both Fixtures are blue team
                if (((Character) FixA.getUserData()).isBlueTeam() && ((Character) FixB.getUserData()).isBlueTeam()) {

                }
                //both fixtures are red team
                else if (((Character) FixA.getUserData()).isRedTeam() && ((Character) FixB.getUserData()).isRedTeam()) {

                }
                //fixtures are different teams
                else {
                    if (((Character) FixA.getUserData()).hasFlag()) {
                        //blue flag carrier contact with red player
                        ((Character) FixA.getUserData()).onPlayerContact();

                    }
                    if (((Character) FixB.getUserData()).hasFlag()) {
                        ((Character) FixB.getUserData()).onPlayerContact();
                    }

                }
                return;
            //player on trap contact
            case (CaptureTheFlagApp.PLAYER_BIT | CaptureTheFlagApp.TRAP_BIT):
                //FixA is player FixB is Trap
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.PLAYER_BIT) {

                    if (((Character) FixA.getUserData()).isBlueTeam() && ((Lava) FixB.getUserData()).isRedTeam()) {
                        ((Character) FixA.getUserData()).onLavaContact();
                    } else if (((Character) FixA.getUserData()).isRedTeam() && ((Lava) FixB.getUserData()).isBlueTeam()) {
                        ((Character) FixA.getUserData()).onLavaContact();
                    }
                    //FixB is Player FixA is Trap
                } else {
                    if (((Character) FixB.getUserData()).isBlueTeam() && ((Lava) FixA.getUserData()).isRedTeam()) {
                        ((Character) FixB.getUserData()).onLavaContact();
                    } else if (((Character) FixB.getUserData()).isRedTeam() && ((Lava) FixA.getUserData()).isBlueTeam()) {
                        ((Character) FixB.getUserData()).onLavaContact();
                    }

                }
                return;

            case (CaptureTheFlagApp.PLAYER_BIT | CaptureTheFlagApp.INTERACTIVE_ITEM_BIT):
                //FixA is player
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.PLAYER_BIT) {
                    boolean isBlueTeam = ((Character) FixA.getUserData()).isBlueTeam();
                    boolean isRedTeam = ((Character) FixA.getUserData()).isRedTeam();
                    boolean hasFlag = ((Character) FixA.getUserData()).hasFlag();
                    if (FixB.getUserData() instanceof Bomb) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                    } else if (FixB.getUserData() instanceof Button) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData()));

                    } else if (FixB.getUserData() instanceof SpawnPoint) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));

                    } else if (FixB.getUserData() instanceof Flag) {
                        //flag is blue and blue player has flag
                        if (((InteractiveTileObject) FixB.getUserData()).isBlueTeam() && hasFlag && isBlueTeam) {
                            ((Character) FixA.getUserData()).onFlagCapture();
                            ((Flag) FixB.getUserData()).flagCaptured(FixA.getUserData().toString());
                        } else if (((InteractiveTileObject) FixB.getUserData()).isRedTeam() && hasFlag && isRedTeam) {
                            ((Character) FixA.getUserData()).onFlagCapture();
                            ((Flag) FixB.getUserData()).flagCaptured(FixA.getUserData().toString());
                        } else if (((InteractiveTileObject) FixB.getUserData()).isRedTeam() && (!hasFlag) && isBlueTeam) {
                            ((Character) FixA.getUserData()).onFlagPickup();
                            ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));
                        } else if (((InteractiveTileObject) FixB.getUserData()).isBlueTeam() && (!hasFlag) && isRedTeam) {
                            ((Character) FixA.getUserData()).onFlagPickup();
                            ((InteractiveTileObject) FixB.getUserData()).onContact((FixA.getUserData().toString()));
                        }
                    } else if (FixB.getUserData() instanceof Spike) {
                        ((Character) FixA.getUserData()).onSpikeContact();
                    }
                    else if (FixB.getUserData() instanceof PowerUp) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact(FixA.getUserData());
                    }
                    else if (FixB.getUserData() instanceof Portal) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact(FixA.getUserData());
                    }
                    else if (FixB.getUserData() instanceof SpeedPad) {
                        ((InteractiveTileObject) FixB.getUserData()).onContact(FixA.getUserData());
                    }
                }
//                FixB is player
                else {
                    boolean isBlueTeam = ((Character) FixB.getUserData()).isBlueTeam();
                    boolean isRedTeam = ((Character) FixB.getUserData()).isRedTeam();
                    boolean hasFlag = ((Character) FixB.getUserData()).hasFlag();
                    if (FixA.getUserData() instanceof Bomb) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                    } else if (FixA.getUserData() instanceof Button) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData()));

                    } else if (FixA.getUserData() instanceof SpawnPoint) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));

                    } else if (FixA.getUserData() instanceof Flag) {
                        //flag is blue and blue player has flag
                        if (((InteractiveTileObject) FixA.getUserData()).isBlueTeam() && hasFlag && isBlueTeam) {
                            ((Character) FixB.getUserData()).onFlagCapture();
                            ((Flag) FixA.getUserData()).flagCaptured(FixB.getUserData().toString());
                        } else if (((InteractiveTileObject) FixA.getUserData()).isRedTeam() && hasFlag && isRedTeam) {
                            ((Character) FixB.getUserData()).onFlagCapture();
                            ((Flag) FixA.getUserData()).flagCaptured(FixB.getUserData().toString());
                        } else if (((InteractiveTileObject) FixA.getUserData()).isRedTeam() && (!hasFlag) && isBlueTeam) {
                            ((Character) FixB.getUserData()).onFlagPickup();
                            ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));
                        } else if (((InteractiveTileObject) FixA.getUserData()).isBlueTeam() && (!hasFlag) && isRedTeam) {
                            ((Character) FixB.getUserData()).onFlagPickup();
                            ((InteractiveTileObject) FixA.getUserData()).onContact((FixB.getUserData().toString()));
                        }
                    } else if (FixA.getUserData() instanceof Spike) {
                        ((Character) FixB.getUserData()).onSpikeContact();
                    }
                    else if (FixA.getUserData() instanceof PowerUp) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact(FixB.getUserData());
                    }
                    else if (FixA.getUserData() instanceof Portal) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact(FixB.getUserData());
                    }
                    else if (FixA.getUserData() instanceof SpeedPad) {
                        ((InteractiveTileObject) FixA.getUserData()).onContact(FixB.getUserData());
                    }

                }return;

        }


    }

    @Override
    public void endContact(Contact contact) {
//        System.out.println("end contact: ");
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        if (FixA == null || FixB == null) {
            System.out.println("ONE FIXTURE IS NULL");
            return;
        }

        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;
        switch(cDef){
            case(CaptureTheFlagApp.PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.PLAYER_BIT) {

                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixA.getUserData().toString());
                } else {
                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixB.getUserData().toString());

                }
                return;
            case(CaptureTheFlagApp.PLAYER_BIT|CaptureTheFlagApp.INTERACTIVE_ITEM_BIT):
                //FixA is player
                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.PLAYER_BIT) {
                    //sending button object info of player in contact
                    if(FixB.getUserData() instanceof Button){

                    ((InteractiveTileObject) FixB.getUserData()).onExit(FixA.getUserData().toString());

                } else if(FixB.getUserData() instanceof SpawnPoint){
                    ((InteractiveTileObject) FixB.getUserData()).onExit((FixB.getUserData().toString()));

                }


                }
                //FixB is player
                else{
                    if(FixA.getUserData() instanceof Button){

                        ((InteractiveTileObject) FixA.getUserData()).onExit(FixB.getUserData().toString());

                    } else if(FixA.getUserData() instanceof SpawnPoint){
                        ((InteractiveTileObject) FixA.getUserData()).onExit((FixB.getUserData().toString()));

                    }

                }
                return;

        }
//        switch (cDef) {
//            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
//                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_FLAG_CARRIER) {
//
//                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
//                } else {
//                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());
//
//                }
//                return;
//            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
//                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BLUE_PLAYER_BIT) {
//
//                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
//                } else {
//                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());
//
//                }
//                return;
//            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.SPAWN_BIT):
//                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_FLAG_CARRIER) {
//
//                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
//                } else {
//                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());
//
//                }
//                return;
//            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.SPAWN_BIT):
//                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.RED_PLAYER_BIT) {
//
//                    ((InteractiveTileObject)FixB.getUserData()).onExit(FixB.getUserData().toString());
//
//                } else {
//                    ((InteractiveTileObject)FixA.getUserData()).onExit(FixA.getUserData().toString());
//
//                }return;
//            case(CaptureTheFlagApp.BLUE_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
//            case(CaptureTheFlagApp.RED_PLAYER_BIT|CaptureTheFlagApp.BUTTON_BIT):
//            case(CaptureTheFlagApp.BLUE_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):
//            case(CaptureTheFlagApp.RED_FLAG_CARRIER|CaptureTheFlagApp.BUTTON_BIT):
//
//                if (FixA.getFilterData().categoryBits == CaptureTheFlagApp.BUTTON_BIT) {
//                    //sending button object info of player in contact
//                    ((InteractiveTileObject) FixA.getUserData()).onExit(FixB.getUserData().toString());
//
//                } else {
//                    ((InteractiveTileObject) FixB.getUserData()).onExit((FixA.getUserData().toString()));
//
//                }return;
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
