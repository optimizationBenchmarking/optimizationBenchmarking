package org.optimizationBenchmarking.utils.comparison;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A set of comparisons that return {@code true} if they match and
 * {@code false} if they don't.
 */
public enum EComparison {

  /** less */
  LESS("less than") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a < b);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final boolean compare(final Object a, final Object b) {
      if (a == b) {
        return false;
      }
      if (a == null) {
        return false;
      }
      if (b == null) {
        return true;
      }
      if (a.equals(b)) {
        return false;
      }

      if (a instanceof Comparable) {
        try {
          return (((Comparable) a).compareTo(b) < 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      if (b instanceof Comparable) {
        try {
          return (((Comparable) b).compareTo(a) > 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return ((!a) && b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) < 0);
    }

  },

  /** less or equal */
  LESS_OR_EQUAL("less than or equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (EComparison.compareFloats(a, b) <= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (EComparison.compareDoubles(a, b) <= 0);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final boolean compare(final Object a, final Object b) {
      if (a == b) {
        return true;
      }
      if (a == null) {
        return false;
      }
      if (b == null) {
        return true;
      }
      if (a.equals(b)) {
        return true;
      }

      if (a instanceof Comparable) {
        try {
          return (((Comparable) a).compareTo(b) <= 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      if (b instanceof Comparable) {
        try {
          return (((Comparable) b).compareTo(a) >= 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return ((!a) || b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) <= 0);
    }

  },

  /** equal */
  EQUAL("equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (EComparison.compareFloats(a, b) == 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (EComparison.compareDoubles(a, b) == 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return EComparison.equals(a, b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) == 0);
    }
  },

  /** greater or equal */
  GREATER_OR_EQUAL("greater than or equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (EComparison.compareFloats(a, b) >= 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (EComparison.compareDoubles(a, b) >= 0);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final boolean compare(final Object a, final Object b) {
      if (a == b) {
        return true;
      }
      if (a == null) {
        return true;
      }
      if (b == null) {
        return false;
      }
      if (a.equals(b)) {
        return true;
      }

      if (a instanceof Comparable) {
        try {
          return (((Comparable) a).compareTo(b) >= 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      if (b instanceof Comparable) {
        try {
          return (((Comparable) b).compareTo(a) <= 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a || (!b));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) >= 0);
    }

  },

  /** greater */
  GREATER("greater than") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a > b);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final boolean compare(final Object a, final Object b) {
      if (a == b) {
        return false;
      }
      if (a == null) {
        return false;
      }
      if (b == null) {
        return true;
      }
      if (a.equals(b)) {
        return false;
      }

      if (a instanceof Comparable) {
        try {
          return (((Comparable) a).compareTo(b) > 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      if (b instanceof Comparable) {
        try {
          return (((Comparable) b).compareTo(a) < 0);
        } catch (final Throwable error) {
          // ignore
        }
      }

      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a && (!b));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) > 0);
    }
  },

  /** not equal */
  NOT_EQUAL("not equal to") { //$NON-NLS-1$

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final byte a, final byte b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final short a, final short b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final int a, final int b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final long a, final long b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final float a, final float b) {
      return (EComparison.compareFloats(a, b) != 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (EComparison.compareDoubles(a, b) != 0);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      if (a == b) {
        return false;
      }
      if (a == null) {
        return true;
      }
      if (b == null) {
        return true;
      }
      return (!(a.equals(b)));
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final boolean a, final boolean b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final char a, final char b) {
      return (Character.compare(a, b) != 0);
    }

  };

  /** an array set view of the comparison instances */
  public static final ArraySetView<EComparison> INSTANCES = new ArraySetView<>(
      EComparison.values());

  /** the name */
  private final String m_name;

  /**
   * create
   *
   * @param name
   *          the name
   */
  private EComparison(final String name) {
    this.m_name = name;
  }

  /**
   * The {@link #toString()} method returns a string which can replace
   * {@code [relation]} in the following sentence {@code "x [relation] y}
   * in the case that {@link #compare(Object, Object) compare(X, Y)}
   * returns {@code true}.
   *
   * @return a string properly representing the comparison relationship
   */
  @Override
  public final String toString() {
    return this.m_name;
  }

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final byte a, final byte b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final short a, final short b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final int a, final int b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final long a, final long b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final float a, final float b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final double a, final double b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final Object a, final Object b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first boolean
   * @param b
   *          the second boolean
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final boolean a, final boolean b);

  /**
   * compare {@code a} and {@code b}.
   *
   * @param a
   *          the first character
   * @param b
   *          the second character
   * @return {@code true} if the comparison is met, {@code false} otherwise
   */
  public abstract boolean compare(final char a, final char b);

  /**
   * Compare one {@code double} to another one. This method sets
   * {@code 0d == -0d}, although
   * <code>{@link java.lang.Double#doubleToLongBits(double) Double.doubleToLongBits(0d)==0}</code>
   * while
   * <code>{@link java.lang.Double#doubleToLongBits(double) Double.doubleToLongBits(-0d)==-9223372036854775808}</code>
   * . <code>equals(0d, -0d) == true</code>. {@link java.lang.Double#NaN}
   * values are treated as equal to each other.
   *
   * @param a
   *          the first {@code double}
   * @param b
   *          the second {@code double}
   * @return {@code -1} if {@code a<b}, {@code 0} if {@code a equals b},
   *         {@code 1} if {@code a>b}
   */
  public static final int compareDoubles(final double a, final double b) {
    final boolean aNaN, bNaN;

    if (a < b) {
      return (-1);
    }
    if (a > b) {
      return 1;
    }
    if (a == b) {
      return 0;
    }

    aNaN = (a != a);
    bNaN = (b != b);
    if (aNaN && bNaN) {
      return 0;
    }
    if (aNaN) {
      return 1;
    }
    if (bNaN) {
      return (-1);
    }

    throw new IllegalStateException(//
        ("Impossible error occured: compare " //$NON-NLS-1$
        + (a + " (" + (Double.doubleToRawLongBits(a) + //$NON-NLS-1$
        (") with " + (b + //$NON-NLS-1$
        (" (" + Double.doubleToRawLongBits(b))))))) + ')'); //$NON-NLS-1$
  }

  /**
   * Compare one {@code float} to another one. This method sets
   * {@code 0d == -0d}, although
   * <code>{@link java.lang.Float#floatToIntBits(float) Float.floatToLongBits(0d)==0}</code>
   * while
   * <code>{@link java.lang.Float#floatToIntBits(float) Float.floatToLongBits(-0d)==-9223372036854775808}</code>
   * . <code>equals(0d, -0d) == true</code>. {@link java.lang.Float#NaN}
   * values are treated as equal to each other.
   *
   * @param a
   *          the first {@code float}
   * @param b
   *          the second {@code float}
   * @return {@code -1} if {@code a<b}, {@code 0} if {@code a equals b},
   *         {@code 1} if {@code a>b}
   */
  public static final int compareFloats(final float a, final float b) {
    final boolean aNaN, bNaN;

    if (a < b) {
      return (-1);
    }
    if (a > b) {
      return 1;
    }
    if (a == b) {
      return 0;
    }

    aNaN = (a != a);
    bNaN = (b != b);
    if (aNaN && bNaN) {
      return 0;
    }
    if (aNaN) {
      return 1;
    }
    if (bNaN) {
      return (-1);
    }

    throw new IllegalStateException(//
        ("Impossible error occured: compare " //$NON-NLS-1$
        + (a + " (" + (Float.floatToRawIntBits(a) + //$NON-NLS-1$
        (") with " + (b + //$NON-NLS-1$
        (" (" + Float.floatToRawIntBits(b))))))) + ')'); //$NON-NLS-1$
  }

  /**
   * Does one object equal another one.
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return {@code true} if they are equal, {@code false} otherwise
   */
  public static final boolean equals(final Object a, final Object b) {
    if (a == b) {
      return true;
    }
    if (a == null) {
      return false;
    }
    if (b == null) {
      return false;
    }
    return a.equals(b);
  }

  /**
   * Compare whether two comparable objects
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return the comparison result
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final int compareObjects(final Comparable a,
      final Comparable b) {
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }
    return a.compareTo(b);
  }

  /**
   * Compare two numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @return the result
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final int compareNumbers(final Number a, final Number b) {

    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }

    if ((a.getClass() == b.getClass()) && (a instanceof Comparable)) {
      return ((Comparable) a).compareTo(b);
    }

    if (((a instanceof Byte) || //
        (a instanceof Short) || //
        (a instanceof Integer) || //
        (a instanceof Long))//
        && ((b instanceof Byte) || //
            (b instanceof Short) || //
            (b instanceof Integer) || //
        (b instanceof Long))) {
      return Long.compare(a.longValue(), b.longValue());
    }

    return EComparison.compareDoubles(a.doubleValue(), b.doubleValue());
  }

  /**
   * Compare one object to another one
   *
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return the comparison result
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final int compareObjects(final Object a, final Object b) {
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return (-1);
    }

    if (a instanceof Comparable) {
      try {
        return ((Comparable) a).compareTo(b);
      } catch (final Throwable error) {
        // ignore
      }
    }

    if (b instanceof Comparable) {
      try {
        return (-(((Comparable) b).compareTo(a)));
      } catch (final Throwable error) {
        // ignore
      }
    }

    if ((a instanceof Number) && (b instanceof Number)) {
      return EComparison.compareNumbers(((Number) a), ((Number) b));
    }

    return 0;
  }
}
