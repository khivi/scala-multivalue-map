package com.khivi.collection

import scala.collection.MapLike

trait MultiValueMapLike[K, +V, +This <: MultiValueMapLike[K, V, This] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], This]
{
  type UV = Iterable[V]
  type U = Map[K, UV]
  protected val underlying: Map[K, Iterable[V]]

  protected[this] def update[IterableV1 >: UV](key:K, value: IterableV1): This
  protected[this] def remove(key:K): This

  override def get(key: K): Option[UV] = underlying.get(key)
  override def iterator = underlying.iterator
  override def + [IterableV1 >: UV](kv: (K, IterableV1)): This = update(kv._1, kv._2)
  override def - (key: K): This = remove(key)
  override def size: Int = underlying.size
  override def foreach[U](f: ((K, Iterable[V])) => U): Unit = underlying.foreach(f)

  def add[V1 >: V](kv : (K, V1)): This =  {
    val (key, value) = kv
    addl(key -> Iterable[V1](value))
  }
  def addl[V1 >: V](kv : (K, Iterable[V1])): This =  {
    val (key, values) = kv
    val newV = underlying.getOrElse(key, Iterable[V]()) ++ values
    update(key, newV)
  }

  def rem[V1 >: V](kv : (K, V1)): This =  {
    val (key, value) = kv
    reml(key -> Iterable[V1](value))
  }
  def reml[V1 >: V](kv : (K, Iterable[V1])): This =  {
    val (key, values) = kv
    underlying.get(key) match {
      case Some(oldV) =>  val removeV = values.toSet
                          oldV.filter(!removeV.contains(_)) match {
                            case Nil => remove(key)
                            case newV => update(key, newV)
                          }
      case None => this.asInstanceOf[This]
    }
  }
}

