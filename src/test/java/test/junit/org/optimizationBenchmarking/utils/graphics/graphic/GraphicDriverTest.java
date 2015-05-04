package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import java.awt.HeadlessException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;
import examples.org.optimizationBenchmarking.utils.graphics.GraphicsExample;

/** A test class for graphic drivers */
@Ignore
public abstract class GraphicDriverTest extends ToolTest<IGraphicDriver> {

  /** create */
  protected GraphicDriverTest() {
    super();
  }

  /**
   * test painting an example graphic
   *
   * @throws IOException
   *           if i/o fails
   */
  @Test(timeout = 3600000)
  public void testExampleGraphic() throws IOException {
    final IGraphicBuilder builder;
    final __FileProducerListener listener;

    try (final TempDir temp = new TempDir()) {
      builder = this.getInstance().use();
      builder.setBasePath(temp.getPath());
      builder.setMainDocumentNameSuggestion("test"); //$NON-NLS-1$
      builder.setSize(new PhysicalDimension(10, 10, ELength.CM));
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);

      try (final Graphic graph = builder.create()) {
        try {
          GraphicsExample.paint(graph);
        } catch (final HeadlessException hex) {
          // this may happend
        }
      }

      Assert.assertNotNull(listener.m_path);
      Assert.assertTrue(Files.exists(listener.m_path));
      Assert.assertTrue(Files.isRegularFile(listener.m_path));
    }

    MemoryUtils.quickGC();
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    try {
      this.testExampleGraphic();
    } catch (final IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  /** the listener */
  private static final class __FileProducerListener implements
      IFileProducerListener {

    /** the path to the graphic */
    Path m_path;

    /** create */
    __FileProducerListener() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public void onFilesFinalized(
        final Collection<Entry<Path, IFileType>> result) {
      Iterator<Entry<Path, IFileType>> i;
      Entry<Path, IFileType> e;

      Assert.assertNotNull(result);
      Assert.assertEquals(1, result.size());
      i = result.iterator();
      Assert.assertNotNull(i);
      Assert.assertTrue(i.hasNext());
      e = i.next();
      Assert.assertFalse(i.hasNext());

      Assert.assertNotNull(e);
      Assert.assertNotNull(e.getValue());

      Assert.assertTrue(e.getValue() instanceof EGraphicFormat);

      this.m_path = e.getKey();
      Assert.assertNotNull(this.m_path);
    }
  }
}
