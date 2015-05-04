package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A record holding a source or location */
class _Location {

  /** the stream encoding */
  StreamEncoding<?, ?> m_encoding;

  /** perform/handle compression? */
  EArchiveType m_archiveType;

  /** the first location object */
  Object m_location1;

  /** the second location object */
  Object m_location2;

  /** create the location record */
  _Location() {
    super();
  }

  /**
   * create the location record
   *
   * @param location1
   *          the first location
   * @param location2
   *          the second location
   * @param archiveType
   *          if the archive type, if any
   * @param encoding
   *          the encoding
   */
  _Location(final Object location1, final Object location2,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    super();
    this.m_location1 = location1;
    this.m_location2 = location2;
    this.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_archiveType = archiveType;
  }

  /**
   * Append a location to a given memory text output
   *
   * @param location
   *          the location
   * @param text
   *          the text
   */
  static final void _appendLocation(final _Location location,
      final MemoryTextOutput text) {
    Object obj;

    if ((location != null) && ((obj = location.m_location1) != null)) {
      text.append(',');
      if ((obj instanceof Path) || (obj instanceof File)
          || (obj instanceof URI) || (obj instanceof URL)) {
        text.append(obj.toString());
      } else {
        if ((obj instanceof Class) || (obj instanceof String)) {
          text.append(obj.toString());
          obj = location.m_location2;
          if (obj != null) {
            text.append(':');
            text.append(obj.toString());
          }
        } else {
          text.append(TextUtils.className(obj.getClass()));
          text.append('@');
          text.append(System.identityHashCode(obj));
        }
      }
    }
  }

  /**
   * set the location
   *
   * @param location1
   *          the first location
   * @param location2
   *          the second location record
   */
  final void _setLocation(final Object location1, final Object location2) {
    final MemoryTextOutput text;

    if (this.m_location1 != null) {
      text = new MemoryTextOutput();
      text.append("Location has already been set to an instance of "); //$NON-NLS-1$
      text.append(this.m_location1.getClass().getSimpleName());
      if (this.m_location2 != null) {
        text.append(' ');
        text.append('(');
        text.append(this.m_location2.getClass().getSimpleName());
        text.append(')');
      }

      text.append(" and cannot be set to an instance of "); //$NON-NLS-1$
      text.append(location1.getClass().getSimpleName());
      if (location2 != null) {
        text.append(' ');
        text.append('(');
        text.append(location2.getClass().getSimpleName());
        text.append(')');
      }
      text.append(" anymore."); //$NON-NLS-1$
      throw new IllegalStateException(text.toString());

    }
    this.m_location1 = location1;
    this.m_location2 = location2;
  }

}
