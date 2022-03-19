/*File Name: ProjectOneSource.cpp
**Developer: Daniel Adler
**Date: September 15th, 2018
**Function: This program simulates an online store transaction through if statements and user input.
*/

#include <iostream>
#include <string>
#include <iomanip>
using namespace std;

int main() {
	double productCost;

	string productChoice;

	int quantity;

	double armCost, legCost, headCost, torsoCost, laserCost;

	string shippingMethod;

	int currentDay, shippingDay;

	bool followingMonth = false;

	double rateFactor;

	double packageWeight;

	bool oversizedPackageCharge;

	double shippingCost;

	double packageVolume;

	double packageVolumePlaceHolder;

	double tax;
	
	//sets individual parts to random costs
	armCost = (double)(rand() % 200 + 400);
	legCost = (double)(rand() % 200 + 700);
	headCost = (double)(rand() % 200 + 1500);
	torsoCost = (double)(rand() % 200 + 1800);
	laserCost = (double)(rand() % 200 + 1300);

	//Prompts user on what they would like to buy
	cout << "Welcome to the Interchangeable Cyborg Parts Virtual Store!" << endl << endl;
	cout << "What would you like to buy today?" << endl;
	cout << "Arms" << endl;
	cout << "Legs" << endl;
	cout << "Heads" << endl;
	cout << "Torsos" << endl;
	cout << "Lasers" << endl;
	cin >> productChoice;

	//Sets productCost to the cost of whichever product the user picks
	if (productChoice == "Arms" || productChoice == "arms")
		productCost = armCost;
	else if (productChoice == "Legs" || productChoice == "legs")
		productCost = legCost;
	else if (productChoice == "Heads" || productChoice == "heads")
		productCost = headCost;
	else if (productChoice == "Torsos" || productChoice == "torsos")
		productCost = torsoCost;
	else if (productChoice == "Lasers" || productChoice == "lasers")
		productCost = laserCost;
	else {
		cout << "Error: Invalid Input." << endl;
		system("PAUSE");
	}

	//Prompts user on item quantity
	cout << "How many of those would you like to purchase?" << endl;
	cin >> quantity;
	if (quantity > 0) {
		
		//Prompts user for current day
		cout << "What day of the month is it?" << endl;
		cin >> currentDay;

		//Prompts user for package weight
		cout << "How many pounds does your package weigh?" << endl;
		cin >> packageWeight;
		
		//Prompts user on shipping method and makes appropriate adjustments.
		cout << "Excellent. What would you like your shipping method to be?" << endl;
		cout << "UPS" << endl;
		cout << "USPS" << endl;
		cout << "FedEx" << endl;
		cin >> shippingMethod;

		//Makes appropriate values if the user picks UPS for shipping
		if (shippingMethod == "UPS") {
			shippingDay = currentDay + 7;
			rateFactor = 1.65;
			if (shippingDay > 30) {
				followingMonth = true;
				shippingDay = shippingDay - 30;
			}
		}

		//Makes appropriate values if the user picks USPS for shipping
		else if (shippingMethod == "USPS") {
			shippingDay = currentDay + 10;
			rateFactor = 1.55;
			if (shippingDay > 30) {
				followingMonth = true;
				shippingDay = shippingDay - 30;
			}
		}

		//Makes appropriate values if the user picks FedEx for shipping
		else if (shippingMethod == "FedEx") {
			shippingDay = currentDay + 4;
			rateFactor = 1.72;
			if (shippingDay > 30) {
				followingMonth = true;
				shippingDay = shippingDay - 30;
			}
		}
		
		//Checks whether or not the package arrives next month and informs the user of the arrival date
		if (followingMonth)
			cout << "Your package will arrive on day " << shippingDay << " of the following month." << endl;
		else
			cout << "Your package will arrive on day " << shippingDay << " of this month." << endl;

		//Uses packageWeight in order to calculate the shipping cost
		if (packageWeight > 50)
			shippingCost = rateFactor * packageWeight + 15;
		else
			shippingCost = rateFactor * packageWeight;

		//Prompts the users on the dimensions of the package in order to calculate the volume
		cout << "What is the width of your package?" << endl;
		cin >> packageVolume;
		cout << "What is the length of your package?" << endl;
		cin >> packageVolumePlaceHolder;
		packageVolume = packageVolume * packageVolumePlaceHolder;
		cout << "What is the height of your package?" << endl;
		cin >> packageVolumePlaceHolder;
		packageVolume = packageVolume * packageVolumePlaceHolder;
		
		//Calculates additional shipping costs based on volume of package
		packageVolumePlaceHolder = packageVolume;
		while (packageVolumePlaceHolder > 1000) {
			packageVolumePlaceHolder -= 1000;
			shippingCost++;
		}

		//sets the tax value based on appropriate modifiers
		tax = .0875*(productCost + shippingCost);

		//prints the recipt for the user
		cout << "***************************************************************************" << endl;
		cout << "Subtotal: $" << fixed << setprecision(2) << (quantity*productCost) << endl;
		cout << "Shipping Cost: $" << shippingCost << endl;
		cout << "Tax: $" << tax << endl;
		cout << "Grand Total: $" << (quantity * productCost) + shippingCost + tax << endl;
		cout << "***************************************************************************" << endl;
	}
	
	//occurs if quantity does not contain a valid numeric value
	else {
		cout << "Error: Invalid Input." << endl;
		system("PAUSE");
	}

	system("PAUSE");
}