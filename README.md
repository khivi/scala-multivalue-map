scala-multivalue-map
====================

Multivalue maps in scala
=======

    scala> var m = MultiValueMap.empty[String, Int]
    m: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map()
    scala> m += ("a", 1)
    scala> m ++= ("b", List(3,4,5))
    scala> m.toString
    res12: String = Map(a -> List(1), b -> List(3, 4, 5))
    scala> m -= ("a", 1)
    scala> m --= ("b", List(3,5))
    scala> m.toString
    res16: String = Map(b -> List(4))