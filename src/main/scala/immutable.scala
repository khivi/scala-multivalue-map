package com.khivi.collection
package immutable


import scala.collection.immutable.{Map, MapLike}

class MultiValueMap[K, +V] private (delegate: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]] with MapLike[K, Iterable[V], MultiValueMap[K,V]]
{
  override def get(key: K) =  delegate.get(key)
  override def iterator =  delegate.iterator
  override def +[B1 >: Iterable[V]](kv: (K, B1)) =  new MultiValueMap[K,V](delegate + (kv._1 -> kv._2.asInstanceOf[Iterable[V]]))
  override def -(key: K) =  new MultiValueMap[K,V](delegate-key)
  override def empty = MultiValueMap.empty[K,V]

}

object MultiValueMap
{
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)

  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
