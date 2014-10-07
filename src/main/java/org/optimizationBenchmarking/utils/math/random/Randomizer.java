package org.optimizationBenchmarking.utils.math.random;

import java.util.Random;

/**
 * This unsynchronized random number generator overrides the class
 * {@link java.util.Random}. This class has three major purposes:</p>
 * <ol>
 * <li>First, implements the exactly same behavior (as off Java 1.7),
 * which, in turn, is based on well-known algorithms&nbsp;[<a
 * href="#cite_K1969TAOCPSNA" style="font-weight:bold">1</a>, <a
 * href="#cite_BM1958RAN" style="font-weight:bold">2</a>]. The reason of
 * implementing the same behavior already provided by Java is that we
 * cannot be sure about future changes, i.e., whether some of the backing
 * algorithms of the random number generation are changed. This may result
 * in different behavior of software relying on them - in particular when
 * we want to repeat experiments with exactly the same random number
 * sequence. Therefore, by fixing the algorithms here, we can avoid such
 * issues (at the cost that updates/improvements by the JVM are not used by
 * us).</li>
 * <li>Second, it removes the synchronization. We do not need
 * synchronization, as we use instances of the randomizer only within a
 * single thread. Removing the synchronization may thus result in a much
 * faster computation - at least in cases where the JIT cannot or does not
 * remove the synchronization automatically. By furthermore making the
 * class {@code final} we aim to encourage the JIT to inline as much of its
 * code as possible.</li>
 * <li>Finally, we add additional routines for generating random number
 * (actually, we do that already in
 * {@link org.optimizationBenchmarking.utils.math.random.BasicRandom}).</li>
 * </ol>
 * <h2>References</h2>
 * <ol>
 * <li><div><span id="cite_K1969TAOCPSNA" />Donald Ervin Knuth: <span
 * style="font-style:italic;font-family:cursive;">&ldquo;Seminumerical
 * Algorithms,&rdquo;</span> 1969, volume 2 of The Art of Computer
 * Programming (TAOCP), Boston, MA, USA: Addison-Wesley Longman Publishing
 * Co., Inc.. ISBN:&nbsp;<a
 * href="https://www.worldcat.org/isbn/0201896842">0-201-89684-2</a>, <a
 * href="https://www.worldcat.org/isbn/8177583352">8177583352</a>, <a
 * href="https://www.worldcat.org/isbn/9780201896848"
 * >978-0-201-89684-8</a>, and&nbsp;<a
 * href="https://www.worldcat.org/isbn/9788177583359">978-8177583359</a>;
 * OCLC:&nbsp;<a href="https://www.worldcat.org/oclc/85975465">85975465</a>
 * and&nbsp;<a href="https://www.worldcat.org/oclc/38207978">38207978</a>;
 * Google Book ID:&nbsp;<a
 * href="http://books.google.com/books?id=OtLNKNVh1XoC">OtLNKNVh1XoC</a>;
 * ASIN:&nbsp;<a
 * href="http://www.amazon.com/dp/0201896842">0201896842</a></div></li>
 * <li><div><span id="cite_BM1958RAN" />George Edward Pelham Box
 * and&nbsp;Mervin Edgar Muller: <span style="font-weight:bold">&ldquo;A
 * Note on the Generation of Random Normal Deviates,&rdquo;</span> in <span
 * style="font-style:italic;font-family:cursive;">The Annals of
 * Mathematical Statistics</span> 29(2):610&ndash;611, June&nbsp;1958;
 * published by Beachwood, OH, USA: Institute of Mathematical Statistics.
 * LCCN:&nbsp;<a href="http://lccn.loc.gov/sn98-23312">sn98-23312</a>;
 * doi:&nbsp;<a href="http://dx.doi.org/10.1214/aoms/1177706645"
 * >10.1214/aoms/1177706645</a>; JSTOR stable:&nbsp;<a
 * href="http://www.jstor.org/stable/2237361">2237361</a>; OCLC:&nbsp;<a
 * href="https://www.worldcat.org/oclc/40280837">40280837</a>;
 * ISSN:&nbsp;<a
 * href="https://www.worldcat.org/issn/00034851">0003-4851</a>. <div>link:
 * [<a
 * href="http://projecteuclid.org/euclid.aoms/1177706645">1</a>]</div></
 * div></li>
 * </ol>
 */
public final class Randomizer extends BasicRandom {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the MULTIPLIER */
  private static final long MULTIPLIER = 0x5DEECE66DL;
  /** the ADDEND */
  private static final long ADDEND = 0xBL;
  /** the MASK */
  private static final long MASK = ((1L << 48L) - 1L);

  /** the random number generator for getting seeds */
  private static final Random SEEDS = new Random();

  /** the seed @serial serializable field */
  private long m_seed;

  /**
   * the next Gaussian random number, if available @serial serializable
   * field
   */
  private double m_nextNextGaussian;

  /**
   * do we have such a Gaussian random number ready? @serial serializable
   * field
   */
  private boolean m_haveNextNextGaussian;

  /**
   * create!
   * 
   * @param seed
   *          the seed to use
   * @param dummy
   *          ignored
   */
  private Randomizer(final long seed, final boolean dummy) {
    super(seed);
    this.m_seed = seed;
  }

  /**
   * create!
   * 
   * @param seed
   *          the seed to use
   */
  public Randomizer(final long seed) {
    this(Randomizer._initialScramble(seed), false);
  }

  /** create! */
  public Randomizer() {
    this(Randomizer.SEEDS.nextLong(), true);
    // Use as seed a random number generated by the original random
    // implementation.
    // This is ok, since the initialization should be random and thus
    // unpredictable and it does not matter if this behavior changes from
    // version to version due to changes in the implementation of the class
    // Random.
  }

  /**
   * scramble the initial seed
   * 
   * @param seed
   *          the seed
   * @return the scrambled seed
   */
  private static long _initialScramble(final long seed) {
    return ((seed ^ Randomizer.MULTIPLIER) & Randomizer.MASK);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("sync-override")
  public final void setSeed(final long seed) {
    this.m_seed = (Randomizer._initialScramble(seed));
    this.m_haveNextNextGaussian = false;
  }

  /** {@inheritDoc} */
  @Override
  protected int next(final int bits) {
    return (int) ((this.m_seed = (((this.m_seed * Randomizer.MULTIPLIER) + Randomizer.ADDEND) & Randomizer.MASK)) >>> (48 - bits));
  }

  /** {@inheritDoc} */
  @Override
  public final int nextInt(final int n) {
    int bits, val;

    if (n <= 0) {
      super.nextInt(-1);// throw the original exception
    }

    if ((n & (-n)) == n) {
      return (int) ((n * ((long) (this.next(31)))) >> 31);
    }

    do {
      bits = this.next(31);
      val = (bits % n);
    } while (((bits - val) + (n - 1)) < 0);
    return val;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("sync-override")
  @Override
  public final double nextGaussian() {
    double v1, v2, s;
    final double ymultiplier;

    if (this.m_haveNextNextGaussian) {
      this.m_haveNextNextGaussian = false;
      return this.m_nextNextGaussian;
    }

    do {
      v1 = ((2d * this.nextDouble()) - 1d);
      v2 = ((2d * this.nextDouble()) - 1d);
      s = ((v1 * v1) + (v2 * v2));
    } while ((s >= 1d) || (s <= 0d));

    ymultiplier = StrictMath.sqrt((-2d * StrictMath.log(s)) / s);
    this.m_nextNextGaussian = (v2 * ymultiplier);
    this.m_haveNextNextGaussian = true;
    return (v1 * ymultiplier);
  }
}