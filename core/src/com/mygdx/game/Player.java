package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

class Bullet {
    public float x; // X-coordinate of the bullet
    public float y; // Y-coordinate of the bullet
    private float angle; // Angle of the bullet's trajectory
    private float speed ; // Speed of the bullet

    public float width;
    public float height;
    public Sprite bullet_skin;

    public Bullet(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 500;


        // Set the texture of the bullet (replace "bullet_texture" with your actual texture)
        Texture bulletTexture = new Texture("bullet.png");
        this.bullet_skin = new Sprite(bulletTexture);

        this.height = bullet_skin.getHeight();
        this.width = bullet_skin.getWidth();
    }

    public void update(float deltaTime) {
        x += speed * Math.cos(Math.toRadians(angle)) * deltaTime;
        y += speed * Math.sin(Math.toRadians(angle)) * deltaTime;
    }

    public void render(SpriteBatch batch) {
        bullet_skin.setRotation(angle);
        batch.draw(bullet_skin, x, y, bullet_skin.getOriginX(), bullet_skin.getOriginY(), bullet_skin.getWidth(), bullet_skin.getHeight(), 0.5f, 0.5f, bullet_skin.getRotation());
    }
}


public class Player {

    private String bullet_imgPath;
    public Sprite bullet_skin;
    public Vector2 bullet_pos = Vector2.Zero;
    public float bullet_angle = 0;
    public float bullet_speed = 5;


    private int health;
    private SpriteBatch batch;
    private String player_imgPath;
    public Sprite player_skin;
    public Vector2 player_pos = Vector2.Zero;
    public float player_angle = 0;
    public ArrayList<Bullet> bullets;

    public Player(int health, SpriteBatch batch, String imagePath, String bullet_imgPath, Vector2 player_pos, float player_angle) {
        this.health = health;
        this.batch = batch; // Ensure batch is properly assigned
        this.player_imgPath = imagePath;
        this.player_angle =  player_angle;
        this.player_pos = player_pos;
        this.bullet_imgPath = bullet_imgPath;
        bullets = new ArrayList<>();

        setValues(); // Call setValues to initialize the player sprite
    }

    public void setValues() {
        Texture player_texture = new Texture(Gdx.files.internal(player_imgPath));
        player_skin = new Sprite(player_texture);

        Texture bullet_texture = new Texture(Gdx.files.internal(bullet_imgPath));
        bullet_skin = new Sprite(bullet_texture);

        player_skin.setPosition(player_pos.x, player_pos.y);
        player_skin.setOrigin(player_skin.getWidth() / 2, player_skin.getHeight() / 2); // Set origin to the center of the sprite

        Texture bulletTexture = new Texture(Gdx.files.internal(bullet_imgPath));
        for (Bullet bullet : bullets) {
            bullet.bullet_skin = new Sprite(bulletTexture);
            bullet.bullet_skin.setOrigin(bullet.bullet_skin.getWidth() / 2, bullet.bullet_skin.getHeight() / 2);
        }
    }

    public void render() {
        if (batch != null && player_skin != null) {
            // Draw the player sprite
            batch.draw(player_skin, player_skin.getX(), player_skin.getY(), player_skin.getOriginX(), player_skin.getOriginY(), player_skin.getWidth(), player_skin.getHeight(), 1, 1, player_skin.getRotation());

            // Render bullets
            for (Bullet bullet : bullets) {
                bullet.render(batch);
            }
        } else {
            System.out.println("Batch or skin is null!");
        }
    }

    public void update()
    {
        player_skin.setPosition(player_pos.x, player_pos.y);
        player_skin.setRotation(player_angle);
        for (Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
        }
    }

    public void shoot_action() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            Bullet bullet = new Bullet(player_pos.x, player_pos.y, player_angle );
            bullets.add(bullet);
        }
    }

    public void dispose() {
        player_skin.getTexture().dispose();
    }
    
}