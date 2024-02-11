package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class game extends ApplicationAdapter {

    public enum GameState {
        MENU,
        PLAYING,
        PAUSED
    }
    private Stage stage;
    private Skin skin;

    private GameState currentState;
    private SpriteBatch gameBatch;
    private SpriteBatch textBatch;
    private BitmapFont font;
    private OrthographicCamera gameCamera;
    private OrthographicCamera textCamera;
    private InputManager inputManager;
    private Player player;
    private MapGenerator mapGenerator;
    @Override
    public void create() {
        currentState = GameState.MENU;
        gameBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        font = new BitmapFont(); // Initialize the font object here
        inputManager = new InputManager();
        player = new Player(50, gameBatch, "player.png", new Vector2(100,100), 0);

        // Initialize the camera with orthographic projection
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mapGenerator = new MapGenerator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 32);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCamera.update();
        gameBatch.setProjectionMatrix(gameCamera.combined);

        gameCamera.position.x = player.player_pos.x + player.skin.getWidth() / 2;
        gameCamera.position.y = player.player_pos.y + player.skin.getHeight() / 2;

        textCamera.update();
        //textBatch.setProjectionMatrix(textCamera.combined);

        textCamera.position.x = gameCamera.position.x;
        textCamera.position.y = gameCamera.position.y;

        renderGame();
        update(Gdx.graphics.getDeltaTime());
    }
    private void update(float deltaTime) {
        // Update game logic based on game state

        inputManager.MousePos = gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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
                    mapGenerator.render(gameBatch);
                    player.render();
                gameBatch.end();

                textBatch.begin();
                //DEBUG
                    font.draw(textBatch, "PLAYING", 100, 100);
                    font.draw(textBatch, "Mouse Position: " + inputManager.MousePos.x + ", " + inputManager.MousePos.y , 100,  150);
                    font.draw(textBatch, "Player Position: " + player.player_pos.x + ", " + player.player_pos.y , 100, 170);
                    font.draw(textBatch, "Movement: "  + inputManager.movement().x + ", " + inputManager.movement().y , 100, 190);
                    font.draw(textBatch, "Angle: "+ player.player_angle, 100, 210);
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
        gameBatch.dispose();
        textBatch.dispose();
        font.dispose();
        mapGenerator.dispose();
        player.dispose();
    }
}