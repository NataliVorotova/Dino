package com.example.s_4;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Dino {
    private Texture image;
    private int positionY;
    private boolean isJumping, isFalling;
    private Rectangle bounds;
    private Circle headBounds;

    public Dino() {
        image = new Texture("dino1.png");
        positionY = 0;
        bounds = new Rectangle((float) Gdx.graphics.getWidth() /12, positionY, Gdx.graphics.getWidth() / 15f,
            Gdx.graphics.getHeight() / 6f);
        headBounds = new Circle(Gdx.graphics.getWidth() * 0.15f,
            positionY + Gdx.graphics.getHeight() * 0.3f, Gdx.graphics.getHeight() * 0.04f);
        Gdx.app.log("dinoheigth", bounds.getHeight()+" "+bounds.getWidth());
    }

    public void update() {
        if (isJumping && positionY < Gdx.graphics.getHeight() / 3f) {
            positionY += Gdx.graphics.getHeight() / 200;
        } else if (positionY >= Gdx.graphics.getHeight() / 3f) {
            isJumping = false;
            isFalling = true;
        }

        if (isFalling && positionY > 0) {
            positionY -= Gdx.graphics.getHeight() / 200;
        } else if (positionY <= 0) {
            isFalling = false;
            positionY = 0;
        }

        updateBounds();
    }

    private void updateBounds() {
        bounds.setY(positionY);
        headBounds.setY(positionY + Gdx.graphics.getHeight() * 0.3f);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, 0, positionY, Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 3f);
    }

    public void jump() {
        if (!isJumping && !isFalling) {
            isJumping = true;
        }
    }

    public boolean canJump() {
        return !isJumping && !isFalling;
    }

    public boolean collidesWith(Stone stone) {
        return bounds.overlaps(stone.getBounds());
    }

    public boolean collidesWith(Bird bird) {
        return headBounds.overlaps(bird.getBounds());
    }

    public void reset() {
        positionY = 0;
        isJumping = false;
        isFalling = false;
        updateBounds();
    }

    public void dispose() {
        image.dispose();
    }
}
