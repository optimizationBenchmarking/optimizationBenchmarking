package org.optimizationBenchmarking.utils.io;

/** This interface is used to signify file types */
public interface IFileType {

  /**
   * Get the default suffix of file names of a given type
   * 
   * @return the default suffix of file names of a given type
   */
  public abstract String getDefaultSuffix();

  /**
   * Get the mime type, or {@code null} if no mime type is defined for this
   * kind of file
   * 
   * @return the mime type
   */
  public abstract String getMIMEType();

  /**
   * Get the official, full name of the file type
   * 
   * @return the official, full name of the file type
   */
  public abstract String getName();

}
