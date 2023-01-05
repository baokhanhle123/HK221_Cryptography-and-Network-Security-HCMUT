#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <ctime>
#include <conio.h>

#define SIZE 10000
using namespace std;

int char_to_int(char _ch){
	return (int)_ch;
}
char in_to_char(int num){
	return (char)num;
}
string decimal2binstr(int num)
{
	string ret = "";
	for (int i = 0; i < 8; i++)
	{
		if (num % 2 == 1)
			ret = "1" + ret;
		else
			ret = "0" + ret;
		num >>=1;
	}
	return ret;
}

//lenght = 4
string decimal2binstr_4(int num)
{
	string ret = "";
	for (int i = 0; i < 4; i++)
	{
		if (num % 2 == 1)
			ret = "1" + ret;
		else
			ret = "0" + ret;
		num >>= 1;
	}
	return ret;
}
string decimal2binstr_2(int num)
{
	string ret = "";
	for (int i = 0; i < 2; i++)
	{
		if (num % 2 == 1)
			ret = "1" + ret;
		else
			ret = "0" + ret;
		num >>= 1;
	}
	return ret;
}
int binstr2decimal(string binstr)
{
	int ret = 0;
	for (int i = 0; i < binstr.size(); i++)
	{
		ret <<= 1;
		if (binstr[i] == '1')
			ret++;
	}
	return ret;
}

string derive_key(){
	srand(time(0));
	int n;
	n = rand() % 9999999;
	return "0"+decimal2binstr(n)+"1";
}
string sconvert(const char *pCh, int arraySize){
	string str;
	if (pCh[arraySize - 1] == '\0') str.append(pCh);
	else for (int i = 0; i<arraySize; i++) str.append(1, pCh[i]);
	return str;
}
string circular_left_shift(string input_1, string input_2){
	char output_1[] = { input_1.at(1), input_1.at(2), input_1.at(3), input_1.at(4),input_1.at(0) };
	char output_2[] = { input_2.at(1), input_2.at(2), input_2.at(3), input_2.at(4), input_2.at(0) };
	string str_1 = sconvert(output_1, 5);
	string str_2 = sconvert(output_2, 5);
	return str_1 + str_2;
}


char Xor(char a, char b){
	if ((a == '0'&& b == '0') || (a == '1'&& b == '1'))
		return '0';
	return '1';
}

///
string P10(string input){
	char output[] = { input.at(2), input.at(4), input.at(1), input.at(6), input.at(3),
		input.at(9), input.at(0), input.at(8), input.at(7), input.at(5) };
	string str = sconvert(output, 10);
	return str;
}

string P8(string input){
	char output[] = { input.at(5), input.at(2), input.at(6), input.at(3), input.at(7),
		input.at(4), input.at(9), input.at(8) };
	string str = sconvert(output, 8);
	return str;
}

string P4(string input){
	char output[] = { input.at(1), input.at(3), input.at(2), input.at(0) };
	string str = sconvert(output, 4);
	return str;
}

string IP(string input){
	char output[] = { input.at(1), input.at(5), input.at(2), input.at(0), input.at(3),
		input.at(7), input.at(4), input.at(6) };
	string str = sconvert(output, 8);
	return str;
}

string R_IP(string input){
	char output[] = { input.at(3), input.at(0), input.at(2), input.at(4), input.at(6),
		input.at(1), input.at(7), input.at(5) };
	string str = sconvert(output, 8);
	return str;
}

string EP(string input_1, string input_2){
	char output_1[] = { input_1.at(3), input_1.at(0), input_1.at(1), input_1.at(2) };
	char output_2[] = { input_2.at(1), input_2.at(2), input_2.at(3), input_2.at(0) };
	string str_1 = sconvert(output_1, 4);
	string str_2 = sconvert(output_2, 4);
	return str_1 + str_2;
}

string Ex_Xor(string input_1, string input_2){
	char output[8];
	for (int i = 0; i < 8; i++){
		output[i] = Xor(input_1.at(i), input_2.at(i));
	}
	string str = sconvert(output, 8);
	return str;
}
string Ex_Xor_4(string input_1, string input_2){
	char output[4];
	for (int i = 0; i < 4; i++){
		output[i] = Xor(input_1.at(i), input_2.at(i));
	}
	string str = sconvert(output, 4);
	return str;
}
string S_Boxs(string input_1, string input_2){
	int S_Box_0[4][4] = { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3, 1, 3, 2 } };
	int	S_Box_1[4][4] = { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2, 1, 0, 3 } };
	//Box_0
	char output_1[] = { input_1.at(0), input_1.at(3) };
	string str_1 = sconvert(output_1, 2);
	char output_2[] = { input_1.at(1), input_1.at(2) };
	string str_2 = sconvert(output_2, 2);
	string a = decimal2binstr_2(S_Box_0[binstr2decimal(str_1)][binstr2decimal(str_2)]);
	//Box_1
	char output_3[] = { input_2.at(0), input_2.at(3) };
	string str_3 = sconvert(output_3, 2);
	char output_4[] = { input_2.at(1), input_2.at(2) };
	string str_4 = sconvert(output_4, 2);
	string b = decimal2binstr_2(S_Box_1[binstr2decimal(str_3)][binstr2decimal(str_4)]);

	return P4(a+b);
}

