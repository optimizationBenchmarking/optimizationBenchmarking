package org.optimizationBenchmarking.utils.text.charset;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** A collection of braces. */
public final class Braces extends _Enclosure<Brace> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  static {
    // make sure that the character set is initialized first
    if (Characters.CHARACTERS != null) {
      Characters.CHARACTERS.isEmpty();
    }
  }

  /** parenthesis */
  public static final Braces PARENTHESES = new Braces(
      Characters.BRACE_PARENTHESES_OPEN,
      Characters.BRACE_PARENTHESES_CLOSE);
  /** brackets */
  public static final Braces BRACKETS = new Braces(
      Characters.BRACE_BRACKET_OPEN, Characters.BRACE_BRACKET_CLOSE);
  /** braces */
  public static final Braces CURLY_BRACES = new Braces(
      Characters.BRACE_BRACES_OPEN, Characters.BRACE_BRACES_CLOSE);
  /** pipe braces */
  public static final Braces PIPE = new Braces(Characters.BRACE_PIPE,
      Characters.BRACE_PIPE);
  /** pipe braces */
  public static final Braces DOUBLE_PIPE = new Braces(
      Characters.BRACE_DOUBLE_PIPE, Characters.BRACE_DOUBLE_PIPE);
  /** vertical */
  public static final Braces VERTICAL = new Braces(
      Characters.BRACE_VERTICAL_OPEN, Characters.BRACE_VERTICAL_CLOSE);
  /** with dash */
  public static final Braces BRACKETS_WITH_DASH = new Braces(
      Characters.BRACE_BRACKET_WITH_DASH_OPEN,
      Characters.BRACE_BRACKET_WITH_DASH_CLOSE);
  /** raised parentheses */
  public static final Braces RAISED_PARENTHESES = new Braces(
      Characters.BRACE_RAISED_PARENTHESES_OPEN,
      Characters.BRACE_RAISED_PARENTHESES_CLOSE);
  /** sunk parentheses */
  public static final Braces SUNK_PARENTHESES = new Braces(
      Characters.BRACE_SUNK_PARENTHESES_OPEN,
      Characters.BRACE_SUNK_PARENTHESES_CLOSE);
  /** angle braces */
  public static final Braces ANGLE = new Braces(
      Characters.BRACE_ANGLE_PRIMITIVE_SINGLE_OPEN,
      Characters.BRACE_ANGLE_PRIMITIVE_SINGLE_CLOSE);
  /** chevron */
  public static final Braces CHEVRON = new Braces(
      Characters.BRACE_CHEVRON_OPEN, Characters.BRACE_CHEVRON_CLOSE);
  /** double angle braces */
  public static final Braces DOUBLE_ANGLE = new Braces(
      Characters.BRACE_DOUBLE_ANGLE_OPEN,
      Characters.BRACE_DOUBLE_ANGLE_CLOSE);
  /** triple angle braces */
  public static final Braces TRIPLE_ANGLE = new Braces(
      Characters.BRACE_TRIPLE_ANGLE_OPEN,
      Characters.BRACE_TRIPLE_ANGLE_CLOSE);
  /** ceiling braces */
  public static final Braces CEILING = new Braces(
      Characters.BRACE_CEILING_OPEN, Characters.BRACE_CEILING_CLOSE);
  /** floor braces */
  public static final Braces FLOOR = new Braces(
      Characters.BRACE_FLOOR_OPEN, Characters.BRACE_FLOOR_CLOSE);

  /** chinese black parentheses */
  public static final Braces CHINESE_BLACK_PARENTHESES = new Braces(
      Characters.BRACE_CHINESE_BLACK_PARENTHESES_OPEN,
      Characters.BRACE_CHINESE_BLACK_PARENTHESES_CLOSE);

  /** chinese convex parentheses */
  public static final Braces CHINESE_CONVEX = new Braces(
      Characters.BRACE_CHINESE_CONVEX_OPEN,
      Characters.BRACE_CHINESE_CONVEX_CLOSE);

  /** chinese white parentheses */
  public static final Braces CHINESE_WHITE_PARENTHESES = new Braces(
      Characters.BRACE_CHINESE_WHITE_PARENTHESES_OPEN,
      Characters.BRACE_CHINESE_WHITE_PARENTHESES_CLOSE);

  /** chinese double convex parentheses */
  public static final Braces CHINESE_DOUBLE_CONVEX = new Braces(
      Characters.BRACE_CHINESE_DOUBLE_CONVEX_OPEN,
      Characters.BRACE_CHINESE_DOUBLE_CONVEX_CLOSE);

  /** chinese double braces */
  public static final Braces CHINESE_DOUBLE_BRACES = new Braces(
      Characters.BRACE_CHINESE_DOUBLE_BRACES_OPEN,
      Characters.BRACE_CHINESE_DOUBLE_BRACES_CLOSE);

  /** the default brace to use */
  public static final Braces DEFAULT = Braces.PARENTHESES;

  /** the square brackets */
  public static final Braces SQUARE_BRACKETS = Braces.BRACKETS;
  /** the angle brackets */
  public static final Braces ANGLE_BRACKETS = Braces.CHEVRON;
  /** the absolute brackets */
  public static final Braces ABSOLUTE = Braces.PIPE;
  /** the normbrackets */
  public static final Braces NORM = Braces.DOUBLE_PIPE;

  /** the set of all braces */
  public static final ArraySetView<Braces> BRACES;

  static {
    final Braces[] data;
    int i;

    data = new Braces[] { Braces.PARENTHESES, Braces.BRACKETS,
        Braces.CURLY_BRACES, Braces.ANGLE, Braces.PIPE,
        Braces.DOUBLE_PIPE, Braces.VERTICAL, Braces.BRACKETS_WITH_DASH,
        Braces.RAISED_PARENTHESES, Braces.SUNK_PARENTHESES,
        Braces.CHEVRON, Braces.DOUBLE_ANGLE, Braces.TRIPLE_ANGLE,
        Braces.CEILING, Braces.FLOOR, Braces.CHINESE_BLACK_PARENTHESES,
        Braces.CHINESE_CONVEX, Braces.CHINESE_WHITE_PARENTHESES,
        Braces.CHINESE_DOUBLE_CONVEX, Braces.CHINESE_DOUBLE_BRACES };

    i = 0;
    for (final Braces q : data) {
      q.m_id = i++;
    }
    BRACES = new _Braces(data);
  }

  /**
   * Create
   * 
   * @param begin
   *          the beginning character
   * @param end
   *          the ending character
   */
  private Braces(final Brace begin, final Brace end) {
    super(begin, end);
  }

  /** {@inheritDoc} */
  @Override
  public final ArraySetView<Braces> getSet() {
    return Braces.BRACES;
  }
}
