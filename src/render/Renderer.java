package render;

import core.Entry;
import core.Window;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Renderer extends JPanel {
    public static ArrayList<Renderable> renderableObjects = new ArrayList<>();
    private static ArrayList<Renderable> addRenderableObjects = new ArrayList<>();
    private static ArrayList<Renderable> removeRenderableObjects = new ArrayList<>();

    public static void clearRenderableObjects() {
        renderableObjects.clear();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Render setiap objek dalam daftar renderable
        for (Renderable object : renderableObjects) {
            object.drawSprite(g2d);
        }

        // Tampilkan skor di layar
        g2d.setColor(Color.WHITE); // Warna teks skor
        g2d.setFont(new Font("Verdana", Font.BOLD, 15)); // Gaya dan ukuran font
        g2d.drawString("Score: " + Entry.getScore(), 20, 30); // Posisi teks skor

        // Update daftar renderable
        updateRenderableObjects();
    }

    // Sinkronisasi daftar renderable
    private void updateRenderableObjects() {
        renderableObjects.removeAll(removeRenderableObjects);
        if (!addRenderableObjects.isEmpty()) {
            renderableObjects.addAll(addRenderableObjects);
            Collections.sort(renderableObjects); // Urutkan berdasarkan layer
        }
        addRenderableObjects.clear();
        removeRenderableObjects.clear();
    }

    public static void addRenderableObject(Renderable object) {
        addRenderableObjects.add(object);
    }

    public static void removeRenderableObject(Renderable object) {
        removeRenderableObjects.add(object);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) Window.getWinWidth(), (int) Window.getWinHeight());
    }
}
