/**
 * The hierarchical document output API. In package
 * {@link org.optimizationBenchmarking.utils.document.spec} we define an
 * API based on our
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM
 * hierarchical API idea}. Almost everything is defined as interfaces, so
 * that it is possible to decide whether to base implementations on the
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalText
 * hierarchical text} classes, which support parallelized serial text
 * output, whether to implement it in a DOM-like fashion, or whether to do
 * it completely differently. Either way, the goal is to provide a
 * versatile way to write documents in different formats, ranging from
 * XHTML to LaTeX.
 */
package org.optimizationBenchmarking.utils.document;