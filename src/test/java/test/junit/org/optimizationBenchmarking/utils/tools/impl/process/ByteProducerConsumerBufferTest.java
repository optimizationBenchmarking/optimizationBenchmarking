package test.junit.org.optimizationBenchmarking.utils.tools.impl.process;

import java.util.Random;

import org.junit.Assert;
import org.optimizationBenchmarking.utils.parallel.ByteProducerConsumerBuffer;
import org.optimizationBenchmarking.utils.parallel.ProducerConsumerBuffer;

/**
 * The base class for testing producer and consumer buffers.
 */
public class ByteProducerConsumerBufferTest extends
    ProducerConsumerBufferTest<byte[]> {

  /** create */
  public ByteProducerConsumerBufferTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final byte[] _new(final int size) {
    return new byte[size];
  }

  /** {@inheritDoc} */
  @Override
  final void _random(final byte[] array, final Random r) {
    r.nextBytes(array);
  }

  /** {@inheritDoc} */
  @Override
  final void _assertEquals(final byte[] a, final byte[] b) {
    Assert.assertArrayEquals(a, b);
  }

  /** {@inheritDoc} */
  @Override
  final ProducerConsumerBuffer<byte[]> _newBuffer(final int size) {
    return new ByteProducerConsumerBuffer(size);
  }

}
