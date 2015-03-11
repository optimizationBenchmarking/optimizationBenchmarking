package org.optimizationBenchmarking.experimentation.io.impl.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDI;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDIInput;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputJobBuilder;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/** the internal input token for CSV data */
final class _CSVInputToken {

  /** the files to test */
  private static final String[] FILES;

  static {
    final LinkedHashSet<String> suffixes, baseNames, names;
    String string;
    int length;

    suffixes = new LinkedHashSet<>();
    suffixes.add(EDI.EDI_XML.getDefaultSuffix().toLowerCase());
    suffixes.add(XMLFileType.XML.getDefaultSuffix());

    baseNames = new LinkedHashSet<>();

    string = CSVInput.DIMENSIONS_EDI_FILE;
    baseNames.add(string);
    length = string.length();
    if (string.charAt(length - 1) == 's') {
      if (length > 1) {
        baseNames.add(string.substring(0, length - 1));
      }
    } else {
      baseNames.add(string + 's');
    }

    string = CSVInput.CONFIG_EDI_FILE;
    baseNames.add(string);
    length = string.length();
    if (string.charAt(length - 1) == 's') {
      if (length > 1) {
        baseNames.add(string.substring(0, length - 1));
      }
    } else {
      baseNames.add(string + 's');
    }

    string = CSVInput.INSTANCES_EDI_FILE;
    baseNames.add(string);
    length = string.length();
    if (string.charAt(length - 1) == 's') {
      if (length > 1) {
        baseNames.add(string.substring(0, length - 1));
      }
    } else {
      baseNames.add(string + 's');
    }

    names = new LinkedHashSet<>();
    for (final String baseName : baseNames) {
      for (final String suffix : suffixes) {
        names.add(baseName + '.' + suffix);
      }
    }
    FILES = names.toArray(new String[names.size()]);
  }

  /** the paths already processed */
  private final HashSet<Path> m_done;

  /** the hierarchical fsm stack */
  private final ArrayList<HierarchicalFSM> m_stack;

  /**
   * create
   * 
   * @param context
   *          the input context
   */
  _CSVInputToken(final ExperimentSetContext context) {
    super();
    this.m_done = new HashSet<>(8192);
    this.m_stack = new ArrayList<>();
    this.m_stack.add(context);
  }

  /**
   * check if a path is new and should be processed
   * 
   * @param path
   *          the path
   * @param store
   *          should we store or just check?
   * @return {@code true} if the path has not been processed before,
   *         {@code false} otherwise
   */
  final boolean _isPathNew(final Path path, final boolean store) {
    synchronized (this.m_done) {
      if (store) {
        return this.m_done.add(path);
      }
      return this.m_done.contains(path);
    }
  }

  /**
   * are we currently in the root level?
   * 
   * @param job
   *          the job
   * @param dir
   *          the directory
   * @throws IOException
   *           if i/o fails
   */
  final void _checkDir(final IOJob job, final Path dir) throws IOException {
    Path candidate;

    synchronized (this.m_stack) {

      if (this.m_stack.size() == 1) {

        for (final String file : _CSVInputToken.FILES) {
          candidate = PathUtils.normalize(dir.resolve(file));
          if (Files.isRegularFile(candidate)) {
            this._parseEDI(job, candidate);
          }
        }
      }
    }
  }

  /**
   * parse edi
   * 
   * @param job
   *          the job
   * @param path
   *          the path
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  final void _parseEDI(final IOJob job, final Path path)
      throws IOException {
    final IFileInputJobBuilder<ExperimentSetContext> isb;
    final Logger logger;
    final HierarchicalFSM fsm;
    final EDIInput tool;

    synchronized (this.m_stack) {
      if (this.m_stack.size() == 1) {
        if (this._isPathNew(path, true)) {
          fsm = this.m_stack.get(0);
          if (fsm instanceof ExperimentSetContext) {
            tool = EDIInput.getInstance();
            if (tool.canUse()) {
              isb = tool.use();
              if (isb != null) {
                isb.addPath(path);
                logger = job.getLogger();
                if (logger != null) {
                  isb.setLogger(job.getLogger());
                }
                isb.setDestination((ExperimentSetContext) fsm);
                isb.create().call();
              }
            }
          }
        }
      }
    }
  }
}
