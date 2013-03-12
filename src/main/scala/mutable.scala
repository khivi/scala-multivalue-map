package com.khivi.collection
package mutable

import scala.collection.mutable.{Map, MapLike}

class MultiValueMap[K, V] private (delegate: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]] with MapLike[K, Iterable[V], MultiValueMap[K,V]]
{
  override def get(key: K) = delegate.get(key)
  override def iterator = delegate.iterator
  override def += (kv: (K, Iterable[V])) = { delegate += kv; this }
  override def -= (key: K) = { delegate -= key; this}
  override def empty = MultiValueMap.empty[K,V]

  override def -(key: K) =  new MultiValueMap[K,V](delegate-key)
  override def +[B1 >: Iterable[V]](kv: (K, B1)) =  (delegate + kv)
}

object MultiValueMap
{
  def empty[K,V] = new MultiValueMap[K,V](Map[K, Iterable[V]]())
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
