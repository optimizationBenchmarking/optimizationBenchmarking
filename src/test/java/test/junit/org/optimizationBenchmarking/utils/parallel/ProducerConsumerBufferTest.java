package test.junit.org.optimizationBenchmarking.utils.parallel;

import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.parallel.ProducerConsumerBuffer;

import test.junit.TestBase;

/**
 * The base class for testing producer and consumer buffers.
 * 
 * @param <T>
 *          the array type
 */
@Ignore
public abstract class ProducerConsumerBufferTest<T> extends TestBase {

  /** create */
  protected ProducerConsumerBufferTest() {
    super();
  }

  /**
   * create a new array
   * 
   * @param size
   *          the size
   * @return the array
   */
  abstract T _new(final int size);

  /**
   * fill an array with random data
   * 
   * @param array
   *          the array
   * @param r
   *          the randomizer
   */
  abstract void _random(final T array, final Random r);

  /**
   * assert that the contents of two arrays are equal
   * 
   * @param a
   *          the first array
   * @param b
   *          the second array
   */
  abstract void _assertEquals(final T a, final T b);

  /**
   * create a new buffer
   * 
   * @param size
   *          the size
   * @return the buffer
   */
  abstract ProducerConsumerBuffer<T> _newBuffer(final int size);

  /**
   * Test whether the written data and the data we read is the same
   * 
   * @throws InterruptedException
   *           if it fails...
   */
  @Test(timeout = 3600000)
  public void testReadEqualsWrite() throws InterruptedException {
    int i, size, use;
    T read, write;
    Random r;
    Thread a, b;

    r = new Random();
    for (i = 0; i < 30; i++) {

      switch (r.nextInt(3)) {
        case 0: {
          size = (1 + r.nextInt(1024 * 1024 * 16));
          break;
        }
        case 1: {
          size = (i + 1);
          break;
        }
        default: {
          size = ((i + 1) * 64);
        }
      }
      read = this._new(size);
      write = this._new(size);
      this._random(write, r);

      switch (r.nextInt(3)) {
        case 0: {
          use = (1 + r.nextInt(100));
          break;
        }
        case 1: {
          use = 1;
          break;
        }
        case 2: {
          use = -1;
          break;
        }
        case 3: {
          use = 1024;
          break;
        }
        default: {
          use = (1 + r.nextInt(size << 1));
          break;
        }
      }

      try (final ProducerConsumerBuffer<T> buffer = this._newBuffer(use)) {
        a = new __WriterThread(write, size, buffer);
        b = new __ReaderThread(read, size, buffer);

        if (r.nextBoolean()) {
          a.start();
          Thread.sleep(100);
          b.start();
        } else {
          b.start();
          Thread.sleep(100);
          a.start();
        }

        a.join();
        b.join();
      }

      this._assertEquals(write, read);
    }
  }

  /** a writer thread */
  private final class __WriterThread extends Thread {
    /** the data to write */
    private final T m_write;
    /** the amount to write */
    private final int m_size;
    /** the buffer */
    private final ProducerConsumerBuffer<T> m_buffer;

    /**
     * create the writer thread
     * 
     * @param write
     *          the stuff to write
     * @param size
     *          the total amount to write
     * @param buffer
     *          the buffer
     */
    __WriterThread(final T write, final int size,
        final ProducerConsumerBuffer<T> buffer) {
      super();
      this.m_write = write;
      this.m_size = size;
      this.m_buffer = buffer;
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      final Random r;
      int pos, w;

      r = new Random();
      pos = 0;

      while (pos < this.m_size) {
        switch (r.nextInt(4)) {
          case 0: {
            w = r.nextInt(this.m_size - pos);
            break;
          }
          case 1: {
            w = Math.min((this.m_size - pos), r.nextInt(100));
            break;
          }
          case 2: {
            w = 1;
            break;
          }
          default: {
            w = 0;
          }
        }
        this.m_buffer.writeToBuffer(this.m_write, pos, w);
        pos += w;
        switch (r.nextInt(3)) {
          case 0: {
            Thread.yield();
            break;
          }
          case 1: {
            try {
              Thread.sleep(r.nextInt(100));
            } catch (final Throwable t) {/* */
            }
            break;
          }
          default: {
            // nothing
          }
        }
      }
    }
  }

  /** a reader thread */
  private final class __ReaderThread extends Thread {
    /** the data to read */
    private final T m_read;
    /** the amount to read */
    private final int m_size;
    /** the buffer */
    private final ProducerConsumerBuffer<T> m_buffer;

    /**
     * create the read thread
     * 
     * @param read
     *          the stuff to read
     * @param size
     *          the total amount to read
     * @param buffer
     *          the buffer
     */
    __ReaderThread(final T read, final int size,
        final ProducerConsumerBuffer<T> buffer) {
      super();
      this.m_read = read;
      this.m_size = size;
      this.m_buffer = buffer;
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      final Random r;
      int pos, w;

      r = new Random();
      pos = 0;

      while (pos < this.m_size) {
        switch (r.nextInt(4)) {
          case 0: {
            w = r.nextInt(this.m_size - pos);
            break;
          }
          case 1: {
            w = Math.min((this.m_size - pos), r.nextInt(100));
            break;
          }
          case 2: {
            w = 1;
            break;
          }
          default: {
            w = 0;
          }
        }
        w = this.m_buffer.readFromBuffer(this.m_read, pos, w);
        if (w < 0) {
          return;
        }
        pos += w;
        switch (r.nextInt(3)) {
          case 0: {
            Thread.yield();
            break;
          }
          case 1: {
            try {
              Thread.sleep(r.nextInt(100));
            } catch (final Throwable t) {/* */
            }
            break;
          }
          default: {
            // nothing
          }
        }
      }
    }
  }
}
