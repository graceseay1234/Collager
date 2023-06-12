package view;

import controller.Features;

/**
 * GUI view.
 * - View that builds a Graphical User Interface (GUI)
 * - it can refresh the screen, renderMessages,
 * get and clear strings from a text field, reset the focus of the view,
 * and add Features to the view(like buttons)
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
   * @return what has been typed in the text field
   */
  public String getInputString();

  /**
   * Clear the text field.
   */
  void clearInputString();

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached
   * to it, so that keyboard events will still flow through if needed.
   */
  void resetFocus();

  /**
   * call relevant methods in features.
   * essentially, set all buttons and other elements up so,
   * when button is pressed for example,
   * recognize it and call the relevant method in features
   * so that the action it represents can occur
   * @param features Features interface
   */
  void addFeatures(Features features);

}
