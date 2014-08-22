package org.optimizationBenchmarking.utils.parsers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.files.CanonicalizeFile;
import org.optimizationBenchmarking.utils.io.files.Paths;

/** A parser for a given type */
public class FileParser extends Parser<File> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the file parser */
  public static final FileParser INSTANCE = new FileParser();

  /** create the parser */
  private FileParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<File> getOutputClass() {
    return File.class;
  }

  /** {@inheritDoc} */
  @Override
  public final File parseString(final String string) throws IOException,
      SecurityException {
    final File f;

    f = new CanonicalizeFile(StringParser.INSTANCE.parseString(string))
        .call();
    this.validate(f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final File instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("File must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final File parseObject(final Object o) throws IOException,
      SecurityException, URISyntaxException {
    String name;
    File ret;
    URI uri;

    if (o instanceof File) {
      ret = ((File) o);
    } else {
      if (o instanceof Path) {
        ret = ((Path) o).toFile();
      } else {

        if (o instanceof URI) {
          uri = ((URI) o);
        } else {
          if (o instanceof URL) {
            uri = ((URL) o).toURI();
          } else {
            uri = null;
          }
        }

        if (uri != null) {
          try {
            ret = new File(uri);
          } catch (final Throwable t) {
            name = uri.toString();
            if (File.separatorChar != '/') {
              name = name.replace('/', File.separatorChar);
            }
            ret = new File(Paths.getCurrentDir(), name);
          }
        } else {
          return this.parseString(String.valueOf(o));
        }
      }
    }

    ret = new CanonicalizeFile(ret).call();
    this.validate(ret);
    return ret;
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return FileParser.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return FileParser.INSTANCE;
  }
}
