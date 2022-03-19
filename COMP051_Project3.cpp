/******************************************************************************************************************************************************************
	* File Name:   Project3.cpp
	* Author:      Daniel Adler
	* Date:        10/15/18
	* Description: This program provides a variety of specific calculations based on user input
******************************************************************************************************************************************************************/

#include <iostream>
using namespace std;


double FtoC(double farenheit);
double CtoF(double celsius);
int Factorial(int number);
void Pythagorean(double &a, double &b, double &c, char &missing, bool &error);
double SquareRoot(double number);
int Fibonacci(int length);
double Power(double base, int exponent);
void Test();

int main() {
	int choice;                      // Represents which function the user wants to use
	double temperature = 0.0;        // Represents user-inputted temperature
	int factorialNum = 0;            // Represents user-inputted number for Factorial function
	char missingSide;                // Represents which side of the triangle is missing
	double sideA, sideB, sideC;      // Represents the lengths of 3 sides of a right triangle

	double squareRootNum = 0;        // Represents user-inputted number for SquareRoot function
	int fiblength;                   // Represents the length the user would like the sum to contain
	double powerBase;                // Represents the user-inputted base of the exponent used in the Power function
	int powerExponent;               // Represents the user-inputted exponent used in the Power function
	do {
		cout << "Please select from the menu:" << endl;
		cout << "1. Farenheit to Celsius conversion" << endl;
		cout << "2. Celsius to Farenheit conversion" << endl;
		cout << "3. Factorial Calculator" << endl;
		cout << "4. Pythagorean Theorum application" << endl;
		cout << "5. Square Root calculator" << endl;
		cout << "6. Fibonacci Number calculator" << endl;
		cout << "7. Power calculator" << endl;
		cout << "8. Test" << endl;
		cout << "9. Exit" << endl;
		cin >> choice;

		switch (choice) {
		case 1:
			// Farenheit to Celsius Conversion
			cout << "Please enter the Farenheit temperature." << endl;
			cin >> temperature;
			if (FtoC(temperature) == -1000)
				cout << "Error: Temperature is below absolute zero." << endl;
			else
				cout << temperature << " degrees Farenheit is equal to " << FtoC(temperature) << " degrees Celsius." << endl;
			break;
		case 2:
			// Celsius to Farenheit conversion
			cout << "Please enter the Celsius temperature." << endl;
			cin >> temperature;
			if (CtoF(temperature) == -1000)
				cout << "Error: Temperature is below absolute zero." << endl;
			else
				cout << temperature << " degrees Celsius is equal to " << CtoF(temperature) << " degrees Farenheit." << endl;
			break;
		case 3:
			// Factorial Calculator
			cout << "Please enter a positive number." << endl;
			cin >> factorialNum;
			while (factorialNum <= 0) {
				cout << "Invalid input. Please enter a positive integer." << endl;
				cin >> factorialNum;
			}
			cout << "The factorial of " << factorialNum << " is " << Factorial(factorialNum) << "." << endl;
			break;
		case 4:
			// Pythagorean Theorum application
			bool pError; // True if there was an error whilst running the Pythagorean function
			cout << "Enter the length of 2 sides of a right triangle and enter '0' for the missing side." << endl;
			cin >> sideA >> sideB >> sideC;
			Pythagorean(sideA, sideB, sideC, missingSide, pError);
			if (!pError) {
				if (missingSide == 'a') {
					cout << "The missing value is: " << sideA << "." << endl;
				}
				else if (missingSide == 'b') {
					cout << "The missing value is: " << sideB << "." << endl;
				}
				else if (missingSide == 'c') {
					cout << "The missing value is: " << sideC << "." << endl;
				}
			}
			break;
		case 5:
			// Square Root calculator
			cout << "Please enter a positive number." << endl;
			cin >> squareRootNum;
			if (SquareRoot(squareRootNum) == -1) {
				cout << "Error: Invalid value provided." << endl;
			}
			else
				cout << "The square root of " << squareRootNum << " is " << SquareRoot(squareRootNum) << "." << endl;
			break;
		case 6:
			// Fibonacci Number calculator
			cout << "Please enter the length of your fibonacci sum." << endl;
			cin >> fiblength;
			if (Fibonacci(fiblength) == -1) {
				cout << "Error: Invalid Range." << endl;
			}
			else
				cout << "The fibonacci sum of length " << fiblength << " is " << Fibonacci(fiblength) << endl;
			break;
		case 7:
			// Power calculator
			cout << "Please enter the base of the exponent you would like to calculate." << endl;
			cin >> powerBase;
			cout << "Please enter the exponential value of the exponent you would like to calculate." << endl;
			cin >> powerExponent;
			cout << powerBase << " to the power of " << powerExponent << " is " << Power(powerBase, powerExponent) << endl;
			break;
		case 8:
			// Tests all cases
			Test();
			break;
		case 9:
			//Exits program
			break;
		default:
			// Invalid Input
			cout << "Error: Invalid input. Please enter an integer between 1 and 7." << endl;
			break;
		}
	} while (choice != 9);
	system("PAUSE");
}
/***************************************************************************************************************
	* Function:    FtoC
	* Parameters:  farenheit
	* Description: converts a value from farenheit to celsius
***************************************************************************************************************/
double FtoC(double farenheit) {
	if (farenheit < CtoF(-273.15))
		return -1000;
	else
		return ((farenheit - 32.00) * (5.0 / 9.0));
}

/***************************************************************************************************************
	* Function:    CtoF
	* Parameters:  celsius
	* Description: converts a value from celsius to farenheit
***************************************************************************************************************/
double CtoF(double celsius) {
	if (celsius < -273.15)
		return -1000;
	else
		return (celsius * (9.0 / 5.0) + 32);

}

