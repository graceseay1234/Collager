import org.junit.Test;


import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;


import controller.ControllerImpl;
import model.ImageImpl;
import model.Layer;
import model.LayerImpl;
import model.Project;
import model.ProjectImpl;
import view.View;
import view.ViewImpl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Test controllerImpl's functionality.
 */
public class ControllerImplTest {
  private ControllerImpl controller;
  private ProjectImpl model;
  private ViewImpl view;
  private MockController mock;

  private LayerImpl layer;

  private ImageImpl image;

  private String[][] rgb;

  private Readable in;
  private MockProject mockp;




  @Test
  public void testConstruction() {
    this.model = new ProjectImpl();
    this.view = new ViewImpl(model);
    this.in = new StringReader("");
    this.controller = new ControllerImpl(this.in, model,view);
    this.controller = new ControllerImpl(model, view);
    this.mockp = new MockProject(10,10);
    this.mock = new MockController(new InputStreamReader(System.in), mockp,view);
    this.mock.getModel().addLayer("name");
    assertEquals(this.mock.getModel().getLayerNames().get(1),"name");

    try {
      this.controller = new ControllerImpl(null, view);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(model, null);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(null, null);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(null, model, view);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(this.in, null, view);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(this.in, model, null);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      this.controller = new ControllerImpl(null, null,null);
      fail();
    } catch (IllegalArgumentException e) {
      // do nothing
    }

  }



  @Test
  public void testHandleUserInput() {
    // just test that it gets to places because already testsed the funcs
    this.model = new ProjectImpl();
    this.view = new ViewImpl(model);
    this.in = new StringReader("");
    this.controller = new ControllerImpl(this.in, model,view);
    this.mockp = new MockProject();
    this.in = new StringReader("new-project 1 1");
    this.mock = new MockController(in, mockp,view);
    assertEquals("11", this.mock.handleUserInputGET("new-project 1 1"));
    assertEquals("namered-component",
            this.mock.handleUserInputGET("set-filter name red-component"));
    assertEquals("path/p", this.mock.handleUserInputGET("load-project path/p"));
    assertEquals("path/p", this.mock.handleUserInputGET("save-project path/p"));
    assertEquals("name", this.mock.handleUserInputGET("add-layer name"));
    assertEquals("nameimg00", this.mock.handleUserInputGET("add-image-to-layer name img 0 0"));
    assertEquals("path/p", this.mock.handleUserInputGET("save-image path/p"));
    assertEquals("quit", this.mock.handleUserInputGET("quit"));




  }


  /**
   * mock controller.
   */
  static class MockController extends ControllerImpl {
    private MockProject m;
    private Readable readable;
    private Project model;

    private View view;

    /**
     * constructor.
     * @param readable readable
     * @param model model
     * @param view view
     */
    public MockController(Readable readable, MockProject model, View view) {
      this.readable = readable;
      this.model = model;
      this.m = model;
      this.view = view;
    }

    /**
     * get model.
     * @return model
     */
    public MockProject getModel() {
      return this.m;
    }

    /**
     * get view.
     * @return view
     */
    public View getView() {
      return this.view;
    }

    /**
     * checks got to eveyrthing.
     * @param input the user-input user input
     * @return the string it got toz
     */
    public String handleUserInputGET(String input)  {
      String[] inputParts = input.split(" ");
      String command = inputParts[0];


      switch (command) {
        case ("set-filter"):
          String layerName = inputParts[1];
          String filterOption = inputParts[2];
          return "" + layerName + filterOption;
        case ("new-project"):
          int height = Integer.parseInt(inputParts[1]);
          int width = Integer.parseInt(inputParts[2]);
          return "" + height + width;
        case ("load-project"):
          String pathToProjectFile = inputParts[1];
          return pathToProjectFile;
        case ("save-project"):
          String filePathName = inputParts[1];
          return filePathName;
        case ("add-layer"):
          String aADlayerName = inputParts[1];
          return aADlayerName;
        case ("add-image-to-layer"):
          String aAITLLayerName = inputParts[1];
          String aATILImageName = inputParts[2];
          int aATILXPos = Integer.parseInt(inputParts[3]);
          int aATILYPos = Integer.parseInt(inputParts[4]);
          return "" + aAITLLayerName + aATILImageName + aATILXPos  + aATILYPos;
        case ("save-image"):
          String fileName = inputParts[1];
          return fileName;
        case ("quit"):
          // main function catches this quit
          //quits
          return "quit";
        default:
          break;
      }
      return "";
    }





  }














  /**
   * mock project.
   */
  public static class MockProject extends ProjectImpl {
    /**
     * create an empty mock project.
     */
    public MockProject() {
      super();
    }

