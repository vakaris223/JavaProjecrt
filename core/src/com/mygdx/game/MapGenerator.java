package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    public int width;
    public int height;
    private int[][] tiles;
    private int[][] new_tiles;
    private TextureRegion[] tileTextures; 
    private int tileSize;
    private int scale;


    public MapGenerator(int tileSize, int scale) {
        generateMap();
        read_file();
        this.scale = scale;
        this.tileSize = tileSize;
        this.width = new_tiles.length;
        this.height = new_tiles[0].length;
        //loadTextures();
        loadTexturesFormOne();
    }
    private void loadTextures() {
        tileTextures = new TextureRegion[7];
        for (int i = 0; i < 7; i++)
        {
            tileTextures[i] = new TextureRegion(new Texture("tiles/grass"+ i +".png"));
        }
    }
    private void loadTexturesFormOne() {
        Texture texture = new Texture("tiles/alltiles.png"); // Load the single image containing multiple tiles
        int tileWidth = tileSize; // Width of each tile
        int tileHeight = tileSize; // Height of each tile
        int rows = 256/tileSize; // Number of rows of tiles in the single image
        int cols = 256/tileSize; // Number of columns of tiles in the single image

        TextureRegion[][] tmp = TextureRegion.split(texture, tileWidth, tileHeight);
        tileTextures = new TextureRegion[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tileTextures[index++] = tmp[i][j];
            }
        }
        System.out.println(tileTextures.length);
    }
    void generateMap()
    {
        //grass and flower 0 32
        //tile and stone paths 32 64
        int min = 0;
        int max = 32;

        //size (500x500) max
        int w = 5;
        int h = 5;

        try
        {
            FileWriter file = new FileWriter("map.txt");
            for (int i = 0; i < w; i++) {
                file.write("\r\n");
                Random randY = new Random();
                file.write(String.valueOf(randY.nextInt(max-min) + min+" "));
                //file.write(String.valueOf(30+" "));
                for (int j = 0; j < h; j++) {
                    Random randX = new Random();
                    file.write( String.valueOf(randX.nextInt(max-min) + min+" "));
                    //file.write( String.valueOf(30+" "));
                }
            }
            file.close();
            System.out.println("Map generated");
        }
        catch (IOException e) {
            System.out.println("An error occurred while generating map");
            e.printStackTrace();
        }
    }
    public void render(SpriteBatch batch, BitmapFont font, SpriteBatch textBatch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tileType = getTileType(x, y);
                TextureRegion texture = tileTextures[tileType];
                texture.setRegionHeight(tileSize);
                texture.setRegionWidth(tileSize);
                batch.draw(texture, x * tileSize * scale, y * tileSize*scale,  texture.getRegionWidth()*scale, texture.getRegionHeight()*scale);
                //debug
                //font.draw(textBatch, ""+tileType, (x * tileSize * scale)+(tileSize * scale)/2, (y * tileSize * scale)+(tileSize* scale)/2);
            }
        }
    }
    public int getTileType(int x, int y) {
        return new_tiles[x][y];
    }

    public void dispose() {
        for (TextureRegion texture : tileTextures) {
            texture.getTexture().dispose();
        }
    }
    public void map_debug(BitmapFont font, SpriteBatch textBatch)
    {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                font.draw(textBatch, ""+getTileType(i,j), (i * tileSize + 3), (j * tileSize + 730));
            }
        }

    }
    private void read_file() {
        // Read the entire file in
        List<String> myFileLines = null;
        try {
            myFileLines = Files.readAllLines(Paths.get("map.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Remove any blank lines
        for (int i = myFileLines.size() - 1; i >= 0; i--) {
            if (myFileLines.get(i).isEmpty()) {
                myFileLines.remove(i);
            }
        }

        // Declare you 2d array with the amount of lines that were read from the file
         new_tiles = new int[myFileLines.size()][];
         // columns

        // Iterate through each row to determine the number of columns
        for (int i = 0; i < myFileLines.size(); i++) {
            // Split the line by spaces
            String[] splitLine = myFileLines.get(i).split("\\s");

            // Declare the number of columns in the row from the split
            new_tiles[i] = new int[splitLine.length];
            for (int j = 0; j < splitLine.length; j++) {
                // Convert each String element to an integer
                new_tiles[i][j] = Integer.parseInt(splitLine[j]);
                //new_tiles[i][j] = Integer.parseInt(splitLine[j]);
            }
        }

        // Print the integer array
        for (int[] row : new_tiles) {
            for (int col : row) {
                System.out.printf("%5d ", col);
            }
            System.out.println();
        }
    }
}