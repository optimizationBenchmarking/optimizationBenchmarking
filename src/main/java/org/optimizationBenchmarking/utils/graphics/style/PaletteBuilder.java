package org.optimizationBenchmarking.utils.graphics.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.hierarchy.NormalizingFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.tokenizers.SeparatorBasedStringIterator;

/**
 * An object that can build a palette. A palette is an
 * {@link org.optimizationBenchmarking.utils.collections.lists.ArrayListView}
 * which contains objects of a given type, which, in turn, defines a
 * graphical appearance.
 * 
 * @param <ET>
 *          the element type
 * @param <PT>
 *          the palette type
 */
public abstract class PaletteBuilder<ET extends IStyle, PT extends Palette<ET>>
    extends BuilderFSM<PT> {

  /** do we process a header */
  private static final int FLAG_HAS_PROCESSED_HEADER = (FSM.FLAG_NOTHING + 1);
  /** do we have elements? */
  protected static final int FLAG_HAS_ELEMENTS = (PaletteBuilder.FLAG_HAS_PROCESSED_HEADER << 1);

  /** the elements */
  private ArrayList<ET> m_elements;
  /** the data */
  private HashMap<Object, Object> m_data;

  /**
   * Create the palette builder
   * 
   * @param owner
   *          the owner
   */
  protected PaletteBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.m_elements = new ArrayList<>();
    if (!(owner instanceof NormalizingFSM)) {
      this.m_data = new HashMap<>();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_PROCESSED_HEADER: {
        append.append("hasProcessedHeader"); //$NON-NLS-1$
        return;
      }
      case FLAG_HAS_ELEMENTS: {
        append.append("hasElements"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Add an element to this palette
   * 
   * @param element
   *          the element to add
   */
  public synchronized final void add(final ET element) {
    final ET t;
    final String s1;
    String s2;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    t = this.normalize(element);
    if (t == null) {
      throw new IllegalArgumentException(//
          "Palette element cannot be null."); //$NON-NLS-1$
    }

    s1 = t.toString();
    if ((s1 == null) || (s1.length() <= 0)) {
      throw new IllegalArgumentException(//
          "Palette element must not have empty name, but '" //$NON-NLS-1$
              + t + "' has.");//$NON-NLS-1$
    }

    this.beforeAdd(element);

    for (final ET x : this.m_elements) {
      if (EComparison.equals(x, t)) {
        throw new IllegalArgumentException(
            "Cannot add the same element twice to a palette, but '" + //$NON-NLS-1$
                t + "' equals the element '" + //$NON-NLS-1$
                x + "' which is already stored.");//$NON-NLS-1$
      }
      s2 = x.toString();
      if (String.CASE_INSENSITIVE_ORDER.compare(s1, s2.toString()) == 0) {
        throw new IllegalArgumentException(//
            "No two palette elements must have the same name, but '"//$NON-NLS-1$ 
                + s1 + "' and '" + s2 + "' do.");//$NON-NLS-1$//$NON-NLS-2$
      }
    }

    this.m_elements.add(t);
    this.fsmFlagsSet(PaletteBuilder.FLAG_HAS_ELEMENTS);
  }

  /**
   * This method is called before an element is added in order to provide a
   * final chance to check it
   * 
   * @param element
   *          the element to be added
   */
  protected void beforeAdd(final ET element) {
    //
  }

  /**
   * Add a list of element to this palette
   * 
   * @param elements
   *          the elements to add
   */
  public synchronized final void addAll(final Iterable<ET> elements) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    for (final ET t : elements) {
      this.add(t);
    }
  }

  /**
   * create the element builder
   * 
   * @return the builder
   */
  protected abstract PaletteElementBuilder<ET> createElementBuilder();

  /**
   * create a builder fsm
   * 
   * @return add the element
   */
  public synchronized PaletteElementBuilder<ET> add() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return this.createElementBuilder();
  }

  /**
   * Process any potential header information directly. This method is
   * called by {@link PaletteInputDriver}.
   * 
   * @param reader
   *          the reader to process
   * @throws IOException
   *           if something goes wrong
   */
  @SuppressWarnings("unused")
  protected synchronized void processHeader(final BufferedReader reader)
      throws IOException {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        PaletteBuilder.FLAG_HAS_PROCESSED_HEADER,
        PaletteBuilder.FLAG_HAS_PROCESSED_HEADER, FSM.STATE_NOTHING);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    if (!(child instanceof PaletteElementBuilder)) {
      this.throwChildNotAllowed(child);
    }
    super.beforeChildOpens(child, hasOtherChildren);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    if (!(child instanceof PaletteElementBuilder)) {
      this.throwChildNotAllowed(child);
    }
    super.afterChildOpened(child, hasOtherChildren);
  }

  /**
   * Process a finished and closed palette element builder
   * 
   * @param child
   *          the completed element builder
   */
  protected void processElementBuilder(
      final PaletteElementBuilder<ET> child) {
    this.add(child.getResult());
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    if (child instanceof PaletteElementBuilder) {
      this.processElementBuilder((PaletteElementBuilder<ET>) child);
      super.afterChildClosed(child);
    } else {
      this.throwChildNotAllowed(child);
    }
  }

  /**
   * Create the palette
   * 
   * @param data
   *          the data
   * @return the palette
   */
  protected abstract PT createPalette(final ArrayList<ET> data);

  /** {@inheritDoc} */
  @Override
  protected final PT compile() {
    final ArrayList<ET> l;

    this.fsmFlagsAssertTrue(PaletteBuilder.FLAG_HAS_ELEMENTS);

    l = this.m_elements;
    this.m_elements = null;
    this.m_data = null;

    return this.createPalette(l);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <T> T doNormalizePersistently(final T input) {
    Object r;

    r = input;
    if (this.m_data != null) {
      r = this.m_data.get(r);
      if (r == null) {
        r = input;
        this.m_data.put(r, r);
      }
    }
    return ((T) r);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    super.onClose();
    this.m_data = null;
    this.m_elements = null;
  }

  /**
   * Iterate over a string
   * 
   * @param s
   *          the string
   * @return the iterator
   */
  protected SeparatorBasedStringIterator iterate(final String s) {
    return new SeparatorBasedStringIterator(s, ',', true);
  }
}
