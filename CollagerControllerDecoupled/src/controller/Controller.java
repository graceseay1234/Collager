package controller;

import java.io.IOException;

/**
 * Controller for a Collager.
 */
public interface Controller {

  /**
   * Handle user input and call functions from the model and view as needed.
   * @param input the user-input
   */
  public void handleUserInput(String input) throws IOException;


}
