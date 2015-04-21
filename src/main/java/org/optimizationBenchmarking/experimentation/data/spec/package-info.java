/**
 * <p>
 * The specification of the Experiment Data API in form of clean
 * interfaces. We distinguish the following elements of the API:
 * </p>
 * <ol>
 * <li>If you benchmark an algorithm, you will apply it to at least one
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance
 * Benchmark Instance}. An instance is one concrete problem you apply the
 * algorithm to.</li>
 * <li>Usually, you not just use one benchmark instance, but a whole
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet
 * set} of them, in order to test the algorithm in different scenarios.</li>
 * <li>The instances in this instance set therefore are <em>different</em>.
 * They can be distinguished based on
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeature
 * features}.</li>
 * <li>An instance for the Traveling Salesman Problem (TSP), for instance,
 * may have the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue
 * value} {@code 10} for the feature {@code scale} (which represents the
 * number of cities) and {@code true} for the feature {@code symmetric}
 * (meaning the travel distance from city {@code A} to city {@code B} is
 * the same).</li>
 * <li>Thus, there also is a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet
 * set} of features, and each benchmark instance has one value for each
 * feature. This is how we can distinguish them.</li>
 * <li>We now want to conduct an
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment
 * experiment}, i.e., apply one optimization algorithm to the set of all
 * benchmark instances.</li>
 * <li>In other words, for each benchmark instance, we conduct a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns
 * set} of...</li>
 * <li>...independent
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IRun runs}
 * , where each run is an independent application of our algorithm to the
 * specific benchmark instance.</li>
 * <li>A run then, in turn, is an ordered sequence of
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDataPoint
 * data point}. Each data point represents one concrete point in time
 * during the run and results that the algorithm has achieved until then.</li>
 * <li>Let's shed some more light on that: Before we do an experiment, we
 * must define the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimension
 * dimensions} we want to measure, i.e., the "columns" of our log files.
 * Let's say I want to count the number of function evaluations (FEs) and
 * whenever the algorithm finds a better solution, I store the tuple of
 * (FE, solution quality) in the log file.</li>
 * <li>This means that the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet
 * set of measurement dimensions} contains two dimensions: FEs and quality.
 * Each data point thus has two values, one value for the FE and one for
 * the solution quality. A run is an ordered list of these values.</li>
 * <li>In order to compare different algorithms, I can conduct a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet
 * set of experiments}, again each having a set of runs for each instance.</li>
 * <li>Now I must distinguish these experiments, and I can do that based on
 * their
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameter
 * parameters}.</li>
 * <li>The <em>algorithm</em> I apply may be a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterValue
 * value} of one parameter, but I may also apply the same algorithm several
 * different times. Let's say I have an Evolutionary Algorithm and I test
 * the population sizes (parameter) {@code 10}, {@code 100}, and
 * {@code 1000} (parameter values) as well as the crossover rates
 * (parameter) {@code 0.3} and {@code 0.6} (parameter value).</li>
 * <li>Thus, there also is a
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterSet
 * set} of parameters, and each experiment may or may not specify a value
 * for each parameter: Simulated Annealing would have a value for the
 * parameter "algorithm", but not for "population size".</li>
 * </ol>
 * <p>
 * And these are the main components of the Experiment Data API. The main
 * entry point to the API is the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet
 * experiment set} interface, from which you can access all the other
 * components.
 * </p>
 * <p>
 * Of course, there are more elements to this API, but for starters, that's
 * the idea.
 * </p>
 * <p>
 * It should be noted that different
 * {@link org.optimizationBenchmarking.experimentation.data.impl
 * implementations} of this API do not need to be "compatible" and may even
 * throw exceptions if fed with classes of each other. This behavior stems
 * from the fact that we need high performance and data fidelity. The API
 * is instead defined as interfaces with the aim of allowing
 * {@link org.optimizationBenchmarking.experimentation.evaluation
 * evaluation} components to deal with data of different types and sources.
 * </p>
 */
package org.optimizationBenchmarking.experimentation.data.spec;