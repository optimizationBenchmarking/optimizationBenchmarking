package org.optimizationBenchmarking.utils.chart.spec;

/**
 * The type of a line
 */
public enum ELineType {

  /** directly connect the points in a line / the data */
  DIRECT_CONNECT,

  /** make the line smooth */
  SMOOTH,

  /** stairs, keeping the left-most value */
  STAIRS_KEEP_LEFT,

  /** stairs, keeping the right-most value */
  STAIRS_PREVIEW_RIGHT;

  /** the default connection */
  public static final ELineType DEFAULT = DIRECT_CONNECT;

}
