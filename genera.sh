cd Proyecto_Final/bin
rm *.class
rm Ventas*
cd ../src
javac *.java
mv *.class ../bin
cd ../bin
java Simulador
