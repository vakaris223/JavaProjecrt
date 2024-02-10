package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private int health;
    public Sprite skin;
    private SpriteBatch batch;
    private String imagePath;


    public Vector2 player_pos = Vector2.Zero;
    public float player_angle = 0;
     

    public Player(int health, SpriteBatch batch, String imagePath, Vector2 player_pos, float player_angle) {
        this.health = health;
        this.batch = batch; // Ensure batch is properly assigned
        this.imagePath = imagePath;
        this.player_angle =  player_angle;
        this.player_pos = player_pos;
        setValues(); // Call setValues to initialize the player sprite
    }

    public void setValues() {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        skin = new Sprite(texture);
        skin.setPosition(player_pos.x, player_pos.y);
        skin.setOrigin(skin.getWidth() / 2, skin.getHeight() / 2); // Set origin to the center of the sprite
    }

    public void render() {
        if (batch != null && skin != null) {
            batch.draw(skin, skin.getX(), skin.getY(), skin.getOriginX(), skin.getOriginY(), skin.getWidth(), skin.getHeight(), 1, 1, skin.getRotation());
        } else {
            System.out.println("Batch or skin is null!"); // Add this line for debugging
        }
    }

    public void update()
    {
        skin.setPosition(player_pos.x, player_pos.y);
        skin.setRotation(player_angle);
    }

    public void dispose() {
        skin.getTexture().dispose();
    }
    
}
