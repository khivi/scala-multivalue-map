
import org.scalatest.FunSuite
import com.khivi.collection.mutable.MultiValueMap
import scala.collection.mutable.Map

class MutableSuite extends FunSuite {
    test("basic multivalue map") {
      val m: MultiValueMap[String, Int] = MultiValueMap()
      m addl  ("a", List(1,2))
      m addl ("c", List(3,4))
      assert(m.size === 2)
      assert(m.get("a").get === Seq(1,2))
      assert(m.get("b") === None)
      m addl ("c", List(5))
      assert(m.get("c").get === Seq(3,4,5))

      m -= "a"
      assert(m.get("a") == None)
    }
    test("add multiple values") {
      val m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,2,4)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4))
      m add ("a", 5)
      assert(m.size === 1)
      m add ("a", 6)
      assert(m.get("a").get === Seq(1,3,2,4,5,6))
      m addl ("a", List(1,3,5,7))
      assert(m.get("a").get === Seq(1,3,2,4,5,6,1,3,5,7))
    }
    test("remove multiple values") {
      val m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,4,5)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,4,5))
      m rem ("a", 4)
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,5))
      m reml ("a", List(1,5))
      assert(m.get("a").get === Seq(3))
      m reml ("a", List(3,5))
      assert(m.get("a") === None)
    }
    test("mutability ") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      m add ("d", 0)
      assert(m.size === 1)

      m rem ("d", 0)
      assert(m.size === 0)
    }
}

