package controller;


import view.GUIView;

/**
 * Features interface.
 */
public interface Features {

  /**
   * call what is needed based on input in text box.
   * also reset text box
   * @param typed the input in text box
   */
  void callControllerOnInput(String typed);

  /**
   * set up the view, and add features.
   * @param v the view to set up
   */
  public void setView(GUIView v);

  /**
   * Change the format of the string.
   * to simulate the command prompts
   * so that it can be parsed by controllerImpls usual methods
   * @param typed the input from the user (file path, layer name, etc)
   * @param button the command prompt relevant to the button pressed (ex: new-project)
   */
  public void changeFormat(String typed, String button);


  /**
   * switch which layer is the current layer.
   * @param typed which layer to switch the current layer to
   */
  public void switchLayer(String typed);





}
