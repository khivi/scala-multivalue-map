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
  def _add[SeqV1 >: SeqV](kv: (K, SeqV1)): This = {
    val res = (underlying.get(kv._1) match {
      case Some(sv) => sv ++ kv._2.asInstanceOf[SeqV]
      case None => kv._2.asInstanceOf[SeqV]
    })
    MultiValueMap(underlying + ((kv._1, res)))
  }
  def ++ [SeqV1 >: SeqV](kv: (K, SeqV1)): This = this._add(kv)
  override def + [V1 >: SeqV](kv: (K, V1)): This = this._add((kv._1, Seq(kv._2)))

  def _rem[SeqV1 >: SeqV](kv: (K, SeqV1)): This = underlying.get(kv._1) match {
      case Some(sv) =>  val kv2 = kv._2.asInstanceOf[SeqV]
                        sv.filter(!kv2.contains(_)) match {
                          case Nil => MultiValueMap(underlying - kv._1)
                          case res => MultiValueMap(underlying + ((kv._1, res)))
                        }
      case None => this
  }
  def --[SeqV1 >: SeqV](kv: (K, SeqV1)): This = this._rem(kv)
  def -[V1 >: SeqV](kv: (K, V1)): This = this._rem((kv._1, Seq(kv._2)))

}

object MultiValueMap {
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)
  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](underlying: Map[K, Seq[V]]) = new MultiValueMap(underlying)
}
