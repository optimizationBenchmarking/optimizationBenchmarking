package examples.snippets;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * A simple tool to get the median file size from logs created with <a
 * href="http://www.logisticPlanning.org/tsp/">TSPSuite</a>.
 */
public final class MedianFileSize extends SimpleFileVisitor<Path> {

  /** the number of visited files */
  private int m_count;

  /** the sizes */
  private long[] m_sizes;

  /** the size sum */
  private long m_sum;

  /** the result counter */
  private int m_resultCount;

  /** are we in the results */
  private boolean m_inResult;

  /** create */
  private MedianFileSize() {
    super();
    this.m_sizes = new long[8192];
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult preVisitDirectory(final Path dir,
      final BasicFileAttributes attrs) throws IOException {
    if ("results".equalsIgnoreCase(PathUtils.getName(dir))) { //$NON-NLS-1$
      this.m_resultCount++;
      this.m_inResult = true;
    }
    return FileVisitResult.CONTINUE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult postVisitDirectory(final Path dir,
      final IOException exc) throws IOException {
    if ("results".equalsIgnoreCase(PathUtils.getName(dir))) { //$NON-NLS-1$
      this.m_inResult = false;
    }
    return FileVisitResult.CONTINUE;
  }

  /** {@inheritDoc } */
  @Override
  public FileVisitResult visitFile(final Path file,
      final BasicFileAttributes attrs) throws IOException {
    long[] sizes;
    int count;
    long s;

    if (this.m_inResult) {
      if ("txt".equalsIgnoreCase(PathUtils.getFileExtension(file))) { //$NON-NLS-1$
        sizes = this.m_sizes;
        s = attrs.size();
        if (s >= 0L) {
          count = (this.m_count++);
          if (count >= sizes.length) {
            sizes = new long[count << 1];
            System.arraycopy(this.m_sizes, 0, sizes, 0, count);
            this.m_sizes = sizes;
          }
          sizes[count] = s;
          this.m_sum += s;
        }
      }
    }

    return FileVisitResult.CONTINUE;
  }

  /**
   * The main routine
   *
   * @param args
   *          ignored
   * @throws Throwable
   *           ignore
   */
  public static final void main(final String[] args) throws Throwable {
    final Path p;
    final MedianFileSize mfs;
    int k, maxLength;

    p = PathUtils.normalize((args.length > 0) ? args[0] : "."); //$NON-NLS-1$
    System.out.println("Checking text files in path " + p);//$NON-NLS-1$

    mfs = new MedianFileSize();
    Files.walkFileTree(p, mfs);

    System.out.println("Finished checking text files in path " + p);//$NON-NLS-1$

    k = mfs.m_count;
    maxLength = Math.max(Long.toString(k).length(),
        Long.toString(mfs.m_sum).length());

    System.out.print("Experiments:     ");//$NON-NLS-1$
    MedianFileSize.__printLong(mfs.m_resultCount, maxLength);
    System.out.println();

    System.out.print("Number of Files: ");//$NON-NLS-1$
    MedianFileSize.__printLong(k, maxLength);
    System.out.println();

    System.out.print("Total Size:      ");//$NON-NLS-1$
    MedianFileSize.__printlnSize(mfs.m_sum, maxLength);

    System.out.print("Mean Size:       ");//$NON-NLS-1$
    MedianFileSize.__printlnSize(Math.round(mfs.m_sum / ((double) k)),
        maxLength);

    Arrays.sort(mfs.m_sizes, 0, mfs.m_count);
    System.out.print("Median Size:     ");//$NON-NLS-1$

    if ((k & 1) == 0) {
      k >>= 1;
            MedianFileSize.__printlnSize(
                (mfs.m_sizes[k >> 1] + mfs.m_sizes[1 + (k >> 1)]) >>> 1L,
                maxLength);
    } else {
      MedianFileSize.__printlnSize(mfs.m_sizes[k >> 1], maxLength);
    }

  }

  /**
   * print a long
   *
   * @param l
   *          the long
   * @param maxLength
   *          the max length
   */
  private static final void __printLong(final long l, final int maxLength) {
    String s;
    int i;

    s = Long.toString(l);
    for (i = s.length(); i < maxLength; i++) {
      System.out.print(' ');
    }
    System.out.print(s);
  }

  /**
   * print a given size
   *
   * @param size
   *          the size
   * @param maxLength
   *          the max length
   */
  private static final void __printlnSize(final long size,
      final int maxLength) {

    MedianFileSize.__printLong(size, maxLength);
    System.out.print(' ');
    System.out.print('B');

    if (size > 1024L) {
      System.out.print("   (~"); //$NON-NLS-1$
      if (size > (1024L * 1024L)) {

        if (size > (1024L * 1024L * 1024L)) {
          if (size > (1024L * 1024L * 1024L * 1024L)) {
            System.out.print(size / (1024L * 1024L * 1024L * 1024L));
            System.out.print(" TiB"); //$NON-NLS-1$
          } else {
            System.out.print(size / (1024L * 1024L * 1024L));
            System.out.print(" GiB"); //$NON-NLS-1$
          }
        } else {
          System.out.print(size / (1024L * 1024L));
          System.out.print(" MiB"); //$NON-NLS-1$
        }

      } else {
        System.out.print(size / 1024L);
        System.out.print(" kiB"); //$NON-NLS-1$
      }
      System.out.print(')');

    }

    System.out.println();
  }

}
