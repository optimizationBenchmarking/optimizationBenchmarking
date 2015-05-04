package org.optimizationBenchmarking.utils.compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/** the byte array java file object */
final class _ByteArrayJavaFileObject extends SimpleJavaFileObject {

  /**
   * Byte code created by the compiler will be stored in this
   * ByteArrayOutputStream so that we can later get the byte array out of
   * it and put it in the memory as an instance of our class.
   */
  private _BOS bos;

  /**
   * Registers the compiled class object under URI containing the class
   * full name
   *
   * @param name
   *          Full name of the compiled class
   * @param dkind
   *          Kind of the data. It will be CLASS in our case
   */
  _ByteArrayJavaFileObject(final String name, final Kind dkind) {
    super(URI.create("string:///" + name.replace('.', '/') //$NON-NLS-1$
        + dkind.extension), dkind);
    this.bos = null;
  }

  /**
   * Will be used by our file manager to get the byte code that can be put
   * into memory to instantiate our class
   *
   * @return compiled byte code
   */
  public final synchronized byte[] getBytes() {
    if (this.bos == null) {
      throw new IllegalStateException(//
          "Stream never created."); //$NON-NLS-1$
    }
    if (this.bos.m_closed) {
      return this.bos.toByteArray();
    }
    throw new IllegalStateException(//
        "Stream not yet closed."); //$NON-NLS-1$
  }

  /**
   * Will provide the compiler with an output stream that leads to our byte
   * array. This way the compiler will write everything into the byte array
   * that we will instantiate later
   */
  @Override
  public final synchronized OutputStream openOutputStream()
      throws IOException {
    if (this.bos == null) {
      this.bos = new _BOS();
    } else {
      if (this.bos.m_closed) {
        throw new IllegalStateException("Stream already closed."); //$NON-NLS-1$
      }
    }

    return this.bos;
  }

}
