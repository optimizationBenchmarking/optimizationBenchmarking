package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.parsers.BooleanParser;
import org.optimizationBenchmarking.utils.parsers.ByteParser;
import org.optimizationBenchmarking.utils.parsers.CharParser;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.FloatParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.ShortParser;
import org.optimizationBenchmarking.utils.parsers.StrictBooleanParser;
import org.optimizationBenchmarking.utils.parsers.StrictByteParser;
import org.optimizationBenchmarking.utils.parsers.StrictCharParser;
import org.optimizationBenchmarking.utils.parsers.StrictDoubleParser;
import org.optimizationBenchmarking.utils.parsers.StrictFloatParser;
import org.optimizationBenchmarking.utils.parsers.StrictIntParser;
import org.optimizationBenchmarking.utils.parsers.StrictLongParser;
import org.optimizationBenchmarking.utils.parsers.StrictShortParser;

/** The number type. */
public enum EPrimitiveType {

  /** the boolean type class */
  BOOLEAN(boolean.class, Boolean.class, 1,
      StrictBooleanParser.STRICT_INSTANCE, BooleanParser.INSTANCE),

  /** the boolean type class */
  CHAR(char.class, Character.class, 2, StrictCharParser.STRICT_INSTANCE,
      CharParser.INSTANCE),

  /** the byte type class */
  BYTE(byte.class, Byte.class, 4, StrictByteParser.STRICT_INSTANCE,
      ByteParser.INSTANCE),

  /** the short type class */
  SHORT(short.class, Short.class, 4, StrictShortParser.STRICT_INSTANCE,
      ShortParser.INSTANCE),

  /** the int type class */
  INT(int.class, Integer.class, 4, StrictIntParser.STRICT_INSTANCE,
      IntParser.INSTANCE),

  /** the long type class */
  LONG(long.class, Long.class, 4, StrictLongParser.STRICT_INSTANCE,
      LongParser.INSTANCE),

  /** the float type class */
  FLOAT(float.class, Float.class, 8, StrictFloatParser.STRICT_INSTANCE,
      FloatParser.INSTANCE),

  /** the double type class */
  DOUBLE(double.class, Double.class, 8,
      StrictDoubleParser.STRICT_INSTANCE, DoubleParser.INSTANCE), ;

  /** the type */
  private static final EPrimitiveType[] _TYPES = EPrimitiveType.values();

  /** create */
  public static final ArraySetView<EPrimitiveType> TYPES = new _PrimitiveTypeSet(
      EPrimitiveType._TYPES);

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the primitive type */
  private transient final Class<?> m_primitive;

  /** the primitive type */
  private transient final Class<?> m_wrapper;

  /** type class */
  private transient final int m_class;

  /** the strict parser */
  private transient final Parser<?> m_strictParser;

  /** the loose parser */
  private transient final Parser<?> m_looseParser;

  /**
   * create the number type
   * 
   * @param primitive
   *          the primitive type
   * @param wrapper
   *          the wrapper class
   * @param clazz
   *          the type class
   * @param strict
   *          the strict parser
   * @param loose
   *          the loose parser
   */
  private EPrimitiveType(final Class<?> primitive, final Class<?> wrapper,
      final int clazz, final Parser<?> strict, final Parser<?> loose) {
    this.m_primitive = primitive;
    this.m_wrapper = wrapper;
    this.m_class = clazz;
    this.m_strictParser = strict;
    this.m_looseParser = loose;
  }

  /**
   * Get the strict parser belonging to this type
   * 
   * @return the strict parser belonging to this type
   */
  public final Parser<?> getStrictParser() {
    return this.m_strictParser;
  }

  /**
   * Get the loose parser belonging to this type
   * 
   * @return the loose parser belonging to this type
   */
  public final Parser<?> getLooseParser() {
    return this.m_looseParser;
  }

  /**
   * Get the primitive type
   * 
   * @return the primitive type
   */
  public final Class<?> getPrimitiveType() {
    return this.m_primitive;
  }

  /**
   * Get the wrapper class
   * 
   * @return the wrapper class
   */
  public final Class<?> getWrapperClass() {
    return this.m_wrapper;
  }

  /**
   * Is this a boolean type?
   * 
   * @return {@code true} if this is a boolean type, {@code false}
   *         otherwise
   */
  public final boolean isBoolean() {
    return ((this.m_class & 1) != 0);
  }

  /**
   * Is this a character type?
   * 
   * @return {@code true} if this is a character type, {@code false}
   *         otherwise
   */
  public final boolean isCharacter() {
    return ((this.m_class & 2) != 0);
  }

  /**
   * Is this a integer type?
   * 
   * @return {@code true} if this is a integer type, {@code false}
   *         otherwise
   */
  public final boolean isInteger() {
    return ((this.m_class & 4) != 0);
  }

  /**
   * Is this a floating point type?
   * 
   * @return {@code true} if this is a floating type, {@code false}
   *         otherwise
   */
  public final boolean isFloat() {
    return ((this.m_class & 8) != 0);
  }

  /**
   * Is this a numerical type?
   * 
   * @return {@code true} if this is a numerical type, {@code false}
   *         otherwise
   */
  public final boolean isNumber() {
    return ((this.m_class & 12) != 0);
  }

  /**
   * Get the primitive type name
   * 
   * @return the primitive type name
   */
  public final String getPrimitiveTypeName() {
    return this.m_primitive.getCanonicalName();
  }

  /**
   * Is a variable of this primitive type directly assignable from an
   * expression of primitive type {@code type} (without explicit cast)?
   * 
   * @param type
   *          the other type
   * @return {@code true} if and only if a variable of this primitive type
   *         directly assignable from an expression of primitive type
   *         {@code type} (without explicit cast), {@code false} otherwise.
   */
  public final boolean isAssignableFrom(final EPrimitiveType type) {
    if (this == type) {
      return true;
    }

    if (type == null) {
      return false;
    }

    if (this.m_class == type.m_class) {
      return (this.ordinal() >= type.ordinal());
    }

    if (this.m_class == 8) {
      if ((type.m_class == 4) || (type.m_class == 2)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Get a primitive type fitting to a given class.
   * 
   * @param clazz
   *          the class
   * @return the primitive type, or {@code null} if none was found
   */
  public static final EPrimitiveType getPrimitiveType(final Class<?> clazz) {
    for (final EPrimitiveType x : EPrimitiveType._TYPES) {
      if ((x.m_primitive == clazz) || (x.m_wrapper == clazz)) {
        return x;
      }
    }
    return null;
  }

}
