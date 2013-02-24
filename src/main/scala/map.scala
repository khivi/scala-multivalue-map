package com.khivi.collection

package immutable

class MultiValueMap[K, +V](underlying: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] {
  type IterableV = Iterable[V]
  type U = Map[K, IterableV]
  type This = MultiValueMap[K, V]

  override def get(key: K): Option[IterableV] = underlying.get(key)

  override def iterator = underlying.iterator

  override def - (key: K): This = MultiValueMap(underlying - key)

  override def empty: This = MultiValueMap.empty

  override def size: Int = underlying.size

  override def foreach[U](f: ((K, IterableV)) => U): Unit = underlying.foreach(f)

  // Implementation
  def _add[V1, Iterable[V1] >: IterableV](kv: (K, Iterable[V1])): This = {
    val res = (underlying.get(kv._1) match {
      case Some(sv) => sv ++ kv._2.asInstanceOf[IterableV]
      case None => kv._2.asInstanceOf[IterableV]
    })
    MultiValueMap(underlying + ((kv._1, res)))
  }
  def ++ [V1, Iterable[V1] >: IterableV](kv: (K, Iterable[V1])): This = this._add(kv)
  override def + [V1 >: IterableV](kv: (K, V1)): This = this._add((kv._1, Iterable(kv._2)))

  def _rem[V1, Iterable[V1] >: IterableV](kv: (K, Iterable[V1])): This = underlying.get(kv._1) match {
      case Some(sv) =>  val rv = kv._2.asInstanceOf[IterableV].toSet
                        sv.filter(!rv.contains(_)) match {
                          case Nil => MultiValueMap(underlying - kv._1)
                          case res => MultiValueMap(underlying + ((kv._1, res)))
                        }
      case None => this
  }
  def --[V1, Iterable[V1] >: IterableV](kv: (K, Iterable[V1])): This = this._rem(kv)
  def -[V1 >: IterableV](kv: (K, V1)): This = this._rem((kv._1, Iterable(kv._2)))

}

object MultiValueMap {
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)
  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](underlying: Map[K, Iterable[V]]) = new MultiValueMap(underlying)
}
