MiniNet:
	javac src/com/AdvancedProgramming/*.java src/com/AdvancedProgramming/Users/*.java src/com/AdvancedProgramming/Menus/*.java
	java -cp ./src/ com.AdvancedProgramming.MiniNet
clean: 
	rm -r src/com/AdvancedProgramming/*.class src/com/AdvancedProgramming/Users/*.class src/com/AdvancedProgramming/Menus/*.class
