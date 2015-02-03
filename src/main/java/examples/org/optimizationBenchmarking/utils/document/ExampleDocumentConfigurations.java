package examples.org.optimizationBenchmarking.utils.document;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10ConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;

import examples.org.optimizationBenchmarking.utils.graphics.ExampleFontPalettes;
import examples.org.optimizationBenchmarking.utils.graphics.ExampleGraphicConfigurations;
import examples.org.optimizationBenchmarking.utils.graphics.chart.ExampleChartDrivers;

/** A set of example document configurations */
public final class ExampleDocumentConfigurations {

  /** the shared configurations */
  public static final ArrayListView<DocumentConfiguration> CONFIGURATIONS = ExampleDocumentConfigurations
      .__make();

  /**
   * make the configurations
   * 
   * @return the list view
   */
  private static final ArrayListView<DocumentConfiguration> __make() {
    final LinkedHashSet<DocumentConfiguration> all;

    all = new LinkedHashSet<>();

    ExampleDocumentConfigurations.__addXHTML10(all);

    return ArrayListView.collectionToView(all, false);
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

  /** the forbidden constructor */
  private ExampleDocumentConfigurations() {
    ErrorUtils.doNotCall();
  }
}
