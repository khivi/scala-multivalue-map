package com.khivi.collection

import scala.collection.{Map, MapLike}

trait MultiValueMapLike[K, +V, +This <: MultiValueMapLike[K, V, This] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], This]
{
  def add[V1 >: V](kv : (K, V1)): This = addl(kv._1 -> Iterable[V1](kv._2))
  def addl[V1 >: V](kv : (K, Iterable[V1])): This
  def rem[V1 >: V](kv : (K, V1)): This = reml(kv._1 -> Iterable[V1](kv._2))
  def reml[V1 >: V](kv : (K, Iterable[V1])): This
}

