package controller;

import java.io.IOException;

import model.Project;
import view.GUIView;


/**
 * implement features interface.
 */
public class FeaturesImpl implements Features {


  protected GUIView view;
  protected Project project;
  protected Controller controller;


  /*
   * Features Controller constructor.
   * @param model model
   * @param view view
   */
  /*public FeaturesImpl(IModel model, GUIView view) {
    if ((model == null) || (view == null)) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.model = model;
    this.view = view;
  }*/


  /**
   * features controller constructor.
   * @param p Project
   * @param view GUIView
   * @param c Controller
   */
  public FeaturesImpl(Project p, GUIView view, Controller c) {
    if ((view == null) || (p == null) || (c == null)) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.project = p;
    this.view = view;
    this.controller = c;
  }

  /*public FeaturesImpl(IModel m) {
    model = m;
  }*/

  @Override
  public void setView(GUIView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }



  @Override
  public void callControllerOnInput(String typed) {
    System.out.println("IN CALL");
    System.out.println(typed);
    // send to controller
    try {
      controller.handleUserInput(typed);
      System.out.println("SENT\n");
    } catch (IOException e) {
      // do nothing
    } catch (IllegalArgumentException a) {
      view.renderMessage(a.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
      this.view.renderMessage("Missing input from the text field " +
              "(add-image coordinates or new-project height and width for example)");
    }

    //clear input textfield
    view.clearInputString();

    //refresh view
    if (typed.contains("set-filter")
            || typed.contains("add-image-to-layer")
            || typed.contains("new-project")
            || typed.contains("load-project")
            || typed.contains("add-layer")) {
      System.out.println("REFRESH VIEW");
      view.refresh();
    }


    //set focus back to main frame so that keyboard events work
    view.resetFocus();

  }



  @Override
  public void switchLayer(String typed) {
    try {
      this.project.switchCurrentLayer(typed);
      view.refresh();
    } catch (IllegalArgumentException e) {
      this.view.renderMessage(e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
      this.view.renderMessage("Missing input from the text field " +
              "(add-image coordinates or new-project height and width for example)");
    }
    view.clearInputString();
    view.resetFocus();
  }

  @Override
  public void changeFormat(String typed, String button) {
    if ( (button.equals("set-filter ")) || (button.equals("add-image-to-layer "))) {
      typed = button + this.project.getCurrentLayerName() + " " + typed;
    }
    else {
      typed = button + typed;
    }
    this.callControllerOnInput(typed);
  }










  /*
  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void toggleColor() {
    view.toggleColor();
  }

  @Override
  public void makeUppercase() {
    String text = model.getString();
    text = text.toUpperCase();
    view.setEchoOutput(text);
  }

  @Override
  public void restoreLowercase() {
    String text = model.getString();
    view.setEchoOutput(text);
  }

  @Override
  public void echoOutput(String typed) {
    //send text to the model
    model.setString(typed);

    //clear input textfield
    view.clearInputString();
    //finally echo the string in view
    String text = model.getString();
    view.setEchoOutput(text);

    //set focus back to main frame so that keyboard events work
    view.resetFocus();
  }



  @Override
  public void setFilter(String typed) {
    typed = "set-filter " + this.project.getCurrentLayerName() + " " + typed;
    this.callControllerOnInput(typed);
  }


  @Override
  public void newProject(String typed) {
    typed = "new-project " + typed;
    this.callControllerOnInput(typed);
  }

  @Override
  public void addLayer(String typed) {
    typed = "add-layer " + typed;
    this.callControllerOnInput(typed);
  }


  @Override
  public void addImageToLayer(String typed) {
    typed = "add-image-to-layer " + this.project.getCurrentLayerName() + " " + typed;
    System.out.println(typed);
    this.callControllerOnInput(typed);
  }

  @Override
  public void saveImage(String typed) {
    // NEED TO FIGURE THIS OUT MORE LATER
    typed = "save-image " + typed;
    this.callControllerOnInput(typed);
  }

  @Override
  public void saveProject(String typed) {
    // NEED TO FIGURE THIS OUT MORE LATER
    typed = "save-project " + typed;
    this.callControllerOnInput(typed);
  }



  @Override
  public void loadProject(String typed) {
    // NEED TO FIGURE THIS OUT MORE SPECIFICALLY LATER
    typed = "load-project " + typed;
    this.callControllerOnInput(typed);
  }
  */






}
