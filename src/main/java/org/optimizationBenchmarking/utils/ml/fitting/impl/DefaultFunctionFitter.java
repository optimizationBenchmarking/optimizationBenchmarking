package org.optimizationBenchmarking.utils.ml.fitting.impl;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.ml.fitting.impl.lssimplex.LSSimplexFitter;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;

/**
 * The default function fitter.
 */
public final class DefaultFunctionFitter {

  /** the forbidden constructor */
  private DefaultFunctionFitter() {
    ErrorUtils.doNotCall();
  }

  /**
   * Get the default function fitter
   *
   * @return the default function fitter
   */
  public static final IFunctionFitter getInstance() {
    return __DefaultHolder.INSTANCE;
  }

  /**
   * Get the available function fitter instances
   *
   * @return the available function fitter instances
   */
  public static final ArrayListView<IFunctionFitter> getAvailableInstance() {
    return __AllHolder.INSTANCES;
  }

  /** the internal holder for the default fitter */
  private static final class __DefaultHolder {

    /** the fitter */
    static final IFunctionFitter INSTANCE;

    static {
      IFunctionFitter cur;

      cur = LSSimplexFitter.getInstance();
      if ((cur != null) && (cur.canUse())) {
        INSTANCE = cur;
      } else {
        INSTANCE = null;
      }
    }
  }

  /** the internal holder for all available fitters */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final class __AllHolder {

    /** the instances */
    static final ArrayListView<IFunctionFitter> INSTANCES;

    static {
      IFunctionFitter fitter;

      fitter = DefaultFunctionFitter.getInstance();
      if (fitter != null) {
        INSTANCES = new ArrayListView<>(new IFunctionFitter[] { fitter });
      } else {
        INSTANCES = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
      }
    }
  }
}
