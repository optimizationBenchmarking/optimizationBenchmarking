package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

/** The {@code IEEEtran} document class */
public final class IEEEtran extends _IEEEtranUsage {

  /** the {@code IEEEtran} document class */
  private static final IEEEtran INSTANCE = new IEEEtran();

  /** create */
  private IEEEtran() {
    super(null,// parameters
        696d);// height
  }

  /**
   * get the globally shared instance of the {@code IEEEtran} document
   * class
   *
   * @return the {@code IEEEtran} document class
   */
  public static final IEEEtran getInstance() {
    return IEEEtran.INSTANCE;
  }
}
