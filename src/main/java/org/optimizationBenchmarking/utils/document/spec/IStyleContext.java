package org.optimizationBenchmarking.utils.document.spec;

/**
 * A context in which different styles can be allocated.
 */
public interface IStyleContext {

  /**
   * <p>
   * Allocate a new style: The underlying implementation of this interface
   * will create a new, visually different style text each time this method
   * is invoked or throw a {@link IllegalStateException} if it is not
   * possible to create another visually different style.
   * </p>
   * <p>
   * Styles are only guaranteed to work or to be different within the scope
   * of the {@link IStyleContext} in which they have been created.
   * </p>
   * 
   * @return the style
   */
  public abstract IStyle createTextStyle();

  /**
   * <p>
   * Allocate a new style: The underlying implementation of this interface
   * will create a new, visually different style graphical each time this
   * method is invoked or throw a {@link IllegalStateException} if it is
   * not possible to create another visually different style.
   * </p>
   * <p>
   * Styles are only guaranteed to work or to be different within the scope
   * of the {@link IStyleContext} in which they have been created.
   * </p>
   * 
   * @return the style
   */
  public abstract IStyle createGraphicalStyle();

  /**
   * The default style for emphasized text
   * 
   * @return default style for emphasized text
   */
  public abstract IStyle emphasized();

  /**
   * The plain text style
   * 
   * @return the plain text style
   */
  public abstract IStyle plain();

}
