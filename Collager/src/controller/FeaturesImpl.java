package controller;

import model.Project;
import view.GUIView;


/**
 * implementation of Features interface.
 * has a GUIView, Project, and Controller attribute
 * Project and Controller are used to delegate to relevant methods
 * GUIView is used to call relevant methods to the GUI,
 * like refreshing the GUI and clearing the text box
 */
public class FeaturesImpl implements Features {

  protected GUIView view;
  protected final Project project;
  private final Controller controller;

  /**
   * features controller constructor.
   * @param p Project
   * @param view GUIView
   * @param c Controller
   * @throws IllegalArgumentException if any given parameters are null
   */
  public FeaturesImpl(Project p, GUIView view, Controller c)
          throws IllegalArgumentException {
    if ((view == null) || (p == null) || (c == null)) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.project = p;
    this.view = view;
    this.controller = c;
  }

  @Override
  public void setView(GUIView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }



  @Override
  public void callControllerOnInput(String typed) {
    //System.out.println("IN CALL");
    //System.out.println(typed);
    // send to controller
    try {
      controller.handleUserInput(typed);
      //System.out.println("SENT\n");
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



}
