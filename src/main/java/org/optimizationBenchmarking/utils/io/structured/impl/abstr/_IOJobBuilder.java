package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.spec.IIOJob;
import org.optimizationBenchmarking.utils.io.structured.spec.IIOJobBuilder;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;

/**
 * The base class for building IO jobs
 *
 * @param <JBT>
 *          the job builder type
 */
abstract class _IOJobBuilder<JBT extends _IOJobBuilder<JBT>> extends
    ConfigurableToolJobBuilder<IIOJob, JBT> implements IIOJobBuilder {

  /** the tool */
  final IOTool<?> m_tool;

  /** the base path */
  Path m_basePath;

  /**
   * create the IIOJobBuilder
   *
   * @param tool
   *          the owning tool
   */
  _IOJobBuilder(final IOTool<?> tool) {
    super();
    _IOJobBuilder._validateTool(tool);
    this.m_tool = tool;
  }

  /** {@inheritDoc} */
  @Override
  protected final String getParameterPrefix() {
    return this.m_tool.getParameterPrefix();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setBasePath(final Path path) {
    final Path use;

    if (path != null) {
      use = PathUtils.normalize(path);
      if (use == null) {
        throw new IllegalArgumentException(//
            "Cannot set a non-null base path which normalizes to null, but '" //$NON-NLS-1$
                + use + "' does."); //$NON-NLS-1$
      }
    } else {
      use = null;
    }

    this.m_basePath = use;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public JBT configure(final Configuration config) {
    super.configure(config);
    return this.setBasePath(config.getBasePath());
  }

  /**
   * load the give source/dest list string
   *
   * @param location
   *          the location string
   */
  void _location(final String location) {
    int start, end, i;
    String function, parameter;

    if (location == null) {
      return;
    }
    start = location.indexOf('(');
    if (start <= 0) {
      return;
    }
    end = location.lastIndexOf(')');
    if (end <= start) {
      return;
    }

    function = TextUtils.prepare(location.substring(0, start));
    if (function == null) {
      return;
    }

    parameter = TextUtils.prepare(location.substring(start + 1, end));
    if (parameter == null) {
      return;
    }

    if (IOTool.PATH_ELEMENT.equalsIgnoreCase(function)) {
      this._setPath(parameter, null, null);
      return;
    }

    if (IOTool.ZIPPED_PATH_ELEMENT.equalsIgnoreCase(function)) {
      this._setPath(parameter, null, EArchiveType.ZIP);
      return;
    }

    if (IOTool.ZIPPED_URI_ELEMENT.equalsIgnoreCase(function)) {
      this._setURI(parameter, null, EArchiveType.ZIP);
      return;
    }

    if (IOTool.ZIPPED_URL_ELEMENT.equalsIgnoreCase(function)) {
      this._setURL(parameter, null, EArchiveType.ZIP);
      return;
    }

    if (IOTool.RESOURCE_ELEMENT.equalsIgnoreCase(function)) {
      i = parameter.indexOf('#');
      this._setResource(parameter.substring(0, i),
          parameter.substring(i + 1), null, null);
      return;
    }

    if (IOTool.ZIPPED_RESOURCE_ELEMENT.equalsIgnoreCase(function)) {
      i = parameter.indexOf('#');
      this._setResource(parameter.substring(0, i),
          parameter.substring(i + 1), null, EArchiveType.ZIP);
      return;
    }

    throw new UnsupportedOperationException(//
        "Cannot process I/O function '" + function + //$NON-NLS-1$
            "' with parameter '" + parameter + '\'');//$NON-NLS-1$
  }

  /**
   * Set the path
   *
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param archiveType
   *          the archive type
   */
  abstract void _setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set the uri
   *
   * @param uri
   *          the uri
   * @param encoding
   *          the encoding
   * @param archiveType
   *          the archive type
   */
  abstract void _setURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set the url
   *
   * @param url
   *          the url
   * @param encoding
   *          the encoding
   * @param archiveType
   *          the archive type
   */
  abstract void _setURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set the resource
   *
   * @param clazz
   *          the class
   * @param resource
   *          the resource name
   * @param encoding
   *          the encoding
   * @param archiveType
   *          the archive type
   */
  abstract void _setResource(final String clazz, final String resource,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * validate the tool
   *
   * @param tool
   *          the tool
   */
  static final void _validateTool(final IOTool<?> tool) {
    if (tool == null) {
      throw new IllegalArgumentException("Tool must not be null."); //$NON-NLS-1$
    }
    if (!(tool.canUse())) {
      throw new IllegalArgumentException("Tool '" + //$NON-NLS-1$
          tool.toString() + "' cannot be used."); //$NON-NLS-1$
    }
  }

  /**
   * create the job
   *
   * @return the job
   */
  abstract IIOJob _doCreate();

  /** {@inheritDoc} */
  @Override
  public final IIOJob create() {
    this.validate();
    return this._doCreate();
  }
}
