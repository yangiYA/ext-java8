package jp.que.ti.stream;

import static jp.que.ti.stream.StreamUtils.foldLeft;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamUtilsTest {

	@Test
	public void test_foldLeft() {

		String result = null;
		IntStream iStream = null;

		// *********
		iStream = IntStream.range(1, 10);
		result = foldLeft(iStream, "", (str, i) -> str + i);
		assertThat(result, is("123456789"));

		// *********
		iStream = IntStream.range(1, 10) //
				.filter(i -> i % 2 == 0);
		result = foldLeft(iStream, "", (str, i) -> str + i);
		assertThat(result, is("2468"));

		// *********
		Stream<String> stream = Stream.of("a", "b", "c", "d")//
				.filter(str -> str.equals("c") || str.equals("a"));
		result = foldLeft(stream, "", (str, i) -> str + i);
		assertThat(result, is("ac"));

	}

}
