package com.khivi.collection

import scala.collection.{Map, MapLike}

trait MultiValueMapLike[K, +V, +This <: MultiValueMapLike[K, V, This] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], This]
{
  def +[V1 >: V](key: K, value : V1): This = this+(key, Iterable[V1](value))
  def +[V1 >: V](key: K, values :Iterable[V1]): This
  def -[V1 >: V](key: K, value :V1): This = this-(key, Iterable[V1](value))
  def -[V1 >: V](key: K, values: Iterable[V1]): This
}

