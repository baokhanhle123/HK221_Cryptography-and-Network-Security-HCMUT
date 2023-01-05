using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace S_DES
{
    public class BinAndDec
    {
        public static int[] DecToBinArr(int no)
        {
            // 13 1
            // 6  0
            // 3  1
            // 1  1
            // 0  


            if (no == 0)
            {
                int[] zero = new int[2];
                zero[0] = 0;
                zero[1] = 0;
                return zero;
            }
            int[] temp = new int[10];


            int count = 0;
            for (int i = 0; no != 0; i++)
            {
                temp[i] = no % 2;
                no = no / 2;
                count++;
            }


            int[] temp2 = new int[count];


            for (int i = count - 1, j = 0; i >= 0 && j < count; i--, j++)
            {
                temp2[j] = temp[i];
            }

            //because we requires 2-bits as output .. so for adding leading 0
            if (count < 2)
            {
                temp = new int[2];
                temp[0] = 0;
                temp[1] = temp2[0];
                return temp;
            }

            return temp2;
        }

        public static int BinToDec(int[] bits)
        {
            int temp = 0;
            int base1 = 1;
            for (int i = bits.Length - 1; i >= 0; i--)
            {
                temp = temp + (bits[i] * base1);
                base1 = base1 * 2;
            }

            return temp;
        }
    }
}
