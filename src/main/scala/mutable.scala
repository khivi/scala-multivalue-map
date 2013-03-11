package com.khivi.collection
package mutable


import scala.collection.mutable.Map

class MultiValueMap[K, V] private (self: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]]
{
  def get(key: K): Option[Iterable[V]] = self.get(key)
  def iterator: Iterator[(K, Iterable[V])] = self.iterator
  def += (kv: (K, Iterable[V])) = { self += kv; this }
  def -= (key: K) = { self -= key; this}

  override def _update[IterableV1 >: Iterable[V]](key:K, value: IterableV1) = {
    self += (key -> value.asInstanceOf[Iterable[V]])
    this
  }
  override def _remove(key:K) = {
    self -= key
    this
  }
}

object MultiValueMap
{
  def empty[K,V] = new MultiValueMap[K,V](Map[K, Iterable[V]]())
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
