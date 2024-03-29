package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class RenderManager {


    public void RenderGame(GameState currentState,
                           SpriteBatch gameBatch,
                           SpriteBatch textBatch,
                           BitmapFont font,
                           OrthographicCamera gameCamera,
                           OrthographicCamera textCamera,
                           MapGenerator mapGenerator,
                           InputManager inputManager,
                           Player player,
                           ArrayList<Item> items,
                           ShapeRenderer shapeRenderer
                           )
    {
        font.setColor(Color.WHITE);
        switch (currentState) {
            case MENU:
                textBatch.begin();
                font.draw(textBatch, "MENU", 100, 100);
                textBatch.end();
                break;
            case PLAYING:
                gameBatch.begin();
                gameBatch.setProjectionMatrix(gameCamera.combined);

                mapGenerator.render(gameBatch, font, gameBatch);

                player.render(gameBatch, shapeRenderer);

                for (Item item : items) {
                    item.render(gameBatch);
                }
                gameBatch.end();

                // Draw rectangle around player for debugging
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setProjectionMatrix(gameCamera.combined); // Set projection matrix for shapeRenderer
                shapeRenderer.setColor(Color.WHITE);
                int h = player.currentFrame.getRegionHeight();
                int w = player.currentFrame.getRegionWidth();
                shapeRenderer.rect(player.player_pos.x, player.player_pos.y, w * player.scale, h * player.scale);
                shapeRenderer.end();

                textBatch.begin();
                //DEBUG
                //textBatch.setProjectionMatrix(textCamera.combined);
                font.draw(textBatch, "PLAYING", 100, 100);
                font.draw(textBatch, "Mouse Position: " + inputManager.MousePos.x + ", " + inputManager.MousePos.y , 3,  Gdx.graphics.getHeight() - 10);
                font.draw(textBatch, "Player Position: " + player.player_pos.x + ", " + player.player_pos.y , 3, Gdx.graphics.getHeight() - 30);
                font.draw(textBatch, "Movement: "  + inputManager.movement().x + ", " + inputManager.movement().y , 3, Gdx.graphics.getHeight() - 50);
                font.draw(textBatch, "Is colliding: "+ player.isColliding, 3, Gdx.graphics.getHeight() - 70);
                font.draw(textBatch, (int)game.frameRate + " fps", 3, Gdx.graphics.getHeight() - 90);
                //font.draw(textBatch, "Bullet count in world: " + player.bullets.size() , 3, Gdx.graphics.getHeight() - 110);
                font.draw(textBatch, "--map structure--", 3, Gdx.graphics.getHeight() - 130);
                //draw map's tile number
                //mapGenerator.map_debug(font, textBatch);
                // End rendering text
                textBatch.end();

                break;
            case PAUSED:
                textBatch.begin();
                font.draw(textBatch, "PAUSED", 100, 100);
                textBatch.end();
                break;
        }
    }


}
