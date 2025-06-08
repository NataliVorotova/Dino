package com.example.s_4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Stone {
    private Texture image;
    private int positionX;
    private int correctionY;
    private Rectangle bounds;

    public Stone() {
        image = new Texture("stone1.png");
        positionX = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 10;
        correctionY = 0;
        bounds = new Rectangle(positionX, correctionY, Gdx.graphics.getWidth() / 20f,
            Gdx.graphics.getHeight() / 15f);
        Gdx.app.log("Stoneheigth", bounds.getHeight()+" "+bounds.getWidth());
    }

    public int getPositionX() {
        return positionX;
    }

    public boolean update(boolean visible) {//вернет false если камень достиг конца экрана, иначе true
        positionX -= Gdx.graphics.getWidth() / 250;
        if (positionX < -image.getWidth()) {
            positionX = Gdx.graphics.getWidth();
            correctionY = (visible) ? 0 : Gdx.graphics.getHeight();
            return false;
        }
        updateBounds();
        return true;
    }

    private void updateBounds() {
        bounds.set(positionX, correctionY, Gdx.graphics.getWidth() / 10f,
            Gdx.graphics.getHeight() / 5f);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, positionX, correctionY, Gdx.graphics.getWidth() / 10f,
            Gdx.graphics.getHeight() / 5f);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void reset(boolean visible) {
        positionX = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 10;
        if (visible){
            correctionY = 0;
        }else{
            correctionY = Gdx.graphics.getWidth();
        }
        updateBounds();
    }

    public void dispose() {
        image.dispose();
    }
}
