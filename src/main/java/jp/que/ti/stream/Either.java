package jp.que.ti.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Represents a value of one of two possible types (a disjoint union.) Instances
 * of Either are either an instance of {@link Left} or {@link Right}
 *
 * @param <LEFT>
 * @param <RIGHT>
 */
public abstract class Either<LEFT, RIGHT> implements Stream<RIGHT> {

	/**
	 * Constructs a {@link Left}
	 *
	 * @param value
	 * @return {@link Left}
	 */
	public static <LEFT, RIGHT> Left<LEFT, RIGHT> left(LEFT value) {
		return Left.of(value);
	}

	/**
	 * Constructs a {@link Right}
	 *
	 * @param value
	 * @return {@link Right}
	 */
	public static <LEFT, RIGHT> Right<LEFT, RIGHT> right(RIGHT value) {
		return Right.of(value);
	}

	/**
	 * The Left version of an Either.
	 *
	 * @author yanagawa.h
	 *
	 * @param <LEFT>
	 * @param <RIGHT>
	 */
	public static class Left<LEFT, RIGHT> extends Either<LEFT, RIGHT> {
		private Left(LEFT value) {
			super(Optional.of(value), Optional.empty());
		}

		/**
		 * Constructs a {@link Left}
		 *
		 * @param value
		 * @return {@link Left}
		 */
		public static <LEFT, RIGHT> Left<LEFT, RIGHT> of(LEFT value) {
			if (value == null) {
				throw new NullPointerException("parameter value is null !! ");
			}
			return new Left<>(value);
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeft() {
			return true;
		}

	}

	/**
	 * The Right version of an Either.
	 *
	 * @author yanagawa.h
	 *
	 * @param <LEFT>
	 * @param <RIGHT>
	 */
	public static class Right<LEFT, RIGHT> extends Either<LEFT, RIGHT> {
		private Right(RIGHT value) {
			super(Optional.empty(), Optional.of(value));
		}

		/**
		 * Constructs a {@link Right}
		 *
		 * @param value
		 * @return {@link Right}
		 */
		public static <LEFT, RIGHT> Right<LEFT, RIGHT> of(RIGHT value) {
			if (value == null) {
				throw new NullPointerException("parameter value is null !! ");
			}
			return new Right<>(value);
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeft() {
			return false;
		}

	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/**
	 * @return true If the both values are same . false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Either other = (Either) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	private final Optional<LEFT> left;
	private final Optional<RIGHT> right;

	/**
	 * Gets the left value if this is a Left.
	 *
	 * @return left value.
	 */
	public Optional<LEFT> getLeft() {
		return left;
	}

	/**
	 * Gets the right value if this is a Right.
	 *
	 * @return right value
	 */
	public Optional<RIGHT> getRight() {
		return right;
	}

	/**
	 * @return true if this is a Left, false otherwise.
	 */
	public abstract boolean isLeft();

	/**
	 * @return true if this is a Right, false otherwise.
	 */
	public boolean isRight() {
		return !isLeft();
	}

	private Either(Optional<LEFT> left, Optional<RIGHT> right) {
		this.left = left;
		this.right = right;
	}

	private Stream<RIGHT> stream() {
		if (right.isPresent()) {
			return Stream.of(right.get());
		}
		return Stream.empty();
	}

	/** {@inheritDoc} **/
	@Override
	public boolean allMatch(Predicate<? super RIGHT> predicate) {
		return stream().allMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean anyMatch(Predicate<? super RIGHT> predicate) {
		return stream().anyMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public void close() {
		stream().close();
	}

	/** {@inheritDoc} **/
	@Override
	public <R, A> R collect(Collector<? super RIGHT, A, R> collector) {
		return stream().collect(collector);
	}

	/** {@inheritDoc} **/
	@Override
	public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super RIGHT> accumulator, BiConsumer<R, R> combiner) {
		return stream().collect(supplier, accumulator, combiner);
	}

	/** {@inheritDoc} **/
	@Override
	public long count() {
		return stream().count();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> distinct() {
		return stream().distinct();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> filter(Predicate<? super RIGHT> predicate) {
		return stream().filter(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<RIGHT> findAny() {
		return stream().findAny();
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<RIGHT> findFirst() {
		return stream().findFirst();
	}

	/** {@inheritDoc} **/
	@Override
	public <R> Stream<R> flatMap(Function<? super RIGHT, ? extends Stream<? extends R>> mapper) {
		return stream().flatMap(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public DoubleStream flatMapToDouble(Function<? super RIGHT, ? extends DoubleStream> mapper) {
		return stream().flatMapToDouble(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public IntStream flatMapToInt(Function<? super RIGHT, ? extends IntStream> mapper) {
		return stream().flatMapToInt(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public LongStream flatMapToLong(Function<? super RIGHT, ? extends LongStream> mapper) {
		return stream().flatMapToLong(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public void forEach(Consumer<? super RIGHT> action) {
		stream().forEach(action);
	}

	/** {@inheritDoc} **/
	@Override
	public void forEachOrdered(Consumer<? super RIGHT> action) {
		stream().forEachOrdered(action);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean isParallel() {
		return stream().isParallel();
	}

	/** {@inheritDoc} **/
	@Override
	public Iterator<RIGHT> iterator() {
		return stream().iterator();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> limit(long maxSize) {
		return stream().limit(maxSize);
	}

	/** {@inheritDoc} **/
	@Override
	public <R> Stream<R> map(Function<? super RIGHT, ? extends R> mapper) {
		return stream().map(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public DoubleStream mapToDouble(ToDoubleFunction<? super RIGHT> mapper) {
		return stream().mapToDouble(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public IntStream mapToInt(ToIntFunction<? super RIGHT> mapper) {
		return stream().mapToInt(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public LongStream mapToLong(ToLongFunction<? super RIGHT> mapper) {
		return stream().mapToLong(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<RIGHT> max(Comparator<? super RIGHT> comparator) {
		return stream().max(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<RIGHT> min(Comparator<? super RIGHT> comparator) {
		return stream().min(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean noneMatch(Predicate<? super RIGHT> predicate) {
		return stream().noneMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> onClose(Runnable closeHandler) {
		return stream().onClose(closeHandler);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> parallel() {
		return stream().parallel();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> peek(Consumer<? super RIGHT> action) {
		return stream().peek(action);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<RIGHT> reduce(BinaryOperator<RIGHT> accumulator) {
		return stream().reduce(accumulator);
	}

	/** {@inheritDoc} **/
	@Override
	public RIGHT reduce(RIGHT identity, BinaryOperator<RIGHT> accumulator) {
		return stream().reduce(identity, accumulator);
	}

	/** {@inheritDoc} **/
	@Override
	public <U> U reduce(U identity, BiFunction<U, ? super RIGHT, U> accumulator, BinaryOperator<U> combiner) {
		return stream().reduce(identity, accumulator, combiner);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> sequential() {
		return stream().sequential();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> skip(long n) {
		return stream().skip(n);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> sorted() {
		return stream().sorted();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> sorted(Comparator<? super RIGHT> comparator) {
		return stream().sorted(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public Spliterator<RIGHT> spliterator() {
		return stream().spliterator();
	}

	/** {@inheritDoc} **/
	@Override
	public Object[] toArray() {
		return stream().toArray();
	}

	/** {@inheritDoc} **/
	@Override
	public <A> A[] toArray(IntFunction<A[]> generator) {
		return stream().toArray(generator);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> unordered() {
		return stream().unordered();
	}

}
