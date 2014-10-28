package org.optimizationBenchmarking.utils.graphics;

import org.optimizationBenchmarking.utils.math.units.ELength;

/** An enumeration of paper sizes */
public enum EPaperSize implements IMedium {

  /** A0 paper */
  A0(new PhysicalDimension(841, 1189, ELength.MM)),

  /** A1 paper */
  A1(new PhysicalDimension(594, 841, ELength.MM)),

  /** A2 paper */
  A2(new PhysicalDimension(420, 594, ELength.MM)),

  /** A3 paper */
  A3(new PhysicalDimension(297, 420, ELength.MM)),

  /** A4 paper */
  A4(new PhysicalDimension(210, 297, ELength.MM)),

  /** A5 paper */
  A5(new PhysicalDimension(148, 210, ELength.MM)),

  /** A6 paper */
  A6(new PhysicalDimension(105, 148, ELength.MM)),

  /** A7 paper */
  A7(new PhysicalDimension(74, 105, ELength.MM)),

  /** A8 paper */
  A8(new PhysicalDimension(52, 74, ELength.MM)),

  /** A9 paper */
  A9(new PhysicalDimension(26, 37, ELength.MM)),

  /** A10 paper */
  A10(new PhysicalDimension(26, 37, ELength.MM)),

  /** B0 paper */
  B0(new PhysicalDimension(1000, 1414, ELength.MM)),

  /** B1 paper */
  B1(new PhysicalDimension(707, 1000, ELength.MM)),

  /** B2 paper */
  B2(new PhysicalDimension(500, 707, ELength.MM)),

  /** B3 paper */
  B3(new PhysicalDimension(353, 500, ELength.MM)),

  /** B4 paper */
  B4(new PhysicalDimension(250, 353, ELength.MM)),

  /** B5 paper */
  B5(new PhysicalDimension(176, 250, ELength.MM)),

  /** B6 paper */
  B6(new PhysicalDimension(125, 176, ELength.MM)),

  /** B7 paper */
  B7(new PhysicalDimension(88, 125, ELength.MM)),

  /** B8 paper */
  B8(new PhysicalDimension(62, 88, ELength.MM)),

  /** B9 paper */
  B9(new PhysicalDimension(44, 62, ELength.MM)),

  /** B10 paper */
  B10(new PhysicalDimension(31, 44, ELength.MM)),

  /** C0 paper */
  C0(new PhysicalDimension(917, 1297, ELength.MM)),

  /** C1 paper */
  C1(new PhysicalDimension(648, 917, ELength.MM)),

  /** C2 paper */
  C2(new PhysicalDimension(458, 648, ELength.MM)),

  /** C3 paper */
  C3(new PhysicalDimension(324, 458, ELength.MM)),

  /** C4 paper */
  C4(new PhysicalDimension(229, 324, ELength.MM)),

  /** C5 paper */
  C5(new PhysicalDimension(162, 229, ELength.MM)),

  /** C6 paper */
  C6(new PhysicalDimension(114, 162, ELength.MM)),

  /** C7_6 paper */
  C7_6(new PhysicalDimension(81, 162, ELength.MM)),

  /** C7 paper */
  C7(new PhysicalDimension(81, 114, ELength.MM)),

  /** C8 paper */
  C8(new PhysicalDimension(57, 81, ELength.MM)),

  /** C9 paper */
  C9(new PhysicalDimension(40, 57, ELength.MM)),

  /** C10 paper */
  C10(new PhysicalDimension(28, 40, ELength.MM)),

  /** DL paper */
  DL(new PhysicalDimension(110, 220, ELength.MM)),

  /** Letter paper */
  LETTER(new PhysicalDimension(8.5, 11, ELength.IN)),

  /** Government Letter paper */
  GOVERNMENT_LETTER(new PhysicalDimension(8, 10.5, ELength.IN)),

  /** Legal paper */
  LEGAL(new PhysicalDimension(8.5, 14, ELength.IN)),

  /** Junior Legal paper */
  JUNIOR_LEGAL(new PhysicalDimension(8, 5, ELength.IN)),

  /** Ledger paper */
  LEDGER(new PhysicalDimension(17, 11, ELength.IN)),

  /** Tabloid paper */
  TABLOID(new PhysicalDimension(11, 17, ELength.IN)),

  /** Ansi C paper */
  ANSI_C(new PhysicalDimension(17, 22, ELength.IN)),

  /** Ansi D paper */
  ANSI_D(new PhysicalDimension(22, 34, ELength.IN)),

  /** Ansi E paper */
  ANSI_E(new PhysicalDimension(34, 44, ELength.IN)), ;

  /** the ansi a paper size */
  public static final EPaperSize ANSI_A = LETTER;

  /** the ansi b paper size */
  public static final EPaperSize ANSI_B = TABLOID;

  /** the physical dimensions */
  private final PhysicalDimension m_size;

  /**
   * Create a new paper size
   * 
   * @param size
   *          the size
   */
  private EPaperSize(final PhysicalDimension size) {
    this.m_size = size;
  }

  /** {@inheritDoc} */
  @Override
  public final PhysicalDimension getPageSize() {
    return this.m_size;
  }

}
