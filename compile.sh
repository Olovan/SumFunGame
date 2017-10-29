if [ ! -d ./bin ]; then
       mkdir bin
fi       
javac -cp "src/" -d bin/ src/Main.java
