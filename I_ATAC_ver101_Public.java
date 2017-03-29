
	/********************************************************           
    * I-ATAC ver 101 -- A standalone software platform for		* 
    * the management and pre-processing of ATAC-seq 			*
    * samples.											   		*   
    *                                                      		*   
    * Authors:  Dr. Zeeshan AHMED  & Asst Prof. Dr.Duygu Ucar   *   
    * LAB:		Ucar Lab                                   		*
    * Organization:	The Jackson Laboratory, USA 				*   
    *                                                      		*   
    * Programming Language: Java                           		*
    * Date Code Submitted: 03-29-2017							*   
    ********************************************************/ 

package ATAC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import java.awt.SystemColor;

public class I_ATAC_ver101_Public extends JFrame {

	// File Information
		String str_FastQ_Zip_File_R1 = "";
		String str_FastQ_Zip_File_R2 = "";
		String str_FastQ_Zip_File_Path = "";
	
	// CLUSTER INFORMATION
		public static String str_Cluster_User_Name;
		public static String str_Cluster_User_Password;
		public static String str_Cluster_Host_Name = "data-cluster";
		public static int int_Cluster_Host_port = 22;
			
	// SSH STATEMENTS
		public static String str_ls = "ls -l ";
		public static String str_MakeDir = "mkdir ";
		public static String str_RemoveDir = "rmdir ";
		public static String str_RemoveFile = "rm ";
		public static String str_cd = "cd ";
		public static String str_cd_dotdot = "cd .. ";

		public static String str_Dir_Data = "/data";

		public static String str_New_Output_Dir = "/data/ATAC_PROJECTS";

		// File Types
		public static String str_Cluster_Dir_ProjectName = "PROJECT";
		public static String str_Cluster_Dir_Project_SampleName = "PROJECT_SAMPLE";
		public static String str_Cluster_Dir_fastQC = "fastQC";
		public static String str_Cluster_Dir_trimmomatic = "trimmomatic";
		public static String str_Cluster_Dir_trimmomatic_bwa = "bwa";
		public static String str_Cluster_Dir_trimmomatic_bwa_macs2 = "macs2";
		public static String str_Merged_Samples_Dir = "MergedSamples";
		public static String str_FileName_R1_filtered_trimmed = "_filtered_trimmed";
		public static String str_FileName_R1_unpaired = "_unpaired";
		public static String str_FileName_R1_filtered_trimmed_fastq = "_filtered_trimmed.fastq";
		public static String str_FileName_R2_filtered_trimmed = "_filtered_trimmed";
		public static String str_FileName_R2_unpaired = "_unpaired";
		public static String str_FileName_R2_filtered_trimmed_fastq = "_filtered_trimmed.fastq";
		public static String str_Extension_fastq  = ".fastq";
		public static String str_Extension_ftrimDOTfastq = ".trimU.fastq";
		public static String str_Extension_fastq_filtered = ".fastq_filtered";
		public static String str_Extension_sam = ".sam";
		public static String str_Extension_sorted_sam = "_sorted.sam";
		public static String str_Extension_bam = ".bam";
		public static String str_Extension_bed = ".bed";
		public static String str_Extension_txt = ".txt";
		public static String str_Extension_pdf = ".pdf";
		public static String str_MakeDir_Full_Command;
		public static String str_Reference_Genome = "/INDEXES/HUMAN/BWA/hg19.fa";
			
		// Software with Paths
		public static String str_SW_FastQC_Location = "/software/FastQC/fastqc";
		public static String str_SW_Trimmomatic_Location = "/software/Trimmomatic-0.32/trimmomatic-0.32.jar ";
		public static String str_Trailing = "TRAILING:" + "3";	// default value for now
		public static String str_SlidingWindow = "SLIDINGWINDOW:" + "4:15"; // default value for now
		public static String str_MinVal = "MINLEN:"+ "36"; // default value for now
		public static String str_Trimmomatic_TRAILING_SLIDINGWINDOW_MINLEN = str_Trailing + " "+ str_SlidingWindow + " " + str_MinVal;
		public static String str_Trimmomatic_ILLUMINACLIP_PE = " ILLUMINACLIP:/software/Trimmomatic-0.32/adapters/NexteraPE-PE.fa:2:30:10 ";
		public static String str_Trimmomatic_ILLUMINACLIP_SE = " ILLUMINACLIP:/software/Trimmomatic-0.32/adapters/SmartSeq_Adapter.fa:2:30:10 ";
		public static String str_SW_BWA_Location = "/software/bwa-0.7.15/bwa";
		public static String str_BWA_Output_File_Name = "";
		public static String str_BWA_Output_File_Name_with_Path = "";
		public static String str_SW_SortSam_Location = "/software/picard/1.95/SortSam.jar";
		public static String str_SW_MarkDuplicates_Location = "/software/picard/1.95/MarkDuplicates.jar";
		public static String str_SW_CollectInsertSizeMetrics_Location = "/software/picard/1.95/CollectInsertSizeMetrics.jar";
		public static String str_SW_ATAC_BAM_shifter_gappedAlign_Location = "/software/ATAC_BAM_shifter_gappedAlign/ATAC_BAM_shifter_gappedAlign.pl";
		public static String str_SW_samtools_Location = "samtools";
		public static String str_SW_samtools_Sort_Command = " sort";
		public static String str_SW_samtools_Index_Command = " index";
		public static String str_SW_samtools_View_Command = " view -h -o" ;
		public static String str_SW_samtools_Merge_Command =  " merge";
		public static String str_SW_bedtools_Location = "bedtools";
		public static String str_SW_bedtools_bamTObed_Command = " bamtobed";
		public static String str_SW_MACS2_Location = "macs2";   // New version is installed that is why directly running
		public static String str_FastQC_Command = "";
		public static String str_QC_One_Line_Command = "";
		public static String str_Trimmomatic_Command = "";
		public static String str_Trimmomatic_One_Line_Command= "";
		public static String str_BWA_Command = "";
		public static String str_RUN_ATAC_SEC_PIPILINE = "";
		public static String str_Project_Name = "";
		public static String str_InputFiles_Path_AutomaticPipeline = "";
		public static String str_Default_Project_Path = "";
		public static String [] arr_Str_GZ_FileNames;
		public static String [] arr_Str_FASTQ_FileNames;
		public static String [] arr_Str_FileNames;
		public static String [] arr_Str_FileNames_without_Extension;
		public static String [][] td_arr_Str_FileNamse_without_Extension;
		public static String str_File_Extension = ".gz";
		public static int int_GZ_file_Count = 0;
		public static int int_FASTQ_file_Count = 0;
		public static String [] arr_Str_Write_BASH_Script;
		public static String [] arr_Str_BAM_Files_MergingReplicates;
		public static int int_Count_BAM_Files_MergingReplicates = 0;
		public static String str_ATAC_Seq_Pipeline_Script_Name = "user_ATAC_Seq_Pipeline";
		public static String str_walltime = "00:00:00";
		public static String str_nodes = "1";
		public static String str_ppn = "1";
		public static String str_email = "";
		public static String Job_Seq_Protocol;

			
		// FLAGS
		boolean bool_chk_QC = true;
		boolean bool_chk_Trimmomatic = true;
		boolean bool_chk_BWA = true;
		boolean bool_chk_SamSort = true;
		boolean bool_chk_MarkDuplicates = true;
		boolean bool_chk_CollectInsertSizeMetrics = true;
		boolean bool_chk_ATAC_BAM_shifter_gappedAlign = true;
		boolean bool_chk_Samtools = true;
		boolean bool_chk_SamtoolsIndex = true;
		boolean bool_chk_BedTools = true;
		boolean bool_chk_Macs2 = true;
		boolean bool_chk_Multi_Queue_Job_Submissions= true;
		boolean bool_chk_Put_in_Queue = false;
		boolean bool_chk_MergeReplicates= false;
		boolean bool_chk_Copy_and_Process = false;
		boolean bool_chk_Create_Softlinks_and_Process = true;
		boolean bool_SSH_KeepConnectionAlive_Flag = false;

