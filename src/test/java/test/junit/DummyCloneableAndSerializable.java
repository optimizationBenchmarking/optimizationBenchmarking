package test.junit;

import java.io.Serializable;
import java.util.Random;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A dummy {@link java.io.Serializable serializable} and
 * {@link java.lang.Cloneable cloneable} class for testing purposes. If
 * clone-ability is not necessary, use
 * {@link org.optimizationBenchmarking.utils.math.random.RandomUtils#longToObject(long, boolean)}
 * instead.
 */
public class DummyCloneableAndSerializable implements Serializable,
    Cloneable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the syncher */
  private static final Random RANDOM = new Random();

  /** the ids */
  private static long s_ids = DummyCloneableAndSerializable.RANDOM
      .nextLong();

  /** the id */
  private final long m_id;

  /** create */
  DummyCloneableAndSerializable() {
    super();
    synchronized (DummyCloneableAndSerializable.RANDOM) {
      DummyCloneableAndSerializable.s_ids += (1 + DummyCloneableAndSerializable.RANDOM
          .nextInt(10));
      this.m_id = (DummyCloneableAndSerializable.s_ids);
    }
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (final Throwable tt) {
      throw new RuntimeException(tt);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.hashCode(this.m_id);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DummyCloneableAndSerializable) {
      return (this.m_id == (((DummyCloneableAndSerializable) o).m_id));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return String.valueOf(this.m_id);
  }
}
