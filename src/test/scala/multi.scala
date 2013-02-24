
import org.scalatest.FunSuite
import com.khivi.collection.immutable.MultiValueMap

class ExampleSuite extends FunSuite {
    test("basic multivalue map") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      m ++= ("a", List(1,2))
      m ++= ("c", List(3,4))
      assert(m.size === 2)
      assert(m.get("a").get === Seq(1,2))
      assert(m.get("b") === None)
      assert(m.get("c").get === Seq(3,4))

      m -= "a"
      assert(m.size === 1)
      assert(m.get("a") == None)
    }
    test("add multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,2,4)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4))
      m += ("a", 5)
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4,5))
    }
    test("remove multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap(Map("a" -> List(1,3,4,5)))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,4,5))
      m -= ("a", 4)
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,5))
      m --= ("a", List(1,5))
      assert(m.get("a").get === Seq(3))
      m --= ("a", List(3,5))
      assert(m.get("a") === None)
    }
}

