package examples.org.optimizationBenchmarking;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A class which will print the finished files */
public final class FinishedPrinter implements IFileProducerListener {

  /** the source objects */
  private final ArrayListView<Object> m_src;

  /**
   * the constructor
   *
   * @param src
   *          the source objects
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public FinishedPrinter(final Object... src) {
    super();
    this.m_src = (((src != null) && (src.length > 0)) ? new ArrayListView<>(
        src.clone()) : ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW)));
  }

  /**
   * the constructor
   */
  public FinishedPrinter() {
    this.m_src = ArraySetView.EMPTY_SET_VIEW;
  }

  /** {@inheritDoc} */
  @Override
  public void onFilesFinalized(
      final Collection<Entry<Path, IFileType>> result) {
    final ArrayListView<Object> creators;
    final String string;
    final int length;
    final Logger logger;
    MemoryTextOutput message;

    message = new MemoryTextOutput(1024);
    creators = this.m_src;
    if ((length = creators.size()) <= 0) {
      message.append('F');
    } else {
      if (length <= 1) {
        message.append(creators.get(0));
      } else {
        creators.toText(message);
      }
      message.append(' ');
      message.append('f');
    }

    message.append("inished creating "); //$NON-NLS-1$

    if ((result == null) || (result.size() <= 0)) {
      message.append("no file.");//$NON-NLS-1$
    } else {
      message.append(result);
    }
    string = message.toString();
    message = null;

    logger = Configuration.getGlobalLogger();

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(string);
    } else {
      synchronized (System.out) {
        System.out.println(string);
      }
    }
  }

}
