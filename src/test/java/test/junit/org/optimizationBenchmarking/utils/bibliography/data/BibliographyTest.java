package test.junit.org.optimizationBenchmarking.utils.bibliography.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLInput;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLOutput;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArrayListViewTest;

/**
 * a test case to test generating, reading, and writing some bibliography
 */
@Ignore
public abstract class BibliographyTest extends ArrayListViewTest {

  /** the bibliography */
  private Bibliography m_bib;

  /** create */
  public BibliographyTest() {
    super();
  }

  /**
   * Create a bibliography
   *
   * @return the bibliography
   */
  protected abstract Bibliography createBibliography();

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  public synchronized Bibliography getBibliography() {
    if (this.m_bib == null) {
      this.m_bib = this.createBibliography();
    }
    return this.m_bib;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<Object> getInstance() {
    return ((ArrayListView) (this.getBibliography()));
  }

  /**
   * test serializing and deserializing to xml
   *
   * @throws IOException
   *           if it fails
   */
  @Test(timeout = 3600000)
  public void testSerializeToAndDeserializeFromXMLEqual()
      throws IOException {
    final Bibliography a, b, c;
    final BibliographyXMLOutput output;
    final BibliographyXMLInput input;

    input = BibliographyXMLInput.getInstance();
    Assert.assertNotNull(input);
    Assert.assertTrue(input.canUse());

    output = BibliographyXMLOutput.getInstance();
    Assert.assertNotNull(output);
    Assert.assertTrue(output.canUse());

    a = this.getBibliography();
    try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      output.use().setStream(bos).setSource(a).create().call();
      try (final ByteArrayInputStream bis = new ByteArrayInputStream(
          bos.toByteArray())) {
        try (final BibliographyBuilder bb = new BibliographyBuilder()) {
          input.use().addStream(bis).setDestination(bb).create().call();
          b = bb.getResult();
        }
      }
    }

    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);

    try (final CharArrayWriter cw = new CharArrayWriter()) {
      output.use().setWriter(cw).setSource(b).create().call();
      try (final CharArrayReader cr = new CharArrayReader(cw.toCharArray())) {
        try (final BibliographyBuilder bb = new BibliographyBuilder()) {
          input.use().addReader(cr).setDestination(bb).create().call();
          c = bb.getResult();
        }
      }
    }

    Assert.assertEquals(a, c);
    Assert.assertEquals(b, c);
    Assert.assertEquals(c, a);
    Assert.assertEquals(c, b);
  }

  /**
   * test serializing and deserializing to xml
   *
   * @throws IOException
   *           if it fails
   */
  @Test(timeout = 3600000)
  public void testSerializeToAndDeserializeFromXMLStringEqual()
      throws IOException {
    final Bibliography a, b, c;
    final String x, y, z;
    final BibliographyXMLOutput output;
    final BibliographyXMLInput input;

    input = BibliographyXMLInput.getInstance();
    Assert.assertNotNull(input);
    Assert.assertTrue(input.canUse());

    output = BibliographyXMLOutput.getInstance();
    Assert.assertNotNull(output);
    Assert.assertTrue(output.canUse());

    a = this.getBibliography();

    try (final StringWriter cw = new StringWriter()) {
      output.use().setWriter(cw).setSource(a).create().call();
      x = cw.toString();
      try (final StringReader cr = new StringReader(x)) {
        try (final BibliographyBuilder bb = new BibliographyBuilder()) {
          input.use().addReader(cr).setDestination(bb).create().call();
          b = bb.getResult();
        }
      }
    }

    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);

    try (final StringWriter cw = new StringWriter()) {
      output.use().setWriter(cw).setSource(b).create().call();
      y = cw.toString();
      try (final StringReader cr = new StringReader(y)) {
        try (final BibliographyBuilder bb = new BibliographyBuilder()) {
          input.use().addReader(cr).setDestination(bb).create().call();
          c = bb.getResult();
        }
      }
    }

    Assert.assertEquals(x, y);
    Assert.assertEquals(c, b);
    Assert.assertEquals(b, c);

    try (final StringWriter cw = new StringWriter()) {
      output.use().setWriter(cw).setSource(c).create().call();
      z = cw.toString();
    }

    Assert.assertEquals(z, y);

  }

}
