package org.optimizationBenchmarking.utils.ml.fitting.impl;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
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

  /** No fitter was found. */
  private static final void __noFitter() {
    throw new IllegalStateException("No useable function fitter found."); //$NON-NLS-1$
  }

  /**
   * Get the default function fitter
   *
   * @return the default function fitter
   * @throws IllegalStateException
   *           if no working fitter exists
   */
  public static final IFunctionFitter getInstance() {
    final IFunctionFitter inst;
    inst = __DefaultHolder.INSTANCE;
    if (inst == null) {
      DefaultFunctionFitter.__noFitter();
    }
    return inst;
  }

  /**
   * Get the available function fitter instances
   *
   * @return the available function fitter instances
   * @throws IllegalStateException
   *           if no working fitter exists
   */
  public static final ArrayListView<IFunctionFitter> getAllInstance() {
    final ArrayListView<IFunctionFitter> fitters;
    fitters = __AllHolder.INSTANCES;
    if (fitters == null) {
      DefaultFunctionFitter.__noFitter();
    }
    return fitters;
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
  private static final class __AllHolder {

    /** the instances */
    static final ArrayListView<IFunctionFitter> INSTANCES;

    static {
      IFunctionFitter fitter;

      fitter = DefaultFunctionFitter.getInstance();
      if (fitter != null) {
        INSTANCES = new ArrayListView<>(new IFunctionFitter[] { fitter });
      } else {
        INSTANCES = null;
      }
    }
  }
}
