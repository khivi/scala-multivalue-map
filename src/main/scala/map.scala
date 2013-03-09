package com.khivi.collection

package immutable

class MultiValueMap[K, +V] private (underlying: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] 
{
  type U = Map[K, Iterable[V]]
  type This = MultiValueMap[K, V]

  override def get(key: K): Option[Iterable[V]] = underlying.get(key)

  override def iterator = underlying.iterator

  override def + [B >: Iterable[V]](kv: (K, B)): This =  {
    val (key, value) = kv
    new MultiValueMap[K,V](underlying + (key -> value.asInstanceOf[Iterable[V]]))
  }

  override def - (key: K): This = MultiValueMap(underlying - key)

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
    MultiValueMap(underlying + (key -> newV))
  }

  def rem[V1 >: V](kv : (K, V1)) =  {
    val (key, value) = kv
    reml(key -> Iterable[V1](value))
  }
  def reml[V1 >: V](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    underlying.get(key) match {
      case Some(oldV) =>  val remove = values.toSet
                          oldV.filter(!remove.contains(_)) match {
                            case Nil => MultiValueMap(underlying - key)
                            case newV => MultiValueMap(underlying + (key -> newV))
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
