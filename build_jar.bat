javac -cp "src" -d bin src\Main.java
pushd bin
jar -cvfe SumFun.jar Main *.class
popd
move bin\SumFun.jar SumFun.jar
