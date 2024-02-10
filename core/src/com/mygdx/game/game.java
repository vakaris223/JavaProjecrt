package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.game.InputManager;
import com.mygdx.game.Player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class game extends ApplicationAdapter {

    public enum GameState {
        MENU,
        PLAYING,
        PAUSED
    }

    private InputManager inputManager;
    private Player player;

    private GameState currentState;
    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera camera;

    private MapGenerator mapGenerator;

    @Override
    public void create() {
        currentState = GameState.MENU;
        batch = new SpriteBatch();
        font = new BitmapFont();
        inputManager = new InputManager();
        player = new Player(50, batch, "player.png", new Vector2(100,100), 0);

        // Initialize the camera with orthographic projection
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mapGenerator = new MapGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 32);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Gdx.graphics.getDeltaTime());
        renderGame();
        camera.position.x = player.player_pos.x + player.skin.getWidth() / 2;
        camera.position.y = player.player_pos.y + player.skin.getHeight() / 2;
        camera.update();
        

    }

    private void update(float deltaTime) {
        // Update game logic based on game state
        inputManager.MousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        switch (currentState) {
            case MENU:
                // Handle menu logic
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    currentState = GameState.PLAYING;
                }
                break;
            case PLAYING:
                // Handle playing logic
                player.player_pos.x += inputManager.movement().x * 1.5f;
                player.player_pos.y += inputManager.movement().y * 1.5f;
                
                // if (inputManager.movement().len2() > 0) {

                //     float angle = inputManager.movement().angleDeg();
                //     player.player_angle = angle;
                // }

                float radians = (float) Math.atan2(inputManager.MousePos.y - player.player_pos.y, inputManager.MousePos.x - player.player_pos.x);
                player.player_angle = (float) Math.toDegrees(radians);

                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    currentState = GameState.PAUSED;
                }
                player.update();
                break;
            case PAUSED:
                // Handle paused logic
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    currentState = GameState.PLAYING;
                }
                break;
        }       
    }

    private void renderGame() {

        batch.begin();
        font.setColor(Color.WHITE);
    
        switch (currentState) {
            case MENU:
                font.draw(batch, "MENU", 100, 100); 
                break;
            case PLAYING:
                
                batch.setProjectionMatrix(camera.combined);
                
                
                mapGenerator.render(batch);
                player.render();


                //DEBUG
                font.draw(batch, "PLAYING", 100, 100); 
                font.draw(batch, "Mouse Position: " + inputManager.MousePos.x + ", " + inputManager.MousePos.y , 100, 150); 
                font.draw(batch, "Player Position: " + player.player_pos.x + ", " + player.player_pos.y , 100, 170); 
                font.draw(batch, "Movement: "  + inputManager.movement().x + ", " + inputManager.movement().y , 100, 190);
                font.draw(batch, "Angle: "+ player.player_angle, 100, 210);
                

                break;
            case PAUSED:
                font.draw(batch, "PAUSED", 100, 100); 
                break;
        }
        // End rendering text
        batch.end();

        
    }

    @Override
    public void pause() {
        // Handle pausing the game
    }


    @Override
    public void resume() {
        // Handle resuming the game
    }

    @Override
    public void dispose() {
      batch.dispose();
      font.dispose(); 
      mapGenerator.dispose();
      player.dispose();
    }
}
