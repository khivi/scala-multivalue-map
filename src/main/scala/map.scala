package com.khivi.collection

package immutable

class MultiValueMap[K, +V] private (override val delegate: Map[K, Iterable[V]]) extends Map[K,Iterable[V]] with MultiValueMapLike[K,V, MultiValueMap[K,V]] 
{

  def update[IterableV1 >: UV](key:K, value: IterableV1) = new MultiValueMap[K,V](delegate + (key -> value.asInstanceOf[UV]))
  def remove(key:K) = new MultiValueMap[K,V](delegate - key)
  override def empty = MultiValueMap.empty

}

object MultiValueMap
{
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)

  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
