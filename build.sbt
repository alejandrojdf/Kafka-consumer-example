name := "ctm-data-eng-exercise"
val root = (project in file("."))
  .settings(
    //name := "exercise",
    version := "0.1",
    organization := "com.comparethemarket",
    scalaVersion := "2.12.8",
    //libraryDependencies := Seq(),
    libraryDependencies ++= Seq("org.apache.kafka" %% "kafka" % "2.2.0",
      "net.liftweb" %% "lift-json" % "3.3.0")
  )

