package org.optimizationBenchmarking.experimentation.io.impl.edi;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.abstr.ExperimentXMLInput;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for Experiment Data Interchange (EDI) input. EDI is our
 * default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 */
public final class EDIInput extends ExperimentXMLInput {

  /** create */
  EDIInput() {
    super();
  }

  /**
   * get the instance of the {@link EDIInput}
   * 
   * @return the instance of the {@link EDIInput}
   */
  public static final EDIInput getInstance() {
    return __EDIInputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Object rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(EDI.EDI_XML.getSchemaSource());
    } catch (final Throwable a) {
      rec = ErrorUtils.aggregateError(a, rec);
    } finally {
      sf = null;
    }

    try {
      spf.setNamespaceAware(true);
      if (schema != null) {
        spf.setValidating(false);
        spf.setSchema(schema);
      } else {
        spf.setValidating(false);
      }
    } catch (final Throwable b) {
      rec = ErrorUtils.aggregateError(b, rec);
    }

    if (rec != null) {
      RethrowMode.AS_IO_EXCEPTION
          .rethrow(//
              "Error while loading XMLFileType Schema for Experiment Data Interchange (EDI).",//$NON-NLS-1$
              true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final String n;
    final char lm3, lm2, lm1, lm0;
    int len;

    if (super.isFileInDirectoryLoadable(job, data, path, attributes)) {
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

    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final DefaultHandler wrapDestination(
      final ExperimentSetContext dataDestination, final IOJob job) {
    return new _EDIContentHandler(null, dataDestination, job);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "EDI Experiment Data Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __EDIInputLoader {
    /** create */
    static final EDIInput INSTANCE = new EDIInput();
  }
}
