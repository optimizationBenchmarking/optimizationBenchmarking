package examples.org.optimizationBenchmarking.utils.document;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Configuration;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10ConfigurationBuilder;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;

import examples.org.optimizationBenchmarking.utils.graphics.ExampleFontPalettes;
import examples.org.optimizationBenchmarking.utils.graphics.ExampleGraphicConfigurations;
import examples.org.optimizationBenchmarking.utils.graphics.chart.ExampleChartDrivers;

/** A set of example document configurations */
public final class ExampleDocumentConfigurations {

  /** the shared configurations */
  public static final ArrayListView<DocumentConfiguration> ALL_CONFIGURATIONS = ExampleDocumentConfigurations
      .__makeAll();

  /** the minimum level */
  private static final int LEVEL_MIN = 0;

  /** the document driver mode */
  private static final int LEVEL_DOCUMENT_DRIVER = ExampleDocumentConfigurations.LEVEL_MIN;
  /** the graphic driver mode */
  private static final int LEVEL_GRAPHIC_DRIVER = (ExampleDocumentConfigurations.LEVEL_DOCUMENT_DRIVER + 1);
  /** the chart driver mode */
  private static final int LEVEL_CHART_DRIVER = (ExampleDocumentConfigurations.LEVEL_GRAPHIC_DRIVER + 1);
  /** the fonts mode */
  private static final int LEVEL_FONTS = (ExampleDocumentConfigurations.LEVEL_CHART_DRIVER + 1);
  /** the color model */
  private static final int LEVEL_COLOR_MODEL = (ExampleDocumentConfigurations.LEVEL_FONTS + 1);
  /** the dpi level */
  private static final int LEVEL_DPI = (ExampleDocumentConfigurations.LEVEL_COLOR_MODEL + 1);
  /** the page size mode */
  private static final int LEVEL_PAGE_SIZE = (ExampleDocumentConfigurations.LEVEL_DPI + 1);
  /** the quality mode */
  private static final int LEVEL_QUALITY = (ExampleDocumentConfigurations.LEVEL_PAGE_SIZE + 1);
  /** the color palette mode */
  private static final int LEVEL_COLOR_PALETTE = (ExampleDocumentConfigurations.LEVEL_QUALITY + 1);

  /** the maximum level */
  private static final int LEVEL_MAX = (ExampleDocumentConfigurations.LEVEL_QUALITY);

  /** a subset of {@link #ALL_CONFIGURATIONS} which is rather diverse */
  public static final ArrayListView<DocumentConfiguration> FEW_DIVERSE_CONFIGURATIONS = ExampleDocumentConfigurations
      .__makeUnique(ExampleDocumentConfigurations.ALL_CONFIGURATIONS,
          ExampleDocumentConfigurations.LEVEL_CHART_DRIVER);

  /**
   * make the configurations
   * 
   * @return the list view
   */
  private static final ArrayListView<DocumentConfiguration> __makeAll() {
    final LinkedHashSet<DocumentConfiguration> all;

    all = new LinkedHashSet<>();

    ExampleDocumentConfigurations.__addXHTML10(all);

    return ExampleDocumentConfigurations.__makeUnique(all,
        (ExampleDocumentConfigurations.LEVEL_MIN - 1));
  }

  /**
   * Get the key for the given level
   * 
   * @param level
   *          the level
   * @param config
   *          the configuration
   * @return the key
   */
  private static final Object __getLevelKey(final int level,
      final DocumentConfiguration config) {
    IGraphicDriver graph;
    EGraphicFormat format;
    double d;
    int i;

    switch (level) {
      case LEVEL_DOCUMENT_DRIVER: {
        return config.getDocumentDriver();
      }
      case LEVEL_GRAPHIC_DRIVER: {
        return config.getGraphicDriver();
      }
      case LEVEL_CHART_DRIVER: {
        return config.getChartDriver();
      }
      case LEVEL_QUALITY: {
        graph = config.getGraphicDriver();
        if (graph != null) {
          format = graph.getFileType();
          if (format != null) {
            if (format.isVectorFormat()) {
              return null;
            }
          }
        }
        d = config.getQuality();
        if ((d >= 0d) && (d <= 1d)) {
          return Double.valueOf(d);
        }
        return null;
      }
      case LEVEL_DPI: {
        graph = config.getGraphicDriver();
        if (graph != null) {
          format = graph.getFileType();
          if (format != null) {
            if (format.isVectorFormat()) {
              return null;
            }
          }
        }
        i = config.getDotsPerInch();
        if ((i > 0) && (i < Integer.MAX_VALUE)) {
          return Integer.valueOf(i);
        }
        return null;
      }
      case LEVEL_COLOR_MODEL: {
        return config.getColorModel();
      }
      case LEVEL_COLOR_PALETTE: {
        graph = config.getGraphicDriver();
        if (graph != null) {
          return graph.getColorPalette();
        }
        return null;
      }
      case LEVEL_FONTS: {
        if (config instanceof XHTML10Configuration) {
          return ((XHTML10Configuration) config).getFontPalette();
        }
        return null;
      }
      case LEVEL_PAGE_SIZE: {
        if (config instanceof XHTML10Configuration) {
          return ((XHTML10Configuration) config).getScreenSize();
        }
        return null;
      }

      default: {
        return null;
      }
    }
  }

