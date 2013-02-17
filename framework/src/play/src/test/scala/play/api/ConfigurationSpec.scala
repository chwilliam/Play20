package play.api

import org.specs2.mutable.Specification
import java.util.concurrent.TimeUnit

object ConfigurationSpec extends Specification {

  def exampleConfig = Configuration.from(Map("foo.bar1" -> "value1", "foo.bar2" -> "value2", "blah" -> "value3", "time" -> "1 second"))

  "Configuration" should {

    "be accessible as an entry set" in {
      val map = Map(exampleConfig.entrySet.toList:_*)
      map.keySet must contain("foo.bar1", "foo.bar2", "blah", "time").only
    }

    "make all paths accessible" in {
      exampleConfig.keys must contain("foo.bar1", "foo.bar2", "blah", "time").only
    }

    "make all sub keys accessible" in {
      exampleConfig.subKeys must contain("foo", "blah", "time").only
    }

    "convert a time to millis" in {
      exampleConfig.getMilliseconds("time") must beSome(1000)
    }

    "convert a time to a duration" in {
      exampleConfig.getDuration("time") must beSome(concurrent.duration.Duration(1, TimeUnit.SECONDS))
    }

  }

}
