package org.optimizationBenchmarking.utils.math.random;

import java.util.Random;

import org.optimizationBenchmarking.utils.ErrorUtils;

/**
 * This class provides methods for shuffling things.
 */
public class Shuffle {

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code byte}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final byte[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Byte array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling byte array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code byte}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code byte}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final byte[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Byte array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code byte}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final byte[] array,
      final int length, final int start, final int count,
      final Random random) {
    byte temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code short}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final short[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Short array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling short array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code short}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code short}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final short[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Short array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code short}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final short[] array,
      final int length, final int start, final int count,
      final Random random) {
    short temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code int}s whose sub-sequence to be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final int[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Integer array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling int array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code int}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code int}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final int[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Integer array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code int}s whose sub-sequence to be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final int[] array, final int length,
      final int start, final int count, final Random random) {
    int temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code long}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final long[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Long array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling long array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code long}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code long}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final long[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Long array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code long}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final long[] array,
      final int length, final int start, final int count,
      final Random random) {
    long temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code float}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final float[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Float array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling float array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code float}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code float}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final float[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Float array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code float}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final float[] array,
      final int length, final int start, final int count,
      final Random random) {
    float temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code double}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final double[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Double array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling double array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code double}s. After
   * this procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code double}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final double[] array,
      final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Double array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code double}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final double[] array,
      final int length, final int start, final int count,
      final Random random) {
    double temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code boolean}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final boolean[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Boolean array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling boolean array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code boolean}s. After
   * this procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code boolean}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final boolean[] array,
      final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Boolean array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code boolean}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final boolean[] array,
      final int length, final int start, final int count,
      final Random random) {
    boolean temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code char}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final char[] array, final int start,
      final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Character array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling char array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code char}s. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code char}s that should be randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final char[] array, final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Character array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code char}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final char[] array,
      final int length, final int start, final int count,
      final Random random) {
    char temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /**
   * Randomly shuffle a sub-sequence of an array or permutation of
   * {@code bytes}. After this procedure, the {@code count} elements of the
   * array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code java.lang.Object}s whose sub-sequence to
   *          be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   * @param random
   *          the random number generator
   * @throws IllegalArgumentException
   *           if {@code array==null} or {@code count<0}
   */
  public static final void shuffle(final java.lang.Object[] array,
      final int start, final int count, final Random random) {
    final int length, useCount;
    int useStart;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Object array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((start >= (length = array.length)) || (start < 0)) {
      throw new IllegalArgumentException(//
          "Start index " + start + //$NON-NLS-1$
              " invalid for shuffling java.lang.Object array of length "//$NON-NLS-1$ 
              + length);
    }

    if (length <= 1) {
      return;
    }

    if (count < 0) {
      if (count >= (-1)) {
        return;
      }
      if (count <= (-length)) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = (start + count + 1);
        if (useStart < 0) {
          useStart += length;
        }
        useCount = (-count);
      }
    } else {
      if (count <= 1) {
        return;
      }
      if (count >= length) {
        useCount = length;
        useStart = 0;
      } else {
        useStart = start;
        useCount = count;
      }
    }

    Shuffle.__shuffle(array, length, useStart, useCount, random);
  }

  /**
   * Randomly shuffle an array or permutation of {@code java.lang.Object}s.
   * After this procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code java.lang.Object}s that should be
   *          randomized
   * @param random
   *          the random number generator
   */
  public static final void shuffle(final java.lang.Object[] array,
      final Random random) {
    final int length;

    if (array == null) {
      throw new IllegalArgumentException(//
          "Object array for shuffling must not be null."); //$NON-NLS-1$
    }

    if ((length = array.length) > 1) {
      Shuffle.__shuffle(array, length, 0, length, random);
    }
  }

  /**
   * This method does the work of randomly shuffling a sub-sequence of an
   * array or permutation of {@code bytes}. After this procedure, the
   * {@code count} elements of the array beginning at index {@code start}
   * are uniformly randomly distributed. This method therefore applies the
   * <a href="http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">
   * Fisher-Yates Shuffle</a> in the <a href=
   * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
   * >modern algorithm variant</a>.
   * 
   * @param array
   *          the array of {@code java.lang.Object}s whose sub-sequence to
   *          be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized: always &geq; 1
   * @param length
   *          the array length: always &geq; 1
   * @param random
   *          the random number generator
   */
  private static final void __shuffle(final java.lang.Object[] array,
      final int length, final int start, final int count,
      final Random random) {
    java.lang.Object temp;
    int index, firstItem, secondItem;

    index = count;
    firstItem = (start + index);
    if (firstItem >= length) {
      firstItem -= length;
    }

    do {
      if ((--firstItem) < 0) {
        firstItem += length;
      }
      if ((secondItem = (start + random.nextInt(index--))) >= length) {
        secondItem -= length;
      }

      temp = array[firstItem];
      array[firstItem] = array[secondItem];
      array[secondItem] = temp;

    } while (index > 0);
  }

  /** the forbidden constructor */
  private Shuffle() {
    ErrorUtils.doNotCall();
  }
}
