package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class game extends ApplicationAdapter {
    static final int TPIXEL_SIZE = 32;
    public enum GameState {
        MENU,
        PLAYING,
        PAUSED
    }


    long lastTimeCounted;
    private float sinceChange;
    public static float frameRate;
    private GameState currentState;
    private SpriteBatch gameBatch;
    private SpriteBatch textBatch;
    private BitmapFont font;
    private OrthographicCamera gameCamera;
    private OrthographicCamera textCamera;
    private InputManager inputManager;
    private Player player;
    private MapGenerator mapGenerator;

    private Pixmap mousepm;
    private Cursor cursor;
    private ArrayList<Bullet> bullets;

    private Rectangle tileRect;
    @Override
    public void create() {
        mousepm = new Pixmap(Gdx.files.internal("corsair.png"));
        //mousepm.setColor(new Color(5,5,5,5));
        cursor = Gdx.graphics.newCursor(mousepm, mousepm.getWidth() / 2, mousepm.getHeight() / 2);

        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();

        currentState = GameState.MENU;
        gameBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        font = new BitmapFont(); // Initialize the font object here
        inputManager = new InputManager();
        player = new Player(50, gameBatch, "player.png","bullet.png", new Vector2(100,100), 0);

        bullets = new ArrayList<>();
        // Initialize the camera with orthographic projection
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mapGenerator = new MapGenerator(TPIXEL_SIZE);
        Rectangle tileRect = new Rectangle();
        tileRect.height = 50;
        tileRect.width = 50;
        tileRect.x = 50;
        tileRect.y = 50;

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCamera.update();
        gameBatch.setProjectionMatrix(gameCamera.combined);

        gameCamera.position.x = player.player_pos.x + player.player_skin.getWidth() / 2;
        gameCamera.position.y = player.player_pos.y + player.player_skin.getHeight() / 2;

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

                //set custom corsair
                Gdx.graphics.setCursor(cursor);

                // Handle playing logic
                player.player_pos.x += inputManager.movement().x * 1.5f;
                player.player_pos.y += inputManager.movement().y * 1.5f;

                float radians = (float) Math.atan2(inputManager.MousePos.y - player.player_pos.y, inputManager.MousePos.x - player.player_pos.x);
                player.player_angle = (float) Math.toDegrees(radians);


                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    currentState = GameState.PAUSED;
                }
                player.shoot_action();

                for (int i = 0; i < player.bullets.size(); i++) {
                    player.bullets.get(i).update(deltaTime);

                    // Check for collision with other game objects
                    // You can implement collision detection here
                    // Remove bullets that are off-screen or have collided

                    if (player.bullets.get(i).x < 0 || player.bullets.get(i).x > Gdx.graphics.getWidth() || player.bullets.get(i).y < 0 || player.bullets.get(i).y > Gdx.graphics.getHeight()) {
                        player.bullets.remove(player.bullets.get(i));
                    }

                    Rectangle bulletRect = new Rectangle();
                    bulletRect.height = player.bullets.get(i).height;
                    bulletRect.width = player.bullets.get(i).width;
                    bulletRect.x = player.bullets.get(i).x;
                    bulletRect.y = player.bullets.get(i).y;




                    if(Intersector.overlaps(bulletRect, tileRect));
                    {
                        player.bullets.remove(player.bullets.get(i));
                    }


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

        //---frame counter-----------------------
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
        //-------------------------

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

                    player.render();
                    for (Bullet bullet : bullets) {
                        bullet.render(gameBatch);
                    }


                gameBatch.end();

                textBatch.begin();
                //DEBUG
                    font.draw(textBatch, "PLAYING", 100, 100);
                    font.draw(textBatch, "Mouse Position: " + inputManager.MousePos.x + ", " + inputManager.MousePos.y , 3,  Gdx.graphics.getHeight() - 10);
                    font.draw(textBatch, "Player Position: " + player.player_pos.x + ", " + player.player_pos.y , 3, Gdx.graphics.getHeight() - 30);
                    font.draw(textBatch, "Movement: "  + inputManager.movement().x + ", " + inputManager.movement().y , 3, Gdx.graphics.getHeight() - 50);
                    font.draw(textBatch, "Angle: "+ player.player_angle, 3, Gdx.graphics.getHeight() - 70);
                    font.draw(textBatch, (int)frameRate + " fps", 3, Gdx.graphics.getHeight() - 90);
                    font.draw(textBatch, "Bullet count in world: " + player.bullets.size() , 3, Gdx.graphics.getHeight() - 110);



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
        mousepm.dispose();
    }
}