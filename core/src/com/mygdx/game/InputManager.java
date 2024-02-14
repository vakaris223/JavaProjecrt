package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.game.GameState;
import com.badlogic.gdx.math.Vector2;

public class InputManager {
    public Vector3 MousePos;
    
    public Vector3 GetMouseClickPos()
    {
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            return touchPos;
        }
        return Vector3.Zero;
    }

    public Vector2 movement()
    {
        float x = 0, y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 1;
        }
        
        // Normalize the vector to ensure consistent speed in all directions
        Vector2 movement = new Vector2(x, y).nor();
        
        return movement;
    }

    public int shoot_btn()
    {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            return 5;
        }
        return 0;
    }




}
