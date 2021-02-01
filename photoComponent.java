
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;




public class photoComponent extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
//    public dollar.DollarRecognizer theRecognizer = new dollar.DollarRecognizer();
    public static dollar.Result r;
    private ImageIcon  pic;
    private boolean gesture;
    public static MainClass x;
    private boolean vacation;
    private boolean home;
    private boolean friends;
    private boolean family;
    private ArrayList<Graphics> graphicsObjectsArray = new ArrayList<>();
    private boolean flippedstate;
    private boolean gridView = false;
    private boolean photoView = true;
    public boolean beautifySquare = false;
    public boolean beautifyCircle = false;
    int clickcount;
    boolean stop = true;
    ArrayList<Rectangle> rectangleArrayNow = new ArrayList<>();
    ArrayList<Ellipse2D> circleArrayNow = new ArrayList<>();
    ArrayList<Point2D> pointsArrayGesture = new ArrayList<>();
    ArrayList<Point> pointsArray = new ArrayList<>();
    ArrayList<Integer> rectangleArrayParameters = new ArrayList<>();
    ArrayList<Integer> CircleArrayParameters = new ArrayList<>();
    private Point p1 = new Point(100, 100);
    private Point p2 = new Point(540, 380);
    private boolean drawing;
    int indexOne;
    int indexTwo;
    int index;
    int row;
    int XX;
    int YY;
    int currX;
    int currY;
    int prevX;
    int prevY;
    boolean isPen = true;
    boolean isText = false;
    boolean fileflagphoto;
    float scaleFactorX;
    float scaleFactorY;
    int x1,x2,y1,y2;
    String ll = "";
    ArrayList<String> stringArray = new ArrayList<>();

    boolean pPopulated;
    ArrayList<Line2D> lineArray = new ArrayList<>();
    ArrayList<Line2D> gestureArray = new ArrayList<>();
    private Point PreviousPoint;
    textField mySquare;
    ArrayList<textField> rectangleArray = new ArrayList<>();

    ArrayList<Graphics> gridRects = new ArrayList<>();





    public photoComponent(ImageIcon p){
        pic = p;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setSize(pic.getIconWidth(),900);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();

//
    }
    String[] s = {"tre"};
//    MainClass isPen = new MainClass(s);
    @Override
    public void paintComponent(Graphics g){
        //System.out.println("photocomponent/paintcomponent/");

        if (gridView){
//            System.out.println("photocomponent/paintcomponent/gridveiw");
//            g.setColor(Color.BLUE);
//            g.drawRect(0,0,75,75);


            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 75, 75);
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(this.getScaleX(),this.getScaleY());
            pic.paintIcon(this, g, 38, 38);










            if (isFlippedstate()) {

                drawLines(g);
                for (textField rect : rectangleArray) {
                    Graphics2D g2dd = (Graphics2D) g;
                    g2dd.draw(rect.rectangle);

                    int count = 0;
                    int line = 1;
                    int maxForLines = rect.height / 20;

                    for (char c : rect.text.toCharArray()) {
                        int w = g.getFontMetrics().charWidth(c);

                        if (w + count >= rect.width) {
                            count = 0;
                            line++;
                        }
                        if (line > maxForLines) {
                            getGraphics().setColor(Color.WHITE);
                            getGraphics().drawRect(rect.xstart, rect.ystart, rect.width, rect.height);
                            rect.height = rect.height + 20;
                            maxForLines++;
                        }

                        int startX = rect.xstart;
                        int startY = rect.ystart;
                        g.drawString(c + "", startX + count, startY + ( 20 * line ));
                        count += w;
                        int s = startY + ( 20 * line );
                        if (s > rect.height) {

                            rect.height += 10;

                            g2d.drawRect(rect.xstart, rect.ystart, rect.width, rect.height);
                            repaint();


                        }


                    }

                }


            }

            this.requestFocus();
        }


        else if (photoView) {

            //handle the gestures here.




            if (isFlippedstate()) {
                if (beautifySquare) {
                    int x  = (int) r.getBoundingBox().getX();
                    int y = (int)  r.getBoundingBox().getY();
                    int width = (int) r.getBoundingBox().getWidth();
                    int height = (int) r.getBoundingBox().getHeight();
                    Rectangle newRect = new  Rectangle(x,y,width,height);
                    rectangleArrayNow.add(newRect);
//                    g.setColor(Color.red);
//                    g.drawRect(x,y,width,height);
                }
                else if (beautifyCircle){
                    int x  = (int) r.getBoundingBox().getX();
                    int y = (int)  r.getBoundingBox().getY();
                    int width = (int) r.getBoundingBox().getWidth();
                    int height= (int) r.getBoundingBox().getHeight();
                    Ellipse2D newCircle = new Ellipse2D.Double(x, y, width, height);
                    circleArrayNow.add(newCircle);

                    g.setColor(Color.red);
                    g.drawOval(x,y,width,height);
                }
                beautifySquare = false;
                beautifyCircle = false;

                g.setColor(Color.DARK_GRAY);
                g.drawRect(0, 0, pic.getIconWidth(), pic.getIconHeight());
                if(rectangleArrayNow.size() > 0){
                    for(int i  = 0 ; i < rectangleArrayNow.size(); i++){
                        g.setColor(Color.RED);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.draw(rectangleArrayNow.get(i));

                    }

                }
                if (circleArrayNow.size() > 0){
                    for(int i  = 0 ; i < circleArrayNow.size(); i++){
                        g.setColor(Color.RED);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.draw(circleArrayNow.get(i));

                    }

                }

            } else {
                pic.paintIcon(this, g, 0, 0);
            }

            if (isFlippedstate()) {

                drawLines(g);
                for (textField rect : rectangleArray) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.draw(rect.rectangle);
                    int count = 0;
                    int line = 1;
                    int maxForLines = rect.height / 20;
                    for (char c : rect.text.toCharArray()) {
                        int w = g.getFontMetrics().charWidth(c);
                        if (w + count >= rect.width) {
                            count = 0;
                            line++;
                        }
                        if (line > maxForLines) {
                            getGraphics().setColor(Color.WHITE);
                            getGraphics().drawRect(rect.xstart, rect.ystart, rect.width, rect.height);
                            rect.height = rect.height + 20;
                            maxForLines++;
                        }
                        int startX = rect.xstart;
                        int startY = rect.ystart;
                        g.drawString(c + "", startX + count, startY + ( 20 * line ));
                        count += w;
                        int s = startY + ( 20 * line );
                        if (s > rect.height) {
                            rect.height += 10;
                            g2d.drawRect(rect.xstart, rect.ystart, rect.width, rect.height);
                            repaint();
                        }
                    }
                }
                if (gesture){
                    drawLines(g);
                }
            }
            if (gesture){
                drawLines(g);
            }
            this.requestFocus();
        }
    }

    private void redispatchToParent(MouseEvent e){
        Component source=(Component)e.getSource();
        MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e, source.getParent());
        source.getParent().dispatchEvent(parentEvent);
    }

    public void drawGesture(Graphics g){

    }

