package examples.snippets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * An example for processing resources with {@code try-finally}.
 */
public final class TryWithResource {

  /**
   * The main routine
   *
   * @param args
   *          ignored
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {

    try (final StringWriter sw = new StringWriter()) {
      try (final BufferedWriter bw = new BufferedWriter(sw)) {
        bw.write("Hello World!"); //$NON-NLS-1$
      }
      System.out.println(sw.toString());
    }
  }

}
