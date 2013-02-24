package com.khivi.collection

package immutable

class MultiValueMap[K, +V](underlying: Map[K, Seq[V]]) extends Map[K, Seq[V]] {
  type SeqV = Seq[V]
  type U = Map[K, SeqV]
  type This = MultiValueMap[K, V]

  override def get(key: K): Option[SeqV] = underlying.get(key)

  override def iterator = underlying.iterator

  override def - (key: K): This = MultiValueMap(underlying - key)

  override def empty: This = MultiValueMap.empty

  override def size: Int = underlying.size

  override def foreach[U](f: ((K, SeqV)) => U): Unit = underlying.foreach(f)

  // Implementation

  override def + [SeqV1 >: SeqV](kv: (K, SeqV1)): This = {
    val res = (underlying.get(kv._1) match {
      case Some(sv) => sv ++ kv._2.asInstanceOf[Seq[V]]
      case None => kv._2.asInstanceOf[Seq[V]]
    })
    MultiValueMap(underlying + ((kv._1, res)))
  }

}

object MultiValueMap {
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)
  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](underlying: Map[K, Seq[V]]) = new MultiValueMap(underlying)
}
