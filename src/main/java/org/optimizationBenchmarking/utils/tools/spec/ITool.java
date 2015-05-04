package org.optimizationBenchmarking.utils.tools.spec;

import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The entry interface to the tool API. Implementations of {@link ITool}
 * will usually be globally shared singleton objects which, upon their
 * instantiation, determine whether their associated modules, external
 * programs, libraries, or language features are present and set the return
 * value for {@code #canUse()} appropriately.
 */
public interface ITool extends ITextable {

  /**
   * This method returns {@code true} if the basic requirements for using
   * the tool are met. For instance, if the tool requires an external
   * program (or library), this method will only return {@code true} if the
   * program (or library) has successfully been located (or loaded). If the
   * tool cannot be used because whatever requirement for it cannot be met,
   * this method returns {@code false}.
   *
   * @return {@code true} if the tool can be used, {@code false} otherwise
   * @see #use()
   */
  public abstract boolean canUse();

  /**
   * Check if this tool can be used an throw a descriptive
   * {@link java.lang.UnsupportedOperationException} otherwise. In other
   * words: if {@link #canUse()} returns {@code false}, this method throws
   * an exception. If {@link #canUse()} returns {@code true}, this method
   * does nothing.
   *
   * @throws UnsupportedOperationException
   *           if {@link #canUse()} returns {@code false}.
   */
  public abstract void checkCanUse();

  /**
   * <p>
   * Create a builder object for a single job to be executed with this
   * tool. All parameters of the job will be set via the returned instance
   * of
   * {@link org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder},
   * whose
   * {@link org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder#create()}
   * method can then be used to create the job.
   * </p>
   * <p>
   * The return type
   * {@link org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder}
   * is just an abstract base interface. Implementations of {@link ITool}
   * can and will override this method to return builder objects, which
   * will allow setting more parameters and/or have a more complex
   * behavior.
   * </p>
   * <p>
   * If {@link #canUse()} returns {@code true}, this tool can be used by
   * invoking the method {@link #use()}. {@link #use()} will return an
   * object which represents a setup builder for single usage of the tool.
   * </p>
   *
   * @return a (builder) object representing a single usage of this tool
   * @throws UnsupportedOperationException
   *           if {@link #canUse()} returns {@code false}
   * @see #canUse()
   */
  public abstract IToolJobBuilder use();

  /**
   * Write the human-readable, unique name of this tool.
   *
   * @param textOut
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
   *          instance to which the textual representation should be
   *          written
   * @see #toString()
   */
  @Override
  public abstract void toText(final ITextOutput textOut);

  /**
   * Get the human-readable, unique name of this tool
   *
   * @return the human-readable, unique name of this tool
   * @see #toText(ITextOutput)
   */
  @Override
  public abstract String toString();
}
