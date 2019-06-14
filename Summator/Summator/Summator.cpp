#include <iostream>
#include <string>

using namespace std;

int main(int argc, char** argv)
{
    if (argc > 2)
    {
        int a = stoi(argv[1]);
        int b = stoi(argv[2]);
        cout << a + b << endl;
    }
    else
    {
        cout << 0 << endl;
    }
	return 0;
}
