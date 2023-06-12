package view;

import java.io.IOException;

/**
 * View interface.
 * A text-based view
 * which can render a message to be seen by the user
 * and print commands to be seen by the user
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
   * Shows the user how to use the text-based version of this program
   * by printing a welcome message and giving the supported commands
   * and how they should be entered.
   */
  public void printCommands();
}
