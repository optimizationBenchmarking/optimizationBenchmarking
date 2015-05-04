package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An interface common to elements that can be sequenced. This interface is
 * intended for flat objects which can be written in a text.
 */
public interface ISequenceable {

  /**
   * Write this object's content to a sequence.
   *
   * @param isFirstInSequence
   *          is the object the first in the sequence?
   * @param isLastInSequence
   *          is the object the first in the last sequence?
   * @param textCase
   *          which text case should apply?
   * @param textOut
   *          the text output destination to write to
   */
  public abstract void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut);

}