/***************************************************************************************************************
	* Function:    Factorial
	* Parameters:  number
	* Description: calculates the factorial of a given value
***************************************************************************************************************/
int Factorial(int number) {
	int factorial = number;
	for (int i = number - 1; i > 0; i--) {
		factorial *= i;
	}
	return factorial;
}

/***************************************************************************************************************
	* Function:    Pythagorean Theorum
	* Parameters:  a, b, c
	* Description: calculates the length of a side of a right triangle given the other two sides
***************************************************************************************************************/
void Pythagorean(double &a, double &b, double &c, char &missing, bool &error) {
	if (a >= 0 && b >= 0 && c >= 0) {
		if (a == 0) {
			missing = 'a';
			if (b >= c) {
				cout << "Error: Hypotenuse is too small." << endl;
				error = true;
			}
			else {
				if (b == 0 || c == 0) {
					cout << "Error: Invalid parameters." << endl;
					error = true;
				}
				else {
					a = sqrt((c * c) - (b * b));
					error = false;
				}
			}
		}
		else if (b == 0) {
			missing = 'b';
			if (a >= c) {
				cout << "Error: Hypotenuse is too small." << endl;
				error = true;
			}
			else {
				if (a == 0 || c == 0) {
					cout << "Error: Invalid parameters." << endl;
					error = true;
				}
				else {
					b = sqrt((c * c) - (a * a));
					error = false;
				}
			}
		}
		else if (c == 0) {
			missing = 'c';
			if (a == 0 || b == 0) {
				cout << "Error: Invalid parameters." << endl;
				error = true;
			}
			else {
				c = sqrt((a * a) + (b * b));
				error = false;
			}
		}
		else {
			cout << "Error: None of your parameters are 0." << endl;
			error = true;
		}
	}
	else {
		cout << "Error: One of the parameters is less than zero." << endl;
		error = true;
	}
}

/***************************************************************************************************************
	* Function:    SquareRoot
	* Parameters:  number
	* Description: calculates the square root of a given value
***************************************************************************************************************/
double SquareRoot(double number) {
	if (number <= 0)
		return -1;
	else {
		double lowerBound = 0;
		double upperBound = number;
		double root;
		double square;
		int count = 0;
		do {
			root = (lowerBound + upperBound) / 2.0;
			square = root * root;
			if (square > number)
				upperBound = root;
			else if (square < number)
				lowerBound = root;
		} while (square != number && count++ != 60);
		return root;
	}
}

/***************************************************************************************************************
	* Function:    Fibonacci
	* Parameters:  num1, num2, length
	* Description: prints a fibonacci series of length [length] with F(1) and F(2) being [num1] and [num2]
***************************************************************************************************************/
int Fibonacci(int length) {
	int secondLastNumber = 0;
	int lastNumber = 1;
	int currentNumber;
	if (length < 0) {
		return -1;
	}
	else if (length == 0) {
		return 0;
	}
	else {
		for (int i = 2; i <= length; i++) {
			currentNumber = secondLastNumber + lastNumber;
			secondLastNumber = lastNumber;
			lastNumber = currentNumber;
		}
		return lastNumber;
	}

	

}

/***************************************************************************************************************
	* Function:    Power
	* Parameters:  base, exponent
	* Description: calculates a power function based on [base] and [exponent]
***************************************************************************************************************/
double Power(double base, int exponent) {
	double result = base;
	if (exponent > 1) {
		for (int i = 1; i < exponent; i++) {
			result *= base;
		}
		return result;
	}
	else if (exponent == 1) {
		return result;
	}
	else if (exponent < 0) {
		int exponentTemp = exponent * -1;
		for (int i = 1; i < exponentTemp; i++) {
			result *= base;
		}
		return (1.0 / result);
	}
	else {
		return 1;
	}
}

/***************************************************************************************************************
	* Function:	   Test
	* Parameters:  none
	* Description: tests the outputs of each function
***************************************************************************************************************/
void Test() {
	//FtoC
	cout << "Testing Farenheit to Celsius" << endl;
	cout << FtoC(-555) << endl; //This is an error case that will return -1000, indicating error, because the temperature is below absolute 0
	cout << FtoC(50) << endl; //This is should return 10 degrees celsius
	//CtoF
	cout << "Testing Farenheit to Celsius" << endl;
	cout << CtoF(-555) << endl; //This is an error case that will return -1000, indicating error, because the temperature is below absolute 0
	cout << CtoF(10) << endl; //This is should return 50 degrees farenheit
	//Pythagorean Theorum
	char missing;
	bool error;
	cout << "Testing Pythagorean Theorum" << endl;
	Pythagorean(0, 0, 1, missing, error);
	cout << error << endl; //error = true because more than two zeroes have been inputted
	Pythagorean(3, -4, 0, missing, error);
	cout << error << endl; //error = true because a negative number exists in the sequence
	Pythagorean(3, 4, 0, missing, error);
	cout << error << endl; //error = false because the input is valid
	//Square Root
	cout << "Testing Square Root" << endl;
	cout << SquareRoot(-5) << endl; //returns -1, indicating error, because a value less than or equal to zero was entered
	cout << SquareRoot(9) << endl;  //expected to return 3
	//Fibonacci
	cout << "Testing Fibonacci Sequence" << endl;
	cout << Fibonacci(-5) << endl; //returns -1, indicating error, because a negative value was inputted
	cout << Fibonacci(3) << endl;  //expected to return 2
	//Power
	cout << "Testing Power Function" << endl;
	cout << Power(5, 3) << endl; //expected to return 25
	cout << Power(4, -1) << endl; //expected to return 0.25
	cout << Power(5, 0) << endl; // expected to return 1
	cout << Power(4, 1) << endl; //expected to return 4
}