import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jline.builtins.Completers;
import org.jline.builtins.Completers.TreeCompleter;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import static org.jline.builtins.Completers.TreeCompleter.node;


public class Project1 {
	public static void usage() {
		System.out.println("Command Line Mode OPTIONS\n"
				+"start command line mode by starting your argument with --generate or -g\n"
				+ "--generate -g    Generate the text with the stated options\n"
				+ "--help -h   Check available OPTIONS\n"
				+ "--version -v   Check version\n"
				+ "--library -l   Choose source of choice of text, include lorem and anguish. If no library is specify, lorem will be default.\r\n" + 
				"--mode -m   Choose the mode the text should display. Choice of mode include: paragraphs, bullets, words. If no mode is specify, paragraphs will be the default mode.\r\n" + 
				"--count -c   Choose the number of the text you want to display. Maximum for paragraphs mode is 10. If no count is specify, 5 will be the default value.\r\n" + 
				"--html -t   Generate the text in html markup. Otherwise, a normal format will be generate. \r\n" + 
				"--outfile -o   Output the text that is being generate. If html markup is chosen, the text will be generate as .html, otherwise, it will be in .txt file. \n"
				+ "\n"
				+"Interactive mode OPTIONS\n"
				+"Start interactive mode by entering Interact on your argument"
				+ "generate   generate the text with the stated options \n"
				+ "help   Check available OPTIONS\r\n" + 
				"version   Check version\n"
				+ "set   set opinions such as mode, count, html, and outfile. With the same functions as stated in command line mode. \r\n" + 
				"show   show all the opinions that is set to generate the text\r\n" + 
				"");
		
	}
	public static void outText(String lib, String count, String mode, String out, String html, boolean validOutText) {
			String libraryName = lib+".txt";
			if(validOutText) {
			List<String> lines = new ArrayList<String>();
			List<String> answer = new ArrayList<String>();
			int c = 0;
		try {
			InputStream is = Project1.class.getResourceAsStream(libraryName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine())!=null) {
			  lines.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("library not found");
		}
		if(lines.isEmpty()) {
			System.out.println("Please set your library");
		}else { 
				if(count == null) {
					c = 5;
				}else {
				c = Integer.parseInt(count);
					}
				answer = output(lines,mode,c,html);
			}
		PrintWriter writer = null;
		
			if(html.equals("true")) {
				if(out != null) {
					try {
						writer = new PrintWriter(out+".html", "UTF-8");
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				for(int i = 0; i<answer.size();i++) {
					writer.write(answer.get(i));}
					writer.close();
				}else {
				for(int i = 0; i<answer.size();i++) {
				System.out.println(answer.get(i));}
					}
			}else {
				if(out != null) {
					try {
						writer = new PrintWriter(out+".txt", "UTF-8");
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				for(int i = 0; i<answer.size();i++) {
					writer.write(answer.get(i));
					}
					writer.close();
				}else {
				for(int i = 0; i<answer.size();i++) {
					System.out.println(i+1+" . " + answer.get(i)+" \n");
					}
				}
			}
		
		
	/*	if(!out.isEmpty()) {
		Path file;
		if(html.equals("true")){
		file = Paths.get(out+".html");
			}else {
		file = Paths.get(out+".txt");		
			}
		for(int i = 0; i<answer.size();i++) {
		//	Files.write(path, bytes, options)
		}
		} */
			}

	}
	public static List<String> output (List<String> library, String mode, int count, String html){
		List<String> lines = new ArrayList<String>();

		switch(mode) {
			case "paragraphs":
			try {
				if(count>10) {
					System.out.println("Maxium 10 paragraph can be generate" );
				}else if(html.equals("true")) {
				lines.add("<!DOCTYPE html>");
				lines.add("<html> ");
				for(int i = 0; i < count; i++) {
					lines.add("<p> ");
					lines.add(library.get(i));
					lines.add("</p> ");
				} 
				lines.add("</html>");
			}else {
				for(int i = 0; i < count; i++) {
					lines.add(library.get(i));
				} 
			}
			}catch(IndexOutOfBoundsException ex) {
				
			}
				break;
			case "bullets":
				if(html.equals("true")) {
					lines.add("<!DOCTYPE html>");
					lines.add("<html>");
					for(int i = 0; i <count;i++) {
						Random rand = new Random();
						int randLib = rand.nextInt(library.size());
						int left = rand.nextInt(library.get(randLib).length());
						int right = rand.nextInt((library.get(randLib).length()-left)+1)+left;
						String bullet = library.get(randLib).substring(left, right);
						lines.add("<li> ");
						lines.add(bullet);
						lines.add("</li> ");
						}
					lines.add("</html>");
				}else {
				for(int i = 0; i <count;i++) {
					Random rand = new Random();
					int randLib = rand.nextInt(library.size());
					int left = rand.nextInt(library.get(randLib).length());
					int right = rand.nextInt((library.get(randLib).length()-left)+1)+left;
					String bullet = library.get(randLib).substring(left, right);
					lines.add(bullet);
					}
				}
				break;
			case "words":
				if(html.equals("true")) {
					for(int i = 0; i<count;i++) {
					lines.add("<!DOCTYPE html>");
					lines.add("<html>");
					Random rand = new Random();
					int randLib = rand.nextInt(library.size());
					String paragraph = library.get(randLib);
					String words[] = paragraph.split(" ");
					String word = words[rand.nextInt(words.length)];
					lines.add("<h1>" );
					lines.add(word);
					lines.add("</h1>" );
					}
					lines.add("</html>");
				}else {
				for(int i = 0; i<count;i++) {
					Random rand = new Random();
					int randLib = rand.nextInt(library.size());
					String paragraph = library.get(randLib);
					String words[] = paragraph.split(" ");
					String word = words[rand.nextInt(words.length)];
					lines.add(word);
					}
				}
				break;
			default:
				System.out.println("Invalid mode");
		}
		return lines;
	}
	public static void main(String[] args) {

		  try {
			Completer completer = new Completers.FileNameCompleter();
			DefaultParser parser = new DefaultParser();
			parser.setEofOnUnclosedQuote(true);
			
			int index = 0;
			String prompt = "prompt> ";
			 String rightPrompt = null;
			 boolean commandLineMode = false;
			 boolean interactiveMode = false;
			 boolean lib = false;
			 boolean m = false;
			while(args.length > index && !commandLineMode ) {
				
				switch(args[index]) {
			/*	case "hi" :
					interactiveMode = true;
					completer = new TreeCompleter( node("how are you"), node("what is ur name"));
					index++;
					break label;
				case "version":
					index++;
					
					break; */
				case "--generate":
				case "-g":
					completer = new ArgumentCompleter(
							new StringsCompleter("help","library","version")
							);
					if(index == 0 && args.length > 1 ) {
						commandLineMode = true;
						interactiveMode = false;
						}
					index++;
					break;
			//	case "--help":
			//	case "-h":
			//	usage();
			//	index++;  
			//	break;
			/*	case "mode":
					completer = new ArgumentCompleter(
							new StringsCompleter("paragraphs","bullets","words")
							);
					m = true;
					index++;
					break;*/
				case "interact": 
					completer = new TreeCompleter(
							node("show"),
							node("help"),
							node("version"),
							node("generate"),
							node("set" ,node("mode",node("paragraphs"),node("words"),node("bullets")),
										node("library",node("lorem"),node("anguish"))
										,node("html", node("true"), node("false"))
										,node("count")
										,node("outfile")));
							
					index++;
					interactiveMode = true;
					break;
				default:
					usage();
					commandLineMode = true;
					interactiveMode = false;
					index++;
					break;
				}
			}
			if(args.length == 0) {
				usage();
			}
			 TerminalBuilder builder = TerminalBuilder.builder();
			Terminal terminal = builder.build();
				LineReader reader = LineReaderBuilder.builder()
						.terminal(terminal)
						.completer(completer)
						.parser(parser)
						.build();
				// TODO makes the variable with its default value
				
				String text = "lorem";
				String mode = "paragraphs";
				String out = null;
				String html = "false";
				String count = null;
				
				
				if(commandLineMode) {
					boolean invalidArg = false;
					boolean validGenerate = true;
					int argLength = args.length;
					if(args[0].equals("--generate") || args[0].equals("-g")) {
						int clineIndex = 1;
						while(args.length > clineIndex && !invalidArg) {
							try {
							switch(args[clineIndex]) {
							case "--version":
							case "-v":
								if(argLength > 2) {
									invalidArg = true;
									System.out.print("invalid arg");
									validGenerate = false;
								}else {
									System.out.println("Version Super Alpha");
								}
								clineIndex++;
								break;
							case "--library":
							case "-l":
								//lib = true;
								clineIndex = clineIndex +1;
								text = args[clineIndex];
								clineIndex++;
								break;
							case "--mode":
							case "-m":
								//if(lib) {
								//	m = true;
									clineIndex = clineIndex +1;
									mode = args[clineIndex];
							//	}else {
								//	System.out.println("Please state your library");
							//		validGenerate = false;
							//	}
								clineIndex++;
								break;
							case "--count":
							case "-c":
							//	if(m) {
									clineIndex = clineIndex +1;
									count = args[clineIndex];
							/*	}else {
									System.out.println("Please state your mode");
									validGenerate = false;
									invalidArg = true;
								} */
								clineIndex++;
								break;
							case "--outfile":
							case "-o":
							//	if(lib) {
									clineIndex = clineIndex +1;
									out = args[clineIndex];
							/*	}else {
									System.out.println("Please state your library");
									validGenerate = false;
									invalidArg = true;
								}*/
								clineIndex++;
								break;
							case "--html":
							case "-t":
						
								//if(lib) {
									html = "true";
							/*	}else {
									System.out.println("Please state your library");
									validGenerate = false;
									invalidArg = true;
								} */
								clineIndex++;
								break;
							case "--help":
							case "-h":
								if(argLength > 2) {
									invalidArg = true;
									System.out.print("invalid arg");
									validGenerate = false;
								}else {
									usage();
								}
								break;
								
							default: 
								System.out.println("Invalid command");
								validGenerate = false;
								invalidArg = true;
								break;
							}
							}catch(IndexOutOfBoundsException e) {
								System.out.println("Please state a vaild argument");
								validGenerate = false;
								invalidArg = true;
							}
							
						}
						outText(text, count, mode, out, html,validGenerate);
					}
				}		
				     
	/*		if(commandLineMode) {
				boolean invalidArg = false;
				int argLength = args.length;
				if(args[0].equals("generate")) {
					int clineIndex = 1;
					//maybe surround the while loop with try catch?
					while(args.length > clineIndex && !invalidArg) {
						switch(args[clineIndex]) {
						case "version":
						case "-v":
							if(argLength > 2) {
								invalidArg = true;
								System.out.print("invalid arg");
							}else {
								System.out.println("1.0.0");
							}
							clineIndex++;
							break;
						case "library":
						case "-l":
							while(args.length > clineIndex && !invalidArg) {
							clineIndex = clineIndex + 1;
							System.out.println(clineIndex + " " + argLength);
							if(clineIndex+1 > argLength) {
								//System.out.println("lorem text");
							
							}else {
							switch(args[clineIndex]) {
							case "lorem":
								System.out.println("lorem text");
								//set lib as lorem//////////////////////////////////////////////////////
								text = "lorem";
								clineIndex++;           
								break;
							case "anguish":
								text = "anguish";
								clineIndex++;
								//set lib as anguish
								break;
							case "mode":
							case "-m":
								System.out.println("change mode");
								clineIndex++;
								break;
							default:
								invalidArg = true;
								System.out.println("Invalid text");
								clineIndex++;
								break;
							}
							}
							
						}	//call method with all its arguments
							break;// this 
						}
					}
				}else {
					//error
				}
						
				*/
			 
				
			
			
			
			while(interactiveMode) {
				String line = null;
				boolean validGenerate = true;
                line = reader.readLine(prompt, rightPrompt, (MaskingCallback)null, null);
             //   terminal.writer().println("======>\"" + line + "\"");
             //   ParsedLine pl = reader.getParser().parse(line, 0);

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
               
                //check line.equal(...) something and set the value of the variable and call the method
                String split[] = line.split(" ");
               try {
               if(split[0].equals("set")) {
                if(split[1].equals("library")) {
                	System.out.println("Library set to "+ split[2]);
                	text = split[2];
                }else if(split[1].equals("mode")) {
                	mode = split[2];
                	System.out.println("Mode set to " + split[2]);
                }else if(split[1].equals("count")) {
                	count = split[2];
                	System.out.println("count set to " + split[2]);
                }else if(split[1].equals("html")) {
                	html = split[2];
                	System.out.println("html set to " + split[2]);
               }else if(split[1].equals("outfile")) {
            	   out = split[2];
            	   System.out.println("outfile set to " + split[2]);
               }
               else {
            	   validGenerate = false;
               }
                
               }
               }catch(Exception e) {
            	   System.out.println("Please state your argument");
               }
               if(split[0].equals("generate")) {
					outText(text, count, mode, out, html,validGenerate);
               }
               if(split[0].equals("version")) {
            	   System.out.println("Version Super Alpha");
               }
               if(split[0].equals("help")) {
            	   usage();
               }
               if(split[0].equals("show")) {
            	   System.out.println("Library: "+text+" Mode: "+mode+" Count: "+count+" html: "+html+" outfile: "+out);
               }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
