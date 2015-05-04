package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

/** The {@code IEEEtran} document class for conference proceedings. */
public final class IEEEtranConference extends _IEEEtranUsage {

  /** the {@code IEEEtran} document class */
  private static final IEEEtranConference INSTANCE = new IEEEtranConference();

  /** create */
  private IEEEtranConference() {
    super(new String[] { "conference" },// parameters //$NON-NLS-1$
        672d);// height
  }

  /**
   * get the globally shared instance of the {@code IEEEtran} document
   * class
   *
   * @return the {@code IEEEtran} document class
   */
  public static final IEEEtranConference getInstance() {
    return IEEEtranConference.INSTANCE;
  }
}
