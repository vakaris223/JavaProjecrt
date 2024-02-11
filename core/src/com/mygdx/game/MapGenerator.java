package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapGenerator {

    private int width;
    private int height;
    private int[][] tiles; 
    private TextureRegion[] tileTextures; 
    private int tileSize; 

    public MapGenerator(int width, int height, int tileSize) {
        this.tileSize = tileSize;
        this.width = width/this.tileSize;
        this.height = height/this.tileSize;
        
        this.tiles = new int[width][height];

        generateMap(); 
        loadTextures();
    }

    private void generateMap() {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = (int) (Math.random() * 5);
            }
        }
    }

    private void loadTextures() {
        tileTextures = new TextureRegion[5];
        tileTextures[0] = new TextureRegion(new Texture("grass1.png")); 
        tileTextures[1] = new TextureRegion(new Texture("grass2.png")); 
        tileTextures[2] = new TextureRegion(new Texture("grass3.png"));
        tileTextures[3] = new TextureRegion(new Texture("grass4.png"));
        tileTextures[4] = new TextureRegion(new Texture("grass5.png"));
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tileType = getTileType(x, y);
                TextureRegion texture = tileTextures[tileType];
                batch.draw(texture, x * tileSize, y * tileSize);
            }
        }
    }
    private int getTileType(int x, int y) {
        return tiles[x][y];
    }


    public void dispose() {
        for (TextureRegion texture : tileTextures) {
            texture.getTexture().dispose();
        }
    }
}