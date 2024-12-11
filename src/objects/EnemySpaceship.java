package objects;

import update.Updateable;
import update.Updater;

import render.Renderable;
import render.Renderer;

import core.Window;
import core.FPS;
import core.Entry;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Random;
import java.io.File;

import javax.imageio.ImageIO;

public class EnemySpaceship implements Updateable, Renderable {
    private double width;
    private double height;
    private double x;
    private double y;

    private final int layer = 2;

    private static BufferedImage enemySpaceship;

    private double speed = 150;

    public int randDimensionMax = 75;
    public int randDimensionMin = 35;

    Random rand = new Random();

    public EnemySpaceship() throws IOException {
        int dimensions = rand.nextInt(randDimensionMax + 1);

        if (dimensions < randDimensionMin)
            dimensions = randDimensionMin;

        width = dimensions;
        height = dimensions;

        int posX = rand.nextInt((int) Window.getWinWidth() - (int) getWidth() + 1);
        this.x = posX;
        this.y = -getHeight();

        enemySpaceship = ImageIO.read(new File("res/EnemySpaceship.png"));

        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return enemySpaceship;
    }

    @Override
    public void update() throws IOException {
        y += speed * FPS.getDeltaTime();

        if (y >= Window.getWinHeight()) {
            // Kurangi skor jika musuh melewati batas bawah layar
            Entry.addScore(-10);

            // Periksa apakah skor kurang dari 0 untuk mengakhiri permainan
            if (Entry.getScore() < 0) {
                Entry.setGameOver();
            }

            // Hapus objek musuh dari sistem
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
        }
    }

    @Override
    public String getID() {
        return "enemyspaceship";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }

    @Override
    public boolean drawCollisionBox() {
        return true;
    }
}
