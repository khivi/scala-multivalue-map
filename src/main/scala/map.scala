package com.khivi.collection

package immutable

class MultiValueMap[K, +V] private (underlying: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] 
{
  type UV = Iterable[V]
  type U = Map[K, UV]
  type This = MultiValueMap[K, V]

  private def update[IterableV1 >: UV](key:K, value: IterableV1): This = new MultiValueMap[K,V](underlying + (key -> value.asInstanceOf[UV]))
  private def remove(key:K): This = new MultiValueMap[K,V](underlying - key)

  override def get(key: K): Option[UV] = underlying.get(key)

  override def iterator = underlying.iterator

  override def + [IterableV1 >: UV](kv: (K, IterableV1)): This =  {
    val (key, value) = kv
    update(key, value)
  }

  override def - (key: K): This = remove(key)

  override def empty: This = MultiValueMap.empty

  override def size: Int = underlying.size

  override def foreach[U](f: ((K, Iterable[V])) => U): Unit = underlying.foreach(f)

  def add[V1 >: V](kv : (K, V1)) =  {
    val (key, value) = kv
    addl(key -> Iterable[V1](value))
  }
  def addl[V1 >: V](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    val newV = underlying.getOrElse(key, Iterable[V]()) ++ values
    update(key, newV)
  }

  def rem[V1 >: V](kv : (K, V1)) =  {
    val (key, value) = kv
    reml(key -> Iterable[V1](value))
  }
  def reml[V1 >: V](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    underlying.get(key) match {
      case Some(oldV) =>  val removeV = values.toSet
                          oldV.filter(!removeV.contains(_)) match {
                            case Nil => remove(key)
                            case newV => update(key, newV)
                          }
      case None => this
    }
  }
}

object MultiValueMap
{
  private object EmptyMap extends MultiValueMap[Any, Nothing](Map.empty)
  def empty[K,V] = EmptyMap.asInstanceOf[MultiValueMap[K, V]]
  def apply[K,V](underlying: Map[K, Iterable[V]]) = new MultiValueMap(underlying)
}
