package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.ProcessExecutor;

/** check whether a program produces the output we'd expect from {@code R} */
final class _RCriterion implements IPredicate<Path> {

  /** create */
  _RCriterion() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Path object) {
    ExternalProcessBuilder esb;
    String s;
    boolean hasRVersion, hasRFoundation;

    if (ProcessExecutor.INSTANCE.canUse()) {
      esb = ProcessExecutor.INSTANCE.use();
      esb.setExecutable(object);
      esb.addStringArgument("--version"); //$NON-NLS-1$
      esb.setDirectory(PathUtils.getTempDir());
      esb.setMergeStdOutAndStdErr(true);
      esb.setStdIn(EProcessStream.IGNORE);
      hasRVersion = hasRFoundation = false;

      try (ExternalProcess ep = esb.create()) {
        try (InputStreamReader isr = new InputStreamReader(ep.getStdOut())) {
          try (BufferedReader br = new BufferedReader(isr)) {
            while ((s = br.readLine()) != null) {
              s = TextUtils.prepare(s);
              if (s != null) {
                if (s.startsWith("R version ")) { //$NON-NLS-1$
                  hasRVersion = true;
                  if (hasRFoundation) {
                    break;
                  }
                } else {
                  if (s.contains(//
                      "The R Foundation for Statistical Computing")) { //$NON-NLS-1$
                    hasRFoundation = true;
                    if (hasRVersion) {
                      break;
                    }
                  }
                }
              }
            }
          }
        }

        if (ep.waitFor() == 0) {
          return (hasRVersion && hasRFoundation);
        }
      } catch (final Throwable t) {
        return false;
      }
    }

    return false;
  }

}
