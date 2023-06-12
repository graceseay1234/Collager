package controller;

import model.Project;
import view.View;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Implementation of Controller.
 * Holds model and view attributes to delegate to.
 * and a readable object(mainly for testing and functionality that may be added later)
 * Main function is to parse user input,
 * and delegate to the attributes functions as needed
 */
public class ControllerImpl implements Controller {

  // readable mainly for testing and functionality
  // we may add later(like controller directly getting input)
  private Readable readable;
  private Project model;

  private View view;


  /**
   * Controller constructor.
   * @param readable readable
   * @param model model
   * @param view view
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public ControllerImpl(Readable readable, Project model, View view) {
    if ((readable == null) || (model == null) || (view == null)) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.readable = readable;
    this.model = model;
    this.view = view;
  }

  /**
   * Controller constructor.
   * Readable defaults to new InputStreamReader(System.in)
   * @param model the model(Project)
   * @param view the view (View)
   * @throws IllegalArgumentException if any of the given parameters are null
   */
  public ControllerImpl(Project model, View view) {
    if ((model == null) || (view == null)) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.readable = new InputStreamReader(System.in);
    this.model = model;
    this.view = view;
  }





  /**
   * create a blank controller.
   * This for testing Mock purposes.
   */
  public ControllerImpl() {
    // intentional
  }


  @Override
  public void handleUserInput(String input)  {
    String[] inputParts = input.split(" ");
    String command = inputParts[0];


    switch (command) {
      case ("set-filter"):
        String layerName = inputParts[1];
        String filterOption = inputParts[2];
        this.addFilterInController(layerName,filterOption);
        break;
      case ("new-project"):
        int height = Integer.parseInt(inputParts[1]);
        int width = Integer.parseInt(inputParts[2]);
        //this.model = new ProjectImpl(height,width);
        this.model.setHeightAndWidth(height,width);
        break;
      case ("load-project"):
        String pathToProjectFile = inputParts[1];
        this.loadProject(pathToProjectFile);
        break;
      case ("save-project"):
        String filePathName = inputParts[1];
        model.saveProject(filePathName);
        break;
      case ("add-layer"):
        String aDlayerName = inputParts[1];
        model.addLayer(aDlayerName);
        break;
      case ("add-image-to-layer"):
        String aITLLayerName = inputParts[1];
        String aTILImageName = inputParts[2];
        int aTILXPos = Integer.parseInt(inputParts[3]);
        int aTILYPos = Integer.parseInt(inputParts[4]);
        model.addImageToLayer(aITLLayerName, aTILImageName, aTILXPos, aTILYPos);
        break;
      case ("save-image"):
        String fileName = inputParts[1];
        model.saveImage(fileName);
        break;
      case ("quit"):
        System.out.println("QUIT");
        System.exit(0);
        // main function catches this quit
        //quits
        break;
      default:
        break;
    }
  }


  /**
   * load a project from a file.
   * (currently supports text files)
   * @param filename the file path to load the project from.
   */
  public void loadProject(String filename) {
    if ((filename == null) || (filename.equals(""))) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    // reset model to blank
    this.model.setHeightAndWidth(this.model.getHeight(),
            this.model.getWidth());
    this.model.load(builder, filename);

  }


  /**
   * sort the filters.
   * sort them based on whether they are regular(only need image on layer layerName to function),
   * or composite(needs to know the image on layer layerName AND
   * the RGB/HSL values of the images on the layers under
   * layerName to function)
   * and then send them to different functions in the model as needed
   * This is in order to preserve memory and time, so that Composite images
   * are not being created for filters that do not need them
   * @param layerName name of layer to apply the filter to
   * @param filterOption type of filter to apply
   */
  private void addFilterInController(String layerName, String filterOption) {
    if (filterOption.equals("difference")
            || filterOption.equals("multiply")
            || filterOption.equals("screen")) {
      model.setCompositeFilter(layerName, filterOption);
    }
    else {
      model.setFilter(layerName, filterOption);
    }

  }










}
