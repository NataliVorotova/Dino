package com.example.s_4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Bird {
    private Texture image;
    private TextureRegion[] regions;
    private int currentFrame;
    private int correctionY;
    private Circle bounds;
    private int positionX;

    public Bird() {
        image = new Texture("bird.png");
        regions = new TextureRegion[14];
        currentFrame = 0;
        correctionY = Gdx.graphics.getHeight();
        bounds = new Circle(0, Gdx.graphics.getHeight() * 0.475f + correctionY,
            Gdx.graphics.getHeight() * 0.025f);
        initRegions();
    }

    private void initRegions() {
        for (int i = 0; i < regions.length; i++) {
            int row = i / 5;
            int col = i % 5;
            regions[i] = new TextureRegion(image, col * image.getWidth() / 5,
                row * image.getHeight() / 3, image.getWidth() / 5, image.getHeight() / 3);
        }
    }

    public boolean update(boolean visible) {//вернет false если птица достигла конца экрана, иначе true
        currentFrame = (currentFrame + 1) % regions.length;

        positionX -= Gdx.graphics.getWidth() / 250;
        if (positionX < -image.getWidth() / 5){
            positionX = Gdx.graphics.getWidth();
            correctionY = (visible) ? 0 : Gdx.graphics.getHeight();
            return false;
        }
        updateBounds();

        return true;
    }

    private void updateBounds() {
        bounds.setX(getPositionX() * 1.5f + Gdx.graphics.getWidth() * 0.02f);
        bounds.setY(Gdx.graphics.getHeight() * 0.475f + correctionY);
    }

    public int getPositionX() {
        return positionX;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(regions[currentFrame], getPositionX() * 1.5f,
            Gdx.graphics.getHeight() / 2.3f + correctionY, image.getWidth() / 5f,
            image.getHeight() / 3f);
    }

    public Circle getBounds() {
        return bounds;
    }

    public void reset(boolean visible) {
        if (visible){
            correctionY = 0;
        }else{
            correctionY = Gdx.graphics.getWidth();
        }
        update(visible);
    }

    public void dispose() {
        image.dispose();
    }
}
