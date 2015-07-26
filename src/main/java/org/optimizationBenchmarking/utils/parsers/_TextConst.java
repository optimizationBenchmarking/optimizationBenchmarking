package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** an internal constant class for number resolution */
final class _TextConst implements Comparable<_TextConst> {

  /** true */
  static final String TRUE = Boolean.TRUE.toString();
  /** true */
  static final String FALSE = Boolean.FALSE.toString();

  /** does this constant have a double? */
  private static final int STATE_DOUBLE = 1;

  /** does this constant have an integer value ? */
  private static final int STATE_INT = (_TextConst.STATE_DOUBLE << 1);

  /** does this constant have a boolean? */
  private static final int STATE_BOOLEAN = (_TextConst.STATE_INT << 1);

  /** the constants */
  private static final _TextConst[] CONSTS = new _TextConst[] {

      new _TextConst(
          TextUtils.toLowerCase(Double.toString(Double.NEGATIVE_INFINITY)),//
          Double.NEGATIVE_INFINITY, Long.MIN_VALUE, Integer.MIN_VALUE,
          false),//

      new _TextConst("negativeinfinity",//$NON-NLS-1$,
          Double.NEGATIVE_INFINITY),//

      new _TextConst(DoubleParser.NEGATIVE_INFINITY,
          Double.NEGATIVE_INFINITY),//

      new _TextConst(TextUtils.toLowerCase(Double
          .toString(Double.POSITIVE_INFINITY)),//
          Double.POSITIVE_INFINITY),//

      new _TextConst("positiveinfinity",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("+inf",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("inf",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("-inf",//$NON-NLS-1$
          Double.NEGATIVE_INFINITY),//

      new _TextConst("positive infinity",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("infty",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("infty",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst(DoubleParser.POSITIVE_INFINITY,
          Double.POSITIVE_INFINITY),//

      new _TextConst("\u221e",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst("unendlich",//$NON-NLS-1$
          Double.POSITIVE_INFINITY),//

      new _TextConst(TextUtils.toLowerCase(Double.toString(Double.NaN)),//
          Double.NaN),//

      new _TextConst("nan!",//$NON-NLS-1$
          Double.NaN),//

      new _TextConst("nan",//$NON-NLS-1$
          Double.NaN),//

      new _TextConst(DoubleParser.NOT_A_NUMBER, Double.NaN),//

      new _TextConst("notanumber",//$NON-NLS-1$
          Double.NaN),//

      new _TextConst("error",//$NON-NLS-1$
          Double.NaN, false),//

      new _TextConst("failure",//$NON-NLS-1$
          Double.NaN, false),//

      new _TextConst("0/0",//$NON-NLS-1$
          Double.NaN),//

      //      new _TextConst("true",//$NON-NLS-1$
      // true),//

      new _TextConst("yes",//$NON-NLS-1$
          true),//

      new _TextConst("ok",//$NON-NLS-1$
          true),//

      new _TextConst("check",//$NON-NLS-1$
          true),//

      new _TextConst("accept", //$NON-NLS-1$
          true),//

      new _TextConst("confirm",//$NON-NLS-1$
          true),//

      new _TextConst("acknowledge",//$NON-NLS-1$
          true),//

      new _TextConst("agree",//$NON-NLS-1$
          true),//

      new _TextConst("ja",//$NON-NLS-1$
          true),//

      new _TextConst("wahr",//$NON-NLS-1$
          true),//

      new _TextConst("t",//$NON-NLS-1$
          true),//

      new _TextConst("no",//$NON-NLS-1$
          false),//

      new _TextConst("not",//$NON-NLS-1$
          false),//

      new _TextConst("uncheck",//$NON-NLS-1$
          false),//

      new _TextConst("refuse", //$NON-NLS-1$
          false),//

      new _TextConst("decline",//$NON-NLS-1$
          false),//

      new _TextConst("disagree",//$NON-NLS-1$
          false),//

      new _TextConst("nein",//$NON-NLS-1$
          false),//

      new _TextConst("falsch",//$NON-NLS-1$
          false),//

      new _TextConst("f",//$NON-NLS-1$
          false),//

      new _TextConst(Character.toString((char) 0xbc),//
          0.25d),//
      new _TextConst(Character.toString((char) 0xbd),//
          0.5d),//

      new _TextConst(Character.toString((char) 0xbe),//
          0.75d),//
      new _TextConst(Character.toString((char) 0x2158),//
          1d / 3d),//
      new _TextConst(Character.toString((char) 0x2154),//
          2d / 3d),//
      new _TextConst(Character.toString((char) 0x2155),//
          0.2d),//
      new _TextConst(Character.toString((char) 0x2156),//
          0.4d),//
      new _TextConst(Character.toString((char) 0x2157),//
          0.6),//
      new _TextConst(Character.toString((char) 0x2158),//
          0.8d),//
      new _TextConst(Character.toString((char) 0x2159),//
          1d / 6d),//
      new _TextConst(Character.toString((char) 0x215a),//
          5d / 6d),//
      new _TextConst(Character.toString((char) 0x215b),//
          0.125d),//
      new _TextConst(Character.toString((char) 0x215c),//
          0.375d),//
      new _TextConst(Character.toString((char) 0x215d),//
          0.625d),//
      new _TextConst(Character.toString((char) 0x215e),//
          0.875d),//
      new _TextConst(Character.toString((char) 0x2160),//
          1d, 1L, 1, true),//
      new _TextConst(Character.toString((char) 0x2161),//
          2d, 2L, 2, true),//
      new _TextConst(Character.toString((char) 0x2162),//
          3d, 3L, 3, true),//
      new _TextConst(Character.toString((char) 0x2163),//
          4d, 4L, 4, true),//
      new _TextConst(Character.toString((char) 0x2164),//
          5d, 5L, 5, true),//
      new _TextConst(Character.toString((char) 0x2165),//
          6d, 6L, 6, true),//
      new _TextConst(Character.toString((char) 0x2166),//
          7d, 7L, 7, true),//
      new _TextConst(Character.toString((char) 0x2167),//
          8d, 8L, 8, true),//
      new _TextConst(Character.toString((char) 0x2168),//
          9d, 9L, 9, true),//
      new _TextConst(Character.toString((char) 0x2169),//
          10d, 10L, 10, true),//
      new _TextConst(Character.toString((char) 0x216A),//
          11d, 11L, 11, true),//
      new _TextConst(Character.toString((char) 0x216B),//
          12d, 12L, 12, true),//

      new _TextConst(Character.toString((char) 0x3c0), Math.PI),//
      new _TextConst(Character.toString('e'), Math.E),//

  };

  static {
    java.util.Arrays.sort(_TextConst.CONSTS);
  }

  /** the name */
  final String m_name;

  /** the double */
  final double m_d;

  /** the long */
  final long m_l;

  /** the int */
  final int m_i;

  /** the boolean value */
  final boolean m_b;

  /** the state */
  private final int m_state;

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param d
   *          the double value
   * @param l
   *          the long
   * @param i
   *          the int
   * @param b
   *          the boolean value
   * @param state
   *          the state
   */
  _TextConst(final String n, final double d, final long l, final int i,
      final boolean b, final int state) {
    super();
    this.m_name = n;
    this.m_d = d;
    this.m_l = l;
    this.m_i = i;
    this.m_b = b;
    this.m_state = state;
  }

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param d
   *          the double value
   * @param l
   *          the long
   * @param i
   *          the int
   * @param b
   *          the boolean value
   */
  _TextConst(final String n, final double d, final long l, final int i,
      final boolean b) {
    this(n, d, l, i, b,//
        (_TextConst.STATE_INT | _TextConst.STATE_DOUBLE | //
        _TextConst.STATE_BOOLEAN));
  }

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param d
   *          the double value
   * @param l
   *          the long
   * @param i
   *          the int
   */
  _TextConst(final String n, final double d, final long l, final int i) {
    this(n, d, l, i, false,
        (_TextConst.STATE_INT | _TextConst.STATE_DOUBLE));
  }

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param d
   *          the double value
   */
  _TextConst(final String n, final double d) {
    this(n, d, 0L, 0, false, (_TextConst.STATE_DOUBLE));
  }

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param b
   *          the boolean value
   */
  _TextConst(final String n, final boolean b) {
    this(n, Double.NaN, 0L, 0, b, (_TextConst.STATE_BOOLEAN));
  }

  /**
   * create a text constant
   *
   * @param n
   *          the name
   * @param d
   *          the double value
   * @param b
   *          the boolean value
   */
  _TextConst(final String n, final double d, final boolean b) {
    this(n, d, 0L, 0, b,
        (_TextConst.STATE_DOUBLE | _TextConst.STATE_BOOLEAN));
  }

  /**
   * do we have an integer
   *
   * @return {@code true} if yes, {@code false} if no
   */
  final boolean hasInt() {
    return ((this.m_state & _TextConst.STATE_INT) != 0);
  }

  /**
   * do we have a double
   *
   * @return {@code true} if yes, {@code false} if no
   */
  final boolean hasDouble() {
    return ((this.m_state & _TextConst.STATE_DOUBLE) != 0);
  }

  /**
   * do we have a boolean
   *
   * @return {@code true} if yes, {@code false} if no
   */
  final boolean hasBoolean() {
    return ((this.m_state & _TextConst.STATE_BOOLEAN) != 0);
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _TextConst o) {
    int i;

    if (o == this) {
      return 0;
    }

    i = this.m_name.compareTo(o.m_name);
    if (i != 0) {
      return i;
    }

    return Double.compare(this.m_d, o.m_d);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof _TextConst) && (this
        .compareTo((_TextConst) o) == 0)));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(
        HashUtils.combineHashes(HashUtils.hashCode(this.m_b),
            HashUtils.hashCode(this.m_d)),
        HashUtils.combineHashes(HashUtils.hashCode(this.m_i),
            HashUtils.hashCode(this.m_l))), HashUtils
        .hashCode(this.m_state));

  }

  /**
   * find a numerical constant with the given name
   *
   * @param constName
   *          the name
   * @return the constant, or {@code null} if not found
   */
  static final _TextConst findConst(final String constName) {
    int low, high, mid, cmp;
    _TextConst midVal;
    final String name;

    name = TextUtils.toLowerCase(constName);
    low = 0;
    high = (_TextConst.CONSTS.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = _TextConst.CONSTS[mid];
      cmp = midVal.m_name.compareTo(name);

      if (cmp < 0) {
        low = mid + 1;
      } else {
        if (cmp > 0) {
          high = mid - 1;
        } else {
          return midVal; // key found
        }
      }
    }
    return null;
  }
}
