CXX_FLAGS=-Werror
OUTPUT_DIR=../../src/test/resources/Summator/Debug
all: $(OUTPUT_DIR)/Summator.out
$(OUTPUT_DIR)/Summator.o: Summator.cpp
	g++ -std=c++11 $(CXX_FLAGS) -c Summator.cpp -o $(OUTPUT_DIR)/Summator.o
$(OUTPUT_DIR)/Summator.out:  $(OUTPUT_DIR)/Summator.o
	g++ -std=c++11 $(CXX_FLAGS) $(OUTPUT_DIR)/Summator.o -o $(OUTPUT_DIR)/Summator.out
clean:
	rm -f main *.o
.PHONY: clean all