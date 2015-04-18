package org.optimizationBenchmarking.utils.graphics.graphic.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPGraphicDrivers;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIORasterGraphicDrivers;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** a graphic driver parser */
public final class GraphicDriverParser extends
    InstanceParser<IGraphicDriver> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the format parser */
  private final InstanceParser<EGraphicFormat> m_formatParser;

  /** create */
  GraphicDriverParser() {
    super(IGraphicDriver.class, GraphicDriverParser.__prefixes());
    this.m_formatParser = new InstanceParser<>(EGraphicFormat.class, null);
  }

  /**
   * get the prefixes
   * 
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;
    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(EGraphicFormat.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        FreeHEPGraphicDrivers.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        ImageIORasterGraphicDrivers.class, paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final IGraphicDriver parseString(final String string)
      throws Exception {
    try {
      return this.m_formatParser.parseString(string).getDefaultDriver();
    } catch (final Exception exception) {
      try {
        return super.parseString(string);
      } catch (final Exception exception2) {
        RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION.rethrow(
            ((("Could not find graphic driver fitting to string '" //$NON-NLS-1$
            + string) + '\'') + '.'), false,
            ErrorUtils.aggregateError(exception, exception2));
        return null;// never reached
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IGraphicDriver parseObject(final Object o) throws Exception {
    try {
      return this.m_formatParser.parseObject(o).getDefaultDriver();
    } catch (final Exception exception) {
      try {
        return super.parseObject(o);
      } catch (final Exception exception2) {
        RethrowMode.AS_UNSUPPORTED_OPERATION_EXCEPTION.rethrow(
            ((("Could not find graphic driver fitting to object '" //$NON-NLS-1$
            + o) + '\'') + '.'), false,
            ErrorUtils.aggregateError(exception, exception2));
        return null;// never reached
      }
    }
  }

  /**
   * Get the singleton instance of this parser
   * 
   * @return the graphic driver parser
   */
  public static final GraphicDriverParser getInstance() {
    return __GraphicDriverParserLoader.INSTANCE;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} for serialization,
   * i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   * 
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object writeReplace() {
    return GraphicDriverParser.getInstance();
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   * 
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object readResolve() {
    return GraphicDriverParser.getInstance();
  }

  /** the instance loader */
  private static final class __GraphicDriverParserLoader {
    /** the instance */
    static final GraphicDriverParser INSTANCE = new GraphicDriverParser();
  }
}
