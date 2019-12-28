package com.alexzuzow.capturetheflagapp;


import com.alexzuzow.capturetheflagapp.Buffers.BufferAddExistingUsers;
import com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser;
import com.alexzuzow.capturetheflagapp.Listeners.ClientGameListener;
import com.alexzuzow.capturetheflagapp.Screens.MainMenuScreen;
import com.alexzuzow.capturetheflagapp.Network.KryoHelper;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;


import java.util.HashMap;
import java.util.Map;

public class CaptureTheFlagApp extends Game {
    public static Map<Integer, User> playerList;
    public static Array<BufferAddNewUser> newPlayers;
    public static Array<User> disconectedPlayers;

    public static  Integer clientID;
    public SpriteBatch batch;
    public static final int vWidth = 400;
    public static final int vHeight = 208;
    public static final float PPM = 100;
    public static final float UPDATE_TIME = 1 / 60f;
    public static float timer;
    public static final short WALL_BIT = 1;
//    public static final short PLAYER_BIT =2;
//    public static final short TRAP_BIT = 4;
//    public static final short INTERACTIVE_ITEM_BIT=8;
//    public static final short USED_BIT=16;
//    public static final short POWERUP_BIT=32;
//
    public static final short BLUE_PLAYER_BIT = 2;
    public static final short RED_FLAG_BIT = 4;
    public static final short BLUE_FLAG_BIT = 8;
    public static final short BOMB_BIT = 16;
    public static final short SPIKE_BIT = 32;
    public static final short BUTTON_BIT = 64;
    public static final short USED_BIT = 128;
    public static final short RED_PLAYER_BIT = 256;
    public static final short RED_FLAG_CARRIER = 512;
    public static final short BLUE_FLAG_CARRIER = 1024;
    public static final short SPAWN_BIT = 2048;
    public static final short RED_TRAP_BIT = 4096;
    public static final short BLUE_TRAP_BIT= 8192;
    public static final short POWER_UP_BIT = 16384;
    //redo collision so that categories are only
    // Player (blue,red)
    // trap(blue,red)
    // spawn
    // ScoreZone
    // InteractiveItem (bomb,Spike,Button,Flag,PowerUp)
    // Flag
    // Used
    // Wall

    public static long totalPlayers;
    public static  Client client;
    private int port = 25565;
    String ipAddress = "localhost";
    private ClientGameListener cgl;
    public static int blueTeamSize=0;
    public static int redTeamSize=0;


    @Override
    public void create() {
        playerList = new HashMap<Integer, User>();
        newPlayers= new Array<BufferAddNewUser>();
        disconectedPlayers=new Array<User>();
        connectClient();
        totalPlayers = 1;



        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();


    }
    @Override
    public void dispose() {
        batch.dispose();

    }

    public void connectClient() {
        client = new Client();
        client.start();

        cgl = new ClientGameListener();
        cgl.init(client);
        KryoHelper.registerClasses(client);
        client.addListener(cgl);

        try {

            int timeout = 5000;
            client.connect(timeout, ipAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        clientID= client.getID();
        System.out.println("=====================================================================================");
        System.out.println("CLIENT ID: "+ clientID);
        System.out.println("=====================================================================================");
//        playerList.put(clientID,null);

    }


}
