package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.compound.FunctionBuilder;

/**
 * An abstract base class for name resolvers.
 */
public class AbstractNameResolver implements INameResolver {

  /** the set of basic constants */
  private static final StringMapCI<Number> BASIC_CONSTANTS;

  static {
    final StringMapCI<Number> map;
    NamedDoubleConstant constant;
    String name;

    map = new StringMapCI<>();

    name = Character.toString((char) 0x3c0);
    constant = new NamedDoubleConstant(Math.PI, name);
    map.put(name, constant);
    map.put("pi", constant); //$NON-NLS-1$

    name = "e"; //$NON-NLS-1$
    map.put(name, new NamedDoubleConstant(Math.E, name));

    BASIC_CONSTANTS = map;
  }

  /** create the abstract name resolver */
  protected AbstractNameResolver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public MathematicalFunction resolve(String name,
      FunctionBuilder<?> builder) {
    throw new IllegalArgumentException("The name '" + name + //$NON-NLS-1$
        "' cannot be resolved to any known function or constant.");//$NON-NLS-1$
  }

  /**
   * Check whether the given name is a default constant, such as
   * {@link Math#PI}
   * 
   * @param name
   *          the name
   * @return the constant, or {@code null} if none could be detected
   */
  protected static final Number resolveDefaultConstant(final String name) {
    return BASIC_CONSTANTS.get(name);
  }

}
