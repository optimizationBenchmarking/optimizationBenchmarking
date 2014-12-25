/**
 * The data structures and builder API for experimental data.
 * <p>
 * This package employs a set of core techniques:
 * </p>
 * <ol>
 * <li>Builder classes based on our
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM
 * hierarchical API}, which allow us to construct and populate complex data
 * objects in a simple way. In particular, the API can nicely be plugged
 * into {@link org.optimizationBenchmarking.experimentation.evaluation.io
 * I/O classes} . It also tries to create data structures which are as
 * compact as possible, by detecting redundant features.</li>
 * <li>On-the-fly compiling: Necessary data structures are compiled on the
 * fly, based on the provided descriptions. This allows for precise loading
 * (e.g., using {@code long}s where {@code longs} are needed,
 * {@code double}s where {@code double}s are needed, and maybe even
 * {@code byte}s where {@code byte}s are sufficient} for storing
 * information).</li>
 * </ol>
 */
package org.optimizationBenchmarking.experimentation.evaluation.data;