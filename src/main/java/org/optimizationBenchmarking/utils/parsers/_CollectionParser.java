package org.optimizationBenchmarking.utils.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.optimizationBenchmarking.utils.text.charset.Char;
import org.optimizationBenchmarking.utils.text.charset.Characters;
import org.optimizationBenchmarking.utils.text.charset.EnclosureEnd;

/**
 * A parser for a given type
 * 
 * @param <ET>
 *          the element type
 * @param <CT>
 *          the collection type
 */
abstract class _CollectionParser<ET, CT extends Collection<ET>> extends
    Parser<CT> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the list item separator */
  public static final String LIST_ITEM_SEPARATORS;

  static {
    if (File.pathSeparatorChar == ';') {
      LIST_ITEM_SEPARATORS = File.pathSeparator;
    } else {
      LIST_ITEM_SEPARATORS = (File.pathSeparator + ';');
    }
  }

  /** the element parser */
  private final Parser<ET> m_elementParser;

  /**
   * create the parser
   * 
   * @param elementParser
   *          the element parser
   */
  _CollectionParser(final Parser<ET> elementParser) {
    super();
    if (elementParser == null) {
      throw new IllegalArgumentException();
    }
    this.m_elementParser = elementParser;
  }

  /**
   * create the collection used for gathering the elements
   * 
   * @return the collection used for gathering the elements
   */
  Collection<ET> _createGatheringCollection() {
    return new ArrayList<>();
  }

  /**
   * Convert a collection to the result collection
   * 
   * @param collection
   *          the collection used for gathering elements
   * @return the result collection
   */
  @SuppressWarnings("unchecked")
  CT _convertCollectionToResultCollection(final Collection<ET> collection) {
    return ((CT) (collection));
  }

  /** {@inheritDoc} */
  @Override
  public final CT parseString(final String string) throws Exception {
    final Parser<ET> parser;
    final Collection<ET> list;
    final ArrayList<EnclosureEnd> enc;
    final String s;
    final int len;
    final CT res;
    Char chr;
    EnclosureEnd end;
    char ch;
    String temp;
    int i, j, encsize;
    boolean quit;

    s = StringParser.INSTANCE.parseString(string);
    len = s.length();

    list = this._createGatheringCollection();
    if (len > 0) {
      i = 0;
      parser = this.m_elementParser;
      enc = new ArrayList<>();

      looper: for (;;) {

        findNextEnd: for (j = i; j < len; j++) {
          ch = s.charAt(j);
          chr = Characters.CHARACTERS.getChar(ch);
          if (chr instanceof EnclosureEnd) {
            end = ((EnclosureEnd) chr);
            encsize = enc.size();
            if ((encsize > 0) && (end.canStartWith(enc.get(encsize - 1)))) {
              enc.remove(encsize - 1);
            } else {
              if (end.isOpening()) {
                enc.add(end);
              }
            }
          } else {
            if (enc.isEmpty()) {
              if (_CollectionParser.LIST_ITEM_SEPARATORS.indexOf(ch) >= 0) {
                break findNextEnd;
              }
            }
          }
        }

        quit = (j >= len);
        if (quit) {
          temp = (s.substring(i));
        } else {
          temp = (s.substring(i, j));
        }

        list.add(parser.parseString(temp));

        if (quit) {
          break looper;
        }
        i = (j + 1);
      }
    }

    res = this._convertCollectionToResultCollection(list);
    this.validate(res);
    return res;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final CT parseObject(final Object o) throws Exception {
    final Class<CT> clazz;
    final CT res;

    clazz = this.getOutputClass();
    if (clazz.isInstance(o)) {
      res = clazz.cast(o);
    } else {
      if (o instanceof Collection) {
        res = this
            ._convertCollectionToResultCollection((Collection<ET>) o);
      } else {
        return this.parseString(String.valueOf(o));
      }
    }
    this.validate(res);
    return res;
  }

}
