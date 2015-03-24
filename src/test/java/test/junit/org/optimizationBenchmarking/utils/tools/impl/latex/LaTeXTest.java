package test.junit.org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeX;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJob;
import org.optimizationBenchmarking.utils.tools.impl.latex.LaTeXJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/** The test of the LaTeX tool chain */
public class LaTeXTest extends ToolTest<LaTeX> {

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
   * @param temp
   *          the temp dir
   * @return the path to the example
   */
  private final Path __makePlainExample(final TempDir temp) {
    final Path path;

    path = PathUtils.createPathInside(temp.getPath(), "document.tex"); //$NON-NLS-1$

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter fos = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(fos)) {
          bw.write("\\documentclass{article}%");//$NON-NLS-1$
          bw.newLine();
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
          bw.write("\\end{document}%");//$NON-NLS-1$
          bw.newLine();
        }
      }
    } catch (Throwable tt) {
      throw new RuntimeException(//
          "Example LaTeX file generation failed.",//$NON-NLS-1$
          tt);
    }

    return path;
  }

  /**
   * test whether we run the tool chain for a simple example
   */
  @Test(timeout = 3600000)
  public void testCanBuildPlainExample() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makePlainExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * create an example also using BibTeX
   * 
   * @param temp
   *          the temp dir
   * @return the path to the example
   */
  private final Path __makeBibTeXExample(final TempDir temp) {
    final Path path;

    path = PathUtils.createPathInside(temp.getPath(), "document.tex"); //$NON-NLS-1$

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter fos = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(fos)) {
          bw.write("\\documentclass{article}%");//$NON-NLS-1$
          bw.newLine();
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
          bw.write("\\item here we cite paper \\cite{paperA}.%");//$NON-NLS-1$
          bw.newLine();
          bw.write("\\end{itemize}%");//$NON-NLS-1$
          bw.newLine();
          bw.write("and here we cite paper \\cite{paperB}.%");//$NON-NLS-1$
          bw.newLine();
          bw.write("\\bibliographystyle{unsrt}.%");//$NON-NLS-1$
          bw.newLine();
          bw.write("\\bibliography{references}.%");//$NON-NLS-1$
          bw.newLine();
          bw.write("\\end{document}%");//$NON-NLS-1$
          bw.newLine();
        }
      }
    } catch (Throwable tt) {
      throw new RuntimeException(//
          "Example LaTeX file generation failed.",//$NON-NLS-1$
          tt);
    }

    try (final OutputStream os = PathUtils.openOutputStream(PathUtils
        .createPathInside(temp.getPath(), "references.bib"))) {//$NON-NLS-1$
      try (final OutputStreamWriter fos = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(fos)) {
          bw.write("@inproceedings{paperA,");//$NON-NLS-1$
          bw.newLine();
          bw.write("author = {Not Me},");//$NON-NLS-1$
          bw.newLine();
          bw.write("title = {Some Title},");//$NON-NLS-1$
          bw.newLine();
          bw.write("booktitle = {Some Book Title},");//$NON-NLS-1$
          bw.newLine();
          bw.write("year = {2014},");//$NON-NLS-1$
          bw.newLine();
          bw.write("month = jan,");//$NON-NLS-1$
          bw.newLine();
          bw.write("publisher = {A Big Company},");//$NON-NLS-1$
          bw.newLine();
          bw.write("address = {This Place},");//$NON-NLS-1$
          bw.newLine();
          bw.write("editor = {Cool Guy},");//$NON-NLS-1$
          bw.newLine();
          bw.write("pages = {173--245},");//$NON-NLS-1$
          bw.newLine();
          bw.write("}");//$NON-NLS-1$
          bw.newLine();
          bw.newLine();
          bw.write("@article{paperB,");//$NON-NLS-1$
          bw.newLine();
          bw.write("author = {Egon Olsen},");//$NON-NLS-1$
          bw.newLine();
          bw.write("journal = {Some Journal},");//$NON-NLS-1$
          bw.newLine();
          bw.write("year = {2014},");//$NON-NLS-1$
          bw.newLine();
          bw.write("month = jan,");//$NON-NLS-1$
          bw.newLine();
          bw.write("volume = {200},");//$NON-NLS-1$
          bw.newLine();
          bw.write("number = {12},");//$NON-NLS-1$
          bw.newLine();
          bw.write("pages = {73--145},");//$NON-NLS-1$
          bw.newLine();
          bw.write("}");//$NON-NLS-1$
          bw.newLine();
        }
      }
    } catch (Throwable tt) {
      throw new RuntimeException(//
          "Example BibTeX file generation failed.",//$NON-NLS-1$
          tt);
    }

    return path;
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   */
  @Test(timeout = 3600000)
  public void testCanBuildBibTeXExample() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makeBibTeXExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      builder.requireFileType(ELaTeXFileType.BIB);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   * and a mock requirement for JPEG images
   */
  @Test(timeout = 3600000)
  public void testCanBuildBibTeXExampleWithMockJPEGRequirement() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makeBibTeXExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      builder.requireFileType(ELaTeXFileType.BIB);
      builder.requireFileType(EGraphicFormat.JPEG);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   * and a mock requirement for PDF images
   */
  @Test(timeout = 3600000)
  public void testCanBuildBibTeXExampleWithMockPDFRequirement() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makeBibTeXExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      builder.requireFileType(ELaTeXFileType.BIB);
      builder.requireFileType(EGraphicFormat.PDF);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   * and a mock requirement for EPS images
   */
  @Test(timeout = 3600000)
  public void testCanBuildBibTeXExampleWithMockEPSRequirement() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makeBibTeXExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      builder.requireFileType(ELaTeXFileType.BIB);
      builder.requireFileType(EGraphicFormat.EPS);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /**
   * test whether we run the tool chain for a simple example with BibTeX
   * and a mock requirement for PNG images
   */
  @Test(timeout = 3600000)
  public void testCanBuildBibTeXExampleWithMockPNGRequirement() {
    final LaTeX tool;
    final __FileProducerListener listener;
    final LaTeXJobBuilder builder;
    final LaTeXJob job;

    tool = this.getInstance();
    Assert.assertNotNull(tool);
    try (final TempDir temp = new TempDir()) {
      builder = tool.use();
      listener = new __FileProducerListener();
      builder.setFileProducerListener(listener);
      builder.setMainFile(this.__makeBibTeXExample(temp));
      builder.requireFileType(ELaTeXFileType.TEX);
      builder.requireFileType(ELaTeXFileType.BIB);
      builder.requireFileType(EGraphicFormat.PNG);
      job = builder.create();
      Assert.assertNotNull(job);
      try {
        job.call();
      } catch (Throwable t) {
        throw new RuntimeException(//
            "LaTeX document compilation failed.", //$NON-NLS-1$
            t);
      }
      Assert.assertNotNull(listener.m_types);

      if (listener.m_types.size() > 0) {
        Assert.assertTrue(listener.m_types.contains(ELaTeXFileType.PDF));
      }

    } catch (IOException ioe) {
      throw new RuntimeException(//
          "LaTeX test failed.", //$NON-NLS-1$
          ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testCanBuildPlainExample();
    this.testCanBuildBibTeXExample();
    this.testCanBuildBibTeXExampleWithMockEPSRequirement();
    this.testCanBuildBibTeXExampleWithMockJPEGRequirement();
    this.testCanBuildBibTeXExampleWithMockPDFRequirement();
    this.testCanBuildBibTeXExampleWithMockPNGRequirement();
  }

  /** the listener */
  private static final class __FileProducerListener implements
      IFileProducerListener {

    /** the file types */
    HashSet<IFileType> m_types;

    /** create */
    __FileProducerListener() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public void onFilesFinalized(
        final Collection<Entry<Path, IFileType>> result) {
      Assert.assertNotNull(result);

      this.m_types = new HashSet<>();
      for (Entry<Path, IFileType> entry : result) {
        Assert.assertNotNull(entry);
        Assert.assertNotNull(entry.getKey());
        Assert.assertTrue(Files.exists(entry.getKey()));
        Assert.assertNotNull(entry.getValue());
        this.m_types.add(entry.getValue());
      }
    }
  }
}
