import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.Timer;




public class lightTable extends JPanel implements MouseListener, MouseMotionListener {
    private static boolean stopPic;
    int click;
    boolean highlight = true;
    boolean dubsClicked = false;
    static int index;
    boolean pressed = false;
    int mouseX;
    int mouseY;
    int rows;
    Timer vacationtimer;
    Timer familytimer;
    Timer hometimer;
    Timer friendstimer;
    boolean mPressed= false;
    int cxOriginal;
    int cyOriginal;
    int getCyOriginal;
    boolean vacationBoolean = false;
    boolean familyBoolean = false;
    boolean friendsBoolean = false;
    boolean homeBoolean = false;

    public int numIterations = 0;

    boolean draggingVacation;
    boolean draggingHome;
    boolean draggingFriend;
    boolean draggingFamily;
    public static magnet vacationM = new magnet("vacation");
    public static magnet homeM = new magnet("home");
    public static magnet friendsM = new magnet("friends");
    public static magnet familyM = new magnet("family");
    boolean mouseRealeased = false;


    int xDraw;
    int yDraw;
    boolean mouseboolean = false;
    boolean nextButtons;
    private ArrayList<photoComponent> lightarray;
    public ArrayList<Graphics> lightRects = new ArrayList<>();
    private ArrayList<photoComponent> thearrayforkeepingmousepoints = new ArrayList<>();
    int theRedSquareX;
    int theRedSquareY;
    int indexofArrayforLighttable;
    JScrollPane scrollPane;

    public  lightTable(ArrayList<photoComponent> n,JScrollPane pictureScrollArea) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        scrollPane = pictureScrollArea;
        setLayout(null);
        lightarray = n;
        int x = 0;
        int y = 0;
        int indexcount = 0;

        /* populating the lightarray with scaled down version of the photoComponent objects
        and then laying them out in the lightTable in a grid pattern.
         */
        int photoCount = 1;
        for(photoComponent c : lightarray){
            c.setScaleX((float) 0.25);
            c.setScaleY((float) 0.25);
            c.setGridViewTrue();
            c.setPhotoViewFalse();
            c.setEnabled(false);
            c.setBounds(x,y, 70,70);
            c.setBorder(BorderFactory.createLineBorder(Color.black));
            c.XX = x;
            cxOriginal = x;
            cyOriginal = y;
            c.index = indexcount;
            this.add(c);
            thearrayforkeepingmousepoints.add(c);
            x+= 75;
            if(photoCount % 6 ==0 &&  photoCount != 1){
                x = 0;
                c.YY = y;
                y+= 80;
                rows++;
                c.row++;
            }


            photoCount++;
            indexcount++;

        }



