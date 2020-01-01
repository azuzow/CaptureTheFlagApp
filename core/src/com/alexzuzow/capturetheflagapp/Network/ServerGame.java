package com.alexzuzow.capturetheflagapp.Network;

import com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser;
import com.alexzuzow.capturetheflagapp.Listeners.ServerGameListener;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

public class ServerGame {
    String IPConnection = "3.132.69.26";
    private int port = 25565;
    public Server server;

    private HashMap userList;

    public ServerGame() throws IOException {

        server = new Server();
        KryoHelper.registerClasses(server);
        server.addListener(new ServerGameListener(this));
        server.bind(port);
        userList = new HashMap<Integer, BufferAddNewUser>();
        server.start();
    }
    public HashMap<Integer,BufferAddNewUser> getUserList(){
     return userList;
    }

    
    public static void main(String[] args) throws IOException {
        new ServerGame();

    }
}
