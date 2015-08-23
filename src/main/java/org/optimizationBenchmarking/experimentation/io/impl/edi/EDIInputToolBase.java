package org.optimizationBenchmarking.experimentation.io.impl.edi;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.AbstractFlatExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.FlatExperimentSetContext;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A base class for drivers for Experiment Data Interchange (EDI) input.
 * EDI is our default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}. The goal of having this base class is to be able to combine
 * several different formats with EDI.
 *
 * @param <T>
 *          the type of element to fill with data, usually a subclass of
 *          {@link org.optimizationBenchmarking.experimentation.io.impl.AbstractFlatExperimentSetContext}
 *          or
 *          {@link org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext}
 */
public abstract class EDIInputToolBase<T> extends XMLInputTool<T> {

  /** create */
  protected EDIInputToolBase() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Object rec;
    Schema schema;

    schema = __SchemaLoader.SCHEMA;
    rec = null;
    try {
      spf.setNamespaceAware(true);
      if (schema != null) {
        spf.setValidating(false);
        spf.setSchema(schema);
      } else {
        spf.setValidating(false);
      }
    } catch (final Throwable b) {
      rec = ErrorUtils.aggregateError(b, __SchemaLoader.ERROR);
    }

    if (rec != null) {
      RethrowMode.AS_IO_EXCEPTION
          .rethrow(//
              "Error while loading XML Schema for Experiment Data Interchange (EDI).",//$NON-NLS-1$
              true, rec);
    }
  }

  /**
   * Check whether a regular file may be an EDI file: Files are considered
   * to be EDI files if their suffix is either {@code edi} or {@code xml}.
   *
   * @param job
   *          the IO job
   * @param data
   *          the data
   * @param path
   *          the path
   * @param attributes
   *          the file attributes
   * @return {@code true} if the file is an EDI file
   * @throws Throwable
   *           if something goes wrong
   */
  protected boolean isEDI(final IOJob job, final T data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final String n;
    final char lm3, lm2, lm1, lm0;
    int len;

    n = path.toString();
    len = n.length();
    if (len <= 4) {
      return false;
    }

    lm0 = (n.charAt(--len));
    lm1 = (n.charAt(--len));
    lm2 = (n.charAt(--len));
    lm3 = (n.charAt(--len));

    return (((lm3 == '.') && (//
    (((lm2 == 'x') || (lm2 == 'X'))//
        && ((lm1 == 'm') || (lm1 == 'M'))//
    && ((lm0 == 'l') || (lm0 == 'L'))) || //
    (((lm2 == 'e') || (lm2 == 'E'))//
        && ((lm1 == 'd') || (lm1 == 'D'))//
    && ((lm0 == 'i') || (lm0 == 'I'))))) || //
    ((lm2 == EDI.SUFFIX_CHARS[0])//
        && (lm1 == EDI.SUFFIX_CHARS[1])//
    && (lm0 == EDI.SUFFIX_CHARS[2])));
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(final IOJob job,
      final T data, final Path path, final BasicFileAttributes attributes)
      throws Throwable {
    if (super.isFileInDirectoryLoadable(job, data, path, attributes)) {
      return this.isEDI(job, data, path, attributes);
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final DefaultHandler wrapDestination(final T dataDestination,
      final IOJob job) {
    return new _EDIContentHandler(null,
        ((AbstractFlatExperimentSetContext) (job.getToken())), job);
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractFlatExperimentSetContext createToken(final IOJob job,
      final T data) throws Throwable {
    if (data instanceof ExperimentSetContext) {
      return new FlatExperimentSetContext((ExperimentSetContext) data);
    }
    if (data instanceof AbstractFlatExperimentSetContext) {
      return ((AbstractFlatExperimentSetContext) data);
    }
    throw new IllegalArgumentException(//
        "Data element '" + data + //$NON-NLS-1$
            "' is not supported as input destation by " + //$NON-NLS-1$
            this.getClass().getSimpleName());
  }

  /** {@inheritDoc} */
  @Override
  protected void after(final IOJob job, final T data) throws Throwable {
    ((FlatExperimentSetContext) (job.getToken())).flush();
    super.after(job, data);
  }

  /** the schema */
  private static final class __SchemaLoader {
    /** the schema */
    static final Schema SCHEMA;

    /** the error */
    static final Throwable ERROR;

    static {
      SchemaFactory schemaFactory;
      Schema schema;
      Throwable error;

      schema = null;
      error = null;
      try {
        schemaFactory = SchemaFactory
            .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFactory.newSchema(EDI.EDI_XML.getSchemaSource());
      } catch (final Throwable caught) {
        error = caught;
        schema = null;
      }

      SCHEMA = schema;
      ERROR = error;
    }
  }
}
