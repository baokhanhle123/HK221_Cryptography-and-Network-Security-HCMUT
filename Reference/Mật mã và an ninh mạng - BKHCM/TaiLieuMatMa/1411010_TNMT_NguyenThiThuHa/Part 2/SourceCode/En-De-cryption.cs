using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace S_DES
{
    public class En_De_cryption
    {
        private static int[] K1 = new int[8];
        private static int[] K2 = new int[8];
        private static int[] pt = new int[8];

        static void SaveParameters(String plaintext, int[] k1, int[] k2)
        {
            int[] ptt = new int[8];

            char c1;
            string ts;
            try
            {
                for (int i = 0; i < 8; i++)
                {
                    c1 = plaintext[i];
                    ts = c1.ToString();
                    ptt[i] = Convert.ToInt32(ts);
                    if (ptt[i] != 0 && ptt[i] != 1)
                    {
                        return;
                    }

                }
            }
            catch (Exception e)
            {
                return;
            }
            pt = ptt;
            K1 = k1;
            K2 = k2;
        }
        static void InitialPermutation()
        {
            int[] temp = new int[8];
            temp[0] = pt[1];
            temp[1] = pt[5];
            temp[2] = pt[2];
            temp[3] = pt[0];
            temp[4] = pt[3];
            temp[5] = pt[7];
            temp[6] = pt[4];
            temp[7] = pt[6];

            pt = temp;
        }

        static void InverseInitialPermutation()
        {
            int[] temp = new int[8];

            temp[0] = pt[3];
            temp[1] = pt[0];
            temp[2] = pt[2];
            temp[3] = pt[4];
            temp[4] = pt[6];
            temp[5] = pt[1];
            temp[6] = pt[7];
            temp[7] = pt[5];

            pt = temp;
        }
        static int[] mappingF(int[] R, int[] SK)
        {
            int[] temp = new int[8];

            // EXPANSION/PERMUTATION [4 1 2 3 2 3 4 1] 
            temp[0] = R[3];
            temp[1] = R[0];
            temp[2] = R[1];
            temp[3] = R[2];
            temp[4] = R[1];
            temp[5] = R[2];
            temp[6] = R[3];
            temp[7] = R[0];
            temp[0] = temp[0] ^ SK[0];
            temp[1] = temp[1] ^ SK[1];
            temp[2] = temp[2] ^ SK[2];
            temp[3] = temp[3] ^ SK[3];
            temp[4] = temp[4] ^ SK[4];
            temp[5] = temp[5] ^ SK[5];
            temp[6] = temp[6] ^ SK[6];
            temp[7] = temp[7] ^ SK[7];
            int[,] S0 = new int[4, 4] { { 1, 0, 3, 2 }, { 3, 2, 1, 0 }, { 0, 2, 1, 3 }, { 3, 1, 3, 2 } };
            int[,] S1 = new int[4, 4] { { 0, 1, 2, 3 }, { 2, 0, 1, 3 }, { 3, 0, 1, 0 }, { 2, 1, 0, 3 } };


            int d11 = temp[0]; // first bit of first half 
            int d14 = temp[3]; // fourth bit of first half
            int[] a = new int[2] { d11, d14 };
            int row1 = BinAndDec.BinToDec(a); // for input in s-box S0


            int d12 = temp[1]; // second bit of first half
            int d13 = temp[2]; // third bit of first half  
            int[] a1 = new int[2] { d12, d13 };
            int col1 = BinAndDec.BinToDec(a1); // for input in s-box S0


            int o1 = S0[row1, col1];

            int[] out1 = BinAndDec.DecToBinArr(o1);

            int d21 = temp[4]; // first bit of second half
            int d24 = temp[7]; // fourth bit of second half
            int[] a3 = new int[2] { d21, d24 };
            int row2 = BinAndDec.BinToDec(a3);

            int d22 = temp[5]; // second bit of second half
            int d23 = temp[6]; // third bit of second half
            int[] a4 = new int[2] { d22, d23 };
            int col2 = BinAndDec.BinToDec(a4);

            int o2 = S1[row2, col2];

            int[] out2 = BinAndDec.DecToBinArr(o2);
            int[] out3 = new int[4];
            out3[0] = out1[0];
            out3[1] = out1[1];
            out3[2] = out2[0];
            out3[3] = out2[1];

            //permutation P4 [2 4 3 1]

            int[] O_Per = new int[4];
            O_Per[0] = out3[1];
            O_Per[1] = out3[3];
            O_Per[2] = out3[2];
            O_Per[3] = out3[0];

            return O_Per;
        }
        static int[] functionFk(int[] L, int[] R, int[] SK)
        {
            int[] temp = new int[4];
            int[] out1 = new int[8];


            temp = mappingF(R, SK);


            //XOR left half with output of mappingF 
            out1[0] = L[0] ^ temp[0];
            out1[1] = L[1] ^ temp[1];
            out1[2] = L[2] ^ temp[2];
            out1[3] = L[3] ^ temp[3];

            out1[4] = R[0];
            out1[5] = R[1];
            out1[6] = R[2];
            out1[7] = R[3];


            return out1;
        }
        static int[] switchSW(int[] in1)
        {

            int[] temp = new int[8];

            temp[0] = in1[4];
            temp[1] = in1[5];
            temp[2] = in1[6];
            temp[3] = in1[7];

            temp[4] = in1[0];
            temp[5] = in1[1];
            temp[6] = in1[2];
            temp[7] = in1[3];

            return temp;
        }
        public int[] encrypt(String plaintext, int[] LK, int[] RK)
        {
            SaveParameters(plaintext, LK, RK);
            InitialPermutation();
            int[] LH = new int[4];
            int[] RH = new int[4];
            LH[0] = pt[0];
            LH[1] = pt[1];
            LH[2] = pt[2];
            LH[3] = pt[3];


            RH[0] = pt[4];
            RH[1] = pt[5];
            RH[2] = pt[6];
            RH[3] = pt[7];

            int[] r1 = new int[8];
            r1 = functionFk(LH, RH, K1);
            int[] temp = new int[8];
            temp = switchSW(r1);
            LH[0] = temp[0];
            LH[1] = temp[1];
            LH[2] = temp[2];
            LH[3] = temp[3];

            RH[0] = temp[4];
            RH[1] = temp[5];
            RH[2] = temp[6];
            RH[3] = temp[7];
            int[] r2 = new int[8];
            r2 = functionFk(LH, RH, K2);

            pt = r2;
            InverseInitialPermutation();
            return pt;
        }
        public int[] decrypt(String plaintext, int[] LK, int[] RK)
        {
            SaveParameters(plaintext, LK, RK);
            //InitialPermutation();
            InverseInitialPermutation();
            int[] LH = new int[4];
            int[] RH = new int[4];
            LH[0] = pt[0];
            LH[1] = pt[1];
            LH[2] = pt[2];
            LH[3] = pt[3];


            RH[0] = pt[4];
            RH[1] = pt[5];
            RH[2] = pt[6];
            RH[3] = pt[7];

            int[] r1 = new int[8];
            r1 = functionFk(LH, RH, K1);
            int[] temp = new int[8];
            temp = switchSW(r1);
            LH[0] = temp[0];
            LH[1] = temp[1];
            LH[2] = temp[2];
            LH[3] = temp[3];

            RH[0] = temp[4];
            RH[1] = temp[5];
            RH[2] = temp[6];
            RH[3] = temp[7];
            int[] r2 = new int[8];
            r2 = functionFk(LH, RH, K2);

            pt = r2;
            //InverseInitialPermutation();
            InitialPermutation();
            return pt;
        }
        public static string binarraytostring(int[] bit)
        {
            int temp = BinAndDec.BinToDec(bit);                        
            return Convert.ToChar(temp).ToString();
        }
        public static string BitArrayToString(int[] input)
        {
            int length = input.Length;
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < length; x++)
            {
                sb.Append(Convert.ToBoolean(input[x]) ? 1 : 0);
            }
            return sb.ToString();
        }
    }
}
