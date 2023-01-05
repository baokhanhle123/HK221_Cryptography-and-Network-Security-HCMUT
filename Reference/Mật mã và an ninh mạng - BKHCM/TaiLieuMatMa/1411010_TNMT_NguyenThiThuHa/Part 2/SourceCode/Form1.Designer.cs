namespace S_DES
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rddecryp = new System.Windows.Forms.RadioButton();
            this.rdEncryp = new System.Windows.Forms.RadioButton();
            this.btnsDes = new System.Windows.Forms.Button();
            this.txtKey = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btnInput = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.openFileDialog1_FileOk);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.rddecryp);
            this.groupBox1.Controls.Add(this.rdEncryp);
            this.groupBox1.Location = new System.Drawing.Point(13, 13);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(259, 39);
            this.groupBox1.TabIndex = 1;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Mode";
            // 
            // rddecryp
            // 
            this.rddecryp.AutoSize = true;
            this.rddecryp.Location = new System.Drawing.Point(99, 16);
            this.rddecryp.Name = "rddecryp";
            this.rddecryp.Size = new System.Drawing.Size(76, 17);
            this.rddecryp.TabIndex = 1;
            this.rddecryp.TabStop = true;
            this.rddecryp.Text = "Decryption";
            this.rddecryp.UseVisualStyleBackColor = true;
            this.rddecryp.CheckedChanged += new System.EventHandler(this.rddecryp_CheckedChanged);
            // 
            // rdEncryp
            // 
            this.rdEncryp.AutoSize = true;
            this.rdEncryp.Location = new System.Drawing.Point(7, 16);
            this.rdEncryp.Name = "rdEncryp";
            this.rdEncryp.Size = new System.Drawing.Size(75, 17);
            this.rdEncryp.TabIndex = 0;
            this.rdEncryp.TabStop = true;
            this.rdEncryp.Text = "Encryption";
            this.rdEncryp.UseVisualStyleBackColor = true;
            this.rdEncryp.CheckedChanged += new System.EventHandler(this.rdEncryp_CheckedChanged);
            // 
            // btnsDes
            // 
            this.btnsDes.Location = new System.Drawing.Point(12, 82);
            this.btnsDes.Name = "btnsDes";
            this.btnsDes.Size = new System.Drawing.Size(75, 23);
            this.btnsDes.TabIndex = 2;
            this.btnsDes.Text = "Encryption";
            this.btnsDes.UseVisualStyleBackColor = true;
            this.btnsDes.Click += new System.EventHandler(this.btnsDes_Click);
            // 
            // txtKey
            // 
            this.txtKey.Location = new System.Drawing.Point(41, 59);
            this.txtKey.Name = "txtKey";
            this.txtKey.Size = new System.Drawing.Size(147, 20);
            this.txtKey.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(10, 62);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(25, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "Key";
            // 
            // btnInput
            // 
            this.btnInput.Location = new System.Drawing.Point(197, 56);
            this.btnInput.Name = "btnInput";
            this.btnInput.Size = new System.Drawing.Size(75, 23);
            this.btnInput.TabIndex = 5;
            this.btnInput.Text = "File Input";
            this.btnInput.UseVisualStyleBackColor = true;
            this.btnInput.Click += new System.EventHandler(this.btnInput_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 120);
            this.Controls.Add(this.btnInput);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtKey);
            this.Controls.Add(this.btnsDes);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "S-DES";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton rddecryp;
        private System.Windows.Forms.RadioButton rdEncryp;
        private System.Windows.Forms.Button btnsDes;
        private System.Windows.Forms.TextBox txtKey;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnInput;
    }
}

