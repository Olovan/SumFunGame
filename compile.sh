if [ ! -d ./bin ]; then
       mkdir bin
fi       
javac -cp "src/" -d bin/ -Xlint src/Main.java
read -p "Press Any Key To Continue"
