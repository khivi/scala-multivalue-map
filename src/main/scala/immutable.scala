package com.khivi.collection
package immutable


import scala.collection.immutable.Map

class MultiValueMap[K, +V] private (delegate: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]]
{
  override def get(key: K): Option[Iterable[V]] =  delegate.get(key)
  override def iterator: Iterator[(K, Iterable[V])] =  delegate.iterator
  override def + [B1 >: Iterable[V]](kv: (K, B1)): Map[K, B1] =  delegate+kv
  override def -(key: K): Map[K, Iterable[V]] =  delegate-key

  override def _update[IterableV1 >: Iterable[V]](key:K, value: IterableV1) = new MultiValueMap[K,V](delegate + (key -> value.asInstanceOf[Iterable[V]]))
  override def _remove(key:K) = new MultiValueMap[K,V](delegate - key)
}

object MultiValueMap
{
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)

  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
