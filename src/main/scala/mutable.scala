package com.khivi.collection
package mutable

import scala.collection.mutable.{Map, MapLike}

class MultiValueMap[K, V] private (delegate: Map[K, Iterable[V]]) extends Map[K, Iterable[V]] with MultiValueMapImpl[K,V, MultiValueMap[K,V]] with MapLike[K, Iterable[V], MultiValueMap[K,V]]
{
  override def get(key: K) = delegate.get(key)
  override def iterator = delegate.iterator
  override def += (kv: (K, Iterable[V])) = { delegate += kv; this }
  override def -= (key: K) = { delegate -= key; this}
  override def empty = MultiValueMap.empty[K,V]

  override def -(key: K) =  new MultiValueMap[K,V](delegate-key)
  override def +[B1 >: Iterable[V]](kv: (K, B1)) =  (delegate + kv)

  override def addl[V1 >: V](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    val newV = (get(key) match {
                case Some(oldV) => oldV
                case None => Iterable[V]()
               }) ++ values.asInstanceOf[Iterable[V]]
    this += (key -> newV)
  }

  override def reml[V1 >: V](kv : (K, Iterable[V1])) =  {
    val (key, values) = kv
    get(key) match {
      case Some(oldV) =>  val removeV = values.toSet
                          oldV.filter(!removeV.contains(_)) match {
                            case Nil => this -= key
                            case newV => this += (key -> newV)
                          }
      case None => this
    }
  }
}

object MultiValueMap
{
  def empty[K,V] = new MultiValueMap[K,V](Map[K, Iterable[V]]())
  def apply[K,V](delegate: Map[K, Iterable[V]]) = new MultiValueMap(delegate)
}
