package jp.que.ti.stream;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.BaseStream;

public class StreamUtils {
	private StreamUtils() {
	}

	public static <ACCUM, T> ACCUM foldLeft(BaseStream<T, ?> stream //
			, ACCUM initAccumulator //
			, BiFunction<ACCUM, T, ACCUM> op) {

		ACCUM accum = initAccumulator;
		final Iterator<T> it = stream.iterator();
		while (it.hasNext()) {
			final T t = it.next();
			accum = op.apply(accum, t);
		}
		return accum;
	}

}
