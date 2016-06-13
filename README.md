# onsbt

Shows how to build project using sbt

## Assembly

make uber jar using Assembly plugin
```
sbt assembly
```
Assembly jar should not include Provided jars

setting.sbt assemblyExcludedJars allows to exclude jars directly

build.sbt dependencyOverrides allws to select correct jar versions

## Dependency Graph

Show dependency tree using net.virtual-void.sbt-dependency-graph plugin
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
