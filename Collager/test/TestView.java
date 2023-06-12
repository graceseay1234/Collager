import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;



import static org.junit.Assert.assertEquals;

import model.Project;
import model.ProjectImpl;
import view.BoardPanel;
import view.GUIView;
import view.SwingGUIView;
import view.View;
import view.ViewImpl;


/**
 * Test ViewImpls functionality.
 * (also briefly test GUIView Implementation construction)
 */
public class TestView {

  private ViewImpl view;
  private Appendable log;





  @Test
  public void testReactionToIOException() {
    this.log = new AppendableAlwaysThrowsExceptionMock();
    this.view = new ViewImpl(new ProjectImpl(), log);
    try {
      this.view.renderMessage("Throw Exception");
      fail("Expected IOException");
    } catch (IOException ignore) {
      // do nothing bc passed
    }

  }



  /**
   * mock Appendable class to test IOExceptions.
   */
  public class AppendableAlwaysThrowsExceptionMock implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException();
    }
  }




















  Project project1 = new ProjectImpl();
  Appendable appendable = new StringBuilder();
  ViewImpl text;




  //TESTING CONSTRUCTOR commands
  @Test
  public void testViewConstructor() {
    View controller = new ViewImpl(new ProjectImpl());
    controller = new ViewImpl(project1);
    controller = new ViewImpl(project1, appendable);

    try {
      controller = new ViewImpl(project1, null);
      fail();
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }

    try {
      controller = new ViewImpl(null, appendable);
      fail();
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }

  }


  Project project;

  @Test
  public void testRenderMessage() {
    this.project = new ProjectImpl(500, 500);
    Appendable log = new StringBuilder();
    this.text = new ViewImpl(this.project, log);

    try {
      this.text.renderMessage("Hi");
    } catch (IOException ignore) {
      // ignore
    }

    assertEquals("Hi", log.toString());
  }







  @Test
  public void testPrintCommands() {
    this.project = new ProjectImpl(500, 500);
    Appendable log = new StringBuilder();
    this.text = new ViewImpl(this.project, log);
    this.text.printCommands();

    assertEquals("Welcome! Please use the following commands: \n" +
            "\n" +
            "-------------------------------------------------------------------------------\n" +
            "--    new-project                height width                                --\n" +
            "--    add-layer                  layerName                                   --\n" +
            "--   load-project                filePath                                    --\n" +
            "-- add-image-to-layer            layerName imagePath xoffset yoffset         --\n" +
            "--    set-filter                 layerName filterOption                      --\n" +
            "--    save-image                 imagePath                                   --\n" +
            "--    save-project               imagePath                                   --\n" +
            "--       quit                                                                --\n" +
            "-------------------------------------------------------------------------------\n" +
            "The available filters are:\n" +
            "-------------------------\n" +
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
            "-------------------------\n", log.toString());


  }











  // test construction of the gui views
  // cant rly test anything else
  @Test
  public void testGUIConstruction() {
    this.project = new ProjectImpl(10,10);
    GUIView guiView = new SwingGUIView(this.project);

    try {
      guiView = new SwingGUIView(null);
      fail();
    } catch (IllegalArgumentException e) {
      // ignore
    }


    BoardPanel bp;
    try {
      bp = new BoardPanel(this.project);
    } catch (Exception e) {
      // ignore
    }

    try {
      bp = new BoardPanel(null);
      fail();
    } catch (IllegalArgumentException e) {
      // ignore
    }
  }








}
