package org.optimizationBenchmarking.utils.comparison;

/**
 * A set of comparisons that return {@code true} if they match and
 * {@code false} if they don't. These comparisons also serve as result
 * constants of semantically more complex comparisons, such as those
 * provided by
 * {@link org.optimizationBenchmarking.utils.comparison.PreciseComparator#preciseCompare(Object, Object)
 * PreciseComparator}.
 */
public enum EComparison {

  /** less */
  LESS(-1) {

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
      try {
        return (((Comparable) a).compareTo(b) < 0);
      } catch (final ClassCastException t) {
        return false;
      }
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

  /** less or same */
  LESS_OR_SAME(-1) {

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
      return (a <= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a <= b);
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
        return false;
      }
      try {
        return (((Comparable) a).compareTo(b) < 0);
      } catch (final ClassCastException t) {
        return false;
      }
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

  /** less or equal */
  LESS_OR_EQUAL(-1) {

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
      try {
        return (((Comparable) a).compareTo(b) <= 0);
      } catch (final ClassCastException t) {
        return false;
      }
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

  /** are the values identical? */
  SAME(0) {

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
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a == b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (a == b);
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

  /** equal */
  EQUAL(0) {

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
  GREATER_OR_EQUAL(1) {

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
      try {
        return (((Comparable) a).compareTo(b) >= 0);
      } catch (final ClassCastException t) {
        return false;
      }
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

  /** greater or same */
  GREATER_OR_SAME(1) {

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
      return (a >= b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a >= b);
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
        return false;
      }
      try {
        return (((Comparable) a).compareTo(b) > 0);
      } catch (final ClassCastException t) {
        return false;
      }
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
  GREATER(1) {

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
      try {
        return (((Comparable) a).compareTo(b) > 0);
      } catch (final ClassCastException t) {
        return false;
      }
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

  /** not same */
  NOT_SAME(0) {

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
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final double a, final double b) {
      return (a != b);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean compare(final Object a, final Object b) {
      return (a != b);
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
  },

  /** not equal */
  NOT_EQUAL(0) {

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

  /** a comparison constant */
  final int m_cmp;

  /**
   * create
   * 
   * @param cmp
   *          the comparison result to be returned by
   *          {@link org.optimizationBenchmarking.utils.comparison.PreciseComparator#preciseCompare(Object, Object)}
   */
  private EComparison(final int cmp) {
    this.m_cmp = cmp;
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
   * Get the equivalent result of a {@link java.util.Comparator}, i.e.,
   * either {@code -1}, {@code 0}, or {@code 1}.
   * 
   * @return the equivalent result of a {@link java.util.Comparator}.
   */
  public final int getComparisonResult() {
    return this.m_cmp;
  }

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
        (" (" + Double.doubleToRawLongBits(b))))))) + ')');//$NON-NLS-1$
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
        (" (" + Float.floatToRawIntBits(b))))))) + ')');//$NON-NLS-1$
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
   * Compare whether one comparable object is equal another one.
   * 
   * @param a
   *          the first object
   * @param b
   *          the second object
   * @return the comparison result
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final int compare(final Comparable a, final Comparable b) {
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

  /** the difference multiplier as integer */
  static final int DIFFERENCE_MULTIPLIER_INT = 10;
  /** the difference multiplier as long */
  static final long DIFFERENCE_MULTIPLIER_LONG = EComparison.DIFFERENCE_MULTIPLIER_INT;
  /** the maximum value we can multiply */
  static final long MAX_MULTIPLY = (Long.MAX_VALUE / EComparison.DIFFERENCE_MULTIPLIER_LONG);
}
