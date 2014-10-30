package org.optimizationBenchmarking.utils.parsers;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * A parser for a given type
 * 
 * @param <ET>
 *          the element type
 */
public class SetParser<ET> extends _CollectionParser<ET, ArraySetView<ET>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared file set parser */
  public static final SetParser<Path> PATH_SET_PARSER = new _PathSetParser();

  /**
   * create the parser
   * 
   * @param elementParser
   *          the element parser
   */
  public SetParser(final Parser<ET> elementParser) {
    super(elementParser);
  }

  /** {@inheritDoc} */
  @Override
  final Collection<ET> _createGatheringCollection() {
    return new HashSet<>();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final ArraySetView<ET> _convertCollectionToResultCollection(
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
