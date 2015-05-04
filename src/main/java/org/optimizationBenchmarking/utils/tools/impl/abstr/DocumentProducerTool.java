package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerTool;

/**
 * A tool which can produce a specific kind of file, i.e., a document
 */
public abstract class DocumentProducerTool extends FileProducerTool
    implements IDocumentProducerTool {

  /**
   * Create the file producer tool.
   */
  protected DocumentProducerTool() {
    super();
  }

  /**
   * Combine a name suggestion for the main document with a base path and
   * the
   * {@link org.optimizationBenchmarking.utils.io.IFileType#getDefaultSuffix()
   * default suffix} of the {@link #getFileType()} managed by this file
   * producer.
   *
   * @param basePath
   *          the base path
   * @param mainDocumentNameSuggestion
   *          the name suggestion for the main document
   * @return a path suitable for the main document of this file producer
   *         tool
   */
  protected Path makePath(final Path basePath,
      final String mainDocumentNameSuggestion) {
    return PathUtils.createPathInside(basePath,//
        PathUtils.makeFileName(mainDocumentNameSuggestion,//
            this.getFileType().getDefaultSuffix()));
  }

}
