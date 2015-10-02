package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Andy on 8/22/2015.
 */
    public class MainMenuScreen implements Screen {

        MyGdxGame game;

        private Texture icon;
        private Texture start_pic;
        private Texture title;
        private SpriteBatch batch;
        private Vector2 centerOfScreen;
        private float icon_offset_x;
        private float title_offset_y;
        private float start_offset_y;
        private float ballipedia_offset_y;

        SimpleButton start_game;
        SimpleButton my_profile;
        SimpleButton ballipedia;

        private Vector2 profile_button_pos = new Vector2(30,30);
        private float button_h = 100;
        private float button_w = 500;
        private Vector2 start_pos = new Vector2(30,40);
        private Vector2 ballipedia_pos = new Vector2(30,20);

        public MainMenuScreen(MyGdxGame game){

            centerOfScreen = new Vector2();
            this.game = game;


            icon = new Texture("icon6.png");
            title = new Texture("zany_balls_title.png");
            batch = new SpriteBatch();
            centerOfScreen.x = Gdx.graphics.getWidth() / 2;
            centerOfScreen.y = Gdx.graphics.getHeight() / 2;
            icon_offset_x = centerOfScreen.x - (Gdx.graphics.getWidth() / 3.5f);
            title_offset_y = centerOfScreen.y + (Gdx.graphics.getHeight() / 3.5f);
            start_offset_y = centerOfScreen.y - (Gdx.graphics.getHeight() / 6f);
            ballipedia_offset_y = centerOfScreen.y - (Gdx.graphics.getHeight() / 3.5f);


            start_pos.x = Gdx.graphics.getWidth() * ( start_pos.x / 100);
            start_pos.y = Gdx.graphics.getHeight() * ( start_pos.y / 100);

            ballipedia_pos.x = Gdx.graphics.getWidth() * ( ballipedia_pos.x / 100);
            ballipedia_pos.y = Gdx.graphics.getHeight() * ( ballipedia_pos.y / 100);

            profile_button_pos.x = Gdx.graphics.getWidth() * ( profile_button_pos.x / 100);
            profile_button_pos.y = Gdx.graphics.getHeight() * ( profile_button_pos.y / 100);

            start_game = new SimpleButton(new Texture("startgame2.png"), start_pos.x ,
                    start_pos.y, button_w, button_h);

            my_profile = new SimpleButton(new Texture("button_for_profile.png"),
                    profile_button_pos.x, profile_button_pos.y, button_w,button_h );

            ballipedia = new SimpleButton(new Texture("ballipedia2.png"), ballipedia_pos.x ,
                    ballipedia_pos.y, button_w, button_h);

        }

        @Override
        public void render (float delta){
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            start_game.update(batch, Gdx.input.getX(), Gdx.input.getY(), delta);
            my_profile.update(batch,Gdx.input.getX(), Gdx.input.getY(),delta);
            ballipedia.update(batch, Gdx.input.getX(), Gdx.input.getY(),delta);

            if(start_game.pressed){
                //Gdx.app.log("mytag" , "now switching to countdown screen...");
                game.setScreen(game.levelSelectScreen);
            }
            else if(my_profile.pressed){
                game.setScreen(game.profileScreen);
            }
            else if(ballipedia.pressed){

                game.setScreen(game.ballipediaScreen);
            }
            batch.draw(icon, icon_offset_x, centerOfScreen.y);
            batch.draw(title, icon_offset_x,title_offset_y);
            batch.end();
        }

        @Override
        public void resize(int w, int h){

        }

        @Override
        public void show(){
            ballipedia.reset();
            start_game.reset();
            my_profile.reset();
        }

        @Override
        public void hide(){

        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
            // never called automatically

        }


    }
