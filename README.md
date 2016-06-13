# onsbt

Shows how to build project using sbt

to run regular build and assembly
```
sbt clean package assembly
```

## Assembly

to make uber jar using Assembly plugin
```
sbt assembly
```
Assembly jar should not include Provided jars

setting.sbt assemblyExcludedJars allows to exclude jars directly

build.sbt dependencyOverrides allows to select correct jar versions

## Dependency Graph

to how dependency tree
```
# ASCII
sbt dependency-graph

# in browser
sbt dependencyBrowseGraph
```

## Test

```
sbt test
```

### Run only particular Test suites
to run only particular test Suite (M1Suite) open sbt console and run
````
testOnly *M1Suite
````

to run only particular method (f1) from test Suite (M1Suite)
```
testOnly *M1Suite -- -t f1
```

## TODO Integration Test
