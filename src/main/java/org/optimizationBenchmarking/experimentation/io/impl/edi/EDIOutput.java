package org.optimizationBenchmarking.experimentation.io.impl.edi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DataPoint;
import org.optimizationBenchmarking.experimentation.data.Dimension;
import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.FeatureValue;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.experimentation.data.InstanceRuns;
import org.optimizationBenchmarking.experimentation.data.InstanceSet;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.ParameterValue;
import org.optimizationBenchmarking.experimentation.data.Run;
import org.optimizationBenchmarking.experimentation.io.impl.abstr.ExperimentSetXMLOutput;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.XMLNumberAppender;

/**
 * A driver for Experiment Data Interchange (EDI) output. EDI is our
 * default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 */
public final class EDIOutput extends ExperimentSetXMLOutput<Object> {

  /** create */
  EDIOutput() {
    super();
  }

  /**
   * Get the instance of the {@link EDIOutput}
   * 
   * @return the instance of the {@link EDIOutput}
   */
  public static final EDIOutput getInstance() {
    return __EDIOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final String getDefaultPlainOutputFileName() {
    return ("experiments." + //$NON-NLS-1$ 
    EDI.EDI_XML.getDefaultSuffix());
  }

  /** {@inheritDoc} */
  @Override
  protected final void file(final IOJob job, final Object data,
      final Path file, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    super.file(job, data, file, encoding);
    if (Files.exists(file)) {
      this.addFile(job, file, EDI.EDI_XML);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void xml(final IOJob job, final Object data,
      final XMLBase xmlBase) throws Throwable {
    try (final XMLElement root = xmlBase.element()) {
      root.namespaceSetPrefix(EDI.NAMESPACE_URI, "e"); //$NON-NLS-1$
      root.name(EDI.NAMESPACE_URI, EDI.ELEMENT_EXPERIMENT_DATA);
      EDIOutput.__write(data, root, job, new HashSet<>());
    }
  }

  /**
   * Write some experiment data structure
   * 
   * @param data
   *          the data
   * @param root
   *          the root
   * @param job
   *          the job
   * @param described
   *          the set of described things
   */
  @SuppressWarnings("rawtypes")
  private static final void __write(final Object data,
      final XMLElement root, final IOJob job,
      final HashSet<Object> described) {
    final Logger logger;

    if (data == null) {
      throw new IllegalArgumentException(//
          "Data to store to EDI stream must not be null."); //$NON-NLS-1$
    }

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          "Begin writing instance of " + //$NON-NLS-1$
              TextUtils.className(data.getClass()));
    }
    write: {
      if (data instanceof ExperimentSet) {
        EDIOutput.__writeExperimentSet(((ExperimentSet) data), root, job);
        break write;
      }

      if (data instanceof DimensionSet) {
        EDIOutput.__writeDimensionSet(((DimensionSet) data), root, job);
        break write;
      }

      if (data instanceof InstanceSet) {
        EDIOutput.__writeInstanceSet(((InstanceSet) data), root, job);
        break write;
      }

      if (data instanceof Experiment) {
        EDIOutput.__writeExperiment(((Experiment) data), root, job,
            described);
        break write;
      }

      if (data instanceof Iterable) {
        for (final Object element : ((Iterable) data)) {
          EDIOutput.__write(element, root, job, described);
        }
        break write;
      }

      if (data instanceof Object[]) {
        for (final Object element : ((Object[]) data)) {
          EDIOutput.__write(element, root, job, described);
        }
        break write;
      }

      throw new IllegalArgumentException((((("Cannot deal with input " //$NON-NLS-1$
          + data) + ' ') + '(') + TextUtils.className(data.getClass())) + ')');
    }

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          "Finished writing instance of " + //$NON-NLS-1$
              TextUtils.className(data.getClass()));
    }
  }

  /**
   * Write an experiment set
   * 
   * @param es
   *          the experiment set
   * @param dest
   *          the destination
   * @param job
   *          the job
   */
  private static final void __writeExperimentSet(final ExperimentSet es,
      final XMLElement dest, final IOJob job) {
    final Logger logger;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Beginning to write experiment set " + es)); //$NON-NLS-1$
    }

    EDIOutput.__writeDimensionSet(es.getDimensions(), dest, job);
    EDIOutput.__writeInstanceSet(es.getInstances(), dest, job);
    EDIOutput.__writeExperiments(es.getData(), dest, job);

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Finished writing experiment set " + es)); //$NON-NLS-1$
    }
  }

  /**
   * Write an dimension set
   * 
   * @param ds
   *          the dimension set
   * @param dest
   *          the destination
   * @param job
   *          the job
   */
  private static final void __writeDimensionSet(final DimensionSet ds,
      final XMLElement dest, final IOJob job) {
    final Logger logger;
    EPrimitiveType t;
    NumberParser<?> p;
    String s;
    long l;
    double f;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Beginning to write dimension set " + ds)); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDI.NAMESPACE_URI, EDI.ELEMENT_DIMENSIONS);

      for (final Dimension d : ds.getData()) {
        try (final XMLElement dim = root.element()) {
          dim.name(EDI.NAMESPACE_URI, EDI.ELEMENT_DIMENSION);

          dim.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_NAME,
              d.getName());

          s = d.getDescription();
          if (s != null) {
            dim.attributeEncoded(EDI.NAMESPACE_URI,
                EDI.ATTRIBUTE_DESCRIPTION, s);
          }

          dim.attributeRaw(EDI.NAMESPACE_URI,
              EDI.ATTRIBUTE_DIMENSION_TYPE,
              EDI._getDimensionTypeName(d.getDimensionType()));

          dim.attributeRaw(EDI.NAMESPACE_URI,
              EDI.ATTRIBUTE_DIMENSION_DIRECTION,
              EDI._getDimensionDirectionName(d.getDirection()));

          t = d.getDataType();
          dim.attributeRaw(EDI.NAMESPACE_URI,
              EDI.ATTRIBUTE_DIMENSION_DATA_TYPE, EDI._getDataTypeName(t));

          p = d.getParser();
          switch (t) {
            case BYTE: {
              if ((l = p.getLowerBoundLong()) > Byte.MIN_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Byte.MAX_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case SHORT: {
              if ((l = p.getLowerBoundLong()) > Short.MIN_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Short.MAX_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case INT: {
              if ((l = p.getLowerBoundLong()) > Integer.MIN_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Integer.MAX_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case LONG: {
              if ((l = p.getLowerBoundLong()) > Long.MIN_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Long.MAX_VALUE) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case FLOAT:
            case DOUBLE: {
              if ((f = p.getLowerBoundDouble()) > Double.NEGATIVE_INFINITY) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_FLOAT_LOWER_BOUND,
                    XMLNumberAppender.INSTANCE.toString(f,
                        ETextCase.IN_SENTENCE));
              }
              if ((f = p.getUpperBoundDouble()) < Double.POSITIVE_INFINITY) {
                dim.attributeRaw(EDI.NAMESPACE_URI,
                    EDI.ATTRIBUTE_FLOAT_UPPER_BOUND,
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

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Finished to write dimension set " + ds)); //$NON-NLS-1$
    }
  }

  /**
   * Write an instance set
   * 
   * @param ds
   *          the instance set
   * @param dest
   *          the destination
   * @param job
   *          the job
   */
  private static final void __writeInstanceSet(final InstanceSet ds,
      final XMLElement dest, final IOJob job) {
    final HashSet<Object> hs;
    final ArraySetView<Dimension> dims;
    final Logger logger;
    String s;
    Feature fe;
    Number lower, upper;
    NumberParser<?> p;
    boolean x, y;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Beginning to write instance set " + ds)); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDI.NAMESPACE_URI, EDI.ELEMENT_INSTANCES);

      hs = new HashSet<>();
      dims = ds.getOwner().getDimensions().getData();

      for (final Instance i : ds.getData()) {

        try (XMLElement inst = root.element()) {
          inst.name(EDI.NAMESPACE_URI, EDI.ELEMENT_INSTANCE);

          inst.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_NAME,
              i.getName());

          s = i.getDescription();
          if (s != null) {
            inst.attributeEncoded(EDI.NAMESPACE_URI,
                EDI.ATTRIBUTE_DESCRIPTION, s);
          }

          for (final FeatureValue fv : i.getFeatureSetting()) {
            try (final XMLElement feat = inst.element()) {
              feat.name(EDI.NAMESPACE_URI, EDI.ELEMENT_FEATURE);

              fe = fv.getKey();

              feat.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_NAME,
                  fe.getName());
              s = fe.getDescription();
              if (s != null) {
                if (hs.add(fe)) {
                  feat.attributeEncoded(EDI.NAMESPACE_URI,
                      EDI.ATTRIBUTE_FEATURE_DESCRIPTION, s);
                }
              }

              feat.attributeEncoded(EDI.NAMESPACE_URI,
                  EDI.ATTRIBUTE_FEATURE_VALUE, fv.getName());
              s = fv.getDescription();
              if (s != null) {
                if (hs.add(fv)) {
                  feat.attributeEncoded(EDI.NAMESPACE_URI,
                      EDI.ATTRIBUTE_FEATURE_VALUE_DESCRIPTION, s);
                }
              }

            }
          }

          for (final Dimension d : dims) {
            lower = i.getLowerBound(d);
            upper = i.getUpperBound(d);
            p = d.getParser();

            if (d.getDataType().isInteger()) {
              x = (lower.longValue() > p.getLowerBoundLong());
              y = (upper.longValue() < p.getUpperBoundLong());
              if (x || y) {
                try (final XMLElement dim = inst.element()) {
                  dim.name(EDI.NAMESPACE_URI, EDI.ELEMENT_BOUNDS);

                  dim.attributeEncoded(EDI.NAMESPACE_URI,
                      EDI.ATTRIBUTE_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(EDI.NAMESPACE_URI,
                        EDI.ATTRIBUTE_INTEGER_LOWER_BOUND,
                        Long.toString(lower.longValue()));
                  }
                  if (y) {
                    dim.attributeRaw(EDI.NAMESPACE_URI,
                        EDI.ATTRIBUTE_INTEGER_UPPER_BOUND,
                        Long.toString(upper.longValue()));
                  }
                }

              }

            } else {

              x = (lower.doubleValue() > p.getLowerBoundDouble());
              y = (upper.doubleValue() < p.getUpperBoundDouble());
              if (x || y) {
                try (final XMLElement dim = inst.element()) {
                  dim.name(EDI.NAMESPACE_URI, EDI.ELEMENT_BOUNDS);

                  dim.attributeEncoded(EDI.NAMESPACE_URI,
                      EDI.ATTRIBUTE_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(
                        EDI.NAMESPACE_URI,
                        EDI.ATTRIBUTE_FLOAT_LOWER_BOUND,
                        XMLNumberAppender.INSTANCE.toString(
                            lower.doubleValue(), ETextCase.IN_SENTENCE));
                  }
                  if (y) {
                    dim.attributeRaw(
                        EDI.NAMESPACE_URI,
                        EDI.ATTRIBUTE_FLOAT_UPPER_BOUND,
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

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Finished to write instance set " + ds)); //$NON-NLS-1$
    }
  }

  /**
   * Write the experiments
   * 
   * @param e
   *          the experiment
   * @param dest
   *          the dest
   * @param job
   *          the job
   * @param described
   *          the described elements
   */
  private static final void __writeExperiment(final Experiment e,
      final XMLElement dest, final IOJob job,
      final HashSet<Object> described) {
    final boolean[] isInt;
    final int k;
    final ArraySetView<Dimension> ds;
    final Logger logger;
    String s;
    Parameter fe;
    int i;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Beginning to write experiment " + e)); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDI.NAMESPACE_URI, EDI.ELEMENT_EXPERIMENT);

      root.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_NAME,
          e.getName());

      s = e.getDescription();
      if (s != null) {
        root.attributeEncoded(EDI.NAMESPACE_URI,
            EDI.ATTRIBUTE_DESCRIPTION, s);
      }

      for (final ParameterValue fv : e.getParameterSetting()) {
        if (fv.isUnspecified()) {
          continue;
        }
        try (final XMLElement p = root.element()) {
          p.name(EDI.NAMESPACE_URI, EDI.ELEMENT_PARAMETER);
          fe = fv.getKey();

          p.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_NAME,
              fe.getName());
          s = fe.getDescription();
          if (s != null) {
            if (described.add(fe)) {
              p.attributeEncoded(EDI.NAMESPACE_URI,
                  EDI.ATTRIBUTE_PARAMETER_DESCRIPTION, s);
            }
          }

          p.attributeEncoded(EDI.NAMESPACE_URI,
              EDI.ATTRIBUTE_PARAMETER_VALUE, fv.getName());
          s = fv.getDescription();
          if (s != null) {
            if (described.add(fv)) {
              p.attributeEncoded(EDI.NAMESPACE_URI,
                  EDI.ATTRIBUTE_PARAMETER_VALUE_DESCRIPTION, s);
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
          irx.name(EDI.NAMESPACE_URI, EDI.ELEMENT_INSTANCE_RUNS);

          irx.attributeEncoded(EDI.NAMESPACE_URI, EDI.ATTRIBUTE_INSTANCE,
              ir.getInstance().getName());
          for (final Run r : ir.getData()) {
            try (final XMLElement rx = irx.element()) {
              rx.name(EDI.NAMESPACE_URI, EDI.ELEMENT_RUN);

              for (final DataPoint p : r.getData()) {
                try (final XMLElement px = rx.element()) {
                  px.name(EDI.NAMESPACE_URI, EDI.ELEMENT_POINT);
                  for (i = 0; i < k; i++) {
                    if (isInt[i]) {
                      try (final XMLElement ix = px.element()) {
                        ix.name(EDI.NAMESPACE_URI, EDI.ELEMENT_INT);
                        ix.textRaw().append(p.getLong(i));
                      }
                    } else {
                      try (final XMLElement ix = px.element()) {
                        ix.name(EDI.NAMESPACE_URI, EDI.ELEMENT_FLOAT);
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

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Finished writing experiment " + e)); //$NON-NLS-1$
    }
  }

  /**
   * Write the experiments
   * 
   * @param es
   *          the experiments
   * @param dest
   *          the dest
   * @param job
   *          the job
   */
  private static final void __writeExperiments(
      final ArraySetView<Experiment> es, final XMLElement dest,
      final IOJob job) {
    final HashSet<Object> described;
    final Logger logger;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Beginning to write experiments.")); //$NON-NLS-1$
    }

    try (final XMLElement root = dest.element()) {
      root.name(EDI.NAMESPACE_URI, EDI.ELEMENT_EXPERIMENTS);

      described = new HashSet<>();
      for (final Experiment e : es) {
        EDIOutput.__writeExperiment(e, root, job, described);
      }

    }

    if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
      logger.log(IOTool.FINE_LOG_LEVEL,//
          ("Finished to write experiments.")); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "EDI Experiment Data Output"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __EDIOutputLoader {
    /** create */
    static final EDIOutput INSTANCE = new EDIOutput();
  }
}
