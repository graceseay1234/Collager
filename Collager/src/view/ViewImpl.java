package view;

import java.io.IOException;
import model.Project;

/**
 * Implements View.
 * A text-based view for a Collager Project.
 * Contains a Project to see the current status of the Collager Project(the model)
 * Contains an Appendable to write messages to so that the user can view them
 */
public class ViewImpl implements View {
  private final Project p;
  private final Appendable destination;

  /**
   * create a ViewImpl.
   * @param p the given project for this view
   * @throws IllegalArgumentException if any parameters are null
   */
  public ViewImpl(Project p) throws IllegalArgumentException {
    if (p == null) {
      throw new IllegalArgumentException("given project cannot be null");
    }
    this.p = p;
    this.destination = System.out;
  }

  /**
   * create a ViewImpl.
   * @param p the given project
   * @param destination the given destination to display messages
   * @throws IllegalArgumentException if any given parameters are null
   */
  public ViewImpl(Project p, Appendable destination) throws IllegalArgumentException {
    if ((p == null) || (destination == null)) {
      throw new IllegalArgumentException("given project and/or destination cannot be null");
    }
    this.p = p;
    this.destination = destination;
  }


  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }



  @Override
  public void printCommands() {
    try {
      renderMessage("Welcome! Please use the following commands: \n");
      renderMessage("\n" +
              "-------------------------------------------------------------------------------\n" +
              "--    new-project                height width                                --\n" +
              "--    add-layer                  layerName                                   --\n" +
              "--   load-project                filePath                                    --\n" +
              "-- add-image-to-layer            layerName imagePath xoffset yoffset         --\n" +
              "--    set-filter                 layerName filterOption                      --\n" +
              "--    save-image                 imagePath                                   --\n" +
              "--    save-project               imagePath                                   --\n" +
              "--       quit                                                                --\n" +
              "-------------------------------------------------------------------------------\n");
      renderMessage("The available filters are:\n");
      renderMessage("-------------------------\n" +
              "--      normal         --\n" +
              "--    red-component    --\n" +
              "--   blue-component    --\n" +
              "--   green-component   --\n" +
              "--   brighten-luma     --\n" +
              "--   bright-intensity  --\n" +
              "--   brighten-value    --\n" +
              "--   darken-luma       --\n" +
              "--   darken-intensity  --\n" +
              "--   darken-value      --\n" +
              "--    difference       --\n" +
              "--     multiply        --\n" +
              "--      screen         --\n" +
              "--       quit          --\n" +
              "-------------------------\n");

    } catch (IOException e) {
      // ignore
    }
  }
}
