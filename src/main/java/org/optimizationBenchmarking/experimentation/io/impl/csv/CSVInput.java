package org.optimizationBenchmarking.experimentation.io.impl.csv;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDI;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileInputTool;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;
import org.optimizationBenchmarking.utils.text.TextFileTypes;

/**
 * The CSV text input tool can load experiment data from simple text files.
 * These files contain the log points. The definition of the measurement
 * dimensions and problem instances must be provided in an
 * {@link org.optimizationBenchmarking.experimentation.io.impl.edi.EDI EDI
 * file}.
 */
public final class CSVInput extends FileInputTool<ExperimentSetContext> {

  /** the EDI file containing both dimension and instance information */
  public static final String CONFIG_EDI_FILE = "config"; //$NON-NLS-1$
  /** the EDI file containing only dimension information */
  public static final String DIMENSIONS_EDI_FILE = "dimensions";//$NON-NLS-1$
  /** the EDI file containing only instance information */
  public static final String INSTANCES_EDI_FILE = "instances";//$NON-NLS-1$

  /** create */
  CSVInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final _CSVInputToken createToken(final IOJob job,
      final ExperimentSetContext data) throws Throwable {
    return new _CSVInputToken(data);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean enterDirectory(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final _CSVInputToken token;

    token = ((_CSVInputToken) (job.getToken()));
    if (!(token._isPathNew(path, true))) {
      return false;
    }
    token._checkDir(job, path);

    return super.enterDirectory(job, data, path, attributes);
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJob job, final ExperimentSetContext data,
      final Path path, final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    final String suffix;
    final _CSVInputToken token;
    final Path owner, ownerOwner;

    suffix = PathUtils.getFileExtension(path);
    if (suffix == null) {
      return;
    }

    token = ((_CSVInputToken) (job.getToken()));

    if (TextFileTypes.TXT.getDefaultSuffix().equalsIgnoreCase(suffix) || //
        TextFileTypes.CSV.getDefaultSuffix().equalsIgnoreCase(suffix)) {
      owner = PathUtils.normalize(path.getParent());
      if (!(token._isPathNew(owner, false))) {
        ownerOwner = PathUtils.normalize(owner.getParent());
        if (!(token._isPathNew(ownerOwner, false))) {
          // TODO
        }
      }

      return;
    }

    if (XMLFileType.XML.getDefaultSuffix().equals(suffix) || //
        EDI.EDI_XML.getDefaultSuffix().equalsIgnoreCase(suffix)) {
      token._parseEDI(job, path);
      return;
    }
  }

  /**
   * Obtain the globally shared instance of the CSV experiment set data
   * loader.
   * 
   * @return the globally shared instance of the CSV experiment set data
   *         loader.
   */
  public static final CSVInput getInstance() {
    return __CSVInputLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __CSVInputLoader {
    /** the globally shared instance */
    static final CSVInput INSTANCE = new CSVInput();
  }
}