        this.setPreferredSize(new Dimension(800,800));
//        this.add(vacationM);
//        vacationM.setBackground(Color.cyan);
//        vacationM.setOpaque(true);
//        vacationM.setVisible(true);
//        vacationM.setBounds(650,10,60,15);

    }

    public lightTable(boolean t){
        this.nextButtons = t;
    }

    public lightTable() {

    }

    // This is just one huge switch statement to handle the logic for the magnets it ends on line 953

    public void addMagnet(String nameOFMagnet){
        switch(nameOFMagnet){
            case "vacationMagnet":
                System.out.println("fanily = " + familyM.getMagonOrOff());


                vacationBoolean = true;
                System.out.println("called in switch in lightTable");
                this.add(vacationM);
                this.setComponentZOrder(vacationM, 0);
                vacationM.setBackground(Color.cyan);
                vacationM.setOpaque(true);
                vacationM.setVisible(true);
                vacationM.setBounds(600,10,60,15);
                vacationM.setMagonOrOff(true);
                vacationtimer = new Timer(10, new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        for (photoComponent c : lightarray) {
                            // handles vacation by itself
                            if (vacationBoolean && (!familyBoolean) && (!homeBoolean) && (!friendsBoolean)) {
                                System.out.println("only in vacation");
                                if(c.getTagVaction()) {
                                    if (c.getX() + 75 < vacationM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( vacationM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < vacationM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (vacationM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }
                                }
                            }
                            //handles vacation pared with family
                            else if(familyBoolean && vacationBoolean && (!homeBoolean) && (!friendsBoolean)){
                                System.out.println("familyM = " + familyM.getMagonOrOff() + " vacationM = " + vacationM.getMagonOrOff());
                                if(c.getTagFamily() && c.getTagVaction()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(familyM.getLocation(),vacationM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagVaction() && !c.getTagFamily()){
                                    if (c.getX() + 75 < vacationM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( vacationM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < vacationM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (vacationM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }
                            }
                            // handles vacation with home
                            else if(!familyBoolean && vacationBoolean && homeBoolean && (!friendsBoolean)){
                                System.out.println("familyM = " + familyM.getMagonOrOff() + " vacationM = " + vacationM.getMagonOrOff());
                                if(c.getTagFamily() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(homeM.getLocation(),vacationM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagVaction() && !c.getTagHome()){
                                    if (c.getX() + 75 < vacationM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( vacationM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < vacationM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (vacationM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }
                            }
                            // handles vacation with friends
                            else if(!familyBoolean && vacationBoolean && !homeBoolean && friendsBoolean){

                                if(c.getTagFamily() && c.getTagFriends()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(friendsM.getLocation(),vacationM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagVaction() && !c.getTagFriends()){
                                    System.out.println("In the right loop");

                                    if (c.getX() + 75 < vacationM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( vacationM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < vacationM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (vacationM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }
                                }
                            }
                        }
                    }
                });
                vacationtimer.start();
                break;
            case "vacationMagnetoff":
                System.out.println("tried to remove the vacation Magnet");
                vacationBoolean = false;
                if(!friendsBoolean && !vacationBoolean && !homeBoolean && !familyBoolean) {
                    int x = 0;
                    int y = 0;
                    int indexcount = 0;
                    int photoCount = 1;
                    for (photoComponent c : lightarray) {
                        c.setScaleX((float) 0.25);
                        c.setScaleY((float) 0.25);
                        c.setGridViewTrue();
                        c.setPhotoViewFalse();
                        c.setEnabled(false);
                        c.setBounds(x, y, 70, 70);
                        c.setBorder(BorderFactory.createLineBorder(Color.black));
                        c.XX = x;
                        cxOriginal = x;
                        cyOriginal = y;
                        c.index = indexcount;
                        this.add(c);
                        thearrayforkeepingmousepoints.add(c);
                        x += 75;
                        if (photoCount % 6 == 0 && photoCount != 1) {
                            x = 0;
                            c.YY = y;
                            y += 80;
                            rows++;
                            c.row++;
                        }


                        photoCount++;
                        indexcount++;

                    }
                }
                vacationtimer.stop();
                vacationM.setMagonOrOff(false);
                this.remove(vacationM);
             ;

                repaint();
                break;
            case "familyMagnet" :
                familyBoolean = true;
                System.out.println("in the case magnet");
                this.add(familyM);
                familyM.setBackground(Color.RED);
                familyM.setOpaque(true);
                familyM.setVisible(true);
                familyM.setBounds(600,100,60,15);
                familyM.setMagonOrOff(true);
                System.out.println( "famil mag ==== "+ familyM.getMagonOrOff());
                familytimer = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        for (photoComponent c : lightarray) {
                            // handles family by itsels
                            if (!vacationBoolean && familyBoolean && (!homeBoolean) && (!friendsBoolean)) {
                                System.out.println("only in vacation");
                                if(c.getTagFamily()) {
                                    if (c.getX() + 75 < familyM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( familyM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < familyM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (familyM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }
                                }

                            }
                            // handles family with vacation
                            else if(familyBoolean && vacationBoolean && !homeBoolean && !friendsBoolean){
                                if(c.getTagFamily() && c.getTagVaction()){
                                    Point midP = midPoint(familyM.getLocation(),vacationM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }  else if (c.getTagFamily() && !c.getTagVaction()){
                                    if (c.getX() + 75 < familyM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( familyM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < familyM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (familyM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }


                            }
                            // handles family and home
                            else if(familyBoolean && !vacationBoolean && homeBoolean && !friendsBoolean){
                                if(c.getTagFamily() && c.getTagHome()){
                                    Point midP = midPoint(familyM.getLocation(),homeM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }  else if (c.getTagFamily() && !c.getTagHome()){
                                    if (c.getX() + 75 < familyM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( familyM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < familyM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (familyM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }
                                else if (!c.getTagFamily() && c.getTagHome()){
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }


                            }
                            //friends and family
                            else if(familyBoolean && !vacationBoolean && !homeBoolean && friendsBoolean){
                                if(c.getTagFamily() && c.getTagHome()){
                                    Point midP = midPoint(familyM.getLocation(),friendsM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }  else if (c.getTagFamily() && !c.getTagFriends()){
                                    if (c.getX() + 75 < familyM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( familyM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < familyM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (familyM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }


                            }
                        }

                    }

                });
                familytimer.start();
                break;
            case "familyMagnetoff" :
                familyBoolean = false;
                if(!friendsBoolean && !vacationBoolean && !homeBoolean && !familyBoolean) {
                    int x = 0;
                    int y = 0;
                    int indexcount = 0;
                    int photoCount = 1;
                    for (photoComponent c : lightarray) {
                        c.setScaleX((float) 0.25);
                        c.setScaleY((float) 0.25);
                        c.setGridViewTrue();
                        c.setPhotoViewFalse();
                        c.setEnabled(false);
                        c.setBounds(x, y, 70, 70);
                        c.setBorder(BorderFactory.createLineBorder(Color.black));
                        c.XX = x;
                        cxOriginal = x;
                        cyOriginal = y;
                        c.index = indexcount;
                        this.add(c);
                        thearrayforkeepingmousepoints.add(c);
                        x += 75;
                        if (photoCount % 6 == 0 && photoCount != 1) {
                            x = 0;
                            c.YY = y;
                            y += 80;
                            rows++;
                            c.row++;
                        }


                        photoCount++;
                        indexcount++;

                    }
                }
                familytimer.stop();
                this.remove(familyM);
                familyM.setVisible(false);
                familyM.setMagonOrOff(false);
                repaint();
                break;
            case "homeMagnet":
                homeBoolean = true;
                System.out.println("called in switch in lightTable");
                this.add(homeM);
                this.setComponentZOrder(homeM, 0);
                homeM.setBackground(Color.gray);
                homeM.setOpaque(true);
                homeM.setVisible(true);
                homeM.setBounds(600,200,60,15);
                homeM.setMagonOrOff(true);


                hometimer = new Timer(10, new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        // handles family by itself
                        for (photoComponent c : lightarray) {
                            if (homeBoolean && (!familyBoolean) && (!vacationBoolean) && (!friendsBoolean)) {
                                System.out.println("in home");
                                if(c.getTagHome()) {
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }
                                }
                            }

                            // handles home paird with family

                            else if(familyBoolean && !vacationBoolean && homeBoolean && (!friendsBoolean)){
                                System.out.println("poo = " + familyM.getMagonOrOff() + " vacationM = " + vacationM.getMagonOrOff());
                                if(c.getTagFamily() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(familyM.getLocation(),homeM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagHome() && !c.getTagFamily()){
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }else if (!c.getTagHome() && c.getTagFamily()){
                                    System.out.println("should be here");
                                    if (c.getX() + 75 < familyM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( familyM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < familyM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (familyM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }
                            // handles home paird with friend
                            else if(!familyBoolean && !vacationBoolean && homeBoolean && friendsBoolean){
                                if(c.getTagFriends() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(friendsM.getLocation(),homeM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagHome() && !c.getTagFriends()){
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (!c.getTagHome() && c.getTagFriends()){
                                    if (c.getX() + 75 < friendsM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( friendsM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < friendsM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (friendsM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }

                            // handles home with vacation
                            else if(!familyBoolean && vacationBoolean && homeBoolean && !friendsBoolean){
                                if(c.getTagVaction() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(vacationM.getLocation(),homeM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagHome() && !c.getTagVaction()){
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }
                                else if (!c.getTagHome() && c.getTagVaction()){
                                    if (c.getX() + 75 < vacationM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( vacationM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < vacationM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (vacationM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }
                        }
                    }
                });
                hometimer.start();
                break;



            case "homeMagnetoff":
                homeBoolean = false;
                if(!friendsBoolean && !vacationBoolean && !homeBoolean && !familyBoolean) {
                    int x = 0;
                    int y = 0;
                    int indexcount = 0;
                    int photoCount = 1;
                    for (photoComponent c : lightarray) {
                        c.setScaleX((float) 0.25);
                        c.setScaleY((float) 0.25);
                        c.setGridViewTrue();
                        c.setPhotoViewFalse();
                        c.setEnabled(false);
                        c.setBounds(x, y, 70, 70);
                        c.setBorder(BorderFactory.createLineBorder(Color.black));
                        c.XX = x;
                        cxOriginal = x;
                        cyOriginal = y;
                        c.index = indexcount;
                        this.add(c);
                        thearrayforkeepingmousepoints.add(c);
                        x += 75;
                        if (photoCount % 6 == 0 && photoCount != 1) {
                            x = 0;
                            c.YY = y;
                            y += 80;
                            rows++;
                            c.row++;
                        }


                        photoCount++;
                        indexcount++;

                    }
                }
                hometimer.stop();
                this.remove(homeM);
                homeM.setVisible(false);
                homeM.setMagonOrOff(false);
                repaint();
                break;



            case "friendsMagnet":
                friendsBoolean = true;

                System.out.println("called in switch in lightTable");
                this.add(friendsM);
                this.setComponentZOrder(friendsM, 0);
                friendsM.setBackground(Color.pink);
                friendsM.setOpaque(true);
                friendsM.setVisible(true);
                friendsM.setBounds(600,300,60,15);
                friendsM.setMagonOrOff(true);


                friendstimer = new Timer(10, new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        // handles friends by itself
                        for (photoComponent c : lightarray) {
                            if (!homeBoolean && (!familyBoolean) && (!vacationBoolean) && friendsBoolean) {
                                System.out.println("in home");
                                if(c.getTagFriends()) {
                                    if (c.getX() + 75 < friendsM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( friendsM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                        lightTable.stopPic = false;
                                    }
                                    if (c.getY() + 75 < friendsM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (friendsM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }
                                }
                            }

                            // handles friends paird with family

                            else if(familyBoolean && !vacationBoolean && !homeBoolean && friendsBoolean){
                                System.out.println("poo = " + familyM.getMagonOrOff() + " vacationM = " + vacationM.getMagonOrOff());
                                if(c.getTagFamily() && c.getTagFriends()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(familyM.getLocation(),friendsM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagFriends() && !c.getTagFamily()){
                                    if (c.getX() + 75 < friendsM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( friendsM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < friendsM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (friendsM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }
                            // handles friends paird with home
                            else if(!familyBoolean && !vacationBoolean && homeBoolean && friendsBoolean){
                                if(c.getTagFriends() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(friendsM.getLocation(),homeM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagHome() && !c.getTagFriends()){
                                    if (c.getX() + 75 < homeM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( homeM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < homeM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (homeM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }

                            // handles friends with vacation
                            else if(!familyBoolean && vacationBoolean && !homeBoolean && friendsBoolean){
                                if(c.getTagVaction() && c.getTagHome()){
                                    System.out.println("In vacation and family");
                                    Point midP = midPoint(vacationM.getLocation(),friendsM.getLocation());
                                    if (c.getX() + 75 < midP.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( midP.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < midP.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (midP.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                } else if (c.getTagFriends() && !c.getTagVaction()){
                                    if (c.getX() + 75 < friendsM.getX()) {
                                        c.setLocation(c.getX() + 1, c.getY());
                                    }
                                    if (( friendsM.getX() - ( c.getX() + 75 ) ) <= 6) {
                                        c.setLocation(c.getX() - 1, c.getY());
                                    }
                                    if (c.getY() + 75 < friendsM.getY()) {
                                        c.setLocation(c.getX(), c.getY() + 1);
                                    }
                                    if (friendsM.getY() < c.getY()) {
                                        c.setLocation(c.getX(), c.getY() - 1);
                                    }

                                }

                            }
                        }
                    }
                });
                friendstimer.start();
                break;
            case "friendsMagnetoff":

                friendsBoolean = false;
                if(!friendsBoolean && !vacationBoolean && !homeBoolean && !familyBoolean) {
                    int x = 0;
                    int y = 0;
                    int indexcount = 0;
                    int photoCount = 1;
                    for (photoComponent c : lightarray) {
                        c.setScaleX((float) 0.25);
                        c.setScaleY((float) 0.25);
                        c.setGridViewTrue();
                        c.setPhotoViewFalse();
                        c.setEnabled(false);
                        c.setBounds(x, y, 70, 70);
                        c.setBorder(BorderFactory.createLineBorder(Color.black));
                        c.XX = x;
                        cxOriginal = x;
                        cyOriginal = y;
                        c.index = indexcount;
                        this.add(c);
                        thearrayforkeepingmousepoints.add(c);
                        x += 75;
                        if (photoCount % 6 == 0 && photoCount != 1) {
                            x = 0;
                            c.YY = y;
                            y += 80;
                            rows++;
                            c.row++;
                        }


                        photoCount++;
                        indexcount++;

                    }
                }
                friendstimer.stop();
                this.remove(friendsM);
                friendsM.setVisible(false);
                friendsM.setMagonOrOff(false);
                repaint();
                break;
        }

    }
//    lightTable.setComponentZOrder(vacationM, 0);


    public void setNextButtons(boolean t){
        this.nextButtons = t;
    }
    public boolean getNextButtons(){
        return this.nextButtons;
    }
    public void setTheRedSquareX(int x){ this.theRedSquareX = x; }
    public void setTheRedSquareY(int y){
        this.theRedSquareY = y;
    }

    /*
    Finding the midpoint for two magnets As seen in the Youtube demo when a picture has two
    is tagged with more than one tag and both magnets are present.
     */

    public Point midPoint(Point aplha, Point beta){
        int pointX = (int) (aplha.getX() + beta.getX())/2;
        int pointY = (int) (aplha.getY() + beta.getY())/2;

        Point newPoint = new Point(pointX,pointY);
        return newPoint;

    }

    /*
    This highlights the thumbnails based on the indext selected by adding a red frame around the thumbnail.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

            double i = index;
            double six = 6.0;
            double division = index/6;
            int finish = (int) division;
                yDraw = 80 *finish;
                xDraw = ( index % 6 ) * 75;
            g.setColor(Color.RED);
            g.fillRect(xDraw - 3, yDraw - 3, 75, 75);
    }



    /*
    Just incrementing/decrementing the index variable of the lightTable class
    also calls repaint to clear the old highlight
     */
    public void incrementIndex(){

        index++;
        repaint();
    }
    public void decrementIndex(){
        if(index > 0){
            index--;
        };
        repaint();
    }

    /*
    Handles the case for deleting a photo only when in the gridView mode
    After deleting the index from the lightArray it then recreates the lightarray
    and calls repaint() to show the change in the lightTable
     */
    public void deletphoto(){
        if(index > 0 && index < lightarray.size()) {

            System.out.println("Delet = " + index);
            lightarray.remove(index);
            System.out.println("size of " + lightarray.size());
            System.out.println("delete called");
            this.remove(lightarray.get(index));

            int x = 0;
            int y = 0;
            int indexcount = 0;
            int photoCount = 1;



            int i = 0;
            for (photoComponent c : lightarray) {
                c.setScaleX((float) 0.25);
                c.setScaleY((float) 0.25);
                c.setGridViewTrue();
                c.setPhotoViewFalse();
                c.setEnabled(false);
                c.setBounds(x, y, 70, 70);
                c.setBorder(BorderFactory.createLineBorder(Color.black));
                c.XX = x;
                c.index = indexcount;
                this.add(c);
                thearrayforkeepingmousepoints.add(c);
                x += 75;
                if (photoCount % 6 == 0 && photoCount != 1) {
                    x = 0;
                    y += 80;

                }


                photoCount++;
                indexcount++;

            }
            y = 0;
            x = 0;
            repaint();
        }



    }


    String[] p =  {"k","d"};












    /*
    Handles when moving from grid view mode back to the single view
    If any picture is highlighted in the gridview and singleview is selected then
    That higlighted picture will be show in single view. Also sets the booleans
    for if grid view or single view is selected
     */
public void goTophotoview(){
    thearrayforkeepingmousepoints.get(index).setEnabled(true);
    scrollPane.setViewportView(thearrayforkeepingmousepoints.get(index));

    for(photoComponent j : thearrayforkeepingmousepoints){
        j.setGridViewFalse();
        j.setPhotoViewTrue();
    }

}













    /*
    Calling the parent classes mouse position in order to
    get the actual mouseposition within the whole view and not just the
    mouse position relative each picture.
     */
    private void redispatchToParent(MouseEvent e){
        Component source=(Component)e.getSource();
        System.out.println("Right here Trea " + source.getParent().getParent());
        source.getParent().getParent().dispatchEvent(e);
    }

    /*
    mouse eventhandler for clicking on thumbnails
    handles finding out which picture was clicked on in the grid and then changing the active
    index based on the click. Supposed to handle the event that if you double click on a picture in
    gridview mode then it will return you to the singleview mode but is not currently working.
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(!pressed) {
            mouseX = mouseEvent.getX();
            System.out.println("in mouse/lightTable");

            mouseY = mouseEvent.getY();
            System.out.println("Y = " + mouseY);

            System.out.println("third click");
            mouseboolean = true;
            double m = (double) mouseY;
            double k = (double) rows;
            double b = ( m ) / ( 80.0 * k ) * 10.0;
            double d = b / k;

            int f = (int) d;

            if (mouseX < 70 && mouseX > 0) {
                System.out.println("index" + index);
                index = 0 + f * 6;


                System.out.println(index);
            } else if (mouseX < 145 && mouseX > 75) {
                System.out.println("index" + index);
                index = 1 + f * 6;


                System.out.println(index);
            } else if (mouseX < 220 && mouseX > 150) {
                System.out.println("index" + index);
                index = 2 + f * 6;


                System.out.println(index);
            } else if (mouseX < 295 && mouseX > 225) {
                System.out.println("index" + index);
                index = 3 + f * 6;


                System.out.println(index);
            } else if (mouseX < 370 && mouseX > 300) {
                System.out.println("index" + index);
                index = 4 + f * 6;


                System.out.println(index);
            } else if (mouseX < 445 && mouseX > 375) {
                index = 5 + f * 6;


            }

            System.out.println("This is mouseX " + mouseX);

            if (mouseEvent.getClickCount() >= 2) {
                MainClass.gridViewSelected = false;
                MainClass.index = index;
                System.out.println(">2");
                lightarray.get(index).setEnabled(true);
                scrollPane.setViewportView(lightarray.get(index));

                for (photoComponent j : lightarray) {
                    j.setGridViewFalse();
                    j.setPhotoViewTrue();
                }


//             System.out.println(this.getParent().getParent());
                repaint();

            }
            System.out.println("Here is the index " + index);
            repaint();
        }
        pressed = false;
    }



    /*
    Handles the drag and drop actions of the magnets
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {


        if (vacationM.getBounds().contains(mouseEvent.getPoint())){
            draggingVacation = true;




        } else if (friendsM.getBounds().contains(mouseEvent.getPoint())){
            draggingFriend = true;

            System.out.println("in the friends bounds");
        } else if (homeM.getBounds().contains(mouseEvent.getPoint())){
            draggingHome = true;
        } else if (familyM.getBounds().contains(mouseEvent.getPoint())){
            draggingFamily = true;

        }
        System.out.println(mouseEvent.getSource());
        pressed = true;

    }
    /*
    Handles the drag and drop actions of the magnets
     */

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("mouse RELEASED");
        mouseRealeased = true;
        draggingVacation = false;
        draggingHome = false;
        draggingFriend = false;
        draggingFamily = false;


    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    /*
    Handles the drag and drop actions of the magnets
    */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {


        if(draggingVacation){
            System.out.println("dragging vacation");
            vacationM.setLocation(mouseEvent.getPoint());

        }
        else if(draggingFamily) {
            familyM.setLocation(mouseEvent.getPoint());
        } else if (draggingHome){
            homeM.setLocation(mouseEvent.getPoint());
        } else if (draggingFriend){
            friendsM.setLocation(mouseEvent.getPoint());
        }








    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public photoComponent get(int index) {
        return lightarray.get(index);
    }
}

