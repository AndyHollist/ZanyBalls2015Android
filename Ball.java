package com.mygdx.game;

enum Color{RED,BLUE}

public class Ball {

    int x_pos;
    int y_pos;
    int x_vel;
    int y_vel;

    Color col;
    boolean alive;

    Ball()
    {
        x_pos = 0;
        y_pos = 0;
        col = Color.BLUE;
        alive = true;
    }

    Ball(int x, int y, int xv, int yv, Color c, boolean a)
    {
        x_pos = x;
        y_pos = y;
        x_vel = xv;
        y_vel = yv;
        col = c;
        alive = a;
    }

    void kill()
    {
        alive = false;
    }

}