import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import model.LayerImpl;
import model.Layer;
import model.Image;
import model.ImageImpl;
import model.Project;
import model.ProjectImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Test LayerImpl's functionality.
 */
public class LayerImplTest {
  private LayerImpl layer;

  @Test
  public void testConstruction() {
    this.layer = new LayerImpl(500, 500);
    assertEquals("normal", this.layer.getFilterName());
    assertEquals(500, this.layer.getImage().getHeight());
    assertEquals(500, this.layer.getImage().getWidth());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerDefaultConstructorHeight0() {
    Layer layer = new LayerImpl(0, 500);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerDefualConstructorWidth0() throws IllegalArgumentException {
    Layer layer = new LayerImpl(500, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerDefaultConstructorHeightAndWidth0() throws IllegalArgumentException {
    Layer layer = new LayerImpl(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLayerDefaultConstructorExecption() throws IllegalArgumentException {
    Layer layer = new LayerImpl(-1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLayerDefaultConstructorExecption2() throws IllegalArgumentException {
    Layer layer = new LayerImpl(10, -1);
  }


  @Test
  public void testLayerConstructor2() {
    Image image = new ImageImpl("test/CAT2.ppm");
    this.layer = new LayerImpl(112, 200, "darken-luma", image);
    assertEquals("darken-luma", this.layer.getFilterName());
    assertEquals(112, this.layer.getImage().getHeight());
    assertEquals(200, this.layer.getImage().getWidth());

    image = new ImageImpl(50, 50);
    layer = new LayerImpl(50, 50, "darken-luma", image);
    assertEquals("darken-luma", this.layer.getFilterName());
    assertEquals(50, this.layer.getImage().getHeight());
    assertEquals(50, this.layer.getImage().getWidth());

    try {
      image = new ImageImpl(10, 50);
      layer = new LayerImpl(50, 50, "darken-luma", image);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // purposefully blank
    }

    try {
      image = new ImageImpl(50, 10);
      layer = new LayerImpl(50, 50, "darken-luma", image);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // purposefully blank
    }
    try {
      image = new ImageImpl(10, 50);
      layer = new LayerImpl(50, 50, "darken-luma", image);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // purposefully blank
    }
    try {
      image = new ImageImpl(10, 10);
      layer = new LayerImpl(50, 50, "darken-luma", image);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // purposefully blank
    }

  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2Height0() throws IllegalArgumentException {
    Image image = new ImageImpl("CAT.ppm");
    Layer layer = new LayerImpl(0, 500, "darken-luma", image);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2Width0() throws IllegalArgumentException {
    Image image = new ImageImpl("CAT.ppm");
    Layer layer = new LayerImpl(500, 0, "darken-luma", image);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2NullFilter() throws IllegalArgumentException {
    Image image = new ImageImpl("CAT.ppm");
    Layer layer = new LayerImpl(500, 500, null, image);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructor2NullImage() throws IllegalArgumentException {
    Image image = new ImageImpl("CAT.ppm");
    Layer layer = new LayerImpl(500, 500, "darken-luma", null);
  }


  @Test
  public void testLayerConstructor2Filter() {
    Image image = new ImageImpl("test/CAT2.ppm");
    Layer layer = new LayerImpl(112, 200, "darken-luma", image);
    assertEquals(layer.getFilterName(), "darken-luma");
  }


  @Test
  public void testLayerConstructorInitialFilter() {
    Layer layer = new LayerImpl(50, 50);
    assertEquals(layer.getFilterName(), "normal");
  }


  //LAYER METHODS


  @Test
  public void testAddImage() {
    Layer layer = new LayerImpl(2, 1);
    Image image = new ImageImpl(1, 2);
    layer.addImage(image, 0, 0);
    //image.adjustImage("normal",true,true);
    assertEquals(layer.getLayerDataPPM(), image.rgbToString());

    layer = new LayerImpl(112, 200);
    image = new ImageImpl("test/CAT2.ppm");
    layer.addImage(image, 10, 10);
    Image img = new ImageImpl(200, 112);
    img.addImage(image, 10, 10);
    //img.adjustImage("normal",true,true);
    assertEquals(layer.getLayerDataPPM(), img.rgbToString());

    // make sure if there is already a filter it still works
    layer = new LayerImpl(2, 2);
    layer.addFilter("red-component");
    image = new ImageImpl(2, 2);
    image.adjustImage("luma", false, false);
    layer.addImage(image, 0, 0);
    assertEquals(layer.getLayerDataText(),
            "\n2.8421709430404007E-14 0 0 255.0\n" +
                    "2.8421709430404007E-14 0 0 255.0\n" +
                    "2.8421709430404007E-14 0 0 255.0\n" +
                    "2.8421709430404007E-14 0 0 255.0");


  }


  @Test
  public void testGetImage() {
    Image image = new ImageImpl("test/CAT2.ppm");
    Layer layer = new LayerImpl(112, 200, "darken-luma", image);
    image.adjustImage("normal", true, true);
    assertEquals(layer.getLayerDataPPM(), image.rgbToString());
  }


  @Test
  public void testConcatLayer() {
    Image image = new ImageImpl(200, 200);
    image.addImage(new ImageImpl("test/CAT2.ppm"), 0, 0);
    Image image1 = new ImageImpl(200, 200);
    image.addImage(new ImageImpl(
                    "C:\\Users\\25moo\\OneDrive\\Desktop" +
                            "\\OOD\\Group\\Collager\\res\\pictures\\redcat.ppm"),
            100, 100);

    Layer layer = new LayerImpl(200, 200, "darken-luma", image);
    Layer layer1 = new LayerImpl(200, 200, "normal", image1);

    layer.concatLayer(layer1);

    Image concat = layer.getImage();

    assertFalse(image.getRgbs() == image1.getRgbs());
    //the initial white image has changed
    //assertFalse(image.getRgbs() == concat.getRgbs());
    assertFalse(image1.getRgbs() == concat.getRgbs());


  }


  @Test
  public void testAddFilter() {
    Project p = new ProjectImpl();


    Image image = new ImageImpl(200, 200);


    Layer layer = new LayerImpl(200, 200, "darken-luma", image);
    //p.addLayer(layer);
    layer.addImage(image, 0, 0);
    //should return "normal"
    assertEquals(layer.getFilterName(), "darken-luma");


    layer.addFilter("darken-luma");
    //should return darken-luma
    assertEquals(layer.getFilterName(), "darken-luma");


  }


  @Test
  public void testAddFilterDEFAULT() throws IllegalArgumentException {
    Image image = new ImageImpl(10, 10);

    Layer layer = new LayerImpl(10, 10, "darken-luma", image);
    assertEquals("darken-luma", layer.getFilterName());

    layer.addFilter("red-component");
    assertEquals("red-component", layer.getFilterName());

    layer.addFilter("brighten-luma");
    assertEquals("brighten-luma", layer.getFilterName());

    try {
      layer.addFilter("unacceptable");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // intentionally blank
    }

    try {
      layer.addFilter("");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // intentionally blank
    }


  }


  @Test
  public void testGetLayerDataPPM() {
    // and testing set filter
    Image image = new ImageImpl(2, 2);

    Layer layer = new LayerImpl(2, 2, "normal", image);
    //GET VALUES
    assertEquals(layer.getLayerDataPPM(), "P3\n" +
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
    layer.addFilter("red-component");
    assertEquals(layer.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n");
    layer.addFilter("blue-component");
    assertEquals("P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n", layer.getLayerDataPPM());

    layer.addFilter("green-component");
    assertEquals(layer.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n" +
            "0\n" +
            "255.0\n" +
            "0\n");
    layer.addFilter("brighten-value");
    assertEquals(layer.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n");
    layer.addFilter("darken-luma");
    assertEquals(layer.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n" +
            "2.8421709430404007E-14\n");

  }


  @Test
  public void testGetLayerDataText() {
    Image image = new ImageImpl(2, 2);

    Layer layer = new LayerImpl(2, 2, "normal", image);
    assertEquals(layer.getLayerDataText(), "\n" +
            "255 255 255 255.0\n" +
            "255 255 255 255.0\n" +
            "255 255 255 255.0\n" +
            "255 255 255 255.0");

    layer = new LayerImpl(2, 2, "red-component", image);
    assertEquals(layer.getLayerDataText(),
            "\n255.0 0 0 255.0\n" +
                    "255.0 0 0 255.0\n" +
                    "255.0 0 0 255.0\n" +
                    "255.0 0 0 255.0");
  }


  @Test
  public void testGetFilterName() {
    Layer layer = new LayerImpl(50, 50, "darken-luma", new ImageImpl(50, 50));
    assertEquals(layer.getFilterName(), "darken-luma");


    layer.addFilter("darken-luma");
    //should return darken-luma
    assertEquals(layer.getFilterName(), "darken-luma");


  }

  @Test
  public void testSetFilterName() {
    Layer layer = new LayerImpl(50, 50, "darken-luma", new ImageImpl(50, 50));
    assertEquals(layer.getFilterName(), "darken-luma");


    layer.addFilter("darken-luma");
    //should return darken-luma
    assertEquals(layer.getFilterName(), "darken-luma");


  }


  @Test
  public void testAddCompositeFilterUnsupportedOption() {
    LayerImpl layer1 = new LayerImpl(10, 10);
    LayerImpl layer2 = new LayerImpl(10, 10);
    try {
      layer1.addCompositeFilter(null, layer2);
      fail("expected to fail");
    } catch (NullPointerException ignore) {
      // blank intentionally
    }
  }


  @Test
  public void testAddCompositeFilter() {
    // testing set composite filters
    LayerImpl layer1 = new LayerImpl(2, 2, "normal", new ImageImpl(2, 2));
    LayerImpl layer2 = new LayerImpl(2, 2, "blue-component", new ImageImpl(2, 2));
    //GET VALUES
    assertEquals(layer1.getLayerDataPPM(), "P3\n" +
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
    layer1.addCompositeFilter("multiply", layer2);
    assertEquals(layer1.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n" +
            "127.5\n");
    layer1.addCompositeFilter("difference", layer2);
    assertEquals("P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255.0\n" +
            "255.0\n" +
            "0.0\n" +
            "255.0\n" +
            "255.0\n" +
            "0.0\n" +
            "255.0\n" +
            "255.0\n" +
            "0.0\n" +
            "255.0\n" +
            "255.0\n" +
            "0.0\n", layer1.getLayerDataPPM());


    layer1.addCompositeFilter("screen", layer2);
    assertEquals(layer1.getLayerDataPPM(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n");


  }


  @Test
  public void testAddCompositeNullLayerInputs() {
    // testing set composite filters
    LayerImpl layer1 = new LayerImpl(2, 2, "normal", new ImageImpl(2, 2));
    LayerImpl layer2 = new LayerImpl(2, 2, "blue-component", new ImageImpl(2, 2));
    //GET VALUES
    assertEquals(layer1.getLayerDataPPM(), "P3\n" +
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
    try {
      layer1.addCompositeFilter("multiply", null);
      fail("expected to fail");
    } catch (NullPointerException ignore) {
      // blank
    }
    try {
      layer1.addCompositeFilter("lol", layer2);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank
    }
  }


  @Test
  public void testAddCompositeEmptyLayerInputs() {
    // testing set composite filters
    LayerImpl layer1 = new LayerImpl(2, 2, "normal", new ImageImpl(2, 2));
    LayerImpl layer2 = new LayerImpl(2, 2, "blue-component", new ImageImpl(2, 2));
    //GET VALUES
    assertEquals(layer1.getLayerDataPPM(), "P3\n" +
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
    try {
      layer1.addCompositeFilter("lol", layer2);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank
    }
  }


  @Test
  public void testBufferedImageGet() {
    ImageImpl image = new ImageImpl(2, 2);
    this.layer = new LayerImpl(2, 2, "red-component", image);
    Color c;

    BufferedImage buffered = this.layer.getBufferedImageOfLayer();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        c = new Color(buffered.getRGB(i, j));
        assertEquals(255, c.getRed());
        assertEquals(0, c.getBlue());
        assertEquals(0, c.getGreen());
        assertEquals(255, c.getAlpha());
      }
    }

    this.layer.addFilter("normal");
    buffered = this.layer.getBufferedImageOfLayer();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        c = new Color(buffered.getRGB(i, j));
        assertEquals(255, c.getRed());
        assertEquals(255, c.getBlue());
        assertEquals(255, c.getGreen());
        assertEquals(255, c.getAlpha());
      }
    }
  }


}






