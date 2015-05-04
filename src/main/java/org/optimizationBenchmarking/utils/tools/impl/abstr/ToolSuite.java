package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.io.InputStream;
import java.util.Properties;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A tool which marks the entry point to a whole tool suite.
 */
public abstract class ToolSuite extends Tool {

  /** the unknown string */
  private static final String UNKNOWN = "unknown";//$NON-NLS-1$
  /** the version string */
  private static final String PROJECT_VERSION = "project.version";//$NON-NLS-1$
  /** the tool suite string */
  private static final String PROJECT_NAME = "project.name";//$NON-NLS-1$
  /** the jdk string */
  private static final String PROJECT_JDK = "project.jdk";//$NON-NLS-1$
  /** the url string */
  private static final String PROJECT_URL = "project.url";//$NON-NLS-1$
  /** the contact name string */
  private static final String CONTACT_NAME = "contact.name";//$NON-NLS-1$
  /** the contact email string */
  private static final String CONTACT_EMAIL = "contact.email";//$NON-NLS-1$
  /** the contact url string */
  private static final String CONTACT_URL = "contact.url";//$NON-NLS-1$

  /** the version */
  private String m_projectVersion;

  /** the tool suite */
  private String m_projectName;

  /** the jdk */
  private String m_projectJdk;

  /** the url */
  private String m_projectUrl;

  /** the contact name */
  private String m_contactName;
  /** the contact email */
  private String m_contactEmail;
  /** the contact url */
  private String m_contactUrl;

  /** create the tool suite */
  protected ToolSuite() {
    super();
  }

  /** Load the properties of the evaluator */
  private synchronized final void __loadProperties() {
    final Properties properties;
    final Class<?> clazz;

    properties = new Properties();
    clazz = this.getClass();
    try (final InputStream input = clazz.getResourceAsStream(//
        clazz.getSimpleName() + ".properties")) { //$NON-NLS-1$
      properties.load(input);
    } catch (final Throwable error) {
      ErrorUtils.logError(
          Configuration.getGlobalLogger(),
          (("Error while loading the properties of " + //$NON-NLS-1$
          TextUtils.className(clazz)) + '.'), error, true,
          RethrowMode.DONT_RETHROW);
    }

    this.m_projectVersion = TextUtils.prepare(properties.getProperty(//
        ToolSuite.PROJECT_VERSION));
    if (this.m_projectVersion == null) {
      this.m_projectVersion = ToolSuite.UNKNOWN;
    }

    this.m_projectName = TextUtils.prepare(properties.getProperty(//
        ToolSuite.PROJECT_NAME));
    if (this.m_projectName == null) {
      this.m_projectName = ToolSuite.UNKNOWN;
    }

    this.m_projectJdk = TextUtils.prepare(properties.getProperty(//
        ToolSuite.PROJECT_JDK));
    if (this.m_projectJdk == null) {
      this.m_projectJdk = ToolSuite.UNKNOWN;
    }

    this.m_projectUrl = TextUtils.prepare(properties.getProperty(//
        ToolSuite.PROJECT_URL));
    if (this.m_projectUrl == null) {
      this.m_projectUrl = "http://www.it-weise.de/"; //$NON-NLS-1$
    }

    this.m_contactUrl = TextUtils.prepare(properties.getProperty(//
        ToolSuite.CONTACT_URL));
    if (this.m_contactUrl == null) {
      this.m_contactUrl = this.m_projectUrl;
    }

    this.m_contactName = TextUtils.prepare(properties.getProperty(//
        ToolSuite.CONTACT_NAME));
    if (this.m_contactName == null) {
      this.m_contactName = ToolSuite.UNKNOWN;
    }

    this.m_contactEmail = TextUtils.prepare(properties.getProperty(//
        ToolSuite.CONTACT_EMAIL));
    if (this.m_contactEmail == null) {
      this.m_contactEmail = ToolSuite.UNKNOWN;
    }
  }

  /**
   * Get the version of this tool suite
   *
   * @return the version of this tool suite
   */
  public synchronized final String getProjectVersion() {
    if (this.m_projectVersion == null) {
      this.__loadProperties();
    }
    return this.m_projectVersion;
  }

  /**
   * Get the name of this tool suite
   *
   * @return the name of this tool suite
   */
  public synchronized final String getProjectName() {
    if (this.m_projectName == null) {
      this.__loadProperties();
    }
    return this.m_projectName;
  }

  /**
   * Get the name of this tool suite
   *
   * @return the name of this tool suite
   */
  @Override
  public synchronized final String toString() {
    return this.getProjectName();
  }

  /**
   * Get the PROJECT_JDK version for this tool suite
   *
   * @return the PROJECT_JDK version for this tool suite
   */
  public synchronized final String getProjectJDK() {
    if (this.m_projectJdk == null) {
      this.__loadProperties();
    }
    return this.m_projectJdk;
  }

  /**
   * Get the project url for this tool suite
   *
   * @return the project url for this tool suite
   */
  public synchronized final String getProjectURL() {
    if (this.m_projectUrl == null) {
      this.__loadProperties();
    }
    return this.m_projectUrl;
  }

  /**
   * Get the contact url for this tool suite
   *
   * @return the contact url for this tool suite
   */
  public synchronized final String getContactURL() {
    if (this.m_contactUrl == null) {
      this.__loadProperties();
    }
    return this.m_contactUrl;
  }

  /**
   * Get the contact name for this tool suite
   *
   * @return the contact name for this tool suite
   */
  public synchronized final String getContactName() {
    if (this.m_contactName == null) {
      this.__loadProperties();
    }
    return this.m_contactName;
  }

  /**
   * Get the contact email for this tool suite
   *
   * @return the contact email for this tool suite
   */
  public synchronized final String getContactEmail() {
    if (this.m_contactEmail == null) {
      this.__loadProperties();
    }
    return this.m_contactEmail;
  }
}
