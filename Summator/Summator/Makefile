CXX_FLAGS=-Werror
OUTPUT_DIR=../../src/test/resources/Summator/Debug
all: $(OUTPUT_DIR)/Summator.out
$(OUTPUT_DIR)/Summator.o: Summator.cpp
	g++ $(CXX_FLAGS) -c Summator.cpp -o $(OUTPUT_DIR)/Summator.o
$(OUTPUT_DIR)/Summator.out:  $(OUTPUT_DIR)/Summator.o
	g++ $(CXX_FLAGS) $(OUTPUT_DIR)/Summator.o -o $(OUTPUT_DIR)/Summator.out
clean:
	rm -f main *.o
.PHONY: clean all