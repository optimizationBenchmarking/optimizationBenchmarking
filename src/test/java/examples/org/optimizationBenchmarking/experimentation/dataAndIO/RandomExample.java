package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.DataPoint;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Dimension;
import org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.Instance;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceRunsContext;
import org.optimizationBenchmarking.experimentation.data.impl.ref.InstanceSet;
import org.optimizationBenchmarking.experimentation.data.impl.ref.RunContext;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.parsers.LooseByteParser;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.LooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.LooseIntParser;
import org.optimizationBenchmarking.utils.parsers.LooseLongParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.parsers.LooseShortParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** A class for creating experiment sets */
public class RandomExample extends ExperimentSetCreator {

  /** naming */
  static final String NAMING = "abcdefghijklmnopqrstuvwxyz"; //$NON-NLS-1$

  /** the parsers */
  private static final NumberParser<?>[] PARSERS = new NumberParser[] {
      LooseByteParser.INSTANCE, LooseShortParser.INSTANCE, LooseIntParser.INSTANCE,
      LooseLongParser.INSTANCE, LooseFloatParser.INSTANCE, LooseDoubleParser.INSTANCE };

  /** the name counter */
  final AtomicLong m_v;

  /**
   * create
   *
   * @param logger
   *          the logger, or {@code null} to use the global logger
   */
  public RandomExample(final Logger logger) {
    super(logger);
    this.m_v = new AtomicLong();
  }

  /** {@inheritDoc} */
  @Override
  protected ExperimentSet buildExperimentSet() {
    final ExperimentSet es;
    final Random r;

    r = new Random();
    this.m_v.set(r.nextLong());

    try (final ExperimentSetContext esb = new ExperimentSetContext(
        this.getLogger())) {

      this._createDimensionSet(esb, r);

      this._createInstanceSet(esb, esb.getDimensionSet(), r);

      this._createExperimentSet(esb, esb.getDimensionSet(),
          esb.getInstanceSet(), r);

      es = esb.create();
    }

    return es;
  }

  /**
   * create the dimension set
   *
   * @param dsc
   *          the context
   * @param r
   *          the randomizer
   */
  void _createDimensionSet(final ExperimentSetContext dsc, final Random r) {

    do {
      this._createDimension(dsc, r);
    } while (r.nextBoolean());
  }

