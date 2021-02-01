import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;


public class MainClass implements MouseListener, MouseMotionListener{
	public static dollar.DollarRecognizer theRecognizer = new dollar.DollarRecognizer();
	public static int index = 0;
	boolean dubsClicks = false;
	static boolean fileflag;
	static JLabel bottomLabel;
	static boolean folderOfFiles;
	static boolean gridViewSelected;
	static boolean photoViewSelected;
    static ArrayList<photoComponent> myArray = new ArrayList<>();
	static JCheckBox vacation = new JCheckBox("vacation");
	static JCheckBox home = new JCheckBox("home");
	static JCheckBox family = new JCheckBox("family");
	static JCheckBox friends = new JCheckBox("friends");
	static JCheckBox vacationMagnet = new JCheckBox("Vacation Magnet");
	static JCheckBox homeMagnet = new JCheckBox("Home Magnet");
	static JCheckBox friendsMagnet = new JCheckBox("Friends Magnet");
	static JCheckBox familyMagnet = new JCheckBox("Family Magnet");
	boolean penSelected;
	boolean textSelected;
	boolean isPopulated = false;
	ArrayList<photoComponent> myFiles = new ArrayList<>();
	photoComponent label;
	lightTable lightobject;
	static JScrollPane pictureScrollArea = new JScrollPane();


