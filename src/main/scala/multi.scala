package com.khivi.collection

import scala.collection.{Map, MapLike}

trait MultiValueMapLike[K, +V, +This <: MultiValueMapLike[K, V, This] with Map[K, Iterable[V]]] extends MapLike[K, Iterable[V], This]
{
  def add[V1 >: V](key: K, value : V1): This = add(key, Iterable[V1](value))
  def add[V1 >: V](key: K, values :Iterable[V1]): This
  def rem[V1 >: V](key: K, value :V1): This = rem(key, Iterable[V1](value))
  def rem[V1 >: V](key: K, values: Iterable[V1]): This
}

