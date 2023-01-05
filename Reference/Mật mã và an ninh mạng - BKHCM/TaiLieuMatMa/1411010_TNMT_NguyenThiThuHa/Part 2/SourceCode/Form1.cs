using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace S_DES
{
    public partial class Form1 : Form
    {
        public string text = "";

        public Form1()
        {
            InitializeComponent();
            btnInput.Visible = false;
            btnsDes.Visible = false;
        }


        
        private void rdEncryp_CheckedChanged(object sender, EventArgs e)
        {
            btnInput.Visible = true;
            btnsDes.Visible = true;
            btnsDes.Text = "Encryption";
        }

        private void rddecryp_CheckedChanged(object sender, EventArgs e)
        {
            btnInput.Visible = true;
            btnsDes.Visible = true;
            btnsDes.Text = "Decryption";
        }

        private void btnsDes_Click(object sender, EventArgs e)
        {
            if (txtKey.Text == "")
            {
                MessageBox.Show("Please enter Key", "Error");
            }
            else
                if (text == "")
                {
                    MessageBox.Show("File input empty, please enter text into file input", "Error");
                }
            else
            {
                KeyGeneration Key = new KeyGeneration();
                En_De_cryption cryption = new En_De_cryption();
                
                Key.keyGeneration(txtKey.Text);
                string plaintext = "";
                if (btnsDes.Text == "Encryption")
                {
                    
                    foreach (char c in text)
                    {
                        string block_string = Convert.ToString(c, 2).PadLeft(8, '0');
                        int[] temp = cryption.encrypt(block_string, Key.getK1(), Key.getK2());
                        plaintext += En_De_cryption.BitArrayToString(temp);
                    }

                    using (StreamWriter sw = new StreamWriter("./result_ciphertext.txt"))
                    {

                        foreach (char s in plaintext)
                        {
                            sw.Write(s);
                        }
                    }

                    MessageBox.Show("Successful", "Notification");
                }
                else if (btnsDes.Text == "Decryption")
                {
                    //text = File.ReadAllText("./result_ciphertext.txt");
                    string[] cipher = new string[1000];
                    int n = 0;
                    for (int i = 0; i < text.Length; )
                    {
                        cipher[n] = text[i].ToString() + text[i + 1].ToString() + text[i + 2].ToString() + text[i + 3].ToString() + text[i + 4].ToString() + text[i + 5].ToString() + text[i + 6].ToString() + text[i + 7].ToString();
                        n++;
                        i += 8;
                    }
                    foreach (string c in cipher)
                    {
                        if (c == null) break;
                        int[] temp = cryption.encrypt(c, Key.getK2(), Key.getK1());
                        plaintext += En_De_cryption.binarraytostring(temp);
                    }
                    using (StreamWriter sw = new StreamWriter("./plaintext.txt"))
                    {

                        foreach (char s in plaintext)
                        {
                            sw.Write(s);
                        }
                    }

                    MessageBox.Show("Successful", "Notification");
                }
            }
        }

        private void openFileDialog1_FileOk(object sender, CancelEventArgs e)
        {
            
        }

        private void btnInput_Click(object sender, EventArgs e)
        {
            Stream myStream = null;
            OpenFileDialog openFileDialog1 = new OpenFileDialog();

            openFileDialog1.InitialDirectory = "./";
            openFileDialog1.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*";
            openFileDialog1.FilterIndex = 2;
            openFileDialog1.RestoreDirectory = true;

            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    if ((myStream = openFileDialog1.OpenFile()) != null)
                    {
                        using (myStream)
                        {
                            // Insert code to read the stream here.
                            text = File.ReadAllText(openFileDialog1.FileName);
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error: Could not read file from disk. Original error: " + ex.Message);
                }
            }
        }
    }
}
