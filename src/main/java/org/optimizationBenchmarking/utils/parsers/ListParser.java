package org.optimizationBenchmarking.utils.parsers;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A parser for a given type
 * 
 * @param <ET>
 *          the element type
 */
public class ListParser<ET> extends
    _CollectionParser<ET, ArrayListView<ET>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the string list parser */
  public static final ListParser<String> STRING_LIST_PARSER = new _StringListParser();

  /**
   * create the parser
   * 
   * @param elementParser
   *          the element parser
   */
  public ListParser(final Parser<ET> elementParser) {
    super(elementParser);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final ArrayListView<ET> _convertCollectionToResultCollection(
      final Collection<ET> collection) {
    final Object[] lst;

    if ((collection == null) || (collection.isEmpty()) || //
        ((lst = collection.toArray()) == null) || //
        (lst.length <= 0)) {//
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    return new ArrayListView(lst);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Class<ArrayListView<ET>> getOutputClass() {
    return ((Class) (ArrayListView.class));
  }

}
