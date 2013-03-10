
import org.scalatest.FunSuite
import com.khivi.collection.immutable.MultiValueMap

class ExampleSuite extends FunSuite {
    test("basic multivalue map") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      m += ("a", List(1,2))
      m = m addl ("c", List(3,4))
      assert(m.size === 2)
      assert(m.get("a").get === Seq(1,2))
      assert(m.get("b") === None)
      m = m addl ("c", List(5))
      assert(m.get("c").get === Seq(3,4,5))

      m -= "a"
      assert(m.size === 1)
      assert(m.get("a") == None)
    }
    test("add multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,2,4)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4))
      m = m add ("a", 5)
      assert(m.size === 1)
      m = m add ("a", 6)
      assert(m.get("a").get === Seq(1,3,2,4,5,6))
      m = m addl ("a", List(1,3,5,7))
      assert(m.get("a").get === Seq(1,3,2,4,5,6,1,3,5,7))
    }
    test("remove multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,4,5)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,4,5))
      m = m rem ("a", 4)
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,5))
      m = m reml ("a", List(1,5))
      assert(m.get("a").get === Seq(3))
      m = m reml ("a", List(3,5))
      assert(m.get("a") === None)
    }
    test("immutability ") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      val m1 = m add ("d", 0)
      assert(m.size === 0)
      m = m add ("d", 0)
      assert(m.size === 1)

      val m2 = m rem ("d", 0)
      assert(m.size === 1)
      m = m rem ("d", 0)
      assert(m.size === 0)
    }
}

