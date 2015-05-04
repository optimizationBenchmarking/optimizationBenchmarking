package org.optimizationBenchmarking.utils.parsers;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A parser for sets
 *
 * @param <ET>
 *          the element type
 */
public class SetParser<ET> extends CollectionParser<ET, ArraySetView<ET>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create the parser
   *
   * @param elementParser
   *          the element parser
   * @param ignoreNull
   *          should {@code null} elements be ignored ({@code true}) or
   *          added ({@code false})?
   */
  protected SetParser(final Parser<ET> elementParser,
      final boolean ignoreNull) {
    super(elementParser, ignoreNull, true);
  }

  /** {@inheritDoc} */
  @Override
  protected final Collection<ET> createGatheringCollection() {
    return new LinkedHashSet<>();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected final ArraySetView<ET> convertCollectionToResultCollection(
      final Collection<ET> collection) {
    final Object[] lst;

    if ((collection == null) || (collection.isEmpty()) || //
        ((lst = collection.toArray()) == null) || //
        (lst.length <= 0)) {//
      return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
    }

    return new ArraySetView(lst);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Class<ArraySetView<ET>> getOutputClass() {
    return ((Class) (ArraySetView.class));
  }

}
