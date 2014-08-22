package org.optimizationBenchmarking.utils.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/** A java file object backed by strings in memory. */
public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

  /** the CharSequence representing the source code to be compiled */
  private final CharSequence m_content;

  /**
   * This constructor will store the source code in the internal "content"
   * variable and register it as a source code, using a URI containing the
   * class full name
   * 
   * @param className
   *          name of the public class in the source code
   * @param content
   *          source code to compile
   */
  public CharSequenceJavaFileObject(final String className,
      final CharSequence content) {
    super(URI.create("string:///" + className.replace('.', '/') //$NON-NLS-1$
        + Kind.SOURCE.extension), Kind.SOURCE);
    this.m_content = content;
  }

  /**
   * Answers the CharSequence to be compiled. It will give the source code
   * stored in variable "content"
   */
  @Override
  public final CharSequence getCharContent(
      final boolean ignoreEncodingErrors) {
    return this.m_content;
  }

}
