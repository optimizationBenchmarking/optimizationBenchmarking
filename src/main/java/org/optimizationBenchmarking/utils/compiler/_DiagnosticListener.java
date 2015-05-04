package org.optimizationBenchmarking.utils.compiler;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;

import org.optimizationBenchmarking.utils.io.LoggingWriter;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a diagnostic listener */
final class _DiagnosticListener extends LoggingWriter implements
    DiagnosticListener<Object> {

  /**
   * create the listener
   *
   * @param logger
   *          the logger
   */
  _DiagnosticListener(final Logger logger) {
    super(logger, Level.FINE);
  }

  /** {@inheritDoc} */
  @Override
  public void report(final Diagnostic<? extends Object> diagnostic) {
    final Level level;
    MemoryTextOutput text;
    String string;
    Object source;

    if (this.m_logger == null) {
      return;
    }

    if (diagnostic == null) {
      this.m_logger.warning("Received null diagnostic."); //$NON-NLS-1$
      return;
    }

    switch (diagnostic.getKind()) {
      case ERROR: {
        level = Level.SEVERE;
        break;
      }
      case MANDATORY_WARNING: {
        level = Level.WARNING;
        break;
      }
      case NOTE: {
        level = Level.INFO;
        break;
      }
      case WARNING: {
        level = Level.WARNING;
        break;
      }
      default: {
        level = this.m_logLevel;
        break;
      }
    }

    text = new MemoryTextOutput();
    text.append("Received a diagnostic when compiling Java code:"); //$NON-NLS-1$

    text.appendLineBreak();
    text.append("Message: ");//$NON-NLS-1$
    text.append(diagnostic.getMessage(Locale.US));

    text.appendLineBreak();
    text.append("Location: chars ");//$NON-NLS-1$
    text.append(diagnostic.getStartPosition());
    text.append(" to ");//$NON-NLS-1$
    text.append(diagnostic.getEndPosition());
    text.append("i.e., line ");//$NON-NLS-1$
    text.append(diagnostic.getLineNumber());
    text.append(" column ");//$NON-NLS-1$
    text.append(diagnostic.getColumnNumber());

    text.appendLineBreak();
    text.append("Code: ");//$NON-NLS-1$
    text.append(diagnostic.getCode());

    text.appendLineBreak();
    text.append("Source: ");//$NON-NLS-1$
    source = diagnostic.getSource();
    if (source == null) {
      text.append("null"); //$NON-NLS-1$
    } else {
      text.append(TextUtils.className(source.getClass()));
    }
    source = null;

    string = text.toString();
    text = null;
    synchronized (this.m_logger) {
      this.m_logger.log(level, string);
    }
  }

}
