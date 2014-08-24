package org.optimizationBenchmarking.experimentation.io.edi;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.experimentation.data.DataPoint;
import org.optimizationBenchmarking.experimentation.data.Dimension;
import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.FeatureValue;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.data.InstanceRuns;
import org.optimizationBenchmarking.experimentation.data.InstanceSet;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.ParameterValue;
import org.optimizationBenchmarking.experimentation.data.Run;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.structured.XMLIODriver;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.XMLNumberAppender;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for Experiment Data Interchange (EDI) input and output. EDI is
 * our default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 */
public final class EDIDriver extends
    XMLIODriver<Object, ExperimentSetContext> {

  /** create */
  public static final EDIDriver INSTANCE = new EDIDriver();

  /** the namespace uri */
  private static final URI NAMESPACE_URI = URI
      .create(
          "http://www.optimizationBenchmarking.org/formats/experimentDataInterchange/experimentDataInterchange.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace uri */
  static final String NAMESPACE = EDIDriver.NAMESPACE_URI.toString();
  // /** the namespace prefix */
  //  static final String NAMESPACE_PREFIX = "edi"; //$NON-NLS-1$

  /** the experiment set tag */
  static final String E_EXPERIMENT_DATA = "experimentData"; //$NON-NLS-1$

  /** the experiment data version attribute */
  static final String A_VERSION = "version"; //$NON-NLS-1$

  /** the experiment data version attribute value */
  static final String AV_VERSION_VALUE = "1.0"; //$NON-NLS-1$

  /** the dimensions element */
  static final String E_DIMENSIONS = "dimensions"; //$NON-NLS-1$

  /** the dimension element */
  static final String E_DIMENSION = "dimension"; //$NON-NLS-1$

  /** the name attribute */
  static final String A_NAME = "name"; //$NON-NLS-1$

  /** the name attribute */
  static final String A_DESCRIPTION = "description"; //$NON-NLS-1$

  /** the dimension type attribute */
  static final String A_DIMENSION_TYPE = "dimensionType"; //$NON-NLS-1$

  /** the dimension type values */
  static final String[] AV_DIMENSION_TYPE;

  static {
    AV_DIMENSION_TYPE = new String[EDimensionType.INSTANCES.size()];
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.RUNTIME_CPU.ordinal()] = "runtimeCPU"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.RUNTIME_NORMALIZED
        .ordinal()] = "runtimeNormalized"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.ITERATION_ALGORITHM_STEP
        .ordinal()] = "iterationAlgorithmStep"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.ITERATION_FE.ordinal()] = "iterationFE"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.ITERATION_SUB_FE.ordinal()] = "iterationSubFE"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.QUALITY_PROBLEM_DEPENDENT
        .ordinal()] = "qualityProblemDependent"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_TYPE[EDimensionType.QUALITY_PROBLEM_INDEPENDENT
        .ordinal()] = "qualityProblemIndependent"; //$NON-NLS-1$
  }

  /** the dimension direction attribute */
  static final String A_DIMENSION_DIRECTION = "direction"; //$NON-NLS-1$

  /** the dimension direction values */
  static final String[] AV_DIMENSION_DIRECTION;

  static {
    AV_DIMENSION_DIRECTION = new String[EDimensionDirection.INSTANCES
        .size()];
    EDIDriver.AV_DIMENSION_DIRECTION[EDimensionDirection.DECREASING
        .ordinal()] = "decreasing"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DIRECTION[EDimensionDirection.DECREASING_STRICTLY
        .ordinal()] = "decreasingStrictly"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DIRECTION[EDimensionDirection.INCREASING
        .ordinal()] = "increasing"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DIRECTION[EDimensionDirection.INCREASING_STRICTLY
        .ordinal()] = "increasingStrictly"; //$NON-NLS-1$
  }

  /** the dimension data type attribute */
  static final String A_DIMENSION_DATA_TYPE = "dataType"; //$NON-NLS-1$

  /** the dimension data type values */
  static final String[] AV_DIMENSION_DATA_TYPE;

  static {
    AV_DIMENSION_DATA_TYPE = new String[EPrimitiveType.TYPES.size()];
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.BYTE.ordinal()] = "byte"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.SHORT.ordinal()] = "short"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.INT.ordinal()] = "int"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.LONG.ordinal()] = "long"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.FLOAT.ordinal()] = "float"; //$NON-NLS-1$
    EDIDriver.AV_DIMENSION_DATA_TYPE[EPrimitiveType.DOUBLE.ordinal()] = "double"; //$NON-NLS-1$
  }

  /** the dimension integer lower bound */
  static final String A_INTEGER_LOWER_BOUND = "iLowerBound"; //$NON-NLS-1$
  /** the dimension integer upper bound */
  static final String A_INTEGER_UPPER_BOUND = "iUpperBound"; //$NON-NLS-1$

  /** the dimension floating point lower bound */
  static final String A_FLOAT_LOWER_BOUND = "fLowerBound"; //$NON-NLS-1$
  /** the dimension floating point upper bound */
  static final String A_FLOAT_UPPER_BOUND = "fUpperBound"; //$NON-NLS-1$

  /** the instances element */
  static final String E_INSTANCES = "instances"; //$NON-NLS-1$

  /** the instance element */
  static final String E_INSTANCE = "instance"; //$NON-NLS-1$
  /** the feature element */
  static final String E_FEATURE = "feature"; //$NON-NLS-1$
  /** the feature description attribute */
  static final String A_FEATURE_DESCRIPTION = "featureDescription"; //$NON-NLS-1$
  /** the feature value attribute */
  static final String A_FEATURE_VALUE = "value"; //$NON-NLS-1$
  /** the feature value description attribute */
  static final String A_FEATURE_VALUE_DESCRIPTION = "valueDescription"; //$NON-NLS-1$

  /** the limits element */
  static final String E_BOUNDS = "bounds"; //$NON-NLS-1$
  /** the dimension attribute */
  static final String A_DIMENSION = "dimension"; //$NON-NLS-1$

  /** the experiments element */
  static final String E_EXPERIMENTS = "experiments"; //$NON-NLS-1$
  /** the experiment element */
  static final String E_EXPERIMENT = "experiment"; //$NON-NLS-1$

  /** the parameter element */
  static final String E_PARAMETER = "parameter"; //$NON-NLS-1$
  /** the parameter description attribute */
  static final String A_PARAMETER_DESCRIPTION = "parameterDescription"; //$NON-NLS-1$
  /** the parameter value attribute */
  static final String A_PARAMETER_VALUE = EDIDriver.A_FEATURE_VALUE;
  /** the parameter value description attribute */
  static final String A_PARAMETER_VALUE_DESCRIPTION = "valueDescription"; //$NON-NLS-1$

  /** the instance runs element */
  static final String E_INSTANCE_RUNS = "instanceRuns"; //$NON-NLS-1$
  /** the instance attribute */
  static final String A_INSTANCE = "instance"; //$NON-NLS-1$
  /** the run element */
  static final String E_RUN = "run"; //$NON-NLS-1$
  /** the point element */
  static final String E_POINT = "p"; //$NON-NLS-1$
  /** the int element */
  static final String E_INT = "i"; //$NON-NLS-1$
  /** the float element */
  static final String E_FLOAT = "f"; //$NON-NLS-1$

  /** create */
  private EDIDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Throwable rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(//
          EDIDriver.class.getResource("experimentDataInterchange.1.0.xsd")); //$NON-NLS-1$
    } catch (final Throwable a) {
      rec = a;
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
      rec = ErrorUtils.aggregateError(rec, b);
    }

    if (rec != null) {
      ErrorUtils.throwAsIOException(rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(
      final ExperimentSetContext loadContext, final Path file) {
    final String n;
    final char lm3, lm2, lm1, lm0;
    int len;

    if (super.isFileInDirectoryLoadable(loadContext, file)) {
      n = file.toString();
      len = n.length();
      if (len <= 4) {
        return false;
      }

      lm0 = (n.charAt(--len));
      lm1 = (n.charAt(--len));
      lm2 = (n.charAt(--len));
      lm3 = (n.charAt(--len));

      return ((lm3 == '.') && (//
      (((lm2 == 'x') || (lm2 == 'X')) && ((lm1 == 'm') || (lm1 == 'M')) && ((lm0 == 'l') || (lm0 == 'L'))) || //
      (((lm2 == 'e') || (lm2 == 'E')) && ((lm1 == 'd') || (lm1 == 'D')) && ((lm0 == 'i') || (lm0 == 'I')))));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final DefaultHandler wrapLoadContext(
      final ExperimentSetContext loaderContext, final Logger logger) {
    return new _EDIContentHandler(null, loaderContext, logger);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void doStoreXML(final Object data, final XMLBase dest,
      final Logger logger) {
    final ArraySetView v;

    try (final XMLElement root = dest.element()) {

      root.namespaceSetPrefix(EDIDriver.NAMESPACE_URI, "e"); //$NON-NLS-1$
      root.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_EXPERIMENT_DATA);

      root.attributeRaw(EDIDriver.NAMESPACE_URI, EDIDriver.A_VERSION,
          EDIDriver.AV_VERSION_VALUE);

      write: {
        if (data instanceof ExperimentSet) {
          EDIDriver.__writeExperimentSet(((ExperimentSet) data), root,
              logger);
          break write;
        }
        if (data instanceof DimensionSet) {
          EDIDriver.__writeDimensionSet(((DimensionSet) data), root,
              logger);
          break write;
        }
        if (data instanceof InstanceSet) {
          EDIDriver.__writeInstanceSet(((InstanceSet) data), root, logger);
          break write;
        }
        if (data instanceof Experiment) {
          EDIDriver.__writeExperiment(((Experiment) data), root, logger,
              new HashSet<>());
          break write;
        }

        if (data instanceof ArraySetView) {
          v = ((ArraySetView) data);
          if (v.size() > 0) {
            if (v.get(0) instanceof Experiment) {
              EDIDriver.__writeExperiments(v, root, logger);
              break write;
            }
          }
        }

        throw new IllegalArgumentException("Cannot deal with input " //$NON-NLS-1$
            + data);
      }
    }

  }

  /**
   * Write an experiment set
   *
   * @param es
   *          the experiment set
   * @param dest
   *          the destination
   * @param logger
   *          the logger for log output
   */
  private static final void __writeExperimentSet(final ExperimentSet es,
      final XMLElement dest, final Logger logger) {

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to write experiment set " + es); //$NON-NLS-1$
    }

    EDIDriver.__writeDimensionSet(es.getDimensions(), dest, logger);
    EDIDriver.__writeInstanceSet(es.getInstances(), dest, logger);
    EDIDriver.__writeExperiments(es.getData(), dest, logger);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished writing experiment set " + es); //$NON-NLS-1$
    }
  }

  /**
   * Write an dimension set
   *
   * @param ds
   *          the dimension set
   * @param dest
   *          the destination
   * @param logger
   *          the logger for log output
   */
  private static final void __writeDimensionSet(final DimensionSet ds,
      final XMLElement dest, final Logger logger) {
    EPrimitiveType t;
    NumberParser<?> p;
    String s;
    long l;
    double f;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to write dimension set " + ds); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_DIMENSIONS);

      for (final Dimension d : ds.getData()) {
        try (final XMLElement dim = root.element()) {
          dim.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_DIMENSION);

          dim.attributeEncoded(EDIDriver.NAMESPACE_URI, EDIDriver.A_NAME,
              d.getName());

          s = d.getDescription();
          if (s != null) {
            dim.attributeEncoded(EDIDriver.NAMESPACE_URI,
                EDIDriver.A_DESCRIPTION, s);
          }

          dim.attributeRaw(EDIDriver.NAMESPACE_URI,
              EDIDriver.A_DIMENSION_TYPE, EDIDriver.AV_DIMENSION_TYPE[d
                  .getDimensionType().ordinal()]);

          dim.attributeRaw(EDIDriver.NAMESPACE_URI,
              EDIDriver.A_DIMENSION_DIRECTION,
              EDIDriver.AV_DIMENSION_DIRECTION[d.getDirection().ordinal()]);

          t = d.getDataType();
          dim.attributeRaw(EDIDriver.NAMESPACE_URI,
              EDIDriver.A_DIMENSION_DATA_TYPE,
              EDIDriver.AV_DIMENSION_DATA_TYPE[t.ordinal()]);

          p = d.getParser();
          switch (t) {
            case BYTE: {
              if ((l = p.getLowerBoundLong()) > Byte.MIN_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Byte.MAX_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case SHORT: {
              if ((l = p.getLowerBoundLong()) > Short.MIN_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Short.MAX_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case INT: {
              if ((l = p.getLowerBoundLong()) > Integer.MIN_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Integer.MAX_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case LONG: {
              if ((l = p.getLowerBoundLong()) > Long.MIN_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Long.MAX_VALUE) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case FLOAT:
            case DOUBLE: {
              if ((f = p.getLowerBoundDouble()) > Double.NEGATIVE_INFINITY) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_FLOAT_LOWER_BOUND,
                    XMLNumberAppender.INSTANCE.toString(f,
                        ETextCase.IN_SENTENCE));
              }
              if ((f = p.getUpperBoundDouble()) < Double.POSITIVE_INFINITY) {
                dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                    EDIDriver.A_FLOAT_UPPER_BOUND,
                    XMLNumberAppender.INSTANCE.toString(f,
                        ETextCase.IN_SENTENCE));
              }
              break;
            }
            default: {
              throw new IllegalArgumentException(t + //
                  " cannot be the data type of dimension " //$NON-NLS-1$
                  + d);
            }
          }

        }
      }

    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished to write dimension set " + ds); //$NON-NLS-1$
    }
  }

  /**
   * Write an instance set
   *
   * @param ds
   *          the instance set
   * @param dest
   *          the destination
   * @param logger
   *          the logger for log output
   */
  private static final void __writeInstanceSet(final InstanceSet ds,
      final XMLElement dest, final Logger logger) {
    final HashSet<Object> hs;
    final ArraySetView<Dimension> dims;
    String s;
    Feature fe;
    Number lower, upper;
    NumberParser<?> p;
    boolean x, y;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to write instance set " + ds); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_INSTANCES);

      hs = new HashSet<>();
      dims = ds.getOwner().getDimensions().getData();

      for (final Instance i : ds.getData()) {

        try (XMLElement inst = root.element()) {
          inst.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_INSTANCE);

          inst.attributeEncoded(EDIDriver.NAMESPACE_URI, EDIDriver.A_NAME,
              i.getName());

          s = i.getDescription();
          if (s != null) {
            inst.attributeEncoded(EDIDriver.NAMESPACE_URI,
                EDIDriver.A_DESCRIPTION, s);
          }

          for (final FeatureValue fv : i.features()) {
            try (final XMLElement feat = inst.element()) {
              feat.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_FEATURE);

              fe = fv.getKey();

              feat.attributeEncoded(EDIDriver.NAMESPACE_URI,
                  EDIDriver.A_NAME, fe.getName());
              s = fe.getDescription();
              if (s != null) {
                if (hs.add(fe)) {
                  feat.attributeEncoded(EDIDriver.NAMESPACE_URI,
                      EDIDriver.A_FEATURE_DESCRIPTION, s);
                }
              }

              feat.attributeEncoded(EDIDriver.NAMESPACE_URI,
                  EDIDriver.A_FEATURE_VALUE, fv.getName());
              s = fv.getDescription();
              if (s != null) {
                if (hs.add(fv)) {
                  feat.attributeEncoded(EDIDriver.NAMESPACE_URI,
                      EDIDriver.A_FEATURE_VALUE_DESCRIPTION, s);
                }
              }

            }
          }

          for (final Dimension d : dims) {
            lower = i.lowerBound(d);
            upper = i.upperBound(d);
            p = d.getParser();

            if (d.getDataType().isInteger()) {
              x = (lower.longValue() > p.getLowerBoundLong());
              y = (upper.longValue() < p.getUpperBoundLong());
              if (x || y) {
                try (final XMLElement dim = inst.element()) {
                  dim.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_BOUNDS);

                  dim.attributeEncoded(EDIDriver.NAMESPACE_URI,
                      EDIDriver.A_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                        EDIDriver.A_INTEGER_LOWER_BOUND,
                        Long.toString(lower.longValue()));
                  }
                  if (y) {
                    dim.attributeRaw(EDIDriver.NAMESPACE_URI,
                        EDIDriver.A_INTEGER_UPPER_BOUND,
                        Long.toString(upper.longValue()));
                  }
                }

              }

            } else {

              x = (lower.doubleValue() > p.getLowerBoundDouble());
              y = (upper.doubleValue() < p.getUpperBoundDouble());
              if (x || y) {
                try (final XMLElement dim = inst.element()) {
                  dim.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_BOUNDS);

                  dim.attributeEncoded(EDIDriver.NAMESPACE_URI,
                      EDIDriver.A_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(
                        EDIDriver.NAMESPACE_URI,
                        EDIDriver.A_FLOAT_LOWER_BOUND,
                        XMLNumberAppender.INSTANCE.toString(
                            lower.doubleValue(), ETextCase.IN_SENTENCE));
                  }
                  if (y) {
                    dim.attributeRaw(
                        EDIDriver.NAMESPACE_URI,
                        EDIDriver.A_FLOAT_UPPER_BOUND,
                        XMLNumberAppender.INSTANCE.toString(
                            upper.doubleValue(), ETextCase.IN_SENTENCE));
                  }
                }
              }
            }
          }
        }
      }
    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished to write instance set " + ds); //$NON-NLS-1$
    }
  }

  /**
   * Write the experiments
   *
   * @param e
   *          the experiment
   * @param dest
   *          the dest
   * @param logger
   *          the logger for log output
   * @param described
   *          the described elements
   */
  private static final void __writeExperiment(final Experiment e,
      final XMLElement dest, final Logger logger,
      final HashSet<Object> described) {
    final boolean[] isInt;
    final int k;
    final ArraySetView<Dimension> ds;
    String s;
    Parameter fe;
    int i;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to write experiment " + e); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_EXPERIMENT);

      root.attributeEncoded(EDIDriver.NAMESPACE_URI, EDIDriver.A_NAME,
          e.getName());

      s = e.getDescription();
      if (s != null) {
        root.attributeEncoded(EDIDriver.NAMESPACE_URI,
            EDIDriver.A_DESCRIPTION, s);
      }

      for (final ParameterValue fv : e.parameters()) {
        if (fv.isUnspecified()) {
          continue;
        }
        try (final XMLElement p = root.element()) {
          p.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_PARAMETER);
          fe = fv.getKey();

          p.attributeEncoded(EDIDriver.NAMESPACE_URI, EDIDriver.A_NAME,
              fe.getName());
          s = fe.getDescription();
          if (s != null) {
            if (described.add(fe)) {
              p.attributeEncoded(EDIDriver.NAMESPACE_URI,
                  EDIDriver.A_PARAMETER_DESCRIPTION, s);
            }
          }

          p.attributeEncoded(EDIDriver.NAMESPACE_URI,
              EDIDriver.A_PARAMETER_VALUE, fv.getName());
          s = fv.getDescription();
          if (s != null) {
            if (described.add(fv)) {
              p.attributeEncoded(EDIDriver.NAMESPACE_URI,
                  EDIDriver.A_PARAMETER_VALUE_DESCRIPTION, s);
            }
          }

        }
      }

      ds = e.getOwner().getDimensions().getData();
      k = ds.size();
      isInt = new boolean[k];
      for (i = k; (--i) >= 0;) {
        isInt[i] = ds.get(i).getDataType().isInteger();
      }

      for (final InstanceRuns ir : e.getData()) {
        try (final XMLElement irx = root.element()) {
          irx.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_INSTANCE_RUNS);

          irx.attributeEncoded(EDIDriver.NAMESPACE_URI,
              EDIDriver.A_INSTANCE, ir.getInstance().getName());
          for (final Run r : ir.getData()) {
            try (final XMLElement rx = irx.element()) {
              rx.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_RUN);

              for (final DataPoint p : r.getData()) {
                try (final XMLElement px = rx.element()) {
                  px.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_POINT);
                  for (i = 0; i < k; i++) {
                    if (isInt[i]) {
                      try (final XMLElement ix = px.element()) {
                        ix.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_INT);
                        ix.textRaw().append(p.getLong(i));
                      }
                    } else {
                      try (final XMLElement ix = px.element()) {
                        ix.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_FLOAT);
                        ix.textRaw().append(
                            XMLNumberAppender.INSTANCE.toString(
                                p.getDouble(i), ETextCase.IN_SENTENCE));

                      }
                    }

                  }

                }
              }
            }
          }

        }
      }

    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished writing experiment " + e); //$NON-NLS-1$
    }
  }

  /**
   * Write the experiments
   *
   * @param es
   *          the experiments
   * @param dest
   *          the dest
   * @param logger
   *          the logger for log output
   */
  private static final void __writeExperiments(
      final ArraySetView<Experiment> es, final XMLElement dest,
      final Logger logger) {
    final HashSet<Object> described;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to write experiments."); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDIDriver.NAMESPACE_URI, EDIDriver.E_EXPERIMENTS);

      described = new HashSet<>();
      for (final Experiment e : es) {
        EDIDriver.__writeExperiment(e, root, logger, described);
      }

    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished to write experiments."); //$NON-NLS-1$
    }
  }

}
