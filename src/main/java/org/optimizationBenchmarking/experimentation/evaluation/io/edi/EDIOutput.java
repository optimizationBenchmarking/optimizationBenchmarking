package org.optimizationBenchmarking.experimentation.evaluation.io.edi;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.data.DataPoint;
import org.optimizationBenchmarking.experimentation.evaluation.data.Dimension;
import org.optimizationBenchmarking.experimentation.evaluation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.Feature;
import org.optimizationBenchmarking.experimentation.evaluation.data.FeatureValue;
import org.optimizationBenchmarking.experimentation.evaluation.data.Instance;
import org.optimizationBenchmarking.experimentation.evaluation.data.InstanceRuns;
import org.optimizationBenchmarking.experimentation.evaluation.data.InstanceSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.Parameter;
import org.optimizationBenchmarking.experimentation.evaluation.data.ParameterValue;
import org.optimizationBenchmarking.experimentation.evaluation.data.Run;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.structured.XMLOutputDriver;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.XMLNumberAppender;

/**
 * A driver for Experiment Data Interchange (EDI) output. EDI is our
 * default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.evaluation.data
 * experiment data structures}.
 */
public final class EDIOutput extends XMLOutputDriver<Object> {

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
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void doStoreXML(final Object data, final XMLBase dest,
      final Logger logger) {
    final ArraySetView v;
    try (final XMLElement root = dest.element()) {
      root.namespaceSetPrefix(_EDIConstants.NAMESPACE_URI, "e"); //$NON-NLS-1$
      root.name(_EDIConstants.NAMESPACE_URI,
          _EDIConstants.E_EXPERIMENT_DATA);
      write: {
        if (data instanceof ExperimentSet) {
          EDIOutput.__writeExperimentSet(((ExperimentSet) data), root,
              logger);
          break write;
        }
        if (data instanceof DimensionSet) {
          EDIOutput.__writeDimensionSet(((DimensionSet) data), root,
              logger);
          break write;
        }
        if (data instanceof InstanceSet) {
          EDIOutput.__writeInstanceSet(((InstanceSet) data), root, logger);
          break write;
        }
        if (data instanceof Experiment) {
          EDIOutput.__writeExperiment(((Experiment) data), root, logger,
              new HashSet<>());
          break write;
        }
        if (data instanceof ArraySetView) {
          v = ((ArraySetView) data);
          if (v.size() > 0) {
            if (v.get(0) instanceof Experiment) {
              EDIOutput.__writeExperiments(v, root, logger);
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

    EDIOutput.__writeDimensionSet(es.getDimensions(), dest, logger);
    EDIOutput.__writeInstanceSet(es.getInstances(), dest, logger);
    EDIOutput.__writeExperiments(es.getData(), dest, logger);

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
      root.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_DIMENSIONS);

      for (final Dimension d : ds.getData()) {
        try (final XMLElement dim = root.element()) {
          dim.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_DIMENSION);

          dim.attributeEncoded(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_NAME, d.getName());

          s = d.getDescription();
          if (s != null) {
            dim.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                _EDIConstants.A_DESCRIPTION, s);
          }

          dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_DIMENSION_TYPE,
              _EDIConstants.AV_DIMENSION_TYPE[d.getDimensionType()
                  .ordinal()]);

          dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_DIMENSION_DIRECTION,
              _EDIConstants.AV_DIMENSION_DIRECTION[d.getDirection()
                  .ordinal()]);

          t = d.getDataType();
          dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_DIMENSION_DATA_TYPE,
              _EDIConstants.AV_DIMENSION_DATA_TYPE[t.ordinal()]);

          p = d.getParser();
          switch (t) {
            case BYTE: {
              if ((l = p.getLowerBoundLong()) > Byte.MIN_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Byte.MAX_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case SHORT: {
              if ((l = p.getLowerBoundLong()) > Short.MIN_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Short.MAX_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case INT: {
              if ((l = p.getLowerBoundLong()) > Integer.MIN_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Integer.MAX_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case LONG: {
              if ((l = p.getLowerBoundLong()) > Long.MIN_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_LOWER_BOUND, Long.toString(l));
              }
              if ((l = p.getUpperBoundLong()) < Long.MAX_VALUE) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_INTEGER_UPPER_BOUND, Long.toString(l));
              }
              break;
            }
            case FLOAT:
            case DOUBLE: {
              if ((f = p.getLowerBoundDouble()) > Double.NEGATIVE_INFINITY) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_FLOAT_LOWER_BOUND,
                    XMLNumberAppender.INSTANCE.toString(f,
                        ETextCase.IN_SENTENCE));
              }
              if ((f = p.getUpperBoundDouble()) < Double.POSITIVE_INFINITY) {
                dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                    _EDIConstants.A_FLOAT_UPPER_BOUND,
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
      root.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_INSTANCES);

      hs = new HashSet<>();
      dims = ds.getOwner().getDimensions().getData();

      for (final Instance i : ds.getData()) {

        try (XMLElement inst = root.element()) {
          inst.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_INSTANCE);

          inst.attributeEncoded(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_NAME, i.getName());

          s = i.getDescription();
          if (s != null) {
            inst.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                _EDIConstants.A_DESCRIPTION, s);
          }

          for (final FeatureValue fv : i.features()) {
            try (final XMLElement feat = inst.element()) {
              feat.name(_EDIConstants.NAMESPACE_URI,
                  _EDIConstants.E_FEATURE);

              fe = fv.getKey();

              feat.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                  _EDIConstants.A_NAME, fe.getName());
              s = fe.getDescription();
              if (s != null) {
                if (hs.add(fe)) {
                  feat.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.A_FEATURE_DESCRIPTION, s);
                }
              }

              feat.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                  _EDIConstants.A_FEATURE_VALUE, fv.getName());
              s = fv.getDescription();
              if (s != null) {
                if (hs.add(fv)) {
                  feat.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.A_FEATURE_VALUE_DESCRIPTION, s);
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
                  dim.name(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.E_BOUNDS);

                  dim.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.A_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                        _EDIConstants.A_INTEGER_LOWER_BOUND,
                        Long.toString(lower.longValue()));
                  }
                  if (y) {
                    dim.attributeRaw(_EDIConstants.NAMESPACE_URI,
                        _EDIConstants.A_INTEGER_UPPER_BOUND,
                        Long.toString(upper.longValue()));
                  }
                }

              }

            } else {

              x = (lower.doubleValue() > p.getLowerBoundDouble());
              y = (upper.doubleValue() < p.getUpperBoundDouble());
              if (x || y) {
                try (final XMLElement dim = inst.element()) {
                  dim.name(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.E_BOUNDS);

                  dim.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.A_DIMENSION, d.getName());
                  if (x) {
                    dim.attributeRaw(
                        _EDIConstants.NAMESPACE_URI,
                        _EDIConstants.A_FLOAT_LOWER_BOUND,
                        XMLNumberAppender.INSTANCE.toString(
                            lower.doubleValue(), ETextCase.IN_SENTENCE));
                  }
                  if (y) {
                    dim.attributeRaw(
                        _EDIConstants.NAMESPACE_URI,
                        _EDIConstants.A_FLOAT_UPPER_BOUND,
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
      root.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_EXPERIMENT);

      root.attributeEncoded(_EDIConstants.NAMESPACE_URI,
          _EDIConstants.A_NAME, e.getName());

      s = e.getDescription();
      if (s != null) {
        root.attributeEncoded(_EDIConstants.NAMESPACE_URI,
            _EDIConstants.A_DESCRIPTION, s);
      }

      for (final ParameterValue fv : e.parameters()) {
        if (fv.isUnspecified()) {
          continue;
        }
        try (final XMLElement p = root.element()) {
          p.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_PARAMETER);
          fe = fv.getKey();

          p.attributeEncoded(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_NAME, fe.getName());
          s = fe.getDescription();
          if (s != null) {
            if (described.add(fe)) {
              p.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                  _EDIConstants.A_PARAMETER_DESCRIPTION, s);
            }
          }

          p.attributeEncoded(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_PARAMETER_VALUE, fv.getName());
          s = fv.getDescription();
          if (s != null) {
            if (described.add(fv)) {
              p.attributeEncoded(_EDIConstants.NAMESPACE_URI,
                  _EDIConstants.A_PARAMETER_VALUE_DESCRIPTION, s);
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
          irx.name(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.E_INSTANCE_RUNS);

          irx.attributeEncoded(_EDIConstants.NAMESPACE_URI,
              _EDIConstants.A_INSTANCE, ir.getInstance().getName());
          for (final Run r : ir.getData()) {
            try (final XMLElement rx = irx.element()) {
              rx.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_RUN);

              for (final DataPoint p : r.getData()) {
                try (final XMLElement px = rx.element()) {
                  px.name(_EDIConstants.NAMESPACE_URI,
                      _EDIConstants.E_POINT);
                  for (i = 0; i < k; i++) {
                    if (isInt[i]) {
                      try (final XMLElement ix = px.element()) {
                        ix.name(_EDIConstants.NAMESPACE_URI,
                            _EDIConstants.E_INT);
                        ix.textRaw().append(p.getLong(i));
                      }
                    } else {
                      try (final XMLElement ix = px.element()) {
                        ix.name(_EDIConstants.NAMESPACE_URI,
                            _EDIConstants.E_FLOAT);
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
      root.name(_EDIConstants.NAMESPACE_URI, _EDIConstants.E_EXPERIMENTS);

      described = new HashSet<>();
      for (final Experiment e : es) {
        EDIOutput.__writeExperiment(e, root, logger, described);
      }

    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished to write experiments."); //$NON-NLS-1$
    }
  }

  /** the loader */
  private static final class __EDIOutputLoader {
    /** create */
    static final EDIOutput INSTANCE = new EDIOutput();
  }
}
