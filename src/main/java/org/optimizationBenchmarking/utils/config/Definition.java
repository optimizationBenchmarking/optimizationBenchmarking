package org.optimizationBenchmarking.utils.config;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;

/**
 * A configuration definition specifies which parameters an implementation
 * of {@link org.optimizationBenchmarking.utils.config.IConfigurable} can
 * understand.
 */
public final class Definition extends _DefSet<Parameter<?>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create
   *
   * @param data
   *          the data
   * @param allowsMore
   *          are more parameters allowed than defined here?
   */
  Definition(final Parameter<?>[] data, final boolean allowsMore) {
    super(data, allowsMore);
  }

  /**
   * Create the configuration dump
   *
   * @param config
   *          the configuration, or {@code null} if none is provided
   * @return the dump
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public final Dump dump(final Configuration config) {
    final boolean allowsMore;
    ImmutableAssociation<Parameter<?>, Object>[] known, copy;
    StringMapCI<Object> map;
    Parameter<?> param;
    int paramSize, mapSize, index, origLen;

    paramSize = this.m_data.length;
    allowsMore = this.m_allowsMore;

    plainDump: {
      goodDump: {
        // Check whether the dump will be empty anyway
        if ((paramSize > 0) || (allowsMore)) {

          // Is the configuration null?
          if (config != null) {

            synchronized (config.m_data) {
              map = config.m_data;
              mapSize = map.size();
              if (mapSize <= 0) {// Or empty?
                break goodDump;
              }
              map = ((StringMapCI) (map.clone()));
            }

            // If we get here, the configuration has at least one parameter
            // and the definition either has one parameter too or allows
            // additional parameters.
            known = new ImmutableAssociation[paramSize
                + (allowsMore ? mapSize : 0)];

            // First, dump all the specified parameters.
            for (index = 0; index < paramSize; index++) {
              param = this.m_data[index];
              known[index] = new ImmutableAssociation(param,//
                  param._formatForDump(map.remove(param.m_name)));
            }

            Arrays.sort(known, 0, index, new __Comp2());

            if (allowsMore) {
              // If there are more (unspecified) parameters, dump them as
              // well
              origLen = index;
              for (final Map.Entry<String, Object> entry : map) {
                known[index++] = new ImmutableAssociation(//
                    new UnspecifiedParameter(entry.getKey()),//
                    String.valueOf(entry.getValue()));
              }

              if (index > (origLen + 1)) {
                Arrays.sort(known, origLen, index, new __Comp());
              }
            }

            // Let the list have the right size.
            if (index < known.length) {
              copy = known;
              known = new ImmutableAssociation[index];
              System.arraycopy(copy, 0, known, 0, index);
              copy = null;
            }
            break plainDump;
          }
        }
      }

      // The configuration was null or empty or this definition is empty
      // and does not allow additional parameters.
      if (paramSize <= 0) {// The latter case given above.
        return new Dump(new ImmutableAssociation[0], this.m_allowsMore);
      }

      // Fill the array with associations of parameters to null.
      known = new ImmutableAssociation[paramSize];
      for (index = paramSize; (--index) >= 0;) {
        known[index] = new ImmutableAssociation(this.m_data[index], null);
      }
    }

    return new Dump(known, allowsMore);
  }

  /** the internal comparator */
  private static final class __Comp implements
      Comparator<ImmutableAssociation<Parameter<?>, Object>> {
    /** create */
    __Comp() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(
        final ImmutableAssociation<Parameter<?>, Object> o1,
        final ImmutableAssociation<Parameter<?>, Object> o2) {
      return String.CASE_INSENSITIVE_ORDER.compare(o1.getKey().m_name,
          o2.getKey().m_name);
    }
  }

  /** the second internal comparator */
  private static final class __Comp2 implements
      Comparator<ImmutableAssociation<Parameter<?>, Object>> {
    /** create */
    __Comp2() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(
        final ImmutableAssociation<Parameter<?>, Object> o1,
        final ImmutableAssociation<Parameter<?>, Object> o2) {
      Object a, b;
      a = o1.getValue();
      b = o2.getValue();
      if (a == null) {
        if (b == null) {
          return 0;
        }
        return 1;
      }
      if (b == null) {
        return (-1);
      }
      return 0;
    }
  }
}
