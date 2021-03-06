/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2014 Osman KOCAK <kocakosm@gmail.com>                   *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.kocakosm.pitaya.collection;

import org.kocakosm.pitaya.util.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Contains static utility methods that operate on or return {@link Iterable}s.
 *
 * @author Osman KOCAK
 */
public final class Iterables
{
	/** The empty {@link Iterable}. */
	public static final Iterable EMPTY_ITERABLE = new EmptyIterable();

	/**
	 * Returns the empty {@link Iterable} for a particular type (type-safe).
	 * Note that unlike this method, the like-named field does not provide
	 * type safety.
	 *
	 * @param <T> the type of the {@link Iterable}'s elements.
	 *
	 * @return the empty {@link Iterable}.
	 */
	public static <T> Iterable<T> emptyIterable()
	{
		return (Iterable<T>) EMPTY_ITERABLE;
	}

	/**
	 * Concatenates the given {@link Iterable}s into a single one. The
	 * source {@link Iterable}s' {@link Iterator}s are not polled until
	 * necessary. The returned {@link Iterable}'s {@link Iterator}s support
	 * {@link Iterator#remove()} if the corresponding input
	 * {@link Iterable}'s {@link Iterator}s support it.
	 *
	 * @param <T> the type of the returned {@link Iterable}s' elements.
	 * @param iterables the {@link Iterable}s to concatenate.
	 *
	 * @return the concatenated {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends T>... iterables)
	{
		return concat(Arrays.asList(iterables));
	}

	/**
	 * Concatenates the given {@link Iterable}s into a single one. The
	 * source {@link Iterable}s' {@link Iterator}s are not polled until
	 * necessary. The returned {@link Iterable}'s {@link Iterator}s support
	 * {@link Iterator#remove()} if the corresponding input
	 * {@link Iterable}'s {@link Iterator}s support it.
	 *
	 * @param <T> the type of the returned {@link Iterable}s' elements.
	 * @param iterables the {@link Iterable}s to concatenate.
	 *
	 * @return the concatenated {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterables)
	{
		return new ConcatIterable<T>(iterables);
	}

	/**
	 * Returns an {@link Iterable} whose {@link Iterator}s cycle
	 * indefinitely over the elements returned by the {@link Iterable}'s
	 * {@link Iterator}s. The returned {@link Iterable}'s {@link Iterator}s
	 * support {@link Iterator#remove()} if the source {@link Iterable}'s
	 * {@link Iterator}s support it. The returned {@link Iterable} is only a
	 * view, any modifications made to the source {@link Iterable} will also
	 * affect the returned view. Use with caution.
	 *
	 * @param <T> the type of the returned {@link Iterable}'s elements.
	 * @param iterable the {@link Iterable} containing the elements to
	 *	cycle over.
	 *
	 * @return the cyclic {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Iterable<T> cycle(Iterable<? extends T> iterable)
	{
		return new CyclicIterable<T>(toList(iterable));
	}

	/**
	 * Returns an {@link Iterable} whose {@link Iterator}s cycle
	 * indefinitely over the given elements. The returned {@link Iterable}'s
	 * {@link Iterator}s support {@link Iterator#remove()}. Use with
	 * caution.
	 *
	 * @param <T> the type of the returned {@link Iterable}'s elements.
	 * @param elements the elements to cycle over.
	 *
	 * @return the cyclic {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Iterable<T> cycle(T... elements)
	{
		return new CyclicIterable<T>(new ArrayList(Arrays.asList(elements)));
	}

	/**
	 * Creates an {@link Iterable} whose {@link Iterator}s return the first
	 * {@code limit} elements returned by the given {@link Iterable}'s
	 * {@link Iterator}. The returned {@link Iterable} is only a view, any
	 * modifications made to the source {@link Iterable} will also affect
	 * the returned view.
	 *
	 * @param <T> the type of the returned {@link Iterable}'s elements.
	 * @param iterable the {@link Iterable} to limit.
	 * @param limit the number of elements in the returned {@link Iterable}.
	 *
	 * @return the limited {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 * @throws IllegalArgumentException if ({@code limit} is negative.
	 */
	public static <T> Iterable<T> limit(Iterable<? extends T> iterable, int limit)
	{
		return new LimitIterable<T>(iterable, limit);
	}

