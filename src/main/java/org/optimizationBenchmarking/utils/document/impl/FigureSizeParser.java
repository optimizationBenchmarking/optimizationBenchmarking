package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for instances of
 * {@link org.optimizationBenchmarking.utils.document.spec.EFigureSize}.
 */
public final class FigureSizeParser extends InstanceParser<EFigureSize> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the figure size parameter */
  public static final String PARAM_FIGURE_SIZE = "figureSize"; //$NON-NLS-1$

  /** the internal loopkup */
  private static final StringMapCI<EFigureSize> LOOKUP = FigureSizeParser
      .__makeMap();

  /** the globally shared instance of the figure size parser */
  public static final FigureSizeParser INSTANCE = new FigureSizeParser();

  /** create the figure size parser */
  private FigureSizeParser() {
    super(EFigureSize.class, null);
  }

  /** {@inheritDoc} */
  @Override
  public final EFigureSize parseString(final String string) {
    EFigureSize res;

    res = FigureSizeParser.LOOKUP.get(string);
    if (res != null) {
      return res;
    }

    return super.parseString(string);
  }

  /**
   * Make the map for lookups
   *
   * @return the map
   */
  private static final StringMapCI<EFigureSize> __makeMap() {
    final StringMapCI<EFigureSize> map;
    String name, name2;

    map = new StringMapCI<>();

    for (final EFigureSize size : EFigureSize.INSTANCES) {
      name = size.name();
      map.put(name, size);
      name = TextUtils.toLowerCase(name);

      for (int i = 0; i < 16; i++) {
        name2 = name;

        if ((i & 1) != 0) {
          name2 = name2.replace('_', ' ');
          map.put(name2.trim(), size);
        }

        if ((i & 2) != 0) {
          name2 = name2.replace("page ", "")//$NON-NLS-1$//$NON-NLS-2$
              .replace("column ", "col ");//$NON-NLS-1$//$NON-NLS-2$
          map.put(name2.trim(), size);
        }

        if ((i & 4) != 0) {
          name2 = name2.replace(" by ", "x")//$NON-NLS-1$//$NON-NLS-2$
              .replace("_by_", "x")//$NON-NLS-1$//$NON-NLS-2$
              .replace(" per ", "/")//$NON-NLS-1$//$NON-NLS-2$
              .replace("_per_", "/");//$NON-NLS-1$//$NON-NLS-2$
          map.put(name2.trim(), size);
        }

        if ((i & 8) != 0) {
          name2 = name2.replace(" by ", "*")//$NON-NLS-1$//$NON-NLS-2$
              .replace("_by_", "*");//$NON-NLS-1$//$NON-NLS-2$
          map.put(name2.trim(), size);
        }
      }

      name = size.toString();
      map.put(name, size);
    }

    map.put("page", EFigureSize.PAGE_FULL);//$NON-NLS-1$
    map.put("column", EFigureSize.COLUMN_FULL); //$NON-NLS-1$

    return map;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return FigureSizeParser.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return FigureSizeParser.INSTANCE;
  }
}
