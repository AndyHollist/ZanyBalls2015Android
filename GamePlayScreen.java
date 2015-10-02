package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import sun.applet.Main;

/**
 * Created by Andy on 8/22/2015.
 */
public class GamePlayScreen implements Screen {

    MyGdxGame game;

    private SpriteBatch batch;
    private Texture paddle;
    private Texture blue_ball;
    private Texture red_ball;
    ArrayList<Ball> balls;
    Iterator<Ball> ball_iterator;
    private int score;
    private String score_string;
    BitmapFont font;
    private int time;
    private String time_string;
    private int gravity;

    private int PaddleWidth = 200;
    private int PaddleHeight = 200;
    private int PaddleStartCenter = 300;
    private int GameLength = 3000; //ms
    private int topOfScreeen;
    private float rightSide;
    private float wall_adjustment = - Gdx.graphics.getWidth() / 20;

    private SimpleButton back_button;

    private boolean first_time = true;

    //boolean first = true;

    Vector2 PaddlePosition;

    public GamePlayScreen(MyGdxGame game){
        this.game = game;
        topOfScreeen = Gdx.graphics.getHeight();
        rightSide = Gdx.graphics.getWidth() + wall_adjustment;
        Vector2 centerOfScreen = new Vector2();
        centerOfScreen.x = Gdx.graphics.getWidth() / 2;
        centerOfScreen.y = Gdx.graphics.getHeight() / 2;
        float icon_offset_x = centerOfScreen.x - (Gdx.graphics.getWidth() / 3.5f);
        float title_offset_y = centerOfScreen.y + (Gdx.graphics.getHeight() / 3.5f);
        float start_offset_y = centerOfScreen.y + (Gdx.graphics.getHeight() / 3.0f);
        back_button = new SimpleButton(new Texture("back_button.png" ),
                icon_offset_x , start_offset_y , 400, 200);
        //Gdx.app.log("mytag", "constructed gameplay!");
        //System.out.print("first time!");
        batch = new SpriteBatch();
        paddle = new Texture("paddle.PNG");
        blue_ball = new Texture("blue_ball.PNG");
        red_ball = new Texture( "red_ball.PNG");
        PaddlePosition = new Vector2(PaddleStartCenter,PaddleHeight);

        Ball ball1 = new Ball(100,topOfScreeen, -10 + (int)(Math.random() * ((20) + 1)),
                -10 + (int)(Math.random() * ((5) + 1))	,Color.BLUE, true);
        Ball ball2 = new Ball(500,topOfScreeen,-10 + (int)(Math.random() * ((20) + 1)) ,
                -10 + (int)(Math.random() * ((5) + 1)) ,Color.RED, true);
        Ball ball3 = new Ball(300,topOfScreeen, -10 + (int)(Math.random() * ((20) + 1)) ,
                -10 + (int)(Math.random() * ((5) + 1)) ,Color.BLUE, true);
        Ball ball4 = new Ball(600,topOfScreeen, -10 + (int)(Math.random() * ((20) + 1)) ,
                -10 + (int)(Math.random() * ((5) + 1)) ,Color.RED, false);
        Ball ball5 = new Ball(200,topOfScreeen,-10 + (int)(Math.random() * ((20) + 1)) ,
                -10 + (int)(Math.random() * ((5) + 1)) ,Color.BLUE, false);


        balls = new ArrayList<Ball>();
        balls.add(ball1);
        balls.add(ball2);
        balls.add(ball3);
        balls.add(ball4);
        balls.add(ball5);


        score = 0;
        score_string = "Score: 0";
        font = new BitmapFont(Gdx.files.internal("font/blue_arial.fnt"), false);
        //font.scale(2f);
        font.getData().setScale(3f, 3f);
        time = GameLength;

        gravity = 0;
    }

