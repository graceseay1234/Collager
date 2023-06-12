import org.junit.Test;

import model.RepresentationConverter;

import static org.junit.Assert.assertEquals;



/**
 * Tests RepresentationConverter's functionality.
 */
public class RepresentationConvertorTest {
  private RepresentationConverter rep;


  @Test
  public void testConstruction() {
    this.rep = new RepresentationConverter();
    assertEquals("0.0,0.0,0.0",
            this.rep.convertHSLtoRGB(0.0,0.0,0.0));
  }

  @Test
  public void testHSLToRGB() {
    this.rep = new RepresentationConverter();
    assertEquals("255.0,8.49999999999997,0.0",
            this.rep.convertHSLtoRGB(2.0,1.0,0.5));
    assertEquals("255.0,255.0,255.0",
            this.rep.convertHSLtoRGB(360,1.0,1.0));
  }

  @Test
  public void testRGBToHSL() {
    this.rep = new RepresentationConverter();
    assertEquals("20.0,-0.6048387096774194,125.0",
            this.rep.convertRGBtoHSL(200,100,50));
    assertEquals("0.0,0.0,255.0",
            this.rep.convertRGBtoHSL(255,255,255));
  }






















}
