javac -cp "src" -d bin src\sumfun\Main.java
pushd bin
jar -cvfe SumFun.jar sumfun.Main sumfun/*.class
popd
move bin\SumFun.jar SumFun.jar
