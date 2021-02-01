
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Cursor;
import java.awt.Graphics2D;



public class textField {

    Rectangle2D rectangle = new Rectangle2D.Double(0,0,0,0);

    String text = "";
    int xstart;
    int ystart;
    int width;
    int height;


    ArrayList<Graphics2D> rectangleArray = new ArrayList<>();



    public textField() {



    }
    public void setText(String s){
        this.text += s;
    }


//    void drawMyRect(Graphics g, int x1, int y1, int x2, int y2){
//        int realX = Math.min(x1,x2);
//        int realY = Math.min(y1,y2);
//        int width = Math.abs(x1 - x2);
//        int height = Math.abs(y1 - y2);
//        rectangle = (Rectangle2D) g;
//        rectangle.drawRect(realX,realY,width,height);
//    }



}

