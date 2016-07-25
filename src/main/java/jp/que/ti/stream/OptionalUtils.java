package jp.que.ti.stream;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import jp.que.ti.stream.Param.Tuple3;
import jp.que.ti.stream.Param.Tuple4;
import jp.que.ti.stream.Param.Tuple5;

public class OptionalUtils {
	private OptionalUtils() {
	}

	public static <A, B, RETURN> Optional<RETURN> forYield(//
			Optional<A> optA, Optional<B> optB//
			, BiFunction<A, B, RETURN> operator) {

		return optA.flatMap(a -> //
		optB.map(b -> operator.apply(a, b)));
	}

	public static <T1, T2, T3, RETURN> Optional<RETURN> forYield(//
			Optional<T1> optA, Optional<T2> optB, Optional<T3> optC //
			, Function<Tuple3<T1, T2, T3>, RETURN> operator) {

		return optA.flatMap(_1 -> //
		optB.flatMap(_2 -> //
		optC.map(_3 -> //
		operator.apply(Param.t3(_1, _2, _3)))));
	}

	public static <T1, T2, T3, T4, RETURN> Optional<RETURN> forYield(//
			Optional<T1> optA, Optional<T2> optB, Optional<T3> optC, Optional<T4> optD //
			, Function<Tuple4<T1, T2, T3, T4>, RETURN> operator) {

		return optA.flatMap(_1 -> //
		optB.flatMap(_2 -> //
		optC.flatMap(_3 -> //
		optD.map(_4 -> //
		operator.apply(Param.t4(_1, _2, _3, _4))))));
	}

	public static <T1, T2, T3, T4, T5, RETURN> Optional<RETURN> forYield(//
			Optional<T1> optA, Optional<T2> optB, Optional<T3> optC, Optional<T4> optD, Optional<T5> optE//
			, Function<Tuple5<T1, T2, T3, T4, T5>, RETURN> operator) {

		return optA.flatMap(_1 -> //
		optB.flatMap(_2 -> //
		optC.flatMap(_3 -> //
		optD.flatMap(_4 -> //
		optE.map(_5 -> //
		operator.apply(Param.t5(_1, _2, _3, _4, _5)))))));
	}

}
