package controller;


import view.GUIView;

/**
 * Features interface.
 * interfaces between the GUI View and controller
 * makes the GUI methods work,
 * like when a button is pressed it leads to a method here,
 * and then usually calls a method in the controller after transforming the
 * input to the correct format
 * and doing what is needed to teh GUI, like refreshing the view to redraw
 * or clearing the text box.
 */
public interface Features {

  /**
   * call what is needed based on input in text box.
   * also reset the text box, and focus
   * call handleUserInput in Controller to perform the actions based on typed
   * and reset the view if a method was called that affects the
   * way the view is drawn (set-filter, add-image-to-layer, new-project,
   * load-project, add-layer)
   * @param typed the input from the GUIView in the format of a text command
   */
  void callControllerOnInput(String typed);

  /**
   * set the view attribute, and call add features in view.
   * to set up the button features
   * @param v a GUIView
   */
  public void setView(GUIView v);

  /**
   * Change the format of the string to simulate the text commands.
   * so that it can be parsed by the Controller's usual methods
   * (essentially convert what actions occurred in the GUI into
   * what they would have looked like had the user typed them in with command prompts)
   * @param typed the input from the GUI text box (file path, layer name, etc)
   * @param button the command prompt relevant to the button pressed in the GUI (ex: new-project)
   */
  public void changeFormat(String typed, String button);


  /**
   * switch which layer is the current layer in Project.
   * @param typed which layer to switch the current layer to
   */
  public void switchLayer(String typed);





}
