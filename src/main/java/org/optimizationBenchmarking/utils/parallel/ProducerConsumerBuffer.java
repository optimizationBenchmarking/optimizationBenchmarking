package org.optimizationBenchmarking.utils.parallel;

import java.io.Closeable;

/**
 * <p>
 * An internal base class for buffers intended to be used by different
 * threads: A producer {@link #writeToBuffer(Object, int, int) writes} data
 * into the buffer and a consumer {@link #readFromBuffer(Object, int, int)}
 * reads the data from the buffer. {@link #writeToBuffer(Object, int, int)
 * writing} will never block while
 * {@link #readFromBuffer(Object, int, int) reading} blocks until either
 * data becomes available (has been written) or the buffer is closed.
 * </p>
 * <p>
 * The {@link #m_buffer internal array} is used as a ring buffer. However,
 * different from a traditional ring buffer, it may grow infinitely.
 * </p>
 * 
 * @param <T>
 *          the array type
 */
public abstract class ProducerConsumerBuffer<T> implements Closeable {

  /** the buffer */
  private T m_buffer;

  /** the position */
  private int m_readPosition;

  /** the number of buffered elements */
  private int m_size;

  /** is the buffer closed? */
  private volatile boolean m_closed;

  /** the internal, hidden synchronization object */
  private final Object m_synch;

  /**
   * Create the buffer
   * 
   * @param initialSize
   *          the buffer size, 0 for default
   */
  protected ProducerConsumerBuffer(final int initialSize) {
    super();
    this.m_buffer = this._new((initialSize <= 0) ? 8192
        : ((initialSize & 0xfffffff0) | 32));
    this.m_synch = new Object();
  }

  /**
   * Create an array of the given size
   * 
   * @param size
   *          the size
   * @return the array
   */
  abstract T _new(final int size);

  /**
   * Get the length of an array
   * 
   * @param array
   *          the array
   * @return its length
   */
  abstract int _length(final T array);

  /**
   * Store a number of elements into the buffer. If {@code count>0}, then
   * any waiting reader will be {@link java.lang.Object#notifyAll()
   * notified}. If the current internal backing store is not big enough to
   * hold the data, a new store will be allocated. If the buffer has been
   * {@link #close() closed}, nothing will be stored.
   * 
   * @param source
   *          the source
   * @param start
   *          the start index
   * @param count
   *          the number of elements to store
   */
  public final void writeToBuffer(final T source, final int start,
      final int count) {
    T bufferArray;
    int s, bufferArraySize, currentSize, newArraySize, newSize, readPosition, writePosition;

    s = 0;
    if ((source == null) || (count < 0) || (start < 0)
        || ((count + start) > (s = this._length(source)))) {
      throw new IllegalArgumentException("Reading " + //$NON-NLS-1$
          count + " elements starting at " + start//$NON-NLS-1$ 
          + " from array " + source + //$NON-NLS-1$
          " of length " + s//$NON-NLS-1$
          + " is not possible.");//$NON-NLS-1$
    }

    if (count <= 0) {
      return;
    }

    if (this.m_closed) {
      return;
    }
    synchronized (this.m_synch) {
      if (this.m_closed) {
        return;
      }

      bufferArray = this.m_buffer;
      bufferArraySize = this._length(bufferArray);
      readPosition = this.m_readPosition;
      currentSize = this.m_size;
      writePosition = (currentSize + readPosition);

      newSize = (currentSize + count);
      if (newSize > bufferArraySize) {
        newArraySize = (((newSize & 0xfffffff0) | 32) << 1);
        if (newArraySize <= currentSize) {
          newArraySize = newSize;
        }
        bufferArray = this._new(newArraySize);

        if (writePosition <= bufferArraySize) {
          System.arraycopy(this.m_buffer, readPosition, bufferArray, 0,
              currentSize);
        } else {
          s = (bufferArraySize - readPosition);
          System.arraycopy(this.m_buffer, readPosition, bufferArray, 0, s);
          System.arraycopy(this.m_buffer, 0, bufferArray, s,
              (currentSize - s));
        }

        this.m_readPosition = readPosition = 0;
        writePosition = currentSize;
        this.m_buffer = bufferArray;
        bufferArraySize = newArraySize;
      } else {
        writePosition %= bufferArraySize;
      }

      s = (writePosition + count);
      if (s <= bufferArraySize) {
        System.arraycopy(source, start, bufferArray, writePosition, count);
      } else {
        s = (bufferArraySize - writePosition);
        System.arraycopy(source, start, bufferArray, writePosition, s);
        System.arraycopy(source, (start + s), bufferArray, 0, (count - s));
      }
      this.m_size = newSize;

      this.m_synch.notifyAll();
    }
  }

  /**
   * Blocking read (at most) a number ({@code count} of bytes from the
   * buffer into a destination array. This method may read less if fewer
   * elements are in the buffer.
   * 
   * @param dest
   *          the destination array
   * @param start
   *          the start index
   * @param count
   *          the number of bytes to read
   * @return the <em>actual</em> number of bytes read (may be less than
   *         {@code count}, or {@code -1} if the buffer is empty and
   *         {@link #close() closed}.
   */
  public final int readFromBuffer(final T dest, final int start,
      final int count) {
    return this.__readFromBuffer(dest, start, count, false, true);
  }

