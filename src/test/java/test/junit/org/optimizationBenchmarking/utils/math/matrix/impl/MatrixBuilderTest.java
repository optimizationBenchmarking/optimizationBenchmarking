package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

import test.junit.TestBase;

/** A matrix builder test */
public class MatrixBuilderTest extends TestBase {

  /** create */
  public MatrixBuilderTest() {
    super();
  }

  /** test building a matrix */
  @Test(timeout = 3600000)
  public final void testIntegerMatrixBuilding() {
    final Random rand;
    long[][] data;
    boolean hasM, hasN;
    int testIt, i, j, curType, size, intVal;
    long longVal;
    MatrixBuilder builder;
    IMatrix result;
    byte byteVal;
    short shortVal;

    rand = new Random();
    for (testIt = 1; testIt <= 50000; testIt++) {

      data = new long[rand.nextBoolean() ? 1 : (1 + rand.nextInt(1000))]//
      [rand.nextBoolean() ? 1 : (1 + rand.nextInt(1000))];
      size = (data.length * data[0].length);
      builder = new MatrixBuilder(EPrimitiveType.BYTE);
      curType = 1;
      hasM = hasN = false;
      for (i = 0; i < data.length; i++) {
        for (j = 0; j < data[i].length; j++) {

          switch (rand.nextInt(curType)) {
            case 0: {
              byteVal = (byte) (rand.nextInt() & 0xff);
              data[i][j] = byteVal;
              builder.append(byteVal);
              break;
            }
            case 1: {
              shortVal = (short) (rand.nextInt() & 0xffff);
              data[i][j] = shortVal;
              builder.append(shortVal);
              break;
            }
            case 2: {
              intVal = rand.nextInt();
              data[i][j] = intVal;
              builder.append(intVal);
              break;
            }
            default: {
              longVal = rand.nextLong();
              data[i][j] = longVal;
              builder.append(longVal);
              break;
            }
          }

          if ((curType <= 4) && (rand.nextInt(size) <= 1)) {
            curType++;
          }
          if ((!hasM) && (rand.nextInt(size) <= 0)) {
            builder.setM(data.length);
            hasM = true;
          }
          if ((!hasN) && (rand.nextInt(size) <= 0)) {
            builder.setN(data[i].length);
            hasN = true;
          }
        }
      }

      if ((!hasM) && (!hasN)) {
        switch (rand.nextInt(3)) {
          case 0: {
            builder.setM(data.length);
            break;
          }
          case 1: {
            builder.setN(data[0].length);
            break;
          }
          default: {
            builder.setM(data.length);
            builder.setN(data[0].length);
          }
        }
      }

      result = builder.make();
      Assert.assertEquals(data.length, result.m());
      Assert.assertEquals(data[0].length, result.n());
      for (i = data.length; (--i) >= 0;) {
        for (j = data[0].length; (--j) >= 0;) {
          Assert.assertEquals(data[i][j], result.getLong(i, j));
        }
      }
    }
  }

  /** test building a matrix */
  @Test(timeout = 3600000)
  public final void testFloatMatrixBuilding() {
    final Random rand;
    double[][] data;
    boolean hasM, hasN;
    int testIt, i, j, curType, size, intVal, int2;
    MatrixBuilder builder;
    IMatrix result;
    byte byteVal, byte2;
    short shortVal, short2;
    float floatVal, float2;
    double doubleVal;
    long longVal, long2;

    rand = new Random();
    for (testIt = 1; testIt <= 30000; testIt++) {

      data = new double[rand.nextBoolean() ? 1 : (1 + rand.nextInt(1000))]//
      [rand.nextBoolean() ? 1 : (1 + rand.nextInt(1000))];
      size = (data.length * data[0].length);
      builder = new MatrixBuilder(EPrimitiveType.BYTE);
      curType = 1;
      hasM = hasN = false;
      for (i = 0; i < data.length; i++) {
        for (j = 0; j < data[i].length; j++) {

          switch (rand.nextInt(curType)) {
            case 0: {
              do {
                byteVal = (byte) (rand.nextInt() & 0xff);
                data[i][j] = byteVal;
                byte2 = ((byte) (data[i][j]));
              } while ((byteVal != byte2) || (data[i][j] != byteVal));
              builder.append(byteVal);
              break;
            }
            case 1: {
              do {
                shortVal = (short) (rand.nextInt() & 0xffff);
                data[i][j] = shortVal;
                short2 = ((short) (data[i][j]));
              } while ((shortVal != short2) || (data[i][j] != shortVal));
              builder.append(shortVal);
              break;
            }
            case 2: {
              do {
                intVal = rand.nextInt();
                data[i][j] = intVal;
                int2 = ((int) (data[i][j]));
              } while ((int2 != intVal) || (data[i][j] != intVal));
              builder.append(intVal);
              break;
            }
            case 3: {
              do {
                longVal = rand.nextLong();
                data[i][j] = longVal;
                long2 = ((long) (data[i][j]));
              } while ((longVal != long2) || (data[i][j] != longVal));
              builder.append(longVal);
              break;
            }
            case 4: {
              do {
                floatVal = (rand.nextBoolean() ? (rand.nextInt() / 32f)
                    : ((float) (rand.nextDouble())));
                data[i][j] = floatVal;
                float2 = ((float) (data[i][j]));
              } while ((floatVal != float2) || (data[i][j] != floatVal));
              builder.append(floatVal);
              break;
            }
            default: {
              doubleVal = (rand.nextBoolean() ? (rand.nextLong() / 32d)
                  : rand.nextDouble());
              data[i][j] = doubleVal;
              builder.append(doubleVal);
              break;
            }
          }

          if ((curType <= 6) && (rand.nextInt(size) <= 1)) {
            curType = (1 + rand.nextInt(6));
          }
          if ((!hasM) && (rand.nextInt(size) <= 0)) {
            builder.setM(data.length);
            hasM = true;
          }
          if ((!hasN) && (rand.nextInt(size) <= 0)) {
            builder.setN(data[i].length);
            hasN = true;
          }
        }
      }

      if ((!hasM) && (!hasN)) {
        switch (rand.nextInt(3)) {
          case 0: {
            builder.setM(data.length);
            break;
          }
          case 1: {
            builder.setN(data[0].length);
            break;
          }
          default: {
            builder.setM(data.length);
            builder.setN(data[0].length);
          }
        }
      }

      result = builder.make();
      Assert.assertEquals(data.length, result.m());
      Assert.assertEquals(data[0].length, result.n());
      for (i = data.length; (--i) >= 0;) {
        for (j = data[0].length; (--j) >= 0;) {
          Assert.assertEquals(data[i][j], result.getDouble(i, j), 0d);
        }
      }
    }
  }
}
