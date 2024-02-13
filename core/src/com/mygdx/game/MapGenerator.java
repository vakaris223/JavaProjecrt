package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MapGenerator {
    public int width;
    public int height;
    private int[][] tiles;
    private int[][] new_tiles;
    private TextureRegion[] tileTextures; 
    private int tileSize; 

    public MapGenerator(int tileSize) {
        read_file();
        this.tileSize = tileSize;
        this.width = new_tiles.length;
        this.height = new_tiles[0].length;
        loadTextures();
    }

    private void loadTextures() {
        tileTextures = new TextureRegion[7];
        for (int i = 0; i < 7; i++)
        {
            tileTextures[i] = new TextureRegion(new Texture("grass"+ i +".png"));
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, SpriteBatch textBatch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tileType = getTileType(x, y);
                TextureRegion texture = tileTextures[tileType];



                batch.draw(texture, x * tileSize, y * tileSize);
                //debug
                font.draw(textBatch, ""+tileType, (x * tileSize)+tileSize/2, (y * tileSize)+tileSize/2);
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
                font.draw(textBatch, ""+getTileType(i,j), (i * 32 + 3), (j * 32 + 730));
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