package org.optimizationBenchmarking.utils.math.statistics;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;

/** A statistic parameter. */
public abstract class StatisticParameter extends MathematicalFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  protected StatisticParameter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getMinArity() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public final int getMaxArity() {
    return Integer.MAX_VALUE;
  }

  /**
   * Create an aggregate which can be used to compute this parameter from a
   * sequence of numbers
   * 
   * @return the aggregate
   */
  public abstract ScalarAggregate createAggregate();

  /** {@inheritDoc} */
  @Override
  public double compute(final double... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final double d : x) {
      ag.append(d);
    }
    return ag.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public byte compute(final byte... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final byte d : x) {
      ag.append(d);
    }
    return ag.byteValue();
  }

  /** {@inheritDoc} */
  @Override
  public short compute(final short... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final short d : x) {
      ag.append(d);
    }
    return ag.shortValue();
  }

  /** {@inheritDoc} */
  @Override
  public int compute(final int... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final int d : x) {
      ag.append(d);
    }
    return ag.intValue();
  }

  /** {@inheritDoc} */
  @Override
  public long compute(final long... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final long d : x) {
      ag.append(d);
    }
    return ag.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public float compute(final float... x) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    for (final float d : x) {
      ag.append(d);
    }
    return ag.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public double computeOverColumn(final IMatrix matrix, final int column) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    matrix.aggregateColumn(column, ag);
    return ag.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public double computeOverRow(final IMatrix matrix, final int row) {
    final ScalarAggregate ag;

    ag = this.createAggregate();
    matrix.aggregateRow(row, ag);
    return ag.doubleValue();
  }
}
