package test.junit.org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.io.BibTeXOutput;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJob;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJobBuilder;

import test.junit.FileProducerCollector;
import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;
import examples.org.optimizationBenchmarking.utils.bibliography.data.RandomBibliography;
import examples.org.optimizationBenchmarking.utils.graphics.GraphicsExample;

/** The test of the LaTeX tool chain */
public class LaTeXTest extends ToolTest<LaTeX> {

  /** the references file */
  private static final String BIB_FILE = "references"; //$NON-NLS-1$

  /** create */
  public LaTeXTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected LaTeX getInstance() {
    return LaTeX.getInstance();
  }

  /**
   * create a simple example
   *
   * @param graphicFormat
   *          a graphic format to include (or {@code null} if none is
   *          needed
   * @param useBib
   *          should we create a bibliography? a bibliography
   */
  private final void __test(final EGraphicFormat graphicFormat,
      final boolean useBib) {
    final Path path;
    final IGraphicDriver driver;
    final BibTeXOutput bibOut;
    final FileProducerCollector listener, all;
    final LaTeX tool;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;
    final ArrayList<IFileType> required;
    Path graphic;
    Bibliography bib;
    boolean first;

    try (final TempDir temp = new TempDir()) {
      path = PathUtils.createPathInside(temp.getPath(), "document.tex"); //$NON-NLS-1$

      bib = null;
      if (useBib) {
        bibOut = BibTeXOutput.getInstance();
        Assert.assertNotNull(bibOut);
        Assert.assertTrue(bibOut.canUse());
        bib = new RandomBibliography().createBibliography();

        bibOut.use()//
            .setSource(bib)//
            .setPath(temp.getPath()//
                .resolve(LaTeXTest.BIB_FILE + '.'//
                    + ELaTeXFileType.BIB.getDefaultSuffix()))//
            .create().call();
      }

      graphic = null;
      if (graphicFormat != null) {
        driver = graphicFormat.getDefaultDriver();
        Assert.assertNotNull(driver);
        Assert.assertTrue(driver.canUse());

        listener = new FileProducerCollector();
        try (final Graphic g = driver.use().setBasePath(temp.getPath())//
            .setMainDocumentNameSuggestion("graphic")//$NON-NLS-1$
            .setFileProducerListener(listener)//
            .setSize(new PhysicalDimension(5, 5, ELength.CM))//
            .create()) {
          GraphicsExample.paint(g);
        }

        for (final Map.Entry<Path, IFileType> e : listener
            .getProducedFiles().entrySet()) {
          if (graphicFormat.equals(e.getValue())) {
            Assert.assertNull(graphic);
            graphic = e.getKey();
          }
        }
        Assert.assertNotNull(graphic);
      }

      try (final OutputStream os = PathUtils.openOutputStream(path)) {
        try (final OutputStreamWriter fos = new OutputStreamWriter(os)) {
          try (final BufferedWriter bw = new BufferedWriter(fos)) {
            bw.write("\\documentclass{article}%");//$NON-NLS-1$
            bw.newLine();
            if (graphic != null) {
              bw.write("\\RequirePackage{graphicx}%");//$NON-NLS-1$
              bw.newLine();
            }

            bw.write("\\begin{document}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\title{Example Document}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\author{Thomas Weise}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\maketitle%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\begin{abstract}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("This is an example abstract. It is short and useless.%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\end{abstract}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\section{Introduction}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\label{Introduction}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("This is the introduction section~\\ref{Introduction}. It is useless too.%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\begin{itemize}%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\item here is one item.%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\item and another one.%");//$NON-NLS-1$
            bw.newLine();
            bw.write("\\end{itemize}%");//$NON-NLS-1$
            bw.newLine();

            if (graphic != null) {
              bw.write("\\begin{figure}%");//$NON-NLS-1$
              bw.newLine();

              bw.write("\\begin{center}%");//$NON-NLS-1$
              bw.newLine();

              bw.write("\\caption{This is a figure.}%");//$NON-NLS-1$
              bw.newLine();
              bw.write("\\includegraphics{");//$NON-NLS-1$
              bw.write(temp.getPath().relativize(graphic).toString());
              bw.write("}%");//$NON-NLS-1$

              bw.newLine();
              bw.write("\\end{center}%");//$NON-NLS-1$

              bw.newLine();
              bw.write("\\end{figure}%");//$NON-NLS-1$

              bw.newLine();
            }

            bw.newLine();
            bw.write(//
            "blablablabla blablablabla ");//$NON-NLS-1$
            bw.write(//
            "blablablabla blablablabla ");//$NON-NLS-1$
            bw.write(//
            "blablablabla blablablabla ");//$NON-NLS-1$
            bw.write(//
            "blablablabla blablablabla ");//$NON-NLS-1$

            if (bib != null) {
              bw.write("\\cite{");//$NON-NLS-1$
              first = true;
              for (final BibRecord rec : bib) {
                if (first) {
                  first = false;
                } else {
                  bw.write(',');
                }
                bw.write(rec.getKey());
              }
              bw.write('}');
            }
            bw.write('.');
            bw.write('%');
            bw.newLine();

            if (bib != null) {
              bw.newLine();
              bw.write("\\bibliographystyle{unsrt}.%");//$NON-NLS-1$
              bw.newLine();
              bw.write("\\bibliography{");//$NON-NLS-1$
              bw.write(LaTeXTest.BIB_FILE);
              bw.write('}');
              bw.write('%');
              bw.newLine();
            }

            bw.write("\\end{document}%");//$NON-NLS-1$
            bw.newLine();
          }
        }

        tool = this.getInstance();
        Assert.assertNotNull(tool);

        builder = tool.use();
        all = new FileProducerCollector();
        builder.setFileProducerListener(all);
        builder.setMainFile(path);
        required = new ArrayList<>();
        builder.requireFileType(ELaTeXFileType.TEX);
        required.add(ELaTeXFileType.TEX);

        if (bib != null) {
          builder.requireFileType(ELaTeXFileType.BIB);
          required.add(ELaTeXFileType.BIB);
        }

        if (graphicFormat != null) {
          builder.requireFileType(graphicFormat);
          required.add(graphicFormat);
        }

        job = builder.create();
        Assert.assertNotNull(job);
        try {
          job.call();
        } catch (final Throwable t) {
          throw new RuntimeException(//
              "LaTeX document compilation failed.", //$NON-NLS-1$
              t);
        }

        if (this.getInstance().hasToolChainFor(
            required.toArray(new IFileType[required.size()]))) {
          all.assertFilesOfType(ELaTeXFileType.PDF);
        }

      } catch (final Throwable tt) {
        throw new RuntimeException(//
            "Example LaTeX file generation failed.",//$NON-NLS-1$
            tt);
      }

    } catch (final IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * test whether we run the tool chain for a simple example
   */
  @Test(timeout = 3600000)
  public void testCanBuildPlain() {
    this.__test(null, false);
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   */
  @Test(timeout = 3600000)
  public void testCanBuildWithBibTeX() {
    this.__test(null, true);
  }

  /**
   * test whether we run the tool chain for
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PDF}
   * figures
   */
  @Test(timeout = 3600000)
  public void testCanBuildWithPDF() {
    this.__test(EGraphicFormat.PDF, false);
  }

  /**
   * test whether we run the tool chain for
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#EPS}
   * figures
   */
  @Test(timeout = 3600000)
  public void testCanBuildWithEPS() {
    this.__test(EGraphicFormat.EPS, false);
  }

  /**
   * test whether we run the tool chain for
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#JPEG}
   * figures
   */
  @Test(timeout = 3600000)
  public void testCanBuildWithJPEG() {
    this.__test(EGraphicFormat.JPEG, false);
  }

  /**
   * test whether we run the tool chain for
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat#PNG}
   * figures
   */
  @Test(timeout = 3600000)
  public void testCanBuildWithPNG() {
    this.__test(EGraphicFormat.PNG, false);
  }
}
