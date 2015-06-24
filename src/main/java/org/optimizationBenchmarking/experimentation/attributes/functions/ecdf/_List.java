package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.attributes.functions.TransformationFunction;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

/** a list */
abstract class _List {

  /** the size */
  int m_size;

  /** the total number of potential elements */
  int m_total;

  /** the time index */
  final int m_timeIndex;

  /** the goal index */
  final int m_goalIndex;

  /** the time dimension */
  final IDimension m_timeDim;

  /** is the time an increasing dimension ? */
  final boolean m_isTimeIncreasing;

  /** the goal criterion */
  final EComparison m_criterion;

  /** the goal transformation */
  final UnaryFunction m_goalTransform;

  /**
   * do we need to use the {@link #m_goalTransform goal transformation} and
   * {@link #m_criterion criterion}?
   */
  final boolean m_useGoalTransformAndCriterion;

  /**
   * create the list
   *
   * @param timeDim
   *          the time dimension
   * @param goalDim
   *          the goal dimension
   * @param criterion
   *          the goal criterion
   * @param goalTransform
   *          the goal transformation
   */
  _List(final IDimension timeDim, final IDimension goalDim,
      final EComparison criterion,
      final TransformationFunction goalTransform) {
    super();
    this.m_timeIndex = timeDim.getIndex();
    this.m_goalIndex = goalDim.getIndex();
    this.m_timeDim = timeDim;
    this.m_isTimeIncreasing = timeDim.getDirection().isIncreasing();

    setTransform: {

      if (goalTransform.isIdentityTransformation()) {
        setNoTransform: {
          switcher: switch (criterion) {
            case LESS_OR_EQUAL: {
              if (goalDim.getDirection().isIncreasing()) {
                break setNoTransform;
              }
              break switcher;
            }
            case GREATER_OR_EQUAL: {
              if (goalDim.getDirection().isIncreasing()) {
                break switcher;
              }
              break setNoTransform;
            }
            default: {
              break setNoTransform;
            }
          }

          this.m_useGoalTransformAndCriterion = false;
          this.m_goalTransform = null;
          this.m_criterion = null;
          break setTransform;
        }
      }

      this.m_useGoalTransformAndCriterion = true;
      this.m_goalTransform = goalTransform;
      this.m_criterion = criterion;
    }
  }

  /**
   * add a run
   *
   * @param run
   *          the run to add
   */
  abstract void _addRun(final IRun run);

  /**
   * Convert the data to a matrix
   *
   * @param timeTransform
   *          the time transformation
   * @param resultTransform
   *          the result transformation
   * @return the matrix
   */
  abstract DoubleMatrix1D _toMatrix(final UnaryFunction timeTransform,
      final UnaryFunction resultTransform);
}
