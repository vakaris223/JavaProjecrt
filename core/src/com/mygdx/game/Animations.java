package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animations {

    Texture texture;

    public Animation<TextureRegion> animation_add(String file_path, int FRAME_COLS, int FRAME_ROWS, float speed, int ignore_last)
    {
        texture = new Texture(file_path);
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / FRAME_COLS, texture.getHeight() / FRAME_ROWS);

        TextureRegion[] Frames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) - (ignore_last * FRAME_COLS)]; // Adjust the size

        int index = 0;
        for (int i = 0; i < FRAME_ROWS - ignore_last; i++) { // Adjust the loop termination condition
            for (int j = 0; j < FRAME_COLS; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(speed, Frames);
    }
}