package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Item {
    Vector2 position = Vector2.Zero;
    public float width;
    public float height;
    public Sprite skin;
    public float scale;

    public Item(Vector2 position, String filePath, float scale) {
        this.position = position;
        Texture texture = new Texture(filePath);
        this.skin = new Sprite(texture);
        this.scale = scale;

        this.height = skin.getHeight();
        this.width = skin.getWidth();
    }
    public void update(float deltaTime) {
        //position.x += speed * Math.cos(Math.toRadians(angle)) * deltaTime;
        //position.y += speed * Math.sin(Math.toRadians(angle)) * deltaTime;
    }
    public void render(SpriteBatch batch) {
        //bullet_skin.setRotation(angle);
        batch.draw(skin, position.x, position.y,
                skin.getOriginX(), skin.getOriginY(),
                skin.getWidth(), skin.getHeight(),
                scale, scale, skin.getRotation());
    }
}
