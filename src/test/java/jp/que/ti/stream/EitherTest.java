package jp.que.ti.stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

public class EitherTest {

	@Test
	public void test_RightGetter() {
		Either<RuntimeException, String> eth = Either.right("a");

		assertThat(eth.getOr("foo"), is("a"));
		assertThat(eth.getOrNoSuchElementException(), is("a"));
		assertThat(eth.orElse("foo"), is("a"));
		assertThat(eth.orElseGet(() -> "foo"), is("a"));
		assertThat(eth.orElseThrow(() -> new RuntimeException()), is("a"));

		NumberFormatException excp = new NumberFormatException("hoge");
		assertThat(eth.getLeftOr(excp), is(excp));

		try {
			eth.getLeftOrNoSuchElementException();
		} catch (NoSuchElementException e) {
			assertTrue("NoSuchElementException例外発生するはず", true);
		}

	}

	@Test
	public void test_LeftGetter() {
		NumberFormatException excp = new NumberFormatException("boo");
		Either<RuntimeException, String> eth = Either.left(excp);

		assertThat(eth.getOr("foo"), is("foo"));

		try {
			eth.getOrNoSuchElementException();
		} catch (NoSuchElementException e) {
			assertTrue("NoSuchElementException 例外発生するはず", true);
		}

		assertThat(eth.orElse("foo"), is("foo"));
		assertThat(eth.orElseGet(() -> "foo"), is("foo"));

		try {
			eth.orElseThrow(() -> new IllegalArgumentException());
		} catch (IllegalArgumentException e) {
			assertTrue("IllegalArgumentException例外発生するはず", true);
		}

	}

	@Test
	public void test_map() {
		Either<RuntimeException, String> eth = null;

		eth = Either.right("a");

		Function<String, String> mapper = x -> "*" + x;

		assertThat(eth.map(mapper).getOr(""), is("*a"));

		// *********
		NumberFormatException excp = new NumberFormatException("boo");
		eth = Either.left(excp);

		assertTrue(eth.map(mapper).isLeft());

	}

	@Test
	public void test_flatMap() {
		Either<RuntimeException, String> eth = null;

		eth = Either.right("a");

		Function<String, Stream<String>> mapper = x -> Stream.of("*" + x);
		Function<String, Either.Right<RuntimeException, String>> mapperEth = x -> Either.right("*" + x);

		final IllegalArgumentException ilExcp = new IllegalArgumentException("boo");
		Function<String, Either.Left<RuntimeException, String>> mapperException = x -> Either.left(ilExcp);

		assertThat(eth.flatMap(mapper).findFirst().orElse(""), is("*a"));
		assertThat(eth.flatMapEither(mapperEth).findFirst().orElse(""), is("*a"));

		assertThat(eth.flatMapEither(mapperException).getLeftOrNoSuchElementException(), is(ilExcp));

		// *********
		final NumberFormatException numExcp = new NumberFormatException("boo");
		eth = Either.left(numExcp);

		assertTrue(eth.flatMap(mapper).findFirst().isPresent() == false);
		assertTrue(eth.flatMapEither(mapperEth).findFirst().isPresent() == false);

		assertThat(eth.flatMapEither(mapperException).getLeftOrNoSuchElementException(), is(numExcp));

	}

}
