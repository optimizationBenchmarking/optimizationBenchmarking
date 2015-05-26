package examples.snippets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * An example for processing resources with {@code try-finally}.
 */
public final class TryFinallyResource {

  /**
   * The main routine
   *
   * @param args
   *          ignored
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  public static final void main(final String[] args) throws IOException {
    StringWriter sw;
    BufferedWriter bw;

    sw = new StringWriter();
    try {
      bw = new BufferedWriter(sw);
      try {
        bw.write("Hello World!"); //$NON-NLS-1$
      } finally {
        bw.close();
      }

      System.out.println(sw.toString());
    } finally {
      sw.close();
    }
  }

}
