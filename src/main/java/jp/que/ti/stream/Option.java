package jp.que.ti.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
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
 * A container object which may or may not contain a non-null value.
 *
 * @param <T>
 */
public abstract class Option<T> implements Stream<T> {

	@SuppressWarnings("unchecked")
	public static <T> Empty<T> empty() {
		return (Empty<T>) Empty.empty;
	}

	public static <T> Option<T> of(T value) {
		if (value == null) {
			return Empty.empty();
		}
		return new Some<T>(value);
	}

	public static <T> Some<T> some(T value) {
		final T v = Objects.requireNonNull(value);
		return new Some<T>(v);
	}

	public static class Some<T> extends Option<T> {
		private Some(T value) {
			super(value);
		}
	}

	public static class Empty<T> extends Option<T> {
		private static final Empty<?> empty = new Empty<>();

		private Empty() {
			super();
		}
	}

	private final T value;

	/**
	 * If a value is present in this {@code Optional}, returns the value,
	 * otherwise throws {@code NoSuchElementException}.
	 *
	 * @return the non-null value held by this {@code Optional}
	 * @throws NoSuchElementException
	 *             if there is no value present
	 */
	public T getOrNoSuchElementException() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	/**
	 * Return {@code true} if there is a value present, otherwise {@code false}.
	 *
	 * @return {@code true} if there is a value present, otherwise {@code false}
	 */
	public boolean isPresent() {
		return value != null;
	}

	/**
	 * If a value is present, invoke the specified consumer with the value,
	 * otherwise do nothing.
	 *
	 * @param consumer
	 *            block to be executed if a value is present
	 * @throws NullPointerException
	 *             if value is present and {@code consumer} is null
	 */
	public void ifPresent(Consumer<? super T> consumer) {
		if (value != null)
			consumer.accept(value);
	}

	public boolean isEmpry() {
		return !isPresent();
	}

	/**
	 * Return the value if present, otherwise return {@code other}.
	 *
	 * @param other
	 *            the value to be returned if there is no value present, may be
	 *            null
	 * @return the value, if present, otherwise {@code other}
	 */
	public T or(T other) {
		return value != null ? value : other;
	}

	/**
	 * Return the value if present, otherwise invoke {@code other} and return
	 * the result of that invocation.
	 *
	 * @param other
	 *            a {@code Supplier} whose result is returned if no value is
	 *            present
	 * @return the value if present otherwise the result of {@code other.get()}
	 * @throws NullPointerException
	 *             if value is not present and {@code other} is null
	 */
	public T orElseGet(Supplier<? extends T> other) {
		return value != null ? value : other.get();
	}

	/**
	 * Return the contained value, if present, otherwise throw an exception to
	 * be created by the provided supplier.
	 *
	 * @apiNote A method reference to the exception constructor with an empty
	 *          argument list can be used as the supplier. For example,
	 *          {@code IllegalStateException::new}
	 *
	 * @param <X>
	 *            Type of the exception to be thrown
	 * @param exceptionSupplier
	 *            The supplier which will return the exception to be thrown
	 * @return the present value
	 * @throws X
	 *             if there is no value present
	 * @throws NullPointerException
	 *             if no value is present and {@code exceptionSupplier} is null
	 */
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (value != null) {
			return value;
		} else {
			throw exceptionSupplier.get();
		}
	}

	/**
	 * Indicates whether some other object is "equal to" this Option.
	 *
	 * @param obj
	 *            an object to be tested for equality
	 * @return {code true} if the other object is "equal to" this object
	 *         otherwise {@code false}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Option)) {
			return false;
		}

		Option<?> other = (Option<?>) obj;
		return Objects.equals(value, other.value);
	}

	/**
	 * @return true if this is a Empty, false otherwise.
	 */
	public boolean isEmpty() {
		return !isPresent();
	}

	private Option(T value) {
		this.value = value;
	}

	private Option() {
		this.value = null;
	}

	private Stream<T> stream() {
		if (isPresent()) {
			return Stream.of(value);
		}
		return Stream.empty();
	}

	/** {@inheritDoc} **/
	@Override
	public boolean allMatch(Predicate<? super T> predicate) {
		return stream().allMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean anyMatch(Predicate<? super T> predicate) {
		return stream().anyMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public void close() {
		stream().close();
	}

	/** {@inheritDoc} **/
	@Override
	public <R, A> R collect(Collector<? super T, A, R> collector) {
		return stream().collect(collector);
	}

	/** {@inheritDoc} **/
	@Override
	public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
		return stream().collect(supplier, accumulator, combiner);
	}

	/** {@inheritDoc} **/
	@Override
	public long count() {
		return stream().count();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> distinct() {
		return stream().distinct();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> filter(Predicate<? super T> predicate) {
		return stream().filter(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<T> findAny() {
		return stream().findAny();
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<T> findFirst() {
		return stream().findFirst();
	}

	/** {@inheritDoc} **/
	@Override
	public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return stream().flatMap(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
		return stream().flatMapToDouble(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
		return stream().flatMapToInt(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
		return stream().flatMapToLong(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public void forEach(Consumer<? super T> action) {
		stream().forEach(action);
	}

	/** {@inheritDoc} **/
	@Override
	public void forEachOrdered(Consumer<? super T> action) {
		stream().forEachOrdered(action);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean isParallel() {
		return stream().isParallel();
	}

	/** {@inheritDoc} **/
	@Override
	public Iterator<T> iterator() {
		return stream().iterator();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> limit(long maxSize) {
		return stream().limit(maxSize);
	}

	/** {@inheritDoc} **/
	@Override
	public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return stream().map(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
		return stream().mapToDouble(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return stream().mapToInt(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return stream().mapToLong(mapper);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<T> max(Comparator<? super T> comparator) {
		return stream().max(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<T> min(Comparator<? super T> comparator) {
		return stream().min(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public boolean noneMatch(Predicate<? super T> predicate) {
		return stream().noneMatch(predicate);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> onClose(Runnable closeHandler) {
		return stream().onClose(closeHandler);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> parallel() {
		return stream().parallel();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> peek(Consumer<? super T> action) {
		return stream().peek(action);
	}

	/** {@inheritDoc} **/
	@Override
	public Optional<T> reduce(BinaryOperator<T> accumulator) {
		return stream().reduce(accumulator);
	}

	/** {@inheritDoc} **/
	@Override
	public T reduce(T identity, BinaryOperator<T> accumulator) {
		return stream().reduce(identity, accumulator);
	}

	/** {@inheritDoc} **/
	@Override
	public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
		return stream().reduce(identity, accumulator, combiner);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> sequential() {
		return stream().sequential();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> skip(long n) {
		return stream().skip(n);
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> sorted() {
		return stream().sorted();
	}

	/** {@inheritDoc} **/
	@Override
	public Stream<T> sorted(Comparator<? super T> comparator) {
		return stream().sorted(comparator);
	}

	/** {@inheritDoc} **/
	@Override
	public Spliterator<T> spliterator() {
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
	public Stream<T> unordered() {
		return stream().unordered();
	}

}