	public MainClass(String[] commandargs){





		// Creating most of my buttons and panels to place within the Jframe

		JMenuBar menuBar;
		JMenu file, view;
		JMenuItem thingImport;
		JMenuItem thingDelete;
		JMenuItem thingExit;
		JMenuItem thingPhotoView;
		JMenuItem thingGridView;
		boolean scrollerFlag;
		ButtonGroup controlCenterGroup = new ButtonGroup();
		JButton pPrevious = new JButton("Previous");
		JButton delete = new JButton("Delete");
		JButton next = new JButton("Next");
		JPanel masterButtons = new JPanel();
		JPanel controlCenter = new JPanel();
		JPanel checkboxes = new JPanel();
		JRadioButton pen = new JRadioButton("Pen",true);
		JRadioButton text = new JRadioButton("Text", false);



		/*
		Action Listner with anonymous inner class for the pen JRadioButton
			if the pen is selected and only a folder of files has been imported and the array of pictures has been succesfully
			populated then the then it toggles the text or pen mode for the picture within the array

			else if
			the pen is selected and the only a single file has been uploaded then handle the case for a single file.
		 */

		pen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(pen.isSelected() && (!(fileflag)) && isPopulated){
					myArray.get(index).isPen = true;
					myArray.get(index).isText = false;


				} else if (pen.isSelected() && fileflag){
					label.isPen = true;
					label.isText = false;
				}
			}
		});

				/*
		Action Listner with anonymous inner class for the text JRadioButton
			if the text is selected and only a folder of files has been imported and the array of pictures has been succesfully
			populated then the then it toggles the text or pen mode for the picture within the array

			else if
			the text is selected and the only a single file has been uploaded then handle the case for a single file.
		 */
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(text.isSelected() && (!(fileflag)) && isPopulated) {
						myArray.get(index).isPen = false;
						myArray.get(index).isText = true;
					}

				 if (text.isSelected() && fileflag){
					label.isPen = false;
					label.isText = true;

				}
			}
		});






		// Creating the buttons and the group for the pen and text JradioButtons
		ButtonGroup penTextGroup = new ButtonGroup();
		penTextGroup.add(pen);
		penTextGroup.add(text);


		// Adding all my buttons to the two new panels called controlCenter and checkboxes
		// then adding those to a final panel called Master buttons in order to place them
		// on the left side of the screen in a vertical manner
		controlCenter.add(pPrevious);
		controlCenter.add(delete);
		controlCenter.add(next);
		controlCenter.add(pen);
		controlCenter.add(text);
		checkboxes.add(vacation);
		checkboxes.add(home);
		checkboxes.add(family);
		checkboxes.add(friends);
		checkboxes.add(vacationMagnet);
		checkboxes.add(homeMagnet);
		checkboxes.add(familyMagnet);
		checkboxes.add(friendsMagnet);
		checkboxes.setBounds(61, 11, 81, 140);
		masterButtons.add(checkboxes);
		masterButtons.add(controlCenter);
		checkboxes.setLayout(new BoxLayout(checkboxes ,BoxLayout.Y_AXIS));
		controlCenter.setLayout(new BoxLayout(controlCenter ,BoxLayout.Y_AXIS));
		masterButtons.setLayout(new BoxLayout(masterButtons, BoxLayout.Y_AXIS));

		//Jpanel to hold the buttons for the layout
		JPanel allButtons = new JPanel();
		allButtons.add(controlCenter);
		allButtons.add(masterButtons);
		allButtons.setLayout(new BoxLayout(allButtons, BoxLayout.Y_AXIS));








		//Just creating the basic frame here
		JFrame frame = new JFrame();
		frame.setSize(800,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(pictureScrollArea,BorderLayout.CENTER);
		frame.getContentPane().add(allButtons,BorderLayout.WEST);




		frame.show();

		// Creating the Label for the bottom
		bottomLabel = new JLabel("PhotoView");
		frame.getContentPane().add(bottomLabel, BorderLayout.SOUTH);





		//creating the menue bar
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);


		//creating the file in the Jmenu bar
		file = new JMenu("File");
		// add File menu to menubar
		menuBar.add(file);
		// creating and adding the items to the file menu
		thingImport = new JMenuItem("Import");
		file.add(thingImport);
		thingDelete = new JMenuItem("Delete");
		file.add(thingDelete);
		thingExit = new JMenuItem("Exit");
		file.add(thingExit);



			//creating the actions for the file menus subItems
			thingExit.addActionListener(e -> System.exit(0));




			//Creating the JFileChooser object and setting selection mode
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			/*
			Action listener and anonymous innerclass for the import drop down button in the file menu
			this first checks if a folder or a single file is being imported
			if it is a folder then it creates an array to house the contents of the folder
			it then turns all of the files into imageIcons, scales the imageIcons, and then converts them
			to photoComponents finally it will add the newly created photoComponent object to the array created
			to house the photos

			if it is just file that has been imported then the file is handled the same as above but
			without iteration through subsequent items.
			 */
			thingImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					folderOfFiles = true;
					index = 0;
					myArray = new ArrayList<>();
					fc.showOpenDialog(frame);
					// File treturnedfile;
					isPopulated = true;
					File treturnedfile = fc.getSelectedFile();
					if (treturnedfile.isDirectory()) {
						fileflag = false;
						File treturnedfileArray[] = treturnedfile.listFiles();
						String f;
						int i = treturnedfileArray.length - 1;
						while (i != 0) {
							f = treturnedfileArray[i].getPath();
							ImageIcon icon = new ImageIcon(f);
							Image image = icon.getImage();
							Image newimg = image.getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH);
							icon = new ImageIcon(newimg);
							photoComponent photo = new photoComponent(icon);
							photo.setBounds(0, 0, 200, 200);
							myArray.add(photo);
							i--;
						}

						delete.setEnabled(true);
						pPrevious.setEnabled(true);
						next.setEnabled(true);
						pictureScrollArea.setViewportView(myArray.get(index));


					} else if (treturnedfile.isFile()) {
						fileflag = true;

						ImageIcon icon = new ImageIcon(treturnedfile.getPath());
						Image image = icon.getImage();
						Image newimg = image.getScaledInstance(220, 220, java.awt.Image.SCALE_SMOOTH);
						icon = new ImageIcon(newimg);
						label = new photoComponent(icon);
						label.fileflagphoto = true;
						label.setBounds(0, 0, 200, 200);
//						System.out.println("Single image uploaded");
						pictureScrollArea.setViewportView(label);
						pPrevious.setEnabled(false);
						next.setEnabled(false);
						delete.setEnabled(true);
						frame.show();

					}

					/* The action listener and Anonymous inner class for the delete button in the drop down menu
						first is checks to see if it is dealing with a folder of files or just one single
						file. If it is dealing with a folder then it calls the remove method on the array for
						that houses all the PhotoComponent objects. it then removes the index of the array accordingly.
						it als changes the bottom label of the app to reflect what is happening in the application. In
						this instance it says "deleting item" in the bottom left and corner.
							If it is just a single file then the function just clears the image from the screen.
						and then disables the delete button.

					 */
					thingDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.out.println("DELETE IN MENU hit");
							if (!fileflag) {
								myArray.remove(index);
								if (myArray.size() == 0) {
									delete.setEnabled(false);
								}
								bottomLabel.setText("deleting item");
								index--;
								pictureScrollArea.setViewportView(null);
							} else {
								pictureScrollArea.setViewportView(null);
								delete.setEnabled(false);


							}

						}
					});


					frame.show();

				}

			});

			/*
			The vacation magnet listener
			 */

		vacationMagnet.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("vacation Magnet called");
				if (lightobject != null){
					if (vacationMagnet.isSelected()) {
						lightobject.addMagnet("vacationMagnet");
					} else if(!vacationMagnet.isSelected()) {
						lightobject.addMagnet("vacationMagnetoff");
					}

				}




			}
		});

		familyMagnet.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("family Magnet called");
				if (lightobject != null){
					if (familyMagnet.isSelected()) {
						lightobject.addMagnet("familyMagnet");
					} else if(!familyMagnet.isSelected()) {
						lightobject.addMagnet("familyMagnetoff");
					}

				}

			}
		});

		homeMagnet.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("home Magnet called");
				if (lightobject != null){
					if (homeMagnet.isSelected()) {
						lightobject.addMagnet("homeMagnet");
					} else if(!homeMagnet.isSelected()) {
						lightobject.addMagnet("homeMagnetoff");
					}

				}

			}
		});

		friendsMagnet.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("friends Magnet called");
				if (lightobject != null){
					if (friendsMagnet.isSelected()) {
						lightobject.addMagnet("friendsMagnet");
					} else if(!friendsMagnet.isSelected()) {
						lightobject.addMagnet("friendsMagnetoff");
					}

				}

			}
		});




		/*
		listener for the vacation JcheckBox (The tag Items)
		First thing this does is check whether or not the grid view button is checked
		I did this in kind of a round about way since I was still becoming familiar with Swing during this process
		I ended up creating the if statement seen on line 397 that checks wether or not our pictureScrollArea is an
		instance of the class I created called light table.-- The lightTable class is just a class I made in order to
		the grid view and the magnets as well. More on this in the lightTable class notes--
		If it was not in the grid view mode then I would simply add a call the SetVacationTrue() function on the photoComponent
		at the index of the array housing the photoComponent objects.
		This is handled the same way for all tags.
		 */
		vacation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
						index = ( (lightTable) pictureScrollArea.getViewport().getView() ).get(index).index;
				}
				if(folderOfFiles){
					if (vacation.isSelected() == true){
						myArray.get(index).setVactionTrue();
						bottomLabel.setText("You have selected a vacation tag");
					} else if( vacation.isSelected() == false){
						myArray.get(index).setVactionFalse();

						bottomLabel.setText("");
					}
				}

			}
		});

		home.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
					index = ( (lightTable) pictureScrollArea.getViewport().getView() ).get(index).index;
				}
				if(folderOfFiles){
					if (home.isSelected() == true){
						bottomLabel.setText("You have selected a home tag");
						myArray.get(index).setHomeTrue();
					} else{
						myArray.get(index).setHomeFalse();
						bottomLabel.setText("");

					}

				}
			}
		});

		family.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
					index = ( (lightTable) pictureScrollArea.getViewport().getView() ).get(index).index;
				}
				System.out.println("family");
				if (folderOfFiles) {
					if (family.isSelected() == true){
						bottomLabel.setText("You have selected a family tag");
						myArray.get(index).setFamilyTrue();
					} else{
						myArray.get(index).setFamilyFalse();
						bottomLabel.setText("");
					}


				}

			}
		});
		friends.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
					index = ( (lightTable) pictureScrollArea.getViewport().getView() ).get(index).index;
				}
				if (folderOfFiles) {
					if (friends.isSelected() == true){
						bottomLabel.setText("You have selected a friends tag");
						System.out.println(index);
						myArray.get(index).setFriendsTrue();
						System.out.println("This is the value now for the friends tag when true " + myArray.get(index).getTagFriends());
					} else if (friends.isSelected() == false){
						myArray.get(index).setFriendsFalse();

						System.out.println("This is the value now for the friends tag when false " + myArray.get(index).getTagFriends());
						bottomLabel.setText("");
					}

				}

			}
		});







		/* The action listener and Anonymous inner class for the delete button on the sidebar
			first checks to see if we are in the gridview mode by seeing if a lightTable object exists.
			if the lightObject exists then we handle the case for deleting if the lightTable is active.
			Otherwise we just delete the objects based on the index they hold in the photoComponent array "myArray"
			If it is a single file then it just clears the view and disable the button.
		 */
			delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (lightobject != null) {
					if (lightobject.get(index).getGridView()) {
						if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
							( (lightTable) pictureScrollArea.getViewport().getView() ).deletphoto();
							System.out.println("lighttable index" + lightobject.index);
							System.out.println("MainClass index" + index);
						}
					}
				}

				else {
					System.out.println("Delete index" + index);
					if (!fileflag) {
						myArray.remove(index);
						if (myArray.size() == 0) {
							index = 0;
						}
						bottomLabel.setText("deleting item");
						index--;
						if (index >= 0 && index < myArray.size()) {
							pictureScrollArea.setViewportView(myArray.get(index));
						} else {
							pictureScrollArea.setViewportView(null);
						}
					} else {
						pictureScrollArea.setViewportView(null);
						bottomLabel.setText("deleting item");

					}
				}
			}

		});
			/*
			This works very much the same way that the corresponding delete button works.
			Checks for lightTable object
				If it is the lightObject then the index through is decremented in both the main class and
				the lightTable class.
			if not a lightTable obecjt then it checks for folder of files or for a single file
			from here we index through array and set the pictureScrollArea view to display
			the current photo. We also disable the previous button if the photo is the last
			in the photoComponent array "myArray" or if it is a single file.

			 */
				pPrevious.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

					if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
						System.out.println("in the next");
						( (lightTable) pictureScrollArea.getViewport().getView()).decrementIndex();
						index--;
						selectTagsTrue(((lightTable) pictureScrollArea.getViewport().getView()).index);
						selectTagsTrue(index);
					}
				 else {
					bottomLabel.setText("Moving to Previous Item");
//					dollar.Result r = theRecognizer.recognize(theRecognizer.getPointsArrayGesture());
					if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
						System.out.println("crazy");
					}
					if (index != 0) {
						next.setEnabled(true);
						index--;
						selectTagsTrue(index);
						pictureScrollArea.setViewportView(myArray.get(index));
					}
					if (index == 0) {
						pPrevious.setEnabled(false);
					}

					System.out.println("Indext = " + index);
				}
			}

		});
				/*
				please see notes on the next button as they are handled identically
				 */
				next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

					if (pictureScrollArea.getViewport().getView() instanceof lightTable) {
						System.out.println("in the next");
						( (lightTable) pictureScrollArea.getViewport().getView() ).incrementIndex();
						index++;
						selectTagsTrue(((lightTable) pictureScrollArea.getViewport().getView() ).index);

						selectTagsTrue(index);

				} else {
					bottomLabel.setText("Moving to Next Item");
					if (index != ( myArray.size() - 1 )) {
						pPrevious.setEnabled(true);
						index++;
						pictureScrollArea.setViewportView(myArray.get(index));
						selectTagsTrue(index);

						System.out.println("Index = " + index);
						System.out.println("myArray ==" + myArray.size());

					}
					if (index == myArray.size() - 1) {
						next.setEnabled(false);
					}

				}
			}

		});
















		//creating the drop down menu that includes the JRadioButtons for the grid vs single view mode
