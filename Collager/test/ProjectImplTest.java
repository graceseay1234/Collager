import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.util.HashMap;

import model.ImageImpl;
import model.Layer;
import model.ProjectImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test ProjectImpl's functionality.
 */
public class ProjectImplTest {

  private ProjectImpl project;
  private MockProject mock;

  private ImageImpl image;

  private String[][] rgb;

  /**
   * set Rgb vals.
   *
   * @param w width
   * @param h height
   * @param r red
   * @param g green
   * @param b blue
   */
  public void setRGB(int w, int h, int r, int g, int b) {
    this.rgb = new String[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        this.rgb[i][j] = "" + r + "," + g + "," + b;
      }
    }
  }

  @Test
  public void testConstruction() {
    this.project = new ProjectImpl(50, 50);
    this.mock = new MockProject(20, 20);
    assertEquals(20, mock.getHeight());
    assertEquals(20, mock.getWidth());

    // test illegal construction
    try {
      this.project = new ProjectImpl(0, 50);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.project = new ProjectImpl(50, 0);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.project = new ProjectImpl(0, 0);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.project = new ProjectImpl(-1, 50);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.project = new ProjectImpl(50, -1);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.project = new ProjectImpl(-1, -1);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }

  }


  @Test
  public void testAddLayer() {
    this.project = new ProjectImpl(2, 2);
    this.project.addLayer("name");
    this.mock = new MockProject(2, 2);
    this.mock.addLayer("name");
    assertEquals("Layer0", this.mock.getLayerNames().get(0));
    assertEquals("name", this.mock.getLayerNames().get(1));
    assertEquals("normal", this.mock.getLayers().get("name").getFilterName());
    assertEquals("\n255 255 255 255.0\n" +
            "255 255 255 255.0\n" +
            "255 255 255 255.0\n" +
            "255 255 255 255.0", this.mock.getLayers().get("name").getLayerDataText());
    this.mock.addLayer("name2");
    assertEquals("name2", this.mock.getLayerNames().get(2));
    assertEquals("normal", this.mock.getLayers().get("name2").getFilterName());


  }

  @Test
  public void testAddImageToLayer() {
    this.project = new ProjectImpl(2, 2);
    this.project.addLayer("name");
    this.project.addImageToLayer("name", "test/CAT2.ppm", 0, 0);
    this.mock = new MockProject(2, 2);
    this.mock.addLayer("name");
    this.mock.addImageToLayer("name", "test/CAT2.ppm", 0, 0);
    assertEquals(this.mock.saveImageGet("pictures/name.ppm"), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "8.0\n" +
            "75.0\n" +
            "145.0\n" +
            "8.0\n" +
            "75.0\n" +
            "145.0\n" +
            "8.0\n" +
            "75.0\n" +
            "145.0\n" +
            "8.0\n" +
            "75.0\n" +
            "145.0\n");
    // TEST ADD JPEG AND PNG TO LAYER
    this.mock.addImageToLayer("name", "test/tinyRed.jpeg", 0, 0);
    assertEquals(this.mock.saveImageGet("pictures/name.jpeg"), "254 0 0 JPEG");

    this.mock.addImageToLayer("name", "test/tinyBlue.png", 0, 0);
    assertEquals(this.mock.saveImageGet("pictures/name.png"), "0 0 255 PNG");


    try {
      // file not found
      this.mock.addImageToLayer("name", "fileNotFound", 0, 0);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }

    try {
      // file not found
      this.mock.addImageToLayer("notAlAYER", "test/CAT2.ppm", 0, 0);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }

  }


  @Test
  public void testAddImageToLayerRGB() {
    this.setRGB(2, 2, 255, 200, 100);
    this.image = new ImageImpl(this.rgb, 2, 2);
    this.project = new ProjectImpl(2, 2);
    this.project.addImageToLayerRGB("Layer0", this.rgb);
    this.mock = new MockProject(2, 2);
    this.mock.addImageToLayerRGB("Layer0", this.rgb);
    assertEquals(this.mock.saveImageGet("pictures/name.ppm"), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255\n" +
            "200\n" +
            "100\n" +
            "255\n" +
            "200\n" +
            "100\n" +
            "255\n" +
            "200\n" +
            "100\n" +
            "255\n" +
            "200\n" +
            "100\n");
    try {
      this.setRGB(5, 2, 255, 200, 100);
      this.project.addImageToLayerRGB("Layer0", rgb);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }
    try {
      this.setRGB(2, 2, 255, 200, 100);
      this.project.addImageToLayerRGB("NotALayer", rgb);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }


  }


  @Test
  public void testSetFilter() {
    this.project = new ProjectImpl(10, 10);
    this.project.setFilter("Layer0", "red-component");
    this.mock = new MockProject(10, 10);
    this.mock.setFilter("Layer0", "red-component");
    assertEquals("red-component", this.mock.getLayers().get("Layer0").getFilterName());

    try {
      this.mock.setFilter("NOtALayer", "red-component");
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }

  }


  @Test
  public void testSaveProject() {
    this.project = new ProjectImpl(2, 2);
    this.mock = new MockProject(2, 2);
    assertEquals(this.mock.saveProjectGet("projects/try.txt"),
            "projects/try.txt\n" +
                    "2 2\n" +
                    "225\n" +
                    "Layer0 normal\n" +
                    "255 255 255 255.0\n" +
                    "255 255 255 255.0\n" +
                    "255 255 255 255.0\n" +
                    "255 255 255 255.0\n" +
                    "...\n");

  }

  @Test
  public void testLoad() {
    this.project = new ProjectImpl();
    this.mock = new MockProject();
    this.mock.load(new StringBuilder("res/projects/small.txt\n" +
            "5 5\n" +
            "225\n" +
            "Layer0 normal\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "255 255 255 255\n" +
            "...\n" +
            "one brighten-value\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "255.0 255.0 255.0 255\n" +
            "...\n" +
            "two red-component\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "255.0 0 0 255\n" +
            "...\n"), "projects/small.txt");

    assertEquals("Layer0", this.mock.getLayerNames().get(0));
    assertEquals("one", this.mock.getLayerNames().get(1));
    assertEquals("two", this.mock.getLayerNames().get(2));
    assertEquals(5, this.mock.getHeight());
    assertEquals(5, this.mock.getWidth());
    assertEquals(this.mock.saveImageGet("pictures/name.ppm"), "P3\n" +
            "5\n" +
            "5\n" +
            "255\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "0.0\n" +
            "0.0\n");


  }


  @Test
  public void testSaveImage() {
    this.project = new ProjectImpl(2, 2);
    this.mock = new MockProject(2, 2);
    assertEquals(this.mock.saveImageGet("pictures/name.ppm"), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n" +
            "255\n");
    // TEST SAVE JPEG AND PNG IMAGES
    assertEquals(this.mock.saveImageGet("pictures/name.jpeg"), "255 255 255 JPEG");
    assertEquals(this.mock.saveImageGet("pictures/name.png"), "255 255 255 PNG");

  }

  // TEST setHeightAndWidth(int h, int w) throws IllegalArgumentException
  @Test
  public void testSetHeightAndWidth() {
    this.project = new ProjectImpl();
    this.project.setHeightAndWidth(2, 2);
    assertEquals(2, this.project.getHeight());
    assertEquals(2, this.project.getWidth());

    this.project = new ProjectImpl(2, 3);
    this.project.addLayer("l1");
    this.project.addLayer("l2");
    assertEquals(3, this.project.getLayerNames().size());
    assertEquals(2, this.project.getHeight());
    assertEquals(3, this.project.getWidth());

    this.project.setHeightAndWidth(4, 1);

    assertEquals(1, this.project.getLayerNames().size());
    assertEquals(4, this.project.getHeight());
    assertEquals(1, this.project.getWidth());

    try {
      this.project.setHeightAndWidth(0, 1);
      fail();
    } catch (IllegalArgumentException e) {
      // blank
    }
    try {
      this.project.setHeightAndWidth(-1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      // blank
    }
    try {
      this.project.setHeightAndWidth(1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      // blank
    }
    try {
      this.project.setHeightAndWidth(1, -1);
      fail();
    } catch (IllegalArgumentException e) {
      // blank
    }
    try {
      this.project.setHeightAndWidth(0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      // blank
    }
  }

  // TEST getHeight() getWidth()
  @Test
  public void testGetHeightAndWidth() {
    this.project = new ProjectImpl(2, 3);
    assertEquals(2, this.project.getHeight());
    assertEquals(3, this.project.getWidth());
  }

  // TEST List<String> getLayerNames()
  @Test
  public void testGetLayerNames() {
    this.project = new ProjectImpl(2, 3);
    this.project.addLayer("l1");
    this.project.addLayer("l2");
    assertEquals(3, this.project.getLayerNames().size());
    assertEquals("Layer0", this.project.getLayerNames().get(0));
    assertEquals("l1", this.project.getLayerNames().get(1));
    assertEquals("l2", this.project.getLayerNames().get(2));
  }

  // String getCurrentLayerName()
  // also test the current layer name throughout the other methods (add layer, etc)
  @Test
  public void testGetCurrentLayerName() {
    this.project = new ProjectImpl(2, 2);
    assertEquals("Layer0", this.project.getCurrentLayerName());
    this.project.addLayer("l1");
    assertEquals("l1", this.project.getCurrentLayerName());
    this.project.addLayer("l2");
    assertEquals("l2", this.project.getCurrentLayerName());

    this.project.switchCurrentLayer("l1");
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.setFilter("l2", "red-component");
    assertEquals("l2", this.project.getCurrentLayerName());

    String[][] rgb = new ImageImpl(2, 2).getRgbs();
    this.project.addImageToLayerRGB("l1", rgb);
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.addImageToLayer("l2",
            "C:\\Users\\25moo\\OneDrive\\Desktop\\" +
                    "OOD\\Group\\Collager\\res\\pictures\\bluecat.ppm",
            0, 0);
    assertEquals("l2", this.project.getCurrentLayerName());

    this.project.setCompositeFilter("l1", "difference");
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.getCompositeImage();
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.getLayerNames();
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.setHeightAndWidth(1, 1);
    assertEquals("Layer0", this.project.getCurrentLayerName());

    // save-image
    // save-project
    // load
    // should not change it
  }

  // TEST void switchCurrentLayer(String layerName) throws IllegalArgumentException
  @Test
  public void testSwitchCurrentLayer() {
    this.project = new ProjectImpl(2, 2);
    assertEquals("Layer0", this.project.getCurrentLayerName());
    this.project.addLayer("l1");
    assertEquals("l1", this.project.getCurrentLayerName());

    this.project.switchCurrentLayer("Layer0");
    assertEquals("Layer0", this.project.getCurrentLayerName());

    try {
      this.project.switchCurrentLayer("NotALayer");
      fail();
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }
  }

  // TEST Image getCompositeImage()
  @Test
  public void testGetCompositeImage() {
    this.project = new ProjectImpl(2, 2);
    this.project.addLayer("l1");
    this.project.setFilter("l1", "red-component");

    String[][] rgbs = this.project.getCompositeImage().getRgbs();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255.0,0,0", rgbs[i][j]);
      }
    }

    this.project.setHeightAndWidth(3, 3);
    this.project.addLayer("l1");
    this.project.setFilter("Layer0", "red-component");

    rgbs = this.project.getCompositeImage().getRgbs();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals("255,255,255", rgbs[i][j]);
      }
    }

    this.project.setHeightAndWidth(2, 2);
    this.project.addLayer("l1");
    this.project.addLayer("l2");
    this.project.setFilter("Layer0", "red-component");
    this.project.setFilter("l2", "blue-component");

    rgbs = this.project.getCompositeImage().getRgbs();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("0,0,255.0", rgbs[i][j]);
      }
    }


  }

  // TEST setCompositeFilter(String layerName, String filterOption)
  @Test
  public void testSetCompositeFilter() {
    this.project = new ProjectImpl(2, 2);
    this.project.addLayer("l1");
    this.project.setCompositeFilter("l1", "difference");
    String[][] rgbs = this.project.getCompositeImage().getRgbs();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("0.0,0.0,0.0", rgbs[i][j]);
      }
    }

    this.project.setHeightAndWidth(2, 2);
    this.project.setFilter("Layer0", "red-component");
    this.project.addLayer("l1");
    this.project.setCompositeFilter("l1", "difference");
    rgbs = this.project.getCompositeImage().getRgbs();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("0.0,255.0,255.0", rgbs[i][j]);
      }
    }

    this.project.setHeightAndWidth(2, 2);
    this.project.setFilter("Layer0", "red-component");
    this.project.addLayer("l1");
    this.project.setCompositeFilter("l1", "multiply");
    rgbs = this.project.getCompositeImage().getRgbs();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("127.5,127.5,127.5", rgbs[i][j]);
      }
    }

    this.project.setHeightAndWidth(2, 2);
    this.project.setFilter("Layer0", "blue-component");
    this.project.addLayer("l1");
    this.project.setCompositeFilter("l1", "screen");
    rgbs = this.project.getCompositeImage().getRgbs();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255.0,255.0,255.0", rgbs[i][j]);
      }
    }


    try {
      this.project.setCompositeFilter("notALayer", "screen");
      fail();
    } catch (IllegalArgumentException e) {
      // blank intentionally
    }
  }

  /**
   * mock project.
   */
  public class MockProject extends ProjectImpl {
    /**
     * mock project constructor.
     */
    public MockProject() {
      super();
    }

    /**
     * mock project constructor.
     * @param h height
     * @param w width
     */
    public MockProject(int h, int w) {
      super(h, w);
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
     * @param fileName  the filepath to save the image to
     * @return teh project image data PPM format.
     */
    public String saveImageGet(String fileName) {
      // concat all layers
      // then print their data and save
      for (int i = 0; i < this.layers.size(); i++) {
        this.layers.get("Layer0").concatLayer(this.layers.get(this.getLayerNames().get(i)));
      }

      BufferedWriter writer = null;


      if (fileName.contains(".ppm")) {
        String data = this.layers.get("Layer0").getLayerDataPPM();

        return data;
      }
      if (fileName.contains(".jpeg")) {
        BufferedImage buffered = this.layers.get("Layer0").getBufferedImageOfLayer();
        String color = new Color(buffered.getRGB(0, 0)).getRed() + " "
                + new Color(buffered.getRGB(0, 0)).getGreen() + " "
                + new Color(buffered.getRGB(0, 0)).getBlue() + " JPEG";
        return color;
      }
      if (fileName.contains(".png")) {
        BufferedImage buffered = this.layers.get("Layer0").getBufferedImageOfLayer();
        String color = new Color(buffered.getRGB(0, 0)).getRed() + " "
                + new Color(buffered.getRGB(0, 0)).getGreen() + " "
                + new Color(buffered.getRGB(0, 0)).getBlue() + " PNG";
        return color;

      }
      return "";
    }


    /**
     * save project but return data string instead.
     * @param fileName the file path to save the project to
     * @return data string of project in text format
     */
    public String saveProjectGet(String fileName) {
      String data = fileName + "\n" + this.getWidth() + " " + this.getHeight() + "\n225\n";
      for (int i = 0; i < this.layers.size(); i++) {
        data = data + this.getLayerNames().get(i) + " ";
        data = data + this.layers.get(this.getLayerNames().get(i)).getFilterName();
        data = data + this.layers.get(this.getLayerNames().get(i)).getLayerDataText();
        data = data + "\n...\n";
      }
      return data;
    }


  }


}
