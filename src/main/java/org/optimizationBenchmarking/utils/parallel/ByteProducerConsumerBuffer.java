package org.optimizationBenchmarking.utils.parallel;

/**
 * <p>
 * A producer/consumer buffer backed by a byte array, intended to be used
 * by different threads: A producer
 * {@link #writeToBuffer(byte[], int, int) writes} data into the buffer and
 * a consumer {@link #readFromBuffer(byte[], int, int)} reads the data from
 * the buffer. {@link #writeToBuffer(byte[], int, int) writing} will never
 * block while {@link #readFromBuffer(byte[], int, int) reading} blocks
 * until either data becomes available (has been written) or the buffer is
 * closed.
 * </p>
 */
public final class ByteProducerConsumerBuffer extends
    ProducerConsumerBuffer<byte[]> {

  /** Create the buffer */
  public ByteProducerConsumerBuffer() {
    this(-1);
  }

  /**
   * Create the buffer
   * 
   * @param initialSize
   *          the buffer size, 0 for default
   */
  public ByteProducerConsumerBuffer(final int initialSize) {
    super(initialSize);
  }

  /** {@inheritDoc} */
  @Override
  final byte[] _new(final int size) {
    return new byte[size];
  }

  /** {@inheritDoc} */
  @Override
  final int _length(final byte[] array) {
    return array.length;
  }

}
