package examples.org.optimizationBenchmarking.utils.io.xml.performance.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** a writer writing to a temporary file */
final class _TempFileWriter extends FileWriter {

  /** the file */
  private final File m_f;

  /**
   * create
   *
   * @throws IOException
   *           if i/o fails
   */
  _TempFileWriter() throws IOException {
    this(File.createTempFile("_temp_", //$NON-NLS-1$
        ".xml")); //$NON-NLS-1$
  }

  /**
   * create
   *
   * @param f
   *          the file
   * @throws IOException
   *           if i/o fails
   */
  private _TempFileWriter(final File f) throws IOException {
    super(f);
    this.m_f = f;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    try {
      super.close();
    } finally {
      this.m_f.delete();
    }
  }

}
