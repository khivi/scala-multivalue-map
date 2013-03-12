package com.khivi.collection
package mutable

import scala.collection.mutable.{Map, MapLike}

class MultiValueMap[K, V] private (self: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]] with MapLike[K, Iterable[V], MultiValueMap[K,V]]
{
  override def get(key: K): Option[Iterable[V]] = self.get(key)
  override def iterator: Iterator[(K, Iterable[V])] = self.iterator
  override def += (kv: (K, Iterable[V])) = { self += kv; this }
  override def -= (key: K) = { self -= key; this}
  override def -(key: K) =  { self -= key; this}
  override def empty = MultiValueMap.empty[K,V]

  override def _update[IterableV1 >: Iterable[V]](kv:(K, IterableV1)) = {
    self += (kv._1 -> kv._2.asInstanceOf[Iterable[V]])
    this
  }
}

object MultiValueMap
{
  def empty[K,V] = new MultiValueMap[K,V](Map[K, Iterable[V]]())
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
