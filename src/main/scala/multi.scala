package com.khivi.collection

import scala.collection.{Map, MapLike}

trait MultiValueMapImpl[K, +V, +This <: MultiValueMapImpl[K, V, This] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], This]
{
  protected[this] def _update[IterableV1 >: Iterable[V]](kv:(K, IterableV1)): This

  def add[V1 >: V](kv : (K, V1)): This =  {
    val (key, value) = kv
    addl(key -> Iterable[V1](value))
  }
  def addl[V1 >: V](kv : (K, Iterable[V1])): This =  {
    val (key, values) = kv
    val newV = (get(key) match {
                case Some(oldV) => oldV
                case None => Iterable[V]()
               }) ++ values
    _update((key, newV))
  }

  def rem[V1 >: V](kv : (K, V1)): This =  {
    val (key, value) = kv
    reml(key -> Iterable[V1](value))
  }
  def reml[V1 >: V](kv : (K, Iterable[V1])): This =  {
    val (key, values) = kv
    get(key) match {
      case Some(oldV) =>  val removeV = values.toSet
                          oldV.filter(!removeV.contains(_)) match {
                            case Nil => this - key
                            case newV => _update((key, newV))
                          }
      case None => this.asInstanceOf[This]
    }
  }
}

