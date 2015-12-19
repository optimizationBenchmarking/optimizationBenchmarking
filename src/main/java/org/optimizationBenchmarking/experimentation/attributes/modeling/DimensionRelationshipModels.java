package org.optimizationBenchmarking.experimentation.attributes.modeling;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.ml.fitting.models.ExponentialDecayModel;
import org.optimizationBenchmarking.utils.ml.fitting.models.LogisticModelWithOffsetOverLogX;
import org.optimizationBenchmarking.utils.ml.fitting.models.QuadraticModel;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/** This class provides models for relationships between dimensions. */
public final class DimensionRelationshipModels {

  /** the hidden constructor */
  private DimensionRelationshipModels() {
    ErrorUtils.doNotCall();
  }

  /**
   * Check whether the given modeling constellation is viable
   *
   * @param isXTime
   *          is the {@code x}-dimension a time dimension ({@code true}) or
   *          an objective dimension ({@code false})
   * @param isYTime
   *          is the {@code y}-dimension a time dimension ({@code true}) or
   *          an objective dimension ({@code false})
   */
  static final void _checkDimensions(final boolean isXTime,
      final boolean isYTime) {
    if (isYTime && (!isXTime)) {
      throw new IllegalArgumentException(//
          "Only time-objective, objective-objective, and time-time relatioships can be modeled, but you specified an objective-time relationship."); //$NON-NLS-1$
    }
  }

  /**
   * Obtain the models for a given dimension relationship
   *
   * @param x
   *          the input dimension
   * @param y
   *          the output dimension
   * @return the list of models
   */
  public static final ArrayListView<ParametricUnaryFunction> getModels(
      final IDimension x, final IDimension y) {
    return DimensionRelationshipModels.getModels(
        x.getDimensionType().isTimeMeasure(),
        y.getDimensionType().isTimeMeasure());
  }

  /**
   * Obtain the models for a given dimension relationship
   *
   * @param isXTime
   *          is the {@code x}-dimension a time dimension ({@code true}) or
   *          an objective dimension ({@code false})
   * @param isYTime
   *          is the {@code y}-dimension a time dimension ({@code true}) or
   *          an objective dimension ({@code false})
   * @return the list of models
   */
  public static final ArrayListView<ParametricUnaryFunction> getModels(
      final boolean isXTime, final boolean isYTime) {
    DimensionRelationshipModels._checkDimensions(isXTime, isYTime);
    if (isXTime == isYTime) {
      return __EqualType.MODELS;
    }
    return __TimeObjective.MODELS;
  }

  /** the time-objective relationship holder */
  private static final class __TimeObjective {

    /** the models */
    static final ArrayListView<ParametricUnaryFunction> MODELS = //
    new ArrayListView<>(new ParametricUnaryFunction[] { //
        new LogisticModelWithOffsetOverLogX(), //
        new ExponentialDecayModel(),//
    });
  }

  /** the holder for relationships of equally-typed dimensions */
  private static final class __EqualType {

    /** The models attempted for relationships of equal-type dimensions */
    static final ArrayListView<ParametricUnaryFunction> MODELS = //
    new ArrayListView<>(new ParametricUnaryFunction[] { //
        new QuadraticModel(),//
    });
  }

}
