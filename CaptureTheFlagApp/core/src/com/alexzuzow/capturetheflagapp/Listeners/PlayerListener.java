package com.alexzuzow.capturetheflagapp.Listeners;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector2;


    public class PlayerListener implements GestureListener {

        User player;

        public PlayerListener(User player) {
            this.player = player;
        }

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {

            // call CaptureTheFlag and find player in list of players and apply force to them

//            if(velocityX > velocityY)
//                if(velocityX > 0)
//                    player.();
//                    CaptureTheFlagApp.playerList
//                else
////                    player.moveLeft();
//            else
//            if(velocityY > 0)
////                player.();
//            else
////                player.moveDown();
            return true;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void pinchStop() {

        }
    }
