package org.optimizationBenchmarking.utils.document.impl.latex;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Article;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The builder for LaTeX configurations */
public class LaTeXConfigurationBuilder extends
    DocumentConfigurationBuilder {

  /** the document class */
  public static final String PARAM_DOCUMENT_CLASS = "documentClass"; //$NON-NLS-1$

  /** the search path for document classes */
  private static final InstanceParser<LaTeXDocumentClass> DOCUMENT_CLASS_PARSER;

  static {
    final LinkedHashSet<String> path;

    path = new LinkedHashSet<>();

    ReflectionUtils.addPackageOfClassToPrefixList(Article.class, path);
    ReflectionUtils.addPackageOfClassToPrefixList(
        LaTeXDocumentClass.class, path);

    DOCUMENT_CLASS_PARSER = new InstanceParser<>(LaTeXDocumentClass.class,
        path.toArray(new String[path.size()]));
  }

  /** the document class */
  private LaTeXDocumentClass m_documentClass;

  /** create the LaTeX document configuration builder */
  public LaTeXConfigurationBuilder() {
    this(null);
  }

  /**
   * create the LaTeX document configuration builder
   * 
   * @param driver
   *          the driver to use
   */
  protected LaTeXConfigurationBuilder(final LaTeXDriver driver) {
    super((driver != null) ? driver : LaTeXDriver.getInstance());
  }

  /** {@inheritDoc} */
  @Override
  public LaTeXConfiguration immutable() {
    return new LaTeXConfiguration(this);
  }

  /** {@inheritDoc} */
  @Override
  protected void configureWithoutDriver(final Configuration config) {
    final LaTeXDocumentClass newClass;

    super.configureWithoutDriver(config);

    newClass = config.get(LaTeXConfigurationBuilder.PARAM_DOCUMENT_CLASS,
        LaTeXConfigurationBuilder.DOCUMENT_CLASS_PARSER, null);
    if (newClass != null) {
      this.setDocumentClass(newClass);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void assign(final GraphicConfiguration copyFrom) {
    final LaTeXConfigurationBuilder builder;
    final LaTeXConfiguration config;
    final LaTeXDocumentClass newClass;

    if (copyFrom != null) {
      if (copyFrom instanceof LaTeXConfigurationBuilder) {
        builder = ((LaTeXConfigurationBuilder) copyFrom);
        if ((newClass = builder.m_documentClass) != null) {
          this.setDocumentClass(newClass);
        }
      } else {
        if (copyFrom instanceof LaTeXConfiguration) {
          config = ((LaTeXConfiguration) copyFrom);
          if ((newClass = config.getDocumentClass()) != null) {
            this.setDocumentClass(newClass);
          }
        }
      }

      super.assign(copyFrom);
    }
  }

  /**
   * Get the document class
   * 
   * @return the document class
   */
  public final LaTeXDocumentClass getDocumentClass() {
    return ((this.m_documentClass != null) ? this.m_documentClass
        : LaTeXDocumentClass.getDefaultDocumentClass());
  }

  /**
   * Set the document class
   * 
   * @param documentClass
   *          the document class
   */
  public final void setDocumentClass(final LaTeXDocumentClass documentClass) {
    LaTeXConfiguration._checkDocumentClass(documentClass);
    this.m_documentClass = documentClass;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(super.hashCode(),
        HashUtils.hashCode(this.getDocumentClass()));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {

    if (o == this) {
      return true;
    }
    if (o instanceof LaTeXConfiguration) {
      if (super.equals(o)) {
        return (EComparison.equals(this.getDocumentClass(),
            ((LaTeXConfiguration) o).getDocumentClass()));
      }
    } else {
      if (o instanceof LaTeXConfigurationBuilder) {
        if (super.equals(o)) {
          return (EComparison.equals(this.getDocumentClass(),
              ((LaTeXConfigurationBuilder) o).getDocumentClass()));
        }
      }
    }

    return false;
  }

  /** to string */
  @Override
  public void toText(final ITextOutput textOut) {
    super.toText(textOut);
    if (this.m_documentClass != null) {
      textOut.append('@');
      LaTeXConfiguration._documentClass(this.m_documentClass, textOut);
    }
  }

}
