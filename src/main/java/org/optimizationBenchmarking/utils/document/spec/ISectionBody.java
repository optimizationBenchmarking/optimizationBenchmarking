package org.optimizationBenchmarking.utils.document.spec;

/**
 * The basic interface for blocks of texts.
 */
public interface ISectionBody extends IStructuredText, ISectionContainer {

  /**
   * Create a table in this block of text.
   *
   * @param useLabel
   *          a label to use for the table,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @param spansAllColumns
   *          will the table span all columns ({@code true}) or not?
   * @param cells
   *          the table cell definitions
   * @return the table object
   */
  public abstract ITable table(final ILabel useLabel,
      final boolean spansAllColumns, final ETableCellDef... cells);

  /**
   * Create a figure in this block of text.
   *
   * @param useLabel
   *          a label to use for the figure,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @param size
   *          the figure size
   * @param path
   *          a relative path (using '/' as separators) suggestion which
   *          may be used for creating an output path to store the document
   *          under, can also be {@code null} in which case a default path
   *          may be used
   * @return the figure object
   */
  public abstract IFigure figure(final ILabel useLabel,
      final EFigureSize size, final String path);

  /**
   * Create a series of figures in this block of text. The provided
   * relative {@code path} may be modified, resolved, or extended with a
   * suffix to create a graphics file, if necessary. It may also be ignored
   * entirely in cases where the document stores resources internally.
   *
   * @param useLabel
   *          a label to use for the figure,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @param size
   *          the size of the sub-figures
   * @param path
   *          a relative path (using '/' as separators) suggestion which
   *          may be used for creating an output path to store the document
   *          under, can also be {@code null} in which case a default path
   *          may be used
   * @return the figure series
   */
  public abstract IFigureSeries figureSeries(final ILabel useLabel,
      final EFigureSize size, final String path);

  /**
   * Create a code block in this block of text.
   *
   * @param useLabel
   *          a label to use for the code block,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @param spansAllColumns
   *          will the code span all columns ({@code true}) or not?
   * @return the code object
   */
  public abstract ICode code(final ILabel useLabel,
      final boolean spansAllColumns);

  /**
   * Create an equation
   *
   * @param useLabel
   *          a label to use for the equation,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @return the maths context to write the equation to
   */
  public abstract IEquation equation(final ILabel useLabel);

}
