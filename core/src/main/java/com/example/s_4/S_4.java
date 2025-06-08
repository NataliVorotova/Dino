package com.example.s_4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class S_4 extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Dino dino;
    private Stone stone;
    private Bird bird;
    private boolean gameIsRunning, gameIsOver, soundOfsGameIsOn;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private int points;
    private Sound sound_jump, sound_end;
    private boolean visible;

    @Override
    public void create() {
        sound_jump = Gdx.audio.newSound(Gdx.files.internal("sound_jump.mp3"));
        sound_end = Gdx.audio.newSound(Gdx.files.internal("sound_end.mp3"));

        batch = new SpriteBatch();
        backgroundImage = new Texture("background.jpg");
        dino = new Dino();
        stone = new Stone();
        bird = new Bird();

        font = new BitmapFont();
        font.getData().setScale(4);
        spriteBatch = new SpriteBatch();
        points = 0;
        gameIsRunning = false;
        gameIsOver = false;
        soundOfsGameIsOn = true;

        visible = visibleBirdOrStone();
    }

    @Override
    public void render() {
        updateGame();
        drawGame();
    }

    private void updateGame() {
        if (gameIsRunning && !gameIsOver) {
            dino.update();
            if (!(stone.update(!visible) && bird.update(visible))){
                visible = visibleBirdOrStone();
            }

            points++;
        }
        checkGameOver();
        handleInput();
    }

    private void drawGame() {
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        dino.draw(batch);
        stone.draw(batch);
        bird.draw(batch);
        batch.end();

        spriteBatch.begin();
        font.setColor(Color.RED);
        font.draw(spriteBatch, "COUNT: " + points, Gdx.graphics.getWidth() * 0.8f,
            Gdx.graphics.getHeight() * 0.9f);
        spriteBatch.end();
    }

    private void checkGameOver() {
        if (dino.collidesWith(stone) || dino.collidesWith(bird)) {
            gameIsRunning = false;
            gameIsOver = true;
            if (soundOfsGameIsOn) {
                sound_end.play();
                soundOfsGameIsOn = false;
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Gdx.app.log("handleInput", ""+gameIsRunning+gameIsOver);
            if (!gameIsRunning && !gameIsOver) {
                gameIsRunning = true;
                soundOfsGameIsOn = true;
            } else if (gameIsOver) {
                resetGame();
            } else if (dino.canJump()) {
                dino.jump();
                sound_jump.play();
            }
        }
    }

    private void resetGame() {
        gameIsRunning = false;
        gameIsOver = false;
        visible = visibleBirdOrStone();
        stone.reset(!visible);
        dino.reset();
        bird.reset(visible);
        points = 0;
        Gdx.app.log("reset", "reset");
    }

    private boolean visibleBirdOrStone() {
        Random randomStoneOrBird = new Random();
        return randomStoneOrBird.nextBoolean();
    }


    @Override
    public void dispose() {
        batch.dispose();
        spriteBatch.dispose();
        backgroundImage.dispose();
        dino.dispose();
        stone.dispose();
        bird.dispose();
        sound_jump.dispose();
        sound_end.dispose();
        font.dispose();
    }
}





