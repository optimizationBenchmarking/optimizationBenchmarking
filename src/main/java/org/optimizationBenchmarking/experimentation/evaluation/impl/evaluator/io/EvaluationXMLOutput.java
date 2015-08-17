package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.EvaluationModules;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleEntry;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLOutput;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** the evaluation modules xml output */
public final class EvaluationXMLOutput extends
    XMLOutputTool<EvaluationModules> {

  /** create */
  EvaluationXMLOutput() {
    super();
  }

  /**
   * get the instance of the {@link EvaluationXMLOutput}
   *
   * @return the instance of the {@link EvaluationXMLOutput}
   */
  public static final EvaluationXMLOutput getInstance() {
    return __EvaluationXMLOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    ConfigurationXMLOutput.getInstance().checkCanUse();
    super.checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (super.canUse() && //
    ConfigurationXMLOutput.getInstance().canUse());
  }

  /** {@inheritDoc} */
  @Override
  protected final void xml(final IOJob job, final EvaluationModules data,
      final XMLBase xmlBase) throws Throwable {
    final ConfigurationXMLOutput cxo;
    final Configuration rootConfig;
    Configuration config;

    try (XMLElement root = xmlBase.element()) {
      root.namespaceSetPrefix(EvaluationXML.NAMESPACE_URI, "e"); //$NON-NLS-1$
      root.namespaceSetPrefix(
          ConfigurationXML.CONFIG_XML.getNamespaceURI(),
          ConfigurationXMLOutput.CONFIGURATION_NAMESPACE_PREFIX);
      root.name(EvaluationXML.NAMESPACE_URI,
          EvaluationXML.ELEMENT_EVALUATION);

      rootConfig = data.getConfiguration();
      cxo = ConfigurationXMLOutput.getInstance();

      cxo.use().setLogger(job.getLogger()).setSource(rootConfig)
          .setWriter(root).create().call();

      for (final ModuleEntry module : data.getEntries()) {
        try (final XMLElement param = root.element()) {
          param.name(EvaluationXML.NAMESPACE_URI,
              EvaluationXML.ELEMENT_MODULE);

          param.attributeEncoded(EvaluationXML.NAMESPACE_URI,
              EvaluationXML.ATTRIBUTE_CLASS,
              TextUtils.className(module.getModule().getClass()));
          config = module.getConfiguration();
          if (config != rootConfig) {
            cxo.use().setLogger(job.getLogger()).setSource(config)
                .setWriter(root).create().call();
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Evaluation XML Output"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final void file(final IOJob job, final EvaluationModules data,
      final Path file, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    super.file(job, data, file, encoding);
    if (Files.exists(file)) {
      this.addFile(job, file, EvaluationXML.EVALUATION_XML);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final String getDefaultPlainOutputFileName() {
    return ("evaluation." + //$NON-NLS-1$
    EvaluationXML.EVALUATION_XML.getDefaultSuffix());
  }

  /** the loader */
  private static final class __EvaluationXMLOutputLoader {
    /** the globally shared evaluation xml output writer instance */
    static final EvaluationXMLOutput INSTANCE = new EvaluationXMLOutput();
  }
}
