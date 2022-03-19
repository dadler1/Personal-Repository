/*






*/

#include <iostream>
#include <ctime>
using namespace std;


int main() {
	srand((unsigned)time(NULL));
	int heapOneValue = rand()%91+10;
	int heapTwoValue = rand()%91+10;
	int heapInput;
	bool error;
	bool heapOneContainsMarbles = true;
	bool heapTwoContainsMarbles = true;
	int heap;

	
	cout << "Welcome to Subtract-A-Square" << endl << endl;

	//Prompts the user on which pile they would like to select
	cout << "Would you like to take marbles out of pile one or two? (1/2)." << endl;
	cin >> heap;

	
		//Reprompts the user for a valid number if the user enters a value that is not 1 or 2
		while (!(heap == 1 || heap == 2)) {
			cout << "Please enter either '1' or '2' depending on which pile you would like to select." << endl;
			cin >> heap;
		}
		//Prompts the user on how many marbles they would like to remove from said pile
		cout << "Please enter how many marbles you would like to remove from heap " << heap << "." << endl;
		cin >> heapInput;
		do {
			error = false;
			if()

		} while (error)










	cout << "Heap One: " << heapOneValue << endl;
	cout << "Heap Two: " << heapTwoValue << endl;

	system("PAUSE");
}