	/**
	 * Returns an {@link Iterable} whose {@link Iterator}s skip {@code n}
	 * elements from the given {@link Iterable}'s {@link Iterator}s. The
	 * returned {@link Iterable} is only a view, any modifications made to
	 * the source {@link Iterable} will also affect the returned view.
	 *
	 * @param <T> the type of the {@link Iterable}'s elements.
	 * @param iterable the {@link Iterable}.
	 * @param n the number of items to skip.
	 *
	 * @return the skipping {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 * @throws IllegalArgumentException if ({@code n} is negative.
	 */
	public static <T> Iterable<T> skip(Iterable<T> iterable, int n)
	{
		return new SkipIterable<T>(iterable, n);
	}

	/**
	 * Returns a {@link List} containing all the given {@link Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@link List}'s elements.
	 * @param iterable the source {@link Iterable}.
	 *
	 * @return the {@link List} created from the given {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> List<T> toList(Iterable<? extends T> iterable)
	{
		List<T> list = new ArrayList<T>();
		for (T e : iterable) {
			list.add(e);
		}
		return list;
	}

	/**
	 * Returns a {@link Set} containing all the given {@link Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@link Set}'s elements.
	 * @param iterable the source {@link Iterable}.
	 *
	 * @return the {@link Set} created from the given {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Set<T> toSet(Iterable<? extends T> iterable)
	{
		Set<T> set = new HashSet<T>();
		for (T e : iterable) {
			set.add(e);
		}
		return set;
	}

	/**
	 * Returns a {@link Bag} containing all the given {@link Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@link Bag}'s elements.
	 * @param iterable the source {@link Iterable}.
	 *
	 * @return the {@link Bag} created from the given {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Bag<T> toBag(Iterable<? extends T> iterable)
	{
		return new HashBag<T>(iterable);
	}

	/**
	 * Returns a {@link String} representation of the given
	 * {@link Iterable}, with the format [e1, e2, ..., en].
	 *
	 * @param iterable the {@link Iterable}.
	 *
	 * @return the {@link String} created from the given {@link Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static String toString(Iterable<?> iterable)
	{
		return toList(iterable).toString();
	}

	private static final class EmptyIterable<T> implements Iterable<T>
	{
		@Override
		public Iterator<T> iterator()
		{
			return Iterators.emptyIterator();
		}
	}

	private static final class ConcatIterable<T> implements Iterable<T>
	{
		private final Iterable<? extends Iterable<? extends T>> iterables;

		ConcatIterable(Iterable<? extends Iterable<? extends T>> iterables)
		{
			this.iterables = iterables;
		}

		@Override
		public Iterator<T> iterator()
		{
			List<Iterator<? extends T>> iterators = new ArrayList<Iterator<? extends T>>();
			for (Iterable<? extends T> iterable : iterables) {
				iterators.add(iterable.iterator());
			}
			return Iterators.concat(iterators.iterator());
		}
	}

	private static final class CyclicIterable<T> implements Iterable<T>
	{
		private final Iterable<? extends T> iterable;

		CyclicIterable(Iterable<? extends T> iterable)
		{
			Parameters.checkNotNull(iterable);
			this.iterable = iterable;
		}

		@Override
		public Iterator<T> iterator()
		{
			return Iterators.cycle(iterable.iterator());
		}
	}

	private static final class LimitIterable<T> implements Iterable<T>
	{
		private final int limit;
		private final Iterable<? extends T> iterable;

		LimitIterable(Iterable<? extends T> iterable, int limit)
		{
			Parameters.checkNotNull(iterable);
			Parameters.checkCondition(limit >= 0);
			this.iterable = iterable;
			this.limit = limit;
		}

		@Override
		public Iterator<T> iterator()
		{
			return Iterators.limit(iterable.iterator(), limit);
		}
	}

	private static final class SkipIterable<T> implements Iterable<T>
	{
		private final int n;
		private final Iterable<T> iterable;

		SkipIterable(Iterable<T> iterable, int n)
		{
			Parameters.checkNotNull(iterable);
			Parameters.checkCondition(n >= 0);
			this.iterable = iterable;
			this.n = n;
		}

		@Override
		public Iterator<T> iterator()
		{
			Iterator<T> iterator = iterable.iterator();
			Iterators.skip(iterator, n);
			return iterator;
		}
	}

	private Iterables()
	{
		/* ... */
	}
}
