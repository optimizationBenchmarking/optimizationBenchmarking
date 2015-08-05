package org.optimizationBenchmarking.utils.io;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;
import org.optimizationBenchmarking.utils.text.ETextFileType;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/**
 * A detector for mime types. This detector attempts to use use
 * {@link java.nio.file.Files#probeContentType(Path)} to determine the MIME
 * type of a path. If that does not work, it tries to match the path with
 * some of the file types we frequently use in our code via the
 * {@link #getMimeTypeBySuffix(String)} method. If that does not work
 * either, it returns {@code application/octet-stream}.
 */
public final class MimeTypeDetector {

  /** prevent instantiation */
  MimeTypeDetector() {
    super();
  }

  /**
   * Get the mime type based on a file suffix
   *
   * @param suffix
   *          the suffix
   * @return the mime type, or {@code application/octet-stream} if nothing
   *         else was found
   */
  public final String getMimeTypeBySuffix(final String suffix) {
    final String use;
    String detected;

    if (suffix != null) {

      use = TextUtils.toLowerCase(suffix);

      for (final IFileType type : ETextFileType.values()) {
        if (use.equalsIgnoreCase(type.getDefaultSuffix())) {
          detected = type.getMIMEType();
          if (detected != null) {
            return detected;
          }
        }
      }

      for (final IFileType type : EGraphicFormat.INSTANCES) {
        if (use.equalsIgnoreCase(type.getDefaultSuffix())) {
          detected = type.getMIMEType();
          if (detected != null) {
            return detected;
          }
        }
      }

      for (final IFileType type : ELaTeXFileType.INSTANCES) {
        if (use.equalsIgnoreCase(type.getDefaultSuffix())) {
          detected = type.getMIMEType();
          if (detected != null) {
            return detected;
          }
        }
      }

      if (use.equalsIgnoreCase(XMLFileType.XML.getDefaultSuffix())
          || use.equalsIgnoreCase("edi")) { //$NON-NLS-1$
        detected = XMLFileType.XML.getMIMEType();
        if (detected != null) {
          return detected;
        }
      }

      if (use.equalsIgnoreCase(ConfigurationXML.CONFIG_XML
          .getDefaultSuffix())) {
        detected = ConfigurationXML.CONFIG_XML.getMIMEType();
        if (detected != null) {
          return detected;
        }
      }

      if (use.equalsIgnoreCase(XHTML.XHTML_1_0.getDefaultSuffix())) {
        detected = XHTML.XHTML_1_0.getMIMEType();
        if (detected != null) {
          return detected;
        }
      }

    }
    return "application/octet-stream"; //$NON-NLS-1$
  }

  /**
   * Obtain the mime type of a given file.
   *
   * @param file
   *          the file
   * @return the mime type, or {@code application/octet-stream} if nothing
   *         else was found
   */
  public final String getMimeType(final Path file) {
    String detected;

    detected = null;
    try {
      detected = Files.probeContentType(file);
    } catch (final Throwable error) {//
    }

    if (detected != null) {
      return detected;
    }

    return this.getMimeTypeBySuffix(PathUtils.getFileExtension(file));
  }

  /**
   * Get the globally shared instance of the mime type detector
   *
   * @return the globally shared instance of the mime type detector
   */
  public static final MimeTypeDetector getInstance() {
    return __MimeTypeDetectorHolder.INSTANCE;
  }

  /** the holder */
  private static final class __MimeTypeDetectorHolder {

    /** the shared instance */
    static final MimeTypeDetector INSTANCE = new MimeTypeDetector();
  }
}
