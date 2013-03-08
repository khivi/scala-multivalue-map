package com.khivi.collection

package immutable

class MultiValueMap[K, +V](underlying: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] {
  type IterableV = Iterable[V]
  type U = Map[K, IterableV]
  type This = MultiValueMap[K, V]

  override def get(key: K): Option[IterableV] = underlying.get(key)

  override def iterator = underlying.iterator

  override def + [IterableV1 >: IterableV](kv: (K, IterableV1)): This = MultiValueMap(underlying + kv.asInstanceOf[(K,IterableV)])

  override def - (key: K): This = MultiValueMap(underlying - key)

  override def empty: This = MultiValueMap.empty

  override def size: Int = underlying.size

  override def foreach[U](f: ((K, IterableV)) => U): Unit = underlying.foreach(f)

  def add[V1, Iterable[V1] >: IterableV](kv : (K, V1)) =  {
    val (key, value) = kv
    addl(key -> Iterable(value))
  }
  def addl[V1, Iterable[V1] >: IterableV](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    val newV = underlying.getOrElse(key, Iterable()) ++ values.asInstanceOf[IterableV]
    MultiValueMap(underlying + (key -> newV))
  }

  def rem[V1, Iterable[V1] >: IterableV](kv : (K, V1)) =  {
    val (key, value) = kv
    reml(key -> Iterable(value))
  }
  def reml[V1, Iterable[V1] >: IterableV](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    underlying.get(key) match {
      case Some(oldV) =>  val remove = values.asInstanceOf[IterableV].toSet
                  oldV.filter(!remove.contains(_)) match {
                    case Nil => MultiValueMap(underlying - key)
                    case newV => MultiValueMap(underlying + (key -> newV))
                  }
      case None => this
    }
  }
}

object MultiValueMap {
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)
  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](underlying: Map[K, Iterable[V]]) = new MultiValueMap(underlying)
}
