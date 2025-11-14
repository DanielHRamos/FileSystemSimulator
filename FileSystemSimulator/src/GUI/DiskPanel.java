/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Disk.DiskSimulator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public class DiskPanel extends JPanel {

    private DiskSimulator disk;

    
    private static final int BLOCK_WIDTH = 51;
    private static final int BLOCK_HEIGHT = 51;

    public DiskPanel(DiskSimulator disk) {
        this.disk = disk;
        // Ajustar tamaño preferido del panel según número de bloques
        int cols = 10; 
        int rows = (int) Math.ceil((double) disk.getTotalBlocks() / cols);
        setPreferredSize(new Dimension(cols * BLOCK_WIDTH, rows * BLOCK_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cols = 10; 
        int rows = (int) Math.ceil((double) disk.getTotalBlocks() / cols);

        for (int i = 0; i < disk.getTotalBlocks(); i++) {
            int row = i / cols;
            int col = i % cols;

            int x = col * BLOCK_WIDTH;
            int y = row * BLOCK_HEIGHT;

            
            if (disk.isBlockUsed(i)) {
                g.setColor(Color.RED);   
            } else {
                g.setColor(Color.GREEN); 
            }

            
            g.fillRect(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);

            
            g.setColor(Color.BLACK);
            g.drawRect(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);

            
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(String.valueOf(i), x + 5, y + 15);
        }
    }
}
