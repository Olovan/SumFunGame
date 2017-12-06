source compile.sh
pushd bin/
jar -cvfe SumFun.jar sumfun.Main sumfun/*.class
popd
mv bin/SumFun.jar .
read -p "Press Any Key"
