import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import controller.Features;
import controller.FeaturesImpl;
import model.Project;
import model.ProjectImpl;
import controller.Controller;
import controller.ControllerImpl;
import view.GUIView;
import view.SwingGUIView;
import view.View;
import view.ViewImpl;


/**
 * Main class.
 * to run the program
 */
public class Main {
  /**
   * main method.
   * if:
   * -file, expects a path to a file
   * -text, expects text input from the command line
   * neither: expect nothing, GUI pops up
   * @param args arguments
   * @throws IOException if IoException.
   */

  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    Project model = new ProjectImpl();
    View v = new ViewImpl(model);

    //Controller controller = new ControllerImpl(new InputStreamReader(System.in), model, v);
    Controller controller = new ControllerImpl(model, v);

    String type = "";
    try {
      type = args[0];
    } catch (ArrayIndexOutOfBoundsException e) {
      // do nothing
    }


    boolean quit = false;

    if (type.equals("-file")) {
      Readable reader;
      try {
        reader = new FileReader(args[1]);
        Scanner scan = new Scanner(reader);
        while (scan.hasNextLine()) {
          String nextLine = scan.nextLine();
          System.out.println(nextLine);
          try {
            controller.handleUserInput(nextLine);
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }
        }
      } catch (FileNotFoundException ex) {
        //do something with the error
        System.out.println(ex.getMessage());
      }
    }
    if (type.equals("-text")) {
      // no set view
      v.printCommands();
      while (!quit) {
        System.out.print("Enter a command: ");
        String userInput = scanner.nextLine();
        if (userInput.equals("quit")) {
          quit = true;
        } else {
          try {
            controller.handleUserInput(userInput);
          } catch (IllegalArgumentException e) {
            //String m = e.getMessage();
            System.out.println(e.getMessage());
            //gui.renderMessage(m);
          } 
        }
      }
      return;
    }

    GUIView gui = new SwingGUIView(model);

    // do we make a features thing here??
    Features features = new FeaturesImpl(model, gui, controller);

    features.setView(gui);



  }
}
