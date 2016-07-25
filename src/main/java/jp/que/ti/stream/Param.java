package jp.que.ti.stream;

public class Param {
	private Param() {
	}

	/**
	 * A pair - a tuple of the types <code>A</code> and <code>B</code>.
	 */
	public static class Tuple<A, B> {

		final public A _1;
		final public B _2;

		public Tuple(A _1, B _2) {
			this._1 = _1;
			this._2 = _2;
		}

		@Override
		public String toString() {
			return "Tuple2(_1: " + _1 + ", _2: " + _2 + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
			result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Tuple))
				return false;
			Tuple other = (Tuple) obj;
			if (_1 == null) {
				if (other._1 != null)
					return false;
			} else if (!_1.equals(other._1))
				return false;
			if (_2 == null) {
				if (other._2 != null)
					return false;
			} else if (!_2.equals(other._2))
				return false;
			return true;
		}
	}

	/**
	 * Constructs a tuple of A,B
	 *
	 * @param a
	 *            The a value
	 * @param b
	 *            The b value
	 * @return The tuple
	 */
	public static <A, B> Tuple<A, B> t2(A a, B b) {
		return new Tuple<A, B>(a, b);
	}

	/**
	 * A tuple of A,B,C
	 */
	public static class Tuple3<A, B, C> {

		final public A _1;
		final public B _2;
		final public C _3;

		public Tuple3(A _1, B _2, C _3) {
			this._1 = _1;
			this._2 = _2;
			this._3 = _3;
		}

		@Override
		public String toString() {
			return "Tuple3(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
			result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
			result = prime * result + ((_3 == null) ? 0 : _3.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Tuple3))
				return false;
			Tuple3 other = (Tuple3) obj;
			if (_1 == null) {
				if (other._1 != null)
					return false;
			} else if (!_1.equals(other._1))
				return false;
			if (_2 == null) {
				if (other._2 != null)
					return false;
			} else if (!_2.equals(other._2))
				return false;
			if (_3 == null) {
				if (other._3 != null)
					return false;
			} else if (!_3.equals(other._3))
				return false;
			return true;
		}
	}

	/**
	 * Constructs a tuple of A,B,C
	 *
	 * @param a
	 *            The a value
	 * @param b
	 *            The b value
	 * @param c
	 *            The c value
	 * @return The tuple
	 */
	public static <A, B, C> Tuple3<A, B, C> t3(A a, B b, C c) {
		return new Tuple3<A, B, C>(a, b, c);
	}

	/**
	 * A tuple of A,B,C,D
	 */
	public static class Tuple4<A, B, C, D> {

		final public A _1;
		final public B _2;
		final public C _3;
		final public D _4;

		public Tuple4(A _1, B _2, C _3, D _4) {
			this._1 = _1;
			this._2 = _2;
			this._3 = _3;
			this._4 = _4;
		}

		@Override
		public String toString() {
			return "Tuple4(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ", _4:" + _4 + ")";
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
			result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
			result = prime * result + ((_3 == null) ? 0 : _3.hashCode());
			result = prime * result + ((_4 == null) ? 0 : _4.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Tuple4))
				return false;
			Tuple4 other = (Tuple4) obj;
			if (_1 == null) {
				if (other._1 != null)
					return false;
			} else if (!_1.equals(other._1))
				return false;
			if (_2 == null) {
				if (other._2 != null)
					return false;
			} else if (!_2.equals(other._2))
				return false;
			if (_3 == null) {
				if (other._3 != null)
					return false;
			} else if (!_3.equals(other._3))
				return false;
			if (_4 == null) {
				if (other._4 != null)
					return false;
			} else if (!_4.equals(other._4))
				return false;
			return true;
		}
	}

	/**
	 * Constructs a tuple of A,B,C,D
	 *
	 * @param a
	 *            The a value
	 * @param b
	 *            The b value
	 * @param c
	 *            The c value
	 * @param d
	 *            The d value
	 * @return The tuple
	 */
	public static <A, B, C, D> Tuple4<A, B, C, D> t4(A a, B b, C c, D d) {
		return new Tuple4<A, B, C, D>(a, b, c, d);
	}

	/**
	 * A tuple of A,B,C,D,E
	 */
	public static class Tuple5<A, B, C, D, E> {

		final public A _1;
		final public B _2;
		final public C _3;
		final public D _4;
		final public E _5;

		public Tuple5(A _1, B _2, C _3, D _4, E _5) {
			this._1 = _1;
			this._2 = _2;
			this._3 = _3;
			this._4 = _4;
			this._5 = _5;
		}

		@Override
		public String toString() {
			return "Tuple5(_1: " + _1 + ", _2: " + _2 + ", _3:" + _3 + ", _4:" + _4 + ", _5:" + _5 + ")";
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
			result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
			result = prime * result + ((_3 == null) ? 0 : _3.hashCode());
			result = prime * result + ((_4 == null) ? 0 : _4.hashCode());
			result = prime * result + ((_5 == null) ? 0 : _5.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Tuple5))
				return false;
			Tuple5 other = (Tuple5) obj;
			if (_1 == null) {
				if (other._1 != null)
					return false;
			} else if (!_1.equals(other._1))
				return false;
			if (_2 == null) {
				if (other._2 != null)
					return false;
			} else if (!_2.equals(other._2))
				return false;
			if (_3 == null) {
				if (other._3 != null)
					return false;
			} else if (!_3.equals(other._3))
				return false;
			if (_4 == null) {
				if (other._4 != null)
					return false;
			} else if (!_4.equals(other._4))
				return false;
			if (_5 == null) {
				if (other._5 != null)
					return false;
			} else if (!_5.equals(other._5))
				return false;
			return true;
		}
	}

	/**
	 * Constructs a tuple of A,B,C,D,E
	 *
	 * @param a
	 *            The a value
	 * @param b
	 *            The b value
	 * @param c
	 *            The c value
	 * @param d
	 *            The d value
	 * @param e
	 *            The e value
	 * @return The tuple
	 */
	public static <A, B, C, D, E> Tuple5<A, B, C, D, E> t5(A a, B b, C c, D d, E e) {
		return new Tuple5<A, B, C, D, E>(a, b, c, d, e);
	}

}
