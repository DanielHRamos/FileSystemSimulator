/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Disk.DiskSimulator;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public class DiskPanel extends JPanel {

    private DiskSimulator disk;

    public DiskPanel(DiskSimulator disk) {
        this.disk = disk;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int blockSize = 20;
        int cols = getWidth() / blockSize;

        for (int i = 0; i < disk.getTotalBlocks(); i++) {
            int row = i / cols;
            int col = i % cols;

            int x = col * blockSize;
            int y = row * blockSize;

            if (disk.isBlockUsed(i)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, blockSize, blockSize);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, blockSize, blockSize);
            }
        }
    }
}