string Swith(string input){
	char output[] = { input.at(4), input.at(5), input.at(6), input.at(7), input.at(0),
					input.at(1), input.at(2), input.at(3) };
	string str = sconvert(output, 8);
	return str;
}
///
///FK
string FK(string input, string key){
	char output_1[] = { input.at(0), input.at(1), input.at(2), input.at(3) };
	char output_2[] = { input.at(4), input.at(5), input.at(6), input.at(7) };
	string left = sconvert(output_1, 4);
	string right = sconvert(output_2, 4);
	string xor = Ex_Xor(EP(right,right), key);
	char output_3[] = { xor.at(0), xor.at(1), xor.at(2), xor.at(3) };
	char output_4[] = { xor.at(4), xor.at(5), xor.at(6), xor.at(7) };

	string box = S_Boxs(sconvert(output_3, 4), sconvert(output_4, 4));

	string xor_1 = Ex_Xor_4(left, box) + right;

	return xor_1;
}
//
void encrypt(string key){
	//key_1
	string key_1,key_2;
	string key_10 = P10(key);
	string l_key, r_key, l_key_2, r_key_2;
	char output_1[] = { key_10.at(0), key_10.at(1), key_10.at(2), key_10.at(3), key_10.at(4) };
	char output_2[] = { key_10.at(5), key_10.at(6), key_10.at(7), key_10.at(8), key_10.at(9) };
	l_key = sconvert(output_1, 5);
	r_key = sconvert(output_2, 5);
	string LS_1 = circular_left_shift(l_key, r_key);
	key_1 = P8(LS_1);
	//key_2
	char output_3[] = { LS_1.at(0), LS_1.at(1), LS_1.at(2), LS_1.at(3), LS_1.at(4) };
	char output_4[] = { LS_1.at(5), LS_1.at(6), LS_1.at(7), LS_1.at(8), LS_1.at(9) };
	l_key_2 = sconvert(output_3, 5);
	r_key_2 = sconvert(output_4, 5);
	string LS_2 = circular_left_shift(l_key_2, r_key_2);
	key_2 = P8(LS_2);
	//
	ifstream input("plaintext.txt");
	ofstream output("result_ciphertext.txt");
	string plaintext;
	stringstream ss;
	ss << input.rdbuf();
	plaintext = ss.str();
	int arr[SIZE];
	for (unsigned int i = 0; i < plaintext.size(); i++){
		arr[i] = char_to_int(plaintext.at(i));
	}
	string plain[SIZE];
	for (unsigned int i = 0; i < plaintext.size(); i++){
		plain[i] = Swith(FK(IP(decimal2binstr(arr[i])), key_1));
	}
	string cipher[SIZE];
	for (unsigned int i = 0; i < plaintext.size(); i++){
		cipher[i] = R_IP(FK(plain[i],key_2));
	}
	for (unsigned int i = 0; i < plaintext.size(); i++){
		output << cipher[i];
	}
}

void decrypt(string key){
	//key_1
	string key_1, key_2;
	string key_10 = P10(key);
	string l_key, r_key, l_key_2, r_key_2;
	char output_1[] = { key_10.at(0), key_10.at(1), key_10.at(2), key_10.at(3), key_10.at(4) };
	char output_2[] = { key_10.at(5), key_10.at(6), key_10.at(7), key_10.at(8), key_10.at(9) };
	l_key = sconvert(output_1, 5);
	r_key = sconvert(output_2, 5);
	string LS_1 = circular_left_shift(l_key, r_key);
	key_1 = P8(LS_1);
	//key_2
	char output_3[] = { LS_1.at(0), LS_1.at(1), LS_1.at(2), LS_1.at(3), LS_1.at(4) };
	char output_4[] = { LS_1.at(5), LS_1.at(6), LS_1.at(7), LS_1.at(8), LS_1.at(9) };
	l_key_2 = sconvert(output_3, 5);
	r_key_2 = sconvert(output_4, 5);
	string LS_2 = circular_left_shift(l_key_2, r_key_2);
	key_2 = P8(LS_2);
	//
	ifstream input("result_ciphertext.txt");
	ofstream output("plaintext_1.txt");
	string plaintext;
	stringstream ss;
	ss << input.rdbuf();
	plaintext = ss.str();
	string arr[SIZE];
	int n = 0;
	for (unsigned int i = 0; i < plaintext.size();i++){
		arr[i] = plaintext.at(i);
	}
	string ciphertext[SIZE];
	for (unsigned int i = 0; i < plaintext.size();){
		ciphertext[n] = arr[i] + arr[i + 1] + arr[i + 2] + arr[i + 3] + arr[i + 4] +
			arr[i + 5] + arr[i + 6] + arr[i + 7];
		i += 8;
		n++;
	}
	int m = 0;
	for (unsigned int i = 0; i < n; i++){

		string a = ciphertext[i];
		output << in_to_char(binstr2decimal(R_IP(FK(Swith(FK(IP(a), key_2)), key_1))));
	}
}
void main(){
	string key;
	int n;
	cout << "Nhan 1 de sinh key tu dong, 2 de nhap key : ";
	cin >> n;
	if (n == 1){
		key = derive_key();
		cout << "Key : " << key << endl;
		encrypt(key);
		decrypt(key);
	}
	else {
		cout << "Nhap key voi cac chu so '0', '1' chieu dai 10bit : ";
		cin >> key;
		encrypt(key);
		decrypt(key);
	}
	system("pause");
}