    /**
     * create a mock project.
     * @param h height of project
     * @param w width of project
     */
    public MockProject(int h, int w) {
      super(h,w);
    }

    /**
     * get layers.
     * @return layers.
     */
    public HashMap<String, Layer> getLayers() {
      return this.layers;
    }

    /**
     * save image but return the data instead of saving an image.
     * @return teh project image data PPM format.
     */
    public String saveImageGet() {
      // concat all layers
      // then print their data and save
      for (int i = 0; i < this.layers.size(); i++) {
        System.out.println(this.layers);
        this.layers.get("Layer0").concatLayer(this.layers.get(this.getLayerNames().get(i)));
      }

      String data = this.layers.get("Layer0").getLayerDataPPM();
      return data;
    }

    /**
     * save project but return data string instead.
     * @param fileName the filepath for the file to be saved as
     * @return data string of project in text format
     */
    public String saveProjectGet(String fileName) {
      String data = fileName + "\n" + this.getWidth() + " " + this.getHeight() + "\n225\n";
      for (int i = 0; i < this.layers.size() ; i++) {
        //System.out.println("in loop, layerordersize:"
        // + this.layerOrder.size() + " layers size: " + this.layers.size());
        //System.out.println("\n i =" + i + "\n");
        data = data + this.getLayerNames().get(i) + " ";
        //System.out.println(this.layers);
        data = data + this.layers.get(this.getLayerNames().get(i)).getFilterName();
        data = data + this.layers.get(this.getLayerNames().get(i)).getLayerDataText();
        data = data + "\n...\n";
      }
      return data;
    }


  }








  //make sure there is an intial layer when project is created
  /*@Test
  public void testAddLayer() {
    project = new Project(100, 100);


    assertEquals(1, project.getLayers().size());
    assertNotNull(project.getLayer("layer-0"));


    //add a layer and now there are two layers
    project.addLayer("Layer");
    assertEquals(2, project.getLayers().size());
    assertNotNull(project.getLayer("Layer"));
  }


  @Test
  public void testGetLayer() {
    project = new Project(100, 100);
    assertNotNull(project.getLayer("layer-0"));
    assertNull(project.getLayer("Nonexistent Layer"));
  }


  @Test
  public void testSetDimensions() {
    project = new Project(100, 100);
    project.setDimensions(200, 200);
    assertEquals(200, project.getHeight());
    assertEquals(200, project.getWidth());
  }


  @Test
  public void testConstructor() {
    project = new Project(100, 100);
    assertThrows(IllegalArgumentException.class, () -> {
      Project invalidProject = new Project(0, 100);
    });


    assertThrows(IllegalArgumentException.class, () -> {
      Project invalidProject = new Project(100, 0);
    });
  }


  @Test
  public void testAddImageToLayer() throws IOException {
    project = new Project(3000, 3000);
    project.addImageToLayer("test/Love.ppm", "Layer 0", 0, 0);
    Layer layer = project.getLayer("layer-0");
    assertEquals(2, layer.getImages().size());
    assertNotNull(layer.getImages().get(0));


    project.addLayer("tako-blue");
    assertEquals(2, project.getLayers().size());
    project.addImageToLayer("test/Love.ppm", "tako-blue", 0, 0);
    Layer layerblue = project.getLayer("layer-0");
    assertEquals(2, layer.getImages().size());
  }


  @Test
  public void testAddLayer1() throws IOException {
    Controller controller = new Controller();
    controller.newProject(3000, 3000);


    controller.handleUserInput("add-layer tako-blue");


    // Check if the layer has been added
    Layer layer = controller.getLayer("tako-blue");
    assertNotNull(layer);


    assertEquals(layer.getHeight(), 3000);
    assertEquals(layer.getWidth(), 3000);


    Image image = layer.getImages().get(0);


    assertEquals(image.getHeight(), 3000);
    assertEquals(image.getWidth(), 3000);


  }


  @Test
  public void testSetFilter() throws IOException {
    Controller controller = new Controller();
    controller.newProject(3000, 3000);


    controller.handleUserInput("set-filter layer-0 brighten-luma");
  }


  @Test
  public void testSetFilterOverride() throws IOException {
    Controller controller = new Controller();
    controller.newProject(3000, 3000);


    controller.handleUserInput("set-filter layer-0 brighten-luma");
    controller.handleUserInput("set-filter layer-0 darken-luma");
  }


  @Test
  public void testImageUtil() {
    ImageUtil image = new ImageUtil();
    image.readPPM("test/Love.ppm");


  }*/
}
