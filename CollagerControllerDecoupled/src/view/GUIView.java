package view;

import controller.Features;

/**
 * GUI view.
 */
public interface GUIView {

  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and must be redrawn.
   */
  public void refresh();

  /**
   * Display a message in a suitable area of the GUI.
   * @param message the message to be displayed
   */
  public void renderMessage(String message);


  /**
   * Get the string from the text field and return it.
   * @return
   */
  public String getInputString();

  /**
   * Clear the text field. Note that a more general "setInputString" would work for this
   * purpose but would be incorrect. This is because the text field is not set programmatically
   * in general but input by the user.
   */

  void clearInputString();

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached
   * to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * call relevant methods on features.
   * essentially, when button is pressed,
   * recognize it and call relevant method in features
   * @param features Features interface
   */
  void addFeatures(Features features);

}
