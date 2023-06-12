package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import model.Project;


/**
 * Implementation of GUI View.
 * that is implemented using Java
 *  * Swing.
 */
public class SwingGUIView extends JFrame implements GUIView {

  //the custom panel on which the board will be drawn
  //private JPanel boardPanel;
  //the model state
  //private IModel modelState;

  private Project model;

  //a label to display the score
  private JLabel scoreLabel;
  //a label to display any messages to the user
  private JLabel messageLabel;

  /*
  private JLabel fileOpenDisplay;
  private JLabel fileOpenDisplay2;
  private JLabel fileSaveDisplay;
  private JLabel fileSaveProjectDisplay;*/

  private JLabel display;
  private JButton setFilterButton;
  private JButton exitButton;
  private JButton newProjectButton;
  private JButton loadProjectButton;
  private JButton saveProjectButton;
  private JButton addLayerButton;
  private JButton addImageToLayerButton;
  private JButton saveImageButton;
  private JButton switchLayerButton;

  private JTextField input;
  private JLabel optionDisplay;
  //private JScrollPane mainScrollPane;

  /**
   * Constructs the gui view for the collager.
   * Will visually show the cureent state
   * @param state the current state of th image
   */
  public SwingGUIView(Project state) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("state cannot be null");
    }

    //public SwingGUIView(IModel state)
    //this.modelState = state;
    this.model = state;

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    /* main frame uses a border layout. Read about it here:
     *  https://docs.oracle.com/javase/10/docs/api/java/awt/BorderLayout.html
     *
     */



    this.setLayout(new BorderLayout());
    //initialize the custom board with the model state
    this.model.setHeightAndWidth(200,200);
    //this.model.addLayer("hi");
    //this.model.setFilter("hi","red-component");

    JPanel boardPanel = new BoardPanel(this.model);
    //add custom board to the center of the frame
    this.add(boardPanel,BorderLayout.CENTER);

    JScrollPane mainScrollPane = new JScrollPane(boardPanel);
    this.add(mainScrollPane);




    this.setSize(200, 200);
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //the textfield
    input = new JTextField(10);

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setBorder(BorderFactory.createTitledBorder(""));
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));


    // set filter
    //setFilterButton = new JButton("Set Filter");
    //setFilterButton.setActionCommand("Set Filter");

    this.newProjectButton = new JButton("New Project");
    newProjectButton.setActionCommand("New Project");

    //this.loadProjectButton =  new JButton("Load Project");
    //loadProjectButton.setActionCommand("Load Project");

    //this.saveProjectButton =  new JButton("Save Project");
    //saveProjectButton.setActionCommand("Save Project");

    this.addLayerButton =  new JButton("Add Layer");
    addLayerButton.setActionCommand("Add Layer");

    //this.addImageToLayerButton = new JButton("Add Image");
    //addImageToLayerButton.setActionCommand("Add Image");

    //this.saveImageButton =  new JButton("Save Image");
    //saveImageButton.setActionCommand("Save Image");

    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");

    this.switchLayerButton =  new JButton("Switch Layer");
    switchLayerButton.setActionCommand("Switch Layer");

    //this.messageLabel = new JLabel("hi");

    buttonsPanel.add(input);
    //buttonsPanel.add(setFilterButton);
    buttonsPanel.add(newProjectButton);
    buttonsPanel.add(addLayerButton);
    buttonsPanel.add(exitButton);
    buttonsPanel.add(switchLayerButton);
    //buttonsPanel.add(this.messageLabel);


    // filter options thing
    //JOptionsPane options dialog
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    buttonsPanel.add(optionsDialogPanel);

    setFilterButton = new JButton("Set Filter");
    setFilterButton.setActionCommand("Option");
    //optionButton.addActionListener(this);
    optionsDialogPanel.add(setFilterButton);

    optionDisplay = new JLabel("Normal");
    optionsDialogPanel.add(optionDisplay);







    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder(""));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    //mainPanel.add(dialogBoxesPanel);

    //file open
    // ADD IMAGE TO LAYER
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    addImageToLayerButton = new JButton("Add Image To Layer");
    addImageToLayerButton.setActionCommand("Add Image To Layer");
    //fileOpenButton.addActionListener(this);
    fileopenPanel.add(addImageToLayerButton);
    JLabel fileOpenDisplay = new JLabel("");
    fileopenPanel.add(fileOpenDisplay);

    // load project
    JPanel fileopenPanel2 = new JPanel();
    fileopenPanel2.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel2);
    loadProjectButton = new JButton("Load Project");
    loadProjectButton.setActionCommand("Load Project");
    //fileOpenButton.addActionListener(this);
    fileopenPanel2.add(loadProjectButton);
    JLabel fileOpenDisplay2 = new JLabel("");
    fileopenPanel2.add(fileOpenDisplay2);

    //file save image
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    saveImageButton = new JButton("Save an image");
    saveImageButton.setActionCommand("Save image");
    //fileSaveButton.addActionListener(this);
    filesavePanel.add(saveImageButton);
    JLabel fileSaveDisplay = new JLabel("");
    filesavePanel.add(fileSaveDisplay);

    // save project
    JPanel filesavePanel2 = new JPanel();
    filesavePanel2.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel2);
    saveProjectButton = new JButton("Save a project");
    saveProjectButton.setActionCommand("Save Project");
    //fileSaveButton.addActionListener(this);
    filesavePanel2.add(saveProjectButton);
    JLabel fileSaveProjectDisplay = new JLabel("");
    filesavePanel2.add(fileSaveProjectDisplay);


    //put them both in a panel. This is done mostly to arrange them properly
    JPanel panel = new JPanel();
    /*
    the panel uses a grid layout with two columns. The gridlayout
    will stretch the labels so that they are exactly half of the width
    of this panel.

    Since we mention that we want a grid of 2 columns, and we
    add exactly two things to it, it will use one row.
     */

    panel.setLayout(new GridLayout(0,2));
    /*
    panel.add(input);
    panel.add(setFilterButton);
    panel.add(newProjectButton);
    //panel.add(loadProjectButton);
    //panel.add(saveProjectButton);
    panel.add(addLayerButton);
    //panel.add(addImageToLayerButton);
    //panel.add(saveImageButton);
    panel.add(switchLayerButton);
    panel.add(exitButton);
    */
    panel.add(buttonsPanel);
    panel.add(dialogBoxesPanel);

    //add this panel to the bottom of the frame
    this.add(panel,BorderLayout.PAGE_END);



    pack();
    setVisible(true);
  }













  @Override
  public void refresh() {
    //refresh the score
    //this.scoreLabel.setText("Score: " );

    // refresh size???
    this.setSize(new Dimension(this.model.getWidth() + 200,
            this.model.getHeight() + 200));

    //this repaints the frame, which cascades to everything added
    //in the frame
    this.repaint();


  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message, "Error", JOptionPane.ERROR_MESSAGE);
    //this.messageLabel.setText(message);
  }












  /*
   In order to make this frame respond to keyboard events, it must be within strong focus.
   Since there could be multiple components on the screen that listen to keyboard events,
   we must set one as the "currently focussed" one so that all keyboard events are
   passed to that component. This component is said to have "strong focus".

   We do this by first making the component focusable and then requesting focus to it.
   Requesting focus makes the component have focus AND removes focus from whoever had it
   before.
 */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /*@Override
  public void toggleColor() {
    if (this.display.getForeground().equals(Color.RED))
      this.display.setForeground(Color.BLACK);
    else
      this.display.setForeground(Color.RED);
  }*/

  @Override
  public void addFeatures(Features features) {
    exitButton.addActionListener(evt -> features.changeFormat("","quit"));

    newProjectButton.addActionListener(evt -> features.changeFormat(input.getText(),
            "new-project "));
    addLayerButton.addActionListener(evt -> this.addLayer(input.getText(),
            features));
    switchLayerButton.addActionListener(evt -> features.switchLayer(input.getText()));

    // open file
    addImageToLayerButton.addActionListener(evt -> this.addImageToLayer(features, input.getText()));
    loadProjectButton.addActionListener(evt -> this.loadProject(features));

    // save file
    saveProjectButton.addActionListener(evt -> this.saveProject(features));
    saveImageButton.addActionListener(evt -> this.saveImage(features));


    // set filter options panel
    setFilterButton.addActionListener(evt -> this.setFilter(features));
  }

  /**
   * when add layer button is pressed.
   * @param typed layer name
   * @param features features
   */
  private void addLayer(String typed, Features features) {
    optionDisplay.setText("normal");
    features.changeFormat(typed,"add-layer ");
  }


  /**
   * when add image to layer button is pressed.
   * @param features features
   * @param typed image coordinates
   */
  private void addImageToLayer(Features features, String typed) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
    fchooser.setFileFilter(filter);
    String path = "";
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
      //fileOpenDisplay.setText(path);
    }
    // add coords to end?
    path = path + " " + typed;
    // DO SOMETHING W FEATURES
    features.changeFormat(path, "add-image-to-layer ");
  }

  /**
   * when load project button is pressed.
   * open file display and send to features
   * @param features features
   */
  private void loadProject(Features features) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
    fchooser.setFileFilter(filter);
    String path = "";
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
      //fileOpenDisplay.setText(path);
    }
    optionDisplay.setText("normal");
    features.changeFormat(path, "load-project ");
  }

  /**
   * when save image button is pressed.
   * open files, choose location, and send to features
   * @param features features
   */
  private void saveImage(Features features) {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    String path = "";
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
      //fileSaveDisplay.setText(path);
    }
    // DO SOMETHING W FEATURES??? SEND ABSOLUTE PATH?
    features.changeFormat(path, "save-image ");
  }

  /**
   * when save project button is pressed.
   * open files, choose location, and send to features
   * @param features features
   */
  private void saveProject(Features features) {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    String path = "";
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
      //fileSaveDisplay.setText(path);
    }
    // DO SOMETHING W FEATURES??? SEND ABSOLUTE PATH?
    features.changeFormat(path, "save-project ");
  }


  /**
   * when set filter button is pressed.
   * open option dialogue
   * @param features features
   */
  private void setFilter(Features features) {
    String[] options = {"red-component"
            , "green-component"
            , "blue-component"
            , "brighten-value"
            , "brighten-intensity"
            , "brighten-luma"
            , "darken-value"
            , "darken-intensity"
            , "darken-luma"
            , "difference"
            , "multiply"
            , "screen"};
    int retvalue = JOptionPane.showOptionDialog(
            this,
            "Please choose filter",
            "Options",
            JOptionPane.YES_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null, options, options[4]);
    optionDisplay.setText(options[retvalue]);
    features.changeFormat(options[retvalue], "set-filter ");
  }




  @Override
  public String getInputString() {
    return input.getText();
  }

  @Override
  public void clearInputString() {
    input.setText("");
  }

}
