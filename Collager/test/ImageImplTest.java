import model.Image;
import model.ImageImpl;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test ImageImpl's functionality.
 */
public class ImageImplTest {
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

  /**
   * set Rgb vals.
   *
   * @param w width
   * @param h height
   * @param r red
   * @param g green
   * @param b blue
   */
  public void setRGBDouble(int w, int h, double r, double g, double b) {
    this.rgb = new String[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        this.rgb[i][j] = "" + r + "," + g + "," + b;
      }
    }
  }

  /**
   * set Rgb vals.
   *
   * @param w width
   * @param h height
   * @param r red
   * @param g green
   * @param b blue
   */
  public void setRGBObj(int w, int h, Object r, Object g, Object b) {
    this.rgb = new String[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        this.rgb[i][j] = "" + r + "," + g + "," + b;
      }
    }
  }


  @Test
  public void testConstruction() {
    this.image = new ImageImpl(500, 500);
    assertEquals(500, this.image.getHeight());
    assertEquals(500, this.image.getWidth());
    this.setRGB(500, 500, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());
    // test invalid construction
    try {
      this.image = new ImageImpl(0, 500);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl(10, 0);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl(-1, 500);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl(10, -1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
  }

  @Test
  public void testPPMConstruction() {
    this.image = new ImageImpl("test/CAT2.ppm");
    assertEquals(112, this.image.getHeight());
    assertEquals(200, this.image.getWidth());
    // test invalid construction
    try {
      this.image = new ImageImpl("");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("     idk");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl(null);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/idkg.txt");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }


  }


  @Test
  public void testRGBConstruction() {
    this.setRGB(2, 3, 255, 200, 100);
    this.image = new ImageImpl(this.rgb, 2, 2);
    assertEquals(this.image.rgbToString(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "255\n" + "200\n" + "100\n" +
            "255\n" + "200\n" + "100\n" +
            "255\n" + "200\n" + "100\n" +
            "255\n" + "200\n" + "100\n");
    try {
      this.setRGB(1, 2, 255, 200, 100);
      this.image = new ImageImpl(this.rgb, 2, 2);
      fail("expected");
    } catch (IllegalArgumentException ignore) {
      // ignore
    }


  }


  @Test
  public void adjustImageTest() {
    // this also tests that makeWithinBounds and AdjustBrightDark works,
    // as they cant be tested since they are private methods
    this.image = new ImageImpl(10, 10);
    this.setRGB(10, 10, 255, 255, 255);

    // test normal bright and dark do nothing
    this.setRGB(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());
    this.image.adjustImage("normal", false, false);
    assertEquals(this.rgb, this.image.getRgbs());


    // test max
    this.image.adjustImage("max", true, false);
    this.setRGBDouble(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());

    this.image.adjustImage("max", false, false);
    this.setRGBDouble(10, 10, 0, 0, 0);
    assertEquals(this.rgb, this.image.getRgbs());


    // test avg
    this.image = new ImageImpl(10, 10);
    this.image.adjustImage("avg", true, false);
    this.setRGBDouble(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());


    this.image.adjustImage("avg", false, false);
    this.setRGBDouble(10, 10, 0, 0, 0);
    assertEquals(this.rgb, this.image.getRgbs());


    // test luma
    this.image = new ImageImpl(10, 10);
    this.image.adjustImage("luma", true, false);
    this.setRGBDouble(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());


    this.image.adjustImage("luma", false, false);
    this.setRGBDouble(10, 10, 2.8421709430404007E-14,
            2.8421709430404007E-14, 2.8421709430404007E-14);
    assertEquals(this.rgb, this.image.getRgbs());


    // test reset thing works
    /// do dark
    this.image = new ImageImpl(10, 10);
    this.image.adjustImage("avg", false, false);
    this.setRGBDouble(10, 10, 0, 0, 0);
    assertEquals(this.rgb, this.image.getRgbs());

    // then bright w reset
    this.image.adjustImage("avg", true, true);
    this.setRGBDouble(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());


  }


  @Test
  public void testChangeColor() {
    this.image = new ImageImpl(10, 10);

    // test red
    this.image.changeColor("red", false);
    this.setRGBObj(10, 10, 255.0, 0, 0);
    assertEquals(this.rgb, this.image.getRgbs());

    // test green
    this.image = new ImageImpl(10, 10);
    this.image.changeColor("green", false);
    this.setRGBObj(10, 10, 0, 255.0, 0);
    assertEquals(this.rgb, this.image.getRgbs());

    // test blue
    this.image = new ImageImpl(10, 10);
    this.image.changeColor("blue", false);
    this.setRGBObj(10, 10, 0, 0, 255.0);
    assertEquals(this.rgb, this.image.getRgbs());

    // test all with reset
    this.image = new ImageImpl(10, 10);
    this.image.changeColor("red", false);
    this.setRGBObj(10, 10, 255.0, 0, 0);
    assertEquals(this.rgb, this.image.getRgbs());

    this.image.changeColor("green", true);
    this.setRGBObj(10, 10, 0, 255.0, 0);
    assertEquals(this.rgb, this.image.getRgbs());

    this.image.changeColor("blue", true);
    this.setRGBObj(10, 10, 0, 0, 255.0);
    assertEquals(this.rgb, this.image.getRgbs());


    try {
      this.image.changeColor("", false);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // intentionally blank
    }

  }



  /*@Test
  public void addImageTest() {
    this.image = new ImageImpl(500,500);
    this.image.addImage(new ImageImpl("test/CAT2.ppm"),0,0);
    assertEquals(500,this.image.getHeight());
    assertEquals(500,this.image.getWidth());
    assertEquals(this.rgb,this.image.getRgbs());


    // check builder reset




  }*/


  @Test
  public void testGets() {
    this.image = new ImageImpl(10, 10);
    // get height
    assertEquals(10, this.image.getHeight());

    // get width
    assertEquals(10, this.image.getWidth());

    // get rgb
    this.setRGB(10, 10, 255, 255, 255);
    assertEquals(this.rgb, this.image.getRgbs());

  }

  @Test
  public void testAddImage() {
    // check changes RGBs
    this.image = new ImageImpl(3, 3);
    ImageImpl i2 = new ImageImpl(2, 2);
    i2.changeColor("red", false);
    assertEquals("255.0,0,0", i2.getRgbs()[0][0]);
    assertEquals("255,255,255", this.image.getRgbs()[0][0]);
    this.image.addImage(i2, 0, 0);
    assertEquals("255.0,0,0", this.image.getRgbs()[0][0]);
    assertEquals("255.0,0,0", this.image.getRgbs()[0][1]);
    assertEquals("255,255,255", this.image.getRgbs()[0][2]);
    assertEquals("255.0,0,0", this.image.getRgbs()[1][0]);
    assertEquals("255.0,0,0", this.image.getRgbs()[1][1]);
    assertEquals("255,255,255", this.image.getRgbs()[1][2]);
    assertEquals("255,255,255", this.image.getRgbs()[2][0]);
    assertEquals("255,255,255", this.image.getRgbs()[2][1]);
    assertEquals("255,255,255", this.image.getRgbs()[2][2]);


    // check changes RGBs w offset correctly
    this.image = new ImageImpl(3, 3);
    i2 = new ImageImpl(2, 2);
    i2.changeColor("red", false);
    assertEquals("255.0,0,0", i2.getRgbs()[0][0]);
    assertEquals("255,255,255", this.image.getRgbs()[0][0]);
    this.image.addImage(i2, 1, 1);
    assertEquals("255,255,255", this.image.getRgbs()[0][0]);
    assertEquals("255,255,255", this.image.getRgbs()[0][1]);
    assertEquals("255,255,255", this.image.getRgbs()[0][2]);
    assertEquals("255,255,255", this.image.getRgbs()[1][0]);
    assertEquals("255.0,0,0", this.image.getRgbs()[1][1]);
    assertEquals("255.0,0,0", this.image.getRgbs()[1][2]);
    assertEquals("255,255,255", this.image.getRgbs()[2][0]);
    assertEquals("255.0,0,0", this.image.getRgbs()[2][1]);
    assertEquals("255.0,0,0", this.image.getRgbs()[2][2]);


    // check builder is set correctly
    // reset and make sure is same
    System.out.println("\nBuilder");
    this.image.adjustImage("normal", true, true);
    assertEquals("255.0,0.0,0.0", this.image.getRgbs()[2][1]);

  }


  @Test
  public void testrgbToString() {
    this.image = new ImageImpl(2, 2);
    String s = "P3\n2\n2\n255\n"
            + "255\n255\n255\n"
            + "255\n255\n255\n"
            + "255\n255\n255\n"
            + "255\n255\n255\n";
    assertEquals(s, this.image.rgbToString());

    this.image = new ImageImpl(2, 2);
    this.image.changeColor("red", false);
    s = "P3\n2\n2\n255\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n";
    assertEquals(s, this.image.rgbToString());

    this.image = new ImageImpl(2, 2);
    ImageImpl i2 = new ImageImpl(2, 2);
    i2.changeColor("red", false);
    this.image.addImage(i2, 0, 0);
    s = "P3\n2\n2\n255\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n"
            + "255.0\n0\n0\n";
    assertEquals(s, this.image.rgbToString());

    this.image = new ImageImpl(2, 2);
    i2 = new ImageImpl(1, 1);
    i2.changeColor("red", false);
    this.image.addImage(i2, 0, 0);
    s = "P3\n2\n2\n255\n"
            + "255.0\n0\n0\n"
            + "255\n255\n255\n"
            + "255\n255\n255\n"
            + "255\n255\n255\n";
    assertEquals(s, this.image.rgbToString());

  }

  @Test
  public void testrgbToString4Across() {
    this.image = new ImageImpl(2, 2);
    String s = ""
            + "\n255 255 255 255.0"
            + "\n255 255 255 255.0"
            + "\n255 255 255 255.0"
            + "\n255 255 255 255.0";
    assertEquals(s, this.image.rgbToString4Across());


  }


  @Test
  public void testSetHSL() {
    this.image = new ImageImpl(2, 2);
    this.image.setHsl(false);
    String[][] hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
    this.image.changeColor("blue", false);
    hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
    hsl = this.image.getHSL(true);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
    this.image.adjustCompositeImage("difference", false, new ImageImpl(2, 2));
    hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
    this.image.adjustCompositeImage("multiply", false, new ImageImpl(2, 2));
    hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,0.0");
      }
    }
    this.image.adjustCompositeImage("screen", false, new ImageImpl(2, 2));
    hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
  }


  @Test
  public void testGetHSL() {
    this.image = new ImageImpl(2, 2);
    this.image.setHsl(false);
    String[][] hsl = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(hsl[i][j], "0.0,0.0,1.0");
      }
    }
  }


  // test adjustCompositeImage
  @Test
  public void testAdjustCompositeImage() {
    this.image = new ImageImpl(2, 2);
    Image other = new ImageImpl(2, 2);
    other.changeColor("red", false);

    // test difference
    this.image.adjustCompositeImage("difference", false, other);
    String[][] rgbs = this.image.getRgbs();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        // dif = |r-dr|, |g-dg|, |b-db|
        // this  = 255 255 255
        // other = 255 0   0
        // result = 0  255 255
        assertEquals(rgbs[i][j], "0.0,255.0,255.0");
      }
    }

    // test multiply
    this.image.setHsl(true);
    System.out.println(this.image.getHSL(false)[0][0]);
    other.setHsl(false);
    System.out.println(other.getHSL(false)[0][0]);
    this.image.adjustCompositeImage("multiply", false, other);
    String[][] hsls = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        // multiply 1 * dl
        // this l = 1.0
        // other l = 0.5
        // result = 0.5
        assertEquals(hsls[i][j], "0.0,0.0,0.5");
      }
    }

    // test screen
    this.image.setHsl(true);
    System.out.println(this.image.getHSL(false)[0][0]);
    other.setHsl(false);
    System.out.println(other.getHSL(false)[0][0]);
    this.image.adjustCompositeImage("screen", false, other);
    hsls = this.image.getHSL(false);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        // this l = 1.0
        // other l = 0.5
        // result = ...
        assertEquals(hsls[i][j], "0.0,0.0,1.0");
      }
    }


  }


  @Test
  public void testRgbToBufferedImage() {
    this.image = new ImageImpl(2, 2);
    Color c;

    this.image.changeColor("red", false);
    BufferedImage buffered = this.image.rgbToBufferedImage();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        c = new Color(buffered.getRGB(i, j));
        assertEquals(255, c.getRed());
        assertEquals(0, c.getBlue());
        assertEquals(0, c.getGreen());
        assertEquals(255, c.getAlpha());
      }
    }

    this.image.changeColor("blue", true);
    buffered = this.image.rgbToBufferedImage();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        c = new Color(buffered.getRGB(i, j));
        assertEquals(0, c.getRed());
        assertEquals(255, c.getBlue());
        assertEquals(0, c.getGreen());
        assertEquals(255, c.getAlpha());
      }
    }

    this.image.changeColor("green", true);
    buffered = this.image.rgbToBufferedImage();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        c = new Color(buffered.getRGB(i, j));
        assertEquals(0, c.getRed());
        assertEquals(0, c.getBlue());
        assertEquals(255, c.getGreen());
        assertEquals(255, c.getAlpha());
      }
    }

  }


  @Test
  public void constructFromAJPEGorPNG() {
    this.image = new ImageImpl("test/tinyBlue.png");
    assertEquals(2, this.image.getHeight());
    assertEquals(2, this.image.getWidth());
    assertEquals(this.image.rgbToString(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
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
            "255.0\n");

    this.image = new ImageImpl("test/tinyRed.jpeg");
    assertEquals(2, this.image.getHeight());
    assertEquals(2, this.image.getWidth());
    assertEquals(this.image.rgbToString(), "P3\n" +
            "2\n" +
            "2\n" +
            "255\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n");

    // test invalid construction
    try {
      this.image = new ImageImpl("");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("     idk");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl(null);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/idkg.txt");
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
  }


  @Test
  public void constructFromFileWHeightAndWidth() {
    this.image = new ImageImpl("test/CAT2.ppm", 1, 1);
    assertEquals(1, this.image.getHeight());
    assertEquals(1, this.image.getWidth());
    assertEquals(this.image.rgbToString(), "P3\n" +
            "1\n" +
            "1\n" +
            "255\n" +
            "8.0\n" +
            "75.0\n" +
            "145.0\n");

    this.image = new ImageImpl("test/tinyBlue.png", 1, 1);
    assertEquals(1, this.image.getHeight());
    assertEquals(1, this.image.getWidth());
    assertEquals(this.image.rgbToString(), "P3\n" +
            "1\n" +
            "1\n" +
            "255\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n");

    this.image = new ImageImpl("test/tinyRed.jpeg", 1, 4);
    assertEquals(1, this.image.getHeight());
    assertEquals(4, this.image.getWidth());
    assertEquals(this.image.rgbToString(), "P3\n" +
            "4\n" +
            "1\n" +
            "255\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n" +
            "254.0\n" +
            "0.0\n" +
            "0.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n" +
            "255.0\n");

    // test invalid construction
    try {
      this.image = new ImageImpl("", 1, 1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("     idk", 1, 1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/idkg.txt", 1, 1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", 0, 1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", -1, 1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", 1, 0);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", 1, -1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", 0, 0);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }
    try {
      this.image = new ImageImpl("test/tinyRed.jpeg", -1, -1);
      fail("expected to fail");
    } catch (IllegalArgumentException ignore) {
      // blank intentionally
    }

  }




  @Test
  public void testGetAlphas() {
    this.image = new ImageImpl(2,2);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        double a = this.image.getAlphas()[i][j];
        assertEquals(255.0, a,.001);
      }
    }
  }



  @Test
  public void testPNGAlpha() {
    this.image = new ImageImpl("test/PNGtransparentwithblue.png");
    assertEquals(this.image.getRgbs()[0][0], "18.0,0.0,255.0");
    assertEquals(this.image.getRgbs()[0][1], "0.0,0.0,0.0");
    assertEquals(this.image.getRgbs()[1][1], "0.0,0.0,0.0");
  }

}
