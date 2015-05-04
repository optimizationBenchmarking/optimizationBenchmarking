package org.optimizationBenchmarking.utils.io.encoding;

/**
 * a package-private interface for stuffs that can state the encoding under
 * which it is running.
 */
public interface IStreamEncoded {

  /**
   * Get the encoding used by this stream object
   *
   * @return the encoding used by this stream object
   */
  public abstract StreamEncoding<?, ?> getStreamEncoding();
}
