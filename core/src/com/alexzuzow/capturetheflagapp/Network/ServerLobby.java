package com.alexzuzow.capturetheflagapp.Network;


import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerConnect;
import com.alexzuzow.capturetheflagapp.Listeners.ServerLobbyListener;
import com.alexzuzow.capturetheflagapp.Screens.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ServerLobby extends Stage {

    private Skin skin;
    private Server server;
    private Label titleLabel, playersLabel, portLabel;
    private TextField textField;
    private TextButton okTextButton;
    private ServerLobbyListener serverLobbyListener;
    private final MainMenuScreen main;
    private com.alexzuzow.capturetheflagapp.Network.KryoHelper KryoHelper;

    public ServerLobby(final MainMenuScreen main, Skin skin, Viewport viewport) {
        super(viewport);
        Gdx.input.setInputProcessor(this);
        this.skin = skin;
        this.main = main;
        titleLabel = new Label("SELECT PORT", skin, "small");
        titleLabel.setPosition(410.0f, 510.0f);
        addActor(titleLabel);

        textField = new TextField("", skin);
        textField.setBounds(510.0f, 360.0f, 340.0f, 80.0f);
        addActor(textField);

        okTextButton = new TextButton("OK", skin, "small");
        okTextButton.setBounds(510.0f, 200.0f, 340.0f, 110.0f);
        okTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!textField.getText().equals(""))
                    try {
                        selectPort(Integer.parseInt(textField.getText()));
                    } catch (NumberFormatException e) {
                        Gdx.app.log("Error", "Invalid port.");
                    }
                super.clicked(event, x, y);
            }
        });
        addActor(okTextButton);

    }

    private void selectPort(int port) {
        server = new Server();

        Kryo kryo = server.getKryo();
        KryoHelper.registerClasses(server);

        server.start();
        try {
            server.bind(port);
        } catch (IOException e) {
            Gdx.app.log("Error", "Port " + port + " is invalid.\n");
            e.printStackTrace();
        }

        okTextButton.remove();
        textField.remove();
        titleLabel.setText("ONLINE PLAYERS");
        titleLabel.setPosition(380.0f, 510.0f);
        playersLabel = new Label("0", skin, "small");
        playersLabel.setPosition(660.0f, 390.0f);
        addActor(playersLabel);
        portLabel = new Label("PORT: " + port, skin, "small");
        portLabel.setPosition(475.0f, 100.0f);
        addActor(portLabel);

        serverLobbyListener = new ServerLobbyListener(this);
        server.addListener(serverLobbyListener);
    }

    public void connect() {
//        playersLabel.setText(Integer.toString(server.getConnections().length));
//        BufferPlayerConnect buffer = new BufferPlayerConnect();
//        if (buffer.player == null) {
////            buffer.player = server.getConnections().length - 1;
//            for (int i = 0; i < server.getConnections().length; i++) {
//                server.getConnections()[i].sendTCP(buffer);
//            }
//        }
//        Gdx.app.log("PositionServer", "Game Start");
//        Gdx.app.postRunnable(new Runnable() {
//            public void run() {
//                removeListener(serverLobbyListener);
//                //UNDO COMMENT
////                main.changeStage(new ServerGame(main, skin, getViewport(), server));
//            }
//        });
    }
//        if(server.getConnections().length == 2)
//        {
//
//            buffer.player = 2;
//
//            buffer.player = 1;
//            server.getConnections()[1].sendTCP(buffer);
//
//            Gdx.app.log("PositionServer", "Game Start");
//            Gdx.app.postRunnable(new Runnable() {
//                public void run() {
//                    removeListener(serverLobbyListener);
//                    main.changeStage(new ServerGame(main, skin, getViewport(), server));
//                }
//            });
//        }
//    }

    public void disconnect() {
        playersLabel.setText(Integer.toString(server.getConnections().length));
    }

    public void removeListener(Listener listener) {
        server.removeListener(listener);
    }

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
    }
}