/////////////////////////////////////////////////////////////////////////////////////////////
		//adding Radio Buttons to the View menu
		JRadioButton singlePhoto = new JRadioButton("Single Photo View",true);
		JRadioButton gridView = new JRadioButton("Grid Photo View", false);
		//creating the view in the Jmenu bar
		view = new JMenu("View");
		//add view to menubar
		menuBar.add(view);
		//creating and adding each menu item to the view menu
		view.add(singlePhoto);
		view.add(gridView);
/////////////////////////////////////////////////////////////////////////////////////////////


		//adding JRadioButtons to the group
		ButtonGroup viewButtonsGroup = new ButtonGroup();
		viewButtonsGroup.add(singlePhoto);
		viewButtonsGroup.add(gridView);


		//adding the action listener for the JRadioButtons
				singlePhoto.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if (lightobject != null) {
							lightobject.goTophotoview();
							if (singlePhoto.isSelected() && fileflag) {
								myArray.get(index).setPhotoViewTrue();
								myArray.get(index).setGridViewFalse();
								for (photoComponent c : myArray) {
									c.setScaleX(1);
									c.setScaleX(1);
								}
							}

						} else {

						if (singlePhoto.isSelected() && fileflag) {
							myArray.get(index).setPhotoViewTrue();
							myArray.get(index).setGridViewFalse();
							for (photoComponent c : myArray) {
								c.setScaleX(1);
								c.setScaleX(1);
							}
						}
					}


					bottomLabel.setText("photoView");
				}
			});

				gridView.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {


							if (gridView.isSelected() && folderOfFiles) {
								lightobject = new lightTable(myArray, pictureScrollArea);
//						pictureScrollArea.removeAll();

								pictureScrollArea.setViewportView(lightobject);
								frame.revalidate();
							}


					bottomLabel.setText("Grid");
				}
			});


				// This code is identical to the action listener for the import button it is purely for
				// handling args passed in through the command line.
				if (commandargs != null && commandargs.length > 0 ){
				File treturnedfileCommand = new File(commandargs[0]);

				if (treturnedfileCommand.isDirectory()){
						fileflag = false;
						folderOfFiles = true;
						isPopulated = true;
						File treturnedfileArray[] = treturnedfileCommand.listFiles();
						String n;
						String f;
						int i = treturnedfileArray.length - 1;
						while(i != 0){
							n = treturnedfileArray[i].getName();
							f = treturnedfileArray[i].getPath();
							ImageIcon icon = new ImageIcon(f);
							Image image = icon.getImage();
							Image newimg = image.getScaledInstance(220, 220,  java.awt.Image.SCALE_SMOOTH);
							icon = new ImageIcon(newimg);
							photoComponent photo = new photoComponent(icon);
							photo.setBounds(0,0,200,200);
							System.out.println("This is the Label.   " + f.getClass());
							myArray.add(photo);
							i--;
							System.out.println("tag vacation == " + photo.getTagVaction());
						}
							delete.setEnabled(true);
							pPrevious.setEnabled(true);
							next.setEnabled(true);
							pictureScrollArea.setViewportView(myArray.get(index));

					} else if (treturnedfileCommand.isFile()){
					fileflag = true;

					ImageIcon icon = new ImageIcon(treturnedfileCommand.getPath());
					Image image = icon.getImage();
					Image newimg = image.getScaledInstance(220,220, java.awt.Image.SCALE_SMOOTH);
					icon = new ImageIcon(newimg);
					label = new photoComponent(icon);
					label.fileflagphoto = true;
					label.setBounds(0,0,200,200);
//						System.out.println("Single image uploaded");
					pictureScrollArea.setViewportView(label);
					pPrevious.setEnabled(false);
					next.setEnabled(false);
					delete.setEnabled(true);
					frame.show();
					}
			}
	}



	public ArrayList<photoComponent> getArray(){
		return this.myArray;
	}

	public boolean GetGridView(){
		return this.gridViewSelected;
	}
	public boolean GetPhotoView(){
		return this.photoViewSelected;
	}
	public void setGrid(boolean y){
		this.gridViewSelected = y;
	}
	public void setPhoto(boolean y){
		this.photoViewSelected = y;
	}



	public void deselectALL(){
		vacation.setSelected(false);
		home.setSelected(false);
		friends.setSelected(false);
		family.setSelected(false);

	}
	public static void selectTagsTrue(int index){
		if(myArray.get(index).getTagVaction()){
			vacation.setSelected((true));
		} else{
			vacation.setSelected(false);
		}
		if(myArray.get(index).getTagHome()){
			home.setSelected((true));
		} else {
			home.setSelected(false);
		}
		if(myArray.get(index).getTagFamily()){
			family.setSelected((true));
		} else{
			family.setSelected(false);
		}
		if(myArray.get(index).getTagFriends()){
			friends.setSelected((true));
		} else{
			friends.setSelected(false);
		}


	}

	public static void gestureSwitch(dollar.Result n) {



		switch (n.getName()) {
			case "delete":
				if (!fileflag){
					myArray.remove(index);
					if (myArray.size() == 0) {
						index = 0;
					}
					bottomLabel.setText("deleting item" + n.getScore());
					index--;
					if (index >= 0 && index < myArray.size()) {
						pictureScrollArea.setViewportView(myArray.get(index));
					} else {
						pictureScrollArea.setViewportView(null);
					}
				} else {
					pictureScrollArea.setViewportView(null);
					bottomLabel.setText("deleting item");
				}
				break;
			case "v":
				System.out.println("logic for next");
				if (index != ( myArray.size() - 1 )) {
					index++;
					pictureScrollArea.setViewportView(myArray.get(index));
					selectTagsTrue(index);
					bottomLabel.setText("moving to next" + n.getScore());
					System.out.println("Index = " + index);
					System.out.println("myArray ==" + myArray.size());
				}
				break;
			case "caret":

				if (index != 0) {
					index--;
					selectTagsTrue(index);
					pictureScrollArea.setViewportView(myArray.get(index));
					bottomLabel.setText("Back to previous" + n.getScore());
				}
				System.out.println("logic for previous");
				break;
			case "check":
				myArray.get(index).toggleVacation();
				selectTagsTrue(index);
				bottomLabel.setText("check" + n.getScore());
				break;
			case "star":
				myArray.get(index).toggleFamily();
				selectTagsTrue(index);
				bottomLabel.setText("star" + n.getScore());
				break;
			case "pigtail":
				myArray.get(index).toggleFriends();
				selectTagsTrue(index);
				bottomLabel.setText("pigTail" + n.getScore());
				break;
			case "x":
				myArray.get(index).toggleHome();
				selectTagsTrue(index);
				bottomLabel.setText("x" + n.getScore());
				break;
		}
	}


















	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {

				MainClass app = new MainClass(args);

			}
		});
	}

	private void redispatchToChild(MouseEvent e){
		Component source=(Component)e.getSource();
		source.getParent().dispatchEvent(e);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		System.out.println("Command mouse");
		if(mouseEvent.getClickCount() >= 2){
			System.out.println("Assignemnt1/mouseClicked");
			myArray.get(index).setPhotoViewTrue();
		} else {
			dubsClicks = false;
		}

	}

	;
//      JScrollPane.addMouseListener(new MouseAdapter() {
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			System.out.println("Different MouseEvent");
//		}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

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

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {

	}
	public int getIndex(){
		return index;
	}





}