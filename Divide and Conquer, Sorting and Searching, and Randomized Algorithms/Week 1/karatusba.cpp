#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <math.h>

using namespace std;

int length(int num);

long long karatsuba(long long num1, long long num2);

int main(void)
{
    long long x = 123456;
    long long y = 985433;

    long long result = karatsuba(x, y);

    cout << result << endl; // Output: 121932631112635776

    cout << x * y;
}

long long karatsuba(long long num1, long long num2)
{
    if (num1 < 10 || num2 < 10)
        return num1 * num2;

    long long n = max(length(num1), length(num2));

    long long half = n / 2;

    /* Partition of first number */
    long long a = num1 / pow(10, half);
    long long b = num1 % (int)pow(10, half);

    /* Partition of Second number*/
    long long c = num2 / pow(10, half);
    long long d = num2 % (int)pow(10, half);

    /* 3 Recusive Calls */
    long long ac = karatsuba(a, c);
    long long bd = karatsuba(b, d);
    long long bc_plus_ad = karatsuba((a + b), (c + d)) - (ac + bd);

    /*Calculating the expression*/
    return (long long)(ac * pow(10, 2 * half) + bc_plus_ad * pow(10, half) + bd);
}

int length(int num)
{
    int count = 0;

    while (num > 0)
    {
        count++;
        num /= 10;
    }

    return count;
}
