package org.optimizationBenchmarking.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * The archive types supported by the structured I/O API.
 * </p>
 * <p>
 * Archive types may support building full file system structures inside an
 * archive. The {@link #ZIP} format does this, for instance. Other archive
 * types, such as {@link #GZIP}, may only support single compressed data
 * streams.
 * </p>
 * <p>
 * If full file system structures are supported, the
 * {@link #compressPathToStream(Path, OutputStream)} and
 * {@link #decompressStreamToFolder(InputStream, Path, String)} methods
 * allow for the compression and decompression of such structures from the
 * actual file system. Otherwise, if no file structure is permitted in an
 * archive, then these methods will merge all compressed files into one
 * single stream or extract the stream into a single file.
 * </p>
 * <p>
 * If single data streams are supported, it is possible to store a single
 * stream of data into another stream. If file system structures inside the
 * archive are required, then an archive with a single file inside will be
 * created to host the stream.
 * </p>
 */
public enum EArchiveType implements IFileType {

  /**
   * The ZIP archive type. ZIP archives support a directory structure
   * inside them.
   */
  ZIP() {
    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "zip"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/zip";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "ZIP Archive"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final void compressPathsToStream(
        final Iterable<Path> sourcePaths, final Path nameBase,
        final OutputStream destStream) throws IOException {
      final Path res;
      final __PathZipper zipper;

      res = PathUtils.normalize(nameBase);
      try (final ZipOutputStream zipStream = EArchiveType
          ._makeZipOutputStream(destStream)) {
        zipper = new __PathZipper(res, zipStream);
        for (final Path p : sourcePaths) {
          Files.walkFileTree(PathUtils.normalize(p), zipper);
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void decompressStreamToFolder(
        final InputStream sourceStream, final Path destFolder,
        final String fallbackFileName) throws IOException {

      final Path res;
      Path out;
      ZipEntry entry;
      byte[] buffer;
      int bytesRead;

      res = PathUtils.normalize(destFolder);

      try (final ZipInputStream zis = new ZipInputStream(sourceStream)) {
        buffer = null;

        while ((entry = zis.getNextEntry()) != null) {
          try {

            out = PathUtils.createPathInside(res, entry.getName());

            if (entry.isDirectory()) {
              Files.createDirectories(out);
              continue;
            }
            Files.createDirectories(out.getParent());

            try (final OutputStream fos = PathUtils.openOutputStream(out)) {
              if (buffer == null) {
                buffer = new byte[4096];
              }
              bytesRead = 0;

              while ((bytesRead = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
              }
            }

          } finally {
            zis.closeEntry();
          }
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final OutputStream compressStream(
        final OutputStream destination, final String fallbackFileName)
        throws IOException {
      return new __ZipOutputStream(destination,
          EArchiveType._fallback(fallbackFileName));
    }

    /** {@inheritDoc} */
    @Override
    public final InputStream decompressStream(final InputStream source)
        throws IOException {
      return new __ZipInputStream(source);
    }
  },

  /**
   * The GZIP archive type. GZIP archives do not support a directory
   * structure inside them but only single streams of data.
   */
  GZIP() {

    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "gzip"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/x-gzip";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "GZIP Archive"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final void compressPathsToStream(
        final Iterable<Path> sourcePaths, final Path nameBase,
        final OutputStream destStream) throws IOException {
      final __PathCompressor zipper;

      try (final GZIPOutputStream zipStream = new GZIPOutputStream(
          destStream)) {
        zipper = new __PathCompressor(zipStream);
        for (final Path p : sourcePaths) {
          Files.walkFileTree(PathUtils.normalize(p), zipper);
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void decompressStreamToFolder(
        final InputStream sourceStream, final Path destFolder,
        final String fallbackFileName) throws IOException {

      final Path res;

      res = PathUtils.normalize(destFolder);
      try (final GZIPInputStream zis = new GZIPInputStream(sourceStream)) {
        Files.copy(
            zis,
            PathUtils.createPathInside(res,
                EArchiveType._fallback(fallbackFileName)));
      }
    }

    /** {@inheritDoc} */
    @Override
    public final OutputStream compressStream(
        final OutputStream destination, final String fallbackFileName)
        throws IOException {
      return new GZIPOutputStream(destination);
    }

    /** {@inheritDoc} */
    @Override
    public final InputStream decompressStream(final InputStream source)
        throws IOException {
      return new GZIPInputStream(source);
    }

  };

  /**
   * get a fall back file name
   *
   * @param fallbackFileName
   *          the suggestion
   * @return the name
   */
  static final String _fallback(final String fallbackFileName) {
    final String name;

    name = TextUtils.normalize(fallbackFileName);
    return ((name != null) ? name : "data"); //$NON-NLS-1$
  }

  /**
   * Compress a set of files or folder structures to an output stream.
   *
   * @param sourcePaths
   *          the files or folders to compress
   * @param nameBase
   *          the path used to resolve names against
   * @param destStream
   *          the destination stream
   * @throws IOException
   *           if I/O fails
   */
  public abstract void compressPathsToStream(
      final Iterable<Path> sourcePaths, final Path nameBase,
      final OutputStream destStream) throws IOException;

  /**
   * Compress a single file or a whole folder structure to an output
   * stream.
   *
   * @param sourcePath
   *          the file or folder
   * @param destStream
   *          the destination stream
   * @throws IOException
   *           if I/O fails
   */
  public void compressPathToStream(final Path sourcePath,
      final OutputStream destStream) throws IOException {
    this.compressPathsToStream(new ArrayListView<>(
        new Path[] { sourcePath }), sourcePath, destStream);
  }

  /**
   * De-compress a stream to a folder.
   *
   * @param sourceStream
   *          the source stream
   * @param destFolder
   *          the destination folder
   * @param fallbackFileName
   *          if the archive format only supports single streams and no
   *          file systems, then a single file of this name is created in
   *          the destination folder
   * @throws IOException
   *           if I/O fails
   */
  public abstract void decompressStreamToFolder(
      final InputStream sourceStream, final Path destFolder,
      final String fallbackFileName) throws IOException;

  /**
   * Create an archive only containing a single file or data stream and
   * provide a destination stream.
   *
   * @param destination
   *          the output stream to which the compressed output will be
   *          written
   * @param fallbackFileName
   *          if the archive format requires creating a file system
   *          structure inside the archive, then a single file of this name
   *          will be created
   * @return the wrapped (archived) stream to which uncompressed data can
   *         be written
   * @throws IOException
   *           if I/O fails
   */
  public abstract OutputStream compressStream(
      final OutputStream destination, final String fallbackFileName)
      throws IOException;

  /**
   * Open a single stream from an archive an load its data
   *
   * @param source
   *          the input stream with the compressed data
   * @return the wrapped stream from which uncompressed data can be read
   * @throws IOException
   *           if I/O fails
   */
  public abstract InputStream decompressStream(final InputStream source)
      throws IOException;

  /**
   * A visitor for the storing paths into a ZIP archive.
   */
  private static final class __PathZipper extends SimpleFileVisitor<Path> {

    /** the root path */
    private final Path m_root;

    /** the output stream */
    private final ZipOutputStream m_zipStream;

    /**
     * create the zipper
     *
     * @param root
     *          the root path
     * @param zipStream
     *          the stream
     */
    __PathZipper(final Path root, final ZipOutputStream zipStream) {
      super();
      this.m_root = root;
      this.m_zipStream = zipStream;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult preVisitDirectory(final Path dir,
        final BasicFileAttributes attrs) {
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) throws IOException {
      String name;

      if (file.equals(this.m_root)) {
        name = PathUtils.getName(file);
      } else {
        name = this.m_root.relativize(file).toString();
      }

      this.m_zipStream.putNextEntry(new ZipEntry(name));
      try {
        Files.copy(file, this.m_zipStream);
      } finally {
        this.m_zipStream.closeEntry();
      }
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) throws IOException {
      throw exc;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }
  }

  /**
   * make a zip output stream
   *
   * @param out
   *          the output stream
   * @return the zip output stream
   */
  static final ZipOutputStream _makeZipOutputStream(final OutputStream out) {
    final ZipOutputStream zipStream;

    zipStream = new ZipOutputStream(out, StandardCharsets.UTF_8);
    zipStream.setMethod(ZipOutputStream.DEFLATED);
    zipStream.setLevel(Deflater.BEST_COMPRESSION);
    return zipStream;
  }

  /** a zip output stream */
  private static final class __ZipOutputStream extends OutputStream {

    /** the zip output stream */
    private final ZipOutputStream m_zos;

    /**
     * create
     *
     * @param out
     *          the destination stream
     * @param fallbackFileName
     *          the file name to use
     * @throws IOException
     *           if I/O fails
     */
    __ZipOutputStream(final OutputStream out, final String fallbackFileName)
        throws IOException {
      super();
      this.m_zos = EArchiveType._makeZipOutputStream(out);
      this.m_zos.putNextEntry(new ZipEntry(fallbackFileName));
    }

    /** {@inheritDoc} */
    @Override
    public final void write(final int b) throws IOException {
      this.m_zos.write(b);
    }

    /** {@inheritDoc} */
    @Override
    public final void write(final byte[] b) throws IOException {
      this.m_zos.write(b);
    }

    /** {@inheritDoc} */
    @Override
    public final void write(final byte[] b, final int off, final int len)
        throws IOException {
      this.m_zos.write(b, off, len);
    }

    /** {@inheritDoc} */
    @Override
    public final void flush() throws IOException {
      this.m_zos.flush();
    }

    /** {@inheritDoc} */
    @Override
    public final void close() throws IOException {
      try {
        try {
          this.m_zos.closeEntry();
        } finally {
          this.m_zos.close();
        }
      } finally {
        super.close();
      }
    }
  }

  /** the zip input stream */
  private static final class __ZipInputStream extends InputStream {

    /** the real zip input stream */
    private final ZipInputStream m_zis;

    /** the entry */
    private ZipEntry m_entry;

    /**
     * create the zip input stream
     *
     * @param in
     *          the stream with the compressed data
     * @throws IOException
     *           if i/o fails
     */
    __ZipInputStream(final InputStream in) throws IOException {
      super();
      this.m_zis = new ZipInputStream(in);
      this.__hasMore();
    }

    /** {@inheritDoc} */
    @Override
    public final int read() throws IOException {
      int retVal;

      while (this.__hasMore()) {
        retVal = this.m_zis.read();
        if (retVal >= 0) {
          return retVal;
        }
        this.__finishPart();
      }
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    public final int read(final byte[] b, final int off, final int len)
        throws IOException {
      if (len == 0) {
        return 0;
      }

      int retVal;

      while (this.__hasMore()) {
        retVal = this.m_zis.read(b, off, len);
        if (retVal > 0) {
          return retVal;
        }
        this.__finishPart();
      }
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    public final long skip(final long n) throws IOException {
      long retVal;

      if (n <= 0) {
        return 0;
      }

      while (this.__hasMore()) {
        retVal = this.m_zis.skip(n);
        if (retVal >= 0L) {
          return retVal;
        }
        this.__finishPart();
      }
      return (-1);
    }

    /** {@inheritDoc} */
    @Override
    public final int available() throws IOException {
      if (this.__hasMore()) {
        return this.m_zis.available();
      }
      return 0;
    }

    /** {@inheritDoc} */
    @Override
    public final void close() throws IOException {
      try {
        this.__finishPart();
      } finally {
        try {
          this.m_zis.close();
        } finally {
          super.close();
        }
      }
    }

    /** {@inheritDoc} */
    @Override
    public final synchronized void mark(final int readlimit) {
      this.m_zis.mark(readlimit);
    }

    /** {@inheritDoc} */
    @Override
    public final synchronized void reset() throws IOException {
      this.m_zis.reset();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean markSupported() {
      return this.m_zis.markSupported();
    }

    /**
     * Do we potentially have data to read?
     *
     * @return {@code true} if we may have data, {@code false} otherwise
     * @throws IOException
     *           if i/o fails
     */
    private final boolean __hasMore() throws IOException {
      if (this.m_entry != null) {
        return true;
      }
      for (;;) {
        this.m_entry = this.m_zis.getNextEntry();
        if (this.m_entry == null) {
          return false;
        }
        if (this.m_entry.isDirectory()) {
          this.__finishPart();
          continue;
        }
        return true;
      }
    }

    /**
     * finish a stream part
     *
     * @throws IOException
     *           if i/o fails
     */
    private final void __finishPart() throws IOException {
      if (this.m_entry != null) {
        try {
          this.m_zis.closeEntry();
        } finally {
          this.m_entry = null;
        }
      }
    }
  }

  /**
   * A visitor for the storing paths all into a single stream
   */
  private static final class __PathCompressor extends
      SimpleFileVisitor<Path> {

    /** the output stream */
    private final OutputStream m_stream;

    /**
     * create the path compressor
     *
     * @param stream
     *          the stream
     */
    __PathCompressor(final OutputStream stream) {
      super();
      this.m_stream = stream;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult preVisitDirectory(final Path dir,
        final BasicFileAttributes attrs) {
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) throws IOException {
      Files.copy(file, this.m_stream);
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) throws IOException {
      throw exc;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }
  }

}