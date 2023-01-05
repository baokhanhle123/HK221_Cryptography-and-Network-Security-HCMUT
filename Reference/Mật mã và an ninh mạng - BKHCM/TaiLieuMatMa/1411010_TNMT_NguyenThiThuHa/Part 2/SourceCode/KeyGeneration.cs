using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace S_DES
{
    public class KeyGeneration
    {
        private static int[] key = new int[10];
        private static int[] k1 = new int[8];
        private static int[] k2 = new int[8];
        private static bool flag = false;

        private static void permutationP10()
        {
            int[] temp = new int[10];

            temp[0] = key[2];
            temp[1] = key[4];
            temp[2] = key[1];
            temp[3] = key[6];
            temp[4] = key[3];
            temp[5] = key[9];
            temp[6] = key[0];
            temp[7] = key[8];
            temp[8] = key[7];
            temp[9] = key[5];


            key = temp;

        }

        private static void leftshiftLS1()
        {
            int[] temp = new int[10];

            temp[0] = key[1];
            temp[1] = key[2];
            temp[2] = key[3];
            temp[3] = key[4];
            temp[4] = key[0];

            temp[5] = key[6];
            temp[6] = key[7];
            temp[7] = key[8];
            temp[8] = key[9];
            temp[9] = key[5];

            key = temp;

        }

        private static int[] permutationP8()
        {
            int[] temp = new int[8];

            temp[0] = key[5];
            temp[1] = key[2];
            temp[2] = key[6];
            temp[3] = key[3];
            temp[4] = key[7];
            temp[5] = key[4];
            temp[6] = key[9];
            temp[7] = key[8];

            return temp;

        }

        private static void leftshiftLS2()
        {
            int[] temp = new int[10];

            temp[0] = key[2];
            temp[1] = key[3];
            temp[2] = key[4];
            temp[3] = key[0];
            temp[4] = key[1];

            temp[5] = key[7];
            temp[6] = key[8];
            temp[7] = key[9];
            temp[8] = key[5];
            temp[9] = key[6];

            key = temp;

        }

        public int[] getK1()
        {
            if (!flag)
            {
                //Print.msg("\nError Occured: Keys are not generated yet ");
                return null;
            }
            return k1;
        }

        public int[] getK2()
        {
            if (!flag)
            {
                //Print.msg("\nError Occured: Keys are not generated yet ");
                return null;
            }
            return k2;
        }

        public void keyGeneration(string inputkey)
        {
            int[] key2 = new int[10];
            char c1;
            string ts;

            try
            {
                for (int i = 0; i < 10; i++)
                {
                    c1 = inputkey[i];
                    ts = c1.ToString();
                    key2[i] = Convert.ToInt32(ts);
                    if (key2[i] != 0 && key2[i] != 1)
                    {
                        return;
                    }
                }
            }
            catch (Exception e)
            {
                return;
            }

            key = key2;
            permutationP10();
            leftshiftLS1();
            k1 = permutationP8();
            leftshiftLS2();
            k2 = permutationP8();
            flag = true;

        }

        public KeyGeneration()
        {

        }
    }
}
