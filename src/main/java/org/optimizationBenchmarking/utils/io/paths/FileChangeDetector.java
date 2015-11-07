package org.optimizationBenchmarking.utils.io.paths;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestException;
import java.security.MessageDigest;

/**
 * This class helps us to detect whether a file identified by a path has
 * changed.
 */
public class FileChangeDetector {

  /** the file change detector has just been created */
  private static final int STATE_FIRST_CALL = 0;
  /** the file does not exist */
  private static final int STATE_DOES_NOT_EXIST = (FileChangeDetector.STATE_FIRST_CALL + 1);
  /** the file is a regular file */
  private static final int STATE_IS_REGULAR_FILE = (FileChangeDetector.STATE_DOES_NOT_EXIST + 1);
  /** the file is a directory */
  private static final int STATE_IS_DIRECTORY = (FileChangeDetector.STATE_IS_REGULAR_FILE + 1);
  /** the file is a symbolic link */
  private static final int STATE_IS_SYMBOLIC_LINK = (FileChangeDetector.STATE_IS_DIRECTORY + 1);
  /** the file is something other */
  private static final int STATE_IS_OTHER = (FileChangeDetector.STATE_IS_SYMBOLIC_LINK + 1);
  /** the file is something else */
  private static final int STATE_IS_SOMETHING_ELSE = (FileChangeDetector.STATE_IS_OTHER + 1);
  /** the we cannot detect what the file is because of security issues */
  private static final int STATE_SECURITY = (FileChangeDetector.STATE_IS_SOMETHING_ELSE + 1);
  /**
   * the we cannot detect what the file is because something else went
   * wrong
   */
  private static final int STATE_UNKNOWN_EXCEPTION = (FileChangeDetector.STATE_SECURITY + 1);

  /** the path */
  private final Path m_path;

  /** the message digests */
  private final MessageDigest[] m_digests;
  /** the array of byte hosting all the checksums */
  private final byte[] m_checkSums;
  /** the digest sizes */
  private final int[] m_sizes;

  /** the state of the file */
  private int m_state;

  /** the size of the file */
  private long m_size;

  /** did something change? */
  private boolean m_changed;

