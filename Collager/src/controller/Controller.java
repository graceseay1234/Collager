package controller;


/**
 * Controller for a Collager Project.
 * handles text input of commands.
 */
public interface Controller {

  /**
   * Handle user input and call functions from the model and view as needed.
   * the commands are inputted and then sent to this function
   * currently supported commands are:
   * - new-project       (creates a new project)
   * - save-project      (saves project as a text file)
   * - save-image        (saves project as a PPM, PNG, or JPEG depending on input)
   * - add-layer         (add a blank white layer to the project)
   * - add-image-to-layer (adds a PPM, PNG, or JPEG to the layer specified)
   * - set-filter   (changes the filter on the layer specified to the specified filter)
   * - load-project (loads a project from a txt file)
   * - quit         (ends the program)
   * @param input the user-input, or the input from the gui converted to user-input form
   */
  public void handleUserInput(String input) ;


}
