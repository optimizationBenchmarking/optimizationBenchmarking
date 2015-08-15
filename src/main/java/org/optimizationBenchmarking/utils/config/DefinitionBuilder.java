package org.optimizationBenchmarking.utils.config;

import java.util.LinkedHashMap;

import org.optimizationBenchmarking.utils.collections.cache.NormalizingCache;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseByteParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseIntParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseLongParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseShortParser;
import org.optimizationBenchmarking.utils.parsers.LooseBooleanParser;
import org.optimizationBenchmarking.utils.parsers.LooseByteParser;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.LooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.LooseIntParser;
import org.optimizationBenchmarking.utils.parsers.LooseLongParser;
import org.optimizationBenchmarking.utils.parsers.LooseShortParser;
import org.optimizationBenchmarking.utils.parsers.LooseStringParser;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * A builder for a definition.
 */
public final class DefinitionBuilder extends BuilderFSM<Definition> {

  /** the internal cache */
  private static final NormalizingCache CACHE = new NormalizingCache();

  /** the parameters */
  private LinkedHashMap<String, Parameter<?>> m_params;

  /** create the definition builder */
  public DefinitionBuilder() {
    super(null);
    this.m_params = new LinkedHashMap<>();
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final <T> T doNormalizePersistently(final T input) {
    return DefinitionBuilder.CACHE.normalize(input);
  }

  /**
   * check if the provided name is allowed
   *
   * @param name
   *          the name
   */
  private final void __checkAllowed(final String name) {
    if (this.m_params.containsKey(name)) {
      throw new IllegalStateException("Parameter of name '" + //$NON-NLS-1$
          name + "' already defined."); //$NON-NLS-1$
    }
  }

  /**
   * Add a given parameter
   *
   * @param parameter
   *          the parameter to add
   */
  public synchronized final void addParameter(final Parameter<?> parameter) {
    final String name;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    name = parameter.m_name;
    this.__checkAllowed(name);

    this.m_params.put(name, this.normalize(parameter));
  }

  /**
   * Create a {@code byte} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void byteParameter(final String name,
      final String description, final byte min, final byte max,
      final byte def) {
    final Parser<Byte> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Byte.MIN_VALUE) && (max >= Byte.MAX_VALUE)) {
      p = LooseByteParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseByteParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Byte.valueOf(def))));
  }

  /**
   * Create a {@code short} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void shortParameter(final String name,
      final String description, final short min, final short max,
      final short def) {
    final Parser<Short> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Short.MIN_VALUE) && (max >= Short.MAX_VALUE)) {
      p = LooseShortParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseShortParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Short.valueOf(def))));
  }

  /**
   * Create a {@code int} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void intParameter(final String name,
      final String description, final int min, final int max, final int def) {
    final Parser<Integer> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Integer.MIN_VALUE) && (max >= Integer.MAX_VALUE)) {
      p = LooseIntParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseIntParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Integer.valueOf(def))));
  }

  /**
   * Create a {@code long} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void longParameter(final String name,
      final String description, final long min, final long max,
      final long def) {
    final Parser<Long> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Long.MIN_VALUE) && (max >= Long.MAX_VALUE)) {
      p = LooseLongParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseLongParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Long.valueOf(def))));
  }

  /**
   * Create a {@code double} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void doubleParameter(final String name,
      final String description, final double min, final double max,
      final double def) {
    final Parser<Double> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Double.NEGATIVE_INFINITY)
        && (max >= Double.POSITIVE_INFINITY)) {
      p = LooseDoubleParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseDoubleParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Double.valueOf(def))));
  }

  /**
   * Create a {@code float} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void floatParameter(final String name,
      final String description, final float min, final float max,
      final float def) {
    final Parser<Float> p;
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    if ((min <= Float.NEGATIVE_INFINITY)
        && (max >= Float.POSITIVE_INFINITY)) {
      p = LooseFloatParser.INSTANCE;
    } else {
      p = this.normalize(new BoundedLooseFloatParser(min, max));
    }

    this.addParameter(new SimpleParameter<>(useName,//
        this.normalize(description), p, //
        this.normalize(Float.valueOf(def))));
  }

  /**
   * Create a {@code String} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void stringParameter(final String name,
      final String description, final String def) {
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    this.addParameter(//
    new SimpleParameter<>(useName,//
        this.normalize(description), //
        LooseStringParser.INSTANCE,//
        this.normalize(def)));
  }

  /**
   * Create a {@code boolean} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param def
   *          the default value of the parameter
   */
  public synchronized final void booleanParameter(final String name,
      final String description, final boolean def) {
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    this.addParameter(//
    new SimpleParameter<>(useName,//
        this.normalize(description), //
        LooseBooleanParser.INSTANCE,//
        Boolean.valueOf(def)));
  }

  /**
   * Create a {@code byte} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void byteParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.byteParameter(name, description,//
        LooseByteParser.INSTANCE.parseByte(min),//
        LooseByteParser.INSTANCE.parseByte(max),//
        LooseByteParser.INSTANCE.parseByte(def));
  }

  /**
   * Create a {@code short} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void shortParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.shortParameter(name, description,//
        LooseShortParser.INSTANCE.parseShort(min),//
        LooseShortParser.INSTANCE.parseShort(max),//
        LooseShortParser.INSTANCE.parseShort(def));
  }

  /**
   * Create a {@code int} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void intParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.intParameter(name, description,//
        LooseIntParser.INSTANCE.parseInt(min),//
        LooseIntParser.INSTANCE.parseInt(max),//
        LooseIntParser.INSTANCE.parseInt(def));
  }

  /**
   * Create a {@code long} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void longParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.longParameter(name, description,//
        LooseLongParser.INSTANCE.parseLong(min),//
        LooseLongParser.INSTANCE.parseLong(max),//
        LooseLongParser.INSTANCE.parseLong(def));
  }

  /**
   * Create a {@code float} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void floatParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.floatParameter(name, description,//
        LooseFloatParser.INSTANCE.parseFloat(min),//
        LooseFloatParser.INSTANCE.parseFloat(max),//
        LooseFloatParser.INSTANCE.parseFloat(def));
  }

  /**
   * Create a {@code double} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param min
   *          the minimum of the parameter
   * @param max
   *          the maximum of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void doubleParameter(final String name,
      final String description, final String min, final String max,
      final String def) {
    this.doubleParameter(name, description,//
        LooseDoubleParser.INSTANCE.parseDouble(min),//
        LooseDoubleParser.INSTANCE.parseDouble(max),//
        LooseDoubleParser.INSTANCE.parseDouble(def));
  }

  /**
   * Create a {@code boolean} parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param def
   *          the default value of the parameter
   */
  public final void booleanParameter(final String name,
      final String description, final String def) {
    this.booleanParameter(name, description,//
        LooseBooleanParser.INSTANCE.parseBoolean(def));
  }

  /**
   * Create a builder for an instance parameter.
   *
   * @param name
   *          the name of the parameter
   * @param description
   *          the description of the parameter
   * @param parser
   *          the parser
   * @param def
   *          the default value of the parameter
   * @param allowsMore
   *          will more parameter values be allowed than just the provided
   *          choices?
   * @return the builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public synchronized final InstanceParameterBuilder instanceParameter(
      final String name, final String description, final Parser<?> parser,
      final String def, final boolean allowsMore) {
    final String useName;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    useName = this.normalize(name);
    this.__checkAllowed(useName);

    return new InstanceParameterBuilder(this, new Parameter(useName,
        this.normalize(description), this.normalize(parser),
        this.normalize(def)), allowsMore);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    if (child instanceof InstanceParameterBuilder) {
      this.addParameter(((InstanceParameterBuilder) child)._take());
    } else {
      this.throwChildNotAllowed(child);
    }
    super.afterChildClosed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected final Definition compile() {
    LinkedHashMap<String, Parameter<?>> al;

    al = this.m_params;
    this.m_params = null;

    return this.normalize(new Definition(al.values().toArray(//
        new Parameter[al.size()])));
  }

}