  /**
   * Create the change detector
   *
   * @param path
   *          the path to look at
   */
  @SuppressWarnings("unused")
  public FileChangeDetector(final Path path) {
    super();

    final MessageDigest[] all;
    final int[] sizes;
    int count, total, size;
    MessageDigest digest;

    this.m_state = FileChangeDetector.STATE_FIRST_CALL;
    this.m_changed = true;
    this.m_path = PathUtils.normalize(path);

    this.m_size = Long.MIN_VALUE;

    all = new MessageDigest[6];
    sizes = new int[all.length];
    total = count = 0;

    try {
      all[count] = digest = MessageDigest.getInstance("SHA-1"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // SHA-1 not installed
    }

    try {
      all[count] = digest = MessageDigest.getInstance("SHA-512"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // SHA-512 not installed
    }

    try {
      all[count] = digest = MessageDigest.getInstance("SHA-384"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // SHA-384 not installed
    }

    try {
      all[count] = digest = MessageDigest.getInstance("SHA-256"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // SHA-256 not installed
    }

    try {
      all[count] = digest = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // MD5 not installed
    }

    try {
      all[count] = digest = MessageDigest.getInstance("MD2"); //$NON-NLS-1$
      if (digest != null) {
        sizes[count] = size = digest.getDigestLength();
        if (size > 0) {
          total += size;
          count++;
        }
      }
    } catch (final Throwable t) {
      // MD2 not installed
    }

    if ((count <= 0) || (total <= 0)) {
      this.m_checkSums = null;
      this.m_sizes = null;
      this.m_digests = null;
    } else {
      if (count < all.length) {
        this.m_sizes = new int[count];
        System.arraycopy(sizes, 0, this.m_sizes, 0, count);
        this.m_digests = new MessageDigest[count];
        System.arraycopy(all, 0, this.m_digests, 0, count);
      } else {
        this.m_sizes = sizes;
        this.m_digests = all;
      }
      this.m_checkSums = new byte[total << 1];
    }
  }

  /** update the file checksums */
  @SuppressWarnings("unused")
  private final void __updateCheckSums() {
    final byte[] checkSums;
    final long size;
    byte[] buffer;
    int read, pos, index;
    byte a, b;
    boolean different;

    if ((checkSums = this.m_checkSums).length <= 0) {
      return;
    }

    try (final InputStream input = PathUtils.openInputStream(this.m_path)) {
      if (((size = this.m_size) >= 0) && (size < 10485760L)) {
        buffer = new byte[(int) (size)];
      } else {
        buffer = new byte[4096];
      }

      while ((read = input.read(buffer)) > 0) {
        for (final MessageDigest digest : this.m_digests) {
          digest.update(buffer, 0, read);
        }
      }

    } catch (final Throwable error) {
      //
    }

    index = 0;
    pos = 0;
    for (final MessageDigest digest : this.m_digests) {
      read = this.m_sizes[index++];
      try {
        digest.digest(checkSums, pos, read);
      } catch (final DigestException de) {
        // ignoreable
      }
      pos += read;
    }

    different = false;
    read = pos;
    for (index = checkSums.length; (--pos) >= 0;) {
      a = checkSums[pos];
      b = checkSums[--index];
      if (a != b) {
        different = true;
        break;
      }
    }

    if (different) {
      this.m_changed = true;
      System.arraycopy(checkSums, 0, checkSums, read, read);
    }
  }

  /**
   * Check whether the file has changed
   *
   * @param reset
   *          reset the change flag?
   * @return {@code true} if a change has been detected to the file or this
   *         is the first call to this method
   */
  @SuppressWarnings("unused")
  public synchronized final boolean hasChanged(final boolean reset) {
    final boolean changed;
    final int oldState, newState;
    int errorType;
    BasicFileAttributes bfa;
    long size;

    errorType = 0;
    try {
      bfa = Files.readAttributes(this.m_path, BasicFileAttributes.class);
    } catch (final IOException doesNotExist) {
      bfa = null;
      errorType = FileChangeDetector.STATE_DOES_NOT_EXIST;
    } catch (final SecurityException security) {
      bfa = null;
      errorType = FileChangeDetector.STATE_SECURITY;
    } catch (final Throwable unknown) {
      bfa = null;
      errorType = FileChangeDetector.STATE_UNKNOWN_EXCEPTION;
    }

    if (bfa != null) {
      if (bfa.isRegularFile()) {
        newState = FileChangeDetector.STATE_IS_REGULAR_FILE;
      } else {
        if (bfa.isDirectory()) {
          newState = FileChangeDetector.STATE_IS_DIRECTORY;
        } else {
          if (bfa.isSymbolicLink()) {
            newState = FileChangeDetector.STATE_IS_SYMBOLIC_LINK;
          } else {
            if (bfa.isOther()) {
              newState = FileChangeDetector.STATE_IS_OTHER;
            } else {
              newState = FileChangeDetector.STATE_IS_SOMETHING_ELSE;
            }
          }
        }
      }
    } else {
      if (errorType != 0) {
        newState = errorType;
      } else {
        newState = FileChangeDetector.STATE_DOES_NOT_EXIST;
      }
    }

    oldState = this.m_state;
    if (oldState != newState) {
      this.m_changed = true;
      this.m_state = newState;
    }

    if (newState == FileChangeDetector.STATE_IS_REGULAR_FILE) {
      if (bfa != null) {
        size = bfa.size();
        if (size != this.m_size) {
          this.m_size = size;
          this.m_changed = true;
        }
      } else {
        this.m_size = Long.MIN_VALUE;
      }

      this.__updateCheckSums();
    }

    changed = this.m_changed;
    if (reset) {
      this.m_changed = false;
    }
    return changed;
  }

  /**
   * Check if the path refers to a regular file
   *
   * @return {@code true} if the path refers to a regular file,
   *         {@code false} otherwise
   */
  public synchronized final boolean isRegularFile() {
    if (this.m_state == FileChangeDetector.STATE_FIRST_CALL) {
      this.hasChanged(false);
    }
    return (this.m_state == FileChangeDetector.STATE_IS_REGULAR_FILE);
  }

  /**
   * Get the path tracked by this tracker
   *
   * @return the path tracked by this tracker
   */
  public final Path getPath() {
    return this.m_path;
  }
}
