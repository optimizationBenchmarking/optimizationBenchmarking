package org.optimizationBenchmarking.utils.math.random;

import java.util.Random;

/**
 * A base class for random number generators which is compatible to
 * {@link java.util.Random} and provides some additional functions.
 */
public class BasicRandom extends Random {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create!
   * 
   * @param seed
   *          the seed to be handed to the {@link java.util.Random}
   */
  protected BasicRandom(final long seed) {
    super(seed);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code bytes}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code byte}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final byte[] array, final int start,
      final int count) {
    final int n;
    byte t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code bytes}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code byte}s that should be randomized
   */
  public final void shuffle(final byte[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code shorts}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code short}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final short[] array, final int start,
      final int count) {
    final int n;
    short t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code shorts}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code short}s that should be randomized
   */
  public final void shuffle(final short[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code ints}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code int}s whose sub-sequence to be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final int[] array, final int start,
      final int count) {
    final int n;
    int i, j, k, t;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code ints}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code int}s that should be randomized
   */
  public final void shuffle(final int[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code longs}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code long}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final long[] array, final int start,
      final int count) {
    final int n;
    long t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code longs}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code long}s that should be randomized
   */
  public final void shuffle(final long[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code floats}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code float}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final float[] array, final int start,
      final int count) {
    final int n;
    float t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code floats}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code float}s that should be randomized
   */
  public final void shuffle(final float[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code doubles}
   * . After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code double}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final double[] array, final int start,
      final int count) {
    final int n;
    double t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code doubles}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code double}s that should be randomized
   */
  public final void shuffle(final double[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of {@code chars}.
   * After this procedure, the {@code count} elements of the array
   * beginning at index {@code start} are uniformly randomly distributed.
   * 
   * @param array
   *          the array of {@code char}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final char[] array, final int start,
      final int count) {
    final int n;
    char t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code chars}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code char}s that should be randomized
   */
  public final void shuffle(final char[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of
   * {@code booleans}. After this procedure, the {@code count} elements of
   * the array beginning at index {@code start} are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code boolean}s whose sub-sequence to be
   *          randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final boolean[] array, final int start,
      final int count) {
    final int n;
    boolean t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code booleans}. After this
   * procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code boolean}s that should be randomized
   */
  public final void shuffle(final boolean[] array) {
    this.shuffle(array, 0, array.length);
  }

  /**
   * Randomize a sub-sequence of an array or permutation of
   * {@code java.lang.Objects}. After this procedure, the {@code count}
   * elements of the array beginning at index {@code start} are uniformly
   * randomly distributed.
   * 
   * @param array
   *          the array of {@code java.lang.Object}s whose sub-sequence to
   *          be randomized
   * @param start
   *          the start index
   * @param count
   *          the number of elements to be randomized
   */
  public final void shuffle(final Object[] array, final int start,
      final int count) {
    final int n;
    Object t;
    int i, j, k;

    if (count > 0) {
      n = array.length;
      for (i = count; (--i) > 0;) {
        j = ((start + this.nextInt(i + 1)) % n);
        k = ((start + i) % n);
        t = array[k];
        array[k] = array[j];
        array[j] = t;
      }
    }
  }

  /**
   * Randomize an array or permutation of {@code java.lang.Objects}. After
   * this procedure, the elements of the array are uniformly randomly
   * distributed.
   * 
   * @param array
   *          the array of {@code java.lang.Object}s that should be
   *          randomized
   */
  public final void shuffle(final java.lang.Object[] array) {
    this.shuffle(array, 0, array.length);
  }
}