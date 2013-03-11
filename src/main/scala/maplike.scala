package com.khivi.collection

import scala.collection.MapLike

trait MultiValueMapLike[K, +V, +Self <: MultiValueMapLike[K, V, Self] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], Self]
{
  type UV = Iterable[V]
  type U = Map[K, UV]
  protected val delegate: Map[K, Iterable[V]]

  protected[this] def _update[IterableV1 >: UV](key:K, value: IterableV1): Self
  protected[this] def _remove(key:K): Self

  override def get(key: K): Option[UV] = delegate.get(key)
  override def iterator = delegate.iterator
  override def + [IterableV1 >: UV](kv: (K, IterableV1)): Self = _update(kv._1, kv._2)
  override def - (key: K): Self = _remove(key)
  override def size: Int = delegate.size
  override def foreach[U](f: ((K, Iterable[V])) => U): Unit = delegate.foreach(f)

  def add[V1 >: V](kv : (K, V1)): Self =  {
    val (key, value) = kv
    addl(key -> Iterable[V1](value))
  }
  def addl[V1 >: V](kv : (K, Iterable[V1])): Self =  {
    val (key, values) = kv
    val newV = delegate.getOrElse(key, Iterable[V]()) ++ values
    _update(key, newV)
  }

  def rem[V1 >: V](kv : (K, V1)): Self =  {
    val (key, value) = kv
    reml(key -> Iterable[V1](value))
  }
  def reml[V1 >: V](kv : (K, Iterable[V1])): Self =  {
    val (key, values) = kv
    delegate.get(key) match {
      case Some(oldV) =>  val removeV = values.toSet
                          oldV.filter(!removeV.contains(_)) match {
                            case Nil => _remove(key)
                            case newV => _update(key, newV)
                          }
      case None => this.asInstanceOf[Self]
    }
  }
}

