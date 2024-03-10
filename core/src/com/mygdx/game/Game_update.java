package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Game_update {

    public void update(GameState currentState,
                       SpriteBatch gameBatch,
                       SpriteBatch textBatch,
                       BitmapFont font,
                       OrthographicCamera gameCamera,
                       OrthographicCamera textCamera,
                       MapGenerator mapGenerator,
                       InputManager inputManager,
                       Player player,
                       float deltaTime,
                       Cursor cursor)
    {
        inputManager.MousePos = gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        gameCamera.update();
        gameBatch.setProjectionMatrix(gameCamera.combined);

        gameCamera.position.x = player.player_pos.x + player.w / 2;
        gameCamera.position.y = player.player_pos.y + player.h / 2;

        textCamera.update();

        textCamera.position.x = gameCamera.position.x;
        textCamera.position.y = gameCamera.position.y;

        switch (currentState) {
            case MENU:
                // Handle menu logic
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    game.currentState = GameState.PLAYING;
                }
                break;
            case PLAYING:
                //set custom corsair
                Gdx.graphics.setCursor(cursor);

                // Handle playing logic
                player.player_pos.x += inputManager.movement().x * player.walkspeed;
                player.player_pos.y += inputManager.movement().y * player.walkspeed;

                float radians = (float) Math.atan2(inputManager.MousePos.y - player.player_pos.y, inputManager.MousePos.x - player.player_pos.x);
                //player.player_angle = (float) Math.toDegrees(radians);

                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    game.currentState = GameState.PAUSED;
                }
                //player.shoot_action();

                for (int i = 0; i < player.bullets.size(); i++) {
                    player.bullets.get(i).update(deltaTime);

                    // Check for collision with other game objects
                    // You can implement collision detection here
                    // Remove bullets that are off-screen or have collided

                    if (player.bullets.get(i).x < 0 || player.bullets.get(i).x > Gdx.graphics.getWidth() || player.bullets.get(i).y < 0 || player.bullets.get(i).y > Gdx.graphics.getHeight()) {
                        player.bullets.remove(player.bullets.get(i));
                    }
                }
                player.update();
                break;
            case PAUSED:
                // Handle paused logic
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    game.currentState = GameState.PLAYING;
                }
                break;
        }
    }

}
