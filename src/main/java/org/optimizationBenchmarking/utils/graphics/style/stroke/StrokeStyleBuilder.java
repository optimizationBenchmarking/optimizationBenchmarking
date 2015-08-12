package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.parsers.LooseFloatParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;

/**
 * A builder for a stroke style
 */
public class StrokeStyleBuilder extends PaletteElementBuilder<StrokeStyle> {

  /** the name of the palette entry */
  private String m_name;

  /** the width */
  private float m_width;

  /** the dash */
  private float[] m_dash;

  /** the dash count */
  private int m_dashCount;

  /** the type */
  int m_type;

  /** the id */
  private String m_id;

  /**
   * create
   *
   * @param owner
   *          the owning palette builder
   * @param type
   *          the type
   * @param id
   *          the id of the style builder
   */
  protected StrokeStyleBuilder(final StrokePaletteBuilder owner,
      final int type, final String id) {
    super(owner);
    this.m_dash = EmptyUtils.EMPTY_FLOATS;
    this.m_type = type;
    this.m_id = TextUtils.prepare(id);
    this.open();
  }

  /** create! */
  public StrokeStyleBuilder() {
    this(null, 4, null);
  }

  /**
   * Set the name of the stroke
   *
   * @param name
   *          the name of the stroke
   */
  public synchronized final void setName(final String name) {
    final String s;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    s = this.normalize(name);
    if (s == null) {
      throw new IllegalArgumentException(//
          "Name must not be null or empty, but is '" //$NON-NLS-1$
              + name + '\'');
    }
    this.m_name = name;

    if (this.m_id == null) {
      this.m_id = NormalCharTransformer.getInstance().transform(
          this.m_name);
    }
  }

  /**
   * Set the width of this stroke builder
   *
   * @param width
   *          the width value
   */
  public synchronized final void setWidth(final float width) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if (width < 0f) {
      throw new IllegalArgumentException(//
          "Width cannot be less than zero, but is " + width); //$NON-NLS-1$
    }
    this.m_width = width;
  }

  /**
   * Add a dash element
   *
   * @param dash
   *          the dash element to add
   */
  public synchronized final void addDash(final float dash) {
    final int c;
    float[] z;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    if (dash < 0f) {
      throw new IllegalArgumentException(//
          "Dash element must not be negative, but " + //$NON-NLS-1$
              dash + " is."); //$NON-NLS-1$
    }

    z = this.m_dash;
    c = this.m_dashCount;
    if (c >= z.length) {
      z = new float[(c + 1) << 1];
      System.arraycopy(this.m_dash, 0, z, 0, c);
      this.m_dash = z;
    }
    z[c] = dash;
    this.m_dashCount = (c + 1);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fromStrings(final Iterator<String> strings) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    try {
      this.setName(strings.next());
      this.setWidth(LooseFloatParser.INSTANCE.parseFloat(strings.next()));
      while (strings.hasNext()) {
        this.addDash(LooseFloatParser.INSTANCE.parseFloat(strings.next()));
      }
    } catch (final Throwable t) {
      throw new IllegalArgumentException(('\'' + strings.toString())
          + "' is not a valid stroke.", t); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final StrokeStyle compile() {
    final int c;
    final float[] z;

    c = this.m_dashCount;
    if (c > 0) {
      if (c != this.m_dash.length) {
        z = new float[c];
        System.arraycopy(this.m_dash, 0, z, 0, c);
      } else {
        z = this.m_dash;
      }
    } else {
      z = null;
    }

    return new StrokeStyle(this.m_width, z, this.m_name, this.m_id);
  }
}
