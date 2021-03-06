
import org.scalatest.FunSuite
import com.khivi.collection.immutable.MultiValueMap

class ImmutableSuite extends FunSuite {
    test("basic multivalue map") {
      var m: MultiValueMap[String, Int] = MultiValueMap()
      m = m +  ("a", List(1,2))
      m = m + ("c", List(3,4))
      assert(m.size === 2)
      assert(m.get("a").get === Seq(1,2))
      assert(m.get("b") === None)
      m = m + ("c", List(5))
      assert(m.get("c").get === Seq(3,4,5))

      assert((m - "a").get("a") == None)
    }
    test("add multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,2,4)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4))
      m = m + ("a", 5)
      assert(m.size === 1)
      m = m + ("a", 6)
      assert(m.get("a").get === Seq(1,3,2,4,5,6))
      m = m + ("a", List(1,3,5,7))
      assert(m.get("a").get === Seq(1,3,2,4,5,6,1,3,5,7))
    }
    test("remove multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,4,5)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,4,5))
      m = m - ("a", 4)
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,5))
      m = m - ("a", List(1,5))
      assert(m.get("a").get === Seq(3))
      m = m - ("a", List(3,5))
      assert(m.get("a") === None)
    }
    test("immutability ") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      val m1 = m + ("d", 0)
      assert(m.size === 0)
      m = m + ("d", 0)
      assert(m.size === 1)

      val m2 = m - ("d", 0)
      assert(m.size === 1)
      m = m - ("d", 0)
      assert(m.size === 0)
    }
}

