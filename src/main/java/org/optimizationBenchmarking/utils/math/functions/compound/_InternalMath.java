package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
import org.optimizationBenchmarking.utils.document.spec.IText;

/** the internal maths context for checking whether braces are in order */
final class _InternalMath implements IMath {

  /** the internal math */
  private final IMath m_math;

  /** the renderer to update with information */
  final _CompoundParameterRendererBase m_renderer;

  /**
   * create
   *
   * @param math
   *          the wrapped maths context
   * @param renderer
   *          the renderer to update with information
   */
  _InternalMath(final IMath math,
      final _CompoundParameterRendererBase renderer) {
    if (math == null) {
      throw new IllegalArgumentException(//
          "Wrapped maths context cannot be null."); //$NON-NLS-1$
    }

    this.m_math = math;
    this.m_renderer = renderer;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    //
  }

  /** {@inheritDoc} */
  @Override
  public final IMath inBraces() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.inBraces();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath add() {
    return this.m_math.add();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath sub() {
    return this.m_math.sub();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath mul() {
    return this.m_math.mul();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath div() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.div();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath divInline() {
    return this.m_math.divInline();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath mod() {
    return this.m_math.mod();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath log() {
    return this.m_math.log();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath ln() {
    return this.m_math.ln();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath ld() {
    return this.m_math.ld();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath lg() {
    return this.m_math.lg();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath pow() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.pow();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath root() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.root();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath sqrt() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.sqrt();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath compare(final EMathComparison cmp) {
    return this.m_math.compare(cmp);
  }

  /** {@inheritDoc} */
  @Override
  public final IMath negate() {
    return this.m_math.negate();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath abs() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.abs();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath factorial() {
    return this.m_math.factorial();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath sin() {
    return this.m_math.sin();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath cos() {
    return this.m_math.cos();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath tan() {
    return this.m_math.tan();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath nAryFunction(final String name, final int minArity,
      final int maxArity) {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.nAryFunction(name, minArity, maxArity);
  }

  /** {@inheritDoc} */
  @Override
  public final IComplexText text() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.text();
  }

  /** {@inheritDoc} */
  @Override
  public final IMathName name() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.name();
  }

  /** {@inheritDoc} */
  @Override
  public final IText number() {
    // could be negative...
    return this.m_math.number();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath cbrt() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.cbrt();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath exp() {
    return this.m_math.exp();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath sqr() {
    return this.m_math.sqr();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath cube() {
    return this.m_math.cube();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath max() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.max();
  }

  /** {@inheritDoc} */
  @Override
  public final IMath min() {
    this.m_renderer.m_bracesNotNeeded = true;
    return this.m_math.min();
  }
}
