package test.junit.org.optimizationBenchmarking.utils.math.random;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.math.random.Shuffle;

/**
 * A test for shuffling numbers.
 */
public class ShuffleTest {

  /** the constructor */
  public ShuffleTest() {
    super();
  }

  /** test shuffling of byte arrays */
  @Test(timeout = 3600000)
  public void testByteArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    byte[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new byte[arrayLength];
      arrayB = new byte[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        rand.nextBytes(arrayA);

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertEquals(arrayA[testIndex],//
                      arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of short arrays */
  @Test(timeout = 3600000)
  public void testShortArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    short[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new short[arrayLength];
      arrayB = new short[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = ((short) ((rand.nextInt() & 0xffff)));
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertEquals(arrayA[testIndex],//
                      arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of int arrays */
  @Test(timeout = 3600000)
  public void testIntegerArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    int[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new int[arrayLength];
      arrayB = new int[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = rand.nextInt();
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertEquals(arrayA[testIndex],//
                      arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of long arrays */
  @Test(timeout = 3600000)
  public void testLongArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    long[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new long[arrayLength];
      arrayB = new long[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = rand.nextLong();
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertEquals(arrayA[testIndex],//
                      arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of float arrays */
  @Test(timeout = 3600000)
  public void testFloatArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    float[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new float[arrayLength];
      arrayB = new float[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = rand.nextFloat();
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertTrue(//
                      arrayA[testIndex] == arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of double arrays */
  @Test(timeout = 3600000)
  public void testDoubleArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    double[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new double[arrayLength];
      arrayB = new double[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = rand.nextDouble();
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertTrue(//
                      arrayA[testIndex] == arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of char arrays */
  @Test(timeout = 3600000)
  public void testCharArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    char[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new char[arrayLength];
      arrayB = new char[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = ((char) (rand.nextInt() & 0xffff));
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert
                      .assertEquals(arrayA[testIndex], arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of boolean arrays */
  @Test(timeout = 3600000)
  public void testBooleanArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    boolean[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new boolean[arrayLength];
      arrayB = new boolean[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = rand.nextBoolean();
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertTrue(//
                      arrayA[testIndex] == arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }

  /** test shuffling of Object arrays */
  @Test(timeout = 3600000)
  public void testObjectArrayShuffleDoesNotCrashAndDoesNotModifyUnrelatedContent() {
    final Random rand;
    Object[] arrayA, arrayB;
    int arrayLength, loopEnd, startIndex, count, arrayIndex, testLoop, testIndex;

    rand = new Random();

    for (arrayLength = 0; arrayLength <= 200; arrayLength++) {
      arrayA = new Object[arrayLength];
      arrayB = new Object[arrayLength];

      for (arrayIndex = 0; arrayIndex < 10; arrayIndex++) {
        for (count = arrayLength; (--count) >= 0;) {
          arrayA[count] = RandomUtils.longToObject(rand.nextLong(), true);
        }

        for (startIndex = 0; startIndex < arrayLength; startIndex++) {

          loopEnd = ((2 * arrayLength) + 5);
          for (count = (-loopEnd); count <= loopEnd; count++) {
            System.arraycopy(arrayA, 0, arrayB, 0, arrayLength);
            Shuffle.shuffle(arrayB, startIndex, count, rand);

            if ((count >= (-1)) && (count <= 1)) {
              Assert.assertTrue(Arrays.equals(arrayA, arrayB));
            } else {
              if ((count > (-arrayLength)) && (count < arrayLength)) {
                for (testLoop = (arrayLength - Math.abs(count)); (--testLoop) >= 0;) {
                  if (count > 0) {
                    testIndex = (testLoop + startIndex + count);
                  } else {
                    testIndex = ((startIndex + count) - testLoop);
                  }
                  testIndex = ((((testIndex + arrayLength) % arrayLength) + arrayLength) % arrayLength);
                  Assert.assertSame(arrayA[testIndex], arrayB[testIndex]);
                }
              }
            }
          }
        }
      }
    }
  }
}
