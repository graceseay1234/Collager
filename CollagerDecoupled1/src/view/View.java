package view;

import java.io.IOException;

/**
 * View interface.
 */
public interface View {

  /**
   * Renders a given message to the data output in the implementation.
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Print the commands the user can use in this program.
   */
  public void printCommands();
}