  /**
   * add the xhtml drivers
   * 
   * @param add
   *          the collection to add to
   */
  private static final void __addXHTML10(
      final Collection<DocumentConfiguration> add) {
    XHTML10ConfigurationBuilder xhtml;
    int i;

    xhtml = new XHTML10ConfigurationBuilder();
    add.add(xhtml.immutable());

    i = 0;
    outer: for (final GraphicConfiguration graphicConfig : ExampleGraphicConfigurations.CONFIGURATIONS) {
      xhtml = new XHTML10ConfigurationBuilder();
      try {
        xhtml.assign(graphicConfig);
      } catch (final IllegalArgumentException notSupported) {
        continue outer;
      }
      add.add(xhtml.immutable());

      for (final IChartDriver chartDriver : ExampleChartDrivers.DRIVERS) {
        xhtml.setChartDriver(chartDriver);
        add.add(xhtml.immutable());

        for (final FontPalette fonts : ExampleFontPalettes.PALETTES) {
          xhtml.setFontPalette(fonts);
          add.add(xhtml.immutable());

          xhtml.setScreenSize(EScreenSize.INSTANCES.get(i));
          i = ((i + 1) % EScreenSize.INSTANCES.size());
          add.add(xhtml.immutable());
        }
      }
    }
  }

  /**
   * Create a collection of unique document configurations
   * 
   * @param allConfigs
   *          the list of all configurations
   * @param uniqueLevel
   *          the minimum number of unique configurations
   * @return the document configurations
   */
  private static final ArrayListView<DocumentConfiguration> __makeUnique(
      final Collection<DocumentConfiguration> allConfigs,
      final int uniqueLevel) {
    final LinkedHashSet<DocumentConfiguration> configs;
    final HashSet<Object> all;
    final Object[] allKeys;
    int mode, level;
    Object key;

    configs = new LinkedHashSet<>();
    all = new HashSet<>();
    allKeys = new Object[(ExampleDocumentConfigurations.LEVEL_MAX - ExampleDocumentConfigurations.LEVEL_MIN) + 1];

    for (mode = ExampleDocumentConfigurations.LEVEL_MAX; mode >= uniqueLevel; mode--) {
      checkSingleConfig: for (final DocumentConfiguration config : allConfigs) {

        for (level = ExampleDocumentConfigurations.LEVEL_MAX; level >= ExampleDocumentConfigurations.LEVEL_MIN; level--) {
          allKeys[level - ExampleDocumentConfigurations.LEVEL_MIN] = ExampleDocumentConfigurations
              .__getLevelKey(level, config);
        }

        if (uniqueLevel >= ExampleDocumentConfigurations.LEVEL_MIN) {
          uniqueCheck: {
            for (level = uniqueLevel; level >= ExampleDocumentConfigurations.LEVEL_MIN; level--) {
              key = allKeys[level
                  - ExampleDocumentConfigurations.LEVEL_MIN];
              if (key != null) {
                if (!(all.contains(key))) {
                  break uniqueCheck;
                }
              }
            }
            continue checkSingleConfig;
          }
        }

        for (level = mode; level > uniqueLevel; level--) {
          key = allKeys[level - ExampleDocumentConfigurations.LEVEL_MIN];
          if (key != null) {
            if (all.contains(key)) {
              continue checkSingleConfig;
            }
          }
        }

        if (configs.add(config)) {
          for (final Object o : allKeys) {
            if (o != null) {
              all.add(o);
            }
          }
        }
      }
    }

    return ArrayListView.collectionToView(configs, false);
  }

  /** the forbidden constructor */
  private ExampleDocumentConfigurations() {
    ErrorUtils.doNotCall();
  }
}
