package org.optimizationBenchmarking.experimentation.io.impl.edi;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.io.impl.FlatExperimentSetContext;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** the internal content handler for EDI */
final class _EDIContentHandler extends DelegatingHandler {

  /** the hierarchical fsm stack */
  private final FlatExperimentSetContext m_context;

  /** the internal string builder */
  private final MemoryTextOutput m_sb;

  /** are we inside a point */
  private int m_inPoint;

  /** the job */
  private final IOJob m_job;
  /** the logger */
  private final Logger m_logger;

  /**
   * create
   *
   * @param owner
   *          the owning handler
   * @param esb
   *          the experiment set builder
   * @param job
   *          the job
   */
  public _EDIContentHandler(final DelegatingHandler owner,
      final FlatExperimentSetContext esb, final IOJob job) {
    super(owner);
    this.m_context = esb;
    this.m_sb = new MemoryTextOutput();
    this.m_job = job;
    this.m_logger = job.getLogger();
  }

  /** {@inheritDoc} */
  @Override
  public final void doWarning(final SAXParseException e)
      throws SAXException {

    if ((this.m_logger != null) && //
        (this.m_logger.isLoggable(Level.WARNING))) {
      this.m_logger.log(Level.WARNING,
          "Warning during parsing of the EDI file.", //$NON-NLS-1$
          e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void doError(final SAXParseException e) throws SAXException {
    try {
      this.m_job.handleError(e, "Error during parsing of the EDI file."); //$NON-NLS-1$
    } catch (final Exception ioe) {
      throw new SAXException(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void doFatalError(final SAXParseException e)
      throws SAXException {
    try {
      this.m_job.handleError(e,
          "Fatal error during parsing of the EDI file."); //$NON-NLS-1$
    } catch (final Exception ioe) {
      throw new SAXException(ioe);
    }
  }

  /**
   * start the bounds element
   *
   * @param atts
   *          the attributes
   */
  private final void __startBounds(final Attributes atts) {
    final String dim;
    String lb, ub;

    dim = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_DIMENSION);
    if (dim != null) {

      lb = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
          EDI.ATTRIBUTE_FLOAT_LOWER_BOUND);
      if (lb == null) {
        lb = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_INTEGER_LOWER_BOUND);
      }

      ub = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
          EDI.ATTRIBUTE_FLOAT_UPPER_BOUND);
      if (ub == null) {
        ub = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_INTEGER_UPPER_BOUND);
      }

      if ((lb != null) || (ub != null)) {
        if (lb != null) {
          this.m_context.instanceSetLowerBound(dim, lb);
        }
        if (ub != null) {
          this.m_context.instanceSetUpperBound(dim, ub);
        }
      }
    }
  }

  /**
   * start the dimension
   *
   * @param atts
   *          the attributes
   */
  private final void __startDimension(final Attributes atts) {
    EPrimitiveType pt;
    String s;
    final Number lb, ub;

    this.m_context.dimensionBegin(true);

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_NAME);
    this.m_context.dimensionSetName(s);

    if ((this.m_logger != null) && //
        (this.m_logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      this.m_logger.log(IOTool.FINE_LOG_LEVEL,
          ("Begin of dimension '" + s + '\'')); //$NON-NLS-1$
    }

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_DESCRIPTION);
    if (s != null) {
      this.m_context.dimensionSetDescription(s);
    }

    this.m_context.dimensionSetType(EDI._parseDimensionType(//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_DIMENSION_TYPE)));

    this.m_context.dimensionSetDirection(EDI._parseDimensionDirection(//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_DIMENSION_DIRECTION)));

    pt = EDI._parseDataType(DelegatingHandler.getAttributeNormalized(atts,
        EDI.NAMESPACE, EDI.ATTRIBUTE_DIMENSION_DATA_TYPE));

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_INTEGER_LOWER_BOUND);
    if (s != null) {
      lb = LongParser.INSTANCE.parseString(s);
    } else {
      s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
          EDI.ATTRIBUTE_FLOAT_LOWER_BOUND);
      if (s != null) {
        lb = DoubleParser.INSTANCE.parseString(s);
      } else {
        lb = null;
      }
    }

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_INTEGER_UPPER_BOUND);
    if (s != null) {
      ub = LongParser.INSTANCE.parseString(s);
    } else {
      s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
          EDI.ATTRIBUTE_FLOAT_UPPER_BOUND);
      if (s != null) {
        ub = DoubleParser.INSTANCE.parseString(s);
      } else {
        ub = null;
      }
    }

    this.m_context.dimensionSetParser(NumberParser.createNumberParser(pt,
        lb, ub));
  }

  /**
   * start the experiment
   *
   * @param atts
   *          the attributes
   */
  private final void __startExperiment(final Attributes atts) {
    String s;

    this.m_context.experimentBegin(true);

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_NAME);
    this.m_context.experimentSetName(s);

