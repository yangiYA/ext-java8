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

		final private LEFT left;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Left other = (Left) obj;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			return true;
		}

		private Left(LEFT value) {
			left = value;
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

		@Override
		Optional<LEFT> left() {
			return Optional.of(left);
		}

		@Override
		Optional<Supplier<RIGHT>> rightSupplier() {
			return Optional.empty();
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
		final Supplier<RIGHT> rightSupplier;

		private Right(Supplier<RIGHT> rightSupplier) {
			this.rightSupplier = rightSupplier;
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
			return new Right<>(() -> value);
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLeft() {
			return false;
		}

		@Override
		Optional<LEFT> left() {
			return Optional.empty();
		}

		@Override
		Optional<Supplier<RIGHT>> rightSupplier() {
			return Optional.of(rightSupplier);
		}

	}

	abstract Optional<LEFT> left();

	final private Optional<RIGHT> right() {
		if (rightSupplier().isPresent()) {
			return Optional.of(rightSupplier().get().get());
		} else {
			return Optional.empty();
		}
	}

	abstract Optional<Supplier<RIGHT>> rightSupplier();

	/**
	 * Gets the left value if this is a Left.
	 *
	 * @return left value.
	 */
	public Optional<LEFT> getLeft() {
		return left();
	}

	/**
	 * Gets the right value if this is a Right.
	 *
	 * @return right value
	 */
	public Optional<RIGHT> getRight() {
		return right();
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

	private Either() {
	}

	private Stream<RIGHT> stream() {
		if (right().isPresent()) {
			return Stream.of(right().get());
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
		if (isLeft()) {
			return this;
		}
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

	public <R> Either<LEFT, R> flatMapEither(Function<? super RIGHT, ? extends Either<LEFT, ? extends R>> mapper) {
		if (isLeft()) {
			@SuppressWarnings("unchecked")
			final Left<LEFT, R> lf = (Left<LEFT, R>) this;
			return lf;
		} else {

			final Either<LEFT, ? extends R> rg = mapper.apply(this.getRight().get());
			if (rg.isLeft()) {
				return (Left<LEFT, R>) rg;
			} else {
				final Supplier<? extends R> sp2 = rg.rightSupplier().get();
				final Supplier<R> spRtn = () -> sp2.get();

				return new Right<LEFT, R>(spRtn);
			}
		}
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
		if (isLeft()) {
			return this;
		}
		return stream().limit(maxSize);
	}

	private <R> Either<LEFT, R> leftOr(Function<? super RIGHT, ? extends R> mapper) {
		if (isLeft()) {
			@SuppressWarnings("unchecked")
			final Left<LEFT, R> lf = (Left<LEFT, R>) this;
			return lf;
		} else {
			final Right<LEFT, R> rg = Right.of(mapper.apply(this.getRight().get()));
			return rg;
		}
	}

	/** {@inheritDoc} **/
	@Override
	public <R> Either<LEFT, R> map(Function<? super RIGHT, ? extends R> mapper) {
		return leftOr(mapper);
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
		if (isLeft()) {
			return this;
		}
		return stream().sequential();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> skip(long n) {
		if (isLeft()) {
			return this;
		}
		return stream().skip(n);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> sorted() {
		if (isLeft()) {
			return this;
		}
		return stream().sorted();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<RIGHT> sorted(Comparator<? super RIGHT> comparator) {
		if (isLeft()) {
			return this;
		}
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
		if (isLeft()) {
			return this;
		}
		return stream().unordered();
	}

}