    @Override
    public void render (float deltaTime){

        //Gdx.app.log("mytag", "render!");
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(time <= 0) //the game has ended
        {
            //font.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            Vector2 level_up_pos = new Vector2(60,50);
            level_up_pos.x = Gdx.graphics.getWidth() * (level_up_pos.x / 100);
            level_up_pos.y = Gdx.graphics.getHeight() * (level_up_pos.y / 100);

            batch.begin();
            font.draw(batch, score_string, 25, 1150); //
            if(first_time) {
                game.myPlayer.progress += score; //add the score to advance the user progress
                first_time = false;
            }
            if(game.myPlayer.target_progress <= game.myPlayer.progress){ //you leveled up
                game.myPlayer.level += 1;
                game.myPlayer.progress -= game.myPlayer.target_progress;
                game.myPlayer.target_progress *= 1.50f;
                //font.draw(batch, "Leveled Up!" , level_up_pos.x,  level_up_pos.y);
            }
            if(game.myPlayer.high_score < score){ //you have a new high score
                game.myPlayer.high_score = score;
            }

            Vector2 level_pos = new Vector2(40,52);
            level_pos.x = Gdx.graphics.getWidth() * (level_pos.x / 100);
            level_pos.y = Gdx.graphics.getHeight() * (level_pos.y / 100);

            Vector2 high_score_pos = new Vector2(40, 42);
            high_score_pos.x = Gdx.graphics.getWidth() * (high_score_pos.x / 100);
            high_score_pos.y = Gdx.graphics.getHeight() * (high_score_pos.y / 100);

            Vector2 progress_pos = new Vector2(40, 47);
            progress_pos.x = Gdx.graphics.getWidth() * (progress_pos.x / 100);
            progress_pos.y = Gdx.graphics.getHeight() * (progress_pos.y / 100);


            font.draw(batch, "Level: " + game.myPlayer.level,level_pos.x,level_pos.y);
            font.draw(batch, "High Score: " + game.myPlayer.high_score, high_score_pos.x,
                    high_score_pos.y);
            font.draw(batch, "Progress: " + game.myPlayer.progress + " / " +
                game.myPlayer.target_progress, progress_pos.x, progress_pos.y );

            back_button.update(batch , Gdx.input.getX(), Gdx.input.getY(), deltaTime);
            if( back_button.pressed){
                game.myPlayer.save();
                game.setScreen(game.mainMenuScreen);
            }
            batch.end();
            return;
        }

        else{ //the game isn't over yet

            //paddle
            int x_value = Gdx.input.getX();
            //to prevent jumping, you are required to drag the paddle
            if((x_value > PaddlePosition.x) && x_value < PaddlePosition.x + PaddleWidth)
            {
                PaddlePosition.x = x_value - 100;
            }
            PaddlePosition.y = PaddleHeight;

            batch.begin();

            //print all the balls, and update the position
            ball_iterator = balls.iterator();
            while(ball_iterator.hasNext())
            {
                Ball b = ball_iterator.next();
                if(b.alive)
                {
                    //draw
                    if(b.col == Color.BLUE)
                    {
                        batch.draw(blue_ball, b.x_pos, b.y_pos);
                    }
                    else
                    {
                        batch.draw(red_ball, b.x_pos, b.y_pos);
                    }

                    //update
                    //wall collision
                    if(b.x_pos <= 0 || b.x_pos >= rightSide)
                    {
                        b.x_vel = -(b.x_vel);
                    }
                    b.y_vel -= gravity;
                    //paddle collision
                    if((b.y_pos <= PaddleHeight + 5 && b.y_pos >= PaddleHeight - 5 )&&
                            (b.x_pos >= PaddlePosition.x - 20) &&
                            (b.x_pos <= PaddlePosition.x + 160) )
                    {
                        if(b.col == Color.BLUE)
                        {
                            score += 1;
                            score_string = "Score: " + score;
                        }
                        else
                        {
                            score -= 1;
                            score_string = "Score: " + score;
                        }
                        b.alive = false;
                    }

                    //bottom collision
                    if(b.y_pos <= 0)
                    {
                        b.alive = false;
                    }
                    //last
                    b.x_pos += b.x_vel;
                    b.y_pos += b.y_vel;

                }
                //respawn dead ones
                else
                {
                    b.x_pos = 10 + (int)(Math.random() * ((680) + 1));
                    b.y_pos = topOfScreeen;
                    b.x_vel = -10 + (int)(Math.random() * ((20) + 1));
                    b.y_vel = -10 + (int)(Math.random() * ((5) + 1));
                    int color_choice = 0 + (int)(Math.random() * ((1) + 1));
                    if(color_choice == 1) b.col = Color.BLUE;
                    else b.col = Color.RED;
                    b.alive = true;
                }
            }

            batch.draw(paddle, PaddlePosition.x, PaddlePosition.y);


            font.setColor(0.0f, 0.0f, 1.0f, 1.0f);

            //font.setUseIntegerPositions(false);
            font.draw(batch, score_string, 25, 1150);


            //Label message = new Label("Hello",skinA);
            //message.setFontScale(3);

            time -= deltaTime;

            time_string = "Time: " + time / 100;
            font.draw(batch, time_string, 25, 1050);

            batch.end();

        }
    }

    @Override
    public void resize(int w, int h){

    }

    @Override
    public void show(){
        Gdx.app.log("mytag", "show!");
        //reset the game
        time = GameLength;
        score = 0;
        PaddlePosition.x = PaddleStartCenter;
        score_string = "Score: 0";

        ball_iterator = balls.iterator();
        while(ball_iterator.hasNext()){
            Ball b = ball_iterator.next();
            b.alive = true;
            b.y_pos = topOfScreeen;

        }
        back_button.reset();
        first_time = true;
    }

    @Override
    public void hide(){

    }

    @Override
    public void pause() {
        Gdx.app.log("mytag", "paused!");
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // never called automatically
    }


}