		//Controls
		private JPanel contentPane;
		private JTextField txt_Cluster_Host;
		private JTextField txt_Cluster_User_Name;
		private JPasswordField txt_Cluster_User_Password;
		JTextArea jTextArea1; 
		JTextArea JtextArea_FilesNames_Status;
		private JTextField txt_InputFiles_Path_AutomaticPipeline;
		private JLabel lblNewLabel_3;
		private JButton btn_Run_Automatic_ATACSeq_Pipeline;
		private JPanel JPanel_Set_Paths;
		private JTextField txt_FastQC_Location;
		private JTextField txt_Trimmomatic_Location;
		private JTextField txt_BWA_Location;
		private JTextField txt_SortSam_Location;
		private JTextField txt_MarkDuplicates_Location;
		private JTextField txt_ColInsterSizeMetrics_Location;
		private JTextField txt_BAMGapAlign_Location;
		private JTextField txt_ProjectName;
		private JLabel lblNewLabel_13;
		private JTextField txt_WallTime;
		private JTextField txt_Email;
		private JLabel lblNewLabel_17;
		private JLabel lblNewLabel_19;
		private JLabel lblNewLabel_20;
		private JTextField txt_nodes;
		private JTextField txt_ppn;
		private JTextField txt_Reference_Genome;
		private JLabel lblNewLabel_22;
		private JCheckBox chk_Create_Softlink_and_Process;
		private JCheckBox chk_Fast_GZ_files;
		private JTextField txt_Output_Drectory_Location;
		private JLabel lblNewLabel_23;
		private JLabel lblNewLabel_12;
		private JTextField txt_new_output_dir;
		private JPanel panel;
		private JCheckBox chk_Create_Queue_Jobs;
		private JCheckBox chk_Direct_Processing_without_Queue;
		private JTextField txt_Trimmomatic_illumina_Adaptor;
		private JLabel lblNewLabel;
		private JLabel lblNewLabel_4;
		private JTextField txt_Samtools_Location;
		private JTextField txt_Bedtools_Location;
		private JTextField txt_MACS_Location;
		private JLabel lbl_Samtools;
		private JLabel lblNewLabel_15;
		private JLabel lblNewLabel_24;
		private JComboBox cmb_Seq_Protocol;
		private JCheckBox chk_AutoCorrect;
		JCheckBox chk_QC;
		JCheckBox chk_Trimmomatic;
		JCheckBox chk_BWA;
		JCheckBox chk_SamSort;
		JCheckBox chk_MarkDuplicates;
		JCheckBox chk_CollectInsertSizeMetrics;
		JCheckBox chk_ATAC_BAM_shifter_gappedAlign;
		JCheckBox chk_Samtools;
		JCheckBox chk_SamtoolsIndex;
		JCheckBox chk_BedTools;
		JCheckBox chk_Macs2;
		JCheckBox chk_Put_in_Queue;
		JCheckBox chk_Multi_Queue_Job_Submissions;
		JCheckBox chk_MergeReplicates;
		JCheckBox chk_CheckAll;
		JCheckBox chk_Copy_and_Process;
		

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_ATAC_ver101_Public obj_ATAC = new I_ATAC_ver101_Public();
					obj_ATAC.setResizable(false);
					obj_ATAC.setVisible(true);
					obj_ATAC.Fill_JCombo_Seq_Protocol();
					obj_ATAC.Auto_Input_Settings_File();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Set Project Combo Values.
	 */
	public void Fill_JCombo_Seq_Protocol(){
		try 
		{
			cmb_Seq_Protocol.addItem("2x");
			cmb_Seq_Protocol.addItem("1x");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Automatized_ATAC_SecPipeline
	 */
	public void Automatized_ATAC_SecPipeline()
	{
		try{
				//Check if user wants to run over network or using data cluster
				// Create directory structure, copy and unzip if needed.
				Creat_Driectory_Structure_and_Copy_Unzip_ATAC_Sec_FILES();
				
				if (bool_chk_Copy_and_Process == true || bool_chk_Create_Softlinks_and_Process == true)
			    {
					// Get information about number of files
					PO_Get_GZ_FastQ_File_Information(str_Default_Project_Path); // Output Array with all file information is: arr_Str_FileNames
					
			    }
				else if (bool_chk_Copy_and_Process == false && bool_chk_Create_Softlinks_and_Process == false)
				{
					// Get information about number of files
					PO_Get_GZ_FastQ_File_Information(str_InputFiles_Path_AutomaticPipeline); // Output Array with all file information is: arr_Str_FileNames
		
				}
				
		        String str_File_Extension = ".gz";

				//check if file is ".gz" or ".fastq"
		        if(arr_Str_GZ_FileNames.length > 0)
		        {
			        PO_Create_Split_Extensions_and_GroupSamples(arr_Str_GZ_FileNames);
			        str_File_Extension = ".gz";
		        }
		        else if(arr_Str_FASTQ_FileNames.length > 0)
		        {
			        PO_Create_Split_Extensions_and_GroupSamples(arr_Str_FASTQ_FileNames);
			        str_File_Extension = ".fastq";
		        }
		        
				// Separate file names and extensions and create directories
					if(td_arr_Str_FileNamse_without_Extension.length > 0)
				{
					// Run pipeline for two files
					int int_Decision = JOptionPane.showConfirmDialog(null, td_arr_Str_FileNamse_without_Extension.length + " Sample identified, \n Do you want me to run ATACSec Pipeline ?", "Information" , JOptionPane.YES_NO_OPTION);
					
					arr_Str_Write_BASH_Script = new String [td_arr_Str_FileNamse_without_Extension.length];
					arr_Str_BAM_Files_MergingReplicates = new String [td_arr_Str_FileNamse_without_Extension.length];
					int_Count_BAM_Files_MergingReplicates = 0;
					
					if (int_Decision == 0) 
	                { 
						for(int td_loop_count = 0; td_loop_count<td_arr_Str_FileNamse_without_Extension.length ; td_loop_count++){
							
							//																								R1															R2		
							arr_Str_Write_BASH_Script[td_loop_count] = Create_Automatized_ATACSeq_Pipeline_Script(td_arr_Str_FileNamse_without_Extension[td_loop_count][0]+ str_File_Extension, td_arr_Str_FileNamse_without_Extension[td_loop_count][1]+ str_File_Extension);
						}
						
						String str_Merged_Replicates = "";
						if(bool_chk_MergeReplicates == true)
						{
							str_Merged_Replicates = Create_MergedReplicate(arr_Str_BAM_Files_MergingReplicates);
						}
						
						Create_ATACSeq_Pipeline_Script_File_in_Cluster_Run_Pipeline(arr_Str_Write_BASH_Script, str_Merged_Replicates);
					
	            	} 
				}
				else
				{
					JOptionPane.showMessageDialog(null, td_arr_Str_FileNamse_without_Extension.length + " Sample identified, Job Aborted", "Information" ,JOptionPane.PLAIN_MESSAGE);

				}
				
                
			}
		catch(Exception ex){
			System.out.print(ex);
		}		
	}
	
	/**
	 * 
	 * @param arr_Str_Files_with_Extensions_GZ_0r_FASTQ
	 */
	public void PO_Create_Split_Extensions_and_GroupSamples(String [] arr_Str_Files_with_Extensions_GZ_0r_FASTQ)
	{
		try{
			
				int inner_loop_count = 0;
			
				// Code
				int int_Total_Number_of_Files = arr_Str_Files_with_Extensions_GZ_0r_FASTQ.length;
				arr_Str_FileNames_without_Extension = new String [int_Total_Number_of_Files];
				
				for(int i=0; i<int_Total_Number_of_Files; i++)
				{
					String str_Parameters =  arr_Str_Files_with_Extensions_GZ_0r_FASTQ[i];
					String[] arr_Split_Param = str_Parameters.split("\\.");
					
					str_Parameters = arr_Split_Param[0];	//Clear parsed value
					
					for(int j=1; j<arr_Split_Param.length - 1; j++)
					{
						str_Parameters = str_Parameters + "." + arr_Split_Param[j];
					}
					arr_Str_FileNames_without_Extension[i] = str_Parameters;
	            }
				
				int td_arr_lenght_col1_total_number_of_files = int_Total_Number_of_Files;
				int td_arr_lenght_col2_possible_number_of_R_files = 2;					

		        String str_Job_Seq_Protocol = Job_Seq_Protocol;
	    		String str_Sub_Seq_Protocol = str_Job_Seq_Protocol.substring(0,2);
	    		String str_File_Information= "";
	    		if(str_Sub_Seq_Protocol.equals("1x"))
	    		{
					td_arr_Str_FileNamse_without_Extension = new String [td_arr_lenght_col1_total_number_of_files][td_arr_lenght_col2_possible_number_of_R_files];
					
					for(int td_loop_count = 0; td_loop_count<td_arr_lenght_col1_total_number_of_files; td_loop_count++){
						td_arr_Str_FileNamse_without_Extension[td_loop_count][0] = arr_Str_FileNames_without_Extension[td_loop_count];
						td_arr_Str_FileNamse_without_Extension[td_loop_count][1] = "NA";
						str_File_Information = str_File_Information + "\n" + "Sample -->" + "File R1: " + td_arr_Str_FileNamse_without_Extension[td_loop_count][0] + "\n";
					}	
	    		}
	    		else if (str_Sub_Seq_Protocol.equals("2x"))
	    		{
	    			td_arr_lenght_col1_total_number_of_files = td_arr_lenght_col1_total_number_of_files /2;
					td_arr_Str_FileNamse_without_Extension = new String [td_arr_lenght_col1_total_number_of_files][td_arr_lenght_col2_possible_number_of_R_files];
					
					inner_loop_count = 0;
					for(int td_loop_count = 0; td_loop_count<td_arr_lenght_col1_total_number_of_files; td_loop_count++){
						
						td_arr_Str_FileNamse_without_Extension[td_loop_count][0] = arr_Str_FileNames_without_Extension[inner_loop_count];
						inner_loop_count++;
						td_arr_Str_FileNamse_without_Extension[td_loop_count][1] = arr_Str_FileNames_without_Extension[inner_loop_count];

						str_File_Information = str_File_Information + "\n" + "Samples -->" + "File R1: " + td_arr_Str_FileNamse_without_Extension[td_loop_count][0] + "File R2: " + td_arr_Str_FileNamse_without_Extension[td_loop_count][1] + "\n";
						
						inner_loop_count++;
					}	
	    		}	
				
				// Count the length to check for merging replicates
				int_FASTQ_file_Count = td_arr_lenght_col1_total_number_of_files;
				
				System.out.println("Sample: " + td_arr_Str_FileNamse_without_Extension.length);
				JtextArea_FilesNames_Status.append("Sample: " + td_arr_Str_FileNamse_without_Extension.length + "\n");
				
				System.out.println("Total FastQ Files: " + inner_loop_count);
				JtextArea_FilesNames_Status.append("Total FastQ Files: " + inner_loop_count + "\n");

				JtextArea_FilesNames_Status.append(str_File_Information);

			}
		catch(Exception ex){
			System.out.print(ex);
		}		
	}
	
	/**
	 * Automatized_ATAC_SecPipeline
	 */
	public void Create_ATACSeq_Pipeline_Script_File_in_Cluster_Run_Pipeline(String [] str_Pipeline_Path_Script, String str_Merged_Replicates)
	{
		try{
			// Establish Connection to the Server and Create Communication Channel
	        JSch obj_JSch = new JSch();
	        Session obj_JSch_Session = obj_JSch.getSession(str_Cluster_User_Name, str_Cluster_Host_Name, int_Cluster_Host_port);
	        obj_JSch_Session.setPassword(str_Cluster_User_Password);
	        obj_JSch_Session.setConfig("StrictHostKeyChecking", "no");	        
	        obj_JSch_Session.connect(10 * 10000);	// 10 Seconds time out
	        
	        //Create Channel
	        Channel obj_Channel = obj_JSch_Session.openChannel("shell");
	        OutputStream inputstream_for_the_channel = obj_Channel.getOutputStream();
	        PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
	        obj_Channel.setOutputStream(System.out, true);
	        obj_Channel.connect();
	        
	        // Create Job and Put in Queue
			str_ATAC_Seq_Pipeline_Script_Name = str_Project_Name ;

			String str_Hash_Bin_Bash = "#!/bin/bash"  + "\n";
				
			// Create Multiple Queues for Multiple Jobs
			if(bool_chk_Multi_Queue_Job_Submissions == true) // && bool_chk_Put_in_Queue == false)
			{
					
				for (int i = 0; i < str_Pipeline_Path_Script.length; i++) 
				{
					// Write Shell Script
					String str_Start_Echo = "echo '";
					String  tmp_str_ATAC_Seq_Pipeline_Script_Name = str_ATAC_Seq_Pipeline_Script_Name + "_Job" + i +".sh";
					String str_End_Echo = "' >" + tmp_str_ATAC_Seq_Pipeline_Script_Name;
					String str_Ehco_Script_File = "";
											
					// Memory, Walltime and additional parameters
					String PSB_N = "#PBS -N " + str_Project_Name + "Job_" + i;	
					String PBS_l_nodes_pnn_walltime = "#PBS -l nodes=" + str_nodes + ":ppn=" + str_ppn +",walltime="+ str_walltime;	
					String PBS_M = "#PBS -M " + str_email  + "\n";
					// ------------------------------------------
					
					String str_Locate = "cd " + str_Default_Project_Path;
			        String str_Run = "qsub " + tmp_str_ATAC_Seq_Pipeline_Script_Name;
			        String str_Stat = "qstat ";
			        String str_Lcate_Create_Run_Script = "";
				        
			        if(chk_Create_Queue_Jobs.isSelected())
			        {
			    		String str_RUN_ALL_MODULES = "module purge && "
			    				+ "module load "
			    				+ "python/2.7.3 "
			    				+ "R "
			    				+ "java/1.8.0_73 " 
			    				+ "samtools "
			    				+ "perl/cga "
			    				+ "MACS/2.1.0.20151222 "
			    				//+ "bwa/0.7.9a "
			    				+ "BEDtools/2.22.0 "
			    				+ " ";
			    		
			    		str_Ehco_Script_File =str_Start_Echo;
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" + str_Hash_Bin_Bash;
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PSB_N ;
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PBS_l_nodes_pnn_walltime ;
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PBS_M;
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" + str_RUN_ALL_MODULES + " ; " ;
						
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" +  str_Pipeline_Path_Script[i];
						str_Ehco_Script_File = str_Ehco_Script_File + str_End_Echo;	 						//"echo '#!/bin/bash \nSecond line \n Third line.' >foo.sh"

					       str_Lcate_Create_Run_Script =  str_Locate + " && " +
									str_Ehco_Script_File + " && " +
									str_Run  + " ; " ;
			        	
			        }
			        else if (chk_Direct_Processing_without_Queue.isSelected())
			        {
			        	str_Run = tmp_str_ATAC_Seq_Pipeline_Script_Name;
				        str_Stat = "ls -l ";
				        
						str_Ehco_Script_File =str_Start_Echo;
						
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" +  str_Pipeline_Path_Script[i];
						str_Ehco_Script_File = str_Ehco_Script_File + str_End_Echo;	 						//"echo '#!/bin/bash \nSecond line \n Third line.' >foo.sh"

				       str_Lcate_Create_Run_Script =  str_Locate + " && " +
								str_Ehco_Script_File;
			        	
			        }

		        commander.println(str_Lcate_Create_Run_Script);	
			    }
					
			        String str_Stat = "qstat ";
			        commander.println(str_Stat);	
					
				}
				else if(bool_chk_Multi_Queue_Job_Submissions == false)// && bool_chk_Put_in_Queue == true)
				{
					// Create one Queue and submit all jobs in it
					String  tmp_str_ATAC_Seq_Pipeline_Script_Name = str_ATAC_Seq_Pipeline_Script_Name +"_"+ str_Project_Name +".sh";

					// Write Shell Script
					String str_Start_Echo = "echo '";
					String str_End_Echo = "' >" + tmp_str_ATAC_Seq_Pipeline_Script_Name;
					String str_Ehco_Script_File = "";
					
					// Memory, Walltime and additional parameters
					String PSB_N = "#PBS -N " + str_Project_Name;	
					String PBS_l_nodes_pnn_walltime = "#PBS -l nodes=" + str_nodes + ":ppn=" + str_ppn +",walltime="+ str_walltime;	
					String PBS_M = "#PBS -M " + str_email  + "\n";
					// ------------------------------------------

					str_Ehco_Script_File =str_Start_Echo;

					 if(chk_Create_Queue_Jobs.isSelected())
				        {
						 
						 String str_RUN_ALL_MODULES = "module purge && "
				    				+ "module load "
				    				+ "python/2.7.3 "
				    				+ "R "
				    				+ "java/1.8.0_73 " 
				    				+ "samtools "
				    				+ "perl/cga "
				    				+ "MACS/2.1.0.20151222 "
				    				//+ "bwa/0.7.9a "
				    				+ "BEDtools/2.22.0 "
				    				+ " ";
						 
							str_Ehco_Script_File = str_Ehco_Script_File + "\n" + str_Hash_Bin_Bash;
							str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PSB_N ;
							str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PBS_l_nodes_pnn_walltime ;
							str_Ehco_Script_File = str_Ehco_Script_File + "\n" + PBS_M;
							str_Ehco_Script_File = str_Ehco_Script_File + "\n" + str_RUN_ALL_MODULES + " ; " ;

				        }
					
					for (int i = 0; i < str_Pipeline_Path_Script.length; i++) 
					{
						str_Ehco_Script_File = str_Ehco_Script_File + "\n" +  str_Pipeline_Path_Script[i];
				    }
					
					String str_Ehco_Script_File_PLUS_str_Merged_Replicates = str_Ehco_Script_File + "\n" + str_Merged_Replicates;
					str_Ehco_Script_File = str_Ehco_Script_File_PLUS_str_Merged_Replicates + str_End_Echo;
					
			        String str_Locate = "cd " + str_Default_Project_Path;
			        String str_Run = "qsub " + tmp_str_ATAC_Seq_Pipeline_Script_Name;
			        String str_Stat = "qstat ";

			        String str_Lcate_Create_Run_Script =  str_Locate + " && " +
			        									str_Ehco_Script_File + " && " + 
			        									str_Run + " ; " +
			        									str_Stat ;

			        //In case need to create only script but not to submit.
			        /*String str_Lcate_Create_Run_Script =  str_Locate + " && " + 
							str_Ehco_Script_File;
			        */
			        
			        commander.println(str_Lcate_Create_Run_Script);	
				}
			commander.println("exit");
	        commander.close();
	        
	        do {
	            Thread.sleep(2000);
	        } while(!obj_Channel.isEOF());
	        
	        obj_Channel.disconnect();
	        obj_JSch_Session.disconnect();
	        System.out.println("Disconnected to " + str_Cluster_Host_Name);
	        
			JOptionPane.showMessageDialog(null, "I think you are all set!", "Good Luck " + str_Cluster_User_Name, JOptionPane.PLAIN_MESSAGE);
		}
		catch(Exception ex){
			System.out.print(ex);
		}
	}
	
	
	/**
	 * Automatized_ATAC_SecPipeline
	 */
	public void RunTime_Create_ATACSeq_Pipeline_Script_File_in_Cluster_Run_Pipeline(String [] str_Pipeline_Path_Script, String str_Merged_Replicates)
	{
		try{
				// Create Job and Put in Queue
				str_ATAC_Seq_Pipeline_Script_Name = str_Project_Name ;

				String str_Hash_Bin_Bash = "#!/bin/bash"  + "\n";
					
				for (int i = 0; i < str_Pipeline_Path_Script.length; i++) {

					System.out.println(str_Pipeline_Path_Script[i]);
			    }
	        
		}
		catch(Exception ex){
			System.out.print(ex);
		}
	}
	
	
	/**
	 * Automatized_ATAC_SecPipeline
	 */
	public String Create_MergedReplicate(String [] str_Pipeline_Path_Script)
	{
		
		String str_CD_MKDIR_Replicates = "cd " + str_Default_Project_Path + " && " +
										"mkdir " + str_Merged_Samples_Dir + " && " +
										"cd " + str_Merged_Samples_Dir;
		
		String str_MegredReplicate_File_Name_without_Extension = str_Project_Name + "_mergedSample";
		String str_MegredReplicate_BAM_File_Name = str_Project_Name +"_mergedSample.bam";

		String str_Replicates = "";
		
		for(int loopCount = 0; loopCount < str_Pipeline_Path_Script.length; loopCount++)
		{
			str_Replicates = str_Replicates + str_Pipeline_Path_Script[loopCount] +" ";
		}
		
		// Merge BAM
		String str_Run_Samtool_merge_at_Replicates_BAM = str_SW_samtools_Location + str_SW_samtools_Merge_Command + " " + str_MegredReplicate_BAM_File_Name  + " " + str_Replicates;

		///Sort BAM
		String str_Run_Samtool_sort_at_MergedReplicates_BAM = str_SW_samtools_Location  + str_SW_samtools_Sort_Command + " " + str_MegredReplicate_BAM_File_Name + " -o " + str_MegredReplicate_File_Name_without_Extension + "_sorted.bam" ;

		////Index BAM
		String str_Run_Samtool_Index_at_MergedReplicates_BAM = str_SW_samtools_Location + str_SW_samtools_Index_Command  + " " + str_MegredReplicate_File_Name_without_Extension + "_sorted.bam" ;

		/////BBAM To BED
		String str_Run_Samtool_Bamtobed_at_MergedReplicates_BED = str_SW_bedtools_Location + str_SW_bedtools_bamTObed_Command + " " + "-i "  + str_MegredReplicate_File_Name_without_Extension + "_sorted.bam" + " > " + str_MegredReplicate_File_Name_without_Extension + "_sorted.bed" ;
		
		//........................................
		// Run MACS2
		String str_macs2_Input_FileName = str_MegredReplicate_File_Name_without_Extension + "_sorted.bed";
		String str_macs2_Output_FileName = str_MegredReplicate_File_Name_without_Extension + "_sorted";
		String str_macs2_Output_Dir = str_Default_Project_Path + "/" + str_Merged_Samples_Dir + "/macs2";
		
		String str_macs2_Command = str_SW_MACS2_Location + " callpeak -t " + str_macs2_Input_FileName + " -f BED -n " +  str_macs2_Output_FileName + " -g 'hs' --nomodel --shift -100 --extsize 200 -B --broad --outdir " + str_macs2_Output_Dir + "/";
		
		
		///////Script
		String str_Megred_Replicates_Script = str_CD_MKDIR_Replicates + " && " + 
											str_Run_Samtool_merge_at_Replicates_BAM + " && " +
											str_Run_Samtool_sort_at_MergedReplicates_BAM + " && " +
											str_Run_Samtool_Index_at_MergedReplicates_BAM  + " && " +
											str_Run_Samtool_Bamtobed_at_MergedReplicates_BED + " && " +
											str_macs2_Command;	

		
		return	str_Megred_Replicates_Script;    
	}
	
	/**
	 * Automatized_ATAC_SecPipeline
	 */
	public String Create_Automatized_ATACSeq_Pipeline_Script(String str_R1, String str_R2)
	{
		String Project_Name = txt_ProjectName.getText();
		String Project_Plus_R1_R2_Name = Project_Name +"_"+ str_R1 +"_"+ str_R2;
		
        String str_Merged_Create_Directory_Structure_Commands = "";       												
        
        //Create Sample Directory
        str_Cluster_Dir_Project_SampleName = Project_Name +"_"+ str_R1 +"_"+ str_R2;
		str_Cluster_Dir_Project_SampleName = str_New_Output_Dir  + "/" + str_Cluster_Dir_ProjectName + "/" + str_Cluster_Dir_Project_SampleName;
		
		

		//........................................
		// Directories .....
		
		str_Merged_Create_Directory_Structure_Commands = "cd " + str_Default_Project_Path + " ; " +
				"mkdir " + str_Cluster_Dir_Project_SampleName + " ; " + 
				"cd " + str_Cluster_Dir_Project_SampleName + " ; " +
				"mkdir " + str_Cluster_Dir_fastQC + " ; " +
				"mkdir " + str_Cluster_Dir_trimmomatic + " ; " +
				"mkdir " + str_Cluster_Dir_trimmomatic + "/" + str_Cluster_Dir_trimmomatic_bwa + " ; " +
				"mkdir " + str_Cluster_Dir_trimmomatic + "/" + str_Cluster_Dir_trimmomatic_bwa + "/" + str_Cluster_Dir_trimmomatic_bwa_macs2;

		String str_R1_File_Name = Get_FastGZ_FileName_from_Path(str_R1);
		String str_R2_File_Name = Get_FastGZ_FileName_from_Path(str_R2);
		
		str_R1 = str_Default_Project_Path + "/" + str_R1;
		str_R2 = str_Default_Project_Path + "/" + str_R2;

		if (Job_Seq_Protocol.equals("2x"))
		{
			/////////////*****FASTQC*******///////////
    	   str_FastQC_Command = str_SW_FastQC_Location + " " + str_R1 + " " + str_R2 + " -o ./ && "; 
    	   str_QC_One_Line_Command = "cd " + str_Cluster_Dir_Project_SampleName +"/"+ str_Cluster_Dir_fastQC + " ; " + str_FastQC_Command + " \n ";
			
			/////////////*****TRIMMING*******///////////
			str_Trimmomatic_Command = "java -jar " + str_SW_Trimmomatic_Location + " PE " + 
																			str_R1 + " " +
																			str_R2 + " " +
																			str_R1_File_Name + str_FileName_R1_filtered_trimmed + " " +
																			str_R1_File_Name + str_FileName_R1_unpaired + " " +
																			str_R2_File_Name + str_FileName_R2_filtered_trimmed + " " +
																			str_R2_File_Name + str_FileName_R2_unpaired + " " +
																			str_Trimmomatic_TRAILING_SLIDINGWINDOW_MINLEN + " " + 
																			str_Trimmomatic_ILLUMINACLIP_PE + " && " + " \n ";
			
			str_Trimmomatic_One_Line_Command = 
										"cd " + str_Cluster_Dir_Project_SampleName +"/"+ str_Cluster_Dir_trimmomatic + " ; " +
		 								str_Trimmomatic_Command;
	        
			/////////////*****BWA*******///////////
			str_BWA_Output_File_Name = Project_Plus_R1_R2_Name + str_Extension_sam;
			String str_BWA_Output_File_Name_with_Path = str_Cluster_Dir_trimmomatic_bwa +"/"+ str_BWA_Output_File_Name;
			
			str_BWA_Command = str_SW_BWA_Location + " " + 
								" mem -M -t 8 " + 
								str_Reference_Genome + " " +  
								str_R1_File_Name + "_filtered_trimmed " +
								str_R2_File_Name + "_filtered_trimmed " +
								" > " + 
								str_BWA_Output_File_Name_with_Path + 
								" && " + " \n ";
			
		}
		else if (Job_Seq_Protocol.equals("1x"))
		{
			/////////////*****FASTQC*******///////////
			str_FastQC_Command = str_SW_FastQC_Location + " " + str_R1 + " -o ./ && "; 
			str_QC_One_Line_Command = "cd " + str_Cluster_Dir_Project_SampleName +"/"+ str_Cluster_Dir_fastQC + " ; " + str_FastQC_Command + " \n ";
			
			/////////////*****TRIMMING*******///////////
			str_Trimmomatic_Command = "java -jar " + str_SW_Trimmomatic_Location + " SE " + 
																			str_R1 + " " +
																			str_R1_File_Name + str_FileName_R1_filtered_trimmed + " " +
																			str_R1_File_Name + str_FileName_R1_unpaired + " " +
																			str_Trimmomatic_TRAILING_SLIDINGWINDOW_MINLEN + " " + 
																			str_Trimmomatic_ILLUMINACLIP_SE + " && "+ " \n ";

			str_Trimmomatic_One_Line_Command = 
					"cd " + str_Cluster_Dir_Project_SampleName +"/"+ str_Cluster_Dir_trimmomatic + " ; " +
						str_Trimmomatic_Command;

			/////////////*****BWA*******///////////
			str_BWA_Output_File_Name = Project_Plus_R1_R2_Name + str_Extension_sam;
			String str_BWA_Output_File_Name_with_Path = str_Cluster_Dir_trimmomatic_bwa +"/"+ str_BWA_Output_File_Name;

			str_BWA_Command = str_SW_BWA_Location + " " + 
								" mem -M -t 8 " + 
								str_Reference_Genome + " " +  
								str_R1_File_Name + "_filtered_trimmed " +
								" > " + 
								str_BWA_Output_File_Name_with_Path + 
								" && "+ " \n ";
		}
		
			   									
			//........................................
			// Run Picard SorSam.jar
			String str_SortSam_Input_FileName = str_BWA_Output_File_Name;
			String str_SortSam_Output_FileName = Project_Plus_R1_R2_Name + str_Extension_sorted_sam; // str_Extension_bam;
			String str_SortSam_Command = "java -Xms1g -Xmx4g -jar " + str_SW_SortSam_Location + " INPUT=" +  str_SortSam_Input_FileName + 
													" OUTPUT=" + str_SortSam_Output_FileName + 
													" SO=coordinate" + " && " + " \n ";
			String str_SortSam_One_Line_Command = "cd " + str_Cluster_Dir_trimmomatic_bwa + " ; " + " \n " + str_SortSam_Command ;
												
			//........................................
			// Run MarkDuplicates.jar
			
			String str_MarkDuplicates_Input_FileName = str_SortSam_Output_FileName;
			String str_MarkDuplicates_Rmdup_Output_FileName = Project_Plus_R1_R2_Name + "_rmdup"	+ str_Extension_sam;
			String str_MarkDuplicates_METRICS_Output_FileName = Project_Plus_R1_R2_Name + "_rmdup_metrics" + str_Extension_txt;
			String str_MarkDuplicates_Command = "java -Xms1g -Xmx4g -jar " + str_SW_MarkDuplicates_Location + " INPUT=" +  str_MarkDuplicates_Input_FileName + 
																				" OUTPUT=" + str_MarkDuplicates_Rmdup_Output_FileName + 
																				" METRICS_FILE=" + str_MarkDuplicates_METRICS_Output_FileName +
																				" REMOVE_DUPLICATES=true" + " && " +" \n ";
			//........................................
			// Run CollectInsertSizeMetrics.jar
			
			String str_CollectInsertSizeMetrics_Input_FileName = str_MarkDuplicates_Rmdup_Output_FileName;
			
			String str_CollectInsertSizeMetrics_insertSize_Output_FileName = Project_Plus_R1_R2_Name + "_rmdup_insertSize"	+ str_Extension_txt;
			String str_CollectInsertSizeMetrics_insertSize_Histo_Output_FileName = Project_Plus_R1_R2_Name + "_rmdup_metrics" + str_Extension_pdf;
			
			String str_CollectInsertSizeMetrics_Command = "java -Xms1g -Xmx4g -jar " + str_SW_CollectInsertSizeMetrics_Location + 
															" METRIC_ACCUMULATION_LEVEL=ALL_READS" + 
															" OUTPUT=" + str_CollectInsertSizeMetrics_insertSize_Output_FileName + 
															" HISTOGRAM_FILE=" + str_CollectInsertSizeMetrics_insertSize_Histo_Output_FileName +
															" INPUT=" +  str_CollectInsertSizeMetrics_Input_FileName + " && " +" \n ";			
			//........................................
			// Run ATAC_BAM_shifter_gappedAlign.pl
			
			String str_ATAC_BAM_shifter_gappedAlign_Input_FileName = str_MarkDuplicates_Rmdup_Output_FileName;
			String str_ATAC_BAM_shifter_gappedAlign_Output_FileName = Project_Plus_R1_R2_Name + "_rmdup_shifted";
			
			String str_ATAC_BAM_shifter_gappedAlign_Command = "perl " + str_SW_ATAC_BAM_shifter_gappedAlign_Location + " " 
																		+ str_ATAC_BAM_shifter_gappedAlign_Input_FileName + " " 
																		+ str_ATAC_BAM_shifter_gappedAlign_Output_FileName + " && " +" \n ";
			
			//........................................
			// Run samtools
			String str_samtools_Input_FileName = str_ATAC_BAM_shifter_gappedAlign_Output_FileName + ".bam";
			String str_samtools_Output_FileName = str_ATAC_BAM_shifter_gappedAlign_Output_FileName + "_sorted";
			
			String str_samtools_Command = str_SW_samtools_Location + str_SW_samtools_Sort_Command  + " " 
																	+ str_samtools_Input_FileName + " -o " 
																	+ str_samtools_Output_FileName+ ".bam" + " && " +" \n ";
			
			arr_Str_BAM_Files_MergingReplicates[int_Count_BAM_Files_MergingReplicates] = //str_Default_Project_Path +"/"+ 
													str_Cluster_Dir_Project_SampleName +"/"+ 
													str_Cluster_Dir_trimmomatic +"/"+ 
													str_Cluster_Dir_trimmomatic_bwa +"/"+ 
													str_ATAC_BAM_shifter_gappedAlign_Output_FileName + "_sorted.bam" ;
			
			int_Count_BAM_Files_MergingReplicates++;

		 
			//........................................
			// Run samtools index
			String str_samtools_index_Output_FileName = str_ATAC_BAM_shifter_gappedAlign_Output_FileName + "_sorted.bam";	//  this BAM file need to be merged
			
			String str_samtools_index_Command = str_SW_samtools_Location + str_SW_samtools_Index_Command  + " " + str_samtools_index_Output_FileName + " && " +" \n "; //+ ".bam" ;
		
			//........................................
			// Run bedtools
			String str_bedtools_Input_FileName = str_samtools_index_Output_FileName;// + ".bam";
			String str_bedtools_Output_FileName = str_samtools_index_Output_FileName + "_sorted.bed";
			
			String str_bedtools_Command = str_SW_bedtools_Location + str_SW_bedtools_bamTObed_Command + " " + "-i "  + str_bedtools_Input_FileName + " > " + str_bedtools_Output_FileName + " && " +" \n ";
	
			//........................................
			// Run MACS2
			String str_macs2_Input_FileName = str_bedtools_Output_FileName;
			String str_macs2_Output_FileName = str_samtools_index_Output_FileName + "_sorted";
			
			String str_macs2_Output_Dir = str_Cluster_Dir_Project_SampleName + "/" + str_Cluster_Dir_trimmomatic + "/" + str_Cluster_Dir_trimmomatic_bwa + "/" + str_Cluster_Dir_trimmomatic_bwa_macs2;
			String str_macs2_Command = str_SW_MACS2_Location 
											+ " callpeak -t " 
											+ str_macs2_Input_FileName 
											+ " -f BED -n " 
											+  str_macs2_Output_FileName 
											+ " -g 'hs' --nomodel --shift -100 --extsize 200 -B --broad --outdir " 
											+ str_macs2_Output_Dir + "/" + " \n ";
			
				
			String str_Merged_Commands_One_Line ="";
			
			if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == true &&
					bool_chk_Samtools == true &&
					bool_chk_SamtoolsIndex == true &&
					bool_chk_BedTools == true &&
					bool_chk_Macs2 == true)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + " " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command  + " " +
						str_ATAC_BAM_shifter_gappedAlign_Command + " " +
						str_samtools_Command + "  " +
						str_samtools_index_Command + "  " +
						str_bedtools_Command + "  " +
						str_macs2_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == false)			
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command;
			}

			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + " ; " + 
						str_MarkDuplicates_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + "  " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == true &&
					bool_chk_Samtools == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + "  " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command  + "  " +
						str_ATAC_BAM_shifter_gappedAlign_Command; 
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == true &&
					bool_chk_Samtools == true &&
					bool_chk_SamtoolsIndex == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + "  " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command  + "  " +
						str_ATAC_BAM_shifter_gappedAlign_Command + "  " +
						str_samtools_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == true &&
					bool_chk_Samtools == true &&
					bool_chk_SamtoolsIndex == true &&
					bool_chk_BedTools == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + "  " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command  + "  " +
						str_ATAC_BAM_shifter_gappedAlign_Command + "  " +
						str_samtools_Command + "  " +
						str_samtools_index_Command;
			}
			
			else if	(bool_chk_QC == true && 
					bool_chk_Trimmomatic == true &&
					bool_chk_BWA == true &&
					bool_chk_SamSort == true &&
					bool_chk_MarkDuplicates == true &&
					bool_chk_CollectInsertSizeMetrics == true &&
					bool_chk_ATAC_BAM_shifter_gappedAlign == true &&
					bool_chk_Samtools == true &&
					bool_chk_SamtoolsIndex == true &&
					bool_chk_BedTools == true &&
					bool_chk_Macs2 == false)
			{
				str_Merged_Commands_One_Line = str_Merged_Create_Directory_Structure_Commands + " ; " + 
						str_QC_One_Line_Command + "    " +  
						str_Trimmomatic_One_Line_Command + "   " + 
						str_BWA_Command + "    " + 
						str_SortSam_One_Line_Command + "  " + 
						str_MarkDuplicates_Command + "  " + 
						str_CollectInsertSizeMetrics_Command  + "  " +
						str_ATAC_BAM_shifter_gappedAlign_Command + "  " +
						str_samtools_Command + "  " +
						str_samtools_index_Command + "  " +
						str_bedtools_Command;
			}
			
			return str_Merged_Commands_One_Line;
	}
	
	/**
	 * 
	 * @param file_Path_Name
	 * @return
	 */
	public String Get_FastGZ_FileName_from_Path(String file_Path_Name)
	{
		String[] arr_Split_Param = file_Path_Name.split("/");
		String str_File_Name = arr_Split_Param[arr_Split_Param.length - 1];
		return str_File_Name;
	}
	
	/**
	 * Automatized_ATAC_Sec_COPY_UNZIP_FILES
	 */
	public void Creat_Driectory_Structure_and_Copy_Unzip_ATAC_Sec_FILES()
	{
		try{
			// Code
			
			// Cluster Login information
								
			str_Project_Name = txt_ProjectName.getText();
			str_InputFiles_Path_AutomaticPipeline = txt_InputFiles_Path_AutomaticPipeline.getText();

			// 1. Create Project Directory
			// Establish Connection to the Server
	        JSch obj_JSch = new JSch();
	        Session obj_JSch_Session = obj_JSch.getSession(str_Cluster_User_Name, str_Cluster_Host_Name, int_Cluster_Host_port);
	        obj_JSch_Session.setPassword(str_Cluster_User_Password);
	        obj_JSch_Session.setConfig("StrictHostKeyChecking", "no");	        
	        obj_JSch_Session.connect(10 * 10000);	// 10 Seconds time out
			        
	        //Create Channel
	        Channel obj_Channel = obj_JSch_Session.openChannel("shell");
			        
	        // Data sent back from the server
	        OutputStream inputstream_for_the_channel = obj_Channel.getOutputStream();
	        PrintStream commander = new PrintStream(inputstream_for_the_channel, true);

	        obj_Channel.setOutputStream(System.out, true);
	        obj_Channel.connect();

	        // Create File Names
	        str_Cluster_Dir_ProjectName = str_Project_Name;
	        
	        //........................................
			// Create Directory Structure
			        
	        commander.println("ls -l");
	        commander.println("cd " + str_New_Output_Dir); //  + / + ATACSeq_Project
		        
	        //Create Project Directory
	        //commander.println("rm -r " + str_Cluster_Dir_ProjectName);

	        commander.println("mkdir " + str_Cluster_Dir_ProjectName);
	        commander.println("cd " + str_Cluster_Dir_ProjectName);
	        commander.println("ls -l");
	        
	        // Default Project Path
			str_Default_Project_Path = str_New_Output_Dir  + "/" + str_Cluster_Dir_ProjectName;

	        // 2. Copy & Unzipp All Zipped Files to Project Directory
		    if (bool_chk_Copy_and_Process == true)
		    {
		    	if(chk_Fast_GZ_files.isSelected())
		    	{
			        commander.println("cp " + str_InputFiles_Path_AutomaticPipeline + "/" + "*.gz " + str_Default_Project_Path); // In case of GZ files
			        //commander.println("gzip -d " + str_Default_Project_Path + "/" + "*.gz "); // In case of GZ files // 3. UNZIP (gzip -d) to Zipped (gz) Files to Project Directory 
		    	}
		    	else
		    	{
			        commander.println("cp " + str_InputFiles_Path_AutomaticPipeline + "/" + "*.fastq " + str_Default_Project_Path); // In case of GZ files
		    	}
		    }
		    else if (bool_chk_Create_Softlinks_and_Process == true)
		    {
		    	if(chk_Fast_GZ_files.isSelected())
		    	{
			        commander.println("ln -s " + str_InputFiles_Path_AutomaticPipeline + "/" + "*.gz " + str_Default_Project_Path); // Create soft links
		    	}
		    	else
		    	{
			        commander.println("ln -s " + str_InputFiles_Path_AutomaticPipeline + "/" + "*.fastq " + str_Default_Project_Path); // Create soft links
		    	}
		    	
		    }
		    
			// Close Connection
			commander.println("exit");
	        commander.close();
			
	        do {
	            Thread.sleep(2000);
	        } while(!obj_Channel.isEOF());
	        
	        obj_Channel.disconnect();
	        obj_JSch_Session.disconnect();
	        System.out.println("Disconnected to " + str_Cluster_Host_Name);
	        			
		}
		catch(Exception ex){
			//System.out.print(ex);
			System.out.print("User is not recognized, please check User Name and Password!");
			JOptionPane.showMessageDialog(null, "User is not recognized, please check User Name and Password!", "User Authentication", JOptionPane.PLAIN_MESSAGE);


		}		
	}
	
	/**
	 * Automatized_ATAC_Sec_COPY_UNZIP_FILES
	 */
	public void RunTime_Creat_Driectory_Structure_and_Copy_Unzip_ATAC_Sec_FILES()
	{
		try{
			str_Project_Name = txt_ProjectName.getText();
			str_InputFiles_Path_AutomaticPipeline = txt_InputFiles_Path_AutomaticPipeline.getText();

			// 1. Create Project Directory
	        // Create File Names
	        str_Cluster_Dir_ProjectName = str_Project_Name;

	        str_Default_Project_Path = str_New_Output_Dir  + "/" + str_Cluster_Dir_ProjectName;
			Runtime.getRuntime().exec("mkdir " + str_Default_Project_Path);
		}
		catch(Exception ex){
			//System.out.print(ex);
			System.out.print("User is not recognized, please check User Name and Password!");
			JOptionPane.showMessageDialog(null, "User is not recognized, please check User Name and Password!", "User Authentication", JOptionPane.PLAIN_MESSAGE);


		}		
	}
	
	/*
	 * Get Current Directory Path
	 */ 
	public String get_Current_Directory_Location()
	{
		String str_Current_Directory_Location = "";
		
		str_Current_Directory_Location = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + str_Current_Directory_Location);
				
        return str_Current_Directory_Location;
	}
	 
	/**
	 * 	File Information
	 * Automatized_ATAC_Sec_COPY_UNZIP_FILES
	 */
	public void PO_Get_GZ_FastQ_File_Information(String str_Project_FastQ_Files_Path)
	{
		
		String SFTPHOST = txt_Cluster_Host.getText(); // "data-cluster";
        String SFTPUSER = txt_Cluster_User_Name.getText();
        String SFTPPASS = txt_Cluster_User_Password.getText(); 
        int    SFTPPORT = 22;
        
        String SFTPWORKINGDIR = str_Project_FastQ_Files_Path;// "/ATAC_PROJECTS/NEW/";
                 
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;
         
        try{
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            
            // ALL FILES
            Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
            for(int i=0; i<filelist.size();i++){
                System.out.println(filelist.get(i).toString());

            }
                        
            //---------------------------------------
            // ALL FILES with Particular Extension GZ
	        ArrayList<String> list = new ArrayList<String>();
	        Vector<LsEntry> entries = channelSftp.ls("*.*");
	        for (LsEntry entry : entries) {
	            if(entry.getFilename().toLowerCase().endsWith(".gz")) {
	                list.add(entry.getFilename());
	            }
	        }
	        
	        arr_Str_GZ_FileNames = new String [list.size()];
	        
	        for(int i=0; i<list.size();i++){
                arr_Str_GZ_FileNames[i] = list.get(i).toString() ;
            }
	        
	        Arrays.sort(arr_Str_GZ_FileNames);
	        int_GZ_file_Count = arr_Str_GZ_FileNames.length; 
	        
	        
	        //---------------------------------------
            // ALL FILES with Particular Extension FASTQ
	        ArrayList<String> fastq_list = new ArrayList<String>();
	        Vector<LsEntry> fastq_entries = channelSftp.ls("*.*");
	        for (LsEntry entry : fastq_entries) {
	            if(entry.getFilename().toLowerCase().endsWith(".fastq")) {
	            	fastq_list.add(entry.getFilename());
	            }
	        }
	        
	        arr_Str_FASTQ_FileNames = new String [fastq_list.size()];
	        
	        for(int i=0; i<fastq_list.size();i++){
	        	arr_Str_FASTQ_FileNames[i] = fastq_list.get(i).toString() ;
            }
		    
	        Arrays.sort(arr_Str_FASTQ_FileNames);
	        int_FASTQ_file_Count = arr_Str_FASTQ_FileNames.length;

	        channel.disconnect();
	        session.disconnect();
             
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }	
	}
	
	/**
	 * 	File Information
	 * Automatized_ATAC_Sec_COPY_UNZIP_FILES
	 */
	public void RunTime_PO_Get_GZ_FastQ_File_Information(String str_Project_FastQ_Files_Path)
	{
		
		List<String> results = new ArrayList<String>();


		File[] files = new File(str_Project_FastQ_Files_Path).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		String [] str_arr_files = new String [files.length];
		
		for(int count = 0; count< files.length; count++)
		{
			str_arr_files[count] = files[count].toString();
		}

		if(chk_Fast_GZ_files.isSelected())
		{
			arr_Str_GZ_FileNames = new String [files.length];
	        
	        for(int i=0; i<str_arr_files.length;i++)
	        {
	        	if(str_arr_files[i].toLowerCase().endsWith(".gz"))
	        	{
		        	arr_Str_GZ_FileNames[i] = str_arr_files[i] ;
	        	}
	        }
	        
	        
	        Arrays.sort(arr_Str_GZ_FileNames);
	        int_GZ_file_Count = arr_Str_GZ_FileNames.length; 
		}
		else
		{
	        arr_Str_FASTQ_FileNames = new String [files.length];

	        for(int i=0; i<str_arr_files.length;i++)
	        {
	        	if(str_arr_files[i].toLowerCase().endsWith(".fastq"))
	        	{
	        		arr_Str_FASTQ_FileNames[i] = str_arr_files[i] ;
	        	}
	        }
	        
	        Arrays.sort(arr_Str_FASTQ_FileNames);
	        int_FASTQ_file_Count = arr_Str_FASTQ_FileNames.length;
		}
	}

	/**
	 * All_Automatic_Run_Pipeline_TextBoxes_Filled()
	 * @return
	 */
	// Check for Null Textboxes All_Automatic_Run_Pipeline_TextBoxes_Filled
	public Boolean All_Automatic_Run_Pipeline_TextBoxes_Filled()
	{
		
		String temp_Str = txt_Cluster_Host.getText();
		
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter Host / Cluster Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			
			temp_Str = txt_Cluster_Host.getText();
			if(temp_Str.contains(" "))
			{
				JOptionPane.showMessageDialog(null, "Please do not put any space in Cluster Name!", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			
			temp_Str = txt_Cluster_User_Name.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter User Name", "Data Missing",  JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			temp_Str = txt_Cluster_User_Name.getText();
			if(temp_Str.contains(" "))
			{
				JOptionPane.showMessageDialog(null, "Please do not put any space in User Name!", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;

			}
			
			temp_Str = txt_WallTime.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter Walltime e.g. 06:30:25", "Data Missing",  JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			temp_Str = txt_nodes.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter nodes e.g. 1", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			temp_Str = txt_ppn.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter Processors per Node (ppn) e.g. 1", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			temp_Str = txt_Email.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter Email for Notification", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}

			str_Cluster_User_Name = txt_Cluster_User_Name.getText(); 
			str_Cluster_User_Password = txt_Cluster_User_Password.getText(); 
			str_Cluster_Host_Name = txt_Cluster_Host.getText(); // "data-cluster";
			str_walltime = txt_WallTime.getText();
			str_nodes = txt_nodes.getText();
			str_ppn = txt_ppn.getText();
			str_email = txt_Email.getText();
	
				
		temp_Str = txt_ProjectName.getText();
		if(temp_Str.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Project Name", "Data Missing",  JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		temp_Str = txt_ProjectName.getText();
		if(temp_Str.contains(" "))
		{
			JOptionPane.showMessageDialog(null, "Please do not put any space in Project Name!", "Data Missing", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		
		temp_Str = txt_InputFiles_Path_AutomaticPipeline.getText();
		if(temp_Str.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Path to the Files", "Data Missing", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		str_Project_Name = txt_ProjectName.getText();
		str_InputFiles_Path_AutomaticPipeline = txt_InputFiles_Path_AutomaticPipeline.getText();
		Job_Seq_Protocol = cmb_Seq_Protocol.getSelectedItem().toString();
		str_New_Output_Dir = txt_new_output_dir.getText();
		
		return true;
	} 	
	
	public void Enable_Disable_Pipeline_Job_Controls(boolean b_flag)
	{
		txt_Cluster_Host.setEnabled(b_flag);
		txt_Cluster_User_Name.setEnabled(b_flag);
		txt_Cluster_User_Password.setEnabled(b_flag);

		txt_WallTime.setEnabled(b_flag);
		txt_nodes.setEnabled(b_flag);
		txt_ppn.setEnabled(b_flag);
		txt_Email.setEnabled(b_flag);

		chk_MergeReplicates.setSelected(b_flag);
		
		chk_MergeReplicates.setEnabled(b_flag);
		chk_Multi_Queue_Job_Submissions.setEnabled(b_flag);
		chk_Put_in_Queue.setEnabled(b_flag);
		chk_Direct_Processing_without_Queue.setEnabled(b_flag);
		chk_Create_Queue_Jobs.setEnabled(b_flag);
		
		chk_Create_Softlink_and_Process.setEnabled(b_flag);
		chk_Copy_and_Process.setEnabled(b_flag);
		
	}
	
	// Check for sequence protocol
	public boolean Is_Sequence_Protocol_Valid(String str_Seq_Protocol)
	{
		boolean bool_Flag = false;
		if (str_Seq_Protocol.length() >= 2)
		{
			String str_Sub_Seq_Protocol = str_Seq_Protocol.substring(0,2);

			//Format 1xABC  or  2xABC
			if(str_Sub_Seq_Protocol.equals("2x") || str_Sub_Seq_Protocol.equals("1x"))
			{
				bool_Flag = true;
			}
		}
		return bool_Flag;
	}
	
	// Check for Null Texboxes For Cluster Login
		public Boolean OnlyLogin_Automatic_Run_Pipeline_TextBoxes_Filled()
		{
			String temp_Str = txt_Cluster_Host.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter Host / Cluster Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			temp_Str = txt_Cluster_User_Name.getText();
			if(temp_Str.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter User Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			
			return true;
		} 
		
	// Check for Null Texboxes All_Manual_Run_Pipeline_TextBoxes_Filled
	public Boolean All_Manual_Run_Pipeline_TextBoxes_Filled()
	{
		String temp_Str = txt_Cluster_Host.getText();
		if(temp_Str.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Host / Cluster Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		temp_Str = txt_Cluster_User_Name.getText();
		if(temp_Str.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter User Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		temp_Str = txt_ProjectName.getText();
		if(temp_Str.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Project Name", "Data Missing", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	//Redirects Console text to jTextArea1    
	  private void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        jTextArea1.append(text);
	      }
	    });
	  }
	// The Methods that do the Redirect 
	  private void redirectSystemStreams() {
	    OutputStream out = new OutputStream() {
	      @Override
	      public void write(int b) throws IOException {
	        updateTextArea(String.valueOf((char) b));
	      }
	 
	      @Override
	      public void write(byte[] b, int off, int len) throws IOException {
	        updateTextArea(new String(b, off, len));
	      }
	 
	      @Override
	      public void write(byte[] b) throws IOException {
	        write(b, 0, b.length);
	      }
	    };
	 
	    System.setOut(new PrintStream(out, true));
	    System.setErr(new PrintStream(out, true));
	  }

	  public void Auto_Input_Settings_File()
	  {
		  try
		  {
		  	// Return Path to OPEN FILE
			String str_File_Name_Path = "settings-iatac-v101.iatac";
			
			File obj_file = new File(str_File_Name_Path);
			if(obj_file.exists() && !obj_file.isDirectory()) 
			{ 
			    // do something
			
				FileReader reader = new FileReader(str_File_Name_Path);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str_Parameters = bufferedReader.readLine();
				reader.close();

				int int_Decision = JOptionPane.showConfirmDialog(null, "Do you want to load settings from settings-iatac-v101.iatac ?", "Warning", JOptionPane.YES_NO_OPTION);
				if (int_Decision == 0) 
				{
					String[] arr_Split_Param = str_Parameters.split("@");
					
					if(arr_Split_Param.length > 0 )
					{
						txt_Reference_Genome.setText(arr_Split_Param[0]);
						txt_FastQC_Location.setText(arr_Split_Param[1]);
			  		 	txt_Trimmomatic_Location.setText(arr_Split_Param[2]);
			  		 	txt_Trimmomatic_illumina_Adaptor.setText(arr_Split_Param[3]);
			  		 	txt_BWA_Location.setText(arr_Split_Param[4]);				
			  		 	txt_SortSam_Location.setText(arr_Split_Param[5]);			
			  		 	txt_MarkDuplicates_Location.setText(arr_Split_Param[6]);		
			  		 	txt_ColInsterSizeMetrics_Location.setText(arr_Split_Param[7]);	
			  		 	txt_BAMGapAlign_Location.setText(arr_Split_Param[8]);
			 			txt_Samtools_Location.setText(arr_Split_Param[9]);
			 			txt_Bedtools_Location.setText(arr_Split_Param[10]);
			 			txt_MACS_Location.setText(arr_Split_Param[11]);
			  		 	txt_new_output_dir.setText(arr_Split_Param[12]);				
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Incorrect Parameters", "Information" ,JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "settings-iatac-v101.iatac not found", "Information" ,JOptionPane.PLAIN_MESSAGE);
			}
 		}
 		catch(Exception ex)
 		{
 			System.out.print(ex);
 		}
	  }
	  
  
	/**
	 * Create the frame.
	 */
	public I_ATAC_ver101_Public() {
		setBackground(new Color(255, 255, 255));		
		
		setTitle("I-ATAC ver 1.0.1 - Public Release");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 1119, 646);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 1114, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 617, Short.MAX_VALUE)
		);
		
		JPanel JPanel_Automatic_Operations = new JPanel();
		JPanel_Automatic_Operations.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Process", null, JPanel_Automatic_Operations, null);
		
		txt_InputFiles_Path_AutomaticPipeline = new JTextField();
		txt_InputFiles_Path_AutomaticPipeline.setText("/ATAC_PROJECTS/gz_fastq_files");
		txt_InputFiles_Path_AutomaticPipeline.setBackground(new Color(255, 255, 255));
		txt_InputFiles_Path_AutomaticPipeline.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Input Directory");
		
		btn_Run_Automatic_ATACSeq_Pipeline = new JButton("Run ATAC Seq.");
		btn_Run_Automatic_ATACSeq_Pipeline.setForeground(new Color(0, 0, 102));
		btn_Run_Automatic_ATACSeq_Pipeline.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btn_Run_Automatic_ATACSeq_Pipeline.setIcon(new ImageIcon("/Users/user/Zeeshan/Development/ATACSeq_Workspace/ATAC/images/play_medium.png"));
		btn_Run_Automatic_ATACSeq_Pipeline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					//Code here\
					
					if (All_Automatic_Run_Pipeline_TextBoxes_Filled() == true)
					{
						String str_User_Information = "Hi " + str_Cluster_User_Name + ", its JAX-ATACSec-GUI" ;
						
						String str_Job_Information = "";
						
							if(chk_Create_Queue_Jobs.isSelected())
							{
								str_Job_Information =  "Here is your job description:" + "\n" +
										"-> Project: " + str_Project_Name + "\n" +
										"-> walltime: " + str_walltime + "\n" +
										"-> nodes: " + str_nodes + "\n" +
										"-> ppn: " + str_ppn + "\n" +
										"-> Input Files should be at: " + str_InputFiles_Path_AutomaticPipeline + "\n" +
										"-> Results will be at: " + str_New_Output_Dir + "/" + str_Project_Name + "\n\n" +
										"Do you want me submit it to the " + str_Cluster_Host_Name + " ?";	
							}
							else
							{
								str_Job_Information =  "Here is your job description:" + "\n" +
										"-> Project: " + str_Project_Name + "\n" +
										"-> Input Files should be at: " + str_InputFiles_Path_AutomaticPipeline + "\n" +
										"-> Results will be at: " + str_New_Output_Dir + "/" + str_Project_Name + "\n\n" +
										"Do you want me submit it to the " + str_Cluster_Host_Name + " ?";
							}
								
						txt_Output_Drectory_Location.setText(str_New_Output_Dir + "/" + str_Project_Name);
						
						int int_Decision = JOptionPane.showConfirmDialog(null, str_Job_Information, str_User_Information, JOptionPane.YES_NO_OPTION);

						if (int_Decision == 0) 
		                { 
							JtextArea_FilesNames_Status.setText(null);
		    				redirectSystemStreams();
		    				
		    				Automatized_ATAC_SecPipeline();
		            	} 
	
					}
					
				}
				catch(Exception ex){
					System.out.print(ex);
				}
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		chk_QC = new JCheckBox("FastQC");
		chk_QC.setSelected(true);
		chk_QC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chk_QC.isSelected()) {
				    bool_chk_QC = true;

				} else {
				 
					bool_chk_QC = false;
					//bool_chk_Trimmomatic = false;
					//bool_chk_BWA = false;
					//bool_chk_SamSort = false;
					//bool_chk_MarkDuplicates = false;
					//bool_chk_CollectInsertSizeMetrics = false;
					//bool_chk_ATAC_BAM_shifter_gappedAlign = false;
					//bool_chk_Samtools = false;
					//bool_chk_SamtoolsIndex = false;
					//bool_chk_BedTools = false;
					//bool_chk_Macs2 = false;
					
				    // Check Boxes
				    //chk_Trimmomatic.setSelected(false);
					//chk_BWA.setSelected(false);
					//chk_SamSort.setSelected(false);
					//chk_MarkDuplicates.setSelected(false);
					//chk_CollectInsertSizeMetrics.setSelected(false);
					//chk_Samtools.setSelected(false);
					//chk_SamtoolsIndex.setSelected(false);
					//chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
					//chk_BedTools.setSelected(false);
					//chk_Macs2.setSelected(false);
			    	
				}
			}
		});
		
		 chk_Trimmomatic = new JCheckBox("Trimmomatic");
		 chk_Trimmomatic.setSelected(true);
		 chk_Trimmomatic.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				if (chk_Trimmomatic.isSelected()) {
				    bool_chk_Trimmomatic = true;
				    
				    //bool_chk_QC = true;
				    //chk_QC.setSelected(true);

				} else {
				 
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = false;
						bool_chk_BWA = false;
						bool_chk_SamSort = false;
						bool_chk_MarkDuplicates = false;
						bool_chk_CollectInsertSizeMetrics = false;
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
						
					    // Check Boxes
						chk_BWA.setSelected(false);
						chk_SamSort.setSelected(false);
						chk_MarkDuplicates.setSelected(false);
						chk_CollectInsertSizeMetrics.setSelected(false);
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
						
					}
				}
		 	}
		 });
		 
		  chk_BWA = new JCheckBox("BWA");
		  chk_BWA.setSelected(true);
		  chk_BWA.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		if (chk_BWA.isSelected()) {	 
				    bool_chk_BWA = true;
				    
				    //bool_chk_QC = true;
				    //chk_QC.setSelected(true);

					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = true;				    
						chk_Trimmomatic.setSelected(true);
					}
				    
				} else {

					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_BWA = false;
						bool_chk_SamSort = false;
						bool_chk_MarkDuplicates = false;
						bool_chk_CollectInsertSizeMetrics = false;
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
					    // Check Boxes
						chk_SamSort.setSelected(false);
						chk_MarkDuplicates.setSelected(false);
						chk_CollectInsertSizeMetrics.setSelected(false);
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		  	}
		  });
		  
		  chk_SamSort = new JCheckBox("Sam Sort");
		  chk_SamSort.setSelected(true);
		  chk_SamSort.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		if (chk_SamSort.isSelected()) {	 
					bool_chk_SamSort = true;
					
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = true;
						chk_Trimmomatic.setSelected(true);
						
						bool_chk_BWA = true;
						chk_BWA.setSelected(true);
					}

				} else {
					if(chk_AutoCorrect.isSelected())
					{	
						bool_chk_SamSort = false;
						bool_chk_MarkDuplicates = false;
						bool_chk_CollectInsertSizeMetrics = false;
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
					    // Check Boxes
						chk_MarkDuplicates.setSelected(false);
						chk_CollectInsertSizeMetrics.setSelected(false);
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		  	}
		  });
		  
		   chk_MarkDuplicates = new JCheckBox("Mark Duplicates");
		   chk_MarkDuplicates.setSelected(true);
		   chk_MarkDuplicates.addActionListener(new ActionListener() {
		   	public void actionPerformed(ActionEvent e) {
		   		if (chk_MarkDuplicates.isSelected()) {	 
					bool_chk_MarkDuplicates = true;
					
					//bool_chk_QC = true;
					//chk_QC.setSelected(true);
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = true;
						chk_Trimmomatic.setSelected(true);
						
						bool_chk_BWA = true;
						chk_BWA.setSelected(true);
						
						bool_chk_SamSort = true;
						chk_SamSort.setSelected(true);
					}
				} else {
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_MarkDuplicates = false;
						bool_chk_CollectInsertSizeMetrics = false;
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
					    // Check Boxes
						chk_CollectInsertSizeMetrics.setSelected(false);
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		   	}
		   });
		   
		    chk_CollectInsertSizeMetrics = new JCheckBox("Insert Size");
		    chk_CollectInsertSizeMetrics.setSelected(true);
		    chk_CollectInsertSizeMetrics.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if (chk_CollectInsertSizeMetrics.isSelected()) {	 
					bool_chk_CollectInsertSizeMetrics = true;
					
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = true;
						chk_Trimmomatic.setSelected(true);
						
						bool_chk_BWA = true;
						chk_BWA.setSelected(true);
						
						bool_chk_SamSort = true;
						chk_SamSort.setSelected(true);
						
						bool_chk_MarkDuplicates = true;
					    chk_MarkDuplicates.setSelected(true);
					}
				    
				} else {
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_CollectInsertSizeMetrics = false;
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
					    // Check Boxes
						chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		    	}
		    });
		    
		
		    chk_ATAC_BAM_shifter_gappedAlign = new JCheckBox("BAM Shifter ");
		    chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);	
		    chk_ATAC_BAM_shifter_gappedAlign.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		if (chk_ATAC_BAM_shifter_gappedAlign.isSelected()) {	 
					bool_chk_ATAC_BAM_shifter_gappedAlign = true;
					
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Trimmomatic = true;
						chk_Trimmomatic.setSelected(true);
						
						bool_chk_BWA = true;
						chk_BWA.setSelected(true);
						
						bool_chk_SamSort = true;
						chk_SamSort.setSelected(true);
						
						bool_chk_MarkDuplicates = true;
					    chk_MarkDuplicates.setSelected(true);
						
						bool_chk_CollectInsertSizeMetrics = true;
					    chk_CollectInsertSizeMetrics.setSelected(true);
					}

				} else {
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_ATAC_BAM_shifter_gappedAlign = false;
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
					    // Check Boxes
						chk_Samtools.setSelected(false);
						chk_SamtoolsIndex.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		    	}
		    });
		    		 chk_Samtools = new JCheckBox("Sam tools");
		    		 chk_Samtools.setSelected(true);
		    		 chk_Samtools.addActionListener(new ActionListener() {
		    		 	public void actionPerformed(ActionEvent e) {
		    		 		if (chk_Samtools.isSelected()) 
		    		 		{	 
								bool_chk_Samtools = true;
								
								if(chk_AutoCorrect.isSelected())
								{
									bool_chk_Trimmomatic = true;
									chk_Trimmomatic.setSelected(true);
									
									bool_chk_BWA = true;
									chk_BWA.setSelected(true);
									
									bool_chk_SamSort = true;
									chk_SamSort.setSelected(true);
									
									bool_chk_MarkDuplicates = true;
								    chk_MarkDuplicates.setSelected(true);
									
									bool_chk_CollectInsertSizeMetrics = true;
								    chk_CollectInsertSizeMetrics.setSelected(true);
								    
									bool_chk_ATAC_BAM_shifter_gappedAlign = true;
								    chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);
								}
				    
				} else {
	
					if(chk_AutoCorrect.isSelected())
					{
						bool_chk_Samtools = false;
						bool_chk_SamtoolsIndex = false;
						bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
					    
						// Check Boxes
						chk_SamtoolsIndex.setSelected(false);
						chk_BedTools.setSelected(false);
						chk_Macs2.setSelected(false);
					}
				}
		    		 	}
		    		 });
		    		 
		    		 		
		 		 chk_SamtoolsIndex = new JCheckBox("Samtools Index");
		 		 chk_SamtoolsIndex.setSelected(true);
		 		 chk_SamtoolsIndex.addActionListener(new ActionListener() {
		 		 	public void actionPerformed(ActionEvent e) {
		 		 		if (chk_SamtoolsIndex.isSelected()) 
		 		 		{	 
							bool_chk_SamtoolsIndex = true;
							
							if(chk_AutoCorrect.isSelected())
							{
								bool_chk_Trimmomatic = true;
								chk_Trimmomatic.setSelected(true);
								
								bool_chk_BWA = true;
								chk_BWA.setSelected(true);
								
								bool_chk_SamSort = true;
								chk_SamSort.setSelected(true);
								
								bool_chk_MarkDuplicates = true;
							    chk_MarkDuplicates.setSelected(true);
								
								bool_chk_CollectInsertSizeMetrics = true;
							    chk_CollectInsertSizeMetrics.setSelected(true);
							    
								bool_chk_ATAC_BAM_shifter_gappedAlign = true;
							    chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);
							    
								bool_chk_Samtools = true;
							    chk_Samtools.setSelected(true);
							}
						    
						} else {
							if(chk_AutoCorrect.isSelected())
							{
								bool_chk_SamtoolsIndex = false;
								bool_chk_BedTools = false;
								bool_chk_Macs2 = false;
							    
							    // Check Boxes
								chk_BedTools.setSelected(false);
								chk_Macs2.setSelected(false);
							}
						}
		    		 		 	}
		    		 		 });
		    		 		 
		    		 		 
		 		  chk_BedTools = new JCheckBox("Bed Tools");
		 		  chk_BedTools.setSelected(true);
		 		  chk_BedTools.addActionListener(new ActionListener() {
		 		  	public void actionPerformed(ActionEvent e) {
		 		  		if (chk_BedTools.isSelected()) 
		 		  		{	 
							bool_chk_BedTools = true;
							
							if(chk_AutoCorrect.isSelected())
							{
								bool_chk_Trimmomatic = true;
								chk_Trimmomatic.setSelected(true);
								
								bool_chk_BWA = true;
								chk_BWA.setSelected(true);
								
								bool_chk_SamSort = true;
								chk_SamSort.setSelected(true);
								
								bool_chk_MarkDuplicates = true;
							    chk_MarkDuplicates.setSelected(true);
								
								bool_chk_CollectInsertSizeMetrics = true;
							    chk_CollectInsertSizeMetrics.setSelected(true);
							    
								bool_chk_ATAC_BAM_shifter_gappedAlign = true;
							    chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);
							    
								bool_chk_Samtools = true;
							    chk_Samtools.setSelected(true);
								
								bool_chk_SamtoolsIndex = true;
							    chk_SamtoolsIndex.setSelected(true);
							}
				    
				} else {
					if(chk_AutoCorrect.isSelected())
					{
					    bool_chk_BedTools = false;
						bool_chk_Macs2 = false;
	
					    // Check Boxes
						chk_Macs2.setSelected(false);
					}
				}
		    		 		  	}
		    		 		  });
		    		 		  
		    		 		  		
 		  		 chk_Macs2 = new JCheckBox("Macs2: Peak Calling");
 		  		 chk_Macs2.setSelected(true);
 		  		 chk_Macs2.addActionListener(new ActionListener() {
 		  		 	public void actionPerformed(ActionEvent e) {
 		  		 		if (chk_Macs2.isSelected()) 
 		  		 		{	 
							bool_chk_Macs2 = true;

							if(chk_AutoCorrect.isSelected())
							{
								bool_chk_Trimmomatic = true;
								chk_Trimmomatic.setSelected(true);
								
								bool_chk_BWA = true;
								chk_BWA.setSelected(true);
								
								bool_chk_SamSort = true;
								chk_SamSort.setSelected(true);
								
								bool_chk_MarkDuplicates = true;
							    chk_MarkDuplicates.setSelected(true);
								
								bool_chk_CollectInsertSizeMetrics = true;
							    chk_CollectInsertSizeMetrics.setSelected(true);
							    
								bool_chk_ATAC_BAM_shifter_gappedAlign = true;
							    chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);
							    
								bool_chk_Samtools = true;
							    chk_Samtools.setSelected(true);
								
								bool_chk_SamtoolsIndex = true;
							    chk_SamtoolsIndex.setSelected(true);
							    
								bool_chk_BedTools = true;
								chk_BedTools.setSelected(true);
							}
				    
				} else {
				    bool_chk_Macs2 = false;
				}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_CheckAll = new JCheckBox("All");
		    		 		  		 chk_CheckAll.setSelected(true);
		    		 		  		 chk_CheckAll.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		if(chk_CheckAll.isSelected())
		    		 		  		 		{
		    		 		  		 			// Check Boxes
		    		 		  		 			bool_chk_QC = true;
		    		 		  		 			bool_chk_Trimmomatic = true;
		    		 		  		 			bool_chk_BWA = true;
		    		 		  		 			bool_chk_SamSort = true;
		    		 		  		 			bool_chk_MarkDuplicates = true;
		    		 		  		 			bool_chk_CollectInsertSizeMetrics = true;
		    		 		  		 			bool_chk_ATAC_BAM_shifter_gappedAlign = true;
		    		 		  		 			bool_chk_Samtools = true;
		    		 		  		 			bool_chk_SamtoolsIndex = true;
		    		 		  		 			bool_chk_BedTools = true;
		    		 		  		 			bool_chk_Macs2 = true;
		    		 		  		 			
		    		 		  		 			chk_QC.setSelected(true);
		    		 		  		 		    chk_Trimmomatic.setSelected(true);
		    		 		  		 			chk_BWA.setSelected(true);
		    		 		  		 			chk_SamSort.setSelected(true);
		    		 		  		 			chk_MarkDuplicates.setSelected(true);
		    		 		  		 			chk_CollectInsertSizeMetrics.setSelected(true);
		    		 		  		 			chk_Samtools.setSelected(true);
		    		 		  		 			chk_SamtoolsIndex.setSelected(true);
		    		 		  		 			chk_ATAC_BAM_shifter_gappedAlign.setSelected(true);
		    		 		  		 			chk_BedTools.setSelected(true);
		    		 		  		 			chk_Macs2.setSelected(true);
		    		 		  		 		}
		    		 		  		 		else
		    		 		  		 		{
		    		 		  		 			// Check Boxes
		    		 		  		 			bool_chk_QC = false;
		    		 		  		 			bool_chk_Trimmomatic = false;
		    		 		  		 			bool_chk_BWA = false;
		    		 		  		 			bool_chk_SamSort = false;
		    		 		  		 			bool_chk_MarkDuplicates = false;
		    		 		  		 			bool_chk_CollectInsertSizeMetrics = false;
		    		 		  		 			bool_chk_ATAC_BAM_shifter_gappedAlign = false;
		    		 		  		 			bool_chk_Samtools = false;
		    		 		  		 			bool_chk_SamtoolsIndex = false;
		    		 		  		 			bool_chk_BedTools = false;
		    		 		  		 			bool_chk_Macs2 = false;
		    		 		  		 			
		    		 		  		 			chk_QC.setSelected(false);
		    		 		  		 		    chk_Trimmomatic.setSelected(false);
		    		 		  		 			chk_BWA.setSelected(false);
		    		 		  		 			chk_SamSort.setSelected(false);
		    		 		  		 			chk_MarkDuplicates.setSelected(false);
		    		 		  		 			chk_CollectInsertSizeMetrics.setSelected(false);
		    		 		  		 			chk_Samtools.setSelected(false);
		    		 		  		 			chk_SamtoolsIndex.setSelected(false);
		    		 		  		 			chk_ATAC_BAM_shifter_gappedAlign.setSelected(false);
		    		 		  		 			chk_BedTools.setSelected(false);
		    		 		  		 			chk_Macs2.setSelected(false);
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 txt_Output_Drectory_Location = new JTextField();
		    		 		  		 txt_Output_Drectory_Location.setColumns(10);
		    		 		  		 
		    		 		  		 lblNewLabel_23 = new JLabel("Output Directory");
		    		 		  		 
		    		 		  		 JScrollPane scrollPane = new JScrollPane();
		    		 		  		 
		    		 		  		 jTextArea1 = new JTextArea();
		    		 		  		 jTextArea1.setBackground(new Color(0, 0, 102));
		    		 		  		 jTextArea1.setForeground(new Color(255, 255, 255));
		    		 		  		 scrollPane.setViewportView(jTextArea1);
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_1 = new JLabel("Please write for assistance and troubleshooting at: zeeshan.ahmed@jax.org OR please call at: 860-837-2063");
		    		 		  		 lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		    		 		  		 
		    		 		  		 lblNewLabel_12 = new JLabel("I-ATAC ver. 1.0.1 is developed by Dr. Zeeshan AHMED");
		    		 		  		 lblNewLabel_12.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		    		 		  		 
		    		 		  		 lblNewLabel_22 = new JLabel("Seq. Protocol");
		    		 		  		 
		    		 		  		 lblNewLabel_13 = new JLabel("Project");
		    		 		  		 
		    		 		  		 txt_ProjectName = new JTextField();
		    		 		  		 txt_ProjectName.setText("MACProject");
		    		 		  		 txt_ProjectName.setForeground(new Color(0, 0, 102));
		    		 		  		 txt_ProjectName.setBackground(new Color(240, 248, 255));
		    		 		  		 txt_ProjectName.setColumns(10);
		    		 		  		 
		    		 		  		 lblNewLabel_20 = new JLabel("");
		    		 		  		 lblNewLabel_20.setIcon(new ImageIcon("/Users/user/Zeeshan/Development/ATACSeq_Workspace/ATAC/images/jax_logo.png"));
		    		 		  		 
		    		 		  		 cmb_Seq_Protocol = new JComboBox();
		    		 		  		 cmb_Seq_Protocol.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		    		 		  		 
		    		 		  		 chk_AutoCorrect = new JCheckBox("Auto Correct");
		    		 		  		 chk_AutoCorrect.setSelected(true);
		    		 		  		 GroupLayout gl_JPanel_Automatic_Operations = new GroupLayout(JPanel_Automatic_Operations);
		    		 		  		 gl_JPanel_Automatic_Operations.setHorizontalGroup(
		    		 		  		 	gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 			.addContainerGap()
		    		 		  		 			.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1083, Short.MAX_VALUE)
		    		 		  		 				.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING, false)
		    		 		  		 					.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 						.addComponent(lblNewLabel_1)
		    		 		  		 						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    		 		  		 						.addComponent(lblNewLabel_12))
		    		 		  		 					.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 						.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 							.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 								.addComponent(lblNewLabel_22)
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(cmb_Seq_Protocol, 0, 73, Short.MAX_VALUE)
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(lblNewLabel_13))
		    		 		  		 							.addComponent(btn_Run_Automatic_ATACSeq_Pipeline, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
		    		 		  		 						.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 						.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 							.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 								.addComponent(txt_ProjectName, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(lblNewLabel_3)
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(txt_InputFiles_Path_AutomaticPipeline, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
		    		 		  		 							.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 								.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 									.addComponent(chk_QC)
		    		 		  		 									.addComponent(chk_ATAC_BAM_shifter_gappedAlign)
		    		 		  		 									.addComponent(chk_CheckAll))
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 									.addComponent(chk_Trimmomatic, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
		    		 		  		 									.addComponent(chk_Samtools, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
		    		 		  		 									.addComponent(chk_AutoCorrect))
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 									.addComponent(chk_SamtoolsIndex, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
		    		 		  		 									.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.TRAILING)
		    		 		  		 										.addComponent(lblNewLabel_23)
		    		 		  		 										.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 											.addComponent(chk_BWA, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
		    		 		  		 											.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 											.addComponent(chk_SamSort, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 									.addComponent(txt_Output_Drectory_Location, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
		    		 		  		 									.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 										.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 											.addComponent(chk_MarkDuplicates, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
		    		 		  		 											.addComponent(chk_BedTools, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
		    		 		  		 										.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 										.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 											.addComponent(chk_Macs2, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
		    		 		  		 											.addComponent(chk_CollectInsertSizeMetrics))
		    		 		  		 										.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
		    		 		  		 										.addComponent(lblNewLabel_20)))))))
		    		 		  		 				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 1077, GroupLayout.PREFERRED_SIZE))
		    		 		  		 			.addContainerGap())
		    		 		  		 );
		    		 		  		 gl_JPanel_Automatic_Operations.setVerticalGroup(
		    		 		  		 	gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 			.addContainerGap()
		    		 		  		 			.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.TRAILING)
		    		 		  		 				.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.BASELINE)
		    		 		  		 					.addComponent(lblNewLabel_22)
		    		 		  		 					.addComponent(txt_ProjectName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 					.addComponent(lblNewLabel_3)
		    		 		  		 					.addComponent(cmb_Seq_Protocol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 					.addComponent(lblNewLabel_13))
		    		 		  		 				.addComponent(txt_InputFiles_Path_AutomaticPipeline, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
		    		 		  		 			.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 			.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.TRAILING)
		    		 		  		 				.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 					.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(chk_CheckAll)
		    		 		  		 						.addComponent(txt_Output_Drectory_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel_23)
		    		 		  		 						.addComponent(chk_AutoCorrect))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.TRAILING)
		    		 		  		 						.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 							.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.BASELINE)
		    		 		  		 								.addComponent(chk_QC)
		    		 		  		 								.addComponent(chk_Trimmomatic)
		    		 		  		 								.addComponent(chk_BWA)
		    		 		  		 								.addComponent(chk_SamSort)
		    		 		  		 								.addComponent(chk_MarkDuplicates)
		    		 		  		 								.addComponent(chk_CollectInsertSizeMetrics))
		    		 		  		 							.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 							.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.BASELINE)
		    		 		  		 								.addComponent(chk_Macs2)
		    		 		  		 								.addComponent(chk_BedTools)
		    		 		  		 								.addComponent(chk_SamtoolsIndex)
		    		 		  		 								.addComponent(chk_Samtools)
		    		 		  		 								.addComponent(chk_ATAC_BAM_shifter_gappedAlign)))
		    		 		  		 						.addComponent(lblNewLabel_20))
		    		 		  		 					.addGap(20))
		    		 		  		 				.addGroup(gl_JPanel_Automatic_Operations.createSequentialGroup()
		    		 		  		 					.addComponent(btn_Run_Automatic_ATACSeq_Pipeline, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
		    		 		  		 					.addGap(26)))
		    		 		  		 			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addGroup(gl_JPanel_Automatic_Operations.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(lblNewLabel_1)
		    		 		  		 				.addComponent(lblNewLabel_12))
		    		 		  		 			.addGap(248))
		    		 		  		 );
		    		 		  		 
		    		 		  		 JtextArea_FilesNames_Status = new JTextArea();
		    		 		  		 JtextArea_FilesNames_Status.setForeground(new Color(0, 0, 102));
		    		 		  		 JtextArea_FilesNames_Status.setBackground(new Color(240, 248, 255));
		    		 		  		 scrollPane_1.setViewportView(JtextArea_FilesNames_Status);
		    		 		  		 JPanel_Automatic_Operations.setLayout(gl_JPanel_Automatic_Operations);
		    		 		  		 
		    		 		  		 JPanel_Set_Paths = new JPanel();
		    		 		  		 JPanel_Set_Paths.setBackground(new Color(255, 255, 255));
		    		 		  		 tabbedPane.addTab("Settings", null, JPanel_Set_Paths, null);
		    		 		  		 
		    		 		  		 txt_FastQC_Location = new JTextField();
		    		 		  		 txt_FastQC_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_FastQC_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_FastQC_Location.setText("/software/FastQC/fastqc");
		    		 		  		 txt_FastQC_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_FastQC_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_Trimmomatic_Location = new JTextField();
		    		 		  		 txt_Trimmomatic_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_Trimmomatic_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_Trimmomatic_Location.setText("/software/Trimmomatic-0.32/trimmomatic-0.32.jar");
		    		 		  		 txt_Trimmomatic_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_Trimmomatic_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_BWA_Location = new JTextField();
		    		 		  		 txt_BWA_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_BWA_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_BWA_Location.setText("/software/bwa-0.7.15/bwa");
		    		 		  		 txt_BWA_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_BWA_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_SortSam_Location = new JTextField();
		    		 		  		 txt_SortSam_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_SortSam_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_SortSam_Location.setText("/software/picard/1.95/SortSam.jar");
		    		 		  		 txt_SortSam_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_SortSam_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_MarkDuplicates_Location = new JTextField();
		    		 		  		 txt_MarkDuplicates_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_MarkDuplicates_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_MarkDuplicates_Location.setText("/software/picard/1.95/MarkDuplicates.jar");
		    		 		  		 txt_MarkDuplicates_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_MarkDuplicates_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_ColInsterSizeMetrics_Location = new JTextField();
		    		 		  		 txt_ColInsterSizeMetrics_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_ColInsterSizeMetrics_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_ColInsterSizeMetrics_Location.setText("/software/picard/1.95/CollectInsertSizeMetrics.jar");
		    		 		  		 txt_ColInsterSizeMetrics_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_ColInsterSizeMetrics_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_BAMGapAlign_Location = new JTextField();
		    		 		  		 txt_BAMGapAlign_Location.setFont(new Font("Dialog", Font.PLAIN, 12));
		    		 		  		 txt_BAMGapAlign_Location.setForeground(new Color(0, 0, 0));
		    		 		  		 txt_BAMGapAlign_Location.setText("/software/ATAC_BAM_shifter_gappedAlign/ATAC_BAM_shifter_gappedAlign.pl");
		    		 		  		 txt_BAMGapAlign_Location.setBackground(new Color(255, 255, 255));
		    		 		  		 txt_BAMGapAlign_Location.setColumns(10);
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_5 = new JLabel("FastQC");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_6 = new JLabel("Trimmomatic");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_7 = new JLabel("BWA");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_8 = new JLabel("Picard SortSam");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_9 = new JLabel("Mark Duplicates");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_10 = new JLabel("Insert Size Metrics");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_11 = new JLabel("BAM Gap Align");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_2 = new JLabel("Reference Genome");
		    		 		  		 
		    		 		  		 txt_Reference_Genome = new JTextField();
		    		 		  		 txt_Reference_Genome.setText("/INDEXES/HUMAN/BWA/hg19.fa");
		    		 		  		 txt_Reference_Genome.setBackground(new Color(204, 255, 204));
		    		 		  		 txt_Reference_Genome.setColumns(10);
		    		 		  		 
		    		 		  		 txt_new_output_dir = new JTextField();
		    		 		  		 txt_new_output_dir.setBackground(new Color(248, 248, 255));
		    		 		  		 txt_new_output_dir.setText("/ATAC_PROJECTS");
		    		 		  		 txt_new_output_dir.setColumns(10);
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_14 = new JLabel("Output Directory");
		    		 		  		 
		    		 		  		 panel = new JPanel();
		    		 		  		 panel.setBackground(new Color(248, 248, 255));
		    		 		  		 
		    		 		  		 txt_Trimmomatic_illumina_Adaptor = new JTextField();
		    		 		  		 txt_Trimmomatic_illumina_Adaptor.setText("/software/Trimmomatic-0.32/adapters/NexteraPE-PE.fa");
		    		 		  		 txt_Trimmomatic_illumina_Adaptor.setColumns(10);
		    		 		  		 
		    		 		  		 lblNewLabel = new JLabel("");
		    		 		  		 
		    		 		  		 lblNewLabel_4 = new JLabel("Trim. Adapters");
		    		 		  		 
		    		 		  		 txt_Samtools_Location = new JTextField();
		    		 		  		 txt_Samtools_Location.setText("samtools");
		    		 		  		 txt_Samtools_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_Bedtools_Location = new JTextField();
		    		 		  		 txt_Bedtools_Location.setText("bedtools");
		    		 		  		 txt_Bedtools_Location.setColumns(10);
		    		 		  		 
		    		 		  		 txt_MACS_Location = new JTextField();
		    		 		  		 txt_MACS_Location.setText("macs2");
		    		 		  		 txt_MACS_Location.setColumns(10);
		    		 		  		 
		    		 		  		 lbl_Samtools = new JLabel("Samtools");
		    		 		  		 
		    		 		  		 lblNewLabel_15 = new JLabel("Bedtools");
		    		 		  		 
		    		 		  		 lblNewLabel_24 = new JLabel("MACS");
		    		 		  		 
		    		 		  		 JButton btn_Save_Parameters = new JButton("Save Parameters into File");
		    		 		  		 btn_Save_Parameters.setBackground(new Color(153, 204, 255));
		    		 		  		 btn_Save_Parameters.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) 
		    		 		  		 	{
			    		 		  		 	try
		    		 		  		 		{
			    								String str_File_Path_Name = ""; //"JAX-user.xlsx";//name of excel file

			    		 		  		 		JFileChooser chooser = new JFileChooser();
				    		 		  		 	int retrival = chooser.showSaveDialog(null);
				    						    if (retrival == JFileChooser.APPROVE_OPTION) 
				    						    {
				    						    	str_File_Path_Name = chooser.getSelectedFile().toString();
				    						    	str_File_Path_Name = str_File_Path_Name + ".iatac";
				    						    			
				    								//JOptionPane.showMessageDialog(null, str_File_Path_Name, "Information" ,JOptionPane.PLAIN_MESSAGE);
				    								
				    								String str_Parameters = txt_Reference_Genome.getText();
				    								str_Parameters = str_Parameters + "@" +txt_FastQC_Location.getText();			    								 
			    								 	str_Parameters = str_Parameters + "@" + txt_Trimmomatic_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" + txt_Trimmomatic_illumina_Adaptor.getText();
			    								 	str_Parameters = str_Parameters + "@" + txt_BWA_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" + txt_SortSam_Location.getText();	
			    								 	str_Parameters = str_Parameters + "@" + txt_MarkDuplicates_Location.getText(); 	
			    								 	str_Parameters = str_Parameters + "@" + txt_ColInsterSizeMetrics_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" + txt_BAMGapAlign_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" +	txt_Samtools_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" +	txt_Bedtools_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" +	txt_MACS_Location.getText();
			    								 	str_Parameters = str_Parameters + "@" + txt_new_output_dir.getText();				
				    								
				    								FileWriter writer = new FileWriter(str_File_Path_Name, true);
				    								writer.write(str_Parameters);
				    								writer.close();


				    						    }
				    						    
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			System.out.print(ex);
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 JButton btn_Load_Parameters = new JButton("Load Parameters from File");
		    		 		  		 btn_Load_Parameters.setBackground(new Color(153, 204, 255));
		    		 		  		 btn_Load_Parameters.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) 
		    		 		  		 	{
			    		 		  		 	try
		    		 		  		 		{
			    		 		  				int int_Decision = JOptionPane.showConfirmDialog(null, "Do you really want to load settings from file ?", "Warning", JOptionPane.YES_NO_OPTION);
			    		 						if (int_Decision == 0) 
			    		 		                {
				    		 		  		 		String str_Parameters = "";
				    		 		  		 		
				    		 		  		 		JFileChooser fileChooser = new JFileChooser();
				    		 		  		 		int returnValue = fileChooser.showOpenDialog(null);			
				    		 		  		 	
				    		 		  		 		if (returnValue == JFileChooser.APPROVE_OPTION) 
				    		 		  		 		{
				    		 		  		 			// Return Path to OPEN FILE
				    		 							String str_File_Name_Path = fileChooser.getSelectedFile().toString();
				    		 							
				    		 							FileReader reader = new FileReader(str_File_Name_Path);
				    		 							BufferedReader bufferedReader = new BufferedReader(reader);
				    		 							
				    		 							str_Parameters = bufferedReader.readLine();
				    		 							reader.close();
				    		 							
				    		 							//JOptionPane.showMessageDialog(null, str_Parameters, "Information" ,JOptionPane.PLAIN_MESSAGE);
				    		 							
				    		 							String[] arr_Split_Param = str_Parameters.split("@");

				    		 							
				    		 							if(arr_Split_Param.length > 0 )
				    		 							{
				    		 								txt_Reference_Genome.setText(arr_Split_Param[0]);
				    		 								txt_FastQC_Location.setText(arr_Split_Param[1]);
							    		 		  		 	txt_Trimmomatic_Location.setText(arr_Split_Param[2]);
							    		 		  		 	txt_Trimmomatic_illumina_Adaptor.setText(arr_Split_Param[3]);
							    		 		  		 	txt_BWA_Location.setText(arr_Split_Param[4]);				
							    		 		  		 	txt_SortSam_Location.setText(arr_Split_Param[5]);			
							    		 		  		 	txt_MarkDuplicates_Location.setText(arr_Split_Param[6]);		
							    		 		  		 	txt_ColInsterSizeMetrics_Location.setText(arr_Split_Param[7]);	
							    		 		  		 	txt_BAMGapAlign_Location.setText(arr_Split_Param[8]);
							    		 		 			txt_Samtools_Location.setText(arr_Split_Param[9]);
					    		 		  		 			txt_Bedtools_Location.setText(arr_Split_Param[10]);
					    		 		  		 			txt_MACS_Location.setText(arr_Split_Param[11]);
				 			    		 		  		 	txt_new_output_dir.setText(arr_Split_Param[12]);				
				    		 							}
				    		 							else
				    		 							{
				    		 								JOptionPane.showMessageDialog(null, "Incorrect Parameters", "Information" ,JOptionPane.PLAIN_MESSAGE);
				    		 							}
			    		 							
				    		 		  		 		}
			    		 		  		 		}
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			System.out.print(ex);
		    		 		  		 		}
		    		 		  		 		
		    		 		  		 	
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 JButton btn_Clear_Parameters = new JButton("Clear Parameters");
		    		 		  		 btn_Clear_Parameters.setBackground(new Color(153, 204, 255));
		    		 		  		 btn_Clear_Parameters.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) 
		    		 		  		 	{
			    		 		  		 	try
		    		 		  		 		{
			    		 						int int_Decision = JOptionPane.showConfirmDialog(null, "Do you really want to clear settings ?", "Warning", JOptionPane.YES_NO_OPTION);
			    		 						if (int_Decision == 0) 
			    		 		                {
			    		 							txt_Reference_Genome.setText("");
				    		 		  		 		txt_FastQC_Location.setText("");
					    		 		  		 	txt_Trimmomatic_Location.setText("");		
					    		 		  		 	txt_Trimmomatic_illumina_Adaptor.setText("");
					    		 		  		 	txt_BWA_Location.setText("");				
					    		 		  		 	txt_SortSam_Location.setText("");			
					    		 		  		 	txt_MarkDuplicates_Location.setText("");		
					    		 		  		 	txt_ColInsterSizeMetrics_Location.setText("");	
					    		 		  		 	txt_BAMGapAlign_Location.setText("");
					    		 		 			txt_Samtools_Location.setText("");
			    		 		  		 			txt_Bedtools_Location.setText("");
			    		 		  		 			txt_MACS_Location.setText("");
					    		 		  		 	txt_new_output_dir.setText("");	
			    		 		                }
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			System.out.print(ex);
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 JButton btn_Set_Defautl_Parameters = new JButton("Defualt Parameters");
		    		 		  		 btn_Set_Defautl_Parameters.setBackground(new Color(153, 204, 255));
		    		 		  		 btn_Set_Defautl_Parameters.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
			    		 						int int_Decision = JOptionPane.showConfirmDialog(null, "Do you really want to load default settings ?", "Warning", JOptionPane.YES_NO_OPTION);
			    		 						if (int_Decision == 0) 
			    		 		                {
			    		 		  		 			txt_Reference_Genome.setText("/INDEXES/HUMAN/BWA/hg19.fa");

			    		 		  		 			txt_FastQC_Location.setText("/software/FastQC/fastqc");
					    		 		  		 	txt_Trimmomatic_Location.setText("/software/Trimmomatic-0.32/trimmomatic-0.32.jar");
					    		 		  		 	txt_Trimmomatic_illumina_Adaptor.setText("/software/Trimmomatic-0.32/adapters/NexteraPE-PE.fa");
					    		 		  		 	txt_BWA_Location.setText("/software/bwa-0.7.10/bwa");				
					    		 		  		 	txt_SortSam_Location.setText("/software/picard/1.95/SortSam.jar");			
					    		 		  		 	txt_MarkDuplicates_Location.setText("/software/picard/1.95/MarkDuplicates.jar");		
					    		 		  		 	txt_ColInsterSizeMetrics_Location.setText("/software/picard/1.95/CollectInsertSizeMetrics.jar");	
					    		 		  		 	txt_BAMGapAlign_Location.setText("/software/ATAC_BAM_shifter_gappedAlign/ATAC_BAM_shifter_gappedAlign.pl");
					    		 		  		 	txt_Samtools_Location.setText("/software/samtools-1.3.1/samtools");		
					    		 		  		 	txt_Bedtools_Location.setText("/software/bedtools2/bin/bedtools");
					    		 		  		 	txt_MACS_Location.setText("/software/MACS2-2.1.0.20140616/bin/macs2");
					    		 		  		 	
					    		 		  		 	txt_new_output_dir.setText("/ATAC_PROJECTS");

			    		 		                }
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			System.out.print(ex);
		    		 		  		 		}
		    		 		  		 		
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 JButton btnNewButton = new JButton("Reset Paths");
		    		 		  		 btnNewButton.setForeground(new Color(0, 0, 102));
		    		 		  		 btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		    		 		  		 btnNewButton.setIcon(new ImageIcon("/Users/user/Zeeshan/Development/ATACSeq_Workspace/ATAC/images/set_path_64.png"));
		    		 		  		 btnNewButton.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			int int_Decision = JOptionPane.showConfirmDialog(null, "Do you really want to reset settings ?", "Warning", JOptionPane.YES_NO_OPTION);
			    		 						if (int_Decision == 0) 
			    		 		                {
			    		 							
			    		 		  		 			str_SW_FastQC_Location = txt_FastQC_Location.getText();
			    		 		  		 			
			    		 		  		 			str_SW_Trimmomatic_Location = txt_Trimmomatic_Location.getText();
			    		 		  		 			
			    		 		  		 			str_Trimmomatic_ILLUMINACLIP_PE = " ILLUMINACLIP:" + txt_Trimmomatic_illumina_Adaptor.getText() + ":2:30:10 "; //" ILLUMINACLIP:/software/Trimmomatic-0.32/adapters/NexteraPE-PE.fa:2:30:10 ";
			    		 		  		 			str_Trimmomatic_ILLUMINACLIP_SE = " ILLUMINACLIP:" + txt_Trimmomatic_illumina_Adaptor.getText() + ":2:30:10 ";
			    		 		  		 	
			    		 		  		 			str_SW_BWA_Location = txt_BWA_Location.getText();
			    		 		  		 			
			    		 		  		 			str_SW_SortSam_Location = txt_SortSam_Location.getText();
			    		 		  		 			str_SW_MarkDuplicates_Location = txt_MarkDuplicates_Location.getText();
			    		 		  		 			str_SW_CollectInsertSizeMetrics_Location = txt_ColInsterSizeMetrics_Location.getText();
			    		 		  		 			str_SW_ATAC_BAM_shifter_gappedAlign_Location = txt_BAMGapAlign_Location.getText();
			    		 		  		 			str_SW_samtools_Location = txt_Samtools_Location.getText();
			    		 		  		 			str_SW_bedtools_Location = txt_Bedtools_Location.getText();
			    		 		  		 			str_SW_MACS2_Location = txt_MACS_Location.getText();
			    		 		  		 			
			    		 		  		 			str_New_Output_Dir = txt_new_output_dir.getText();
			    		 		  		 			
			    		 		  		 			//str_Dir_Data = txt_Drive.getText(); 	//"/data";
			    		 		  		 			//str_Home_Dir = txt_Drive.getText();		//"/data";
			    		 		  		 			
			    		 		  		 			str_Reference_Genome = txt_Reference_Genome.getText();
			    		 		  		 			
			    		 		  		 			//str_Output_Dir = txt_Output_Directory.getText();			    		 		  		 			
			    		 		  		 							//txt_User_Driectory		//""
			    		 							
			    		 		                }
		    		 		  		 			
		    		 		  		 		}
		    		 		  		 		catch(Exception ex){
		    		 		  		 			System.out.print(ex);
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 GroupLayout gl_JPanel_Set_Paths = new GroupLayout(JPanel_Set_Paths);
		    		 		  		 gl_JPanel_Set_Paths.setHorizontalGroup(
		    		 		  		 	gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 			.addGap(6)
		    		 		  		 			.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 				.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 					.addComponent(lblNewLabel_2)
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addComponent(txt_Reference_Genome, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
		    		 		  		 				.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.TRAILING)
		    		 		  		 						.addComponent(lblNewLabel_24)
		    		 		  		 						.addComponent(lblNewLabel_15)
		    		 		  		 						.addComponent(lbl_Samtools)
		    		 		  		 						.addComponent(lblNewLabel_6)
		    		 		  		 						.addComponent(lblNewLabel_5)
		    		 		  		 						.addComponent(lblNewLabel)
		    		 		  		 						.addComponent(lblNewLabel_4)
		    		 		  		 						.addComponent(lblNewLabel_7)
		    		 		  		 						.addComponent(lblNewLabel_8)
		    		 		  		 						.addComponent(lblNewLabel_9)
		    		 		  		 						.addComponent(lblNewLabel_10)
		    		 		  		 						.addComponent(lblNewLabel_11)
		    		 		  		 						.addComponent(lblNewLabel_14))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 						.addComponent(txt_MarkDuplicates_Location, 743, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_SortSam_Location, 743, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_BWA_Location, 743, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_Trimmomatic_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 							.addComponent(txt_FastQC_Location, 743, 743, Short.MAX_VALUE)
		    		 		  		 							.addPreferredGap(ComponentPlacement.RELATED))
		    		 		  		 						.addComponent(txt_Trimmomatic_illumina_Adaptor, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_new_output_dir, 743, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_Samtools_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_BAMGapAlign_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_ColInsterSizeMetrics_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_Bedtools_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_MACS_Location, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
		    		 		  		 						.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING, false)
		    		 		  		 							.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 								.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.TRAILING, false)
		    		 		  		 									.addComponent(btn_Clear_Parameters, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    		 		  		 									.addComponent(btn_Set_Defautl_Parameters, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		    		 		  		 							.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 								.addComponent(btn_Save_Parameters, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
		    		 		  		 								.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 								.addComponent(btn_Load_Parameters))))))
		    		 		  		 			.addGap(12)
		    		 		  		 			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
		    		 		  		 			.addContainerGap())
		    		 		  		 );
		    		 		  		 gl_JPanel_Set_Paths.setVerticalGroup(
		    		 		  		 	gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 			.addContainerGap()
		    		 		  		 			.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 544, GroupLayout.PREFERRED_SIZE)
		    		 		  		 				.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_2)
		    		 		  		 						.addComponent(txt_Reference_Genome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addGap(9)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_5)
		    		 		  		 						.addComponent(txt_FastQC_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_Trimmomatic_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel_6))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_Trimmomatic_illumina_Adaptor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel)
		    		 		  		 						.addComponent(lblNewLabel_4))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_7)
		    		 		  		 						.addComponent(txt_BWA_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_8)
		    		 		  		 						.addComponent(txt_SortSam_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_9)
		    		 		  		 						.addComponent(txt_MarkDuplicates_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 						.addComponent(lblNewLabel_10)
		    		 		  		 						.addComponent(txt_ColInsterSizeMetrics_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(lblNewLabel_11)
		    		 		  		 						.addComponent(txt_BAMGapAlign_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_Samtools_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lbl_Samtools))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_Bedtools_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel_15))
		    		 		  		 					.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_MACS_Location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel_24))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(txt_new_output_dir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addComponent(lblNewLabel_14))
		    		 		  		 					.addPreferredGap(ComponentPlacement.UNRELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.BASELINE)
		    		 		  		 						.addComponent(btn_Load_Parameters)
		    		 		  		 						.addComponent(btn_Save_Parameters))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_JPanel_Set_Paths.createParallelGroup(Alignment.LEADING)
		    		 		  		 						.addGroup(gl_JPanel_Set_Paths.createSequentialGroup()
		    		 		  		 							.addComponent(btn_Set_Defautl_Parameters)
		    		 		  		 							.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 							.addComponent(btn_Clear_Parameters))
		    		 		  		 						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))))
		    		 		  		 			.addContainerGap(21, Short.MAX_VALUE))
		    		 		  		 );
		    		 		  		 
		    		 		  		 JLabel label = new JLabel("Host");
		    		 		  		 
		    		 		  		 txt_Cluster_Host = new JTextField();
		    		 		  		 txt_Cluster_Host.setText("data-cluster");
		    		 		  		 txt_Cluster_Host.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		    		 		  		 txt_Cluster_Host.setForeground(new Color(255, 255, 255));
		    		 		  		 txt_Cluster_Host.setBackground(new Color(0, 0, 102));
		    		 		  		 txt_Cluster_Host.setColumns(10);
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_16 = new JLabel("User");
		    		 		  		 
		    		 		  		 txt_Cluster_User_Password = new JPasswordField();
		    		 		  		 txt_Cluster_User_Password.setForeground(new Color(255, 255, 255));
		    		 		  		 txt_Cluster_User_Password.setBackground(new Color(0, 0, 128));
		    		 		  		 txt_Cluster_User_Password.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
                
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 txt_Cluster_User_Name = new JTextField();
		    		 		  		 txt_Cluster_User_Name.setText("user");
		    		 		  		 txt_Cluster_User_Name.setForeground(new Color(255, 255, 255));
		    		 		  		 txt_Cluster_User_Name.setBackground(new Color(0, 0, 128));
		    		 		  		 txt_Cluster_User_Name.setColumns(10);
		    		 		  		 
		    		 		  		 JLabel label_2 = new JLabel("Password");
		    		 		  		 
		    		 		  		 chk_Multi_Queue_Job_Submissions = new JCheckBox("Multi Queued Jobs");
		    		 		  		 chk_Multi_Queue_Job_Submissions.setSelected(true);
		    		 		  		 chk_Multi_Queue_Job_Submissions.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			if (chk_Multi_Queue_Job_Submissions.isSelected()) 
		    		 		  		 			{
		    		 		  		 			    
		    		 		  		 				bool_chk_Multi_Queue_Job_Submissions= true;
		    		 		  		 			    
		    		 		  		 			    bool_chk_Put_in_Queue = false;
		    		 		  		 			    chk_Put_in_Queue.setSelected(false);
		    		 		  		 			    
		    		 		  		 				bool_chk_MergeReplicates= false;
		    		 		  		 			    chk_MergeReplicates.setSelected(false);

		    		 		  		 			} 
		    		 		  		 			else 
		    		 		  		 			{
		    		 		  		 			    
		    		 		  		 				bool_chk_Multi_Queue_Job_Submissions = false;
		    		 		  		 			    bool_chk_Put_in_Queue = true;
		    		 		  		 			}
		    		 		  		 			
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}

		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_Put_in_Queue = new JCheckBox("Put in Single Queue");
		    		 		  		 chk_Put_in_Queue.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			if (chk_Put_in_Queue.isSelected()) 
		    		 		  		 			{
		    		 		  		 			    bool_chk_Put_in_Queue = true;
		    		 		  		 			    bool_chk_Multi_Queue_Job_Submissions = false;
		    		 		  		 			    
		    		 		  		 			    chk_Multi_Queue_Job_Submissions.setSelected(false);

		    		 		  		 			} else {
		    		 		  		 			    
		    		 		  		 				bool_chk_Put_in_Queue = false;
		    		 		  		 			    bool_chk_Multi_Queue_Job_Submissions = true;

		    		 		  		 			}
		    		 		  		 			
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_MergeReplicates = new JCheckBox("Merge Replicates");
		    		 		  		 chk_MergeReplicates.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			// check state
		    		 		  		 			if (chk_MergeReplicates.isSelected()) 
		    		 		  		 			{
		    		 		  		 				bool_chk_MergeReplicates = true;
		    		 		  		 				
		    		 		  		 			    chk_Put_in_Queue.setSelected(true);
		    		 		  		 			    bool_chk_Put_in_Queue = true;

		    		 		  		 			    chk_Multi_Queue_Job_Submissions.setSelected(false);
		    		 		  		 				bool_chk_Multi_Queue_Job_Submissions = false;

		    		 		  		 			} 
		    		 		  		 			else 
		    		 		  		 			{
		    		 		  		 			    bool_chk_MergeReplicates = false;

		    		 		  		 			}
		    		 		  		 			
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 	
		    		 		  		 });
		    		 		  		 
		    		 		  		 txt_WallTime = new JTextField();
		    		 		  		 txt_WallTime.setForeground(new Color(0, 0, 102));
		    		 		  		 txt_WallTime.setBackground(new Color(240, 248, 255));
		    		 		  		 txt_WallTime.setText("00:10:00");
		    		 		  		 txt_WallTime.setColumns(10);
		    		 		  		 
		    		 		  		 lblNewLabel_17 = new JLabel("Wall time");
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_18 = new JLabel("nodes");
		    		 		  		 
		    		 		  		 txt_nodes = new JTextField();
		    		 		  		 txt_nodes.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		    		 		  		 txt_nodes.setForeground(new Color(0, 0, 128));
		    		 		  		 txt_nodes.setBackground(new Color(240, 248, 255));
		    		 		  		 txt_nodes.setText("1");
		    		 		  		 txt_nodes.setColumns(10);
		    		 		  		 
		    		 		  		 JLabel lblNewLabel_21 = new JLabel("ppn");
		    		 		  		 
		    		 		  		 txt_ppn = new JTextField();
		    		 		  		 txt_ppn.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		    		 		  		 txt_ppn.setForeground(new Color(0, 0, 128));
		    		 		  		 txt_ppn.setBackground(new Color(240, 248, 255));
		    		 		  		 txt_ppn.setText("1");
		    		 		  		 txt_ppn.setColumns(10);
		    		 		  		 
		    		 		  		 lblNewLabel_19 = new JLabel("Email");
		    		 		  		 
		    		 		  		 txt_Email = new JTextField();
		    		 		  		 txt_Email.setText("name@email.com");
		    		 		  		 txt_Email.setForeground(new Color(0, 0, 102));
		    		 		  		 txt_Email.setBackground(new Color(240, 248, 255));
		    		 		  		 txt_Email.setColumns(10);
		    		 		  		 
		    		 		  		 chk_Create_Queue_Jobs = new JCheckBox("Create & Queue Jobs");
		    		 		  		 chk_Create_Queue_Jobs.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 	    		 		  		 	try
		    		 		  		 	    		 					{
		    		 		  		 	    		 						if (chk_Create_Queue_Jobs.isSelected()) 
		    		 		  		 	    		 						{
		    		 		  		 	    		 							
		    		 		  		 	    		 							chk_Direct_Processing_without_Queue.setSelected(false);

		    		 		  		 	    		 						} 
		    		 		  		 	    		 						else
		    		 		  		 	    		 						{
		    		 		  		 	    		 							chk_Direct_Processing_without_Queue.setSelected(true);
		    		 		  		 	    		 						}
		    		 		  		 	    		 						
		    		 		  		 	    		 					}
		    		 		  		 	    		 					catch(Exception ex)
		    		 		  		 	    		 					{
		    		 		  		 	    		 						ex.printStackTrace();
		    		 		  		 	    		 					}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 chk_Create_Queue_Jobs.setSelected(true);
		    		 		  		 
		    		 		  		 chk_Direct_Processing_without_Queue = new JCheckBox("Direct Processing");
		    		 		  		 chk_Direct_Processing_without_Queue.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
			    		 		  		 	try
			    		 					{
			    		 						if (chk_Direct_Processing_without_Queue.isSelected()) 
			    		 						{
			    		 							
			    		 							chk_Create_Queue_Jobs.setSelected(false);

			    		 						} 
			    		 						else
			    		 						{
			    		 							chk_Create_Queue_Jobs.setSelected(true);
			    		 						}
			    		 						
			    		 					}
			    		 					catch(Exception ex)
			    		 					{
			    		 						ex.printStackTrace();
			    		 					}

		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_Create_Softlink_and_Process = new JCheckBox("Soft-Links ");
		    		 		  		 chk_Create_Softlink_and_Process.setSelected(true);
		    		 		  		 chk_Create_Softlink_and_Process.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			if (chk_Create_Softlink_and_Process.isSelected()) 
		    		 		  		 			{
		    		 		  		 				bool_chk_Create_Softlinks_and_Process = true;
		    		 		  		 				
		    		 		  		 			    chk_Copy_and_Process.setSelected(false);
		    		 		  		 				bool_chk_Copy_and_Process = false;
		    		 		  		 			} 
		    		 		  		 			else 
		    		 		  		 			{
		    		 		  		 				bool_chk_Create_Softlinks_and_Process = false;
		    		 		  		 			}
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}

		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_Copy_and_Process = new JCheckBox("Copy");
		    		 		  		 chk_Copy_and_Process.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			if (chk_Copy_and_Process.isSelected())
		    		 		  		 			{
		    		 		  		 				bool_chk_Copy_and_Process = true;
		    		 		  		 			    chk_Create_Softlink_and_Process.setSelected(false);

		    		 		  		 			}
		    		 		  		 			else
		    		 		  		 			{
		    		 		  		 				bool_chk_Copy_and_Process = false;
		    		 		  		 			}
		    		 		  		 			
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 
		    		 		  		 chk_Fast_GZ_files = new JCheckBox("*.gz - zipped files");
		    		 		  		 chk_Fast_GZ_files.addActionListener(new ActionListener() {
		    		 		  		 	public void actionPerformed(ActionEvent e) {
		    		 		  		 		try
		    		 		  		 		{
		    		 		  		 			if (chk_Fast_GZ_files.isSelected()) 
		    		 		  		 			{
		    		 		  		 				str_File_Extension = ".gz";						
		    		 		  		 			} 
		    		 		  		 			else 
		    		 		  		 			{
		    		 		  		 		        str_File_Extension = ".fastq";
		    		 		  		 			}
		    		 		  		 		}
		    		 		  		 		catch(Exception ex)
		    		 		  		 		{
		    		 		  		 			ex.printStackTrace();
		    		 		  		 		}
		    		 		  		 	}
		    		 		  		 });
		    		 		  		 chk_Fast_GZ_files.setSelected(true);
		    		 		  		 GroupLayout gl_panel = new GroupLayout(panel);
		    		 		  		 gl_panel.setHorizontalGroup(
		    		 		  		 	gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 			.addContainerGap()
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 						.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 							.addGap(29)
		    		 		  		 							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 								.addComponent(label, Alignment.TRAILING)
		    		 		  		 								.addComponent(lblNewLabel_16, Alignment.TRAILING)))
		    		 		  		 						.addComponent(label_2))
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
		    		 		  		 						.addComponent(txt_Cluster_User_Password)
		    		 		  		 						.addComponent(txt_Cluster_User_Name, 0, 0, Short.MAX_VALUE)
		    		 		  		 						.addComponent(txt_Cluster_Host, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
		    		 		  		 					.addContainerGap(27, Short.MAX_VALUE))
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Create_Queue_Jobs)
		    		 		  		 					.addContainerGap(39, Short.MAX_VALUE))
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Direct_Processing_without_Queue)
		    		 		  		 					.addContainerGap(57, Short.MAX_VALUE))
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Fast_GZ_files)
		    		 		  		 					.addContainerGap(53, Short.MAX_VALUE))
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Create_Softlink_and_Process)
		    		 		  		 					.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 					.addComponent(chk_Copy_and_Process)
		    		 		  		 					.addContainerGap(26, Short.MAX_VALUE))
		    		 		  		 				.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Multi_Queue_Job_Submissions)
		    		 		  		 					.addContainerGap(52, Short.MAX_VALUE))
		    		 		  		 				.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_Put_in_Queue)
		    		 		  		 					.addContainerGap(45, Short.MAX_VALUE))
		    		 		  		 				.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(chk_MergeReplicates)
		    		 		  		 					.addContainerGap(61, Short.MAX_VALUE))
		    		 		  		 				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
		    		 		  		 					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
		    		 		  		 						.addComponent(txt_Email, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
		    		 		  		 						.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
		    		 		  		 								.addComponent(lblNewLabel_18)
		    		 		  		 								.addComponent(lblNewLabel_21)
		    		 		  		 								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 									.addComponent(lblNewLabel_19)
		    		 		  		 									.addComponent(lblNewLabel_17)))
		    		 		  		 							.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
		    		 		  		 								.addComponent(txt_ppn, 0, 0, Short.MAX_VALUE)
		    		 		  		 								.addComponent(txt_nodes, 0, 0, Short.MAX_VALUE)
		    		 		  		 								.addComponent(txt_WallTime, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
		    		 		  		 					.addGap(36))))
		    		 		  		 );
		    		 		  		 gl_panel.setVerticalGroup(
		    		 		  		 	gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 		.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 			.addContainerGap()
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(txt_Cluster_Host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 				.addComponent(label))
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(txt_Cluster_User_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 				.addComponent(lblNewLabel_16))
		    		 		  		 			.addGap(7)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(txt_Cluster_User_Password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 				.addComponent(label_2))
		    		 		  		 			.addGap(18)
		    		 		  		 			.addComponent(chk_Multi_Queue_Job_Submissions)
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(chk_Put_in_Queue)
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(chk_MergeReplicates)
		    		 		  		 			.addGap(30)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(txt_WallTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 				.addComponent(lblNewLabel_17))
		    		 		  		 			.addGap(7)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
		    		 		  		 				.addComponent(lblNewLabel_18)
		    		 		  		 				.addComponent(txt_nodes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		    		 		  		 				.addGroup(gl_panel.createSequentialGroup()
		    		 		  		 					.addComponent(lblNewLabel_21)
		    		 		  		 					.addGap(27)
		    		 		  		 					.addComponent(lblNewLabel_19))
		    		 		  		 				.addComponent(txt_ppn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(txt_Email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(chk_Create_Queue_Jobs)
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(chk_Direct_Processing_without_Queue)
		    		 		  		 			.addGap(34)
		    		 		  		 			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
		    		 		  		 				.addComponent(chk_Create_Softlink_and_Process)
		    		 		  		 				.addComponent(chk_Copy_and_Process))
		    		 		  		 			.addPreferredGap(ComponentPlacement.RELATED)
		    		 		  		 			.addComponent(chk_Fast_GZ_files)
		    		 		  		 			.addContainerGap(33, Short.MAX_VALUE))
		    		 		  		 );
		    		 		  		 panel.setLayout(gl_panel);
		    		 		  		 JPanel_Set_Paths.setLayout(gl_JPanel_Set_Paths);
		contentPane.setLayout(gl_contentPane);
	}
}