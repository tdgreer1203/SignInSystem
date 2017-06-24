/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triosignin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
/**
 *
 * @author Theo
 */
public class TRiOSignIn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int xSize, ySize;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        xSize = (int) screenSize.getHeight();
        ySize = (int) screenSize.getWidth();
        
        Holder holder = new Holder();
        holder.setSize(ySize, xSize);
        holder.setVisible(true);
        holder.getContentPane().setBackground(Color.white);
    }
    
}
