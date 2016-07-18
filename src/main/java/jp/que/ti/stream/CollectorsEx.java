package jp.que.ti.stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Implementations of {@link Collector} that implement various useful reduction
 * operations.
 *
 * @author yangiYa
 */
public class CollectorsEx {

	private CollectorsEx() {
	}

	private static final Set<java.util.stream.Collector.Characteristics> emptySet = Collections
			.unmodifiableSet(new HashSet<>());

	/**
	 * {@link Collector} for foldLeft
	 *
	 * @param accumulatorSupplier
	 *            {@link Supplier] of accumulator.
	 * @param op
	 *            the binary operator
	 * @param combiner
	 *            A function that accepts two partial results and merges them.
	 *            In not parallel,this can be {@link Optional#empty()}.
	 * @return {@link Collector} for foldLeft
	 */
	public static <T, ACCUM> Collector<T, ACCUM, ACCUM> foldLeft(Supplier<ACCUM> accumulatorSupplier,
			BiConsumer<ACCUM, T> op, Optional<BinaryOperator<ACCUM>> combiner) {

		if (accumulatorSupplier == null)
			throw new NullPointerException("accumulatorSupplier is null.");
		if (op == null)
			throw new NullPointerException("function is null.");
		if (combiner == null)
			throw new NullPointerException("combiner is null.");

		final Collector<T, ACCUM, ACCUM> rtn = new Collector<T, ACCUM, ACCUM>() {

			@Override
			public Supplier<ACCUM> supplier() {
				return accumulatorSupplier;
			}

			@Override
			public BiConsumer<ACCUM, T> accumulator() {
				return op;
			}

			@Override
			public BinaryOperator<ACCUM> combiner() {
				return combiner.orElse(null);
			}

			@Override
			public Function<ACCUM, ACCUM> finisher() {
				return a -> a;
			}

			@Override
			public Set<java.util.stream.Collector.Characteristics> characteristics() {
				return emptySet;
			}
		};
		return rtn;
	}
}
