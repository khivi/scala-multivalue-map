
import org.scalatest.FunSuite
import com.khivi.collection.immutable.MultiValueMap

class ExampleSuite extends FunSuite {
    test("basic multivalue map") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      m += ("a", List(1,2))
      m += ("c", List(3,4))
      assert(m.size === 2)
      assert(m.get("a").get === Seq(1,2))
      assert(m.get("b") === None)
      assert(m.get("c").get === Seq(3,4))

      m -= "a"
      assert(m.size === 1)

      assert(m.get("a") == None)
    }
    test("add multiple values") {
      var m: MultiValueMap[String, Int] = MultiValueMap.empty
      m += ("a", List(1,3))
      m += ("a", List(2,4))
      assert(m.size === 1)
      assert(m.get("a").get === Seq(1,3,2,4))
    }
}

