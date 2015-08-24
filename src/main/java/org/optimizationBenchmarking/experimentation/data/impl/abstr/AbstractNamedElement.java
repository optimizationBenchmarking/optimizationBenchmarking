package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.INamedElement}
 * interface.
 */
public abstract class AbstractNamedElement extends DataElement implements
    INamedElement {

  /** Create the abstract named element. */
  protected AbstractNamedElement() {
    super();
  }

  /**
   * Validate and format a name.
   *
   * @param name
   *          the name
   * @return the formatted name
   */
  public static final String formatName(final String name) {
    final String ret;
    ret = TextUtils.normalize(name);
    if (name == null) {
      throw new IllegalArgumentException(//
          "The name cannot null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + name + "' is."); //$NON-NLS-1$
    }
    return ret;
  }

  /**
   * Validate and format a description
   *
   * @param description
   *          the description
   * @return the formatted description
   */
  public static final String formatDescription(final String description) {
    final String ret;
    ret = TextUtils.normalize(description);
    if (description == null) {
      throw new IllegalArgumentException(//
          "The description cannot null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + description + "' is."); //$NON-NLS-1$
    }
    return ret;
  }

  /**
   * Merge descriptions.
   *
   * @param descA
   *          description original description
   * @param descB
   *          description new description to add
   * @return the merged result
   */
  public static final String mergeDescriptions(final String descA,
      final String descB) {
    final int len1, len2;
    final String a, b;
    final char x;

    a = TextUtils.normalize(descA);
    b = TextUtils.normalize(descB);

    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }

    if (a.equalsIgnoreCase(b)) {
      return a;
    }

    len1 = a.length();
    if (len1 <= 0) {
      return b;
    }

    len2 = b.length();
    if (len2 <= 0) {
      return a;
    }

    x = a.charAt(len1 - 1);
    if (x <= ' ') { // this should never happen...
      return (a + b);
    }
    switch (x) {
      case '.':
      case '!':
      case '?':
      case ',':
      case ';':
      case ':':
      case '-': {
        return (a + ' ' + b);
      }
      default: {
        return ((a + '.') + ' ' + b);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IDataElement getOwner() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getPathComponentSuggestion() {
    return this.getName();
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final IMath out, final IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.getName(), out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.printShortName(this.getName(), textOut,
        textCase, true);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.printShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.printDescription(this.getDescription(),
        textOut, textCase);
  }
}
