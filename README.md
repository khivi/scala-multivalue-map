scala-multivalue-map
====================

Multivalue maps in scala
=======

	scala> com.khivi.collection.immutable.MultiValueMap.empty[String,Int]
	res0: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map()
	scala> res0.addl("a", List(1,2))
	res1: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map(a -> List(1, 2))
	scala> res1.add("a", 3)
	res2: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map(a -> List(1, 2, 3))
	scala> res2.reml("a", List(1,3))
	res3: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map(a -> List(2))
	scala> res3.rem("a",2)
	res5: com.khivi.collection.immutable.MultiValueMap[String,Int] = Map()
