package org.optimizationBenchmarking.utils.parsers;

import java.nio.file.Path;
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
CollectionParser<ET, ArrayListView<ET>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the string list parser */
  public static final ListParser<String> STRING_LIST_PARSER = new _StringListParser();

  /** the globally shared file set parser */
  public static final ListParser<Path> PATH_LIST_PARSER = new _PathListParser();

  /**
   * create the parser
   *
   * @param elementParser
   *          the element parser
   * @param ignoreNull
   *          should {@code null} elements be ignored ({@code true}) or
   *          added ( {@code false})?
   * @param unique
   *          are all the elements unique?
   */
  public ListParser(final Parser<ET> elementParser,
      final boolean ignoreNull, final boolean unique) {
    super(elementParser, ignoreNull, unique);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected final ArrayListView<ET> convertCollectionToResultCollection(
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
