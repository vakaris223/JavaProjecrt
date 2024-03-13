package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        Texture bulletTexture = new Texture("items/bullet.png");
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
    public float h = 0,w = 0;
    public Vector2 player_pos = Vector2.Zero;
    private Animations animations;
    float stateTime;
    public TextureRegion currentFrame = null;
    public Sprite CollSprite;
    public float walkspeed;
    private float scale = 2;
    public Player
            (
             Vector2 player_pos,
             float walkspeed
            )
    {
        this.player_pos = player_pos;
        //bullets = new ArrayList<>();
        this.walkspeed = walkspeed;
        setValues();
    }
    public void setValues()
    {
        animations = new Animations();
    }
    public void render(SpriteBatch batch) {
        if (batch != null) {
            stateTime += Gdx.graphics.getDeltaTime();

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                currentFrame = animations.animation_add("player/Walk_up.png", 4, 1, walkspeed/(walkspeed*5), 0).getKeyFrame(stateTime, true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                currentFrame = animations.animation_add("player/Walk_down.png", 4, 1, walkspeed/(walkspeed*5), 0).getKeyFrame(stateTime, true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                currentFrame = animations.animation_add("player/Walk_left.png", 4, 1, walkspeed/(walkspeed*5), 0).getKeyFrame(stateTime, true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                currentFrame = animations.animation_add("player/Walk_right.png", 4, 1, walkspeed/(walkspeed*5), 0).getKeyFrame(stateTime, true);
            } else {
                currentFrame = animations.animation_add("player/idle.png", 2, 4, walkspeed/(walkspeed*5), 3).getKeyFrame(stateTime, true);
            }

            int h = currentFrame.getRegionHeight();
            int w = currentFrame.getRegionWidth();

            CollSprite = new Sprite(currentFrame.getTexture());
            batch.draw(currentFrame, player_pos.x, player_pos.y, w*scale, h*scale);

        } else {
            System.out.println("Batch is null!");
        }
    }
    public void update(Sprite obj)
    {
        System.out.println(CollisionHandler.areColliding(CollSprite, obj));
    }

    public void shoot_action() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {
            //Bullet bullet = new Bullet(player_pos.x, player_pos.y, player_angle);
            //bullets.add(bullet);
        }
    }

    public void dispose() {

    }
}