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
    public static final int TPIXEL_SIZE = 32;
    public long lastTimeCounted;
    public float sinceChange;
    public static float frameRate;
    public static GameState currentState;
    public SpriteBatch gameBatch;
    public SpriteBatch textBatch;
    public BitmapFont font;
    public OrthographicCamera gameCamera;
    public OrthographicCamera textCamera;
    public InputManager inputManager;
    public Player player;
    public MapGenerator mapGenerator;
    public Pixmap mousepm;
    public Cursor cursor;
    public ArrayList<Bullet> bullets;
    public RenderManager renderManager;
    public Game_update gameUpdate;

    @Override
    public void create() {
        mousepm = new Pixmap(Gdx.files.internal("preference/corsair.png"));
        //mousepm.setColor(new Color(5,5,5,5));
        cursor = Gdx.graphics.newCursor(mousepm, mousepm.getWidth() / 2, mousepm.getHeight() / 2);

        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;

        frameRate = Gdx.graphics.getFramesPerSecond();
        currentState = GameState.MENU;
        gameBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        font = new BitmapFont();
        inputManager = new InputManager();
        player = new Player(new Vector2(2000,2000),1f);
        bullets = new ArrayList<>();
        // Initialize the camera with orthographic projection
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mapGenerator = new MapGenerator(TPIXEL_SIZE, 1);
        renderManager = new RenderManager();
        gameUpdate = new Game_update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //---frame counter-----------------------
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();
        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
        //-------------------------

        renderManager.RenderGame
                (
                        currentState,
                        gameBatch,
                        textBatch,
                        font,
                        gameCamera,
                        textCamera,
                        mapGenerator,
                        inputManager,
                        player
                );
        gameUpdate.update
                (
                        currentState,
                        gameBatch,
                        textBatch,
                        font,
                        gameCamera,
                        textCamera,
                        mapGenerator,
                        inputManager,
                        player,
                        Gdx.graphics.getDeltaTime(),
                        cursor
                );

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
        mousepm.dispose();
        mapGenerator.dispose();
        gameBatch.dispose();
        textBatch.dispose();
        font.dispose();
        player.dispose();
    }
}