  /**
   * Blocking read exactly a number ({@code count} of bytes from the buffer
   * into a destination array, if possible. This method may read less
   * <em>only</em> if the buffer was {@link #close() closed} and there can
   * never be enough elements in it to satisfy the request.
   * 
   * @param dest
   *          the destination array
   * @param start
   *          the start index
   * @param count
   *          the number of bytes to read
   * @return the <em>actual</em> number of bytes read (may be less than
   *         {@code count}, or {@code -1} if the buffer is empty and
   *         {@link #close() closed}.
   */
  public final int readFromBufferFully(final T dest, final int start,
      final int count) {
    return this.__readFromBuffer(dest, start, count, true, true);
  }

  /**
   * Blocking read (at most) a number ({@code count} of bytes from the
   * buffer into a destination array.
   * 
   * @param dest
   *          the destination array
   * @param start
   *          the start index
   * @param count
   *          the number of bytes to read
   * @param fully
   *          block until the required amount of data is available?
   * @param read
   *          should data actually be copied ({@code true}) or just skipped
   *          ({@code false})?
   * @return the <em>actual</em> number of bytes read (may be less than
   *         {@code count}, or {@code -1} if the buffer is empty and
   *         {@link #close() closed}.
   */
  private final int __readFromBuffer(final T dest, final int start,
      final int count, final boolean fully, final boolean read) {
    T bufferArray;
    int s, bufferArraySize, currentSize, readAmount, readPosition;

    if (read) {
      s = 0;
      if ((dest == null) || (count < 0) || (start < 0)
          || ((count + start) > (s = this._length(dest)))) {
        throw new IllegalArgumentException("Writing " + //$NON-NLS-1$
            count + " elements starting at " + start//$NON-NLS-1$ 
            + " to array " + dest + //$NON-NLS-1$
            " of length " + s//$NON-NLS-1$
            + " is not possible.");//$NON-NLS-1$
      }
    }

    if (count <= 0) {
      return (this.m_closed ? (-1) : 0);
    }

    for (;;) {
      synchronized (this.m_synch) {

        currentSize = this.m_size;
        if (currentSize >= ((fully && (!(this.m_closed))) ? count : 1)) {
          readAmount = Math.min(currentSize, count);
          bufferArray = this.m_buffer;
          bufferArraySize = this._length(bufferArray);
          readPosition = this.m_readPosition;
          s = (readPosition + readAmount);
          if (s <= bufferArraySize) {
            if (read) {
              System.arraycopy(bufferArray, readPosition, dest, start,
                  readAmount);
            }
            this.m_readPosition = s;
          } else {
            s = (bufferArraySize - readPosition);
            if (read) {
              System.arraycopy(bufferArray, readPosition, dest, start, s);
            }
            this.m_readPosition = readPosition = (readAmount - s);
            if (read) {
              System.arraycopy(bufferArray, 0, dest, (start + s),
                  readPosition);
            }
          }
          this.m_size = (currentSize - readAmount);
          return readAmount;
        }

        if (this.m_closed) {
          return (-1);
        }

        try {
          // wait for 60s only, which may guard against some odd
          // implementation errors
          this.m_synch.wait(60000L);
        } catch (final InterruptedException ie) {
          //
        }
      }
    }
  }

  /**
   * Blocking skip over (delete) (at most) a number ({@code count} of bytes
   * from the buffer. This method may delete/skip over less if fewer
   * elements are in the buffer.
   * 
   * @param count
   *          the number of bytes to delete/skip over
   * @return the <em>actual</em> number of bytes skipped over (may be less
   *         than {@code count}, or {@code -1} if the buffer is empty and
   *         {@link #close() closed}.
   */
  public final int deleteFromBuffer(final int count) {
    return this.__readFromBuffer(null, 0, count, false, false);
  }

  /**
   * Blocking skip over (delete) exactly a number ({@code count} of bytes
   * from the buffer, if possible. This method may delete less
   * <em>only</em> if the buffer was {@link #close() closed} and there can
   * never be enough elements in it to satisfy the request.
   * 
   * @param count
   *          the number of bytes to delete
   * @return the <em>actual</em> number of bytes deleted/skipped over (may
   *         be less than {@code count}, or {@code -1} if the buffer is
   *         empty and {@link #close() closed}.
   */
  public final int deleteFromBufferFully(final int count) {
    return this.__readFromBuffer(null, 0, count, true, false);
  }

  /** Discard all data in the buffer. */
  public final void discardEverythingInBuffer() {
    synchronized (this.m_synch) {
      this.m_size = this.m_readPosition = 0;
    }
  }

  /**
   * Get the current number of elements in the buffer
   * 
   * @return the current number of elements in the buffer
   */
  public final int size() {
    synchronized (this.m_synch) {
      return this.m_size;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    synchronized (this.m_synch) {
      this.m_closed = true;
      this.m_synch.notifyAll();
    }
  }

  /**
   * Has this buffer been closed?
   * 
   * @return {@code true} if the buffer has been closed, {@code false}
   *         otherwise
   */
  public final boolean isClosed() {
    return this.m_closed;
  }
}
