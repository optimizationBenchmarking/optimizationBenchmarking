package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A builder for LaTeX jobs.
 */
public class LaTeXJobBuilder extends
    ConfigurableToolJobBuilder<LaTeXJob, LaTeXJobBuilder> implements
    IFileProducerJobBuilder {

  /** the source file */
  public static final String PARAM_MAIN = "latexSource"; //$NON-NLS-1$
  /** the formats */
  public static final String PARAM_FORMATS = "latexUsedFormats"; //$NON-NLS-1$

  /** the file types which need to be processed. */
  private HashSet<IFileType> m_types;

  /** the main file */
  private Path m_main;

  /** the file producer listener */
  private IFileProducerListener m_listener;

  /** create the job builder */
  LaTeXJobBuilder() {
    super();
    this.m_types = new HashSet<>();
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return "latex"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final LaTeXJobBuilder configure(
      final Configuration config) {
    final Path source;
    final ArrayListView<String> required;
    IFileType fileFormat;

    this.__checkState();

    super.configure(config);

    source = config.getPath(LaTeXJobBuilder.PARAM_MAIN, null);
    if (source != null) {
      this.setMainFile(source);
    }

    required = config.getStringList(LaTeXJobBuilder.PARAM_FORMATS, null);
    if (required != null) {
      for (final String format : required) {
        fileFormat = LaTeXJobBuilder.__findFormat(format);

        if (fileFormat != null) {
          this.requireFileType(fileFormat);
        }
      }
    }

    return this;
  }

  /**
   * find a format
   * 
   * @param string
   *          the string
   * @return the format
   */
  @SuppressWarnings("rawtypes")
  private static final IFileType __findFormat(final String string) {
    String s;
    int i;

    if (string == null) {
      return null;
    }

    for (i = 0; i < 2; i++) {
      for (final IFileType type : ((i == 0) ? EGraphicFormat.INSTANCES
          : ELaTeXFileType.INSTANCES)) {
        s = type.getName();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        s = type.getDefaultSuffix();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        s = type.getMIMEType();
        if (s != null) {
          if (s.equalsIgnoreCase(string)) {
            return type;
          }
        }

        if (type instanceof Enum) {
          s = ((Enum) type).name();
          if (s != null) {
            if (s.equalsIgnoreCase(string)) {
              return type;
            }
          }
        }
      }
    }

    return null;
  }

  /**
   * Require a given file type
   * 
   * @param type
   *          the type
   * @return this builder
   */
  public synchronized LaTeXJobBuilder requireFileType(final IFileType type) {
    final IFileType put;

    if (type == null) {
      throw new IllegalArgumentException(
          "A file type for which you require LaTeX tool chain support cannot be null."); //$NON-NLS-1$
    }

    cannotAdd: {

      if (type instanceof EGraphicFormat) {
        put = type;
        break cannotAdd;
      }

      for (final ELaTeXFileType types : ELaTeXFileType.INSTANCES) {
        if (_LaTeXToolChainComponent._equals(types, type)) {
          if (types._canRequire()) {
            put = types;
            break cannotAdd;
          }
          if (types == ELaTeXFileType.PDF) {
            put = EGraphicFormat.PDF;
            break cannotAdd;
          }

          return this;
        }
      }

      throw new IllegalArgumentException(
          (("No LaTeX tool chain can be required to understand type '" //$NON-NLS-1$
          + type) + '\'') + '.');
    }

    this.__checkState();
    this.m_types.add(put);
    return this;
  }

  /** check the state */
  private final void __checkState() {
    if (this.m_types == null) {
      throw new IllegalStateException(//
          "LaTeX job already created."); //$NON-NLS-1$
    }
  }

  /**
   * Set the main file for the LaTeX compilation
   * 
   * @param path
   *          the path
   * @return the builder
   */
  public synchronized final LaTeXJobBuilder setMainFile(final Path path) {
    final Path normalized;

    if (path == null) {
      throw new IllegalArgumentException(//
          "Path to main LaTeX file cannot be null.");//$NON-NLS-1$
    }

    normalized = PathUtils.normalize(path);
    if (normalized == null) {
      throw new IllegalArgumentException(//
          "Normalized path to main LaTeX file (" //$NON-NLS-1$
              + path + ") cannot be null.");//$NON-NLS-1$
    }

    if (!(Files.exists(normalized))) {
      throw new IllegalArgumentException(//
          "Main LaTeX file (" //$NON-NLS-1$
              + normalized + ") must exist.");//$NON-NLS-1$
    }

    try {
      PathUtils.getPhysicalFile(normalized);
    } catch (final Throwable t) {
      throw new IllegalArgumentException(((//
          "Main LaTeX file (" //$NON-NLS-1$
          + normalized) + ") must be a physical file.")//$NON-NLS-1$
          , t);
    }

    this.__checkState();
    this.m_main = normalized;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final LaTeXJobBuilder setFileProducerListener(
      final IFileProducerListener listener) {

    if (listener == null) {
      throw new IllegalArgumentException(//
          "Cannot set null file producer listener.");//$NON-NLS-1$
    }

    this.__checkState();
    this.m_listener = listener;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void validate() {
    if (this.m_main == null) {
      throw new IllegalArgumentException(//
          "Main LaTeX file must be specified.");//$NON-NLS-1$
    }
    super.validate();
  }

  /**
   * Build the main job and make it ready for execution.
   * 
   * @return the main job
   */
  @Override
  public synchronized final LaTeXJob create() {
    final _LaTeXToolChainComponent bibtex, main;
    final ArrayList<_LaTeXToolChainComponent> loop;
    final _LaTeXToolChainComponent[] refine;
    HashSet<IFileType> types;
    IFileType[] required;

    this.validate();
    this.__checkState();

    types = this.m_types;
    this.m_types = null;

    canDo: {

      loop = new ArrayList<>();

      // bibtex is the only ELaTeXFileType we care about
      if (types.remove(ELaTeXFileType.BIB)) {
        bibtex = LaTeXJobBuilder.__bibtex();
        if (bibtex == null) {
          break canDo;
        }
        loop.add(bibtex);
      }

      // to others we don't care
      types.removeAll(ELaTeXFileType.INSTANCES);
      required = types.toArray(new IFileType[types.size()]);
      types = null;
      main = LaTeXJobBuilder.__tex(required);
      required = null;

      if (main == null) {
        break canDo;
      }

      switch (main._produces()) {
        case PDF: {
          refine = null;
          break;
        }
        case DVI: {
          refine = LaTeXJobBuilder.__dvi2pdf();
          if (refine == null) {
            break canDo;
          }
          break;
        }
        case PS: {
          refine = LaTeXJobBuilder.__ps2pdf();
          if (refine == null) {
            break canDo;
          }
          break;
        }
        default: {
          break canDo;
        }
      }

      loop.add(0, main);

      return new _LaTeXMainJob(this.m_main,
          loop.toArray(new _LaTeXToolChainComponent[loop.size()]), refine,
          this.m_listener, this.getLogger());
    }
    return new _NoSuitableToolChainFound(this.m_listener, this.getLogger());
  }

  /**
   * Try to find a LaTeX tool chain component for the given file types
   * 
   * @param required
   *          the required file types
   * @return the tool chain component
   */
  private static final _LaTeXToolChainComponent __tex(
      final IFileType[] required) {
    _LaTeXToolChainComponent comp;

    mainLoop: for (final _LaTeXToolChainComponentDesc desc : _AllEngines.ALL_ENGINES) {
      if (desc == null) {
        continue mainLoop;
      }

      if (required != null) {
        for (final IFileType type : required) {
          if (!(desc._supports(type))) {
            continue mainLoop;
          }
        }
      }

      comp = desc._getComponent();
      if (comp == null) {
        continue mainLoop;
      }
      if (!(comp._canUse())) {
        continue mainLoop;
      }

      return comp;
    }

    return null;
  }

  /**
   * Get a tool chain able to convert dvi files to pdf, or {@code null} if
   * none is found
   * 
   * @return the tool chain, or {@code null} if none is found
   */
  private static final _LaTeXToolChainComponent[] __dvi2pdf() {
    return __DVI_2_PDF.CHAIN;
  }

  /**
   * Get the tool to be used for bibtex, or {@code null} if none is found
   * 
   * @return the tool to be used for bibtex, or {@code null} if none is
   *         found
   */
  private static final _LaTeXToolChainComponent __bibtex() {
    return __BIBTEX.BIBTEX;
  }

  /**
   * Get the tool to be used for ps to pdf conversion, or {@code null} if
   * none is
   * 
   * @return the tool to be used for ps to pdf conversion , or {@code null}
   *         if none is found
   */
  private static final _LaTeXToolChainComponent[] __ps2pdf() {
    return __PS_2_PDF.CHAIN;
  }

  /** the BibTeX to use */
  private static final class __BIBTEX {
    /** the tool chain */
    static final _LaTeXToolChainComponent BIBTEX;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent bibtex;

      bibtex = null;
      desc = _BibTeX._getDescription();
      if (desc != null) {
        bibtex = desc._getComponent();
        if ((bibtex != null) && (!(bibtex._canUse()))) {
          bibtex = null;
        }
      }

      if (bibtex == null) {
        desc = _BibTeX8._getDescription();
        if (desc != null) {
          bibtex = desc._getComponent();
          if ((bibtex != null) && (!(bibtex._canUse()))) {
            bibtex = null;
          }
        }
      }

      BIBTEX = bibtex;
    }
  }

  /** the dvi to pdf chain */
  private static final class __DVI_2_PDF {

    /** the tool chain */
    static final _LaTeXToolChainComponent[] CHAIN;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent dvi2ps, ps2pdf;

      dvi2ps = null;
      desc = _Dvi2Ps._getDescription();
      if (desc != null) {
        dvi2ps = desc._getComponent();
        if ((dvi2ps != null) && (!(dvi2ps._canUse()))) {
          dvi2ps = null;
        }
      }

      ps2pdf = null;
      if (dvi2ps != null) {
        ps2pdf = __PS_2_PDF.CHAIN[0];
      }

      if ((dvi2ps != null) && (ps2pdf != null)) {
        CHAIN = new _LaTeXToolChainComponent[] { dvi2ps, ps2pdf };
      } else {
        CHAIN = null;
      }

    }
  }

  /** the ps to pdf chain */
  private static final class __PS_2_PDF {

    /** the tool */
    static final _LaTeXToolChainComponent[] CHAIN;

    static {
      _LaTeXToolChainComponentDesc desc;
      _LaTeXToolChainComponent ps2pdf;

      ps2pdf = null;
      desc = _GhostScript._getDescription();
      if (desc != null) {
        ps2pdf = desc._getComponent();
        if ((ps2pdf != null) && (!(ps2pdf._canUse()))) {
          ps2pdf = null;
        }
      }

      if (ps2pdf == null) {
        desc = _Ps2Pdf._getDescription();
        if (desc != null) {
          ps2pdf = desc._getComponent();
          if ((ps2pdf != null) && (!(ps2pdf._canUse()))) {
            ps2pdf = null;
          }
        }
      }
      CHAIN = new _LaTeXToolChainComponent[] { ps2pdf };
    }
  }

}
