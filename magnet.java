import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class magnet extends JLabel implements MouseMotionListener, MouseListener {

    int xPosition = 50;
    int yPosition = 50;
    public static boolean onOrOff;
    public magnet(String i){
        this.setText(i);
        this.onOrOff = false;


    }

    public void setMagonOrOff(boolean x){
        this.onOrOff = x;
    }

    public boolean getMagonOrOff(){
        return this.onOrOff;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        System.out.println("magnet clicked");

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("magnet clicked");

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("magnet clicked");

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
