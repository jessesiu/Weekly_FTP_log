package weekly;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;







public class report {
	
	
		
		
		private final static ScheduledExecutorService scheduler= Executors.newScheduledThreadPool(1);
		
		
		public static void connectFor5() {
	        final Runnable beeper = new Runnable() {
	                public void run() { 
	                	
	                		database db= new database();
	                		try {
								String answer1= null;
								
	                		@SuppressWarnings("unchecked")
							ArrayList<String> aa= new ArrayList<String>(db.getdataset_manuscript());
	                		if (aa.size() == 0)
	                		{
	                			String content1 = "All datasets have the manuscripts";
	                			answer1= "\n\n"+ content1;
	                		}else
	                		{
	                			String content2 = "";
	                			for(int i=0;i<aa.size();++i)
	                			{
	                				String temp = aa.get(i);
	                				content2+=temp+"; ";
	                					                				
	                			}
	                			answer1="The list of DOI's that are published but have no related manuscript: \n\n" + content2+"\n\n\n";
	                		}
	                		
	                		//System.out.println(answer1);  //report published but have no related manuscript
	                		
	                		int number= db.getallpublished_doi();
	                		String answer2= "Current number of DOI's published: \n\n"+number+"\n\n\n";
	                		
	                		//System.out.println(number); // current number of DOI's published
	                		
	                		long number1= db.getallpublished_size();
	                		//System.out.println(number1/1024/1024/1024/1024+"TB");
	                		
	                		Process p = Runtime.getRuntime().exec("du -H -depth=0 /Users/xiaosizhe/Downloads/");

	                		p.waitFor();
	                		BufferedReader buf = new BufferedReader(new InputStreamReader(
	                		          p.getInputStream()));
	                		  String line = "";
	                		  String output = "";

	                		  while ((line = buf.readLine()) != null) {
	                		    output += line + "\n";
	                		  }

	                		  System.out.println(output);
	                		
	                		
	                		String answer3="Overall size of Published data: \n\n"+number1/1024/1024/1024/1024+"TB"+"\n\n\n";
	                		
	                		//System.out.println(db.getallunpublished_doi());
	                		
	                		String answer4="List of unpublished DOIs with dataset titles: \n\n"+db.getallunpublished_doi()+"\n";
	                		
	                		

	                        
	                        
	                        //String contentascp =null;
	                        String contentftp = null;
	                        //contentascp= readascpfile();
	                        contentftp= readftpfile();
	                        
	                        Date dd = new Date();
	                        @SuppressWarnings("deprecation")
							String aaa= dd.toGMTString();
	                        String answer0="Report generated <"+aaa+">\n\n";
	                      
	                        		
	                        String msgBody = answer0+ answer1+answer2+answer3+contentftp+answer4;
	                        
	                        System.out.println(msgBody);
	                        
	                        String from = "jesse@gigasciencejournal.com";
	                        String pass = "xsz19880322ipm";
	                       // String[] to = { "database@gigasciencejournal.com","scott@gigasciencejournal.com","laurie@gigasciencejournal.com" }; // list of recipient email addresses
	                        String[] to = { "xiaosizheme@gmail.com" };
	                        String subject = "GigaDB weekly report";
	                       
	                      //  sendFromGMail(from, pass, to, subject, msgBody);
	                		
	                		
	                		
	                		
	                		
	                		
	                		
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                		
	                		try {
								db.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                		
	                	
	                	}
	            };
	        final ScheduledFuture<?> beeperHandle =
	        scheduler.scheduleAtFixedRate(beeper, 0, 7, DAYS);
	        
	        scheduler.schedule(new Runnable() {
	                public void run() { beeperHandle.cancel(true); }
	            }, 60 * 60, DAYS);
	        

	    }
		
		
		public static HashMap<String,String> readsinglefile(String filelocation,HashMap<String,String> download) throws ParseException {
			
			for(String key: download.keySet())
			{
				System.out.println(key+"....."+download.get(key));
				
			}
			
			BufferedReader br = null;
			int total=0;
			try {
				
				String currentline;
				br = new BufferedReader(new FileReader(filelocation));
				Date current_date= new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(current_date); 
				c.add(Calendar.DATE, -7);
				current_date = c.getTime();
				//System.out.println(current_date);
				String year= String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
				while((currentline= br.readLine()) !=null)
				{
					if(currentline.endsWith("c"))
						
					{
						
						String[] aa = currentline.split(" ");
						@SuppressWarnings("deprecation")
						DateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
						String time="";
						if(aa[2].isEmpty())
						{
							time= aa[1]+" "+aa[3]+" "+year;
						}
						else
							time= aa[1]+" "+aa[2]+" "+year;
						/*for(int i=0;i<aa.length;++i)
						{
							System.out.println(aa[i]);
						}
						*/
						Date file_date = format.parse(time);
						
						if(file_date.after(current_date))
						{
						
						//System.out.println(file_date);
						//System.out.println(currentline);
						total+=1;
						int index1=0;
						int times=0;
						index1=currentline.indexOf("10.5524");
						
						if(index1<0)
							continue;
						String address= currentline.substring(index1, index1+28);
						if(download.size()==0)
						{
							download.put(address, "1");
						}
						else{
							boolean flag=true;
							for(String key: download.keySet())
							{
								if (key.equals(address))
								{
									String value= download.get(key);
									int value1= Integer.valueOf(value)+1;
									download.put(key, String.valueOf(value1));
									if(key.equals("10.5524/100001_101000/100059"))
									{
										System.out.println(".........................."+String.valueOf(value1));
									}
									flag=false;
									
								}
								
							}
							
							if(flag)
							{
								download.put(address,"1");
								
							}
							
							
						}
						
						
					}
					}
					
					}
				
			}
			catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			
			
			
			return download;
		}
		public static String readftpfile() throws IOException, ParseException
		{
		
	        BufferedWriter bw = null;
	        
	        bw = new BufferedWriter(new FileWriter("/Users/xiaosizhe/Desktop/ftpcount.txt", true));
			
			int total=0;
			int totalfile=0;
			
			HashMap<String, String> download= new HashMap<String, String>();
			download=readsinglefile("/Users/xiaosizhe/Desktop/xferlog",download);
			
			System.out.println("PART1.........");
			download=readsinglefile("/Users/xiaosizhe/Desktop/xferlog.1",download);
			
			System.out.println("PART2.........");
			download=readsinglefile("/Users/xiaosizhe/Desktop/xferlog.2",download);
		
			System.out.println("FINISHED.........");
			
			
			
	
			
			/*
			String content1=null;
			
			try {
				
			String currentline;
			br = new BufferedReader(new FileReader("/var/log/xferlog.1"));
	
			bw = new BufferedWriter(new FileWriter("/var/log/ftp_count", true));
			//br = new BufferedReader(new FileReader("/Users/xiaosizhe/Downloads/xferlog"));
			//bw = new BufferedWriter(new FileWriter("/Users/xiaosizhe/Downloads/ftpcount.txt", true)); 
			
			Date current_date= new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(current_date); 
			c.add(Calendar.DATE, -7);
			current_date = c.getTime();
			//System.out.println(current_date);
			String year= String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			
			while((currentline= br.readLine()) !=null)
			{
				if(currentline.endsWith("c"))
					
				{
					
					String[] aa = currentline.split(" ");
					@SuppressWarnings("deprecation")
					DateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
					String time="";
					if(aa[2].isEmpty())
					{
						time= aa[1]+" "+aa[3]+" "+year;
					}
					else
						time= aa[1]+" "+aa[2]+" "+year;
					
					Date file_date = format.parse(time);
					
					if(file_date.after(current_date))
					{
					
					//System.out.println(file_date);
					//System.out.println(currentline);
					total+=1;
					int index1=0;
					int times=0;
					index1=currentline.indexOf("10.5524");
					
					if(index1<0)
						continue;
					String address= currentline.substring(index1, index1+28);
					if(download.size()==0)
					{
						download.put(address, "1");
					}
					else{
						boolean flag=true;
						for(String key: download.keySet())
						{
							if (key.equals(address))
							{
								String value= download.get(key);
								int value1= Integer.valueOf(value)+1;
								download.put(key, String.valueOf(value1));
								flag=false;
								
							}
							
						}
						
						if(flag)
						{
							download.put(address,"1");
							
						}
						
						
					}
					
					
				}
				}
				
				}
			
			
		}
			catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			*/
			
			HashMap<String, String> sortedMap = sortHashMapByValuesD(download);
			
			String content2="In FTP server each datasets download times are:  "+"\n\n";
			String content3= "";
			int totalsize=0;
			for(String key : sortedMap.keySet())
			{
				//System.out.println(key+"       "+download.get(key));
				content3+=key+" ---------- "+sortedMap.get(key)+"\n";
				System.out.println(key+"..............."+sortedMap.get(key));
				totalsize+= Integer.valueOf(sortedMap.get(key));

			}
			 Date dd = new Date();
             @SuppressWarnings("deprecation")
			String aaa= "#"+dd.toString()+"\n";
            System.out.println("here"+aaa); 
             
			bw.append(aaa+content3);
			bw.close();
			content2+=content3;
			
			content2+="Total number of datasets: "+sortedMap.size()+"		Total download times: "+totalsize+"\n\n\n";
			System.out.println(content2);
			return content2;
		}
		
		public static String readascpfile() throws ParseException
		{
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			int total=0;
			int totalfile=0;
			
			HashMap<String, String> download= new HashMap<String, String>();
			
			String content1=null;
			
			try {
				
			String currentline;
			br = new BufferedReader(new FileReader("/var/log/messages"));
			bw = new BufferedWriter(new FileWriter("/var/log/aspera_count", true));
			//br = new BufferedReader(new FileReader("/Users/xiaosizhe/Downloads/messages"));
			//bw = new BufferedWriter(new FileWriter("/Users/xiaosizhe/Downloads/ascpcount.txt", true));
			Date current_date= new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(current_date); 
			c.add(Calendar.DATE, -7);
			current_date = c.getTime();
			System.out.println(current_date);
			String year= String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			
			while((currentline= br.readLine()) !=null)
			{
				
				
				if(currentline.contains("FASP Session Stop") && currentline.contains("success"))
				{
					
					String[] aa = currentline.split(" ");
					
					
					System.out.println(year);
					DateFormat format = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
					String time="";
					if(aa[1].isEmpty())
					{
						time= aa[0]+" "+aa[2]+" "+year;
					}
					else
						time= aa[0]+" "+aa[1]+" "+year;
					/*for(int i=0;i<aa.length;++i)
					{
						System.out.println(aa[i]);
					}
					*/
					Date file_date = format.parse(time);
					
					if(file_date.after(current_date))
					{
					System.out.println(file_date);
					total+=1;
					int index1=0;
					int times= 0;
					while((index1=currentline.indexOf("10.5524",index1))!= -1){
					times++;
					index1++;
					//System.out.println(times);
					}
					index1=currentline.indexOf("10.5524");
					if(index1<0)
						continue;
					
					totalfile+=times;
					String address= currentline.substring(index1, index1+28);
					if(download.size()==0)
					{
						String ttt= "1,"+times;
						download.put(address, ttt);
					}else{
						boolean flag=true;
						for(String key: download.keySet()){
							
							if(key.equals(address))
							{
								String temp=download.get(key);
								String[] ttp = temp.split(",");
								int part1=Integer.valueOf(ttp[0])+1;
								int part2=Integer.valueOf(ttp[1])+times;
								String tttt= part1+","+part2;
								download.put(key, tttt);
								flag=false;
							}
	
						}
						if(flag)
						{
							download.put(address, "1,"+times);
						}
		
					}
				}
				}	
				
				}
		
			//System.out.println("Total download times in Aspera is:\n"+total+"\n\n");
			//System.out.println("Each datasets download times are :"+"\n");
			String content2="In Aspera each datasets download times are: Sessions,Files "+"\n\n";
			String content3="";
			for(String key : download.keySet())
			{
				//System.out.println(key+"       "+download.get(key));
				content3+=key+" ---------- "+download.get(key)+"\n";

			}
			
			Date dd = new Date();
            @SuppressWarnings("deprecation")
			String aaa= "#"+dd.toString()+"\n";
			
			bw.append(aaa + content3);
			bw.close();
			br.close();
			content2+=content3;
			content1="Total download sessions and files in Aspera is:\n\n"+total+","+totalfile+"\n\n"+content2+"\n\n\n";
			
			System.out.println(content1);
			
			
				
			
			}
			catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			return content1;		
		}
		
		public static void main (String[] args) throws IOException, ParseException
		{
			
			
			connectFor5();
			//readascpfile();
			//readftpfile();
			
		}
		
		public static Object exec(String cmd) {
		  try {
		            String[] cmdA = { "/bin/sh", "-c", cmd };
		            Process process = Runtime.getRuntime().exec(cmdA);
		            LineNumberReader br = new LineNumberReader(new InputStreamReader(
		                    process.getInputStream()));
		            StringBuffer sb = new StringBuffer();
		            String line;
		            while ((line = br.readLine()) != null) {
		                System.out.println(line);
		                sb.append(line).append("\n");
		            }
		            return sb.toString();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return null;
		    }
		
		
		private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
	        Properties props = System.getProperties();
	        String host = "smtp.gmail.com";
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", from);
	        props.put("mail.smtp.password", pass);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);

	        try {
	            message.setFrom(new InternetAddress(from));
	            InternetAddress[] toAddress = new InternetAddress[to.length];

	            // To get the array of addresses
	            for( int i = 0; i < to.length; i++ ) {
	                toAddress[i] = new InternetAddress(to[i]);
	            }

	            for( int i = 0; i < toAddress.length; i++) {
	                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	            }

	            message.setSubject(subject);
	            message.setText(body);
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, from, pass);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        }
	        catch (AddressException ae) {
	            ae.printStackTrace();
	        }
	        catch (MessagingException me) {
	            me.printStackTrace();
	        }
	    }
		
		
		public static HashMap<String, String> sortHashMapByValuesD(HashMap<String, String> passedMap) {
			   List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
			   List<Integer> mapValues = new ArrayList<Integer>();
			   for(String key:passedMap.keySet())
			   {
				   int value= Integer.valueOf(passedMap.get(key));
				   mapValues.add(value);
			   }
			   Collections.sort(mapValues);
			   Collections.sort(mapKeys);

			   HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();

			   Iterator<Integer> valueIt = mapValues.iterator();
			   while (valueIt.hasNext()) {
			       Object val = valueIt.next();
			       Iterator<String> keyIt = mapKeys.iterator();
			      // System.out.println(val);

			       while (keyIt.hasNext()) {
			           Object key = keyIt.next();
			           String comp1 = passedMap.get(key).toString();
			           String comp2 = val.toString();

			           if (comp1.equals(comp2)){
			               passedMap.remove(key);
			               mapKeys.remove(key);
			               sortedMap.put((String)key, String.valueOf(val));
			               break;
			           }

			       }

			   }
			   return sortedMap;
			}
		
		
	}
	

