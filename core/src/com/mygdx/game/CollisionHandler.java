package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {
    public static boolean areColliding(Sprite sprite1, Sprite sprite2) {
        Rectangle rect1 = sprite1.getBoundingRectangle();
        Rectangle rect2 = sprite2.getBoundingRectangle();
        return Intersector.overlaps(rect1, rect2);
    }
}
