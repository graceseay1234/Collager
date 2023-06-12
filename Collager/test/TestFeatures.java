import controller.Controller;
import controller.ControllerImpl;
import controller.Features;
import controller.FeaturesImpl;
import model.Project;
import model.ProjectImpl;
import view.GUIView;
import view.SwingGUIView;
import view.View;
import view.ViewImpl;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test FeatureImpls functionality.
 */
public class TestFeatures {
  private ControllerImpl controller;
  private ControllerImplTest.MockController mockController;
  private Features features;
  private ProjectImpl model;
  private GUIView guiView;
  private View view;

  private Readable in;

  private MockFeatures mockFeatures;






  @Test
  public void testConstruction() {
    this.model = new ProjectImpl();
    this.view = new ViewImpl(this.model);
    this.in = new StringReader("");
    this.controller = new ControllerImpl(this.in, model,view);
    this.guiView = new SwingGUIView(this.model);
    this.features = new FeaturesImpl(this.model, this.guiView, this.controller);

    try {
      this.features = new FeaturesImpl(null, null, null);
      fail("expected");
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }
    try {
      this.features = new FeaturesImpl(null, this.guiView, this.controller);
      fail("expected");
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }
    try {
      this.features = new FeaturesImpl(this.model, this.guiView, null);
      fail("expected");
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }
    try {
      this.features = new FeaturesImpl(this.model, null, this.controller);
      fail("expected");
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }

  }

  @Test
  public void testSetView() {
    this.model = new ProjectImpl();
    this.view = new ViewImpl(this.model);
    this.in = new StringReader("");
    ControllerImplTest.MockProject mockProject = new ControllerImplTest.MockProject();
    this.mockController = new ControllerImplTest.MockController(this.in, mockProject, this.view);
    this.guiView = new MockView();
    MockView mockView = new MockView();
    this.mockFeatures = new MockFeatures(this.model, this.guiView, this.mockController);

    assertFalse(mockView.featuresAdded);
    this.mockFeatures.setView(mockView);
    assertTrue(mockView.featuresAdded);
  }



  @Test
  public void testSwitchLayer() {
    this.model = new ProjectImpl(1,1);
    this.view = new ViewImpl(this.model);
    this.in = new StringReader("");
    ControllerImplTest.MockProject mockProject = new ControllerImplTest.MockProject();
    this.mockController = new ControllerImplTest.MockController(this.in, mockProject, this.view);
    this.guiView = new SwingGUIView(this.model);
    //this.features = new FeaturesImpl(this.model, this.guiView, this.mockController);
    this.mockFeatures = new MockFeatures(this.model, this.guiView, this.mockController);

    this.model.addLayer("layer1");
    this.model.addLayer("layer2");

    assertEquals("layer2", this.model.getCurrentLayerName());
    this.mockFeatures.switchLayerNoGUI("layer1");
    assertEquals("layer1", this.model.getCurrentLayerName());
  }



  @Test
  public void testChangeFormat() {
    this.model = new ProjectImpl();
    this.view = new ViewImpl(this.model);
    this.in = new StringReader("");
    ControllerImplTest.MockProject mockProject = new ControllerImplTest.MockProject();
    this.mockController = new ControllerImplTest.MockController(this.in, mockProject, this.view);
    this.guiView = new SwingGUIView(this.model);
    //this.features = new FeaturesImpl(this.model, this.guiView, this.mockController);
    this.mockFeatures = new MockFeatures(this.model, this.guiView, this.mockController);

    assertEquals("new-project 1 1", this.mockFeatures.changeFormatGET("1 1", "new-project "));
    assertEquals("set-filter Layer0 red-component",
            this.mockFeatures.changeFormatGET("red-component", "set-filter "));
    assertEquals("load-project path/p",
            this.mockFeatures.changeFormatGET("path/p", "load-project "));
    assertEquals("save-project path/p",
            this.mockFeatures.changeFormatGET("path/p", "save-project "));
    assertEquals("add-layer name", this.mockFeatures.changeFormatGET("name", "add-layer "));
    assertEquals("add-image-to-layer Layer0 img 0 0",
            this.mockFeatures.changeFormatGET("img 0 0", "add-image-to-layer " ));
    assertEquals("save-image path/p", this.mockFeatures.changeFormatGET("path/p", "save-image "));
    assertEquals("quit", this.mockFeatures.changeFormatGET("", "quit"));
  }





