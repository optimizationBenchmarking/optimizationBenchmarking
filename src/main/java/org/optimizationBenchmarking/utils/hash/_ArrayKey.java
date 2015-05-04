package org.optimizationBenchmarking.utils.hash;

import java.util.Arrays;

/**
 * This object can be used to wrap arrays in order to make them properly
 * support {@link java.lang.Object#hashCode()} and
 * {@link java.lang.Object#equals(Object)}.
 */
final class _ArrayKey {

  /** the array */
  final Object m_array;
  /** the type */
  private final int m_type;
  /** the hash code */
  private final int m_hc;

  /**
   * create
   *
   * @param o
   *          the object
   */
  _ArrayKey(final Object o) {
    super();

    final int h;

    this.m_array = o;
    if (o instanceof boolean[]) {
      this.m_type = 0;
      h = Arrays.hashCode((boolean[]) o);
    } else {
      if (o instanceof byte[]) {
        this.m_type = 1;
        h = Arrays.hashCode((byte[]) o);
      } else {
        if (o instanceof short[]) {
          this.m_type = 2;
          h = Arrays.hashCode((short[]) o);
        } else {
          if (o instanceof int[]) {
            this.m_type = 3;
            h = Arrays.hashCode((int[]) o);
          } else {
            if (o instanceof long[]) {
              this.m_type = 4;
              h = Arrays.hashCode((long[]) o);
            } else {
              if (o instanceof char[]) {
                this.m_type = 5;
                h = Arrays.hashCode((char[]) o);
              } else {
                if (o instanceof float[]) {
                  this.m_type = 6;
                  h = Arrays.hashCode((float[]) o);
                } else {
                  if (o instanceof double[]) {
                    this.m_type = 7;
                    h = Arrays.hashCode((double[]) o);
                  } else {
                    this.m_type = 8;
                    h = HashUtils.deepHashCode(o);
                  }
                }
              }
            }
          }
        }
      }
    }

    this.m_hc = (((56598313 + this.m_type) * 2796203) ^ h);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    _ArrayKey h;
    if (o == this) {
      return true;
    }
    if (o instanceof _ArrayKey) {
      h = ((_ArrayKey) o);
      if ((h.m_type == this.m_type) && (this.m_hc == h.m_hc)) {
        if (h.m_array == this.m_array) {
          return true;
        }

        switch (this.m_type) {
          case 0: {
            return Arrays.equals(((boolean[]) (this.m_array)),
                ((boolean[]) (h.m_array)));
          }
          case 1: {
            return Arrays.equals(((byte[]) (this.m_array)),
                ((byte[]) (h.m_array)));
          }
          case 2: {
            return Arrays.equals(((short[]) (this.m_array)),
                ((short[]) (h.m_array)));
          }
          case 3: {
            return Arrays.equals(((int[]) (this.m_array)),
                ((int[]) (h.m_array)));
          }
          case 4: {
            return Arrays.equals(((long[]) (this.m_array)),
                ((long[]) (h.m_array)));
          }
          case 5: {
            return Arrays.equals(((char[]) (this.m_array)),
                ((char[]) (h.m_array)));
          }
          case 6: {
            return Arrays.equals(((float[]) (this.m_array)),
                ((float[]) (h.m_array)));
          }
          case 7: {
            return Arrays.equals(((double[]) (this.m_array)),
                ((double[]) (h.m_array)));
          }
          default: {
            return Arrays.deepEquals(((Object[]) (this.m_array)),
                ((Object[]) (h.m_array)));
          }
        }
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hc;
  }
}