    if ((this.m_logger != null) && //
        (this.m_logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      this.m_logger.log(IOTool.FINE_LOG_LEVEL,
          ("Begin of experiment '" + s + '\'')); //$NON-NLS-1$
    }

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_DESCRIPTION);
    if (s != null) {
      this.m_context.experimentSetDescription(s);
    }
  }

  /**
   * start the feature
   *
   * @param atts
   *          the attributes
   */
  private final void __startFeature(final Attributes atts) {
    this.m_context.instanceSetFeatureValue(//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_NAME),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_FEATURE_DESCRIPTION),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_FEATURE_VALUE),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_FEATURE_VALUE_DESCRIPTION));
  }

  /**
   * start the instance
   *
   * @param atts
   *          the attributes
   */
  private final void __startInstance(final Attributes atts) {
    String s;

    this.m_context.instanceBegin(true);

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_NAME);
    this.m_context.instanceSetName(s);

    if ((this.m_logger != null) && //
        (this.m_logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      this.m_logger.log(IOTool.FINE_LOG_LEVEL,
          ("Begin of instance '" + s + '\'')); //$NON-NLS-1$
    }

    s = DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
        EDI.ATTRIBUTE_DESCRIPTION);
    if (s != null) {
      this.m_context.instanceSetDescription(s);
    }
  }

  /**
   * start the instance runs
   *
   * @param atts
   *          the attributes
   */
  private final void __startInstanceRuns(final Attributes atts) {
    this.m_context.runsBegin(true);
    this.m_context.runsSetInstance(DelegatingHandler
        .getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_INSTANCE));
  }

  /** start a run */
  private final void __startRun() {
    this.m_context.runBegin(true);
  }

  /** end the run */
  private final void __endRun() {
    this.m_context.runEnd();
  }

  /** start a point */
  private final void __startPoint() {
    if ((this.m_inPoint++) == 0) {
      this.m_sb.clear();
    }
  }

  /** end the point */
  private final void __endPoint() {
    try {
      this.m_context.runAddDataPoint(TextUtils.normalize(//
          this.m_sb.toString()));
    } finally {
      this.m_sb.clear();
    }
  }

  /** start a point value */
  private final void __startVal() {
    if (this.m_inPoint > 0) {
      if (this.m_sb.length() > 0) {
        this.m_sb.append(' ');
      }
    }
  }

  /**
   * start the parameter
   *
   * @param atts
   *          the attributes
   */
  private final void __startParameter(final Attributes atts) {
    this.m_context.parameterSetValue(//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_NAME),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_PARAMETER_DESCRIPTION),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_PARAMETER_VALUE),//
        DelegatingHandler.getAttributeNormalized(atts, EDI.NAMESPACE,
            EDI.ATTRIBUTE_PARAMETER_VALUE_DESCRIPTION));
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {

    if (((this.m_logger != null) && //
    (this.m_logger.isLoggable(IOTool.FINER_LOG_LEVEL)))) {
      this.m_logger.log(IOTool.FINER_LOG_LEVEL, ("Start of element <" + //$NON-NLS-1$
          uri + ':' + localName + '>'));
    }

    if ((uri == null) || EDI.NAMESPACE.equalsIgnoreCase(uri)) {

      if (EDI.ELEMENT_BOUNDS.equalsIgnoreCase(localName)) {
        this.__startBounds(attributes);
        return;
      }
      if (EDI.ELEMENT_DIMENSION.equalsIgnoreCase(localName)) {
        this.__startDimension(attributes);
        return;
      }

      if (EDI.ELEMENT_EXPERIMENT.equalsIgnoreCase(localName)) {
        this.__startExperiment(attributes);
        return;
      }

      if (EDI.ELEMENT_FEATURE.equalsIgnoreCase(localName)) {
        this.__startFeature(attributes);
        return;
      }
      if (EDI.ELEMENT_FLOAT.equalsIgnoreCase(localName)) {
        this.__startVal();
        return;
      }
      if (EDI.ELEMENT_INSTANCE.equalsIgnoreCase(localName)) {
        this.__startInstance(attributes);
        return;
      }
      if (EDI.ELEMENT_INSTANCE_RUNS.equalsIgnoreCase(localName)) {
        this.__startInstanceRuns(attributes);
        return;
      }
      if (EDI.ELEMENT_INT.equalsIgnoreCase(localName)) {
        this.__startVal();
        return;
      }
      if (EDI.ELEMENT_PARAMETER.equalsIgnoreCase(localName)) {
        this.__startParameter(attributes);
        return;
      }
      if (EDI.ELEMENT_POINT.equalsIgnoreCase(localName)) {
        this.__startPoint();
        return;
      }
      if (EDI.ELEMENT_RUN.equalsIgnoreCase(localName)) {
        this.__startRun();
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {

    if (((this.m_logger != null) && //
    (this.m_logger.isLoggable(IOTool.FINER_LOG_LEVEL)))) {
      this.m_logger.log(IOTool.FINER_LOG_LEVEL, ("End of element </" + //$NON-NLS-1$
          uri + ':' + localName + '>'));
    }

    if ((uri == null) || EDI.NAMESPACE.equalsIgnoreCase(uri)) {

      if (EDI.ELEMENT_POINT.equalsIgnoreCase(localName)) {
        this.__endPoint();
        return;
      }

      if (EDI.ELEMENT_RUN.equalsIgnoreCase(localName)) {
        this.__endRun();
        return;
      }

      if (EDI.ELEMENT_EXPERIMENT_DATA.equalsIgnoreCase(localName)) {
        this.close();
        return;
      }
    }

  }

  /** {@inheritDoc} */
  @Override
  public final void characters(final char ch[], final int start,
      final int length) throws SAXException {
    try {
      if (this.m_inPoint > 0) {
        this.m_sb.append(ch, start, (start + length));
      }
    } finally {
      super.characters(ch, start, (start + length));
    }
  }

}
