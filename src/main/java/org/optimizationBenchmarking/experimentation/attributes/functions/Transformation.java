package org.optimizationBenchmarking.experimentation.attributes.functions;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;

/**
 * A unary function which receives values from a given dimension as input
 * and transforms potentially them based on information obtain from
 * experiment parameters or instance features.
 */
public class Transformation extends HashObject {

  /** the function to be applied to the input data */
  final UnaryFunction m_func;

  /** the internal property-based constants */
  private final _DataBasedConstant[] m_constants;

  /** the marker that this transformation is blocked because it is in use */
  private volatile boolean m_isInUse;

  /**
   * Create the data transformation
   *
   * @param function
   *          the function to be applied
   * @param constants
   *          the constants
   */
  Transformation(final UnaryFunction function,
      final _DataBasedConstant[] constants) {
    super();

    if (function == null) {
      throw new IllegalArgumentException(//
          "The transformation function cannot be null."); //$NON-NLS-1$
    }

    this.m_func = function;
    this.m_constants = (((constants == null) || (constants.length <= 0)) ? null
        : constants);
  }

  /**
   * Create the identity transformation
   */
  public Transformation() {
    this(Identity.INSTANCE, null);
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_func),//
        HashUtils.hashCode(this.m_constants));
  }

  /**
   * Check whether this function represents an identity transformation of
   * the input to the output, i.e., if it just returns its input directly.
   *
   * @return {@code true} if this functions is an identity transformation,
   *         {@code false} otherwise
   */
  public final boolean isIdentityTransformation() {
    return (this.m_func instanceof Identity);
  }

  /**
   * Provide the data transformation function based on a given data element
   *
   * @param element
   *          the data element
   * @return the transformation function
   */
  public synchronized final TransformationFunction use(
      final IDataElement element) {

    if (this.m_constants != null) {
      if (this.m_isInUse) {
        throw new IllegalStateException("The data transformation " + //$NON-NLS-1$
            this.m_func + " is already in use concurrently.."); //$NON-NLS-1$
      }

      for (final _DataBasedConstant constant : this.m_constants) {
        constant._update(element);
      }
    }

    this.m_isInUse = true;
    return new TransformationFunction(this);
  }

  /** notice that a function application has been finished */
  synchronized final void _endUse() {
    final boolean isInUse;

    isInUse = this.m_isInUse;
    this.m_isInUse = false;

    if (isInUse) {
      if (this.m_constants != null) {
        for (final _DataBasedConstant constant : this.m_constants) {
          constant._clear();
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final Transformation other;
    if (o == this) {
      return true;
    }
    if (o instanceof Transformation) {
      other = ((Transformation) o);
      return ((this.m_func.equals(other.m_func)) && //
      (Arrays.equals(this.m_constants, other.m_constants)));
    }
    return true;
  }
}
