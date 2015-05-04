package org.optimizationBenchmarking.utils.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A java file object backed by strings in memory. */
final class _CharSequenceJavaFileObject extends SimpleJavaFileObject
    implements ITextable {

  /** the class name */
  private final String m_className;

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
  public _CharSequenceJavaFileObject(final String className,
      final CharSequence content) {
    super(URI.create("string:///" + className.replace('.', '/') //$NON-NLS-1$
        + Kind.SOURCE.extension), Kind.SOURCE);
    this.m_className = className;
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

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append("Class '");//$NON-NLS-1$
    textOut.append(this.m_className);
    textOut.append("' generated in memory."); //$NON-NLS-1$
    textOut.appendLineBreak();
    textOut.append(super.toString());
    textOut.appendLineBreak();
    textOut.append(this.m_content);
  }

}