  /**
   * create the dimension set
   *
   * @param dsc
   *          the context
   * @param r
   *          the randomizer
   */
  void _createDimension(final ExperimentSetContext dsc, final Random r) {

    try (DimensionContext dc = dsc.createDimension()) {

      dc.setName(RandomUtils.longToString(RandomExample.NAMING,
          this.m_v.incrementAndGet()));
      if (r.nextBoolean()) {
        dc.setDescription(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
      }
      dc.setParser(RandomExample.PARSERS[r
          .nextInt(RandomExample.PARSERS.length)]);
      dc.setType(EDimensionType.INSTANCES.get(r
          .nextInt(EDimensionType.INSTANCES.size())));
      dc.setDirection(EDimensionDirection.INSTANCES.get(r
          .nextInt(EDimensionDirection.INSTANCES.size())));

    }
  }

  /**
   * create a property map
   *
   * @param r
   *          the randomizer
   * @return the property map
   */
  private final Map.Entry<String, Integer>[] __createProperties(
      final Random r) {
    final HashMap<String, Integer> features;

    features = new HashMap<>();
    do {
      features.put(
          RandomUtils.longToString(RandomExample.NAMING,
              this.m_v.incrementAndGet()), Integer.valueOf(r.nextInt(11)));
    } while (r.nextInt(4) > 0);

    return features.entrySet().toArray(new Map.Entry[features.size()]);
  }

  /**
   * create a property map
   *
   * @param map
   *          the map
   * @param canOmit
   *          can we skip the value?
   * @param r
   *          the randomizer
   * @return the property map
   */
  private final HashMap<String, Object> __createValues(
      final Map.Entry<String, Integer>[] map, final boolean canOmit,
      final Random r) {
    final HashMap<String, Object> res;
    Map.Entry<String, Integer> entry;
    int idx, start;
    Object o;
    String s;

    res = new HashMap<>();
    start = r.nextInt(map.length);
    for (idx = map.length; (--idx) >= 0;) {
      if (canOmit && (idx > 0) && (r.nextInt(3) <= 0)) {
        continue;
      }
      entry = map[((idx + start) % map.length)];

      switch (entry.getValue().intValue()) {
        case 0: {
          o = Boolean.valueOf(r.nextBoolean());
          break;
        }
        case 1: {
          o = Byte.valueOf((byte) (r.nextInt(256) - 128));
          break;
        }
        case 2: {
          o = Short.valueOf((short) (r.nextInt(65536) - 32768));
          break;
        }
        case 3: {
          o = Integer.valueOf(r.nextInt());
          break;
        }
        case 4: {
          o = Long.valueOf(r.nextLong());
          break;
        }
        // case 5: {
        // o = Float.valueOf((float) (r.nextDouble()));
        // break;
        // }
        case 5: {
          o = Double.valueOf(r.nextDouble());
          break;
        }
        case 6: {
          o = Character.valueOf(RandomExample.NAMING.charAt(r
              .nextInt(RandomExample.NAMING.length())));
          break;
        }
        case 7: {
          do {
            o = s = RandomUtils.longToString(RandomExample.NAMING,
                r.nextLong());
          } while (("true".equalsIgnoreCase(s)) || //$NON-NLS-1$
              ("false".equalsIgnoreCase(s))); //$NON-NLS-1$
          break;
        }
        case 8: {
          o = Integer.valueOf(r.nextInt(4));
          break;
        }
        case 9: {
          o = Double.valueOf(((r.nextInt(4) + 0.3d) * 37d)
              / ((r.nextInt(3) - 0.7d) * 71d));
          break;
        }
        default: {
          do {
            o = s = RandomUtils.longToString(RandomExample.NAMING,
                r.nextInt(10));
          } while (("true".equalsIgnoreCase(s)) || //$NON-NLS-1$
              ("false".equalsIgnoreCase(s))); //$NON-NLS-1$
          break;
        }
      }

      res.put(entry.getKey(), o);
    }

    return res;
  }

  /**
   * create the instance set
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param isc
   *          the context
   * @param features
   *          the features
   */
  void _createInstance(final ExperimentSetContext isc,
      final DimensionSet dims,
      final Map.Entry<String, Integer>[] features, final Random r) {

    try (final InstanceContext ic = isc.createInstance()) {
      ic.setName(RandomUtils.longToString(RandomExample.NAMING,
          this.m_v.incrementAndGet()));
      if (r.nextBoolean()) {
        ic.setDescription(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
      }

      for (final Map.Entry<String, Object> e : this.__createValues(
          features, false, r).entrySet()) {
        ic.setFeatureValue(e.getKey(), e.getValue());
      }
    }
  }

  /**
   * create the instance set
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param isc
   *          the context
   */
  void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    final Map.Entry<String, Integer>[] features;

    features = this.__createProperties(r);

    do {
      this._createInstance(isc, dims, features, r);
    } while (r.nextBoolean());
  }

  /**
   * create a random data point
   *
   * @param r
   *          the randomizer
   * @param dimss
   *          the dimensions
   * @return the random data point
   */
  DataPoint _createDataPoint(final DimensionSet dimss, final Random r) {
    final ArraySetView<Dimension> dims;
    Class<?> clazz;
    String s;
    DataPoint p1;
    ArrayList<Number> lst;
    NumberParser<Number> np;
    byte tb;
    short ts;
    int ti;
    long tl, upperL, lowerL;
    double td, upperD, lowerD;
    float tf;
    int maxTrials;

    dims = dimss.getData();

    s = ""; //$NON-NLS-1$
    if (r.nextBoolean()) {
      lst = new ArrayList<>();
    } else {
      lst = null;
    }

    maxTrials = 1000;

    for (final Dimension d : dims) {
      if (lst == null) {
        s += ' ';
      }
      clazz = d.getDataType().getPrimitiveType();
      np = d.getParser();
      upperL = np.getUpperBoundLong();
      lowerL = np.getLowerBoundLong();
      upperD = np.getUpperBoundDouble();
      lowerD = np.getLowerBoundDouble();

      if (clazz == byte.class) {
        do {
          if ((--maxTrials) <= 0) {
            return null;
          }
          tb = ((byte) (lowerL + r.nextInt(((int) (upperL - lowerL)))));
        } while (((tb <= (Byte.MIN_VALUE + 2)))
            || ((tb >= (Byte.MAX_VALUE - 2)) || (tb < lowerL) || (tb > upperL)));
        if (lst != null) {
          lst.add(Byte.valueOf(tb));
        } else {
          s += tb;
        }
      } else {
        if (clazz == short.class) {

          do {
            if ((--maxTrials) <= 0) {
              return null;
            }
            ts = ((short) (lowerL + r.nextInt(((int) (upperL - lowerL)))));
          } while (((ts <= (Short.MIN_VALUE + 2)))
              || ((ts >= (Short.MAX_VALUE - 2))
                  || (ts < np.getLowerBoundLong()) || (ts > np
                  .getUpperBoundLong())));

          if (lst != null) {
            lst.add(Short.valueOf(ts));
          } else {
            s += ts;
          }
        } else {
          if (clazz == int.class) {
            do {
              if ((--maxTrials) <= 0) {
                return null;
              }
              ti = r.nextInt();
              if (ti < lowerL) {
                ti += ((1 + ((lowerL - ti) / (upperL - lowerL))) * (upperL - lowerL));
              } else {
                if (ti > upperL) {
                  ti -= ((1 + ((ti - upperL) / (upperL - lowerL))) * (upperL - lowerL));
                }
              }
            } while ((ti <= (Integer.MIN_VALUE + 2))
                || (ti >= (Integer.MAX_VALUE - 2)) || (ti < lowerL)
                || (ti > upperL));
            if (lst != null) {
              lst.add(Integer.valueOf(ti));
            } else {
              s += ti;
            }
          } else {
            if (clazz == long.class) {
              do {
                if ((--maxTrials) <= 0) {
                  return null;
                }
                tl = r.nextLong();
                if (tl < lowerL) {
                  tl += ((1 + ((lowerL - tl) / (upperL - lowerL))) * (upperL - lowerL));
                } else {
                  if (tl > upperL) {
                    tl -= ((1 + ((tl - upperL) / (upperL - lowerL))) * (upperL - lowerL));
                  }
                }
              } while ((tl <= (Long.MIN_VALUE + 2))
                  || (tl >= (Long.MAX_VALUE - 2)) || (tl < lowerL)
                  || (tl > upperL));
              if (lst != null) {
                lst.add(Long.valueOf(tl));
              } else {
                s += tl;
              }
            } else {
              if (clazz == float.class) {
                do {
                  if ((--maxTrials) <= 0) {
                    return null;
                  }
                  tf = Float.intBitsToFloat(r.nextInt());
                  if (tf < lowerD) {
                    tf += ((1d + Math.rint((lowerD - tf)
                        / (upperD - lowerD))) * (upperD - lowerD));
                  } else {
                    if (tf > upperD) {
                      tf -= ((1d + Math.rint((tf - upperD)
                          / (upperD - lowerD))) * (upperD - lowerD));
                    }
                  }
                } while (Float.isInfinite(tf) || Float.isNaN(tf)
                    || (tf <= (-0.5f * Float.MAX_VALUE))
                    || (tf >= (0.5f * Float.MAX_VALUE)) || (tf < lowerD)
                    || (tf > upperD));
                if (lst != null) {
                  lst.add(Float.valueOf(tf));
                } else {
                  s += tf;
                }
              } else {
                do {
                  if ((--maxTrials) <= 0) {
                    return null;
                  }
                  td = Double.longBitsToDouble(r.nextLong());
                  if (td < lowerD) {
                    td += ((1d + Math.rint((lowerD - td)
                        / (upperD - lowerD))) * (upperD - lowerD));
                  } else {
                    if (td > upperD) {
                      td -= ((1d + Math.rint((td - upperD)
                          / (upperD - lowerD))) * (upperD - lowerD));
                    }
                  }
                } while (Double.isInfinite(td) || Double.isNaN(td)
                    || (td <= (-0.5d * Double.MAX_VALUE))
                    || (td >= (0.5d * Double.MAX_VALUE)) || (td < lowerD)
                    || (td > upperD));
                if (lst != null) {
                  lst.add(Double.valueOf(td));
                } else {
                  s += td;
                }
              }
            }
          }
        }
      }
    }

    if (lst == null) {
      p1 = dimss.getDataFactory().parseString(s);
    } else {
      p1 = dimss.getDataFactory().parseNumbers(
          lst.toArray(new Number[lst.size()]));
    }

    if (p1 == null) {
      throw new IllegalStateException("Data point cannot be null."); //$NON-NLS-1$
    }
    return p1;
  }

  /**
   * create a random data point
   *
   * @param r
   *          the randomizer
   * @param dimss
   *          the dimensions
   * @param lower
   *          the lower bound
   * @param upper
   *          the upper bound
   * @return the random data point
   */
  DataPoint _createDataPointBetween(final DimensionSet dimss,
      final DataPoint lower, final DataPoint upper, final Random r) {
    final ArraySetView<Dimension> dims;
    Class<?> clazz;
    String s;
    DataPoint p1;
    ArrayList<Number> lst;
    NumberParser<Number> np;
    EPrimitiveType type;
    byte tb;
    short ts;
    int ti, idx, maxTrials;
    long tl, upperL, lowerL, rangeL, minL, maxL;
    double td, upperD, lowerD, rangeD, minD, maxD;
    boolean dir;
    float tf;

    dims = dimss.getData();

    s = ""; //$NON-NLS-1$
    if (r.nextBoolean()) {
      lst = new ArrayList<>();
    } else {
      lst = null;
    }

    maxTrials = 10000;

    for (final Dimension d : dims) {
      if (lst == null) {
        s += ' ';
      }

      type = d.getDataType();
      clazz = type.getPrimitiveType();
      np = d.getParser();
      idx = d.getIndex();
      if (type.isInteger()) {
        upperL = np.getUpperBoundLong();
        lowerL = np.getLowerBoundLong();
        minL = lower.getLong(idx);
        maxL = upper.getLong(idx);
        if (minL < maxL) {
          dir = true;
        } else {
          dir = false;
          tl = minL;
          minL = maxL;
          maxL = tl;
        }
        rangeL = (maxL - minL);
        if (rangeL <= 0L) {
          return null;
        }

        if (clazz == byte.class) {
          do {
            if ((--maxTrials) <= 0) {
              return null;
            }
            ti = (1 + r.nextInt((int) rangeL));
            tb = ((byte) (dir ? (minL + ti) : (maxL - ti)));
          } while (((tb <= (Byte.MIN_VALUE + 2)))
              || ((tb >= (Byte.MAX_VALUE - 2)) || (tb < lowerL) || (tb > upperL)));
          if (lst != null) {
            lst.add(Byte.valueOf(tb));
          } else {
            s += tb;
          }
        } else {
          if (clazz == short.class) {
            do {
              if ((--maxTrials) <= 0) {
                return null;
              }
              ti = (1 + r.nextInt((int) rangeL));
              ts = ((short) (dir ? (minL + ti) : (maxL - ti)));
            } while (((ts <= (Short.MIN_VALUE + 2)))
                || ((ts >= (Short.MAX_VALUE - 2))
                    || (ts < np.getLowerBoundLong()) || (ts > np
                    .getUpperBoundLong())));

            if (lst != null) {
              lst.add(Short.valueOf(ts));
            } else {
              s += ts;
            }
          } else {
            if (clazz == int.class) {
              do {
                if ((--maxTrials) <= 0) {
                  return null;
                }
                ti = ((rangeL < Integer.MAX_VALUE) ? (1 + r
                    .nextInt((int) rangeL)) : Math.abs(r.nextInt()));
                ti = ((int) (dir ? (minL + ti) : (maxL - ti)));
              } while ((ti <= (Integer.MIN_VALUE + 2))
                  || (ti >= (Integer.MAX_VALUE - 2)) || (ti < lowerL)
                  || (ti > upperL));
              if (lst != null) {
                lst.add(Integer.valueOf(ti));
              } else {
                s += ti;
              }
            } else {
              if (clazz == long.class) {
                do {
                  if ((--maxTrials) <= 0) {
                    return null;
                  }
                  tl = r.nextLong();
                  if (rangeL < Long.MAX_VALUE) {
                    tl = (Math.abs(tl % rangeL) + 1);
                  }
                  tl = (dir ? (minL + tl) : (maxL - tl));
                } while ((tl <= (Long.MIN_VALUE + 2))
                    || (tl >= (Long.MAX_VALUE - 2)) || (tl < lowerL)
                    || (tl > upperL));
                if (lst != null) {
                  lst.add(Long.valueOf(tl));
                } else {
                  s += tl;
                }
              }
            }
          }
        }

      } else {
        upperD = np.getUpperBoundDouble();
        lowerD = np.getLowerBoundDouble();

        minD = lower.getDouble(idx);
        maxD = upper.getDouble(idx);
        if (minD < maxD) {
          dir = true;
        } else {
          dir = false;
          td = minD;
          minD = maxD;
          maxD = td;
        }
        rangeD = (maxD - minD);
        if ((rangeD <= 0d) || (rangeD >= Double.MAX_VALUE)) {
          return null;
        }

        if (clazz == float.class) {
          do {
            if ((--maxTrials) <= 0) {
              return null;
            }
            tf = ((float) ((dir ? (minD + (r.nextDouble() * rangeD))
                : (maxD - (r.nextDouble() * rangeD)))));
          } while (Float.isInfinite(tf) || Float.isNaN(tf)
              || (tf <= (-0.5f * Float.MAX_VALUE))
              || (tf >= (0.5f * Float.MAX_VALUE)) || (tf < lowerD)
              || (tf > upperD));
          if (lst != null) {
            lst.add(Float.valueOf(tf));
          } else {
            s += tf;
          }
        } else {
          do {
            if ((--maxTrials) <= 0) {
              return null;
            }
            td = ((dir ? (minD + (r.nextDouble() * rangeD)) : (maxD - (r
                .nextDouble() * rangeD))));
          } while (Double.isInfinite(td) || Double.isNaN(td)
              || (td <= (-0.5d * Double.MAX_VALUE))
              || (td >= (0.5d * Double.MAX_VALUE)) || (td < lowerD)
              || (td > upperD));
          if (lst != null) {
            lst.add(Double.valueOf(td));
          } else {
            s += td;
          }
        }
      }
    }

    if (lst == null) {
      p1 = dimss.getDataFactory().parseString(s);
    } else {
      p1 = dimss.getDataFactory().parseNumbers(
          lst.toArray(new Number[lst.size()]));
    }

    if (p1 == null) {
      throw new IllegalStateException("Data point cannot be null."); //$NON-NLS-1$
    }
    return p1;
  }

  /**
   * create a random run
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param irc
   *          the instance run context
   */
  @SuppressWarnings("unused")
  void _createRun(final InstanceRunsContext irc, final DimensionSet dims,
      final Random r) {
    ArrayList<DataPoint> dps;
    DataPoint p, before, after;
    int j, k;

    dps = new ArrayList<>(30);
    dps.add(this._createDataPoint(dims, r));

    j = 0;

    inner: for (j = 1000; (--j) >= 0;) {
      k = dps.size();
      if ((k > 1) && (r.nextBoolean())) {
        k = r.nextInt(k - 1);
        p = this._createDataPointBetween(dims, dps.get(k), dps.get(k + 1),
            r);
      } else {
        p = null;
      }
      if (p == null) {
        p = this._createDataPoint(dims, r);
      }

      if (p == null) {
        continue;
      }

      before = null;
      for (k = dps.size(); (--k) >= 0;) {
        after = before;
        before = dps.get(k);

        try {
          if (after != null) {
            after.validateAfter(p);
          }
          p.validateAfter(before);
          dps.add((k + 1), p);
          if ((r.nextInt(4) <= 0) && (r.nextInt(dps.size()) > 0)) {
            break inner;
          }
          continue inner;
        } catch (final Throwable t) {
          //
        }
      }

      if (before != null) {
        try {
          before.validateAfter(p);
          dps.add(0, p);
          if ((r.nextInt(8) <= 0) && (r.nextInt(dps.size()) > 0)) {
            break inner;
          }
          continue inner;
        } catch (final Throwable t) {
          //
        }
      }
    }

    if (dps.size() > 0) {
      try (final RunContext rc = irc.createRun()) {
        for (final DataPoint ppp : dps) {
          if (r.nextBoolean()) {
            rc.addDataPoint(ppp);
          } else {
            rc.addDataPoint(ppp.toString());
          }
        }
      }
    }
  }

  /**
   * create a random run
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param irc
   *          the run context
   */
  void _createInstanceRunsInner(final InstanceRunsContext irc,
      final DimensionSet dims, final Random r) {
    do {
      this._createRun(irc, dims, r);
    } while (r.nextInt(5) > 0);
  }

  /**
   * create a random run
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param ec
   *          the experiment context
   * @param inst
   *          the instance
   */
  void _createInstanceRunsOuter(final ExperimentContext ec,
      final Instance inst, final DimensionSet dims, final Random r) {
    try (final InstanceRunsContext irc = ec.createInstanceRuns()) {

      if (r.nextBoolean()) {
        irc.setInstance(inst);
      } else {
        irc.setInstance(inst.getName());
      }

      this._createInstanceRunsInner(irc, dims, r);
    }
  }

  /**
   * create a random run
   *
   * @param r
   *          the randomizer
   * @param dims
   *          the dimensions
   * @param ec
   *          the experiment context
   * @param is
   *          the instances
   */
  void _createExperimentInner(final ExperimentContext ec,
      final Instance[] is, final DimensionSet dims, final Random r) {
    int i, s;

    s = r.nextInt(is.length);
    for (i = is.length; (--i) >= 0;) {
      if ((i > 0) && (r.nextInt(3) <= 0)) {
        continue;
      }

      this._createInstanceRunsOuter(ec, is[(i + s) % is.length], dims, r);
    }
  }

  /**
   * create the experiment set
   *
   * @param isc
   *          the context
   * @param dims
   *          the dimensions
   * @param is
   *          the instances
   * @param r
   *          the randomizer
   * @param params
   *          the parameters
   * @param configs
   *          the configurations
   */
  void _createExperimentOuter(final ExperimentSetContext isc,
      final DimensionSet dims, final Instance[] is,
      final Map.Entry<String, Integer>[] params,
      final HashSet<HashMap<String, Object>> configs, final Random r) {
    HashMap<String, Object> config;
    int z;

    z = 100;

    find: for (;;) {
      config = this.__createValues(params, true, r);
      synchronized (configs) {
        if (configs.add(config)) {
          break find;
        }
      }
      if ((--z) <= 0) {
        return;
      }
    }

    try (final ExperimentContext ec = isc.createExperiment()) {
      ec.setName(RandomUtils.longToString(RandomExample.NAMING,
          this.m_v.incrementAndGet()));
      if (r.nextBoolean()) {
        ec.setDescription(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
      }

      for (final Map.Entry<String, Object> e : config.entrySet()) {
        ec.setParameterValue(e.getKey(), e.getValue());
      }

      this._createExperimentInner(ec, is, dims, r);
    }
  }

  /**
   * create the experiment set
   *
   * @param isc
   *          the context
   * @param dims
   *          the dimensions
   * @param insts
   *          the instances
   * @param r
   *          the randomizer
   */
  void _createExperimentSet(final ExperimentSetContext isc,
      final DimensionSet dims, final InstanceSet insts, final Random r) {
    final Map.Entry<String, Integer>[] params;
    final Instance[] is;
    final HashSet<HashMap<String, Object>> configs;

    params = this.__createProperties(r);
    is = insts.getData().toArray(new Instance[insts.getData().size()]);

    configs = new HashSet<>();

    this._createExperimentSetInner(isc, dims, is, params, configs, r);
  }

  /**
   * create the experiment set
   *
   * @param isc
   *          the context
   * @param dims
   *          the dimensions
   * @param is
   *          the instances
   * @param params
   *          the parameters
   * @param configs
   *          the configurations
   * @param r
   *          the randomizer
   */
  void _createExperimentSetInner(final ExperimentSetContext isc,
      final DimensionSet dims, final Instance[] is,
      final Map.Entry<String, Integer>[] params,
      final HashSet<HashMap<String, Object>> configs, final Random r) {
    int z;

    z = 100;
    do {
      this._createExperimentOuter(isc, dims, is, params, configs, r);
    } while ((r.nextInt(4) > 0) && ((--z) >= 0));
  }

  /**
   * The main routine
   *
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    Configuration.setup(args);
    new RandomExample(null).run();
  }
}
