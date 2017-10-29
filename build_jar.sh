source compile.sh
pushd bin/
jar -cvfe SumFun.jar Main *.class
popd
mv bin/SumFun.jar .
