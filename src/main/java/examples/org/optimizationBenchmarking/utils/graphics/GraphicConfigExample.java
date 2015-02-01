package examples.org.optimizationBenchmarking.utils.graphics;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGZGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOBMPGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOPNGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.hash.HashObject;

/** a graphic configuration example */
public final class GraphicConfigExample extends HashObject {

  /** the shared instances */
  public static final ArrayListView<GraphicConfiguration> CONFIGURATIONS;

  /** the shared drivers */
  public static final ArrayListView<IGraphicDriver> DRIVERS;

  static {
    final LinkedHashSet<GraphicConfiguration> examples;
    final LinkedHashSet<IGraphicDriver> drivers;
    GraphicConfigurationBuilder builder;

    examples = new LinkedHashSet<>();
    drivers = new LinkedHashSet<>();

    IGraphicDriver d;
    d = FreeHEPEPSGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = FreeHEPPDFGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = FreeHEPSVGZGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = FreeHEPSVGGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = FreeHEPEMFGraphicDriver.getInstance();
    if (d.canUse()) {
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = ImageIOPNGGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());

      builder.setDotsPerInch(256);

      builder.setColorModel(EColorModel.ARGB_32_BIT);
      examples.add(builder.immutable());
      builder.setColorModel(EColorModel.RGB_24_BIT);
      examples.add(builder.immutable());
      builder.setColorModel(EColorModel.RGB_16_BIT);
      examples.add(builder.immutable());
      builder.setColorModel(EColorModel.RGB_15_BIT);
      examples.add(builder.immutable());
      builder.setColorModel(EColorModel.GRAY_16_BIT);
      examples.add(builder.immutable());
      builder.setColorModel(EColorModel.GRAY_8_BIT);
      examples.add(builder.immutable());
    }

    d = ImageIOJPEGGraphicDriver.getInstance();
    if (d.canUse()) {
      drivers.add(d);
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());

      builder.setDotsPerInch(256);
      builder.setColorModel(EColorModel.RGB_24_BIT);
      builder.setQuality(0d);
      examples.add(builder.immutable());
      builder.setQuality(1d);
      examples.add(builder.immutable());

      builder.setDotsPerInch(333);
      builder.setColorModel(EColorModel.RGB_24_BIT);
      builder.setQuality(0d);
      examples.add(builder.immutable());
      builder.setQuality(1d);
      examples.add(builder.immutable());

      builder.setDotsPerInch(256);
      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setQuality(0d);
      examples.add(builder.immutable());
      builder.setQuality(1d);
      examples.add(builder.immutable());

      builder.setDotsPerInch(333);
      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setQuality(0d);
      examples.add(builder.immutable());
      builder.setQuality(1d);
      examples.add(builder.immutable());
    }

    d = ImageIOGIFGraphicDriver.getInstance();
    if (d.canUse()) {
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.ARGB_32_BIT);
      builder.setDotsPerInch(600);
      examples.add(builder.immutable());
    }

    d = EGraphicFormat.NULL.getDefaultDriver();
    if (d.canUse()) {
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());
    }

    d = ImageIOBMPGraphicDriver.getInstance();
    if (d.canUse()) {
      builder = new GraphicConfigurationBuilder();
      builder.setGraphicDriver(d);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(2048);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(1024);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(512);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(256);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(128);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(64);
      examples.add(builder.immutable());

      builder.setColorModel(EColorModel.RGB_15_BIT);
      builder.setDotsPerInch(32);
      examples.add(builder.immutable());
    }

    for (final EGraphicFormat format : EGraphicFormat.INSTANCES) {
      d = format.getDefaultDriver();
      if (d.canUse()) {
        builder = new GraphicConfigurationBuilder();
        builder.setGraphicDriver(d);
        examples.add(builder.immutable());
      }
    }

    CONFIGURATIONS = ArrayListView.collectionToView(examples, false);
    DRIVERS = ArrayListView.collectionToView(drivers, false);
  }
}
