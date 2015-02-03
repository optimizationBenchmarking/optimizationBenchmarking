package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.Ignore;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

import test.junit.InstanceTest;
import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/**
 * A test for matrix data structures.
 * 
 * @param <MT>
 *          the matrix type
 */
@Ignore
final class _MatrixTest<MT extends IMatrix> extends MatrixTest<MT> {

  /**
   * Create the matrix test
   * 
   * @param owner
   *          the owner
   * @param isSingleton
   *          is this a singleton-based tests?
   * @param instance
   *          the instance, or {@code null} if unspecified
   */
  _MatrixTest(final _MatrixTest<MT> owner, final MT instance,
      final boolean isSingleton) {
    super(owner, instance, isSingleton);
  }

  /** {@inheritDoc} */
  @Override
  protected void checkIntegerMatrixSame(final IMatrix a, final IMatrix b) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public void testSerializationAndDeserializationEquals() {
    // ignore
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected InstanceTest<?> createTestForInstance(final Object instance,
      final boolean isSingleton, final boolean isModifiable) {

    if (instance instanceof IMatrix) {
      return new _MatrixTest(this, ((IMatrix) instance), isSingleton);
    }

    return super
        .createTestForInstance(instance, isSingleton, isModifiable);

  }
}
