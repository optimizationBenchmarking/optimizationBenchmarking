package examples.org.optimizationBenchmarking.utils.document;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map.Entry;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** A class which will print the finished files */
public final class FinishedPrinter implements IFileProducerListener {

  /** the source objects */
  private final ArrayListView<Object> m_src;

  /**
   * the internal constructor
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

  /** {@inheritDoc} */
  @Override
  public void onFilesFinalized(
      final Collection<Entry<Path, IFileType>> result) {
    final ArrayListView<Object> l;
    final int i;
    MemoryTextOutput mo;
    String s;

    mo = new MemoryTextOutput(1024);
    l = this.m_src;
    if ((i = l.size()) <= 0) {
      mo.append('F');
    } else {
      if (i <= 1) {
        mo.append(l.get(0));
      } else {
        l.toText(mo);
      }
      mo.append(' ');
      mo.append('f');
    }
    mo.append("inished creating "); //$NON-NLS-1$
    mo.append(result);
    s = mo.toString();
    mo = null;

    synchronized (System.out) {
      System.out.println(s);
    }
  }

}