  @Test
  public void testcallControllerOnInput() {
    this.model = new ProjectImpl();
    this.view = new ViewImpl(this.model);
    this.in = new StringReader("");
    ControllerImplTest.MockProject mockProject = new ControllerImplTest.MockProject();
    this.mockController = new ControllerImplTest.MockController(this.in, mockProject, this.view);
    this.guiView = new SwingGUIView(this.model);

    //this.features = new FeaturesImpl(this.model, this.guiView, this.mockController);

    this.mockFeatures = new MockFeatures(this.model, this.guiView, this.mockController);
    assertEquals("R11", this.mockFeatures.callControllerOnInputTestGET("new-project 1 1"));
    assertEquals("Rnamered-component",
            this.mockFeatures.callControllerOnInputTestGET("set-filter name red-component"));
    assertEquals("Rpath/p", this.mockFeatures.callControllerOnInputTestGET("load-project path/p"));
    assertEquals("path/p", this.mockFeatures.callControllerOnInputTestGET("save-project path/p"));
    assertEquals("Rname", this.mockFeatures.callControllerOnInputTestGET("add-layer name"));
    assertEquals("Rnameimg00",
            this.mockFeatures.callControllerOnInputTestGET("add-image-to-layer name img 0 0"));
    assertEquals("path/p", this.mockFeatures.callControllerOnInputTestGET("save-image path/p"));
    assertEquals("quit", this.mockFeatures.callControllerOnInputTestGET("quit"));


  }

  /**
   * mock features implementation.
   */
  public class MockFeatures extends FeaturesImpl {
    private ControllerImplTest.MockController mock;


    /**
     * features controller constructor.
     *
     * @param p    Project
     * @param view GUIView
     * @param c    Controller
     */
    public MockFeatures(Project p, GUIView view, Controller c) {
      super(p, view, c);
    }

    /**
     * cinstrutcor w mock controller.
     *
     * @param p    project
     * @param view view
     * @param c    mock controller
     */
    public MockFeatures(Project p, GUIView view, ControllerImplTest.MockController c) {
      super(p, view, c);
      this.mock = c;
    }

    /**
     * call controller on input mock for testing.
     *
     * @param typed text to send to controller
     * @return what the controller returns
     */
    public String callControllerOnInputTestGET(String typed) {
      String r = "";
      //refresh view
      if (typed.contains("set-filter")
              || typed.contains("add-image-to-layer")
              || typed.contains("new-project")
              || typed.contains("load-project")
              || typed.contains("add-layer")) {
        r = r + "R";
      }

      r = r + mock.handleUserInputGET(typed);

      return r;

    }

    /**
     * version of change format for testing.
     * @param typed the string user typed
     * @param button the button the user pressed
     * @return the action to take in command-prompt form
     */
    public String changeFormatGET(String typed, String button) {
      if ( (button.equals("set-filter ")) || (button.equals("add-image-to-layer "))) {
        typed = button + this.project.getCurrentLayerName() + " " + typed;
      }
      else {
        typed = button + typed;
      }
      return typed;
    }

    /**
     * version of switch layer for testing.
     * @param typed the string the user typed(layer name to switch)
     */
    public void switchLayerNoGUI(String typed) {
      try {
        this.project.switchCurrentLayer(typed);
      } catch (IllegalArgumentException e) {
        this.view.renderMessage(e.getMessage());
      } catch (ArrayIndexOutOfBoundsException e) {
        this.view.renderMessage("Missing input from the text field " +
                "(add-image coordinates or new-project height and width for example)");
      }
      view.clearInputString();
      view.resetFocus();
    }


    /**
     * get this GUI view.
     * @return this view
     */
    public GUIView getView() {
      return this.view;
    }
  }




























  /**
   * mock GUI View.
   */
  public class MockView implements GUIView {
    protected boolean featuresAdded;

    /**
     * create a mock GUI View.
     */
    public MockView() {
      // blank
      this.featuresAdded = false;
    }

    @Override
    public void refresh() {
      // blank
    }

    @Override
    public void renderMessage(String message) {
      // blank
    }

    @Override
    public String getInputString() {
      return null;
    }

    @Override
    public void clearInputString() {
      // blank
    }

    @Override
    public void resetFocus() {
      // blank
    }

    @Override
    public void addFeatures(Features features) {
      this.featuresAdded = true;
      // blank
    }
  }






  // make sure to test everything
  // might need a test for IOException handling thing


}
