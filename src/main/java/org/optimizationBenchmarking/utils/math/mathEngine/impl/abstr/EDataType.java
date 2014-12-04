package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

/** the data types that a math engine can handle */
public enum EDataType {

  /** {@code boolean} */
  BOOLEAN,
  /** {@code long} */
  LONG,
  /** {@code double} */
  DOUBLE,
  /**
   * a {@link org.optimizationBenchmarking.utils.math.matrix.IMatrix} with
   * only one row or column
   */
  VECTOR,
  /** {@link org.optimizationBenchmarking.utils.math.matrix.IMatrix} */
  MATRIX,
  /**
   * the type of the variable or expression is undefined, may be any of the
   * above
   */
  UNKNOWN;

}