//    public void paintIcon(Component c,
//                          Graphics g,
//                          int x,
//                          int y)

    void drawBeauty(Graphics g){
        for(int i = 0; i < 100; i+= 4){
            g.drawRect(rectangleArrayParameters.get((i+4) % 4),rectangleArrayParameters.get((i+5) % 4),rectangleArrayParameters.get((i+6) % 4),rectangleArrayParameters.get((i+4) % 4));
        }
        for(int i = 0 ; i < rectangleArrayParameters.size(); i++){
            System.out.println("this is where it starts");
            System.out.println(rectangleArrayParameters.get(i) + i);
        }
    }


    void drawLines(Graphics g) {
        if (gesture) {
            g.setColor(Color.red);
            Graphics2D g2d = (Graphics2D) g;
            for(Line2D line: gestureArray){
                g2d.draw(line);
            }
        }
        g.setColor(Color.black);
            Graphics2D g2d = (Graphics2D) g;
            for(Line2D line: lineArray){
                g2d.draw(line);
            }




    }

    void drawmyRect(Graphics g, textField t){
        int realX = Math.min(x1,x2);
        int realY = Math.min(y1,y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(realX,realY,width,height);
    }

    //getters and setters for the tags
    public void  toggleVacation(){
        this.vacation = !this.vacation;
    }

    public void  toggleFamily(){
        this.family = !this.family;
    }

    public void  toggleHome(){
        this.home = !this.home;
    }

    public void  toggleFriends(){
        this.friends = !this.friends;
    }


    public void setVactionTrue(){
        this.vacation = true;

    }
    public void setVactionFalse(){
        this.vacation = false;

    }
    public void setFamilyTrue(){
        this.family = true;

    }
    public void setFamilyFalse(){
        this.family = false;

    }
    public void setFriendsTrue(){
        this.friends = true;

    }
    public void setFriendsFalse(){
        this.friends = false;

    }
    public void setHomeTrue(){
        this.home = true;

    }
    public void setHomeFalse(){
        this.home = false;

    }



    public void setGridViewTrue(){
        this.gridView = true;
    }
    public void setGridViewFalse(){
        this.gridView = false;
    }
    public void setPhotoViewTrue(){
        this.photoView = true;
    }
    public void setPhotoViewFalse(){
        this.photoView = false;
    }
    public boolean getGridView(){
        return gridView;
    }


    // making the getters for the tags
    public boolean getTagFriends(){
        return this.friends;
    }


    public boolean getTagFamily(){
        return this.family;
    }


    public boolean getTagHome(){
        return this.home;
    }


    public boolean getTagVaction(){
        return this.vacation;
    }


    public void setAllToFalse(){
        setFriendsFalse();
        setVactionFalse();
        setFamilyFalse();
        setHomeFalse();
    }

    public float getScaleX(){
        return this.scaleFactorX;
    }

    public float getScaleY(){
        return this.scaleFactorY;
    }

    public void setScaleX(float scale){
        this.scaleFactorX = scale;
    }

    public void setScaleY(float scale){
        this.scaleFactorY = scale;
    }




    ///All mouse events below this line

    public void mouseClicked(MouseEvent e) {

        if (this.isEnabled()) {

                System.out.println("first click");
                if (e.getClickCount() >= 2) {
                    clickcount++;
                }
                if (clickcount % 2 == 0) {
                    this.setFlippedstate(false);

                } else {
                    this.setFlippedstate(true);

                }
                if (mySquare != null) {
                }

        } else{
            ;
            if(e.getClickCount() == 1){
                System.out.println("second click");
                redispatchToParent(e);
            }


        }

    }

    public void mousePressed(MouseEvent e){
        if (this.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(e)){
                gesture = true;


            }
            if (this.isFlippedstate()) {

                if (isPen) {
                    pointsArray.add(e.getPoint());


                    pPopulated = true;

                    PreviousPoint = new Point(e.getX(), e.getY());

                    x1 = e.getX();
                    y1 = e.getY();


                }
                if (isText) {
                    mySquare = new textField();
                    mySquare.xstart = e.getX();
                    mySquare.ystart = e.getY();
                    x1 = e.getX();
                    y1 = e.getY();
//                mySquare = new textField();
                    mySquare.rectangle = new Rectangle2D.Double(x1, y1, 0, 0);
                    rectangleArray.add(mySquare);
                    stringArray.add(ll);

                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            gesture =false;
            r = MainClass.theRecognizer.recognize(MainClass.theRecognizer.getPointsArrayGesture());
            if(r.getName() == "rectangle"){
                beautifySquare = true;
            }
            if(r.getName() == "circle"){
                beautifyCircle = true;
            }
//            System.out.println(r.getName());
//            System.out.println(r.getScore());
//            System.out.println(r.getBoundingBox());
//            System.out.println(r.getBoundingBox().getX()+ " The x position for the bounding box");



            MainClass.gestureSwitch(r);
            pointsArrayGesture.clear();
            gestureArray.clear();
            MainClass.theRecognizer.PointArray.clear();
            PreviousPoint = null;

        }
//        if (mySquare != null && (mySquare.rectangle != null)){
//
//            mySquare = null;


//        }
      repaint();


    }


    public void mouseDragged(MouseEvent e) {
        if (this.isEnabled()) {
            if(gesture){

                if(PreviousPoint != null) {
                    Point2D newPoint = new Point(e.getX(), e.getY());
                    Line2D myline = new Line2D.Double(PreviousPoint, newPoint);
                    PreviousPoint = new Point(e.getX(), e.getY());
                    MainClass.theRecognizer.PointArray.add(newPoint);
                    gestureArray.add(myline);

                } else {
                    PreviousPoint = new Point(e.getX(), e.getY());
                }
                repaint();

            }
            else if(this.isFlippedstate()) {
                if (isPen) {
                    if (PreviousPoint != null) {
                        Point newPoint = new Point(e.getX(), e.getY());
                        Line2D myline = new Line2D.Double(PreviousPoint, newPoint);
                        PreviousPoint = new Point(e.getX(), e.getY());
                        lineArray.add(myline);
                    } else {
                        PreviousPoint = new Point(e.getX(), e.getY());
                    }
                    repaint();

                }
                if (isText) {
                    x2 = e.getX();
                    y2 = e.getY();
                    int realX = Math.min(x1, x2);
                    int realY = Math.min(y1, y2);
                    int width = Math.abs(x1 - x2);
                    int height = Math.abs(y1 - y2);
                    mySquare.width = width;
                    mySquare.height = height;
                    mySquare.rectangle.setRect(realX, realY, width, height);
//
                    repaint();
                    requestFocus();

                }

            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    // Getter and setter for flipped state true = flipped;
    public boolean isFlippedstate() {
        return flippedstate;
    }

    public void setFlippedstate(boolean flippedstate) {
        this.flippedstate = flippedstate;
    }

    @Override
    public void keyTyped(KeyEvent f) {
//        System.out.println("keytyped");

    }

    @Override
    public void keyPressed(KeyEvent f) {

//        if (mySquare != null) {
            char mychar = f.getKeyChar();
            int code = f.getKeyCode();

            String myString = f.getKeyText(code);


            ll =  myString;

            mySquare.setText(ll);
            repaint();

    }

    @Override
    public void keyReleased(KeyEvent f) {
//        System.out.println("keyReleased");

    }


}


