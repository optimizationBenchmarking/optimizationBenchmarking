package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A parser for primitive types */
public class PrimitiveTypeParser extends InstanceParser<EPrimitiveType> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final PrimitiveTypeParser INSTANCE = new PrimitiveTypeParser();

  /** the dimension type parser */
  private PrimitiveTypeParser() {
    super(EPrimitiveType.class, null);
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType parseString(final String string) {
    final String use;

    use = TextUtils.prepare(string);
    if (use == null) {
      throw new IllegalArgumentException(//
          "Dimension type name cannot be null, empty, or just consist of white space, but you provided '" //$NON-NLS-1$
              + string + '\'' + '.');
    }

    for (final EPrimitiveType type : EPrimitiveType._TYPES) {
      if (type.name().equalsIgnoreCase(use)) {
        return type;
      }
      if (type.getPrimitiveType().getSimpleName().equalsIgnoreCase(use)) {
        return type;
      }
    }

    return super.parseString(use);
  }

  /**
   * Get the human readable name of the given dimension type
   *
   * @param type
   *          the type
   * @return the human readable name
   */
  public static final String getHumanReadable(final EPrimitiveType type) {
    if (type == null) {
      throw new IllegalArgumentException("Primitive type cannot be null."); //$NON-NLS-1$
    }
    return type.getPrimitiveType().getSimpleName();
  }
}
