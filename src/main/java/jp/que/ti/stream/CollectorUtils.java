package jp.que.ti.stream;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * {@link Collector}Utilities.
 *
 * @author yangiYa
 */
public class CollectorUtils {

	private CollectorUtils() {
	}

	/**
	 * Applies a binary operator to a start value and all elements of this
	 * stream.
	 *
	 * @param stream
	 *            {@link Stream} object.
	 * @param accumulatorSupplier
	 *            {@link Supplier] of accumulator.
	 * @param op
	 *            the binary operator
	 * @param combiner
	 *            A function that accepts two partial results and merges them.
	 *            In not parallel,this can be {@link Optional#empty()}.
	 * @return the result of inserting op between consecutive elements of this
	 *         consecutive.
	 */
	public static <T, ACCUM> ACCUM foldLeft(Stream<T> stream, Supplier<ACCUM> accumulatorSupplier,
			BiConsumer<ACCUM, T> op, Optional<BinaryOperator<ACCUM>> combiner) {

		if (stream == null)
			throw new NullPointerException("stream is null.");

		if (stream.isParallel() && combiner.isPresent()) {
			if (combiner.isPresent()) {
				return stream.collect(CollectorsEx.foldLeft(accumulatorSupplier, op, combiner));
			} else {
				final Stream<T> streamSeq = stream.sequential();
				return streamSeq.collect(CollectorsEx.foldLeft(accumulatorSupplier, op, combiner));
			}
		} else {
			return stream.collect(CollectorsEx.foldLeft(accumulatorSupplier, op, combiner));
		}
	}

}
